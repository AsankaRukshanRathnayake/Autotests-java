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

public class CatalogPageTests {

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

    private By tvCategoryLocator = By.cssSelector(".product-categories [href$='tv/']");
    private By searchResultsLocator = By.cssSelector(".content-inner .product");

    @Test
    public void selectCategory_categoryMatches() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");

        //act
        driver.findElement(tvCategoryLocator).click();

        //assert
        var expectedTextInSearchResult = "телевизор";
        var actualTextInSearchResult = driver.findElements(searchResultsLocator);
        for (int i = 0; i < actualTextInSearchResult.size(); i++) {
            Assert.assertTrue("Найденный товар (" + (i + 1) + ") не соответствуют категории",
                    actualTextInSearchResult.get(i).getText().toLowerCase().contains(expectedTextInSearchResult));
        }
    }

    @Test
    public void selectCategory_SearchResultCountMatches() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");

        //act
        driver.findElement(tvCategoryLocator).click();

        //assert
        var expectedSearchResultCount = Integer.parseInt(driver.findElement(
                By.cssSelector("[href$='tv/'] + .count")).getText().replaceAll("[\\(\\)]", ""));
        var actualSearchResultCount = driver.findElements(searchResultsLocator).size();
        Assert.assertEquals("Количетво товара не соответствует указаному в категории",
                expectedSearchResultCount, actualSearchResultCount);
    }

    @Test
    public void addToCart_CartIsOpenAndProductIsAddedToCart() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");

        //act
        var firstAddToCartButtonLocator = driver.findElement(By.className("add_to_cart_button"));
        firstAddToCartButtonLocator.click();
        var moreDetailsButtonLocator = driver.findElement(By.className("added_to_cart"));
        moreDetailsButtonLocator.click();

        //assert
        var expectedPageHeading = "Корзина";
        var actualPageHeading = driver.findElement(By.cssSelector(".page_header_wrap .current")).getText();
        var expectedProductInCartName = "Apple Watch 6";
        var actualProductInCartName = driver.findElement(By.cssSelector(".product-name a")).getText();
        Assert.assertEquals("Открылась другая страница", expectedPageHeading, actualPageHeading);
        Assert.assertEquals("Неверное имя товара в корзине", expectedProductInCartName, actualProductInCartName);
    }

    @Test
    public void goToAnotherPage_SelectedPageNumberMatches() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");

        //act
        var fourthPageButtonLocator = driver.findElement(By.cssSelector(".page-numbers a[href$='/4/']"));
        fourthPageButtonLocator.click();

        //assert
        var expectedCurrentPageNumber = "4";
        var actualCurrentPageNumber = driver.findElement(By.cssSelector(".page-numbers .current"));
        Assert.assertEquals("Неверный номер страницы", expectedCurrentPageNumber, actualCurrentPageNumber.getText());
    }

    @Test
    public void goToProductCart_ProductCardIsOpen() {
        //arrange
        driver.navigate().to("http://intershop5.skillbox.ru/product-category/catalog/");
        var watchCategoryLocator = driver.findElement(By.cssSelector(".product-categories a[href$='watch/']"));
        watchCategoryLocator.click();

        //act
        var firstFoundProductLinkLocator = driver.findElement(By.cssSelector(".product.first"));
        firstFoundProductLinkLocator.click();

        //assert
        var expectedOpenProductTitle = "Apple Watch 6";
        var actualOpenProductTitle = driver.findElement(By.className("product_title"));
        Assert.assertTrue("Заголовок товара не отображается", actualOpenProductTitle.isDisplayed());
        Assert.assertEquals("Открыта карточка другого товара", expectedOpenProductTitle, actualOpenProductTitle.getText());
    }

/** Доп.задание:
 Время написания тестов - 50 мин
 Время выполнения тестов  - 0,57 мин
 Время на ручное тестирование - 2,1 мин
 50 * (2,1 - 0,57) = 76,5 => Через 77 запусков тестов тестировать автоматизированно будет выгоднее.
 */
}
