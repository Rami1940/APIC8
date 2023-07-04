package RenasFly.Tests;

import RenasFly.base.TestBase;
import RenasFly.utilities.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Positive_Login_Test extends TestBase {


    @Test
    public void Positivelogin(){
        WebElement username =DriverUtil.getDriver().findElement(By.xpath("//input[@name='userEmail']"));
        WebElement password =DriverUtil.getDriver().findElement(By.xpath("//input[contains(@name,'Password')]"));
        WebElement logingButton =DriverUtil.getDriver().findElement(By.xpath("//button[.='Login']"));
        username.sendKeys("Demiberbatov@yahoo.com");
        password.sendKeys("Aa123456789");
        logingButton.click();

        WebElement profilePage = DriverUtil.getDriver().findElement(By.xpath("//li[.='Sandor']"));
        Assert.assertTrue(profilePage.isDisplayed());

    }



}
