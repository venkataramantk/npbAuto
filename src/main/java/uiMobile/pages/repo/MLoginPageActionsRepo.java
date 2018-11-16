package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 20/10/2017.
 */
public class MLoginPageActionsRepo extends UiBaseMobile {

    @FindBy(css = ".message.message-container h3")
    public WebElement welcomeBackMsg;

    @FindBy(css = ".message.message-container p")
    public WebElement mprMsg;

    @FindBy(css = ".input-common.input-email input")
    public WebElement emailAddrField;

    @FindBy(css = ".input-password.input-with-button input")
    public WebElement passwordField;

    @FindBy(css = ".input-checkbox-title")
    public WebElement rememberMeLabel;

    @FindBy(css = ".button-show-password")
    public WebElement showHideLink;

    @FindBy(css = ".link-forgot")
    public WebElement forgotPasswordLink;

    @FindBy(css = ".button-primary.login-button")
    public WebElement loginButton;

    @FindBy(css = ".new-account>.button-secondary.button-create-account")
    public WebElement createAccountBtn;

    @FindBy(css = ".button-create-account")
    public WebElement createAccountBtnFromCheckout;

    @FindBy(css = ".label-checkbox.input-checkbox.login-remember div")
    public WebElement rememberMeCheckBox;

    @FindBy(css = ".form-login  p.error-box span")
    public WebElement loginErrMsg;


    public WebElement inlineMessage(String field) {
        return getDriver().findElement(By.xpath("//div[text()='" + field + "']/../following-sibling::div/div"));
    }

    @FindBy(css = ".button-modal-close")
    public WebElement closeLoginModal;

    @FindBy(css = ".recaptcha-component")
    public WebElement recaptch;

    @FindBy(css = ".reset-password-button")
    public WebElement resetPasswordLink;

    @FindBy(css = ".error-text")
    public WebElement pageLevelError;
}
