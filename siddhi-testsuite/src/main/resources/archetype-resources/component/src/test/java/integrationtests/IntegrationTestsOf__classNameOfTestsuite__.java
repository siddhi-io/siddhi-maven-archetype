package ${package}.integrationtests;

import com.google.common.io.Resources;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.exception.ConnectionUnavailableException;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.distribution.test.framework.MySQLContainer;
import io.siddhi.distribution.test.framework.NatsContainer;
import io.siddhi.distribution.test.framework.SiddhiRunnerContainer;
import io.siddhi.distribution.test.framework.util.DatabaseClient;
import io.siddhi.distribution.test.framework.util.NatsClient;
import io.siddhi.extension.io.nats.sink.NATSSink;
import io.siddhi.extension.map.json.sinkmapper.JsonSinkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Class for integration testing.
 *
 */
public class IntegrationTestsOf${classNameOfTestsuite} {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTestsOf${classNameOfTestsuite}.class);

    MySQLContainer mySQLContainer;
    NatsContainer natsContainer;
    SiddhiRunnerContainer siddhiRunnerContainer;

    NatsClient natsClient;
    WaitingConsumer siddhiLogConsumer = new WaitingConsumer();

    private String natsClusterId = "TemperatureCluster";
    private String natsUrl = "nats-streaming";
    private String natsInputDestination = "Temp-Alert-App_DeviceTempStream";
    private String natsOutputDestination = "Temp-Alert-App_AlertStream";

    private static final String DATABSE_NAME = "TemperaureDB";
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
        setClusterConfigs(NATS_CLUSTER_ID, natsContainer.getBootstrapServerUrl(), NATS_INPUT_DESTINATION,
                NATS_OUTPUT_DESTINATION);
    }

    public void setClusterConfigs(String natsClusterId, String natsUrl, String natsInputDestination,
                                  String natsOutputDestination) {
        this.natsClusterId = natsClusterId;
        this.natsUrl = natsUrl;
        this.natsInputDestination = natsInputDestination;
        this.natsOutputDestination = natsOutputDestination;
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

    @Test
    public void testDBPersistence() throws SQLException, InterruptedException, IOException, TimeoutException,
            ConnectionUnavailableException {

        natsClient.publish(NATS_INPUT_DESTINATION, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"internal\",\n" +
                "        \"deviceID\": \"C250i\",\n" +
                "        \"temp\": 30.5,\n" +
                "        \"roomID\": \"F2-Conference\"\n" +
                "    }\n" +
                "}");
        ResultSet resultSet = null;
        try {
            Thread.sleep(1000);
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

    @Test
    public void testMessageConsumption() throws InterruptedException, IOException, TimeoutException {
        natsClient.publish(natsInputDestination, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"dummyType\",\n" +
                "        \"deviceID\": \"dummyDeviceID\",\n" +
                "        \"temp\": 50.0,\n" +
                "        \"roomID\": \"dummyRoomID\"\n" +
                "    }\n" +
                "}");
        try {
            siddhiLogConsumer.waitUntil(frame ->
                            frame.getUtf8String().contains("data=[dummyType, dummyDeviceID, 50.0, dummyRoomID]"),
                    50, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            Assert.fail("Message consumption acknowledgement is not available in Siddhi Runner logs.");
        }
    }

    @Test
    public void testMessagePublishingWithSiddhi() throws InterruptedException {

        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("nats-sink", NATSSink.class);
        siddhiManager.setExtension("sink-mapper-json", JsonSinkMapper.class);
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                "@App:name(\"PublishToNatsApp\")\n" +
                "\n" +
                "@App:description('Publishes events to nats topic')\n" +
                "\n" +
                "@sink(type='nats', cluster.id='" + natsClusterId + "', " +
                "destination = '" + natsInputDestination + "', " +
                "bootstrap.servers = '" + natsUrl + "' ,@map(type='json'))\n" +
                "define stream DeviceTemperatureStream (type string, deviceID string, " +
                "temp double, roomID string);");
        siddhiAppRuntime.start();
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("DeviceTemperatureStream");
        inputHandler.send(new Object[]{"fooType", "001", 60, "202"});
        try {
            siddhiLogConsumer.waitUntil(frame ->
                            frame.getUtf8String().contains("data=[fooType, 001, 60.0, 202]"),
                    50, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            Assert.fail("Message consumption acknowledgement is not available in Siddhi Runner logs.");
        } finally {
            siddhiManager.shutdown();
        }
    }

    @Test
    public void testAppOutput() throws InterruptedException, IOException, TimeoutException {

        NatsClient.ResultHolder resultHolder = new NatsClient.ResultHolder(1, 3);
        NatsClient natsClient = new NatsClient(natsClusterId, "stan_test1",
                natsUrl, resultHolder);
        natsClient.connect();
        natsClient.subscribeFromNow(natsOutputDestination);
        natsClient.publish(natsInputDestination, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"monitored\",\n" +
                "        \"deviceID\": \"C001\",\n" +
                "        \"temp\": 40.2,\n" +
                "        \"roomID\": \"F2-Conference\"\n" +
                "    }\n" +
                "}");

        natsClient.publish(natsInputDestination, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"monitored\",\n" +
                "        \"deviceID\": \"C001\",\n" +
                "        \"temp\": 60.0,\n" +
                "        \"roomID\": \"F2-Conference\"\n" +
                "    }\n" +
                "}");
        natsClient.publish(natsInputDestination, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"monitored\",\n" +
                "        \"deviceID\": \"C001\",\n" +
                "        \"temp\": 80.0,\n" +
                "        \"roomID\": \"F2-Conference\"\n" +
                "    }\n" +
                "}");
        natsClient.publish(natsInputDestination, "{\n" +
                "    \"event\": {\n" +
                "        \"type\": \"monitored\",\n" +
                "        \"deviceID\": \"C001\",\n" +
                "        \"temp\": 30.0,\n" +
                "        \"roomID\": \"F2-Conference\"\n" +
                "    }\n" +
                "}");
        Thread.sleep(10000);
        Assert.assertTrue(((ArrayList<String>) resultHolder.waitAndGetResults()).get(0).contains("\"peakTemp\":80.0"));
    }
}
