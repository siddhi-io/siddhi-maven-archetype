package ${package}.blackBoxTests;

import io.siddhi.distribution.test.framework.NatsContainer;
import io.siddhi.distribution.test.framework.util.NatsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlackBoxTestsOf${classNameOfTestsuite} {

    private static final Logger logger = LoggerFactory.getLogger(BlackBoxTestsOf${classNameOfTestsuite}.class);

    @Test
    public void testNatsStartup() throws Exception {
        NatsContainer natsContainer = new NatsContainer()
                .withLogConsumer(new Slf4jLogConsumer(logger));
        natsContainer.start();
        WaitingConsumer consumer = new WaitingConsumer();
        natsContainer.followOutput(consumer, OutputFrame.OutputType.STDERR);
        try {
            consumer.waitUntil(frame ->
                            frame.getUtf8String().contains("Server is ready"),
                    5, TimeUnit.SECONDS);
            testNatsFunctionality(natsContainer);
        } catch (TimeoutException e) {
            Assert.fail("Nats container failed to start.");
        } finally {
            natsContainer.stop();
        }
    }

    private void testNatsFunctionality(NatsContainer natsContainer) throws Exception {
        NatsClient.ResultHolder fooBarresultHolder = new NatsClient.ResultHolder(1, 3);
        NatsClient natsClient = new NatsClient(natsContainer.getClusterID(), natsContainer.getBootstrapServerUrl(),
                fooBarresultHolder);
        natsClient.connect();
        natsClient.subscribeFromNow("foo.bar");
        natsClient.publish("foo.bar", "Siddhi rulezzz!");
        Assert.assertTrue(((ArrayList<String>) fooBarresultHolder.waitAndGetResults())
                .get(0).contains("Siddhi rulezzz!"));
    }
}