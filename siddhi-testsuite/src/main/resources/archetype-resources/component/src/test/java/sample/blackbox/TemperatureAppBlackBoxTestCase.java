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

package ${package}.sample.blackbox;

import com.google.common.io.Resources;
import io.siddhi.distribution.test.framework.SiddhiRunnerContainer;
import io.siddhi.distribution.test.framework.util.NatsClient;
import io.siddhi.testsuite.containers.LoggerServiceContainer;
import ${package}.sample.TemperatureAlertAbstractTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Blackbox tests for Temp-Alert-App. Run black-box testing by configuring the Siddhi Runner instances
 * to communicate with the actual external systems.
 *
 * Description: Used for temperature monitoring and anomaly detection. Consumes events from a Nats topic,
 *              filters the event under types 'monitored' and 'internal'.
 *              Monitored events are then sent through a pattern and the matched events will be alerted to a Nats topic.
 *              The internal events are persisted to a table.
 *
 * Note: This class is disabled in testng. After updating the Nats and MySql configurations as per your environment,
 * enable it from src/test/resources/testng.xml file.
 */
public class TemperatureAppBlackBoxTestCase extends TemperatureAlertAbstractTestCase {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureAppBlackBoxTestCase.class);

    //Update the Nats and MySql configurations as per your environment for Siddhi test framework to communicate with
    // the said instances
    private static final String DATABSE_JDBC_URL = "jdbc:mysql://mysql:3306/";
    private static final String DATABSE_USERNAME = "username";
    private static final String DATABSE_PASSWORD = "password";
    private static final String DATABSE_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String NATS_CLUSTER_ID = "TemperatureCluster";
    private static final String NATS_BOOTSTRAP_URL = "nats://nats-streaming:443";
    private static final String NATS_INPUT_DESTINATION = "Temp-Alert-App_DeviceTempStream";
    private static final String NATS_OUTPUT_DESTINATION = "Temp-Alert-App_AlertStream";

    @BeforeClass
    public void setUpCluster() throws IOException, InterruptedException {
        Path jarsFromMaven = Paths.get("target", "artifacts/jars");
        URL appUrl = Resources.getResource("artifacts/apps");
        URL configUrl = Resources.getResource("artifacts/config/Datasource.yaml");

        LoggerServiceContainer loggerServiceContainer = new LoggerServiceContainer()
                .withLogConsumer(new Slf4jLogConsumer(logger));
        loggerServiceContainer.start();
        loggerServiceContainer.followOutput(logConsumer, OutputFrame.OutputType.STDOUT);

        natsClient = new NatsClient(NATS_CLUSTER_ID, NATS_BOOTSTRAP_URL);
        natsClient.connect();

        Map<String, String> envMap = new HashMap<>();
        envMap.put("CLUSTER_ID", NATS_CLUSTER_ID);
        envMap.put("INPUT_DESTINATION", NATS_INPUT_DESTINATION);
        envMap.put("OUTPUT_DESTINATION", NATS_OUTPUT_DESTINATION);
        envMap.put("NATS_URL", NATS_BOOTSTRAP_URL);
        envMap.put("DATABASE_URL", DATABSE_JDBC_URL);
        envMap.put("USERNAME", DATABSE_USERNAME);
        envMap.put("PASSWORD", DATABSE_PASSWORD);
        envMap.put("JDBC_DRIVER_NAME", DATABSE_DRIVER_NAME);
        envMap.put("LOGGER_SERVICE_URL", loggerServiceContainer.getUrl());
        siddhiRunnerContainer = new SiddhiRunnerContainer("siddhiio/siddhi-runner-alpine:latest-dev")
                .withSiddhiApps(appUrl.getPath())
                .withJars(jarsFromMaven.toString(), false)
                .withConfig(configUrl.getPath())
                .withEnv(envMap)
                .withLogConsumer(new Slf4jLogConsumer(logger));
        siddhiRunnerContainer.start();
        siddhiRunnerContainer.followOutput(logConsumer, OutputFrame.OutputType.STDOUT);
        configureNatsConnection(NATS_CLUSTER_ID, NATS_BOOTSTRAP_URL, NATS_INPUT_DESTINATION, NATS_OUTPUT_DESTINATION);
    }

    @AfterClass
    public void shutdownCluster() {
        if (siddhiRunnerContainer != null) {
            siddhiRunnerContainer.stop();
        }
    }
}
