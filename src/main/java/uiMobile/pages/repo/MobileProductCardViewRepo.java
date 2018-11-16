package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 8/28/2017.
 */
public class MobileProductCardViewRepo extends MobileHeaderMenuRepo {
    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(css = ".color-chips-selector-items-list label div")
    public List<WebElement> colorImages;

    @FindBy(xpath = "(//div[@class='size-and-fit-detail-container size-and-fit-detail'])[1]/div/label")
    public List<WebElement> selectAFitOrSizeList;

    @FindBy(xpath = "//div[@class='size-and-fit-detail-container size-and-fit-detail'][contains(.,'Select a Size')]/div/label[@class='label-radio input-radio size-and-fit-detail-item size-and-fit-detail']")
    public List<WebElement> selectAvailableSizes;

    public WebElement productNamesByPosition(int i) {
        return getDriver().findElement(By.cssSelector("li:nth-child(" + i + ") .product-title-container h3 a"));
    }

    @FindBy(css = "[name='quantity']")
    public WebElement qtyDropDown;

    @FindBy(css = "//label[@class='label-radio input-radio size-and-fit-detail-item size-and-fit-detail'][contains(@for,'size0')]")
    public List<WebElement> availableSizes;

    @FindBy(css = "[name='quantity'] option")
    public List<WebElement> qtyDropDownOptions;

    //Pooja
    @FindBy(css = "label-radio input-radio size-and-fit-detail-item size-and-fit-detail item-selected-option")
    public List<WebElement> selectedOneSizeBtn;

    //Pooja
    @FindBy(css = "//label[@class='label-radio input-radio size-and-fit-detail-item size-and-fit-detail item-selected-option']/div/span")
    public List<WebElement> selectedSizeOnBagFlip;

    @FindBy(css = ".button-close")
    public WebElement closeIcon;

    @FindBy(css = ".size-and-fit-detail-items-list .input-radio-icon-checked")
    public List<WebElement> selectedFits;

    @FindBy(css = ".size-and-fit-detail-items-list")
    public List<WebElement> fitAndSize;

    @FindBy(css="//div[@class='size-and-fit-detail-container size-and-fit-detail'][1]//label")
    public List<WebElement> firstFitAndSize;

    @FindBy(css="//div[@class='size-and-fit-detail-container size-and-fit-detail'][2]//label")
    public List<WebElement> secondFitAndSize;
}
