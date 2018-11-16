package tests.web.initializer;

import org.openqa.selenium.WebDriver;
import ui.pages.actions.*;
import ui.utils.JsonParser;
import uiMobile.pages.actions.*;

/**
 * Created by skonda on 5/18/2016.
 */
public class PageInitializer extends BrowserInitializer {
    public CreateAccountActions createAccountActions;
    public HeaderMenuActions headerMenuActions;
    public HomePageActions homePageActions;
    public FooterActions footerActions;
    public ShoppingBagPageActions shoppingBagPageActions;
    public ShippingPageActions shippingPageActions;
    public SearchResultsPageActions searchResultsPageActions;
    public SiteMapPageActions siteMapPageActions;
    public BillingPageActions billingPageActions;
    public ReviewPageActions reviewPageActions;
    public OrderConfirmationPageActions orderConfirmationPageActions;
    public ProductDetailsPageActions productDetailsPageActions;
    public LoginPageActions loginPageActions;
    public CategoryDetailsPageAction categoryDetailsPageAction;
    public MyAccountPageActions myAccountPageActions;

    public OrderStatusActions orderStatusActions;
    public DepartmentLandingPageActions departmentLandingPageActions;
    public FavoritePageActions favoritePageActions;
    public WishListLoginOverlayActions wishListLoginOverlayActions;
    public LocateAStoreActions locateAStoreActions;
    public ForgotYourPasswordPageActions forgotYourPasswordPageActions;
    public MyAccountPageDrawerActions myAccountPageDrawerActions;
    public OverlayHeaderActions overlayHeaderActions;
    public LoginDrawerActions loginDrawerActions;
    public WishListDrawerActions wishListDrawerActions;
    public ShoppingBagDrawerActions shoppingBagDrawerActions;
    public BopisOverlayActions bopisOverlayActions;
    public CheckoutPickUpDetailsActions checkoutPickUpDetailsActions;
    public PickUpPageActions pickUpPageActions;
    public ProductCardViewActions productCardViewActions;
    public FavoriteOverlayActions favoriteOverlayActions;
    public StoreLocatorPageActions storeLocatorPageActions;
    public StaticContentCarrerPageActions staticContentCarrerPageActions;

    //Mobile Pages
    public MSearchResultsPageActions mSearchResultsPageActions;
    public MProductDetailsPageActions mproductDetailsPageActions;
    public MobileHomePageActions mhomePageActions;
    public MobileHeaderMenuActions mheaderMenuActions;
    public MobileBopisOverlayActions mbopisOverlayActions;
    public MLoginPageActions mloginPageActions;
    public MobileDepartmentLandingPageActions mdepartmentLandingPageActions;
    public MCategoryDetailsPageAction mcategoryDetailsPageAction;
    public MCreateAccountActions mcreateAccountActions;

    public MSearchResultsPageActions msearchResultsPageActions;
    public MGiftCardsPageActions mgiftCardsPageActions;
    public MShoppingBagPageActions mshoppingBagPageActions;
    public MShippingPageActions mshippingPageActions;


    public MobileSiteMapPageActions msiteMapPageActions;
    public MBillingPageActions mbillingPageActions;
    public MReviewPageActions mreviewPageActions;
    public MReceiptThankYouPageActions mreceiptThankYouPageActions;

    public MMyAccountPageActions mmyAccountPageActions;

    public MPayPalPageActions mpayPalPageActions;

    public MobileOrderStatusActions morderStatusActions;
    public PanCakePageActions panCakePageActions;
    public MMyPlaceRewardsActions mMyPlaceRewardsActions;

    public MobileFavouritesActions mobileFavouritesActions;

    public MobileWishListLoginOverlayActions mwishListLoginOverlayActions;

    public MForgotPasswordPageActions mforgotYourPasswordPageActions;
    public MobileMyAccountPageDrawerActions mmyAccountPageDrawerActions;
    public MobileOverlayHeaderActions moverlayHeaderActions;
    public MLoginPageActions mloginDrawerActions;
    public MRecommendationsActions mRecommendationsActions;


    public MobileCheckoutPickUpDetailsActions mcheckoutPickUpDetailsActions;
    public MobilePickUpPageActions mpickUpPageActions;
    public MobileFooterMPRCreditCardActions mfooterMPRCreditCardActions;
    public MobileMPROverlayActions mmprOverlayActions;
    public MobileProductCardViewActions mproductCardViewActions;
    public MStoreLocatorPageActions mstoreLocatorPageActions;
    public MobileIntCheckoutPageActions mobileIntCheckoutPageActions;


    public void initializePages(WebDriver driver) {
        createAccountActions = new CreateAccountActions(driver);
        headerMenuActions = new HeaderMenuActions(driver);
        homePageActions = new HomePageActions(driver);
        footerActions = new FooterActions(driver);
        shoppingBagPageActions = new ShoppingBagPageActions(driver);
        shippingPageActions = new ShippingPageActions(driver);
        searchResultsPageActions = new SearchResultsPageActions(driver);
        siteMapPageActions = new SiteMapPageActions(driver);
        billingPageActions = new BillingPageActions(driver);
        reviewPageActions = new ReviewPageActions(driver);
        orderConfirmationPageActions = new OrderConfirmationPageActions(driver);
        productDetailsPageActions = new ProductDetailsPageActions(driver);
        loginPageActions = new LoginPageActions(driver);
        categoryDetailsPageAction = new CategoryDetailsPageAction(driver);
        myAccountPageActions = new MyAccountPageActions(driver);

        orderStatusActions = new OrderStatusActions(driver);
        departmentLandingPageActions = new DepartmentLandingPageActions(driver);
        favoritePageActions = new FavoritePageActions(driver);
        wishListLoginOverlayActions = new WishListLoginOverlayActions(driver);

        locateAStoreActions = new LocateAStoreActions(driver);
        forgotYourPasswordPageActions = new ForgotYourPasswordPageActions(driver);
        myAccountPageDrawerActions = new MyAccountPageDrawerActions(driver);
        overlayHeaderActions = new OverlayHeaderActions(driver);
        loginDrawerActions = new LoginDrawerActions(driver);
        wishListDrawerActions = new WishListDrawerActions(driver);
        shoppingBagDrawerActions = new ShoppingBagDrawerActions(driver);
        bopisOverlayActions = new BopisOverlayActions(driver);
        checkoutPickUpDetailsActions = new CheckoutPickUpDetailsActions(driver);
        pickUpPageActions = new PickUpPageActions(driver);

        productCardViewActions = new ProductCardViewActions(driver);
        favoriteOverlayActions = new FavoriteOverlayActions(driver);
        storeLocatorPageActions = new StoreLocatorPageActions(driver);
        staticContentCarrerPageActions = new StaticContentCarrerPageActions(driver);
    }

    public void initializeMobilePages(WebDriver mobileDriver) {
        mcreateAccountActions = new MCreateAccountActions(mobileDriver);
        mheaderMenuActions = new MobileHeaderMenuActions(mobileDriver);
        mhomePageActions = new MobileHomePageActions(mobileDriver);
        mgiftCardsPageActions = new MGiftCardsPageActions(mobileDriver);
        mshoppingBagPageActions = new MShoppingBagPageActions(mobileDriver);
        mshippingPageActions = new MShippingPageActions(mobileDriver);
        msearchResultsPageActions = new MSearchResultsPageActions(mobileDriver);
        msiteMapPageActions = new MobileSiteMapPageActions(mobileDriver);
        mbillingPageActions = new MBillingPageActions(mobileDriver);
        mreviewPageActions = new MReviewPageActions(mobileDriver);
        mreceiptThankYouPageActions = new MReceiptThankYouPageActions(mobileDriver);
        mproductDetailsPageActions = new MProductDetailsPageActions(mobileDriver);
        mloginPageActions = new MLoginPageActions(mobileDriver);
        mcategoryDetailsPageAction = new MCategoryDetailsPageAction(mobileDriver);
        mmyAccountPageActions = new MMyAccountPageActions(mobileDriver);
        panCakePageActions = new PanCakePageActions(mobileDriver);
        mMyPlaceRewardsActions = new MMyPlaceRewardsActions(mobileDriver);
        mpayPalPageActions = new MPayPalPageActions(mobileDriver);


        morderStatusActions = new MobileOrderStatusActions(mobileDriver);
        mdepartmentLandingPageActions = new MobileDepartmentLandingPageActions(mobileDriver);
        mwishListLoginOverlayActions = new MobileWishListLoginOverlayActions(mobileDriver);
        mRecommendationsActions = new MRecommendationsActions(mobileDriver);

        mforgotYourPasswordPageActions = new MForgotPasswordPageActions(mobileDriver);
        mmyAccountPageDrawerActions = new MobileMyAccountPageDrawerActions(mobileDriver);
        moverlayHeaderActions = new MobileOverlayHeaderActions(mobileDriver);
        mloginDrawerActions = new MLoginPageActions(mobileDriver);
        mbopisOverlayActions = new MobileBopisOverlayActions(mobileDriver);
        mcheckoutPickUpDetailsActions = new MobileCheckoutPickUpDetailsActions(mobileDriver);
        mpickUpPageActions = new MobilePickUpPageActions(mobileDriver);
        mfooterMPRCreditCardActions = new MobileFooterMPRCreditCardActions(mobileDriver);
        mmprOverlayActions = new MobileMPROverlayActions(mobileDriver);
        mproductCardViewActions = new MobileProductCardViewActions(mobileDriver);
        mstoreLocatorPageActions = new MStoreLocatorPageActions(mobileDriver);
        mobileIntCheckoutPageActions = new MobileIntCheckoutPageActions(mobileDriver);
        mobileFavouritesActions = new MobileFavouritesActions(mobileDriver);
    }


}
