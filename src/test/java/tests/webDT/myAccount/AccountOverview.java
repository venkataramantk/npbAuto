package tests.webDT.myAccount;

import org.apache.regexp.RE;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.pages.actions.MyAccountPageDrawerActions;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 8/1/2017.
 */
public class AccountOverview extends BaseTest {
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;
    String env;

   @Parameters(storeXml)
   @BeforeClass(alwaysRun = true)
   public void initDriver(@Optional("US") String store) throws Exception {
       initializeDriver();
       driver = getDriver();
       initializePages(driver);
       env = EnvironmentConfig.getEnvironmentProfile();
       driver.get(EnvironmentConfig.getApplicationUrl());
       if(store.equalsIgnoreCase("US")) {
           driver.get(EnvironmentConfig.getApplicationUrl());
           emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
           password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

       }
       else if(store.equalsIgnoreCase("CA")){
           footerActions.changeCountryAndLanguage("CA","English");
           emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
           password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");

       }
       driver.manage().deleteAllCookies();
       headerMenuActions.deleteAllCookies();

   }
    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if(store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.addStateCookie("NJ");
        }
        else if(store.equalsIgnoreCase("CA")){
            footerActions.changeCountryAndLanguage("CA","English");
        }
       // headerMenuActions.deleteAllCookies();//to delete privacy policy cookie
    }
    @AfterMethod(alwaysRun = true)
    public void clearCookies(){
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT,REGISTEREDONLY,PROD_REGRESSION})
    public void validateAccountOverview(@Optional("US") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if register user can validate the account overview tab in my account page and validate the fields present in the account overview section");
        List<String> shipContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount","shippingText","TermsContent"));
        String text = getDT2TestingCellValueBySheetRowAndColumn("MyAccount","TooltipContent","TermsContent");
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        if(store.equalsIgnoreCase("US")) {
            if(!env.equalsIgnoreCase("prod") && !env.equalsIgnoreCase("prodstaging")) {
                AssertFailAndContinue(myAccountPageActions.validateAccOverview(), "Validate the buttons and links present in account overview page");
                AssertFailAndContinue(myAccountPageActions.clickViewAllRewardsLink(), "Clicked on view all rewards link");
                AssertFailAndContinue(myAccountPageActions.validateRewardsPage(), "Validate the rewards page");
                myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
                AssertFailAndContinue(myAccountPageActions.checkThePointsToolTipContent(text), "Verify the content displayed in tooltip");
            }
        }

        myAccountPageActions.validateContentNoDefaultAdd(shipContent.get(0),shipContent.get(1));
        AssertFailAndContinue(myAccountPageActions.clickAccOverviewLink(),"Click on account overview link");
        AssertFailAndContinue(myAccountPageActions.clickAddAddressBtn(), "Click on add address button and validate the fileds present in the address book");
        AssertFailAndContinue(myAccountPageActions.validateFieldsInAddress(), "Validate the fields present in the address book tab");
        AssertFailAndContinue(myAccountPageActions.clickAccOverviewLink(), "Click on account overview link");
        AssertFailAndContinue(myAccountPageActions.clickAddPayment(), "Click on add payment and check the user navigate to appropriate page");
        myAccountPageActions.click(myAccountPageActions.ordersLnk);
        myAccountPageActions.clickAccOverviewLink();
 //       AssertFailAndContinue(myAccountPageActions.mprEspotAccOverview(),"validate the mpr espot");


    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void validateMyRewardsPage(@Optional("US") String store) {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if the registered user can navigate to my rewards page and validate the links present in the my rewards page");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(myAccountPageActions.clickMyRewardsLink(), "Click on my rewards link");
            AssertFailAndContinue(myAccountPageActions.mprPointMeterUS(),"Verify whether the points bar is getting displayed in My Account page");
            AssertFailAndContinue(myAccountPageActions.validateHeaders(), "Validate the headers in my rewards page");
            myAccountPageActions.click(myAccountPageActions.myRewardsLink);
            myAccountPageActions.waitUntilElementDisplayed(myAccountPageActions.noRewardsText, 20);
            myAccountPageActions.click(myAccountPageActions.pointsHistoryLink);
            myAccountPageActions.waitUntilElementDisplayed(myAccountPageActions.emptyText, 20);
            myAccountPageActions.waitUntilElementDisplayed(myAccountPageActions.submitFormLink, 20);
            myAccountPageActions.click(myAccountPageActions.couponsLink);
            myAccountPageActions.click(myAccountPageActions.pointsHistoryLink);
            headerMenuActions.waitUntilElementDisplayed(myAccountPageActions.submitFormLink,3);
            myAccountPageActions.click(myAccountPageActions.submitFormLink);
            myAccountPageActions.click(myAccountPageActions.cancelButton_PointHistory);
            AssertFailAndContinue(myAccountPageActions.clickCoustomerService(myAccountPageActions, createAccountActions), "Verify the Coustomer service link redirection");
            headerMenuActions.clickOnTCPLogo();
            footerActions.changeCountryByCountry("CANADA");
            AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
            myAccountPageActions.click(myAccountPageActions.lnk_OffersAndCoupons);
            myAccountPageActions.staticWait(2000);
            AssertFailAndContinue(myAccountPageActions.mprPointMeterCA(),"Verify the Points meter is not displayed in CA store");
        }
        if (store.equalsIgnoreCase("CA")){
            myAccountPageActions.click(myAccountPageActions.lnk_OffersAndCoupons);
            myAccountPageActions.staticWait(2000);
            AssertFailAndContinue(myAccountPageActions.mprPointMeterCA(),"Verify the Points meter is not displayed in CA store");
        }
    }

    @Parameters(storeXml)
    @Test( groups = {MYACCOUNT, REGISTEREDONLY,PROD_REGRESSION})
    public void validateOverviewWithDefaultCardAndAddrs(@Optional("US") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if register user can validate the account overview tab in my account page and when user has default shipping address and payment method");

            Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
            Map<String, String> amexBillCA = excelReaderDT2.getExcelData("BillingDetails", "AmexCA");
            List<String> shippingAddress = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","enteredAddress","Details"));
            String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
            Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, "5");

            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
            AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
            if (store.equals("US")) {

                addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                        shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));
                addDefaultPaymentMethodCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                        visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent"));
            }
            if (store.equals("CA")) {

                addDefaultShipMethod(shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"),
                        shipDetailCA.get("country"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"));
                addDefaultPaymentMethodCC(amexBillCA.get("fName"), amexBillCA.get("lName"), amexBillCA.get("addressLine1"), amexBillCA.get("city"), amexBillCA.get("stateShortName"),
                        amexBillCA.get("zip"), amexBillCA.get("phNum"), amexBillCA.get("cardType"), amexBillCA.get("cardNumber"), amexBillCA.get("expMonthValueUnit"), amexBillCA.get("SuccessMessageContent"));

            }
        headerMenuActions.staticWait(2000);
        myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
        myAccountPageActions.verifyDefaultShippingAddressDisplay(shippingAddress.get(0),shippingAddress.get(1));
        addToBagBySearching(itemsAndQty);
        if(!env.equalsIgnoreCase("prod") || !env.equalsIgnoreCase("prodstaging")) {
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            AssertFailAndContinue(myAccountPageActions.applyRewardsAndCoupons(), "Verify that the user is able to apply coupon from My Account");
            //      myAccountPageActions.applyMPRCouponsMyAcc();
            //     myAccountPageActions.applyMPRCouponsMyAcc(); // since after applying $5, $10 is displayed in My Account
        }
    }
    }

