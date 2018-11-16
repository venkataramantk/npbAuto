package tests.webDT.shoppingBag;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class RemoveCouponAndVerifyCheckout extends BaseTest

{
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(RemoveCouponAndVerifyCheckout.class);
    String emailAddressReg;
    private String password;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        env = EnvironmentConfig.getEnvironmentProfile();
        initializePages(driver);
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
        } else if (store.equalsIgnoreCase("CA")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");

        }
        emailAddressReg = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "loyaltyAccount1", "Email");
        password = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "loyaltyAccount1", "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");

        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)//TODO: Need clarification on this scenario
    @Test(priority = 3, groups = {SHOPPINGBAG, COUPONS, REGISTEREDONLY})
    public void applyCouponAndAssociateAndVerifyError(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("as a registered user verify that when the bag has products in it and the user applies Multi use coupon, then the associate discount is applied from the my account page the error message should be thrown for the multi use coupon");

        String searchTerm = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchTerm, qty);
        String associateID = getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ValidAssociateID", "Associate ID");
        String merchCoupon = null;
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validAddressTax");
        if (store.equalsIgnoreCase("US")) {
            merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "Flat5Off", "couponCode");
        } else if (store.equalsIgnoreCase("CA")) {
            merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "coupon5CodeCA");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        }
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.clickProfileInfoLink();
        if(store.equalsIgnoreCase("US")){
        myAccountPageActions.clickOnPersonalInfoEditButton();}
        else{
            myAccountPageActions.clickOnPersonalInfoEditButtonCA();
        }
        myAccountPageActions.removeAssociateID();

        addToBagBySearching(searchKeywordAndQty);
 //       shoppingBagPageActions.updateQty("5");

        shoppingBagPageActions.clickShowMoreAndVerifyAvailCoupons();
        AssertFailAndContinue(shoppingBagPageActions.clickMerchandiseApplyCoupon("$1", merchCoupon), "Merchandise $5 coupon has been applied");
        AssertFailAndContinue(shoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Estimated total after applying associate, loyalty coupons and free shipping coupons");

        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.clickProfileInfoLink();
        myAccountPageActions.clickOnPersonalInfoEditButton();
        myAccountPageActions.addAssociateID(associateID,shipDetailUS.get("addressLine1"),shipDetailUS.get("city"), shipDetailUS.get("stateShortName"));
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.checkCouponAfterAddingAssociateDis(merchCoupon);
    }

    @Parameters(storeXml)
    @Test(priority = 0, groups = {SHOPPINGBAG, COUPONS, USONLY, REGISTEREDONLY})
    public void removeLTYCouponAndVerify_OLThroughCO(@Optional("US") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Remove the coupon and verify if the applied promotions is removed from the order in Order ledger.Also ensure that the Order ledger does not change through the checkout pages and after placing the order in the order confirmation page.");
        String searchTerm = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchTerm, qty);
        Map<String, String> vi = dt2ExcelReader.getExcelData("BillingDetails", "Visa");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.removeDefaultCreditCard();


        if (Integer.parseInt(headerMenuActions.getQtyInBag()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
            shoppingBagPageActions.updateQty("5");
        } else {
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        }
        double couponValue = shoppingBagPageActions.getCouponValue();
        AssertFailAndContinue(shoppingBagPageActions.applyLTYPointsToOrder(), "Applied the Loyalty coupon");
        AssertFailAndContinue(shoppingBagPageActions.getCouponDiscount() == couponValue, "The coupon amount displays at order ledger");
        shoppingBagPageActions.removeAppliedCoupons();
        shoppingBagPageActions.staticWait(3000);
        AssertFailAndContinue(shoppingBagPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger after removing");

        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        if (reviewPageActions.isDisplayed(reviewPageActions.submOrderButton)) {
            reviewPageActions.clickReturnToBillingLink(billingPageActions);
            billingPageActions.returnToBagPage(shoppingBagPageActions);
        }
        AssertFailAndContinue(shippingPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at shipping page");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at billing page");

        String cvv = billingPageActions.getCVVByPaymentType();
        if (cvv.equalsIgnoreCase("")) {
            billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit"));
        } else {
            billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, cvv);
        }
        AssertFailAndContinue(reviewPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at review page");

        reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
        AssertFailAndContinue(orderConfirmationPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at order confirmation page");
    }

    @Parameters(storeXml)
    @Test(priority = 2, groups = {SHOPPINGBAG, COUPONS, REGISTEREDONLY,PROD_REGRESSION})
    public void applyCouponAtEC_RemoveAtSB_OLAtCO(@Optional("US") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("saved Shipping and Billing information in MyAccount,add products to the bag and initiate checkout.In the Express checkout review page,apply Place Cash/Merchandise/Loyalty coupon.Return to the shopping bag page and remove the coupon.Verify if the applied promotions is removed from the order in Order ledger." +
                "Also ensure that the Order ledger does not change through the checkout pages and after placing the order in the order confirmation page.");
        String searchTerm = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchTerm, qty);
        Map<String, String> visaBillUS = null;

        String merchCoupon = null;
        if (store.equalsIgnoreCase("US")) {
            merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "Flat5Off", "couponCode");
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
            if(env.equalsIgnoreCase("prod")){
                merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
                visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

            }
        } else if (store.equalsIgnoreCase("CA")) {
            merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "coupon5CodeCA");
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA");
            if(env.equalsIgnoreCase("prod")){
                merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
                visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

            }

        }
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.clickPaymentAndGCLink();
        if (!myAccountPageActions.isDisplayed(myAccountPageActions.defaultPaymentMethodLbl)) {
            AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                    visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent")), "Adding new Credit card details without removing existing details.");
        }
        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.updateQty("5");

        shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions);

        //At review page
        reviewPageActions.clickShowMoreAndVerifyAvailCoupons();
//            shoppingBagPageActions.scrollToTheTopHeader(shoppingBagPageActions.couponCodeFld);
        AssertFailAndContinue(reviewPageActions.clickMerchandiseApplyCoupon("$1", merchCoupon), "Merchandise $5 coupon has been applied");
        AssertFailAndContinue(reviewPageActions.estimatedTotalAfterAppliedCoupon(), "Estimated total after applying associate, loyalty coupons and free shipping coupons");

        reviewPageActions.returnToBagPage(shoppingBagPageActions);

        shoppingBagPageActions.removeAppliedCoupons();
        shoppingBagPageActions.staticWait(5000);
        AssertFailAndContinue(shoppingBagPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at shopping bag page");
        shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions);

        AssertFailAndContinue(reviewPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at Review page");
        reviewPageActions.enterExpressCO_CVVAndSubmitOrder(visaBillUS.get("securityCode"), orderConfirmationPageActions);
        AssertFailAndContinue(orderConfirmationPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at order confirmation page");

    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {SHOPPINGBAG, COUPONS})
    public void removeMerchCouponAndVerify_OLThroughCO(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store + " Remove the coupon and verify if the applied promotions is removed from the order in Order ledger.Also ensure that the Order ledger does not change through the checkout pages and after placing the order in the order confirmation page.");
        String searchTerm = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchTerm, qty);
        Map<String, String> vi = null;

        String merchCoupon = null;
        if (store.equalsIgnoreCase("US")) {
            merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "Flat5Off", "couponCode");
            vi = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            if(env.equalsIgnoreCase("prod")){
                merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
                vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

            }
        } else if (store.equalsIgnoreCase("CA")) {
            merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "coupon5CodeCA");
            vi = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            if(env.equalsIgnoreCase("prod")){
                merchCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
                vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

            }
        }

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.removeDefaultCreditCard();
        }

        if (Integer.parseInt(headerMenuActions.getQtyInBag()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
            shoppingBagPageActions.updateQty("5");
        } else {
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        }
        shoppingBagPageActions.clickShowMoreAndVerifyAvailCoupons();
        AssertFailAndContinue(shoppingBagPageActions.clickMerchandiseApplyCoupon("$1", merchCoupon), "Merchandise $5 coupon has been applied");
        AssertFailAndContinue(shoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Estimated total after applying associate, loyalty coupons and free shipping coupons");

        shoppingBagPageActions.scrollToTheTopHeader(shoppingBagPageActions.couponCodeFld);
        shoppingBagPageActions.removeAppliedCoupons();
        shoppingBagPageActions.staticWait(3000);
        AssertFailAndContinue(shoppingBagPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger after removing");
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        if (reviewPageActions.isDisplayed(reviewPageActions.submOrderButton)) {
            reviewPageActions.clickReturnToBillingLink(billingPageActions);
            billingPageActions.returnToBagPage(shoppingBagPageActions);
        }
        AssertFailAndContinue(shippingPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at shipping page");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at billing page");
        String cvv = billingPageActions.getCVVByPaymentType();
        if (cvv.equalsIgnoreCase("")) {
            billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit"));
        } else {
            billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, cvv);
        }
        AssertFailAndContinue(reviewPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at review page");

        reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
        AssertFailAndContinue(orderConfirmationPageActions.getCouponDiscount() == 0.00, "The applied coupon amount removed from order ledger at order confirmation page");
    }


}
