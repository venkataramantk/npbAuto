package tests.webDT.shoppingBag;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 4/25/2017.
 */
public class ViewProductDetails extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(ViewProductDetails.class);
    String emailAddress;
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            if (user.equalsIgnoreCase("registered")) {
                emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");//"testb10e6b5242981@yopmail.com";//
            }


        } else if (store.equalsIgnoreCase("CA")) {
            // headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered")) {
                emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
            }
            headerMenuActions.addStateCookie("MB");
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

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION})
    public void validateShoppingBagPageContent(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Validate the content present in the shopping bag page and check if user navigate to PDP page on clicking the product name" +
                "2. DT-43789 and DT-43788");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> helpLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "linkName"));
        List<String> helpURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "URLValue"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.validateHeaderBanner(), "Validate the header banner present in shopping bag page");
        AssertFailAndContinue(shoppingBagPageActions.validateProdDetails(), "Validate the product details present in shopping bag page");
        AssertFailAndContinue(shoppingBagPageActions.clickOnProdName(productDetailsPageActions), "Verify if user redirected to PDP page on clicking the product name in shopping bag page");
        //DT-43789 and DT-43788
        AssertFailAndContinue(footerActions.contactUSLinkValidation(helpLinkValidation.get(6), helpURLValidation.get(8),store), "Validate if user navigates to " + helpURLValidation.get(8) + " page on clicking the " + helpLinkValidation.get(6) + " link");
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, COUPONS, CHECKOUT, PROD_REGRESSION})
    public void applyCouponAndUpdateQTYCheckTotal(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify guest user can add prodict to the cart and check if the estimated total is equal to the sum of item price, shipping and tax in order summary section after applied coupon");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> couponCode = null;
        Map<String, String> vi = null;
        Map<String, String> shipDetails = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
            vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            if(env.equalsIgnoreCase("prod")){
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));
                vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

            }

        } else if (store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA");
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            if(env.equalsIgnoreCase("prod")){
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));
                vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

            }
        }
        List<String> invalidCoupon = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "specialChar", "invalidInput"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        shoppingBagPageActions.updateQty("2");
        AssertFailAndContinue(shoppingBagPageActions.calculatingEstimatedTotal(), "Calculate the estimated total in order summary section which is equal to item price, shipping price and tax price");
        AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button");
        AssertFailAndContinue(shoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Verify the estimated cost after applied the valid coupon code");
        AssertFailAndContinue(shoppingBagPageActions.enterInvalidCouponCodeAndApply(invalidCoupon.get(1)), "Entering invalid coupon throwing error message");
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);

        } else {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }

        AssertFailAndContinue(shippingPageActions.removeAppliedCoupons(), "Removed the coupon at shipping page");
        AssertFailAndContinue(shippingPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at Shipping page");

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");

        } else {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");

        }
        if(!env.equalsIgnoreCase("prod") && (!env.equalsIgnoreCase("dr"))) {
            AssertFailAndContinue(billingPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at billing page");
            AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered the Payment details and Same as shipping Address and clicked on the Next Review button.");
            AssertFailAndContinue(reviewPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at review page");
            reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
            AssertFailAndContinue(orderConfirmationPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at order confirmation page");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, BOPIS, COUPONS,PROD_REGRESSION})
    public void updateBopisQTYAndCheckCount(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if reg user can add BOPIS into the cart and apply coupon and also check the estimated total after applied the coupons");
        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
            validZipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZipCode);
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if (store.equalsIgnoreCase("CA")) {
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA");

        }
        List<String> couponCode = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        } else if (store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
        }

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        String zipCode = findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        AssertFailAndContinue(headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions), "Clicked on View Bag Button navigates to Shopping Bag page");
        AssertFailAndContinue(shoppingBagPageActions.updateFirstOccur_BopisProdQty(bopisOverlayActions, "2", zipCode), "Update the quantity");
        AssertFailAndContinue(shoppingBagPageActions.calculatingEstimatedTotal(), "Calculate the estimated total after update the quantity");
        AssertFailAndContinue(shoppingBagPageActions.validateTitleAndBagCount(), "Verify that the title and bag count is updating properly");
        AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(couponCode.get(0)), "Apply the coupon code");
        String prodPrice = shoppingBagPageActions.getProdPrice();

        AssertFailAndContinue(shoppingBagPageActions.validateTotalAfterAppliedCoupon(), "Verify the original total and estimated total when coupons applied");
        AssertFailAndContinue(shoppingBagPageActions.validateTotalAfterRemoveCoupon(), "Verify the original price and estimated total has been changed accordingly when removed the applied coupons");
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
            pickUpPageActions.clickBillingBtn(billingPageActions);
//            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
//                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
            pickUpPageActions.enterFieldValuesWithoutNextClick("auto","script","tcp@yopmail.com","2123452341");
            pickUpPageActions.clickBillingBtn(billingPageActions);
//            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
//                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        if (!env.equalsIgnoreCase("prod") || (!env.equalsIgnoreCase("dr"))) {
            AssertFailAndContinue(billingPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button at billing page");
            AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"),
                    billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"),
                    billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
            AssertFailAndContinue(reviewPageActions.isBopisProdPriceMatchSBPrice(prodPrice), "Bopis Product price is updated at review page compared with shopping bag price");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, RECOMMENDATIONS})
    public void prodRecommendationInHomePage(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify if reg user can view product recommendations in the Homepage, add the recommended product into wishlist and navigate to PDP and add the product to the bag");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        AssertFailAndContinue(footerActions.productRecommDisplayValidation(), "Validate the Prod Recom present in the homepage"); //Espot content changed to Prod Recom //
        AssertFailAndContinue(footerActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the homepage");
        AssertFailAndContinue(footerActions.addRecommendationProdCard(productCardViewActions), "Verify the Product card functionality ");
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
        String prodName = headerMenuActions.getText(footerActions.prodNameRecommWithPrice.get(0));
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.itemOrderDisplay(prodName), "Verify that the item is added to bag");
        headerMenuActions.click(headerMenuActions.closemodal);
        AssertFailAndContinue(footerActions.checkHeader(), "Check whether the header is displayed constantly");
        footerActions.scrollToTheTopHeader(footerActions.prodWithPriceByPos(1));
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddress, password), "Add the recommendation product into wishlist");

        }
        if(!env.equalsIgnoreCase("prod") || (!env.equalsIgnoreCase("dr"))) {
            performNormalCheckout(store,user,emailAddress);
    }}


    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, RECOMMENDATIONS, REGISTEREDONLY,PROD_REGRESSION})
    public void prodRecommendationInCheckoutPage(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if reg user can view product recommendations in the Order Receipt page, add the recommended product into wishlist and navigate to PDP and add the product to the bag");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", "CreateAccountCA");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        performNormalCheckout(store, user, emailAddress);
        AssertFail(footerActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the order receipt page");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addRecommendationProdCard(productCardViewActions), "Verify the Product card functionality ");
            AssertFailAndContinue(productCardViewActions.selectAFitAndSizeAndAddToBag(), "Add the recommendation product in to the cart");
            AssertFailAndContinue(footerActions.addProdToFav_Guest(loginPageActions), "Add the recommendation product into wishlist");
            loginPageActions.clickCreateAccountLink(createAccountActions);
            createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage"));
            //    AssertFailAndContinue(loginPageActions.successfulLogin(emailAddress, password), "Enter valid credentials and click on login button " + emailAddress);
        } else if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist");

        }
        AssertFailAndContinue(footerActions.addRecommendationProdCard(productCardViewActions), "Verify the Product card functionality ");
        AssertFailAndContinue(productCardViewActions.selectAFitAndSizeAndAddToBag(), "Add the recommendation product in to the cart");
        AssertFailAndContinue(headerMenuActions.clickMiniCartAndCheckoutAsRegUser(shoppingBagDrawerActions, shippingPageActions), "Go to Shopping bag page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        if (!env.equalsIgnoreCase("prod") || (!env.equalsIgnoreCase("dr"))) {
            AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        }
    }
    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, RECOMMENDATIONS,PROD_REGRESSION})
    public void prodRecommendationInPDP(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if reg user can view the product recommendations in the Product Details page");
        String itemID = excelReaderDT2.getExcelData("Search", "SearchTerm").get("ProductUPC");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        AssertFailAndContinue(headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, itemID), "Performed Search using the product ID and landed on the Product Details Page.");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the product details page");
        AssertFailAndContinue(footerActions.addRecommendationProdCard(productCardViewActions), "Verify the Product card functionality ");
        AssertFailAndContinue(productCardViewActions.selectAFitAndSizeAndAddToBag(), "Add the recommendation product in to the cart");
        String prodName = headerMenuActions.getText(footerActions.prodNameRecommWithPrice.get(0));
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.itemOrderDisplay(prodName), "Verify that the item is added to bag");
        headerMenuActions.click(headerMenuActions.closemodal);
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest(loginPageActions), "Add the recommendation product into wishlist");
            AssertFailAndContinue(loginPageActions.successfulLogin(emailAddress, password), "Enter valid credentials and click on login button " + emailAddress);
        } else if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist");
        }
    }


    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, GIFTCARDPRODUCT, USONLY,PROD_REGRESSION})
    public void placeGiftCard(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if guest user can add gift card as a product into the cart and place the order successfully");
        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        List<String> couponCode1 = null;
        couponCode1 = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "BOPISCode", "couponCode"));

        Map<String, String> shipDetails = null, sm = null, vi = null;

        if (store.equalsIgnoreCase("US")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validNJAddressDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        }
        if(!env.equalsIgnoreCase("prodstaging")) {
            if (user.equalsIgnoreCase("registered")) {
                clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
            }
            footerActions.waitUntilElementDisplayed(footerActions.link_GiftCards, 10);
            footerActions.click(footerActions.link_GiftCards);
            footerActions.staticWait(1000);
            footerActions.waitUntilElementDisplayed(footerActions.sendGC_Btn, 10);
            footerActions.click(footerActions.sendGC_Btn);
            productDetailsPageActions.staticWait(1000);
            AssertFailAndContinue(productDetailsPageActions.addGCFromPDP(headerMenuActions), "Adding the Gift Card from PDP");
            AssertFailAndContinue(!productDetailsPageActions.isDisplayed(productDetailsPageActions.pickUpStore), "Pick up store button is not available for GC PDP");
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
            AssertFailAndContinue(shoppingBagPageActions.validateGCProdDetails(), "Verifying giftcard prodImg, prodName, size, upc, Nofav Icon, remove link, qty and color");
            AssertFailAndContinue(!shoppingBagPageActions.isPickUpInStoreRadoCheckedByPos(0), "pickup instore radio button is not displayig for GC");
            if (!env.equalsIgnoreCase("prod") || (!env.equalsIgnoreCase("dr"))) {
                AssertFailAndContinue(shoppingBagPageActions.applyCouponCodeForGC(couponCode.get(0)), "can't apply Coupon code for gift cards");
                AssertFailAndContinue(shoppingBagPageActions.applyCouponCodeForGC(couponCode1.get(0)), "Verify the user is able to apply the BOPIS GEN coupon");
            }
            double priceForProd = shoppingBagPageActions.GetPrice();
            shoppingBagPageActions.scrollToTheTopHeader(shoppingBagPageActions.editBtnLnk);
            AssertFailAndContinue(shoppingBagPageActions.verifyPriceUpdatedWithQtyUpdate("2", priceForProd), "Updating the gift card quantity");
            if (user.equalsIgnoreCase("registered")) {
                AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify if register user navigate to shipping page on clicking checkout button");
                AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
            } else if (user.equalsIgnoreCase("guest")) {
                AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Click on checkout button from shopping bag page");
                AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Click on continue as guest user and navigate to Shipping page");
                AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                        shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
            }
            if (!env.equalsIgnoreCase("prod") && (!env.equalsIgnoreCase("dr"))) {
                AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
                AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
                getAndVerifyOrderNumber("placeGiftCard");
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, BOPIS,PROD_REGRESSION,USONLY})
    public void bopisIneligibleProd(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if guest user can BOPIS ineligible product into the cart and check the message");
        List<String> urlValue = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CanonicalURL", "CanonicalForm", "ProdDetailsPage"));
        String prodUpc = excelReaderDT2.getExcelData("Search", "bopisInEligible").get("ProductUPC");
        String prodRecommUPC = excelReaderDT2.getExcelData("Search", "SearchTerm").get("ProductUPC1");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, prodUpc);
        AssertFailAndContinue(productDetailsPageActions.selectFitAndSizeInPDP(), "Select a size and add item to bag");
        AssertFailAndContinue(productDetailsPageActions.pdpURLCheck(urlValue.get(0)), "Check the URL of PDP page");
        //productDetailsPageActions.selectAnySizeAndClickAddToBagInPDP();
        productDetailsPageActions.click(productDetailsPageActions.addToBag);
        headerMenuActions.staticWait(3000);
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon,5);
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions), "Navigate to SB page");
        AssertFailAndContinue(shoppingBagPageActions.checkBOPISInelegible(), "Check whether select a store Name is enabled");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, prodRecommUPC);
        String prodName = null;

    }


}