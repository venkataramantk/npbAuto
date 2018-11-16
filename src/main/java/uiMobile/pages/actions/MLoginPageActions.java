package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MLoginPageActionsRepo;

/**
 * Created by JKotha on 20/10/2017.
 */
public class MLoginPageActions extends MLoginPageActionsRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MLoginPageActions.class);
    MForgotPasswordPageActions forgotYourPasswordPageActions;
    MCreateAccountActions createAccountActions;

    public MLoginPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        forgotYourPasswordPageActions = new MForgotPasswordPageActions(mobileDriver);
        createAccountActions = new MCreateAccountActions(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Verify whether the login page is displayed or not
     *
     * @return true if login page displayed
     */
    public boolean validateLoginPage() {
        return waitUntilElementDisplayed(welcomeBackMsg, 2) &&
                waitUntilElementDisplayed(mprMsg, 2) &&
                waitUntilElementDisplayed(emailAddrField, 2) &&
                waitUntilElementDisplayed(passwordField, 2) &&
                waitUntilElementDisplayed(rememberMeCheckBox, 2) &&
                waitUntilElementDisplayed(rememberMeLabel, 2) &&
                waitUntilElementDisplayed(forgotPasswordLink, 2) &&
                waitUntilElementDisplayed(loginButton, 2) &&
                waitUntilElementDisplayed(showHideLink, 2);

    }

    /**
     * Login As Registered user and Password
     * from mobile browser
     *
     * @param emailAddr Email of the user
     * @param pwd       Password of the user
     * @return true if the login successful
     */
    public void loginAsRegisteredUserFromLoginForm(String emailAddr, String pwd) {
        PanCakePageActions panCakePageActions = new PanCakePageActions(mobileDriver);
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, emailAddr);
        clearAndFillText(passwordField, pwd);
        click(loginButton);
        //staticWait(10000);
        waitUntilElementDisplayed(panCakePageActions.salutationLinkOnLogin, 60);
        addStepDescription("Logged into the account " + emailAddr);
        mobileDriver.navigate().refresh();
    }

    /**
     * Created by RichaK
     * Login from move to favorites login dialog
     *
     * @param emailAddr Email of the user
     * @param pwd       Password of the user
     * @return true if the login successful
     */
    public void loginAsRegisteredUserFromMoveToFav(String emailAddr, String pwd, MobileHeaderMenuActions headerMenuActions, PanCakePageActions panCakePageActions) {
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, emailAddr);
        clearAndFillText(passwordField, pwd);
        click(loginButton);
        jqueryClick(headerMenuActions.menuIconHeader);
        waitUntilElementDisplayed(panCakePageActions.menuClose, 20);
        waitUntilElementDisplayed(panCakePageActions.myAccount);
        mobileDriver.navigate().refresh();
    }


    /**
     * Login As Registered user and Password
     * from mobile browser
     *
     * @param emailAddr Email of the user
     * @param pwd       Password of the user
     * @return true if the login successful
     */
    public boolean loginWithIncorrectCredentails(String emailAddr, String pwd) {
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, emailAddr);
        clearAndFillText(passwordField, pwd);
        click(loginButton);
        return waitUntilElementDisplayed(loginErrMsg, 10);
    }

    /**
     * Created by Shubhika
     * Return error message when user logs in with incorrect credentials.
     * @param emailAddr
     * @param pwd
     * @return
     */
    public String loginWithIncorrectCredentailErrorMsg(String emailAddr, String pwd) {
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, emailAddr);
        clearAndFillText(passwordField, pwd);
        click(loginButton);
        return getText(loginErrMsg);
    }
    
    /**
     * Created by RichaK
     * Login with blank credentials
     * from mobile browser
     */
    public void loginWithEmptyCredentails() {
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, "");
        clearAndFillText(passwordField, "");
        tabFromField(passwordField);
        click(loginButton);
    }
    
    /**
     * Verify the text displayed in show/hide link
     *
     * @param text : string to compare
     * @return true
     * @Author: JK
     */
    public boolean clickShowHideLink(String text) {
        click(showHideLink);
        staticWait(2000);
        String act = showHideLink.getText();
        return act.equalsIgnoreCase(text);
    }

    /**
     * Login As Registered user and Password
     * from mobile browser
     *
     * @param emailAddr Email of the user
     * @param pwd       Password of the user
     * @return true if the login successful
     */
    public boolean validateErrorMessageForAccountLocked(String emailAddr, String pwd) {
        waitUntilElementDisplayed(emailAddrField);
        for (int i = 0; i < 7; i++) {
            staticWait();
            clearAndFillText(emailAddrField, emailAddr);
            clearAndFillText(passwordField, pwd);
            click(loginButton);
        }
        if (waitUntilElementDisplayed(loginErrMsg, 10))
            return loginErrMsg.getText().contains("the account has been locked");
        return false;
    }

    /**
     * Gets the error message in login page
     *
     * @return error message
     */
    public String getLoginErrorMsg() {
        waitUntilElementDisplayed(loginErrMsg, 10);
        return getText(loginErrMsg);
    }

    /**
     * Click Forgot Password link in login page
     *
     * @return trut if forgot password page is displayed
     */

    public boolean clickForgotPassword() {
        click(forgotPasswordLink);
        return waitUntilElementDisplayed(forgotYourPasswordPageActions.emailAddrField);
    }

    /**
     * Created by RichaK
     * Click on Reset password link on login page.
     *
     * @return
     */
    public boolean clickResetPassword() {
        waitUntilElementDisplayed(resetPasswordLink);
        click(resetPasswordLink);
        return waitUntilElementDisplayed(forgotYourPasswordPageActions.emailAddrField);
    }

    /**
     * Verify Remember Me Label text
     *
     * @param label Label to compare
     * @return true if the Remember Me label match with expected value
     */
    public boolean verifyRememberMeLabel(String label) {
        if (getText(rememberMeLabel).equalsIgnoreCase(label)) {
            return true;
        }
        return false;
    }

    /**
     * Click on Create Account link in Login Page
     *
     * @return true if Create Account Page displayed
     */
    public boolean clickCreateAccountLink() {
        waitUntilElementDisplayed(createAccountBtn, 5);
        click(createAccountBtn);
        return waitUntilElementDisplayed(createAccountActions.createAccountTitle, 5);
    }

    /**
     * Close login page
     *
     * @return true if the login page is disappeared
     */
    public boolean clickCloseLoginModal() {
        waitUntilElementDisplayed(closeLoginModal);
        click(closeLoginModal);
        return verifyElementNotDisplayed(closeLoginModal);
    }

    /**
     * Convert password to rawt
     *
     * @param pwd password to type and convert
     * @return true if the password convert
     */
    public boolean showHideLinkPassword(String pwd) {
        waitUntilElementDisplayed(passwordField, 4);
        clearAndFillText(passwordField, pwd);
        click(showHideLink);
        boolean checkShow = getAttributeValue(passwordField, "value").contains(pwd) && getText(showHideLink).equalsIgnoreCase("hide");
        click(showHideLink);
        boolean checkHide = (!(getText(passwordField).contains(pwd))) && getText(showHideLink).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }

    /**
     * Verify login button states
     *
     * @param email    email address
     * @param password password
     * @return true if correct states return
     */
    public boolean validateBtnLoginEnableDisable(String email, String password) {
        waitUntilElementDisplayed(loginButton, 5);
        boolean isBtnDisabled = !(isEnabled(loginButton));

        clearAndFillText(emailAddrField, email);
        clearAndFillText(passwordField, password);

        waitUntilElementClickable(loginButton, 5);

        boolean isBtnEnabled = isEnabled(loginButton);

        return isBtnEnabled && isBtnDisabled;
    }

    /**
     * Login As Registered user and Password checking Remember Me checkbox
     * from mobile browser
     *
     * @param userName Email of the user
     * @param password Password of the user
     */
    public void loginWithRememberMe(String userName, String password, boolean rememberMe) {
        PanCakePageActions panCakePageActions = new PanCakePageActions(mobileDriver);
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, userName);
        clearAndFillText(passwordField, password);
        if (!rememberMe) {
            if (rememberMeCheckBox.isEnabled())
                click(rememberMeCheckBox);
        } else {
            if (!(rememberMeCheckBox.isEnabled())) {
                click(rememberMeCheckBox);
            }
        }

        click(loginButton);

        waitUntilElementDisplayed(panCakePageActions.salutationLink);
        mobileDriver.navigate().refresh();
    }

    public boolean isEmailAddressDisplayed() {
        return waitUntilElementDisplayed(emailAddrField);
    }

    /**
     * Verify whether recaptch is displayed or not after 3 failed attempts
     *
     * @return
     */
    public boolean validateRecaptch() {
        return waitUntilElementDisplayed(recaptch, 60);
    }

    /**
     * Created by Richa Priya
     *
     * @return
     */
    public boolean clickCreateAcctFromCheckoutSB(MCreateAccountActions mcreateAccountActions) {
        waitUntilElementDisplayed(createAccountBtnFromCheckout, 10);
        click(createAccountBtnFromCheckout);
        return waitUntilElementDisplayed(mcreateAccountActions.firstNameField, 10);
    }

    public boolean loginAsRegisteredUserFromCheckout(String emailAddr, String pwd, MShippingPageActions mshippingPageActions, MReviewPageActions mReviewPageActions) {
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, emailAddr);
        clearAndFillText(passwordField, pwd);
        click(loginButton);
        staticWait(10000);
        return waitUntilElementDisplayed(mshippingPageActions.nextBillingButton) || waitUntilElementDisplayed(mReviewPageActions.submitOrderBtn);
    }

    public void fillLoginFieldText(String field, String value) {
        switch (field.toUpperCase()) {
            case "EMAIL":
                if (waitUntilElementDisplayed(emailAddrField, 10)) {
                    clearAndFillText(emailAddrField, value);
                } else {
                    addStepDescription("Email address field is not displayed in login Page");
                }
                break;
            case "PASSWORD":
                if (waitUntilElementDisplayed(passwordField, 10)) {
                    clearAndFillText(passwordField, value);
                } else {
                    addStepDescription("Email address field is not displayed in login Page");
                }
                break;
        }
    }

    public boolean verifyPageLevelError() {
        if (waitUntilElementDisplayed(pageLevelError, 10)) {
            return waitUntilElementDisplayed(pageLevelError, 2);
        } else {
            addStepDescription("Page lever error is not displayed in login page");
            return false;
        }
    }

    public String getFieldLeverError(String field) {
        String s = "";
        switch (field.toUpperCase()) {
            case "EMAIL":
                s = getText(inlineMessage("Email Address"));
                break;
            case "PASSWORD":
                s = getText(inlineMessage("Password"));
                break;
        }
        return s;
    }
}

