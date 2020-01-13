package ${package}.template.unit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is the class for unit testing. Siddhi is used in an embedded sandbox mode to make sure no network connections
 * or external dependencies are required for testing. This requires to write a Java code to test the Siddhi app.
 *
 */
public class ${classNameOfUnitTest} {

    private static final Logger logger = LoggerFactory.getLogger(${classNameOfUnitTest}.class);

    @BeforeClass
    private void setUpTest() {

    }

    @Test
    public void testQuery() {

    }

}
