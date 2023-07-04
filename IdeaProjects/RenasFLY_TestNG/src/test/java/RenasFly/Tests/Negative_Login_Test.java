package RenasFly.Tests;
import RenasFly.utilities.BrowserUtil;
import RenasFly.utilities.DriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import RenasFly.base.TestBase;

import java.time.Duration;

public class Negative_Login_Test extends TestBase {


    @Test
    public void blankUsernameBlankPassword (){ // locate login button and click it without sending any keys
        WebElement logingButton =DriverUtil.getDriver().findElement(By.xpath("//button[.='Login']"));

        Assert.assertTrue(!logingButton.isEnabled());
    }
    @Test
    public void validUsernameWrongPassword(){
        WebElement username = DriverUtil.getDriver().findElement(By.xpath("//input[@name='userEmail']"));
        WebElement password =DriverUtil.getDriver().findElement(By.xpath("//input[contains(@name,'Password')]"));
        WebElement logingButton =DriverUtil.getDriver().findElement(By.xpath("//button[.='Login']"));
        username.sendKeys("Demiberbatov@yahoo.com");
        password.sendKeys("Aa123456719");
        logingButton.click();

        WebElement errorMessage=DriverUtil.getDriver().findElement(By.xpath("//div[.='Wrong Email Or Password']"));
        WebDriverWait explicitlyWaiting = new WebDriverWait(DriverUtil.getDriver(), Duration.ofSeconds(5));
        explicitlyWaiting.until(ExpectedConditions.visibilityOf(errorMessage));

        Assert.assertTrue(errorMessage.isDisplayed());

    }
    @Test
    public void WrongUsernameValidPassword(){
        WebElement username = DriverUtil.getDriver().findElement(By.xpath("//input[@name='userEmail']"));
        WebElement password =DriverUtil.getDriver().findElement(By.xpath("//input[contains(@name,'Password')]"));
        WebElement logingButton =DriverUtil.getDriver().findElement(By.xpath("//button[.='Login']"));

        username.sendKeys("Demaberbatov@yahoo.com");
        password.sendKeys("Aa123456719");
        logingButton.click();

        WebElement errorMessage=DriverUtil.getDriver().findElement(By.xpath("//div[.='Wrong Email Or Password']"));
        WebDriverWait explicitlyWaiting = new WebDriverWait(DriverUtil.getDriver(), Duration.ofSeconds(5));
        explicitlyWaiting.until(ExpectedConditions.visibilityOf(errorMessage));

        Assert.assertTrue(errorMessage.isDisplayed());
    }
    @Test
    public void WrongUsernameWrongPassword(){
        WebElement username = DriverUtil.getDriver().findElement(By.xpath("//input[@name='userEmail']"));
        WebElement password =DriverUtil.getDriver().findElement(By.xpath("//input[contains(@name,'Password')]"));
        WebElement logingButton =DriverUtil.getDriver().findElement(By.xpath("//button[.='Login']"));

        username.sendKeys("Demaberbatov@yahoo.com");
        password.sendKeys("Aa124456719");
        logingButton.click();

        WebElement errorMessage=DriverUtil.getDriver().findElement(By.xpath("//div[.='Wrong Email Or Password']"));
        WebDriverWait explicitlyWaiting = new WebDriverWait(DriverUtil.getDriver(), Duration.ofSeconds(5));
        explicitlyWaiting.until(ExpectedConditions.visibilityOf(errorMessage));

        Assert.assertTrue(errorMessage.isDisplayed());
    }

}
