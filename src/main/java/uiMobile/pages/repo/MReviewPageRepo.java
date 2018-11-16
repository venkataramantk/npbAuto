package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.pages.actions.MobileOrderLedgerAndCOSummaryActions;

import java.util.List;


/**
 * Created by JKotha on 11/14/2017.
 */
public class MReviewPageRepo extends MobileOrderLedgerAndCOSummaryActions {

    @FindBy(css = ".container-button button")
    public WebElement submitOrderButton;

    @FindBy(css = "input[name='billing.cvv']")
    public WebElement cvvTxtField;

    @FindBy(css = ".active.stage3>h2")
    public WebElement isReviewPageActive;

    @FindBy(css = ".button-primary.button-next-step")
    public WebElement submOrderButton;

    @FindBy(css = ".title-checkout")
    public WebElement expressCheckout;

    @FindBy(css = "#WC_SingleShipmentSummary_links_GoBack")
    public WebElement goBack;

    @FindBy(xpath = "(//div[@class='container'] //div[contains(@class,'popup-close')][contains(@class,'offer-close')])[1]")
    public WebElement shareTheLovePromo;

    @FindBy(css = ".checkout-review-shipping-address .address-details")
    public WebElement shippingAddressSection;

    @FindBy(css = ".checkout-review-shipping-address .address-details .name")
    public WebElement shippingAddressSectionCusName;

    @FindBy(css = ".checkout-review-shipping-address .address-details .address")
    public WebElement shippingAddressSectionAdd;

    @FindBy(css = ".checkout-review-billing-address .address-details")
    public WebElement billingAddressSection;
    
    @FindBy(css = ".checkout-review-billing-address .address-details .name")
    public WebElement billingAddressCustName;
    
    @FindBy(css = ".checkout-review-billing-address .address-details .address")
    public WebElement billingAddress;

    @FindBy(css = "#WC_CheckoutPaymentAndBillingAddressSummaryf_div_2>a:nth-of-type(1)")
    public WebElement editPhoneNumberLink;

    @FindBy(css = "#WC_CheckoutPaymentAndBillingAddressSummaryf_div_2>a:nth-of-type(2)")
    public WebElement editEmailLink;

    /*@FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'gift options') or contains(.,'Air Miles rewards') or contains(.,'opciones de regalos') or contains(.,'compenses Air Miles')]]")*/
    @FindBy(css = ".checkout-review-section .checkout-review-gift-wrapping")
    public WebElement giftWrappingSection;

    @FindBy(xpath = "//div[@class='summary-details' and h1[contains(.,'Air Miles rewards') or contains(.,'ompenses Air Miles')]]")
    public WebElement airMilesRewardsSection;

    @FindBy(css = "#gift_modal")
    public WebElement editGiftOptionsUS;

    @FindBy(css = ".summary-details>a[href*='OrderBillingView']")
    public WebElement editGiftOptionsCA;

    @FindBy(css = ".shipping-methods.shipping-methods-form")
    public WebElement shippingMethodSection;

    @FindBy(css = ".shipping-methods.shipping-methods-form label span")
    public List<WebElement> shippingMethods;

    /*@FindBy(css="div.summary-details a[href*='TCPOrderShippingView']")
    public WebElement editShippingMethod;*/

    @FindBy(css = ".checkout-review-payment-method .card-info")
    public WebElement paymentMethodSection;
    
    @FindBy(css = ".checkout-review-payment-method .card-info img.card-info-figure")
    public WebElement cardImg;

    @FindBy(css = "#WC_CheckoutPaymentAndBillingAddressSummaryf_div_3_ p a")
    public WebElement editPaymentLink;

    @FindBy(css = ".bag-button a.button-blue")
    public WebElement editBagButton;

    @FindBy(css = ".bag-contents")
    public WebElement shopBagContents;

    @FindBy(css = "#total_breakdown")
    public WebElement orderTotalSummary;

    /*@FindBy(css=".summary-title")*/
    @FindBy(css = ".checkout-order-summary .order-summary")
    public WebElement title_OrderSummary;

    //    @FindBy(css="#WC_OrderItemDetailsSummaryf_div_2_1")
//    public WebElement prodDetailsFirst;
//
    @FindBy(css = "#WC_OrderItemDetailsSummaryf_div_2_2")
    public WebElement prodDetailsSecond;

//    public WebElement editBagProdDetlsByPosn(int i){
//        return getDriver().findElement(By.cssSelector("#WC_OrderItemDetailsSummaryf_div_"+i+"_2"));
//    }

    public WebElement editBagProdDetlsByPosn(int i) {
        return getDriver().findElement(By.cssSelector("#WC_OrderItemDetailsSummaryf_div_2_" + i));
    }

    @FindBy(css = ".express-cvv div.inline-error-message")
    public WebElement errorMessage;

    @FindBy(xpath = "//*[@id='singleOrderSummary0']/font")
    public WebElement btn_SubmitOrderTop;

    @FindBy(xpath = "//*[@id='singleOrderSummary']/font")
    public WebElement btn_SubmitOrderBottom;

    @FindBy(css = ".address-container")
    public WebElement pickupInfo;

    @FindBy(css = ".savings-total")
    public WebElement promotionsLabel;


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

    /*@FindBy(css = ".checkout-summary-edit.pick-up.pick-up-contact")*/
    @FindBy(css = ".checkout-summary-edit.pick-up.pick-up-contact")
    public WebElement pickUpEditLnk;

    @FindBy(css = ".checkout-review-pickup-note")
    public WebElement govtText;

    @FindBy(css = ".accordion.accordion-cart-list h4")
    public WebElement myBagCount;

    @FindBy(xpath = "//h4[contains(.,'Bag')]")
    public WebElement myBagLink;

    //Product details
    @FindBy(css = ".department-name")
    public WebElement deptName;

    @FindBy(css = ".department-name")
    public List<WebElement> productNames;

    @FindBy(css = ".text-color")
    public WebElement productColor;

    @FindBy(css = ".text-size")
    public WebElement productSize;

    @FindBy(css = ".text-qty")
    public WebElement productQty;


    @FindBy(css = ".button-previous-step")
    public WebElement returnBillingLnk;

    @FindBy(css = ".button-primary.button-next-step")
    public WebElement submitOrderBtn;

    @FindBy(css = ".submit-order-spinner")
    public WebElement ordersubmitSpinner;

    @FindBy(css = ".error-box")
    public WebElement errorBox;

    @FindBy(xpath = ".//a[@class='logo']")
    public WebElement logo;

    @FindBy(css = ".button-quaternary.button-stay")
    public WebElement stayCheckoutBtn;

    @FindBy(xpath = ".//span[@class='title-list-product'][.='Shipping']")
    public WebElement shippingText;

    @FindBy(xpath = ".//div[@class='checkout-summary-title'][.='Shipping']/following-sibling::ul[1]//h4[@class='department-name']")
    public WebElement prodNameSTH;

    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//span[@class='text-color']")
    public WebElement prodColorSTH;

    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//span[@class='text-size']")
    public WebElement prodSizeSTH;

    @FindBy(xpath = ".//div[@class='header-list'][.='Shipping']/following-sibling::ul[1]//span[@class='text-qty']")
    public WebElement prodQuantitySTH;

    @FindBy(xpath = ".//span[@class='title-list-product'][.='Pickup']")
    public WebElement pickUpText;

    @FindBy(xpath = "(.//h4[@class='department-name''])[1]")
    public WebElement prodNameBopis;

    @FindBy(xpath = "(.//span[@class='text-color'])[1]")
    public WebElement prodColorBopis;

    @FindBy(xpath = "(.//span[@class='text-color'])[1]")
    public WebElement prodSizeBopis;

    @FindBy(xpath = "(.//span[@class='text-qty'])[1]")
    public WebElement prodQuantityBopis;

    @FindBy(css = ".store-of-product")
    public List<WebElement> fulfilmentCentre;

    @FindBy(xpath = "//button[@type='button'][contains(text(),'Pickup')]")
    public WebElement pickUpAccordion;

  //  @FindBy(xpath = "//button[@type='button'][contains(text(),'Shipping')]")
    @FindBy(xpath = "//html//li[1]/button[1][contains(text(),'Shipping')]|//html//li[2]/button[1][contains(text(),'Shipping')]")
    public WebElement shippingAccordion;

    @FindBy(xpath = "//html//li[2]/button[1][contains(text(),'Billing')]|//html//li[3]/button[1][contains(text(),'Billing')]")
    public WebElement billingAccordion;

    @FindBy(xpath = ".//section[@class='checkout-review-pickup-person']//div")
    public WebElement pickUpStoreSection;

    @FindBy(css = ".giftcard-aplied-summary")
    public WebElement giftCardAppliedSection;

    @FindBy(css = ".giftcard-apply-summary")
    public WebElement giftcardsAvailableSection;

    @FindBy(css = "form.gift-card-add-form button.gift-card-toggle")
    public WebElement newGiftCardBtn;

    @FindBy(css = ".giftcard-apply-summary p.balance")
    public WebElement giftCardLast4Digits;
    
    @FindBy(css = ".giftcard-aplied-summary p.balance")
    public WebElement giftCardLast4DigitsAppliedSec;

    @FindBy(css = ".giftcards-total>strong")
    public WebElement giftCardsTotal;

    @FindBy(css = ".address-container.contact-pickup")
    public WebElement alternatePickUpStore;

    @FindBy(xpath = "//div[@class='checkout-review-section checkout-review-billing']//div[@class='checkout-summary-title']//button[@type='button']")
    public WebElement editBillingLink;

    @FindBy(css = ".term-and-conditions-checkout>a:nth-of-type(1)")//".term-and-conditions-checkout a")
    public WebElement termsAndCondLink;

    @FindBy(css = ".header-hc.help-center-header h1")
    public WebElement helpCentreText;

    @FindBy(css = "button.button-giftcard-apply")
    public WebElement applyGCButton;

    @FindBy(css = ".button-giftcard-remove.button-tertiary")
    public WebElement removeBtn;

    @FindBy(xpath = "//div[@class='checkout-review-section checkout-review-shipping']//div[@class='checkout-summary-title']//button[@type='button']")
    public WebElement editShippingAddress;

    @FindBy(css = "//button[@type='button'][contains(text(),'Remove')]")
    public WebElement couponRemoveButton;

    @FindBy(css = ".text-price.product-list-price")
    public WebElement originalPrice;
    
    @FindBy(css = ".text-price.product-offer-price")
    public WebElement offerPrice;

    @FindBy(xpath = "//div[text()=\"Oops... an error occured\"]")
    public WebElement OopsErrorBox;

    @FindBy(css = ".accordion.accordion-cart-list button.accordion-toggle")
    public WebElement myBagToggleBtn;

    @FindBy(css = ".inline-error-message")
    public WebElement cvvInlineError;

    @FindBy(xpath = ".//div[@class=\"ghost-error-container\"][contains(.,\"There is no balance for the account provided.\")]")
    public WebElement zeroBalanceGiftCardError;

    @FindBy(css = ".checkout-progress-bar")
    public WebElement checkoutProgressBar;

    @FindBy(css = ".checkout-progress-bar li")
    public List<WebElement> accordians;

    @FindBy(css = ".button-exit-checkout")
    public WebElement backBtn;

    
   // @FindBy(css="section.checkout-review-pickup-alternate p.address-details.contact-pickup")
    @FindBy(xpath="//div[@class='address-container contact-pickup']//p/span[@class='name']")
    public WebElement altPickupName;
    
    @FindBy(xpath="//section[@class='checkout-review-pickup-alternate']//p[@class='address-additional contact-pickup']")
    public WebElement altPickupEmail;
    
    @FindBy(css = ".shipping-method-title")
    public WebElement shippingMethodText;

    @FindBy(xpath = "//div[@class='shipping-methods shipping-methods-form shipping-methods-input-box']/fieldset/label/span")
    public List<WebElement> availableMethods;

    @FindBy(css = ".estimated-shipping-rate")
    public WebElement estimatedDays;

    public WebElement shippingMethodRadioButtonByValue(String value) {
        return getDriver().findElement(By.xpath("//div//span[contains(text(),'" + value + "')]"));
    }

    @FindBy(xpath="//section[@class='checkout-review-shipping-address']//span[@class='name']")
    public WebElement custShippingName;
    
    @FindBy(xpath="//section[@class='checkout-review-shipping-address']//span[@class='address']")
    public WebElement custShppingAddress;
    
    @FindBy(xpath="//p[@class='address-additional']")
    public WebElement custEmailPhn;
    
    @FindBy(xpath="//section[@class='checkout-review-pickup-person']//span[@class='name']")
    public WebElement pickUpName;
    
    @FindBy(xpath="//aside[@class='checkout-review-pickup-note']")
    public WebElement pickUpNote;
    
    @FindBy(xpath="//div[@class='label-checkbox input-checkbox alternate-pickup']")
    public WebElement altPickUpChkBox;
    
    @FindBy(css = "div.tooltip-cvv")
    public WebElement tooltipCvv;


    @FindBy(css = ".gift-card-toggle")
    public WebElement addNewGC;

    @FindBy(css = ".input-common.gift-card-number input")
    public WebElement cardNumberField;

    @FindBy(css = ".input-common.gift-card-pin input")
    public WebElement pinNumberField;

    @FindBy(xpath = ".//div[@class=\"gift-card-add-fields\"]")
    public WebElement gcContent;

    @FindBy(css = ".button-giftcard-remove")
    public WebElement removeButtonOnGC;
    
    @FindBy(css = "button.gift-card-cancel")
    public WebElement cancelGCButton;

    @FindBy(css = ".checkout-summary-edit.summary-title-pick-up-contact")
    public WebElement editLinkPickUp;

    @FindBy(css = ".checkout-review-section .checkout-review-gift-wrapping p")
    public WebElement giftServiceText;

    @FindBy(css = ".checkout-review-section .checkout-review-gift-wrapping strong")
    public WebElement giftServiceLabel;

    @FindBy(css = ".checkout-review-shipping-address strong")
    public WebElement shippingAddLabel;

    @FindBy(css = ".shipping-methods.shipping-methods-form legend")
    public WebElement shippingMethodLabel;
    
    @FindBy(css = "li.giftcard-aplied-summary")
    public List<WebElement> appliedGiftCards;
    
    @FindBy(css = ".checkout-review-container.express.mobile-checkout-field div div")
    public List<WebElement> orderReviewContents;

    @FindBy(css=".card-info")
    public WebElement cardInfo;

}
