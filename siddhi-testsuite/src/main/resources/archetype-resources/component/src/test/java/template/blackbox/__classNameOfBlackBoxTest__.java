package template.blackbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import template.${classNameOfAbstractTest};

/**
 * Class for black box testing. Run black-box testing by configuring the Siddhi Runner instances to communicate
 * with the actual external systems.
 */
public class ${classNameOfBlackBoxTest} extends ${classNameOfAbstractTest} {

    private static final Logger logger = LoggerFactory.getLogger(${classNameOfBlackBoxTest}.class);

    @BeforeClass
    public void setUpCluster() {

    }

    @AfterClass
    public void shutdownCluster() {

    }
}
