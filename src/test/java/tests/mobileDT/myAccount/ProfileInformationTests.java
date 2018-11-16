package tests.mobileDT.myAccount;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class ProfileInformationTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email;
    String latestPwd = "Place@134";
    String pwd2 = "Place@143";
    String password = "";
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String fname = "";
    String lname = "";
    String phoneNo = "";
    String zip = "";


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
            createAccount(rowInExcelUS, email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
            createAccount(rowInExcelCA, email);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void profileInfoUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for Profile Information Page" +
                "1. DT-31424, DT-31427, DT-31426, DT-44468, DT-44578");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        addToBagBySearching(searchKeywordAndQty);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        if (store.equalsIgnoreCase("US")) {
            fname = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "FirstName");
            lname = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "LastName");
            phoneNo = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "PhoneNumber");
            zip = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "ZipCode");
        }

        if (store.equalsIgnoreCase("CA")) {
            fname = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "FirstName");
            lname = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "LastName");
            phoneNo = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "PhoneNumber");
            zip = getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "ZipCode");
        }
        //DT-23478;
        AssertFailAndContinue(mmyAccountPageActions.validateUserNameInProfileInfoPage(fname, lname), "Verify User Name is displayed in Profile Info page");
        AssertFailAndContinue(mmyAccountPageActions.validatePhoneAndzip(phoneNo, zip), "Validate phone no and zip code ");

        AssertFailAndContinue(mmyAccountPageActions.validateProfileInfoPage(email, fname, lname, phoneNo, store), "Validate Information header, Edit link, Firstname, lastname, mobile no, " +
                "password header, change password btn, favorite store, set fav store btn, birthday savings header, birthdays savings btn");

        //DT-23472
        AssertFailAndContinue(mmyAccountPageActions.clickEditButton(), "Click Edit button and verify page is displayed in edit mode with save, cancel and Note under email add");

        AssertFailAndContinue(mmyAccountPageActions.valdaiteUserInfoInEditMode(email, fname, lname, phoneNo), "click on the 'Edit' link present next to the 'Profile Information' header " +
                "Verify following info Email, first name, last name, phone no are same as while creating account");

        mmyAccountPageActions.scrollToTop();
        AssertFailAndContinue(mmyAccountPageActions.clickChangePasswordBtn(), "Click Change password button verify password fields");
        AssertFailAndContinue(mmyAccountPageActions.validateChagnePwdFields(), "clicks on the 'Change password' button, verify Password Instructions, Current password text box with show link, New password text box with show link, Confirm New Password text box with a 'Show' link");

        mmyAccountPageActions.clickCompleteAddressBtn();
        AssertFailAndContinue(mmyAccountPageActions.verifyPhoneAndZip(phoneNo, zip), "Click Complete Address btn and verify zip code and phone nos are pre-populated");

        //DT-23486
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        mmyAccountPageActions.clickEditButton();
        AssertFailAndContinue(mmyAccountPageActions.verifyEmployeeCheckBox(), "verify TCP emp check box and un-selected by default");
        AssertFailAndContinue(mmyAccountPageActions.checkEmpCheckBox(), "Check Employee checkbox and verify Associate ID field is displayed");
        mmyAccountPageActions.clickSaveButton();
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.inlineErrMsg), "Verify Error message for empty Associate field");
        Map<String, String> associateDetails = dt2MobileExcel.getExcelData("MyAccount", "ValidAssociateID");

        Map<String, String> usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA1");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.enterMailAddress(usShipping.get("addressLine1"), usShipping.get("city"), usShipping.get("StateShortcut"));
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.enterMailAddress(caShipping.get("addressLine1"), caShipping.get("city"), caShipping.get("StateShortcut"));
        }
        AssertFailAndContinue(mmyAccountPageActions.enterAssociateDetails(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID")), "Verify user is able to enter associate details");
        AssertFailAndContinue(mmyAccountPageActions.clickSaveButton(), "Enter correct associate details and verify success message");
        mheaderMenuActions.clickShoppingBagIcon();

        AssertFailAndContinue(!mshippingPageActions.getText(mshippingPageActions.promotionsTot).equals("-"), "Verify Promotions value is displayed");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        //DT-23512, DT-23517,
        mmyAccountPageActions.clickEditButton();

        //DT-23520
        mmyAccountPageActions.fillMyAccountField("EMAIL", "new" + email);
        AssertFailAndContinue(mmyAccountPageActions.clickSaveButton(), "Change email id and save changes. Verify success message");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.emailID).equalsIgnoreCase("new" + email), "Verify that the registered user is able to successfully edit the email address");

        /*mmyAccountPageActions.scrollToTop();
        mmyAccountPageActions.clickEditButton();
        mmyAccountPageActions.clickCancelBtn();
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.emailID).equalsIgnoreCase("new" + email), "Verify that cancel button is not updating existing email");*/
        email = "new" + email;
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void inlineErrorMessages(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("inline Error message validations for email address field");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        //DT-23516
        mmyAccountPageActions.clickEditButton();

        //DT-23524
        mmyAccountPageActions.enterUserDetailsandSave("invalidFormat", "ln12", "fn34", phoneNo);
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILEFIRST").contains("First name field should not contain any special characters"), "Verify error message for First name with Numbers");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILELAST").contains("Last name field should not contain any special characters"), "Verify error message for First name with Numbers");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("EMAIL").contains("Email format is invalid"), "Verify error message for invalid email");

        mmyAccountPageActions.enterUserDetailsandSave("*(&ˆ", "@$%F%^", "%GT^HH", "jhfhfghfgh");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILEFIRST").contains("First name field should not contain any special characters"), "Verify error message for First name with Numbers");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILELAST").contains("Last name field should not contain any special characters"), "Verify error message for Last name with special characters");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILEPHONENO").contains("Please enter a valid phone number"), "Verify error message for phone no with letters");

        mmyAccountPageActions.enterUserDetailsandSave("", "", "", "");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILEFIRST").contains("Please enter a first name"), "Verify error message for empty First name");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILELAST").contains("Please enter a last name"), "Verify error message for empty Last name");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PROFILEPHONENO").contains("Please enter a valid phone number"), "Verify error message for empty phone no");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("EMAIL").contains("Please enter a valid email"), "Verify error message for empty email");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void passwordUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for password functionality");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        //DT-23535
        mmyAccountPageActions.clickChangePasswordBtn();
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("CANCELBTN"), "Verify cancel button");
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("SAVEBTN"), "Verify save button");

        //DT-23540
        mmyAccountPageActions.clickSaveButton();

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("PWDERR").contains("Please enter your password"), "Verify inline error message for current password field when empty");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("NEWPWDERR").contains("Please enter your password"), "Verify inline error message for new password field when empty");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("CONFIRMPWDERR").contains("Please enter a valid password"), "Verify inline error message for confirm password field when empty");

        //DT-23541, 42
        mmyAccountPageActions.fillMyAccountField("CURRENTPWD", password);
        mmyAccountPageActions.fillMyAccountField("CONFIRMPWD", "Place@132");

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("NEWPWDERR").contains("Please enter your password"), "Verify inline error message for new password field when confirm password field is filled");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("CONFIRMPWDERR").contains("Passwords must match"), "Verify inline error message for confirm password field when password not matched");

        mmyAccountPageActions.fillMyAccountField("NEWPWD", "<abcd>");
        mmyAccountPageActions.fillMyAccountField("CONFIRMPWD", "<abcd>");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("NEWPWDERR").contains("Please enter a valid password"), "Verify inline error message for password not meet the criteria");

        mmyAccountPageActions.fillMyAccountField("CURRENTPWD", password);
        mmyAccountPageActions.fillMyAccountField("NEWPWD", "Pllllace@123");
        mmyAccountPageActions.fillMyAccountField("CONFIRMPWD", "Pllllace@123");
        mmyAccountPageActions.clickSaveButton();
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("PAGELEVELERROR"), "Verify error message for password contains with more than 3 consecutive occurrence");

        mmyAccountPageActions.fillMyAccountField("CURRENTPWD", password);
        mmyAccountPageActions.fillMyAccountField("NEWPWD", password);
        mmyAccountPageActions.fillMyAccountField("NEWPWD", password);
        mmyAccountPageActions.fillMyAccountField("CONFIRMPWD", password);
        mmyAccountPageActions.clickSaveButton();

        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("PAGELEVELERROR"), "Verify error message registered user provides an password that is/was already associated with the TCP account for which password is being reset");

        //DT-23549, DT-23550
        mmyAccountPageActions.clickCancelBtn();
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("CANCELBTN"), "Verify change password section closed upon clicking cancel");

        //DT-23552
        mmyAccountPageActions.scrollToTop();
        mmyAccountPageActions.clickChangePasswordBtn();
        mmyAccountPageActions.fillMyAccountField("CURRENTPWD", password);
        mmyAccountPageActions.fillMyAccountField("NEWPWD", latestPwd);
        mmyAccountPageActions.fillMyAccountField("NEWPWD", latestPwd);
        mmyAccountPageActions.fillMyAccountField("CONFIRMPWD", latestPwd);

        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("NEWPWDOK"), "Validate Looks good text for new password field");
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("CONFIRMPWDOK"), "Validate Looks good text for confirm password field");
        mmyAccountPageActions.clickSaveButton();
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("SUCCESSMSG"), "Verify user is able to update password");
        password = latestPwd;
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void mailingAddressInlineErrorMessage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered user in" + store + " verify" +
                "DT-44578");

        Map<String, String> usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA1");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        mmyAccountPageActions.clickEditButton();

        mmyAccountPageActions.fillMyAccountField("MAILINGADD1", "");
        mmyAccountPageActions.fillMyAccountField("MAILINGCITY", "");
        mmyAccountPageActions.fillMyAccountField("MAILINGZIP", "");
        mmyAccountPageActions.fillMyAccountField("MAILINGADD1", "");
        mmyAccountPageActions.clickSaveButton();

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("ADDRESSLINE1").contains("Please enter a valid street address"), "Verify error message for empty Address Line 1");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGCITY").contains("Please enter a valid city"), "Verify error message for empty city");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGZIP").contains("Please enter your zip code"), "Verify error message for empty zip code");

        mmyAccountPageActions.fillMyAccountField("MAILINGADD1", "!#$%%");
        mmyAccountPageActions.fillMyAccountField("MAILINGCITY", "#$%");
        mmyAccountPageActions.fillMyAccountField("MAILINGZIP", "ˆˆ*(");
        mmyAccountPageActions.clickSaveButton();

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("ADDRESSLINE1").contains("The value entered in the street address has special character"), "Verify error message for empty Address Line 1");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGCITY").contains("The value entered in the city has special character"), "Verify error message for empty city");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGZIP").contains("Please enter a valid zip code"), "Verify error message for empty zip code");

    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void mailingAddressUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered user in" + store + " verify" +
                "DT-44575, DT-44574, DT-44578");

        Map<String, String> usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        mmyAccountPageActions.clickEditButton();

        AssertFailAndContinue(mmyAccountPageActions.validateMailingAddressFields(), "Verify mailing addres fields");
        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.enterMailAddress(usShipping.get("addressLine1"), usShipping.get("city"), usShipping.get("StateShortcut"));
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.enterMailAddress(caShipping.get("addressLine1"), caShipping.get("city"), caShipping.get("StateShortcut"));
        }
        AssertFailAndContinue(mmyAccountPageActions.clickSaveButton(), "Verify user is able to add Mailing address");

        mmyAccountPageActions.clickEditButton();

        usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "editedShippingAddressUS");
        caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "editedShippingAddressCA");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.enterMailAddress(usShipping.get("addressLine1"), usShipping.get("city"), usShipping.get("StateShortcut"));
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.enterMailAddress(caShipping.get("addressLine1"), caShipping.get("city"), caShipping.get("StateShortcut"));
        }
        AssertFailAndContinue(mmyAccountPageActions.clickSaveButton(), "Verify user is able to modify the existing Mailing address");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void differStoreMailingAddress(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered user in" + store + " verify" +
                "DT-44899");

        Map<String, String> usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA1");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        mmyAccountPageActions.clickEditButton();

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.selectMailingCountry("Canada");
            mmyAccountPageActions.enterMailAddress(caShipping.get("addressLine1"), caShipping.get("city"), caShipping.get("StateShortcut"));
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.selectMailingCountry("United States");
            mmyAccountPageActions.enterMailAddress(usShipping.get("addressLine1"), usShipping.get("city"), usShipping.get("StateShortcut"));
        }

        mmyAccountPageActions.clickSaveButton();
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
