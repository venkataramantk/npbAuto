package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

/**
 * Created by JKotha on 22/11/2017.
 */
//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class FooterTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email, password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelCA, email);
            }
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, CHEETAH, PRODUCTION})
    public void footerLinksValidation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in " + store + " Verify Different Footer links" +
                "DT-20578, DT-25127, DT-25216, DT-11760, DT-11769, DT-11766, DT-25125, DT-25179");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        //AssertFailAndContinue(mheaderMenuActions.validateGlobalNavBanner(), "Verify Marketing banner in home page");
        //DT-23737
        AssertFailAndContinue(mfooterActions.validateSocialNetworkingLinks(), "Verify Facebook, Instagram, Twitter, Pinterest links");
        AssertFailAndContinue(mfooterActions.validateTermsAndConditionsLink(), "Verify Terms and Condition link and verify its navigating to correct page");
        AssertFailAndContinue(mfooterActions.validatePrivacyPolicyLink(), "Verify Privacy policy link and verify its navigating to correct page");
        AssertFailAndContinue(mfooterActions.validateCaliforniaAct(), "Verify California Supply Chains Act link and verify its navigating to correct page");
        AssertFailAndContinue(mfooterActions.validateCopyRightInfo(), "Validate copy right info");
        AssertFailAndContinue(mfooterActions.validateSiteMap(), "Verify Site Map link and verify its navigating to correct page");
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(!mfooterActions.verifyMPRSection(), "Verify MPR Link is not displayed for CA");
        }

        AssertFailAndContinue(mfooterActions.isDisplayed(mfooterActions.sectionLink(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "helpCenter", "value"))), "Help Center Footer Link");
        AssertFailAndContinue(mfooterActions.isDisplayed(mfooterActions.sectionLink(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "shopping", "value"))), "Shopping Footer Link");
        AssertFailAndContinue(mfooterActions.isDisplayed(mfooterActions.sectionLink(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"))), "About Us Footer Link");
        mheaderMenuActions.clickOnTCPLogo();
        mfooterActions.scrollToBottom();
        mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"));
        AssertFailAndContinue(mfooterActions.validateMomSpace(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "momspace", "value")), "Click Mom space under about us and verify mom space home page is displayed");
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, CHEETAH})
    public void footerGiftCardValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify Gift card page validations" +
                "DT-20573, DT-20569");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-23740
        mfooterActions.goToGiftcardsPage();
        //AssertFailAndContinue(mheaderMenuActions.validateGlobalNavBanner(), "Verify Marketing banner in Gift card page");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mgiftCardsPageActions.validatedGiftCards(), "Verify EMAIL AN E Gift Card Button");

            //DT-23742
            mgiftCardsPageActions.goToGiftCardPage("giftCard");
            AssertFailAndContinue(mproductDetailsPageActions.verifyGiftCardPDPPage(), "Verify Send gift card page");

            mproductDetailsPageActions.selectASize();
            String selectedValue = mproductDetailsPageActions.getGiftCardSelectedValue();
            String price = Double.toString(mproductDetailsPageActions.getPrice()).split("\\.")[0];
            AssertFailAndContinueWithTextFont(selectedValue.equalsIgnoreCase(price), "DT-  Verify Selected gift card value is displaying at top price(in red) ");

        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mgiftCardsPageActions.validatedGiftCards_CA(), "Verify only Send an e-gift card is displayed for CA");
        }

        AssertFailAndContinue(mfooterActions.validateGiftCardBalanceLink(mgiftCardsPageActions), "Verify gift card balance page is displayed");
        mgiftCardsPageActions.goGiftCardBalancePage();
        mgiftCardsPageActions.verifyGiftcardBalance("6006491259499902517", "9876");
        AssertFailAndContinue(mgiftCardsPageActions.error.size() == 1, "Enter card no and pin click check balance without selecting re-captcha, verify only one error message is displayed");

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void trackOrderTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As A guest user in " + store + " store.\n" +
                "1. Validate all fields in Track Order" +
                "2. Verify Error Message for Track Order" +
                "DT-43784");
        mfooterActions.goTrackOrder();
        AssertFailAndContinue(mfooterActions.validateAllFieldsInTrackOrder(), "Validate all Fields In Track order page");
        AssertFailAndContinue(validateLoginLink(), "Verify login link is displayed in track order popup");
        mobileDriver.navigate().refresh();
        mfooterActions.goTrackOrder();
        AssertFailAndContinue(mfooterActions.validateNeedHelpLink(), "Verify Need help link in the track order popup");
        AssertFailAndContinue(mfooterActions.validateErrorMessagesinTrackOrder(), "Validate Error messages track order page");
        mfooterActions.trackOrder("test@yopmail.com", "87654323");
        AssertFailAndContinue(mfooterActions.waitUntilElementDisplayed(mfooterActions.couponError, 20), "Validate Error messages for invalid Order ID");
        //AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Click scroll to top in bottom page and verify user is moved to page top");
        AssertFailAndContinue(mfooterActions.verifyInternationTrackPage(), "As a guest user click International order track link from Track order page , verify new ui is displayed");
        mheaderMenuActions.switchToParent();
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void validate_AboutUs(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As A " + user + " user in " + store + " store.\n" +
                "1. Validate Blog Link" +
                "DT-25186");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        mfooterActions.scrollToBottom();
        if (store.equalsIgnoreCase("US")) {
            mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"));
            AssertFailAndContinue(mfooterActions.validateBlog(), "Verify blog link is displayed and clicking on blog links redirects to Blog page");
            mfooterActions.switchToParent();
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"));
            AssertFailAndContinue(!mfooterActions.waitUntilElementDisplayed(mfooterActions.blogLink, 5), "Verify Blog link is not displayed for CA store");
            AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Click scroll to top in bottom page and verify user is moved to page top");
        }

        mheaderMenuActions.navigateBack();
        mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"));

    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void validate_helpCenter(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store validate Help center links" +
                "DT-31591, DT-43788, DT-43789, DT-20574, DT-20576, DT-24199, DT-20577, DT-25177");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "helpCenter", "value"));
        AssertFailAndContinue(mfooterActions.gotFaqs(), "Click on FAQ's link under help center verify Help Center home page is displayed");
        if (store.equalsIgnoreCase("us")) {
            AssertFailAndContinue(mfooterActions.clickBopisLink(), "Validate BOPIS link redirection");
            AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Click scroll to top in bottom page and verify user is moved to page top");
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mfooterActions.goTrackOrder(), "Verify that loading spinner is getting displayed on Order History page, when user clicks on Track Order link from the footer.");
        }

        AssertFailAndContinue(mfooterActions.validateContactUsLink(), "Validate contact us link");
        mheaderMenuActions.clickOnTCPLogo();

        AssertFailAndContinue(mfooterActions.validateReturnPolicyLink(), "Validate Return Policy link");
        mheaderMenuActions.clickOnTCPLogo();

        AssertFailAndContinue(mfooterActions.validateGiftCardBalanceLink(mgiftCardsPageActions), "verify Gift card balance link from footer");
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void validate_Shopping(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store validate Shopping links" +
                "DT-20571, DT-20572, DT-20592, DT-25209, DT-25206, DT-25204, DT-25203, DT-25208");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "shopping", "value"));
        AssertFailAndContinue(mfooterActions.isDisplayed(mfooterActions.storeLocatorTest), "Verify Store Locator");
        AssertFailAndContinue(mfooterActions.validateSizeChart(), "Validate Size chart link in Footer");
        AssertFailAndContinue(mfooterActions.validateSeasonalLookBooksLink(), "Validate Seasonal LookBook and different seasonal books in landing page");
        AssertFailAndContinue(mfooterActions.checkPdfLinkInLookBook(), "Verify pdf download link is displayed and carousel button");
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mfooterActions.verifyElementNotDisplayed(mfooterActions.link_MobileApp), "Validate Mobile app link is not displayed in CA store");
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mfooterActions.validateCreateAccountLink(mcreateAccountActions), "Verify if \"Create an account\" link is present in the footer section when the guest user is accessing the " + store + " store ");
        }
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, USONLY, PRODUCTION})
    public void validate_MPRSection(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in US store validate MPR links" +
                "DT-20579, DT-20581, DT-41914, DT-32451");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        AssertFailAndContinue(mfooterActions.isDisplayed(mfooterActions.sectionLink(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "myPlaceRewards", "value"))), "My Place Reward Footer Link");
        AssertFailAndContinue(mfooterActions.isDisplayed(mfooterActions.sectionLink(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "myPlaceRewardsCC", "value"))), "My Plcc CC Footer Link");
        AssertFailAndContinue(mfooterActions.clickMPRLOGO(), "Verify navigation when user clicks on My Place Rewards Logo from footer");
        //AssertFailAndContinue(mheaderMenuActions.validateGlobalNavBanner(), "Verify Marketing banner in MPR landing page");
        AssertFailAndContinue(mfooterActions.validateMemberBenefitsLink(), "Validate member Benefits link in footer");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mfooterActions.validateCheckPointsBalance(mloginPageActions), "Verify if the guest user in US Store is navigated to the 'Login' Overlay by clicking on ' Check Points Balance' link from footer from My Place Rewards section");
        }
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, PRODUCTION})
    public void emailUs(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in " + store + " store verify user is able to submit a request from contact us link" +
                "DT-24185, DT-24183");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        mfooterActions.validateContactUsLink();
        mfooterActions.clickEmailUsLink(mEmailUsPageActions);
        Map<String, String> details = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");

        AssertFailAndContinue(mEmailUsPageActions.contactWithEmail(details.get("fName"), details.get("lName"), email, "9876543212", "Online Ordering", "My sizes are not available online", "Submit request"), "Submit a request and verify success message");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mEmailUsPageActions.validateUSSubject(), "Validate Subjects options for US");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mEmailUsPageActions.validateCASubject(), "Validate Subject options for CA");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        mobileDriver.quit();
    }
}
