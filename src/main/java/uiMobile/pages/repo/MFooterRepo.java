package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 11/10/2017.
 */
public class MFooterRepo extends UiBaseMobile {


    //My Place Reward Section
    public WebElement sectionLink(String section) {
        return getDriver().findElement(By.xpath("//h4[text()='" + section + "']"));
    }

    public WebElement sectionLinkOpen(String section) {
        return getDriver().findElement(By.xpath("//h4[text()='" + section + "'][@class='title-item-category list-navigation-open']"));
    }

    @FindBy(xpath = "//h4[text()='My Place Rewards']")
    public WebElement mprSection;

    @FindBy(css = "a[aria-label='My Place Rewards'] img")
    public WebElement mprlogo;

    @FindBy(css = ".navigation-with-promo-image")
    public WebElement plccImg;

    @FindBy(css = ".button-apply.button-primary")
    public WebElement applayOrAcceptOffer;

    @FindBy(css = ".why-join-inner")
    public WebElement img_TheMomSpace;

    @FindBy(css = ".emailUsLink")
    public WebElement contactUsEmilLink;


    //left Section
    @FindBy(xpath = "//*[contains(@class,'footer-email')]/span")
    public WebElement lbl_SignUpText;

    @FindBy(xpath = "//*[contains(@class,'footer-email')] //a[contains(.,'SIGN UP') or contains(.,'SUSCRIBIRSE') or contains(.,'INSCRIRE')]")
    public WebElement link_SignUp;

    //Middle section

    @FindBy(xpath = "//*[contains(@class,'footer-social')] //span[contains(.,'Connect with us.') or contains(.,'Contáctanos.') or contains(.,'Communiquez avec nous.') or contains(.,'Suivez-nous.')]")
    public WebElement lbl_ConnectWithUs;

    @FindBy(xpath = "//button[text()='Save']|//button[text()='Guardar']|//button[text()='Sauvegarder']")
    public WebElement saveBtn;

    @FindBy(css = ".note-clarification")
    public WebElement noteTxt;

    @FindBy(css = ".modal-subtitle")
    public WebElement changePrefernceTxt;

    @FindBy(css = ".item-navigation a[href*='sitemap']")
    public WebElement siteMapLink;


    @FindBy(css = ".item-navigation-link.redeem-rewards")
    public WebElement link_RedeemRewards;

    @FindBy(css = "a[data-id='MemberBenefits']")
    public WebElement membersBenefitLink;


    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='FAQs']")
    public WebElement link_FAQs;

    @FindBy(css = "button[title='Order Status']")
    public WebElement link_OrderStatus;

    @FindBy(css = "div.track-order .input-common input[name='emailAddress']")
    public WebElement trackerEmail;

    @FindBy(css = ".item-navigation a[href*='#termsAndConditionsli']")
    public WebElement termsAndCondition;

    @FindBy(css = ".item-navigation a[href*='#termsAndConditionsli']")
    public WebElement shipToTermsAndCondition;

    @FindBy(css = ".accordion.accordion-expanded h4")
    public WebElement activeSessionHeader;

    @FindBy(css = "#termsAndConditionsli .accordion.accordion-expanded h4")
    public WebElement acticTAndCheader;

    @FindBy(css = ".main-section-container")
    public WebElement supplyChaninHeader;

    @FindBy(css = ".site-map-container h1")
    public WebElement siteMapHeader;

    @FindBy(xpath = "//a[text()='Girls Outfits']")
    public WebElement girlsOutfittingLink;

    @FindBy(css = ".item-navigation a[href*='/#privacyPolicySectionli']")
    public WebElement privacyPolicy;

    @FindBy(css = ".item-navigation a[href*='content/supply-chain']")
    public WebElement californiaSupplyChainLink;

    @FindBy(css = ".copyright-information")
    public WebElement copyRightInfo;

    @FindBy(css = "a[data-id='Blog']")
    public WebElement blogLink;

    @FindBy(css = "div.track-order .input-common input[name='orderNumber']")
    public WebElement ordernoFld;

    @FindBy(xpath = "//input[@name='orderNumber']/../following-sibling::div/div")
    public WebElement orderNoErrorMsg;

    @FindBy(xpath = "//input[@name='emailAddress']/../following-sibling::div/div")
    public WebElement emailErrorMsg;

    @FindBy(css = ".error-box")
    public WebElement couponError;

    @FindBy(css = ".button-primary.button-submit")
    public WebElement trackorderBtn;

    @FindBy(css = ".custom-loading-icon")
    public WebElement orderDetailsSpinner;

    @FindBy(css = ".button-login")
    public WebElement loginlink;

    @FindBy(css = ".need-help")
    public WebElement needHelpLink;

    @FindBy(css = ".international-order a")
    public WebElement trackinternationlOrder;


    @FindBy(css = "a[data-id='ReturnPolicy']")
    public WebElement returnPolicyLink;

    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Shipping Options']")
    public WebElement link_ShippingOption;

    @FindBy(css = "a[data-id='GiftCardBalance']")
    public WebElement gcBalanceLink;


    @FindBy(css = "a[data-id='StoreLocator']")
    public WebElement storeLocatorTest;

    @FindBy(css = "a[data-id='SizeChart']")
    public WebElement link_SizeChart;

    @FindBy(css = "a[data-id='GiftCards']")
    public WebElement link_GiftCards;


    @FindBy(xpath = ".//div[@class='container-navigation']//a[.='Mobile App']")
    public WebElement link_MobileApp;

    @FindBy(css = "a[data-id='SeasonalLookbooks']")
    public WebElement seasonalLookBooksLink;

    @FindBy(id = "lookbooks")
    public List<WebElement> differentLookbooks;

    @FindBy(css = ".discover-roles")
    public WebElement careersPage;


    @FindBy(css = ".download-copy>a")
    public WebElement downloadLookBookPdfBtn;


    @FindBy(css = "a[data-id='Careers']")
    public WebElement careersLink;


    @FindBy(css = "a[data-id='TheMomSpace']")
    public WebElement link_MomSpace;

    @FindBy(xpath = ".//nav[@class='item-navigation']//a[.='Privacy Policy']")
    public WebElement link_FooterPrivacy;

    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Twitter']")
    public WebElement link_Twitter;

    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Pinterest']")
    public WebElement link_Pinterest;

    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Facebook']")
    public WebElement link_Facebook;

    @FindBy(xpath = ".//div[@class='social-networks']//a[@title='Instagram']")
    public WebElement link_Instagram;

    @FindBy(css = ".help-center-section")
    public WebElement helpCenterHomePage;


    @FindBy(css = "a[href='/us/content/buy-online-faq']")
    public WebElement bopis_Link;

    @FindBy(css = ".bopis-faqs-copy")
    public WebElement bopisFaqPage;

    @FindBy(xpath = ".//select[@name='country']")
    public WebElement shipToCountryDropdown;

    @FindBy(xpath = ".//select[@name='language']")
    public WebElement langDropdown;

    @FindBy(xpath = ".//select[@name='currency']")
    public WebElement currencyDropdown;

    @FindBy(css = ".country-selector button")
    public WebElement languageButton;

    @FindBy(css = ".country-selector img")
    public WebElement flagCountryImg;

    @FindBy(css = ".container-popup")
    public WebElement shipToModalDialog;

    @FindBy(css = ".button-modal-close")
    public WebElement closeLinkOnShipTOModal;

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


    public WebElement getError(String field) {
        return getDriver().findElement(By.xpath("//input[@name='" + field + "']/following-sibling::span[last()]"));
    }

    @FindBy(id = "signupFormPage")
    public WebElement signuppage;

    //you may also like section
    @FindBy(css = ".item-product-recomendation .favorite-icon-container")
    public List<WebElement> recomm_facIcons;

    @FindBy(css = ".favorite-icon-active.favorite-icon-container")
    public WebElement favorited_item;

    @FindBy(css = ".item-product-recomendation .department-name")
    public List<WebElement> recomm_productNames;

    @FindBy(css = "a[data-id='ContactUs']")
    public WebElement contactUsLink;

    @FindBy(css = ".hc-link.hc-link-selected")
    public WebElement selectedSection;

    @FindBy(css = "button[data-id='CreateAnAccount']")
    public WebElement createAccountLink;

    @FindBy(css = "button[data-id='CheckPointBalance']")
    public WebElement checkPointsBalance;

    @FindBy(css = ".bx-next")
    public WebElement nextCarousel;

}
