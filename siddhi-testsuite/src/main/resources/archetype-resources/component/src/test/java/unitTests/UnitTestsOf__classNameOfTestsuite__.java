package ${package}.unitTests;

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.stream.input.InputHandler;
import ${package}.LoggerServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testng.annotations.Test;

import java.io.IOException;

public class UnitTestsOf${classNameOfTestsuite}{

    private static final Logger log = LoggerFactory.getLogger(UnitTestsOf${classNameOfTestsuite}.class);
    private WaitingConsumer consumer = new WaitingConsumer();

    @Test
    public void testLoggerApp() throws InterruptedException, IOException {
        LoggerServiceContainer loggerServiceContainer = new LoggerServiceContainer()
            .withLogConsumer(new Slf4jLogConsumer(log));
        loggerServiceContainer.start();
        loggerServiceContainer.followOutput(consumer, OutputFrame.OutputType.STDOUT);
        Thread.sleep(3000);
        SiddhiManager siddhiManager = new SiddhiManager();
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
        "@App:name('Transaction-Request-Handler')\n" +
        "@App:description('Notify best selling products')\n" +
        "\n" +
        "@source(type = 'http', receiver.url = \"http://0.0.0.0:8005/apiRequest\", basic.auth.enabled = \"false\", \n" +
            "@map(type = 'json'))\n" +
        "define stream TransactionStream (name string, volume int, unitPrice int);" +
        "\n" +
        "@sink(type = 'http', publisher.url = \" " + loggerServiceContainer.getUrl() +
        "\", method = 'POST', @map(type='json'))\n" +
        "define stream DemandTransactionStream (name string, volume int, totalSales int);" +
        "\n" +
        "@info(name='Log the sales records') \n" +
        "from TransactionStream \n" +
        "select name, volume, volume*unitPrice as totalSales \n" +
        "order by totalSales \n" +
        "insert into DemandTransactionStream; \n");
        siddhiAppRuntime.start();
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("TransactionStream");
        inputHandler.send(new Object[]{"TV", 1, 100});
        inputHandler.send(new Object[]{"Laptop", 2, 200});
        inputHandler.send(new Object[]{"Radio", 1, 150});
        inputHandler.send(new Object[]{"Phone", 3, 100});
        Thread.sleep(3000);
        loggerServiceContainer.stop();
    }
}