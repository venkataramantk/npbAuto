package tests.mobileDT.myAccount.orders;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

/**
 * Created by JKotha on 15/12/2017.
 */

//@Test(singleThreaded = true)
public class OnlineOrders extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = UiBaseMobile.randomEmail();
    String password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
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

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, EAGLE,GUESTONLY})
    public void onlineOrder_guest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " Store. Verify \n" +
                "1. User is able to place order" +
                "\n2. First Month and First year value is displayed in billing page" +
                "\n3. Spinner is displayed while submitting order \n" +
                "4. Verify Site Wide Banner page is not displayed in Shipping, Billing and Review Page" +
                "5. Validate CVV field re-design" +
                "DT-31586, DT-31588, DT-40760, DT-43826, DT-20583, DT-44313");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        int initial = Integer.parseInt(mheaderMenuActions.getCookieValue("cartItemsCount"));

        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(Integer.parseInt(mheaderMenuActions.getCookieValue("cartItemsCount")) == initial + 1, "Added an item to bag and verify cartItem Cookie value is increased by one");
        //AssertFailAndContinue(mshoppingBagPageActions.calculatePoints(), "Calculate points");

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
            if (store.equalsIgnoreCase("CA"))
                AssertFailAndContinue(mshippingPageActions.isSelected(mshippingPageActions.signupEmail_ChkBox, mshippingPageActions.signupEmail_ChkInput), "Verify Marketing emails checkbox is selected by default");
        }

        AssertFailAndContinue(!mheaderMenuActions.verifySiteWideBanner(), "verify site wide banner is not displayed in Shipping page");

        AssertFailAndContinue(mshippingPageActions.validateAccordiansInShippingPage("ecomm"), "validate Progress bar accordians in shipping page for ecomm order");
        //shipping details
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest("Alert", shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest("Alert", shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        AssertFailAndContinue(!mheaderMenuActions.verifySiteWideBanner(), "verify site wide banner is not displayed in Billing page");
        AssertFailAndContinue(mbillingPageActions.validateCVVErrorMessage(), "Verify error message is displayed for CVV by default with red color");

        AssertFailAndContinue(mbillingPageActions.validateAccordiansInBillingPage("ecomm"), "validate Progress bar accordians in Billing page for ecomm order");


        AssertFailAndContinue(mbillingPageActions.selectFirstMonthAndYearAndVerify(), "Select First Month and First year . Verify Select options are displayed from dropdowns");
        mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

        //get order details in review page
        AssertFailAndContinue(mbillingPageActions.isSelected(mbillingPageActions.sameAsShippingChkBox, mbillingPageActions.sameAsShippingChkBoxinput), "Verify \"Same as shipping box is selected by default in billing page\" ");
        mbillingPageActions.clickNextReviewButton();

        AssertFailAndContinue(!mheaderMenuActions.verifySiteWideBanner(), "verify site wide banner is not displayed in review page page");

        AssertFailAndContinue(mreviewPageActions.validateAccordiansInReviewPage("ecomm"), "validate Progress bar accordians in Review page for ecomm order");


        mreviewPageActions.click(mreviewPageActions.myBagLink);
        String productName = mreviewPageActions.getText(mreviewPageActions.deptName);
        String productColor = mreviewPageActions.getText(mreviewPageActions.productColor);
        String productSize = mreviewPageActions.getText(mreviewPageActions.productSize);
        String productQty = mreviewPageActions.getText(mreviewPageActions.productQty).split(" ")[1].trim();

        // Billing section is displayed is this bug
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(), "Verify Billing Address section in Review Page");
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Submit order and verify order confimration page");
        AssertFailAndContinue(mreceiptThankYouPageActions.verifyOrderModifyCancelNotification(getmobileDT2CellValueBySheetRowAndColumn("OrderStatus", "OrderModifyCancelNotifaction", "value")), "Verify order modify and cancel notification on the order confirmation page");
        //DT-5654
        AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mreceiptThankYouPageActions.clickMPREspot(mplccActions), "Click on MPR eSpot in order confirmtion page and verify MPR landing page is displayed");
        }

        //DT-5652
        AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
        if (user.equalsIgnoreCase("guest")) {
            String orderNo = mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.orderNumber).trim();
            String orderDate = mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.orderDate).trim();

            String itemsCost = mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.itemsCost).trim();
            String total = mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.totalCost).trim();
            AssertFailAndContinue(mfooterActions.trackOrderFromFooter(email, orderNo), "Verify that loading spinner is getting displayed on Order Details page, when an user tracks an ECOM order from the footer");
            AssertFailAndContinue(mmyAccountPageActions.verifyOrderDetailsFieldsEcomm(), "Verify Order Details for online order");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.orderIdvalue).equals(orderNo), "Verify Order #  " + orderNo + " is displayed in order details page");
            AssertFailAndContinue(orderDate.contains(mmyAccountPageActions.getText(mmyAccountPageActions.orderDateVal).split("at")[0].trim()), "Verify Order Date " + orderDate + " is displayed in order details page");

            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.cardInfo).contains(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardType")), "Verify Billing Details contains card Name");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.cardInfo).contains(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "Last4")), "Verify Billing Details contains contains card last four digits");

            mmyAccountPageActions.click(mmyAccountPageActions.itemsToggle);
            AssertFailAndContinue(mobileDriver.getCurrentUrl().contains(orderNo), "Order id is displayed in URL");
            AssertFailAndContinue(!mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.ordersLnk, 5), "Verify OnlineOrders bread crumb is not displayed");

            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.shippingAddressName).contains(shipDetails.get("fName")), "validate shipping address name");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.billingAddressName).contains(shipDetails.get("fName")), "validate billing address name");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtName).equalsIgnoreCase(productName), "Verify Product Name");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.productDesc).contains("UPC"), "Verify Product UPC");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtColor).equalsIgnoreCase(productColor), "Verify Product Color");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtsize).equalsIgnoreCase(productSize), "Verify Product Size");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtQty).split(":")[1].trim().equalsIgnoreCase(productQty), "Verify Product Qty");

            //AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.totalItemsCost).equals(itemsCost), "Verify Product Original Cost");
        }
    }


    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, JAGUAR})
    public void changeStore_pdp(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " Store. Verify \n" +
                "change store frm pdp and verify checkout is successful");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (store.equalsIgnoreCase("US")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("US", "English");
        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }

        //shipping details
        if (store.equalsIgnoreCase("CA")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("US")) {
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        AssertFailAndContinue(!mheaderMenuActions.verifySiteWideBanner(), "verify site wide banner is not displayed in Billing page");
        AssertFailAndContinue(mbillingPageActions.validateCVVErrorMessage(), "Verify error message is displayed for CVV by default with red color");

        AssertFailAndContinue(mbillingPageActions.selectFirstMonthAndYearAndVerify(), "Select First Month and First year . Verify Select options are displayed from dropdowns");
        mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

        //get order details in review page
        AssertFailAndContinue(mbillingPageActions.isSelected(mbillingPageActions.sameAsShippingChkBox, mbillingPageActions.sameAsShippingChkBoxinput), "Verify \"Same as shipping box is selected by default in billing page\" ");
        mbillingPageActions.clickNextReviewButton();

        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify user is able to place order");
    }


    @Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT})
    public void verifySameOrderTtlInOrderConfAndOrderDetails(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify decimal value in order total of order confirmation page is same as order details page");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> usBillingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        Map<String, String> PaymentDetails = dt2MobileExcel.getExcelData("PaymentDetails", "billingDetails");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        addToBagFromPDP(searchKeywordAndQty);
        mheaderMenuActions.clickShoppingBagIcon();

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
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        mbillingPageActions.enterCardDetails(PaymentDetails.get("cardNumber"), PaymentDetails.get("securityCode"), PaymentDetails.get("expMonthValue"));
        mbillingPageActions.clickNextReviewButton();

        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.clickSubmOrderButton();
            //Till here order has been submitted.

            //now note the order number
            String orderNumber = mreceiptThankYouPageActions.getOrderNum();
            String orderTotalPrice = mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.totalCost);

            //Now navigate to order details page
            mmyAccountPageActions.click(mmyAccountPageActions.orderNo(orderNumber));
            AssertFailAndContinue(mmyAccountPageActions.verifySpinnerWhileLoadingOrderSetails(), "Verify spinner when clicked on Order no in Order Confirmation page");
            String orderTotalInOderDetails = mmyAccountPageActions.getText(mmyAccountPageActions.totalCost);
            //DT-36502
            AssertFailAndContinue(orderTotalPrice.equals(orderTotalInOderDetails), "Verify same order total is displayed in order confirmation page and order details page.");
            mmyAccountPageActions.clickOrderListContainer();
            //DT-36503
            mmyAccountPageActions.isDisplayed(mmyAccountPageActions.itemOriginalPriceNotStrikedOut);
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT},enabled = false)
    public void checkoutFrmAddtoBagNotification(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        Map<String, String> usBillingAdd = null;
        Map<String, String> caShippingAdd = null;
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to initiate checkout from Add to Bag Notification, "
                + "coupon shouldn't be applied in OrderLedger  if no coupon is applied" +
                "DT-37350");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, searchKeyword);
        AssertFailAndContinue(addToBagFromPDPPage(qty) != null,"Verify product added to bag from PDP page");

        //addToBagBySearching(searchKeywordAndQty);
        // DT-35892
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mheaderMenuActions.clickCheckoutFrmNotificationAsGuest(mshippingPageActions, mshoppingBagPageActions), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mheaderMenuActions.clickCheckoutFrmNotificationAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        if (store.equalsIgnoreCase("US")) {
            usBillingAdd = dt2MobileExcel.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                usBillingAdd = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            caShippingAdd = dt2MobileExcel.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                caShippingAdd = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        //DT-34560
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(!mshippingPageActions.isDisplayed(mshippingPageActions.couponCodeApplied), "Verify if the Coupon field is not displayed in Order Summary");
        //DT-35891
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

     //   mshippingPageActions.clickContinueOnAddressVerificationModal();
        mbillingPageActions.enterCardDetails(usBillingAdd.get("cardNumber"), usBillingAdd.get("securityCode"), usBillingAdd.get("expMonthValue"));
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.couponCodeApplied), "Verify if the Coupon field is not displayed in Order Summary");
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.couponCodeApplied), "Verify if the Coupon field is not displayed in Order Summary");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
        }

    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {CHECKOUT},enabled = false)
    public void validatePromotionChkOutFrmAtbNotification(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        Map<String, String> usBillingAdd = null;
        Map<String, String> caShippingAdd = null;
        Map<String, String> mailingDetails = null;
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to see promotion details in order ledger with negative sign on shipping/billing/review and order confirmation page when checkout is performed from add to bag notification");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }


        Map<String, String> associateDetails = dt2MobileExcel.getExcelData("MyAccount", "ValidAssociateID");

        if(store.equalsIgnoreCase("US")){
            mailingDetails = dt2MobileExcel.getExcelData("MyAccount", "USAddress");
        } if(store.equalsIgnoreCase("CA")){
            mailingDetails = dt2MobileExcel.getExcelData("MyAccount", "CAAddress");
        }
        makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"),mailingDetails.get("AddressLine1"),mailingDetails.get("City"),mailingDetails.get("stateShortName"));

        //makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"));

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);


        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, searchKeyword);
        // msearchResultsPageActions.clickOnProductImageByPosition(1);
        AssertFailAndContinue(addToBagFromPDPPage(qty) != null,"Verify product added to bag from PDP page");

        //addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mheaderMenuActions.clickCheckoutFrmNotificationAsGuest(mshippingPageActions, mshoppingBagPageActions), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mheaderMenuActions.clickCheckoutFrmNotificationAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        if (store.equalsIgnoreCase("US")) {
            usBillingAdd = dt2MobileExcel.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                usBillingAdd = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            caShippingAdd = dt2MobileExcel.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                caShippingAdd = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in shipping page");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        mbillingPageActions.enterCardDetails(usBillingAdd.get("cardNumber"), usBillingAdd.get("securityCode"), usBillingAdd.get("expMonthValueUnit"));
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.getText(mbillingPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in billing page");
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in review page");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in confirmation page");
        }
    }


    @Test(priority = 5, dataProvider = dataProviderName, groups = {CHECKOUT, GIFTCARD, RECAPTCHA})
    public void placeOrderWith_GCAndCC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("AS a " + user + " user in " + store + " store, Placing the order as registered user with giftcard + Credit Card as a payment method");
        //DT-35857
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> usBillingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        Map<String, String> PaymentDetails = dt2MobileExcel.getExcelData("PaymentDetails", "billingDetails");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm5QTY", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm5QTY", "Qty");
        Map<String, String> usGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc3");
        Map<String, String> caGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc4");


        if (env.equalsIgnoreCase("prod"))
            usGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc1_prod");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        AddInfoStep("Enter and apply gift card");
        if (store.equalsIgnoreCase("US"))
            mbillingPageActions.enterGiftCardDetails(usGiftCard.get("Card"), usGiftCard.get("Pin"));
        if (store.equalsIgnoreCase("CA"))
            mbillingPageActions.enterGiftCardDetails(caGiftCard.get("Card"), caGiftCard.get("Pin"));

        mbillingPageActions.expandOrderSummary();
        mbillingPageActions.enterCardDetails(PaymentDetails.get("cardNumber"), PaymentDetails.get("securityCode"), PaymentDetails.get("expMonthValue"));
        mbillingPageActions.clickNextReviewButton();
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
        getAndVerifyOrderNumber("placeOrderWith_GC+CreditCard_AsPayment");

    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {CHECKOUT, RECAPTCHA, GIFTCARD})
    public void verifyEcomOrderUsingGC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify that user is able to place Ecomm order using gift card as payment method");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> usBillingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        Map<String, String> usGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc1");
        if (env.equalsIgnoreCase("prod")) {
            usGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc1_prod");
        }
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }

        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        //DT-35854
        if (store.equalsIgnoreCase("US"))
            mbillingPageActions.enterGiftCardDetails(usGiftCard.get("Card"), usGiftCard.get("Pin"));

        AddInfoStep("Enter and apply gift card");
        mreviewPageActions.click(mbillingPageActions.orderSummaryToggleBtn);
        Float price = 0.00f;

        AssertFailAndContinue(mbillingPageActions.verifyEstmdTotalOnOrderLedger(price), "Verify that Estimated balance is displayed as $0.00 on the Order Ledger as gift card with greater amount has been applied");
        ;

        mbillingPageActions.clickNextReviewButton();

        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Placed order with gift card as payment");
            AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Verify Ecomm Order is placed with gift card as payment method");
        }
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {REGRESSION,REGISTEREDONLY})
    public void validateTAndCRedirection(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if user is redirected " +
                "to the terms and conditions section of the help center page, " +
                "when the user is in order review page, Clicks on the terms and conditions" +
                " link below the submit order button ");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> PaymentDetails = dt2MobileExcel.getExcelData("PaymentDetails", "billingDetails");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        makeExpressCheckoutUser(store,env);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if(mheaderMenuActions.getBagCount() > 0){
            addToBagBySearching(searchKeywordAndQty);
        }else{
            mheaderMenuActions.clickShoppingBagIcon();
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress( mreviewPageActions), "Verify successful checkout with Registered user");
        }

        AssertFailAndContinue(mreviewPageActions.applyCouponCode("AUTO10OFF"), "Apply a coupon in review page");

        String currentWindow = driver.getWindowHandle();
        mreviewPageActions.clickOnTermsAndConditionPage();
        switchToWindow(currentWindow);
        //DT-36714
        AssertFailAndContinue(mreviewPageActions.termsAndConditionValidateURL(mreviewPageActions.getCurrentURL()), "Verify URL of terms and condition browser window");
        AssertFailAndContinue(mreviewPageActions.verifyHelpCentreTextOnTermsAndConditionPageIsDisplayed().toUpperCase().equalsIgnoreCase("HELP CENTER"), "Verify HELP CENTRE text displayed on terms and condition page");
        mreviewPageActions.switchToParent();

        mreviewPageActions.clickOnBillingAccordion(mbillingPageActions);

        AssertFailAndContinue(mbillingPageActions.removeCoupon(), "remove coupon from billing page");

        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);

        mshoppingBagPageActions.applyCouponCode("AUTO10OFF");

        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);

        mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);

        AssertFailAndContinue(mshoppingBagPageActions.removeCoupon(), "Remove coupon from shipping page");
    }

    @Test(priority = 8, dataProvider = dataProviderName, groups = {CHECKOUT, EAGLE, REGISTEREDONLY})
    public void onlineOrderValidation_Reg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As Registered user in " + store + " Store with shipping and billing address saved. Verify\n" +
                "1. same as shipping checkbox is selected in billing page" +
                "DT-43784, DT-40749, DT-43826, DT-44319, DT-44320");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addNewShippingAddress(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("city"),
                    shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("country"), shipDetails.get("phNum"),panCakePageActions);
        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA1");
            mmyAccountPageActions.addNewShippingAddress(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("city"),
                    shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("country"), shipDetails.get("phNum"),panCakePageActions);
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        String expYear = mheaderMenuActions.getFutureYearWithCurrentDate("YYYY", 7);
        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"), expYear);

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Verify Review page");
        AssertFailAndContinue(mreviewPageActions.validateCVVErrorMessage(), "Verify error message is displayed for CVV by default with red color in review page for Express checkout");

        mreviewPageActions.click(mreviewPageActions.myBagLink);
        String productName = mreviewPageActions.getText(mreviewPageActions.deptName);
        String productColor = mreviewPageActions.getText(mreviewPageActions.productColor);
        String productSize = mreviewPageActions.getText(mreviewPageActions.productSize);
        String productQty = mreviewPageActions.getText(mreviewPageActions.productQty).split(" ")[1].trim();

        //DT-5642
        mreviewPageActions.enterExpressCO_CVVAndSubmitOrder(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"));
        AssertFailAndContinue(mreceiptThankYouPageActions.verifyOrderModifyCancelNotification(getmobileDT2CellValueBySheetRowAndColumn("OrderStatus", "OrderModifyCancelNotifaction", "value")), "Verify order modify and cancel notification on the order confirmation page");

        //DT-5654
        AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        String orderNo = mreceiptThankYouPageActions.orderNumber.getText().trim();
        String orderDate = mreceiptThankYouPageActions.orderDate.getText().trim();
        String orderTotalPrice = mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.totalCost);

        AssertFailAndContinue(!mreceiptThankYouPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.createAccountButton, 2), "Verify create account is not displayed in order confirmation page");

        mreceiptThankYouPageActions.click(mreceiptThankYouPageActions.orderNumber);
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.orderDetailsPage), "Clicked on order number in Order conformation page verify order details page is displayed");

        panCakePageActions.navigateToMenu("VIEW");
        AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(panCakePageActions.orderNumber, 10), "Click User name in the pan cake verify order number is displayed");
        panCakePageActions.click(panCakePageActions.orderNumber);
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.orderDetailsPage), "Click Order number Verify Orders details page is displayed");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ORDERS");
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.ordersPage), "Verify OnlineOrders page is displayed after clicked on My Account -> OnlineOrders");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.getOrderType(orderNo)).equals("Online"), "Verify order type as Online");
        AssertFailAndContinue(orderDate.contains(mmyAccountPageActions.getText((mmyAccountPageActions.getOrderDate(orderNo)))), "Verify order date format");

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.canadaOrders), "Verify Show Canadian orders");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.canadaOrders).contains("US"), "Verify Show US orders is displayed");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.canadaOrders), "Verify Show US orders");
            AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.canadaOrders).contains("Canadian"), "Verify Show CA orders is displayed");
        }

        AssertFailAndContinue(mmyAccountPageActions.validateInternationOrdersUi(), "Verify International orders");
        mmyAccountPageActions.switchToParent();

        mmyAccountPageActions.click(mmyAccountPageActions.orderNo(orderNo));
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.orderDetailsPage), "Verify clicking on order number navigating to order details page of that order");

        String orderTotalInOderDetails = mmyAccountPageActions.getText(mmyAccountPageActions.totalCost);
        AssertFailAndContinue(orderTotalPrice.equals(orderTotalInOderDetails), "Verify same order total is displayed in order confirmation page and order details page.");

        AssertFailAndContinue(mmyAccountPageActions.verifyOrderDetailsFieldsEcomm(), "Verify Order Details for online order");

        /*AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.ordertext), "Verify Order # is displayed in order details page");
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.orderDateSec), "Verify Order Date is displayed in order details page");
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.orderShippingsec), "Verify Order Shipping is displayed in order details page");
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.orderBillingsec), "Verify Order Billing is displayed in order details page");*/

        AssertFailAndContinue(mobileDriver.getCurrentUrl().contains(orderNo), "Order id is displayed in URL");

        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.cardInfo).contains(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardType")), "Verify Billing Details contains card Name");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.cardInfo).contains(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "Last4")), "Verify Billing Details contains contains card last four digits");

        mmyAccountPageActions.javaScriptClick(mobileDriver, mmyAccountPageActions.itemsToggle);

        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtName).equalsIgnoreCase(productName), "Verify Product Name");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.productDesc).contains("UPC"), "Verify Product UPC");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtColor).equalsIgnoreCase(productColor), "Verify Product Color");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtsize).equalsIgnoreCase(productSize), "Verify Product Size");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pdtQty).split(":")[1].trim().equalsIgnoreCase(productQty), "Verify Product Qty");

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




}

