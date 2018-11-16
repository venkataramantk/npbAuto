package ui.pages.repo;

import ui.UiBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
/**
 * Created by AbdulazeezM on 8/11/2017.
 */
public class MPROverlayRepo extends UiBase {

    @FindBy(css = ".button-primary.button-apply")
    public WebElement applyButton;

    @FindBy(xpath = ".//a[@class='button-manage'][1]")
    public WebElement manageCCBtn;

    @FindBy(css = ".title.title-contact")
    public WebElement contactInfoTitle;

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

    @FindBy(xpath = ".//form[@name='plccForm']//input[@name='emailAddress']")
    public WebElement emailAddress;

    @FindBy(name = "altPhoneNumber")
    public WebElement alternatePhoneNumber;

    @FindBy(xpath = ".//select[@name='birthMonth']")
    public WebElement birthMonth;

    @FindBy(xpath = ".//select[@name='birthDay']")
    public WebElement birthDay;

    @FindBy(xpath = ".//select[@name='birthYear']")
    public WebElement birthYear;

    @FindBy(xpath = ".//input[@name='ssn']")
    public WebElement socialSecurityNumber;

    @FindBy(name = "plccTermsAndConditions")
    public WebElement termsCheckBox;

    @FindBy(css = ".input-checkbox-title")
    public WebElement termsAndCondition;

    @FindBy(id = "plccSubmitButton")
    public WebElement submitButton;

    @FindBy(css = ".button-disagree")
    public WebElement noThanksButton;

    @FindBy(css = ".title-approved")
    public WebElement approvedMessage;

    @FindBy(css = ".address-verification button[type='submit']")
    public WebElement addressVerificationContinue;

    @FindBy(xpath = ".//a[@class='link-footer'][.='FAQ']")
    public WebElement faqLink;

    @FindBy(xpath = ".//a[@class='link-footer'][.='reward terms']")
    public WebElement rewardTermsLink;

    @FindBy(css = ".button-offer")
    public WebElement copyCliboardLink;

    @FindBy(css = ".button-primary.button-continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(xpath = ".//p[@class='text-approved']//button[.='Create an account']")
    public WebElement createAccLink;

    @FindBy(xpath = ".//p[@class='text-approved']//button[.='log in']")
    public WebElement loginLink;

    @FindBy(css = ".offer-code")
    public WebElement offerCode;

    @FindBy(css = ".details")
    public WebElement detailsLink;

    @FindBy(css = ".button-modal-close")
    public List<WebElement> closeMprOverlay;
}
