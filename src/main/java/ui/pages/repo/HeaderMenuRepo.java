package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class HeaderMenuRepo extends FooterRepo {

    @FindBy(css = ".breadcrum-last-item")
    public WebElement deptNameTitle;

    @FindBy(css = ".welcome-message")
    public WebElement welcomeMessage;

    @FindBy(css = ".access-acount.create-account-container>.create-account-link")
    public WebElement createAccountLink;

    @FindBy(css = "header>.create-account-container .create-account-link")
    public WebElement createAccountOverlay;

    @FindBy(css = "header>.wishlist-header .wishlist-link")
    public WebElement wishlistOverlay;

    @Deprecated
    @FindBy(xpath = "//li[@id='customer-name']//a[contains(.,'Log Out') or contains(.,'Déconnexion') or contains(.,'Salir')]")
    public WebElement logOut;

    @Deprecated
    @FindBy(css = "[title='myPLACE Rewards']")
    public WebElement mPROldLogo;

    @Deprecated
    @FindBy(css = ".welcome-message button[alt='My Account']")
    public WebElement myAccountLink;

    @FindBy(css = ".button-checkout")
    public WebElement checkoutFromNotification;

    @FindBy(css=".add-to-bag-confirmation")
    public WebElement addToBagNotification;

    @FindBy(css = ".paypal-button-mobile")
    public WebElement paypalFromNotification;

    @Deprecated
    public WebElement myAccountLinkWithText(String text) {
        return getDriver().findElement(By.xpath("//li/a[text()='" + text + "']"));
    }

    @FindBy(css = ".welcome-message strong")
    public WebElement welcomeCustomerLink;

    @FindBy(name = "typeahead")
    public WebElement searchBox;

    @FindBy(css=".rotatingBanner")
    public WebElement rotatingBanner;

    @FindBy(css = ".view-all")
    public WebElement viewall;


    @FindBy(css = ".typeahead>.button-search")
    public WebElement searchSubmitButton;

    @Deprecated//{@link refer #ShoppingBagDrawerRepo.checkoutButton
    @FindBy(css = "a[value='CHECKOUT']")
    public WebElement checkoutButton;

    @FindBy(css = ".login-link-container .login-link")
    public WebElement loginLinkHeader;

    @FindBy(css = "header>.login-link-container .login-link")//".overlay-header .login-link-container .login-link")
    public WebElement loginLinkOverlay;

    @FindBy(css = "header> .welcome-message strong")//".overlay-header .welcome-message strong")
    public WebElement welcomeCustomerLinkOverlay;

    @FindBy(xpath = "//li[@class='navigation-level-one']/a[contains(.,'Clearance') or contains(.,'Liquidation') or contains(.,'Liquidación')]")
    public WebElement clearanceLink;

    @Deprecated
    @FindBy(xpath = ".//*[@id='primary-nav']/ul/li[2]/a")
    public WebElement babyGirlCategory;

    @Deprecated
    @FindBy(css = ".department-name-title")
    public WebElement deptTitle;

    public WebElement subCategoryUnderClearanceByPos(int i) {
        return getDriver().findElement(By.xpath("(//ul[@class='menus'])[13]/li[" + i + "]/a"));
    }

    //  @FindBy(css="#qty") changed
    @FindBy(css = ".minicart-container>.button-cart")
    public WebElement shoppingBagIcon;

    @FindBy(css = ".button-close")
    public WebElement conf_ButtonClose;

    @FindBy(css = ".overlay-header-desktop .minicart-container .button-cart")
    public WebElement overlay_SBIcon;

    /*@FindBy(css = "#nav-row #primary-nav>ul>li>a")*/
    @FindBy(css = ".navigation-bar .navigation-level-one>a")
    public List<WebElement> list_Departments;

    public WebElement deptLinkWithName(String deptName) {
        String deptNameUC = deptName.toUpperCase();
        String deptNameLC = deptName.toLowerCase();
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one']/a[contains(translate(., '" + deptNameUC + "', '" + deptNameLC + "'), '" + deptNameLC + "')]"));
    }

    public List<WebElement> clickL2category(String cat) {
        //return getDriver().findElements(By.xpath("//a[text()='"+cat+"']"));
        return getDriver().findElements(By.xpath("//div[@class='plp-section-container search-result-container viewport-container search-from-navigation']//li//a[contains(.,'"+cat+"')]"));
    }

    @FindBy(css = ".list-container")
    public WebElement suggestion;

    @FindBy(xpath = "//h4[text()=\"CATEGORY\"]/../../following-sibling::li")
    public List<WebElement> categoryList;

    @FindBy(css = ".item-common.display-search-suggested-keywords-item span")
    public List<WebElement> imLookingForValues;

    @FindBy(css = ".item-common.display-search-suggested-keywords-item.item-disabledOption.display-search-suggested-keywords-disabledOption")
    public List<WebElement> categorySuggestion;

    public WebElement otherDeptLinkWithName(String deptName) {
        return getDriver().findElement(By.xpath("//a[contains(@href,'/content/')][.='" + deptName + "']"));
    }

    @FindBy(css = ".logo>img")
    public WebElement TCPLogo;

    @Deprecated//@link refer #FooterRepo.flagCountryImg
    @FindBy(xpath = "//*[@title='Ship to Country: US']")
    public WebElement imgFlagUS;

    @Deprecated//@link refer #FooterRepo.flagCountryImg
    @FindBy(xpath = "//*[@title='Ship to Country: CA']")
    public WebElement imgFlagCA;

    @FindBy(css = ".content-product-recomendation")
    public WebElement recomendation;

    @Deprecated//{@link refer # wishListLink}
    @FindBy(css = ".wishlist-cta")
    public WebElement wishList;

    @FindBy(css = "#ropis-popover")
    public WebElement reservationLink;

    @FindBy(css = "#ropis-popover-details div a img")
    public WebElement ropisPopOver;

    @FindBy(css = "#ropis-popover-details div p")
    public WebElement emptyResSummary;

    @FindBy(css = ".content h2")
    public WebElement availableResMsgOnResSummary;

    @FindBy(css = ".store-details>p>a")
    public List<WebElement> availableResNumOnResSummary;

    @FindBy(css = ".welcome-name")
    public WebElement hiUserName;

    @FindBy(css = "input[name='email']")
    public WebElement loginPopupEmail;


    //ROPIS page objects
    @FindBy(css = "#ropis-popover")
    public WebElement reservationsLink;

    @FindBy(css = "#ropis-popover-details")
    public WebElement reservationDetails;

    @FindBy(css = "#ropis-popover-details .content")
    public WebElement noReservationMessage;

    @FindBy(css = ".content>h2")
    public WebElement yourReservationsLbl;

    @FindBy(css = ".ropis-product-details>h3")
    public WebElement resProductName;


    public List<WebElement> getLevelTwoCategories(String deptName) {
        return getDriver().findElements(By.xpath("//li[@class='navigation-level-one'][a[text()='" + deptName + "']]//div/ul/li/a"));
    }


    @FindBy(css = ".ropis-product-details>p")
    public WebElement resProductSize;

    @FindBy(css = ".product-color>.color-name")
    public WebElement resProductColor;

    @FindBy(css = ".store-details a")
    public WebElement reserveID;

    @FindBy(css = ".store-details strong")
    public WebElement reserveStoreName;

    //mPR banner
    @FindBy(css = "#myPlace-rewards-close")
    public WebElement mPRBannerCloseButton;

    @FindBy(css = "#toolbar .rewards-popover")
    public WebElement rewardsLink;


    @FindBy(css = ".stores>a")
    public WebElement storesLink;

    @Deprecated//FooterRepo.shipToLink (ShipTo link is not a link (R2 code))
    @FindBy(css = "#WC_TCPCachedCommonToolbar_FormLink_IShipping1")
    public WebElement shipToLink;

    @Deprecated//{@link refer #FooterRepo.enLink}
    @FindBy(css = "a[title='English']")
    public WebElement englishLink;

    @Deprecated//{@link refer #FooterRepo.esLink}
    @FindBy(css = "a[title='Español']")
    public WebElement spanishLink;

    @Deprecated//{@link refer #FooterRepo.frLink}
    @FindBy(css = "a[title='Français']")
    public WebElement frenchLink;

    @Deprecated//@link refer #FooterRepo
    public WebElement countryImgByCountry(String country) {
        return getDriver().findElement(By.cssSelector("img[title='Ship to Country: " + country + "']"));
    }

    @Deprecated
    /*@FindBy(css = "#bag-icon-link")*/
    @FindBy(css = ".minicart-container .button-cart")
    public WebElement shoppingCartImg;

    @Deprecated
   @FindBy(css = "ul>.points-total")
    public WebElement myPointsLink;

    public WebElement   outfitsLinkFromCategoryByPosition(int i) {
        return getDriver().findElement(By.xpath(".//div[@class='container-global-navigation viewport-container']/div/nav/ul/li[" + i + "]//a[contains(.,'Outfits')][not(contains(.,'Style Squad'))]"));
    }

    public WebElement girlLinkUnderAccessoriesCategory() {
       // return getDriver().findElement(By.xpath(".//li[a[contains(.,'Accessories')]]/div/ul/li/a[contains(.,'Girl')][not(contains(.,'Baby'))] [not(contains(.,'Toddler'))]"));
    return getDriver().findElement(By.xpath(".//li[a[contains(.,'Accessories')]]//li[contains(.,'Girl')] [not(contains(.,'Toddler'))] [not(contains(.,'Baby'))]"));
    }

    public WebElement boyLinkUnderAccessoriesCategory() {
        // return getDriver().findElement(By.xpath(".//li[a[contains(.,'Accessories')]]/div/ul/li/a[contains(.,'Girl')][not(contains(.,'Baby'))] [not(contains(.,'Toddler'))]"));
        return getDriver().findElement(By.xpath(".//li[a[contains(.,'Accessories')]]//li[contains(.,'Boy')] [not(contains(.,'Toddler'))] [not(contains(.,'Baby'))]"));
    }

    public WebElement linkUnderGirlCategory(String subNavLink) {
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[contains(.,'Girl')][not(contains(.,'Toddler'))]]//li[@class='sub-menu-category-item']/a[contains(.,'"+subNavLink+"')]"));
    }
    public WebElement linkUnderToddlerGirlCategory(String subNavLink) {
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[contains(.,'Toddler Girl')]]//li[@class='sub-menu-category-item']/a[contains(.,'"+subNavLink+"')]"));
    }

    public WebElement linkUnderBoyCategory(String subNavLink) {
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[contains(.,'Boy')][not(contains(.,'Toddler'))]]//li[@class='sub-menu-category-item']/a[contains(.,'"+subNavLink+"')]"));
    }

    public WebElement categoryByPosition(int i) {
        return getDriver().findElement(By.xpath(".//div[@class='container-global-navigation viewport-container']/div/nav/ul/li[" + i + "]/a"));
    }

    @FindBy(css = ".points-total")
    public WebElement pointsTotalInHeader;

    //DT2 Page objects
    // @FindBy(css = ".overlay-header>.button-overlay-close")
    @FindBy(css = ".overlay-header-desktop>.button-overlay-close")
    public WebElement closeTheOpenDrawerBtn;

    @FindBy(css = ".welcome .wishlist-header .wishlist-link")
    public WebElement wishListLink;

    @FindBy(xpath = "(.//a[@class='wishlist-link'])[2]")
    public WebElement wishlistIcon;

    public List<WebElement> subCategoryLink(String dept) {
        return getDriver().findElements(By.xpath("//a[contains(@href,'/c/')][.='" + dept + "']/following-sibling::div//li"));
    }
//  Newly Added

    @FindBy(css = ".new-account>span")
    public WebElement createNewAccText;

    @FindBy(css = ".new-account>.button-secondary.button-create-account")
    public WebElement createAccBtn;

    @Deprecated//Please refer #wishListLink
    @FindBy(css = ".button-add-to-wishlist")
    public WebElement wishListIcon;

    //DT2 Page objects


    public WebElement deptLinksContainer(String deptName) {
//        return getDriver().findElement(By.xpath("//a[contains(@href,'/c/')][.='"+ deptName +"']/following-sibling::div"));
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[text()='" + deptName + "']]/div"));
    }

    public WebElement sizesTxtLbl(String deptName) {
//        return getDriver().findElement(By.xpath("//a[contains(@href,'/c/')][.='"+ deptName +"']/following-sibling::div//span"));
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[text()='" + deptName + "']]/div/div/span"));

    }

    @FindBy(xpath = ".//a[@class='button-find-store']/span")
    public WebElement findStoreLnk;

    @FindBy(css = ".button-find-store")
    public WebElement findStoreBtn;

    @FindBy(css = ".button-cart")
    public WebElement bagLink;

    @Deprecated//FooterRepo.flagCountryImg
    @FindBy(css = "#countryFlagInToolbar")
    public WebElement flagImg;

    @FindBy(css = ".checkout-header .return-to-bag-link")
    public WebElement returnToBagLnk;

    @FindBy(css = ".navigation-confirmation span")
    public WebElement returnToBagConfTxtLbl;

    @FindBy(css = ".checkout-header .header-title-checkout")
    public WebElement checkoutTxtLbl;

    @FindBy(css = ".navigation-confirmation button.button-stay")
    public WebElement stayInCheckoutBtn;

    @FindBy(css = ".navigation-confirmation button.button-return")
    public WebElement returnToBagBtn;

    @FindBy(css = ".button-container .button-view-bag")
    public WebElement viewBagButtonFromAddToBagConf;

    @FindBy(xpath = ".//a[@class=\"department-name\"]")
    public List<WebElement> overlay_ItemNAme;

    @FindBy(css = ".button-find-store")
    public WebElement storeLocatorLink;

    @FindBy(xpath = ".//a[@class='button-find-store']//span")
    public WebElement changedFavStoreName;

    @FindBy(css = ".my-place-rewards-link")
    public WebElement mprLink;

    @FindBy(xpath = "(.//ul[@class='mpr-links']//a)[1]")
    public WebElement loginLink_MPR;

    @FindBy(xpath = "(.//ul[@class='mpr-links']//a)[2]")
    public WebElement createAccLink_MPR;

    @FindBy(css=".global-sub-nav-banner")
    public WebElement globalNavBanner;

    @FindBy(xpath = "(.//ul[@class='mpr-links']//a)[3]")
    //   @FindBy(xpath = ".mpr-links li a[href*='/content/myplace-rewards-page']")
    public WebElement learnMoreLink_MPR;

    @FindBy(xpath = "//div[@class=\"input-checkbox-title\"]/a [text()='terms & conditions']")
    public WebElement termsAndCondition_Link;

    @FindBy(css = ".contact-us-link")
    public WebElement contactusLink;

    //  @FindBy (xpath = "//div[@class=\"container-links\"]/a[text()=\"Reward terms\"]")
    @FindBy(xpath = "(.//p[@class=\"links\"]/a[text()=\"REWARD TERMS\"])[1]")
    public WebElement rewardsTerms;

    //@FindBy(xpath = ".//div[@id='header-global-banner-container']//img")
    @FindBy(xpath = ".//div[@class='header-global-banner']")
    public WebElement globalEspot;

    @FindBy(xpath = "//button[@class=\"button-overlay-close\"]")
    //@FindBy(css = ".button-modal-close")
    public WebElement closemodal;

    //@FindBy(xpath = "//div[@class=\"links-container\"]/a[text()='Reward terms']")
    @FindBy(xpath = "//*[@class=\"links\"]/a[text()='REWARD TERMS']")
    public List<WebElement> rewardsFromEspot;

    @FindBy(xpath = "//a[@class=\"button-manage\"]")
    public List<WebElement> manageCredit;

    @FindBy(xpath = "//a[@class=\"button-apply button-quaternary\"]")
    public List<WebElement> applyOrAccept;

    @FindBy(xpath = "//a[@class=\"link-footer\"]")
    public WebElement termsLinkRedirection;

    @FindBy(xpath = ".item-navigation-link.log-out")
    public WebElement logoutFooter;

    @FindBy(xpath = ".//header[@class=\"overlay-header-desktop\"]//strong[@class=\"welcome-name\"]")
    public WebElement welcomeCustomerHeaderOverlay;

    @FindBy(xpath = ".//div[@class=\"list-navigation navigation-with-promo\"]//a[contains(.,'Learn')]")
    public WebElement footerLearnMore;

    @FindBy(xpath = ".//button[@class=\"welcome-message\"]//span[contains(.,\"Rewards\")]")
    public WebElement pointsAndRewards_Text;

    @FindBy(xpath = ".//div[@class=\"overlay-content\"]//a")
    public WebElement viewBag;

    @FindBy(xpath = ".//header[@class=\"overlay-header-desktop\"]//div[@class=\"minicart-container\"]")
    public WebElement bagIconDrawer;

    @FindBy(xpath = ".//div[@class=\"cta\"]")
    public WebElement joinNowCTA;

    @FindBy(css = ".container-image>img")
    public WebElement prodImgOnNotiOverlay;

    @FindBy(xpath = ".//button[@class=\"welcome-message\"]//span[contains(.,\"Rewards\")]")
    public WebElement rewardsDisplayHeader;

    @FindBy(xpath = ".//div[@class=\"input-common input-first-name\"]//input")
    public WebElement contanctUs_FirstName;

    @FindBy(xpath = ".//div[@class=\"input-common input-last-name\"]//input")
    public WebElement contanctUs_LastName;

    @FindBy(xpath = "(.//div[@class=\"input-common\"]//input)[1]")
    public WebElement contanctUs_EmailID;

    @FindBy(xpath = "(.//div[@class=\"input-common\"]//input)[2]")
    public WebElement contanctUs_ConfirmEmailID;

    @FindBy(css = ".button-submit.button-secondary")
    public WebElement contanctUs_Submit;

    @FindBy(css = ".custom-select-common.subject.subject")
    public WebElement contactUs_Subject;

    @FindBy(xpath = ".//div[@class=\"inline-error-message\"][contains(.,\"Subject\")]")
    public WebElement selectSubject_ErrMsg;

    @FindBy(xpath = ".//div[@class=\"inline-error-message\"][contains(.,\"Reason\")]")
    public WebElement selectReason_ErrMsg;

    @FindBy(css = ".button-modal-close")
    public WebElement Favclosemodal;

    @FindBy(xpath = "//div[@class='breadcrum-container']/p/a")
    public List<WebElement> breadcrumb_values;

    @FindBy(css = ".back-to-login-button")
    public WebElement backToLogin;

    @FindBy(xpath = ".//div[@class='inline-navigation-container']//a[contains(.,'Style Squad')]")
    public  WebElement link_StyleSquad;

    @FindBy(xpath = ".//div[@class='inline-navigation-container']//a[contains(.,'Outfits')]")
    public  WebElement link_Outfit;

    @FindBy(css = "..custom-loading-icon")
    public WebElement loadingIcon;

    @FindBy(css = ".button-view-bag")
    public WebElement conf_ViewBag;

    @FindBy(css = ".button-checkout")
    public WebElement conf_Checkout;

    @FindBy(css = ".continue-shopping")
    public WebElement conf_ContinueCheckout;

    @FindBy(css = "#added-to-bag-notification .paypal-button")//"div[aria-label='paypal']")//".paypal-button-desktop")
    public WebElement paypal_Conf;

    //Pooja
    @FindBy(xpath = ".//div[@class='container-global-navigation viewport-container']/div/nav/ul/li/a[contains(.,'Accessories')]")
    public WebElement accessories_l1Category;

    //Pooja
    @FindBy(xpath = ".//div[@class='container-global-navigation viewport-container']/div/nav/ul/li/a[contains(.,'Girl')][not(contains(.,'Toddler'))]")
    public WebElement girl_l1Category;

    @FindBy(xpath = ".//div[@class='container-global-navigation viewport-container']/div/nav/ul/li/a[contains(.,'Toddler Girl')]")
    public WebElement toddlerGirl_l1Category;

    @FindBy(xpath = ".//div[@class='container-global-navigation viewport-container']/div/nav/ul/li/a[contains(.,'Boy')][not(contains(.,'Toddler'))]")
    public WebElement boy_l1Category;

    public WebElement link_L2ByL1(String l1, String l2){
        return getDriver().findElement(By.xpath("//a[@class='navigation-level-one-link'][text()='"+l1+"']/following-sibling::div//li[contains(.,'"+l2+"')]/a"));
    }

    @FindBy(xpath = ".//*[@class=\"navigation-level-one-item inline-navigation-active\"][contains(.,\"Outfits\")]")
    public WebElement leftNavActiveCatOutfits;

    @FindBy(id="signupFormPage")
    public WebElement signupEmailLink_Outfits;

    @FindBy(id = "sms-footer-button")
    public WebElement smsLink_Outfits;

    @FindBy(xpath = "//*[@class=\"ecom-black-button\"][contains(.,'FIND')]")
    public WebElement findAStoreLink_Outfits;

    @FindBy(css = ".body-copy")
    public WebElement bodyCopyText;

    public WebElement deptFlyoutCatDisplay(String deptName) {
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[text()='"+ deptName +"']]//h2[contains(.,'Categories')]"));
}
    public WebElement deptFlyoutFeatDisplay(String deptName) {
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[text()='"+ deptName +"']]//h2[contains(.,'Featured Shops')]"));
    }

    //Pooja
    @FindBy(css = ".navigation-level-one")
    public List<WebElement> l1Categories;

    @FindBy(css = ".navigation-level-two-link")
    public List<WebElement> l2Categories;

    //Pooja
    @FindBy(css = ".sub-menu-category-item.navigation-level-three-item")
    public List<WebElement> l3Categories;

    @FindBy(css = ".typeahead-form-container.typeahead-with-image-drawer")
    public WebElement searchSuggestionFlyOut;

    @FindBy(xpath = "//div[@class='input-radio-title']//span[contains(.,'Available')]")
    public WebElement availableSectionInFav;

    @FindBy(css = ".bv-rating-stars-on.bv-rating-stars")
    public List<WebElement> ratingDisplayPDP;

    @FindBy(css = ".product-recomendation.viewport-container")
    public WebElement prodRecomSearch;

    @FindBy(css = ".item-product-recomendation")
    public List<WebElement> recomImag;

    @FindBy(css = ".size-and-fit-detail-container.size-and-fit-detail .selected-option")
    public WebElement fitOptionSelected;

    @FindBy(css=".empty-search-result-suggestion")
    public WebElement didYouMeanText;

    @FindBy(xpath = "//div[@class='bv-dropdown-target']/span[contains(.,\"Sort by:\")]")
    public WebElement sortByOptionPDP;

    @FindBy(css = "link[rel='alternate']")
    public List<WebElement> hrefLangTags;
}

