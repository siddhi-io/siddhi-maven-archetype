package ${package}.template.integration;

import ${package}.template.${classNameOfAbstractTest};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Class for integration testing. Performs integration tests by running the application and dependent services as
 * Docker containers. This ensures that the updated Siddhi application functions as expected.
 *
 */
public class ${classNameOfIntegrationTest} extends ${classNameOfAbstractTest} {

    private static final Logger logger = LoggerFactory.getLogger(${classNameOfIntegrationTest}.class);

    //Set up a cluster with dependent docker containers for the siddhi app before running tests.
    @BeforeClass
    public void setUpCluster() {

    }

    //Use to stop the running docker containers after running tests.
    @AfterClass
    public void shutdownCluster() {

    }

}
