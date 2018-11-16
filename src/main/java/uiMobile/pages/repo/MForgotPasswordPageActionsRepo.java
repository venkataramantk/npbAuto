package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by JKotha on 20/10/2017.
 */
public class MForgotPasswordPageActionsRepo extends MobileHeaderMenuRepo {
    @FindBy(css = ".modal-title.modal-only-title")
    public WebElement forgotpwdTitle;

    @FindBy(css = ".back-to-login-button")
    public WebElement backToLoginLink;

    @FindBy(css = ".reset-password-form-title")
    public WebElement sectionHeading;

    @FindBy(css = ".message.message-container p")
    public WebElement emailInstructionstxt;

    @FindBy(css = ".button-primary.reset-button")
    public WebElement resetPwdButton;

    @FindBy(css = ".new-account span")
    public WebElement createAccountTxt;

    @FindBy(css = ".new-account>.button-secondary.button-create-account")
    public WebElement createAccountBtn;

    @FindBy(css = ".back-to-login-button")
    public WebElement loginLink;

    @FindBy(css = ".inline-error-message")
    public WebElement inlineerrorMsg;

    @FindBy(css = ".error-text")
    public WebElement pageLevelError;

    @FindBy(css = ".success-box")
    public WebElement successMsg;

    @FindBy(css = ".success-box a")
    public WebElement custmorCareLink;

    @FindBy(css = ".email-title")
    public WebElement emailusTitle;

    @FindBy(css = ".reset-password [name='emailAddress']")
    public WebElement emailAddrField;

    @FindBy(xpath = ".//p[@class='error-box']")
    public WebElement errMsg;

    @FindBy(css = ".button-modal-close")
    public WebElement closeOverlay;

}
