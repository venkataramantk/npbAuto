package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.pages.actions.MobileOrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class MobileReceiptThankYouPageRepo extends MobileOrderLedgerAndCOSummaryActions {

    @FindBy(css = ".confirmation-item.confirmation-order>a")
    public WebElement orderNumber;

    @FindBy(css = ".confirmation-item.confirmation-order>a")
    public List<WebElement> orderNumbers;

    @FindBy(css = ".confirmation-item.confirmation-date")
    public WebElement orderDate;

    @FindBy(css = ".order-summary .items-total strong")
    public WebElement itemsCost;

    @FindBy(css = ".order-summary .shipping-total strong")
    public WebElement shippingCost;

    @FindBy(css = ".order-summary .tax-total strong")
    public WebElement taxCost;

    @FindBy(css = ".order-summary .balance-total strong")
    public WebElement totalCost;


    @FindBy(css = "input[name='password']")
    public WebElement enterPasswordField;

    @FindBy(css = "input[name='confirmPassword']")
    public WebElement confirmPasswordField;

    @FindBy(css = ".button-primary")
    public WebElement createAccountButton;

    @FindBy(css = ".success-box")
    public WebElement accountSuccess;

    @FindBy(css = "input[name='password'] ~ .inline-error-message")
    public WebElement err_password;

    @FindBy(css = "input[name='confirmPassword'] ~ .inline-error-message")
    public WebElement err_ConfirmPassword;


    @FindBy(css = ".checkout-order-summary")
    public WebElement ordeLedger;

    @FindBy(css = ".itemspecs>h1")
    public WebElement orderReceipt_ProductName;


    @FindBy(css = ".confirmation-title")
    public WebElement confirmationTitle;

    @FindBy(xpath = "//*[@id='total_breakdown'] //span[@class='points-large-font']")
    public WebElement Points;

    @FindBy(xpath = "//span[contains(.,'total:') and not(contains(.,'subtotal:'))]/following-sibling::span")
    public WebElement lbl_TotalAmount;


    @FindBy(css = ".savings-total")
    public WebElement promotionsLabel;


    @FindBy(css = "a[alt='MPR Rewards']")
    public WebElement mPReSpot;

    @FindBy(xpath = ".//li[@class='items-total']/strong")
    public WebElement itemTotal;

    @FindBy(xpath = ".//li[@class='items-total']")
    public WebElement itemCount;

    @FindBy(xpath = ".//li[@class='shipping-total']//strong")
    public WebElement shippingTot;

    @FindBy(xpath = ".//li[@class='tax-total']//strong")
    public WebElement taxTotal;

    @FindBy(xpath = ".//header[@class='confirmation-title']//h1")
    public WebElement thanksText;

    @FindBy(css = ".confirmation-email")
    public WebElement emailID;

    @FindBy(css = ".button-show-password")//(css = "[name='password'] + .button-show-password")
    public WebElement showHideLink;

    @FindBy(css = ".confirmation-fullfillment-store")
    public WebElement storeLocator;

    @FindBy(css = "button.favorite-icon-container")
    public List<WebElement> addToFavIcon;

    @FindBy(css = "button.bag-icon-container")
    public List<WebElement> addToBagIcon;

    @FindBy(css = ".favorite-icon-active")
    public List<WebElement> favIconEnabled;

    public WebElement itemImg(int i) {
        return getDriver().findElement(By.xpath("(.//ol[@class='content-product-recomendation']//figure//a)  [" + i + "]"));
    }

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'.item-disabled-option'))]")
    public List<WebElement> availableSizes;

    @FindBy(xpath = "(.//div[@class='size-and-fit-detail-items-list'])[1]//span")
    public List<WebElement> availableFits;

    @FindBy(css = ".confirmation-create-account-title")
    public WebElement createAccountTitle;

    @FindBy(css = ".user-email-title")
    public WebElement userEmailTitle;

    @FindBy(css = ".user-email-message")
    public WebElement userEmail;

    @FindBy(css = ".input-password.input-with-button input")
    public WebElement passwordFld;

    @FindBy(css = ".text-under")
    public WebElement airmiles_Text;

    @FindBy(xpath = "//*[text()='Password']/../../../button")
    public WebElement showPwd;

    @FindBy(xpath = "//*[text()='Confirm Password']/../../../button")
    public WebElement showConfirmPwd;

    @FindBy(xpath = "//*[text()='Password']/../following-sibling::div")
    public WebElement pwdError;

    @FindBy(xpath = "//*[text()='Confirm Password']/../following-sibling::div")
    public WebElement confirmPwdError;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-terms-and-conditions.label-error>div")
    public WebElement termsANdConditionsError;

    @FindBy(css = "div.checkbox-terms-and-conditions .input-checkbox-title")
    public WebElement termsAndConditionTxt;

    @FindBy(css = ".link-terms")
    public WebElement termsAndConditionTlink;

    @FindBy(css = ".contact-us-link")
    public WebElement contactUsLink;

    @FindBy(css = ".button-primary")
    public WebElement createAccountBtn;

    @FindBy(css = ".product-recomendation")
    public WebElement productRecomendation;

    @FindBy(css = ".confirmation-banner")
    public WebElement mprBanner;

    @FindBy(xpath = "//span[@class='error-text']")
    public WebElement existingAccError;

    @FindBy(css = ".guest-rewards-promo-container.summary-message")
    public WebElement mprContainer;

    @FindBy(xpath = "//h3[contains(.,'Thank you for your order,')]")
    public WebElement thankTextForInt;

    @FindBy(xpath = "//h4[contains(.,'Your Order Number is:')]")
    public WebElement OrderNoTextForInt;

    @FindBy(xpath = "//h2/strong[@class='ng-binding']")
    public WebElement estTotalForInt;

    @FindBy(css = ".button-info-wire")
    public WebElement toolTip;

    @FindBy(css = ".tooltip-content")
    public WebElement toolTipContent;

    public WebElement orderNoLink(String order) {
        return getDriver().findElement(By.linkText(order));
    }

    @FindBy(css = ".confirmation-fullfillmentlist-item")
    public List<WebElement> confirmationFullFillmentListItem;

    @FindBy(xpath = "//h2[@class = 'confirmation-fullfillment-type'][contains(., \"Pickup\")]/../div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-order']")
    public WebElement orderNumPickup;

    @FindBy(xpath = "//h2[@class = 'confirmation-fullfillment-type'][contains(., \"Pickup\")]/../div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-date']")
    public WebElement orderDatePickup;

    @FindBy(xpath = "//h2[@class = 'confirmation-fullfillment-type'][contains(., \"Pickup\")]/../div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-total']")
    public WebElement orderTotalPickup;

    @FindBy(xpath = "//h2[@class = 'confirmation-fullfillment-type'][contains(., \"Shipping\")]/../div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-order']")
    public WebElement orderNumShipping;

    @FindBy(xpath = "//h2[@class = 'confirmation-fullfillment-type'][contains(., \"Shipping\")]/../div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-date']")
    public WebElement orderDateShipping;

    @FindBy(xpath = "//h2[@class = 'confirmation-fullfillment-type'][contains(., \"Shipping\")]/../div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-total']")
    public WebElement orderTotalShipping;

    @FindBy(css = ".confirmation-thanks .confirmation-email strong")
    public WebElement emailIDText;

    @FindBy(css = ".create-account .user-email-message")
    public WebElement emailIDCreateAcc;

    @FindBy(css = ".tooltip-content h3")
    public WebElement toolTipStoreName;

    @FindBy(css = ".tooltip-content p")
    public WebElement toolTipDetail;

    @FindBy(css = ".confirmation-next-steps p")
    public WebElement orderModifyCancelConfirmation;

    @FindBy(css = "div.confirmation-fullfillmentlist-item:nth-child(2) > div.confirmation-fullfillment-details")
    public WebElement customerName;

    @FindBy(css = "p.pending-notification")
    public WebElement offlineOrderNotification;

    @FindBy(css = ".sms-marketing-form-header")
    public WebElement marketingHeader;

    @FindBy(css = ".checkout-sms-order-status-signup div:nth-child(2)")
    public WebElement marketingSmsText;

    @FindBy(css = ".checkout-sms-order-status-signup div:nth-child(1) label div")
    public WebElement marketingSmsCB;

    @FindBy(css = ".checkout-sms-order-status-signup div:nth-child(1) label div input")
    public WebElement marketingSmsCBInput;

    @FindBy(css = ".sms-wrapping-fields .input-common.input-phone input")
    public WebElement marketingSmsPhoneField;

    @FindBy(css = ".sms-disclaimer.disclaimer-version-2")
    public WebElement smsDisclaimer;

    @FindBy(css = ".privacy-policy-link")
    public WebElement smsPrivacyPolicy;

    @FindBy(css = ".submit-container button")
    public WebElement submitBtn;

    @FindBy(css = ".sms-wrapping-fields .inline-error-message")
    public WebElement phoneNoErrMsg;

    @FindBy(css = ".error-box ")
    public WebElement pageLeverError;

    //ODM
    @FindBy(css = ".heading-container")
    public WebElement odmHeaderText;

    public WebElement expandODMCoupon(int position) {
        return getDriver().findElement(By.xpath("(//button[@class= 'accordion-toggle down-arrow'])[" + position + "]"));
    }

    @FindBy(css = ".description")
    public WebElement couponDescription;

    @FindBy(css = ".validity-text")
    public WebElement validityDate;

    @FindBy(css = "svg g")
    public WebElement barCode;

    @FindBy(css = ".code")
    public WebElement promoCode;

    @FindBy(css = ".underlined")
    public WebElement detailsLink;

    @FindBy(css = ".overlay-content.long-description p")
    public WebElement longDescrption;

    @FindBy(css = ".button-modal-close")
    public WebElement closeDetailsLink;
}
