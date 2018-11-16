package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 8/3/2016.
 */
public class StoreLocatorPageRepo extends UiBase {

    @FindBy(css=".search-store-input input[name='addressLocation']")
    public WebElement searchStoreField;

    @FindBy(css=".search-store-input button")
    public WebElement searchButton;

    @FindBy(css=".store-information-container .google-map")
    public WebElement googleMap;

    public WebElement storeNameByPos(int pos){
        return getDriver().findElement(By.xpath("(//div[@class='store-info-content']//h2[contains(@class,'store-name')])["+pos+"]"));
    }

    @FindBy (xpath = "(//div[@class='store-info-content']//h2[contains(@class,'store-name')])[1]")
    public WebElement firstStoreName;

    public WebElement storeDetailsLnkByPos(int pos){
        return getDriver().findElement(By.xpath("(//div[@class='store-info-content']//div[contains(@class,'content-store-detail')]//a)["+pos+"]"));
    }

    @FindBy(xpath = "(//div[@class='store-info-content']//div[contains(@class,'content-store-detail')]//a)")
    public List<WebElement> storeDetailsLnks;

    @FindBy (xpath = "(//div[@class='store-info-content']//div[contains(@class,'content-store-detail')]//a)[1]")
    public WebElement firstStoreDetailsLnk;


    //Store Details page
    @FindBy(css = ".store-info-content .store-name")
    public WebElement storeNameLbl;

    @FindBy(css=".store-info-content .button-directions")
    public WebElement getDirectionsButton;

    @FindBy(css=".container-map-slot .google-map")
    public WebElement googleMapDetails;

    @FindBy(css=".store-info-content .time-schedules")
    public WebElement storeHoursDetails;

    @FindBy (xpath = "(//button[@class='button-set-as-favorite'])[1]")
    public WebElement setAsFavStoreLnk;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid street address']")
    public WebElement addressErrMsg;

    @FindBy(xpath = ".//a[@class='button-find-store']//span")
    public WebElement store_lnk;

    @FindBy(css=".button-logout")
    public WebElement logout;

    @FindBy(css=".my-account-options a")
    public WebElement viewMyAccountLink;

    @FindBy(css=".remembered-logout>.button-logout")
    public WebElement logOutLnk;

    @FindBy(css = ".link-stores")
    public WebElement listOfStoreLink;

    @FindBy(css = ".store-search-container.stores-info")
    public WebElement storeListContainer;

    @FindBy(name = "searchType")
    public WebElement countryDropDown;

    @FindBy(css = ".button-return")
    public WebElement returnToStoreLocatorLink;

    @FindBy(css = ".store-name")
    public List<WebElement> storeNameList;

    @FindBy(css = ".store-address")
    public List<WebElement> storeAddressList;

    @FindBy(css = ".store-phone-number")
    public List<WebElement> storePhoneNumberList;

    @FindBy(css = "div.pac-item")
    public List<WebElement> googleAddressLookUpItem;

}
