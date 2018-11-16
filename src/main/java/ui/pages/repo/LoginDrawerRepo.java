package ui.pages.repo;

import org.apache.poi.ss.formula.functions.WeekNum;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by AbdulazeezM on 3/8/2017.
 */
public class LoginDrawerRepo extends UiBase
{
    @FindBy(css=".access-acount.login-link-container>.login-link.active")
    public WebElement loginLink;

    @FindBy(css =".remembered-logout")
    public WebElement rememberedLogout;

 //   @FindBy(css = ".overlay-content input[name='emailAddress']")
    @FindBy(css=".input-email input")
    public WebElement emailAddrField;

 //     @FindBy(name="password")
 //   @FindBy(css = "input[name='password']")
     @FindBy(css = ".input-password label input")
     public WebElement passwordField;

    @FindBy(css = ".button-show-password")
    public WebElement showHideLink;

    /*@FindBy(css = ".label-checkbox.input-checkbox.login-remember ~ .button-primary.login-button")*/
    @FindBy(css = ".button-primary.login-button")
    public WebElement loginButton;

    @FindBy(css=".general-loading-content")
    public WebElement loadingIcon;

    @FindBy(css = ".form-login  p.error-box")
    public WebElement loginErrMsg;

    @FindBy(css=".ad #espot-tab-link")
    public WebElement createAccountLinkLoginPage;

//    @FindBy(name = "rememberMe")
    @FindBy(css=".label-checkbox.input-checkbox.login-remember div:nth-child(1)")
    public WebElement rememberMeCheckBox;

    @FindBy(css=".label-checkbox.input-checkbox.login-remember .input-checkbox-title")//"[name='rememberMe']+span.input-subtitle")
    public WebElement rememberMeLabel;

//    @FindBy(css = ".new-account .login-banner>div>img")
    @FindBy(xpath = ".//div[@class='login-banner']//img")
    public WebElement myPlaceRewardsESpot;

    @FindBy(css = ".link-forgot")
    public WebElement forgotPasswordLink;

    //@FindBy(css="[name='emailAddress'] + .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid email']")
    public WebElement emailInlineErr;

    //@FindBy(css = "[name='password'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='This field is required.']")
    public WebElement passwordInlineErr;

    @FindBy(css = ".reset-password-text>.reset-password-button")
    public WebElement resetPasswordLink;

    @FindBy(css=".new-account>.button-secondary.button-create-account")
    public WebElement createAccountBtn;

    @FindBy(css=".message>.login-form-title")
    public WebElement titleContentLbl;

    @FindBy(css=".message>.login-form-subtitle")
    public WebElement subTitleContentLbl;

    //@FindBy(css=".resset-password-container>.reset-password-text")
    @FindBy(xpath = ".//div[@class=\"reset-password-container\"]/p[@class=\"reset-password-text\"]")
    public WebElement resetPasswordContentLbl;

    @FindBy(css=".new-account>span")
    public WebElement createAccountContentLbl;

    @FindBy(css =".button-modal-close")
    public WebElement closeLogin;

    @FindBy(css =".button-secondary.continue-guest")
    public WebElement continueBtn;

    @FindBy(css=".button-create-account")
    public WebElement createOneLnk;

    @FindBy(css = ".wishlist-link.active")
    public WebElement wishListIcon;

    @FindBy(css=".button-secondary.continue-guest")
    public WebElement continueAsGuestButton;

    @FindBy(css = "[name='savePlcc']")
    public WebElement uncheckPlcc_Checkbox;

    @FindBy(xpath = ".//button[@class='button-overlay-close']")
    public WebElement closeOverlay;

    @FindBy(xpath = ".//div[@class='ghost-error-container']//div[text()='Email format is invalid.']")
    public WebElement emailSplChar_Err;

    @FindBy(css = ".recaptcha-component")
    public WebElement recaptcha;


}
