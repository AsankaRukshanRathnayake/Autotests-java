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

public class CartPageTests {

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
    private By subtotalPriceLocator = By.cssSelector(".product-subtotal bdi");
    private By finalSumLocator = By.cssSelector(".order-total bdi");
    private By removeFromCartButtonLocator = By.className("remove");
    private By couponInputLocator = By.id("coupon_code");
    private By submitCouponButtonLocator = By.cssSelector("[name='apply_coupon']");
    private By couponInfoLocator = By.className("cart-discount");
    private int couponAmount = 500;
    private int discountedProductPrice = 34990;

    @Test
    public void addToCart_TotalCartPriceIsCalculatedCorrectly() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");

        //act
        driver.findElements(addToCartButtonLocator).get(1).click();
        driver.findElements(addToCartButtonLocator).get(2).click();
        driver.findElement(cartLinkLocator).click();

        //assert
        var productOnePrice = Integer.parseInt(driver.findElements(subtotalPriceLocator).get(0).getText()
                .substring(0, driver.findElements(subtotalPriceLocator).get(0).getText().indexOf(",")));
        var productTwoPrice = Integer.parseInt(driver.findElements(subtotalPriceLocator).get(1).getText()
                .substring(0, driver.findElements(subtotalPriceLocator).get(1).getText().indexOf(",")));
        var expectedFinalSumOfCart = "" + (productOnePrice + productTwoPrice);
        var actualFinalSumOfCart = driver.findElement(finalSumLocator).getText()
                .substring(0, driver.findElement(finalSumLocator).getText().indexOf(","));
        Assert.assertEquals("Неверная сумма корзины", expectedFinalSumOfCart, actualFinalSumOfCart);
    }

    @Test
    public void productRemovalFromCart_ProductIsRemoved() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //act
        driver.findElement(removeFromCartButtonLocator).click();

        //assert
        var expectedNoteText = "Корзина пуста.";
        var actualNoteText = driver.findElement(By.className("cart-empty"));
        Assert.assertTrue("Не отображается уведомление", actualNoteText.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления", expectedNoteText, actualNoteText.getText());
    }

    @Test
    public void returnRemovedProductToCart_ProductIsReturned() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();
        driver.findElement(removeFromCartButtonLocator).click();

        //act
        var removeCancellationButtonLocator = driver.findElement(By.className("restore-item"));
        removeCancellationButtonLocator.click();

        //assert
        var expectedProductInCartName = "Apple Watch 6";
        var actualProductInCartName = driver.findElement(By.cssSelector(".product-name a"));
        Assert.assertTrue("Товар не вернулся в корзину", actualProductInCartName.isDisplayed());
        Assert.assertEquals("Неверное наименование товара",
                expectedProductInCartName, actualProductInCartName.getText());
    }

    @Test
    public void applyValidCoupon_CouponInformationAppearsAndTotalCartPriceIsChanged() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //act
        driver.findElement(couponInputLocator).sendKeys("sert500");
        driver.findElement(submitCouponButtonLocator).click();

        //assert
        Assert.assertTrue("Не отображается информация о скидке",
                driver.findElement(couponInfoLocator).isDisplayed());
        var productPrice = Integer.parseInt(driver.findElements(subtotalPriceLocator).get(0).getText()
                .substring(0, driver.findElements(subtotalPriceLocator).get(0).getText().indexOf(",")));
        var expectedFinalSumOfCart = "" + (productPrice - couponAmount);
        var actualFinalSumOfCart = driver.findElement(finalSumLocator).getText()
                .substring(0, driver.findElement(finalSumLocator).getText().indexOf(","));
        Assert.assertEquals("Неверная сумма корзины после применения купона",
                expectedFinalSumOfCart, actualFinalSumOfCart);
    }

    @Test
    public void applyInvalidCoupon_AlertAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //act
        driver.findElement(couponInputLocator).sendKeys("sert5000");
        driver.findElement(submitCouponButtonLocator).click();

        //assert
        var alertLocator = driver.findElement(By.className("woocommerce-error"));
        var expectedAlertText = "Неверный купон.";
        Assert.assertTrue("Не отображается уведомление о недействительном купоне", alertLocator.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления о недействительном купоне",
                expectedAlertText, alertLocator.getText());
    }

    @Test
    public void goToCheckout_CheckoutPageIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //act
        var checkoutButtonLocator = driver.findElement(By.className("checkout-button"));
        checkoutButtonLocator.click();

        //assert
        var expectedOpenPageSubtitleText = "Оформление Заказа";
        var actualOpenPageSubtitleText = driver.findElement(By.cssSelector(".page_header_wrap .current"));
        Assert.assertTrue("Не открывается страница оформления закаа", actualOpenPageSubtitleText.isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы оформления заказа",
                expectedOpenPageSubtitleText, actualOpenPageSubtitleText.getText());
    }

    @Test
    public void addDiscountedProductToCart_DiscountPriceRemains() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");

        //act
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //assert
        var actualDiscountedProductPrice = Integer.parseInt(driver.findElements(subtotalPriceLocator).get(0).getText()
                .substring(0, driver.findElements(subtotalPriceLocator).get(0).getText().indexOf(",")));
        Assert.assertEquals("Неверная цена акционного товара",
                discountedProductPrice, actualDiscountedProductPrice);
    }

    @Test
    public void applyCouponToDiscountedProduct_TotalCartPriceIsChangedCorrectly() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        driver.findElement(addToCartButtonLocator).click();
        driver.findElement(cartLinkLocator).click();

        //act
        driver.findElement(couponInputLocator).sendKeys("sert500");
        driver.findElement(submitCouponButtonLocator).click();

        //assert
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(couponInfoLocator)));
        var expectedFinalSumOfCart = "" + (discountedProductPrice - couponAmount);
        var actualFinalSumOfCart = driver.findElement(finalSumLocator).getText()
                .substring(0, driver.findElement(finalSumLocator).getText().indexOf(","));
        Assert.assertEquals("Неверная цена акционного товара после применения купона",
                expectedFinalSumOfCart, actualFinalSumOfCart);
    }
}
