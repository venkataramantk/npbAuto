package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by AbdulazeezM on 5/11/2017.
 */
public class PickUpPageRepo extends UiBase {

    @FindBy(css = ".button-primary.button-next-step")
    public WebElement nxtShippingBtn;

    @FindBy(css = ".button-primary.button-next-step")
    public WebElement nxtBillingBtn;

    @FindBy(css = ".items-total")
    public WebElement itemTotalText;

    @FindBy(xpath = ".//li[@class='balance-total']/strong")
    public WebElement estimatedTotal;

    @FindBy(css = ".return-to-bag-link")
    public WebElement returnBagLink;

    @FindBy(xpath = ".//div[@class='navigation-confirmation expanded']//span")
    public WebElement overlayContent;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnBagBtn;

    @FindBy(css= "[name='pickUpContact.firstName']")
    public WebElement firstNameField;

    @FindBy(css= "[name='pickUpAlternate.firstName']")
    public WebElement altFirstNameFld;

    @FindBy(css = "input[name='firstName']")
    public WebElement fn_pickupContactFld;

    @FindBy(css = "input[name='lastName']")
    public WebElement ln_pickupContactFld;

    @FindBy(css = "input[name='phoneNumber']")
    public WebElement mn_pickupContactFld;

    @FindBy(css = "[name='pickUpContact.lastName']")
    public WebElement lastNameField;

    @FindBy(css = "[name='pickUpAlternate.lastName']")
    public WebElement altLastNameField;

    @FindBy(css = "[name='pickUpContact.emailAddress']")
    public WebElement emailIdField;

    @FindBy(css = "input[name='pickUpAlternate.emailAddress']")
    public WebElement altEmailIdField;

    @FindBy(css = "[name='pickUpContact.emailSignup']")
    public WebElement signUpEmail_CheckBox;

    @FindBy(css = ".label-checkbox.input-checkbox.alternate-pickup")
    public WebElement alternatePickupCheckBox;

    @FindBy(css = ".label-checkbox.input-checkbox.alternate-pickup input")
    public WebElement alternatePickupCheckBoxInput;

    @FindBy(css = ".checkout-review-pickup-note")
    public WebElement govtText;

    @FindBy(css = ".checkout-summary-edit.summary-title-pick-up-contact")
    public WebElement editLink;

    @FindBy(css = ".button-save-address")
    public WebElement saveButton;

    @FindBy(css = ".button-cancel-address")
    public WebElement cancelLink;

    @FindBy(css = ".first-name .inline-error-message")//"[name='pickUpContact.firstName'] +  .inline-error-message")
    public WebElement firstNameErr;

    @FindBy(css = ".last-name .inline-error-message")//"[name='pickUpContact.lastName'] + .inline-error-message")
    public WebElement lastNameErr;

    @FindBy(css = ".email-address .inline-error-message")//"[name='pickUpContact.emailAddress'] + .inline-error-message")
    public WebElement emailErrMsg;

    @FindBy(css = "[name='pickUpContact.phoneNumber']")
    public WebElement mobileNumField;

    @FindBy(css = ".phone-number .inline-error-message")//"[name='pickUpContact.phoneNumber'] + .inline-error-message")
    public WebElement mobileFieldErr;

    @FindBy(xpath="//li//button[text()='Pickup']")
    public WebElement pickUplinkInProgressBar;

    @FindBy(css=".container-button .button-primary.button-next-step")
    public WebElement nxtButton;

    @FindBy(css=".name")
    public WebElement pickUpContactName;

    @FindBy(xpath="//*[text()='Pickup']/..")
    public WebElement pickupProgressBar;

    @FindBy(xpath="//*[text()='Shipping']/..")
    public WebElement shippingProgressBar;

    @FindBy(xpath="//*[text()='Billing']/..")
    public WebElement billingProgressBar;

    @FindBy(xpath = "//*[text()='Review']/..")
    public WebElement reviewProgressBar;

    @FindBy(tagName = "h2")
    public WebElement checkoutTitle;
}
