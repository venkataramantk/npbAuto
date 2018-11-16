package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class FooterRepo extends UiBase {

    @FindBy(xpath = "//div[@class='footer-mpr footer-row-top-col']//img[contains(@alt,'My Place Rewards') or contains(@alt,'Recompensas My Place')]")
    public WebElement img_MySearchRewards;

    @FindBy(xpath = "//*[.='My Place Rewards']//following-sibling::li/a[contains(.,'Learn More') or contains(.,'Saber más')]")
    public List<WebElement> link_MySearchRewards_LearnMore;

    @FindBy(xpath = "//img[@alt='E-Gift Card' or @alt='Gift Card or E-Gift Card' or @alt='Tarjeta de regalo o tarjeta de regalo electrónica']")
    public WebElement img_GiftCards;

    //Present in Phase 1 with identifier link_GiftCards_GetitNow
    @FindBy(xpath = "//a[contains(.,'Get It Now') or contains(.,'Obtenla ahora') or contains(.,'Achetez-en une')]")
    public WebElement getItNow_GiftCardLink;

    @FindBy(xpath = "//img[@alt='My Place Rewards Credit Card' or @alt='Tarjetas de créditos My Place Rewards']")
    public WebElement img_PlaceCreditCard;

    @FindBy(xpath = "//a[contains(.,'Apply Today') or contains(.,'Solicitar hoy')]")
    public WebElement link_PlaceCreditCard_ApplyNow;

    @FindBy(xpath = "//img[@alt='Mobile App' or @alt='Aplicación móvil']")
    public WebElement img_MobileApp;

    @FindBy(xpath = "//a[contains(.,'Download It') or contains(.,'Descárgala') or contains(.,'Téléchargez-la')]")
    public WebElement link_MobileApp_Downloadit;

    @FindBy(css = ".navigation-and-newsletter")
    public WebElement footerSection;

    @FindBy(xpath = "//img[@alt='The Mom Space' or @alt='El espacio para mamás']")
    public WebElement img_TheMomSpace;

    //left Section
    @FindBy(xpath = "//*[contains(@class,'footer-email')]/span")
    public WebElement lbl_SignUpText;

    @FindBy(xpath = "//*[contains(@class,'footer-email')] //a[contains(.,'SIGN UP') or contains(.,'SUSCRIBIRSE') or contains(.,'INSCRIRE')]")
    public WebElement link_SignUp;

    //Middle section

    @FindBy(xpath = "//*[contains(@class,'footer-social')] //span[contains(.,'Connect with us.') or contains(.,'Contáctanos.') or contains(.,'Communiquez avec nous.') or contains(.,'Suivez-nous.')]")
    public WebElement lbl_ConnectWithUs;


    //Canada Site
    @FindBy(xpath = "//img[contains(@alt,'Air Miles')]")
    public WebElement img_AirMiles;

    @FindBy(xpath = "//a[contains(.,'Learn More') or contains(.,'En savoir plus')]")
    public WebElement link_AirMilesLearnMore;

    public WebElement footerSocialLinksByName(String linkName) {
        return getDriver().findElement(By.xpath("//*[contains(@class,'footer-social')] //a[@title='" + linkName + "']"));
    }

    //right section
    @FindBy(xpath = "//*[contains(@class,'footer-store')] //span[contains(.,'Find a store near you.') or contains(.,'Busca una tienda cercana.') or contains(.,'Trouvez un magasin près de chez vous.') or contains(.,'Magasins à proximité.')]")
    public WebElement lbl_FindaStore;

    @FindBy(xpath = "//*[contains(@class,'footer-store')] //a[contains(.,'LOCATE A STORE') or contains(.,'BUSCAR TIENDA') or contains(.,'TROUVER UN MAGASIN')]")
    public WebElement link_LocateAStore;

    @FindBy(css = ".hc-link-selected")
    public WebElement helpCenterLinkSelected;

    public WebElement footerLinksByName(String linkName) {
        return getDriver().findElement(By.xpath("//div[@class='container-navigation']//a[contains(.,'" + linkName + "')]"));

    }

    public WebElement footerButtonsByName(String linkName) {
        return getDriver().findElement(By.xpath("//div[@class='container-navigation']//button[.='" + linkName + "']"));

    }

    public WebElement footerLinkDataId(String linkName) {
        return getDriver().findElement(By.cssSelector("button[data-id='" + linkName + "']"));
    }

    public WebElement footerLinksByTitle(String linkName) {
        return getDriver().findElement(By.xpath("//div[@class='container-navigation']//button[.='" + linkName + "']"));
    }

    public WebElement socialMediaLinksInFooter(String linkName) {
        return getDriver().findElement(By.xpath("//div[@class='social-networks']//a[@title='" + linkName + "']"));

    }

    public WebElement MPRLinksInFooter(String linkName) {
    //    return getDriver().findElement(By.xpath("//div[@class='container-navigation']//a[.= '" + linkName + "']"));
        return getDriver().findElement(By.xpath("//div[@class='container-navigation']//*[.= '" + linkName + "']"));
    }

    //
    public WebElement footerLinkMyPlaceRewardCCByName(String linkName) {
        return getDriver().findElement(By.xpath(".//div[@class='list-navigation navigation-with-promo']" + linkName + "']"));
    }

    public List<WebElement> footerLinkMyPlaceRewardByName(String linkName) {
        return getDriver().findElements(By.xpath(".//div[@class='list-navigation list-navigation-open navigation-with-promo']" + linkName + "']"));
    }

    @FindBy(xpath = "//*[@class='footer-links']//a[contains(.,'Site Map') or contains(.,'Mapa del sitio') or contains(.,'Plan du site')]")
    public WebElement siteMapLink;

    /*@FindBy(xpath = "//div[@class='footer-links']//a[contains(.,'Create An Account')]")*/
//    @FindBy(id = "create-account")
//    @FindBy(xpath = "//div[@class='list-navigation'][contains(.,'Shopping')]/div/button[@class='item-navigation-link create-account']")//.//div[@class='list-navigation list-navigation-open navigation-with-promo']//a[.='Create An Account']")
    @FindAll({
    @FindBy(xpath = ".//h4[text()='My Place Rewards']/parent::*/div/button[text()='Create An Account']"),
    @FindBy(css = "button[data-id='CreateAnAccount']")
    })
    public WebElement link_CreateAnAccount;

    //    @FindBy(xpath = ".//div[@class='list-navigation list-navigation-open navigation-with-promo']//a[.='Check Points Balance']")
    @FindBy(css = ".item-navigation-link.check-point-balance")
    public WebElement link_ChkPntBalance;

    //    @FindBy(xpath = ".//div[@class='list-navigation list-navigation-open navigation-with-promo']//a[.='Redeem Rewards']")
    @FindBy(css = ".item-navigation-link.redeem-rewards")
    public WebElement link_RedeemRewards;

    //    @FindBy(xpath = ".//div[@class='list-navigation list-navigation-open navigation-with-promo']//a[.='Member Benefits']")
    @FindBy(xpath = ".//h4[text()='My Place Rewards']/parent::*/div/a[text()='Member Benefits']")
    public WebElement link_MembersBenefit;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Pay Your Bill']")
    public WebElement link_PayYourBill;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Apply Now']")
    public WebElement link_ApplyNow;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Manage Your Account']")
    public WebElement link_ManageYouAcc;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Learn More']")
    public WebElement link_LearnMore;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='FAQs']")
    public WebElement link_FAQs;

    //    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Order Status']")
    @FindBy(xpath = ".//button[@class='item-navigation-link'][.='Order Status']")
    public WebElement link_OrderStatus;

    @FindBy(xpath = ".//a[@class=\"order-number\"]")
    public List<WebElement> orderNumberHistory;

    @FindBy(xpath = ".//div[@class=\"breadcrum-link\"]/a[@class=\"orders-link\"]")
    public WebElement orderBreadcrumb;

    @FindBy(xpath = ".//button[contains(.,\"Order Status\")]")
    public WebElement orderStausLink_Footer;

    @FindBy(css = ".order-or-reservation-list")
    public WebElement orderStatusTable;

    //    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Check Reservation Status']")
    @FindBy(xpath = ".//button[@class='item-navigation-link'][.='Check Reservation Status']")
    public WebElement link_ChkReservationStatus;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Return Policy']")
    public WebElement link_ReturnPolicy;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Shipping Options']")
    public WebElement link_ShippingOption;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Gift Card Balance']")
    public WebElement link_GCBal;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[contains(.,'Privacy Policy')]")
    public WebElement link_PrivacyPolicy;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Internet Based Ads']")
    public WebElement link_InternetAds;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Terms and Conditions']")
    public WebElement link_TermsAndCondition;

    //    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='My Account']")
    @FindBy(css = ".item-navigation-link.my-account")
    public WebElement link_MyAcc;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Coupons']")
    public WebElement link_Coupons;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Store Locator']")
    public WebElement link_StoreLocator;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Size Chart']")
    public WebElement link_SizeChart;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Gift Cards']")
    public WebElement link_GiftCards;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Gift Services']")
    public WebElement link_GiftServices;

    @FindBy(xpath = ".//div[@class='container-navigation']//button[.='Favorites']")
    public WebElement link_Fav;

    @FindBy(xpath = ".//a[@title='Gift Wrapping']")
    public WebElement link_GiftWrapping;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Mobile App']")
    public WebElement link_MobileApp;

    @FindBy(css = ".item-navigation-link.wishlist")
    public WebElement link_WishList;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Seasonal Lookbooks']")
    public WebElement link_SeasonalLoookbooks;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Public Relations']")
    public WebElement link_PublicRelation;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Investor Relations']")
    public WebElement link_InvestorRelation;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Careers']")
    public WebElement link_Careers;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Responsible Sourcing']")
    //   @FindBy(xpath = ".//a[@title='Social Responsibility']")
    public WebElement link_SocialResponse;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='International Opportunities']")
    public WebElement link_InterOpportuinities;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Recall Information']")
    public WebElement link_RecallInfo;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='The Mom Space']")
    public WebElement link_MomSpace;

    @FindBy(xpath = ".//nav[@class='item-navigation']//a[contains(.,'Privacy Policy')]")
    public WebElement link_FooterPrivacy;

    @FindBy(xpath = ".//nav[@class='item-navigation']//a[.='California Supply Chains Act']")
    public WebElement link_SupplyChain;

    @FindBy(xpath = ".//nav[@class='item-navigation']//a[.='Site Map']")
    public WebElement link_SiteMap;

    @FindBy(xpath = ".//nav[@class='item-navigation']//a[.='Terms and Conditions']")
    public WebElement termsAndConditionLnk;
    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Twitter']")
    public WebElement link_Twitter;

    @FindBy(xpath = "..//div[@class='social-networks']//a[@title='Pinterest']")
    public WebElement link_Pinterest;

    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Facebook']")
    public WebElement link_Facebook;

    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Instagram']")
    public WebElement link_Instagram;

    @FindBy(xpath = ".//div[@class='department-name-title']/a[.='Help Center']")
    public WebElement helpCenterPageLbl;

    @FindBy(css = "a[id='ropisOrderHistory']")
    public WebElement checkReserveStatus;

    @FindBy(css = "#ropisOrderHistory[title='My Reservations']")
    public WebElement myReservationsLink;

    @FindBy(xpath = "(//div[@class='footer-links'])/div/div/ul/li[not(contains(@class,'footer-title'))]")
    public List<WebElement> footerLinks;

    @FindBy(xpath = "//div[@class='content-language']/button[text()='en']")
    public WebElement enLink;

    @FindBy(css = "//div[@class='content-language']/button[text()='es']")
    public WebElement esLink;

    @FindBy(css = "//div[@class='content-language']/button[text()='fr']")
    public WebElement frLink;

    @FindBy(css = ".country-selector-elements .button-primary")
    public WebElement saveBtn;

    @FindBy(xpath = ".//select[@name='country']")
    public WebElement shipToCountryDropdown;

    @FindBy(xpath = ".//select[@name='language']")
    public WebElement langDropdown;

    @FindBy(css = ".content-language")//(css=".content-language>button:nth-child(1)")
    public WebElement languageButton;

    @FindBy(css = ".country-selector img")
    public WebElement flagCountryImg;

    public WebElement flagNameByCountry(String country){
        return getDriver().findElement(By.xpath("//img[@alt='"+country+" flag']"));
    }

    @FindBy(css = ".container-popup")
    public WebElement shipToModalDialog;

    @FindBy(css = ".button-modal-close")
    public WebElement closeLinkOnShipTOModal;

    //ShipTo link is not a link
    @FindBy(css = ".title-country-selector")
    public WebElement shipToLink;

    @FindBy(css = ".stores>a")
    public WebElement storesLink;

    @FindBy(xpath = "(.//img[@class='navigation-with-promo-image'])[2]")
    public WebElement image_Plcc;

    //    @FindBy(xpath = ".//div/a[@class='gf-tcp-button'][contains(@href,'gift-cards')]")//.//div[@class='gc-right-btn']/a")
    @FindBy(xpath = "//a[text()='MAIL A GIFT CARD']")////div[@class=\"gc-right-btn\"]/a[@class=\"button-blue\"]")
    public WebElement sendGC_Btn;

    @FindBy(css = ".continue-shopping")
    public WebElement continueShoppingLink;

    @FindBy(css = ".email-link")
    public WebElement emailUS_Lnk;

    @FindBy(css = "[id='phoneNumber_4']")
    public WebElement phoneNumberForm;

    @FindBy(xpath = "//div[@class=\"my-account-options\"]/a[@class=\"view\"]")
    public WebElement viewMyAccount;


    @FindBy(xpath = ".//div[@class=\"col-md-6 col-sm-6 col-xs-12\"]//input[@class=\"email-signup-input\"]")
    public WebElement signUpEmail;

    @FindBy(css = ".btn.pushdown")
    public WebElement submitButton;

    @FindBy(xpath = ".//div[@class=\"buttons\"]/a[text()=\"Shop Now\"]")
    public WebElement shopNowButton;

    @FindBy(xpath = ".//div[@class=\"buttons\"]/a[text()=\"Locate a Store\"]")
    public WebElement locateAStore;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price offer-price']")
    public WebElement prodprice;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price offer-price']")
    public WebElement prodWasPrice;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price list-price']")
    public List<WebElement> prodWithPrice;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price list-price']")
    public WebElement offerPrice;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li")
    public List<WebElement> prods;

    public WebElement prodWithPriceByPos(int i) {
        return getDriver().findElement(By.xpath("(.//div[@class='container-product-recomendation']//li//span[@class='text-price offer-price'])[" + i + "]"));
    }

    @FindBy(xpath = ".//div[@class=\"product-recomendation viewport-container\"]/h2[text()=\"You May Also Like\"]")
    public WebElement youMayAlsoLike;

    @FindBy(xpath = ".//div[@class=\"product-recomendation viewport-container\"]/div/ol[@class=\"content-product-recomendation\"]")
    public WebElement productDisplay;

    @FindBy(css = ".button-prev")
    public WebElement previouscarousel;

    @FindBy(css = ".button-next")
    public WebElement nextcarousel;

    @FindBy(xpath = ".//div[@class=\"container-product-recomendation\"]/button[@class=\"button-next\"]")
    public WebElement navButton;

    @FindBy(xpath = ".//button[@class=\"button-add-to-bag\"]")
    // @FindBy(xpath = ".//div[@class=\"recommendations-and-outfit-container\"]//button[@class=\"button-add-to-bag\"]")
    //@FindBy (xpath = ".//ol[@class=\"content-product-recomendation\"]//button[@class=\"button-add-to-bag\"]")
    public WebElement addToBagRecom;

    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure//a")
    public List<WebElement> prodRecommendation;

    @FindBy(css = ".heading-product-recomendation")
    public WebElement recommendationHeading;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price offer-price']//ancestor::li//div/button[@class=\"hover-button-enabled favorite-icon-container\"]")
    public List<WebElement> addToFavIcon;

    @FindBy(xpath = "//div[@class='container-product-recomendation']//li//span[@class='text-price offer-price']//ancestor::li//div/button[@class='bag-icon-container hover-button-enabled']")
    public List<WebElement> addToBagIcon;

    @FindBy(css = ".favorite-icon-active.hover-button-enabled.favorite-icon-container")
    public List<WebElement> favIconEnabled;

    @FindBy(xpath = ".//div[@class=\"select-common select-country\"]//span[@class=\"selection select-option-selected\"]")
    public WebElement selectedCountry;

    @FindBy(xpath = ".//div[@class=\"select-common select-language\"]//select")
    public WebElement availaableLangUS;

    @FindBy(xpath = ".//div[@class=\"select-common select-currency\"]//option")
    public List<WebElement> shipTOCurrency;

    @FindBy(xpath = ".//div[@class=\"select-common select-currency\"]//option[contains(.,\"Indian Rupee\")]")
    public WebElement indianCurrency;

    @FindBy(css = ".button-add-to-wishlist")
    public List<WebElement> wishListIcons;

    @FindBy(xpath = ".//p[@class=\"note-clarification\"]")
    public WebElement noteTextInShipToModal;

    @FindBy(xpath = ".//header[@class=\"header-global\"]//div[@class=\"content-global-navigation header-sticky\"]")
    public WebElement stickeyHeader;

    @FindBy(xpath = ".//nav[@class=\"item-navigation\"]//a[contains(.,\"Terms\")]")
    public WebElement link_ItermNavTermsAndCondition;

    @FindBy(xpath = ".//nav[@class=\"item-navigation\"]//a[contains(.,\"Supply Chain\")]")
    public WebElement link_ItermNavSupplyChainAct;

    @FindBy(xpath = ".//nav[@class=\"item-navigation\"]//a[contains(.,\"Site Map\")]")
    public WebElement link_ItermNavSiteMap;

    //@FindBy(xpath = ".//*[@class=‘title’][contains(text(),‘Site Map’)]")
    @FindBy(css = ".title")
    public WebElement siteMap_title;

    @FindBy(css = "meta[name='description']")
    public WebElement metaDescription;

    @FindBy(css = ".site-map-container .title")
    public WebElement siteMapHeading;

    //@FindBy(xpath = "(.//*[@classname=‘level-three-title’][contains(text(),‘Boys Outfits’)])[1]")
    @FindBy(xpath = "(//*[@class=\"level-three-container active\"][contains(.,\"Boys Outfits\")])[1]")
    public WebElement boys_Outfits;

    //@FindBy(xpath = "(.//*[@classname=‘level-three-title’][contains(text(),‘Girls Outfits’)])[1]")
    @FindBy(xpath = "(//*[@class=\"level-three-container active\"][contains(.,\"Girls Outfits\")])[1]")
    public WebElement girls_Outfits;

    //@FindBy(xpath = "(.//*[@classname=‘level-three-title’][contains(text(),‘Girls Outfits’)])[2]")
    @FindBy(xpath = "(//*[@class=\"level-three-container active\"][contains(.,\"Girls Outfits\")])[2]")
    public WebElement toddlergirls_Outfits;

    //@FindBy(xpath = ".//*[@class=‘item-title’][contains(.,‘Outfits’)]")
    @FindBy(xpath = "//*[@class=\"item-title\"][contains(.,'Outfits')]")
    public WebElement outfits_title;

    @FindBy(xpath = ".//nav[@class=\"item-navigation\"]//a[contains(.,\"Privacy\")]")
    public WebElement link_ItermNavPrivacyPolicy;

    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price list-price no-badge']//ancestor::li//h3")
    public List<WebElement> prodNameRecommWithPrice;

    @FindBy(xpath = ".//p[@class=\"note-clarification\"]//a")
    public WebElement tAndClink;

    @FindBy(xpath = ".//label[@for=\"labeled-select_0\"]//span")
    public List<WebElement> label_TranslatedCountry;

    @FindBy(xpath = ".//label[@for=\"labeled-select_1\"]//span")
    public List<WebElement> label_TranslatedLang;

    @FindBy(xpath = ".//label[@for=\"labeled-select_2\"]//span")
    public List<WebElement> label_TranslatedCurrency;

    @FindBy(css = ".selected-language")
    public WebElement selectedLanguage;

    @FindBy(xpath = ".//*[@class='gf-tcp-button'][contains(.,'EMAIL AN EGIFT CARD')]")
//    @FindBy(xpath = ".//*[@class=\"button-blk\"][contains(.,\"EMAIL AN EGIFT CARD\")]")
    public WebElement link_EmailAnGC;

    @FindBy(xpath = ".//*[@class='button-blk']")
    public WebElement link_EmailAnGC_CA;

    @FindBy(xpath = ".//div[@class=\"page-header\"]//h1[contains(.,\"Size\")]")
    public WebElement sizeChartHeader;

    @FindBy(xpath = ".//*[@alt=\"Gift Services | We offer the following Gift Service Options that can be selected during checkout\"]")
    public WebElement giftServiceTitle;

    @FindBy(xpath = ".//div[@class=\"lkbook-container\"]")
    public WebElement lookbookContainer;

    @FindBy(xpath = ".//div[@id=\"mobile-app-container\"]")
    public WebElement mobileAppContainer;

    @FindBy(xpath = ".//*[@name='addressLocation']")
    public WebElement searchStoreLocation;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__us__buttons']//a[text()='CORPORATE']")
    public WebElement corporateBtn;

    @FindBy(css = "#us-career")
    public WebElement careersPage_Prod;

    @FindBy(xpath = ".//a[contains(.,\"Vendor Code of Conduct\")]")
    public WebElement vendorCodeOfConduct_Link;

    @FindBy(xpath = ".//*[@class=\"top-banner-copy-desktop\"]")
    public WebElement internationalOppBanner;

    @FindBy(xpath = ".//div[@class=\"recall\"]")
    public WebElement recallInfo;

    @FindBy(name = "address.firstName")
    public WebElement firstNameField_WIC;

    @FindBy(id = "userNameSignIn_field")
    public WebElement userNameField;

    @FindBy(css = ".col-sm-12.sq-main-img.align-center")
    public WebElement styleSquad_Espot;

    @FindBy(name = "parentfirst")
    public WebElement styleSquad_FirstName;

    @FindBy(name = "parentlast")
    public WebElement styleSquad_LastName;

    @FindBy(name = "email")
    public WebElement styleSquad_Email;

    @FindBy(name = "childname1")
    public WebElement styleSquad_ChildName;

    @FindBy(name = "parentbirth")
    public WebElement styleSquad_ParentYear;

    @FindBy(name = "ciy")
    public WebElement styleSquad_City;

    @FindBy(name = "state")
    public WebElement styleSquad_State;

    @FindBy(name = "zip")
    public WebElement styleSquad_Zip;

    @FindBy(name = "socialMediaGuidelinesConfirmation")
    public WebElement styleSquad_MediaGuide;

    @FindBy(name = "waiverAndReleaseConfirmation")
    public WebElement styleSquad_WavierRelease;

    @FindBy(name = "signature")
    public WebElement styleSquad_DigitalSign;

    @FindBy(name = "month1")
    public WebElement styleSquad_BirthMon;

    @FindBy(name = "year1")
    public WebElement styleSquad_BirthYear;

    @FindBy(name = "gender1")
    public WebElement styleSquad_Gender;

    @FindBy(id = "submit-form")
    public WebElement styleSquad_Submit;

    @FindBy(id = "addchild")
    public WebElement styleSquad_AddChild;

    @FindBy(css = ".input-error")
    public WebElement styleSquad_Error;

    @FindBy(css = ".product-title-container a")
    public WebElement itemNameFav;

    @FindBy(css = ".scroll-to-top-message")
    public WebElement backToTop;

    @FindBy(css = ".scroll-to-top-icon")
    public WebElement backToTopIcon;

    @FindBy(id = "navigation-sticky-wrapper")
    public WebElement headerInBlogPage;

    @FindBy(xpath = "//*[@data-id=\"Blog\"]")
    public WebElement blogLink;

    @FindBy(css = ".bopis-faqs-copy")
    public WebElement bopislandingPage_Content;

   // @FindBy(xpath = "//*[@class=\"container-topics\"]//a[contains(.,\"BUY ONLINE PICK-UP IN STORE\")]")
    @FindBy(xpath = "//*[@class=\"container-topics\"]//a[contains(.,\" BUY ONLINE PICK UP IN STORE \")]")
    public WebElement bopisLink;

    @FindBy(xpath = "//div[@class=\"container-navigation\"]//a[contains(.,\"Track Order\")]")
    public WebElement trackOrder_Link;

    @FindBy(css = ".badge-item-container.top-badge-container")
    public List<WebElement> badges;

    //@FindBy(css = ".button-overlay-close")
    @FindBy(css = ".overlay-header-desktop .button-overlay-close")
    public WebElement closemodal;

    @FindBy(css = ".my-account-navigation-container")
    public WebElement myAccountContainer;

    @FindBy(css = ".button-apply.button-quaternary")
    public List<WebElement> applyOrAcceptOfferButton;

    @FindBy(css = ".international-store-button")
    public WebElement clickHereLinkStoreLocatorPage;

    @FindBy(css = ".INScontainer")
    public WebElement intStoreLists;

    @FindBy(name = "INSdropdpown")
    public WebElement countryListDropDown;

    @FindBy(xpath = "//select[@name='INSdropdpown']/option")
    public WebElement countryListValue;

    @FindBy(xpath = "//*[@id=\"customerServiceli\"]//h4")
    public WebElement expandedCustomerField;

    @FindBy(name = "emailAddress")
    public WebElement emailIDFieldName;
}


