package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.actions.OrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class BillingPageRepo extends OrderLedgerAndCOSummaryActions {


    @FindBy(xpath = "//label[contains(.,'Same as shipping')]/div")
    public WebElement sameAsShippingChkBox;

    @FindBy(xpath = "//label[contains(.,'Same as shipping')]/div/input")
    public WebElement sameAsShippingChkBoxinput;

    @FindBy(xpath = "//label[contains(.,'Same as shipping')]/div/input")
    public WebElement sameAsShippingChkBoxValue;

    @FindBy(css = "#email1")
    public WebElement emailAddress;


    @FindBy(css = ".text-under")
    public WebElement airmiles_Text;

    @FindBy(css = "#email1Verify")
    public WebElement confEmailAddr;

    @FindBy(css = "#payMethodId")
    public WebElement cardTypeDropDown;

    //@FindBy(css=".input-common.input-cc")//"input[name='billing.billing.cardNumber']")
    @FindBy(xpath = ".//div[@class=\"input-common input-cc\"]//input")
    public WebElement cardNumField;

    @FindBy(css = ".selected-card")
    public WebElement cardNumberDisplayed;


    //@FindBy(css=".input-common.input-cvv>input")
    //@FindBy(css = ".input-common.input-cvv")
    @FindBy(css = "[name='cvv']")
    public WebElement cvvFld;

    //    @FindBy(css=".input-common.input-cvv-express-checkout>input")
    @FindBy(css = "[name='cvv']")
    public WebElement cvvExpressFld;

    @FindBy(css=".error-box")
    public WebElement errorBox;

    //@FindBy(css=".select-common.select-exp-mm>select")
    @FindBy(xpath = ".//div[@class=\"select-common select-exp-mm\"]//select")
    public WebElement expMonDropDown;


    //@FindBy(css=".select-common.select-exp-yy>select")
    @FindBy(xpath = ".//div[@class=\"select-common select-exp-yy\"]//select")
    public WebElement expYrDopDown;

    @FindBy(css= ".select-common.select-exp-mm .selection.select-option-selected")
    public WebElement selectedExpMonth;

    @FindBy(xpath = ".//*[@class=\"select-common select-exp-yy\"]//span[@class=\"selection select-option-selected\"]")
    public WebElement selectedExpYear;


    @FindBy(xpath = "//button[text()='NEXT: Review']")
    public WebElement nextReviewButton;

    //@FindBy(css = ".ReactModalPortal .button-primary")
    @FindBy(xpath = ".//div[@data-funding-source ='paypal']")
    public WebElement proceedWithPaypalModalButton;

    @FindBy(css = "#confirmButtonTop")
    public WebElement continueButtonOnPaypalModal;

    //    @FindBy(css="input[value='paypal']")
    public String paypalRadioBox = "input[value='paypal']";

    @FindBy(css = "input[value='paypal']")
    public WebElement paypalRadioButton;

    /*@FindBy(css="input[value='paypal']")*/
    @FindBy(css = ".paypal-method .input-radio")
    public WebElement paypalRadioBoxElement;

//    @FindBy(css = ".button-primary.button-next-step.button-proceed-with-paypal")
    @FindBy(css = ".paypal-button-container div")
    public WebElement proceedWithPaPalButton;

    @FindBy(css=".button-container button")
    public WebElement submOrderButton;

    @FindBy(css = "iframe[title='ppbutton']")
    public WebElement continuePayPalFrame;

    @FindBy(css = ".checkout-summary-edit")
    public WebElement editLinkOnCard;

    @FindBy(css = ".label-radio.input-radio.credit-card-method div")
    public WebElement creditCardRadioBox;

    @FindBy(css = ".label-radio.input-radio.credit-card-method div input")
    public WebElement creditCardRadioBoxinput;

    @FindBy(name = "couponCode")
    public WebElement couponCodeFld;

    @FindBy(css = ".button-apply-coupons")
    public WebElement applyCodeButton;

    @FindBy(css = ".savings-total")
    public WebElement promotionsLabel;

    @FindBy(css = ".total-row.discount")
    public WebElement appliedRowDiscElement;

    @FindBy(xpath = ".//*[@id='WC_ShipmentDisplay_div_18']/div[2]/div/div")
    public List<WebElement> applyLinksForLoyaltyRewards;

    @FindBy(css = ".label-error-promo")
    public WebElement promoNotValid;

    @FindBy(css = "#promotion_")
    public WebElement removePromo1;

    @FindBy(css = ".toggle-miles")
    public WebElement expandAirMilesSection;

    @FindBy(xpath = "//div[@data-id='PlaceCard']//a[@class='show_hide']")
    public WebElement expandPlaceCardSection;

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

    @FindBy(css = ".show_hide.unbound_toggle")
    public WebElement expandGiftCardSection;

    @FindBy(xpath = "//label[contains(.,'The gift card number you entered is not a valid number, please re-enter a valid gift card number.')]")
    public WebElement errInvalidGiftCard;

    @FindBy(xpath = "//label[contains(.,'The PIN number is required for this gift card. Please enter a valid PIN number. Please see the instructions on the right hand side to help find your PIN.')]")
    public WebElement errNoPinEntered;

    @FindBy(xpath = "//label[contains(.,'The PIN number you entered is not a valid number, please re-enter a valid PIN number. Please see the instructions on the right hand side to help find your PIN. If you continue to experience a problem, please call our customer service department at 1-877-PLACE USA for assistance.')]")
    public WebElement errInvalidPinEntered;

    @FindBy(xpath = "//label[contains(.,'You have entered the same gift card number again. Please enter a different gift card number.')]")
    public WebElement errRepeatedGiftCard;

    @FindBy(css = "#removeGiftCard1")
    public WebElement removeGiftCard;

    @FindBy(xpath = "//div[@class='ui-dialog-buttonset']/button[1]")
    public WebElement useAsEnteredButton;


    public WebElement applyLinkForLoyaltyRewards(int i) {
        return getDriver().findElement(By.xpath(".//*[@id='WC_ShipmentDisplay_div_18']/div[2]/div/div[" + i + "]/span/a"));
    }

    public WebElement couponCodeForLoyaltyRewards(int i) {
        return getDriver().findElement(By.xpath(".//*[@id='WC_ShipmentDisplay_div_18']/div[2]/div/div[" + i + "]"));
    }

    @FindBy(xpath = ".//*[@id='WC_ShipmentDisplay_div_18']/div[3]/div/div/span/a")
    public List<WebElement> discountAppliedElements;


    @FindBy(xpath = ".//*[@id='WC_ShipmentDisplay_div_18']/div[3]/div/div/span/a")
    public List<WebElement> removeLinkForDiscAppliedElements;

    public WebElement removeLinkForAppliedDiscByPosition(int i) {
        return getDriver().findElement(By.xpath(".//*[@id='WC_ShipmentDisplay_div_18']/div[3]/div/div/span[" + i + "]/a"));
    }

    public WebElement discountAppliedElementByPosition(int i) {
        return getDriver().findElement(By.xpath(".//*[@id='WC_ShipmentDisplay_div_18']/div[3]/div/div[" + i + "]"));
    }

    @FindBy(css = ".discount-applied")
    public WebElement discountAppliedElementsBox;

    @FindBy(css = ".discount-applied .total-row.discount")
    public List<WebElement> discountRowAppliedElements;

    public WebElement discountAppliedByCouponType(String couponCode) {
        return getDriver().findElement(By.xpath(".//*[@id='WC_ShipmentDisplay_div_18']/div[3]/div[contains(.,'" + couponCode + "')]"));
    }

    //    @FindBy(css="#firstName")
    @FindBy(css = ".input-common.input-first-name input")//"billing.address.firstName")
    public WebElement firstName;

    //    @FindBy(css="#lastName")
    @FindBy(css = ".input-common.input-last-name input")//"billing.address.lastName")
    public WebElement lastName;

    //    @FindBy(css="#address1")
    @FindBy(css = ".input-common.input-address-line1 input")//"billing.address.addressLine1")
    public WebElement addressLine1;

    //    @FindBy(css="#address2")
    @FindBy(css = ".input-common.input-address-line2 input")//"billing.address.addressLine2")
    public WebElement addressLine2;

    //    @FindBy(css="#city")
    @FindBy(css = ".input-common.input-city input")//"billing.address.city")
    public WebElement cityFld;

    //    @FindBy(css="#state")
    @FindBy(css = ".select-common.select-state select")
    public WebElement stateDropDown;

    @FindBy(css = ".save-to-account span.input-checkbox-title")
    public WebElement saveToAccountCheckBox;

    @FindBy(css = ".save-to-account div")
    public WebElement saveToAccountCheckBoxNew;

    @FindBy(css = ".save-to-account div input")
    public WebElement saveToAccountCheckBoxNewinput;

    @FindBy(css = ".//input[@name='setAsDefault']")
    public WebElement defaultPaymentMethod;

    //@FindBy(xpath = ".//div[@class=\"label-checkbox input-checkbox set-as-default\"]//div[@class=\"input-checkbox-icon-unchecked\"]")
    @FindBy(xpath = ".//div[@class=\"label-checkbox input-checkbox set-as-default\"]//div[@class=\"input-checkbox-disabled input-checkbox-icon-unchecked\"]")
    public WebElement defaultPaymentUnchecked;

    //    @FindBy(css="#country")
    @FindBy(css = ".select-common.select-country select")
    public WebElement countryDropDown;

    //    @FindBy(css="#addressField3")
    @FindBy(css = ".input-common.input-zip-code input")//"billing.address.zipCode")
    public WebElement zipCode;

    //    @FindBy(css="#phone1")
    @FindBy(css = "#phone1")
    public WebElement phoneNumber;

    @FindBy(css = "//div[@data-id='PlaceCard'][@class='payment-row']//span")
    public WebElement placeCardLbl;

    @FindBy(css = "#place_card_number[name='place_card_number']")
    public WebElement placeCard;

    @FindBy(css = ".pay-paypal.row")
    public WebElement paypalRow;


    @FindBy(xpath = "//*[@id='billingPageNext'][contains(.,'Proceed With Paypal')]")
    public WebElement payWithPayPal;

    @FindBy(xpath = "//label[contains(.,'You must select a payment method.')]")
    public WebElement errSelectCard;

    @FindBy(xpath = "//label[contains(.,'The credit card account number cannot be empty.')]")
    public WebElement errCardNumber;

    @FindBy(xpath = "//label[contains(.,'CVV2 field must be numeric.')]")
    public WebElement errCVV2Field;

    @FindBy(xpath = "//label[contains(.,'Card number does not match with card type. Please select correct credit card type and try again.')]")
    public WebElement errCardTypeMismatch;

    @FindBy(xpath = "//label[contains(.,'Invalid expiration year and month')]")
    public WebElement errOldExpiryDate;

    @FindBy(xpath = "//label[contains(.,'Card verification number must be a 4-digit number without any spaces.')]")
    public WebElement errNoAmexCVV2Field;

    @FindBy(xpath = "//label[contains(.,'Please enter a valid')]")
    public WebElement errPlaceCard;

    @FindBy(xpath = "//span[contains(.,'The credit card number is not valid. Type the number of the credit card in the Credit card number field and try again.')]")
    public WebElement errInvalidCard;

    /*@FindBy(xpath = "//label[contains(.,'Please enter first name.')]")*/
    @FindBy(css = ".input-common.input-first-name div.inline-error-message")
    public WebElement errNoFirstName;

    /*@FindBy(xpath = "//label[contains(.,'Please enter last name.')]")*/
    @FindBy(css = ".input-common.input-last-name div.inline-error-message")
    public WebElement errNoLastName;

    /*@FindBy(xpath = "//label[contains(.,'Please enter a valid street address.')]")*/
    @FindBy(css = ".input-common.input-address-line1 div.inline-error-message")
    public WebElement errNoAddr1;

    @FindBy(css = ".input-common.input-address-line2 div.inline-error-message")
    public WebElement errNoAddr2;

    /*@FindBy(xpath = "//label[contains(.,'Please enter a valid city.')]")*/
    @FindBy(css = ".input-common.input-city div.inline-error-message")
    public WebElement errNoCity;

    /*@FindBy(xpath = "//label[contains(.,'Please select a state/province.')]")*/
    @FindBy(css = ".select-common.select-state div.inline-error-message")
    public WebElement errNoState;

    /*@FindBy(xpath = "//label[contains(.,'Please enter a valid zip/postal code.')]")*/
    @FindBy(css = ".input-common.input-zip-code div.inline-error-message")
    public WebElement errNoZipPO;

    @FindBy(xpath = "//label[contains(.,'Please enter a valid phone number.')]")
    public WebElement errNoPhoneNumber;

    @FindBy(xpath = "//label[contains(.,'Oops! We need a valid email address to send you updates.')]")
    public WebElement errBVEmail;

    @FindBy(xpath = "//input[@id='email1']/ancestor::div[1]//label[contains(.,'Email format is invalid')]")
    public WebElement errNoEmail;

    @FindBy(xpath = "//input[@id='email1Verify']/ancestor::div[1]//label[contains(.,'Email format is invalid')]")
    public WebElement confEmailAddrErr;


    @FindBy(xpath = "//*[@id='order_total']/div[contains(.,'shipping tax')]/span[2]")
    public WebElement payment_shipping_Tax;


    @FindBy(css = ".sku-name")
    public WebElement payment_ProductName;

    @FindBy(css = ".sku-size")
    public WebElement payment_ProductSize;

    @FindBy(css = ".sku-color")
    public WebElement payment_ProductColor;

    @FindBy(css = ".sku-quantity")
    public WebElement payment_ProductQuantity;

    @FindBy(css = ".price")
    public WebElement payment_ProductPrice;

    @FindBy(css = ".sale-price")
    public WebElement payment_ProductSalePrice;

    @FindBy(css = ".payment-row.no-cursor>span")
    public WebElement payWithGiftCardElement;


    @FindBy(xpath = "//button[text()='Return to Shipping']")
    public WebElement retToShippingLink;

    @FindBy(css = "#recaptcha-anchor-label")
    public WebElement reCaptchaBlock;

    @FindBy(xpath = ".//*[@id='gift-card-div']/p[2]")
    public WebElement giftCardMsg;

    public WebElement errorMessageByFieldError(String fldErrMsg) {
        return getDriver().findElement(By.xpath("//label[contains(@class,'label-error') and contains(@style,'block')][contains(.,'" + fldErrMsg + "')]"));
    }

    @FindBy(css = "button.button-login")
    public WebElement eSpotLoginLink;

    @FindBy(css = "button.button-register")
    public WebElement eSpotCreateAccountLink;

    @FindBy(css = ".button-modal-close")
    public WebElement closeOverlayBtn;

    @FindBy(xpath = "//li[@class='items-total']//strong")
    public WebElement itemTotal;

    @FindBy(xpath = "//li[@class='items-total']")
    public WebElement itemsLbl;

    @FindBy(xpath = ".//li[@class='shipping-total']//strong")
    public WebElement shippingTot;

    @FindBy(xpath = ".//li[@class='savings-total']//strong")
    public WebElement promotionsTot;

    @FindBy(xpath = ".//li[@class='tax-total']//strong")
    public WebElement taxTotal;

    @FindBy(xpath = ".//li[@class='balance-total']//strong")
    public WebElement estimatedTot;

    @FindBy(css = ".return-to-bag-link")
    public WebElement returnBagLink;

    @FindBy(xpath = ".//div[@class='navigation-confirmation expanded']//span")
    public WebElement overlayContent;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnBagBtn;

    @FindBy(css = ".button-previous-step")
    public WebElement rtnToShippingpageLnk;

    @FindBy(xpath = ".//div[@class='ReactModal__Content ReactModal__Content--after-open overlay-container']//button[.='Change Payment Method']")
    public WebElement changePaymentBtn;

    //@FindBy(xpath = ".//div[@class='ReactModal__Content ReactModal__Content--after-open overlay-container']//button[.='Continue With Paypal']")
    @FindBy(xpath = ".paypal-checkout-continue a")
    public WebElement continuePaypalBtn;

    @FindBy(css = ".button-primary.button-next-step.button-proceed-with-paypal")
    public WebElement proceedPaypalBtn;

    @FindBy(css = ".custom-select-button.dropdown-address-book-button-closed")
    public WebElement selectCCDropdown;

    @FindBy(css = ".card-container")
    public WebElement paymentMethod;

    @FindBy(css = ".card-container img")
    public WebElement paymentMethodImg;

    @FindBy(css = ".address-container")
    public WebElement billingAddress;

    @FindBy(css = ".card-suffix")
    public WebElement creditCardField;

    @FindBy(css = ".button-add-new.button-add-new-card")
    public WebElement addNewCC;

    //    @FindBy(css = ".select-or-new-billing-address>div>div")
    //@FindBy(css = ".dropdown-address-book.dropdown-address-book")
    //@FindBy(css = ".select-address-billing .dropdown-address-book.dropdown-address-book")
    //@FindBy(css = ".billing-address-section .custom-select-button.dropdown-address-book-button-closed")
    @FindBy(css = ".custom-select-button.dropdown-address-book-button-closed")
    public WebElement dropDownLink;

    @FindBy(css = ".button-add-new.button-add-new-address")
    public WebElement addNewAddress;

    @FindBy(css = ".button-add-new.button-add-new-card")
    public WebElement addNewCard;

    @FindBy(xpath = "(.//ul[@class=\"item-list-common dropdown-address-book-items-list\"]/li)[1]")
    public WebElement firstAddressFromDropDown;

    @FindBy(css = ".input-common.input-first-name>input")
    public WebElement firstNameFld;

    @FindBy(css = ".giftcard-apply-summary>button")
    public WebElement applyGCButton;

    @FindBy(css = ".giftcard-aplied-summary>button")
    public WebElement removeAppliedGCButton;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesBill;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesNumb;

    @FindBy(css = ".button-save-address")
    public WebElement saveButton;

    @FindBy(css = ".button-giftcard-apply")
    public WebElement applyButtonOnGC;

    @FindBy(css = ".button-giftcard-remove")
    public WebElement removeButtonOnGC;

    @FindBy(css = ".rc-anchor.rc-anchor-normal.rc-anchor-light")
    public WebElement captch;

    @FindBy(css = ".label-checkbox.input-checkbox.save-giftcard-balance-checkbox div")
    public WebElement saveGiftCardBalance;

    @FindBy(css = "[name=\"cardNumber\"]")
    public WebElement cardNumber;

    @FindBy(xpath = ".//div[@class=\"label-checkbox input-checkbox save-to-account\"]//div[@class=\"input-checkbox-icon-unchecked\"]")
    public WebElement saveToAddressUnCheck;

    @FindBy(css = ".name")
    public WebElement flNameBillAddress;

    @FindBy(xpath = "//span[@class='address']")
    public WebElement billAddrDisplay;

    @FindBy(xpath = ".//div[@class='checkout-progress-indicator']/ul/li/*[text()='Shipping']")
    public WebElement shippingAccordian;

    @FindBy(css = ".checkout-progress-bar .active span:nth-child(1)")
    public WebElement billingAccordianActive;

    @FindBy(css = ".input-checkbox-icon-unchecked")
    public WebElement sameAsShippingUnchecked;

    @FindBy(css = ".gift-card-toggle")
    public WebElement addNewGC;

    @FindBy(xpath = ".//div[@class=\"gift-card-add-fields\"]")
    public WebElement gcContent;

    @FindBy(css = ".input-common.gift-card-number input")
    public WebElement cardNumberField;

    @FindBy(css = ".input-common.gift-card-pin input")
    public WebElement pinNumberField;

    // @FindBy(css = ".rc-anchor.rc-anchor-normal.rc-anchor-light")
    //public WebElement recaptchaBox;

    @FindBy(xpath = ".//button[@class=\"gift-card-cancel\"]")
    public WebElement cancelGCButoon;

    @FindBy(xpath = ".//button[@class=\"gift-card-apply\"]")
    public WebElement applyGCButton1;

    @FindBy(css = ".balance")
    public WebElement remainingBalOnGC;

    @FindBy(css = ".notice-gift-card-applied")
    public WebElement gcAppliedNotice;

    @FindBy(xpath = "(.//div[@class=\"message-paypal\"]/p)[1]")
    public WebElement payPalText1;

    @FindBy(xpath = "(.//div[@class=\"message-paypal\"]/p)[2]")
    public WebElement payPalText2;

    @FindBy(xpath="//*[text()='Shipping']/..")
    public WebElement shippingProgressBar;

    @FindBy(xpath="//*[text()='Billing']/..")
    public WebElement billingProgressBar;

    @FindBy(xpath="//*[text()='Pickup']/..")
    public WebElement pickupProgressBar;

    @FindBy(xpath = "//*[text()='Review']/..")
    public WebElement reviewProgressBar;

    @FindBy(tagName = "h2")
    public WebElement checkoutTitle;

    @FindBy(css = ".btn.full.ng-binding")
    public WebElement payPalLoginButton;

    @FindBy(css = ".notice-gift-card-applied")
    public WebElement appliedGCCopy;
}
