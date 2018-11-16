package tests.mobileDT;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;
import java.util.List;

//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class MobileOrderLedgerAndCoupon extends MobileBaseTest {
    WebDriver mobileDriver;
    String productName0 = "";
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email = UiBaseMobile.randomEmail();
    String password = "";
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {SHOPPINGBAG})
    public void verifyShippingMethodOnOrderLedger_SB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String loyaltyText = "";
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify on shopping bag that the Shipping cost line item is getting displayed in the Order Ledger " +
                "and the line item value is 'FREE' in case Free shipping method is selected");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email ,password);
        }

        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }

        //DT-37914
        if(store.equalsIgnoreCase("US")) {
            String estimatedTtl = mshoppingBagPageActions.getEstimateTotal();
            int value = getRoundOffValue(estimatedTtl);
            if(user.equalsIgnoreCase("guest")){
                loyaltyText = mshoppingBagPageActions.getLoyaltyEspotText("guest");
            } if(user.equalsIgnoreCase("registered")){
                loyaltyText = mshoppingBagPageActions.getLoyaltyEspotText("registered");
            }
            AddInfoStep("Text returned from loyalty points espot::" + loyaltyText);
            AssertFailAndContinue(loyaltyText.equalsIgnoreCase("You'll earn " + value + " points on this purchase!"), "Verify Loyalty Points eSpot displayed as You can earn <Order Total> points on this purchase!");
        }

        AddInfoStep("Shipping field value in order summary is ::" + mshoppingBagPageActions.getShippingDetailsFromOrderSummaryText());
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        //DT-38662
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        mbillingPageActions.clickReturnToShippingPage();
        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
        // DT-35132
        AddInfoStep("Shipping field value in order summary is after returning from Shipping page::" +mshoppingBagPageActions.getShippingDetailsFromOrderSummaryText());
        AssertFailAndContinue(mshoppingBagPageActions.getShippingDetailsFromOrderSummaryText().equalsIgnoreCase("Free"), "Verify whether the shipping field value in Order Ledger is displayed as Free on selecting Free shipping method from Shipping Page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, COUPONS})
    public void applyAndRemoveCouponOnShippingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String mCoupon = null;
        Map<String, String> mc = null;
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shopping bag that if the coupon applied on shipping page and also removed on same page, "
                + "the item price should remain across the checkout flow ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
            
        }

        //DT-43083, //DT-36439//DT-40420
        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty2");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

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
        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
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

        mbillingPageActions.clickOnShipping(mshippingPageActions);
        mshippingPageActions.applyCouponCode(mCoupon);
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.ApplyCouponInOrderSummery(), "Validate coupon has been applied");
        AssertFailAndContinue(mshippingPageActions.validateTotalAfterAppliedCoupon(), "Validate the Estimated cost after applied coupon");
        AssertFailAndContinue(mshippingPageActions.removeCoupon(),"Verify remove coupon on shipping page");
        AssertFailAndContinue(!mshippingPageActions.ApplyCouponInOrderSummery(), "Validate coupon has been removed");

        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(),"Verify click billing button");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Verify click on review button");
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(), "Verify Billing Address section in Review Page");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
            //DT-35918
            /*String orderNo = mreceiptThankYouPageActions.getOrderNum();
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("ORDERS");
            mmyAccountPageActions.click(mmyAccountPageActions.orderNo(orderNo));
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.orderStatusLabel).equalsIgnoreCase("cancelled"),"Verify order status for cancelled order");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.totalCost).equalsIgnoreCase("$0.00"),"Verify that order total for cancelled order is displayed as $0.00.");
*/
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {COUPONS})
    public void applyCouponOnShippingAndRemoveOnCart(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shipping bag that if the coupon applied on shopping page is, removed on shipping Page, "
                + "the item price should remain accross the checkout flow ");
        String mCoupon = "";
        Map<String, String> mc = null;
        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

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

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }

        AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(mCoupon), "Enter the valid coupon code and click on apply button at Shopping page");
        AssertFailAndContinue(mshoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Validate coupon has been appplied successfully");
        AddInfoStep("Estimated total in order summary is ::" + mshoppingBagPageActions.getEstimateTotal());

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        // mshippingPageActions.clickContinueOnAddressVerificationModal();
        mbillingPageActions.clickOnShipping(mshippingPageActions);
        mshippingPageActions.removeCoupon();
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.RemoveCoupanInOrderSummery(), "Validate coupon has been removed");
        String estimatedCost = mshippingPageActions.getEstimateTotal();
        mshippingPageActions.clickNextBillingButton();
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.validateTotInShippingAndBillingPage(estimatedCost), "Validate the cost is same  as shipping Page" + estimatedCost);
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Verify user redirected to review page");
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.validateTotInShippingAndBagPage(estimatedCost), "Validate the cost is same  as billing Page" + estimatedCost);
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Validate that the total is same on Order confirmation page");
        }
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {COUPONS})
    public void applyCouponRemoveOnBag(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shopping bag that if the coupon applied on Shopping page removed on cart, "
                + "the item price should remain accross the checkout flow ");
        String mCoupon = null;
        Map<String, String> mc = null;
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password"));
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }
        AssertFailAndContinue(true, "Verify the count of mini cart of adding items to bag:" + mheaderMenuActions.getQty());
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

        mshoppingBagPageActions.applyCouponCode(mCoupon);
        //DT-38017 //DT-38018
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.couponCodeApplied),"verify Applied coupon amount is displayed in Order summary");
        AssertFailAndContinue(mshoppingBagPageActions.validateTotalAfterAppliedCoupon(), "Validation of the Total Price after applying the coupon");
        AssertFailAndContinue(mshoppingBagPageActions.verifyCouponFieldDisplayedOnOrderLedger(), "Verify Coupon field is present on Order ledger after applying coupon in shopping Bag Page");
        mshoppingBagPageActions.removeCoupon();
        AssertFailAndContinue(mshoppingBagPageActions.RemoveCoupanInOrderSummery(), "Validate coupon has been removed");
        String estimatedCost = mshoppingBagPageActions.getEstimateTotal();

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        // mshippingPageActions.clickContinueOnAddressVerificationModal();
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.click(mbillingPageActions.nextReviewButton);
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.validateTotInShippingAndBagPage(estimatedCost), "Validate the cost is same  as billing Page" + estimatedCost);
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Validate that the total is same on Order confirmation page");
        }

    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {CHECKOUT,REGRESSION,PROD_REGRESSION})
    public void ValidateOrderLedger_IncreaseBagCount(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> mc = null;

        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify items count and estimated total in the order ledger section," +
                " when item count is increased in shopping bag");

        Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");


        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

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


        String estimatedTtl = mshoppingBagPageActions.getProdPrice();
        AddInfoStep("Estimated total displayed on Shopping bag::" +estimatedTtl);

        int quantity = Integer.parseInt(mheaderMenuActions.getQty());
        AddInfoStep("Quantity displayed on shopping bag::" +quantity);

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingDetails.get("fName"), caShippingDetails.get("lName"), caShippingDetails.get("addressLine1"), caShippingDetails.get("addressLine2"), caShippingDetails.get("city"), caShippingDetails.get("stateShortName"), caShippingDetails.get("zip"), caShippingDetails.get("phNum"), caShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        mbillingPageActions.enterCardDetails(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Verify redirection to review page");

        AssertFailAndContinue(mreviewPageActions.returnToBag(mshoppingBagPageActions),"Verify return to shopping bag");

        String searchKeyword_1 = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty_1 = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty_2 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword_1, qty_1);


        addToBagBySearching(searchKeywordAndQty_2);

        estimatedTtl = mshoppingBagPageActions.getProdPrice();
        AddInfoStep("Estimated total displayed on Shopping bag after adding one more product to bag::" +estimatedTtl);
        quantity = Integer.parseInt(mheaderMenuActions.getQty());
        AddInfoStep("Quantity displayed on Shopping bag after adding one more product to bag::" +quantity);
        //DT-38009
        AssertFailAndContinue(mshoppingBagPageActions.validateEstmtedTtlOnOrderLedger(estimatedTtl),"Validate Estimated total on Shopping page is updated after adding one more product to bag");
        AddInfoStep("Quantity returned from Shopping page UI::"+mshoppingBagPageActions.getText(mshippingPageActions.itemPriceTtlLineItem));
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshippingPageActions.itemPriceTtlLineItem).contains("Items ("+quantity+")"),"verify that the Estimated total is the sum of all the line items displayed on the Order Ledger");

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        //DT-34556
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.validateEstmtedTtlOnOrderLedger(estimatedTtl),"Validate Estimated total on Shipping page is updated after adding one more product to bag");
        AddInfoStep("Quantity returned from Shipping page UI::"+mshippingPageActions.getText(mshippingPageActions.itemPriceTtlLineItem));
        AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.itemPriceTtlLineItem).contains("Items ("+quantity+")"),"Validate Item count on order ledger of Shipping page is updated");

        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(),"Click billing button");

        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(mc.get("securityCode"));
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.validateEstmtedTtlOnOrderLedger(estimatedTtl),"Validate Estimated total on Shipping page is updated after adding one more product to bag");
        AddInfoStep("Quantity returned from Billing page UI::"+mbillingPageActions.getText(mbillingPageActions.itemPriceTtlLineItem));
        AssertFailAndContinue(mbillingPageActions.getText(mbillingPageActions.itemPriceTtlLineItem).contains("Items ("+quantity+")"),"Validate Item count on order ledger of Billing page is updated");

        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Click Review button");

        if (!mreviewPageActions.isDisplayed(mreviewPageActions.expandToggleBtn)) {
            mreviewPageActions.expandOrderSummary();
        }
        AssertFailAndContinue(mreviewPageActions.validateEstmtedTtlOnOrderLedger(estimatedTtl),"Validate Estimated total on Shipping page is updated after adding one more product to bag");
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.itemPriceTtlLineItem).contains("Items ("+quantity+")"),"Validate Item count on order ledger of Review page is updated");

        //DT-38011
        AssertFailAndContinue(mreviewPageActions.returnToBag(mshoppingBagPageActions),"Verify return to shopping bag");

        String searchKeyword_2 = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty_2 = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty_1 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword_2, qty_2);

        addToBagBySearching(searchKeywordAndQty_1);

        int updated_quantity = Integer.parseInt(mheaderMenuActions.getQty());
        AddInfoStep("Quantity displayed on shopping bag::" +updated_quantity);
        AssertFailAndContinue(updated_quantity > quantity,"Verify bag count is increased after adding one more product to bag");

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingDetails.get("fName"), caShippingDetails.get("lName"), caShippingDetails.get("addressLine1"), caShippingDetails.get("addressLine2"), caShippingDetails.get("city"), caShippingDetails.get("stateShortName"), caShippingDetails.get("zip"), caShippingDetails.get("phNum"), caShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(mc.get("securityCode"));
        AssertFailAndContinue(mreviewPageActions.returnToBag(mshoppingBagPageActions),"Verify return to shopping bag");
        String estimatedTtl_updated = mshoppingBagPageActions.getProdPrice();
        AssertFailAndContinue(Double.parseDouble(estimatedTtl_updated) > Double.parseDouble(estimatedTtl),"Verify order total is updated accordingly in the Order Ledger on Shopping bag");

    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void verifyOrderLedgerUpdate_MovedToWL(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> loginDetails = null;
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "\n Verify if the user moves products from shoppingbag to favorites then estimated total and item cost should be decreased");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if(Integer.parseInt(mheaderMenuActions.getQty()) > 0){
            emptyShoppingBag();
        }

        addToBagFromPDP(searchKeywordAndQty);

        searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber1", "Value");
        qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber1", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Verify user redirected to shopping bag");
        AddInfoStep("Two products have been added to bag");

        String productName1InBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag: " + productName1InBag);
        String productName2InBag = mshoppingBagPageActions.getProductNames().get(1);
        AddInfoStep("Product name added in bag: " + productName2InBag);

        Float totalItemCostInOrderLedger = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        Float estimatedTotalOnOrderLedger = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        int quantity = mshoppingBagPageActions.getItemsCountInOL();
        AssertFailAndContinue(quantity == 2, "Verify item count in order ledger is 2");

        mshoppingBagPageActions.clickWLIconAsGuestUser(mloginPageActions);

        mloginPageActions.clickCreateAcctFromCheckoutSB(mcreateAccountActions);

        if (store.equalsIgnoreCase("US")) {
            loginDetails = dt2ExcelReader.getExcelData("CreateAccount", rowInExcelUS);
        }
        if (store.equalsIgnoreCase("CA")) {
            loginDetails = dt2ExcelReader.getExcelData("CreateAccount", rowInExcelCA);
        }
        AssertFailAndContinue(mcreateAccountActions.createAnAccountFromSB(loginDetails.get("FirstName"), loginDetails.get("LastName"), mcreateAccountActions.randomEmail(), loginDetails.get("Password"), loginDetails.get("ZipCode"), loginDetails.get("PhoneNumber"), mshoppingBagPageActions), "Created account");

        Float totalItemCostInOrderLedger_updated = Float.parseFloat(mshoppingBagPageActions.getItemsTotalAmount());
        Float estimatedTotalOnOrderLedger_updated = Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        //DT-38015
        AssertFailAndContinue(totalItemCostInOrderLedger_updated < totalItemCostInOrderLedger,"Verify item cost is decreased accordingly");
        AssertFailAndContinue(estimatedTotalOnOrderLedger_updated < estimatedTotalOnOrderLedger,"Verify estimated total is decreased accordingly");
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == (quantity-1) , "Verify item count in order ledger is decreased");

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
