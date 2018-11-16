package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.FavoriteOverlayRepo;

/**
 * Created by AbdulazeezM on 9/21/2017.
 */
public class FavoriteOverlayActions extends FavoriteOverlayRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(FavoritePageActions.class);
    public static String itemNumVerify;

    public FavoriteOverlayActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean validateFieldsInNewFavList() {
        if (waitUntilElementDisplayed(listName_Field) &&
                waitUntilElementDisplayed(makeDefaultList_Checkbox) &&
                waitUntilElementDisplayed(saveBtn) &&
                waitUntilElementDisplayed(cancelLink))
            return true;
        else
            return false;
    }

    public boolean createNewFavList(String listName, FavoritePageActions favoritePageActions) {
        waitUntilElementDisplayed(listName_Field, 10);

        clearAndFillText(listName_Field, listName);
        staticWait(1000);
        click(saveBtn);
        staticWait(1000);
        return waitUntilElementDisplayed(favoritePageActions.emptyWLMsg, 10);
    }

    public boolean makeDefaultFavList(String listName, FavoritePageActions favoritePageActions) {
        waitUntilElementDisplayed(listName_Field, 10);

        clearAndFillText(listName_Field, listName);
        staticWait(1000);
        click(makeDefaultList_Checkbox);
        staticWait(1000);
        click(saveBtn);
        return waitUntilElementDisplayed(favoritePageActions.emptyWLMsg, 10);
    }

    public boolean clickCancelBtn(FavoritePageActions favoritePageActions) {
        waitUntilElementDisplayed(cancelLink, 10);
        click(cancelLink);
        staticWait(1000);
        return waitUntilElementDisplayed(favoritePageActions.defaultWLName, 10);
    }

    public boolean create5FavList(String wishListName) {

        for (int i = 0; i <= 4; i++) {
            scrollUpToElement(defaultWLName);
            staticWait(9000);
            click(defaultWLName);
            waitUntilElementDisplayed(createNewFavListBtn, 10);
            click(createNewFavListBtn);
            waitUntilElementDisplayed(listName_Field, 3);
            clearAndFillText(listName_Field, wishListName);
            waitUntilElementClickable(makeDefaultList_Checkbox, 10);
            click(makeDefaultList_Checkbox);
            waitUntilElementDisplayed(saveBtn, 8);
            click(saveBtn);
        }
        staticWait(3000);
        waitUntilElementDisplayed(defaultWLName, 3);
        click(defaultWLName);
        return waitUntilElementDisplayed(createButtonDisabled, 3);
    }

    public boolean createNewFavInlineError(String tabErrorCopy, String nullValueError) {

        waitUntilElementDisplayed(defaultWLName, 3);
        click(defaultWLName);
        waitUntilElementDisplayed(createNewFavListBtn, 7);
        click(createNewFavListBtn);
        waitUntilElementDisplayed(listName_Field, 3);
        tabFromField(listName_Field);
        waitUntilElementDisplayed(errorMsgContainer, 3);
        click(closeModal);
        waitUntilElementDisplayed(defaultWLName, 3);

        click(defaultWLName);
        waitUntilElementDisplayed(createNewFavListBtn, 7);
        click(createNewFavListBtn);
        waitUntilElementDisplayed(saveBtn, 5);
        click(saveBtn);
        waitUntilElementDisplayed(errorMsgContainer, 5);

        boolean tabError = getText(errorMsgContainer).contains(tabErrorCopy);
        boolean nullSaveError = getText(errorMsgContainer).contains(nullValueError);
        if (tabError && nullSaveError) {
            return true;
        } else
            return false;
    }


}
