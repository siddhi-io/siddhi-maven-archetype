/*
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package ${package}.sample.integration;

import com.google.common.io.Resources;
import io.siddhi.core.exception.ConnectionUnavailableException;
import io.siddhi.distribution.test.framework.MySQLContainer;
import io.siddhi.distribution.test.framework.NatsContainer;
import io.siddhi.distribution.test.framework.SiddhiRunnerContainer;
import io.siddhi.distribution.test.framework.util.DatabaseClient;
import io.siddhi.distribution.test.framework.util.NatsClient;
import ${package}.sample.TemperatureAlertAbstractTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Integration tests for Temp-Alert-App. Performs integration tests by running the application and dependent
 * services as Docker containers. This ensures that the updated Siddhi application functions as expected.
 *
 * Description: Used for temperature monitoring and anomaly detection. Consumes events from a Nats topic,
 *              filters the event under types 'monitored' and 'internal'.
 *              Monitored events are then sent through a pattern and the matched events will be alerted to a Nats topic.
 *              The internal events are persisted to a table.
 */
public class TemperatureAppIntegrationTestCase extends TemperatureAlertAbstractTestCase {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureAppIntegrationTestCase.class);

    public MySQLContainer mySQLContainer;
    public NatsContainer natsContainer;

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
