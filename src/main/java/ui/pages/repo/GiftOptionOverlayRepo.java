package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by user on 6/15/2016.
 */

public class GiftOptionOverlayRepo extends UiBase {
    @FindBy(css="#commonLoadingId2")
    public WebElement btn_NoThanks;

    @FindBy(css="#commonLoadingId1")
    public WebElement btn_Next;

    @FindBy(xpath = "//*[@id='ui-dialog-title-gift-options']")
    public WebElement txt_Overlay_GiftOption;

    @FindBy(xpath = "//*[@type='radio']/following-sibling::b[contains(.,'select this option')]")
    public WebElement txt_Select_The_Options;

    @FindBy(xpath = "//*[@id='gift-message']")
    public WebElement txtArea_Gift_Message_box;

    @FindBy(xpath = "//*[@id='gift-options']//p[contains(.,'Maximum of 4 lines, 25 characters per line, including spaces and hard returns.')]")
    public WebElement txt_MessageLimit;

    @FindBy(xpath = "//*[contains(@id,'giftOption') and @type='radio']")
    public WebElement radio_giftOptions;

    @FindBy(xpath = "//*[contains(@id,'giftOption')][@data_tproductsku='giftingPrdSku1']")
    public WebElement radio_Select_giftOptions;

    @FindBy(xpath = "//*[@class='gift-details-name' and contains(.,'Gift Receipt / Gift Message Only')]")
    public WebElement txt_GiftOptionName;

    @FindBy(xpath = "//*[@class='gift-details-price' and contains(.,'FREE')]")
    public WebElement txt_GiftOptionPrice;

}
