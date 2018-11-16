package tests.webDT;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import tests.web.initializer.TestNGListeners;

@Listeners(TestNGListeners.class)
public class Sample extends BaseTest {

    final static Logger logger = Logger.getLogger(Sample.class);

    @BeforeClass
    public void beforeClass() {
        logger.debug("Before class");
    }

    @BeforeMethod
    public void beforMethod() {
        logger.debug("Before Method");
    }

    @Test(priority = 0)
    public void test1() {
        logger.debug("test1");
    }

    @Test(priority = 1)
    public void test2() {
        throw new SkipException("");
    }

    @Test(priority = 2)
    public void test3() {
        Assert.assertTrue(false);
    }

    @AfterMethod
    public void afterMethod() {
        logger.debug("Before method");
    }

    @AfterClass
    public void afterClass() {
        logger.debug("after class");
    }

    @AfterSuite
    public void count() {
        //TestNGListeners s = new TestNGListeners();
        logger.debug(TestNGListeners.total + " total scripts");
        logger.debug(TestNGListeners.failed + " total failed scripts");
        logger.debug(TestNGListeners.passed + " total passed scripts");
        logger.debug(TestNGListeners.skipped + " total skipped scripts");
    }
}
