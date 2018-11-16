package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 8/5/2016.
 */
public class FavoritePageRepo extends HeaderMenuRepo {

    @FindBy(css = ".users-wishlits")
    public WebElement usersWishList;

    @FindBy(css = ".active-wishlist")
    public WebElement wishlistLink;

    @FindBy(css = ".email")
    public WebElement shareViaEmail;

    @FindBy(css = ".style-number")
    public List<WebElement> styleNumbers;

    @FindBy(css = ".delete-item")
    public List<WebElement> deleteItemElements;

    @FindBy(css = ".confirm-yes")
    public WebElement confirmYes;

    public List<WebElement> editWishListByName(String wishListName) {
        return getDriver().findElements(By.xpath("//li[contains(.,'" + wishListName + "')]/a[@class='edit']"));
    }

    @FindBy(xpath = "//li[@data-status='Active']/a[@class='edit']")
    public WebElement editActiveWishList;

    @FindBy(xpath = "//li[@data-status='Active']/a[1]")
    public WebElement activeWishListName;

    @FindBy(css = ".set-default-wishlist")
    public WebElement setAsDefaultLink;

    @FindBy(xpath = "//ol[@class='wishlist-list']/li[@data-status='Default']/a[@class='edit']")
    public WebElement editDefaultWishList;

    @FindBy(css = ".delete-wishlist")
    public WebElement deleteWishList;

    public WebElement wishListByName(String wlName) {
        return getDriver().findElement(By.xpath("//a[text()='" + wlName + "']"));
    }

    @FindBy(css = ".active-wishlist")
    public List<WebElement> activeWishLists;

    @FindBy(css = ".create-new-wishlist")
    public WebElement createNewWishListLink;

    @FindBy(css = ".result.inline-error.active")
    public WebElement errorFor_MaxNumOfWLs;

    //@FindBy(css = ".users-wishlits form label input[name='wishlist-name']")
    @FindBy(name = "wishlistName")
    public WebElement newWishListTextBox;

    @FindBy(css = ".create-new-wishlist-cta")
    public WebElement createButton;

    @FindBy(css = ".modal.no-veil.edit-wishlist>form>fieldset>label>input[name='wishlist-name']")
    public WebElement defaultWishListTextBox;

    @FindBy(css = ".modal.no-veil.edit-wishlist>form>fieldset>label>input[name='update']")
    public WebElement updateButtonOnDefWL;

    @FindBy(css = ".users-wishlits li[data-status='Default'] a:nth-child(1)")
    public WebElement defaultWLNameElement;

    @FindBy(css = "#ropis-cta")
    public WebElement ropisBlockWL;

    @FindBy(css = ".caption>span")
    public WebElement reserveOnline;

    public WebElement reserveOnlineByItem(String itemID) {
        return getDriver().findElement(By.xpath("//article[@itemprop='itemListElement'][contains(.,'" + itemID + "')]/div[2]/div[@id='ropis-cta']"));
    }

    public WebElement selectWishListByName(String wishListName) {
        return getDriver().findElement(By.xpath("//a[@class='active-wishlist'][contains(.,'" + wishListName + "')]"));
    }

    //@FindBy(css = ".button-add-to-bag")
    @FindBy(css = ".bag-icon-container.hover-button-enabled")
    public List<WebElement> addToBagBtn;

    @FindBy(css = ".modal.confirm.success-add-to-bag")
    public WebElement addToCartSuccessMsg;

    @FindBy(css = ".modal.confirm.success-add-to-bag>p>a")
    public WebElement viewBagOnSuccessMsg;

    public WebElement addToBagByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container.product-item-container:nth-child(" + i + ") a"));
    }

    @FindBy(css = ".qty")
    public List<WebElement> qtyLabel;

    @FindBy(xpath = ".//*[@id='bopis-cta']/a/div/strong")
    public List<WebElement> pickUpInStoreButtons;

    public WebElement pickUpInStoreButtonByPos(int pos) {
        return getDriver().findElement(By.xpath("(.//*[@id='bopis-cta']/a/div/strong)[" + pos + "]"));
    }

    @FindBy(css = ".button-move")
    public List<WebElement> moveToAnotherList;

    @FindBy(css = ".move-item-content")
    public WebElement moveItemsContainer;

    @FindBy(css = ".favorite-item-title")
    public List<WebElement> favItemsListToMove;

    @FindBy(css = ".button-quaternary.button-new")
    public WebElement createNewListBtn;

    @FindBy(css = ".button-save.button-quaternary")
    public WebElement saveButton;

    @FindBy(css = ".favorite-list-name")
    public WebElement defaultWLName;

    @FindBy(css = ".item-list")
    public List<WebElement> itemsListDrpDown;

    @FindBy(css = ".list-container")
    public WebElement listContainer;

    @FindBy(css = ".button-quaternary.button-add-new-favorite-list.add-new-favorite-list-button")
    public WebElement createNewFavListBtn;

    @FindBy(name = "[name='wishlistName']")
    public WebElement listNameField;

    @FindBy(css = ".empty-wishlist-title")
    public WebElement emptyWLMsg;

    @FindBy(css = ".button-quaternary.button-delete")
    public WebElement deleteListBtn;

    @FindBy(css = ".button-quaternary.button-delete-confirm")
    public WebElement confirmDeleteListBtn;

    @FindBy(xpath = ".//div[@class='setting-edit-section']//button")
    public WebElement settingsIcon;

    @FindBy(css = ".product-title-content.name-item")
    public List<WebElement> prodTitles;

    public WebElement getProdNameByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container.product-item-container:nth-child(" + i + ") .product-title-content.name-item"));
    }

    @FindBy(css = ".favorite-results-count")
    public WebElement itemQty;

    @FindBy(css = ".item-container.product-item-container")
    public List<WebElement> prodImg;

    @FindBy(css = ".item-container.product-item-container")
    public List<WebElement> removeWLProdLink;

    @FindBy(css = ".link-redirect")
    public WebElement viewProdDetailsLink;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'.item-disabled-option'))]")
    public List<WebElement> availableSizes;

    @FindBy(css = ".button-close")
    public WebElement closeLink;

    @FindBy(css = ".add-to-bag-confirmation")
    public WebElement addToBagConfirmation;

    @FindBy(css = ".wishlist-list-container .bag-icon-container")
//(xpath = ".//ul[@class='wishlist-list-container']//button[@class='bag-icon-container']")
    public List<WebElement> addProdFromWL;

    public WebElement addToBagIconByPos(int i) {
        return getDriver().findElement(By.xpath("(.//div[@class='item-button-container']/button[@class='bag-icon-container hover-button-enabled']) [" + i + "]"));
    }

    public WebElement favIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".wishlist-list-container .item-container:nth-child(" + i + ") .favorite-icon-container"));
    }

    public WebElement itemImg(int i) {
        return getDriver().findElement(By.xpath("(.//img[@class='product-image-content img-item'])  [" + i + "]"));
    }

    @FindBy(css = ".button-edit")
    public WebElement prod_EditLink;

    //    @FindBy(css = ".select-common.select-qty")//"select[name='quantity']")
    @FindBy(css = "select[name='quantity']")
    public WebElement qtyDropdown;

    @FindBy(css = ".button-add-to-bag")
    public WebElement saveProdBtn;

    @FindBy(xpath = "//a[@class=\"link-redirect\"][text()=\"View Product Details\"]")
    public WebElement viewProductDetails_Lnk;

    @FindBy(css = ".custom-select-button.share-list-button-closed.custom-select-button-placeholder")
    public WebElement shareDropDown;

    @FindBy(xpath = "(.//div[@class=\"favorite-social-container\"]//div[@class=\"dropdown-share-options\"]//ul/li)")
    public List<WebElement> shareDropDownList;

    @FindBy(xpath = ".//div[@class=\"input-common to-email-input\"]")
    public WebElement toAddress;

    @FindBy(xpath = ".//div[@class=\"input-common subject-input\"]")
    public WebElement subjectAddress;

    @FindBy(xpath = ".//div[@class=\"buttons-container\"]/button[@class=\"button-quaternary button-send\"]")
    public WebElement sendButton;

    @FindBy(xpath = ".//div[@class=\"buttons-container\"]/button[@class=\"button-cancel\"]")
    public WebElement cancelButton;

    @FindBy(xpath = ".//div[@class='display-filter-container display-filter']")
    public WebElement filterOption;

    @FindBy(xpath = "(.//div[@class='display-filter-items-list']/label)")
    public List<WebElement> filterByOption;

    @FindBy(xpath = "(.//ul[@class=\"wishlist-list-container\"]/li)")
    public List<WebElement> filteredPageImage;

    @FindBy(xpath = ".//ul[@class=\"wishlist-list-container\"]//span[contains(.,\"$\")]")
    public List<WebElement> priceOfTheItem;

    @FindBy(xpath = ".//div[@class=\"select-common sort-by\"]//select")
    public WebElement sortByFavDropDown;

    @FindBy(xpath = ".//form[@class=\"update-wishlist-form\"]//p[@class=\"message-delete\"]")
    public WebElement contentDisplay;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModal;

    @FindBy(xpath = ".//header[@class=\"modal-header\"]")
    public WebElement updateListHeader;

    @FindBy(css = ".button-quaternary.button-delete")
    public WebElement deleteButton;

    @FindBy(css = "[name='wishlistName']")
    public WebElement listName_Field;

    @FindBy(css = ".by-default div")//"[name='setAsDefault']")
    public WebElement makeDefaultList_Checkbox;

    @FindBy(css = ".button-save.button-quaternary")
    public WebElement saveBtn;

    @FindBy(css = ".button-cancel")
    public WebElement cancelLink;

    @FindBy(css = ".error-box")
    public WebElement errorBox;

    @FindBy(css = ".purchased-status-content")
    public List<WebElement> purchasedItemDisplay;

    @FindBy(css = ".favorite-icon-active.hover-button-enabled.favorite-icon-container")
    public WebElement addToFavImgSelectedOnSrp;

    @FindBy(css = "button.button-quaternary.button-copy")
    public WebElement shareViaCopyLinkBtn;

    @FindBy(css = ".button-modal-close")
    public WebElement closeCopyLinkModalIcon;

    @FindBy(css = ".item-button-container .hover-button-enabled.favorite-icon-container")
    public List<WebElement> addToFavIconOnPLP;


    @FindBy(css = ".selected-option")
    public WebElement selectedColor;

    @FindBy(css = ".button-add-to-bag")
    public WebElement saveProdDetails;

    @FindBy(css = ".custom-select-button.favorite-list-button-closed")
    public WebElement favDropDown;

    @FindBy(css = ".button-quaternary.button-add-new-favorite-list.add-new-favorite-list-button")
    public WebElement createFavButton;

    @FindBy(css = ".input-checkbox-icon-checked")
    public WebElement defaultCheckboxUncheck;

    @FindBy(name = "setAsDefault")
    public WebElement setAsDefault;

    @FindBy(xpath = "//*[@class=\"favorite-filter-container\"]/div[@class=\"notification generic-sticky-notification\"]")
    public WebElement updateNotification;



}
