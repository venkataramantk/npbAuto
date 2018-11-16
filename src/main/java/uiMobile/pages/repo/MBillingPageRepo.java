package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.pages.actions.MobileOrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by JKotha on 23/10/2017.
 */
public class MBillingPageRepo extends MobileOrderLedgerAndCOSummaryActions {

    @FindBy(xpath = "//label[contains(.,'Same as shipping')]/div")
    public WebElement sameAsShippingChkBox;

    @FindBy(xpath = "//label[contains(.,'Same as shipping')]/div//input")
    public WebElement sameAsShippingChkBoxinput;

    @FindBy(css = ".input-common.input-cc input")
    public WebElement cardNumField;

    @FindBy(xpath = "//*[contains(@id,'cvv_')]")
    public WebElement cvvFld;

    public WebElement cardNo(String cardNo) {
        return getDriver().findElement(By.xpath("//span[contains(.,'" + cardNo + "')]"));
    }

    @FindBy(css = "[name='cvv']")
    public WebElement cvvExpressFld;

    @FindBy(css = ".select-common.select-exp-mm select")
    public WebElement expMonDropDown;

    @FindBy(css = ".select-common.select-exp-yy select")
    public WebElement expYrDopDown;

    @FindBy(xpath = "//button[text()='NEXT: Review']")
    public WebElement nextReviewButton;

    @FindBy(css = ".button-giftcard-apply")
    public WebElement applyButtonOnGC;

    @FindBy(css = ".giftcards-total>strong")
    public WebElement giftCardsTotal;

    @FindBy(css = ".button-giftcard-remove")
    public WebElement removeButtonOnGC;

    @FindBy(css = ".balance")
    public WebElement remainingBalOnGC;

    @FindBy(css = ".notice-gift-card-applied")
    public WebElement gcAppliedNotice;

    @FindBy(css = "li.completed button")
    public WebElement ProgressBarshippingBtn;

    @FindBy(css = ".button-previous-step")
    public WebElement returnToPickup;

   // @FindBy(css = ".payment-method-form-container .paypal-button.paypal-button-context-iframe.paypal-button-label-paypal")//".paypal-method .input-radio")
    @FindBy(css = ".payment-method-form-container .paypal-button.paypal-button-context-iframe.paypal-button-label-paypal")//".paypal-method .input-radio")
    public WebElement paypalRadioBoxElement;

    @FindBy(css = ".button-primary.button-next-step.button-proceed-with-paypal")
    public WebElement proceedWithPaPalButton;

    @FindBy(css = ".button-back")
    public WebElement backButton;

    @FindBy(css = ".message-paypal")
    public WebElement messagePaypal;

    @FindBy(css = "button.checkout-summary-edit")
    public WebElement editLinkOnCard;

    @FindBy(css = ".label-radio.input-radio.credit-card-method")
    public WebElement creditCardRadioBox;

    @FindBy(css = "#giftCardAccount")
    public WebElement giftCardNumber;

    @FindBy(css = "#giftCardPin")
    public WebElement giftCardPin;

    @FindBy(css = "#checkGiftCardBalance")
    public WebElement checkGiftCardBalanace;

    @FindBy(css = ".check-balance>h1")
    public WebElement availableBalance;

    @FindBy(css = "#balance")
    public WebElement balanceElement;

    @FindBy(css = ".check-balance .button-blue")
    public WebElement applyToOrder;

    @FindBy(css = ".label-error")
    public WebElement giftCardError;

    @FindBy(css = ".input-common.input-first-name input")
    public WebElement firstName;

    @FindBy(css = ".input-common.input-last-name input")
    public WebElement lastName;

    @FindBy(css = ".input-common.input-address-line1 input")
    public WebElement addressLine1;

    @FindBy(css = ".input-common.input-address-line2 input")
    public WebElement addressLine2Fld;

    @FindBy(css = ".input-common.input-city input")
    public WebElement cityFld;

    @FindBy(css = ".input-common.input-phone input")
    public WebElement phNumFld;

    @FindBy(css = ".select-common.select-state select")
    public WebElement stateDropDown;

    @FindBy(css = ".save-to-account div")
    public WebElement saveToAccountCheckBox;

    @FindBy(css = ".save-to-account div input")
    public WebElement saveToAccountCheckBoxInput;

    @FindBy(css=".label-checkbox.input-checkbox.save-to-account div")
    public WebElement saveCardToMyAccount;

    @FindBy(css=".label-checkbox.input-checkbox.save-to-account div input")
    public WebElement saveCardToMyAccountInput;

    @FindBy(css = ".input-common.input-zip-code input")
    public WebElement zipCode;

    @FindBy(css = ".payment-row.no-cursor>span")
    public WebElement payWithGiftCardElement;

    @FindBy(xpath = "//li[@class='items-total']")
    public WebElement itemsLbl;

    @FindBy(xpath = ".//li[@class='balance-total']//strong")
    public WebElement estimatedTot;

    @FindBy(css = ".button-add-card")
    public WebElement addACard;

    @FindBy(css = ".modal-title.modal-only-title")
    public WebElement addCardPage;

    @FindBy(css = ".button-select-shipping-address.select")
    public WebElement saveBtn;
    
    @FindBy(css = ".button-edit")
    public List<WebElement> editLinkList;

   // @FindBy(xpath = "//button[@type='button'][contains(text(),'Shipping')]")
    @FindBy(xpath = "//html//li[1]/button[1][contains(text(),'Shipping')]|//html//li[2]/button[1][contains(text(),'Shipping')]")
    public WebElement shippingAcc;

    @FindBy(css = ".inline-error-message")
    public WebElement cvvInlineError;

    @FindBy(css = ".return-to-bag-link")
    public WebElement returnBagLink;
    
    @FindBy(css = "img.credit-card-image")
    public WebElement cardImg;
    
    @FindBy(css = ".button-exit-checkout")
    public WebElement backBtn;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnBagBtn;
    
    @FindBy(css = ".gift-card-toggle")
    public WebElement addNewGC;
    
    @FindBy(xpath = ".//div[@class=\"gift-card-add-fields\"]")
    public WebElement gcContent;

    @FindBy(css = ".input-common.gift-card-number input")
    public WebElement cardNumberField;

    @FindBy(css = ".input-common.gift-card-pin input")
    public WebElement pinNumberField;
    
    @FindBy(xpath = ".//button[@class=\"gift-card-apply\"]")
    public WebElement applyGCButton1;

    @FindBy(css=".checkout-progress-bar")
    public WebElement checkoutProgressBar;

    @FindBy(css=".fieldset-creditcard-editable")
    public WebElement ccFieldSection;

    @FindBy(css=".checkout-billing-section")
    public WebElement billingSection;

    @FindBy(css=".checkout-progress-bar li")
    public List<WebElement> accordians;

    @FindBy(css=".button-select-this")
    public List<WebElement> selectThisBtnOnEditModal;

    @FindBy(css=".checkout-billing-section .payment-method-container")
    public WebElement paymentMethodContainer;

    @FindBy(css=".checkout-billing-section .address-container")
    public WebElement billingAddressContainer;

    @FindBy(xpath="//h2[contains(., \"Select Card\")]")
    public WebElement selectCardOverlayTitle;

    @FindBy(css = "div[aria-label='Credit Card Modal'] button.button-add-new")
    public WebElement addNewCCFromOverlay;

    @FindBy(css = "form.edit-payment-method-container")
    public WebElement addPaymentMethodFromOverlayContainer;

    @FindBy(css = "button[type='submit']")
    public WebElement saveBtnOnOverlay;

    @FindBy(css = ".card-suffix")
    public WebElement cardSuffix;

    @FindBy(css = "div[aria-label='Credit Card Modal'] span.card-suffix")
    public List<WebElement> cardSuffixList;

    @FindBy(css = "div[aria-label='Edit Payment Method']")
    public WebElement editPaymentMethodContainer;

    @FindBy(css = "div.address-billing-view div.address-container .name")
    public WebElement billingAddnameReadOnly;

    @FindBy(css = "div.address-billing-view div.address-container .address")
    public WebElement billingAddressReadOnly;

    @FindBy(css = "div.address-billing-view div.address-container .address-additional")
    public WebElement phoneNumReadOnly;

    @FindBy(css = "div.address-billing-view button.button-add-address")
    public WebElement addNewBillingaddBtn;

    @FindBy(css = "div.address-billing-view button.button-edit-address")
    public WebElement editBillingaddBtn;

    @FindBy(css = "div[aria-label='Add New Address']")
    public WebElement addNewAddressContainer;

    @FindBy(css="#recaptcha-anchor")
    public WebElement reCaptchaChkbx;

    @FindBy(xpath="label[id='recaptcha-anchor-label'] span")
    public WebElement reCaptchatext;

    @FindBy(xpath="//h2[@class='title-billing-address']")
    public WebElement billingAddressHeading;

    @FindBy(css = "iframe[src*=\"https://www.google.com/recaptcha/api2\"]")
    public WebElement captchFrameLoc;

    @FindBy(xpath = "//fieldset[@class='fieldset-address-editable']/following-sibling::div/button[contains(@class, 'button-select-shipping-address')]")
    public WebElement selectThisBillingAddBtn;

    @FindBy(xpath = "//h3[text()='Verify Your Address']")
    public WebElement verifyAddressPopup;

    @FindBy(css = ".address-we-suggest")
    public WebElement weSuggestAddress;

    @FindBy(css = ".button-continue")
    public WebElement continueBtn;

    @FindBy(css = ".address-you-entered .checkbox-combo.address-options")
    public WebElement yourEnteredAddBtn;

    @FindBy(xpath = "//button[@type='button'][contains(text(),'Change Payment Method')]")
    public WebElement changePaymentMethodBtn;

    //@FindBy(xpath = "//button[@type='button'][contains(text(),'Continue With Paypal')]")
    @FindBy(xpath = "//button[@type='button'][contains(text(),'Keep Current Selection')]") //Live2 locator, text changed to keep current selection
    public WebElement continuePayPalBtn;

    @FindBy(css = ".button-giftcard-remove")
    public List<WebElement> removeButtonGC;

    @FindBy(css = ".giftcard-aplied-summary")
    public WebElement giftCardSection;

   // @FindBy(css=".xcomponent-component-frame.xcomponent-visible")
    @FindBy(css=".xcomponent-component-frame.xcomponent-visible")
    public WebElement payPalFrame;

    @FindBy(css="[aria-label='paypal']")//"paypal-animation-content")
    public WebElement payPalBtn;

    @FindBy(css = ".paypal-button-logo.paypal-button-logo-pp.paypal-button-logo-blue")
    public WebElement paypalCheckoutOnPaypalModal;

  /*  @FindBy(css = ".button-proceed-with-paypal")
    public WebElement ProceedWithPaPalBtn;*/

    @FindBy(css = ".button-modal-close")
    public WebElement closeModalChangePayment;

    @FindBy(css = ".address-shipping-view .address-details")
    public WebElement billingAddSection;

    @FindBy(css = ".label-checkbox.input-checkbox.same-as-shipping-checkbox")
    public WebElement sameAsShippingSection;

   @FindBy(css = ".paypal-method.payment-method-field.payment-box-radio-button label div input")
    public WebElement payPalRadioBtn;
   
   @FindBy(css = "li.giftcard-aplied-summary")
   public List<WebElement> appliedGiftCards;

   @FindBy(css = ".title-checkout")
   public WebElement expressCheckout;

    @FindBy(css = "li.completed button")
    public List<WebElement> progressBarBtn;

    @FindBy(xpath = ".//li[@class='balance-total']")
    public WebElement balanceLineItem;
    
    @FindBy(css = ".new-credit-card-container .fieldset-creditcard-editable div:not(.ghost-error-container)")
    public List<WebElement> addNewCardList;
    
    @FindBy(css = "div.payment-method-select-overlay div[aria-label='Add Payment Method']")
    public WebElement addPaymentMethodOverlay;
    
    @FindBy(css = "ul.checkout-progress-bar li.active")
    public WebElement currentActiveAccordion;

    @FindBy(css=".container-button .paypal-button.paypal-button-context-iframe.paypal-button-label-paypal")
    public WebElement payPalContinueBtn;

}
