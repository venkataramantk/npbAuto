package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.FooterMPRCreditCardRepo;

public class FooterMPRCreditCardActions extends FooterMPRCreditCardRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(MyAccountPageDrawerActions.class);

    public FooterMPRCreditCardActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickApplyToday() {
        scrollDownToElement(applyButton);
        click(applyButton);
        return waitUntilElementDisplayed(firstName, 30);
    }

    public boolean verifyExistingAccountPopup() {
        return waitUntilElementDisplayed(accountExists);
    }

    public void closePopup() {
        waitUntilElementDisplayed(closeModel);
        click(closeModel);
    }

    public void applyPrescreenCode(String code) {
        if (waitUntilElementDisplayed(clickHere, 10))
            click(clickHere);
        clearAndFillText(prescreenField, code);
        prescreenField.sendKeys(Keys.RETURN);
    }

    public boolean enterApplicationDetails_Approve(String fname, String lname, String address1, String address2, String city, String state, String zipcode, String phone, String email, String altphone, String birthmonth, String birthday, String birthyear, String ssn) {
        waitUntilElementDisplayed(firstName, 30);

        clearAndFillText(firstName, fname);
        clearAndFillText(lastName, lname);
        clearAndFillText(addressLine1, address1);
        clearAndFillText(cityTextBox, city);
        selectDropDownByVisibleText(stateDropDown, state);
        clearAndFillText(zipCode, zipcode);
        clearAndFillText(mobilePhoneNumber, phone);
        clearAndFillText(emailAddress, email);
        staticWait();
        clearAndFillText(emailAddress, email);
        clearAndFillText(alternatePhoneNumber, altphone);
        selectDropDownByVisibleText(birthMonth, birthmonth);
        selectDropDownByVisibleText(birthDay, birthday);
        selectDropDownByVisibleText(birthYear, birthyear);
        clearAndFillText(socialSecurityNumber, ssn);
        select(termsCheckBox, termsCheckBoxinput);

        click(submitButton);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        return waitUntilElementDisplayed(approvedPlccContainer, 30);
    }

    public boolean enterApplicationDetails_Decline(String fname, String lname, String address1, String address2, String city, String state, String zipcode, String phone, String email, String altphone, String birthmonth, String birthday, String birthyear, String ssn) {
        waitUntilElementDisplayed(firstName, 30);

        clearAndFillText(firstName, fname);
        clearAndFillText(lastName, lname);
        clearAndFillText(addressLine1, address1);
        clearAndFillText(addressLine2, address2);
        clearAndFillText(cityTextBox, city);
        selectDropDownByVisibleText(stateDropDown, state);
        clearAndFillText(zipCode, zipcode);
        clearAndFillText(mobilePhoneNumber, phone);
        clearAndFillText(emailAddress, email);
        clearAndFillText(alternatePhoneNumber, altphone);

        selectDropDownByVisibleText(birthMonth, birthmonth);
        selectDropDownByVisibleText(birthDay, birthday);
        selectDropDownByVisibleText(birthYear, birthyear);
        clearAndFillText(socialSecurityNumber, ssn);

        checkTermsCheckBox();
        click(submitButton);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        staticWait(5000);
        return waitUntilElementDisplayed(continueShoppingButton);
    }

    public boolean validateApprovedPage_Guest() {
        waitUntilElementDisplayed(createAccLink, 10);

        if (isDisplayed(createAccLink) &&
                isDisplayed(loginLink) &&
                isDisplayed(offerCode) &&
                isDisplayed(copyCliboardLink) &&
                scrollDownToElement(continueShoppingButton) &&
                isDisplayed(continueShoppingButton) &&
                isDisplayed(detailsLink))
            return true;
        else
            return false;
    }

    public boolean validatePlaceCard() {
        waitUntilElementDisplayed(applyButton, 20);
        if (isDisplayed(applyButton) &&
                isDisplayed(manageCCBtn) &&
                scrollDownUntilElementDisplayed(faqLink) &&
                isDisplayed(rewardTermsLink))

            return true;
        else
            return false;
    }

    public boolean checkTermsCheckBox() {
        if (!(isSelected(termsCheckBox))) {
            click(termsAndCondition);
        }
        return isSelected(termsCheckBox);
    }

    public void clickCreateAccAndValidateFields(CreateAccountActions createAccountActions) {
        staticWait(1000);
        click(createAccLink);
        waitUntilElementDisplayed(createAccountActions.createAccountButton, 15);
        createAccountActions.validateFieldsInPage();
    }

    public void clickLoginAndValidateFields(LoginDrawerActions loginDrawerActions) {
        staticWait(1000);
        click(loginLink);
        waitUntilElementDisplayed(loginDrawerActions.loginButton, 15);
        loginDrawerActions.validateLoginDrawer();
    }


    public boolean validatePromoAndDisclosureStatement(String statementContent) {

        boolean isPromoImgDisplayed = waitUntilElementDisplayed(plccPromoImg, 5);

        String getDisclosureContent = plccDisclosureStatementContent.getText().replaceAll("[^A-Za-z0-9. ()-:]", "");
        System.out.println(getDisclosureContent);
        boolean checkStatementContent = getDisclosureContent.contains(statementContent);
        waitUntilElementDisplayed(footerSection, 5);
        return isPromoImgDisplayed && checkStatementContent;
    }

    public boolean validateErrorMsg(String fnameError, String lnameError, String addErr, String cityError, String stateErr,
                                    String zipError, String mobileErr, String emailErr, String altPhoneErr, String birthErr, String ssnError) {

        waitUntilElementDisplayed(firstName, 5);
        firstName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(fnameErr, 5);
        boolean firstNameErr = getText(fnameErr).contains(fnameError);

        waitUntilElementDisplayed(lastName, 5);
        lastName.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(lnameErr, 5);
        boolean lastNameErr = getText(lnameErr).contains(lnameError);

        waitUntilElementDisplayed(addressLine1, 5);
        addressLine1.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(address1Err, 5);
        boolean addressErr = getText(address1Err).contains(addErr);

        waitUntilElementDisplayed(cityTextBox, 5);
        cityTextBox.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(cityErr, 5);
        boolean cityErrMsg = getText(cityErr).contains(cityError);

        waitUntilElementDisplayed(stateDropDown, 5);
        stateDropDown.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(stateError, 5);
        boolean stateErrMsg = getText(stateError).contains(stateErr);

        waitUntilElementDisplayed(zipCode, 5);
        zipCode.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(zipErr, 5);
        boolean zipErrMsg = getText(zipErr).contains(zipError);

        waitUntilElementDisplayed(mobilePhoneNumber, 5);
        mobilePhoneNumber.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(phoneErr, 5);
        boolean mobileErrMsg = getText(phoneErr).contains(mobileErr);

        waitUntilElementDisplayed(emailAddress, 5);
        emailAddress.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailError, 5);
        boolean emailErrMsg = getText(emailError).contains(emailErr);

        waitUntilElementDisplayed(alternatePhoneNumber, 5);
        alternatePhoneNumber.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(altPhoneNoErr, 5);
        boolean altPhoneErrMsg = getText(altPhoneNoErr).contains(altPhoneErr);

        waitUntilElementDisplayed(birthMonth, 5);
        birthMonth.sendKeys(Keys.TAB);
        birthDay.sendKeys(Keys.TAB);
        birthYear.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(birthMonthErr, 5);
        boolean birthErrMsg = getText(birthMonthErr).contains(birthErr);

        waitUntilElementDisplayed(socialSecurityNumber, 5);
        socialSecurityNumber.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(ssnErr, 5);
        boolean ssnErrMsg = getText(ssnErr).contains(ssnError);

        if (firstNameErr && lastNameErr && addressErr && cityErrMsg && stateErrMsg && zipErrMsg &&
                mobileErrMsg && emailErrMsg && altPhoneErrMsg && ssnErrMsg && birthErrMsg)
            return true;
        else
            return false;

    }

    public String getResponseCode() {
        String response = getValueOfDataElement("response_tcpstore/processWIC");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCode=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 2).trim();
    }

    public String getResponseCodeDescription() {
        String response = getValueOfDataElement("response_tcpstore/processWIC");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCodeDescription=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 8).trim();
    }

    public String getRTPSResponseCode() {
        String response = getValueOfDataElement("response_tcpstore/processPreScreenAcceptance");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCode=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 2).trim();
    }

    public boolean compareRTPSText(String text) {
        waitUntilElementDisplayed(legalCopyDisplay);
        String copy = getText(legalCopyDisplay);
        if (copy.contains(text)) {
            return true;
        } else {
            addStepDescription("THe text copy have been changed");
            return false;
        }
    }

    /**
     * @Author: JK
     * @param: String to check
     * @return: true if the string matches in rewards
     */
    public boolean validateUpdatedReqards(String terms) {
        String rewards = getText(rewardterms);
        return rewards.contains(terms);
    }

    /**
     * @Author: JK
     * Validated if legal copy is displaying in bullet points
     * @return true
     */
    public boolean validateLegalCopy() {
        waitUntilElementDisplayed(legalpoints);
        String type = legalpoints.getCssValue("list-style-type");
        return type.equalsIgnoreCase("disc");
    }
}

