package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.actions.OrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class ReviewPageRepo extends OrderLedgerAndCOSummaryActions {

    @FindBy(css=".active>h2")
    public WebElement isReviewPageActive;

    @FindBy(css=".button-container button")
    public WebElement submOrderButton;

    @FindBy(css="#WC_SingleShipmentSummary_links_GoBack")
    public WebElement goBack;

    @FindBy(css=".address-additional span")
    public WebElement emailID;

    @FindBy(xpath="(//div[@class='container'] //div[contains(@class,'popup-close')][contains(@class,'offer-close')])[1]")
    public WebElement shareTheLovePromo;

//    @FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'shipping info') or contains(.,'info. de envÃ­o') or contains(.,\"infos sur l'expÃ©dition\")]]")
//    public WebElement shippingAddressSection;

    /*@FindBy(xpath = "//div[@class='address-container']/p[@class='address-details']")*///"//div[@class='summary-details' and h1[contains(.,'shipping info') or contains(.,'info. de env') or contains(.,'infos sur l')]]")
    @FindBy(css=".checkout-review-shipping-address .address-details")
    public WebElement shippingAddressSection;

    @FindBy(css = ".checkout-review-billing-address .address-details")
    public WebElement billingAddressSection;

    @FindBy(css="#WC_CheckoutPaymentAndBillingAddressSummaryf_div_2>a:nth-of-type(1)")
    public WebElement editPhoneNumberLink;

    @FindBy(css="#WC_CheckoutPaymentAndBillingAddressSummaryf_div_2>a:nth-of-type(2)")
    public WebElement editEmailLink;

    /*@FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'gift options') or contains(.,'Air Miles rewards') or contains(.,'opciones de regalos') or contains(.,'compenses Air Miles')]]")*/
    @FindBy(css=".checkout-review-section .checkout-review-gift-wrapping")
    public WebElement giftWrappingSection;

    @FindBy(xpath= "//div[@class='summary-details' and h1[contains(.,'Air Miles rewards') or contains(.,'ompenses Air Miles')]]")
    public WebElement airMilesRewardsSection;

    @FindBy(css="#gift_modal")
    public WebElement editGiftOptionsUS;

    @FindBy(css=".summary-details>a[href*='OrderBillingView']")
    public WebElement editGiftOptionsCA;

    /*@FindBy(css = ".shipping-method-name")/*////div[@class='summary-details' and h1[contains(.,'shipping method') or contains(.,'todo de env') or contains(.,\"mode d'exp\")]]")
    @FindBy(css=".shipping-method-container")
    //@FindBy(xpath = ".//div[@class=\"shipping-methods shipping-methods-form\"]")
    public WebElement shippingMethodSection;

//    @FindBy(css="#shippingGiftOptionArea ~ div.summary-details a[href*='TCPOrderShippingView']")
//    public WebElement editShippingMethod;


    @FindBy(xpath = ".//div[@class=\"shipping-methods shipping-methods-form\"]")
    public WebElement expShippingMethod;


    @FindBy(css="div.summary-details a[href*='TCPOrderShippingView']")
    public WebElement editShippingMethod;

    /*@FindBy(css = "#WC_CheckoutPaymentAndBillingAddressSummaryf_div_3_ p")*/
    @FindBy(css=".checkout-review-payment-method .card-info")
    public WebElement paymentMethodSection;

    @FindBy(css=".card-info-figure")
    public WebElement paymentInfo;

    @FindBy(xpath="//section[contains(strong/text(),'Payment Method')]/p")
    public WebElement paymentMethodUnderPaymentLabel;

    @FindBy(css=".giftcard-aplied-summary")
    public WebElement giftCardSection;

    @FindBy(css="#WC_CheckoutPaymentAndBillingAddressSummaryf_div_3_ p a")
    public WebElement editPaymentLink;

    @FindBy(css=".bag-button a.button-blue")
    public WebElement editBagButton;

    @FindBy(css=".bag-contents")
    public WebElement shopBagContents;

    @FindBy(css="#total_breakdown")
    public WebElement orderTotalSummary;

    /*@FindBy(css=".summary-title")*/
    @FindBy(css=".checkout-order-summary .order-summary")
    public WebElement title_OrderSummary;

    //    @FindBy(css="#WC_OrderItemDetailsSummaryf_div_2_1")
//    public WebElement prodDetailsFirst;
//
    @FindBy(css="#WC_OrderItemDetailsSummaryf_div_2_2")
    public WebElement prodDetailsSecond;

//    public WebElement editBagProdDetlsByPosn(int i){
//        return getDriver().findElement(By.cssSelector("#WC_OrderItemDetailsSummaryf_div_"+i+"_2"));
//    }

    public WebElement editBagProdDetlsByPosn(int i){
        return getDriver().findElement(By.cssSelector("#WC_OrderItemDetailsSummaryf_div_2_"+i));
    }

    @FindBy(css="#ErrorMessageText")
    public WebElement errorMessage;

    @FindBy(xpath = "//*[@id='singleOrderSummary0']/font")
    public WebElement btn_SubmitOrderTop;

    @FindBy(xpath = "//*[@id='singleOrderSummary']/font")
    public WebElement btn_SubmitOrderBottom;

    @FindBy(xpath = "//button[text()='Return to Billing']")
    public WebElement retToBillingLink;

    @FindBy(css=".ReactModalPortal button:nth-child(2)")
    public WebElement returnToPayPalButton;

    @FindBy(css=".registered-rewards-promo")
    public WebElement pointsEarnedEspot;

    @FindBy(xpath = "//*[@id='total_breakdown'] //span[@class='points-large-font']")
    public WebElement lbl_Points;

    @FindBy(xpath = "//*[@id='checkGiftCardBalance']")
    public WebElement btn_CheckBalance;

    @FindBy(xpath = "//a[contains(.,'apply to order')]")
    public WebElement btn_ApplyToOrder;

    @FindBy(xpath = "//*[@id='WC_SingleShipmentOrderTotalsSummary_td_2']")
    public WebElement txt_Subtotal;

    @FindBy(css=".savings-total")
    public WebElement promotionsLabel;

    @FindBy(css=".checkout-order-summary .shipping-total strong")
    public WebElement discAppliedValueLbl;

    @FindBy(xpath = "//*[@class='discount-applied']")
    public WebElement discount_Applied_BillingPage;

    @FindBy(css = ".text-price.product-offer-price")
    public WebElement discountedPrice;

    @FindBy(css = ".text-price.product-list-price")
    public WebElement listPrice;

    @FindBy(css="input[name='billing.cvv']")
    public WebElement cvvTxtField;

    @FindBy (css = ".input-common.airmiles-number input")
    public WebElement airmilesField;

    @FindBy(css = ".return-to-bag-link")
    public WebElement returnBagLink;

    @FindBy(xpath = ".//div[@class='navigation-confirmation expanded']//span")
    public WebElement overlayContent;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnBagBtn;

    @FindBy(css = ".button-quaternary.button-stay")
    public WebElement stayInCheckOut;

    @FindBy(css = ".items-total")
    public WebElement itemTotalText;

    @FindBy(xpath = ".//li[@class='balance-total']/strong")
    public WebElement estimatedTotal;

    @FindBy(css=".checkout-summary-edit.pick-up.pick-up-contact")
    public WebElement pickUpEditLnk;

    @FindBy(css = ".checkout-review-pickup-note")
    public WebElement govtText;

    @FindBy(xpath = ".//div[@class='checkout-cart-list']/h3")
    public WebElement myBagCount;

    @FindBy(css = ".button-previous-step")
    public WebElement returnBillingLnk;

    @FindBy(css = ".button-primary.button-next-step")
    public WebElement submitOrderBtn;

    //   @FindBy(xpath = ".//a[@class='logo']")
    @FindBy (xpath = ".//a[@class='logo-container logo']")
    public WebElement logo;

    @FindBy(xpath = ".//div[@class='navigation-confirmation expanded']//span")
    public WebElement messageContent;

    @FindBy(css = ".button-quaternary.button-stay")
    public WebElement stayCheckoutBtn;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnToBagBtn;

    @FindBy(xpath = ".//div[@class='checkout-summary-title'][contains(.,'Shipping')]")
    public WebElement shippingText;

    //    @FindBy(xpath = "(.//li[@class='item-shopping-cart']//h4)[1]")
    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//h4[@class='department-name']")
    public WebElement prodNameSTH;

    @FindBy(xpath = "//h4[@class='department-name']")
    public WebElement prodNameSTHAlone;

    //    @FindBy(xpath = "(.//li[@class='item-shopping-cart']//span[@class='text-color'])[1]")
    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//span[@class='text-color']")
    public WebElement prodColorSTH;

    @FindBy(css = ".text-color")
    public WebElement prodColorSTHAlone;

    //    @FindBy(xpath ="(.//li[@class='item-shopping-cart']//span[@class='text-size'])[1]")
    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//span[@class='text-size']")
    public WebElement prodSizeSTH;

    @FindBy(css = ".text-size")
    public WebElement prodSizeSTHAlone;

    //    @FindBy (xpath ="(.//li[@class='item-shopping-cart']//span[@class='text-qty'])[1]")
    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//span[@class='text-qty']")
    public WebElement prodQuantitySTH;

    @FindBy(css = ".text-qty")
    public WebElement prodQuantitySTHAlone;

    @FindBy (css =".text-price.product-list-price")
//    @FindBy(css = "container-price")
    public WebElement prodListPriceSTH;

    @FindBy (css =".text-price.product-offer-price")
    public WebElement prodOfferPriceSTH;

    @FindBy(xpath = ".//span[@class='title-list-product'][.='Pickup']")
    public WebElement pickUpText;

    //    @FindBy(xpath = "(.//li[@class='item-shopping-cart']//h4)[2]")
    @FindBy(xpath = "(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//h4[@class='department-name']")
    public WebElement prodNameBopis;

    //    @FindBy(xpath = "(.//li[@class='item-shopping-cart']//span[@class='text-color'])[2]")
    @FindBy(xpath = "(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-color']")
    public WebElement prodColorBopis;

    //    @FindBy(xpath ="(.//li[@class='item-shopping-cart']//span[@class='text-size'])[2]")
    @FindBy(xpath = "(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-size']")
    public WebElement prodSizeBopis;

    @FindBy (xpath ="(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-qty']")
//@FindBy(xpath = "(.//span[@class='text-qty'])[1]")
    public WebElement prodQuantityBopis;

    @FindBy (xpath ="(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-price product-list-price']")
    public WebElement prodListPriceBopis;

    @FindBy(css=".user-email-message")
    public WebElement useremail;

    public String prodListPrice = ".product-list-price";

    @FindBy (xpath ="(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-price product-offer-price']")
    public WebElement prodOfferPriceBopis;

    @FindBy (xpath ="(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-price product-list-price']")
    public List<WebElement> bopisListPrice;

    @FindBy (xpath ="(.//div[@class='header-list'])[contains(.,'Pickup')]/following-sibling::ul[1]//span[@class='text-price product-offer-price']")
    public List<WebElement> bopisOfferPrice;

    @FindBy(css = ".store-of-product")
    public WebElement fulfilmentCentre;

    //@FindBy(xpath = ".//ul[@class='checkout-progress-bar pickup-shipping']//button[.='Pickup']")
    @FindBy(xpath = ".//div[@class='checkout-progress-indicator']//button[.='Pickup']")
    public WebElement pickUpAccordion;

    @FindBy(xpath = ".//div[@class='checkout-progress-indicator']/ul/li/*[text()='Shipping']")
    public WebElement shippingAccordion;

    @FindBy(xpath = ".//section[@class='checkout-review-pickup-person']//div")
    public WebElement pickUpStoreSection;

    @FindBy(css = ".address-container.contact-pickup")
    public WebElement alternatePickUpStore;

    @FindBy(css="button[aria-label='Edit Billing']")//(xpath = ".//div[@class='checkout-summary-edit']//button")
    public WebElement editBillingLink;

    @FindBy(xpath = "(.//button[@class='checkout-summary-edit'])[1]")
    public WebElement editShippingAddressLink;

    @FindBy(xpath = "(.//button[@class='checkout-summary-edit'])[2]")
    public WebElement editBillingAddressLink;

    @FindBy(xpath = ".//button[@class='checkout-summary-edit']")
    public WebElement editBillForBOPISReview;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesReview;

    @FindBy(css = ".term-and-conditions-link")
    public WebElement tAndCLinkBelowSubmitOrder;

    @FindBy(xpath = ".//section[@class=\"checkout-review-payment-method\"]//p[contains(.,\"ending in\")]")
    public  WebElement last4Number;

    @FindBy(xpath = ".//div[@class=\"shipping-methods shipping-methods-form\"]//label[@for=\"method.shippingMethodId_1\"]")
    public WebElement method_AKHIPRRush;

    @FindBy(css = ".gift-card-toggle")
    public WebElement addNewGC;

    @FindBy(xpath = ".//div[@class=\"gift-card-add-fields\"]")
    public WebElement gcContent;

    @FindBy(css = ".input-common.gift-card-number")
    public WebElement cardNumberField;

    @FindBy(css = ".input-common.gift-card-pin")
    public WebElement pinNumberField;

    // @FindBy(css = ".rc-anchor.rc-anchor-normal.rc-anchor-light")
    //public WebElement recaptchaBox;

    @FindBy(xpath = ".//button[@class=\"gift-card-cancel\"]")
    public WebElement cancelGCButoon;

    @FindBy(xpath = ".//button[@class=\"gift-card-apply\"]")
    public WebElement applyGCButton;

    @FindBy(css=".button-giftcard-remove.button-tertiary")
    public WebElement removeBtn;

    @FindBy(css = ".input-checkbox-icon-unchecked")
    public WebElement saveGCToAccount;

    @FindBy(css = ".term-and-conditions-link")
    public WebElement termsAndCondLink;

    @FindBy(css = "a[title='terms']")
    public WebElement termsAndCondSection;

    @FindBy(css = ".gift-wrapping-name")
    public WebElement giftServices;

    @FindBy(css = ".giftcard-aplied-summary .balance")
    public WebElement giftCardSummary_Balance;

    @FindBy(css = ".error-box")
    public WebElement errorBox;

    @FindBy(css = ".custom-loading-icon.submit-order-spinner")
    public WebElement orderSubmitSpinner;

    @FindBy(css = ".button-primary")
    public List<WebElement> submitButtonDisabled;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesNumb;

    @FindBy(xpath="//*[text()='Shipping']/..")
    public WebElement shippingProgressBar;


    //@FindBy(xpath = "//div[@class=\"checkout-progress-indicator\"]//*[text()='Billing']")
    @FindBy(xpath = "//ul//*[text()='Billing']")
    public WebElement billingProgressBar;

    @FindBy(xpath="//*[text()='Pickup']/..")
    public WebElement pickupProgressBar;

    @FindBy(xpath = "//*[text()='Review']/..")
    public WebElement reviewProgressBar;

    @FindBy(tagName = "h2")
    public WebElement checkoutTitle;

    @FindBy(xpath="//button[text()='Billing']/..")
    public WebElement billingProgressHeading;

    @FindBy(xpath = "//div[@class=\"coupon-list-container\"]/h3[contains(.,'AVAILABLE')]")
    public WebElement couponSection;
}