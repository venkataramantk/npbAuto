package ui.pages.actions;


import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import schemasMicrosoftComOfficeOffice.STInsetMode;
import ui.pages.repo.LoginDrawerRepo;

/**
 * Created by AbdulazeezM on 3/8/2017.
 */
public class LoginDrawerActions extends LoginDrawerRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(HeaderMenuActions.class);

    public String storeName;

    public LoginDrawerActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }



    public boolean userLogin(String userName, String password, OverlayHeaderActions overlayHeaderActions) {
        enterEmailIdAndPwd(userName, password);
        return clickLoginButton(overlayHeaderActions);
    }

    public boolean userLoginPDP(String userName, String password, OverlayHeaderActions overlayHeaderActions) {
        enterEmailIdAndPwd(userName, password);
        waitUntilElementDisplayed(loginButton,5);
        click(loginButton);
        return waitUntilElementDisplayed(overlayHeaderActions.logout,30);
}

    public boolean loginWithRememberMe(String userName, String password, OverlayHeaderActions overlayHeaderActions){
        enterEmailAddressAndPwd(userName,password);
        if (!isEnabled(rememberMeCheckBox)) {
            click(rememberMeCheckBox);
        }
        return clickLoginButton(overlayHeaderActions);

    }

    public boolean enterPasswordForRememberUser(String userName, String password, OverlayHeaderActions overlayHeaderActions){
        boolean isEmailAutoFilled = getAttributeValue(emailAddrField, "value").equalsIgnoreCase(userName);
        if(!isEmailAutoFilled){
            addStepDescription("Email is not auto filled for remembered user");
        }
        clearAndFillText(passwordField, password);
        return clickLoginButton(overlayHeaderActions);

    }

    public void enterEmailIdAndPwd(String userName, String password) {
        if (waitUntilElementDisplayed(emailAddrField,5)) {
            clearAndFillText(emailAddrField, userName);
            clearAndFillText(passwordField, password);
        }
    }
    public boolean clickLoginButton(OverlayHeaderActions overlayHeaderActions){
        waitUntilElementDisplayed(loginButton,5);
        click(loginButton);
        return waitUntilElementDisplayed(overlayHeaderActions.logout,30) && verifyElementNotDisplayed(loadingIcon,60);
    }


    public boolean clickForgotPasswordLnk(ForgotYourPasswordPageActions forgotYourPasswordPageActions){
        click(forgotPasswordLink);
        return waitUntilElementDisplayed(forgotYourPasswordPageActions.passwordResetEmailAddrField);
    }


    //DT2 Actions
    public boolean validateLoginDrawer() {
        boolean areAllFieldsDisplaying = waitUntilElementDisplayed(emailAddrField, 10) &&
                waitUntilElementDisplayed(passwordField, 2) &&
                waitUntilElementDisplayed(rememberMeCheckBox, 2) &&
                waitUntilElementDisplayed(rememberMeLabel,2)&&
                getText(rememberMeLabel).contains("Remember me.")&&
                waitUntilElementDisplayed(forgotPasswordLink, 2) &&
                waitUntilElementDisplayed(loginButton, 2) &&
                waitUntilElementDisplayed(createAccountBtn, 2) &&
                waitUntilElementDisplayed(resetPasswordLink, 2)&&
                waitUntilElementDisplayed(showHideLink, 2) &&
                waitUntilElementDisplayed(myPlaceRewardsESpot, 2);
        if (areAllFieldsDisplaying)
            return true;
        else
            return false;
    }

    public boolean validateLoginDrawerCA() {
        boolean areAllFieldsDisplaying = waitUntilElementDisplayed(emailAddrField, 10) &&
                waitUntilElementDisplayed(passwordField, 2) &&
                waitUntilElementDisplayed(rememberMeCheckBox, 2) &&
                waitUntilElementDisplayed(rememberMeLabel,2)&&
                getText(rememberMeLabel).contains("Remember me.")&&
                waitUntilElementDisplayed(forgotPasswordLink, 2) &&
                waitUntilElementDisplayed(loginButton, 2) &&
                waitUntilElementDisplayed(createAccountBtn, 2) &&
                waitUntilElementDisplayed(showHideLink, 2);
        if (areAllFieldsDisplaying)
            return true;
        else
            return false;
    }

    public boolean validateOverlayContent(String text1, String text2, String text3,String text4) {

        boolean welcomeText = getText(titleContentLbl).replaceAll(" ", "").contains(text1);
        boolean subText = getText(subTitleContentLbl).replaceAll(" ", " ").contains(text2);
        boolean resetText = getText(resetPasswordContentLbl).replaceAll(" ", " ").contains(text3);
        boolean newToText = getText(createAccountContentLbl).replaceAll(" ", " ").contains(text4);
        if (welcomeText && subText && resetText && newToText)
            return true;
        else
            return false;
    }
    public boolean showHideLinkPassword(String pwd) {
        waitUntilElementDisplayed(passwordField,4);
        clearAndFillText(passwordField, pwd);
        click(showHideLink);
        boolean checkShow = getAttributeValue(passwordField, "value").contains(pwd) && getText(showHideLink).equalsIgnoreCase("hide");
        click(showHideLink);
        boolean checkHide = (!(getText(passwordField).contains(pwd))) && getText(showHideLink).equalsIgnoreCase("show");
        return checkShow&&checkHide;
    }
   /* public boolean showHideLink(String pwd) {

        clearAndFillText(passwordField, pwd);
        click(showHideLink);
        boolean checkShow = getText(passwordField).contains(pwd) && getText(showHideLink).equalsIgnoreCase("hide");
        click(showHideLink);
        boolean checkHide = (!(getText(passwordField).contains(pwd))) && getText(showHideLink).equalsIgnoreCase("show");
        return checkShow&&checkHide;
    }*/
    public boolean validateLoginOverlayContent(String text1, String text2, String text3, String text4) {

        boolean seeSomethingText = getText(titleContentLbl).contains(text1);
        boolean subTextWishlist = getText(subTitleContentLbl).contains(text2);
        boolean resetTextWishlist = getText(resetPasswordContentLbl).replaceAll(" ", " ").contains(text3);
        boolean newToTextWishlist = text4.contains(getText(createAccountContentLbl).replaceAll(" ", " "));//.contains(text4);
        if (seeSomethingText && subTextWishlist && resetTextWishlist && newToTextWishlist)
            return true;
        else
            return false;
    }

    public boolean validateLoginOverlayContentCA(String text1, String text2) {

        boolean seeSomethingText = getText(titleContentLbl).contains(text1);
        boolean subTextWishlist = getText(subTitleContentLbl).contains(text2);

        if (seeSomethingText && subTextWishlist)
            return true;
        else
            return false;
    }

    public boolean clickCreateAccButton(CreateAccountActions createAccountActions) {
        click(createAccountBtn);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton);
    }
    public boolean clickCreateOneLink(CreateAccountActions createAccountActions) {
        if(isDisplayed(createOneLnk)) {
            click(createOneLnk);
            return waitUntilElementDisplayed(createAccountActions.firstNameField);
        }
        else{
            return false;
        }
    }
    public boolean clickForgotPassword(ForgotYourPasswordPageActions forgotYourPasswordPageActions){
        click(forgotPasswordLink);
        return waitUntilElementDisplayed(forgotYourPasswordPageActions.passwordResetEmailAddrField);
    }

    public boolean validateErrorMessagesTab(String emailErr, String passwordErr){
        waitUntilElementDisplayed(emailAddrField,5);
        emailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailInlineErr, 5);
        boolean validateEmailErr = getText(emailInlineErr).contains(emailErr);

        waitUntilElementDisplayed(passwordField,5);
        passwordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(passwordInlineErr,5);
        boolean validatePasswordErr = getText(passwordInlineErr).contains(passwordErr);

        if(validateEmailErr && validatePasswordErr)
            return true;
        else
            return false;
    }
    public boolean validateErrMsgSpecialChar(String emailErr)

    {
        waitUntilElementDisplayed(emailAddrField,5);
        clearAndFillText(emailAddrField, "!@#$%");
        emailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailSplChar_Err, 5);
        boolean validateEmailErr = getText(emailSplChar_Err).contains(emailErr);

        if(validateEmailErr)
            return true;
        else
            return false;
    }

    public boolean checkRememberMeCheckBoxAndVerify(){
        boolean isRemMeCheckBox = false;
        if (!isSelected(rememberMeCheckBox)) {
            click(rememberMeCheckBox);
            staticWait();
            isRemMeCheckBox = isSelected(rememberMeCheckBox);
        }
        else{
            isRemMeCheckBox = isSelected(rememberMeCheckBox);

        }
        return isRemMeCheckBox;
    }
    public boolean validErrMsgValidUserName(String emailAddr, String passwordErr)

    {
        waitUntilElementDisplayed(emailAddrField,5);
        clearAndFillText(emailAddrField,"");
        emailAddrField.sendKeys(Keys.TAB);
        passwordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(passwordInlineErr,5);
        boolean validatePasswordErr = getText(passwordInlineErr).contains(passwordErr);
        checkRememberMeCheckBoxAndVerify();
        if(validatePasswordErr)
            return true;
        else
            return false;
    }
    public boolean loginWithInvalidID(String invalidEmail, String invalidPwd,String userNameErr, String pwdErr)
    {
        waitUntilElementDisplayed(emailAddrField, 5);
        clearAndFillText(emailAddrField, "");
        emailAddrField.sendKeys(Keys.TAB);
        boolean validateUNErrMsgContent = getText(loginErrMsg).contains(userNameErr);

        clearAndFillText(passwordField,"");
        passwordField.sendKeys(Keys.TAB);
        boolean validatePasswordErr = getText(passwordInlineErr).contains(pwdErr);
        if (validateUNErrMsgContent&&validatePasswordErr)
            return true;
        else
            return false;
    }
    public void enterEmailAddressAndPwd(String userName, String password){
        if(waitUntilElementDisplayed(emailAddrField)) {
            clearAndFillText(emailAddrField, userName);
            clearAndFillText(passwordField, password);
        }
    }

    public boolean loginWithInvalidCred(String invalidEmail, String invalidPwd, String loginErr){
        staticWait(1000);
        enterEmailAddressAndPwd(invalidEmail, invalidPwd);
        click(loginButton);
        boolean validateLoginErrMsgContent = false;
        if(waitUntilElementDisplayed(loginErrMsg, 15)){
             validateLoginErrMsgContent = getText(loginErrMsg).replaceAll(",","").contains(loginErr);
        }
        if (validateLoginErrMsgContent)
            return true;
        else
        return false;

    }

    public boolean  successfulLogin(String validEmail, String validPassword, HeaderMenuActions headerMenuActions){
        staticWait(1000);
        waitUntilElementDisplayed(emailAddrField,20);
        clearAndFillText(emailAddrField, validEmail);
        waitUntilElementDisplayed(passwordField,10);
        clearAndFillText(passwordField, validPassword);
        staticWait(4000);
        click(loginButton);
        staticWait(2000);
//        driver.navigate().refresh();
        staticWait(2000);
        return waitUntilElementDisplayed(headerMenuActions.welcomeMessage,20);
    }

    public boolean  accountLockedErrorMsgLogin(String validEmail, String validPassword){
        staticWait(1000);
        waitUntilElementDisplayed(emailAddrField,20);
        clearAndFillText(emailAddrField, validEmail);
        waitUntilElementDisplayed(passwordField,10);
        clearAndFillText(passwordField, validPassword);
        staticWait(4000);
        click(loginButton);
        staticWait(2000);
//        driver.navigate().refresh();
        staticWait(2000);
        return waitUntilElementDisplayed(loginErrMsg,3);
    }

    public boolean validateBtnLoginEnableDisable(String email, String password){
        waitUntilElementDisplayed(loginButton, 5);
        boolean isBtnDisabled = !(isEnabled(loginButton));

        clearAndFillText(emailAddrField, email);
        clearAndFillText(passwordField,password);

        waitUntilElementClickable(loginButton,5);

        boolean isBtnEnabled = isEnabled(loginButton);

        return isBtnEnabled && isBtnDisabled;
    }

    public boolean accountLocksWith_5_InvalidAttempts(String inValidUN, String invalidPwd, String loginErr){
        boolean isLoginErrMsg = false;
        int count = 0;
        do{
            isLoginErrMsg = loginWithInvalidCred(inValidUN, invalidPwd, loginErr);
            count++;
        }while(count <=6);

        return isLoginErrMsg;
    }

    public void closeLoginDrawer()
    {
        click(closeLogin);
    }

    public boolean clickWishlistLink(WishListDrawerActions wishListDrawerActions) {
        waitUntilElementDisplayed(wishListIcon);
        click(wishListIcon);
        return waitUntilElementDisplayed(wishListDrawerActions.titleContent);
    }
    public boolean uncheckPlcc_Login(String pwd,HeaderMenuActions headerMenuActions){
        waitUntilElementDisplayed(loginButton,10);
        clearAndFillText(passwordField,pwd);
        if (isDisplayed(uncheckPlcc_Checkbox))
            click(uncheckPlcc_Checkbox);
        staticWait(5000);
        return waitUntilElementDisplayed(headerMenuActions.bagLink,10);
    }

    public boolean clickRememberedLogout(HeaderMenuActions headerMenuActions) {
        if (isDisplayed(rememberedLogout)){
            click(rememberedLogout);
            return waitUntilElementDisplayed(headerMenuActions.loginLinkHeader);
        }
        else{
            addStepDescription("Remembered logout is not displaying");
            return false;
        }
    }

    public boolean validateLoginPage() {
        boolean areAllFieldsDisplaying = waitUntilElementDisplayed(emailAddrField, 2) &&
                waitUntilElementDisplayed(passwordField, 2) &&
                waitUntilElementDisplayed(rememberMeCheckBox, 2) &&
                waitUntilElementDisplayed(rememberMeLabel, 2) &&
                waitUntilElementDisplayed(forgotPasswordLink, 2) &&
                waitUntilElementDisplayed(loginButton, 2) &&
                waitUntilElementDisplayed(showHideLink, 2);
        if (areAllFieldsDisplaying)
            return true;
        else
            return false;
    }
    public boolean validateRecaptcha(){
        waitUntilElementDisplayed(recaptcha,5);
        if (isDisplayed(recaptcha)){
            return true;
        }
        else{
            addStepDescription("Re-captcha is not displayed in the Login modal");
            return false;
        }
    }
}
