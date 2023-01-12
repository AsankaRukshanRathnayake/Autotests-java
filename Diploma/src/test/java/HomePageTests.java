import org.junit.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class HomePageTests {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private By searchInputLocator = By.className("search-field");
    private By searchSubmitButtonLocator = By.className("searchsubmit");
    private By openPageTitle = By.className("entry-title");
    private By openPageSubtitle = By.cssSelector("div.page_header_wrap span.current");
    private By addToCartButtonLocator = By.xpath("(//a[contains(@class,'add_to_cart_button')])[1]");
    private By promoSectionTitleLocator = By.className("entry-title");
    private By productsLocator = By.cssSelector("[aria-hidden='false'] > [href *='product']");
    private By backToHomePageLocator = By.cssSelector("[href$='skillbox.ru']");

    //  Отображение сайта
    @Test
    public void openWebsite_OnlineStoreIsOpen() {
        //act
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //assert
        var titleLocator = driver.findElement(By.className("site-description"));
        var title = "Интернет магазин";
        Assert.assertTrue("Не отображается сайт интернет-магазина", titleLocator.isDisplayed());
        Assert.assertEquals("Неверный заголовок сайта", title, titleLocator.getText());
    }

    //  Поиск товара
    @Test
    public void presentedProductSearch_ProductIsFound() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var productName = "стиральная машина";
        driver.findElement(searchInputLocator).sendKeys(productName);
        driver.findElement(searchSubmitButtonLocator).click();

        //assert
        var firstFoundProductNameLocator = driver.findElement(By.cssSelector(".product:nth-of-type(1) h3"));
        Assert.assertTrue("Не отображается искомый товар", firstFoundProductNameLocator.isDisplayed());
        Assert.assertTrue("Найден неверный товар",
                firstFoundProductNameLocator.getText().toLowerCase().contains(productName));
    }

    @Test
    public void non_presentedProductSearch_NoteAppears() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var productName = "блаблабла";
        driver.findElement(searchInputLocator).sendKeys(productName);
        driver.findElement(searchSubmitButtonLocator).click();

        //arrange
        var notificationLocator = driver.findElement(By.className("woocommerce-info"));
        var notificationTextExpected = "По вашему запросу товары не найдены.";
        Assert.assertTrue("Не отображается уведомление", notificationLocator.isDisplayed());
        Assert.assertEquals("Неверный текст уведомления", notificationTextExpected, notificationLocator.getText());
    }

    //  Главное меню
    @Test
    public void clickCatalogInMainMenu_CatalogIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var catalogLinkLocator = driver.findElement(By.cssSelector("#site-navigation [href$='catalog/']"));
        catalogLinkLocator.click();

        //assert
        var expectedText = "КАТАЛОГ";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageTitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageTitle).getText());
    }

    @Test
    public void clickAccountInMainMenu_AccountIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var accountLinkLocator = driver.findElement(By.cssSelector("#site-navigation [href$='account/']"));
        accountLinkLocator.click();

        //assert
        var expectedText = "Мой Аккаунт";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageSubtitle).getText());
    }

    @Test
    public void clickCartInMainMenu_CartIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var cartLinkLocator = driver.findElement(By.cssSelector("#site-navigation [href$='cart/']"));
        cartLinkLocator.click();

        //assert
        var expectedText = "Корзина";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageSubtitle).getText());
    }

    @Test
    public void clickCheckoutInMainMenu_CheckoutIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButtonLocator));
        driver.findElement(addToCartButtonLocator).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //act
        var checkoutLinkLocator = driver.findElement(By.cssSelector("#site-navigation [href$='checkout/']"));
        checkoutLinkLocator.click();

        //assert
        var expectedSubtitleText = "Оформление Заказа";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы",
                expectedSubtitleText, driver.findElement(openPageSubtitle).getText());
    }

    //  Промо-разделы
    @Test
    public void clickFirstPromoCategory_CorrectCategoryIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var firstPromoCategoryLocator = driver.findElement(By.cssSelector("#promo-section1 .widget:nth-of-type(1)"));
        firstPromoCategoryLocator.click();

        //assert
        var expectedCategoryTitle = "КНИГИ";
        Assert.assertTrue("Не отображается раздел 'Книги'",
                driver.findElement(promoSectionTitleLocator).isDisplayed());
        Assert.assertEquals("Неверный заголовок раздела 'Книги'",
                expectedCategoryTitle, driver.findElement(promoSectionTitleLocator).getText());
    }

    @Test
    public void clickSecondPromoCategory_CorrectCategoryIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var secondPromoCategoryLocator = driver.findElement(By.cssSelector("#promo-section1 .widget:nth-of-type(2)"));
        secondPromoCategoryLocator.click();

        //assert
        var expectedCategoryTitle = "ПЛАНШЕТЫ";
        Assert.assertTrue("Не отображается раздел 'Планшеты'",
                driver.findElement(promoSectionTitleLocator).isDisplayed());
        Assert.assertEquals("Неверный заголовок раздела 'Планшеты'",
                expectedCategoryTitle, driver.findElement(promoSectionTitleLocator).getText());
    }

    @Test
    public void clickThirdPromoCategory_CorrectCategoryIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var promoCamerasLocator = driver.findElement(By.cssSelector("#promo-section1 .widget:nth-of-type(3)"));
        promoCamerasLocator.click();

        //assert
        var expectedCategoryTitle = "ФОТО/ВИДЕО";
        Assert.assertTrue("Не отображается раздел 'Фотоаппараты'",
                driver.findElement(promoSectionTitleLocator).isDisplayed());
        Assert.assertEquals("Неверный заголовок раздела 'Фотоаппараты'",
                expectedCategoryTitle, driver.findElement(promoSectionTitleLocator).getText());
    }

    //Блок распродажа
    @Test
    public void openHomePage_OnSaleNoteIsMarkedInOnSaleSection() {
        //act
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
        wait.until(ExpectedConditions.elementToBeClickable((driver.findElements(productsLocator).get(3))));

        //assert
        var onSaleNoteLocator = driver.findElements(By.cssSelector("[aria-hidden='false'] .onsale"));
        var expectedText = "Скидка!";
        for (int i = 0; i < onSaleNoteLocator.size(); i++) {
            Assert.assertEquals("Неверное примечание о скидке на товаре (" + (i + 1) + ") в разделе 'Распродажа'",
                    expectedText, onSaleNoteLocator.get(i).getText());
        }
    }

    //Блок Новые поступления
    @Test
    public void openHomePage_NewProductNoteIsMarkedInNewArrivalsSection() {
        //act
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
        wait.until(ExpectedConditions.elementToBeClickable((driver.findElements(productsLocator).get(7))));

        //assert
        var expectedText = "Новый!";
        var newProductNoteLocator = driver.findElements(By.cssSelector("[aria-hidden='false'] span.label-new"));
        for (int i = 0; i < newProductNoteLocator.size(); i++) {
            Assert.assertEquals("Неверное примечание на товаре (" + (i + 1) + ") в разделе 'Новые поступления'",
                    expectedText, newProductNoteLocator.get(i).getText());
        }
    }

    //  Футтер
    @Test
    public void clickAllGoodsInFooter_AllGoodsAreOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var allGoodsLinkLocator = driver.findElement(By.cssSelector(".top-footer-block [href$='shop/']"));
        allGoodsLinkLocator.click();

        //assert
        var expectedText = "ВСЕ ТОВАРЫ";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageTitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageTitle).getText());
    }

    @Test
    public void clickCartInFooter_CartIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var cartLinkLocator = driver.findElement(By.cssSelector(".top-footer-block [href$='cart/']"));
        cartLinkLocator.click();

        //assert
        var expectedText = "Корзина";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageSubtitle).getText());
    }

    @Test
    public void clickAccountInFooter_AccountIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var accountLinkLocator = driver.findElement(By.cssSelector(".top-footer-block [href$='account/']"));
        accountLinkLocator.click();

        //assert
        var expectedText = "Мой Аккаунт";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageSubtitle).getText());
    }

    @Test
    public void clickCheckoutInFooter_CheckoutIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButtonLocator));
        driver.findElement(addToCartButtonLocator).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //act
        var checkoutLinkLocator = driver.findElement(By.cssSelector(".top-footer-block [href$='checkout/']"));
        checkoutLinkLocator.click();

        //assert
        var expectedText = "Оформление Заказа";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageSubtitle).getText());
    }

    @Test
    public void clickRegistrationInFooter_RegistrationIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");

        //act
        var registrationLinkLocator = driver.findElement(By.cssSelector(".top-footer-block [href$='register/']"));
        registrationLinkLocator.click();

        //assert
        var expectedText = "Регистрация";
        Assert.assertTrue("Не открывается страница", driver.findElement(openPageSubtitle).isDisplayed());
        Assert.assertEquals("Неверный заголовок страницы", expectedText, driver.findElement(openPageSubtitle).getText());
    }

    //  Блок просмотренные товары
    @Test
    public void viewSomeProducts_ViewedSectionConsistsOfViewedProducts() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();

        //act
        for (int i = 0; i <= 2; i++) {
            wait.until(ExpectedConditions.elementToBeClickable((driver.findElements(productsLocator).get(i))));
            driver.findElements(productsLocator).get(i).click();
            driver.findElement(backToHomePageLocator).click();
        }

        //assert
        var expectedTextForViewedProductOne = driver.findElement(
                By.xpath("(//li[@aria-hidden='false']//h3)[1]")).getText().toLowerCase();
        var actualTextForViewedProductOne = driver.findElement(
                        By.xpath("(//aside[contains(@id,'recently_viewed_products')]//span[@class='product-title'])[3]"))
                .getText().toLowerCase();
        Assert.assertEquals("Неверное наименование 1-го просмотренного товара",
                expectedTextForViewedProductOne, actualTextForViewedProductOne);
        wait.until(ExpectedConditions.elementToBeClickable((driver.findElements(productsLocator).get(2))));
        var expectedTextForViewedProductTwo = driver.findElement(
                By.xpath("(//li[@aria-hidden='false']//h3)[2]")).getText().toLowerCase();
        var actualTextForViewedProductTwo = driver.findElement(
                        By.xpath("(//aside[contains(@id,'recently_viewed_products')]//span[@class='product-title'])[2]"))
                .getText().toLowerCase();
        Assert.assertEquals("Неверное наименование 2-го просмотренного товара",
                expectedTextForViewedProductTwo, actualTextForViewedProductTwo);
        var expectedTextForViewedProductThree = driver.findElement(
                By.xpath("(//li[@aria-hidden='false']//h3)[3]")).getText().toLowerCase();
        var actualTextForViewedProductThree = driver.findElement(
                        By.xpath("(//aside[contains(@id,'recently_viewed_products')]//span[@class='product-title'])[1]"))
                .getText().toLowerCase();
        Assert.assertEquals("Неверное наименование 3-го просмотренного товара",
                expectedTextForViewedProductThree, actualTextForViewedProductThree);
    }
}
