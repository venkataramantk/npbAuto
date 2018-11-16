package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class GiftCardsPageRepo extends HeaderMenuRepo {

    @FindBy(xpath = "//a[text()='MAIL A GIFT CARD']")
    public WebElement sendAGiftCard_Btn;

    @FindBy(css = "#swapCard")
    public WebElement giftCardsDropDown;

    @FindBy(css = "#select_TCPSize")
    public WebElement cardValue;

    @FindBy(css = "#quantity")
    public WebElement quantity;

    //@FindBy(css = "#add_to_bag")
    @FindBy(css=".button-add-to-bag")
    public WebElement addToBag;

    public WebElement giftCardBalanceLink(String gcBalanceText) {
        return getDriver().findElement(By.xpath("(//li/a[text()='" + gcBalanceText + "'])[1]"));
    }

    @FindBy(css = ".gc-header-img")
    public WebElement giftCardBanner;

    @FindBy(xpath = ".//div[contains(@class,'gc-inside-submenu')]//a[contains(@onclick,'gcBalance')]")
    public WebElement giftCardBalanceLink;

    @FindBy(xpath = ".//div[contains(@class,'gc-inside-submenu')]//li[contains(.,'FAQ')]")
    public WebElement giftCardFaqLink;

    @FindBy(css = "a[title= 'Terms & Conditions']")
    public WebElement giftCardTemrsConditoonsLink;

    @FindBy(xpath = ".//a[contains(text(),'EMAIL AN EGIFT CARD')]")
    public WebElement giftCardElink;

    @FindBy(css = "img[alt='eGift cards']")
    public WebElement eGiftCardImage;

    @FindBy(xpath = ".//a[@class='gf-tcp-button'][contains(.,'EMAIL AN EGIFT CARD')]")
    public WebElement eGiftCardButton;

    @FindBy(xpath = ".//a[@class='gf-tcp-button'][contains(.,'MAIL A GIFT CARD')]")
    public WebElement mailGiftCardButton;

    @FindBy(xpath = "//*[@class='modal-title'][contains(.,'check your gift card balance')]")
    public WebElement giftCardBalanceHeader;

    @FindBy(css = ".steps-container h3")
    public List<WebElement> giftCardBalancesteps;

    @FindBy(name = "cardNumber")
    public WebElement giftCardBalanceNumber;

    @FindBy(xpath = ".//*[@class='input-common input-gift-card label-error']//div[@class='inline-error-message']")
    public WebElement giftCardBalanceNumberErr;

    @FindBy(xpath = ".//*[@class='input-common input-pin label-error']//div[@class='inline-error-message']")

    public WebElement giftCardBalancePinErr;

    @FindBy(xpath = "//div[@class='gift-card-balance-error']//div[@class='inline-error-message']")
    public WebElement giftCardBalanceCaptchaErr;

    @FindBy(name = "cardPin")
    public WebElement giftCardBalancepin;

    @FindBy(xpath = ".//*[@class='rc-anchor rc-anchor-normal rc-anchor-light']")
    public WebElement giftCardBalanceCaptcha;

    @FindBy(css = ".button-modal-close")
    public WebElement giftCardBalanceClose;

    @FindBy(xpath = "//button[contains(.,'Cancel')]")
    public WebElement giftCardBalanceCancelButton;

    @FindBy(xpath = "//button[contains(.,'Check balance')]")
    public WebElement giftCardBalanceCheckButton;

    @FindBy(css = ".images-container img")
    public List<WebElement> giftCardBalanceImages;

    public WebElement giftCardLinksByName(String linkName) {
        return getDriver().findElement(By.xpath(".//div[@class='gc-inside-submenu']//li[contains(.,'" + linkName + "')]"));

    }

    @FindBy(xpath = ".//*[@class='product-title'][contains(.,'Gift Card')]")
    public WebElement giftCardPDPTitle;

    @FindBy(css = ".actual-price")
    public WebElement giftCardPDPPrice;

    @FindBy(css = ".color-chips-selector-container")
    public WebElement giftCardPDPSelectDesign;

    @FindBy(css = ".size-and-fit-detail-container")
    public WebElement giftCardPDPSelectValue;

    @FindBy(xpath = "(.//*[@class='size-and-fit-detail-container size-and-fit-detail']/span)[2]")
    public WebElement giftCardPDPSelectValueupdate;

    @FindBy(css = ".button-wishlist")
    public WebElement addToWishList;

    @FindBy(xpath = ".//*[.='Select a Value (USD): ']/parent::div//label[not(contains(@class,'disabled'))]")
    public List<WebElement> giftCardBalanceSelectValueText;

    @FindBy(css = ".select-qty")
    public WebElement giftCardPDQuantity;

    @FindBy(css = ".button-add-to-bag")
    public WebElement giftCardPDAddToBag;

    @FindBy(css = ".sending-giftcard")
    public WebElement giftCardPDSendGiftLink;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Gift Card Balance']")
    public WebElement link_GiftCardBalance;

    @FindBy(css = ".button-edit-product")
    public WebElement editLinkSBDrawer;

    @FindBy(css = ".list-container li")
    public List<WebElement> listOfGC;

    @FindBy(css = ".custom-select-button.bag-item-color-select-button-closed")
    public WebElement designDropDown;

    @FindBy(css = ".button-update")
    public WebElement updateButton;

    @FindBy(css=".overlay-my-bag .notification-inline>p")
    public WebElement updateNotification;

    @FindBy(css = ".button-find-in-store")
    public WebElement pickUpStore;

    @FindBy(css=".overlay-my-bag select[name='quantity']")
    public WebElement qtyDropDown;

}
