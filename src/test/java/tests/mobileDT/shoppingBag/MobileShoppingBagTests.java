package tests.mobileDT.shoppingBag;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.List;
import java.util.Map;

//User Story: DT-638
//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class MobileShoppingBagTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String productName0 = "";
    String email1 = UiBaseMobile.randomEmail();
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception
    {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email1);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email1);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {SHOPPINGBAG})
    public void shoppingBagTcpLogoRedirection(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        String mCoupon = null;
        setRequirementCoverage("As a " + user + " user  in " + store + " Store " +
                "1. Verify Shopping Bag Url" +
                "2. User is redirected to homePage by clicking on Logo from shoppingBagPage");
        //DT-2237
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        if (store.equalsIgnoreCase("US")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA");
            }
        }

        Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        mheaderMenuActions.clickShoppingBagIcon();
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.emptyBagMessage).equals(getDT2TestingCellValueBySheetRowAndColumn("Bag", "EmptyBag", "value")), "Verify Empty bag message");

        //DT-7927
        String shoppingbagUrl = EnvironmentConfig.getApplicationUrl().replace("home", "bag");
        AssertFailAndContinue(mobileDriver.getCurrentUrl().equals(shoppingbagUrl), "Verify the vanity URL displaying on address bar based on country");

        //DT-2203
        mheaderMenuActions.click(mheaderMenuActions.TCPLogo);
        AssertFailAndContinue(mobileDriver.getCurrentUrl().equals(EnvironmentConfig.getApplicationUrl()), "verify that the user is redirected to the corresponding store's home page after clicking on Logo in shopping page .");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        //DT-38031
        AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(mCoupon),"Verify apply coupon");

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsAndShipType(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), "Express", email1);
        }
        if (store.equalsIgnoreCase("CA")) {
            mshippingPageActions.enterShippingDetailsAndShipType(caShippingDetails.get("fName"), caShippingDetails.get("lName"), caShippingDetails.get("addressLine1"), caShippingDetails.get("addressLine2"), caShippingDetails.get("city"), caShippingDetails.get("stateShortName"), caShippingDetails.get("zip"), caShippingDetails.get("phNum"), "Express", email1);
        }

        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.applyCoupanAndCheckEstimatedTotal(),"Validate that the Estimated Total is calculated based on the following logic:\n" +
                "Estimated total \n" +
                "=Sum of price of all products in the shopping bag \n" +
                " Amount of Coupons / Promotions (if applicable) + Shipping cost + Estimated Tax (if applicable)");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {SHOPPINGBAG, REGISTEREDONLY})
    public void mobileBagTest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify as a " + user + " user in " + store + " store can apply the coupon code and check the estimated total and the bag count");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0){
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        //DT-2240 and DT-6257, DT-6258, DT-6260
        addToBagBySearching(searchKeywordAndQty);
        productName0 = mheaderMenuActions.getText(mshippingPageActions.produtnames.get(0));
        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermNew", "Value");
        String qty1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermNew", "Qty");
        Map<String, String> searchKeywordAndQty1 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword1, qty1);

        addToBagBySearching(searchKeywordAndQty1);
        int addcount = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink));
        AssertFailAndContinue(addcount == 2, "Verify min-cart count after adding products");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.heading).contains("MY BAG (2)"), "Verify shopping page header text after adding product");
        AssertFailAndContinue(productName0.equals(mheaderMenuActions.getText(mshippingPageActions.produtnames.get(1))), "Verify first item added to agg being at the bottom");

        //DT-2242, DT-6269, DT-6270
        mheaderMenuActions.clickShoppingBagIcon();
        String productName = mheaderMenuActions.getText(mshippingPageActions.produtnames.get(1));
        mshoppingBagPageActions.click(mshoppingBagPageActions.favIcon(productName));
        //mshoppingBagPageActions.clic
        int addcount0 = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink));
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.heading).contains("MY BAG (1)"), "Verify shopping page header after adding products to wishlist");
        AssertFailAndContinue(addcount0 == 1, "Add product from bag to the wishlist, verify bag count");

        //DT-2243
        panCakePageActions.navigateTo("FAVORITES");
        mobileFavouritesActions.click(mobileFavouritesActions.addProductToBag(productName));
        int addcount1 = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink));
        AssertFailAndContinue(addcount1 == 2, "Add product from wishlist to the bag, verify bag count");

        //DT-2241, DT-6267, DT-6268, DT-6278
        mheaderMenuActions.click(mheaderMenuActions.bagLink);
        mshoppingBagPageActions.click(mshoppingBagPageActions.removeByProductName(productName));
        int addcount2 = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink));
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.heading).contains("MY BAG (1)"), "Verify shopping page header after removing product");
        AssertFailAndContinue(addcount2 == 1, "Verify Bag after removing products");

        //DT-6261, DT-6262, DT-6273
        mshoppingBagPageActions.changeQuantity(productName0, "3");
        int newcount = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink));
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.heading).contains("MY BAG (3)"), "Verify shopping page header after updating quantity");
        AssertFailAndContinue(newcount == 3, "Verify mini-cart count after updating quantity");

        //DT-6263, DT-6264
        mshoppingBagPageActions.changeQuantity(productName0, "2");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.heading).contains("MY BAG (2)"), "Verify shopping page header after decreased quantity");
        AssertFailAndContinue(Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink)) == 2, "Verify mini-cart count after decreased quantity");

        //DT-6265, DT-6265
        addToBagBySearching(searchKeywordAndQty1);
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.heading).contains("MY BAG (3)"), "Verify shopping page header after adding new product");
        AssertFailAndContinue(Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink)) == 3, "Verify mini-cart count after adding new product");

        //DT-6373
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.upcNo(productName0)), "Verify upc no");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.productProperties(productName0)), "Verify Product color");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.productProperties(productName0)), "Verify product size");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.productProperties(productName0)), "Verify product quantity");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.getProductPrice(productName0)), "Verify Product price");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {SHOPPINGBAG})
    public void updateProductdetails(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + "in " + store + " store validate edit changes in shopping bag");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> upcs = addToBagBySearching(searchKeywordAndQty);


        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0){
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        //DT-6274
        mshoppingBagPageActions.changeProdColor(upcs.get(0));
        AssertFailAndContinue(mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.productProperties(upcs.get(0))), "Update Product color");

        //DT-6275
        mshoppingBagPageActions.changeProdSize(upcs.get(0));
        mshoppingBagPageActions.editButton(upcs.get(0));
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.productProperties(upcs.get(0))), "Update Product size");

        //DT-6276
        AssertFailAndContinue(mshoppingBagPageActions.clickEditLink(upcs.get(0)), "Click edit button for product and verify update and cancel buttons are displayed");
        AssertFailAndContinue(mshoppingBagPageActions.clickCancelButton(), "Click cancel button and check product moved to non-edited mode");

        //AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.editProduct(productName0)), "Verify Cancel button");
        //DT-6277
        String color = mshoppingBagPageActions.getProductAttributes(upcs.get(0), "Color");
        String size = mshoppingBagPageActions.getProductAttributes(upcs.get(0), "Size");
        String qty1 = mshoppingBagPageActions.getProductAttributes(upcs.get(0), "Quantity");

        mshoppingBagPageActions.clickEditLink(upcs.get(0));

        AssertFailAndContinue(mshoppingBagPageActions.getProductAttributes(productName0, "Color").equals(color), "Verify color after update");
        AssertFailAndContinue(mshoppingBagPageActions.getProductAttributes(productName0, "Size").equals(size), "Verify Size after update");
        AssertFailAndContinue(mshoppingBagPageActions.getProductAttributes(productName0, "Quantity").equals(qty1), "Verify Quantity after update");
        AssertFailAndContinue(mshoppingBagPageActions.verifyPickUpStoreIsDisplayed(), "Verify BOPIS is enabled");
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {SHOPPINGBAG})
    public void validateOffers(@Optional("US") String store, @Optional("registered") String user) throws Exception {
    	setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + "in " + store + " store verify coupon code");

        //DT-6279
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        String validCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
        String invalidCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "invalidCouponCode", "couponCode");

        String invalidCouponErrMsg = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "specialChar", "validErrorMessage");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else{
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Verify click on shopping bag");
        }
        
        AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(mshoppingBagPageActions.convertToCamelCase(validCoupon)), "Apply Coupon and verify coupon is applied");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify Coupon line item is displayed");

        float estimatedTotalAfterApplyingCoupon_1 = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AddInfoStep("Estimated total after applying coupon::" +estimatedTotalAfterApplyingCoupon_1);
        //DT-38019
        AssertFailAndContinue(mshoppingBagPageActions.removeCouponFromShoppingBag(),"remove coupon form SB");
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.couponText),"Verify coupon line item is not displayed on Order Ledger");

        float estimatedTotalAfterRemovingCoupon = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AddInfoStep("Estimated total after removing applying coupon::" +estimatedTotalAfterRemovingCoupon);

        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify Coupon field line item is not displayed");
        AssertFailAndContinue(estimatedTotalAfterRemovingCoupon > estimatedTotalAfterApplyingCoupon_1,"Verify estimated total is increased after removing coupon from SB");
        //DT-6280
        AssertFailAndContinue(mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.orderSummarySection, 20), "Validate order Summary");
        //DT-6281
        if(user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.mprContainer, 10), "Validate MPR Section");
            //DT-6283 //THis is seasonal test cases
            AssertFailAndContinue(mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.mprContainer, 10), "Validate PLCC e-sport");
        }
        if(user.equalsIgnoreCase("registered")){
            AssertFailAndContinue(mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.mprContainerReg, 10), "Validate MPR Section");
            //DT-6283 //THis is seasonal test cases
            AssertFailAndContinue(mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.mprContainerReg, 10), "Validate PLCC e-sport");
        }
    
        //DT-38020
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify Coupon field is not displayed");
        Float estimatedTotalBeforeApplyingCoupon = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        Float itemCostInOrderLedgerBeforeApplyingCoupon = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(mshoppingBagPageActions.convertToCamelCase(validCoupon)), "Apply Coupon and verify coupon is applied");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify Coupon field is displayed");
        Float estimatedTotalAfterApplyingCoupon = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AssertFailAndContinue(estimatedTotalAfterApplyingCoupon<estimatedTotalBeforeApplyingCoupon, "Verify estimated total gets decreased after applying coupon");
        Float itemCostInOrderLedgerAfterApplyingCoupon = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        //DT-38023
        AssertFailAndContinue(Float.compare(itemCostInOrderLedgerBeforeApplyingCoupon, itemCostInOrderLedgerAfterApplyingCoupon)==0, "Verify item cost should not change even after applying coupon.");
        
        //DT-37853
        String originalPriceActualColor = "#999999", offerPriceActualColor = "#333333";
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.originalPrice) && mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.offerPrice), "Verify if both original and offer price is shown when coupon has been applied.");
        AssertFailAndContinue(mshoppingBagPageActions.verifyOriginalPriceElementAndColor(originalPriceActualColor), "validating original price color which striked out after the coupon apply");
        AssertFailAndContinue(mshoppingBagPageActions.verifyOfferPriceElementAndColor(offerPriceActualColor), "validating offer price color after the coupon apply");
        //DT-38022
        AssertFailAndContinue(mshoppingBagPageActions.verifyOfferPriceInBold(), "Verify offer price is shown in bold.");
        //DT-37938
        AssertFailAndContinue(mshoppingBagPageActions.enterInvalidCouponCodeAndApply(invalidCoupon), "Verify on applying invalid coupon, error message is shown to the user.");
        //DT-37932
      //  AssertFailAndContinue(mshoppingBagPageActions.applyInvalidCouponByText("AUTOFREESHIP").equalsIgnoreCase(invalidCouponErrMsg),"DT-37932 :: Verify applying invalid coupon from the available list throws error message");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {SHOPPINGBAG, REGISTEREDONLY})
    public void expressCheckoutAsReg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        //6290, DT-6287,


        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        panCakePageActions.navigateTo("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        mmyAccountPageActions.addNewShippingAddress(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "BirthdayClub", "FirstName"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "BirthdayClub", "LastName"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "AddressLine1"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "City"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "State"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "ZipCode"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "MobileNo"),panCakePageActions);
        panCakePageActions.navigateTo("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        mmyAccountPageActions.addACreditCard(getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "VISA", "cardNumber"),
                getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "VISA", "expMonth"),
                getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "VISA", "expYear"), true,
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "BirthdayClub", "FirstName"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "BirthdayClub", "LastName"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "AddressLine1"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "City"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "State"),
                getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "USAddress", "ZipCode"),panCakePageActions);


        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Verify click on shopping bag");

        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions,mreviewPageActions);

      //  mshoppingBagPageActions.click(mshoppingBagPageActions.checkoutBtn);

        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.expressCheckout).equalsIgnoreCase("Express Check out"), "Validate PLCC e-sport");

        mreviewPageActions.clearAndFillText(mreviewPageActions.cvvTxtField, getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "VISA", "securityCode"));
        mreviewPageActions.click(mreviewPageActions.submitOrderButton);
        AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Order with user having Shipping, Billing and Payment Method in My Account");
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {SHOPPINGBAG, GUESTONLY})
    public void loginFromBagAndCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Login from bag and verify user is able to checkout");
        //DT-6288, DT-6296
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, "1");
        Map<String, String> mc = null;
        addToBagBySearching(searchKeywordAndQty);

        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);
        mshoppingBagPageActions.click(mshoppingBagPageActions.loginFrombagButton);

        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"));
        mreviewPageActions.clearAndFillText(mreviewPageActions.cvvTxtField, getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "VISA", "securityCodem"));

        //shipping details
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "addressLine1"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "addressLine2"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "city"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "stateShortName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "zip"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"), "", "new" + email1);
        }

        if (store.equalsIgnoreCase("CA")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "lName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "addressLine1"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "addressLine2"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "city"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "stateShortName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "zip"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "phNum"), "", "new" + email1);
        }

        if (env.equalsIgnoreCase("prod")) {
            mbillingPageActions.enterCardDetails(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));

        } else {
            mbillingPageActions.enterCardDetails(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        }

        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.clickSubmOrderButton();

        //mreviewPageActions.click(mreviewPageActions.submitOrderButton);
        AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Order with user logged in from bag");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {SHOPPINGBAG, CAONLY})
    public void caEspot(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        //DT-6289 need to check with guest user
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a  " + user + " Verify e-spot in CA");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.CA_epsot), "Verify epost in shopping bag for CA");
    }

    @Test(priority = 8, dataProvider = dataProviderName, groups = {SHOPPINGBAG})
    public void changeOfcountry(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify bag count after changing the store" +
                "DT-37431");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(true, "Verify the count of mini cart of adding items to bag");

        mheaderMenuActions.click(mheaderMenuActions.TCPLogo);

        if (store.equalsIgnoreCase("US")) {
            mfooterActions.changeCountryByCountry("CANADA");
        }

        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("United States");
        }
        int finalCount = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.bagLink));
        AssertFailAndContinue(finalCount == 0, "Verify the count of mini cart is reset after changing country");
    }

    @Test(priority = 9, dataProvider = dataProviderName, groups = {RECOMMENDATIONS, SHOPPINGBAG})
    public void valitedRecommendadtions_SB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store,When the user is in Shopping bag, verify user is able to add recommendations products to fav"+
        "\nAs a guest user verify user is shown login modal upon clicking fav icon in recommendation and upon successful login, product is seen in fav list");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        //DT-37539 & DT-37542
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);
        
        String productName1InRecommendation = (mfooterActions.recomm_productNames.get(0)).getText();
        AddInfoStep("First Product name in recommendation" + productName1InRecommendation);

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsGuest(mloginPageActions), "Click fav icon on first recommended product verify login page is displayed");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsReg(), "Click fav icon on first recommended product ");
        }
        //DT-37843
        panCakePageActions.navigateToMenu("FAVORITES");
        AssertFailAndContinue(mobileFavouritesActions.verifyProdPresentByTitle(productName1InRecommendation), "Verify Item added as Favourite is displayed in favourite list");
        AssertFailAndContinue(mobileFavouritesActions.isDisplayed(mfooterActions.favorited_item), "Verify favorite icon is highlighted");
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }

}
