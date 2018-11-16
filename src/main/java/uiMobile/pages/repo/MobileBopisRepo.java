package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileBopisRepo extends UiBaseMobile {
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

    @FindBy(css = ".distance-container")
    public List<WebElement> storeDistanceLabel;

    //@FindBy(xpath = ".//div[@class='ropis-product-details']//h2") //live2
    @FindBy(css = "h4[class='product-title']")//live1
    public WebElement prodName;

    @FindBy(css = ".bopis-modal-content .container-image img")//live1
    public WebElement prodImg;

    @FindBy(css = ".text-price.product-list-price")
    public WebElement wasPrice;

    @FindBy(css = ".text-price.product-offer-price")
    public WebElement offerPrice;

    @FindBy(xpath = ".//span[@class='js-upc']")
    public WebElement UPCNumber; // not found in Uatlive2

    //@FindBy(css = ".ui-checkbox")//live2
    @FindBy(css = ".label-checkbox.input-checkbox.store-availability-checkbox div")//live1
    public WebElement availableStrCheckBox;

    @FindBy(css = ".label-checkbox.input-checkbox.store-availability-checkbox div input")//live1
    public WebElement availableStrCheckBoxInput;

    //@FindBy(css = ".common-form.js-store-list")//live2
    @FindBy(css = ".stores-info")//live1
    public WebElement storesListContainer;

    @FindBy(xpath = "//input[@name='addressLocation']/../..//i/..") // not found
    public WebElement zipErrMsg;

    @FindBy(css = ".container-selects div.inline-error-message")
    public WebElement sizeErrorMessage;

    /*@FindBy(xpath =".//input[@name='zipCode']")*/ // live2
    @FindBy(css = "input[name='addressLocation']") // live1
    public WebElement zipCodeField;

    //@FindBy(css = ".header-list") //live2
    @FindBy(css = ".stores-info .message-container")//live1
    public WebElement availableText;

    public WebElement selectStoreByPosition(int i) {
        //return getDriver().findElement(By.xpath("(//div[contains(@class,'add-bopis-to-cart')]//button[not(contains(@class,'disabled'))])["+i+"]"));//live2
        return getDriver().findElement(By.xpath("(//button[@class='select-store-button'])[" + i + "]"));//live1
    }

    //@FindBy(css = ".secondary-button.js-add-to-cart")// live 2
    @FindBy(xpath = "//button[@class='select-store-button']")// live 1
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
    @FindBy(css = ".stores-info .message-container")
    public WebElement availableStoreCountTxtLbl;

    @FindBy(css = ".colors>.color-swatch.ui-radio")
    public List<WebElement> colorSwatch; // need to write custom function

    //@FindBy(css="select[name='productQuantity']") //live2
    //@FindBy(xpath = "//label[@class='select-common select-quantity mini-dropdown']//select")
    @FindBy(xpath = "//div[@class='select-common select-quantity mini-dropdown']//select")//prod xpath
    public WebElement qtyDrpDown;

    @FindBy(css = ".server-error")
    public WebElement invUnavailableServerErr;

    //@FindBy(css=".load-more.js-load-more>strong")//live2
    @FindBy(css = ".button-view-all")//live1
    public WebElement showMoreDetailsLink;

    //@FindBy(xpath="//ul[@class='store-list-container']/li[@class='store']/div/div/div/span[contains(@class,'bopis-store-name')]")//live2
    @FindBy(css = ".store-name")//live1
    public List<WebElement> availableStoreNames;

    @FindBy(css = "select[name='size']")//(css="#size")
    public WebElement sizeDropDown;

    @FindBy(css = "select[name='fit']")//(css="#size")
    public WebElement fitDropDown;

    public List<WebElement> sizeDropDownOptions() {
        return getDriver().findElements(By.xpath("//select[@name='size']/option"));
    }

    public List<WebElement> fitDropDownOptions() {
        return getDriver().findElements(By.xpath("//select[@name='fit']/option"));
    }

    @FindBy(css = ".labled-select-option")
    public WebElement colorDD;

    @FindBy(css = ".item-list-common.bag-item-color-select-items-list li")
    public List<WebElement> colors;

    //Pooja
    public WebElement selectedSizeByTitle(String title) {
        return getDriver().findElement(By.xpath("//div[@class='select-common select-size mini-dropdown']//span[@class='selection select-option-selected'][contains(.,'" + title + "')]"));
    }

    //Pooja
    @FindBy(xpath = "//div[contains(@class,'bag-item-color-select-button-closed')]/span[@class='labled-select-option']")
    public WebElement selectedSizeInBopisOverlay;

    @FindBy(xpath = "//div[@class='select-common select-size mini-dropdown']//span[@class='selection'][contains(.,'Select')]")
    public WebElement noSizeSelected;

    //Pooja
    @FindBy(css = ".bopis-store-item-info.selected h2:nth-child(1)")
    public WebElement favStoreSelectedFirst;
    //Pooja
    @FindBy(xpath = ".//div[@class='bopis-store-item-info selected'][1][div[@class='content-direction']][div[@class='content-store-detail']]")
    public WebElement favStoreAddressDetails;

    //Pooja
    @FindBy(xpath = ".//div[@class='bopis-store-item-info']/h2[@class='store-name']")
    public List<WebElement> notSelectedAvailableStores;

    //Pooja
    public WebElement notSelectedStoreByTtitle(String title) {
        return getDriver().findElement(By.xpath(".//div[@class='bopis-store-item-info']/h2[@class='store-name'][contains(.,'" + title + "')]"));
    }

    //Pooja - 6/18/18
    @FindBy(css = ".button-search-bopis.button-search-bopis-limited")
    public WebElement checkAvailability;

    @FindBy(css = ".store-name")
    public List<WebElement> bopisStoreName;

    @FindBy(css = ".distance-container")
    public List<WebElement> bopisDistanceFromUserLocation;

    @FindBy(css = ".store-address")
    public List<WebElement> bopisStoreAddress;

    @FindBy(css = ".store-phone-number")
    public List<WebElement> bopisStorePhoneNo;

    @FindBy(css = ".extended-schedule")
    public List<WebElement> bopisExtendedSchedule;

    @FindBy(css = ".bopis-availability")
    public List<WebElement> bopisAvailability;

    @FindBy(css = ".select-store-button")
    public List<WebElement> bopisSelectStoreBtn;

    @FindBy(css = ".available-product-header-container h3")
    public WebElement twoAvailableStoreTitle;

    @FindBy(css = ".button-search-bopis-limited")
    public WebElement checkAvailabilityBtn;

    @FindBy(css = ".stores-info")
    public WebElement storeInfoContainer;

    @FindBy(css = ".stores-info h2")
    public List<WebElement> pickUpStoreName;

    @FindBy(css = ".select-common.select-fit")
    public WebElement fitSection;

    @FindBy(css = ".empty-search-result")
    public WebElement emptySearchResult;

    @FindBy(xpath = "//*[text()='UNAVAILABLE']/../button")
    public List<WebElement> unavailableStores;

    @FindBy(css = ".container-image .badge-item-container.inline-badge-item")
    public WebElement productBadge;
    
    @FindBy(css = ".bopis-store-item-info div.error-box")
    public WebElement errorMessage;
}
