package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 6/15/2016.
 */
public class PayPalPageRepo extends HeaderMenuRepo{
	
//	@FindBy(css="input[name='login_email']")
//    public WebElement emailAddress;

//    @FindBy(xpath="//input[@id='email']")
//    public WebElement emailAddress;

//    @FindBy(xpath="//*[contains(@name,'email') and contains(@id,'email')]")
//    @FindBy(xpath = ".//input[@class='hasHelp  validate validateEmpty   ']")
    @FindBy(css = ".textInput input#email")
    /*@FindBy(css = "#loginBox input#login_email")*/
    public WebElement emailAddressNew;

    /*@FindBy(css = ".textInput input#email")*/
    @FindBy(css = "#loginBox input#login_email")
    public WebElement emailAddress;
	
//	@FindBy(xpath="//input[@id='password']")
//    public WebElement emailPassword;

//    @FindBy(xpath="//*[contains(@name,'password') and contains(@id,'password')]")
//    @FindBy(xpath = ".//input[@class='hasHelp  validateEmpty   pin-password']")
    @FindBy(css = ".textInput input#password")
    /*@FindBy(css = "#loginBox input#login_password")*/
    public WebElement emailPasswordNew;

    /*@FindBy(css = ".textInput input#password")*/
    @FindBy(css = "#loginBox input#login_password")
    public WebElement emailPassword;
	
//	@FindBy(xpath="//button[@id='btnLogin']")
//    public WebElement btn_Login;

    //@FindBy(xpath="//button[@id='btnLogin']")
    @FindBy(xpath = "//div[@class='loginRedirect']")
    /*@FindBy(css = "#loginBox input#submitLogin")*/
    public WebElement btn_LoginNew;

    /*@FindBy(xpath="./[@id='btnLogin']")*/
    @FindBy(id = "btnLogin")//"#loginBox input#submitLogin")
    public WebElement btn_Login;

    @FindBy(css="#cancelLink")
    public WebElement cancelPayPalPayment;

//    @FindBy(css="#injectedUnifiedLogin>iframe")
//    public WebElement paypalLoginIFrame;

    @FindBy(css="iframe[name='injectedUl']")
    public WebElement paypalLoginIFrame;

    @FindBy(css = "iframe[title='ppbutton']")
    public WebElement continuePayPalFrame;

//    @FindBy(css="#login button[type=submit]")
//    public WebElement loginButtonInFrame;

    @FindBy(css="button[name='btnLogin']")
    public WebElement loginButtonInFrame;

    @FindBy(xpath = "//h3[.='Your order summary']")
    public WebElement summary;

	@FindBy(css = ".modal-title")
    public WebElement paypalTitle;

    //@FindBy(xpath = ".//button[@class='button-primary'][.='Proceed with PayPal']")
    @FindBy(xpath = ".//div[@data-funding-source ='paypal']")
     public WebElement proceedPaypalBtn;

    @FindBy(css = ".alpha.ng-binding")
    public WebElement paypalText;

    //@FindBy(css = "#defaultCancelLink a")
    @FindBy(xpath = "(.//a[contains(.,\"Cancel\")])[2]")
    public WebElement returnToStore;

    @FindBy(xpath = ".//button[@class=\"button actionContinue scTrack:unifiedlogin-login-click-next\"]")
    public WebElement clickNextButton;

    @FindBy(css = "[aria-label='paypal']")
    public WebElement paypalSbDrawer;

    //@FindBy(xpath = ".//div[contains(.,'paypal-button-env')]")
    @FindBy(xpath = ".//div[contains(@class,'paypal-button-layout-horizontal paypal-button-shape-rect')][img]")
    public WebElement paypalCheckoutButton;

    @FindBy(css = ".btn.full.ng-binding")
    public WebElement payPalLoginButton;

    @FindBy(xpath="//a[contains(.,'Cancel and return')]")//section[@id='login']//a[contains(.,'Cancel')]")
    public WebElement cancelAndReturnButtonNew;

    @FindBy(css = ".modal-header.header-modal-paypal .button-modal-close")
    public WebElement closePayPalModal;

    @FindBy(xpath = "(//*[@id='paypal-express'])[2]")
    public WebElement payPalIntCheckoutButton;


    @FindBy(id="continue-btn-left")
    public WebElement continueButton;

    @FindBy(xpath = "//*[@data-test-id='shipToChangeLink']")
    public WebElement chagneShipAddress;

    @FindBy(css = ".leftText.ng-binding")
    public WebElement addNewAddressPayPal;

    @FindBy(id = "country")
    public WebElement country_PayPal;

    @FindBy(id = "shippingFirstName")
    public WebElement firstName_PayPal;

    @FindBy(id = "shippingLastName")
    public WebElement lastName_PayPal;

    @FindBy(id = "shippingLine1")
    public WebElement streetLine_PayPal;

    @FindBy(id = "shippingCity")
    public WebElement city_PayPal;

    @FindBy(id = "shippingPostalCode")
    public WebElement postalCode_PayPal;

    @FindBy(id = "proceedButton")
    public WebElement saveButton_PayPal;




}
