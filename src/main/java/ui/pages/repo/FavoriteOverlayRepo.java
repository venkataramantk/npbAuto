package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by AbdulazeezM on 9/21/2017.
 */
public class FavoriteOverlayRepo extends UiBase {

    @FindBy(css = "[name='wishlistName']")
    public WebElement listName_Field;

    @FindBy(css = ".by-default div")//"[name='setAsDefault']")
    public WebElement makeDefaultList_Checkbox;

    @FindBy(css = ".button-save.button-quaternary")
    public WebElement saveBtn;

    @FindBy(css = ".button-cancel")
    public WebElement cancelLink;

    @FindBy(css = "[name='wishlistName'] ~ .inline-error-message")
    public WebElement listErrorMsg;

    @FindBy(css = ".favorite-list-name")
    public WebElement defaultWLName;

    @FindBy(css = ".button-quaternary.button-add-new-favorite-list.add-new-favorite-list-button")
    public WebElement createNewFavListBtn;

    @FindBy(xpath = ".//button[@class=\"button-quaternary add-new-favorite-list-button disabled\"]")
    public WebElement createButtonDisabled;

    @FindBy(xpath = ".//div[@class=\"ghost-error-container\"]/div[contains(.,\"Please enter name for the favorite list\")]")
    public WebElement errorMsgContainer;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModal;

    @FindBy(css = ".button-quaternary.button-delete-confirm")
    public WebElement deleteConfirmation;

}
