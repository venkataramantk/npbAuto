package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 20/10/2017.
 */
public class MCreateAccountPageRepo extends UiBaseMobile {

    @FindBy(css = ".modal-title.modal-only-title")
    public WebElement createAccountTitle;

    @FindBy(css = ".button-modal-close")
    public WebElement close;

    @FindBy(xpath = ".//input[@name='firstName']")
    public WebElement firstNameField;

    @FindBy(xpath = ".//input[@name='lastName']")
    public WebElement lastNameField;

    @FindBy(css = ".form-create-account input[name='emailAddress']")
    public WebElement emailAddrField;

    @FindBy(css = ".form-create-account input[name='confirmEmailAddress']")
    public WebElement confEmailAddrFld;

    @FindBy(css = ".form-create-account input[name='password']")
    public WebElement passwordFld;

    @FindBy(css = ".button-info-wire")
    public WebElement toolTip;

    @FindBy(css = ".tooltip-content")
    public WebElement toolTipContent;

    @FindBy(css = ".form-create-account input[name='confirmPassword']")
    public WebElement confPasswordFld;

    @FindBy(xpath = ".//input[@name='zipCode']")
    public WebElement zipPostalCodeFld;

    @FindBy(xpath = ".//input[@name='phoneNumber']")
    public WebElement phNumberFld;

    @FindBy(css = ".input-checkbox-title")
    public WebElement termsText;

    @FindBy(css = ".label-checkbox.input-checkbox.terms-and-conditions label div")
    public WebElement termsCheckBox;

    @FindBy(css = ".label-checkbox.input-checkbox.terms-and-conditions label div input")
    public WebElement termsCheckBoxInput;

    @FindBy(css = ".form-create-account .button-secondary.button-create-account")
    public WebElement createAccountButton;

    @FindBy(css = ".create-account-banner .logo-container img")
    public WebElement eSpotLogo;

    @FindBy(css = ".message-item-container")
    public WebElement subHeading;

    @FindBy(css = ".label-checkbox.input-checkbox.remember-me")
    public WebElement rememberMe;

    @FindBy(css = ".form-create-account .error-box span:nth-child(2)")
    public WebElement err_CreateAccount;

    @FindBy(css = ".email-title")
    public WebElement emailusPage;

    public WebElement inlineErrorMessage(String field) {
        WebElement inlineError;
        switch (field) {
            case "fn":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_firstName']"));
                break;
            case "ln":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_lastName']"));
                break;
            case "email":
                inlineError = getDriver().findElement(By.xpath("//div[text()='Email Address']/../following-sibling::div[1]/div"));
                break;
            case "confemail":
                inlineError = getDriver().findElement(By.xpath("//div[text()='Confirm Email Address']/../following-sibling::div[1]/div"));
                break;
            case "pwd":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_password']"));
                break;
            case "confirmPwd":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_confirmPassword']"));
                break;
            case "zip":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_zipCode']"));
                break;
            case "phone":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_phoneNumber']"));
                break;
            case "terms":
                inlineError = getDriver().findElement(By.cssSelector("div[id*='error_termsAndConditions']"));
                break;
            default:
                inlineError = getDriver().findElement(By.cssSelector("div.inline-error-message"));
        }
        return inlineError;
    }

    public WebElement showLink(String label) {
        return getDriver().findElement(By.xpath("//div[text()='" + label + "']/../../following-sibling::button"));
    }

    public WebElement hideLink(String label) {
        return getDriver().findElement(By.xpath("//div[text()='" + label + "']/../../following-sibling::button"));
    }

    @FindBy(css = ".contact-us-link")
    public WebElement contactUsLink;

    @FindBy(partialLinkText = "conditions")
    public WebElement termsAndConditionsLink;

    @FindBy(css = ".title-help.title-help-center")
    public WebElement helpCenter;

    @FindBy(css = "div[class='label-checkbox input-checkbox terms-and-conditions']")
    public WebElement termsAndConditions;

    @FindBy(css = "label[class='label-checkbox input-checkbox remember-me']")
    public WebElement rememberMeCheckBox;

    @FindBy(css = ".disclaimer_container>p")
    public List<WebElement> rememberMeForNextTimeLabel;

    @FindBy(css = ".my-account-options>a")
    public WebElement viewMyAccount;

    @FindBy(css = ".remembered-logout .button-logout")
    public WebElement logOutLnk;

    @FindBy(css = ".button-click-reset-password")
    public WebElement resetPasswordLnk;
}
