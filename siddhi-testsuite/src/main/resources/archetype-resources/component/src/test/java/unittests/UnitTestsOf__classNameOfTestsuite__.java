package ${package}.unittests;

import com.google.common.io.Resources;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.event.Event;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import io.siddhi.distribution.test.framework.SiddhiRunnerContainer;
import ${package}.containers.LoggerServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for unit testing.
 *
 */
public class UnitTestsOf${classNameOfTestsuite} {

    private static final Logger logger = LoggerFactory.getLogger(UnitTestsOf${classNameOfTestsuite}.class);
    private static URL appUrl = Resources.getResource("artifacts/apps/Temp-Alert-App.siddhi");
    private volatile AtomicInteger count = new AtomicInteger(0);
    LoggerServiceContainer loggerServiceContainer;
    WaitingConsumer siddhiLogConsumer = new WaitingConsumer();

    @BeforeClass
    private void setUpTest() {
        Map<String, String> envMap = new HashMap<>();
        envMap.put("CLUSTER_ID", "");
        envMap.put("INPUT_DESTINATION", "");
        envMap.put("OUTPUT_DESTINATION", "");
        envMap.put("NATS_URL", "");
        envMap.put("DATABASE_URL", "");
        envMap.put("USERNAME", "");
        envMap.put("PASSWORD", "");
        envMap.put("JDBC_DRIVER_NAME", "");
        System.getProperties().putAll(envMap);
    }

    @Test
    public void testLoggerService() throws InterruptedException {
        logger.info("Test logger service container.");

        loggerServiceContainer = new LoggerServiceContainer()
            .withLogConsumer(new Slf4jLogConsumer(logger));
        loggerServiceContainer.start();
        loggerServiceContainer.followOutput(siddhiLogConsumer, OutputFrame.OutputType.STDOUT);

        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                        "@App:name('Convert-Temperature')\n" +
                        "\n" +
                        "@App:description('Convert Farenheit into Celcius.')\n" +
                        "\n" +
                        "@source(type = 'http', receiver.url = 'http://0.0.0.0:8005/apiRequest', " +
                                    "basic.auth.enabled = 'false', @map(type = 'json'))\n" +
                        "define stream TemperatureStream (farenheit int);\n" +
                        "@sink(type = 'http', publisher.url = \'" + loggerServiceContainer.getUrl() + "\' , " +
                                    "method = 'POST', @map(type='json'))\n" +
                        "define stream CalculateTemperature (farenheit int, celcius int);" +
                        "@info(name='Temperature records information.')\n" +
                        "from TemperatureStream\n" +
                        "select farenheit, (farenheit-32)*5/9 as celcius\n" +
                        "insert into CalculateTemperature; ");
        siddhiAppRuntime.start();
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("TemperatureStream");
        inputHandler.send(new Object[]{32});
        try {
            Thread.sleep(10000);
            siddhiLogConsumer.waitUntil(frame ->
                frame.getUtf8String().contains("{event={farenheit=32.0, celcius=0.0}}"),
            5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            Assert.fail("Message consumption acknowledgement is not available in Siddhi Runner logs.");
        } finally {
            siddhiManager.shutdown();
            loggerServiceContainer.stop();
        }
    }

    @Test
    public void testMoniteredFilter() throws InterruptedException, IOException {
        logger.info("Tests Monitered Filter Query");

        String testQueryName = "monitered-filter";
        SiddhiManager siddhiManager = new SiddhiManager();
        String siddhiApp = readFileToString(appUrl.getPath());

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSandboxSiddhiAppRuntime(siddhiApp);
        siddhiAppRuntime.addCallback(testQueryName, new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {

                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count.incrementAndGet();
                if (count.get() == 1) {
                    Assert.assertTrue("CyrusOne".equals(inEvents[0].getData(0)));
                }
                if (count.get() == 2) {
                    Assert.assertTrue("Kennisnet".equals(inEvents[0].getData(0)));
                }
            }
        });
        InputHandler deviceTemperatureStream = siddhiAppRuntime.getInputHandler("DeviceTemperatureStream");
        siddhiAppRuntime.start();

        deviceTemperatureStream.send(new Object[]{"monitored", "CyrusOne", 35.5, "ServerRoom1"});
        deviceTemperatureStream.send(new Object[]{"internal", "Generator", 28.6, "Basement"});
        deviceTemperatureStream.send(new Object[]{"monitored", "Kennisnet", 60.2, "ServerRoom5"});
        SiddhiTestHelper.waitForEvents(10, 2, count, 100);
        Event[] eventsFromInternalDevicesTempTable = siddhiAppRuntime.query(
                "from InternalDevicesTempTable ");
        EventPrinter.print(eventsFromInternalDevicesTempTable);
        Assert.assertEquals(1, eventsFromInternalDevicesTempTable.length);
        Assert.assertEquals(eventsFromInternalDevicesTempTable[0].getData()[1], "Generator");
        siddhiAppRuntime.shutdown();
    }

    private String readFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), Charset.forName("UTF-8"));
    }

    @Test
    public void testSiddhiRunnerStartup() {
        SiddhiRunnerContainer siddhiRunnerContainer =
                new SiddhiRunnerContainer("siddhiio/siddhi-runner-alpine:latest-dev")
                        .withLogConsumer(new Slf4jLogConsumer(logger));
        siddhiRunnerContainer.start();
        WaitingConsumer consumer = new WaitingConsumer();
        siddhiRunnerContainer.followOutput(consumer, OutputFrame.OutputType.STDOUT);
        try {
            consumer.waitUntil(frame ->
                            frame.getUtf8String().contains("Siddhi Runner Distribution started"),
                    5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            Assert.fail("Siddhi Runner failed to start.");
        } finally {
            siddhiRunnerContainer.stop();
        }
    }
}
