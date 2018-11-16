package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileWishListLoginOverlayRepo;

/**
 * Created by Venkat on 04/10/2016.
 */
public class MobileWishListLoginOverlayActions extends MobileWishListLoginOverlayRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileWishListLoginOverlayActions.class);

    public MobileWishListLoginOverlayActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }


    public void enterEmailQV(String email) {
        waitUntilElementDisplayed(emailIDFldQV, 2);
        clearAndFillText(emailIDFldQV, email);
    }

    public void enterEmailPDP(String email) {
        waitUntilElementDisplayed(emailIDFldPDP, 2);
        clearAndFillText(emailIDFldPDP, email);
    }

    public void enterEmailShoppingBag(String email) {
        waitUntilElementDisplayed(emailIDFldShoppingBag, 2);
        clearAndFillText(emailIDFldShoppingBag, email);
    }

    public void enterPasswordQV(String password) {
        waitUntilElementDisplayed(passwordFldQV, 2);
        clearAndFillText(passwordFldQV, password);
    }

    public void enterPasswordPDP(String password) {
        waitUntilElementDisplayed(passwordFldPDP, 2);
        clearAndFillText(passwordFldPDP, password);
    }

    public void enterPasswordShoppingBag(String password) {
        waitUntilElementDisplayed(passwordFldShoppingBag, 2);
        clearAndFillText(passwordFldShoppingBag, password);
    }


    public boolean validateErrMessageQV(String err1, String err2, String err3, String err4) {
        staticWait();
        click(loginButtonQV);
        waitUntilElementDisplayed(errMsgLblQV, 5);
        boolean isEnterDetailsErrMessageDisplaying = getText(errMsgLblQV).contains(err1);
        enterEmailQV("tcpropisTest@yopmail.com");
        click(loginButtonQV);
        waitUntilElementDisplayed(errMsgLblQV, 5);
        boolean isEnterPasswordErrMessageDisplaying = getText(errMsgLblQV).contains(err2);
        emailIDFldQV.clear();
        enterPasswordQV("P@ssw0rd");
        click(loginButtonQV);
        waitUntilElementDisplayed(errMsgLblQV, 5);
        boolean isEnterEmailErrMessageDisplaying = getText(errMsgLblQV).contains(err3);
        enterEmailQV("tcpropisTest@yopmail.com");
        enterPasswordQV("P@ssw0rd");
        click(loginButtonQV);
        waitUntilElementDisplayed(errMsgLblQV, 10);
        boolean isInvalidDetailsErrMessageDisplaying = getText(errMsgLblQV).contains(err4);

        if (isEnterDetailsErrMessageDisplaying &&
                isEnterPasswordErrMessageDisplaying &&
                isEnterEmailErrMessageDisplaying &&
                isInvalidDetailsErrMessageDisplaying) {
            return true;
        } else
            return false;
    }

    public boolean validateErrMessagePDP(String err1, String err2, String err3, String err4) {
        staticWait();
        click(loginButtonPDP);
        waitUntilElementDisplayed(errMsgLblPDP, 5);
        boolean isEnterDetailsErrMessageDisplaying = getText(errMsgLblPDP).contains(err1);
        enterEmailPDP("tcpropisTest@yopmail.com");
        click(loginButtonPDP);
        waitUntilElementDisplayed(errMsgLblPDP, 5);
        boolean isEnterPasswordErrMessageDisplaying = getText(errMsgLblPDP).contains(err2);
        emailIDFldPDP.clear();
        enterPasswordPDP("P@ssw0rd");
        click(loginButtonPDP);
        waitUntilElementDisplayed(errMsgLblPDP, 5);
        boolean isEnterEmailErrMessageDisplaying = getText(errMsgLblPDP).contains(err3);
        enterEmailPDP("tcpropisTest@yopmail.com");
        enterPasswordPDP("P@ssw0rd");
        click(loginButtonPDP);
        waitUntilElementDisplayed(errMsgLblPDP, 10);
        boolean isInvalidDetailsErrMessageDisplaying = getText(errMsgLblPDP).contains(err4);

        if (isEnterDetailsErrMessageDisplaying &&
                isEnterPasswordErrMessageDisplaying &&
                isEnterEmailErrMessageDisplaying &&
                isInvalidDetailsErrMessageDisplaying) {
            return true;
        } else
            return false;
    }

    public boolean validateLinksPresentQV() {
        boolean isCreateAccountLinkDisplaying = waitUntilElementDisplayed(createAccLinkQV, 5);
        boolean isForgotPasswordLinkDisplaying = waitUntilElementDisplayed(forgotPasswdLinkQV, 5);

        if (isCreateAccountLinkDisplaying && isForgotPasswordLinkDisplaying)
            return true;
        else
            return false;
    }

    public boolean validateLinksPresentPDP() {
        boolean isCreateAccountLinkDisplaying = waitUntilElementDisplayed(createAccLinkPDP, 5);
        boolean isForgotPasswordLinkDisplaying = waitUntilElementDisplayed(forgotPasswdLinkPDP, 5);

        if (isCreateAccountLinkDisplaying && isForgotPasswordLinkDisplaying)
            return true;
        else
            return false;
    }


    public boolean validateWLLoginPage() {
        return waitUntilElementDisplayed(emailAddrField) &&
                waitUntilElementDisplayed(passwordField, 4) &&
                waitUntilElementDisplayed(rememberMeCheckBox, 4) &&
                waitUntilElementDisplayed(rememberMeLabel, 4) &&
                waitUntilElementDisplayed(forgotPasswordLink, 2) &&
                waitUntilElementDisplayed(loginButton, 2) &&
                waitUntilElementDisplayed(createAccountBtn, 2) &&
                waitUntilElementDisplayed(resetPasswordLink, 2) &&
                waitUntilElementDisplayed(loveItHeading, 2) &&
                waitUntilElementDisplayed(subheading1, 2) &&
                waitUntilElementDisplayed(subheading2, 2) &&
                waitUntilElementDisplayed(showHideLink, 2);
    }

    public boolean loginfromWishList(String email, String pwsd) {
        waitUntilElementDisplayed(emailAddrField);
        clearAndFillText(emailAddrField, email);
        clearAndFillText(passwordField, pwsd);
        click(loginButton);
        return waitUntilElementDisplayed(wishListLanding, 60);
    }
}
