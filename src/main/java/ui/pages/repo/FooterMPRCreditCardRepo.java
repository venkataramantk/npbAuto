package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

public class FooterMPRCreditCardRepo extends UiBase {

    @FindBy(css = ".button-apply.button-quaternary")
//    @FindBy(css = "img +button.button-apply")
    public WebElement applyButton;

    @FindBy(css = ".card-existing-content")
    public WebElement accountExists;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModel;

    @FindBy(css = "p.rewards-terms")
    public WebElement rewardterms;

    @FindBy(css=".list-apply li")
    public WebElement legalpoints;

    @FindBy(css = ".button-here")
    public WebElement clickHere;

    @FindBy(css = "input[name='prescreenCode']")
    public WebElement prescreenField;

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

    @FindBy(xpath = ".//input[@name='ssn']")
    public WebElement socialSecurityNumber;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div")
    public WebElement termsCheckBox;

    @FindBy(css = ".approved-plcc-container")
    public WebElement approvedPlccContainer;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div input")
    public WebElement termsCheckBoxinput;

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

    //    @FindBy(xpath = ".//a[@class='link-footer'][.='reward terms']")
    @FindBy(xpath = ".//div[@class='container-links']/a[text()='Reward terms']")
    public WebElement rewardTermsLink;

    @FindBy(css = ".button-offer")
    public WebElement copyCliboardLink;

    @FindBy(css = ".button-primary.button-continue-shopping")
    public WebElement continueShoppingButton;

    //   @FindBy(xpath = "a[href*='/home/register']")
//    @FindBy(xpath = "(.//div[@class='cta']//a[text()='Create Account'])[1]")
    @FindBy(xpath = ".//div[@class=\"approved-plcc-container\"]//button[text()=\"Create an account\"]")
    public WebElement createAccLink;

    @FindBy(xpath = ".//p[@class='text-approved']//button[.='log in']")
//    @FindBy(xpath = "(.//div[@class='cta']//a[text()='Log In'])[1]")
    public WebElement loginLink;

    @FindBy(css = ".offer-code")
    public WebElement offerCode;

    @FindBy(css = ".details")
    public WebElement detailsLink;

    @FindBy(css = ".plcc-terms-notice-container img")
    public WebElement plccPromoImg;

    @FindBy(css = ".plcc-terms-notice-content")
    public WebElement plccDisclosureStatementContent;

    @FindBy(css = "#plccForm .message.message-review")
    public WebElement plccAgreementTxtLbl;

    @FindBy(css = ".title.title-contact")
    public WebElement contactInfoLbl;

    @FindBy(css = ".inline-error-message")
    public WebElement prescreencode;

    //    @FindBy(css = "input[name='address.firstName']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a first name']")
    public WebElement fnameErr;

    //    @FindBy(css = "input[name='address.lastName']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a last name']")
    public WebElement lnameErr;

    //    @FindBy(css = "input[name='address.addressLine1']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid street address']")
    public WebElement address1Err;

    //    @FindBy(css = "input[name='address.city']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid city']")
    public WebElement cityErr;

    //    @FindBy(css = "[name='address.state']~.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a state']")
    public WebElement stateError;

    //    @FindBy(css = "input[name='address.zipCode']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your zip code']")
    public WebElement zipErr;

    //    @FindBy(css = "input[name='phoneNumber']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your phone number']")
    public WebElement phoneErr;

    //    @FindBy(css = "input[name='emailAddress']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your email address']")
    public WebElement emailError;

    //    @FindBy(css = "input[name='altPhoneNumber']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your phone number']")
    public WebElement altPhoneNoErr;

    //    @FindBy(css = "input[name='ssn'] + .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter the last 4 digits of your social security number']")
    public WebElement ssnErr;

    @FindBy(css = ".title-under-review")
    public WebElement uderReviewText;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid date of birth']")
    public WebElement birthMonthErr;

    @FindBy(css = "[name='plccTermsAndConditions']")
    public WebElement tAndCCheckBox;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='You must agree to the Terms and Conditions to submit the form']")
    public WebElement termsErr;

    @FindBy(css = ".viewport-container.footer-global.footer-global-us")
    public WebElement footerSection;

    @FindBy(css = ".list-apply")
    public WebElement legalCopyDisplay;


}

