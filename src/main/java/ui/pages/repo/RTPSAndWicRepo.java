package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

public class RTPSAndWicRepo extends UiBase {

    @FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.overlay-container")
    public WebElement plccForm;

    @FindBy(css = ".button-primary.button-apply")
    public WebElement yesiaminterstedBtn;

    @FindBy(css = ".button-thanks")
    public WebElement noThanksBtn;

    @FindBy(css=".error-box")
    public WebElement errorMessage;

    @FindBy(css = ".button-disagree")
    public WebElement plccNothanks;

    @FindBy(name = "address.firstName")
    public WebElement firstName;

    @FindBy(name = "address.middleNameInitial")
    public WebElement middleName;

    @FindBy(name = "address.lastName")
    public WebElement lastName;

    @FindBy(name = "address.addressLine1")
    public WebElement addressLine1;

    @FindBy(name = "address.addressLine2")
    public WebElement addressLine2;

    @FindBy(name = "address.city")
    public WebElement cityTextBox;

    @FindBy(name = "address.state")
    public WebElement stateDropDown;

    @FindBy(name = "address.zipCode")
    public WebElement zipCode;

    @FindBy(css = "[name='phoneNumber']")
    public WebElement mobilePhoneNumber;

    @FindBy(xpath = "(.//input[@name='emailAddress'])[1]")
    public WebElement emailAddress;

    @FindBy(name = "altPhoneNumber")
    public WebElement alternatePhoneNumber;

    @FindBy(xpath = ".//select[@name='birthMonth']")
    public WebElement birthMonth;

    @FindBy(xpath = ".//select[@name='birthDay']")
    public WebElement birthDay;

    @FindBy(xpath = ".//select[@name='birthYear']")
    public WebElement birthYear;

    @FindBy(name = "ssn")
    public WebElement socialSecurityNumber;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div")
    public WebElement termsCheckBox;

    @FindBy(css = ".financial-tems-container")
    public WebElement financialTermsContainer;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div")
    public WebElement termsCheckBoxinput;

    @FindBy(css = ".button-modal-close")
    public WebElement buttonClose;

    @FindBy(css = ".input-checkbox-title")
    public WebElement termsAndCondition;

    @FindBy(id = "plccSubmitButton")
    public WebElement submitButton;

    @FindBy(css = ".address-verification button[type='submit']")
    public WebElement addressVerificationContinue;

    @FindBy(css = ".approved-plcc-container")
    public WebElement approvedPlccContainer;

    @FindBy(css = ".title-under-review")
    public WebElement processingError;

    @FindBy(css = ".title-approved")
    public WebElement congratsMsg;

    @FindBy(css = ".credit-limit")
    public WebElement cclimtMsg;

    @FindBy(css = ".text-approved")
    public WebElement mailTimeText;

    @FindBy(css = ".button-primary.button-checkout")
    public WebElement checkout;

    @FindBy(css = ".list-apply")
    public WebElement legalCopyDisplay;

    @FindBy(css=".list-apply li")
    public WebElement legalpoints;

}
