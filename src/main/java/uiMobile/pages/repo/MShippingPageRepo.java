package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.pages.actions.MobileOrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by JKotha on 25/10/2017.
 */

public class MShippingPageRepo extends MobileOrderLedgerAndCOSummaryActions {

    @FindBy(css = ".container-shipping-section-title")
    public WebElement sectionTitle;

    @FindBy(css = ".checkout-content .address-shipping-view .button-edit-address")
    public WebElement editLink_ShipAddress;

    @FindBy(css = ".button-edit")
    public WebElement editLink;

    @FindBy(css = ".giftcards-total>strong")
    public WebElement giftCardsTotal;

    public WebElement getInlineErrors(String field) {
        return getDriver().findElement(By.xpath("//div[text()='" + field + "']/../following-sibling::div//i"));
    }

    public WebElement selectThisAddress(String add1) {
        return getDriver().findElement(By.xpath("//span//text()[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + add1.toLowerCase() + "')]/ancestor::li/div[@class='button-container']/button[1]"));
    }

    public WebElement getAlterNatefieldsErrors(String field) {
        return getDriver().findElement(By.xpath("//div[@class='pick-up-alternate-input']//div[text()='" + field + "']/../following-sibling::div/div"));
    }

    @FindBy(css = ".input-common.input-first-name input")
    public WebElement firstNameFld;

    @FindBy(css = ".input-common.input-last-name input")
    public WebElement lastNameFld;

    @FindBy(css = ".input-common.input-address-line1 input")
    public WebElement addressLine1Fld;

    @FindBy(css = ".input-common.input-address-line2 input")
    public WebElement addressLine2Fld;

    @FindBy(css = ".input-common.input-city input")
    public WebElement cityFld;

    @FindBy(css = ".select-common.select-state select")
    public WebElement stateDropDown;

    @FindBy(css = ".select-common.select-state")
    public WebElement stateDropd;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-address-book div")
    public WebElement saveToAddressBookCheckBox;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-address-book div input")
    public WebElement saveToAddressBookCheckBoxInput;

    @FindBy(css = ".input-common.input-zip-code input")
    public WebElement zipPostalCodeFld;

    @FindBy(css = ".input-common.input-zip-code div")
    public WebElement zipcodeLabel;

    @FindBy(css = ".input-common.input-phone input")
    public WebElement phNumFld;

    @FindBy(css = ".button-select-shipping-address.select")
    public WebElement selectThisShippingAddButton;

    @FindBy(css = ".input-common.input-email input")
    public WebElement emailAddressFld;

    @FindBy(name = "couponCode")
    public WebElement couponCodeField;

    @FindBy(css = ".accordion.coupon-accordion h4")
    public WebElement couponsSection;

    @FindBy(css = "#singleShipmentShippingMode")
    public WebElement shippingMethodDropDown;

    @FindBy(css = ".select-common.select-country>select")
    public WebElement shipToCountryDropdown;

    @FindBy(css = ".select-common.select-country")
    public WebElement shipToCountryDrop;

    @FindBy(css = ".input-subtitle")
    public WebElement signUpchkbox;

    //Int locators
    @FindBy(id = "shipping-first-name")
    public WebElement ifirstNameFld;

    @FindBy(id = "shipping-last-name")
    public WebElement ilastNameFld;

    @FindBy(id = "shipping-address-line1")
    public WebElement iaddressLine1Fld;

    @FindBy(css = ".checkbox-get-email label div input")
    public WebElement signupEmail_ChkInput;

    @FindBy(css = ".checkbox-get-email label div")
    public WebElement signupEmail_ChkBox;

    @FindBy(id = "shipping-city")
    public WebElement icityFld;

    @FindBy(id = "shipping-postal-code")
    public WebElement iPostalCodeFld;

    @FindBy(css = "shipping-tel")
    public WebElement iphNumFld;

    @FindBy(css = "shipping-email")
    public WebElement iemailAddressFld;

    @FindBy(id = "continue-btn")
    public WebElement iContinueBtn;

    @FindBy(id = "cc-exp-date")
    public WebElement iExpDate;

    @FindBy(id = "cc-num")
    public WebElement iCCnumber;

    @FindBy(id = "cc-sec-num")
    public WebElement iCvvCode;

    @FindBy(id = "submit-order-btn")
    public WebElement placeoder;

    public WebElement shippingMethodRadioButtonByValue(String value) {
        return getDriver().findElement(By.xpath("//div//span[contains(text(),'" + value + "')]"));
    }

    public WebElement shippingMethodRadioButtonByPos(String pos) {
        return getDriver().findElement(By.xpath("//div[@class='shipping-methods shipping-methods-form']/fieldset/label[" + pos + "]/div"));
    }

    public WebElement shippingMethod(String shippingMethod) {
        return getDriver().findElement(By.xpath("//span[contains(text()," + shippingMethod + " )]"));
    }

    @FindBy(css = ".accordion.order-summary-accordion")
    public WebElement orderSummarySection;

    @FindBy(xpath = "//div[@class='shipping-methods shipping-methods-form shipping-methods-input-box']/fieldset/label/span")
    public List<WebElement> availableMethods;

    @FindBy(css = ".estimated-shipping-rate")
    public WebElement estimatedDays;

    @FindBy(css = "#shippingBillingPageNext")
    public WebElement continueCheckoutButton;

    @FindBy(xpath = "//button[text()='NEXT: Billing']")
    public WebElement nextBillingButton;

    public String nextBilling = ".button-primary.button-next-step";

    @FindBy(xpath = "//button[text()='NEXT: Shipping']")
    public WebElement nextShippingButton;

    @FindBy(css = ".address-verification button[type='submit']")
    public WebElement addressVerificationContinue;

    @FindBy(css = ".button-secondary")
    public WebElement getAddressVerificationEdit;

    @FindBy(css = ".button-save-address")
    public WebElement saveButton;

    @FindBy(xpath = "//h3[text()='Verify Your Address']")
    public WebElement verifyAddressPopup;

    @FindBy(css = ".address-we-suggest")
    public WebElement weSuggestAddress;

    @FindBy(css = ".button-continue")
    public WebElement continueBtn;
    
    @FindBy(css = "div.label-checkbox.input-checkbox.add-gift-wrapping div") //"div.input-checkbox-title"
    public WebElement giftService_checkbox;
    
    @FindBy(css = "div.label-checkbox.input-checkbox.add-gift-wrapping div input") //"div.input-checkbox-title"
    public WebElement giftService_checkboxInput;

    @FindBy(css = ".gift-wapping-details")
    public WebElement giftwrapDetail;

    @FindBy(css = "div.custom-select-button.dropdown-gift-wrapping-button")
    public WebElement GiftReceiptMessage;

    public WebElement giftserviceOptions(String option) {
        return getDriver().findElement(By.xpath("//p//strong[contains(text(),'" + option + "')]"));
    }

    @FindBy(css = ".gift-wrapping-container")
    public List<WebElement> giftwrapContainers;

    @FindBy(css = "textarea[name='giftWrap.message']")//"#labeled-textarea_0")
    public WebElement receiptMessageBox;

    @FindBy(css = ".input-common.input-first-name div.inline-error-message")
    public WebElement err_FirstName;

    @FindBy(css = ".input-common.input-last-name div.inline-error-message")
    public WebElement err_LastName;

    @FindBy(css = ".input-common.input-address-line1 div.inline-error-message")
    public WebElement err_AddressLine1;

    @FindBy(css = ".input-common.input-address-line2 div.inline-error-message")
    public WebElement err_AddressLine2;

    @FindBy(css = ".input-common.input-city div.inline-error-message")
    public WebElement err_City;

    @FindBy(css = ".select-common.select-state div.inline-error-message")
    public WebElement err_State;

    @FindBy(css = ".input-common.input-phone div.inline-error-message")
    public WebElement err_PhoneNumber;

    @FindBy(css = ".input-common.input-email div.inline-error-message")
    public WebElement err_Email;

    @FindBy(xpath = "//label[contains(.,'Please select a shipping method.')]")
    public WebElement err_ShippingMethod;

    @FindBy(xpath = "//*[@id='order_total']")
    public WebElement costCalculation;

    @FindBy(xpath = "//*[@class='total-row estimate'] //span[2]")
    public WebElement lbl_estimatedtotal;

    @FindBy(xpath = "//*[@id='order_details']")
    public WebElement itemAdded;

    @FindBy(xpath = "//div[@class='shopping-bag'] //font[contains(.,'Editar Bolsa') or contains(.,'Edit Bag')]")
    public WebElement btn_ShoppingBag_EditBag;

    @FindBy(xpath = "//footer//*[@class='footer-mpr footer-row-top-col']/a/img")
    public WebElement img_myRewardEspot;

    @FindBy(xpath = "//*[@class='discount-applied']//span[contains(.,'discounts applied')]")
    public WebElement txt_discountApplied;

    @FindBy(css = ".remove-link a")
    public WebElement removeLinkDiscount;

    @FindBy(css = ".savings-total")
    public WebElement promotionsLabel;

    @FindBy(css = ".checkout-order-summary .shipping-total strong")
    public WebElement shippingCost;

    @FindBy(css = ".items-total")
    public WebElement itemTotalText;

    @FindBy(css = ".shipping-total")
    public WebElement shippingText;

    @FindBy(css = ".balance-total>strong")
    public WebElement estimatedTotal;

    @FindBy(css = ".tax-total")
    public WebElement estimatedTax;

    @FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    public WebElement removeCoupon;

    @FindBy(css = ".coupon-list-container")
    public WebElement couponsContainer;


    @FindBy(css = ".button-apply-coupons")
    public WebElement applyButton;


    @FindBy(xpath = "//div[@class='coupons-container']/div/form/label/button")
//.//button[@type='submit'][text()='Apply']")
    public WebElement applyCouponButton;

    @FindBy(xpath = ".//li[@class='coupons-total']/strong")
    public WebElement couponCodeApplied;

    @FindBy(css = ".return-to-bag-link")
    public WebElement returnBagLink;

    @FindBy(xpath = ".//div[@class='navigation-confirmation expanded']//span")
    public WebElement overlayContent;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnBagBtn;

    @FindBy(css = "button.button-login")
    public WebElement eSpotLoginLink;

    @FindBy(css = "button.button-register")
    public WebElement eSpotCreateAccountLink;


    @FindBy(css = ".shipping-method-title")
    public WebElement shippingMethodText;

    @FindBy(xpath = "//li[@class='gift-wrap-total']//strong")
    public WebElement giftWrapprice;

    @FindBy(xpath = ".//li[@class='savings-total']//strong")
    public WebElement promotionsTot;

    @FindBy(css = ".button-add-address")
    public WebElement addNewAddress;

    @FindBy(css = "h4>a")
    public List<WebElement> produtnames;

    @FindBy(css = ".checkout-section-title>h3")
    public WebElement shippingNote;

    @FindBy(css = ".button-exit-checkout")
    public WebElement backBtn;

    @FindBy(css = ".accordion.coupon-accordion")
    public WebElement couponCodeFieldArea;

    @FindBy(css = ".accordion.coupon-accordion button.accordion-toggle")
    public WebElement couponToggleButton;

    @FindBy(css = ".accordion.order-summary-accordion")
    public WebElement orderSummary;

    @FindBy(css = ".coupon-code-form input")
    public WebElement couponCodeFld;

    @FindBy(css = ".name")
    public WebElement custNameShipping;

    @FindBy(xpath = "//span[@class='address']")
    public WebElement shipAddrDisplay;

    @FindBy(css = "div.input-checkbox-icon-unchecked")
    public WebElement optEmailUnchecked;

    @FindBy(css = ".address-you-entered .checkbox-combo.address-options")
    public WebElement yourEnteredAddBtn;

    @FindBy(css = ".address-you-entered .checkbox-combo.address-options input")
    public WebElement yourEnteredInputAddBtn;

    @FindBy(xpath = ".//div[@class=\"address-container\"]")
    public WebElement addressEntered;

    @FindBy(xpath = ".//div[@class=\"address-container\"]")
    public WebElement shippingMethods;

    @FindBy(css = "button.button-add-new")
    public WebElement addNewAddfrmOverlay;

    @FindBy(xpath = "//input[@name='setAsDefault']")
    public WebElement saveDefaultCheckbox;


    @FindBy(css = ".set-as-default-checkbox-container")
    public WebElement editAddress_SetDefaultAddressCheckBoxInput;

    @FindBy(css = ".input-checkbox-icon-checked input")
    public WebElement defaultCheckBox;

    @FindBy(css = ".input-checkbox.label-checkbox.checkbox-set-default span")
    public WebElement defaultCheckBoxTxt;

    @FindBy(css = ".button-edit")
    public List<WebElement> editLinkList;

    @FindBy(css = ".input-checkbox-icon-checked input")
    public WebElement defaultCheckBoxUnChecked;

    @FindBy(css = ".select-common.select-state .labled-select-title")
    public WebElement provinceText;

    @FindBy(css = ".logo.logo-paypal.logo-paypal-blue")
    public WebElement intPayPalBtn;

    @FindBy(css = ".input-common.first-name input")
    public WebElement pickUpFn;

    @FindBy(css = ".input-common.last-name input")
    public WebElement pickUpln;

    @FindBy(css = ".input-common.email-address input")
    public WebElement pickUpEmail;

    @FindBy(css = ".input-common.phone-number input")
    public WebElement pickUpMobile;

    @FindBy(css = "select[name='address.country']")//select[name='address.country'][disabled]")
    public WebElement newAddress_CountryFld_disabled;

    @FindBy(css = ".modal-title.modal-only-title")
    public WebElement editAddrHeadingLabel;

    @FindBy(css = ".select-common.select-state .selection.select-option-selected")
    public WebElement stateFieldText;

    @FindBy(css = "div.pac-item")
    public List<WebElement> googleAddressLookUpItem;

    @FindBy(css = ".button-back")
    public WebElement backBtnAddressForm;

    @FindBy(css = "div.overlay-address-verification")
    public WebElement addressVerificationOverlay;

    @FindBy(css = ".checkout-progress-bar")
    public WebElement checkoutProgressBar;

    @FindBy(css = ".checkout-progress-bar li")
    public List<WebElement> accordians;

   /* @FindBy(xpath="//span[contains(text(),'Standard - FREE')]/preceding-sibling::div")//span[contains(text(),'Standard - FREE')]")
    public WebElement standardFree;  */

    @FindBy(xpath = "//span[contains(text(),'Standard')]/preceding-sibling::div")
//span[contains(text(),'Standard - FREE')]")
    public WebElement standardFree;

    @FindBy(css = ".address-container .address-details")
    public WebElement ShipAddDisplayed;

    @FindBy(css = ".title-checkout")
    public WebElement checkoutLabel;

    @FindBy(css = ".shipping-methods-input-box div")
    public List<WebElement> shipMethods;

    @FindBy(css = ".shipping-methods-input-box div+span")
    public List<WebElement> shipMethodsNames;

    @FindBy(css = ".title-checkout")
    public WebElement expressCheckout;

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

    @FindBy(css = ".address-details span[class=name]")
    public WebElement nameOnAddressDetails;


}

