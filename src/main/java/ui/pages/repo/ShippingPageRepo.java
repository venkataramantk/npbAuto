package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */

public class ShippingPageRepo extends OrderLedgerRepo {

    @FindBy(css = ".section-title")
    public WebElement sectionTitle;

    @FindBy(css = ".checkout-content .address-shipping-view .button-edit-address")//"shipping.address.firstName")
    public WebElement editLink_ShipAddress;

    //    @FindBy(css="#firstName")
    @FindBy(css = ".input-common.input-first-name input")//"shipping.address.firstName")
    public WebElement firstNameFld;

    @FindBy(css = ".summary-and-message div:nth-of-type(2) div")
    public WebElement promotionOffer;

    //    @FindBy(css="#lastName")
    @FindBy(css = ".input-common.input-last-name input")//"shipping.address.lastName")
    public WebElement lastNameFld;

    //    @FindBy(css="#address1")
    @FindBy(css = ".input-common.input-address-line1 input")//"shipping.address.addressLine1")
    public WebElement addressLine1Fld;

    //    @FindBy(css="#address2")
    @FindBy(css = ".input-common.input-address-line2 input")//"shipping.address.addressLine2")
    public WebElement addressLine2Fld;

    //    @FindBy(css="#city")
    @FindBy(css = ".input-common.input-city input")//"shipping.address.city")
    public WebElement cityFld;

    //    @FindBy(css="#WC_TCPShippingAddress_FormInput_state")
    @FindBy(css = ".select-common.select-state select")
    public WebElement stateDropDown;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-address-book div")
//(css=".checkbox-address-book span.input-subtitle")
    public WebElement saveToAddressBookCheckBox;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-address-book div")
    public WebElement saveToAddrBookChkBoxEnabled;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-address-book div input")
    public WebElement saveToAddrBookChkBoxEnabledinput;

    //    @FindBy(css="#addressField3")
    @FindBy(css = ".input-common.input-zip-code input")//"shipping.address.zipCode")
    public WebElement zipPostalCodeFld;

    //    @FindBy(css="#phone1")
    @FindBy(css = ".input-common.input-phone input")//"shipping.phoneNumber")
    public WebElement phNumFld;

    @FindBy(css = ".input-common.input-email input")//"shipping.emailAddress")
    public WebElement emailAddressFld;

    @FindBy(css = ".registered-rewards-promo")
    public WebElement loyalityNotification;

    @FindBy(name = "couponCode")
    public WebElement couponCodeField;

    @FindBy(css = "#singleShipmentShippingMode")
    public WebElement shippingMethodDropDown;

    @FindBy(css = ".select-common.select-country>select")
    public WebElement shipToCountryDropdown;

    @FindBy(css = ".label-radio.input-radio.radio-method div")
    public List<WebElement> shippingMethodRadioButtons;

    public WebElement shippingMethodRadioButtonByValue(String value) {
//        return getDriver().findElement(By.cssSelector("div input[value='" + value + "']"));
        return getDriver().findElement(By.xpath("(//input[@value='" + value + "']/parent::div)/following-sibling::span[contains(@class,'input')and contains(@class,'-title')]"));
    }

    public WebElement shippingMethodRadioButtonByPos(String pos) {
        return getDriver().findElement(By.xpath("//div[@class='shipping-methods shipping-methods-form']/fieldset/label[" + pos + "]/div"));
    }

//This is duplicate element. refer "couponCodeFld".
//    @FindBy(xpath="//*[@id='promoCode']")
//    public WebElement txt_CouponCode;

    @FindBy(xpath = "//*[@id='WC_PromotionCodeDisplay_links_1']")
    public WebElement btn_ApplyCode;

    @FindBy(css = "#shippingBillingPageNext")
    public WebElement continueCheckoutButton;

    @FindBy(xpath = "//button[text()='NEXT: Billing']")
    public WebElement nextBillingButton;

    @FindBy(xpath = "//*[@id='WC_SingleShipmentOrderTotalsSummary_td_2']")
    public WebElement txt_Subtotal;

    @FindBy(xpath = "//div[@class='ui-dialog-buttonset']/button[1]/span")
    public WebElement useAsEnteredButton;

    @FindBy(css = ".address-verification button[type='submit']")
    public WebElement addressVerificationContinue;

    @FindBy(css = ".button-secondary")
    public WebElement getAddressVerificationEdit;

    @FindBy(css = ".button-save-address")
    public WebElement saveButton;

    @FindBy(name = "addressLine2")
    public WebElement addressLine2AVS;

    //This is duplicate element. refer "useAsEnteredButton"
//    @FindBy(xpath="//span[contains(.,'Use As Entered') or contains(.,'Usar como se ingresó') or contains(.,\"Utiliser l'adresse saisie\")]")
//    public WebElement new_UseAsEntered;

    @FindBy(xpath = "//*[@id='shippingGiftOptionArea']//li[contains(.,'None')]")
    public WebElement txt_None;

    @FindBy(css = "#gift_modal")
    public WebElement editGiftOptions;

    @FindBy(css = "#gift-message")
    public WebElement giftMessageBox;

    @FindBy(css = "#giftOption_4")
    public WebElement giftReceiptMessageOnlyRadioBox;

    @FindBy(css = "#commonLoadingId1")
    public WebElement nextButtonGiftOptions;

    //This is duplicate element. refer "continueCheckoutButton".
//    @FindBy(css="#shippingBillingPageNext")
//    public WebElement nextButton;

    @FindBy(css = ".gift-options>ul>li:nth-child(3)")
    public WebElement giftOptionsYourMessageElement;

   // @FindBy (xpath = "//input[@name='giftWrap.hasGiftWrapping']")// Gift wrapping checkbox
    //@FindBy(xpath = ".//div[@class=\"input-checkbox-icon-unchecked\"]")
    //@FindBy(name = "giftWrap.hasGiftWrapping")
    @FindBy(css = ".checkout-review-gift-wrapping.checkout-review-gift-wrapping-form .input-checkbox-icon-unchecked")
    public WebElement giftWrappingCheckbox;

    //@FindBy(xpath = ".//div[@class=\"input-checkbox-icon-unchecked\"]/input")
    @FindBy(xpath = "//div[@class=\"label-checkbox input-checkbox add-gift-wrapping\"]//input")
    public WebElement giftWrappingCheckboxInput;

    //@FindBy(xpath = "//*[@name='giftWrap.hasGiftWrapping']")// Uncheck Gift wrapping checkbox
    public WebElement giftWrappingCheckboxUnCheck;


    @FindBy(css = ".gift-wapping-details") // Gift option details
    public WebElement giftWrappingOptions;

    @FindBy(xpath = ".//*[@class=\"gift-wrapping-name\"][text()=\"Standard\"]") // select Standard Gift wrapping
    public WebElement giftWrappingStandard;

    @FindBy(css = "#labeled-textarea_0") // Gift wrapping message
    public WebElement giftWrappingMessage;

    @FindBy(xpath = "//textarea")
    public WebElement giftWrappingMesBox;

    /*@FindBy(css="#ErrorMessageText")*/
    @FindBy(css = ".input-common.input-zip-code .inline-error-message")
    public WebElement err_POZipCode;

    //This is duplicate element. refer "editGiftOptions"
//    @FindBy(css="#gift_modal")
//    public WebElement ln_Edit;

    @FindBy(css = ".coupon-code-form input")//#promoCode")
    public WebElement couponCodeFld;

    /*@FindBy(xpath = "//label[contains(.,'Please enter first name.')]")*/
    @FindBy(css = ".input-common.input-first-name div.inline-error-message")
    public WebElement err_FirstName;

    /*@FindBy(xpath = "//label[contains(.,'Please enter last name.')]")*/
    @FindBy(css = ".input-common.input-last-name div.inline-error-message")
    public WebElement err_LastName;

    /*@FindBy(xpath = "//label[contains(.,'Please enter a valid street address.')]")*/
    @FindBy(css = ".input-common.input-address-line1 div.inline-error-message")
    public WebElement err_AddressLine1;

    @FindBy(css = ".input-common.input-address-line2 div.inline-error-message")
    public WebElement err_AddressLine2;

    /*@FindBy(xpath = "//label[contains(.,'Please enter a valid city.')]")*/
    @FindBy(css = ".input-common.input-city div.inline-error-message")
    public WebElement err_City;

    /*@FindBy(css="#label-error")*/
    @FindBy(css = ".select-common.select-state div.inline-error-message")
    public WebElement err_State;

    /*@FindBy(xpath = "//label[contains(.,'Please enter a valid phone number.')]")*/
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
    public WebElement discAppliedValueLbl;

    @FindBy(css = ".error-box")
    public WebElement pageLevelErrBox;

    @FindBy(css = ".items-total")
    public WebElement itemTotalText;

    @FindBy(css = ".shipping-total")
    public WebElement shippingText;

    @FindBy(css = ".balance-total>span")
    public WebElement estimatedTotText;

    @FindBy(xpath = ".//li[@class='balance-total']/strong")
    public WebElement estimatedTotal;

    @FindBy(css = ".tax-total")
    public WebElement estimatedTax;

    @FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    public WebElement removeCoupon;

    @FindBy(xpath = ".//li[@class='coupons-total']")
    public WebElement couponPrice;

    @FindBy(css = ".button-apply-coupons")
//div[@class='coupons-container']/div/form/label/button")//.//button[@type='submit'][text()='Apply']")
    public WebElement applyCouponButton;

    @FindBy(xpath = ".//div[@class='available-rewards apply-coupons apply-rewards']//h3[.='APPLIED COUPONS']")
//div[@class='availableRewards apply-coupons apply-rewards']//h3[.='APPLIED COUPONS']")
    public WebElement appliedCouponText;


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

    @FindBy(css = ".button-modal-close")
    public WebElement closeOverlayBtn;

    @FindBy(css = ".shipping-method-title")
    public WebElement shippingMethodText;

    @FindBy(xpath = ".//label[@class=\"label-radio input-radio radio-method\"]//span[@class=\"input-radio-title\"]")
    public List<WebElement> shippingMethods;

    @FindBy(xpath = ".//li[@class='items-total']//strong")
    public WebElement itemTotal;

    @FindBy(xpath = ".//li[@class='shipping-total']//strong")
    public WebElement shippingTot;

    @FindBy(css = ".input-radio-icon-checked")
    public WebElement selectedShippingMethod;

    @FindBy(xpath = ".//li[@class='savings-total']//strong")
    public WebElement promotionsTot;

    @FindBy(xpath = ".//li[@class='tax-total']//strong")
    public WebElement taxTotal;

    @FindBy(css = ".button-shipping-internationally")
    public WebElement shipInternationalLnk;

    @FindBy(css = ".term-and-conditions-message")
    public WebElement termsAndConditionText;

    @FindBy(xpath = ".//div[@class='custom-select-button dropdown-address-book-button-closed']//p")
    public WebElement dropDownLink;

    @FindBy(css = ".button-add-new.button-add-new-address")
    public WebElement addNewAddress;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Girl') or contains(.,'Fille') or contains(.,'NiÃ±a')]")*/
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Girl' or .='Fille' or .='Niña']")
    public WebElement link_Girls;

    /*@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Toddler Girl') or contains(.,'Tout-petit, fille')  or contains(.,'Niña pequeña')]")*/
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Toddler Girl' or .='Tout-petit, fille' or .='Niña pequeña']")
    public WebElement link_ToddlerGirls;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Boy') or contains(.,'Niño') or contains(.,'Garçon')]")*/
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Boy' or .='Garçon'or .='Niño']")
    public WebElement link_Boys;

    /*@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Toddler Boy') or contains(.,'Niño pequeño') or contains(.,'Tout-petit, garçon')]")*/
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Toddler Boy'or .='Tout-petit, garçon' or .='Niño pequeño']")
    public WebElement link_ToddlerBoys;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Baby') or contains(.,'Bebé') or contains(.,'Bébé')]")*/
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Baby' or .='Bebé' or .='Bébé']")
    public WebElement link_Baby;

    //    @FindBy(xpath = "//li[@class='short']/a[contains(.,'Shoes') or contains(.,'Calzado') or contains(.,'Chaussures')]")
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Shoes']")
    public WebElement link_Shoes;

    //    @FindBy(xpath = "//li[@class='long special']/a[contains(.,'Accessories') or contains(.,'Accesorios') or contains(.,'Accessoires')]")
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Accessories']")
    public WebElement link_Accessories;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesShip;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesNumb;

    @FindBy(css = ".coupon-help-link")
    public WebElement needHelp_Link;

    @FindBy(css = ".text-under")
    public WebElement airmiles_Text;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModal;

    @FindBy(css = "[id=\"addressIndex_22\"]")
    public WebElement youEnterAddress;

    @FindBy(css = ".modal-header")
    public WebElement shipToHeader;

    @FindBy(css = ".button-modal-close")
    public WebElement shipCloseModal;

    @FindBy(css = ".input-checkbox-icon-unchecked")
    public List<WebElement> checkboxUncheckedGuest;

    @FindBy(xpath = ".//div[@class=\"label-checkbox input-checkbox label-checkbox checkbox-get-email\"]")
    public WebElement marketingEmail;

    @FindBy(xpath = ".//a[@class=\"logo-container logo\"]")
    public WebElement tcpLogo;

    @FindBy(xpath = ".//button[@class=\"button-quaternary button-return\"]")
    public WebElement returnToBag_Link;

    @FindBy(css = ".checkbox-get-email label div input")
    public WebElement signupEmail_ChkInput;

    @FindBy(css = ".checkbox-get-email label div")
    public WebElement signupEmail_ChkBox;

    @FindBy(xpath = ".//div[@class=\"label-checkbox input-checkbox add-gift-wrapping\"]")
    public WebElement giftWrappingCheckboxChecked;

    @FindBy(xpath = ".//div[@class=\"address-container\"]")
    public WebElement addressEntered;

    @FindBy(css = ".verify-notification p")
    public WebElement errorAtAddrVerifi;

    @FindBy(css = ".button-cancel-address")
    public WebElement cancelEditShipping;

    @FindBy(xpath = ".//div[@class=\"label-checkbox input-checkbox checkbox-address-book\"]//input")
    public WebElement saveToAddrBookCheckout;

    @FindBy(css = ".red-text")
    public WebElement avsError;

    @FindBy(xpath="//*[text()='Shipping']/..")
    public WebElement shippingProgressBar;

    @FindBy(xpath="//*[text()='Billing']/..")
    public WebElement billingProgressBar;

    @FindBy(xpath = "//*[text()='Review']/..")
    public WebElement reviewProgressBar;

    @FindBy(tagName = "h2")
    public WebElement checkoutTitle;

    @FindBy(xpath = ".//*[@class=\"input-radio-title\"][contains(.,'Standard')]")
    public WebElement standardShipText;

    @FindBy(xpath = ".//div[@class=\"address-container\"]//span[@class='name'] ")
    public WebElement defaultShipName;

    @FindBy(xpath = ".//div[@class=\"address-container\"]//span[@class='address'] ")
    public WebElement defaultShipAddress;

    //Added by Richa Priya
    @FindBy(css = ".checkbox-get-email div.input-checkbox-icon-unchecked")
    public WebElement optEmailInput;

    @FindBy(xpath = "//div[@class=\"label-checkbox input-checkbox get-sms-updates\"]//*[@class=\"input-checkbox-icon-unchecked\"]")
    public WebElement transactionalSMSCheckboxUnChecked;

    @FindBy(css = ".checkout-sms-order-status-signup div label div:nth-child(1) input")
    public WebElement transactionalSMSCheckboxInput;

    @FindBy(css = ".checkout-sms-order-status-signup div label div:nth-child(1)")
    public WebElement transactionalSMSCheckbox;

    @FindBy(name = "smsInfo.smsUpdateNumber")
    public WebElement transactionalNumber;

    @FindBy(css = ".sms-disclaimer")
    public WebElement smsPrivacyPolicy;

    @FindBy(css = ".sms-wrapping-fields .inline-error-message")
    public WebElement smsInlineErrorMessage;

    @FindBy(xpath= "//div[@class='shipping-methods shipping-methods-form']//span[@class='input-radio-title'][contains(.,\"Rush\")]")
    public WebElement rushShippingMethod;

    @FindBy(xpath = "//div[@class='shipping-methods shipping-methods-form']//span[contains(.,\"Express\")]")
    public WebElement ExpressShippingCA;

}

