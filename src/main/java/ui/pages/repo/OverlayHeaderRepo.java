package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 3/2/2017.
 */
public class OverlayHeaderRepo extends UiBase {

    @FindBy(css=".login-link.active")
    public WebElement loginDrawer;

    @FindBy(css="button[aria-label='close this modal']")//".overlay-header .button-overlay-close")
    public WebElement closeOnDrawer;

    @FindBy(css="header>.login-link-container button")//".overlay-header .login-link")
    public WebElement loginLink;

    @FindBy(css="header>.wishlist-header button")//".overlay-header .wishlist-link")
//    @FindBy(css = ".wishlist-link.active")
    public WebElement wishListLink;

    @FindBy(css="header>.create-account-container>button")//".overlay-header .create-account-link")
    public WebElement createAccountLink;

    @FindBy(css=".button-logout")
    public WebElement logout;

    @FindBy(css=".create-account-link.active")
    public WebElement createAccountDrawer;

    @FindBy(css="header>.minicart-container>button")//".overlay-header .minicart-container button")
    public WebElement miniCartIcon;

//    @FindBy(css=".overlay-my-bag .subheading-my-bag a")
//    public WebElement viewBagButtonFromAddToBagConf;

//    @FindBy(css=".subheading-my-bag-title.viewport-container")
//    public WebElement myBagTitle;

    public WebElement removeLinkByItemPosition(int i) {
        return getDriver().findElement(By.cssSelector(".overlay-my-bag li:nth-child(" + i + ") .button-remove"));
    }

    @FindBy(css = ".notification-inline>p")
    public WebElement notification_ItemUpdated;

    @FindBy(css = ".overlay-content .item-product .product-title")
    public List<WebElement> itemDescElements;
}
