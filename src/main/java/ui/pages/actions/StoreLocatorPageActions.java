package ui.pages.actions;

//import javafx.scene.control.Tab;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.StoreLocatorPageRepo;

/**
 * Created by skonda on 8/3/2016.
 */
public class StoreLocatorPageActions extends StoreLocatorPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(StoreLocatorPageActions.class);

    public StoreLocatorPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }


    public boolean searchStore(String storeAddress) {

        waitUntilElementDisplayed(searchStoreField, 5);
        clearAndFillText(searchStoreField, storeAddress);
        staticWait();
        click(searchButton);
        staticWait();
        return waitUntilElementDisplayed(firstStoreDetailsLnk, 10);
    }

    public boolean validatePageContents(String storeAddress) {
        boolean enterAddressAndValidate = searchStore(storeAddress);
        return enterAddressAndValidate && waitUntilElementDisplayed(googleMap, 5);
    }

    public boolean clickStoreDetailsNavigateToStoreDetailsPage(String storeAddress) {

        validatePageContents(storeAddress);
        String storeName = firstStoreName.getText();
        staticWait();

        click(firstStoreDetailsLnk);

        boolean compareStoreName = storeNameLbl.getText().contains(storeName);

        boolean validateStoreDetails = waitUntilElementDisplayed(getDirectionsButton, 5)
                && waitUntilElementDisplayed(googleMapDetails, 5)
                && waitUntilElementDisplayed(storeHoursDetails, 5);

        return compareStoreName && validateStoreDetails;
    }

    public boolean selectFavStore(HeaderMenuActions headerMenuActions) {

        staticWait();
        click(setAsFavStoreLnk);
        staticWait();
        String storeName = firstStoreName.getText().toLowerCase();
        boolean firstStoreName = storeNameLbl.getText().contains(storeName);
        click(headerMenuActions.TCPLogo);
        staticWait();

        String changedFavStore = headerMenuActions.changedFavStoreName.getText().toLowerCase();
        boolean changedStoreName = headerMenuActions.changedFavStoreName.getText().contains(changedFavStore);

        if (firstStoreName==changedStoreName)
            return true;
        else
            return false;
    }
    public boolean selectFavStoreCA() {

        staticWait();
        if(isDisplayed(setAsFavStoreLnk)){
            addStepDescription("FAvorite store is displayed in CA store");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validateStreetErr(String addressErr){
            waitUntilElementDisplayed(searchStoreField, 5);
            searchStoreField.sendKeys(Keys.TAB);
            waitUntilElementDisplayed(addressErrMsg, 5);
            boolean validateAddressErr = getText(addressErrMsg).contains(addressErr);

        if(validateAddressErr)
            return true;
        else
            return false;
        }

    public boolean favStoreDisplay(HeaderMenuActions headerMenuActions ){
        String changedFavStore = headerMenuActions.changedFavStoreName.getText().toLowerCase();
        boolean changedStoreName = headerMenuActions.changedFavStoreName.getText().contains(changedFavStore);
        staticWait();
       click(store_lnk);
        staticWait();
        searchStore("07033");
        String storeName = firstStoreName.getText().toLowerCase();
        boolean firstStoreName = storeNameLbl.getText().contains(storeName);
        staticWait();
        if (firstStoreName==changedStoreName)
            return true;
        else
            return false;
    }

    public boolean logoutStoreDisplay(HeaderMenuActions headerMenuActions){
        String changedFavStore = headerMenuActions.changedFavStoreName.getText().toLowerCase();
        boolean changedStoreName = headerMenuActions.changedFavStoreName.getText().contains(changedFavStore);
        staticWait();
        click(headerMenuActions.welcomeCustomerLink);
        waitUntilElementDisplayed(viewMyAccountLink, 10);
        click(logOutLnk);
        staticWait(10000);
        waitUntilElementDisplayed(store_lnk);
        String storeName = firstStoreName.getText().toLowerCase();
        boolean firstStoreName = storeNameLbl.getText().contains(storeName);
        staticWait();
        if (!firstStoreName==changedStoreName)
            return true;
        else
            addStepDescription("Recent store for Reg user is displayed after logout");
            return false;
    }

    public boolean checkListOfAllStores(){
        waitUntilElementDisplayed(listOfStoreLink,3);
        click(listOfStoreLink);
        if(isDisplayed(storeListContainer)&& isDisplayed(countryDropDown)&&isDisplayed(returnToStoreLocatorLink)){
            if(waitUntilElementsAreDisplayed(storeNameList,2)&&waitUntilElementsAreDisplayed(storeAddressList,2)&&
                    waitUntilElementsAreDisplayed(storePhoneNumberList,2))
            click(returnToStoreLocatorLink);
            return true;
        }
        else{
            addStepDescription("Check the redirected List of store content page");
            return false;
        }

    }

}
