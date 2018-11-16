package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

public class OrderLedgerRepo extends MobileHeaderMenuRepo {

    @FindBy(css = ".text-price.product-offer-price")
    public WebElement offerPrice;

    @FindBy(xpath = ".//li[@class='balance-total']/strong")
    public WebElement estimatedTotal;

    @FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    public WebElement removeCoupon;

    @FindBy(css=".accordion.coupon-accordion")
    public WebElement couponCodeFieldArea;

    @FindBy(css = ".coupon-code-form input")//#promoCode")
    public WebElement couponCodeFld;

    @FindBy(css=".accordion.coupon-accordion button.accordion-toggle")
    public WebElement couponToggleButton;

    @FindBy(xpath = ".//li[@class='coupons-total']//strong")
    public WebElement couponCodeApplied;

    @FindBy(xpath = ".//div[@class='available-rewards apply-coupons apply-rewards']//h3[.='APPLIED COUPONS']")
    public WebElement appliedCouponText;

    @FindBy(css = ".button-apply-coupons")
    public WebElement applyCouponButton;

    @FindBy(css = ".checkout-order-summary .coupons-total")
    public WebElement coupnField;

    @FindBy(css = ".accordion.order-summary-accordion button.accordion-toggle")
    public WebElement orderSummaryToggleBtn;
    
    @FindBy(css = ".accordion.order-summary-accordion h4.accordion-button-toggle")
    public WebElement orderSummaryToggleTxt;

    @FindBy(xpath = ".//li[@class='items-total']/strong")
    public WebElement itemPrice;

    @FindBy(xpath = ".//li[@class='balance-total']//strong")
    public WebElement estimatedTot;

    @FindBy(xpath = ".//li[@class='tax-total']/strong")
    public WebElement estimatedTax;

    @FindBy(css = ".estimated-total>strong")
    public WebElement estimatedTotalPrice;

    @FindBy(css = ".checkout-order-summary .order-summary")
    public WebElement orderSummarySection;

    @FindBy(xpath = ".//li[@class='shipping-total']/strong")
    public WebElement shippingText;


    @FindBy(xpath = "//div[@class='accordion order-summary-accordion']//h4[@class='accordion-button-toggle']//button[@type='button']")
    public WebElement OrderSummeryExpandBtn;

    @FindBy(xpath = ".//li[@class='tax-total']//strong")
    public WebElement taxTotal;

    @FindBy(xpath = ".//li[@class='items-total']//strong")
    public WebElement itemTotal;

    @FindBy(xpath = ".//li[@class='coupons-total']")
    public WebElement couponPrice;

    @FindBy(xpath = ".//li[@class='savings-total']//strong")
    public WebElement promotionsTot;

    @FindBy(xpath = ".//li[@class='shipping-total']//strong")
    public WebElement shippingTot;

    @FindBy(xpath = ".//li[@class='balance-total']")
    public WebElement estimatedTtlLineItem;
    //Raman
    @FindBy(css="button.coupon-help-link")
    public WebElement needHelpCouponLnk;

    @FindBy(css = ".coupon-help-link")
    public WebElement couponHelp;

    @FindBy(xpath="//div[@title='Coupon Help']")
    public WebElement couponHelpPage;

    @FindBy (css="button.button-modal-close")
    public WebElement helpCouponCloseBtn;

    @FindBy(xpath = ".//li[@class='shipping-total']")
    public WebElement shipTtlLineItem;
    
    @FindBy(xpath = ".//li[@class='tax-total']")
    public WebElement taxTtlLineItem;

    @FindBy(xpath = ".//li[@class='items-total']")
    public WebElement itemPriceTtlLineItem;

    @FindBy(xpath = ".//li[@class='giftcards-total']")
    public WebElement giftCardTtlLineItem;
    
    @FindBy(xpath = ".//li[@class='gift-wrap-total']")
    public WebElement giftServiceTtlLineItem;
    
    @FindBy(xpath = ".//li[@class='gift-wrap-total']/strong")
    public WebElement giftServiceTtlValue;

    @FindBy(xpath = ".//li[@class='giftcards-total']/strong")
    public WebElement giftCardTot;

    public WebElement lineItemField(String no) {
        return getDriver().findElement(By.cssSelector(".order-summary li:nth-of-type("+no+")"));
    }
    @FindBy(css = ".order-summary li")
    public List<WebElement> list_OrderLedger;

    @FindBy(xpath="//li[@class='giftcards-total']")
    public WebElement GiftCardLineitem;

    @FindBy(xpath="//li[@class='giftcards-total']//strong")
    public WebElement GiftCardLineitemPrice;

    @FindBy(css=".checkout-review-shipping-address .address-additional span")
    public WebElement emailIdShippingAdd;

    @FindBy(xpath=".//li[@class='tax-total']")
    public WebElement estimatedTaxLineItem;

    @FindBy(css = ".card-suffix")
    public WebElement cardSuffix;
    
    @FindBy(css = "div.coupon-list-container")
    public WebElement couponList;
        
    @FindBy(css= "li.coupon:nth-child(2) div.coupon-content div:nth-child(1) > button.apply-coupons-button")
    public WebElement applyButtonFrmCouponList;

    @FindBy(css=".items-total strong")
    public WebElement itemsTotalPrice;

    @FindBy(css=".items-total")
    public WebElement itemsCount;
    
    @FindBy(xpath="//h3[contains(text(),'APPLIED COUPONS')]")
    public WebElement appliedCouponContainer;
    
    @FindBy(css="li.coupon.applied-coupon-notice div.coupon-content div:nth-child(1) div.information-coupon > button.link-to-details")
    public WebElement appliedCouponDetailsLink;
    
    @FindBy(css="li.coupon:nth-child(2) div.coupon-content div:nth-child(1) >img.image-coupon")
    public WebElement CouponImg;
    
    @FindBy(css="li.coupon:nth-child(2) div.coupon-content div:nth-child(1) div.information-coupon > strong.coupon-value")
    public WebElement CouponName;
    
    @FindBy(css="li.coupon:nth-child(2) div.coupon-content div:nth-child(1) div.information-coupon > span.expire-information")
    public WebElement CouponExpiration;
    
    @FindBy(css="li.coupon:nth-child(2) div.coupon-content div:nth-child(1) div.information-coupon > button.link-to-details")
    public WebElement CouponDetailsLinkOnCouponList;

    @FindBy(css = ".giftcards-total>strong")
    public WebElement giftCardsTotal;

    @FindBy(xpath = "//div[@class='accordion accordion-expanded order-summary-accordion']//h4[@class='accordion-button-toggle']//button[@type='button']")
    public WebElement expandToggleBtn;
    
    public WebElement merchandiseApplyButton(String coupon){
        return getDriver().findElement(By.xpath("//li[contains(.,'"+coupon+"')]//button[@class='apply-coupons-button']"));
    }
    public WebElement merchandiseRemoveButton(String coupon){
        return getDriver().findElement(By.xpath("(//li[@class='coupon applied-coupon-notice'])[contains(.,'"+coupon+"')]//button[@class='apply-coupons-button']"));
    }

    @FindBy(xpath = ".//li[@class='savings-total']")
    public WebElement promotionsLineItem;

    @FindBy(css = ".estimated-total")
    public WebElement estmdTtlLineItem;

    @FindBy(css = ".button-view-all")
    public WebElement showMoreLnk;
    
    public WebElement applyToOrderButtonForText(String text) {
        return getDriver().findElement(By.xpath("//div/strong[contains(.,'"+text+"')]/ancestor::div/button[contains(@class,'apply-coupons-button')]"));
    }

    @FindBy(css = ".error-box")
    public WebElement couponErrorMessage;
}