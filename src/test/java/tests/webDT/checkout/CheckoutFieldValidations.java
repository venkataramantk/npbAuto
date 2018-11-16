package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by Venkat on 3/27/2017.
 */
//DT-608
public class CheckoutFieldValidations extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg = null;
    private String password = null;

    @Parameters({"store", "users"})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());

        if (store.equalsIgnoreCase("US") && user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA") && user.equalsIgnoreCase("registered")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
        driver.navigate().refresh();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT, REGRESSION, PRODUCTION, INTUATSTG}, priority = 1)
    public void checkoutShippingValidationReg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user, is able to validate the appropriate error messages when invalid content is entered across the Shipping section fields during checkout.");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");

        List<String> expEmptyFldErrorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "emptyAllFieldsUS", "ErrorMessages"));
        String errMsgForFNameWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "fNameWithSplChar", "ErrorMessages");
        String errMsgForLNameWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "lNameWithSplChar", "ErrorMessages");
        String errMsgForAdrLine1WithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "addr1WithSplChar", "ErrorMessages");
        String errMsgForCityWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "cityWithSplChar", "ErrorMessages");
        String errMsgForPhoneWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "phoneWithSplChar", "ErrorMessages");
        String errMsgForZipWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "emptySingleZip", "ErrorMessages");
        String errMsgForEmailWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "emailWithSplChar", "ErrorMessages");

        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        addToBagFromFlip(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page as Register user");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page as Guest user");
        }
        shippingPageActions.validateEmptyCharacterErrorMessage("", expEmptyFldErrorMsg.get(0), expEmptyFldErrorMsg.get(1), expEmptyFldErrorMsg.get(2), expEmptyFldErrorMsg.get(3), expEmptyFldErrorMsg.get(4), expEmptyFldErrorMsg.get(5), expEmptyFldErrorMsg.get(6));
        AssertFailAndContinue(shippingPageActions.validateSpecialCharacterErrorMessage("!#556", errMsgForFNameWithSplChar, errMsgForLNameWithSplChar, errMsgForAdrLine1WithSplChar, errMsgForCityWithSplChar, errMsgForPhoneWithSplChar, errMsgForZipWithSplChar, errMsgForEmailWithSplChar), "Validate error message for special characters in all fields in Shipping page");
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT, REGRESSION, PRODUCTION, INTUATSTG}, priority = 2)
    public void checkoutBillingValidationReg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user, is able to validate the appropriate error messages when invalid content is entered across the Billing section fields during checkout in " + store + "Store");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");

        List<String> expEmptyFldErrorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "emptyAllFieldsUS", "ErrorMessages"));
        String errMsgForFNameWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "fNameWithSplChar", "ErrorMessages");
        String errMsgForLNameWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "lNameWithSplChar", "ErrorMessages");
        String zip = null;
        String errMsgForAdrLine1WithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "addr1WithSplChar", "ErrorMessages");
        String errMsgForCityWithSplChar = getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "cityWithSplChar", "ErrorMessages");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");

        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        addToBagFromFlip(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page as Register user");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page as Guest user");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
            zip = shipDetailUS.get("zip");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
            zip = shipDetailCA.get("zip");
        }

        AssertFailAndContinue(billingPageActions.validateEmptyFieldErrMessages(expEmptyFldErrorMsg.get(0), expEmptyFldErrorMsg.get(1), expEmptyFldErrorMsg.get(2), expEmptyFldErrorMsg.get(3), zip, expEmptyFldErrorMsg.get(5)), "Validated the inline Error messages for all empty fields and verified that the err messages disappear after filling the fields.");
        AssertFailAndContinue(billingPageActions.validateSpecialCharacterErrorMessage("!@#$234", errMsgForFNameWithSplChar, errMsgForLNameWithSplChar, errMsgForAdrLine1WithSplChar, errMsgForCityWithSplChar, "Please enter a valid zip code"), "Verify error message for special characters in Billing page");
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT, REGRESSION, PRODUCTION}, priority = 0)
    public void maxCharacterError(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify that when the " + user + " user in the " + store + " store who is in the Shipping page/Billing page, enters more than 50 alphanumeric characters in the firstNameFld, lastNameFld, Address Line 2,Address Line 1, city field, zipPostalCodeFld, the appropriate error message should be displayed.");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = null, sm = null;
        if(store.equalsIgnoreCase("US")){
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        }
        else if(store.equalsIgnoreCase("CA")){
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");

        }

        List<String> splCharForMoreThan = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "moreThan50", "ErrorMessages"));

        addToBagBySearching(searchKeywordAndQty);
        String checkoutURl = EnvironmentConfig.getApplicationUrl().replaceAll("home","") + "checkout/shipping";

        if (store.equalsIgnoreCase("CA")) {
            checkoutURl = checkoutURl.replace("/us/", "/ca/");
        }
        driver.get(checkoutURl);

        AssertFailAndContinue(shippingPageActions.validateMoreThan50ErrMsg("Please enter a valid first name", "Please enter a valid last name", "Please shorten the street address", "Please enter a valid city", "Please enter a valid zip code"), "Verify the appropriate error messages displayed");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.waitUntilElementDisplayed(shippingPageActions.promotionOffer), "Launch shipping page url from shopping bag page, verify promotion details are displayed");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, "morethamfourtycharacterstextinfieldtestinshipping", "morethamfourtycharacterstextinfieldtestinshipping", shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, "morethamfourtycharacterstextinfieldtestinshipping", "morethamfourtycharacterstextinfieldtestinshipping", shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        AssertFailAndContinue(billingPageActions.validateMoreThan50ErrMsg("Please enter a valid first name", "Please enter a valid last name", "Please shorten the street address", "Please enter a valid city", "Please enter a valid zip code", "qwerttasdfdafdfddddfdasdnfmasdnfmasasdfasdfasdfasdd"),
                "Verify the appropriate error messages displayed");
    }
}