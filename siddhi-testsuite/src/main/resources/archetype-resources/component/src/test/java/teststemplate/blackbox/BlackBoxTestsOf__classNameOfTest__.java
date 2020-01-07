package teststemplate.blackbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import teststemplate.AbstractTests;

/**
 * Class for black box testing.
 */
public class BlackBoxTestsOf${classNameOfTest} extends AbstractTests {

    private static final Logger logger = LoggerFactory.getLogger(BlackBoxTestsOf${classNameOfTest}.class);

    @BeforeClass
    public void setUpCluster() {

    }

    @AfterClass
    public void shutdownCluster() {

    }
}
