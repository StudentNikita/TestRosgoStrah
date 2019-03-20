package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**@author Nikita Avdonin
 * @see #main(String[]) главный метод
 * @see #clicByXpath(String) происходит нажатие объекта, на вход подается его xPath
 * @see #clicByName(String)  происходит нажатие объекта, на вход подается его Name
 * @see #compareText(String, String) проверка на совпадение ожидаемого и реального заголовка объекта
 * @see #getTextByXpath(String) возвращает заголовок обхекта объекта, на вход подается его xPath
 * @see #fillForm(By, String) заполнение объекта текстом, который подается на вход
 * @see #todayPlusTwoWeeks() возвращает сегодняшнюю дату + 14 дней
 * @see #scrollToObjectByXPath(String) скролим до нужного нам объекта
 */

public class Main {

    private static WebDriver driver;

    public static void main(String[] args) {
        try {


            System.out.println("Тест по селениум!");
            System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
            driver = new ChromeDriver();


            String url = "https://www.rgs.ru/";
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();

            driver.get(url);
            clicByXpath("//ol/li/a[contains(text(), 'Страхование')]");
            clicByXpath("//*[contains(text(), 'Выезжающим за рубеж')]");
            scrollToObjectByXPath("//a[contains(text(), 'Рассчитать')]");
            clicByXpath("//a[contains(text(), 'Рассчитать')]");

            Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class, 'page')]/span[@class='h1']")))); // ждем, когда появится нужный нам объект

            compareText(getTextByXpath("//div[contains(@class, 'page')]/span[@class='h1']"), "Страхование выезжающих за рубеж");
            scrollToObjectByXPath("//button/*[contains(@class, 'content-title')]");

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button/*[contains(@class, 'content-title')]")))); // ждем, когда появится нужный нам объект


            clicByXpath("//button/*[contains(@class, 'content-title')]");
            fillForm(By.xpath("//*[contains(@class, 'actual-input')]"), "шенген");
            clicByXpath("//*[contains(@class, 'tt-menu tt')]");
            clicByName("ArrivalCountryList");
            clicByXpath("//*[contains(text(), 'Испания')]");
            clicByName("ArrivalCountryList");
            scrollToObjectByXPath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");
            clicByXpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'control-label')]");
            fillForm(By.xpath("//*[contains(@class, 'clear')]/*[contains(@class, 'form-group margin')]/*[contains(@class, 'form-control')]"), todayPlusTwoWeeks());
            clicByXpath("//*[contains(text(), '90 дней')]");
            fillForm(By.xpath("//div[@data-fi-input-mode=\"combined\"]//div[@class=\"form-group\"]//input[@class=\"form-control\" != @disabled]"), "LERCHIK" + " PERCHIK");
            fillForm(By.xpath("//input[@data-test-name='BirthDate']"), "06." + "08." + "1994");
            scrollToObjectByXPath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class='calc-vzr-toggle-risk-group']//div[@class='toggle off toggle-rgs']");
            clicByXpath("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class='calc-vzr-toggle-risk-group']//div[@class='toggle off toggle-rgs']");
            scrollToObjectByXPath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']");


            if (!driver.findElement(By.xpath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']")).isSelected())  // проверка на "галочку" у чекбокса, если галочка не стоит, то ставим
            {
                clicByXpath("//input[@data-test-name='IsProcessingPersonalDataToCalculate']");

            }

            clicByXpath("//div[contains(@class, 'valid')]/button[contains(text(), 'Рассчитать')]");

            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains (text(),'Комфорт')]"))));
                scrollToObjectByXPath("//h1[contains (text(),'Кальку')]");


            compareText(driver.findElement(By.xpath("//div/span[@data-bind]/span[contains(@class, 'text-bold')]")).getText(), "Многократные поездки в течение года");
            compareText(driver.findElement(By.xpath("//span/span/strong[@data-bind='text: Name']")).getText(), "Шенген");
            compareText(driver.findElement(By.xpath("//strong[@data-bind=\"text: LastName() + ' ' + FirstName()\"]")).getText(), "LERCHIK" + " PERCHIK");
            compareText(driver.findElement(By.xpath("//strong[@data-bind=\" text: BirthDay.repr('moscowRussianDate')\"]")).getText(), "06." + "08." + "1994");

        } catch (Exception e)
        {
            driver.quit();
            System.out.println("Тест работает с ошибкой");
        }




        /**
         * методы к тесту
         */

    }
    public static void compareText (String actual, String expect){
        if(actual.contains(expect)){
            System.out.println("Искомый текст ЕСТЬ: " + expect);
        }
        else {
            System.err.println("Искомого текста НЕТ: " + expect + " Вместо него: " + actual);
            driver.quit();
        }

    }

    public static void fillForm(By locator, String value){
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(locator).sendKeys(value);


    }
    public static String todayPlusTwoWeeks() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Calendar instance = Calendar.getInstance();
    instance.setTime(new Date());
    instance.add(Calendar.DAY_OF_MONTH, 14);
    String newDate =dateFormat.format(instance.getTime());
    return newDate;
    }

    public static void clicByXpath (String xPath){
        driver.findElement(By.xpath(xPath)).click();

    }

    public static void clicByName (String name){
        driver.findElement(By.name(name)).click();

    }

    public static String getTextByXpath (String xPath){
        return driver.findElement(By.xpath(xPath)).getText();

    }

    public static void scrollToObjectByXPath(String xPath){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(xPath)));
    }




}
