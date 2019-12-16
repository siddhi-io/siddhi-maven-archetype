package ${package}.blackBoxTests;

import ${package}.LoggerServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BlackBoxTestsOf${classNameOfTestsuite}{

    LoggerServiceContainer loggerServiceContainer;
    private static final Logger log = LoggerFactory.getLogger(BlackBoxTestsOf${classNameOfTestsuite}.class);

    @BeforeClass
    public void setUpCluster() {
        loggerServiceContainer = new LoggerServiceContainer()
        .withLogConsumer(new Slf4jLogConsumer(log));
        loggerServiceContainer.start();
    }

    @AfterClass
    public void shutdownCluster() {
        if (loggerServiceContainer != null) {
            loggerServiceContainer.stop();
        }
    }

}