package ru.crmSite;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class CrmTest extends DriverSettings {
    @Ignore
    @Test
    public void registrationTest() {
        driver.findElement(By.cssSelector(".sign-up-switch")).click();

        driver.findElement(By.cssSelector(".username")).sendKeys("user1");
        driver.findElement(By.cssSelector(".password")).sendKeys("1111");
        driver.findElement(By.cssSelector(".email")).sendKeys("user1@mail.ru");
        driver.findElement(By.cssSelector(".lastName")).sendKeys("Иван");
        driver.findElement(By.cssSelector(".firstName")).sendKeys("Иванов");
        driver.findElement(By.cssSelector(".middleName")).sendKeys("Иванович");
        driver.findElement(By.cssSelector(".apartment")).sendKeys("32");
        driver.findElement(By.cssSelector(".phoneNumber")).sendKeys("88001111111");

        driver.findElement(By.cssSelector(".sign-up-btn")).click();

        String sendForm = driver.findElement(By.cssSelector(".hist-switcher")).getText();

        Assert.assertTrue(sendForm.equals("Отправка"));
    }

    @Ignore
    @Test
    public void signUpTest() {
        driver.findElement(By.cssSelector(".sign-in-form")).findElement(By.cssSelector(".username")).sendKeys("user1");
        driver.findElement(By.cssSelector(".sign-in-form")).findElement(By.cssSelector(".password")).sendKeys("1111");

        driver.findElement(By.cssSelector(".sign-in-btn")).click();

        String sendForm = driver.findElement(By.cssSelector(".hist-switcher")).getText();

        Assert.assertTrue(sendForm.equals("Отправка"));
    }

    @Ignore
    @Test
    public void inputMetersDataTest() throws InterruptedException{
        driver.findElement(By.cssSelector(".sign-in-form")).findElement(By.cssSelector(".username")).sendKeys("user1");
        driver.findElement(By.cssSelector(".sign-in-form")).findElement(By.cssSelector(".password")).sendKeys("1111");
        driver.findElement(By.cssSelector(".sign-in-btn")).click();

        driver.findElement(By.cssSelector(".hist-switcher")).click();
        Thread.sleep(1000);
        List<WebElement> metersData = driver.findElement(By.cssSelector(".hist")).findElements(By.cssSelector(".removable"));
        int sizeBefore = metersData.size();
        metersData.clear();
        driver.navigate().back();

        driver.findElement(By.cssSelector(".electro")).sendKeys("1");
        driver.findElement(By.cssSelector(".cold")).sendKeys("1");
        driver.findElement(By.cssSelector(".hot")).sendKeys("1");

        driver.findElement(By.cssSelector(".send-btn")).click();
        driver.findElement(By.cssSelector(".hist-switcher")).click();
        Thread.sleep(1000);

        metersData = driver.findElement(By.cssSelector(".hist")).findElements(By.cssSelector(".removable"));
        int sizeAfter = metersData.size();

        Assert.assertTrue(sizeAfter - sizeBefore == 4);
    }

    @Test
    public void pageSwapTest() throws InterruptedException{
        Assert.assertTrue(driver.findElement(By.cssSelector(".sign-in-form")).isDisplayed());
        driver.findElement(By.cssSelector(".sign-up-switch")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector(".sign-up-form")).isDisplayed());
        driver.findElement(By.cssSelector(".sign-in-switch")).click();

        driver.findElement(By.cssSelector(".sign-in-form")).findElement(By.cssSelector(".username")).sendKeys("user1");
        driver.findElement(By.cssSelector(".sign-in-form")).findElement(By.cssSelector(".password")).sendKeys("1111");

        driver.findElement(By.cssSelector(".sign-in-btn")).click();
        Thread.sleep(1000);

        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        Assert.assertTrue(currentURL.equals("http://openjdk-app-crm-db.7e14.starter-us-west-2.openshiftapps.com/user_index.html"));

        Assert.assertTrue(driver.findElement(By.cssSelector(".send")).isDisplayed());

        driver.findElement(By.cssSelector(".hist-switcher")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector(".hist")).isDisplayed());

        driver.findElement(By.cssSelector(".exit-btn")).click();
        Thread.sleep(1000);
        currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.equals("http://openjdk-app-crm-db.7e14.starter-us-west-2.openshiftapps.com/"));
    }
}
