package ru.crmSite;

import io.qameta.allure.Step;
import javafx.util.Pair;
import jdk.nashorn.internal.runtime.ListAdapter;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.CRMEntities.UserEntity;

import java.text.SimpleDateFormat;
import java.util.*;

public class CrmTest extends DriverSettings {

    @Step("Проверка формы регистрации")
    public void registrationTest(Map <String, String> cssDt) {
        driver.findElement(By.cssSelector(cssDt.get("sign-up-switch"))).click();

        UserEntity newUser = users.get(0);

        driver.findElement(By.cssSelector(cssDt.get("username"))).sendKeys(newUser.login);
        driver.findElement(By.cssSelector(cssDt.get("password"))).sendKeys(newUser.password);
        driver.findElement(By.cssSelector(cssDt.get("email"))).sendKeys(newUser.post);
        driver.findElement(By.cssSelector(cssDt.get("lastName"))).sendKeys(newUser.lastName);
        driver.findElement(By.cssSelector(cssDt.get("firstName"))).sendKeys(newUser.firstName);
        driver.findElement(By.cssSelector(cssDt.get("middleName"))).sendKeys(newUser.middleName);
        driver.findElement(By.cssSelector(cssDt.get("apartment"))).sendKeys(newUser.apartmentNumber);
        driver.findElement(By.cssSelector(cssDt.get("phoneNumber"))).sendKeys(newUser.phoneNumber);

        driver.findElement(By.cssSelector(cssDt.get("sign-up-btn"))).click();

        String sendForm = driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).getText();

        Assert.assertTrue("Тест не прошел", sendForm.equals("Отправка показаний"));

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
    }

    @Step("Проверка валидации формы регистрации")
    public void registerValidationTest(Map<String, String> cssDt) throws InterruptedException {
        //dataSet of wrong input data
        ArrayList<UserEntity> wrongSet = new ArrayList<UserEntity>();

        //0, 1: user with wrong login (out of 4-24)

        //0: lower than 4
        wrongSet.add(new UserEntity("usr", "111111", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "1", "88001111111"));
        //1: higher than 24
        wrongSet.add(new UserEntity("useruseruseruseruseruseruser", "111111", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "1", "88001111111"));

        //2, 3: user with wrong password (out of 6-32)

        //2: lower than 6
        wrongSet.add(new UserEntity("user", "123", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));
        //3: higher than 32
        wrongSet.add(new UserEntity("user", "123456781234567812345678123456789", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));

        //4, 5: user with wrong post (out of 5-50)

        //4: lower than 5
        wrongSet.add(new UserEntity("user", "1234567", "u@.i",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));
        //5: higher than 50
        wrongSet.add(new UserEntity("user", "1234567", "useruseruseruseruseruseruseruseruseruseruser@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));

        //6, 7, 8: post that doesn't contain "@", "." or both

        //6: doesn't contain "@"
        wrongSet.add(new UserEntity("user", "1234567", "usrmail.ru",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));
        //7: doesn't contain "."
        wrongSet.add(new UserEntity("user", "1234567", "usr@mailru",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));
        //8: doesn't contain both
        wrongSet.add(new UserEntity("user", "1234567", "usrmailru",
                "llll", "ffff", "mmmm",
                "2", "88001111111"));

        //9, 10: user with wrong lastName (out of 3-50)

        //9: lower than 3
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "ll", "ffff", "mmmm",
                "2", "88001111111"));
        //10: higher than 50
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "lllllllllllllllllllllllllllllllllllllllllllllllllll", "ffff", "mmmm",
                "2", "88001111111"));

        //11, 12: user with wrong firstName (out of 3-50)

        //11: lower than 3
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ff", "mmmm",
                "2", "88001111111"));
        //12 : higher than 50
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffffffffffffffffffffffffffffffffffffffffffffffffffff", "mmmm",
                "2", "88001111111"));

        //13, 14: user with wrong middleName (out of 3-50)

        //13: lower than 3
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mm",
                "2", "88001111111"));
        //14: higher than 50
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm",
                "2", "88001111111"));

        //15, 16, 17: user with wrong apartmentNumber (contains "+", "-" or both)

        //15: contain "+" on wrong position
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "+20+", "88001111111"));
        //16: contain "-"
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "-2", "88001111111"));
        //17: contain both
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "+-2", "88001111111"));

        //18: try to set letters in user apartmentNumber
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "two", "88001111111"));

        //19, 20: user with wrong phoneNumber (out of 6-12)

        //19: lower than 6
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "12345"));
        //20: higher than 12
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "88001111111132321"));

        //21, 22, 23: user with wrong phoneNumber (contains "+", "-" or both)

        //21: contain "+"
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "8800111+"));
        //22: contain "-"
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "8800111-"));
        //23: contain both
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "8800111+-"));

        //24: try to set letters in user phoneNumber
        wrongSet.add(new UserEntity("user", "1234567", "usr@mail.ru",
                "llll", "ffff", "mmmm",
                "2", "onetwothree"));

        //25: try to register user without filling fields
        wrongSet.add(new UserEntity("", "", "",
                "", "", "",
                "", ""));

        //26: try to register with another login
        wrongSet.add(new UserEntity(users.get(0).login, "1111111", "user@mail.ru",
                "llll", "ffff", "mmmm",
                "26", "454545"));

        String wrongColor = "rgba(255, 182, 193, 1)";

        driver.findElement(By.cssSelector(cssDt.get("sign-up-switch"))).click();

        for (int i = 0; i < wrongSet.size(); i++) {
            driver.findElement(By.cssSelector(cssDt.get("username")))
                    .sendKeys(wrongSet.get(i).login);
            driver.findElement(By.cssSelector(cssDt.get("password")))
                    .sendKeys(wrongSet.get(i).password);
            driver.findElement(By.cssSelector(cssDt.get("email")))
                    .sendKeys(wrongSet.get(i).post);
            driver.findElement(By.cssSelector(cssDt.get("lastName")))
                    .sendKeys(wrongSet.get(i).lastName);
            driver.findElement(By.cssSelector(cssDt.get("firstName")))
                    .sendKeys(wrongSet.get(i).firstName);
            driver.findElement(By.cssSelector(cssDt.get("middleName")))
                    .sendKeys(wrongSet.get(i).middleName);
            driver.findElement(By.cssSelector(cssDt.get("apartment")))
                    .sendKeys(wrongSet.get(i).apartmentNumber);
            driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                    .sendKeys(wrongSet.get(i).phoneNumber);

            driver.findElement(By.cssSelector(cssDt.get("sign-up-btn"))).click();

            switch (i){
                case (0):
                    //0: login lower than 4
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе логина", driver.findElement(By.cssSelector(cssDt.get("username")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (1):
                    //1: login higher than 24
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе логина", driver.findElement(By.cssSelector(cssDt.get("username")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (2):
                    //2: password lower than 6
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе пароля", driver.findElement(By.cssSelector(cssDt.get("password")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (3):
                    //3: password higher than 32
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе пароля", driver.findElement(By.cssSelector(cssDt.get("password")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (4):
                    //4: post lower than 5
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе почты", driver.findElement(By.cssSelector(cssDt.get("email")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (5):
                    //5: post higher than 50
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе почты", driver.findElement(By.cssSelector(cssDt.get("email")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (6):
                    //6: post doesn't contain "@"
                    Assert.assertTrue("Тест не прошёл, ошибка в проверке '@'", driver.findElement(By.cssSelector(cssDt.get("email")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (7):
                    //7: post doesn't contain "."
                    Assert.assertTrue("Тест не прошёл, ошибка в проверке '.'", driver.findElement(By.cssSelector(cssDt.get("email")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (8):
                    //8: post doesn't contain both
                    Assert.assertTrue("Тест не прошёл, ошибка в проверке '@' и '.'", driver.findElement(By.cssSelector(cssDt.get("email")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (9):
                    //9: lastName lower than 3
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе фамилии", driver.findElement(By.cssSelector(cssDt.get("lastName")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (10):
                    //10: lastName higher than 50
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе фамилии", driver.findElement(By.cssSelector(cssDt.get("lastName")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (11):
                    //11: firstName lower than 3
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе имени", driver.findElement(By.cssSelector(cssDt.get("firstName")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (12):
                    //12 : firstName higher than 50
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе имени", driver.findElement(By.cssSelector(cssDt.get("firstName")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (13):
                    //13: middleName lower than 3
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе отчества", driver.findElement(By.cssSelector(cssDt.get("middleName")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (14):
                    //14: middleName higher than 50
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе отчества", driver.findElement(By.cssSelector(cssDt.get("middleName")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (15):
                    //15: apartmentNumber contain "+"
                    Assert.assertTrue("Тест не прошёл, ошибка в проверке '+'", driver.findElement(By.cssSelector(cssDt.get("apartment")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (16):
                    //16: apartmentNumber contain "-"
                    Assert.assertTrue("Тест не прошёл, ошибка в проверке '-'", driver.findElement(By.cssSelector(cssDt.get("apartment")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (17):
                    //17: apartmentNumber contain both
                    Assert.assertTrue("Тест не прошёл, ошибка в проверке '+' и '-'", driver.findElement(By.cssSelector(cssDt.get("apartment")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (18):
                    //18: try to set letters in user apartmentNumber
                    Assert.assertTrue("Тест не прошёл, буквы в поле не проходят валидацию", driver.findElement(By.cssSelector(cssDt.get("apartment")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (19):
                    //19: phoneNumber lower than 6
                    Assert.assertTrue("Тест не прошёл, ошибка в нижней границе номера телефона", driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (20):
                    //20: phoneNumber higher than 12
                    Assert.assertTrue("Тест не прошёл, ошибка в верхней границе номера телефона", driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (21):
                    //21: phoneNumber contain "+"
                    Assert.assertTrue("Тест не прошёл,  ошибка в проверке '+'", driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (22):
                    //22: phoneNumber contain "-"
                    Assert.assertTrue("Тест не прошёл,  ошибка в проверке '-'", driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (23):
                    //23: phoneNumber contain both
                    Assert.assertTrue("Тест не прошёл,  ошибка в проверке '+' и '-'", driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (24):
                    //24: try to set letters in user phoneNumber
                    Assert.assertTrue("Тест не прошёл, буквы в поле не проходят валидацию", driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (25):
                    //25: try to register user without filling fields
                    Assert.assertTrue("Тест не прошёл, при всех пустых полях валидация проходит не для каждого", driver.findElement(By.cssSelector(cssDt.get("username")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("password")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("email")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("lastName")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("firstName")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("middleName")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("apartment")))
                            .getCssValue("background-color").equals(wrongColor)
                    && driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
                case (26):
                    //26: try to register with another login
                    Thread.sleep(1000);
                    Assert.assertTrue("Тест не прошёл, валидация уже имеющегося пользователя не проходит", driver.findElement(By.cssSelector(cssDt.get("username")))
                            .getCssValue("background-color").equals(wrongColor));
                    Thread.sleep(1000);
                    break;
            }
            driver.findElement(By.cssSelector(cssDt.get("username"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("password"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("email"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("lastName"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("firstName"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("middleName"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("apartment"))).clear();
            driver.findElement(By.cssSelector(cssDt.get("phoneNumber"))).clear();
        }
        driver.findElement(By.cssSelector(cssDt.get("sign-in-switch"))).click();
    }

    @Step("Проверка формы входа")
    public void signUpTest(Map <String, String> cssDt) {
        userSignIn(users.get(0), cssDt);

        String sendForm = driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).getText();

        Assert.assertTrue("Тест не прошел, вход не был выполнен", sendForm.equals("Отправка показаний"));

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
    }

    @Step("Проверка валидации формы входа")
    public void signUpValidationTest(Map <String, String> cssDt) throws InterruptedException {
        String correctLogin = users.get(0).login;
        String correctPassword = users.get(0).password;

        String nonExistLogin = "SomeRandomGuy";
        String wrongPassword = correctPassword + "wrong";

        //dataSet of wrong input cases
        ArrayList<String[]> wrongSet = new ArrayList<String[]>();

        //0: login doesn't exist in database
        wrongSet.add(new String[]{nonExistLogin, correctPassword});

        //1: login exist, but password wrong
        wrongSet.add(new String[]{correctLogin, wrongPassword});

        String wrongColorLightPink = "rgba(255, 182, 193, 1)";

        for (int i = 0; i < wrongSet.size(); i++) {
            driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                    .findElement(By.cssSelector(cssDt.get("username")))
                    .sendKeys(wrongSet.get(i)[0]);
            driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                    .findElement(By.cssSelector(cssDt.get("password")))
                    .sendKeys(wrongSet.get(i)[1]);

            driver.findElement(By.cssSelector(cssDt.get("sign-in-btn"))).click();

            Thread.sleep(1000);

            switch (i){
                case (0):
                    //0: login doesn't exist in database
                    Assert.assertTrue("Тест не прошёл, при несуществующем пользователе одно из полей не проходит валидацию",
                            driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                            .findElement(By.cssSelector(cssDt.get("username")))
                            .getCssValue("background-color")
                            .equals(wrongColorLightPink)
                    && driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                            .findElement(By.cssSelector(cssDt.get("password")))
                            .getCssValue("background-color")
                            .equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (1):
                    //1: login exist, but password wrong
                    Assert.assertTrue("Тест не прошёл, при неверном пароле одно из полей не проходит валидацию",
                            driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                            .findElement(By.cssSelector(cssDt.get("username")))
                            .getCssValue("background-color")
                            .equals(wrongColorLightPink)
                            && driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                            .findElement(By.cssSelector(cssDt.get("password")))
                            .getCssValue("background-color")
                            .equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;
            }

            driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                    .findElement(By.cssSelector(cssDt.get("username")))
                    .clear();
            driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
                    .findElement(By.cssSelector(cssDt.get("password")))
                    .clear();
        }
    }

    @Step("Проверка отправки показаний")
    public void inputMetersDataTest(Map <String, String> cssDt) throws InterruptedException{
        userSignIn(users.get(0), cssDt);

        driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).click();
        Thread.sleep(1000);

        ArrayList<String> dataForSend = genereteInputData(cssDt);
        List<WebElement> metersData = driver.findElement(By.cssSelector(cssDt.get("hist")))
                .findElements(By.cssSelector(cssDt.get("removable")));
        int sizeBefore = metersData.size();
        metersData.clear();
        driver.navigate().back();

        driver.findElement(By.cssSelector(cssDt.get("electro")))
                .sendKeys(dataForSend.get(0));
        driver.findElement(By.cssSelector(cssDt.get("cold")))
                .sendKeys(dataForSend.get(1));
        driver.findElement(By.cssSelector(cssDt.get("hot")))
                .sendKeys(dataForSend.get(2));

        driver.findElement(By.cssSelector(cssDt.get("send-btn"))).click();
        driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).click();
        Thread.sleep(1000);

        metersData = driver.findElement(By.cssSelector(cssDt.get("hist")))
                .findElements(By.cssSelector(cssDt.get("removable")));
        int sizeAfter = metersData.size();

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

        Assert.assertTrue("Тест не прошёл, запись о показаниях не была добавлена", sizeAfter - sizeBefore == 1);
    }

    @Step("Проверка валидации формы отправки показаний")
    public void inputMetersValidateTest(Map <String, String> cssDt) throws InterruptedException {
        userSignIn(users.get(0), cssDt);

        driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).click();
        Thread.sleep(1000);

        boolean isDataExist = false;

        List<WebElement> sendData = driver.findElement(By.cssSelector(cssDt.get("hist")))
                .findElements(By.cssSelector(cssDt.get("removable")));

        String correctElectro = "";
        String correctCold = "";
        String correctHot = "";

        String wrongElectro = "-1";
        String wrongCold = "-1";
        String wrongHot = "-1";

        if (sendData.size() > 0) {
            String lastInput = sendData.get(sendData.size() - 1).getText();
            String[] separatedData = sendData.get(sendData.size() - 1).getText().split("\\s");

            int intElectro = Integer.parseInt(separatedData[0]);
            int intCold = Integer.parseInt(separatedData[1]);
            int intHot = Integer.parseInt(separatedData[2]);

            correctElectro = Integer.toString(intElectro);
            correctCold = Integer.toString(intCold);
            correctHot = Integer.toString(intHot);

            wrongElectro = Integer.toString(intElectro - 1);
            wrongCold = Integer.toString(intCold - 1);
            wrongHot = Integer.toString(intHot - 1);

            isDataExist = true;

            Thread.sleep(1000);
        }
        else {
            correctElectro = "1";
            correctCold = "1";
            correctHot = "1";
        }

        driver.findElement(By.cssSelector(cssDt.get("send-switcher"))).click();

        // cases of wrong inputs
        ArrayList<String[]> wrongInputs = new ArrayList<String[]>();

        //0: try to set letters in electricity
        wrongInputs.add(("ltc" + " " + correctCold + " " + correctHot).split("\\s"));

        //1: electricity contains "+" not as first
        wrongInputs.add(("++" + correctElectro + " " + correctCold + " " + correctHot).split("\\s"));

        //2: electricity contains "-"
        wrongInputs.add(("-" + correctElectro + " " + correctCold + " " + correctHot).split("\\s"));

        //3: try to set letters in cold water
        wrongInputs.add((correctElectro + " " + "cold" + " " + correctHot).split("\\s"));

        //4: cold water contains "+" not as first
        wrongInputs.add((correctElectro + " " + "++" + correctCold + " " + correctHot).split("\\s"));

        //5: cold water contains "-"
        wrongInputs.add((correctElectro + " " + "-" + correctCold + " " + correctHot).split("\\s"));

        //6: try to set letters in hot water
        wrongInputs.add((correctElectro + " " + correctCold + " " + "hot").split("\\s"));

        //7: hot water contains "+" not as first
        wrongInputs.add((correctElectro + " " + correctCold + " " + "++" + correctHot).split("\\s"));

        //8: hot water contains "-"
        wrongInputs.add((correctElectro + " " + correctCold + " " + "-" + correctHot).split("\\s"));

        //9: try to set electricity lower then before
        wrongInputs.add((wrongElectro + " " + correctCold + " " + correctHot).split("\\s"));

        //10: try to set cold water lower then before
        wrongInputs.add((correctElectro + " " + wrongCold + " " + correctHot).split("\\s"));

        //11: try to set hot water lower then before
        wrongInputs.add((correctElectro + " " + correctCold + " " + wrongHot).split("\\s"));

        //12: validation on front didn't pass and back validation didn't start
        wrongInputs.add((wrongElectro + " " + wrongHot + " " + "text").split("\\s"));

        String wrongColorLightPink = "rgba(255, 182, 193, 1)";
        String wrongColorMagenta = "rgba(255, 0, 255, 1)";
        String rightColorLightGreen = "rgba(144, 238, 144, 1)";

        for (int i = 0; i < wrongInputs.size(); i++) {
            driver.findElement(By.cssSelector(cssDt.get("electro")))
                    .sendKeys(wrongInputs.get(i)[0]);
            driver.findElement(By.cssSelector(cssDt.get("cold")))
                    .sendKeys(wrongInputs.get(i)[1]);
            driver.findElement(By.cssSelector(cssDt.get("hot")))
                    .sendKeys(wrongInputs.get(i)[2]);

            driver.findElement(By.cssSelector(cssDt.get("send-btn"))).click();

            switch (i) {
                case (0):
                    //0: try to set letters in electricity
                    Assert.assertTrue("Тест не прошёл, валидация букв в поле 'электричество' не проходит",
                            driver.findElement(By.cssSelector(cssDt.get("electro")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (1):
                    //1: electricity contains "+" not as first
                    Assert.assertTrue("Тест не прошёл, не проходит проверку символа '+' в поле 'электричество'",
                            driver.findElement(By.cssSelector(cssDt.get("electro")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (2):
                    //2: electricity contains "-"
                    Assert.assertTrue("Тест не прошёл, не проходит проверку символа '-' в поле 'электричество'",
                            driver.findElement(By.cssSelector(cssDt.get("electro")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (3):
                    //3: try to set letters in cold water
                    Assert.assertTrue("Тест не прошёл, валидация букв в поле 'холодная вода' не проходит",
                            driver.findElement(By.cssSelector(cssDt.get("cold")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (4):
                    //4: cold water contains "+" not as first
                    Assert.assertTrue("Тест не прошёл, не проходит проверку символа '+' в поле 'холодная вода'",
                            driver.findElement(By.cssSelector(cssDt.get("cold")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (5):
                    //5: cold water contains "-"
                    Assert.assertTrue("Тест не прошёл, не проходит проверку символа '-' в поле 'холодная вода'",
                            driver.findElement(By.cssSelector(cssDt.get("cold")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (6):
                    //6: try to set letters in hot water
                    Assert.assertTrue("Тест не прошёл, валидация букв в поле 'горячая вода' не проходит",
                            driver.findElement(By.cssSelector(cssDt.get("hot")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (7):
                    //7: hot water contains "+" not as first
                    Assert.assertTrue("Тест не прошёл, не проходит проверку символа '+' в поле 'горячая вода'",
                            driver.findElement(By.cssSelector(cssDt.get("hot")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (8):
                    //8: hot water contains "-"
                    Assert.assertTrue("Тест не прошёл, не проходит проверку символа '-' в поле 'горячая вода'",
                            driver.findElement(By.cssSelector(cssDt.get("hot")))
                            .getCssValue("background-color").equals(wrongColorLightPink));
                    Thread.sleep(1000);
                    break;

                case (9):
                    Thread.sleep(1000);
                    //9: try to set electricity lower then before
                    if (isDataExist) {
                        Assert.assertTrue("Тест не прошёл, ограничение на воод показаний не менее чем предыдущее не проходит в поле 'электричество'",
                                driver.findElement(By.cssSelector(cssDt.get("electro")))
                                .getCssValue("background-color").equals(wrongColorMagenta));
                        Thread.sleep(1000);
                    }
                    break;

                case (10):
                    Thread.sleep(1000);
                    //10: try to set cold water lower then before
                    if (isDataExist) {
                        Assert.assertTrue("Тест не прошёл, ограничение на воод показаний не менее чем предыдущее не проходит в поле 'холодная вода'",
                                driver.findElement(By.cssSelector(cssDt.get("cold")))
                                .getCssValue("background-color").equals(wrongColorMagenta));
                        Thread.sleep(1000);
                    }
                    break;

                case (11):
                    Thread.sleep(1000);
                    //11: try to set hot water lower then before
                    if (isDataExist) {
                        Assert.assertTrue("Тест не прошёл, ограничение на воод показаний не менее чем предыдущее не проходит в поле 'горячая вода'",
                                driver.findElement(By.cssSelector(cssDt.get("hot")))
                                .getCssValue("background-color").equals(wrongColorMagenta));
                        Thread.sleep(1000);
                    }
                    break;

                case (12):
                    Thread.sleep(1000);
                    //12: validation on front didn't pass and back validation didn't start
                    if (isDataExist) {
                        Assert.assertTrue("Тест не прошёл, данные с сервера поступают после ошибки на форме",
                                driver.findElement(By.cssSelector(cssDt.get("electro")))
                                .getCssValue("background-color").equals(rightColorLightGreen)
                                && driver.findElement(By.cssSelector(cssDt.get("cold")))
                                .getCssValue("background-color").equals(rightColorLightGreen)
                                && driver.findElement(By.cssSelector(cssDt.get("hot")))
                                .getCssValue("background-color").equals(wrongColorLightPink));
                        Thread.sleep(1000);
                    }
                    break;
            }

            driver.findElement(By.cssSelector(cssDt.get("electro")))
                    .clear();
            driver.findElement(By.cssSelector(cssDt.get("cold")))
                    .clear();
            driver.findElement(By.cssSelector(cssDt.get("hot")))
                    .clear();
        }

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
    }

    @Step("Проверка перехода по старницам")
    public void pageSwapTest(Map <String, String> cssDt) throws InterruptedException {
        Assert.assertTrue("Тест не прошёл, форма входа не выводится", driver.findElement(By.cssSelector(cssDt.get("sign-in-form"))).isDisplayed());
        driver.findElement(By.cssSelector(cssDt.get("sign-up-switch"))).click();

        Assert.assertTrue("Тест не прошёл, форма регистрации не выводится", driver.findElement(By.cssSelector(cssDt.get("sign-up-form"))).isDisplayed());
        driver.findElement(By.cssSelector(cssDt.get("sign-in-switch"))).click();

        userSignIn(users.get(0), cssDt);
        Thread.sleep(1000);

        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        Assert.assertTrue("Тест не прошёл, не открывается профиль пользователя", currentURL.equals("http://openjdk-app-crm-db.7e14.starter-us-west-2.openshiftapps.com/user_index.html"));

        Assert.assertTrue(driver.findElement(By.cssSelector(cssDt.get("send"))).isDisplayed());

        driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).click();
        Assert.assertTrue("Тест не прошёл, не открывается история показаний пользователя", driver.findElement(By.cssSelector(cssDt.get("hist"))).isDisplayed());

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
        Thread.sleep(1000);
        currentURL = driver.getCurrentUrl();
        Assert.assertTrue("Тест не прошёл, выход из профиля не работает", currentURL.equals("http://openjdk-app-crm-db.7e14.starter-us-west-2.openshiftapps.com/"));
    }

    @Step("Проверка входа в профиль админа")
    public void adminPageTest(Map <String, String> cssDt) {
        userSignIn(users.get(1), cssDt);

        String debtSwitcher = driver.findElement(By.cssSelector(cssDt.get("debt-switcher"))).getText();

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

        Assert.assertTrue("Тест не прошёл, вход в профиль админа не выполнен", debtSwitcher.equals("История показаний"));
    }

    @Step("Проверка обновлений данных в профиле админа")
    public void adminUpdateDataTest (Map <String, String> cssDt) throws InterruptedException {
        String electro;
        String hot;
        String cold;

//        driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
//                .findElement(By.cssSelector(cssDt.get("username")))
//                .sendKeys(userName);
//        driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
//                .findElement(By.cssSelector(cssDt.get("password")))
//                .sendKeys(userPassword);
//
//        driver.findElement(By.cssSelector(cssDt.get("sign-in-btn"))).click();

        userSignIn(users.get(0), cssDt);

        driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).click();
        Thread.sleep(1000);

        ArrayList<String> dataForSend = genereteInputData(cssDt);

        electro = dataForSend.get(0);
        cold = dataForSend.get(1);
        hot = dataForSend.get(2);

        driver.findElement(By.cssSelector(cssDt.get("send-switcher"))).click();

        driver.findElement(By.cssSelector(cssDt.get("electro")))
                .sendKeys(electro);
        driver.findElement(By.cssSelector(cssDt.get("cold")))
                .sendKeys(cold);
        driver.findElement(By.cssSelector(cssDt.get("hot")))
                .sendKeys(hot);

        driver.findElement(By.cssSelector(cssDt.get("send-btn"))).click();

        TimeZone tzGMT = TimeZone.getTimeZone("GMT");
        Date sendTime = new Date();

        SimpleDateFormat sdfSendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdfSendTime.setLenient(false);
        sdfSendTime.setTimeZone(tzGMT);
        String strSendTime = sdfSendTime.format(sendTime);

        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

//        driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
//                .findElement(By.cssSelector(cssDt.get("username")))
//                .sendKeys("admin");
//        driver.findElement(By.cssSelector(cssDt.get("sign-in-form")))
//                .findElement(By.cssSelector(cssDt.get("password")))
//                .sendKeys("zxcvbn");
//
//        driver.findElement(By.cssSelector(cssDt.get("sign-in-btn"))).click();

        userSignIn(users.get(1), cssDt);
        Thread.sleep(3000);

        List<WebElement> allTableData = driver.findElement(By.cssSelector(cssDt.get("apart")))
                .findElements(By.cssSelector(cssDt.get("removable")));

        ArrayList<String> dataToString = new ArrayList<String>();

        for (WebElement tr: allTableData) {
            dataToString.add(tr.getText());
        }

        if (dataToString.contains(users.get(0).apartmentNumber)) {
//            int flatNumberInd = dataToString.indexOf(users.get(0).apartmentNumber);
//
//            System.out.println(flatNumberInd);
//
//            String tableWithSameTime = "";
//            int requiredInd = -1;
//
//            ArrayList<String> apartData = new ArrayList<String>();
//            int count = flatNumberInd + 1;
//            while (dataToString.get(count).indexOf(" ") > 0) {
//                apartData.add(dataToString.get(count));
//                String[] getData = dataToString.get(count).split("\\s");
//                String time = getData[3] + " " + getData[4];
//                System.out.println(count);
//                if (time.equals(strSendTime)) {
//                    requiredInd = count;
//                    break;
//                }
//                count++;
//            }

            int requiredInd = -1;
            for (int i = 0; i < dataToString.size(); i++) {
                String[] getData = dataToString.get(i).split("\\s");
                if (getData.length == 5) {
                    String time = getData[3] + " " + getData[4].substring(0, 5);
                    if (time.equals(strSendTime)) {
                        requiredInd = i;
                        break;
                    }
                }
            }

//            String[] getData = apartData.get(apartData.size() - 1).split("\\s");
//            String foundData = getData[0] + " " + getData[1] + " " + getData[2];
//            String inputedData = electro + " " + cold + " " + hot;
//
//            System.out.println(foundData + "\n" + inputedData);
//            System.out.println(getData[3] + " : " + getData[4]);

//            System.out.println(dataToString.size() + " " + requiredInd);
//            System.out.println(dataToString);

            if (requiredInd >= 0) {
                String[] foundedRow = dataToString.get(requiredInd).split("\\s");

                String foundData = foundedRow[0] + " " + foundedRow[1] + " " + foundedRow[2];
                String inputedData = electro + " " + cold + " " + hot;

                driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

                Assert.assertTrue("Тест не прошёл, отправленные показания не совпадает с показаниями в отчёте",
                        inputedData.equals(foundData));
            }
            else {
                driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
                Assert.assertTrue("Тест не прошёл, время отправки показаний не совпадает с временем в отчёте",
                        false);
            }
        }
        else {
            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
            Assert.assertTrue("Тест не прошёл, квартира не была найдена", false);
        }
    }

    @Step("Проверка таблицы с должниками")
    public void adminDebtorExistTest(Map<String, String> cssDt) throws InterruptedException {
        userSignIn(users.get(1), cssDt);
        Thread.sleep(3000);

        List<WebElement> allTableData = driver.findElement(By.cssSelector(cssDt.get("apart")))
                .findElements(By.cssSelector(cssDt.get("removable")));

        ArrayList<String> dataToString = new ArrayList<String>();

        for (WebElement tr: allTableData) {
            dataToString.add(tr.getText());
        }

        if (dataToString.contains("<нет показаний>")) {
            String deptorApartNumber = dataToString.get(dataToString.indexOf("<нет показаний>") - 1);

            driver.findElement(By.cssSelector(cssDt.get("debt-switcher"))).click();
            Thread.sleep(3000);

            List<WebElement> allDeptorData = driver.findElement(By.cssSelector(cssDt.get("debt")))
                    .findElements(By.cssSelector(cssDt.get("removable")));

            ArrayList<String> deptorsToString = new ArrayList<String>();
            for (WebElement tr: allDeptorData) {
                deptorsToString.add(tr.getText());
            }

            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

            Assert.assertTrue("Тест не прошёл, нужной квартиры нет в списке должников", dataToString.contains(deptorApartNumber));
        } else {
            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
            Assert.assertTrue("Проверка не может быть выполнена, т.к нет должников", false);
        }
    }

    @Step("Проверка погашения задолженности")
    public void debtorEraseTest(Map<String, String> cssDt) throws InterruptedException {
        String newApartment = "170";

        driver.findElement(By.cssSelector(cssDt.get("sign-up-switch"))).click();

        users.add(new UserEntity("userTest4", "1111111", "user22@mail.ru",
                "Иван", "Иванов", "Иванович",
                newApartment, "88001111111"));

        driver.findElement(By.cssSelector(cssDt.get("username")))
                .sendKeys(users.get(users.size() - 1).login);
        driver.findElement(By.cssSelector(cssDt.get("password")))
                .sendKeys(users.get(users.size() - 1).password);
        driver.findElement(By.cssSelector(cssDt.get("email")))
                .sendKeys(users.get(users.size() - 1).post);
        driver.findElement(By.cssSelector(cssDt.get("lastName")))
                .sendKeys(users.get(users.size() - 1).lastName);
        driver.findElement(By.cssSelector(cssDt.get("firstName")))
                .sendKeys(users.get(users.size() - 1).firstName);
        driver.findElement(By.cssSelector(cssDt.get("middleName")))
                .sendKeys(users.get(users.size() - 1).middleName);
        driver.findElement(By.cssSelector(cssDt.get("apartment")))
                .sendKeys(users.get(users.size() - 1).apartmentNumber);
        driver.findElement(By.cssSelector(cssDt.get("phoneNumber")))
                .sendKeys(users.get(users.size() - 1).phoneNumber);

        driver.findElement(By.cssSelector(cssDt.get("form"))).findElement(By.cssSelector(cssDt.get("sign-up-btn"))).click();
        driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

        userSignIn(users.get(1), cssDt);
        Thread.sleep(3000);

        driver.findElement(By.cssSelector(cssDt.get("debt-switcher"))).click();
        Thread.sleep(3000);

        List<WebElement> allTableData = driver.findElement(By.cssSelector(cssDt.get("debt")))
                .findElements(By.cssSelector(cssDt.get("removable")));

        ArrayList<String> dataToString = new ArrayList<String>();

        for (WebElement tr: allTableData) {
            dataToString.add(tr.getText());
        }

        if (dataToString.contains(newApartment)) {
            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

            userSignIn(users.get(users.size() - 1), cssDt);

            driver.findElement(By.cssSelector(cssDt.get("hist-switcher"))).click();
            Thread.sleep(1000);

            ArrayList<String> dataForSend = genereteInputData(cssDt);
            driver.findElement(By.cssSelector(cssDt.get("send-switcher"))).click();

            String electro = dataForSend.get(0);
            String cold = dataForSend.get(1);
            String hot = dataForSend.get(2);

            driver.findElement(By.cssSelector(cssDt.get("electro")))
                    .sendKeys(electro);
            driver.findElement(By.cssSelector(cssDt.get("cold")))
                    .sendKeys(cold);
            driver.findElement(By.cssSelector(cssDt.get("hot")))
                    .sendKeys(hot);

            driver.findElement(By.cssSelector(cssDt.get("send-btn"))).click();
            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();

            userSignIn(users.get(1), cssDt);
            driver.findElement(By.cssSelector(cssDt.get("debt-switcher"))).click();
            Thread.sleep(3000);

            List<WebElement> allDebtData = driver.findElement(By.cssSelector(cssDt.get("debt")))
                    .findElements(By.cssSelector(cssDt.get("removable")));

            ArrayList<String> debtorToString = new ArrayList<String>();

            for (WebElement tr: allDebtData) {
                debtorToString.add(tr.getText());
            }

            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
            Assert.assertTrue("Тест не прошёл, должник остался после погашения задолженности", !debtorToString.contains(newApartment));
        }
        else {
            driver.findElement(By.cssSelector(cssDt.get("exit-btn"))).click();
            Assert.assertTrue("Тест не прошёл, должника нет в списках", false);
        }
    }
}
