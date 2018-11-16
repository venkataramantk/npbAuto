package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by skonda on 09/11/2017.
 */
public class MStoreLocatorPageRepo extends UiBaseMobile {

    @FindBy(css = ".search-store-input input[name='addressLocation']")
    public WebElement searchStoreField;

    @FindBy(css = ".search-store-input button")
    public WebElement searchButton;

    @FindBy(className = "filter-store")
    public WebElement filterStoreLink;

    @FindBy(className = "input-checkbox-title")
    public WebElement outletStores;

    @FindBy(css = "span[class*='display-list']")
    public WebElement listView;

    @FindBy(css = "span[class*='display-map']")
    public WebElement mapView;

    @FindBy(css = ".stores-info")
    public WebElement storesInfo;

    @FindBy(className = "link-stores")
    public WebElement usAndCanadaStoresLink;

    @FindBy(className = "international-store-button")
    public WebElement internationalStore;

    @FindBy(name = "INSdropdpown")
    public WebElement countryDropDown;

    @FindBy(css = ".INScontainer-inner.INScountry")
    public List<WebElement> countriesList;

    @FindBy(css = ".scroll-to-top-container.active-scroll")
    public WebElement scrollToTop;

    @FindBy(css = ".INScol h3")
    public WebElement storeName;

    @FindBy(css = ".INScol span")
    public WebElement stateName;

    @FindBy(css = ".button-return")
    public WebElement returnToStoreBtn;

    @FindBy(css = ".country-or-state-list-container")
    public WebElement countryListContainer;

    public WebElement expandState(String state) {
        return getDriver().findElement(By.xpath("//h4[text()='" + state + "']/..//button"));
    }

    @FindBy(css = ".store-list")
    public WebElement storesInState;

    @FindBy(css = ".store-name a")
    public List<WebElement> storeNames;

    @FindBy(css = ".store-address")
    public List<WebElement> storeAddress;

    @FindBy(css = ".store-phone-number")
    public List<WebElement> storePhoneNos;

    @FindBy(css = ".container-map-slot .google-map")
    public WebElement googleMap;

    @FindBy(xpath = "(//div[@class='store-info-content']//h1[contains(@class,'store-name')])[1]")//test env
    public WebElement firstStoreName;

    @FindBy(xpath = "(//div[@class='store-info-content']//div[contains(@class,'content-store-detail')]//a)")
    public List<WebElement> storeDetailsLnks;

    @FindBy(css = ".store-info-content .button-expanded-details")
    public WebElement firstStoreDetailsLnk;

    @FindBy(css = ".distance-container")
    public List<WebElement> storeDistanceLabel;

    //Store Details page
    @FindBy(css = ".store-info-content .store-name")
    public WebElement storeNameLbl;

    @FindBy(css = ".button-directions")
    public WebElement getDirectionsButton;


    @FindBy(css = ".button-set-as-favorite.button-secondary")//Modified by Pooja
    public WebElement setAsFavStoreLnk;

    @FindBy(css = ".favorite-store-indicator")
    public WebElement yourFavStoreLnk;

    @FindBy(xpath = "//strong[@class=\"favorite-store-indicator button-secondary\"]/../h1")
    public WebElement favStoreName;

    @FindBy(css = ".pac-item")
    public List<WebElement> googleSuggestions;

    //Pooja
    @FindBy(xpath = ".//div[@class='store-info-data-detailed-list-item selected']/h1[@class='store-name']")
    public WebElement selectedStoreName;
}
