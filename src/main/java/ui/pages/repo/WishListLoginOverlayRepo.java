package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Venkat on 04/10/2016.
 */
public class WishListLoginOverlayRepo extends HeaderMenuRepo {

    //Quick view overlay
    //@FindBy(css=".overlay [name='email']")//live2
    @FindBy(name="emailAddress")//live1
    public WebElement emailIDFldQV;

    //@FindBy(css=".overlay [name='password']")//live2
    @FindBy(name="password")//live1
    public WebElement passwordFldQV;

    //@FindBy(css=".overlay a.forgot-password")//live2
    @FindBy(css=".link-forgot")//live1
    public WebElement forgotPasswdLinkQV;

    //@FindBy(css=".overlay a.create-account")//live2
    @FindBy(css=".button-create-account.no-margin")//live1
    public WebElement createAccLinkQV;

    //@FindBy(css=".overlay [value='Log in']")//live2
    @FindBy(css=".button-primary.login-button")//live1
    public WebElement loginButtonQV;

    //@FindBy(css=".modal .transaction-messaging.error.active")//live2
    @FindBy(css=".error-box")//live1
    public WebElement errMsgLblQV;


    //Product Details page
   // @FindBy(css=".modal [name='email']")//live2
    @FindBy(xpath="//label[@class='input-common']//input[@name='emailAddress']")//live1
    public WebElement emailIDFldPDP;

    //@FindBy(css=".modal [name='password']")//live2
    @FindBy(xpath="//label[@class='input-common']//input[@name='password']")//live1
    public WebElement passwordFldPDP;

   // @FindBy(css=".modal a.forgot-password ")//live2
   @FindBy(css=".link-forgot")//live1
    public WebElement forgotPasswdLinkPDP;

    //@FindBy(css=".modal a.create-account")//live2
    @FindBy(css=".button-create-account.no-margin")//live1
    public WebElement createAccLinkPDP;

    //@FindBy(css=".modal [value='Log in']")//live2
    @FindBy(css=".button-primary.login-button")//live1
    public WebElement loginButtonPDP;

    //@FindBy(css=".modal .transaction-messaging.error.active")//live2
    @FindBy(css=".error-box")//live1
    public WebElement errMsgLblPDP;


    //Shopping Bag
    @FindBy(css="#order_details .modal [name='email']")
    public WebElement emailIDFldShoppingBag; // not in scope, removed

    @FindBy(css="#order_details .modal [name='password']")
    public WebElement passwordFldShoppingBag; // not in scope, removed

    //@FindBy(css="#order_details .modal [value='Log in']") //live2
    @FindBy(css=".button-primary.login-button")//live1
    public WebElement loginButtonShoppingBag;

    //@FindBy(name="emailAddress")
    @FindBy(xpath="//label[@class='input-common']//input[@name='emailAddress']")//live1
    public WebElement emailAddrField;

    /*@FindBy(css="#logonPassword1")*/
    //@FindBy(name="password")
    @FindBy(xpath="//label[@class='input-common']//input[@name='password']")//live1
    public WebElement passwordField;

    //@FindBy(css = "[name='password'] + .button-show-password")
    @FindBy(css = ".button-show-password.button-float-right")//live1
    public WebElement showHideLink;

    //@FindBy(name = "rememberMe")
    @FindBy(name = "rememberMe")
    public WebElement rememberMeCheckBox; //work fine live2,1

    /*@FindBy(xpath="(//div[@class='password-info'])[2]")*/
    //@FindBy(css="[name='rememberMe']+span.input-subtitle")//live2
    @FindBy(xpath="//span[.='Remember me.']")//live1
    public WebElement rememberMeLabel;

    @FindBy(css = ".label-checkbox.input-checkbox.login-remember ~ .button-primary.login-button")
    public WebElement loginButton;// repeated, need to confirm

    //@FindBy(css = ".label-checkbox.input-checkbox.login-remember + .link-forgot") //live2
    @FindBy(css = ".link-forgot")//live 1
    public WebElement forgotPasswordLink;

    @FindBy(css = ".new-account .login-banner>div>img")
    public WebElement myPlaceRewardsESpot; // not found need to replace value for live1

    //@FindBy(css = ".reset-password-text>.reset-password-button")//live2
    @FindBy(css = ".button-primary.reset-button")//live1
    public WebElement resetPasswordLink;

    //@FindBy(css=".new-account>.button-secondary.button-create-account") //live2
    @FindBy(css=".button-create-account.no-margin")//live1
    public WebElement createAccountBtn;
}
