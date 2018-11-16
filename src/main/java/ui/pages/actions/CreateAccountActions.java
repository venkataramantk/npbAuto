package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.CreateAccountRepo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skonda on 5/18/2016.
 */
public class CreateAccountActions extends CreateAccountRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(CreateAccountActions.class);

    public CreateAccountActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void enterAccountDetails(String fName, String lName, String pwd, String zipCode, String phNum) {
        clearAndFillText(firstNameField, fName);
        clearAndFillText(lastNameField, lName);
        waitUntilElementDisplayed(passwordFld, 5);
        clearAndFillText(passwordFld,"");
        clearAndFillText(passwordFld, pwd);
        waitUntilElementDisplayed(confPasswordFld, 4);
        clearAndFillText(confPasswordFld, pwd);
        waitUntilElementDisplayed(zipPostalCodeFld, 5);
        clearAndFillText(zipPostalCodeFld, zipCode);
        waitUntilElementDisplayed(phNumberFld, 5);
        clearAndFillText(phNumberFld, phNum);
        select(termsCheckboxInput, termsCheckBoxSelected);
    }

    public boolean validateFieldsInPage() {

        boolean areAllFieldsDisplaying = waitUntilElementDisplayed(firstNameField, 2) &&
                waitUntilElementDisplayed(lastNameField, 2) &&
                waitUntilElementDisplayed(emailAddrField, 2) &&
                waitUntilElementDisplayed(confEmailAddrFld, 2) &&
                waitUntilElementDisplayed(passwordFld, 2) &&
                waitUntilElementDisplayed(confPasswordFld, 2) &&
                waitUntilElementDisplayed(zipPostalCodeFld, 2) &&
                waitUntilElementDisplayed(phNumberFld, 2) &&
                waitUntilElementDisplayed(termsText, 2) &&
                waitUntilElementDisplayed(createAccountButton, 2)/*&&
                waitUntilElementDisplayed(rememberMeCheckBox,2)&&
                waitUntilElementDisplayed(termsCheckBoxUnselected,2)*/;
        if (areAllFieldsDisplaying)
            return true;
        else
            return false;
    }


    public boolean createAnAccount(String fName, String lName, String pwd, String zipCode, String phNum) {
        String email = randomEmail();
        setEmailAddress(email);

        logger.info("The random email is generated: " + email);
        clearAndFillText(emailAddrField, email);
        clearAndFillText(confEmailAddrFld, email);
        getText(emailAddrField).contains(email);
        getText(confEmailAddrFld).contains(email);
        enterAccountDetails(fName, lName, pwd, zipCode, phNum);
        staticWait(10000);
        click(createAccountButton);
        staticWait(10000);
        if (waitUntilElementDisplayed(welcomeHeadlineUS, 5)) {
            return (getText(welcomeHeadlineUS).toLowerCase().contains("welcome!") && waitUntilElementDisplayed(continueShoppingButtonUS, 1));
        } else if (waitUntilElementDisplayed(thanksHeadLineCA, 5)) {
            return (getText(thanksHeadLineCA).toLowerCase().contains("thanks!") && waitUntilElementDisplayed(startShoppingButtonCA, 1));
        } else
            return false;
    }

    public boolean verifySuccessfulRegistration() {
        if (((isDisplayed(viewAcctButtonUS) && isDisplayed(continueShoppingButtonUS))
                || (isDisplayed(editAcctButtonCA) && isDisplayed(startShoppingButtonCA))))

            return true;
        else
            return false;
    }

    public void enterAccountDetailsWithoutSubmit(String fName, String lName, String pwd, String zipCode, String phNum) {

        String email = randomEmail();
        setEmailAddress(email);

        logger.info("The random email is generated: " + email);
        clearAndFillText(emailAddrField, email);
        clearAndFillText(confEmailAddrFld, email);

        enterAccountDetails(fName, lName, pwd, zipCode, phNum);
    }
    public Map<String, String> getResponseOfEmail() {
        String response = getValueOfDataElement("bpi.briteverify.com");
        logger.info("email create account BV check: " + response);
        Map<String, String> responseCodeAndMessage = getEmailResponseCode(response);
        return responseCodeAndMessage;
    }
    public boolean isProperResponseCodeAndMessage(Map<String, String> responseCodeAndMessage) {
        String codeKey = responseCodeAndMessage.get("ErrorCode");
        String msgValue = responseCodeAndMessage.get("ErrMessage");
        Boolean response = (codeKey.equals("true")) && (msgValue.equalsIgnoreCase("false"));
        return response;
    }

    public Map<String, String> getEmailResponseCode(String response) {
        Map<String, String> responseErrCodeAndMsgs = new HashMap<>();
        String olpsKeyName = "superagentCallback", Msg = "disposable", code = "status";
        int startIndex = response.indexOf(olpsKeyName);
        String responseCodeAndMsg = response.substring(startIndex);

        int errMsgStartIndex = responseCodeAndMsg.indexOf(Msg) + Msg.length();
        int errMsgEndIndex = responseCodeAndMsg.lastIndexOf(",");

        int errCodeStartIndex = responseCodeAndMsg.indexOf(code) + code.length();
        int errCodeEndIndex = responseCodeAndMsg.indexOf("}");
        String responseErrMessage = responseCodeAndMsg.substring(errMsgStartIndex, errMsgEndIndex);
        String responseErrCode = responseCodeAndMsg.substring(errCodeStartIndex, errCodeEndIndex);
        responseErrCodeAndMsgs.put("ErrorCode", responseErrCode);
        responseErrCodeAndMsgs.put("ErrMessage", responseErrMessage);
        return responseErrCodeAndMsgs;

    }

    public boolean createAnAccount_UserInputEmail(String email, String fName, String lName, String pwd, String zipCode, String phNum) {

        clearAndFillText(emailAddrField, email);
        clearAndFillText(confEmailAddrFld, email);
        enterAccountDetails(fName, lName, pwd, zipCode, phNum);
        click(createAccountButton);
        waitUntilElementDisplayed(successMessage, 30);
        try {
            return getText(successMessage).contains("Yay");
        } catch (Throwable t) {
            return waitUntilElementDisplayed(viewAcctButtonUS);
        }


    }

    public boolean createAnAccount_UserInputEmail_ErrVerification(String email, String fName, String lName, String pwd, String zipCode, String phNum) {

        clearAndFillText(emailAddrField, email);
        clearAndFillText(confEmailAddrFld, email);
        enterAccountDetails(fName, lName, pwd, zipCode, phNum);
        staticWait(5000);
        click(createAccountButton);
        staticWait(2000);
        return waitUntilElementDisplayed(err_CreateAccount, 30);

    }

    public String err_CreateAccount() {
        return getText(err_CreateAccount);
    }

     public boolean verifyRememberMeForNextTimeLabel(String label) {
        for (WebElement rememberMeLabel : rememberMeForNextTimeLabel) {
            if (getText(rememberMeLabel).equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }

    public boolean unCheckRememberMeCheckBox() {
        if (isSelected(rememberMeCheckBox)) {
            click(rememberMeCheckBox);
        }
        return !isSelected(rememberMeCheckBox);
    }

    public boolean isErrorMsgDisplayingOnAllFields(String fNameErr, String lNameErr, String emailErr, String pwdErr, String confPwdErr, String zipErr, String phNumErr) {
        boolean isFNameErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(fNameErr), 1);
        boolean isLNameErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(lNameErr), 1);
        boolean isEmailErrMsgDisplaying = getText(emailAddressErrMsg()).equalsIgnoreCase(emailErr);
        boolean isConfEmailErrMsgDisplaying = getText(confirmEmailAddressErrorMsg()).equalsIgnoreCase(emailErr);
        boolean isPwdErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(pwdErr), 1);
        boolean isConfPwdErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(confPwdErr), 1);
        boolean isZipErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(zipErr), 1);
        boolean isPhNumErrMsgDisplaying = waitUntilElementDisplayed(errorMessageByFieldError(phNumErr), 1);
        boolean isTermChkErrMsgDisplaying = waitUntilElementDisplayed(termsCheckBoxUnselected, 1);


        if (isFNameErrMsgDisplaying && isLNameErrMsgDisplaying && isEmailErrMsgDisplaying && isConfEmailErrMsgDisplaying && isPwdErrMsgDisplaying
                && isConfPwdErrMsgDisplaying && isZipErrMsgDisplaying && isPhNumErrMsgDisplaying && isTermChkErrMsgDisplaying) {
            return true;
        }
        return false;
    }

    public void clickCreateAccountButton() {
        click(createAccountButton);
    }

    public boolean clickCreateAcctButtonAndVerifyErrorMsgOnAllFields(String fNameErr, String lNameErr, String emailErr, String pwdErr, String confPwdErr, String zipErr, String phNumErr) {
        clickCreateAccountButton();
        return isErrorMsgDisplayingOnAllFields(fNameErr, lNameErr, emailErr, pwdErr, confPwdErr, zipErr, phNumErr);
    }

    //DT2 Actions

    public boolean unCheckTermsCheckBox() {
        waitUntilElementDisplayed(termsCheckox);
            click(termsCheckox);
        return waitUntilElementDisplayed(termsCheckBoxUnselected, 2);
    }

    public boolean checkTermsCheckBox() {
        /*if(!isSelected(termsCheckBoxUnselected)){*/
        if (waitUntilElementDisplayed(termsCheckBoxUnselected, 2)) {
            click(termsCheckBoxUnselected);
        }
        return waitUntilElementDisplayed(termsCheckBoxSelected, 2);
    }

    public boolean validateOverlayContent(String content1, String content2, String termsTextVal, String remTextVal, String remSubTextVal) {

//        boolean checkHeaderContent1 = waitUntilElementsAreDisplayed(txtContentLbl1, 10);
        boolean checkHeaderContent2 = waitUntilElementsAreDisplayed(txtContentLbl2, 10);
        validateFieldsInPage();

        boolean termsTextCheck = waitUntilElementDisplayed(termsText, 20);

        boolean rememberMeLblCheck = waitUntilElementDisplayed(rememberMeTxtLbl, 10)
                && waitUntilElementDisplayed(rememberMeSubTxtLbl, 10);

        if (/*checkHeaderContent1
                &&*/checkHeaderContent2
                && validateFieldsInPage()
                && waitUntilElementDisplayed(termsCondLnk, 2)
                && termsTextCheck
                && rememberMeLblCheck)

            return true;

        else
            return false;
    }

    public boolean validateToolTipContent() {
        waitUntilElementDisplayed(toolTip);
        click(toolTip);
        return waitUntilElementDisplayed(toolTipContent);
    }

    public boolean validateEmptyFieldErrMessages(String errFName, String errLName,
                                                 String errEmail, String errConfEmail,
                                                 String errPassword, String errConfPassword,
                                                 String errZipPostal, String errPhone, String errTerms) {
        waitUntilElementDisplayed(createAccountButton, 2);
        clearAndFillText(firstNameField, "");
        clearAndFillText(lastNameField, "");
        clearAndFillText(confEmailAddrFld, "");
        clearAndFillText(emailAddrField, "");
        clearAndFillText(passwordFld, "");
        clearAndFillText(confPasswordFld, "");
        clearAndFillText(zipPostalCodeFld, "");
        clearAndFillText(phNumberFld, "");
//        tabFromField(firstNameField);
//        tabFromField(lastNameField);
//        tabFromField(confEmailAddrFld);
//        tabFromField(emailAddrField);
//        tabFromField(passwordFld);
//        tabFromField(confPasswordFld);
//        tabFromField(zipPostalCodeFld);
//        tabFromField(phNumberFld);
        tabFromField(phNumberFld);
    //    checkTermsCheckBox();
        unCheckTermsCheckBox();

//        waitUntilElementClickable(createAccountButton,10);
        click(createAccountButton);

        boolean fNameErr = getText(firstNameInLineErr).contains(errFName);
        boolean lNameErr = getText(lastNameInLineErr).contains(errLName);
        boolean emailErr = getText(emailAddrInLineErr).contains(errEmail);
        boolean confEmailErr = getText(confEmailAddrInLineErr).contains(errConfEmail);
        boolean passwordErr = getText(passwordInLineErr).contains(errPassword);
        boolean confPasswordErr = getText(confPasswordInLineErr).contains(errConfPassword);
        boolean zipPostalErr = getText(zipPostalCodeInLineErr).contains(errZipPostal);
        boolean phoneErr = getText(phoneNumInLineErr).contains(errPhone);
        boolean termsErr = waitUntilElementDisplayed(termsCondInLineErr, 1);
        if (termsErr) {
            termsErr = getText(termsCondInLineErr).contains(errTerms);
        }

        clearAndFillText(firstNameField, "ab");
        clearAndFillText(lastNameField, "ab");
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        clearAndFillText(passwordFld, "P@ssw0rd");
        clearAndFillText(confPasswordFld, "P@ssw0rd");
        clearAndFillText(zipPostalCodeFld, "07094");
        clearAndFillText(phNumberFld, "8989323224");


        checkTermsCheckBox();

        boolean verifyErrMessageDisappear = (!(waitUntilElementDisplayed(firstNameInLineErr, 1)
                && waitUntilElementDisplayed(lastNameInLineErr, 1)
                && waitUntilElementDisplayed(emailAddrInLineErr, 1)
                && waitUntilElementDisplayed(confEmailAddrInLineErr, 1)
                && waitUntilElementDisplayed(passwordInLineErr, 1)
                && waitUntilElementDisplayed(confPasswordInLineErr, 1)
                && waitUntilElementDisplayed(zipPostalCodeInLineErr, 1)
                && waitUntilElementDisplayed(phoneNumInLineErr, 1)
                && waitUntilElementDisplayed(termsCondInLineErr, 1)));


        if (fNameErr && lNameErr
                && emailErr && confEmailErr
                && passwordErr && confPasswordErr
                && zipPostalErr && phoneErr
                && termsErr
                && verifyErrMessageDisappear)
            return true;

        else
            return false;
    }

    public boolean validateErrOnFNameFld_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errFName) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(firstNameField, splChar);
        tabFromField(firstNameField);
        waitUntilElementDisplayed(firstNameInLineErr, 2);
        boolean fNameErr = getText(firstNameInLineErr).contains(errFName);
        return fNameErr;
    }

    public boolean validateErrOnLNameFld_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errLName) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(lastNameField, splChar);
        tabFromField(lastNameField);
        waitUntilElementDisplayed(lastNameInLineErr, 2);
        boolean lNameErr = getText(lastNameInLineErr).contains(errLName);
        return lNameErr;
    }

    public boolean validateErrorOnExistingEmail(String fName, String lName, String pwd, String zipPostal, String phoneNum, String errEmail) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(confEmailAddrFld, errEmail);
        checkTermsCheckBox();
        clearAndFillText(emailAddrField, errEmail);
        click(createAccountButton);
        boolean emailErr = getText(pageLevelError).contains("matches an existing account");
        return emailErr;
    }

    public boolean validateErrOnEmailFld_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errEmail) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(emailAddrField, splChar);
        tabFromField(emailAddrField);
        waitUntilElementDisplayed(emailAddrInLineErr, 2);
        waitUntilElementDisplayed(confEmailAddrInLineErr, 2);
        boolean emailErr = getText(emailAddrInLineErr).contains(errEmail);
        return emailErr;
    }

    public boolean validateErrOnConfEmailFld_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errConfEmail) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(confEmailAddrFld, splChar);
        tabFromField(confEmailAddrFld);
        waitUntilElementDisplayed(confEmailAddrInLineErr, 2);
        boolean confEmailErr = getText(confEmailAddrInLineErr).contains(errConfEmail);
        return confEmailErr;
    }

    public boolean validateErrOnPwdFlds_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errPassword, String errConfPassword) {

        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(passwordFld, splChar);
        tabFromField(passwordFld);
        clearAndFillText(confPasswordFld, splChar);
        tabFromField(confPasswordFld);
        waitUntilElementDisplayed(passwordInLineErr, 2);
        waitUntilElementDisplayed(confPasswordInLineErr, 2);
        boolean pwdErr = getText(passwordInLineErr).contains(errPassword);
        boolean confPwdErr = getText(confPasswordInLineErr).contains(errConfPassword);
        return pwdErr && confPwdErr;
    }

    public boolean validateErrOnZipFld_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errZipPostal) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        clearAndFillText(passwordFld,pwd);
        checkTermsCheckBox();
        clearAndFillText(zipPostalCodeFld, splChar);
        tabFromField(zipPostalCodeFld);
        waitUntilElementDisplayed(zipPostalCodeInLineErr, 2);
        boolean zipErr = getText(zipPostalCodeInLineErr).contains(errZipPostal);
        return zipErr;
    }

    public boolean validateErrOnPhoneFld_SplChar(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errPhone) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(phNumberFld, splChar);
        tabFromField(phNumberFld);
        waitUntilElementDisplayed(phoneNumInLineErr, 2);
        boolean phoneErr = getText(phoneNumInLineErr).contains(errPhone);
        return phoneErr;
    }

    public boolean validateTermsBoxErr_UnCheck(String fName, String lName, String pwd, String zipPostal, String phoneNum, String errTerms) {
        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "autoscriptgc@yopmail.com");
        clearAndFillText(confEmailAddrFld, "autoscriptgc@yopmail.com");
        unCheckTermsCheckBox();
        waitUntilElementDisplayed(termsCondInLineErr, 2);
        boolean termsErr = getText(termsCondInLineErr).contains(errTerms);
        return termsErr;
    }

    public boolean validateSingleFldEmptyErrMsgs(String fName, String lName, String pwd, String zipPostal, String phoneNum, String errFName, String errLName,
                                                 String errEmail, String errConfEmail,
                                                 String errPassword, String errConfPassword,
                                                 String errZipPostal, String errPhone, String errTerms, String emptyChar) {

        boolean fNameVal = validateErrOnFNameFld_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errFName);
        boolean lNameVal = validateErrOnLNameFld_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errLName);
        boolean emailVal = validateErrOnEmailFld_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errEmail);
        boolean confEmailVal = validateErrOnConfEmailFld_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errConfEmail);
        boolean pwdVal = validateErrOnPwdFlds_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errPassword, errConfPassword);
        boolean zipVal = validateErrOnZipFld_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errZipPostal);
        boolean phoneVal = validateErrOnPhoneFld_SplChar(fName, lName, pwd, zipPostal, phoneNum, emptyChar, errPhone);
        boolean termsVal = validateTermsBoxErr_UnCheck(fName, lName, pwd, zipPostal, phoneNum, errTerms);


        if (fNameVal
                && lNameVal
                && emailVal
                && confEmailVal
                && pwdVal
                && zipVal
                && phoneVal
                && termsVal)
            return true;

        else
            return false;
    }

    public boolean showHideLinkPassword(String pwd) {
        waitUntilElementDisplayed(passwordFld, 4);
        clearAndFillText(passwordFld, pwd);
        click(passwordShowHideLnk);
        boolean checkShow = getAttributeValue(passwordFld, "value").contains(pwd) && getText(passwordShowHideLnk).equalsIgnoreCase("hide");
        click(passwordShowHideLnk);
        boolean checkHide = (getAttributeValue(passwordFld, "type").equalsIgnoreCase("password")) && getText(passwordShowHideLnk).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }

    public boolean showHideLinkConfPassword(String pwd) {
        waitUntilElementDisplayed(confPasswordFld, 4);
        clearAndFillText(confPasswordFld, pwd);
        click(confPasswordShowHideLnk);
        boolean checkShow = getAttributeValue(confPasswordFld, "value").contains(pwd) && getText(confPasswordShowHideLnk).equalsIgnoreCase("hide");
        click(confPasswordShowHideLnk);
        boolean checkHide = (getAttributeValue(confPasswordFld, "type").equalsIgnoreCase("password")) && getText(confPasswordShowHideLnk).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }

    public boolean validateShowHideLnk(String password) {

        boolean valPassword = showHideLinkPassword(password);
        boolean valConfPassword = showHideLinkConfPassword(password);

        return valPassword && valConfPassword;
    }

    public boolean validateErrPassword(String fName, String lName, String pwd, String zipPostal, String phoneNum, String splChar, String errPassword) {

        enterAccountDetails(fName, lName, pwd, zipPostal, phoneNum);
        clearAndFillText(emailAddrField, "tcp@yopmail.com");
        clearAndFillText(confEmailAddrFld, "tcp@yopmail.com");
        checkTermsCheckBox();
        clearAndFillText(passwordFld,"");
        clearAndFillText(passwordFld, splChar);
        tabFromField(passwordFld);
        clearAndFillText(confPasswordFld, splChar);
        tabFromField(confPasswordFld);
        boolean pwdInlineErr = waitUntilElementDisplayed(passwordInLineErr, 5);
        if (pwdInlineErr) {
            pwdInlineErr = getText(passwordInLineErr).contains(errPassword);
        }
        return pwdInlineErr;
    }

    public boolean createAccountExistingEmail(String email, String fName, String lName, String pwd, String zipCode, String phNum, String errMsg) {

        createAnAccount_UserInputEmail_ErrVerification(email, fName, lName, pwd, zipCode, phNum);
        waitUntilElementDisplayed(err_CreateAccount, 30);

        boolean validateErrMsg = getText(err_CreateAccount).contains(errMsg);
        return validateErrMsg;
    }

    public boolean createNewAccount(String fName, String lName, String pwd, String zipCode, String phNum, String successMessageContent) {
        String email = randomEmail();
        setEmailAddress(email);

        logger.info("The random email is generated: " + email);
        clearAndFillText(emailAddrField, email);
        clearAndFillText(confEmailAddrFld, email);
        getText(emailAddrField).contains(email);
        getText(confEmailAddrFld).contains(email);
        enterAccountDetails(fName, lName, pwd, zipCode, phNum);
        //staticWait(5000);
        click(createAccountButton);
        if (waitUntilElementDisplayed(welcomeCustomerLink, 20)) {
//            boolean checkSuccess = waitUntilElementDisplayed(successMessage, 1);//.equalsIgnoreCase(successMessageContent);
//            staticWait(5000);
            boolean checkFirstName = getText(welcomeCustomerLink).contains(fName);
            return (checkFirstName);
        } else
            return false;
    }

    public boolean clickReturnToLoginBtn(LoginDrawerActions loginDrawerActions) {
        waitUntilElementDisplayed(backToLoginLnk, 5);
        click(backToLoginLnk);
        return waitUntilElementDisplayed(loginDrawerActions.loginButton, 5);
    }

    public boolean backToLogin(LoginPageActions loginPageActions) {
        waitUntilElementDisplayed(backToLoginLnk, 5);
        click(backToLoginLnk);
        return waitUntilElementDisplayed(loginPageActions.loginButton, 5);
    }

    public void termsAndCondtionValidate() {
        staticWait(1000);
        click(createAccountLink);
        waitUntilElementDisplayed(termsAndCondition_Link, 5);
        click(termsAndCondition_Link);
    }

    public boolean termsAndCondtionValidateURL(String urlValue) {

        staticWait(5000);
        String currentUrl = getCurrentURL();
        staticWait(7000);
        return currentUrl.contains(urlValue);

    }

    public void rewardsRedirection() {

        staticWait(1000);
        click(mprLink);
        waitUntilElementDisplayed(learnMoreLink_MPR);
        click(learnMoreLink_MPR);
        //scrollDownUntilElementDisplayed(rewardsTerms);
        waitUntilElementDisplayed(rewardsTerms, 5);
        click(rewardsTerms);
        staticWait(2000);

    }

    public void rewardsEspotRedirection(HeaderMenuActions headerMenuActions) {
        headerMenuActions.clickOnTCPLogo();
        staticWait(1000);
        click(globalEspot);
        staticWait(1000);
        //scrollDownUntilElementDisplayed(rewardsFromEspot.get(0));
        click(rewardsFromEspot.get(0));
        staticWait(8000);

    }

    public void creditCardRedirection() {

        staticWait(1000);
        scrollDownUntilElementDisplayed(footerLearnMore);
        click(footerLearnMore);
        waitUntilElementsAreDisplayed(applyOrAccept, 5);
        click(applyOrAccept.get(0));
        staticWait(2000);
        click(termsLinkRedirection);
        staticWait(5000);


    }

    public void clickContactUs() {
        HeaderMenuActions headerMenuActions = new HeaderMenuActions(driver);
        click(createAccountLink);
        waitUntilElementDisplayed(contactusLink);
        click(contactusLink);
        headerMenuActions.click(headerMenuActions.closemodal);
    }

    public boolean validateErrMessageContactUs(String fName, String lName, String email, String subErr, String reasonErr) {

        waitUntilElementDisplayed(contanctUs_FirstName, 3);
        clearAndFillText(contanctUs_FirstName, fName);
        clearAndFillText(contanctUs_LastName, lName);
        clearAndFillText(contanctUs_EmailID, email);
        clearAndFillText(contanctUs_ConfirmEmailID, email);
        click(contanctUs_Submit);
        waitUntilElementDisplayed(selectSubject_ErrMsg, 3);
        String subjectError = getText(selectSubject_ErrMsg).replaceAll("Error: ", "");
        String reasoneErrDisplayed = getText(selectReason_ErrMsg).replaceAll("Error: ", "");
        if (subErr.contains(subjectError) && reasonErr.contains(reasoneErrDisplayed)) {
            return true;
        } else {
            return false;
        }
    }

}



