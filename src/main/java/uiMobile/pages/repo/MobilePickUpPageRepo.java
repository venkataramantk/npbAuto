package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by AbdulazeezM on 5/11/2017.
 */
public class MobilePickUpPageRepo extends UiBaseMobile {

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

    @FindBy(css = "[name='pickUpContact.firstName']")
    public WebElement firstNameField;

    @FindBy(css = "[name='pickUpAlternate.firstName']")
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

    @FindBy(css = ".address-container")
    public WebElement pickUpContact;

    @FindBy(css = ".checkout-review-pickup-note")
    public WebElement govtText;

    @FindBy(css = ".checkout-summary-edit.summary-title-pick-up-contact")
    public WebElement editLink;

    @FindBy(css = ".button.button-primary.button-save-pickup")
    public WebElement saveButton;

    @FindBy(css = ".button-cancel-address")
    public WebElement cancelLink;

    @FindBy(css = "[name='pickUpContact.firstName'] +  .inline-error-message")
    public WebElement firstNameErr;

    @FindBy(css = "[name='pickUpContact.lastName'] + .inline-error-message")
    public WebElement lastNameErr;

    @FindBy(css = "[name='pickUpContact.emailAddress'] + .inline-error-message")
    public WebElement emailErrMsg;

    @FindBy(css = "[name='pickUpContact.phoneNumber']")
    public WebElement mobileNumField;

    @FindBy(css = "[name='pickUpContact.phoneNumber'] + .inline-error-message")
    public WebElement mobileFieldErr;

    @FindBy(css = ".input-common.first-name input")
    public WebElement pickUpFn;

    @FindBy(css = ".input-common.last-name input")
    public WebElement pickUpln;

    @FindBy(css = ".input-common.email-address input")
    public WebElement pickUpEmail;

    @FindBy(css = ".input-common.phone-number input")
    public WebElement pickUpMobile;

    @FindBy(css = ".email-signup-note")
    public WebElement emailterms;

    @FindBy(css = ".input-checkbox-title")
    public WebElement signUpChkbox;

    @FindBy(css = ".accordion.coupon-accordion")
    public WebElement coupouns;

    @FindBy(css = ".accordion.order-summary-accordion")
    public WebElement orderSummary;

    @FindBy(css = "label[for*='hasAlternatePickup'] div")
    public WebElement alternatePersonChkbox;

    @FindBy(css = "label[for*='hasAlternatePickup'] div input")
    public WebElement alternatePersonChkboxInput;


    @FindBy(css = "label[for*='hasAlternatePickup'] div span")
    public WebElement alternatePersonChkboxTitle;

    @FindBy(css = "label[for*='hasAlternatePickup'] div p")
    public WebElement alternatePersonChkboxNote;

    @FindBy(css = "input[name='pickUpAlternate.firstName']")
    public WebElement alternatefirstNameFld;

    @FindBy(css = ".address-container")
    public WebElement pickUpUserInfo;


    @FindBy(css = ".checkout-review-pickup-note")
    public WebElement governmentId;

    @FindBy(css = ".pick-up-note")
    public WebElement alternatePickupNote;

    @FindBy(css = ".checkout-summary-edit.summary-title-pick-up-contact")
    public WebElement pickUpPersonEditLink;

   @FindBy(css = ".summary-title-pick-up-contact>h3")
    public WebElement pickUpTitle;

    @FindBy(css = ".button-primary.button-save-pickup")
    public WebElement savePickUpdetails;

    @FindBy(css = ".checkout-review-pickup-note")
    public WebElement pickupNote;

    @FindBy(css = "input[name='firstName']")
    public WebElement pickUpEditFn;

    @FindBy(css = "input[name='lastName']")
    public WebElement pickUpEditLn;

    @FindBy(css = "input[name='phoneNumber']")
    public WebElement pickUpEditPhone;

    @FindBy(css = "input[name='pickUpAlternate.lastName']")
    public WebElement alternateLastNameFld;

    @FindBy(css = "input[name='pickUpAlternate.emailAddress']")
    public WebElement alternateEmailFld;

    @FindBy(css = ".button-back")
    public WebElement backupBtn;

    @FindBy(xpath = "//button[text()='NEXT: Billing']")
    public WebElement nextBillingButton;

    public String nextBilling = ".button-primary.button-next-step";

    @FindBy(css = ".checkout-progress-bar")
    public WebElement checkoutProgressBar;

    @FindBy(css = ".checkout-progress-bar li")
    public List<WebElement> accordians;

    @FindBy(css = "ul.checkout-progress-bar li.active")
    public WebElement currentActiveAccordion;

    @FindBy(css = ".checkout-review-section .checkout-review-gift-wrapping")
    public WebElement giftWrappingSection;

    @FindBy(css = ".checkout-sms-order-status-signup div label div:nth-child(1) input")
    public WebElement smsCbInput;

    @FindBy(css = ".checkout-sms-order-status-signup div label div:nth-child(1)")
    public WebElement smsCb;

    @FindBy(css = "input[name*='smsUpdateNumber']")
    public WebElement smsNumberField;

    @FindBy(css = ".sms-disclaimer")
    public WebElement smsDisclaimer;

    @FindBy(css = ".privacy-policy-link")
    public WebElement smsPrivacyPolicy;

    @FindBy(css = ".sms-wrapping-fields .inline-error-message")
    public WebElement smsErrorMessage;

}
