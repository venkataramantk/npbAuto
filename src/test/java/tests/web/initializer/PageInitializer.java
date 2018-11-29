package tests.web.initializer;

import org.openqa.selenium.WebDriver;
import ui.pages.actions.*;
import ui.utils.JsonParser;
import uiMobile.pages.actions.*;

/**
 * Created by skonda on 5/18/2016.
 */
public class PageInitializer extends BrowserInitializer {
    public HeaderMenuActions headerMenuActions;
    public FooterActions footerActions;
    public SiteMapPageActions siteMapPageActions;
    public LoginPageActions loginPageActions;
    public OrderStatusActions orderStatusActions;
    public DepartmentLandingPageActions departmentLandingPageActions;
    public WishListLoginOverlayActions wishListLoginOverlayActions;
    public LocateAStoreActions locateAStoreActions;
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
        headerMenuActions = new HeaderMenuActions(driver);
        footerActions = new FooterActions(driver);
        siteMapPageActions = new SiteMapPageActions(driver);
        loginPageActions = new LoginPageActions(driver);

        orderStatusActions = new OrderStatusActions(driver);
        departmentLandingPageActions = new DepartmentLandingPageActions(driver);
        wishListLoginOverlayActions = new WishListLoginOverlayActions(driver);

        locateAStoreActions = new LocateAStoreActions(driver);
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
