package RenasFly.utilities;

import java.security.PublicKey;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BrowserUtil {
    public static void wait(int second){
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void ImplicitWaitMethod(long time){
        DriverUtil.getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }
    public static void SleepMethod(long time) throws InterruptedException {
        Thread.sleep(time);
    }


}
