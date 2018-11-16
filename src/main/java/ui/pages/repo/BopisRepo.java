package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by AbdulazeezM on 3/30/2017.
 */
public class BopisRepo extends UiBase {
    public static WebElement storeButton;

   // @FindBy(xpath = ".//div[@class='bopis-header']//h1")//live2
    @FindBy(css = ".title-container")//live1
    public WebElement headerContent;

    //@FindBy(xpath = ".//div[@class='bopis-header']//p")//live2
    @FindBy(css = ".message-container")//live2
    public WebElement subHeaderContent;

    /*@FindBy(css = ".primary-button")*/ //live2
    @FindBy(css = ".button-search-bopis")//live1
    public WebElement searchButton;


    @FindBy(css = ".button-search-bopis.button-search-bopis-limited")//live1
    public WebElement checkAvailability;

    //@FindBy(xpath = ".//div[@class='ropis-product-details']//h2") //live2
    @FindBy(css = ".bopis-product-container .product-title")//live1
    public WebElement prodName;

    @FindBy(xpath = ".//span[@class='js-upc']")
    public WebElement UPCNumber; // not found in Uatlive2

    //@FindBy(css = ".ui-checkbox")//live2
    @FindBy(css = ".label-checkbox.input-checkbox.store-availability-checkbox")//live1
    public WebElement availableStrCheckBox;

    //@FindBy(css = ".common-form.js-store-list")//live2
    @FindBy(css = ".stores-info")//live1
    public WebElement storesListContainer;

//    @FindBy(xpath = ".//span[@id='zipCode-error']") // not found
    @FindBy(css = ".search-store .inline-error-message")
    public WebElement zipErrMsg;

    /*@FindBy(xpath =".//input[@name='zipCode']")*/ // live2
    @FindBy(css = "input[name='addressLocation']") // live1
    public WebElement zipCodeField;

    //@FindBy(css = ".header-list") //live2
   //@FindBy(css = ".search-result")//live1
    @FindBy(css = ".bopis-availability")
    public WebElement availableText;

    @FindBy(xpath = "(//span[@class='bopis-availability'])[not(contains(.,'UNAVAILABLE'))]")
    public List<WebElement> availableStores;

    public WebElement selectStoreByPosition(int i){
    //return getDriver().findElement(By.xpath("(//div[contains(@class,'add-bopis-to-cart')]//button[not(contains(@class,'disabled'))])["+i+"]"));//live2
        return getDriver().findElement(By.xpath("(//button[@class='select-store-button'])["+i+"]"));//live1
    }

    //@FindBy(css = ".secondary-button.js-add-to-cart")// live 2
    @FindBy(xpath = "(//button[@class='select-store-button'])")// live 1
    public List<WebElement> addBopisToBagButtons;

    //@FindBy(css = ".overlay-close.js-close-modal")//live2
    @FindBy(css = ".button-modal-close") //live1
    public WebElement closeOverlay;

    //@FindBy(css = ".select-distance.js-select-distance.valid") //live2
    @FindBy(name = "distance") //live1
    public WebElement distanceDropdown;

    //@FindBy(xpath="//div[contains(@class,'add-bopis-to-cart')]//button[not(contains(@class,'disabled'))]") //  live 2
    @FindBy(xpath = "(//button[@class='select-store-button'])")// live 1
    public List<WebElement> availableStoreBtn;

    //@FindBy(css=".header-list>strong") // live 2
    /*@FindBy(css=".header-list>strong") // need to write custom function*/
    @FindBy(css=".stores-info .message-container")
    public WebElement availableStoreCountTxtLbl;

    @FindBy(css = ".stores-info .bopis-store-item-info")
    public List<WebElement> storesAvailable;

    @FindBy(xpath = ".//*[@class=\"bopis-availability\"][text()='AVAILABLE']")
    public List<WebElement> getAvailableTwoStr;

    @FindBy(xpath = ".//span[@class=\"bopis-availability\"][text()=\"LIMITED AVAILABILITY\"]")
    public List<WebElement> getLmtAvailableTwoStr;

 @FindBy(css=".colors>.color-swatch.ui-radio")
    public List<WebElement> colorSwatch; // need to write custom function

    //@FindBy(css="select[name='productQuantity']") //live2
//    @FindBy(xpath="//label[@class='select-common select-quantity mini-dropdown']//select")//live1
   // @FindBy(css="select[name='quantity']")
    @FindBy(xpath = "//div[@class=\"item-product-container\"]//select[@name='quantity']")
    public WebElement qtyDrpDown;

    @FindBy(css=".server-error")
    public WebElement invUnavailableServerErr;

    //@FindBy(css=".load-more.js-load-more>strong")//live2
    @FindBy(css=".button-view-all")//live1
    public WebElement showMoreDetailsLink;

    //@FindBy(xpath="//ul[@class='store-list-container']/li[@class='store']/div/div/div/span[contains(@class,'bopis-store-name')]")//live2
    @FindBy(css=".store-name")//live1
    public List<WebElement> availableStoreNames;

    @FindBy(css=".bopis-product-container select[name='size']")//(css="#size")
    public WebElement sizeDropDown;

    public List<WebElement> sizeDropDownOptions(){
        return getDriver().findElements(By.xpath("//select[@name='size']/option"));
    }

    @FindBy(css = ".bopis-modal-content .container-image") // place holder image
    public WebElement itemImage;

      // @FindBy(xpath = ".//label[@class=\"select-common select-size mini-dropdown\"]")
//   @FindBy(xpath = ".//div[@class=\"select-common select-size mini-dropdown\"]")
    @FindBy(css = ".select-size.mini-dropdown")
    public WebElement bopisSize;

//    @FindBy(xpath = ".//*[@class=\"selection select-option-selected\"]")
    @FindBy(css = ".select-quantity.mini-dropdown")
    public WebElement bopisQuantity;

    @FindBy(css = "[name='fit']>option")
    public List<WebElement> bopisFitValues;

    public WebElement bopisFitByPos(int i){
        return getDriver().findElement(By.cssSelector("[name='fit']>option:nth-child("+i+")"));
    }

    @FindBy(xpath = ".//*[@class=\"button-modal-close\"]")
    public WebElement bopisClose;

    @FindBy(xpath = ".//*[@class=\"custom-select-common bag-item-color-select bag-item-color-select-closed\"]")
    public WebElement bopisColor;

    @FindBy (xpath = "//div[@class=\"empty-search-result\"]")
    public WebElement notAvailable;

    @FindBy(xpath = ".//div[@class=\"select-common select-size mini-dropdown\"]//select//option")
    public List<WebElement> bopisAvailableSize;

    @FindBy (css = ".bopis-product-container .container-price")
    public WebElement itemPrice;

    public String inStoreAvailTxt = ".title-container";
    public String messageUnderInStoreTxt = ".message-container";

    @FindBy(css=".text-price.product-offer-price")
    public WebElement salePrice;

    @FindBy(css=".text-price.product-list-price")
    public WebElement originalPrice;

    @FindBy(css=".disabled-fits-message")
    public WebElement fitsErrMsg;

    @FindBy(xpath="(//div[@class='stores-info']/div)[not(contains(@class,'selected'))]")
    public List<WebElement> storeNamesExceptFav;

    @FindBy(css=".bopis-store-item-info.selected h2:nth-child(1)")
    public WebElement favStoreSelectedFirst;

    @FindBy(xpath = ".//div[@class='select-common select-size mini-dropdown']//span[@class='selection select-option-selected']")
    public WebElement defaultSizeInBopis;

    @FindBy(xpath = ".//div[@class='custom-select-button bag-item-color-select-button-closed']/span[img]")
    public WebElement defaultColorInBopis;

    @FindBy(css = ".button-view-bag")
    public WebElement conf_ViewBag;

    @FindBy(css = ".button-checkout")
    public WebElement conf_Checkout;

    @FindBy(css = ".continue-shopping")
    public WebElement conf_ContinueCheckout;

    @FindBy(css = ".paypal-button-desktop")
    public WebElement paypalSbModal;

    @FindBy(css=".add-to-bag-confirmation")
    public WebElement addToBagNotification;

    @FindBy(css = ".button-close")
    public WebElement conf_ButtonClose;

    @FindBy(xpath = "//*[@class=\"bopis-availability\"][contains(.,'UNAVAILABLE')]/following-sibling::button [(@disabled)]")
    public List<WebElement> addToBagDisabledForUnavailable;

    @FindBy(css = ".select-common.select-size.mini-dropdown .selection.select-option-selected")
    public WebElement sizeSelectedInBOPISModal;

    @FindBy(css = ".select-common.select-quantity.mini-dropdown .selection.select-option-selected")
    public WebElement qtySelectedInBOPISModal;

    @FindBy(name = "distance")
    public WebElement distanceField;

    @FindBy(css = ".search-store .selection.select-option-selected")
    public WebElement defaultDistanceSelected;

    @FindBy(css = ".content-direction")
    public List<WebElement> store_Address;

    @FindBy(css = ".extended-schedule")
    public List<WebElement> store_Timing;

    @FindBy(css = ".store-phone-number")
    public List<WebElement> store_PhoneNumber;

    @FindBy(css = ".bopis-modal-content .product-title")
    public WebElement itemName;

    @FindBy(css = ".store-name")
    public List<WebElement> store_name;

    @FindBy(css = ".inline-error-message")
    public WebElement bOPISInlineError;

    @FindBy(css = ".error-box-bopis p")
    public WebElement invalidZipError;

    @FindBy(css = ".error-box")
    public WebElement maxLimitError;

    @FindBy(css = ".select-common.select-quantity.mini-dropdown .selection.select-option-selected")
    public WebElement selectedQuantity;

    @FindBy(css = ".distance-container")
    public List<WebElement> storeDistanceLabel;

    @FindBy(css = ".select-common.select-quantity.mini-dropdown .selection")
    public WebElement selectedSizeByDefault;

    @FindBy(css = ".item-list-common.bag-item-color-select-items-list li")
    public List<WebElement> colorsListInDropDOwn;

    @FindBy(css = ".container-image .badge-item-container.inline-badge-item")
    public WebElement productBadge;

    @FindBy(css = ".error-message")
    public WebElement pageLevelError;

}
