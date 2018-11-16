package tests.webDT.globalComponents.footer;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;
import java.util.Map;


/**
 * Created by AbdulazeezM on 3/13/2017.
 */

//USer Story: DT-646
//@Listeners(MethodListener.class)

public class Footer extends BaseTest {
    WebDriver driver;
    String emailAddressReg;
    private String password;
    String env = EnvironmentConfig.getEnvironmentProfile();

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
            }

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
            }
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PROD_REGRESSION})
    public void validateLinksInFooter(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " the link present in footer");
        String metaDesc = getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "siteMapMetaDesc", "HREFValue");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(footerActions.validateBackToTop(), "Verify user scrolling down at home page starts displaying Back to Top button");
        AssertFailAndContinue(footerActions.clickBackToTop(), "Verify user clicking Back to Top navigates user back to top of home page");
        AssertFailAndContinue(footerActions.validateBottomOfFooterLnks(), "verify the links present in the bottom of the footer");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.validateHelpCenterLnks(), "Verify the links present in the Help center group");
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.validateMPRcreditCardLnks(), "Verify the links present in the MPR CC group");
            if (user.equalsIgnoreCase("guest")) {
                AssertFailAndContinue(footerActions.validateMPRLnks(), "Verify the links present in the MPR group");
            }
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.validateAboutUsGroupLnksUS(), "Verify the links present in the About us group");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.validateAboutUsGroupLnksCA(), "Verify the links present in the About us group");
        }
        AssertFailAndContinue(footerActions.navTermLink(), "Verify the links present in the Navigation footer");
        AssertFailAndContinue(footerActions.siteMap(), "Verify the links present in the Navigation footer");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.metaDescInSiteMap(metaDesc), "Verify Meta descrption in site map page <wil not visible in screenshot>");
            if (env.equalsIgnoreCase("prod")) {
                AssertFailAndContinue(footerActions.validatePageHeading(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "sitePageHeading", "HREFValue")), "validate Page Heading");
                AssertFailAndContinue(footerActions.validateSiteMapPageTitle(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "siteMapPageTitle", "HREFValue")), "Validate Page title");
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.metaDescInSiteMap(metaDesc), "Verify Meta descrption in site map page \n <wil not visible in screenshot>");
            if (env.equalsIgnoreCase("prod")) {
                AssertFailAndContinue(footerActions.validatePageHeading(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "sitePageHeadingCA", "HREFValue")), "validate Page Heading");
                AssertFailAndContinue(footerActions.validateSiteMapPageTitle(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "siteMapPageTitleCA", "HREFValue")), "Validate Page title");
            }
        }
        if (user.equalsIgnoreCase("guest") && store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.validateShoppingGroupLnks(), "Verify the links present in the shopping group");
        }

        if (user.equalsIgnoreCase("guest") && store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.validateShoppingGroupLnksCA(), "Verify the links present in the shopping group");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PROD_REGRESSION})
    public void validateHelpLinksAndURL(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " the help center links in footer and redirect to the particular page" +
                "DT-43789 and DT-43788");

        List<String> helpLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "linkName"));
        List<String> helpURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "URLValue"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(0), helpURLValidation.get(0)), "Verify if user navigates to FAQs page on clicking the FAQs link");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.clickBOPISLinkFromContentPage(), "Click on the BOPIS link in US store");
        }
        AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(1), helpURLValidation.get(1)), "Validate if user navigates to Return Policy page on clicking the Return Policy link");
        AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(2), helpURLValidation.get(2)), "Ensure if user navigates to Shipping Option page on clicking the Shipping Option link");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(3), helpURLValidation.get(3)), "Verify if user navigates to Gift Card Balance page on clicking the Gift Card Balance link");
        }
        //DT-43789 and DT-43788
        AssertFailAndContinue(footerActions.contactUSLinkValidation(helpLinkValidation.get(6), helpURLValidation.get(8),store), "Validate if user navigates to " + helpURLValidation.get(8) + " page on clicking the " + helpLinkValidation.get(6) + " link");

        AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(4), helpURLValidation.get(4)), "Validate if user navigates to Privacy Policy page on clicking the Privacy Policy link");
        AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(5), helpURLValidation.get(5)), "Validate if user navigates to " + helpURLValidation.get(5) + " page on clicking the " + helpLinkValidation.get(5) + " link");

        AssertFailAndContinue(footerActions.validateBackToTop(), "Verify user scrolling down at home page starts displaying Back to Top button");
        AssertFailAndContinue(footerActions.clickBackToTop(), "Verify user clicking Back to Top navigates user back to top of home page");
        AssertFailAndContinue(footerActions.clickContinueShopping(helpLinkValidation.get(1), headerMenuActions), "Validate continue shopping link redirection");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.clickOnOrderStatus(orderStatusActions), "Clicking on Order status navigates to order status overlay");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PROD_REGRESSION})
    public void validateShoppingLinksAndURL_Guest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " the shopping links in footer and redirect to the particular page");

        List<String> shoppingLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "shopping", "linkName"));
        List<String> shoppingURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "shopping", "URLValue"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.clickOnLinkDataIdAndValidateURL(shoppingLinkValidation.get(2), shoppingURLValidation.get(2)), "Ensure if user navigates to create account page on clicking the coupons link");
            overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        }
        AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(4), store), "Ensure if user navigates to size chart page on clicking the size chart link");
        AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(5), store), "Verify if user navigates to gift card page on clicking the gift cards link");
        if(store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(6), store), "validate if user navigates to gift wrapping page on clicking the gift services link");
        }
        footerActions.staticWait(4000);
        AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(9), store), "Verify if user navigates to seasonal lookbooks page on clicking the seasonal lookbooks link");
        footerActions.staticWait(2000);
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.clickOnFavoritesAsGuest(wishListDrawerActions), "Verify if user navigates to " + shoppingURLValidation.get(8) + "  page on clicking the " + shoppingLinkValidation.get(8));
            headerMenuActions.click(headerMenuActions.closemodal);
        }
        if(store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(7), store), "validate if user navigates to mobile app page on clicking the mobile app link");
        }
        //       AssertFailAndContinue(footerActions.mobileAppRedirection(emailAddressReg),"Enter email address and check the redirection");
        footerActions.staticWait(4000);
        AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(3), store), "Verify if user navigates to store locator page on clicking the store locator link");
        AssertFailAndContinue(footerActions.clickHereLinkREdirection(), "Verify if user navigates to International store list page on clicking the click here link");

    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, PROD_REGRESSION})
    public void validateAboutUSLinksAndURL(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " the about us links in footer and redirect to the particular page");

        List<String> aboutUsLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "aboutUs", "linkName"));
        List<String> aboutUsURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "aboutUs", "URLValue"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(0), aboutUsURLValidation.get(0)), "validate if user navigates to public relation page on clicking the pubic relation link");
        AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(1), aboutUsURLValidation.get(1)), "validate if user navigates to careers page on clicking the careers link");
        AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(2), aboutUsURLValidation.get(2)), "validate if user navigates to social responsibility page on clicking the social responsibility link");
        AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(3), aboutUsURLValidation.get(3)), "verify if user navigates to  International Opportunities page on clicking the  International Opportunities link");
        AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(4), aboutUsURLValidation.get(4)), "ensure that if user navigates to Recall Information page on clicking the Recall Information link");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(5), aboutUsURLValidation.get(4)), "ensure that if user navigates to Recall Information page on clicking the Recall Information link");
        } else {
            AssertFailAndContinue(footerActions.blogLinkNotDisplayedInCA(), "Verify that the Blod link is not displayed in US store");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PROD_REGRESSION, USONLY})
    public void validateMyPlaceRewardsLinksAndURL(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " the my place rewards links in footer and redirect to the particular page");

        List<String> myPlaceRewardsLinks = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "myPlaceRewards", "linkName"));
        List<String> myPlaceRewardsURL = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "myPlaceRewards", "URLValue"));

        List<String> myPlaceRewardsLinksCC = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "myPlaceRewardsCC", "linkName"));
        List<String> myPlaceRewardsURLCC = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "myPlaceRewardsCC", "URLValue"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        if (store.equalsIgnoreCase("us")) {
            AssertFailAndContinue(footerActions.clickOnMPRLinksAndValidateURL(myPlaceRewardsLinks.get(1), myPlaceRewardsURL.get(1)), "verify that if user navigates to check point balance page on clicking the check point balance link");
            AssertFailAndContinue(footerActions.clickOnMPRLinksAndValidateURL(myPlaceRewardsLinks.get(2), myPlaceRewardsURL.get(2)), "ensure that if user navigates to redeem rewards page on clicking the redeem rewards link");
            //DT-20579
            AssertFailAndContinue(footerActions.clickOnMPRLinksAndValidateURL(myPlaceRewardsLinks.get(3), myPlaceRewardsURL.get(3)), "ensure that if user navigates to proper content page on clicking the member benefits link");

            if (user.equalsIgnoreCase("guest")) {
                //DT-20580
                AssertFailAndContinue(footerActions.clickOnMPRLinksAndValidateURL(myPlaceRewardsLinks.get(0), myPlaceRewardsURL.get(0)), "ensure that if user is displayed with create account modal by clicking the create account link");
            }
            // to validate MPR CC
            AssertFailAndContinue(footerActions.clickOnMPRCCAndValidateURL(myPlaceRewardsLinksCC.get(0), myPlaceRewardsURLCC.get(1)), "verify that if user navigates to MPR content page on clicking the Learn more link");
            AssertFailAndContinue(footerActions.clickOnMPRCCAndValidateURL(myPlaceRewardsLinksCC.get(1), myPlaceRewardsURLCC.get(1)), "ensure that if user navigates to rewards page on clicking the apply now link");
            AssertFailAndContinue(footerActions.clickOnMPRCCAndValidateURL(myPlaceRewardsLinksCC.get(2), myPlaceRewardsURLCC.get(2)), "ensure that if user navigates to appropriate page on clicking the pay your bill link");
            driver.navigate().back();
            AssertFailAndContinue(footerActions.clickOnMPRCCAndValidateURL(myPlaceRewardsLinksCC.get(3), myPlaceRewardsURLCC.get(3)), "ensure that if user navigates to appropriate page on clicking the manage you Account link");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(!footerActions.isDisplayed(footerActions.MPRLinksInFooter(myPlaceRewardsLinks.get(0))), "Verify Place card link not displayed in CA Store");
        }

    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION, INTUATSTG})
    public void footerLinkValidations_Smoke(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " Order Status, Ship To, Store Locator and Gift Cards link redirecting to appropriate page");
        List<String> shoppingLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "shopping", "linkName"));
        List<String> shoppingURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "shopping", "URLValue"));
        List<String> helpLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "linkName"));
        List<String> helpURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "URLValue"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(1), helpURLValidation.get(1)), "Verify if user navigates to " + helpURLValidation.get(1) + " on clicking the " + helpLinkValidation.get(1));
            AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(3), store), "Ensure if user navigates to " + shoppingURLValidation.get(3) + " on clicking " + shoppingLinkValidation.get(3));
            AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(5), store), "Verify if user navigates to " + shoppingURLValidation.get(5) + " on clicking " + shoppingLinkValidation.get(5));
            AssertFailAndContinue(footerActions.changeCountryByCountry("CANADA"), "Verify that the user is able to switch from Store locator page");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.clickOnHelpCenterLinkAndValidateURL(helpLinkValidation.get(1), helpURLValidation.get(7)), "Verify if user navigates to " + helpURLValidation.get(1) + " on clicking the " + helpLinkValidation.get(1));
            AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(3), store), "Ensure if user navigates to " + shoppingURLValidation.get(3) + " on clicking " + shoppingLinkValidation.get(3));
            AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(5), store), "Verify if user navigates to " + shoppingURLValidation.get(5) + " on clicking " + shoppingLinkValidation.get(5));
            headerMenuActions.clickOnTCPLogo();
            AssertFailAndContinue(footerActions.changeCountryByCountry("United States"), "Verify that the user is able to switch from Store locator page");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, PROD_REGRESSION})
    public void validateStoreLocatorPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + " contents in the Store locator page and verify the Store Search functionality");

        String validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");

        List<String> shoppingLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "shopping", "linkName"));
        List<String> shoppingURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "shopping", "URLValue"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(footerActions.clickOnShoppingAndValidateURL(shoppingLinkValidation.get(3), store), "Verify if user navigates to store locator page on clicking the store locator link");
        AssertFailAndContinue(footerActions.checkHeader(), "Check whether the header is displayed constantly");
        AssertFailAndContinue(storeLocatorPageActions.checkListOfAllStores(), "Check the List of store page and redirection");
        AssertFailAndContinue(storeLocatorPageActions.clickStoreDetailsNavigateToStoreDetailsPage(validZip), "Validated the Store Search page when an address is searched. Also validated the Store Details page");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.changeCountryByCountry("CANADA"), "Verify that the user is able to switch from Store locator page");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(footerActions.changeCountryByCountry("United States"), "Verify that the user is able to switch from Store locator page");
        }
    }


    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PROD_REGRESSION})
    public void favStoreDisplayInHeader_Reg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify that registered user in " + store + " store," + " can select the favorite store from My Account page and the same store name is getting displayed in the header section");
        List<String> validAddress = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "storeLocator", "validSearch"));
        List<String> addressErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "storeLocator", "validErrMsg"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
            AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
            if (store.equalsIgnoreCase("CA")) {
                AssertFailAndContinue(myAccountPageActions.checkFindStoreBtnDisplayCA(), "Click on find a store button and navigate to store locator page");
            }
        }
        headerMenuActions.click(headerMenuActions.findStoreLnk);
        storeLocatorPageActions.staticWait(2000);
        AssertFailAndContinue(storeLocatorPageActions.validateStreetErr(addressErr.get(0)), "Tab the address field and check the error message");
        AssertFailAndContinue(storeLocatorPageActions.searchStore(validAddress.get(0)), "Enter the valid store address and click on search button");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(storeLocatorPageActions.selectFavStore(headerMenuActions), "select the favorite store in store locator page and check the same is getting displayed in the header section");
            AssertFailAndContinue(storeLocatorPageActions.favStoreDisplay(headerMenuActions), "Check whether the Fav store is displayed as first store");
            String favStoreUS = headerMenuActions.changedFavStoreName.getText().toLowerCase();
            if (user.equalsIgnoreCase("Guest")) {
                clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
                // To check the Store display after login
                AssertFailAndContinue(headerMenuActions.checkFavStoreDisplayAfterStoreSwitch(favStoreUS), "Verify the Favorite store display after Login to the store");
                headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions, headerMenuActions);
            }

            //       AssertFailAndContinue(storeLocatorPageActions.logoutStoreDisplay(headerMenuActions),"Check whether the Fav store is displayed in header after logout");
            footerActions.changeCountryAndLanguage("CA", "English");
            String favStoreAfterStoreSwitch = headerMenuActions.changedFavStoreName.getText().toLowerCase();
            AssertFailAndContinue(!headerMenuActions.checkFavStoreDisplayAfterStoreSwitch(favStoreAfterStoreSwitch), "Verify the Favorite store is not display after switching the store");
            if (user.equalsIgnoreCase("registered")) {
                AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
                AssertFailAndContinue(myAccountPageActions.checkFindStoreBtnDisplayCA(), "Click on find a store button and navigate to store locator page");
                headerMenuActions.click(headerMenuActions.findStoreLnk);
                storeLocatorPageActions.staticWait(2000);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(storeLocatorPageActions.selectFavStoreCA(), "select the favorite store in store locator page and check the same is getting displayed in the header section");
        }

    }


    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, PROD_REGRESSION})
    public void storeSwitchValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify that registered user in " + store + " store," + " scenarios related to store switch");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.staticWait(1000);
        if (store.equalsIgnoreCase("US")) {
            footerActions.changeCountryByCountry("CANADA");
            footerActions.changeCountryByCountry("United States");
        }
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("United States");
            if (user.equalsIgnoreCase("registered")) {
                headerMenuActions.pointsText();
                footerActions.changeCountryByCountry("CANADA");
            }
        }
        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.getBagCount();
        headerMenuActions.clickOnTCPLogo();
        footerActions.clickOnLanguageButton();
        headerMenuActions.staticWait(1000);
        AssertFailAndContinue(footerActions.checkShipToModal(store), "Verify the elements in Ship To modal");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.changeStoreFromModal("CANADA"), "Change the country to CA");
        } else {
            AssertFailAndContinue(footerActions.changeStoreFromModal("United States"), "Change the country to CA");
        }
        AssertFailAndContinue(shoppingBagPageActions.checkCartCount(), "Ensure the cart count is zero");
    }
}
