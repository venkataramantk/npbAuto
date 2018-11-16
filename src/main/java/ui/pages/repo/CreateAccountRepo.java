package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class CreateAccountRepo extends HeaderMenuRepo {
    /*@FindBy(css="input[name=firstName]")*/
//    @FindBy(name = "firstName")
    @FindBy(xpath = ".//input[@name='firstName']")
    public WebElement firstNameField;

    /*@FindBy(css="input[name=lastName]")*/
//    @FindBy(name="lastName")
    @FindBy(xpath = ".//input[@name='lastName']")
    public WebElement lastNameField;

    /*@FindBy(css="input[name=logonId]")*/
    /*@FindBy(name="emailAddress")*/
//    @FindBy(xpath = "//input[@type='email'][@name='emailAddress']")
    @FindBy(css = ".form-create-account input[name='emailAddress']")
    public WebElement emailAddrField;

    /*@FindBy(css="input[name=email1Verify]")*/
//    @FindBy(name="confirmEmailAddress")
    @FindBy(css = ".form-create-account input[name='confirmEmailAddress']")
    public WebElement confEmailAddrFld;

    /*@FindBy(css="input[name=logonPassword]")*/
//    @FindBy(name="password")
    @FindBy(css = ".form-create-account input[name='password']")
    public WebElement passwordFld;

    @FindBy(css = ".button-info-wire")
    public WebElement toolTip;

    @FindBy(css = ".button-info")
    public WebElement toolTipToClose;

    @FindBy(css = ".tooltip-content")
    public WebElement toolTipContent;

    @FindBy(css = ".error-text")
    public WebElement pageLevelError;

    @FindBy(css = ".password-input.input-with-button .button-show-password")
    public WebElement passwordShowHideLnk;

    /*@FindBy(css="input[name=logonPasswordVerify]")*/
//    @FindBy(name="confirmPassword")
    @FindBy(css = ".form-create-account input[name='confirmPassword']")
    public WebElement confPasswordFld;

    @FindBy(css = ".confirm-password-input.input-with-button .button-show-password")
    public WebElement confPasswordShowHideLnk;

    /*@FindBy(css="input[name=addressField3]")*/
//    @FindBy(name = "zipCode")
    @FindBy(xpath = ".//input[@name='zipCode']")
    public WebElement zipPostalCodeFld;

    /*@FindBy(css="input[name=phone1]")*/
//    @FindBy(name="phoneNumber")
    @FindBy(xpath = ".//input[@name='phoneNumber']")
    public WebElement phNumberFld;

    /*@FindBy(xpath="//div[@class='genericESpot'][contains(.,'[Email_Disclaimer_CreateAccount]')]//div[@class='disclaimer_container']")*/
    /*@FindBy(css="*//*[name='termsAndConditions']+.input-title")*/
    /*@FindBy(css=".terms-and-conditions .input-title")*/
    @FindBy(css = ".input-checkbox-title")
    public WebElement termsText;

    //    @FindBy(css = ".terms-and-conditions a")
    //   @FindBy(xpath = ".//label[@class='label-checkbox input-checkbox terms-and-conditions']//a")
    @FindBy(xpath = ".//div[@class='label-checkbox input-checkbox terms-and-conditions']//a[text()=\"terms & conditions\"]")
    public WebElement termsCondLnk;

    /*@FindBy(css="#termsCheck")*/
    //@FindBy(css = "input[name='termsAndConditions']") - old one
    @FindBy(css = ".terms-and-conditions .input-checkbox-icon-unchecked")
    public WebElement termsCheckBoxUnselected;

    @FindBy(css = ".terms-and-conditions .input-checkbox-icon-checked")
    public WebElement termsCheckox;

    @FindBy(css=".terms-and-conditions div:nth-child(1)")
    public WebElement termsCheckboxInput;

    @FindBy(xpath = "//input[starts-with(@id,'termsAndConditions')]")
    public WebElement termsCheckBoxSelected;

//    @FindBy(xpath="//a[contains(@id,'Link_Submit_1')]")
//    public WebElement createAccountButton;

    /*@FindBy(css="#WC_TCPUserRegistrationAddForm_Link_Submit_1")*/
    //@FindBy(css = ".form-create-account .button-secondary.button-create-account")
    @FindBy(css = ".button-secondary.button-create-account,disabled")
    public WebElement createAccountButton;

    @FindBy(css = "#register-tab")
    public WebElement createAcctTab;

//    @FindBy(css=".left-col-loyalty.add-border-right>img")//
//    public WebElement congratulationsText;

    @FindBy(css = "span.welcome-headline")
    public WebElement welcomeHeadlineUS;

    @FindBy(css = ".canada.no-border>h1")
    public WebElement thanksHeadLineCA;

    @FindBy(css = ".myplace-headline")//
    public WebElement myPlaceRewardsHeadLine;

    @FindBy(css = "h1")//
    public WebElement thanksText;

//    @FindBy(xpath="//a[text()='Edit Account']")
//    public WebElement ediAcctButton;

//    @FindBy(xpath="//a[text()='Start Shopping'] ")
//    public WebElement startShoppingButton;

    @FindBy(css = ".button-dark-grey")
    public WebElement viewAcctButtonUS;

    @FindBy(xpath = "//a[@class='button-blue'][contains(.,'Edit Account')]")
    public WebElement editAcctButtonCA;

    @FindBy(css = ".button-grey.margin-left")
    public WebElement continueShoppingButtonUS;

    @FindBy(css = ".button-blue.margin-left")
    public WebElement startShoppingButtonCA;

    /*@FindBy(css="#UserRegistrationErrorMessage")*/
    /*@FindBy(css = ".form-create-account .error-box")*/
    @FindBy(css = ".form-create-account .error-box>span")
    public WebElement err_CreateAccount;

    @FindBy(xpath = "//*[contains(@id, 'WC_ContentAreaESpot_div')]//img[contains(@title, 'Congratulations!')]")
    public WebElement CongratsEspot;

    @FindBy(xpath = "//*[@class='mpr-benefits']/li")
    public List<WebElement> espotPoints;

    @FindBy(xpath = "//*[@class='left-col-loyalty add-border-right']//p")
    public WebElement Disclaimer;

    @FindBy(xpath = "//*[@class='canada no-border']/h1")
    public WebElement ThanksEspot;

    @FindBy(xpath = "//*[@class='canada no-border']/p")
    public WebElement Espot;

    @FindBy(xpath = "//*[@class='mpr-logo-US']")
    public WebElement img_MyPlaceRewardPoint;

    public WebElement errorMessageByFieldError(String fldErrMsg) {
        return getDriver().findElement(By.xpath("//label[contains(.,'" + fldErrMsg + "')]"));
    }

    public WebElement emailAddressErrMsg() {
        return getDriver().findElement(By.xpath(".//*[@id='Register']/div/div[2]/div[1]/label[2]"));
    }

    public WebElement confirmEmailAddressErrorMsg() {
        return getDriver().findElement(By.xpath(".//*[@id='Register']/div/div[2]/div[2]/label[2]"));

    }

    @FindBy(css = "#termsCheckError")
    public WebElement termCheckBoxError;

//    //Please refer "errorMessageByFieldError"
//    @FindBy(xpath = "//label[contains(.,'Please enter first name.')]")
//    public WebElement firstName_ErrorMessage;
//
//    @FindBy(xpath = "//label[contains(.,'Please enter last name.')]")
//    public WebElement lastName_ErrorMessage;
//
//    @FindBy(xpath = "//div[@class='fieldset-twoCol'][contains(.,'Email Address')][not(contains(.,'Confirm'))]//label[contains(.,'Email format is invalid.')]")
//    public WebElement emailAddress_ErrorMessage;
//
//    @FindBy(xpath = "//div[@class='fieldset-twoCol'][contains(.,'Confirm Email Address')]//label[contains(.,'Email format is invalid.')]")
//    public WebElement confirmEmailAddress_ErrorMessage;
//
//    //Please refer "errorMessageByFieldError"
//    @FindBy(xpath = "//label[contains(.,'Please enter a valid password.')]")
//    public WebElement password_ErrorMessage;
//
//    @FindBy(xpath = "//label[contains(.,'Passwords must match.')]")
//    public WebElement ReEnterPassword_ErrorMessage;
//
//    @FindBy(xpath = "//label[contains(.,'Please enter a valid zip/postal code.')]")
//    public WebElement zipCode_ErrorMessage;
//
//    @FindBy(xpath = "//label[contains(.,'Zip code can only contain five digits.')]\"")
//    public WebElement ZipCode_ErorMessage_US;
//
//    @FindBy(xpath = "//label[contains(.,'Please enter a valid phone number.')]")
//    public WebElement PhoneNumber_ErrorMessage;
//
////    Please refer "termCheckBoxError"
//    @FindBy(xpath = "//label[contains(.,'You must agree to the Terms and Conditions of the myPlace Rewards program to create an account.')]")
//    public WebElement IAgree_ErrorMessage;

    @FindBy(xpath = "//*[@id='UserRegistrationErrorMessage'][contains(.,'The email address you provided matches an existing account. Did you forget your password? To reset it,')]")
    public WebElement Header_Error;

    //New ones from Venkat
    @FindBy(xpath = "//label[contains(.,'Zip code can only contain five digits.')]")
    public WebElement zipCode_MoreThanFive_ErrorMessage_US;

    @FindBy(xpath = "//label[contains(.,'Zip code must contain five digits.')]")
    public WebElement zipCode_LessThanFive_ErrorMessage;

    @FindBy(xpath = "//label[contains(.,'Postal Code can not exceed 7 characters in length.')]")
    public WebElement zipCode_MoreThanSeven_ErrorMessage;

    @FindBy(xpath = "//label[contains(.,'Postal code must be at least 6 characters in length (ex. M5B 2H1).')]")
    public WebElement zipCode_LessThansix_ErrorMessage;

    /*@FindBy(css=".signup-optin>p>input[name='rememberCheck']")*/
    @FindBy(css = "input[name='rememberMe']")
    public WebElement rememberMeCheckBox;

    @FindBy(css = ".disclaimer_container>p")
    public List<WebElement> rememberMeForNextTimeLabel;

    @FindBy(xpath = ".//*[@id='Register']/div/div/div/label[2]")
    public List<WebElement> accountErrorMsgs;

    @FindBy(css = ".create-account-heading")
    public WebElement pointsOfferCreateAccount;

    //DT2 Page objects

    /*@FindBy(css=".message>h3")*/
    @FindBy(css = ".message>p:nth-child(1)")
    public List<WebElement> txtContentLbl1;

    /*@FindBy(css=".message>p")*/
    //@FindBy(css = ".message>p:nth-child(2)")
    @FindBy(css = ".message.message-container>p:nth-child(2)")
    public List<WebElement> txtContentLbl2;

    /*@FindBy(css="[name='rememberMe']+.input-title")*/
    /*@FindBy(css = ".remember-me .input-title")*/
    @FindBy(css = ".remember-me .input-checkbox-title")
    public WebElement rememberMeTxtLbl;

    /*@FindBy(css="[name='rememberMe']~.input-subtitle")*/
    @FindBy(css = ".remember-me .input-subtitle")
    public WebElement rememberMeSubTxtLbl;

    /*@FindBy(css="[name='firstName'] + .inline-error-message")*/
    @FindBy(xpath = "//input[@name='firstName']/../following-sibling::div/div")
    public WebElement firstNameInLineErr;

    /*@FindBy(css="[name='lastName'] + .inline-error-message")*/
    @FindBy(xpath = "//input[@name='lastName']/../following-sibling::div/div")
    public WebElement lastNameInLineErr;

    /*@FindBy(css="[name='emailAddress'] + .inline-error-message")*/
    @FindBy(xpath = "//input[@name='emailAddress']/../following-sibling::div/div")
    public WebElement emailAddrInLineErr;

    /*@FindBy(css="[name='confirmEmailAddress'] + .inline-error-message")*/
    @FindBy(xpath = "//input[@name='confirmEmailAddress']/../following-sibling::div/div")
    public WebElement confEmailAddrInLineErr;

    /*@FindBy(css = "[name='password'] ~ .inline-error-message")*/
    @FindBy(xpath = "//input[@name='password']/../following-sibling::div/div")
    public WebElement passwordInLineErr;

    /*@FindBy(css = "[name='confirmPassword'] ~ .inline-error-message")*/
    @FindBy(xpath = "//input[@name='confirmPassword']/../following-sibling::div/div")
    public WebElement confPasswordInLineErr;

    /*@FindBy(css = "[name='zipCode'] ~ .inline-error-message")*/
    @FindBy(xpath = "//input[@name='zipCode']/../following-sibling::div/div")
    public WebElement zipPostalCodeInLineErr;

    /*@FindBy(css = "[name='phoneNumber'] ~ .inline-error-message")*/
    @FindBy(xpath = "//input[@name='phoneNumber']/../following-sibling::div/div")
    public WebElement phoneNumInLineErr;

    /*@FindBy(css = "[name='termsAndConditions'] ~ .inline-error-message")*/
    /*@FindBy(css = ".terms-and-conditions.input-checkbox>.inline-error-message")*/
    @FindBy(css = ".terms-and-conditions.input-checkbox>div>.inline-error-message")
    public WebElement termsCondInLineErr;

    @FindBy(css = ".form-create-account .success-box")
    public WebElement successMessage;

    @FindBy(css = ".my-account-options>a")
    public WebElement viewMyAccount;

    @FindBy(css = ".remembered-logout .button-logout")
    public WebElement logOutLnk;

    @FindBy(css = ".button-primary")
    public WebElement rtnToLoginBtn;

    @FindBy(css = ".back-to-login-button")
    public WebElement backToLoginLnk;

    @FindBy(xpath = ".//button[@class='button-overlay-close']")
    public WebElement closeOverlay;

}
