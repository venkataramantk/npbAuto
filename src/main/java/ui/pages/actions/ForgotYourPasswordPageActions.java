package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.ForgotYourPasswordPageRepo;

/**
 * Created by skonda on 11/21/2016.
 */
public class ForgotYourPasswordPageActions extends ForgotYourPasswordPageRepo {
    WebDriver driver;
    public static String catName;
    Logger logger = Logger.getLogger(ForgotYourPasswordPageActions.class);

    public ForgotYourPasswordPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean enterEmailAddrAndSubmit(String emailAddr){
        clearAndFillText(passwordResetEmailAddrField, emailAddr);
        click(resetPwdButton);
        return waitUntilElementDisplayed(thanksCheckUrInboxMsg, 30) && waitUntilElementDisplayed(returnToLoginButton, 30);
    }

    public boolean validateOverlayContent(String text1, String text2, String text3){

        boolean checkMessage1 = getText(messageContent1).contains(text1);
        boolean checkMessage2 = getText(messageContent2).replaceAll(" "," ").contains(text2);
        boolean checkMessage3 = getText(messageContent3).contains(text3);
        if(checkMessage1&&checkMessage2&&checkMessage3&&waitUntilElementDisplayed(passwordResetEmailAddrField,5))
            return true;
        else
            return false;
    }
//    public boolean backToLogin(LoginDrawerActions loginDrawerActions)
//    {
//        click(loginLink);
//        return waitUntilElementDisplayed(loginDrawerActions.loginButton,2);
//    }

    public boolean clickCreateAccBtn(CreateAccountActions createAccountActions){
        waitUntilElementDisplayed(createAccountBtn,5);
        click(createAccountBtn);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton,5);
    }

    public boolean validateErrorMessages(String errMsg) {
        waitUntilElementDisplayed(passwordResetEmailAddrField,5);
        passwordResetEmailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(errorMsg, 5);
        boolean validateEmptyErrMsg = getText(errorMsg).contains(errMsg);
        if(validateEmptyErrMsg)
            return true;
        else
            return false;

    }
    public boolean validateErrMsgWithSpecialChar(String emailErr)

    {
        waitUntilElementDisplayed(passwordResetEmailAddrField,5);
        clearAndFillText(passwordResetEmailAddrField, "");
        passwordResetEmailAddrField.sendKeys("!@#$%");
        passwordResetEmailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(errorMsg, 5);
        boolean validateEmailErr = getText(errorMsg).contains(emailErr);
        if(validateEmailErr)
            return true;
        else
            return false;
    }
    public boolean successMsgWithValidID(String successMsgContent,String email)

    {
        waitUntilElementDisplayed(passwordResetEmailAddrField,5);
        clearAndFillText(passwordResetEmailAddrField, "");
        waitUntilElementDisplayed(passwordResetEmailAddrField,3);
        passwordResetEmailAddrField.sendKeys(email);
        passwordResetEmailAddrField.sendKeys(Keys.TAB);
        click(resetPwdButton);
        waitUntilElementDisplayed(successMsg, 5);
        boolean validateSuccessErr = getText(successMsg).contains(successMsgContent);
        if(validateSuccessErr)
            return true;
        else
        return false;
    }
    public boolean returnToLoginLink(LoginDrawerActions loginDrawerActions)
    {
        click(returnToLoginButton);
        return waitUntilElementDisplayed(loginDrawerActions.loginButton,5);

    }
    public boolean errorMsgWithInvalidID(String emailID, String errorMsgContent)

    {
        waitUntilElementDisplayed(passwordResetEmailAddrField,5);
        clearAndFillText(passwordResetEmailAddrField, emailID);
        passwordResetEmailAddrField.sendKeys(Keys.TAB);
        click(resetPwdButton);
        staticWait();
        waitUntilElementDisplayed(errMsg, 15);
        boolean validateErrorMsg = getText(errMsg).contains(errorMsgContent);
        if(validateErrorMsg)
            return true;
        else
            return false;
    }
    public boolean closeLoginOverlay(ShoppingBagPageActions shoppingBagPageActions)
    {
        click(closeOverlay);
        return waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle,5);
    }

    public boolean validateForgotPwdDrawer(){
        return isDisplayed(returnToLoginButton) &&
                isDisplayed(passwordResetEmailAddrField) &&
                isDisplayed(resetPwdButton) &&
                isDisplayed(createOneTxt) &&
                isDisplayed(createOneLinkInSB);
    }

    public boolean clickCreateOneAndVerify(CreateAccountActions createAccountActions){
        click(createOneLinkInSB);
        return waitUntilElementDisplayed(createAccountActions.emailAddrField);
    }

    public boolean validateContactUsLink(){
        waitUntilElementDisplayed(contactUs,5);
        click(contactUs);
        return waitUntilElementDisplayed(helpCenter);
    }
}
