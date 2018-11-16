package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by skonda on 11/21/2016.
 */
public class ForgotYourPasswordPageRepo extends HeaderMenuRepo {
    /*@FindBy(css="#WC_TCPPasswordResetForm_FormInput_email_In_ResetPasswordForm_1")*/
    @FindBy(css=".reset-password [name=emailAddress]")
    public WebElement passwordResetEmailAddrField;

    /*@FindBy(css="#WC_TCPPasswordResetForm_Link_2")*/
    @FindBy(css=".button-primary.reset-button")
    public WebElement resetPwdButton;

    /*@FindBy(css=".forgot-password>h1")*/
    @FindBy(css=".reset-password .success-box")
    public WebElement thanksCheckUrInboxMsg;

    /*@FindBy(css=".forgot-password .button-blue")*/
//    @FindBy(css = ".reset-password >.button-primary")
    @FindBy(css = ".back-to-login-button")
    public WebElement returnToLoginButton;

    //DT2 Page Objects

    @FindBy(css=".new-account>.button-secondary.button-create-account")
    public WebElement createAccountBtn;

    @FindBy(css=".new-account")
    public WebElement createOneTxt;

    @FindBy(css=".button-create-account")
    public WebElement createOneLinkInSB;

    @FindBy(css=".message>h3")
    public WebElement messageContent1;

    @FindBy(css=".message>p")
    public WebElement messageContent2;

    @FindBy(xpath = ".//div[@class='new-account']/span")
    public WebElement messageContent3;

    @FindBy(css=".inline-error-message")
    public WebElement errorMsg;

    @FindBy(css=" .success-box")
    public WebElement successMsg;

    @FindBy(xpath=".//p[@class='error-box']")
    public WebElement errMsg;

    @FindBy(css=".button-modal-close")
    public WebElement closeOverlay;

    @FindBy(css=".success-box a")
    public WebElement contactUs;

    @FindBy(css=".email-title.viewport-container")
    public WebElement helpCenter;

}
