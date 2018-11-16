package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.pages.actions.MobileOrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by JKotha on 09/11/2017.
 */
public class MShoppingBagPageRepo extends MobileOrderLedgerAndCOSummaryActions {

    @FindBy(css = ".subheading-my-bag-title")
    public WebElement heading;

    @FindBy(css = ".subheading-button-continue-shopping")
    public WebElement continueShoppingLink;

    @FindBy(css = ".coupon-list-container h3")
    public WebElement couponContainerHeading;

    @FindBy(css = ".accordion-button-toggle")
    public WebElement couponCollapsed;

    @FindBy(css = ".accordion.accordion-expanded.coupon-accordion")
    public WebElement couponContainer;

    @FindBy(css = ".subheading-my-bag")
    public WebElement estimatedTotalAtTop;

    public WebElement upcNo(String productName) {
        return getDriver().findElement(By.xpath("//a[text()='" + productName + "']/../following-sibling::h4"));
    }

    @FindBy(css = ".button-cancel")
    public WebElement cancelButton;

    @FindBy(css = ".button-global-update")
    public WebElement updateButton;

    public WebElement productProperties(String upc) {
        return getDriver().findElement(By.xpath("//*[contains(text(),'" + upc + "')]/ancestor::figure/figcaption"));
    }

    public WebElement getProductPrice(String productName) {
        return getDriver().findElement(By.xpath("//a[text()='" + productName + "']/../../following-sibling::figcaption//div[2]/span"));
    }

    public WebElement removeByProductName(String name) {
        return getDriver().findElement(By.xpath("//a[text()=" + name + "]/../../preceding-sibling::div/button"));
    }

    public WebElement favIcon(String name) {
        return getDriver().findElement(By.xpath("//a[text()='" + name + "']/../../following-sibling::div[2]/button"));
    }

    public WebElement editProduct(String name) {
        return getDriver().findElement(By.xpath("//a[text()='" + name + "']/../../following-sibling::figcaption//button[@class='button-edit-product']"));
    }

    @FindBy(css = "select[name='quantity']")
    public WebElement qtyDropdown;

    @FindBy(xpath = ".//li[@class='balance-total']/strong")
    public WebElement estimatedTotal;

    @FindBy(xpath = ".//input[@name='couponCode']")
    public WebElement couponCodeFld;

    @FindBy(css = ".button-apply-coupons")
    public WebElement applyCouponButton;

    //Product Edit
    @FindBy(css = ".custom-select-common.bag-item-color-select.bag-item-color-select-closed")
    public WebElement colorcombobox;

    @FindBy(xpath = "//div[@class = 'item-common bag-item-color-select-item']")
    public List<WebElement> unselectedcolors;

    @FindBy(css = "div.list-container>ul>li")
    public List<WebElement> dropdownValues;

    @FindBy(css = ".notification-inline")
    public WebElement updateMsg;

    @FindBy(css = ".item-product .product-title")
    public List<WebElement> itemDescElements;

    @FindBy(css = ".item-shopping-cart .product-title h4 a")
    public List<WebElement> prodTitles;


    public WebElement removeLinkByItemPosition(int i) {
        return getDriver().findElement(By.xpath("(//li[@class='item-shopping-cart']//button[@class='button-remove'])[" + i + "]"));
    }

    @FindBy(css = ".empty-bag")
    public WebElement emptyShoppingBag;

    @FindBy(css = ".subheading-my-bag-title.viewport-container")
    public WebElement shoppingBagTitle;

    @FindBy(xpath = "//span[contains(@class,'text-price product-offer-price')]")
    public WebElement totalPriceFirst;

    @FindBy(css = ".button-edit-product")
    public WebElement editBtnLnk;

    public WebElement editButton(String upc) {
        return getDriver().findElement(By.xpath("//h4[text()='" + upc + "']//ancestor::figure//button[@class='button-edit-product']"));
    }

    @FindBy(css = ".container-description-item .text-qty")
    public List<WebElement> quantityLblFirst;

    @FindBy(css = ".button-secondary.button-pay-with-paypal span img")
    public WebElement paypalBtn;

    @FindBy(css = ".xcomponent-component-frame.xcomponent-visible")
    public WebElement payPalFrame;

    @FindBy(css = "#paypal-animation-content img")
    public WebElement payPalBtn;

    @FindBy(css = "div.empty>h1")
    public WebElement emptyBagMessage;

    public WebElement moveToWLByPosition(int i) {
        return getDriver().findElement(By.xpath("(.//button[@class='button-add-to-wishlist'])[" + i + "]"));
    }

    //DT2 Page objects


    @FindBy(css = ".airmiles-form.summary-message")
    public WebElement CA_epsot;


    public WebElement wlIconByPosition(int pos) {
        return getDriver().findElement(By.cssSelector(".item-shopping-cart:nth-child(" + pos + ") .button-add-to-wishlist"));
    }


    @FindBy(css = ".giftcards-total>strong")
    public WebElement giftCardsTotal;


    @FindBy(css = ".text-under")
    public WebElement airmiles_Text;

    @FindBy(css = ".items-total")//".container-items-with-tax .items")
    public WebElement itemPriceSummary;

    @FindBy(css = ".button-remove")
    public WebElement closeIcon;

    @FindBy(css = ".button-primary.button-checkout")
    public WebElement checkoutBtn;

    /*@FindBy(css = ".button-cart")
    public WebElement checkoutBtn;*/

    @FindBy(css = ".button-primary.login-button")
    public WebElement loginFrombagButton;

    @FindBy(xpath = "//a[text()='Continue as Guest']")
    public WebElement continueAsGuest;

    @FindBy(css = ".item-product.loading")
    public WebElement prodLoadingSymbol;

    @FindBy(css = ".button-change-store")
    public WebElement changeStoreLnk;

    @FindBy(css = ".container-information-pick-up-in-store>strong")
    public WebElement pickUpInStoreName;

    @FindBy(css = ".label-radio.input-radio.ship-it div")
    public List<WebElement> shipItCheckBoxes;

    @FindBy(css = ".input-radio-icon-unchecked")
    public WebElement STHCheckBox;

    @FindBy(xpath = "//label[@class='label-radio input-radio ship-it']/div/preceding::a[@class='department-name']")
    public List<WebElement> shipItProductName;

    @FindBy(xpath = "//label[contains(.,'I’ll pick up in store')]/div[@class='input-radio-icon-checked']")
    public List<WebElement> pickInStoreChecked;

    @FindBy(xpath = "//*[contains(.,'I’ll pick up in store')]/div[@class='input-radio-icon-checked']/ancestor::li/div/figure/div[2]/h4/a[@class='department-name']")
    public List<WebElement> pickInStoreProdName;

    @FindBy(xpath = "//*[contains(.,'I’ll pick up in store')]/div[@class='input-radio-icon-checked']/ancestor::li/div/figure/figcaption/div/p/span[2]")
    public List<WebElement> getFitForPickInStoreProdName;

    @FindBy(xpath = "//*[contains(.,'I’ll pick up in store')]/div[@class='input-radio-icon-checked']/ancestor::li/div/figure/figcaption/div/p/span[3]")
    public List<WebElement> getSizeForPickInStoreProdName;

    public WebElement removeFromSelectedShipItProdByPos(int pos) {
        return getDriver().findElement(By.xpath("(//div[@class='item-product'][contains(.,'Ship it')])[" + pos + "]/figure/div/button[@class='button-remove']"));
    }

    public WebElement editFromSelectedShipItRadioByPos(int pos) {
        return getDriver().findElement(By.xpath("(//figcaption[@class='product-description'][contains(.,'Ship it')])[" + pos + "]/div/button"));
    }

    public WebElement qtyPresentForNormalProdByPos(int pos) {
        return getDriver().findElement(By.xpath("(//figcaption[@class='product-description'][contains(.,'Ship it')])[" + pos + "]/div[1]//p/span[@class='text-qty']"));

    }

    @FindBy(css = ".notification-inline>p")
    public WebElement notification_ItemUpdated;

    @FindBy(xpath = "//div[@class='item-product'][contains(.,'Change Store')]/figure/div/button[@class='button-remove']")
    public List<WebElement> removeButtonForBopisProd;

    @FindBy(xpath = "//figcaption[@class='product-description'][contains(.,'Change Store')]/div/button")
    public List<WebElement> editLinksForBopisProd;

    @FindBy(xpath = "//figcaption[@class='product-description'][contains(.,'Change Store')]/div/p/span[@class='text-qty']")
    public List<WebElement> qtyPresentForBopisProd;

    @FindBy(xpath = "//label[contains(.,'I’ll pick up in store')]/div[1]")
    public List<WebElement> bopisRadioChkBoxes;

    @FindBy(xpath = "//label[@class='label-radio input-radio'][contains(.,'I’ll pick up in store')]/div[1]")
    public List<WebElement> bopisRadioUnChkedBoxes;

    @FindBy(xpath = ".//div[@class='custom-select-button bag-item-color-select-button-closed']/span")
    public List<WebElement> availableColors;

    @FindBy(css = ".button-change-store")
    public List<WebElement> changeAStoreLinks;

    @FindBy(xpath = ".//label[@class='select-common select-size mini-dropdown']//option")
    public List<WebElement> listOfSize;

    @FindBy(css = ".//label[@class='select-common select-size mini-dropdown']/span[@class='selection select-option-selected']")
    public WebElement selectedSize;

    @FindBy(xpath = ".//li[@class='items-total']/strong")
    public WebElement itemPrice;


    @FindBy(xpath = ".//li[@class='coupons-total']/strong")
    public WebElement couponCodeApplied;

    @FindBy(xpath = ".//div[@class='available-rewards apply-coupons apply-rewards']//h3[.='APPLIED COUPONS']")
    public WebElement appliedCouponText;

    @FindBy(css = ".guest-rewards-promo-container.summary-message")
    public WebElement mprContainer;

    @FindBy(css = ".registered-rewards-promo-container.summary-message")
    public WebElement mprContainerReg;

    @FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    public WebElement removeCoupon;
    
    @FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    public List<WebElement> removeCouponList;

    @FindBy(xpath = ".//li[@class='coupons-total']//strong")
    public WebElement orderPromo;

    @FindBy(css = ".text-price.product-offer-price")
    public WebElement offerPrice;

    @FindBy(css = ".text-price.product-list-price")
    public WebElement originalPrice;

    @FindBy(xpath = ".//li[@class='coupons-total']")
    public WebElement couponText;

    @FindBy(css = ".text-price.product-list-price")
    public List<WebElement> listOfOriginalPrice;

    @FindBy(css = ".subheading-my-bag-title")//.subheading-my-bag-title.viewport-container")
    public WebElement titleText;

    @FindBy(css = ".button-cart")
    public WebElement shoppingBagIcon;

    @FindBy(css = ".department-name")
    public List<WebElement> prodNames;

    @FindBy(css = ".upc-number")
    public WebElement upcNumber;

    @FindBy(css = ".text-color")
    public WebElement prodColor;

    @FindBy(xpath = "//span[contains(text(),'Size')]")
    public WebElement prodSize;

    @FindBy(css = ".text-qty")
    public WebElement prodQty;

    @FindBy(css = ".guest-rewards-promo")
    public WebElement plccEspot;

    @FindBy(xpath = ".//div[@class='coupons-container']//h3")
    public WebElement availableRewardsTxt;

    @FindBy(css = ".button-view-all")
    public WebElement showMoreLnk;

    @FindBy(css = ".error-box")
    public WebElement invalidErrMsg;

    @FindBy(css = ".error-box")
    public List<WebElement> errorBox;

    @FindBy(css = ".button-add-to-wishlist")
    public WebElement wlLink;

    public WebElement getUPCNumWebElementByPosition(int i) {
        return getDriver().findElement(By.xpath("(//div[@class='product-title']//*[@class='upc-number'])[" + i + "]"));
    }

    @FindBy(css = ".container-list-shopping-cart select[name='size']")
    public WebElement valueDetails;

    @FindBy(css = ".container-list-shopping-cart select[name='quantity']")
    public WebElement qtyDetails;

    @FindBy(css = ".button-global-update")
    public WebElement globalUpdatedBtn;

    @FindBy(css = ".container-list-shopping-cart select[name='quantity'] option")
    public List<WebElement> qtyList;

    @FindBy(xpath = "//*[@class='container-description-item']/span[@class='text-size']")
    public WebElement selectedValueDetails;

    @FindBy(xpath = "//*[@class='container-description-item']/span[@class='text-qty']")
    public WebElement selectedQtyDetails;

    @FindBy(css = ".overlay-my-bag .notification-inline>p")
    public WebElement updateNotification;

    @FindBy(xpath = "//*[@class='fpo-banner-rewards-slot'] //a[@href='/ca/content/air-miles']")
    public WebElement airmilesLink;

    @FindBy(css = ".am-main-image")
    public WebElement airmilesImage;

    @FindBy(css = ".item-shopping-cart")
    public List<WebElement> itemsInCart;

    @FindBy(css = ".container-information-pick-up-in-store>strong")
    public List<WebElement> pickUpInStoreNameList;

    @FindBy(css = ".label-radio.input-radio div.input-radio-disabled.input-radio-icon-unchecked")
    public List<WebElement> bopisRadioButtonDisabled;

    public String couponApplyBtn = ".apply-coupons-button";

    public String couponDetailsBtn = ".link-to-details";

    @FindBy(css = ".label-radio.input-radio.ship-it>div>input")
    public List<WebElement> shipItRadioButton;

    @FindBy(xpath = "//div[contains(@class,'notification-inline') and contains(@class,'notification-unavailable')]/p[contains(.,'Sorry, pick up in store is unavailable outside the US. Please ship the items instead or remove them from your bag.')]")
    public WebElement bopisNotAvailableNotification;

    @FindBy(css = ".radio-button-container label:nth-of-type(2) input")
    public List<WebElement> bopisRadioButton;

    @FindBy(css = ".item-shopping-cart .product-title h4 a")
    public WebElement prodTitlesSingle;

    @FindBy(css = ".item-shopping-cart .container-image a img")
    public WebElement prodImage;

    @FindBy(css = ".item-shopping-cart .container-image a img")
    public List<WebElement> products;

    @FindBy(xpath = "//span[contains(@class,'text-price product-offer-price')]")
    public List<WebElement> totalPriceFirstList;

    @FindBy(xpath = "//div[@class='item-product'][contains(.,'Change Store')]/figure/div/button[@class='button-add-to-wishlist']")
    public List<WebElement> WLButtonForBopisProd;

    @FindBy(css = ".list-container li")
    public List<WebElement> listOfGC;

    @FindBy(css = ".custom-select-button.bag-item-color-select-button-closed")
    public WebElement designDropDown;

    @FindBy(css = ".button-go-shopping.button-primary")
    public WebElement goShoppingLink;

    @FindBy(css = "p.summary-message-title strong")
    public WebElement pointsEarned;

    @FindBy(css = ".airmiles-form.summary-message img")
    public WebElement airmilesLogo;

    @FindBy(css = ".input-common.airmiles-number input")
    public WebElement airmilesTxtFld;

    @FindBy(css = ".input-common.airmiles-number .input-title")
    public WebElement airmilesInputTitle;

    @FindBy(css = ".button-tooltip-container.airmiles-number-help")
    public WebElement airmilesToolTip;

    @FindBy(css = ".button-tooltip-container.airmiles-id-information")
    public WebElement airmilesPromoIdToolTip;

    @FindBy(css = ".input-common.airmiles-id .input-title")
    public WebElement airmilesPromoTitle;

    @FindBy(css = ".input-common.airmiles-id input")
    public WebElement airmilesPromoTxtFld;

    @FindBy(css = ".badge-item-container.inline-badge-item")
    public List<WebElement> inlineBadgeDisplay;

    @FindBy(css = ".guest-rewards-promo h3")//.badge-item-container.inline-badge-item")
    public WebElement loyaltyPointTextGuest;

    @FindBy(css = ".registered-rewards-promo p")
    public WebElement loyaltyPointReg;

    public WebElement applyToOrderButtonForText(String text) {
        return getDriver().findElement(By.xpath("//div/strong[contains(.,'" + text + "')]/ancestor::div/button[contains(@class,'apply-coupons-button')]"));
    }

    @FindBy(xpath = "//label[@class='label-radio input-radio ship-it'][contains(.,'Ship it')]/div[1]")
    public List<WebElement> shipItRadioUnChkedBoxes;

    @FindBy(css = ".list-coupons .apply-coupons-button")
    public List<WebElement> applyBtnList;

    @FindBy(xpath = "//div[@class='message-free-shipping']//a[contains(@href,'/us/content/free-shipping')]")
    public WebElement freeShipLink;

    @FindBy(css = ".content h4 strong")
    public WebElement freeShipContent;

    @FindBy(css = ".savings-total")
    public WebElement promotionsLabel;

    @FindBy(css = ".select-quantity.mini-dropdown span.select-option-selected")
    public WebElement selectedQty;

    @FindBy(css = ".container-confirmation .message-cart-alert")
    public WebElement cartAlert;

    @FindBy(css = ".overlay-button-container .button-cancel")
    public WebElement updateCancelBtn;

    @FindBy(css = ".overlay-button-container .button-confirm")
    public WebElement btnOverlayContinue;

    @FindBy(css = ".button-modal-close")
    public WebElement closeNeedHelpOverlay;

    public WebElement storeName(int i) {
        return getDriver().findElement(By.xpath("(//strong[@class='pickup-store-name'])[" + i + "]"));
    }

    @FindBy(css = ".coupon-help-link")
    public WebElement couponHelp;

    @FindBy(css = ".button-register.button-register-uncondense")
    public WebElement createAccountEspotLink;

    @FindBy(css = "div.place-cash-spot-container")
    public WebElement placeCashEspot;
    
    @FindBy(css = ".place-cash-message>strong")
    public WebElement placeCashMsgAmt;
    
    @FindBy(css = ".subheading-button-continue-shopping")
    public WebElement continueShopping;
}
