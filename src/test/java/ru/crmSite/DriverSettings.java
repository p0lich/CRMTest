package ru.crmSite;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import ru.CRMEntities.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ru.CRMEntities.UserEntity.*;

public class DriverSettings {
    public ChromeDriver driver;
    ArrayList<UserEntity> users = new ArrayList<UserEntity>();

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Temp\\ChromeDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://openjdk-app-crm-db.7e14.starter-us-west-2.openshiftapps.com/");

        users.add(new UserEntity("Elizaveta", "1111111", "user1@mail.ru",
                "Иванов", "Иван", "Иванович",
                "88", "88001111111"));

        users.add(new UserEntity("admin", "zxcvbn",
                "", "Админов", "Админ", "Админович",
                "0", ""));

        users.add(new UserEntity("nonUsableUser", "1111111", "nonuse@mail.ru",
                "llll", "ffff", "mmmm",
                "333", "8800800"));

        System.out.println("Test start");
    }

    public void userSignIn(UserEntity user, Map<String, String> cssDt)
    {
        driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                .findElement(By.cssSelector(cssDt.get("username")))
                .sendKeys(user.login);
        driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                .findElement(By.cssSelector(cssDt.get("password")))
                .sendKeys(user.password);

        driver.findElement(By.cssSelector(cssDt.get("sign-in-btn"))).click();
    }

    public ArrayList<String> genereteInputData(Map<String, String> cssDt) {
        ArrayList<String> result = new ArrayList<String>();

        List<WebElement> sendData = driver.findElement(By.cssSelector(cssDt.get("hist")))
                .findElements(By.cssSelector(cssDt.get("removable")));

        String correctElectro = "";
        String correctCold = "";
        String correctHot = "";

        if (sendData.size() > 0) {
            String lastInput = sendData.get(sendData.size() - 1).getText();
            String[] separatedData = sendData.get(sendData.size() - 1).getText().split("\\s");

            int intElectro = Integer.parseInt(separatedData[0]);
            int intCold = Integer.parseInt(separatedData[1]);
            int intHot = Integer.parseInt(separatedData[2]);

            correctElectro = Integer.toString(intElectro);
            correctCold = Integer.toString(intCold);
            correctHot = Integer.toString(intHot);
        }
        else {
            correctElectro = "1";
            correctCold = "1";
            correctHot = "1";
        }

        result.add(correctElectro);
        result.add(correctCold);
        result.add(correctHot);

        return result;
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Test finished");
        driver.quit();
    }
}
