package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 6/6/2016.
 */
public class LoginPageRepo extends HeaderMenuRepo {
    @FindBy(css = ".form-login input[name='emailAddress']")
    public WebElement emailFldOnLoginForm;

    @FindBy(css = ".form-login input[name='password']")
    public WebElement pwdFldOnLoginForm;

    @FindBy(css = ".overlay [name='email']")
    public WebElement emailIDFldQV;

    @FindBy(css = ".overlay [name='password']")
    public WebElement passwordFldQV;

    @FindBy(css = ".overlay [value='Log in']")
    public WebElement loginButtonQV;

    @FindBy(css = ".button-modal-close")//".close")
    public WebElement closeLoginModal;

    /*@FindBy(css=".transaction-messaging.error.active")*/
    @FindBy(css = ".form-login  p.error-box ")
    public WebElement loginErrMsg;

    @FindBy(css = "input[name='email']")
    public WebElement loginPopupEmail;

    @FindBy(css = "input[name='password']")
    public WebElement loginPopupPassword;

    @FindBy(css = "input[value='Log in']")
    public WebElement loginPopupButton;

    @FindBy(css = ".transaction-messaging.error.active a")
    public WebElement loginErrMsgLinks;

    @FindBy(css = "#login-tab")
    public WebElement loginTab;

    @FindBy(css = ".form-login input[name='emailAddress']")
    public WebElement emailAddrField;

    @FindBy(css = ".form-login input[name='password']")
    public WebElement passwordField;

    @FindBy(css = ".button-show-password")
    public WebElement showHideLink;

    @FindBy(css = ".new-account .login-banner>div>img")
    public WebElement myPlaceRewardsESpot;
    
    @FindBy(xpath = "(//button[@class='button-primary login-button'])")
    public WebElement loginButton;

    @FindBy(css = ".ad #espot-tab-link")
    public WebElement createAccountLinkLoginPage;

    /*@FindBy(css=".password-info>input")*/
    @FindBy(css = ".label-checkbox.input-checkbox.login-remember div")
    public WebElement rememberMeCheckBox;

    /*@FindBy(xpath="(//div[@class='password-info'])[2]")*/
    @FindBy(css = ".input-subtitle")
    public WebElement rememberMeLabel;

    /*@FindBy(css=".label-right>a")*/
    @FindBy(css = ".label-checkbox.input-checkbox.login-remember + .link-forgot")
    public WebElement forgotPasswordLink;

    @FindBy(xpath = "//img[@alt='Returning members, please log in.']")
    public WebElement returnMembersLogin;

    @FindBy(xpath = "//img[@alt='myPLACE Rewards members, please sign in!']")
    public WebElement membersLogin;

    @FindBy(css = "div[class='fieldset'] label[class='label-error']")
    public List<WebElement> errorMsg_LoginFields;

    @FindBy(xpath = "(//*[@class='ad']//*[contains(@alt,'New! My Place Rewards')])[1]")
    public WebElement eSpotMPR;

    @FindBy(xpath = "//img[contains(@title,'Earn Air Miles Reward Miles for every')]")
    public WebElement eSpotAirMiles;

    //DT2 Page Objects

    //    @FindBy(css="[name='emailAddress'] + .inline-error-message")
    @FindBy(xpath = ".//div[@class='ghost-error-container']//div[text()='Please enter a valid email']")
    public WebElement emailInlineErr;

    //    @FindBy(css = "[name='password'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='ghost-error-container']//div[text()='This field is required.']")
    public WebElement passwordInlineErr;

    @FindBy(css = ".reset-password-text>.reset-password-button")
    public WebElement resetPasswordLink;

    @FindBy(css = ".new-account>.button-secondary.button-create-account")
    public WebElement createAccountBtn;

    @FindBy(css = ".message>.login-form-title")
    public WebElement titleContentLbl;

    @FindBy(css = ".message>.login-form-subtitle")
    public WebElement subTitleContentLbl;

    @FindBy(css = ".resset-password-container>.reset-password-text")
    public WebElement resetPasswordContentLbl;

    @FindBy(css = ".new-account>span")
    public WebElement createAccountContentLbl;

    @FindBy(xpath = "//a[text()='Continue as Guest']")
    public WebElement continueAsGuestButton;

    //@FindBy(css = ".button-create-account.no-margin")
    @FindBy(css = ".button-create-account.no-margin")//.button-secondary.button-create-account")
    public WebElement createAccLink;

    @FindBy(css = ".login-form-title")
    public WebElement welcomeText;

    @FindBy(css = ".remembered-logout")
    public WebElement rememberedLogout;


}
