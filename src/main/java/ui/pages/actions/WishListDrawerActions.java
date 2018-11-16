
package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.WishListDrawerRepo;

/**
 * Created by AbdulazeezM on 3/10/2017.
 */
public class WishListDrawerActions extends WishListDrawerRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(WishListDrawerActions.class);

    public WishListDrawerActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
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

    public boolean validateWishListLoginOverlayContent(String text1, String text2, String text3, String text4) {

        boolean seeSomethingText = getText(titleContent).contains(text1);
        boolean subTextWishlist = getText(subTitleContent).contains(text2);
        boolean resetTextWishlist = getText(resetPwdContent).contains(text3);
        boolean newToTextWishlist = getText(createAccountContentLbl).contains(text4);
        if (seeSomethingText && subTextWishlist && resetTextWishlist && newToTextWishlist)
            return true;
        else
            return false;
    }

    public boolean validateWishListLoginOverlayContentCA(String text1, String text2) {

        boolean seeSomethingText = getText(titleContent).contains(text1);
        boolean subTextWishlist = getText(subTitleContent).contains(text2);

        if (seeSomethingText && subTextWishlist /*&& resetTextWishlist && newToTextWishlist*/)
            return true;
        else
            return false;
    }

    public boolean validateErrMsg(String err1, String err2) {
        clearAndFillText(emailAddrField, "");
        emailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailInlineErr, 5);
        boolean validateEmailErr = getText(emailInlineErr).contains(err1);
        clearAndFillText(passwordField, "");
        passwordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(passwordInlineErr, 5);
        boolean validatePasswordErr = getText(passwordInlineErr).contains(err2);

        if (validateEmailErr && validatePasswordErr)
            return true;
        else
            return false;
    }

    public boolean validateErrMsgSplChar(String emailErr)

    {
        clearAndFillText(emailAddrField, "");
        emailAddrField.sendKeys("!@#$%");
        emailAddrField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(emailInlineErr, 5);
        boolean validateEmailErr = getText(emailInlineErr).contains(emailErr);
        if (validateEmailErr)
            return true;
        else
            return false;
    }

    public boolean validErrMsgValidUserName(String emailAddr, String passwordErr)

    {
        clearAndFillText(emailAddrField, "");
        emailAddrField.sendKeys(emailAddr);
        passwordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(passwordInlineErr, 5);
        boolean validatePasswordErr = getText(passwordInlineErr).contains(passwordErr);
        checkRememberMeCheckBoxAndVerify();
        if (validatePasswordErr)
            return true;
        else
            return false;
    }


    public boolean successfulLogin(String validEmail, String validPassword, FavoritePageActions favoritePageActions) {
        staticWait(1000);
        waitUntilElementDisplayed(emailAddrField, 20);
        clearAndFillText(emailAddrField, validEmail);
        waitUntilElementDisplayed(passwordField, 2);
        clearAndFillText(passwordField, validPassword);
        staticWait(2000);
        click(loginButton);

        return favoritePageActions.isWishListPageDisplayed();
    }
}