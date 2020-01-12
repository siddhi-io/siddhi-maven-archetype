package sampletests;

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.distribution.test.framework.MySQLContainer;
import io.siddhi.distribution.test.framework.NatsContainer;
import io.siddhi.distribution.test.framework.SiddhiRunnerContainer;
import io.siddhi.distribution.test.framework.util.NatsClient;
import io.siddhi.extension.io.nats.sink.NATSSink;
import io.siddhi.extension.map.json.sinkmapper.JsonSinkMapper;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Class for AbstractTemperatureAlertTests of Temp-Alert-App. This class includes the common logic for integration
 * tests and blackbox tests.
 *
 * Description: Used for temperature monitoring and anomaly detection. Consumes events from a Nats topic,
 *              filters the event under types 'monitored' and 'internal'.
 *              Monitored events are then sent through a pattern and the matched events will be alerted to a Nats topic.
 *              The internal events are persisted to a table.
 */
public abstract class AbstractTemperatureAlertTests {

    public WaitingConsumer siddhiLogConsumer = new WaitingConsumer();

    public MySQLContainer mySQLContainer;
    public NatsContainer natsContainer;
    public SiddhiRunnerContainer siddhiRunnerContainer;
    public NatsClient natsClient;
    private String natsClusterId;
    private String natsUrl;
    private String natsInputDestination;
    private String natsOutputDestination;
    private String mySqlUrl;
    private String mysqlUsername;
    private String mysqlPassword;

    @BeforeClass
    public abstract void setUpCluster() throws IOException, InterruptedException;

    @AfterClass
    public abstract void shutdownCluster();

    public void configureNatsConnection(String natsClusterId, String natsUrl, String natsInputDestination,
                                        String natsOutputDestination) {
        this.natsClusterId = natsClusterId;
        this.natsUrl = natsUrl;
        this.natsInputDestination = natsInputDestination;
        this.natsOutputDestination = natsOutputDestination;
    }

    public void configureMySqlConnection (String mysqlUrl, String username, String password) {
        this.mySqlUrl = mysqlUrl;
        this.mysqlUsername = username;
        this.mysqlPassword = password;
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
