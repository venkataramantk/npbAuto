package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by JKotha on 10/10/2017.
 */
public class MobileHeaderMenuRepo extends MFooterRepo {

    @FindBy(css = ".welcome-message")
    public WebElement welcomeMessage;

    @FindBy(id = "site-wide-slider")
    public WebElement siteWideBanner;

    @FindBy(css = ".paypal-button-mobile")
    public WebElement paypalFromNotification;

    @FindBy(css = ".add-to-bag-confirmation-content")
    public WebElement addtoBagNotification;

    @FindBy(css = ".access-acount.create-account-container>.create-account-link")
    public WebElement createAccountLink;

    @FindBy(css = "header>.wishlist-header .wishlist-link")//".overlay-header .wishlist-header .wishlist-link")
    public WebElement wishlistOverlay;

    //Pooja
    @FindBy(css = ".wishlist-header .wishlist-link")
    public WebElement wishlistLink;

    @FindBy(className = "success-box")
    public WebElement welcomeCustomerLink;

    //mobile
    @FindBy(css = ".button-menu")
    public WebElement menuIcon;

    public String menuIconHeader = ".button-menu";

    @FindBy(css = "input[name='typeahead']")
    public WebElement searchBox;

    @FindBy(css = ".empty-search-result-suggestion")
    public WebElement didYouMeanText;

    @FindBy(css = ".button-modal-close")
    public WebElement closeSearch;

    @FindBy(css = ".item-list-common.display-search-suggested-keywords-items-list")
    public List<WebElement> searchSuggestions;

    //Pooja
    @FindBy(xpath = "//ul[@class='item-list-common display-search-suggested-keywords-items-list']/li/div[not(H4)]")
    public List<WebElement> searchSuggestionsItem;

    //Pooja
    @FindBy(xpath = "//li[div[contains(@class,'display-search-suggested-keywords-disabledOption')]/h4[contains(.,'CATEGORY')]]/following-sibling::li")
    public List<WebElement> categorySuggestion;

    //Pooja
    @FindBy(css = ".button-modal-close")
    public WebElement closeSearchModal;

    //Pooja
    @FindBy(css = ".logo-container.logo")
    public WebElement tcpLogo;
    //Pooja
    @FindBy(xpath = "//li[div[contains(@class,'display-search-suggested-keywords-disabledOption')]/h4[contains(.,'M LOOKING FOR')]]/following-sibling::li[div[@class='item-common display-search-suggested-keywords-item']]")
    public List<WebElement> lookingForSuggestions;
    //Pooja
    @FindBy(xpath = "//li[//h4[contains(.,'M LOOKING FOR...')]]/following-sibling::li//h4[contains(.,'CATEGORY')]")
    public WebElement categoryAsSecondSection;

    @FindBy(className = "button-search")
    public WebElement searchSubmitButton;

    @FindBy(className = "typeahead-icon")
    public WebElement searchIcon;

    @FindBy(css = ".xcomponent-component-frame.xcomponent-visible")
    public WebElement payPalFrame;

    @FindBy(css = ".paypal-button-logo.paypal-button-logo-pp.paypal-button-logo-blue")
    public WebElement paypalCheckoutOnPaypalModal;

    @FindBy(xpath = "//a[@value='CHECKOUT']")
    public WebElement checkoutButton;

    @FindBy(css = ".remembered-logout")
    public WebElement loginLinkHeader;

    @FindBy(css = "header>.login-link-container .login-link")
    public WebElement loginLinkOverlay;

    @FindBy(css = ".button-login.button-login-uncondense")
    public WebElement mprLogin;

    @FindBy(css = ".minicart-container>.button-cart")
    public WebElement shoppingBagIcon;

    @FindBy(css = ".add-to-bag-confirmation-content .button-close")
    public WebElement addtoBagClose;

    @FindBy(css = ".navigation-bar .navigation-level-one>a")
    public List<WebElement> list_Departments;

    public WebElement deptLinkWithName(String deptName) {
        String deptNameUC = deptName.toUpperCase();
        String deptNameLC = deptName.toLowerCase();
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one']/a[contains(translate(., '" + deptNameUC + "', '" + deptNameLC + "'), '" + deptNameLC + "')]"));
    }

    public WebElement otherDeptLinkWithName(String deptName) {
        return getDriver().findElement(By.xpath("//a[contains(@href,'/content/')][.='" + deptName + "']"));
    }

    public WebElement categoryByNamePosition(String deptName, int i) {
        return getDriver().findElement(By.xpath("(//a[contains(@href,'/c/')][.='" + deptName + "']/following-sibling::div//li)[(" + i + ")]//a"));
    }

    @FindBy(css = ".logo>img")
    public WebElement TCPLogo;


    @FindBy(css = ".welcome-name")
    public WebElement hiUserName;

    @FindBy(css = ".header-global")
    public WebElement loginPopupEmail;

    //mPR banner
    @FindBy(css = "#myPlace-rewards-close")
    public WebElement mPRBannerCloseButton;

    @FindBy(css = "#toolbar .rewards-popover")
    public WebElement rewardsLink;

    @FindBy(css = ".stores>a")
    public WebElement storesLink;


    /*@FindBy(css = "#bag-icon-link")*/
    @FindBy(css = ".minicart-container .button-cart")
    public WebElement shoppingCartImg;

    @FindBy(css = ".global-sub-nav-banner")
    public WebElement globalMarketBanner;

    //DT2 Page objects
    @FindBy(css = ".overlay-header>.button-overlay-close")
    public WebElement closeTheOpenDrawerBtn;

    @FindBy(css = ".button-add-to-wishlist")
    public WebElement wishListLink;

    public List<WebElement> subCategoryLink(String dept) {
        return getDriver().findElements(By.xpath("//a[contains(@href,'/c/')][.='" + dept + "']/following-sibling::div//li"));
    }

    @FindBy(css = ".button-cart")
    public WebElement bagLink;


    @FindBy(css = ".navigation-confirmation button.button-return")
    public WebElement returnToBagBtn;

    @FindBy(css = ".button-container .button-view-bag")
    public WebElement viewBagButtonFromAddToBagConf;


    //mobilr
    @FindBy(xpath = "//*[@resource-id='com.android.chrome:id/terms_accept']")
    public WebElement termsLink;

    @FindBy(xpath = "//*[@resource-id='com.android.chrome:id/negative_button']")
    public WebElement noThanksLink;

    //Pooja
    @FindBy(css = "[id='error-container-inner'] h1")
    public WebElement pageNotFound_Page;

    //Raman
    @FindBy(css = ".button-quaternary.button-return")
    public WebElement stayBagBtn;

    //Raman
    @FindBy(css = ".added-to-bag-container .button-checkout")
    public WebElement checkoutFrmNotification;

    @FindBy(css = ".button-view-bag")
    public WebElement viewBagNotification;
    
    @FindBy(css = ".department-name")
    public WebElement departmentName;
    
    @FindBy(css = ".text-color")
    public WebElement itemColor;
    
    @FindBy(css = ".text-size")
    public WebElement itemSize;
    
    @FindBy(css = ".text-qty")
    public WebElement itemQuantity;

    @FindBy(css = ".scroll-to-top-container.active-scroll")
    public WebElement scrollToTop;

    //Pooja
    @FindBy(css = ".navigation-link-container")
    public WebElement subCategoriesLinkGrid;

    @FindBy(css = ".button-logout")
    public WebElement logoutLink;

    @FindBy(css = ".text-size")
    public WebElement prodSize;

    @FindBy(css = ".text-color")
    public WebElement prodColor;

    @FindBy(css = ".text-qty")
    public WebElement prodAty;

    @FindBy(css = ".button-quaternary.button-return")
    public WebElement returnBagBtn;

    @FindBy(css = ".button-exit-checkout")
    public WebElement backBtn;

    @FindBy(css = "link[rel='alternate']")
    public List<WebElement> hrefLangTags;
}

