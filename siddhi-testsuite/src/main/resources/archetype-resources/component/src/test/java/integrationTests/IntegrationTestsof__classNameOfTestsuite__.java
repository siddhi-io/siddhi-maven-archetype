package ${package}.integrationTests;

import ${package}.LoggerServiceContainer;
import io.siddhi.core.exception.ConnectionUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

class IntegrationTestsOf${classNameOfTestsuite}{

    LoggerServiceContainer loggerServiceContainer;
    private static final Logger log = LoggerFactory.getLogger(IntegrationTestsOf${classNameOfTestsuite}.class);

    @BeforeClass
    public void setUpCluster() throws IOException, InterruptedException {
        loggerServiceContainer = new LoggerServiceContainer()
            .withLogConsumer(new Slf4jLogConsumer(log));
        loggerServiceContainer.start();
    }

    @Test
    public void testIntegration() throws SQLException, InterruptedException, IOException, TimeoutException,
        ConnectionUnavailableException {
        //
    }

    @AfterClass
    public void shutdownCluster() {
        if (loggerServiceContainer != null) {
            loggerServiceContainer.stop();
        }
    }

}