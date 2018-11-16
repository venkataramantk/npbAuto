package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

public class MRecommendationsRepo extends UiBaseMobile {

    @FindBy(css = ".heading-product-recomendation")
    public WebElement recommendationsHeading;

    @FindBy(css = ".product-rec-list-item")
    public WebElement recommendedProductsImg;

    @FindBy(css = ".button-prev")
    public WebElement btnPrevious;

    @FindBy(css = ".button-next")
    public WebElement btnNext;

    @FindBy(css = ".favorite-icon-container")
    public WebElement favIcon;

    @FindBy(css = ".bag-button-container")
    public WebElement addToBagBtn;

    @FindBy(css = ".button-close")
    public WebElement closeIcon;


    @FindBy(css = ".select-common.select-qty")
    public WebElement qtyDropDown;


    @FindBy(css = ".text-price.list-price")
    public WebElement wasPrice;

    @FindBy(css = ".coupon-dots button")
    public List<WebElement> dots;

    @FindBy(css = ".button-pagination-dot.active")
    public WebElement activeDot;

    public WebElement getAvailableProduct(int i) {
        return getDriver().findElement(By.xpath("//ol[@class='content-product-recomendation']/li[" + i + "]//div[2]/a/figcaption/div[1]"));
    }

    @FindBy(css=".container-image img")
    public WebElement productImg;
    
    @FindBy(css="li.product-rec-list-item div.container-item-recomendation div.LazyLoad.is-visible")
    public WebElement onlyOneProductVisible;
}
