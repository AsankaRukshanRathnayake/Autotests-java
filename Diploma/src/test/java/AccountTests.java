import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AccountTests {
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private String userName = "Inna";
    private String password = "test";
    private By inputNameLocator = By.id("username");
    private By inputPasswordLocator = By.id("password");
    private By logInButtonLocator = By.cssSelector("button[name='login']");
    private By ordersLinkLocator = By.xpath("//a[.='Заказы']");
    private By moreDetailsButtonLink = By.cssSelector(".order:nth-of-type(1) .button.view");

    @Test
    public void openAccountData_DataIsCorrect(){
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        var dataLinkLocator = driver.findElement(By.xpath("//a[.='Данные аккаунта']"));
        dataLinkLocator.click();

        //assert
        var expectedName = "Инна";
        var actualName = driver.findElement(By.id("account_first_name")).getAttribute("value");

        var expectedLastName = "Иванова";
        var actualLastName = driver.findElement(By.id("account_last_name")).getAttribute("value");

        var expectedUserEmail = "test@sk.ru";
        var actualUserEmail = driver.findElement(By.id("account_email")).getAttribute("value");

        var actualUserName = driver.findElement(By.id("account_display_name")).getAttribute("value");

        Assert.assertEquals("Неверное имя",expectedName,actualName);
        Assert.assertEquals("Неверная фамилия",expectedLastName,actualLastName);
        Assert.assertEquals("Неверное имя пользователя", userName,actualUserName);
        Assert.assertEquals("Нверный email",expectedUserEmail,actualUserEmail);
    }

    @Test
    public void openOrderInformation_OrderInformationOpens(){
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        driver.findElement(ordersLinkLocator).click();
        driver.findElement(moreDetailsButtonLink).click();

        //assert
        var expectedTitle = "Order #3811";
        var actualTitle = driver.findElement(By.className("post-title"));

        var expectedOrderDetailsCaption = "Детали заказа";
        var actualOrderDetailsCaption = driver.findElement
                (By.xpath("//section[contains(@class,'order-details')]/h2")).getText();

        var expectedAddressCaption = "Адрес для отправки чека";
        var actualAddressCaption =driver.findElement
                (By.xpath("//section[contains(@class,'customer-details')]/h2")).getText();

        Assert.assertTrue("Страница с информацией о заказе не открылась", actualTitle.isDisplayed());
        Assert.assertEquals("Неверный текст заголовка страницы",
                expectedTitle, actualTitle.getText());
        Assert.assertEquals("Неверный текст заголовка таблицы с информацией о заказе",
                expectedOrderDetailsCaption, actualOrderDetailsCaption);
        Assert.assertEquals("Неверный текст заголовка таблицы с адресом",
                expectedAddressCaption, actualAddressCaption);
    }

    @Test
    public void openOrderInformation_AddressMatches(){
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        driver.findElement(ordersLinkLocator).click();
        driver.findElement(moreDetailsButtonLink).click();

        //assert
        var expectedAddress = "Инна Иванова\n" +
                "ул.Фруктовая, д.5, корп.3, кв.99\n" +
                "Москва\n" +
                "Московская\n" +
                "123456\n" +
                "89991235577\n" +
                "test@sk.ru";
        var actualAddress = driver.findElement(By.tagName("address")).getText();
        Assert.assertEquals("Неверный адрес для отправки чека",expectedAddress,actualAddress);
    }

    @Test
    public void logOut_AuthorizationFormOpens(){
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        var logOutLinkLocator = driver.findElement(By.xpath("//a[.='Выйти']"));
        logOutLinkLocator.click();

        //assert
        Assert.assertTrue("Нет поля для ввода логина", driver.findElement(inputNameLocator).isDisplayed());
        Assert.assertTrue("Нет поля для ввода пароля", driver.findElement(inputPasswordLocator).isDisplayed());
        Assert.assertTrue("Нет кнопки входа в аккаунт", driver.findElement(logInButtonLocator).isDisplayed());
    }
}
