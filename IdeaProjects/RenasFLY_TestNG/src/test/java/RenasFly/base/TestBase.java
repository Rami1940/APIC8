package RenasFly.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import RenasFly.utilities.BrowserUtil;
import RenasFly.utilities.DriverUtil;
import java.util.concurrent.TimeUnit;



    public abstract class TestBase {

        @BeforeMethod
        public void SetUp(){
            DriverUtil.getDriver().manage().window().maximize();
            DriverUtil.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            DriverUtil.getDriver().get("http://34.136.20.156/#/login");
            BrowserUtil.ImplicitWaitMethod(5);
        }

        @AfterMethod
        public void closing(){
            BrowserUtil.wait(2);
            DriverUtil.closeDriver();
        }

}
