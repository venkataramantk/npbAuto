package tests.mobileDT.pdp;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

import java.util.List;
import java.util.Map;

//@Test(singleThreaded = true)
public class pdpScreen extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = "", password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
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
            mheaderMenuActions.addStateCookie("ON");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {PDP})
    public void verifyPDPFunctionalities(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a Guest/Registered User in US/CA/INT Es/En/Fr navigate to PDP of any item through sub-category/Wishlist/Search result navigation.Click on size chart link.Verify if the size chart is defaulted to the category in which the user is currently in." +
                "DT-37304, DT-37302, DT-37292, DT-37335, DT-44572, DT-37352");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        //DT-37535
        panCakePageActions.navigateToPlpCategory("girl", null, "Bottoms", "Shop All");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "click on Product Image and move to PDP");
        AssertFailAndContinue(mheaderMenuActions.validateHrefLangTag(), "Verify HREF Lang tag should contain www across all the pages");
        mproductDetailsPageActions.addStepDescription("UN-872");
        AssertFailAndContinue(mproductDetailsPageActions.clickAddToBagSizeErr(), "Verify that the user is displayed with inline error message \"Please Select a Size\" in red color.");

        mproductDetailsPageActions.clickSizeChartAndVerify();
        AssertFailAndContinue(mproductDetailsPageActions.sizeChartToggleUS_Metric_Back_US(), "toggling from US to Metrics and Metrics to US");
        mproductDetailsPageActions.closeSizeChartmodal();

        AssertFailAndContinue(mproductDetailsPageActions.scrollDownToElement(mproductDetailsPageActions.size_chart_lnk), "scroll down to Size Chart Link");
        AssertFailAndContinue(mproductDetailsPageActions.verifySizeChartAndClose("Girls", "Bottoms"), "Click on Size Chart Link and verify Respective Size Chart is displayed.Also close the size Chart Modal");

        //DT-37546,DT-37544
        mproductDetailsPageActions.clickAddToWishListAsRegistered();
        if (user.equalsIgnoreCase("guest")) {
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        AssertFailAndContinue(mheaderMenuActions.moveToWishListAsReg(panCakePageActions, mobileFavouritesActions), "Move to Favourite Page");
        int favCount = mobileFavouritesActions.getItemsCountDisplay();
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyTwoCountsEqual(favCount, 1), "Verify 1 favourite item is displayed");
        AssertFailAndContinue(mobileFavouritesActions.removeProdFromWL(0), "Unfavorite the Product from Favorite Page");
        AssertFailAndContinue(mproductDetailsPageActions.verifyElementNotDisplayed(mobileFavouritesActions.itemQty), "Verify No Product is displayed in the Favorite Page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {PDP})
    public void verifyBopisNotOnGiftCardPDP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a Guest/Registered User in US Es/En store, Click on \"Gift card/Gift card balance\" link in the footer, and Click on \"Send a gift card\" CTA. ; Verify in the redirected Gift card PDP page user is not displayed with the \"Find in Store\" CTA." +
                "DT-44369");
        //DT-37533
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        mfooterActions.scrollDownToElement(mfooterActions.sectionLink("Shopping"));
        mfooterActions.openFooterSection("Shopping");
        AssertFailAndContinue(mfooterActions.moveToGiftCardPage(mgiftCardsPageActions), "click on Gift Cards link under Shopping Section");
        AssertFailAndContinue(mgiftCardsPageActions.openGiftCardByType("PLASTIC GIFT CARDS"), "Click on Plastic Gift Card");
        //AssertFailAndContinue(mgiftCardsPageActions.clickSendAGiftCardsButton(), "click on Send a Gift Card link");
        AssertFailAndContinue(mproductDetailsPageActions.verifyElementNotDisplayed(mproductDetailsPageActions.pickUpStore), "Verify Pick Up In Store Button is not displayed");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {PDP})
    public void verifyCompleteTheLookNotDisplayed(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1. Verify if the user is in PDP of the normal product (not outfit product),ensure that \"\"complete the look\" section is not displayed\n" +
                "As a guest/registered user in U.S/C.A/INT store, business has configured Product Recommendation container in PDP page, when the user navigates to PDP of any item through browse/search/favorite/Product Recommemdation /PLP/Outfit-PDP. Verify that the Product Recommemdation is displayed as expandable/collapsible section and by default it is expanded.\n" +
                "Verify price in PDP and PLP matches" +
                "DT-37257");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        //DT-37531
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
        //DT-33705
        String plpPrice = mcategoryDetailsPageAction.getProductPrice(0);
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "Click Product Image");
        String pdpPrice = mproductDetailsPageActions.salePrice.getText();
        AssertFailAndContinue(plpPrice.equalsIgnoreCase(pdpPrice), "Verify PLP and PDP price are same for a produce");
        AssertFailAndContinue(mproductDetailsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.addToBag), "Verify Application navigated to PDP as Add to Bag button is displayed");
        AssertFailAndContinue(mproductDetailsPageActions.verifyElementNotDisplayed(mproductDetailsPageActions.completeTheLook), "Complete the look section is not displayed in PDP page");
        //DT-37520 & DT-37527 & DT-37525
        mobileDriver.navigate().refresh();
        AssertFailAndContinue(mproductDetailsPageActions.verifyRecommendationSectionFunctionality(), "Verify recommendation Section Content, Functionality, user viewing is related to the base Item and clicking on carets displays next recommended product.Also, Verify other recommended products displayed  1. On clicking carets");
        //DT-37522
        AssertFailAndContinue(mproductDetailsPageActions.verifyRecommendedProductImagePDPRedirection(), "Verify on clicking recommended product image, it is redirected to PDP of same product");
        AssertFailAndContinue(mproductDetailsPageActions.verifyRecommendedProductNamePDPRedirection(), "Verify on clicking recommended product name, it is redirected to PDP of same product");
        //DT-37523 & DT-37530
        AssertFailAndContinue(mproductDetailsPageActions.verifyExpandCollapseRecommendationSection(), "Verify recommendation Section Content, Functionality and user viewing is related to the base Item");
        //DT-43707
        AssertFailAndContinue(mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber1", "validUPCNumber")), "Search for an Item");
        AssertFailAndContinue(mproductDetailsPageActions.selectASize(), "Select a Random Size");
        String selectedSize = mproductDetailsPageActions.getSelectedSize();
        String img = mproductDetailsPageActions.getProductImage();
        AssertFailAndContinue(mproductDetailsPageActions.selectAColor(mproductDetailsPageActions), "Select a random color");
        String img1 = mproductDetailsPageActions.getProductImage();
        AssertFailAndContinue(!img.equalsIgnoreCase(img1),"verify product image changes as per color");
        String selectedColor = mproductDetailsPageActions.getColorName();
        AssertFailAndContinue(mproductDetailsPageActions.clickAddToBagAndVerifyConfWindow(), "click Add to Bag Button and verify the Bag Notification");


        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click on Shopping Bag Icon");
        AssertFailAndContinue(mshoppingBagPageActions.validateProductAttributes(getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber1", "validUPCNumber"), "Color", selectedColor), "Verify Selected Color while adding is displayed in Bag");
        AssertFailAndContinue(mshoppingBagPageActions.validateProductAttributes(getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber1", "validUPCNumber"), "Size", selectedSize), "Verify Selected Size while adding is displayed in Bag");

        if (store.equalsIgnoreCase("US")) {
            mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"));
            AssertFailAndContinue(mfooterActions.validateBlog(), "Verify blog link is displayed and clicking on blog links redirects to Blog page");
            mfooterActions.switchToParent();
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.openFooterSection(getmobileDT2CellValueBySheetRowAndColumn("footerValidation", "aboutUs", "value"));
            AssertFailAndContinue(mfooterActions.waitUntilElementDisplayed(mfooterActions.blogLink, 5), "Verify Blog link is not displayed for CA store");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {PDP})
    public void verifyFullSizeImageModal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store,When the user is in PDP, clicks on the full size link in the product image Verify if user is displayed with close button (X) at the top right corner of the full size image model properly" +
                "DT-43788, DT-43789, DT-20789, DT-37259, DT-37272, DT-37309, DT-37255");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        //DT-37539 & DT-37542
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "Click Product Image");
        AssertFailAndContinue(mproductDetailsPageActions.validateColorSwatch(), "validate product color swatches");
        AssertFailAndContinue(mproductDetailsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.addToBag), "Verify Application navigated to PDP as Add to Bag button is displayed");
        AssertFailAndContinue(mproductDetailsPageActions.verifyFullSizeImageModal(), "1. Verify full size image modal with close button is displayed. 2. Click on close button and full size image modal closed and user displayed with corresponding PDP");
        AssertFailAndContinue(mproductDetailsPageActions.clickStarRatings(), "Click star ratings link and verify user is auto scroll to reviews and ratings section");
        mproductDetailsPageActions.scrollToTop();
        AssertFailAndContinue(mproductDetailsPageActions.writeAReview_Reg("This is a reivew lin for the product to check asdfasdf"), "Write a review");

        AssertFailAndContinue(mfooterActions.validateContactUsLink(), "Validate contact us link");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {PDP, RECOMMENDATIONS})
    public void valitedRecommendadtions_PDP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store,When the user is in PDP, verify user is able to add recommendations products to fav" +
                "DT-24236");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        //DT-37539 & DT-37542
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "Click Product Image");

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsGuest(mloginPageActions), "Click fav icon on first recommended product verify login page is displayed");
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsReg(), "Click fav icon on first recommended product ");
        }
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {PDP})
    public void verifyShoesAcsoriesClranceBrdcrms(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("Verify breadcrumb for Shoes, Accessories, Clearance are exceptions.");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        //DT-43743
        panCakePageActions.navigateToPlpCategory("shoes", null, "Boy", "Shop All");
        AssertFailAndContinue(mproductDetailsPageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.breadcrumbsOnPlp("Boy", "Shoes")), "verify Boy>Shoes as Breadcrumbs displayed");
        panCakePageActions.navigateToPlpCategory("accessories", null, "Girl", "Shop All");
        AssertFailAndContinue(mproductDetailsPageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.breadcrumbsOnPlp("Girl", "Accessories")), "verify Girl>Accessories as Breadcrumbs displayed");
        panCakePageActions.navigateToPlpCategory("clearance", null, null, "BOY");
        AssertFailAndContinue(mproductDetailsPageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.breadcrumbsOnPlp("Boy", "Clearance")), "verify Boy>Clearance as Breadcrumbs displayed");
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {PDP, REGISTEREDONLY})
    public void addMultiColor_fav(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user in any store verify user is able to favorite same product with multi color in PDP DT-33059,  DT-43788, DT-43789");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");

        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);

        mproductDetailsPageActions.selectAColor(mproductDetailsPageActions);
        mproductDetailsPageActions.addToWLAsReg();

        mproductDetailsPageActions.selectAColor(mproductDetailsPageActions);
        mproductDetailsPageActions.addToWLAsReg();

        panCakePageActions.navigateToMenu("FAVORITES");

        AssertFailAndContinue(mobileFavouritesActions.prodImg.size() == 2, "Verify all itesm are displayed in FAV");

        AssertFailAndContinue(mfooterActions.validateContactUsLink(), "Validate contact us link");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {PDP, PROD_REGRESSION})
    public void verifyStayOnPDPPageAfterATB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("Verify if the user has added 2 stores to bag, when user navigates to PDP of any BOPIS eligible item and initiates \"Pick Up in Store\" modal and "
                + " add the product to bag. Verify that the user is returned back to PDP page of the product.: DT-37467" +
                "DT-37469, DT-37465");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String validUPCNumber = null;
        String validZip = null;
        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm2", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm2", "validZipCode");
        }

        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm2", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm2", "validZipCodeCA");
        }

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        mheaderMenuActions.clickShoppingBagIcon();
        List<String> bopisStoresSelected = mshoppingBagPageActions.getPickUpStoreNameDesplayedForProduct();
        String fulfillmentCenter1 = bopisStoresSelected.get(0);
        String fulfillmentCenter2 = bopisStoresSelected.get(1);
        if (fulfillmentCenter1.equals(fulfillmentCenter2)) //Incase there is same fulfillment center then change the store.
        {
            mshoppingBagPageActions.clickChangeStoreLink(mbopisOverlayActions, 0);
            mbopisOverlayActions.changeBopisStore(fulfillmentCenter1, validZip.substring(0, validZip.indexOf(',')));

            bopisStoresSelected = mshoppingBagPageActions.getPickUpStoreNameDesplayedForProduct();
            fulfillmentCenter1 = bopisStoresSelected.get(0);
            fulfillmentCenter2 = bopisStoresSelected.get(1);
        }

        AddInfoStep("Two different stores are selected in shopping bag");

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm6", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm6", "validZipCode");
        }

        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm6", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm6", "validZipCodeCA");
        }

        //Now navigate to PDP of any product and add to bag from Pick up in store overlay.
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);
        mproductDetailsPageActions.clickPickUpStoreBtn2StoresSelected(mbopisOverlayActions);
        mbopisOverlayActions.closeBopisOverlayModal();
        AssertFailAndContinue(mproductDetailsPageActions.isDisplayed(mproductDetailsPageActions.productTitleInformation()), "Verify user stays on PDP page when close icon is clicked on bopis overlay.");

        mproductDetailsPageActions.clickPickUpStoreBtn2StoresSelected(mbopisOverlayActions);
        mbopisOverlayActions.selectASize(6);
        mbopisOverlayActions.clickCheckAvailabilityBtn();
        int storeCount = mbopisOverlayActions.notSelectedAvailableStores.size();
        AssertFailAndContinue(storeCount == 2, "Verify store count is equal to 2");
        List<String> storeNames = mbopisOverlayActions.getStoreNames();
        AssertFailAndContinue(storeNames.get(0).equals(fulfillmentCenter1.toLowerCase()), "Previously selected store 1 is shown");
        AssertFailAndContinue(storeNames.get(1).equals(fulfillmentCenter2.toLowerCase()), "Previously selected store 2 is shown");
        mbopisOverlayActions.clickAddtoBag();
        AssertFailAndContinue(mproductDetailsPageActions.isDisplayed(mproductDetailsPageActions.productTitleInformation()), "Verify user stays on PDP page when product is added to bag from bopis overlay.");

    }

    @Test(priority = 8, dataProvider = dataProviderName, groups = {PDP, PROD_REGRESSION})
    public void verifySocialNetworkLinks(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("DT-37364, DT-37362: Verify if the user is redirected to facebook/twitter/pininterest page when clicked on social networking links" +
                "DT-37360, DT-37362");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "Click Product Image");

        AddInfoStep("User has landed on PDP page");
        AssertFailAndContinue(mproductDetailsPageActions.validateSocialLinks(), "Check the Social links displayed in PDP page");
        String parentWindow = mproductDetailsPageActions.getCurrentWindow();

        mproductDetailsPageActions.clickAndSwitchToWindowFrom(mproductDetailsPageActions.pininterestIcon, parentWindow);
        AssertFailAndContinue(mproductDetailsPageActions.getCurrentURL().contains("pinterest"), "The pintrest icon navigates to pinterest share API");
        AddInfoStep("On clicking pininterest icon, user navigated to pininterest page");
        mproductDetailsPageActions.closeDriver();

        mproductDetailsPageActions.switchBackToParentWindow(parentWindow);

        mproductDetailsPageActions.clickAndSwitchToWindowFrom(mproductDetailsPageActions.facebookIcon, parentWindow);
        AssertFailAndContinue(mproductDetailsPageActions.getCurrentURL().contains("facebook"), "The facebook icon navigates to facebook share API");
        AddInfoStep("On clicking facebook icon, user navigated to facebook page");
        mproductDetailsPageActions.closeDriver();

        mproductDetailsPageActions.switchBackToParentWindow(parentWindow);

        mproductDetailsPageActions.clickAndSwitchToWindowFrom(mproductDetailsPageActions.twitterIcon, parentWindow);
        AssertFailAndContinue(mproductDetailsPageActions.getCurrentURL().contains("twitter"), "The twitter icon navigates to twitter share API");
        AddInfoStep("On clicking twitter icon, user navigated to twitter page");
        mproductDetailsPageActions.closeDriver();
        mproductDetailsPageActions.switchBackToParentWindow(parentWindow);
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
