package uiMobile.pages.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MStoreLocatorPageRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skonda on 09/11/2017.
 */
public class MStoreLocatorPageActions extends MStoreLocatorPageRepo {
    WebDriver mobileDriver;

    public MStoreLocatorPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Search for a store in store locator page
     *
     * @param storeAddress to search
     * @return
     */
    public boolean searchStore(String storeAddress) {
        waitUntilElementDisplayed(searchStoreField, 5);
        clearAndFillText(searchStoreField, storeAddress);
        click(searchButton);
        return waitUntilElementsAreDisplayed(storeDetailsLnks, 5);
    }

    /**
     * Created By Pooja
     * This Method adds a store to the favourite of the logged in User
     * Data:-address of the store
     */
    public boolean addAFavStore_Reg(MobileHeaderMenuActions mobileHeaderMenuActions, PanCakePageActions panCakePageActions, String storeAdress) {
        //click(mobileHeaderMenuActions.menuIcon);
        panCakePageActions.navigateToMenu("FINDASTORE");
        searchStore(storeAdress);
        click(firstStoreDetailsLnk);
        click(setAsFavStoreLnk);
        return waitUntilElementDisplayed(yourFavStoreLnk);
    }

    public String getFavrioteStore() {
        waitUntilElementDisplayed(favStoreName);
        return getText(favStoreName).trim();
    }

    public boolean showDetailsOfStore() {
        click(firstStoreDetailsLnk);
        return waitUntilElementDisplayed(setAsFavStoreLnk);
    }

    /**
     * Created By Pooja
     * This Method return the Fav Store Name
     */
    public String getFavStoreName() {
        return getText(selectedStoreName);
    }

    /**
     * Validate all the fields in Store Locator page
     *
     * @return
     */
    public boolean validateStoreLocatorUI() {
        waitUntilElementDisplayed(filterStoreLink, 10);
        click(filterStoreLink);
        return waitUntilElementDisplayed(searchStoreField, 5) &&
                waitUntilElementDisplayed(searchButton, 5) &&
                waitUntilElementDisplayed(listView, 5) &&
                waitUntilElementDisplayed(mapView, 3) &&
                waitUntilElementDisplayed(outletStores, 5) &&
                waitUntilElementDisplayed(usAndCanadaStoresLink, 5) &&
                waitUntilElementDisplayed(internationalStore, 5);
    }

    /**
     * Click on International Stores link adnv verify Country Drop-down is displayed
     *
     * @return
     */
    public boolean validateInternationalStoresLink() {
        waitUntilElementDisplayed(internationalStore, 5);

        click(internationalStore);
        return waitUntilElementDisplayed(countryDropDown, 10) && waitUntilElementsAreDisplayed(countriesList, 10);
    }

    /**
     * Select a country from dorp-down
     *
     * @param countryName to select
     * @return
     */
    public boolean selectACountry(String countryName) {
        waitUntilElementDisplayed(countryDropDown, 10);
        selectDropDownByValue(countryDropDown, countryName);
        return waitUntilElementDisplayed(scrollToTop, 10);
    }

    /**
     * Verify Store and State name
     *
     * @return
     */
    public boolean verifyStoreAndStateName() {
        return waitUntilElementDisplayed(storeName, 10) &&
                waitUntilElementDisplayed(stateName, 10);
    }

    /**
     * Click US and Canda Store Links and verify US states are displayed
     *
     * @return
     */
    public boolean validateUSAndCanadaLinks() {
        waitUntilElementDisplayed(usAndCanadaStoresLink, 10);
        click(usAndCanadaStoresLink);
        return waitUntilElementDisplayed(returnToStoreBtn, 10) && waitUntilElementDisplayed(countryListContainer, 10);
    }

    /**
     * Expa d state to see stores
     *
     * @param stateName to select
     * @return
     */
    public boolean expandStateOrProvince(String stateName) {
        waitUntilElementDisplayed(expandState(stateName), 10);
        click(expandState(stateName));
        return waitUntilElementDisplayed(storesInState, 10);
    }

    /**
     * Validate Store name, Address and Phone nos in a state
     *
     * @return
     */
    public boolean validateStoreNameAddressPhone() {
        return waitUntilElementsAreDisplayed(storeNames, 10) &&
                waitUntilElementsAreDisplayed(storeAddress, 10) &&
                waitUntilElementsAreDisplayed(storePhoneNos, 10);
    }

    /**
     * Click on any Store from the state
     *
     * @param i position of the state
     * @return
     */
    public boolean selectAStoreByPosition(int i) {
        waitUntilElementsAreDisplayed(storeNames, 10);
        click(storeNames.get(i));
        return waitUntilElementDisplayed(googleMap, 10);
    }

    /**
     * Click on get Direction button
     *
     * @return
     */
    public boolean validateGetDirectionsButton() {
        return waitUntilElementDisplayed(getDirectionsButton, 10);
    }

    /**
     * Check Googel Suggestions
     *
     * @param address to enter
     * @return
     */
    public boolean verifyGoogelSuggestions(String address) {
        waitUntilElementDisplayed(searchButton);
        clearAndFillText(searchStoreField, address);
        return waitUntilElementsAreDisplayed(googleSuggestions, 10);
    }

    /**
     * Select random Auto Populated address
     *
     * @return
     */
    public boolean selectRandomAutoPopulatedAddress(String initialString) {
        waitUntilElementDisplayed(searchStoreField);
        clearAndFillText(searchStoreField, initialString);
        //staticWait(0);
        click(googleSuggestions.get(randInt(0, googleSuggestions.size() - 1)));
        click(searchButton);
        return waitUntilElementDisplayed(storesInfo, 10);
    }

    /**
     * verifies store distance in ascending order
     * in search
     *
     * @return
     */
    public boolean validateStoresListedDistance() {
        List<Float> distance = new ArrayList<>();
        waitUntilElementsAreDisplayed(storeDistanceLabel, 10);
        for (WebElement storeDistance : storeDistanceLabel) {
            float d = Float.valueOf(storeDistance.getText().split(" ")[0].trim());
            System.out.println(d);
            distance.add(d);
        }

        for (int i = 1; i < distance.size(); i++) {
            if (distance.get(i - 1) > distance.get(i)) {
                return false;
            }
        }
        return true;
    }
}
