package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 21/10/2017.
 */
public class MGiftCardsPageRepo extends UiBaseMobile {

    @FindBy(xpath = ".//a[contains(.,'A GIFT CARD')]|.//a[contains(.,'send an e-Gift card')]")
//Modified By Pooja added xpath woth or statement
    public WebElement sendAGiftCard_Btn;

    @FindBy(xpath = ".//a[contains(.,'EMAIL AN EGIFT CARD')]")
    public WebElement sendEGiftCard;

    @FindBy(xpath = ".//a[contains(.,'ANIMATED')]")
    public WebElement sendAnimatedGiftCard;

    @FindBy(xpath = "//a[contains(text(),'Gift Card balance')]")
    public WebElement giftCardBalanace;

    @FindBy(className = "label-radio input-radio size-and-fit-detail-item size-and-fit-detail")
    public WebElement cardValue;

    //Pooja
    @FindBy(css = ".gc-header-txt div")
    public WebElement gcPLPBanner;

    public WebElement getCardValue(String value) {
        return getDriver().findElement(By.xpath("//div[@class='size-and-fit-detail-items-list']//input[@value='"+value+"']"));
    }

    @FindBy(name = "cardNumber")
    public WebElement cardNo;

    @FindBy(name = "cardPin")
    public WebElement cardPin;

    @FindBy(xpath = "//button[text()='Check balance']")
    public WebElement checkBalanceBtn;

    @FindBy(css = ".inline-error-message")
    public List<WebElement> error;

    @FindBy(name = "quantity")
    public WebElement quantity;

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBag;

    @FindBy(id = "id_amount")
    public WebElement amountdd;

    @FindBy(css = "label[for='id_name']")
    public WebElement recipientNameFld;

    @FindBy(css = "label[for='id_email']")
    public WebElement recipientEmailFld;

    @FindBy(id = "id_message")
    public WebElement recipientMessageFld;

    @FindBy(id = "id_delivery_date_picker")
    public WebElement deliveryDateFld;

    @FindBy(css = "label[for='id_from_email']")
    public WebElement senderEmailFld;

    @FindBy(id = "id_credit_card_number")
    public WebElement creditCardnofld;

    @FindBy(css = "(//div[@id='expiration']//select)[1]")
    public WebElement expMonthDropdown;

    @FindBy(css = "(//div[@id='expiration']//select)[2]")
    public WebElement expYearDropdown;

    @FindBy(id = "id_security_code")
    public WebElement cvvfld;


    @FindBy(css = "label[for='id_first_name']")
    public WebElement firstNameFld;

    @FindBy(css = "label[for='id_last_name']")
    public WebElement lastNameFld;

    @FindBy(css = "label[for='id_zip_code']")
    public WebElement zipCodeFld;

    @FindBy(css = "label[for='id_phone_number']")
    public WebElement phnoFld;



    //Pooja
    @FindBy(css = ".gc-decision")
    public WebElement giftCardPage;

    //Pooja
    public WebElement getGiftCardByTitle(String type) {
        return getDriver().findElement(By.xpath(".//a/img[@alt='" + type + "']"));
    }

    //Pooja
    @FindBy(xpath = "//div[h2[contains(.,'Choose an eGift Card Design')]]//div[@id='carousel-container']")
    public WebElement eSendGiftCardPage;

    @FindBy(css = ".button-primary.button-cancel")
    public WebElement cancelBtnGCBalance;
}
