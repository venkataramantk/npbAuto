package tests.mobileDT.shoppingBag;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;
import java.util.Map;

//@Test(singleThreaded = true)
public class MobileShoppingBagTests1 extends MobileBaseTest {

    WebDriver mobileDriver;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email =  UiBaseMobile.randomEmail(), password = "";
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);

        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                email = UiBaseMobile.randomEmail();
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
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
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION})
    public void verifyPriceUpdateOnRemovingItems(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify estimated total and item cost gets decreased on removing the product from shopping bag"+
                "\n Verify estimated total and item cost gets increased when product is added to bag" +
                "\nVerify item count gets updated in order ledger when quantity is updated.");

        //DT-38002
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.checkoutBtn), "Verify checkout CTA button is not displayed when shopping bag is empty.");
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.orderSummarySection), "Verify order summary is not shown when shopping bag is empty.");
        mshoppingBagPageActions.staticWait(4000);
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Qty");

        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);

        mheaderMenuActions.clickShoppingBagIcon();
        Float totalItemCostInOrderLedgerFor1Item = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        Float estimatedTotalOnOrderLedgerFor1Item = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());

        String searchKeyword2 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumberUSCA", "Value");
        String qty2 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumberUSCA", "Qty");
        Map<String, String> searchKeywordAndQty2 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword2, qty2);

        addToBagFromPDP(searchKeywordAndQty2);
        mheaderMenuActions.clickShoppingBagIcon();

        //DT-38024
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.shipTtlLineItem), "Verify shipping line item is present.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals("-"), "Verify shipping line item has - as value");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.taxTtlLineItem), "Verify Tax line item is present.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.taxTotal).equals("$0.00"), "Verify tax total value is $0.00");

        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == 2, "Verify item count in order ledger is 2");

        Float totalItemCostInOrderLedgerFor2Item = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        Float estimatedTotalOnOrderLedgerFor2Item = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        //DT-38012
        AssertFailAndContinue(totalItemCostInOrderLedgerFor2Item>totalItemCostInOrderLedgerFor1Item, "Verify items total get increased when product is added to bag");
        AssertFailAndContinue(estimatedTotalOnOrderLedgerFor2Item>estimatedTotalOnOrderLedgerFor1Item, "Verify estimated total gets increased when product is added to bag");

        mshoppingBagPageActions.clickRemoveLinkByPosition(1, mheaderMenuActions);
        Float totalItemCostInOrderLedgerAfterRemoving = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        Float estimatedTotalOnOrderLedgerAfterRemoving = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        //DT-38013
        AssertFailAndContinue(totalItemCostInOrderLedgerAfterRemoving<totalItemCostInOrderLedgerFor2Item, "Verify items total get decreased when product is removed from bag");
        AssertFailAndContinue(estimatedTotalOnOrderLedgerAfterRemoving<estimatedTotalOnOrderLedgerFor2Item, "Verify estimated total gets decreased when product is removed from bag");
        //DT-38005
        int itemCount = mshoppingBagPageActions.getItemsCountInOL();
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == 1, "Verify item count in order ledger is 1 and it gets updated after removing product.");

        //DT-38010 - Added by Richa Priya
        float totalItemCostForOneProduct = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        float estimatedTotalForOneProduct = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());

        String productName0 = mheaderMenuActions.getText(mshippingPageActions.produtnames.get(0));
        //DT-37864
        AssertFailAndContinue(mshoppingBagPageActions.changeQuantity(productName0, "3"), "Verify user is able to update quantity and spinner/notification message is shown to the user.");
        AddInfoStep("Quantity has been increased and updated to 3");
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() > itemCount, "Verify item count in order ledger is 3");

        float totalItemCostInOrderLedgerOnQtyUpdate = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        float estimatedTotalInOrderLedgerOnQtyUpdate = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());

        AssertFailAndContinue(  totalItemCostInOrderLedgerOnQtyUpdate > totalItemCostForOneProduct, " DT-38010:: Verify items total get increased when product qty is increased in SB");
        AssertFailAndContinue(estimatedTotalInOrderLedgerOnQtyUpdate > estimatedTotalForOneProduct, "DT-38010:: Verify estimated total get increased when product qty is increased in SB");
        //DT-37852
        AssertFailAndContinue(String.valueOf(totalItemCostInOrderLedgerOnQtyUpdate).equals(String.format("%.2f", totalItemCostForOneProduct*3)), "DT-37852: Verify price gets updated when quantity is increased.");
        //DT-38013
        AssertFailAndContinue(totalItemCostInOrderLedgerAfterRemoving<totalItemCostInOrderLedgerFor2Item, "Verify items total get decreased when product is removed from bag");
        AssertFailAndContinue(estimatedTotalOnOrderLedgerAfterRemoving<estimatedTotalOnOrderLedgerFor2Item, "Verify estimated total gets decreased when product is removed from bag");
        //DT-38004
        mshoppingBagPageActions.changeQuantity(productName0, "2");
        AddInfoStep("Quantity has been decreased and updated to 2");
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == 2, "Verify item count in order ledger is 2 and it gets updated.");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION,REGISTEREDONLY})
    public void verify_OrderLedger_AddedFromFavorite(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify order ledger is updated accordingly on moving the product from favorite list to shopping bag");

        int quantity = Integer.parseInt(mheaderMenuActions.getQty());
        Double estimatedTtl = Double.parseDouble(mshoppingBagPageActions.getProdPrice());
        AddInfoStep("Estimated total before moving product from favorite::" +estimatedTtl);

        Double itemPrice = mshoppingBagPageActions.getItemPrice();
        AddInfoStep("Item Total before moving product from favorite::" +itemPrice);

        int itemCount = mshoppingBagPageActions.getItemsCountInOL();
        AddInfoStep("Product Count before moving product from favorite::" +itemCount);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToFavoritesBySearching(searchKeywordAndQty);

        panCakePageActions.navigateToMenu("FAVORITES");
        mobileFavouritesActions.addProductToBagByPosition(1);

        mheaderMenuActions.clickShoppingBagIcon();

        AssertFailAndContinue(mheaderMenuActions.getBagCount() > quantity,"Verify bag count should be increased");

        Double estimatedTtl_updated = Double.parseDouble(mshoppingBagPageActions.getProdPrice());
        AddInfoStep("Estimated total after moving product from favorite::" +estimatedTtl_updated);

        Double itemPrice_updated = mshoppingBagPageActions.getItemPrice();
        AddInfoStep("Item Total after moving product from favorite::" +itemPrice_updated);

        int itemCount_updated = mshoppingBagPageActions.getItemsCountInOL();
        AddInfoStep("Product count after moving product from favorite::" +itemCount_updated);
        //DT-38008
        AssertFailAndContinue(estimatedTtl_updated > estimatedTtl,"Verify estimated total is increased");
        AssertFailAndContinue(itemPrice_updated > itemPrice,"Verify item total price line item is increased");
        AssertFailAndContinue(itemCount_updated > itemCount,"Verify item count on line item is increased");

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {COUPONS,PROD_REGRESSION})
    public void verifyCouponAmountFromMyBag_SB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store,  verify that the coupon amount gets discounted from the item(#) added to my Bag and not from the shipping/Tax amount in the Estimated total.");
        String mCoupon = "";
        Map<String, String> mc = null;
        Map<String, String> shippingDetails = null;

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if(store.equalsIgnoreCase("US")){
            shippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        }
        else if(store.equalsIgnoreCase("CA")){
            shippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        addToBagBySearching(searchKeywordAndQty);
        mheaderMenuActions.clickShoppingBagIcon();

        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
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

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shippingDetails.get("fName"), shippingDetails.get("lName"), shippingDetails.get("addressLine1"), shippingDetails.get("addressLine2"), shippingDetails.get("city"), shippingDetails.get("stateShortName"), shippingDetails.get("zip"), shippingDetails.get("phNum"), "Express", email), "Verify after entering shipping detail user is redirected to Billing page successfully ");

      //  mbillingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(mc.get("fName"), mc.get("lName"), mc.get("addressLine1"), mc.get("city"), mc.get("stateShortName"), mc.get("zip"), mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));

        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        //DT-34559
        double itemPrice = mshoppingBagPageActions.getTotalPrice();
        AddInfoStep("Item price before applying coupon is ::" +itemPrice);
        double estimatedTax = mshoppingBagPageActions.getEstimatedTax();
        AddInfoStep("Estimated tax before applying coupon is ::" +estimatedTax);

        double shippingPrice = Double.parseDouble(mshoppingBagPageActions.getShippingPrice());
        AddInfoStep("Shipping price applying coupon is ::" +shippingPrice);

        AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(mCoupon), "Enter the valid coupon code and click on apply button at Shopping page");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField),"Verify coupon line item is displayed on order ledger");

        double itemPrice_updated = mshoppingBagPageActions.getTotalPrice();
        AddInfoStep("Item price after applying coupon is ::" +itemPrice_updated);

        double estimatedTtl = Double.parseDouble(mshoppingBagPageActions.getProdPrice());
        AddInfoStep("Estimated total after applying coupon is ::" +estimatedTtl);

        AddInfoStep("Total of item price, estimated tax and shipping price is ::" +(itemPrice_updated + estimatedTax + shippingPrice));

        AssertFailAndContinue(itemPrice_updated < itemPrice,"Verify item price is decreased after applying coupon");
        AssertFailAndContinue((itemPrice_updated + estimatedTax + shippingPrice) == estimatedTtl,"verify estimated total is equal to the sum of updated item price plus estimated tax plus shipping price");

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.coupnField),"Verify coupon line item is displayed on order ledger on shipping page");
        mshippingPageActions.clickNextBillingButton();

        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.coupnField),"Verify coupon line item is displayed on order ledger on billing page");
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(mc.get("securityCode"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.coupnField),"Verify coupon line item is displayed on order ledger on review page");
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {COUPONS, CHECKOUT,PROD_REGRESSION})
    public void validateUpdateQtyModal_NeedHelpLink(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha/Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shipping page that if the user clicks on need help for coupon, coupon help overlay opens" +
                "DT-37863::Verify user updating attributes and without closing edit, clicking CHECKOUT button displays alert confirmation model");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email,password);
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);
        //DT-40453
        AssertFailAndContinue(mshoppingBagPageActions.validateOverlayOnClickingNeedHelpLink(),"Verify overlay is displayed on clicking need help link in shopping bag page.");
        mshoppingBagPageActions.clickNeedHelpOverlay();

        //DT-37863
        AssertFailAndContinue(mshoppingBagPageActions.updateQty_STHProd_WithOutSaving("3"), "Verify quantity of a product updated successfully on shopping bag");
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtn(mloginPageActions, mshoppingBagPageActions), "Verify successful checkout");
        AssertFailAndContinue(mshoppingBagPageActions.verifyUnsavedAlertModal(), "Verify unsaved cart changes confirmation modal");
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickContinueToCheckoutBtn(mshippingPageActions),"Verify successul checkout");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        //DT-35914
        AssertFailAndContinue(mshoppingBagPageActions.validateOverlayOnClickingNeedHelpLink(),"Verify overlay is displayed on clicking need help link above coupon text");
    }


    @Test(priority = 4, dataProvider = dataProviderName, groups= {SHOPPINGBAG} )
    public  void verifyCpnWithAssociateUser(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> mailingDetails = null;
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to see " +
                "promotion details for associate user and also able to apply free shipping coupon."+"DT-37959,DT-37957,DT-37960");
        //DT-37958
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            String email=getmobileDT2CellValueBySheetRowAndColumn("Login", "UserHavingPLCCCpn", "EmailID");
            String password=getmobileDT2CellValueBySheetRowAndColumn("Login", "UserHavingPLCCCpn", "Password");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT 37965
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm4", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm4", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else{
            mheaderMenuActions.clickShoppingBagIcon();
        }

        Float estTotalOnOLBeforeMakingUserAssociate= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        Map<String, String> associateDetails = dt2ExcelReader.getExcelData("MyAccount", "ValidAssociateID");

        if(store.equalsIgnoreCase("US")){
            mailingDetails = dt2ExcelReader.getExcelData("MyAccount", "USAddress");
        } if(store.equalsIgnoreCase("CA")){
            mailingDetails = dt2ExcelReader.getExcelData("MyAccount", "CAAddress");
        }
        makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"),mailingDetails.get("AddressLine1"),mailingDetails.get("City"),mailingDetails.get("stateShortName"));

        //makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"));

        mheaderMenuActions.clickShoppingBagIcon();
        Float estTotalOnOLAfterMakingUserAssociate= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AssertFailAndContinue(estTotalOnOLBeforeMakingUserAssociate>estTotalOnOLAfterMakingUserAssociate, "Verify that estimated total decreases as user is now associate user and promotion is applied.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals("-"), "Verify shipping line item has - as value");
        String loyaltyCoupon=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validLoyaltyCoupon", "couponCode");
        mshoppingBagPageActions.applyCouponByText(loyaltyCoupon);
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponText), "Verify applied coupon section is shown.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shopping bag");
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.nextBillingButton);
        Map<String, String> usBillingAdd = null;
        Map<String, String> caShippingAdd = null;

        if (store.equalsIgnoreCase("US")) {
            usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsAndShipType(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mshippingPageActions.enterShippingDetailsAndShipType(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email);
        }

        Map<String, String> shipMethodDetailsExpress = null;

        if(store.equalsIgnoreCase("US"))
        {
            shipMethodDetailsExpress = dt2ExcelReader.getExcelData("ShippingMethodCodes", "express");
            mshippingPageActions.selectShippingMethodFromRadioButton(mshippingPageActions.getAvailableShippingMethods().get(1));
        }
        if(store.equalsIgnoreCase("CA"))
        {
            shipMethodDetailsExpress = dt2ExcelReader.getExcelData("ShippingMethodCodes", "express_CA");
            mshippingPageActions.selectShippingMethodFromRadioButton(mshippingPageActions.getAvailableShippingMethods().get(1));
        }

        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "Verify user is redirected to Billing page");

        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals(shipMethodDetailsExpress.get("shipPrice")), "Verify shipping price is shown as $15 for express shipping selected.");
        Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        if(store.equalsIgnoreCase("US"))
            mshoppingBagPageActions.applyCouponByText("AUTOFREESHIP");
        else
            mshoppingBagPageActions.applyCouponByText("Free Shipping at $50 (CABOPISFS)");//DT 37965
        Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsTot), "Verify promotion amount is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals("Free"), "Verify shipping price is removed on applying AUTOFREESHIP coupon");
    }
    @Test(priority = 5, dataProvider = dataProviderName, groups= {SHOPPINGBAG,COUPONS} )
    public  void verifyLoyaltyandMerchandiseWithFreeShipping(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shubhika");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able  " +
                "  to apply free shipping coupon with loyalty and Merchandise."+"DT-37962");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            String email=getmobileDT2CellValueBySheetRowAndColumn("Login", "UserHavingPLCCCpn", "EmailID");
            String password=getmobileDT2CellValueBySheetRowAndColumn("Login", "UserHavingPLCCCpn", "Password");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm4", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm4", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else{
            mheaderMenuActions.clickShoppingBagIcon();
        }

        mheaderMenuActions.clickShoppingBagIcon();

        String loyaltyCoupon=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validLoyaltyCoupon", "couponCode");
        mshoppingBagPageActions.applyCouponByText(loyaltyCoupon);
        mobileDriver.navigate().refresh();
        String merchandiseCoupon=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar10Percent", "couponCode");
        mshoppingBagPageActions.applyCouponByText(merchandiseCoupon);
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponText), "Verify applied coupon section is shown.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shopping bag");
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.nextBillingButton);
        Map<String, String> usBillingAdd = null;
        Map<String, String> caShippingAdd = null;

        if (store.equalsIgnoreCase("US")) {
            usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsAndShipType(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mshippingPageActions.enterShippingDetailsAndShipType(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email);
        }

        Map<String, String> shipMethodDetailsExpress = null;

        if(store.equalsIgnoreCase("US"))
        {
        	shipMethodDetailsExpress = dt2ExcelReader.getExcelData("ShippingMethodCodes", "express");
        	mshippingPageActions.selectShippingMethodFromRadioButton(mshippingPageActions.getAvailableShippingMethods().get(1));
        }
        if(store.equalsIgnoreCase("CA"))
        {
        	shipMethodDetailsExpress = dt2ExcelReader.getExcelData("ShippingMethodCodes", "express_CA");
        	mshippingPageActions.selectShippingMethodFromRadioButton(mshippingPageActions.getAvailableShippingMethods().get(1));
        }

        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "Verify user is redirected to Billing page");

        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals(shipMethodDetailsExpress.get("shipPrice")), "Verify shipping price is shown as $15 for express shipping selected.");
        Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        if(store.equalsIgnoreCase("US"))
        	mshoppingBagPageActions.applyCouponByText("AUTOFREESHIP");
        else
        	mshoppingBagPageActions.applyCouponByText("Free Shipping at $50 (CABOPISFS)");
        Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsTot), "Verify promotion amount is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals("Free"), "Verify shipping price is removed on applying AUTOFREESHIP coupon");
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