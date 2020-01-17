package ${package}.template;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Class for AbstractTemperatureAlertTests. This class includes the common logic for integration tests and
 * blackbox tests.
 *
 */
public abstract class ${classNameOfAbstractTest} {

    //Abstract method used to set up a cluster with dependent docker containers for the siddhi app.
    @BeforeClass
    public abstract void setUpCluster();

    //Abstract method used to stop the running docker containers.
    @AfterClass
    public abstract void shutdownCluster();

    //testMessageConsumption() could be used for testing message consumption and it's implementation will
    // differ according to the siddhi app used.
    @Test
    public void testMessageConsumption() {

    }

    //testAppOutput() could be used for testing the output of the siddhi app and it's implementation will
    // differ according to the siddhi app used.
    @Test
    public void testAppOutput() {

    }
}
