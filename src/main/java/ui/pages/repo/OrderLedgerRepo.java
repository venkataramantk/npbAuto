package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 3/31/2017.
 */
public class OrderLedgerRepo extends UiBase {
    @FindBy(css=".items-total strong")
    public WebElement itemsTotalPrice;

    @FindBy(css=".items-total")
    public WebElement itemsCount;

    @FindBy(css=".shipping-total>strong")
    public WebElement shippingTotalPrice;

    @FindBy(css=".shipping-total")
    public WebElement shippingTxt;

    @FindBy(css=".tax-total>strong")
    public WebElement tax_Total;

    @FindBy(css=".tax-total")
    public WebElement tax_TotalTxt;

    @FindBy(css=".balance-total>strong")
    public WebElement estimatedTotal;

    @FindBy(xpath="//ol[@class='order-summary']/li")
    public List<WebElement> orderSummaryLineItems;

    @FindBy(xpath = ".//li[@class='coupons-total']//strong")
    public WebElement couponCodeApplied;

    @FindBy(xpath = ".//li[@class='shipping-total']//strong")
    public WebElement freeShippingCouponApplied;

    public String getShippingTotalPrice(){
        return getText(shippingTotalPrice).replaceAll("[^0-9.][^a-z]","");
    }

    @FindBy(xpath = ".//div[@class=\"coupons-container\"]//div[@class=\"error-box\"]")
    public WebElement couponCodeError;

    @FindBy(css = ".giftcards-total>strong")
    public WebElement giftCardsTotal;

    @FindBy(css = ".estimated-total>strong")
    public WebElement estimatedTotalPrice;

    @FindBy(css = ".checkout-order-summary")
    public WebElement orderLedgerSection;

    @FindBy(css = ".modal-content.couponTable")
    public WebElement needHelpCouponTable;

    //    @FindBy(css = "#promoCode")
    @FindBy(xpath = ".//input[@name='couponCode']")
    public WebElement couponCodeFld;

    @FindBy(xpath = ".//div[@class='available-rewards apply-coupons apply-rewards']//h3[.='APPLIED COUPONS']")
    public WebElement appliedCouponText;

    @FindBy(css = ".button-apply-coupons")//"//div[@class='coupons-container']/div/form/label/button")//.//button[@type='submit'][text()='Apply']")
    public WebElement applyCouponButton;

    @FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    public WebElement removeCoupon;

    @FindBy(xpath = ".//li[@class='coupons-total']//strong")
    public WebElement orderPromo;

    @FindBy(css = ".coupon-help-link")
    public WebElement needHelpLink;

    @FindBy(css = ".checkout-order-summary .order-summary")
    public WebElement orderSummarySection;

    @FindBy(css = ".coupon.applied-coupon-notice .apply-coupons-button")
    public List<WebElement> removeButtonsOfCoupons;

    @FindBy(xpath = ".//li[@class='savings-total']//strong")
    public WebElement promotionApplied;

    public WebElement applyToOrderByPos(int i){
        return getDriver().findElement(By.cssSelector("li:nth-child("+i+") .apply-coupons-button"));
    }

    public WebElement applyToOrderLTY_ByPos(int i){
        return getDriver().findElement(By.xpath("(//div[@class='information-coupon'][contains(.,'LOYALTY')])["+i+"]/following-sibling::button"));
    }
    public WebElement applyToOrderPlaceCash_ByPos(int i){
        return getDriver().findElement(By.xpath("(//div[@class='information-coupon'][contains(.,'PLACE CASH')])["+i+"]/following-sibling::button"));
    }
    public WebElement merchandiseApplyButton(String coupon){
        return getDriver().findElement(By.xpath("//li[contains(.,'"+coupon+"')]//button[@class='apply-coupons-button']"));
    }

    public WebElement merchandiseRemoveButton(String coupon){
        return getDriver().findElement(By.xpath("(//li[@class='coupon applied-coupon-notice'])[contains(.,'"+coupon+"')]//button[@class='apply-coupons-button']"));
    }

    @FindBy(xpath = "//li[contains(.,'FREE GROUND SHIPPING')]//button[@class='apply-coupons-button']")
    public WebElement freeShippingApplyButton;

    @FindBy(xpath = "(//li[@class='coupon applied-coupon-notice'])[contains(.,'FREE GROUND SHIPPING')]//button[@class='apply-coupons-button']")
    public WebElement freeShippingRemoveButton;

    @FindBy(css = ".summary-message-title strong")
    //@FindBy(css = ".summary-message-title span")
    public WebElement ltyEarnPoints;

    @FindBy(css = ".image-coupon")
    public List<WebElement> rewardsSavingsImgs;

    @FindBy(xpath = "(//div[@class='coupon-list-container'])[contains(.,'AVAILABLE REWARDS')]//strong[@class='coupon-value']")
    public List<WebElement> rewardsCouponNames;

    @FindBy(css = ".expire-information")
    public List<WebElement> rewardsExpireDates;

    @FindBy(xpath = "//div[@class='information-coupon'][contains(.,'LOYALTY')]/span[@class='expire-information']")
    public List<WebElement> rewardsLTYExpireDates;

    @FindBy(xpath = "//div[@class='information-coupon'][contains(.,'PLACE CASH')]/span[@class='expire-information']")
    public List<WebElement> placeCashExpireDates;

    @FindBy(css = ".link-to-details")
    public List<WebElement> rewardsDetailsLinks;

    @FindBy(css = ".apply-coupons-button")
    public List<WebElement> rewardsApplyToOrder;

    @FindBy(css = ".coupon-content")
    public List<WebElement> rewardsContentDisplay;

    @FindBy(css = ".button-view-all")
    public WebElement showMoreLnk;

    @FindBy(css = ".gift-wrap-total")
    public WebElement giftWrapping_OrderLedger;

    @FindBy(css = ".coupons-total")
    public WebElement coupon_Label;

    @FindBy(xpath = ".//div[@class=\"information-coupon\"]//strong[contains(.,\"BOPIS GEN\")]")
    public  WebElement bopisGenCoupon;

    @FindBy(xpath = ".//div[@class=\"information-coupon\"]//strong[contains(.,\"BOPIS PLCC\")]")
    public WebElement bopisPLCCCoupon;

    @FindBy(xpath = ".//div[@class=\"information-coupon\"]//strong[contains(.,\"BOPIS MPR\")]")
    public WebElement bopisMPRCoupon;

    @FindBy(xpath = ".//*[@class='item-product'][contains(.,'gift card')]//span[@class='text-price product-offer-price']")
    public WebElement giftcardAmount;

    @FindBy(name = "pickUpContact.smsInfo.wantsSmsOrderUpdates")
    public WebElement transactionalSMSCheckbox;

    @FindBy(xpath = "//div[@class=\"label-checkbox input-checkbox get-sms-updates\"]//*[@class=\"input-checkbox-icon-unchecked\"]")
    public WebElement transactionalSMSCheckboxUnChecked;

    @FindBy(name = "pickUpContact.smsInfo.smsUpdateNumber")
    public WebElement transactionalNumber;


    @FindBy(css = ".checkout-sms-order-status-signup div label div:nth-child(1) input")
    public WebElement transactionalSMSCheckboxInput;


    @FindBy(css = ".sms-disclaimer")
    public WebElement smsPrivacyPolicy;

    @FindBy(css = ".sms-wrapping-fields .inline-error-message")
    public WebElement smsInlineErrorMessage;
}

