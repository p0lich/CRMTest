package ru.crmSite;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class DriverSettings {
    public ChromeDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Temp\\ChromeDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://openjdk-app-crm-db.7e14.starter-us-west-2.openshiftapps.com/");
        System.out.println("Test start");
    }

    @After
    public void close() {
        System.out.println("Test finished");
        driver.quit();
    }
}
