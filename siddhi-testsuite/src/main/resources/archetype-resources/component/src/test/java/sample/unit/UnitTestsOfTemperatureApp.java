package sample.unit;

import com.google.common.io.Resources;
import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.event.Event;
import io.siddhi.core.query.output.callback.QueryCallback;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is the class for unit testing of Temp-Alert-App. Siddhi is used in an embedded sandbox mode to make sure no
 * network connections or external dependencies are required for testing. This requires to write a Java code to test
 * the Siddhi app.
 *
 */
public class UnitTestsOfTemperatureApp {

    private static final Logger logger = LoggerFactory.getLogger(UnitTestsOfTemperatureApp.class);
    private static URL appUrl = Resources.getResource("artifacts/apps/Temp-Alert-App.siddhi");
    private volatile AtomicInteger count = new AtomicInteger(0);

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
    public void testMoniteredFilter() throws InterruptedException, IOException {
        logger.info("Tests Monitored Filter Query");

        String testQueryName = "monitored-filter";
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

    @Test
    public void testTemperatureRangeFilter() throws InterruptedException, IOException {
        logger.info("Test temperature-range-filter query");

        String testQueryName = "temperature-range-filter";
        SiddhiManager siddhiManager = new SiddhiManager();
        String siddhiApp = readFileToString(appUrl.getPath());

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSandboxSiddhiAppRuntime(siddhiApp);
        siddhiAppRuntime.addCallback(testQueryName, new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {

                EventPrinter.print(timeStamp, inEvents, removeEvents);
                count.incrementAndGet();
                if (count.get() == 1) {
                    Double obj1 = new Double(86.3);
                    Assert.assertTrue(obj1.equals(inEvents[0].getData(1)));
                }
            }
        });
        InputHandler peakTemperatureStream = siddhiAppRuntime.getInputHandler("PeakTempStream");
        siddhiAppRuntime.start();
        peakTemperatureStream.send(new Object[]{"CyrusOne", 35.5, 60.0});
        peakTemperatureStream.send(new Object[]{"Kennisnet", 28.6, 86.3});
        peakTemperatureStream.send(new Object[]{"Generator", 60.2, 65.2});
        SiddhiTestHelper.waitForEvents(10, 1, count, 100);
        siddhiAppRuntime.shutdown();
    }

    private String readFileToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), Charset.forName("UTF-8"));
    }
}
