import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CheckoutTests {
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

    private By addToCartButtonLocator = By.className("add_to_cart_button");
    private By cartLinkLocator = By.className("added_to_cart");
    private By checkoutButtonLocator = By.className("checkout-button");
    private By showLogInButtonLocator = By.className("showlogin");
    private By inputLogInNameLocator = By.id("username");
    private By inputPasswordLocator = By.id("password");
    private By logInButtonLocator = By.cssSelector("[name='login']");
    private String userName = "Inna";
    private String password = "test";
    private By inputNameLocator = By.id("billing_first_name");
    private By inputLastnameLocator = By.id("billing_last_name");
    private By inputAddressLocator = By.id("billing_address_1");
    private By inputCityLocator = By.id("billing_city");
    private By inputRegionLocator = By.id("billing_state");
    private By inputPostCodeLocator = By.id("billing_postcode");
    private By inputPhoneNumberLocator = By.id("billing_phone");
    private By inputCommentLocator = By.id("order_comments");
    private By submitOrderButtonLocator = By.id("place_order");
    private String name = "Инна";
    private String lastname = "Иванова";
    private String address = "ул.Фруктовая, д.5, корп.3, кв.99";
    private String city = "Москва";
    private String region = "Московская";
    private String postcode = "123456";
    private String phoneNumber = "89991235577";
    private String comment = "Просьба предварительно согласовать время доставки";
    private By orderConfirmationLocator = By.cssSelector("h2.post-title");
    private By selectedPaymentMethodLocator = By.className("method");
    private By orderDetailsLocator = By.className("order_details");
    private By alertLocator = By.cssSelector("[role='alert']");


    @Test
    public void checkoutAndChoosePaymentByBankTransfer_CheckoutConfirmationAndPaymentMethodMatches() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);

        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);

        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);

        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);

        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);

        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);

        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);
        driver.findElement(inputCommentLocator).sendKeys(comment);
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(orderDetailsLocator)));
        var expectedOrderConfirmationText = "Заказ получен";
        var actualOrderConfirmationText = driver.findElement(orderConfirmationLocator);
        var expectedPaymentMethod = "Прямой банковский перевод";
        var actualPaymentMethod = driver.findElement(selectedPaymentMethodLocator).getText();
        Assert.assertTrue("Не отображается подтверждение заказа", actualOrderConfirmationText.isDisplayed());
        Assert.assertEquals("Неверный текст подтверждения заказа",
                expectedOrderConfirmationText, actualOrderConfirmationText.getText());
        Assert.assertTrue("Неверный метод оплаты", actualPaymentMethod.contains(expectedPaymentMethod));
    }

    @Test
    public void checkoutAndChoosePaymentOnDelivery_CheckoutConfirmationAndPaymentMethodMatches() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);

        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);

        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);

        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);

        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);

        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);

        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);
        driver.findElement(inputCommentLocator).sendKeys(comment);
        var paymentOnDeliveryButton = driver.findElement(By.cssSelector("[for='payment_method_cod']"));
        paymentOnDeliveryButton.click();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(orderDetailsLocator)));
        var expectedOrderConfirmationText = "Заказ получен";
        var actualOrderConfirmationText = driver.findElement(orderConfirmationLocator);
        var expectedPaymentMethod = "Оплата при доставке";
        var actualPaymentMethod = driver.findElement(selectedPaymentMethodLocator).getText();
        Assert.assertTrue("Не отображается подтверждение заказа", actualOrderConfirmationText.isDisplayed());
        Assert.assertEquals("Неверный текст подтверждения заказа",
                expectedOrderConfirmationText, actualOrderConfirmationText.getText());
        Assert.assertTrue("Неверный метод оплаты", actualPaymentMethod.contains(expectedPaymentMethod));
    }

    @Test
    public void openCheckout_EmailInputIsFilledWithUserEmail() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //act
        driver.findElement(checkoutButtonLocator).click();

        //arrange
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //assert
        var userEmail = "test@sk.ru";
        var emailInputLocator = driver.findElement(By.id("billing_email"));
        Assert.assertFalse("Поле email пустое", emailInputLocator.getAttribute("value").isEmpty());
        Assert.assertEquals("Неверный email", userEmail, emailInputLocator.getAttribute("value"));
    }

    @Test
    public void emptyInput_AlertsAppear() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        //act
        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Имя для выставления счета обязательное поле.\n" +
                "Фамилия для выставления счета обязательное поле.\n" +
                "Адрес для выставления счета обязательное поле.\n" +
                "Город / Населенный пункт для выставления счета обязательное поле.\n" +
                "Область для выставления счета обязательное поле.\n" +
                "Почтовый индекс для выставления счета обязательное поле.\n" +
                "неверный номер телефона.\n" +
                "Телефон для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyNameInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        driver.findElement(inputNameLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Имя для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyLastnameInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Фамилия для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyAddressInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Адрес для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyCityInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        driver.findElement(inputCityLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Город / Населенный пункт для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyRegionInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Область для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyPostCodeInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        ;
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "Почтовый индекс для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void emptyPhoneNumberInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);


        //act
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "неверный номер телефона.\n" +
                "Телефон для выставления счета обязательное поле.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void invalidPhoneNumberInput_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();

        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);

        //act
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys("9991235577");
        driver.findElement(submitOrderButtonLocator).click();

        //assert
        var expectedAlertText = "неверный номер телефона.";
        Assert.assertTrue("Не отображается уведомление", driver.findElement(alertLocator).isDisplayed());
        Assert.assertEquals("Неверный текст уведомления",
                expectedAlertText, driver.findElement(alertLocator).getText());
    }

    @Test
    public void applyCoupon_CouponInformationAppearsAndTotalCartPriceIsChanged() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(checkoutButtonLocator).click();
        driver.findElement(showLogInButtonLocator).click();
        driver.findElement(inputLogInNameLocator).sendKeys(userName);
        driver.findElement(inputPasswordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();
        driver.findElement(inputNameLocator).clear();
        driver.findElement(inputNameLocator).sendKeys(name);
        driver.findElement(inputLastnameLocator).clear();
        driver.findElement(inputLastnameLocator).sendKeys(lastname);
        driver.findElement(inputAddressLocator).clear();
        driver.findElement(inputAddressLocator).sendKeys(address);
        driver.findElement(inputCityLocator).clear();
        driver.findElement(inputCityLocator).sendKeys(city);
        driver.findElement(inputRegionLocator).clear();
        driver.findElement(inputRegionLocator).sendKeys(region);
        driver.findElement(inputPostCodeLocator).clear();
        driver.findElement(inputPostCodeLocator).sendKeys(postcode);
        driver.findElement(inputPhoneNumberLocator).clear();
        driver.findElement(inputPhoneNumberLocator).sendKeys(phoneNumber);

        //act
        var showCouponLocator = driver.findElement(By.className("showcoupon"));
        showCouponLocator.click();
        var inputCouponLocator = driver.findElement(By.id("coupon_code"));
        var submitCouponButton = driver.findElement(By.cssSelector("[name='apply_coupon']"));
        inputCouponLocator.sendKeys("sert500");
        submitCouponButton.click();

        //assert
        var discountInfoLocator = driver.findElement(By.className("cart-discount"));
        wait.until(ExpectedConditions.visibilityOf(discountInfoLocator));
        Assert.assertTrue("Не отображается информация о скидке", discountInfoLocator.isDisplayed());
        var subtotalPriceLocator = driver.findElement(By.cssSelector(".cart-subtotal bdi"));
        var totalPriceLocator = driver.findElement(By.cssSelector(".order-total bdi"));
        var subtotalFigure = Integer.parseInt(subtotalPriceLocator.getText()
                .substring(0, subtotalPriceLocator.getText().indexOf(",")));
        var expectedTotalPrice = "" + (subtotalFigure - 500);
        var actualTotalPrice = totalPriceLocator.getText().substring(0, totalPriceLocator.getText().indexOf(","));
        Assert.assertEquals("Неверная сумма к оплате", expectedTotalPrice, actualTotalPrice);
    }
}
