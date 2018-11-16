package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Venkat on 04/10/2016.
 */
public class MobileWishListLoginOverlayRepo extends MobileHeaderMenuRepo {

    @FindBy(css = "div.message.message-container span")
    public WebElement loveItHeading;

    @FindBy(css = "div.message.message-container p")
    public WebElement subheading1;

    @FindBy(css = "div.message.message-container div")
    public WebElement subheading2;

    @FindBy(css = ".reset-password-button")
    public WebElement resetPasswordLink;

    @FindBy(name = "emailAddress")
    public WebElement emailIDFldQV;

    @FindBy(name = "password")
    public WebElement passwordFldQV;

    @FindBy(css = ".link-forgot")
    public WebElement forgotPasswdLinkQV;

    @FindBy(css = ".button-create-account.no-margin")
    public WebElement createAccLinkQV;

    @FindBy(css = ".setting-edit-section")
    public WebElement wishListLanding;

    @FindBy(css = ".button-primary.login-button")
    public WebElement loginButtonQV;

    @FindBy(css = ".error-box")
    public WebElement errMsgLblQV;

    @FindBy(xpath = "//label[@class='input-common']//input[@name='emailAddress']")//live1
    public WebElement emailIDFldPDP;

    @FindBy(xpath = "//label[@class='input-common']//input[@name='password']")//live1
    public WebElement passwordFldPDP;

    @FindBy(css = ".link-forgot")//live1
    public WebElement forgotPasswdLinkPDP;

    @FindBy(css = ".button-create-account.no-margin")//live1
    public WebElement createAccLinkPDP;

    @FindBy(css = ".button-primary.login-button")//live1
    public WebElement loginButtonPDP;

    @FindBy(css = ".error-box")//live1
    public WebElement errMsgLblPDP;

    //Shopping Bag
    @FindBy(css = "#order_details .modal [name='email']")
    public WebElement emailIDFldShoppingBag; // not in scope, removed

    @FindBy(css = "#order_details .modal [name='password']")
    public WebElement passwordFldShoppingBag; // not in scope, removed

    @FindBy(css = ".button-primary.login-button")//live1
    public WebElement loginButtonShoppingBag;

    @FindBy(css = ".input-common.input-email input")//live1
    public WebElement emailAddrField;

    @FindBy(css = ".input-password.input-with-button input")//live1
    public WebElement passwordField;

    @FindBy(css = ".button-show-password")//live1
    public WebElement showHideLink;

    @FindBy(css = "div[class*='check'] label div")
    public WebElement rememberMeCheckBox;

    @FindBy(xpath = "//span[.='Remember me.']")//live1
    public WebElement rememberMeLabel;

    @FindBy(css = ".label-checkbox.input-checkbox.login-remember ~ .button-primary.login-button")
    public WebElement loginButton;// repeated, need to confirm

    @FindBy(css = ".link-forgot")//live 1
    public WebElement forgotPasswordLink;


    @FindBy(css = ".button-secondary.button-create-account")//live1
    public WebElement createAccountBtn;
}
