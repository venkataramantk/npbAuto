package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 3/24/2017.
 */
public class ShoppingBagDrawerRepo extends OrderLedgerRepo {

    @FindBy(css = ".overlay-my-bag .button-register-condense")
    public WebElement eSpotCreateAccountLink;

    @FindBy(css = ".container-buttom-mybag .button-primary.button-checkout")
    public WebElement checkoutButton;

    @FindBy(css=".overlay-my-bag .subheading-my-bag a")
    public WebElement viewBagLink;

    @FindBy(css=".subheading-my-bag-title.viewport-container")
    public WebElement myBagTitle;

    @FindBy(css=".overlay-my-bag>.empty>h1")
    public WebElement emptyTextMessage;

    @FindBy(css=".new-account>span")
    public WebElement createNewAccText;

    @FindBy(css=".container-buttom-mybag>.order-summary-overlay")
    public WebElement subTotalText;

    @FindBy(xpath="//div[@class='overlay-my-bag']/div[@class='container-buttom-mybag']/ol[following::*[contains(text(),'Checkout')]]/li")
    public WebElement subTotalFldDisplayAboveChkOutButton;

    @FindBy(css=".empty-bag-with-login>.button-primary.login-button")
    public WebElement loginButton;

    @FindBy(css=".new-account>.button-secondary.button-create-account")
    public WebElement createAccBtn;

    @FindBy(css=".continue-shopping")
    public WebElement continueShoppingLnk;

    @FindBy(css=".overlay-content .button-edit-product")//".container-description-view>.edit-product")
    public List<WebElement> editLnks;

    @FindBy(css=".button-edit-product")
    public WebElement editLnk;

   // @FindBy(css=".button-add-to-wishlist")
   @FindBy(xpath=".//li[@class='item-shopping-cart']//button[@class='button-add-to-wishlist']")
    public List<WebElement> wishListIcons;

    @FindBy(css=".overlay-my-bag .button-remove")
    public List<WebElement> removeLinks;

    @FindBy(css=".button-remove")
    public WebElement removeLink;

    @FindBy(css=".product-title .department-name")
    public WebElement prodname;

    @FindBy(xpath = ".//div[@class=\"overlay-content\"]//div[@class=\"product-title\"]//a")
    public List<WebElement> overlay_ProdName;

    @FindBy(xpath = ".//div[@class=\"guest-rewards-promo-container summary-message\"]")
    public WebElement mprBannerEspt;

    @FindBy(css=".container-description-item>.text-color")
    public WebElement prodColor;

   // @FindBy(xpath = ".//div[@class=\"guest-rewards-promo-container summary-message\"]//h3")
    @FindBy(css = ".guest-rewards-promo-container.summary-message span")
    public WebElement pointsEarnedInEspotGuest;

    @FindBy(xpath = ".//div[@class=\"registered-rewards-promo-container summary-message summary-message-rewards\"]//strong")
    public WebElement pointsEarnedInEspotReg;

    @FindBy(xpath="//p/span[@class='text-size'][contains(text(),'Size')]")
    public WebElement prodSize;

    @FindBy(css=".overlay-my-bag .text-qty")
    public List<WebElement> prodQty;

    @FindBy(css=".overlay-my-bag .item-product")
    public List<WebElement> prodLines;

    @FindBy(css=".overlay-my-bag .text-price.product-offer-price")
    public List<WebElement> prodPrices;

    @FindBy(css=".subtotal span")
    public WebElement subTotalPrice;

    @FindBy(css=".overlay-my-bag select[name='quantity']")
    public WebElement qtyDropDown;

    @FindBy(css = ".overlay-my-bag select[name='fit']")
    public WebElement fitDropDown;

    @FindBy(css = ".overlay-my-bag select[name='size']")
    public WebElement sizeDropDown;

    @FindBy(xpath = "(.//select[@name=\"fit\"]//option)[\"+i+\"]")
    public List<WebElement> fitOptionAvailable;

    @FindBy(xpath = "(.//select[@name=\"size\"]//option)[\"+i+\"]")
    public List<WebElement> sizeOptionAvailable;

    @FindBy(xpath = ".//div[@class=\"overlay-content\"]//div[@class=\"custom-select-common bag-item-color-select bag-item-color-select-closed\"]")
    public WebElement colorDropDown;

    @FindBy(xpath =("(.//*[@class=\"list-container\"]//li)[\"+i+\"]"))
    public List<WebElement> selectColor;

    @FindBy(css=".button-update")
    public WebElement updateLink;

    @FindBy(css=".overlay-my-bag .notification-inline>p")
    public WebElement updateNotification;

    @FindBy(css=".minicart-container .button-cart.active")
    public WebElement bagIconInHeader;

    @FindBy(xpath = ".//form[@class='airmiles-form summary-message']//img")
    public WebElement airMiles_Espot;

    //@FindBy(css = "[name='accountNumber']")
    @FindBy(xpath = "(//input[@name='accountNumber'])[2]")
    public WebElement collectorNoField;

    @FindBy(css = ".air-miles-button-edit")
    public WebElement editLink;

    @FindBy(xpath = ".//button[@class='button-overlay-close']")
    public WebElement closeOverlay;

    @FindBy(css = ".airmiles-collector-button-container")
    public WebElement airMilesNumb;

    @FindBy(css = ".slot-drawer-bag")
    public WebElement emptyEspot;

    @FindBy(css = ".text-under")
    public WebElement airmiles_Text;

    @FindBy(xpath = ".//div[@class=\"ReactModalPortal\"]//p[@class=\"text-under\"]")
    public WebElement airmileSBText;

    @FindBy(css = ".guest-rewards-promo")
    public WebElement rewardsPLCCEspot_guest;

    public WebElement inline_qty(int i){
    return getDriver().findElement(By.xpath("(.//li[@class=\"item-shopping-cart\"]//p/span[@class=\"text-qty\"])["+i+"]"));
    }
     @FindBy(xpath = ".//li[@class=\"item-shopping-cart\"]//p/span[@class=\"text-qty\"]")
    public  List<WebElement> inlineQty;

    @FindBy(css=".button-login-condense")
    public WebElement mprLogin;

    @FindBy(css="*[name='emailAddress']")
    public WebElement emailAdd;

    @FindBy(css = ".overlay-my-bag .upc-number")
    public WebElement upcNumber;

    public WebElement getUPCNumWebElementByPosition(int i){
        return getDriver().findElement(By.xpath("(//div[@class='item-desc'])["+i+"]/h3"));
    }

    @FindBy(xpath = ".//div[@class=\"item-product\"]//div/span[@class=\"text-price product-list-price\"]")
    public List<WebElement> strikeOutPrice;

    @FindBy(xpath = ".//li[@class='items-total']/strong")
    public WebElement itemPrice;

    @FindBy(css=".input-email input")
    public WebElement emailAddrField;

    @FindBy(css = ".input-password label input")
    public WebElement passwordField;

   // @FindBy(css = ".button-primary.login-button")
    @FindBy(xpath = "(.//*[@class=\"button-primary login-button\"])[2]")
    public WebElement loginBtn;

    // created duplicate to ensure the working, later checking with Live2/Live3 will remove it
    @FindBy(css = ".button-primary.login-button")
    public WebElement FavLoginButton;

    @FindBy(css = ".guest-rewards-promo-container.summary-message")
    public WebElement guestPointsEarnedEspot_Inline;

    //@FindBy(css = ".registered-rewards-promo-container.summary-message summary-message-rewards")
    @FindBy(css = ".registered-rewards-promo-container.summary-message.summary-message-rewards")
    public WebElement regPointsEarnedEspot_Inline;

    //@FindBy(css = ".button-secondary.button-pay-with-paypal")
    //@FindBy(xpath = "//*[@class=\"paypal-button paypal-button-number-0 paypal-button-layout-horizontal paypal-button-shape-rect paypal-button-branding-branded paypal-button-number-single paypal-button-env-production paypal-should-focus paypal-button-label-paypal paypal-button-color-blue paypal-button-logo-color-white\"]")
    @FindBy(css = ".overlay-my-bag .paypal-button-container .paypal-button")
    public WebElement paypalSbDrawer;

    @FindBy(css = ".badge-item-container.inline-badge-item")
    public List<WebElement> inlineBadgeDisplay;

    @FindBy(css = ".xcomponent-component-frame.xcomponent-visible")
    public WebElement sbModal_PayPalFrame;

    @FindBy(xpath = "//*[@class='text-size'][contains(.,'Fit')]")
    public WebElement productFit;
}
