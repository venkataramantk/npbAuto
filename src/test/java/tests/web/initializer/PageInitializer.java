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
    public GiftCardsPageActions giftCardsPageActions;
    public ShoppingBagPageActions shoppingBagPageActions;
    public ShippingPageActions shippingPageActions;
    public SearchResultsPageActions searchResultsPageActions;
    public SiteMapPageActions siteMapPageActions;
    public BillingPageActions billingPageActions;
    public ReviewPageActions reviewPageActions;
    public OrderConfirmationPageActions orderConfirmationPageActions;
    public ProductDetailsPageActions productDetailsPageActions;
    public DomHomePageActions domHomePageActions;
    public DomSearchOrderPageActions domSearchOrderPageActions;
    public MIFActions mifActions;
    public LoginPageActions loginPageActions;
    public CategoryDetailsPageAction categoryDetailsPageAction;
    public MyAccountPageActions myAccountPageActions;
    public RTPSAndWicActioins plccActions;

    public PayPalPageActions payPalPageActions;
    public PaypalOrderDetailsPageActions paypalOrderDetailsPageActions;

    public OrderStatusActions orderStatusActions;
    public GiftOptionOverlayActions giftOptionOverlayActions;
    public DepartmentLandingPageActions departmentLandingPageActions;
    public DomCreateReturnActions domCreateReturnActions;
    public DomCustomerTransactionsActions domCustomerTransactionsActions;
    public DomCustomerOrderActions domCustomerOrderActions;
    public FavoritePageActions favoritePageActions;
    public WishListLoginOverlayActions wishListLoginOverlayActions;
    public DomManageAvailabilityPageActions domManageAvailabilityPageActions;
    public DomStoreOrdersPageActions domStoreOrdersPageActions;
    public DomSalesOrdersPageActions domSalesOrdersPageActions;
    public MyPlaceRewardsActions myPlaceRewardsActions;
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
    public FooterMPRCreditCardActions footerMPRCreditCardActions;
    public MPROverlayActions mprOverlayActions;
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


    public MFooterActions mfooterActions;
    public MSearchResultsPageActions msearchResultsPageActions;
    public MGiftCardsPageActions mgiftCardsPageActions;
    public MShoppingBagPageActions mshoppingBagPageActions;
    public MShippingPageActions mshippingPageActions;


    public MobileSiteMapPageActions msiteMapPageActions;
    public MBillingPageActions mbillingPageActions;
    public MReviewPageActions mreviewPageActions;
    public MReceiptThankYouPageActions mreceiptThankYouPageActions;

    public MobileDomHomePageActions mdomHomePageActions;
    public MobileDomSearchOrderPageActions mdomSearchOrderPageActions;
    public MMyAccountPageActions mmyAccountPageActions;
    public MRTPSAndWicActions mplccActions;

    public MPayPalPageActions mpayPalPageActions;

    public MobileOrderStatusActions morderStatusActions;
    public PanCakePageActions panCakePageActions;
    public MMyPlaceRewardsActions mMyPlaceRewardsActions;
    public MobileDomCreateReturnActions mdomCreateReturnActions;
    public MobileDomCustomerTransactionsActions mdomCustomerTransactionsActions;
    public MobileDomCustomerOrderActions mdomCustomerOrderActions;

    public MobileFavouritesActions mobileFavouritesActions;

    public MobileWishListLoginOverlayActions mwishListLoginOverlayActions;
    public MobileDomManageAvailabilityPageActions mdomManageAvailabilityPageActions;
    public MobileDomStoreOrdersPageActions mdomStoreOrdersPageActions;
    public MobileDomSalesOrdersPageActions mdomSalesOrdersPageActions;

    public MForgotPasswordPageActions mforgotYourPasswordPageActions;
    public MobileMyAccountPageDrawerActions mmyAccountPageDrawerActions;
    public MobileOverlayHeaderActions moverlayHeaderActions;
    public MLoginPageActions mloginDrawerActions;
    public MobileMIFActions mmifActions;
    public MRecommendationsActions mRecommendationsActions;
    public MCareersPageActions mCareersPageActions;

    public MobileCheckoutPickUpDetailsActions mcheckoutPickUpDetailsActions;
    public MobilePickUpPageActions mpickUpPageActions;
    public MobileFooterMPRCreditCardActions mfooterMPRCreditCardActions;
    public MobileMPROverlayActions mmprOverlayActions;
    public MobileProductCardViewActions mproductCardViewActions;
    public MStoreLocatorPageActions mstoreLocatorPageActions;
    public MobileIntCheckoutPageActions mobileIntCheckoutPageActions;
    public MEmailUsPageActions mEmailUsPageActions;

    public void initializePages(WebDriver driver) {
        createAccountActions = new CreateAccountActions(driver);
        headerMenuActions = new HeaderMenuActions(driver);
        homePageActions = new HomePageActions(driver);
        footerActions = new FooterActions(driver);
        giftCardsPageActions = new GiftCardsPageActions(driver);
        shoppingBagPageActions = new ShoppingBagPageActions(driver);
        shippingPageActions = new ShippingPageActions(driver);
        searchResultsPageActions = new SearchResultsPageActions(driver);
        siteMapPageActions = new SiteMapPageActions(driver);
        billingPageActions = new BillingPageActions(driver);
        reviewPageActions = new ReviewPageActions(driver);
        plccActions = new RTPSAndWicActioins(driver);
        orderConfirmationPageActions = new OrderConfirmationPageActions(driver);
        productDetailsPageActions = new ProductDetailsPageActions(driver);
        domHomePageActions = new DomHomePageActions(driver);
        domSearchOrderPageActions = new DomSearchOrderPageActions(driver);
        mifActions = new MIFActions(driver);
        loginPageActions = new LoginPageActions(driver);
        categoryDetailsPageAction = new CategoryDetailsPageAction(driver);
        myAccountPageActions = new MyAccountPageActions(driver);

        payPalPageActions = new PayPalPageActions(driver);

        paypalOrderDetailsPageActions = new PaypalOrderDetailsPageActions(driver);
        orderStatusActions = new OrderStatusActions(driver);
        giftOptionOverlayActions = new GiftOptionOverlayActions(driver);
        departmentLandingPageActions = new DepartmentLandingPageActions(driver);
        domCreateReturnActions = new DomCreateReturnActions(driver);
        domCustomerTransactionsActions = new DomCustomerTransactionsActions(driver);
        domCustomerOrderActions = new DomCustomerOrderActions(driver);
        favoritePageActions = new FavoritePageActions(driver);
        wishListLoginOverlayActions = new WishListLoginOverlayActions(driver);
        domManageAvailabilityPageActions = new DomManageAvailabilityPageActions(driver);

        domStoreOrdersPageActions = new DomStoreOrdersPageActions(driver);
        domSalesOrdersPageActions = new DomSalesOrdersPageActions(driver);
        myPlaceRewardsActions = new MyPlaceRewardsActions(driver);
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
        footerMPRCreditCardActions = new FooterMPRCreditCardActions(driver);
        mprOverlayActions = new MPROverlayActions(driver);
        productCardViewActions = new ProductCardViewActions(driver);
        favoriteOverlayActions = new FavoriteOverlayActions(driver);
        storeLocatorPageActions = new StoreLocatorPageActions(driver);
        staticContentCarrerPageActions = new StaticContentCarrerPageActions(driver);
    }

    public void initializeMobilePages(WebDriver mobileDriver) {
        mcreateAccountActions = new MCreateAccountActions(mobileDriver);
        mheaderMenuActions = new MobileHeaderMenuActions(mobileDriver);
        mhomePageActions = new MobileHomePageActions(mobileDriver);
        mfooterActions = new MFooterActions(mobileDriver);
        mgiftCardsPageActions = new MGiftCardsPageActions(mobileDriver);
        mshoppingBagPageActions = new MShoppingBagPageActions(mobileDriver);
        mshippingPageActions = new MShippingPageActions(mobileDriver);
        msearchResultsPageActions = new MSearchResultsPageActions(mobileDriver);
        msiteMapPageActions = new MobileSiteMapPageActions(mobileDriver);
        mbillingPageActions = new MBillingPageActions(mobileDriver);
        mreviewPageActions = new MReviewPageActions(mobileDriver);
        mreceiptThankYouPageActions = new MReceiptThankYouPageActions(mobileDriver);
        mproductDetailsPageActions = new MProductDetailsPageActions(mobileDriver);
        mdomHomePageActions = new MobileDomHomePageActions(mobileDriver);
        mdomSearchOrderPageActions = new MobileDomSearchOrderPageActions(mobileDriver);
        mmifActions = new MobileMIFActions(mobileDriver);
        mloginPageActions = new MLoginPageActions(mobileDriver);
        mcategoryDetailsPageAction = new MCategoryDetailsPageAction(mobileDriver);
        mmyAccountPageActions = new MMyAccountPageActions(mobileDriver);
        panCakePageActions = new PanCakePageActions(mobileDriver);
        mMyPlaceRewardsActions = new MMyPlaceRewardsActions(mobileDriver);
        mpayPalPageActions = new MPayPalPageActions(mobileDriver);


        morderStatusActions = new MobileOrderStatusActions(mobileDriver);
        mdepartmentLandingPageActions = new MobileDepartmentLandingPageActions(mobileDriver);
        mdomCreateReturnActions = new MobileDomCreateReturnActions(mobileDriver);
        mdomCustomerTransactionsActions = new MobileDomCustomerTransactionsActions(mobileDriver);
        mdomCustomerOrderActions = new MobileDomCustomerOrderActions(mobileDriver);
        mwishListLoginOverlayActions = new MobileWishListLoginOverlayActions(mobileDriver);
        mdomManageAvailabilityPageActions = new MobileDomManageAvailabilityPageActions(mobileDriver);
        mRecommendationsActions = new MRecommendationsActions(mobileDriver);
        mCareersPageActions = new MCareersPageActions(mobileDriver);
        mEmailUsPageActions = new MEmailUsPageActions(mobileDriver);

        mdomStoreOrdersPageActions = new MobileDomStoreOrdersPageActions(mobileDriver);
        mdomSalesOrdersPageActions = new MobileDomSalesOrdersPageActions(mobileDriver);

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
        mplccActions = new MRTPSAndWicActions(mobileDriver);
        mobileIntCheckoutPageActions = new MobileIntCheckoutPageActions(mobileDriver);
        mobileFavouritesActions = new MobileFavouritesActions(mobileDriver);
    }


}
