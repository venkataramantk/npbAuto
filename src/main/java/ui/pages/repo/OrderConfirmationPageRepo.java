package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.actions.OrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class OrderConfirmationPageRepo extends OrderLedgerAndCOSummaryActions {

    @FindBy(css=".confirmation-item.confirmation-order>a")
    public WebElement orderNumber;

    @FindBy(css=".popup-close.is-solid.js-offer-close")
    public WebElement promoModal_Close;

    @FindBy(css=".new-account")
    public WebElement createAccountEspot;

    //    @FindBy (css="#WC_TCPUserRegistrationAddForm_FormInput_logonPassword")
    @FindBy(css="input[name='password']")
    public WebElement enterPasswordField;

    //    @FindBy (css="#WC_TCPUserRegistrationAddForm_FormInput_logonPasswordVerify")
    @FindBy(css="input[name='confirmPassword']")
    public WebElement confirmPasswordField;

    //    @FindBy (css="#termsCheck")
    @FindBy(css=".label-checkbox.input-checkbox.checkbox-terms-and-conditions div")
    public WebElement termsAndConditionsCheckBox;

    //    @FindBy(css=".input-checkbox .input-title")
    @FindBy(css=".checkbox-terms-and-conditions .input-checkbox-icon-unchecked")
    public WebElement termsAndConditionsTextLbl;

    @FindBy(css = ".checkout-container .viewport-container button[type='submit']")
    public WebElement createAccountButton;

    @FindBy(css = ".inline-error-message")
    public WebElement err_password;

    @FindBy(xpath="//input[@name='confirmPassword']/../../div/div")
    public WebElement err_ConfirmPassword;

    /*@FindBy (xpath="//label[contains(.,'You must agree to the Terms and Conditions of the myPlace Rewards program to create an account.')]")*/
    @FindBy(css = ".input-checkbox .input-title ~ .inline-error-message")
    public WebElement err_TermsAndConditions;

    @FindBy (css="#WC_CheckoutPaymentAndBillingAddressSummaryf_div_2")
    public WebElement billingAddressSection;

    @FindBy (xpath="//*[@class='summary-details'][contains(.,'shipping info') or contains(.,'info. de env')]")
    public WebElement shippingInfoSection;

    @FindBy(css="#UserRegistrationErrorMessage")
    public WebElement err_PageLevel_forgetPassword;

    @FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'shipping method')]]")
    public WebElement shippingMethodSection;

    @FindBy(css = "#WC_CheckoutPaymentAndBillingAddressSummaryf_div_3_ p")
    public WebElement paymentMethodSection;

    @FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'gift options')]]")
    public WebElement giftOptionsSection;

    @FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'Air Miles rewards')]]")
    public WebElement airMilesRewards;

    @FindBy(xpath="//*[@id='order_total']/div[contains(.,'shipping tax')]/span[2]")
    public WebElement orderReceipt_shipping_Tax;

    @FindBy(xpath="//td[contains(@class,'item-size')]")
    public WebElement orderReceipt_Size;

    @FindBy(css=".itemspecs>h1")
    public WebElement orderReceipt_ProductName;

    @FindBy(css=".itemspecs>h2")
    public WebElement orderReceipt_ProductColor;

    @FindBy(css="table td+.item-size")
    public WebElement orderReceipt_ProductSize;

    @FindBy(css="#WC_OrderItemDetailsSummaryf_td_2_1")
    public WebElement orderReceipt_ProductQuantity;

    @FindBy(css="#WC_OrderItemDetailsSummaryf_td_4_1")
    public WebElement orderReceipt_ProductPrice;

    @FindBy(css=".sale-price")
    public WebElement orderReceipt_ProductSalePrice;

    @FindBy(css=".confirmation-title")
    public WebElement confirmationTitle;

    @FindBy(xpath = "//*[@id='total_breakdown'] //span[@class='points-large-font']")
    public WebElement Points;

    @FindBy(id = "tQuantity")
    public WebElement numberOfItems;

    @FindBy(xpath = "//span[contains(.,'subtotal:')]/following-sibling::span")
    public WebElement lbl_SubTotalAmount;

    @FindBy(xpath = "//span[contains(.,'total:') and not(contains(.,'subtotal:'))]/following-sibling::span")
    public WebElement lbl_TotalAmount;

    @FindBy(xpath = "//span[contains(.,'shipping:')]/following-sibling::span")
    public WebElement lbl_ShippingAmount;

    @FindBy(xpath = "//span[contains(.,'shipping tax:')]/following-sibling::span")
    public WebElement lbl_ShippingTaxAmount;

    @FindBy(css=".savings-total")
    public WebElement promotionsLabel;

    @FindBy(css=".shipping-total>strong")
    public WebElement freeShipPriceLabel;

    @FindBy(css=".shipping-total")
    public WebElement shippinglabel;

    @FindBy(css=".tax-total")
    public WebElement taxlabel;

    @FindBy(css = ".checkout-subtitle")
    public WebElement loginForPlaceRewardsText;

    @FindBy(css=".checkout-container .checkout-title")
    public WebElement checkoutTitle;

    @FindBy(css=".checkout-container .checkout-subtitle")
    public WebElement checkoutSubTitle;


    //Old mPR eSpots
    @FindBy(xpath="//div[@class='new-account']//img[contains(@alt,'miss out on myPLACE Rewards!')]")
    public WebElement mPReSpotOld;

    @FindBy(css=".create-account-heading")
    public WebElement mPRDetails;

    @FindBy(css=".create-account-heading")
    public WebElement mPRDetailsBonusInfo;

    @FindBy(xpath="//div[@class='new-account']//div[.='* The 250 myPLACE Rewards bonus points will be awarded approximately 24-48 hours after clicking through and confirming your email.']")
    public WebElement mPRDetailsBonusInfoDetails;

    @FindBy(xpath = ".//li[@class='items-total']/strong")
    public WebElement itemTotal;

    @FindBy(xpath = ".//li[@class='items-total']")
    public WebElement itemCount;

    @FindBy(xpath = ".//li[@class='balance-total']//strong")
    public WebElement estimatedTot;

    @FindBy(xpath = ".//li[@class='savings-total']//strong")
    public WebElement promotion_Cost;

    @FindBy(xpath = ".//li[@class='coupons-total']//strong")
    public WebElement coupon_Cost;

    @FindBy(xpath = ".//li[@class='shipping-total']//strong")
    public WebElement shippingTot;

    @FindBy(xpath = ".//li[@class='tax-total']//strong")
    public WebElement taxTotal;

    @FindBy(xpath = ".//header[@class='confirmation-title']//h1")
    public WebElement thanksText;

    @FindBy(css = ".confirmation-email")
    public WebElement emailID;

    @FindBy(css=".button-show-password")//(css = "[name='password'] + .button-show-password")
    public WebElement showHideLink;

    @FindBy(css = ".confirmation-next-steps")
    public WebElement whatsNextText;

    @FindBy(xpath = ".//h2[text()='Shipping']/following-sibling::div[@class='confirmation-item-container']/p/a")
    public WebElement sthOrderID;

    @FindBy(xpath = ".//h2[text()='Shipping']/following-sibling::div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-date']")
    public WebElement sthOrderDate;

    @FindBy(xpath = ".//h2[text()='Shipping']/following-sibling::div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-total']")
    public WebElement sthOrderTotal;

    @FindBy(xpath = ".//h2[text()='Pickup']/following-sibling::div[@class='confirmation-item-container']/p/a")
    public WebElement bopisOrderID;

    @FindBy(xpath = ".//h2[text()='Pickup']/following-sibling::div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-date']")
    public WebElement bopisOrderDate;

    @FindBy(xpath = ".//h2[text()='Pickup']/following-sibling::div[@class='confirmation-item-container']/p[@class='confirmation-item confirmation-total']")
    public WebElement bopisOrderTotal;

    @FindBy(css = ".confirmation-fullfillment-store")
    public WebElement storeLocator;



    @FindBy(css = "button.favorite-icon-container")
    public List<WebElement> addToFavIcon;

    @FindBy(css = "button.bag-icon-container")
    public List<WebElement> addToBagIcon;

    @FindBy(css = ".favorite-icon-active")
    public List<WebElement> favIconEnabled;

    public WebElement itemImg(int i){
        return getDriver().findElement(By.xpath("(.//ol[@class='content-product-recomendation']//figure//a)  ["+i+"]"));
    }

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(css = ".link-redirect")
    public WebElement viewProdDetailsLink;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'.item-disabled-option'))]")
    public List<WebElement> availableSizes;

    @FindBy(xpath = "(.//div[@class='size-and-fit-detail-items-list'])[1]//span")
    public List<WebElement> availableFits;

    @FindBy(css = ".button-close")
    public WebElement closeLink;

    @FindBy(xpath = ".//div[@class='header-global-banner']//img")
    public WebElement headerBanner_Espot;

    @FindBy(xpath = ".//div[@class=\"confirmation-banner\"]")
    public WebElement confBannerEspot;

    @FindBy(xpath = ".//div[@class=\"guest-rewards-promo-container summary-message\"]")
    public WebElement mprBannerEspt;

    @FindBy(xpath =".//li[@class='coupons-total']/strong" )
    public WebElement couponTextTot;

    @FindBy(css = ".button-info-wire")
    public WebElement storeTooltip;

    @FindBy(css = ".tooltip-content")
    public WebElement toolTipContent;

/*    @FindBy(xpath = ".//li[@class='coupons-total']//strong")
    public WebElement coupon_Cost;*/

    @FindBy(css = ".custom-loading-icon")
    public WebElement loadingIcon;

    // Transactional SMS
    @FindBy(css = ".checkout-sms-order-status-signup div:nth-child(1) label div")
    public WebElement transactionalSmsCheckBox;

    @FindBy(css = ".checkout-sms-order-status-signup div:nth-child(1) label div input")
    public WebElement transactionalSmsCheckBoxInput;

    @FindBy(css = ".sms-wrapping-fields .input-common.input-phone input")
    public WebElement trnasactionalSMS_PhoneField;

    @FindBy(css = ".sms-disclaimer.disclaimer-version-2")
    public WebElement transactionalSMS_Disclaimer;

    @FindBy(css = ".privacy-policy-link")
    public WebElement trnasactionalSMS_PrivacyPolicy;

    @FindBy(css = ".submit-container button")
    public WebElement submitBtn;

    @FindBy(css = ".sms-wrapping-fields .inline-error-message")
    public WebElement phoneNoErrMsg;

    @FindBy(css = ".error-box ")
    public WebElement pageLeverError;

    @FindBy(css = ".success-box")
    public WebElement accountSuccess;

    //ODM

    @FindBy(css = ".heading-container")
    public WebElement odmHeaderText;

    @FindBy(css = ".description")
    public WebElement couponDescription;

    @FindBy(css = ".validity-text")
    public List<WebElement> validityDate;

    @FindBy(css = "svg g")
    public List<WebElement> barCode;

    @FindBy(css = ".code")
    public List<WebElement> promoCode;

    @FindBy(css = ".underlined")
    public List<WebElement> detailsLink;

    @FindBy(css = ".overlay-content.long-description p")
    public WebElement longDescrption;

    @FindBy(css = ".button-modal-close")
    public WebElement closeDetailsLink;

}
