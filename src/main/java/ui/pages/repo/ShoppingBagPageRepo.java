package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.actions.OrderLedgerAndCOSummaryActions;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class ShoppingBagPageRepo extends OrderLedgerAndCOSummaryActions {
    /*@FindBy(css="#shopcartCheckoutButton")*/
    /*@FindBy(css=".checkout-order-summary .button-container")*/

    @Deprecated//Please refer #checkoutBtn
    @FindBy(css = ".button-primary")
    public WebElement proceedToCheckOutButton;

    /*@FindBy(css=".item-descript>h1>a")*/
    @FindBy(css = ".item-product .product-title")
    public List<WebElement> itemDescElements;

    @FindBy(css = ".item-shopping-cart .product-title h4 a")
    public List<WebElement> prodTitles;

    @FindBy(css = ".item-shopping-cart .container-image>a>img")
    public List<WebElement> prodImages;

    public WebElement prodTitleByPos(int i){
        return getDriver().findElement(By.cssSelector(".item-shopping-cart:nth-child("+i+") .product-title h4 a"));
    }

    public WebElement prodImgsByPos(int i){
        return getDriver().findElement(By.cssSelector(".item-shopping-cart:nth-child("+i+") .container-image>a>img"));
    }

    public WebElement removeLinkByItemPosition(int i) {
        /*return getDriver().findElement(By.cssSelector("#WC_OrderItemDetailsf_linkss_2_"+i));*/
       // return getDriver().findElement(By.xpath("(//ol[@class='container-list-shopping-cart']//button[@class='button-remove'])[" + i + "]"));
        return getDriver().findElement(By.cssSelector(".item-shopping-cart:nth-child("+i+") .button-remove"));//(//li[@class='item-shopping-cart']//button[@class='button-remove'])[" + i + "]"));
    }

    @FindBy(css = ".empty-bag")
    public WebElement emptyShoppingBag;

    /*@FindBy(css=".bag-title>h1")*/
    @FindBy(css = ".subheading-my-bag-title.viewport-container")
    public WebElement shoppingBagTitle;

    //Please refer "proceedToCheckOutButton"
//    @FindBy(css=".button-blue>font")
//    public WebElement continueButton;

    @FindBy(xpath = "(//span[contains(@class,'text-price product-offer-price')])[1]")
    public WebElement totalPriceFirst;

    public WebElement totalPriceByPosition(int i) {
        return getDriver().findElement(By.xpath(("//span[contains(@class,'text-price product-offer-price')])[" + i + "])")));
    }

    public WebElement clickEditBtnByPosn(int i) {
        return getDriver().findElement(By.xpath("(//div[contains(@class,'container-description-view')]//button)[" + i + "]"));
    }

//    @FindBy(css = ".container-description-view .button-edit-product")
    @FindBy(css = ".button-edit-product")
    public WebElement editBtnLnk;

    @FindBy(xpath = "//*[@id='WC_OrderItemDetailsf_td_3_1']/span")
    public WebElement itemPriceFirst;

    @FindBy(css = "#quantity_1")
    public WebElement quantityFirst;

    @FindBy(css = ".container-description-item .text-qty")
    public List<WebElement> quantityLblFirst;

    //@FindBy(css = "select[name='quantity']")
    @FindBy(xpath = "//div[@class='container-list-shopping-cart']//select[@name='quantity']")
    public WebElement qtyDropdown;

    @FindBy(xpath = ".//select[@name='quantity']//option")
    public List<WebElement> quantityDropdown;

    @FindBy(css = "button[class='button-cancel']")
    public WebElement btnCancelChanges;

    @FindBy(xpath = "(//span[contains(.,'Your transaction was canceled prior to completion. Please provide another form of payment to complete your transaction.')])[2]")
    public WebElement errPayPalCancel;

    @FindBy(css = "#ErrorArea #ErrorMessageText")
    public WebElement wrongPaypalErrorMessage;

    @FindBy(xpath = ".//div[@class=\"error-box\"][text()='You cannot use a United States based Paypal account when shipping to Canada, and conversely you cannot use a Canadian based Paypal account when shipping to the United States.']")
    // CA Paypal in US Store error
    public WebElement WrongPaypalStoreErrorMessage;

    /*@FindBy(xpath = "//div[@class='empty']/h1")*/
    @FindBy(css="div.empty>h1")
////div[@class=\"title\"][contains(.,'your shopping bag is empty')or contains(.,'tu bolsa de compras está vacía')or contains(.,'votre panier est vide')]")
    public WebElement emptyBagMessage;

    @FindBy(xpath = "//*[contains(@id,'WC_OrderItemDetailsf_linkss')]")
    public WebElement deleteButton;

    /*@FindBy (xpath ="//td //a[contains(.,'remove') or contains(.,'eliminar')]")
    public WebElement deleteAllItems;*/

    //Please refer "removeLinkByItemPosition" element
//    @FindBy (xpath ="//td //a[contains(.,'remove') or contains(.,'eliminar') or contains(.,'enlever')]")
//    public WebElement deleteAllItems;

    @FindBy(xpath = "//tr[contains(.,'You have selected your free gift!')]/following-sibling::tr //a[contains(.,'remove') or contains(.,'eliminar')]")
    public WebElement deleteFreeGiftItem;

    @FindBy(xpath = "//tbody/tr")
    public WebElement table_rows;

    @FindBy(xpath = "//*[@id='WC_PromotionCodeDisplay_div_1']/h1")
////h1[contains(.,'have a coupon code') or contains(.,'¿Tienes un cupón?')]")
    public WebElement lbl_CouponHeader;

    //Please refer "couponCodeFld" element
//    @FindBy (xpath ="//*[@id='subtotal-row'] //input[@id='promoCode']")
//    public WebElement txt_CouponPromoCode;

    //Please refer "applyCodeButton" element
//    @FindBy (xpath ="//*[@id='subtotal-row'] //*[@class='apply-code']")
//    public WebElement btn_CouponApplyCode;

    @FindBy(xpath = "//*[@class='total-row']/span[2]")
    public WebElement lbl_subtotalAmt;

    @FindBy(xpath = "//*[@class='total-row estimate']/span[2]")
    public WebElement lbl_estimatedTotal;

    //Please refer proceedToCheckOutButton
//    @FindBy (xpath ="//div[@class='payment-buttons'] //font[contains(.,'Proceed to Checkout') or contains(.,'Ir a Pagar')]")
//    public WebElement btn_ProceedToCheckout;

    @FindBy(css = ".item-descript>h3")
    public List<WebElement> upcNumbers;

    @FindBy(css = ".discount-bubble>p")
    public WebElement discountAppliedElementsBox;

    @FindBy(css = ".remove-link a")
    public WebElement removeLinkDiscount;

    @FindBy(id = "promoCodeErrorLabel")
    public WebElement invalidPromoCodeError;

    public WebElement upcNumElemByUpcNum(String upcNum) {
        return getDriver().findElement(By.xpath("//div[@class='product-title']/h4[@class='upc-number'][contains(.,'" + upcNum + "')]"));//(".//*[@id='"+upcNum+"']/td[2]/h3"));
    }

    public WebElement favoriteElementByUpcNum(String upcNum) {
        return getDriver().findElement(By.xpath("//ol/li[contains(.,'" + upcNum + "')]/div/figure/div[@class='container-button-wishlist']/button"));//(".//*[@id='"+upcNum+"']/td[7]/div[@class='my-wishlist']"));
    }

    @FindBy(css="div.notification-inline")
    public WebElement inlineNotification;

    public WebElement moveToWLByPosition(int i) {
        return getDriver().findElement(By.xpath("(.//button[@class='button-add-to-wishlist'])[" + i + "]"));
    }

    @FindBy(css = ".flag-wishlist-product")
    public WebElement moveToFavoritesFlag;

    @FindBy(css = ".flag-remove-product")
    public WebElement removeFromBagFlag;

    //DT2 Page objects
//    @FindBy(css = ".summary-message>span .button-register")
    @FindBy(css = ".guest-rewards-button-container>.button-register.button-register-uncondense")
    public WebElement eSpotCreateAccountLnk;

    //    @FindBy(css=".summary-message>span .button-login")
    @FindBy(css = ".button-login.button-login-uncondense")
    public WebElement eSpotLoginLnk;
    // Newly Added
//    @FindBy(xpath = ".//div[@class='global-my-bag viewport-container']//h1")
//@FindBy(xpath = ".//div[@class='empty-guest empty']//h1")
    @FindBy(xpath = ".//div[@class='empty']//h1")
    public WebElement emptyBagTextContainer;

    @FindBy(css = ".button-add-to-wishlist")
    public List<WebElement> WLIcons;

    public WebElement wlIconByPosition(int pos){
        return getDriver().findElement(By.cssSelector(".item-shopping-cart:nth-child("+pos+") .button-add-to-wishlist"));
    }


    @FindBy(css = ".items-total")//".container-items-with-tax .items")
    public WebElement itemPriceSummary;

    @FindBy(css = ".button-remove")
    public WebElement closeIcon;

    @FindBy(css = ".button-primary.button-checkout")
    public WebElement checkoutBtn;

    @FindBy(css = ".button-edit-product")
    public List<WebElement> editLnks;

    public WebElement editLinkFirstElement() {
        return getFirstElementFromList(editLnks);
    }

    @FindBy(css = ".product-description .inline-error-message")
    public WebElement prodOutOfStockOnEdit;

    @FindBy(xpath = ".//select[@name='size']//following-sibling::option")
    public WebElement list_Size;

    @FindBy(css = ".button-global-update")
    public WebElement updateButton;

//    @FindBy(css = ".item-product.loading")
    @FindBy(css = ".custom-loading-icon")
    public WebElement prodLoadingSymbol;

      @FindBy(css = ".button-change-store")
    public WebElement changeStoreLnk;

    @FindBy(css = ".container-information-pick-up-in-store>strong")
   // @FindBy(css = ".container-information-pick-up-in-store.notAvailable>strong")
    public WebElement pickUpInStoreName;

    @FindBy(css = ".label-radio.input-radio.ship-it div")
    public List<WebElement> shipItCheckBoxes;

    @FindBy(css = ".input-radio-icon-unchecked")
    public WebElement STHCheckBox;

    public WebElement removeFromSelectedShipItProdByPos(int pos){
        return getDriver().findElement(By.xpath("(//div[@class='item-product'][contains(.,'Ship it')])["+pos+"]/figure/div/button[@class='button-remove']"));
    }
    public WebElement editFromSelectedShipItRadioByPos(int pos){
        return getDriver().findElement(By.xpath("(//figcaption[@class='product-description'][contains(.,'Ship it')])["+pos+"]/div/button"));
    }

    public WebElement qtyPresentForNormalProdByPos(int pos){
        return getDriver().findElement(By.xpath("(//figcaption[@class='product-description'][contains(.,'Ship it')])["+pos+"]/div[1]//p/span[@class='text-qty']"));

    }

    @FindBy(css = ".notification-inline>p")
    public WebElement notification_ItemUpdated;

    @FindBy(xpath = ".//li[@class='shipping-total']")
    public WebElement shippingText;

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

    @FindBy(xpath = ".//div[@class='container-information-pick-up-in-store']/strong")
    public List<WebElement> pickUpStoreNames;

    @FindBy(css = ".button-change-store")
    public List<WebElement> changeAStoreLinks;

    @FindBy(xpath = ".//label[@class='select-common select-size mini-dropdown']//option")
    public List<WebElement> listOfSize;

    @FindBy(xpath = ".//label[@class='select-common select-size mini-dropdown']/span[@class='selection select-option-selected']")
    public WebElement selectedSize;

//    @FindBy(xpath = ".//li[@class='items-total']/strong")
//    public WebElement itemPrice;

//    @FindBy(xpath = ".//li[@class='balance-total']/strong")
//    public WebElement estimatedTotal;

    @FindBy(css = ".text-price.product-offer-price")
    public WebElement offerPrice;

    @FindBy(css = ".text-price.product-list-price")
    public WebElement originalPrice;

    @FindBy(css = ".text-price.product-offer-price")
    public WebElement offerPr;

    @FindBy(css = ".price-item.actual-price")
    public WebElement actualPriceGC;

    @FindBy(xpath =".//li[@class='coupons-total']")
    public WebElement couponText;

    @FindBy(css = ".text-price.product-list-price")
    public List<WebElement> listOfOriginalPrice;

    @FindBy(css = ".subheading-my-bag-title.viewport-container")
    public WebElement titleText;

    @FindBy(css = ".button-cart")
    public WebElement shoppingBagIcon;

//    @FindBy(css = ".button-secondary.button-pay-with-paypal")
    @FindBy(css = ".paypal-button-container div")
    public WebElement paypalBtn;

    public String paypalBtnString = ".button-secondary.button-pay-with-paypal";

    @FindBy(css = ".department-name")
    public List<WebElement> prodNames;

    @FindBy(css = ".upc-number")
    public WebElement upcNumber;

    @FindBy(css = ".text-color")
    public WebElement prodColor;

    @FindBy(xpath = "//p/span[@class='text-size'][contains(text(),'Size')]")
    public WebElement prodSize;

    @FindBy(css = ".text-qty")
    public WebElement prodQty;

    @FindBy(css = ".guest-rewards-promo")
    public WebElement plccEspot_Guest;

    @FindBy(css = ".coupon-list-container h3")
    public WebElement availableRewardsTxt;

    public WebElement applyCouponsByPosition(int i) {
        return getDriver().findElement(By.xpath("(//ul[@class='list-coupons']//button[@class='apply-coupons-button'])[not(contains(text(),'Remove'))][" + i + "]"));
    }

    public WebElement applyToOrderButtonFor_10Percent(int i){
        return getDriver().findElement(By.xpath("((//div[contains(translate(., '10% OFF', '10% off'), '10% off')])/button[@aria-label='Apply coupon to order'])["+i+"]"));
    }
    public WebElement applyToOrderButtonFor_20Percent(int i){
        return getDriver().findElement(By.xpath("((//div[contains(translate(., '20% OFF', '20% off'), '20% off')])/button[@aria-label='Apply coupon to order'])["+i+"]"));
    }

    @FindBy(css=".header-global-banner")
    public WebElement headerBanner;

    @FindBy(id = "myPlace-rewards-close")
    public WebElement closeHeaderBanner;

    @FindBy(css = ".inline-error-message")
    public WebElement inlineErrMsg;

    @FindBy(css = ".error-box")
    public WebElement invalidErrMsg;

    @FindBy(css = ".error-box")
    public List<WebElement> errorBox;

    @FindBy(css = ".button-cancel")
    public WebElement cancelLink;

    @FindBy(css = ".button-add-to-wishlist")
    public WebElement wlLink;

    public double GetPrice(){
        try {
            WebElement priceEle = getDriver().findElement(By.cssSelector(".text-price.product-offer-price"));
            waitUntilElementDisplayed(priceEle, 30);
            String price = getText(priceEle);
            if (price.contains("for")) {
                return Double.parseDouble(price.substring(price.indexOf("$")).replace("$","").replace(" ", "").replace(",", ".").replace("USD", ""));
            }
            else if(price.contains("Paquete De")){
                return Double.parseDouble(price.substring(price.indexOf("$")).replace("$","").replaceAll("\\s", "").replace(",", ".").replace("USD", ""));
            }else if(price.contains("pour")){
                return Double.parseDouble(price.substring(price.indexOf("pour")+4, price.indexOf("$")).replace("$","").replaceAll("\\s", "").replace(",", ".").replace("USD", ""));
            }else{
                return Double.parseDouble(price.replace("$","").replace(",",".").replace("USD",""));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1.1;
        }
    }

    public double getListPrice(){
        try {
            WebElement priceEle = originalPrice;
            boolean isListPrPresent = waitUntilElementDisplayed(priceEle, 30);
            if(isListPrPresent) {
                String price = getText(priceEle);
                if (price.contains("for")) {
                    return Double.parseDouble(price.substring(price.indexOf("$")).replace("$", "").replace(" ", "").replace(",", ".").replace("USD", ""));
                } else if (price.contains("Paquete De")) {
                    return Double.parseDouble(price.substring(price.indexOf("$")).replace("$", "").replaceAll("\\s", "").replace(",", ".").replace("USD", ""));
                } else if (price.contains("pour")) {
                    return Double.parseDouble(price.substring(price.indexOf("pour") + 4, price.indexOf("$")).replace("$", "").replaceAll("\\s", "").replace(",", ".").replace("USD", ""));
                } else {
                    return Double.parseDouble(price.replace("$", "").replace(",", ".").replace("USD", ""));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1.1;
        }
        return 0.0;
    }

    public WebElement getUPCNumWebElementByPosition(int i){
        return getDriver().findElement(By.xpath("(//div[@class='product-title']//*[@class='upc-number'])["+i+"]"));
    }

    public WebElement getUPCNumWebElementByUPC(String upc){
        return getDriver().findElement(By.xpath("(//div[@class='product-title']//*[@class='upc-number'])[contains(.,'"+upc+"')]"));
    }
    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesInBag;



    @FindBy (xpath = "//a[text()='Continue as Guest']")
    public WebElement continueAsGuest;

    @FindBy(id = "header-global-banner-container")
    public WebElement myPlaceRewardsEspot;

    @FindBy(xpath = ".//div[@class='registered-rewards-promo']")
    public WebElement plccPromoEspot_Reg;

    @FindBy(css = ".message-free-shipping>p>a")
    public WebElement seeDetailsLinkOnFreeShip;

    //@FindBy(css = ".overlay-container>div>div>h1")//".content-wrapper")//.//div[@class=\"overlay-container\"]//button[@class=\"button-overlay-close close-bag-modal\"]")
    @FindBy(css = ".bopis-img")
    public WebElement freeShippingDetails;

     @FindBy(css = ".container-information-pick-up-in-store.notAvailable>strong")
    public WebElement unavailablePickUpInStoreName;

    @FindBy(xpath = "//button[text()='Back to bag']")
    public WebElement backToBagButton;

    @FindBy(css = ".button-confirm")
    public WebElement contToCheckoutButton;

    @FindBy(css = ".message-cart-alert")
    public WebElement cartAlert;

    @FindBy(css = ".item-product-recomendation")
    public List<WebElement> itemsAtProdRecomm;

    @FindBy(xpath = ".//div[@class=\"item-product\"]//div/span[@class=\"text-price product-list-price\"]")
    public List<WebElement> strikeOutPrice;

    @FindBy(xpath = ".//div[@class=\"checkout-order-summary\"]//li[contains(.,\"Promotions\")]")
    public WebElement ledger_PromotionText;

    @FindBy(xpath = ".//div[@class=\"product-title\"]//a[contains(.,\"circles gift card\")]")
    public WebElement giftCardTitle;

    @FindBy(xpath = ".//div[@class=“container-list-shopping-cart”]//div[@class=“product-title”][contains(.,‘gift’)]/following-sibling::*//div[@class=“container-price”]")
    public WebElement giftCardPrice;

    @FindBy(xpath = ".//div[@class=“container-list-shopping-cart”]//div[@class=“product-title”][contains(.,‘gift’)]/following-sibling::*//div[@class=“container-price”]//span[@class=“text-price product-list-price”]")
    public WebElement giftOfferPrice;

    @FindBy(xpath = ".//div[@class=\"checkout-order-summary\"]//li[contains(.,\"Coupon(s)\")]")
    public WebElement label_Coupons;

    @FindBy(xpath = ".//div[@class=\"container-confirmation\"]")
    public WebElement confirmationModal;



    @FindBy(css = ".fpo-banner-rewards-slot>div>a>img")
    public WebElement airMilesBanner;

    @FindBy(css = ".am-container")
    public WebElement airMilesContent;

    @FindBy(css = ".airmiles-form.summary-message>img")
    public WebElement airMilesImgUnderOrderLedger;

    @FindBy(css = "input[name='accountNumber']")
    public WebElement airMilesField;

    @FindBy(css = ".air-miles-button-edit")
    public WebElement airMilesEdit;

    @FindBy(css = ".place-cash-message>strong")
    public WebElement placeCashMsgAmt;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModal;

    @FindBy(css = ".overlay-header-desktop .button-overlay-close")
    public WebElement closeviewcart;

    @FindBy(css = ".overlay-content .item-product .product-title")
    public List<WebElement> overlayitemDescElements;

    @FindBy(css = ".overlay-content .subheading-my-bag a")
    public WebElement viewShoppingBagTitle;

    public WebElement productUpdateCheck(String ProductTitle){
        return getDriver().findElement(By.xpath( "//*[@class=‘overlay-content’]//span[@class=‘text-color’][contains(.,‘“+ProductTitle+“’)]//following-sibling::span[@class=‘text-qty’]"));
    }

    public WebElement productByTitle(String ProductTitle){
        return getDriver().findElement(By.xpath("//li[@class='item-shopping-cart']//div[@class='product-title']//a[@title='"+ ProductTitle +"']"));
    }

    @FindBy(css = ".list-container li")
    public List<WebElement> listOfGC;

    @FindBy(css = ".custom-select-button.bag-item-color-select-button-closed")
    public WebElement designDropDown;

    @FindBy(css = ".badge-item-container.inline-badge-item")
    public List<WebElement> inlineBadgeDisplay;

    @FindBy(xpath = "//*[@class='text-size'][contains(.,'Fit')]")
    public WebElement productFitSeleccted;

}
