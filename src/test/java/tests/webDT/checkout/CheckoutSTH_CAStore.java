package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

/**
 * Created by skonda on 4/25/2017.
 */
public class CheckoutSTH_CAStore extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountCA";
    String emailAddressReg;
    private String password;
    String env;

    @Parameters("store")
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if(store.equalsIgnoreCase("US")){
            driver.get(EnvironmentConfig.getApplicationUrl());
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        }
        if(store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
       }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.deleteAllCookies();//to delete privacy policy cookie
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();//to delete privacy policy cookie
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }
    @AfterMethod
    public void clearCookies(){
        // driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT, GIFTCARD, SMOKE,PROD_REGRESSION})
    public void buyGiftCardWithCC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");

        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, verify - <br/>" +
                "1. User is able to place order while having Gift Card Product in the Bag, using GC as payment method");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "rush");
        Map<String, String> es = null;

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer("gcwithpaypal@yopmail.com", "Place@123");
        }

        footerActions.clickOnGiftCardsLink(giftCardsPageActions);
        giftCardsPageActions.clickSendAGiftCardsButton(productDetailsPageActions);
        productDetailsPageActions.clickAddToBag();
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase( "CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
           // getAndVerifyOrderNumber("buyGiftCardWithGC");
            String orderNo = orderConfirmationPageActions.getText(orderConfirmationPageActions.sthOrderID);
            AssertFailAndContinue(orderConfirmationPageActions.checkLoadingIconByClickingOrderId(), "Click on ship to home product  order ID and check user navigate to order details page");
            AssertFailAndContinue(orderStatusActions.validateOrderNumber(orderNo), "Verify the order ID is getting displayed in both the pages");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {CHECKOUT, GIFTCARD, REGISTEREDONLY, SMOKE, PRODUCTION})
    public void checkout_ExpressShipping_Registered(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Placing normal checkout order as a Registered in CA Store with Express shipping.");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchCA", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchCA", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> mcBillDetails = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        if(env.equalsIgnoreCase("prod")){
            mcBillDetails = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
        }
        Map<String, String> expressShip = excelReaderDT2.getExcelData("ShippingMethodCodes", "express_CA");
        Map<String, String> editBillUS = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressCA");
        if(env.equalsIgnoreCase("prod")){
            editBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");
        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.removeExistingAddress();
        myAccountPageActions.verifyDefaultCreditCard();
        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(headerMenuActions.navigateToCheckoutAsRegUser(shoppingBagDrawerActions, shippingPageActions), "Navigated to checkout page as registered user from shopping bag drawer");

        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinueByPos_Reg(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), expressShip.get("shipMethodPos")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, mcBillDetails.get("cardNumber"),
                mcBillDetails.get("securityCode"), mcBillDetails.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address as shipping address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickEditBillingAddress(billingPageActions), "Verify that the user is able to click the edit link near the billing details in Express checkout Review page");
        AssertFailAndContinue(billingPageActions.editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBillUS.get("fName"), editBillUS.get("lName"), editBillUS.get("addressLine1"), editBillUS.get("addressLine2"), editBillUS.get("city"), editBillUS.get("stateShortName"), editBillUS.get("zip"),
                editBillUS.get("cardNumber"), editBillUS.get("securityCode"), editBillUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        reviewPageActions.clickEditBillingLink(billingPageActions);
        AssertFailAndContinue(billingPageActions.addrDropDownDisplayOnSameAsShipUnSelect(), "Address drop down displaying when unselecting the same as ship check box");
        billingPageActions.selectAddressFromDropDown();
        billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, editBillUS.get("securityCode"));
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("checkout_ExpressShipping_Registered");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {CHECKOUT, PAYPAL, REGISTEREDONLY, SMOKE, PRODUCTION})
    public void checkout_ExpressShipping_RegisteredPayPal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Placing normal checkout with payPal order as a Registered in CA Store with Express shipping.");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchCA", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchCA", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> expressShip = excelReaderDT2.getExcelData("ShippingMethodCodes", "express_CA");
        Map<String, String> caPayPal = excelReaderDT2.getExcelData("Paypal", "CanadaPaypal");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.removeExistingAddress();
        myAccountPageActions.verifyDefaultCreditCard();
        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Navigated to checkout page as registered user from shopping bag Page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinueByPos_Reg(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"),
                shipDetailCA.get("phNum"), expressShip.get("shipMethodPos")), "Entered the Shipping address and clicked on the Next Billing button.");
        String parentWindow = driver.getWindowHandle();
        AssertFailAndContinue(billingPageActions.payWithPayPal(), "Continue payment with PayPal option");
//        billingPageActions.clickProceedWithPaypalModalButton(payPalPageActions);
        AssertFailAndContinue(payPalPageActions.paypalLogin(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, caPayPal.get("UserName"), caPayPal.get("Password"),parentWindow), "Enter the valid paypal credentials and click on the login");
        reviewPageActions.checkEmptyAirmilesNo();
        reviewPageActions.clickEditBillingLink(billingPageActions);
        billingPageActions.click(billingPageActions.proceedWithPaPalButton);
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("checkout_ExpressShipping_RegisteredPayPal");
            headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions, headerMenuActions);
            AssertFailAndContinue(footerActions.changeCountryByCountry("United States"), "Change Store from Canada to US");
        }
    }

}
