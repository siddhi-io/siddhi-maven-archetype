package sampletests.integration;

import com.google.common.io.Resources;
import io.siddhi.distribution.test.framework.MySQLContainer;
import io.siddhi.distribution.test.framework.NatsContainer;
import io.siddhi.distribution.test.framework.SiddhiRunnerContainer;
import io.siddhi.distribution.test.framework.util.NatsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import sampletests.AbstractTemperatureAlertTests;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for integration testing. Performs integration tests by running the application and dependent services as
 * Docker containers. This ensures that the updated Siddhi application functions as expected.
 *
 */
public class IntegrationTestsOfTemperatureApp extends AbstractTemperatureAlertTests {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTestsOfTemperatureApp.class);

    private static final String DATABSE_NAME = "TemperatureDB";
    private static final String DATABSE_HOST = "mysqldb";
    private static final String NATS_CLUSTER_ID = "TemperatureCluster";
    private static final String NATS_CLUSTER_HOST = "nats-streaming";
    private static final String NATS_INPUT_DESTINATION = "Temp-Alert-App_DeviceTempStream";
    private static final String NATS_OUTPUT_DESTINATION = "Temp-Alert-App_AlertStream";

    @BeforeClass
    public void setUpCluster() throws IOException, InterruptedException {
        Path jarsFromMaven = Paths.get("target", "artifacts/jars");
        URL appUrl = Resources.getResource("artifacts/apps");
        URL configUrl = Resources.getResource("artifacts/config/Datasource.yaml");
        Network network = Network.newNetwork();

        mySQLContainer = new MySQLContainer()
                .withDatabaseName(DATABSE_NAME)
                .withNetworkAliases(DATABSE_HOST)
                .withNetwork(network);
        mySQLContainer.start();

        natsContainer = new NatsContainer()
                .withNetwork(network)
                .withClusterId(NATS_CLUSTER_ID)
                .withNetworkAliases(NATS_CLUSTER_HOST);
        natsContainer.start();
        natsClient = new NatsClient(NATS_CLUSTER_ID, natsContainer.getBootstrapServerUrl());
        natsClient.connect();

        Map<String, String> envMap = new HashMap<>();
        envMap.put("CLUSTER_ID", NATS_CLUSTER_ID);
        envMap.put("INPUT_DESTINATION", NATS_INPUT_DESTINATION);
        envMap.put("OUTPUT_DESTINATION", NATS_OUTPUT_DESTINATION);
        envMap.put("NATS_URL", natsContainer.getNetworkedBootstrapServerUrl());
        envMap.put("DATABASE_URL", mySQLContainer.getNetworkedJdbcUrl());
        envMap.put("USERNAME", mySQLContainer.getUsername());
        envMap.put("PASSWORD", mySQLContainer.getPassword());
        envMap.put("JDBC_DRIVER_NAME", mySQLContainer.getDriverClassName());
        siddhiRunnerContainer = new SiddhiRunnerContainer("siddhiio/siddhi-runner-alpine:latest-dev")
                .withSiddhiApps(appUrl.getPath())
                .withJars(jarsFromMaven.toString(), false)
                .withConfig(configUrl.getPath())
                .withNetwork(network)
                .withEnv(envMap)
                .withLogConsumer(new Slf4jLogConsumer(logger));
        siddhiRunnerContainer.start();
        siddhiRunnerContainer.followOutput(siddhiLogConsumer, OutputFrame.OutputType.STDOUT);
        configureNatsConnection(NATS_CLUSTER_ID, natsContainer.getBootstrapServerUrl(), NATS_INPUT_DESTINATION,
                NATS_OUTPUT_DESTINATION);
    }

    @Test
    public void testDBPersistence() throws SQLException, InterruptedException, IOException, TimeoutException,
            ConnectionUnavailableException {

        natsClient.publish(natsInputDestination, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"internal\",\n" +
                "        \"deviceID\": \"C250i\",\n" +
                "        \"temp\": 30.5,\n" +
                "        \"roomID\": \"F2-Conference\"\n" +
                "    }\n" +
                "}");
        ResultSet resultSet = null;
        try {
            Thread.sleep(10000);
            resultSet = DatabaseClient.executeQuery(mySQLContainer, "SELECT * FROM InternalDevicesTempTable");
            Assert.assertNotNull(resultSet);
            Assert.assertEquals("C250i", resultSet.getString(2));
            Assert.assertEquals(30.5, resultSet.getDouble(3));
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @AfterClass
    public void shutdownCluster() {
        if (natsContainer != null) {
            natsContainer.stop();
        }
        if (mySQLContainer != null) {
            mySQLContainer.stop();
        }
        if (siddhiRunnerContainer != null) {
            siddhiRunnerContainer.stop();
        }
    }
}
