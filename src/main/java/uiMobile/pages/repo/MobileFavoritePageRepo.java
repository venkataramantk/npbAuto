package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MobileFavoritePageRepo extends MobileHeaderMenuRepo {

    @FindBy(css = ".style-number")
    public List<WebElement> styleNumbers;

    @FindBy(css = ".button-modal-close")
    public WebElement Favclosemodal;

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

    @Deprecated
    @FindBy(css = ".wl-login.app-login")
    public WebElement wlLoginButton;


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

    @FindBy(css = ".qty")
    public List<WebElement> qtyLabel;


    public WebElement pickUpInStoreButtonByPos(int pos) {
        return getDriver().findElement(By.xpath("(.//*[@id='bopis-cta']/a/div/strong)[" + pos + "]"));
    }

    @FindBy(css = ".button-move")
    public List<WebElement> moveToAnotherList;

    @FindBy(css = ".move-item-content")
    public WebElement moveItemsContainer;

    @FindBy(css = ".favorite-item-title")
    public List<WebElement> favItemsListToMove;

    @FindBy(css = ".button-save.button-quaternary")
    public WebElement saveButton;

    @FindBy(css = ".favorite-list-name")
    public WebElement defaultWLName;

    @FindBy(css = ".item-list")
    public List<WebElement> itemsListDrpDown;

    @FindBy(css = ".list-container")
    public WebElement listContainer;

    @FindBy(css = ".button-quaternary.button-delete")
    public WebElement deleteListBtn;

    @FindBy(xpath = ".//div[@class='setting-edit-section']//button")
    public WebElement settingsIcon;

    @FindBy(css = ".product-title-content.name-item")
    public List<WebElement> prodTitles;


    @FindBy(css = ".favorite-results-count")
    public WebElement itemQty;

    @FindBy(css = ".item-container.product-item-container")
    public List<WebElement> prodImg;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'.item-disabled-option'))]")
    public List<WebElement> availableSizes;


    public WebElement addToBagIconByPos(int i) {
        return getDriver().findElement(By.xpath("(.//div[@class='item-button-container']/button[@class='bag-icon-container hover-button-enabled']) [" + i + "]"));
    }

    public WebElement favIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".wishlist-list-container .item-container:nth-child(" + i + ") .favorite-icon-container"));
    }

    //Pooja
    public WebElement selectedFavIconByTtile(String title) {
        return getDriver().findElement(By.xpath("//li[@class='item-container product-item-container'][div//a[@class='product-title-content name-item']][contains(.,'" + title + "')]/div/button[@class='favorite-icon-active favorite-icon-container']"));
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

    @FindBy(css = "div[data-title='NEW']")
    public WebElement newWishListBtn;

    @FindBy(name = "wishlistName")
    public WebElement wishListName;

    @FindBy(css = ".empty-wishlist-message-container")
    public WebElement emptyWishList;

    @FindBy(css = ".heart-icon-container")
    public WebElement defaultWishList;

    @FindBy(css = ".button-move")
    public List<WebElement> moveToAnotherWishList;


    public WebElement prodCountInWL(String name) {
        return getDriver().findElement(By.xpath(" //span[text()='" + name + "']/following-sibling::span"));
    }

    public WebElement wishlist(String name) {
        return getDriver().findElement(By.xpath("//span[text()='" + name + "']"));
    }

    public WebElement addToBagByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container.product-item-container:nth-child(" + i + ") button.bag-button-container"));
    }

    public WebElement editLink(int i) {
        return getDriver().findElement(By.cssSelector(".item-container.product-item-container:nth-child(" + i + ") .button-edit"));
    }

    @FindBy(css = ".item-container.product-item-container")
    public List<WebElement> favoritesProducts;

    @FindBy(css = ".form-login input[name='emailAddress']")
//"//label[@class='input-common']//input[@name='emailAddress']")//live1
    public WebElement emailAddrField;

    @FindBy(css = ".form-login input[name='password']")
//"//label[@class='input-common']//input[@name='password']")//live1
    public WebElement passwordField;

    @FindBy(css = "button-show-password")
    public WebElement showHideLink;

    @FindBy(name = "rememberMe")
    public WebElement rememberMeCheckBox;

    @FindBy(xpath = "//span[.='Remember me.']")
    public WebElement rememberMeLabel;

    @FindBy(css = ".button-primary.login-button")
    public WebElement loginButton;

    @FindBy(css = ".link-forgot")
    public WebElement forgotPasswordLink;

    @FindBy(xpath = "//button[.='CREATE ACCOUNT']")
    public WebElement createAccountBtn;

    public WebElement addProductToBag(String productName) {
        return getDriver().findElement(By.xpath("//a[text()='" + productName + "']/../../following-sibling::div[2]/button"));
    }

    public WebElement productCards(int position) {
        return getDriver().findElement(By.cssSelector(".item-container.product-item-container:nth-of-type(" + position + ")"));
    }

    @FindBy(css = ".wishlist-container ul li")
    public List<WebElement> productLists;

    @FindBy(css = ".favorite-icon-active.favorite-icon-container")
    public List<WebElement> favIcons;

    @FindBy(css = ".notification-inline")
    public WebElement notification;

    //Pooja
    public WebElement getProductByTitle(String title) {
        return getDriver().findElement(By.xpath("//a[@class='product-title-content name-item'][contains(.,'" + title + "')]"));
    }

    @FindBy(name = "displayMode")
    public WebElement displaySection;

    @FindBy(name = "shareDropdown")
    public WebElement shareDropDown;

    @FindBy(css = "li[unbxdparam_facetvalue='Email']")
    public WebElement emailShare;

    @FindBy(name = "shareToEmailAddresses")
    public WebElement shareTo;

    @FindBy(name = "shareSubject")
    public WebElement shareSubject;

    @FindBy(name = "shareMessage")
    public WebElement shareMessage;

    @FindBy(css = ".button-quaternary.button-send")
    public WebElement sendBtn;

    @FindBy(css = ".badge-item-container.top-badge-container")
    public List<WebElement> badges;
}
