package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

public class MRTPSAndWicRepo extends UiBaseMobile {

    @FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.overlay-container")
    public WebElement plccForm;

    @FindBy(css = ".button-apply.button-primary")
    public WebElement applyOrAcceptOffer;

    @FindBy(css = ".img-promo-plcc")
    public WebElement mprEspot;

    @FindBy(css = ".text-congratulations")
    public WebElement congrats;

    @FindBy(css = ".img-save-discount")
    public WebElement img_discount;

    @FindBy(css = ".title-promo")
    public WebElement discountPromo;

    @FindBy(css = ".button-primary.button-apply")
    public WebElement yesiaminterstedBtn;

    @FindBy(css = ".button-thanks")
    public WebElement noThanksBtn;

    @FindBy(css = ".text-info")
    public WebElement factatext;

    @FindBy(css = ".text-terms")
    public WebElement termsText;

    @FindBy(css = ".card-existing-content")
    public WebElement accountExists;

    @FindBy(css=".offer-code")
    public WebElement couponCode;

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

    @FindBy(css = "[type='password']")
    public WebElement password;

    @FindBy(css = ".text-approved button:nth-of-type(2)")
    public WebElement loginLink;

    @FindBy(css = ".cta a:nth-child(2)")
    public WebElement loginBtn;

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

    @FindBy(css=".link")
    public WebElement clickhere;
}
