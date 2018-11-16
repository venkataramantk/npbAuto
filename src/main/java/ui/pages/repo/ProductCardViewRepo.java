package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 8/28/2017.
 */
public class ProductCardViewRepo extends HeaderMenuRepo {
    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(css = ".color-image")
    public List<WebElement> colorImages;

    public WebElement colorSwatchByPos(int i){
            return getDriver().findElement(By.cssSelector(".color-chips-selector-items-list label:nth-child("+i+")"));
    }

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price list-price']//ancestor::li//h3")
    public List<WebElement> cqItemName;

    @FindBy(xpath = "(//div[@class='size-and-fit-detail-container size-and-fit-detail'])[1]/div/label")
    public List<WebElement> selectAFitOrSizeList;

    @FindBy(xpath = "//div[@class='size-and-fit-detail-container size-and-fit-detail'][contains(.,'Select a Size')]/div/label[@class='label-radio input-radio size-and-fit-detail-item size-and-fit-detail']")
    public List<WebElement> selectAvailableSizes;

    public WebElement productNamesByPosition(int i){
        return getDriver().findElement(By.cssSelector("li:nth-child("+i+") .product-title-container h3 a"));
    }

    @FindBy(css = ".select-common.select-qty select")//"#quantity")
    public WebElement qtyDropDown;

    @FindBy(xpath = "(.//*[.='Select a Size']/parent::div//label[not(contains(@class,'disabled'))])")
    public List<WebElement> availableSizes;

    @FindBy(xpath = "//a[contains(translate(text(),'VIEW PRODUCT DETAILS','view product details'),'view product details')]")
    public WebElement viewProductDetailsLink;

    //Pooja
    @FindBy(xpath = "//label[@class='label-radio input-radio size-and-fit-detail-item size-and-fit-detail item-selected-option']/div/span[text()='ONE SIZE']")
    public WebElement alreadySelectedOneSizeBtnonFlip;



}
