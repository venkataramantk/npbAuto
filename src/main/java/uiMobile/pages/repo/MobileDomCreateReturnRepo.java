package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileDomCreateReturnRepo extends UiBaseMobile {

    @FindBy(xpath="//label[text()='ERRORS']")
    public WebElement returnsError;

    public WebElement returnRadioButton(int rowNum){
        return getDriver().findElement(By.xpath("(//label[text()='RETURN'])[" + rowNum + "]"));
    }

    @FindBy(css=".x-trigger-cell.x-unselectable")
    public List<WebElement> qtyDropDowns;


    public WebElement qtyDropDownValue(int i, String qty){
        return getDriver().findElement(By.xpath("(//li[@class='x-olm-modern-combo-item' and @role='option'][text()='"+qty+"'])["+i+"]"));
    }

    public WebElement qtyTextBox(int i){
    return getDriver().findElement(By.cssSelector("li.x-olm-modern-combo-item.x-olm-modern-combo-item-over"));//("(//input[@class='olm-modern-trigger-field olm-main-text x-form-required-field x-form-text'])["+i+"]"));
    }
    @FindBy(xpath="//span[text()='DONE']")
    public WebElement doneButton;

    public WebElement qtyDropDown(int rowNum){
        return getDriver().findElement(By.xpath("(//div[@class='x-trigger-index-0 x-form-trigger olm-modern-trigger x-form-trigger-first'])[" + rowNum + "]"));
    }

    @FindBy(xpath="//tr[@class='x-form-item-input-row'][contains(.,'SKU')]")
    public List<WebElement> skuElements;


    @FindBy(xpath="//div[@class='x-container olm-item-scroll x-container-default']")
    public WebElement scrollDownElement;
    public String scrollDownCssElement = "x-container olm-item-scroll x-container-default";

    public WebElement skuLabelElement(int rowNum){
        return getDriver().findElement(By.xpath("(//tr[@class='x-form-item-input-row'][contains(.,'SKU')]/td[2]/div[@class='olm-main-text-black olm-text-over-ellipsis olm-no-wrap olm-hide-over-x'])[" + rowNum + "]"));
    }

    @FindBy(xpath="//span[text()='PROCEED TO REVIEW']")
    public WebElement proceedToReview;

    @FindBy(xpath="//span[text()='CONFIRM RETURN']")
    public WebElement confirmReturn;

    @FindBy(xpath="//label[contains(text(),'HAS BEEN CREATED SUCCESSFULLY')]")
    public WebElement returnNumCreatedSuccessMsg;

    @FindBy(css=".x-tool-img.x-tool-close")
    public WebElement closeButtonOnRetNumCreated;

}
