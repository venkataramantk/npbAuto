package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by AbdulazeezM on 3/10/2017.
 */
public class WishListDrawerRepo extends UiBase
{
    @FindBy(css=".form-login input[name='emailAddress']")//"//label[@class='input-common']//input[@name='emailAddress']")//live1
    public WebElement emailAddrField;


    @FindBy(css=".form-login input[name='password']")//"//label[@class='input-common']//input[@name='password']")//live1
    public WebElement passwordField;

    @FindBy(css = "button-show-password")
    public WebElement showHideLink;

    @FindBy(name = "rememberMe")
    public WebElement rememberMeCheckBox;

    @FindBy(xpath="//span[.='Remember me.']")
    public WebElement rememberMeLabel;

    @FindBy(css=".button-primary.login-button")
    public WebElement loginButton;

    @FindBy(css = ".link-forgot")
    public WebElement forgotPasswordLink;

    @FindBy(xpath="//button[.='CREATE ACCOUNT']")
    public WebElement createAccountBtn;

    @FindBy(css=".message>.login-form-title")
    public WebElement titleContent;

    @FindBy(css=".message>.login-form-subtitle")
    public WebElement subTitleContent;

    @FindBy(css=".reset-password-container>.reset-password-text")
    public WebElement resetPwdContent;

    @FindBy(css = ".reset-password-button")
    public WebElement resetPwdLkn;

    @FindBy(css=".new-account>span")
    public WebElement createAccountContentLbl;

    @FindBy(css=".input-email .inline-error-message")//"[name='emailAddress'] + .inline-error-message")
    public WebElement emailInlineErr;

    @FindBy(css = ".input-password .inline-error-message")//"[name='password'] ~ .inline-error-message")
    public WebElement passwordInlineErr;
}
