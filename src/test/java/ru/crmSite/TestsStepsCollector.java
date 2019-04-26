package ru.crmSite;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestsStepsCollector extends CrmTest {
    @DataProvider(name="selectors")
    public static Object[][] cssData() {
        Map<String, String> cssDt = new HashMap<String, String>();

        cssDt.put("sign-up-switch", ".sign-up-switch");
        cssDt.put("sign-in-switch", ".sign-in-switch");
        cssDt.put("username", ".username");
        cssDt.put("password", ".password");
        cssDt.put("email", ".email");
        cssDt.put("lastName", ".lastName");
        cssDt.put("firstName", ".firstName");
        cssDt.put("middleName", ".middleName");
        cssDt.put("apartment", ".apartment");
        cssDt.put("phoneNumber", ".phoneNumber");
        cssDt.put("hist-switcher", ".hist-switcher");
        cssDt.put("exit-btn", ".exit-btn");
        cssDt.put("sign-up-btn", ".sign-up-btn");
        cssDt.put("sign-in-btn", ".sign-in-btn");
        cssDt.put("hist", ".hist");
        cssDt.put("removable", ".removable");
        cssDt.put("electro", ".electro");
        cssDt.put("cold", ".cold");
        cssDt.put("hot", ".hot");
        cssDt.put("sign-up-form", ".sign-up-form");
        cssDt.put("sign-in-form", ".sign-in-form");
        cssDt.put("send-btn", ".send-btn");
        cssDt.put("send", ".send");
        cssDt.put("debt-switcher", ".debt-switcher");
        cssDt.put("table", ".table");
        cssDt.put("apart", ".apart");
        cssDt.put("debt", ".debt");
        cssDt.put("title", ".title");
        cssDt.put("form", ".form");
        cssDt.put("send-switcher", ".send-switcher");
        cssDt.put("select-td", ".select-td");
        cssDt.put("from-date", ".from-date");
        cssDt.put("to-date", ".to-date");
        cssDt.put("date-container", ".date-container");

        return new Object[][] {{cssDt}};
    }

    @Test(dataProvider = "selectors", priority = 1)
    @Description(value = "Тест проверяет регистрацию на сайте")
    public void test1(Map<String, String> cssDt) {
        registrationTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 2)
    @Description(value = "Тест проверяет валидацию формы регистрации")
    public void test2(Map<String, String> cssDt) throws InterruptedException {
        registerValidationTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 3)
    @Description(value = "Тест проверяет вход в профиль")
    public void test3(Map<String, String> cssDt) {
        signUpTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 4)
    @Description(value = "Тест проверяет валидацию формы входа")
    public void test4(Map<String, String> cssDt) throws InterruptedException {
        signUpValidationTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 5)
    @Description(value = "Тест проверяет отправку данных по показаниям")
    public void test5(Map<String, String> cssDt) throws InterruptedException {
        inputMetersDataTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 6)
    @Description(value = "Тест проверяет валидацию формы отправки данных")
    public void test6(Map<String, String> cssDt) throws InterruptedException {
        inputMetersValidateTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 7)
    @Description(value = "Тест проверяет переход по страницам")
    public void test7(Map<String, String> cssDt) throws InterruptedException {
        pageSwapTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 8)
    @Description(value = "Тест проверяет вход в профиль админа")
    public void test8(Map<String, String> cssDt) {
        adminPageTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 9)
    @Description(value = "Тест проверяет обновление данных о пользователях в форме админа")
    public void test9(Map<String, String> cssDt) throws InterruptedException {
        adminUpdateDataTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 10)
    @Description(value = "Тест проверяет существование списка должников")
    public void test10(Map<String, String> cssDt) throws InterruptedException {
        adminDebtorExistTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 11)
    @Description(value = "Тест проверяет погашение задолженности")
    public void test11(Map<String, String> cssDt) throws InterruptedException {
        debtorEraseTest(cssDt);
    }

    @Test(dataProvider = "selectors", priority = 12)
    @Description(value = "Валидация диапазона дат")
    public void test12(Map<String, String> cssDt) throws InterruptedException {
        dateAdminFieldValidateTest(cssDt);
    }
}
