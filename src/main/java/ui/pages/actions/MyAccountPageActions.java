package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.pages.repo.MyAccountPageRepo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by skonda on 6/7/2016.
 */
public class MyAccountPageActions extends MyAccountPageRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(MyAccountPageActions.class);

    public MyAccountPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isAccountPageDisplayed() {
        return waitUntilElementDisplayed(lbl_FirstName, 30);

    }

    public String getFirstNameFromWelcomeMyAcct() {
        String welcomeWithFN = getText(lbl_FirstName);
        int startIndex = welcomeWithFN.indexOf(",");
        String fnWithoutWelcome = welcomeWithFN.substring(startIndex + 1).trim();
        return fnWithoutWelcome;
    }

    public void selectAmountAndClickConvertPoints(String isLtyEnabled, String amt) {
        if (isLtyEnabled.equalsIgnoreCase("Yes")) {
            int rewardsSize = 0;
            try {
                rewardsSize = availableRewards.size();
            } catch (Throwable t) {
                rewardsSize = 0;
            }
            if (rewardsSize == 0) {
                selectDropDownByValue(amtToRedeemDropDown, amt);
                click(convertPointsButton);
                staticWait(6000);
            }
        } else {
            logger.info("Currently, Loyalty points disabled");

        }
    }

    public boolean enableChildrensPlaceEmployeeAndEnterId(String employeeId) {
        enableChildrenPlaceEmployee();
        return enterEmployeeIDAndSave(employeeId);
    }

    public boolean enableChildrenPlaceEmployee() {
        if (!isSelected(chk_ChildrenPlaceEmployee)) {
            click(chk_ChildrenPlaceEmployee);
        }
        return isSelected(chk_ChildrenPlaceEmployee);
    }

    public boolean enterEmployeeIDAndSave(String employeeId) {
        waitUntilElementDisplayed(tcpEmployeeID_Fld);
        clearAndFillText(tcpEmployeeID_Fld, employeeId);
        click(btn_Save);
        return waitUntilElementDisplayed(editEmployeeID);
    }

    public boolean updateHomeAddress(String address1, String city, String state) {
        click(editHomeAddress);
        waitUntilElementDisplayed(txt_Address1);
        clearAndFillText(txt_Address1, address1);
        clearAndFillText(txt_City, city);
        staticWait();
        selectDropDownByValue(drp_State, state);
        click(btn_Save);
        if (waitUntilElementDisplayed(useAsEnteredButton, 30)) {
            click(useAsEnteredButton);
        }
        staticWait(20000);
        switchToDefaultFrame();
        logger.info("The updated address actual is " + getText(lbl_NewAddr) + " Expected: " + address1);
        return getText(lbl_NewAddr).trim().equalsIgnoreCase(address1.trim());


    }

    public boolean completeUSAddressFunction(String addressline1, String city, String state, String zip) {
        waitUntilElementDisplayed(txt_Address1);
        clearAndFillText(txt_Address1, addressline1);
        clearAndFillText(txt_City, city);
        clearAndFillText(txt_Zipcode, zip);
        selectDropDownByVisibleText(drp_State, state);
        click(btn_Save);
        staticWait(5000);
        boolean mellisaData = waitUntilElementDisplayed(btn_MellisaData, 10);
        if (mellisaData == true)
            click(btn_MellisaData);
        staticWait(5000);

        boolean waitForUpdateOverlayToClose = (!(waitUntilElementDisplayed(txt_Address1, 30)));

        return waitForUpdateOverlayToClose;
    }

    public boolean VerifyMyAccountPage(String email, String fname, String lname) {

        if (lbl_PersonalInfoTab.getText().toLowerCase().contains("personal info") &&
                lbl_UserName.getText().equalsIgnoreCase(fname + " " + lname) &&
//                lbl_GreetingLink.getText().equalsIgnoreCase("Hi " + fname) &&
                lbl_FirstName.getText().equalsIgnoreCase("Welcome back, " + fname) &&
                lbl_EmailAddress.getText().equalsIgnoreCase(email) &&
                lbl_LogonPassword.getText().contains("************"))

            return true;
        else
            return false;
    }

    public boolean birthdayClubVerification() {


        if (getText(lbl_BdayClub).equalsIgnoreCase("Birthday Savings:") &&
                verifyElementNotDisplayed(lnk_ProgramDetails, 2) &&
                verifyElementNotDisplayed(amtToRedeemDropDown, 2)) {
            return true;
        } else
            return false;
    }

    public boolean EditEmailAddressOverlay(String email) {
        txt_EmailId.clear();
        txt_EmailId.sendKeys(email);
        txt_EmailIdConf.sendKeys(email);
        btn_EmailUpdateSubmit.click();
        staticWait(3000);

        return waitUntilElementDisplayed(loginLinkHeader, 10);
    }

    public boolean EmailEditOverlayWithoutMatchError() {
        clear(txt_EmailId);
        click(btn_EmailUpdateSubmit);
        staticWait(10);
        return (waitUntilElementDisplayed(err_EmailFormatInvalid, 10) && waitUntilElementDisplayed(err_EmailAddressNotMatch, 10));
    }

    public boolean EmailEditOverlayInvalidAddressError(String email, String invalidEmail) {
        fillText(txt_EmailId, email);
        fillText(txt_EmailIdConf, invalidEmail);
        click(btn_EmailUpdateSubmit);

        staticWait(10);

        return waitUntilElementDisplayed(err_EmailAddressNotMatch, 10);
    }


    public boolean EmailEditOverlayOnlyEmailFieldError(String email) {
        clearAndFillText(txt_EmailId, email);
        clear(txt_EmailIdConf);
        click(btn_EmailUpdateSubmit);

        staticWait(10);

        return waitUntilElementDisplayed(err_EmailFormatInvalid, 10) || waitUntilElementDisplayed(err_EmailAddressNotMatch, 10);
    }


    public boolean EmailEditOverlayOnlyConfEmailFieldError(String email) {
        clear(txt_EmailId);
        clearAndFillText(txt_EmailIdConf, email);
        click(btn_EmailUpdateSubmit);

        staticWait(10);

        return (waitUntilElementDisplayed(err_EmailFormatInvalid, 10) && waitUntilElementDisplayed(err_EmailAddressNotMatch, 10));
    }

    public boolean closeEditEmailAddressOverlay() {
        click(close_EditEmailAddressOverlay);
        return waitUntilElementDisplayed(lbl_PersonalInfoTab, 10);
    }

    public boolean editPasswordOverlay(String password) {
        String newPassword = password;
        clearAndFillText(edit_Password, newPassword);
        clearAndFillText(edit_ConfirmPassword, newPassword);
        click(btn_EmailUpdateSubmit);
        staticWait(5000);
        return verifyElementNotDisplayed(update_Password, 30);
    }

    public boolean editPasswordOverlayWithoutPasswordError() {
        clear(edit_Password);
        clear(edit_ConfirmPassword);
        click(btn_EmailUpdateSubmit);
        staticWait(10);
        return waitUntilElementDisplayed(err_PleaseEnterValidPassword, 10) && waitUntilElementDisplayed(err_PasswordNotMatch, 10);

    }

    public boolean editPasswordOverlayInvalidPasswordError(String password, String errMessage) {
        clearAndFillText(edit_Password, password);
        clearAndFillText(edit_ConfirmPassword, password);
        click(btn_EmailUpdateSubmit);
        staticWait(10);

        return waitUntilElementDisplayed(err_PasswordNotinConstraint(errMessage), 10);
    }

    public boolean editPasswordOverlayDifferentInvalidPasswordError(String password, String conf_Password, String errMessage) {

        clearAndFillText(edit_Password, password);
        clearAndFillText(edit_ConfirmPassword, conf_Password);
        click(btn_EmailUpdateSubmit);
        staticWait(10);

        return waitUntilElementDisplayed(err_PasswordNotinConstraint(errMessage), 10) && waitUntilElementDisplayed(err_PasswordNotMatch, 10);
    }

    public boolean editPasswordOverlayDifferentValidPasswordError(String password, String conf_Password) {
        clearAndFillText(edit_Password, password);
        clearAndFillText(edit_ConfirmPassword, conf_Password);
        click(btn_EmailUpdateSubmit);
        staticWait(10);

        return waitUntilElementDisplayed(err_PasswordNotMatch, 10);
    }

    public boolean editPasswordOverlayOldPasswordError(String password) {
        clearAndFillText(edit_Password, password);
        clearAndFillText(edit_ConfirmPassword, password);
        click(btn_EmailUpdateSubmit);
        staticWait(10);

        return waitUntilElementDisplayed(err_NewPasswordCannotReplicateOldPassword, 10);
    }

    public boolean editBirthdayClubNames(String fname, String lname, String month) {
        waitUntilElementDisplayed(btn_EditBirthdaySavings, 5);
        click(btn_EditBirthdaySavings);
        waitUntilElementDisplayed(btn_Save, 5);
        clearAndFillText(txt_FirstNameFromDigitalSignature, fname);
        clearAndFillText(txt_LastNameFromDigitalSignature, lname);
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);
        waitUntilElementDisplayed(Changing_ConfirmationMsg, 10);
        staticWait(10000);
        return waitUntilElementDisplayed(btn_EditBirthdaySavings, 5);
    }

    public boolean addChildBirthday(String childName, String gender, String month, String year) {
        click(btn_EditBirthdaySavings);
        waitUntilElementDisplayed(btn_AddChildButton);
        click(btn_AddChildButton);
        waitUntilElementDisplayed(click_SelectMonthFromYourBirthdayMonth, 20);
        enterChildInfo(childName, gender, month, year);
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);
        return waitUntilElementDisplayed(Changing_ConfirmationMsg, 10);

    }

    public boolean selectWithDefaultValueInSelectMonth() {
        selectDropDownByValue(click_SelectMonthFromYourBirthdayMonth, "");
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);

        return waitUntilElementDisplayed(err_MonthNotSelectedMsg, 10);
    }

    public boolean saveWithoutEnteringChildInfo() {
        addAnotherChild.click();
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);

        if (isDisplayed(err_PleaseIndicateTheValueOfChildName) &&
                isDisplayed(err_PleaseIndicateTheValueOfChildBirthYear) &&
                isDisplayed(err_PleaseIndicateTheValueOfChildBirthMonth) &&
                isDisplayed(err_PleaseIndicateTheValueOfChildGender))
            return true;
        else
            return false;
    }

    public boolean saveWithoutLastName() {
        clear(txt_LastNameFromDigitalSignature);
        click(btn_Save);
        boolean errAcceptTerms = waitUntilElementDisplayed(err_PleaseAcceptTermAndCondition, 10);
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);
        boolean errLastName = waitUntilElementDisplayed(err_TypeANameInLastNameField, 10);

        return errAcceptTerms && errLastName;
    }

    public boolean addFourChildsBirthdayInfo() {
        waitUntilElementDisplayed(btn_EditBirthdaySavings, 10);
        click(btn_EditBirthdaySavings);
        waitUntilElementDisplayed(btn_Save, 5);
        click(addAnotherChild);
        enterChildInfo("abcd", "Boy", "June", "2015");
        click(addAnotherChild);
        enterChildInfo("efgh", "Girl", "June", "2014");
        click(addAnotherChild);
        enterChildInfo("ijkl", "Boy", "June", "2013");
        click(addAnotherChild);
        enterChildInfo("mnop", "Girl", "June", "2012");
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);
        waitUntilElementDisplayed(Changing_ConfirmationMsg, 10);
        staticWait(10000);
        return waitUntilElementDisplayed(btn_EditBirthdaySavings, 5);
    }


    public void enterChildInfo(String childName, String gender, String month, String year) {
        newChildName().sendKeys(childName);
        selectDropDownByVisibleText(newChildGender(), gender);
        selectDropDownByVisibleText(newChildMonth(), month);
        selectDropDownByVisibleText(newChildYear(), year);
    }

    public void deleteAllChild() {
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@id,'_null_r_')] //a[contains(.,'Delete')]"));
        for (WebElement element : list) {
            element.click();
            staticWait(5);
        }
    }

    public boolean deletingAllChildren() {
        waitUntilElementDisplayed(btn_EditBirthdaySavings, 5);
        click(btn_EditBirthdaySavings);
        waitUntilElementDisplayed(btn_Save, 5);
        deleteAllChild();
        click(chk_TermAndConditionInBirthdayClub);
        click(btn_Save);
        waitUntilElementDisplayed(Changing_ConfirmationMsg, 10);
        staticWait(10000);
        return waitUntilElementDisplayed(btn_EditBirthdaySavings, 5);
    }

    public boolean verifyEditAddressField() {
        waitUntilElementDisplayed(editAddress, 10);
        click(editAddress);
        staticWait(3000);
        if (txt_FirstNameFromDigitalSignature.isDisplayed() &&
                txt_LastNameFromDigitalSignature.isDisplayed() &&
                txt_Address1.isDisplayed() &&
                txt_Address2.isDisplayed() &&
                txt_City.isDisplayed() &&
                drp_State.isDisplayed() &&
                txt_Zipcode.isDisplayed() &&
                drp_Country.isDisplayed() &&
                txt_PhoneNumber.isDisplayed() &&
                btn_Save.isDisplayed() &&
                btn_Cancel.isDisplayed())
            return true;
        else
            return false;


    }

    public boolean enterAddressFields(String fname, String lname, String address1, String city, String state, String zipcode, String phone) {
        clearAndFillText(txt_FirstNameFromDigitalSignature, fname);
        clearAndFillText(txt_LastNameFromDigitalSignature, lname);
        clearAndFillText(txt_Address1, address1);
        clearAndFillText(txt_City, city);
        selectDropDownByVisibleText(drp_State, state);
        clearAndFillText(txt_Zipcode, zipcode);
        clearAndFillText(txt_PhoneNumber, phone);
        click(btn_Save);
        boolean mellisaData = waitUntilElementDisplayed(btn_MellisaData, 10);
        if (mellisaData == true)
            click(btn_MellisaData);
        staticWait(5000);
        return verifyElementNotDisplayed(editAddrModalWindow, 30);
    }

    public boolean verifyAddress(String fname, String lname, String address1, String city, String state, String zipcode, String phone) {
        if (FirstNameLastName.getText().contains(fname) &&
                FirstNameLastName.getText().contains(lname) &&
                lbl_NewAddr.getText().contains(address1) &&
                lbl_NewCity.getText().contains(city) &&
                lbl_NewCity.getText().contains(zipcode) &&
                lbl_Phone.getText().contains(phone))

            return true;

        else
            return false;

    }

    public boolean verifyErrorMessageInHomeAddress() {
        waitUntilElementDisplayed(editAddress, 10);
        click(editAddress);
        waitUntilElementDisplayed(btn_Save, 10);
        txt_FirstNameFromDigitalSignature.clear();
        txt_LastNameFromDigitalSignature.clear();
        txt_Address1.clear();
        txt_Address2.clear();
        txt_City.clear();
        selectDropDownByValue(drp_State, "");
        txt_Zipcode.clear();
        txt_PhoneNumber.clear();
        click(btn_Save);
        staticWait(10);

        return errorValidation();
    }

    public boolean verifyFirstAndLastNameError(String firstNameWithSplChar, String lastNameWithSplChar) {
        clearAndFillText(txt_FirstNameFromDigitalSignature, firstNameWithSplChar);
        clearAndFillText(txt_LastNameFromDigitalSignature, lastNameWithSplChar);
        click(btn_Save);


        return waitUntilElementDisplayed(firstName_SpecialCharError, 10) && waitUntilElementDisplayed(lastName_SpecialCharError, 10);
    }

    public boolean errorValidation() {
        if (firstName_Error.isDisplayed() &&
                lastName_Error.isDisplayed() &&
                streetAddress_Error.isDisplayed() &&
                city_Error.isDisplayed() &&
                state_Error.isDisplayed() &&
                zipCode_Error.isDisplayed() &&
                phoneNumber_Error.isDisplayed())
            return true;
        else
            return false;
    }

    public boolean verifyZipcodeError(String zipCodeLessThanFive, String zipCodeMoreThanFive) {
        clear(txt_FirstNameFromDigitalSignature);
        clear(txt_LastNameFromDigitalSignature);

        clearAndFillText(txt_Zipcode, zipCodeLessThanFive);
        click(btn_Save);
        waitUntilElementDisplayed(zipcode_LessThan_five_Error, 5);

        staticWait(10);

        clearAndFillText(txt_Zipcode, zipCodeMoreThanFive);
        click(btn_Save);
        waitUntilElementDisplayed(ZipCode_MoreThan_five_Error, 5);

        click(btn_Cancel);

        return verifyElementNotDisplayed(editAddrModalWindow, 30);
    }

    public boolean verifyNewAddressSameAsOld() {
        waitUntilElementDisplayed(editAddress, 5);
        click(editAddress);
        waitUntilElementDisplayed(btn_Save, 10);
        click(btn_Save);
        return waitUntilElementDisplayed(pageLevel_Error, 10);
    }

    public boolean fillAddress(String address_1, String city, String state) {
        waitUntilElementDisplayed(editAddress, 10);
        click(editAddress);
        waitUntilElementDisplayed(txt_Address1, 10);
        clearAndFillText(txt_Address1, address_1);
        clearAndFillText(txt_City, city);
        selectDropDownByVisibleText(drp_State, state);
        click(btn_Save);
        boolean mellisaData = waitUntilElementDisplayed(btn_MellisaData, 10);
        if (mellisaData == true)
            click(btn_MellisaData);
        waitUntilElementDisplayed(editAddress, 20);
        staticWait(10000);
        return verifyElementNotDisplayed(editAddrModalWindow, 30);
    }

    public boolean addNewShippingAddress(String fName, String lName, String address1, String address2,
                                         String city, String country, String state, String zipCode, String phoneNo) {

        waitUntilElementDisplayed(lnk_AddressBook, 5);
        staticWait(1000);
        click(lnk_AddressBook);
        staticWait(1000);
        waitUntilElementDisplayed(btn_AddNewAddress, 10);
        //clickAddNewAddressBook();
        click(btn_AddNewAddress);
        //waitUntilElementDisplayed(btn_AddNewAddress, 10);
        waitUntilElementDisplayed(newAddress_FirstName, 10);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        /*clearAndFillText(newAddress_AddressLine2, address2);*/
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        clearAndFillText(newAddress_PhoneNumeber, phoneNo);
        select(newAddress_SetDefaultAddressCheckBox, newAddress_SetDefaultAddressCheckBoxInput);
        click(newAddress_AddNewAddressBtn);
        //staticWait(5000);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);

        staticWait(5000);

        verifyDefaultShippingAddress(fName, lName, address1, city, zipCode);
//        scrollUpToElement(lnk_AddressBook);
//        click(lnk_AddressBook);
        return waitUntilElementDisplayed(defaultShipMethodLbl, 20);
    }

    public boolean addYouEnteredShippingAddress(String fName, String lName, String address1, String address2,
                                                String city, String country, String state, String zipCode, String phoneNo) {

        waitUntilElementDisplayed(lnk_AddressBook, 5);
        staticWait(1000);
        click(lnk_AddressBook);
        staticWait(1000);
        waitUntilElementDisplayed(btn_AddNewAddress, 5);
        clickAddNewAddressBook();
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        /*clearAndFillText(newAddress_AddressLine2, address2);*/
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        clearAndFillText(newAddress_PhoneNumeber, phoneNo);
        if (waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox)) {
            click(newAddress_SetDefaultAddressCheckBox);
        }
        click(newAddress_AddNewAddressBtn);
        staticWait(5000);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true) {
        }
        boolean youEnteredCheckBoxes = waitUntilElementDisplayed(youEnteredCheckBox, 10);
        if (youEnteredCheckBoxes == true) {
            click(youEnteredCheckBox);
        }
        click(addressVerificationContinue);
        staticWait(5000);
        verifyDefaultShippingAddress(fName, lName, address1, city, zipCode);
        return waitUntilElementDisplayed(defaultShipMethodLbl, 20);
    }

    public boolean editYouEnteredShippingAddress(String fName, String lName, String address1, String address2,
                                                 String city, String country, String state, String zipCode, String phoneNo) {

        clearAndFillText(newAddress_PhoneNumeber, phoneNo);
        click(newAddress_AddNewAddressBtn);
        staticWait(5000);
        boolean addressVerifyEdit = waitUntilElementDisplayed(addressVerificationEdit, 10);
        if (addressVerifyEdit == true)
            click(addressVerificationEdit);
        staticWait(2000);
        waitUntilElementDisplayed(newAddress_AddNewAddressBtn, 10);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        clearAndFillText(newAddress_PhoneNumeber, phoneNo);
        boolean addressCheckBox = waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox);
        if (addressCheckBox == false)
            click(newAddress_SetDefaultAddressCheckBox);
        click(newAddress_AddNewAddressBtn);
        staticWait(5000);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        staticWait(5000);
        verifyDefaultShippingAddress(fName, lName, address1, city, zipCode);
        return waitUntilElementDisplayed(defaultShipMethodLbl, 20);
    }

    public boolean editShippingAddress(String fName, String lName, String address1, String address2,
                                       String city, String country, String state, String zipCode, String phoneNo) {

        waitUntilElementDisplayed(editShipAddressLink, 5);
        click(editShipAddressLink);
        waitUntilElementDisplayed(newAddress_FirstName, 5);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        /*clearAndFillText(newAddress_AddressLine2, address2);*/
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        clearAndFillText(newAddress_PhoneNumeber, phoneNo);

        click(newAddress_AddNewAddressBtn);
        staticWait(5000);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        staticWait(5000);
        verifyDefaultShippingAddress(fName, lName, address1, city, zipCode);
//        scrollUpToElement(lnk_AddressBook);
//        click(lnk_AddressBook);
        return waitUntilElementDisplayed(defaultShipMethodLbl, 20);
    }

    public boolean editBillingAddress(String fName, String lName, String address1, String address2,
                                      String city, String country, String state, String zipCode) {

        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        click(saveChangesBtn);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        staticWait(5000);
        return waitUntilElementDisplayed(defaultPaymentMethodLbl, 20);
    }

    public String getDefaultPaymentContent() {
        return getText(defaultpaymentContainer);
    }

    public boolean verifyDefaultShippingAddress(String fname, String lname, String address1, String city, String zipcode)

    {
        boolean validateFName = getText(defaultShipAddress).replaceAll("\"", "").contains(fname);
        boolean validateLName = getText(defaultShipAddress).replaceAll("\"", "").contains(lname);
        boolean validateAddress = getText(defaultShipAddressName).contains(address1);
        boolean validateCity = getText(defaultShipAddressName).contains(city);
        boolean validateZIP = getText(defaultShipAddressName).contains(zipcode);
        waitUntilElementsAreDisplayed(editLinkForAddress,2);
        waitUntilElementsAreDisplayed(deleteLinkForAddress,1);
        if (validateFName && validateLName &&
                validateAddress &&
                validateCity &&
                validateZIP)
            return true;
        else
            return false;
    }

    public boolean makeDefaultShippingToNonDefault(){
        scrollUpToElement(lnk_AddressBook);
        click(lnk_AddressBook);
        if(waitUntilElementDisplayed(editDefShipAddrLink, 5)){
            click(editDefShipAddrLink);
            waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox, 5);
            click(newAddress_SetDefaultAddressCheckBox);
            click(updateOnAddressBook);

            boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
            if (addressVerify == true)
                click(addressVerificationContinue);

            staticWait(5000);
        }
        return waitUntilElementDisplayed(updatedSuccessMsg, 30);

    }
    public boolean modifyToNonDefaultShippingAddress() {
        scrollUpToElement(lnk_AddressBook);
        click(lnk_AddressBook);
        staticWait(2000);
        if (scrollDownToElement(editShipAddressLink)) {
            click(editShipAddressLink);
            staticWait(5000);
            click(newAddress_SetDefaultAddressCheckBox);
            click(updateOnAddressBook);

            boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
            if (addressVerify == true)
                click(addressVerificationContinue);

            staticWait(5000);
        }
        return waitUntilElementDisplayed(updatedSuccessMsg, 30);
    }

    public boolean isErrorMsgDisplayingOnAllFields(String fNameErr, String lNameErr, String addressErr, String cityErr, String stateErr, String zipErr, String phNumErr) {
        staticWait(10000);
        boolean isFNameErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(fNameErr), 10);
        boolean isLNameErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(lNameErr), 3);
        boolean isAddressErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(addressErr), 3);
        boolean isCityErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(cityErr), 3);
        boolean isStateErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(stateErr), 3);
        boolean isZipErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(zipErr), 3);
        boolean isPhNumErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(phNumErr), 3);


        if (isFNameErrMsgDisplaying && isLNameErrMsgDisplaying && isAddressErrMsgDisplaying && isCityErrMsgDisplaying && isStateErrMsgDisplaying
                && isZipErrMsgDisplaying && isPhNumErrMsgDisplaying) {
            return true;
        }
        return false;
    }

    public boolean clickAddAddressButtonAndVerifyErrorMsgOnAllFields(String fNameErr, String lNameErr, String addressErr, String cityErr, String stateErr, String zipErr, String phNumErr) {
        waitUntilElementDisplayed(btnAddCard);
        click(btnAddCard);
        return isErrorMsgDisplayingOnAllFields(fNameErr, lNameErr, addressErr, cityErr, stateErr, zipErr, phNumErr);
    }

    public boolean firstNameAndLastNameErrorMsg(String firstNameWithSplChar, String lastNameWithSplChar) {
        clearAndFillText(newAddress_FirstName, firstNameWithSplChar);
        clearAndFillText(newAddress_LastName, lastNameWithSplChar);
        click(btnAddCard);

        return waitUntilElementDisplayed(firstName_SpecialCharError, 10) && waitUntilElementDisplayed(lastName_SpecialCharError, 10);
    }

    public boolean zipCodeErrorForLessThanAndMoreThanFive(String zipCodeLessThanFive, String zipCodeMoreThanFive) {
        clear(newAddress_FirstName);
        clear(newAddress_LastName);

        clearAndFillText(newAddress_ZipCode, zipCodeLessThanFive);
        click(btnAddCard);
        waitUntilElementDisplayed(zipcode_LessThan_five_Error, 5);

        staticWait(2000);

        clearAndFillText(newAddress_ZipCode, zipCodeMoreThanFive);
        click(btnAddCard);
        waitUntilElementDisplayed(ZipCode_MoreThan_five_Error, 5);

        click(newAddressCancelBtn);

        return verifyElementNotDisplayed(newAddressCancelBtn, 10);
    }

    public boolean zipCodeErrorForLessThanAndMoreThanSix_CA(String zipCodeLessThanFive, String zipCodeMoreThanFive) {
        clear(newAddress_FirstName);
        clear(newAddress_LastName);

        clearAndFillText(newAddress_ZipCode, zipCodeLessThanFive);
        click(btnAddCard);
        waitUntilElementDisplayed(zipcode_LessMoreThan_Six_Error_CA, 5);

        clearAndFillText(newAddress_ZipCode, zipCodeMoreThanFive);
        click(btnAddCard);
        waitUntilElementDisplayed(zipcode_LessMoreThan_Six_Error_CA, 5);

        click(newAddressCancelBtn);

        return waitUntilElementDisplayed(lnk_Logout, 10);
    }

    public boolean verifyDefaultCreditCard() {
        waitUntilElementDisplayed(lnk_PaymentGC, 10);
        click(lnk_PaymentGC);
        staticWait(3000); // need to check why static wait required
        int cards = cardContainers.size();
        for (int i = 0; i < cards; i++) {
            click(paymentInfo_RemoveLink);
            click(btnOverlayConfirmRemove);
        }
        return waitUntilElementDisplayed(btn_AddNewCard, 10);
    }

    public boolean removeDefaultCreditCard() {
        staticWait(3000);
        click(lnk_PaymentGC);
        while (waitUntilElementDisplayed(defaultPayment_RemoveLink, 10)) {
            click(defaultPayment_RemoveLink);
            waitUntilElementDisplayed(btnOverlayConfirmRemove, 15);
            addStepDescription("clicked on remove button of default payment method opens confirm to remove");
            click(btnOverlayConfirmRemove);
            staticWait(5000);
        }

        return waitUntilElementDisplayed(btn_AddNewCard, 10);
    }

    public boolean enterPaymentInfo(String fName, String lName, String address, String city, String state, String zipCode,
                                    String phone, String card, String card_no, String exp_month, String successMsgContent) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);
        scrollDownToElement(lnk_PaymentGC);
        click(lnk_PaymentGC);
        waitUntilElementDisplayed(btn_AddNewCard);
        click(btn_AddNewCard);

        if (waitUntilElementDisplayed(newAddress_FirstName, 5)) {
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        } else {
            clickAddressDropdown();
            clickAddNewAddress();
            staticWait(2000);
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        }

        staticWait(3000);
        clearAndFillText(addPayment_CardNumber, card_no);

        switch (card) {

            case "VISA":
                boolean visaImgDisplayed = waitUntilElementDisplayed(imgVISA, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                scrollDownToElement(btnAddCard);
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return visaImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "Master Card":
                boolean mcImgDisplayed = waitUntilElementDisplayed(imgMC, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return mcImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "Discover":
                boolean discoverImgDisplayed = waitUntilElementDisplayed(imgDiscover, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return discoverImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "AMEX":
                boolean amexImgDisplayed = waitUntilElementDisplayed(imgAMEX, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return amexImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "PLCC":
                boolean plccImgDisplayed = waitUntilElementDisplayed(imgPLCC, 10);
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return plccImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);
        }
        verifyDefaultShippingAddress(fName, lName, address, city, zipCode);
        return verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

    }

    public boolean editDefaultPaymentDetails(String fName, String lName, String address, String city, String state, String zipCode,
                                             String phone, String card, String card_no, String exp_month, String successMsgContent) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);
        staticWait(3000);
        click(editPaymentAddressLnk);
        if (waitUntilElementDisplayed(newAddress_FirstName, 5)) {
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        } else {
            clickAddressDropdown();
            clickAddNewAddress();
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        }

        staticWait(3000);
        clearAndFillText(addPayment_CardNumber, card_no);

        switch (card) {

            case "VISA":
                boolean visaImgDisplayed = waitUntilElementDisplayed(imgVISA, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
//                scrollDownToElement(btnSaveChanges);
                click(btnSaveChanges);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return visaImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "Master Card":
                boolean mcImgDisplayed = waitUntilElementDisplayed(imgMC, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnSaveChanges);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return mcImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "Discover":
                boolean discoverImgDisplayed = waitUntilElementDisplayed(imgDiscover, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnSaveChanges);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return discoverImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "AMEX":
                boolean amexImgDisplayed = waitUntilElementDisplayed(imgAMEX, 10);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnSaveChanges);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return amexImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "PLCC":
                boolean plccImgDisplayed = waitUntilElementDisplayed(imgPLCC, 10);
                click(btnSaveChanges);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return plccImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);
        }

        verifyDefaultShippingAddress(fName, lName, address, city, zipCode);
        return verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

    }

    public boolean enterCCWithExistingShippingDetails(String cardType, String cardNo, String expMonth) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);
        click(lnk_PaymentGC);
        waitUntilElementDisplayed(btn_AddNewCard);
        scrollDownToElement(btn_AddNewCard);
        click(btn_AddNewCard);
        switch (cardType) {
            case "PLCC":
                clearAndFillText(addPayment_CardNumber, cardNo);
                break;
            default:
                clearAndFillText(addPayment_CardNumber, cardNo);
                selectDropDownByValue(addPayment_expmonth, expMonth);
                selectDropDownByVisibleText(addPayment_expyr, expYear);
        }

        click(btnAddCard);
        return waitUntilElementDisplayed(successMessageBox);
    }

    public boolean enterCCAndBillingAddress(String fName, String lName, String address, String city, String state, String zipCode,
                                            String phone, String card, String card_no, String exp_month, String successMsgContent) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);
        staticWait(3000);
        waitUntilElementDisplayed(lnk_PaymentGC, 20);
        click(lnk_PaymentGC);
        staticWait(3000);
        verifyDefaultCreditCard();
        staticWait(3000);
        waitUntilElementDisplayed(btn_AddNewCard, 20);
        click(btn_AddNewCard);
        staticWait(3000);
        clearAndFillText(addPayment_CardNumber, card_no);
        selectDropDownByValue(addPayment_expmonth, exp_month);
        selectDropDownByVisibleText(addPayment_expyr, expYear);
        staticWait(3000);
        if (waitUntilElementDisplayed(newAddress_FirstName, 20)) {
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByValue(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
            staticWait(5000);

        }
        waitUntilElementDisplayed(btnAddCard, 20);
        click(btnAddCard);
        waitUntilElementDisplayed(editPaymentAddressLnk, 10);
        return verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);
    }

    public boolean enterCCAndBillingAddressDiffCountry(String fName, String lName, String country, String address, String city, String state, String zipCode,
                                                       String card, String card_no, String exp_month, String successMsgContent) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);
        staticWait(3000);
        waitUntilElementDisplayed(lnk_PaymentGC, 20);
        click(lnk_PaymentGC);
        staticWait(3000);
        verifyDefaultCreditCard();
        staticWait(3000);
        waitUntilElementDisplayed(btn_AddNewCard, 20);
        click(btn_AddNewCard);
        staticWait(3000);
        clearAndFillText(addPayment_CardNumber, card_no);
        selectDropDownByValue(addPayment_expmonth, exp_month);
        selectDropDownByVisibleText(addPayment_expyr, expYear);
        staticWait(3000);
        if (waitUntilElementDisplayed(newAddress_FirstName, 20)) {
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            selectDropDownByVisibleText(addPayment_Country, country);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByValue(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
            staticWait(5000);

        }
        waitUntilElementDisplayed(btnAddCard, 20);
        click(btnAddCard);
        waitUntilElementDisplayed(editPaymentAddressLnk, 10);
        return verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);
    }

    public boolean verifyDefaultCardDetails(String cardType, String cardNo, String expMonth, String expYr, String successMsgContent) {
        staticWait(5000);
        String actualCardNum;
        String trimCardNum = null;
        if (cardType.contains("AMEX")) {
            actualCardNum = cardNo.substring(11, 15);
            waitUntilElementDisplayed(lbl_CardNumber(actualCardNum), 20);
            trimCardNum = getText(lbl_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        } else {
            actualCardNum = cardNo.substring(12, 16);
            waitUntilElementDisplayed(lbl_CardNumber(actualCardNum), 20);
            trimCardNum = getText(lbl_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        }
        String trimCardType = cardType.replaceAll(" ", "");

        boolean compareCardNumValue = trimCardNum.contains(actualCardNum);

        switch (cardType) {

            case "VISA":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    break;

            case "Master Card":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    break;
            case "Discover":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    break;
            case "AMEX":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    break;
            case "PLCC":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        validateSuccessMessage(successMsgContent))
                    break;

        }
        return waitUntilElementDisplayed(defaultPaymentMethodLbl, 10);
    }

    public boolean verifyDefaultCardDetailsAfterOrder(String cardType, String cardNo, String expMonth, String expYr) {
        String actualCardNum;
        String trimCardNum = null;
        if (cardType.contains("AMEX")) {
            actualCardNum = cardNo.substring(11, 15);
            waitUntilElementDisplayed(lbl_CardNumber(actualCardNum), 20);
            trimCardNum = getText(lbl_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        } else {
            actualCardNum = cardNo.substring(12, 16);
            waitUntilElementDisplayed(lbl_CardNumber(actualCardNum), 20);
            trimCardNum = getText(lbl_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        }
        boolean checkDefaultLbl = false;
        String getDefaultpayment = getDefaultPaymentContent();

        switch (cardType) {

            case "VISA":
                if (getDefaultpayment.contains(trimCardNum) &&
                        getDefaultpayment.contains(expMonth)) {
                    checkDefaultLbl = true;
                }
                break;

            case "Master Card":
                if (getDefaultpayment.contains(trimCardNum) &&
                        getDefaultpayment.contains(expMonth)) {
                    checkDefaultLbl = true;
                }
                break;

            case "Discover":
                if (getDefaultpayment.contains(trimCardNum) &&
                        getDefaultpayment.contains(expMonth)) {
                    checkDefaultLbl = true;
                }
                break;
            case "AMEX":
                if (getDefaultpayment.contains(trimCardNum) &&
                        getDefaultpayment.contains(expMonth)) {
                    checkDefaultLbl = true;
                }
                break;

            case "PLCC":
                if (getDefaultpayment.contains(trimCardNum)) {
                    checkDefaultLbl = true;
                }
                break;

        }
        return checkDefaultLbl;
    }

    public boolean verifyEditCardDetailsPrepopulated(String cardType, String cardNo, String expMonth, String expYr, String successMsgContent) {
        staticWait(5000);
        String actualCardNum;
        String trimCardNum = null;

        if (cardType.contains("AMEX")) {
            actualCardNum = cardNo.substring(11, 15);
            waitUntilElementDisplayed(lbl_CardNumber(actualCardNum), 20);
            trimCardNum = getText(lbl_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        } else {
            actualCardNum = cardNo.substring(12, 16);
            waitUntilElementDisplayed(lbl_CardNumber(actualCardNum), 20);
            trimCardNum = getText(lbl_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        }
        String trimCardType = cardType.replaceAll(" ", "");

        boolean compareCardNumValue = trimCardNum.contains(actualCardNum);

        switch (cardType) {

            case "VISA":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    return waitUntilElementDisplayed(addPayment_CardNumber, 1);

            case "Master Card":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    return waitUntilElementDisplayed(addPayment_CardNumber, 1);

            case "Discover":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    return waitUntilElementDisplayed(addPayment_CardNumber, 1);

            case "AMEX":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        TextPresent(driver, expMonth) &&
                        TextPresent(driver, expYr) &&
                        validateSuccessMessage(successMsgContent))
                    return waitUntilElementDisplayed(addPayment_CardNumber, 1);

            case "PLCC":
                if (TextPresent(driver, trimCardType) &&
                        compareCardNumValue &&
                        validateSuccessMessage(successMsgContent))
                    return waitUntilElementDisplayed(addPayment_CardNumber, 1);


        }
        return compareCardNumValue;
    }

    public boolean clickAddPaymentAndVerifyErrorMsgOnAllFields(String fNameErr, String lNameErr, String addressErr, String cityErr, String zipErr, String stateErr, String cardErr, String cardExpErr) {
        scrollDownToElement(btnAddCard);

        if (waitUntilElementDisplayed(newAddress_FirstName, 10)) {
            waitUntilElementDisplayed(btnAddCard, 10);
            click(btnAddCard);
        } else {
            selectAddNewBillAddress();
            waitUntilElementDisplayed(btnAddCard, 10);
            click(btnAddCard);
        }
        return isErrorMsgDisplayedOnAllPaymentFields(fNameErr, lNameErr, addressErr, cityErr, zipErr, stateErr, cardErr, cardExpErr);
    }

    public boolean isErrorMsgDisplayedOnAllPaymentFields(String fNameErr, String lNameErr, String addressErr, String cityErr, String zipErr, String stateErr, String cardErr, String cardExpErr) {
        boolean isFNameErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(fNameErr), 1);
        boolean isLNameErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(lNameErr), 1);
        boolean isAddressErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(addressErr), 1);
        boolean isCityErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(cityErr), 1);

        scrollDownToElement(errorMessageByFieldError(zipErr));
        boolean isZipErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(zipErr), 1);
        boolean isStateErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(stateErr), 1);

        scrollUpToElement(errorMessageByFieldError(cardErr));
        boolean isCardErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(cardErr), 1);
        boolean isCardExpErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(cardExpErr), 1);


        if (isFNameErrMsgDisplaying && isLNameErrMsgDisplaying && isAddressErrMsgDisplaying && isCityErrMsgDisplaying && isZipErrMsgDisplaying
                && isStateErrMsgDisplaying && isCardErrMsgDisplaying && isCardExpErrMsgDisplaying) {
            return true;
        }
        return false;
    }

    public boolean paymentDetailsNameSpecialCharError(String firstNameWithSplChar, String lastNameWithSplChar, String fnameErr, String lnameErr) {
        scrollDownToElement(newAddress_FirstName);
        waitUntilElementDisplayed(newAddress_FirstName, 5);
        if (!(waitUntilElementDisplayed(newAddress_FirstName, 5))) {
            clickAddressDropdown();
            clickAddNewAddress();
        }
        clearAndFillText(newAddress_FirstName, firstNameWithSplChar);
        clearAndFillText(newAddress_LastName, lastNameWithSplChar);
        click(btnAddCard);

        return waitUntilElementDisplayed(errorMessageByFieldError(fnameErr), 10) && waitUntilElementDisplayed(errorMessageByFieldError(lnameErr), 10);
    }

    public boolean paymentDetailsCitySpecialCharError(String cityWithSplChar, String cityErr) {
        scrollDownToElement(newAddress_City);
        waitUntilElementDisplayed(newAddress_City, 20);
        clearAndFillText(newAddress_City, cityWithSplChar);
        click(btnAddCard);

        return waitUntilElementDisplayed(errorMessageByFieldError(cityErr), 10);
    }

    public boolean paymentDetailsAddressSpecialCharError(String addressWithSplChar, String addressErr) {
        waitUntilElementDisplayed(newAddress_AddressLine1, 20);
        clearAndFillText(newAddress_AddressLine1, addressWithSplChar);
        click(btnAddCard);

        return waitUntilElementDisplayed(errorMessageByFieldError(addressErr), 10);
    }


    public boolean verifyZipCodeErrorBillInfo(String invalidZipValue, String zipErr) {
        scrollDownToElement(newAddress_ZipCode);
        waitUntilElementDisplayed(newAddress_ZipCode, 20);
        clearAndFillText(newAddress_ZipCode, invalidZipValue);
        click(btnAddCard);
        staticWait(5000);
        return waitUntilElementDisplayed(errorMessageByFieldError(zipErr), 5);
    }

    public boolean creditCardValidation(String cardType) {
        selectDropDownByVisibleText(addPayment_CardSelection, cardType);
        click(btnAddCard);

        return waitUntilElementDisplayed(creditcardnumber_Error, 10);
    }

    public boolean creditCardNumberValidation(String cardNumber) {
        clearAndFillText(addPayment_CardNumber, cardNumber);
        click(btnAddCard);

        return waitUntilElementDisplayed(creditcardnumber_InvalidError, 10);
    }

    public boolean checkHeaderItems() {
        waitUntilElementDisplayed(myAccountLink, 10);
        click(myAccountLink);
        refreshPage();
        click(tab_OnlineHistory);
        waitUntilElementDisplayed(header_Date, 30);
        if (isDisplayed(header_Date) &&
                isDisplayed(header_Transaction) &&
                isDisplayed(header_Total) &&
                isDisplayed(header_Status))
            return true;
        else
            return false;
    }

    boolean waitForOrder(String orderNum) {
        waitUntilElementDisplayed(orderHistory_FirstRow, 60);
        click(tab_OnlineHistory);

        if (getDriver().findElements(By.xpath("//*[@id='orderHistory']//tr[1][contains(.,'" + orderNum + "')]")).size() == 1)
            return true;

        else if (getDriver().findElements(By.xpath("//*[@id='orderHistory']//tr[2][contains(.,'" + orderNum + "')]")).size() == 1)
            return true;

        else
            return waitForOrder(orderNum);
    }

    public boolean verifyLatestOrderFirst(String orderNum) {
        waitForOrder(orderNum);
        if (getDriver().findElements(By.xpath("//*[@id='orderHistory']//tr[1][contains(.,'" + orderNum + "')]")).size() == 1) {
            return true;
        } else if (getDriver().findElements(By.xpath("//*[@id='orderHistory']//tr[2][contains(.,'" + orderNum + "')]")).size() == 1) {
            return true;
        } else
            return false;

    }

    public boolean clickOrderLink(String orderNum) {
        driver.findElement(By.xpath("//*[@id='orderHistory']//a[contains(.,'" + orderNum + "')]")).click();

        return waitUntilElementDisplayed(orderDetailsTitle, 10);
    }

    public boolean checkAllSectionsDisplayedInOrderDetails() {
        if (isDisplayed(orderDetailsTitle) &&
                isDisplayed(orderSummaryTitle) &&
                isDisplayed(amountBilledTitle) &&
                isDisplayed(paymentSummaryTitle) &&
                isDisplayed(shoppingBagTitle))
            return true;
        else
            return false;
    }

    public boolean checkAllSectionsDisplayedInPointsHistory() {
        staticWait(10);

        if (isDisplayed(lbl_PointsTransaction) &&
                isDisplayed(pointsHistory_HeaderData) &&
                isDisplayed(pointsHistory_Transaction) &&
                isDisplayed(pointsHistory_TotalPoints))
            return true;
        else
            return false;
    }

    public boolean validatePointSection() {
        if (isDisplayed(lnk_ProgramDetails) &&
                isDisplayed(lnk_ProgramFAQs) &&
                isDisplayed(lnk_ProgramTerms) &&
                isDisplayed(convertPointsButton) &&
                isDisplayed(lbl_AvblePts))
            return true;
        else
            return false;

    }

    public boolean couponConversion() {
        int pointsMore = Integer.parseInt(lbl_AvblePts.getText());
        selectDropDownByValue(amtToRedeemDropDown, "5");
        click(convertPointsButton);
        staticWait(10);
        int pointsLess = Integer.parseInt(lbl_AvblePts.getText());

        if (pointsMore > pointsLess)
            return true;
        else
            return false;

    }

    public boolean verifyOrderTransactionSection(String total) {
        waitUntilElementDisplayed(orderHistory_FirstRow, 60);
        if (TextPresent(driver, OrderConfirmationPageActions.Date) &&
                TextPresent(driver, OrderConfirmationPageActions.Number) &&
                orderHistory_FirstRow.getText().contains(total))
            return true;
        else
            return false;
    }


    public boolean clickSaveOnEmployeeIdButton() {
        click(btn_Save);
        return waitUntilElementDisplayed(editEmployeeID, 10);
    }

    public boolean clickEditEmployeeIDLink() {
        click(editEmployeeID);
        return waitUntilElementDisplayed(tcpEmployeeID_Fld);
    }

    public boolean associateIdErrorValidation(String tcpEmployeeId) {
        switch (tcpEmployeeId) {
            case "":
                clearAndFillText(tcpEmployeeID_Fld, tcpEmployeeId);
                clickSaveOnEmployeeIdButton();
                return waitUntilElementDisplayed(err_PleaseEnterAValidAssociateID, 5);

            case "123456":
                clearAndFillText(tcpEmployeeID_Fld, tcpEmployeeId);
                clickSaveOnEmployeeIdButton();
                return waitUntilElementDisplayed(err_InvalidAssociateIDMsg, 5);

            case "1":
                clearAndFillText(tcpEmployeeID_Fld, tcpEmployeeId);
                clickSaveOnEmployeeIdButton();
                return waitUntilElementDisplayed(check_DisplayError, 5);

            default:
                clearAndFillText(tcpEmployeeID_Fld, tcpEmployeeId);
                clickSaveOnEmployeeIdButton();
                return waitUntilElementDisplayed(err_ThisEmployeeIDAlreadyExistInAnotherAccount, 5);
        }

    }

    public boolean myAccountPageLinksValidation() {
        if (isDisplayed(lnk_ProgramDetails) &&
                isDisplayed(lnk_FAQs) &&
                isDisplayed(lnk_TermsAndCondition) &&
                isDisplayed(lnk_ApplyToday) &&
                isDisplayed(lbl_AvblePts))
            return true;
        else
            return false;
    }

    public boolean clickOnLinksInMyAccount(WebElement link) {
        click(link);
        if (waitUntilElementDisplayed(page_ProgramDetails, 10) || waitUntilElementDisplayed(page_FAQs, 10)
                || waitUntilElementDisplayed(page_TermsAndCondition, 10))
            click(myAccountLink);
        return waitUntilElementDisplayed(lnk_PaymentGC, 10);
    }

    public void switchToChildWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    public Integer getTotalPoints() {

        int numOfTransaction = eachTransactionPoints.size();
        int sum = 0;

        for (int i = 0; i < numOfTransaction; i++) {
            sum = sum + Integer.parseInt(eachTransactionPoints.get(i).getText());
        }
        return sum;
    }

    public String getPoints() {
        String points = getText(totalPoints);
        String[] tokens = points.split(":");
        points = tokens[1].trim();
        return points;
    }

    public boolean validateTotalPoints(Integer sumOfPoints, Integer givenTotalPoints) {
        if (sumOfPoints.equals(givenTotalPoints))
            return true;
        else
            return false;
    }

    public boolean validateBirthdayClubDetails(String termsContent) {
        click(btn_EditBirthdaySavings);
        waitUntilElementDisplayed(btn_AddChildButton, 5);
        boolean isTermsCheckboxDisplayed = waitUntilElementDisplayed(chk_TermAndConditionInBirthdayClub, 5);

        String getTermsContent = termsBirthdaySavingsContent.getText().replaceAll(" ", "");
        boolean isTermsContentDisplaying = termsContent.replaceAll(" ", "").contains(getTermsContent);

        click(closeBDayModal);

        if (isTermsCheckboxDisplayed && isTermsContentDisplaying)
            return true;

        else
            return false;
    }

    public boolean completeAddress(String addressLine1, String city, String state, String zip) {
        if (waitUntilElementDisplayed(lbl_CompleteAddressLink, 10) == true) {
            click(lbl_CompleteAddressLink);
            completeUSAddressFunction(addressLine1, city, state, zip);
        }
        return true;
    }


    public boolean validateBDayNames(String firstName, String lastName) {
        waitUntilElementDisplayed(myAccountLink, 5);
        click(myAccountLink);
        String firstNameLastName = FirstNameLastName.getText().replace(" ", "").trim();
        boolean validateNames = firstNameLastName.contains(firstName + lastName);
        return validateNames;
    }

    public boolean clickAddNewAddressBook() {
        while (waitUntilElementDisplayed(removeFirstAddress, 10)) {
            removeAddress();
        }
        click(btn_AddNewAddress);
        return waitUntilElementDisplayed(newAddress_FirstName, 10);
    }

    public void removeAddress() {
        click(removeFirstAddress);
        boolean deleteOverlay = waitUntilElementDisplayed(deleteAddressOverlay, 10);
        if (deleteOverlay == true) {
            click(okButton);
        }
        staticWait(5000);

    }

    public boolean removeExistingAddress() {

        click(lnk_AddressBook);
        if ((waitUntilElementDisplayed(removeFirstAddress, 5))) {
            while (waitUntilElementDisplayed(removeFirstAddress, 10)) {
                click(removeFirstAddress);
                staticWait(5000);
                click(btnOverlayConfirmRemove);
                staticWait(5000);
            }
        }
        return waitUntilElementDisplayed(btn_AddNewAddress, 10);
    }

    public boolean fieldErrorValidationPayment() {
        waitUntilElementDisplayed(addPayment_CardNumber, 2);
        clearAndFillText(addPayment_CardNumber, "");
        click(saveChanges);
        waitUntilElementDisplayed(inlineErrMsgCard, 3);
        click(cancelButton);
        return waitUntilElementDisplayed(editPaymentAddressLnk, 3);
    }

    public boolean cancelDeletePayment() {
//        waitUntilElementDisplayed(closeModal, 2);
//        click(closeModal);
        waitUntilElementDisplayed(cancelButton, 3);
        click(cancelButton);
        return waitUntilElementDisplayed(editPaymentAddressLnk);
    }

    public boolean validateProfileInfoTabContents() {
        if (isDisplayed(profileInfo) &&
                isDisplayed(editLnkOnPersonalInfo) &&
                isDisplayed(changePwdBtn) &&
                (isDisplayed(addBdayInfoBtn) || isDisplayed(editBdayInfoBtn)))
            return true;
        else
            return false;
    }

    public boolean clickProfileInfoLink() {
        scrollToTheTopHeader(profileInfoLnk);
        staticWait(3000);
        click(profileInfoLnk);
        staticWait(2000);
        return waitUntilElementDisplayed(changePwdBtn, 30);
    }

    public boolean validateFieldsInProfInfoBeforeAdded() {
        if (waitUntilElementDisplayed(profileInfoText, 5) &&
                waitUntilElementDisplayed(editLnkOnPersonalInfo, 5) &&
                waitUntilElementDisplayed(changePwdBtn, 5) &&
                waitUntilElementDisplayed(addBdayInfoBtn, 5))
            return true;
        else
            return false;
    }

    public boolean validateFieldsInProfInfoAfterAdded() {
        if (waitUntilElementDisplayed(profileInfoText, 5) &&
                waitUntilElementDisplayed(editLnkOnPersonalInfo, 5) &&
                waitUntilElementDisplayed(changePwdBtn, 5) &&
                waitUntilElementDisplayed(editBdayInfoBtn, 5))
            return true;
        else
            return false;
    }

    public boolean changePassword(String currentPwd, String newPwd, String confirmPwd) {
//        click(changePwdBtn);
//        waitUntilElementDisplayed(currentPwdField, 20);
        clearAndFillText(currentPwdField, currentPwd);
        staticWait(100);
        clearAndFillText(newPwdField, newPwd);
        waitUntilElementDisplayed(successMsg, 5);
        clearAndFillText(confirmPwdField, confirmPwd);
        waitUntilElementDisplayed(successMsg, 5);
        staticWait(1000);
        clearAndFillText(newPwdField, newPwd);
        scrollUpToElement(saveButton);
        click(saveButton);
        staticWait(1000);
        return waitUntilElementDisplayed(updatedSuccessMsg, 30);
    }

    public boolean validateErrorMessagesTab(String currentPwd, String newPwd, String confirmPwd) {
        waitUntilElementDisplayed(currentPwdField, 5);
        currentPwdField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(currentPwdErr, 5);
        boolean currentPwdError = getText(currentPwdErr).contains(currentPwd);

        waitUntilElementDisplayed(newPwdField, 5);
        newPwdField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(newPwdErr, 5);
        boolean newPwdError = getText(newPwdErr).contains(newPwd);

        waitUntilElementDisplayed(confirmPwdField, 5);
        confirmPwdField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(confirmPwdErr, 5);
        boolean confirmPwdError = getText(confirmPwdErr).contains(confirmPwd);

        if (currentPwdError && newPwdError && confirmPwdError)
            return true;
        else
            return false;
    }

    public boolean validateErrMsgSplChar(String currentPwd, String newPwd) {
        waitUntilElementDisplayed(currentPwdField, 5);
        clearAndFillText(currentPwdField, "!@#$%");
        waitUntilElementDisplayed(currentPwdSplErr, 5);
        boolean currentPwdError = getText(currentPwdSplErr).contains(currentPwd);

        waitUntilElementDisplayed(newPwdField, 5);
        clearAndFillText(newPwdField, "!@#$%");
        waitUntilElementDisplayed(newPwdSplErr, 5);
        boolean newPwdError = getText(newPwdSplErr).contains(newPwd);

        if (currentPwdError && newPwdError)
            return true;
        else
            return false;
    }

    public boolean showHideLinkCurrentPassword(String currentPwd) {
        waitUntilElementDisplayed(currentPwdField, 4);
        clearAndFillText(currentPwdField, currentPwd);
        staticWait();
        if (getDriver().findElements(By.cssSelector(".tooltip-container")).size() == 1) {
//            click(driver.findElement(By.xpath(".//button[@class='button-info']")));
            clickElementByClass("button-info");
//            click(currentPwdField);
        }

        click(currentPwdShowHideLink);
        boolean checkShow = getAttributeValue(currentPwdField, "value").contains(currentPwd) && getText(currentPwdShowHideLink).equalsIgnoreCase("hide");
        click(currentPwdShowHideLink);
        boolean checkHide = (!(getText(currentPwdField).contains(currentPwd))) && getText(currentPwdShowHideLink).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }

    public boolean showHideLinkNewPassword(String newPwd) {
        waitUntilElementDisplayed(newPwdField, 4);
        clearAndFillText(newPwdField, newPwd);
        click(newPwdShowHideLink);
        boolean checkShow1 = getAttributeValue(newPwdField, "value").contains(newPwd) && getText(newPwdShowHideLink).equalsIgnoreCase("hide");
        click(newPwdShowHideLink);
        boolean checkHide1 = (!(getText(newPwdField).contains(newPwd))) && getText(newPwdShowHideLink).equalsIgnoreCase("show");
        return checkShow1 && checkHide1;
    }

    public boolean showHideLinkConfirmPassword(String confirmPwd) {
        waitUntilElementDisplayed(confirmPwdField, 4);
        clearAndFillText(confirmPwdField, confirmPwd);
        click(confirmPwdShowHideLink);
        boolean checkShow2 = getAttributeValue(confirmPwdField, "value").contains(confirmPwd) && getText(confirmPwdShowHideLink).equalsIgnoreCase("hide");
        click(confirmPwdShowHideLink);
        boolean checkHide2 = (!(getText(confirmPwdField).contains(confirmPwd))) && getText(confirmPwdShowHideLink).equalsIgnoreCase("show");
        return checkShow2 && checkHide2;
    }

    public boolean clickChangePwdBtn() {
        waitUntilElementDisplayed(changePwdBtn, 20);
        click(changePwdBtn);
        return waitUntilElementDisplayed(currentPwdField, 30);
    }

    public boolean passwordMisMatchErr(String currentPwd, String newPwd, String confirmPwd, String confirmErrMsg) {
        waitUntilElementDisplayed(currentPwdField, 5);
        clearAndFillText(currentPwdField, currentPwd);
        waitUntilElementDisplayed(newPwdField, 4);
        clearAndFillText(newPwdField, newPwd);
        waitUntilElementDisplayed(confirmPwdField, 4);
        clearAndFillText(confirmPwdField, confirmPwd);
        boolean confirmPwdError = getText(misMatchErr).contains(confirmErrMsg);
        if (confirmPwdError)
            return true;
        else
            return false;
    }

    public boolean enterSamePwdAndValidateErrMsg(String currentPwd, String newPwd, String confirmPwd, String errorMsg) {
        waitUntilElementDisplayed(currentPwdField, 5);
        clearAndFillText(currentPwdField, currentPwd);
        waitUntilElementDisplayed(newPwdField, 5);
        clearAndFillText(newPwdField, newPwd);
        waitUntilElementDisplayed(confirmPwdField, 5);
        clearAndFillText(confirmPwdField, confirmPwd);
        scrollUpToElement(saveButton);
        click(saveButton);
        staticWait(3000);
        waitUntilElementDisplayed(previousPwdErr, 20);
        boolean validateErrMsg = getText(previousPwdErr).contains(errorMsg);
        return validateErrMsg;
    }

    public boolean moreThanEightChar(String currentPwd, String newPwd, String confirmPwd, String errorMsg) {
        waitUntilElementDisplayed(currentPwdField, 5);
        clearAndFillText(currentPwdField, currentPwd);
        waitUntilElementDisplayed(newPwdField, 5);
        clearAndFillText(newPwdField, newPwd);
        staticWait(3000);
        waitUntilElementDisplayed(inlineErrMsg, 20);
        waitUntilElementDisplayed(confirmPwdField, 5);
        clearAndFillText(confirmPwdField, confirmPwd);
        boolean validateErrMsg = getText(inlineErrMsg).contains(errorMsg);
        return validateErrMsg;
    }

    public boolean editProfileInfo(String fname, String lname, String mobileNo) {

        waitUntilElementDisplayed(editLnkOnPersonalInfo, 5);
        click(editLnkOnPersonalInfo);
        waitUntilElementDisplayed(firstNameField, 1);
        clearAndFillText(firstNameField, fname);
        waitUntilElementDisplayed(lastNameField, 1);
        clearAndFillText(lastNameField, lname);
     //   waitUntilElementDisplayed(hintText, 1);
//        waitUntilElementDisplayed(emailIdField, 10);
//        clearAndFillText(emailIdField, emailID);
//        waitUntilElementDisplayed(mobileNoField, 10);
//        clearAndFillText(mobileNoField, mobileNo);

        click(saveButton);
        staticWait(4000);
        if(waitUntilElementDisplayed(addressVerificationContinue, 10)){
            click(addressVerificationContinue);
        }
        staticWait(5000);
        return waitUntilElementDisplayed(updatedSuccessMsg, 20);
    }

    public boolean truncatedNameDisplay(String name, String fName) {
        waitUntilElementDisplayed(helloFNameDisplay, 2);
        waitUntilElementDisplayed(firstNamePI, 2);
        String name1 = getText(firstNamePI);
        String trunckname = getText(helloFNameDisplay).replaceAll("Hi ", "");
        if (name.equals(name1) && fName.equals(trunckname)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean invalidAssociateIDErr(String associateID, String associateErr, String address, String city, String state, String zip) {
        select(associateID_CheckBox, associateID_ChkBoxVal);
        staticWait(4000);
        waitUntilElementDisplayed(associateIDField, 5);
        clearAndFillText(associateIDField, associateID);
        clearAndFillText(mailingAdd_Line1, address);
        clearAndFillText(mailingAdd_City, city);
        clearAndFillText(mailingAdd_Zip, zip);
        selectDropDownByValue(mailingAdd_StateDropDown, state);
        click(saveButton);
        if(waitUntilElementDisplayed(addressVerificationContinue, 10)){
            click(addressVerificationContinue);
        }
        staticWait(4000);
        //boolean associateIDError = waitUntilElementDisplayed(invalidID_Error, 10);
        boolean associateIDError = getText(associateIDErr).contains(associateErr);
        if (associateIDError)
            return true;
        else
            return false;
    }

    public boolean validateProfileInfoTabErrMsg(String fnameErr, String lnameErr, String emailIDErr, String mobileNoErr, String associateErr,
                                                String streetErr,String cityErr1, String zipErr1 ) {

        waitUntilElementDisplayed(editLnkOnPersonalInfo, 10);
        click(editLnkOnPersonalInfo);
        waitUntilElementDisplayed(firstNameField, 5);
        clearAndFillText(firstNameField, "");
        firstNameField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(firstNameErr, 5);
        boolean firstNameError = getText(firstNameErr).contains(fnameErr);

        waitUntilElementDisplayed(lastNameField, 5);
        clearAndFillText(lastNameField, "");
        lastNameField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(lastNameErr, 5);
        boolean lastNameError = getText(lastNameErr).contains(lnameErr);

        waitUntilElementDisplayed(emailIdField, 5);
        clearAndFillText(emailIdField, "");
        emailIdField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailErr, 5);
        boolean emailIDError = getText(emailErr).contains(emailIDErr);

        waitUntilElementDisplayed(mobileNoField, 5);
        clearAndFillText(mobileNoField, "");
        mobileNoField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(mobileErr, 5);
        boolean mobileNoError = getText(mobileErr).contains(mobileNoErr);

        click(associateID_CheckBox);
        waitUntilElementDisplayed(associateIDField, 5);
        clearAndFillText(associateIDField, "");
        associateIDField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(associateIDErr, 5);
        boolean associateIDError = getText(associateIDErr).contains(associateErr);

        waitUntilElementDisplayed(mailingAdd_Line1);
        clearAndFillText(mailingAdd_Line1,"");
        mailingAdd_Line1.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(mailingAddress_StreetErr);
        boolean addressLineErr = getText(mailingAddress_StreetErr).contains(streetErr);

        waitUntilElementDisplayed(mailingAdd_City);
        clearAndFillText(mailingAdd_City,"");
        mailingAdd_City.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(mailingAddress_CityErr);
        boolean cityLineErr = getText(mailingAddress_CityErr).contains(cityErr1);

        waitUntilElementDisplayed(mailingAdd_Zip);
        clearAndFillText(mailingAdd_Zip,"");
        mailingAdd_Zip.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(mailingAddress_ZipErr);
        boolean zipLineErr = getText(mailingAddress_ZipErr).contains(zipErr1);

        if (firstNameError && lastNameError && emailIDError && mobileNoError && associateIDError && addressLineErr &&
                cityLineErr && zipLineErr)
            return true;
        else
            return false;
    }

    public boolean validateProfileInfoSplCharErrMsg(String fnameErr, String lnameErr, String emailIDErr, String mobileNoErr, String associateErr,
                                                    String streetErr,String cityErr1, String zipErr1) {

        //       click(edit_Lnk);
        waitUntilElementDisplayed(firstNameField, 5);
        clearAndFillText(firstNameField, "!@#$%");
        waitUntilElementDisplayed(firstNameSplErr, 5);
        boolean firstNameError = getText(firstNameSplErr).contains(fnameErr);

        waitUntilElementDisplayed(lastNameField, 5);
        clearAndFillText(lastNameField, "!@#$%");
        waitUntilElementDisplayed(lastNameSplErr, 5);
        boolean lastNameError = getText(lastNameSplErr).contains(lnameErr);

        waitUntilElementDisplayed(emailIdField, 5);
        clearAndFillText(emailIdField, "!@#$%");
        waitUntilElementDisplayed(emailSplErr, 5);
        boolean emailIDError = getText(emailSplErr).contains(emailIDErr);

        waitUntilElementDisplayed(mobileNoField, 5);
        clearAndFillText(mobileNoField, "!@#$%");
        waitUntilElementDisplayed(mobileSplErr, 5);
        boolean mobileNoError = getText(mobileSplErr).contains(mobileNoErr);

        waitUntilElementDisplayed(associateIDField, 5);
        clearAndFillText(mobileNoField, "!@#$%");
        waitUntilElementDisplayed(associateIDErr, 5);
        boolean associateIDError = getText(associateIDErr).contains(associateErr);

        waitUntilElementDisplayed(mailingAdd_Line1);
        clearAndFillText(mailingAdd_Line1,"!@#$%");
        waitUntilElementDisplayed(mailingAddress_StreetErr);
        boolean addressLineErr = getText(mailingAddress_StreetErr).contains(streetErr);

        waitUntilElementDisplayed(mailingAdd_City);
        clearAndFillText(mailingAdd_City,"!@#$%");
        waitUntilElementDisplayed(mailingAddress_CityErr);
        boolean cityLineErr = getText(mailingAddress_CityErr).contains(cityErr1);

        waitUntilElementDisplayed(mailingAdd_Zip);
        clearAndFillText(mailingAdd_Zip,"!@#$%");
        waitUntilElementDisplayed(mailingAddress_ZipErr);
        boolean zipLineErr = getText(mailingAddress_ZipErr).contains(zipErr1);

        if (firstNameError && lastNameError && emailIDError && mobileNoError && associateIDError&& addressLineErr &&
                cityLineErr && zipLineErr)
            return true;
        else
            return false;
    }

    public boolean clickOrdersLink() {
        //staticWait(3000);
        click(ordersLnk);
        isDisplayed(loadingIcon);
        //staticWait(2000);
        return waitUntilElementDisplayed(intOrderLink, 30);
    }

    public boolean validateOrdersPage() {
        if (waitUntilElementDisplayed(orderNoLink) &&
                waitUntilElementDisplayed(totalText) &&
                waitUntilElementDisplayed(orderTitle) &&
                waitUntilElementsAreDisplayed(orderDate, 1) &&
                waitUntilElementsAreDisplayed(orderType, 1) &&
                waitUntilElementsAreDisplayed(orderStatus, 1) &&
                waitUntilElementsAreDisplayed(orderTotalAmount, 1))
            return true;
        else
            return false;
    }

    public boolean clickLinksInOrderStatus() {
        waitUntilElementDisplayed(intOrderLink, 5);
        waitUntilElementDisplayed(canadaOrderLink, 3);
        staticWait(2000);
        scrollDownToElement(canadaOrderLink);
        click(canadaOrderLink);
        waitUntilElementDisplayed(usOrderLink, 3);
        click(usOrderLink);
        waitUntilElementDisplayed(canadaOrderLink, 3);
        click(intOrderLink);
        return (waitUntilElementDisplayed(intOrderLink));
    }

    public boolean clickLinksInOrderStatusCA() {
        waitUntilElementDisplayed(intOrderLink, 5);
        staticWait(2000);
        scrollDownToElement(usOrderLink);
        click(usOrderLink);
        waitUntilElementDisplayed(canadaOrderLink, 3);
        click(canadaOrderLink);
        waitUntilElementDisplayed(usOrderLink, 3);
        click(intOrderLink);
        return (waitUntilElementDisplayed(intOrderLink));
    }

    public boolean intOrderRedirection() {
        String url = "www.borderfree.com/order-status/";
        String currentUrl = getCurrentURL();
        return currentUrl.contains(url);
    }

    public boolean clickOnOrderIdLink(String orderNum) {
        waitUntilElementDisplayed(orderNoByOrder(orderNum), 10);
        click(orderNoByOrder(orderNum));
        staticWait();
        return waitUntilElementDisplayed(orderNoLink, 5);
    }

    public String getOrderId() {
        String orderId = getText(orderNoLink).replaceAll("[^0-9]", "");
        return orderId;
    }


    public boolean validateErrInZipField(String zipMoreThanFive, String zipLessThanFive, String splCharZip, String errZip) {

        boolean checkErrZipFld = /*verifyZipCodeErrorBillInfo(zipMoreThanFive, errZip) &&*/
                verifyZipCodeErrorBillInfo(zipLessThanFive, errZip) &&
                        verifyZipCodeErrorBillInfo(splCharZip, errZip);

        return checkErrZipFld;
    }

    public boolean validateCardFieldErrWithErr(String invalidCCNum, String errCCMsg, String errExp) {

        scrollUpToElement(addPayment_CardNumber);
        waitUntilElementDisplayed(addPayment_CardNumber, 10);
        clearAndFillText(addPayment_CardNumber, invalidCCNum);
        scrollDownToElement(btnAddCard);
        click(btnAddCard);
        boolean invalidCCErr = waitUntilElementDisplayed(errorMessageByFieldError(errCCMsg), 10);

        Date date = new Date();
        SimpleDateFormat sfMonth = new SimpleDateFormat("M");
        String currentMonth = sfMonth.format(date).toLowerCase();
        int crntMonth = Integer.parseInt(currentMonth);
        int prvusMonth = crntMonth - 1;
        String previousMonth = Integer.toString(prvusMonth);

        SimpleDateFormat sfYear = new SimpleDateFormat("YYYY");
        String currentYear = sfYear.format(date).toLowerCase();
        waitUntilElementDisplayed(addExpMonthLbl, 10);
        if (crntMonth == 1) {
        /*    selectDropDownByValue(addPayment_expmonth, "12");
            selectDropDownByValue(addPayment_expyr, currentYear);
        */
            boolean invalidMonthYear = waitUntilElementDisplayed(errorMessageByFieldError(errCCMsg), 10);
            return invalidCCErr && invalidMonthYear;

        } else {
            selectDropDownByValue(addPayment_expMonthErrDrop, previousMonth);
            selectDropDownByValue(addPayment_expyr, currentYear);
        }

        scrollDownToElement(btnAddCard);
        click(btnAddCard);

        boolean invalidMonthYear = waitUntilElementDisplayed(errorMessageByFieldError(errCCMsg), 10);

        return invalidCCErr && invalidMonthYear;
    }

    public boolean validateCardFieldErrWithoutErr(String invalidCCNum, String errCCMsg, String errExp) {

        scrollUpToElement(addPayment_CardNumber);
        waitUntilElementDisplayed(addPayment_CardNumber, 10);
        clearAndFillText(addPayment_CardNumber, invalidCCNum);
        scrollDownToElement(btnAddCard);
        click(btnAddCard);
        boolean invalidCCErr = waitUntilElementDisplayed(errorMessageByFieldError(errCCMsg), 10);

        Date date = new Date();
        SimpleDateFormat sfMonth = new SimpleDateFormat("M");
        String currentMonth = sfMonth.format(date).toLowerCase();
        int crntMonth = Integer.parseInt(currentMonth);
        int prvusMonth = crntMonth - 1;
        String previousMonth = Integer.toString(prvusMonth);

        SimpleDateFormat sfYear = new SimpleDateFormat("YYYY");
        String currentYear = sfYear.format(date).toLowerCase();
        waitUntilElementDisplayed(addExpMonthLbl, 10);
        if (crntMonth == 1) {
        /*    selectDropDownByValue(addPayment_expmonth, "12");
            selectDropDownByValue(addPayment_expyr, currentYear);
        */
            boolean invalidMonthYear = waitUntilElementDisplayed(errorMessageByFieldError(errCCMsg), 10);
            return invalidCCErr && invalidMonthYear;

        } else {

            selectDropDownByValue(addPayment_expmonth, previousMonth);
            selectDropDownByValue(addPayment_expyr, currentYear);

            scrollDownToElement(btnAddCard);
            click(btnAddCard);

            boolean invalidMonthYear = waitUntilElementDisplayed(errorMessageByFieldError(errCCMsg), 10);

            return invalidCCErr && invalidMonthYear;
        }
    }

    public boolean cancelAddPaymentMethod() {
        waitUntilElementDisplayed(btnCancelAddCard, 10);
        click(btnCancelAddCard);
        staticWait(5000);
        return waitUntilElementDisplayed(btn_AddNewCard, 30);
    }

    public boolean clickAddressLink() {
        waitUntilElementDisplayed(lnk_AddressBook, 5);
        click(lnk_AddressBook);
        return waitUntilElementDisplayed(addressBookText, 20);
    }

    public boolean clickAddressBookBreadcrumb() {
        scrollUpToElement(addressBookBreadCrumb);
        waitUntilElementDisplayed(addressBookBreadCrumb, 5);
        click(addressBookBreadCrumb);
        return waitUntilElementDisplayed(btn_AddNewAddress, 20);
    }

    public boolean validateFieldsInAddress() {
        if (waitUntilElementDisplayed(newAddress_FirstName, 10) &&
                waitUntilElementDisplayed(newAddress_LastName, 10) &&
                waitUntilElementDisplayed(newAddress_AddressLine1, 10) &&
                waitUntilElementDisplayed(newAddress_AddressLine2, 10) &&
                waitUntilElementDisplayed(newAddress_City, 10) &&
                waitUntilElementDisplayed(newAddress_ZipCode, 10) &&
                waitUntilElementDisplayed(newAddress_Country, 10) &&
                waitUntilElementDisplayed(newAddress_PhoneNumeber, 10) &&
                waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox, 10) &&
                waitUntilElementDisplayed(newAddressCancelBtn, 10) &&
                waitUntilElementDisplayed(newAddress_AddNewAddressBtn, 10) &&
                waitUntilElementDisplayed(addressBookBreadCrumb, 10))
            return true;
        else
            return false;
    }

    public boolean validateAddressErrMsgTab(String firstName, String lastName, String address, String city, String state, String zipCode, String phone) {

        waitUntilElementDisplayed(newAddress_FirstName, 5);
        newAddress_FirstName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(fnameErr, 5);
        boolean firstNameErr = getText(fnameErr).contains(firstName);
        staticWait();

        waitUntilElementDisplayed(newAddress_LastName, 5);
        newAddress_LastName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(lnameErr, 5);
        boolean lastNameErr = getText(lnameErr).contains(lastName);
        staticWait();

        waitUntilElementDisplayed(newAddress_AddressLine1, 5);
        newAddress_AddressLine1.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(address1Err, 5);
        boolean addressErr = getText(address1Err).contains(address);

        waitUntilElementDisplayed(newAddress_City, 5);
        newAddress_City.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(cityErr, 5);
        boolean cityError = getText(cityErr).contains(city);

        waitUntilElementDisplayed(newAddress_State, 5);
        newAddress_State.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(stateErr, 5);
        boolean stateError = getText(stateErr).contains(state);
        staticWait();

        waitUntilElementDisplayed(newAddress_ZipCode, 5);
        newAddress_ZipCode.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(zipErr, 5);
        boolean zipCodeErr = getText(zipErr).contains(zipCode);
        staticWait();

        waitUntilElementDisplayed(newAddress_PhoneNumeber, 5);
        newAddress_PhoneNumeber.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(phoneErr, 5);
        boolean mobileNoErr = getText(phoneErr).contains(phone);
        staticWait();

        if (firstNameErr && lastNameErr && addressErr && cityError && stateError && zipCodeErr && mobileNoErr)
            return true;
        else
            return false;
    }

    public boolean validateAddressSplCharErrMsg(String firstName, String lastName, String address1, String address2, String city, String zipCode, String phone) {


        waitUntilElementDisplayed(newAddress_FirstName, 5);
        clearAndFillText(newAddress_FirstName, "!@#$%");
        newAddress_FirstName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(fnameSplErr, 5);
        boolean firstNameErr = getText(fnameSplErr).contains(firstName);
        staticWait();

        waitUntilElementDisplayed(newAddress_LastName, 5);
        clearAndFillText(newAddress_LastName, "!@#$%");
        newAddress_LastName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(lnameSplErr, 5);
        boolean lastNameErr = getText(lnameSplErr).contains(lastName);
        staticWait();

        waitUntilElementDisplayed(newAddress_AddressLine1, 5);
        clearAndFillText(newAddress_AddressLine1, "!@#$%");
        newAddress_AddressLine1.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(address1SplErr, 5);
        boolean addressErr1 = getText(address1SplErr).contains(address1);
        staticWait();

        waitUntilElementDisplayed(newAddress_City, 5);
        clearAndFillText(newAddress_City, "!@#$%");
        newAddress_City.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(citySplErr, 5);
        boolean cityError = getText(citySplErr).contains(city);
        staticWait();

        waitUntilElementDisplayed(newAddress_ZipCode, 5);
        clearAndFillText(newAddress_ZipCode, "!@#$%");
        newAddress_ZipCode.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(zipSplErr, 5);
        boolean zipCodeErr = getText(zipSplErr).contains(zipCode);
        staticWait();

        waitUntilElementDisplayed(newAddress_PhoneNumeber, 5);
        clearAndFillText(newAddress_PhoneNumeber, "!@#$%");
        newAddress_PhoneNumeber.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(phoneSplErr, 5);
        boolean mobileNoErr = getText(phoneSplErr).contains(phone);
        staticWait();

        if (firstNameErr && lastNameErr && addressErr1 && cityError && zipCodeErr && mobileNoErr)
            return true;
        else
            return false;
    }

    public boolean clickEditLinkAddress() {
        waitUntilElementDisplayed(editShipAddressLink, 10);
        click(editShipAddressLink);
        return waitUntilElementDisplayed(newAddress_FirstName, 10);
    }

    public boolean clickCancelButton() {
        waitUntilElementDisplayed(cancelBtn, 10);
        click(cancelBtn);
        staticWait(1000);
        return waitUntilElementDisplayed(btn_AddNewAddress, 10);
    }

    public boolean selectAddNewBillAddress() {
        if (waitUntilElementDisplayed(expandAdrBookDropDownBillInfo, 10)) {
            click(expandAdrBookDropDownBillInfo);
            scrollDownToElement(addNewBillingAddressOption);
            click(addNewBillingAddressOption);
        }

        return waitUntilElementDisplayed(newAddress_FirstName, 10);

    }

    public boolean clickPaymentAndGCLink() {
        click(lnk_PaymentGC);
        return waitUntilElementDisplayed(btn_AddNewCard, 30);
    }

    public boolean clickAddNewCardButton() {
        click(btn_AddNewCard);
        return waitUntilElementDisplayed(btnAddCard, 30);
    }

    public boolean addCCWithoutRemovingExistingCC(String fName, String lName, String address, String city, String state, String zipCode,
                                                  String phone, String card, String card_no, String exp_month, String successMsgContent) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);
        clickPaymentAndGCLink();
//        click(lnk_PaymentGC);
//        staticWait(3000);
        clickAddNewCardButton();

        if (waitUntilElementDisplayed(newAddress_FirstName, 20)) {
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        }

        staticWait(3000);
        clearAndFillText(addPayment_CardNumber, card_no);

        switch (card) {

            case "VISA":
                boolean visaImgDisplayed = waitUntilElementDisplayed(imgVISA, 30);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return visaImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "Master Card":
                boolean mcImgDisplayed = waitUntilElementDisplayed(imgMC, 30);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return mcImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "Discover":
                boolean discoverImgDisplayed = waitUntilElementDisplayed(imgDiscover, 30);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return discoverImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "AMEX":
                boolean amexImgDisplayed = waitUntilElementDisplayed(imgAMEX, 30);
                if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
                    selectDropDownByValue(addPayment_expmonth, exp_month);
                    selectDropDownByVisibleText(addPayment_expyr, expYear);
                }
                click(btnAddCard);
                staticWait(5000);
                waitUntilElementDisplayed(editPaymentAddressLnk, 10);
                return amexImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);

            case "PLCC":
                boolean plccImgDisplayed = waitUntilElementDisplayed(imgPLCC, 30);
                click(btnAddCard);
                waitUntilElementDisplayed(editPaymentAddressLnk, 30);
                return plccImgDisplayed && verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);
        }

        return verifyDefaultCardDetails(card, card_no, exp_month, expYear, successMsgContent);
    }

    public boolean areAllBillingFieldsDisplayed() {
        if (!(waitUntilElementDisplayed(newAddress_FirstName, 5))) {
            clickAddressDropdown();
            clickAddNewAddress();
        }

        boolean isFNameFieldDisplayed = waitUntilElementDisplayed(newAddress_FirstName);
        boolean isLNameFieldDisplayed = waitUntilElementDisplayed(newAddress_LastName);
        boolean isAddress1FieldDisplayed = waitUntilElementDisplayed(newAddress_AddressLine1);
        boolean isAddress2FieldDisplayed = waitUntilElementDisplayed(newAddress_AddressLine2);
        boolean isCityFieldDisplayed = waitUntilElementDisplayed(newAddress_City);
        boolean isStateFieldDisplayed = waitUntilElementDisplayed(newAddress_StateFld);
        boolean isZipFieldDisplayed = waitUntilElementDisplayed(newAddress_ZipCode);
        boolean isCountryFieldDisplayed = waitUntilElementDisplayed(newAddress_CountryFld);
        boolean isAddCardBtnDisplayed = waitUntilElementDisplayed(btnAddCard);
        boolean isCancelBtnDisplayed = waitUntilElementDisplayed(btnCancelAddCard);

        if (isFNameFieldDisplayed && isLNameFieldDisplayed
                && isAddress1FieldDisplayed && isAddress2FieldDisplayed
                && isCityFieldDisplayed && isStateFieldDisplayed
                && isZipFieldDisplayed && isCountryFieldDisplayed
                && isAddCardBtnDisplayed && isCancelBtnDisplayed)

            return true;

        else
            return false;
    }

    public boolean clickAccOverviewLink() {
        waitUntilElementDisplayed(lnk_AccountOverView, 30);
        click(lnk_AccountOverView);
        return waitUntilElementDisplayed(addPayment_Btn, 20) && waitUntilElementDisplayed(addAddress_Btn, 20);
    }

    public boolean validateAccOverview() {
        waitUntilElementDisplayed(viewRewardsLink, 4);
        if (isDisplayed(viewRewardsLink) &&
                isDisplayed(pointsSection) &&
                scrollDownUntilElementDisplayed(addAddress_Btn) &&
                isDisplayed(addPayment_Btn))
            return true;
        else
            return false;
    }

    public boolean clickViewAllRewardsLink() {
        waitUntilElementDisplayed(viewRewardsLink, 20);
        scrollToTheTopHeader(viewRewardsLink);
        click(viewRewardsLink);
        return waitUntilElementDisplayed(couponsLink, 20);
    }

    public boolean validateRewardsPage() {
        waitUntilElementDisplayed(pointsHistoryLink, 4);
        if (isDisplayed(pointsHistoryLink) &&
                isDisplayed(rewardsLink))
            return true;
        else
            return false;
    }

    public boolean clickAddAddressBtn() {
        waitUntilElementDisplayed(addAddress_Btn, 3);
        scrollDownToElement(addAddress_Btn);
        click(addAddress_Btn);
        return waitUntilElementDisplayed(cancelBtn, 20);
    }

    public boolean clickAddPayment() {
        waitUntilElementDisplayed(addPayment_Btn, 20);
        click(addPayment_Btn);
        return waitUntilElementDisplayed(btnAddCard, 20);
    }

    public boolean clickMyRewardsLink() {
        waitUntilElementDisplayed(myRewardsLink, 30);
        click(myRewardsLink);
        return waitUntilElementDisplayed(breadCrumbContainer, 10);
    }

    public boolean validateHeaders() {
        staticWait(1000);
        if (isDisplayed(rewardsLink) &&
                isDisplayed(pointsHistoryLink) &&
                isDisplayed(couponsLink))
            return true;
        else
            return false;
    }

    public boolean clickAddressDropdown() {
        waitUntilElementDisplayed(dropDownLink, 20);
        click(dropDownLink);
        return waitUntilElementDisplayed(addNewAddress, 10);
    }

    public boolean clickAddNewAddress() {
        waitUntilElementDisplayed(addNewAddress, 20);
        click(addNewAddress);
        return waitUntilElementDisplayed(btnAddCard, 10);
    }

    public boolean validateSuccessMessage(String msgContent) {
        waitUntilElementDisplayed(successMessageBox, 10);
        String msgContentDisplayed = getText(successMessageBox);
        return msgContentDisplayed.contains(msgContent);
    }

    public boolean clickAddChildButton() {
        staticWait(8000);
        scrollUpToElement(addAChildBtn);
        waitUntilElementDisplayed(addAChildBtn, 5);
        click(addAChildBtn);
        staticWait(8000);
        return waitUntilElementDisplayed(cancelInfoBtn, 20);
    }

    public boolean clickAddChildByPosition(int position) {
        staticWait(5000);
        WebElement addChild = addChildByPosition(position);
        click(addChild);
        staticWait(5000);
        return waitUntilElementDisplayed(addChildBtn, 20);
    }

    public boolean removeAddedChildButton() {
        staticWait(2000);
        click(childRemoveBtn);

        if (isDisplayed(yesRemoveBtn)) {
            click(yesRemoveBtn);
        }
        staticWait(2000);
        return waitUntilElementDisplayed(cancelInfoBtn, 20);
    }

    public boolean clickRemoveByPosition(int position) {
        staticWait(5000);
        scrollUpToElement(childRemoveBtn);
        waitUntilElementDisplayed(childRemoveBtn, 5);
        WebElement removeChild = removeLinkByPosition(position);
        click(removeChild);
        if (isDisplayed(yesRemoveBtn)) {
            click(yesRemoveBtn);
        }
        staticWait(5000);
        return waitUntilElementDisplayed(updatedSuccessMsg, 20);
    }

    public boolean clickAddBirthdayInfo() {
        if (waitUntilElementDisplayed(addBirthdayInfoBtn, 20)) {
            click(addBirthdayInfoBtn);
        } else {
            click(editBdayInfoBtn);
        }
        staticWait(2000);
        return waitUntilElementDisplayed(addAChildBtn, 20);
    }

    public boolean validateBirthdayFields() {
        waitUntilElementDisplayed(childFname, 30);
        if (waitUntilElementDisplayed(childFname, 10) &&
                waitUntilElementDisplayed(childLname, 10) &&
                waitUntilElementDisplayed(monthDrpDownDisplay, 10) &&
                waitUntilElementDisplayed(yearDrpDownDisplay, 10) &&
                waitUntilElementDisplayed(genderDrpDownDisplay, 10) &&
                waitUntilElementDisplayed(birthTimeStamp, 10) &&
                waitUntilElementDisplayed(childName, 10) &&
                waitUntilElementDisplayed(addChildBtn, 10) &&
                waitUntilElementDisplayed(cancelInfoBtn, 10))
            return true;
        else
            return false;
    }

    public boolean validateBirthdayFieldErrTab(String childNameError, String birthMthErr, String birthYrErr, String genderErr, String fnameErr, String lnameErr, String termsErrMsg) {

        waitUntilElementDisplayed(childName, 5);
        clearAndFillText(childName, "");
        childName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(childnameErr, 10);
        boolean childNameErr = getText(childnameErr).contains(childNameError);

        waitUntilElementDisplayed(birthMth, 5);
        birthMth.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(birthMthError, 10);
        boolean birthMonthErr = getText(birthMthError).contains(birthMthErr);

        waitUntilElementDisplayed(birthYear, 5);
        birthYear.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(birthYrError, 10);
        boolean birthYearErr = getText(birthYrError).contains(birthYrErr);

        waitUntilElementDisplayed(gender, 5);
        gender.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(genderError, 10);
        boolean genderErrorMsg = getText(genderError).contains(genderErr);

        waitUntilElementDisplayed(childFname, 5);
        clearAndFillText(childFname, "");
        childFname.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(childFnameError, 10);
        boolean childFnameErr = getText(childFnameError).contains(fnameErr);

        waitUntilElementDisplayed(childLname, 5);
        clearAndFillText(childLname, "");
        childLname.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(childLnameError, 10);
        boolean childLnameErr = getText(childLnameError).contains(lnameErr);

        checkTermsCheckBox();
        staticWait();
        unCheckTermsCheckBox();
        boolean termsErr = getText(termsError).contains(termsErrMsg);

        if (childNameErr && birthMonthErr && birthYearErr && genderErrorMsg && childFnameErr && childLnameErr && termsErr)
            return true;
        else
            return false;
    }

    public boolean clickBirthCancelButton() {
        waitUntilElementDisplayed(cancelInfoBtn, 20);
        click(cancelInfoBtn);
        return verifyElementNotDisplayed(cancelInfoBtn, 20);
    }

    public boolean unCheckTermsCheckBox() {
        if (!isSelected(termsCheckBox)) {
            click(termsCheckBox);
        }
        return !isSelected(termsCheckBox);
    }

    public boolean checkTermsCheckBox() {
        if (!(isSelected(termsCheckBox))) {
            click(termsCheckBox);
        }
        return isSelected(termsCheckBox);
    }

    public boolean validateBirthdayFieldSplCharErr(String childNameError, String fnameErr, String lnameErr) {

        waitUntilElementDisplayed(childName, 5);
        clearAndFillText(childName, "!@#$%");
        childName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(splbdayError, 5);
        boolean childNameErr = getText(splbdayError).contains(childNameError);

        waitUntilElementDisplayed(childFname, 5);
        click(childFname);
        clearAndFillText(childFname, "!@#$%");
        childFname.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(splbdayFnameError, 10);
        boolean childFnameErr = getText(splbdayFnameError).contains(fnameErr);

        waitUntilElementDisplayed(childLname, 5);
        clearAndFillText(childLname, "!@#$%");
        childLname.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(splbdayLnameError, 10);
        boolean childLnameErr = getText(splbdayLnameError).contains(lnameErr);

        if (childNameErr && childFnameErr && childLnameErr)
            return true;
        else
            return false;
    }

    public boolean clickOnProfileInfoTab() {
        scrollUpToElement(profileInfoText);
        click(profileInfoText);
        return waitUntilElementDisplayed(editLnkOnPersonalInfo);
    }

    public boolean addOneChildBirthdaySaving(String child, String birthMonth, String birthYr, String selectGender, String firstName, String lastName) {

        waitUntilElementDisplayed(childName, 10);
        clearAndFillText(childName, child);
        selectDropDownByVisibleText(birthMth, birthMonth);
        selectDropDownByVisibleText(birthYear, birthYr);
        selectDropDownByVisibleText(gender, selectGender);
        waitUntilElementDisplayed(childFname, 10);
        clearAndFillText(childFname, firstName);
        waitUntilElementDisplayed(childLname, 10);
        clearAndFillText(childLname, lastName);
        checkTermsCheckBox();
        staticWait(2000);
        click(addChildBtn);
        staticWait(1000);
        waitUntilElementDisplayed(child_Name, 30);
        verifyBirthdaySavings(child);
        return waitUntilElementDisplayed(updatedSuccessMsg, 20);
    }

    public boolean clickMPREspot(MPROverlayActions mprOverlayActions) {
        boolean isMprEspot = waitUntilElementDisplayed(myPlaceRewardsEspot, 20);
        if (isMprEspot) {
            click(myPlaceRewardsEspot);
            return waitUntilElementDisplayed(mprOverlayActions.applyButton, 20);
        } else {
            Assert.fail("myPlaceRewardsEspot is not available at my account page " + isMprEspot);
            return false;
        }
    }

    public boolean verifyBirthdaySavings(String childText1) {

        boolean child_Text = getText(child_Name).replaceAll("\"", "").contains(childText1);
        if (child_Text)
            return true;
        else
            return false;
    }

    public boolean clickFindStoreBtn(StoreLocatorPageActions storeLocatorPageActions) {
        staticWait(1000);
        scrollDownToElement(findStoreBtn);
        click(findStoreBtn);
        return waitUntilElementDisplayed(storeLocatorPageActions.searchButton);
    }

    public boolean checkFindStoreBtnDisplayCA() { // As per New change in CA FInd a store will be displayed to the user
        staticWait(1000);
        if (isDisplayed(findStoreBtn)) {
            addStepDescription("User is displayed with Find a Store button in CA store");
            return true;
        } else {
            addStepDescription("User is not displayed with FInd a store button");
            return false;
        }
    }

    public boolean clickOnPersonalInfoCancelButton() {
        click(cancelPerInfoButton);
        return waitUntilElementDisplayed(editLnkOnPersonalInfo, 20);
    }

    public boolean clickOnPersonalInfoEditButton() {
        click(editLnkOnPersonalInfo);
        if (isDisplayed(airmilesSection)) {
            return false;
        } else
            return waitUntilElementDisplayed(saveButton, 20);
    }

    public boolean clickOnPersonalInfoEditButtonCA() {
        click(editLnkOnPersonalInfo);
        if (isDisplayed(airmilesSection)) {
            return true;
        } else {
            addStepDescription("Airmiles number is not displayed in CA store");
            return false;
        }

    }

    public boolean validatePIError(String unsavedError) {

        waitUntilElementDisplayed(saveButton);
        click(saveButton);
        waitUntilElementDisplayed(pInfoError, 2);
        String error = getText(pInfoError).replaceAll("Error:", "");

        if (error.equals(unsavedError)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addAssociateID(String associateID,String address, String city, String state) {
        if (!isEmployeeChkBoxChecked()) {
            click(associateID_CheckBox);
        }
        waitUntilElementDisplayed(associateIDField, 10);
        String associateValue = getAttributeValue(associateIDField, "value");
        if (!associateValue.equalsIgnoreCase(associateID)) {
            clearAndFillText(lastNameField, "Verma");
            staticWait();
            clearAndFillText(associateIDField, associateID);
            //adding Mailing Address
            if(isDisplayed(maxChar_AddLine1)) {
                clearAndFillText(mailingAdd_Line1, address);
                clearAndFillText(mailingAdd_City, city);
                selectDropDownByValue(mailingAdd_StateDropDown, state);
            }
            click(saveButton);
        } else {
            click(cancelPerInfoButton);
        }
        staticWait(4000);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);

        staticWait(5000);
        return verifyElementNotDisplayed(saveButton, 10);

    }

    public boolean removeAssociateID() {
        if (isEmployeeChkBoxChecked()) {
            click(associateID_CheckBox);
            click(saveButton);
        }
        return verifyElementNotDisplayed(saveButton, 10);

    }

    public boolean clickCoustomerService(MyAccountPageActions myAccountPageActions, CreateAccountActions createAccountActions) {
        staticWait(1000);
        //       click(myAccountPageActions.rewardsma_Link);
        // click(myAccountPageActions.pointsHistoryLink);
        waitUntilElementDisplayed(coustomerService);
        click(coustomerService);

        return waitUntilElementDisplayed(createAccountActions.contanctUs_FirstName);
    }

    public boolean mprValidation(String text) {

        boolean validText = getText(mprRewardsNumber).contains(text);
        return validText;

    }

    public boolean mprValidationCA() {

        if (isDisplayed(mprRewardsNumber)) {
            return false;
        } else {
            return true;
        }

    }

    public boolean changePasswordUS(String currentPwd, String newPwd, String confirmPwd) {
//        click(changePwdBtn);
//        waitUntilElementDisplayed(currentPwdField, 20);
        clearAndFillText(currentPwdField, currentPwd);
        staticWait(100);
        clearAndFillText(newPwdField, newPwd);
        waitUntilElementDisplayed(successMsg, 5);
        clearAndFillText(confirmPwdField, confirmPwd);
        waitUntilElementDisplayed(successMsg, 5);
        staticWait(1000);
        scrollUpToElement(saveButton);
        click(saveButton);
        staticWait(1000);
        return waitUntilElementDisplayed(updatedSuccessMsg, 30);
    }

    public boolean clickOnEditPaymentAddressLink() {
        click(editPaymentAddressLnk);
        return waitUntilElementDisplayed(btnCancelAddCard, 20);
    }

    public String getEmailAddrFromProfileInfo() {
        String emailAddr = "No email ID element present";
        try {
            emailAddr = getText(emailID);
        } catch (NullPointerException n) {
            emailAddr = "No email ID element present";
        }
        return emailAddr;
    }

    public boolean updateShipAdd() {
        waitUntilElementDisplayed(updateButtonShipAdd, 3);
        click(updateButtonShipAdd);
        return waitUntilElementDisplayed(firstNameField, 4);
    }

    public boolean addDefaultPaymentWithDifferentCountry(String fName, String lName, String country, String address1,
                                                         String city, String state, String zipCode, String cardType, String cardNo, String cardExpMonthValue, String successMsgContent) {

        boolean addNewPaymentDetails = enterCCAndBillingAddressDiffCountry(fName, lName, country, address1, city, state, zipCode, cardType, cardNo, cardExpMonthValue, successMsgContent);

        if (addNewPaymentDetails) {
            return true;
        } else
            return false;
    }

    public boolean savedCardError(String fName, String lName, String address, String city, String state, String zipCode,
                                  String phone, String card, String card_no, String exp_month, String successMsgContent) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 2);


        if (waitUntilElementDisplayed(newAddress_FirstName, 5)) {
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        } else {
            clickAddressDropdown();
            clickAddNewAddress();
            clearAndFillText(newAddress_FirstName, fName);
            clearAndFillText(newAddress_LastName, lName);
            clearAndFillText(newAddress_AddressLine1, address);
            clearAndFillText(newAddress_City, city);
            selectDropDownByVisibleText(newAddress_State, state);
            clearAndFillText(newAddress_ZipCode, zipCode);
        }

        staticWait(3000);
        clearAndFillText(addPayment_CardNumber, card_no);

        if (waitUntilElementDisplayed(addExpMonthLbl, 10)) {
            selectDropDownByValue(addPayment_expmonth, exp_month);
            selectDropDownByVisibleText(addPayment_expyr, expYear);
        }
        click(btnAddCard);
        staticWait(5000);
        return waitUntilElementDisplayed(usedCardError, 5);

    }

    public boolean validateMaxCharError(String fNameErr, String lNameErr) {

        waitUntilElementDisplayed(newAddress_FirstName, 3);
        clearAndFillText(newAddress_FirstName, "qaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaa aaaq aaaaaaaaa");
        waitUntilElementDisplayed(newAddress_LastName, 3);
        clearAndFillText(newAddress_LastName, "qaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaa aaaq aaaaaaaaa");
        boolean firstNameErr = getText(maxFNameErr).contains(fNameErr);
        boolean lastNameErr = getText(maxLNameErr).contains(lNameErr);
        if ((firstNameErr && lastNameErr) == true) {
            return true;
        }
        return false;
    }

    public boolean validateChildMaxCharError(String fNameErr, String lNameErr) {

        waitUntilElementDisplayed(childFname, 3);
        clearAndFillText(childFname, "qaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaa aaaq aaaaaaaaa");
        waitUntilElementDisplayed(childLname, 3);
        clearAndFillText(childLname, "qaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaa aaaq aaaaaaaaa");
        boolean firstNameErr = getText(maxFNameErr).contains(fNameErr);
        boolean lastNameErr = getText(maxLNameErr).contains(lNameErr);
        if ((firstNameErr && lastNameErr) == true) {
            return true;
        }
        return false;
    }

    public boolean applyRewardsAndCoupons() {
        waitUntilElementDisplayed(viewRewardsLink, 10);
        isDisplayed(viewAndPrintCoupon.get(0));
        isDisplayed(expiryDateCoupon.get(0));

        int applyRewardSize1 = applyToBagButton.size();

        click(viewRewardsLink);
        waitUntilElementsAreDisplayed(applyToBagButton, 5);

        int applyRewardSize = applyToBagButton.size();

        if (applyRewardSize >= 0) {
            waitUntilElementDisplayed(apply10Off, 5);
            click(apply10Off);
        }

        if (waitUntilElementDisplayed(removeButton, 10)) {
            return true;
        } else
            return waitUntilElementDisplayed(couponError, 3);
    }

    public boolean faqRedirection() {
        scrollUpToElement(bdayAddChildButton.get(0));
        click(bdayAddChildButton.get(0));
        waitUntilElementDisplayed(faqLink, 3);
        click(faqLink);
        String url = "help-center/#faq";
        String currentUrl = getCurrentURL();
        if (currentUrl.contains(url)) {
            navigateBack();
            return waitUntilElementsAreDisplayed(bdayAddChildButton, 4);
        } else return false;
    }

    public boolean validateMaxCharErrorPayment(String fNameErr, String lNameErr) {
        scrollDownToElement(btnAddCard);

        if (waitUntilElementDisplayed(newAddress_FirstName, 10)) {
            waitUntilElementDisplayed(btnAddCard, 10);
            click(btnAddCard);
        } else {
            selectAddNewBillAddress();
            waitUntilElementDisplayed(btnAddCard, 10);
            click(btnAddCard);
        }
        return validateMaxCharError(fNameErr, lNameErr);
    }

    public boolean enterAirmilesNumber() {
        waitUntilElementDisplayed(airmilesSection, 3);
        waitUntilElementDisplayed(airmilesField, 3);
        clearAndFillText(airmilesField, "123456789001");
        click(saveButton);
        waitUntilElementDisplayed(airmilesError, 3);
        clearAndFillText(airmilesField, "123456789");
        click(saveButton);
        waitUntilElementDisplayed(airmilesError, 3);
        clearAndFillText(airmilesField, "12345678900");
        click(saveButton);
        return waitUntilElementDisplayed(airmilesNumberDisplay, 3);
    }

    public boolean clickOrderIDFromHistory() {
        waitUntilElementsAreDisplayed(orderNumberHistory, 3);
        String orderNumber = getText(orderNumberHistory.get(0));
        click(orderNumberHistory.get(0));
        String currentURL = getCurrentURL();
        if (currentURL.contains(orderNumber)) {
            return true;
        } else return false;
    }

    public boolean orderDisplayInHistoryGuestAndReg() {
        waitUntilElementDisplayed(orderTitle, 3);
        if (waitUntilElementsAreDisplayed(orderNumberHistory, 3)) {
            addStepDescription("Order placed as guest is reflected in Reg account after creating account");
            return false;
        } else return true;
    }

    public boolean deleteAddressModal(String header) {

        click(removeFirstAddress);
        waitUntilElementDisplayed(deleteAddressOverlay, 5);
        String text = getText(deleteModalHeader);
//        if (header.equals(text)) {
            click(closeModal);
//        }
        return !waitUntilElementDisplayed(deleteModalHeader, 5);
    }

    public boolean addNewDiffCountryShippingAdd(String fName, String lName, String country, String address1, String address2,
                                                String city, String state, String zipCode, String phoneNo) {

        waitUntilElementDisplayed(lnk_AddressBook, 5);
        staticWait(1000);
        click(lnk_AddressBook);
        staticWait(1000);
        waitUntilElementDisplayed(btn_AddNewAddress, 10);
        clickAddNewAddressBook();
        waitUntilElementDisplayed(newAddress_FirstName, 10);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        selectDropDownByVisibleText(newAddress_ChangeCountry, country);
        clearAndFillText(newAddress_AddressLine1, address1);
        /*clearAndFillText(newAddress_AddressLine2, address2);*/
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        clearAndFillText(newAddress_PhoneNumeber, phoneNo);
        if (waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox)) {
            click(newAddress_SetDefaultAddressCheckBox);
        }
        click(newAddress_AddNewAddressBtn);
        staticWait(5000);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);

        staticWait(5000);

        verifyDefaultShippingAddress(fName, lName, address1, city, zipCode);
//        scrollUpToElement(lnk_AddressBook);
//        click(lnk_AddressBook);
        return waitUntilElementDisplayed(defaultShipMethodLbl, 20);
    }

    public boolean mprEspotAccOverview() {
        waitUntilElementDisplayed(mprEspotAccOverview);
        click(mprEspotAccOverview);
        waitUntilElementDisplayed(closeModal, 2);
        return waitUntilElementDisplayed(acceptOrApplyButton, 2);
    }

    public boolean isEmployeeChkBoxChecked() {
        String isChecked = getAttributeValue(associateID_ChkBoxVal, "value");
        if (isChecked.equalsIgnoreCase("true")) {
            return true;
        } else if (isChecked.equalsIgnoreCase("false")) {
            return false;
        }
        return false;
    }

    public boolean isEmployeeFldPopulated() {
        String isPopulated = getAttributeValue(associateIDField, "value");
        if (!isPopulated.isEmpty()) {
            return true;
        } else if (isPopulated.isEmpty()) {
            return false;
        }
        return false;
    }

    public boolean validateContentNoDefaultAdd(String header1, String content) {

        waitUntilElementDisplayed(shippingText, 3);
        String displayedText = getText(shippingText);
        String header = getText(saveShippingHeader);
        if (displayedText.equalsIgnoreCase(content) && header.equalsIgnoreCase(header1)) {
            return true;
        } else {
            addStepDescription("Static text has been changed, update the sheet");
            return false;
        }
    }

    public boolean validateGiftCardInMyAcc(String textContent) {
        waitUntilElementDisplayed(addGiftCard_button, 3);
        click(addGiftCard_button);
        staticWait(4000);
        String contentDisplayed = getText(giftcard_Message1).replaceAll(",", "");
        waitUntilElementDisplayed(giftcard_Message1, 3);
        if (isDisplayed(giftCard_NumberField) && isDisplayed(giftCard_PinField) && isDisplayed(cancel_Button)
                && contentDisplayed.equalsIgnoreCase(textContent) && isDisplayed(recaptcha_Field)) {
            return true;
        } else
            addStepDescription("Change in static text or Check the Page Object");
        return false;
    }

    public boolean addGcToAccount(String giftCard, String pin, String error) {
        waitUntilElementDisplayed(addNew_GC, 3);
        scrollDownToElement(addNew_GC);
        click(addNew_GC);
        waitUntilElementDisplayed(giftCard_NumberField, 3);
        clearAndFillText(giftCard_NumberField, giftCard);
        clearAndFillText(giftCard_PinField, pin);
        click(save_Button);
        staticWait();
        waitUntilElementDisplayed(recaptcha_Error, 4);
        String recaptchaErrorDisplayed = getText(recaptcha_Error).replaceAll("Error: ", "");
        if (error.equalsIgnoreCase(recaptchaErrorDisplayed)) {
            return true;
        } else {
            addStepDescription("Error message is not displayed is not proper");
            return false;
        }
    }

    public boolean closeGiftCardValidation(String error) {
        waitUntilElementDisplayed(closeGiftcard.get(0), 3);
        waitUntilElementsAreDisplayed(checkBalanceButton, 1);
        waitUntilElementDisplayed(recaptcha_Field, 1);
        click(closeGiftcard.get(0));
        staticWait();
        waitUntilElementDisplayed(confirmationText, 4);
        String confirmationDisplayed = getText(confirmationText);
        if (confirmationDisplayed.contains(error)) {
            click(cancelDeleteGc);
            return true;
        } else {
            addStepDescription("Error message is not displayed is not proper");
            return false;
        }
    }

    public boolean mprPointMeterCA() {
        if (!isDisplayed(rewardsLink) &&
                !isDisplayed(pointsHistoryLink) && !isDisplayed(pointsBar))
            return true;
        else
            addStepDescription("MPR points meter is displayed in CA store.");
        return false;
    }

    public boolean mprPointMeterUS() {
        waitUntilElementDisplayed(lnk_AccountOverView, 4);
        if (isDisplayed(rewardsLink) &&
                isDisplayed(pointsHistoryLink) && isDisplayed(pointsBar))
            return true;
        else
            addStepDescription("MPR points meter is not displayed in US store.");
        return false;
    }

    public boolean applyMPRCouponsMyAcc() {
        waitUntilElementsAreDisplayed(applyMPRCoupon, 3);
        click(applyMPRCoupon.get(0));
        if (waitUntilElementDisplayed(successMessage, 3)) {
            return true;
        } else
            return waitUntilElementDisplayed(couponErrorMessage, 3);
    }

    public boolean validateSavedAddressFromShipping(String address) {
        waitUntilElementDisplayed(savedAddress_MyAccount);
        String addressDisplayed = getText(savedAddress_MyAccount);
        if (addressDisplayed.contains(address)) {
            return true;
        } else {
            addStepDescription("Address in Shipping is not saved in MyAccount");
            return false;
        }
    }

    public boolean verifyDefaultShippingAddressDisplay(String name1, String address) {
        waitUntilElementDisplayed(defaultAddressInAcctOverview, 5);
        String validateName = getText(defaultAddressInAcctOverview).replaceAll("\"", "");
        return validateName.contains(name1) && validateName.contains(address);
    }

    public boolean verifyDefaultCardDetailAfterAdd(String cardType, String cardNo, String expMonth, String expYr) {
        staticWait(5000);
        String actualCardNum;
        String trimCardNum = null;
        if (cardType.contains("AMEX")) {
            actualCardNum = cardNo.substring(11, 15);
            waitUntilElementDisplayed(label_CardNumber(actualCardNum), 20);
            trimCardNum = getText(label_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        } else {
            actualCardNum = cardNo.substring(12, 16);
            waitUntilElementDisplayed(label_CardNumber(actualCardNum), 20);
            trimCardNum = getText(label_CardNumber(actualCardNum)).replaceAll("[^0-9]", "");
        }

        boolean compareCardNumValue = trimCardNum.contains(actualCardNum);
        return compareCardNumValue;
    }

    public boolean checkPrePopulatedAdd(String name, String name1, String address, String city1, String phoneNumber, String zip1) {
        waitUntilElementDisplayed(newAddress_FirstName, 2);

        String fname = getAttributeValue(newAddress_FirstName, "value");
        String lName = getAttributeValue(newAddress_LastName, "value");
        String add1 = getAttributeValue(newAddress_AddressLine1, "value");
        String city = getAttributeValue(newAddress_City, "value");
        String phone = getAttributeValue(newAddress_PhoneNumeber, "value");
        String zip = getAttributeValue(newAddress_ZipCode, "value");
        if (fname.contains(name) && lName.contains(name1) && add1.contains(address) && city.contains(city1)
                && phone.equalsIgnoreCase(phoneNumber) && zip.contains(zip1)) {
            return true;
        } else {
            addStepDescription("Prepopulated values are not same");
            return false;
        }
    }

    public boolean enterCC(String card_no, String exp_month, String expYear, String successMsgContent) {

        waitUntilElementDisplayed(addPayment_CardNumber, 3);
        clearAndFillText(addPayment_CardNumber, card_no);
        selectDropDownByValue(addPayment_expmonth, exp_month);
        selectDropDownByVisibleText(addPayment_expyr, expYear);
        staticWait(3000);
        click(btnAddCard);

        waitUntilElementDisplayed(errorContainer);
        String errorText = getText(errorContainer).replaceAll("Error:", "");
        if (errorText.equalsIgnoreCase(successMsgContent)) {
            return true;
        } else {
            addStepDescription("Check the max limit error copy");
            return false;
        }
    }

    public boolean verifyDefaultPaymentAddress(String fname, String lname, String address1, String city, String zipcode)

    {
        boolean validateFName = getText(billingAddressContainer).replaceAll("\"", "").contains(fname);
        boolean validateLName = getText(billingAddressContainer).replaceAll("\"", "").contains(lname);
        boolean validateAddress = getText(billingAddressContainer).contains(address1);
        ;
        boolean validateCity = getText(billingAddressContainer).contains(city);
        boolean validateZIP = getText(billingAddressContainer).contains(zipcode);

        if (validateFName && validateLName &&
                validateAddress &&
                validateCity &&
                validateZIP)
            return true;
        else
            return false;
    }

    public boolean checkFavStoreDisplay(String store) {
        scrollDownToElement(favStoreDisplay);
        waitUntilElementDisplayed(favStoreDisplay, 2);
        String currentStore = getText(favStoreDisplay).toLowerCase();
        if (currentStore.equalsIgnoreCase(store)) {
            return true;
        } else {
            addStepDescription("Favorite store displayed in My Account is different");
            return false;
        }
    }

    public boolean checkThePointsToolTipContent(String text) {
        waitUntilElementDisplayed(tooltipPoints, 2);
        click(tooltipPoints);
        waitUntilElementDisplayed(tooltipContentPoints, 2);
        String displayedText = tooltipContentPoints.getText();
        if (displayedText.contains(text)) {
            return true;
        } else {
            addStepDescription("Check the content displayed inside the tooltip");
            return false;
        }
    }

    public boolean validateMoreThan50ErrMsg(String errFName, String errLName,
                                            String errAddrLine1, String errCity,
                                            String errZipPostal) {

        waitUntilElementDisplayed(newAddress_FirstName);
        clearAndFillText(newAddress_FirstName, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(newAddress_LastName, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(newAddress_AddressLine1, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(newAddress_AddressLine2, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(newAddress_City, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(newAddress_ZipCode, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        if(isDisplayed(newAddress_PhoneNumeber)) {
            clearAndFillText(newAddress_PhoneNumeber, "9878787896");
        }

        staticWait();
        String fName = getText(maxChar_FNameField);
        String lName = getText(maxChar_LastNameField);
        String adrLne = getText(maxChar_AddLine1);
        String city = getText(maxChar_CityErr);
        String zip = getText(maxChar_ZipCodeErr);

        if (fName.contains(errFName) && lName.contains(errLName) && adrLne.contains(errAddrLine1) && city.contains(errCity) && zip.contains(errZipPostal))
            return true;
        else
            return false;
    }

    public boolean checkLoadingIcon() {
        if (isDisplayed(loadingIcon)) {
            return true;
        } else {
            addStepDescription("Loading icon is not displayed");
            return false;
        }
    }

    public boolean clickTrackOrderLink() {
        waitUntilElementDisplayed(trackOrder_Link, 2);
        scrollToBottom();
        click(trackOrder_Link);
        isDisplayed(loadingIcon);
        if (waitUntilElementDisplayed(orderStatusTable, 10)) {
            return true;
        } else {
            addStepDescription("Loading icon is not displayed in My Account page");
            return false;
        }
    }
    public boolean deleteDefaultAddressFromMultipleAddress(){
        waitUntilElementDisplayed(defaultAddressDeleteLink);
        waitUntilElementDisplayed(defaultAddressEditLink);
        verifyElementNotDisplayed(setAsDefaultLinkForDefaultAddress);
        if(addressContainer.size()>1) {
            waitUntilElementDisplayed(deleteDefaultAddress);
            click(deleteDefaultAddress);
            waitUntilElementDisplayed(okButton,4);
            click(okButton);
            staticWait(2000);
            if(isDisplayed(defaultShipAddress)){
                return true;
            }
            else{
                addStepDescription("The second address is not set as default");
                return false;
            }
        }
        else{
            waitUntilElementDisplayed(deleteDefaultAddress);
            click(deleteDefaultAddress);
            waitUntilElementDisplayed(okButton,4);
            click(okButton);
            staticWait(2000);
            return waitUntilElementDisplayed(btn_AddNewAddress);
        }
    }
    public boolean checkTheAddressDisplayInMailingSection(String text){
        waitUntilElementDisplayed(editLnkOnPersonalInfo);
        click(editLnkOnPersonalInfo);
        waitUntilElementDisplayed(mailingAddress_AddressBookDD);
        click(mailingAddress_AddressBookDD);
        if(waitUntilElementsAreDisplayed(addressList,1)){
            return getText(addressList.get(0)).contains(text);
        }
        else{
            addStepDescription("Shipping addresses are not displayed in mailing address drop down");
            return false;
        }

    }
    public boolean validateHrefLangTag() {
        boolean href = false;
        for (WebElement tag : hrefLangTags) {
            href = getAttributeValue(tag, "href").contains("www") && hrefLangTags.size() == 4;
        }
        return href;
    }
    public boolean removeAppliedCoupon(){
        waitUntilElementDisplayed(removeButton);
        click(removeButton);
        return !waitUntilElementDisplayed(removeButton);
    }
    public boolean verifyExpireDate() {
        return waitUntilElementDisplayed(couponExpireInfoODM, 10);
    }

}
