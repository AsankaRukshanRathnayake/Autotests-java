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

public class RegistrationAndAuthorizationFormsTests {
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

    private String name = "Inna" + (int) (Math.random() * 10000);
    private String email = "test" + (int) (Math.random() * 10000) + "@sk.ru";
    private String password = "test";
    private By regInputNameLocator = By.id("reg_username");
    private By regInputEmailLocator = By.id("reg_email");
    private By regInputPasswordLocator = By.id("reg_password");
    private By regButtonLocator = By.name("register");
    private By alertLocator = By.cssSelector("[role='alert']");
    private String userName = "Inna";
    private String userEmail = "test@sk.ru";
    private By inputNameLocator = By.id("username");
    private By inputPasswordLocator = By.id("password");
    private By logInButtonLocator = By.cssSelector("button[name='login']");
    private By nameInGreetingLocator = By.className("user-name");

    // Форма регистрации
    @Test
    public void registrationWithValidData_ConfirmationOfRegistration() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        var logInButtonLocator = driver.findElement(By.className("login-woocommerce"));
        logInButtonLocator.click();
        var regLinkLocator = driver.findElement(By.className("custom-register-button"));
        regLinkLocator.click();

        //act
        driver.findElement(regInputNameLocator).sendKeys(name);
        driver.findElement(regInputEmailLocator).sendKeys(email);
        driver.findElement(regInputPasswordLocator).sendKeys(password);
        driver.findElement(regButtonLocator).click();

        //assert
        var expectedConfirmRegistrationNote = "Регистрация завершена";
        var actualConfirmRegistrationNote = driver.findElement(By.cssSelector(".content-page div"));
        Assert.assertTrue("Нет сообщения о подтверждении регистрации", actualConfirmRegistrationNote.isDisplayed());
        Assert.assertEquals("Неверный текст сообщения подтверждения регистрации",
                expectedConfirmRegistrationNote, actualConfirmRegistrationNote.getText());
    }

    @Test
    public void registrationWithoutName_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/register/");

        //act
        driver.findElement(regInputEmailLocator).sendKeys(email);
        driver.findElement(regInputPasswordLocator).sendKeys(password);
        driver.findElement(regButtonLocator).click();

        //assert
        var expectedAlertText = "Error: Пожалуйста введите корректное имя пользователя.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления", expectedAlertText, actualAlertText.getText());
    }

    @Test
    public void registrationWithoutEmail_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/register/");

        //act
        driver.findElement(regInputNameLocator).sendKeys(name);
        driver.findElement(regInputPasswordLocator).sendKeys(password);
        driver.findElement(regButtonLocator).click();

        //assert
        var expectedAlertText = "Error: Пожалуйста, введите корректный email.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления", expectedAlertText, actualAlertText.getText());
    }

    @Test
    public void registrationWithoutPassword_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/register/");

        //act
        driver.findElement(regInputNameLocator).sendKeys(name);
        driver.findElement(regInputEmailLocator).sendKeys(email);
        driver.findElement(regButtonLocator).click();

        //assert
        var expectedAlertText = "Error: Введите пароль для регистрации.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления", expectedAlertText, actualAlertText.getText());
    }

    @Test
    public void registrationWithInvalidEmail_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/register/");

        //act
        driver.findElement(regInputNameLocator).sendKeys(name);
        driver.findElement(regInputEmailLocator).sendKeys("test@skru");
        driver.findElement(regInputPasswordLocator).sendKeys(password);
        driver.findElement(regButtonLocator).click();

        //assert
        var expectedAlertText = "Error: Пожалуйста, введите корректный email.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления", expectedAlertText, actualAlertText.getText());
    }

    // Форма авторизации
    @Test
    public void authorizationWithName_UserNameInGreeting() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");
        driver.manage().window().maximize();

        //act
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //assert
        Assert.assertEquals("Неверное имя в приветствии",
                userName, driver.findElement(nameInGreetingLocator).getText());
    }

    @Test
    public void authorizationWithEmail_UserNameInGreeting() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");
        driver.manage().window().maximize();

        //act
        driver.findElement(inputNameLocator).sendKeys(userEmail);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //assert
        Assert.assertEquals("Неверное имя в приветствии",
                userName, driver.findElement(nameInGreetingLocator).getText());
    }


    @Test
    public void emptyInputToAuthorizationForm_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");

        //act
        driver.findElement(logInButtonLocator).click();

        //assert
        var expectedAlertText = "Error: Имя пользователя обязательно.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст в уведомлении об ошибке", expectedAlertText, actualAlertText.getText());
    }

    @Test
    public void authorizationWithoutName_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");

        //act
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //assert
        var expectedAlertText = "Error: Имя пользователя обязательно.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст в уведомлении об ошибке", expectedAlertText, actualAlertText.getText());
    }

    @Test
    public void authorizationWithoutPassword_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");

        //act
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(logInButtonLocator).click();

        //assert
        var expectedAlertText = "Пароль обязателен.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст в уведомлении об ошибке", expectedAlertText, actualAlertText.getText());
    }

    @Test
    public void authorizationWithMisprintedName_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");

        //act
        driver.findElement(inputNameLocator).sendKeys("Ina");
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //assert
        var expectedAlertText = "Неверный логин или пароль.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст в уведомлении об ошибке", expectedAlertText, actualAlertText.getText());
    }


    @Test
    public void authorizationWithMisprintedPassword_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/my-account/");

        //act
        driver.findElement(inputNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys("tes@sk.ru");
        driver.findElement(logInButtonLocator).click();

        //assert
        var expectedAlertText = "Неверный логин или пароль.";
        var actualAlertText = driver.findElement(alertLocator);
        Assert.assertTrue("Нет уведомления об ошибке", actualAlertText.isDisplayed());
        Assert.assertEquals("Неверный текст в уведомлении об ошибке", expectedAlertText, actualAlertText.getText());
    }
}
