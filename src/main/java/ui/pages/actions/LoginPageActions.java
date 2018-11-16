package ui.pages.actions;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.LoginPageRepo;

/**
 * Created by skonda on 6/6/2016.
 */
public class LoginPageActions extends LoginPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(LoginPageActions.class);

    public LoginPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

     public boolean loginAsRegisteredUserFromLoginForm(String emailAddr, String pwd) {
        if (isDisplayed(welcomeText)) {
            clearAndFillText(emailFldOnLoginForm, emailAddr);
            clearAndFillText(pwdFldOnLoginForm, pwd);
            click(loginButton);
            staticWait(10000);
        } else if (isDisplayed(emailFldOnLoginForm)) {
            clearAndFillText(emailFldOnLoginForm, emailAddr);
            clearAndFillText(pwdFldOnLoginForm, pwd);
            click(loginButton);
            staticWait(10000);
        }
        try {
            return waitUntilElementDisplayed(returnToBagLnk, 5) || waitUntilElementDisplayed(welcomeCustomerLink, 5);
        } catch (Throwable t) {
            return true;
        }
    }

    public boolean loginFromBagPageSTH(String emailAddr, String pwd, ShippingPageActions shippingPageActions) {
        if (waitUntilElementDisplayed(emailFldOnLoginForm, 20)) {
            clearAndFillText(emailFldOnLoginForm, emailAddr);
            clearAndFillText(pwdFldOnLoginForm, pwd);
            click(loginButton);
            staticWait(5000);
        }
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 20);
    }

    public boolean loginFromPageBopis(String emailAddr, String pwd, PickUpPageActions pickUpPageActions) {
        if (waitUntilElementDisplayed(emailFldOnLoginForm, 20)) {
            clearAndFillText(emailFldOnLoginForm, emailAddr);
            clearAndFillText(pwdFldOnLoginForm, pwd);
            click(loginButton);
            staticWait(5000);
        }
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 20);
    }

    public boolean loginFromPageHybrid(String emailAddr, String pwd, PickUpPageActions pickUpPageActions) {
        if (waitUntilElementDisplayed(emailFldOnLoginForm, 20)) {
            clearAndFillText(emailFldOnLoginForm, emailAddr);
            clearAndFillText(pwdFldOnLoginForm, pwd);
            click(loginButton);
            staticWait(5000);
        }
        return waitUntilElementDisplayed(pickUpPageActions.nxtShippingBtn, 20);
    }

    public boolean login(String userName, String password, HeaderMenuActions headerMenuActions) {
        enterEmailAddressAndPwd(userName, password);
        return clickLogin(headerMenuActions);
    }

    public boolean loginInBagPageOverlay(String userName, String password) {
        enterEmailAddressAndPwd(userName, password);
        return clickOnLogin();
    }

    public boolean clickOnLogin() {
        waitUntilElementDisplayed(loginButton, 20);
        click(loginButton);
        return true;
    }

    public boolean successfulLogin(String validEmail, String validPassword) {

        waitUntilElementDisplayed(emailAddrField, 20);
        clearAndFillText(emailAddrField, validEmail);
        waitUntilElementDisplayed(passwordField, 2);
        clearAndFillText(passwordField, validPassword);
        staticWait(2000);
        click(loginButton);
        staticWait(2000);
        driver.navigate().refresh();
        staticWait(2000);
        return waitUntilElementDisplayed(hiUserName, 20);
    }

    public boolean isErrorMessageHasLink() {
        return waitUntilElementDisplayed(loginErrMsgLinks, 10);
    }

    public boolean loginWithDisableRememberMeCheckBox(String userName, String password, HeaderMenuActions headerMenuActions) {
        enterEmailAddressAndPwd(userName, password);
        if (isEnabled(rememberMeCheckBox)) {
            click(rememberMeCheckBox);
        }
        return clickLogin(headerMenuActions);
    }

    public boolean enterPasswordForRememberUser(String userName, String password, HeaderMenuActions headerMenuActions) {
        boolean isEmailAutoFilled = getAttributeValue(emailAddrField, "value").equalsIgnoreCase(userName);
        if (!isEmailAutoFilled) {
            addStepDescription("Email is not auto filled for remembered user");
        }
        clearAndFillText(passwordField, password);
        return clickLogin(headerMenuActions);

    }

    public boolean enterPwdForRemem_VerifyShipPage(String userName, String password, ShippingPageActions shippingPageActions) {
        boolean isEmailAutoFilled = getAttributeValue(emailAddrField, "value").equalsIgnoreCase(userName);
        if (!isEmailAutoFilled) {
            addStepDescription("Email is not auto filled for remembered user");
        }
        clearAndFillText(passwordField, password);
        click(loginButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton);

    }

    public void enterEmailAddressAndPwd(String userName, String password) {
        if (waitUntilElementDisplayed(emailAddrField)) {
            clearAndFillText(emailAddrField, userName);
            clearAndFillText(passwordField, password);
        }
    }

    public boolean clickLogin(HeaderMenuActions headerMenuActions) {
        click(loginButton);
        boolean isLoginSuccessful = waitUntilElementDisplayed(headerMenuActions.welcomeCustomerLink, 30);
        boolean isLoginError = waitUntilElementDisplayed(loginErrMsg, 30);

        if (isLoginSuccessful)
            return true;

        else if (isLoginError) {
            ATUReports.add("Login Credentials invalid.", LogAs.INFO, new CaptureScreen(
                    CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            return true;
        } else
            return false;
    }

    public boolean checkUnCheckRememberMeCheckBoxAndVerify() {

        return checkRememberMeCheckBoxAndVerify() && unCheckRememberMeCheckBoxAndVerify();
    }

    public boolean checkRememberMeCheckBoxAndVerify() {
        boolean isRemMeCheckBox = false;
        if (!isSelected(rememberMeCheckBox)) {
            click(rememberMeCheckBox);
            staticWait();
            isRemMeCheckBox = isSelected(rememberMeCheckBox);
        } else {
            isRemMeCheckBox = isSelected(rememberMeCheckBox);

        }
        return isRemMeCheckBox;
    }

    public boolean isRememberMeLabelInCamelcase() {
        boolean isLabelInCamelCase = false, isLabelInCamelCase1 = true;
        String remMeLabel = getText(rememberMeLabel);
        String[] splitText = remMeLabel.split(" ");
        for (int i = 0; i < splitText.length; i++) {
            isLabelInCamelCase = isLabelInCamelCase1 && Character.isUpperCase(splitText[i].charAt(0));
            isLabelInCamelCase1 = isLabelInCamelCase;
        }
        return isLabelInCamelCase;
    }

    public boolean unCheckRememberMeCheckBoxAndVerify() {
        boolean isRemMeCheckBox = false;
        if (isSelected(rememberMeCheckBox)) {
            click(rememberMeCheckBox);
            staticWait();
            isRemMeCheckBox = !isSelected(rememberMeCheckBox);
        }
        return !isSelected(rememberMeCheckBox);
    }

    public String LoginErrorMessage() {
        try {
            waitUntilElementDisplayed(loginErrMsg);
            return getText(loginErrMsg);
        } catch (Throwable t) {
            return "Login Error message is not displaying";
        }

    }

    public boolean clickForgotPassword(ForgotYourPasswordPageActions forgotYourPasswordPageActions) {
        click(forgotPasswordLink);
        return waitUntilElementDisplayed(forgotYourPasswordPageActions.passwordResetEmailAddrField);
    }

    public boolean clickHereLink(ForgotYourPasswordPageActions forgotYourPasswordPageActions) {
        waitUntilElementDisplayed(resetPasswordLink);
        click(resetPasswordLink);
        return waitUntilElementDisplayed(forgotYourPasswordPageActions.passwordResetEmailAddrField);
    }

    public boolean clickLoginWithEmptyFieldsAndVerifyErrorMsg(String emailAddrFldErr, String pwdFldErr) {
        click(loginButton);
        if (getText(errorMsg_LoginFields.get(0)).equalsIgnoreCase(emailAddrFldErr) && getText(errorMsg_LoginFields.get(1)).equalsIgnoreCase(pwdFldErr)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyRememberMeLabel(String label) {
        if (getText(rememberMeLabel).equalsIgnoreCase(label)) {
            return true;
        }

        return false;
    }

    public boolean clickCreateAccountLink(CreateAccountActions createAccountActions) {
   /*     staticWait();
        click(createAccountActions.createAcctTab);
        waitUntilElementDisplayed(createAccountActions.createAcctTab,5);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton,10);*/
        waitUntilElementDisplayed(createAccLink, 5);
        click(createAccLink);
        return waitUntilElementDisplayed(createAccountActions.firstNameField, 5);

    }

    public boolean clickCreateAccButtonFromDrawer(CreateAccountActions createAccountActions) {
   /*     staticWait();
        click(createAccountActions.createAcctTab);
        waitUntilElementDisplayed(createAccountActions.createAcctTab,5);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton,10);*/
        waitUntilElementDisplayed(createAccountBtn, 5);
        click(createAccountBtn);
        return waitUntilElementDisplayed(createAccountActions.firstNameField, 5);

    }

    public boolean clickPLCCeSpot() {
        staticWait();
        click(eSpotMPR);
        String urlCompare = getCurrentURL();
        if (urlCompare.contains("double-up"))
            return true;
        else
            return false;

    }

    public boolean clickAirMilesESpot() {
        staticWait();
        click(eSpotAirMiles);
        String urlCompare = getCurrentURL();
        if (urlCompare.contains("air-miles"))
            return true;
        else
            return false;

    }

    public boolean loginFromLoginPopUpPage(String emailAddr, String pwd, HeaderMenuActions headerMenuActions) {
        staticWait();
        clearAndFillText(loginPopupEmail, emailAddr);
        clearAndFillText(loginPopupPassword, pwd);
        staticWait(3000);
        click(loginPopupButton);
        staticWait(5000);
        return headerMenuActions.isUserSignIn();

    }


    public boolean clickCloseLoginModal() {
        waitUntilElementDisplayed(closeLoginModal);
        click(closeLoginModal);
        return verifyElementNotDisplayed(closeLoginModal);
    }


    public boolean clickContinueAsGuestButton(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(continueAsGuestButton, 30);
        click(continueAsGuestButton);
        staticWait(2000);
        return waitUntilElementDisplayed(shippingPageActions.firstNameFld);
    }

    public boolean clickContinueAsGuestButtonBOPIS(CheckoutPickUpDetailsActions checkoutPickUpDetailsActions) {
        waitUntilElementDisplayed(continueAsGuestButton, 10);
        click(continueAsGuestButton);
        return waitUntilElementDisplayed(checkoutPickUpDetailsActions.nxtButton, 30);
    }

    public boolean clickBopisContinueAsGuestButton(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(continueAsGuestButton, 10);
        click(continueAsGuestButton);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 30);
    }

    public boolean showHideLinkPassword(String pwd) {
        waitUntilElementDisplayed(passwordField, 4);
        clearAndFillText(passwordField, pwd);
        click(showHideLink);
        boolean checkShow = getAttributeValue(passwordField, "value").contains(pwd) && getText(showHideLink).equalsIgnoreCase("hide");
        click(showHideLink);
        boolean checkHide = (!(getText(passwordField).contains(pwd))) && getText(showHideLink).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }


    public void closeLoginModal() {
        waitUntilElementDisplayed(closeLoginModal, 5);
        click(closeLoginModal);
        staticWait(3000);
    }

    public boolean validateBtnLoginEnableDisable(String email, String password) {
        waitUntilElementDisplayed(loginButton, 5);
        boolean isBtnDisabled = !(isEnabled(loginButton));

        clearAndFillText(emailAddrField, email);
        clearAndFillText(passwordField, password);

        waitUntilElementClickable(loginButton, 5);

        boolean isBtnEnabled = isEnabled(loginButton);

        return isBtnEnabled && isBtnDisabled;
    }

    public boolean validateErrorMessagesTab(String emailErr, String passwordErr) {
        waitUntilElementDisplayed(emailAddrField, 5);
        emailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailInlineErr, 5);
        boolean validateEmailErr = getText(emailInlineErr).contains(emailErr);
        waitUntilElementDisplayed(passwordField, 5);
        passwordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(passwordInlineErr, 5);
        boolean validatePasswordErr = getText(passwordInlineErr).contains(passwordErr);

        if (validateEmailErr && validatePasswordErr)
            return true;
        else
            return false;
    }

    public boolean enterValidUNAndInvalidPwd(String loginErr) {
        waitUntilElementDisplayed(emailAddrField, 20);
        clearAndFillText(emailAddrField, "tcpsirius5@yopmail.com");
        clearAndFillText(passwordField, "Password");
        click(loginButton);
        staticWait(3000);
        waitUntilElementDisplayed(loginErrMsg, 5);
        boolean validateLoginErr = getText(loginErrMsg).contains(loginErr);
        if (validateLoginErr)
            return true;
        else
            return false;
    }
}
