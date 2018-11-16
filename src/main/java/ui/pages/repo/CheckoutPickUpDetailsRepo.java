package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Venkat on 4/11/2017.
 */
public class CheckoutPickUpDetailsRepo extends OrderLedgerRepo {
    @FindBy(css=".label-checkbox.input-checkbox.alternate-pickup label div")
    public WebElement altPickUpCheckbox;

    @FindBy(css=".address-container")
    public WebElement pickupAddress;

    @FindBy(css=".address-container.contact-pickup")
    public WebElement AlternativeAddress;

    @FindBy(css=".checkout-summary-edit.summary-title-pick-up-contact")
    public WebElement editLink;

    @FindBy(css=".button-save-address")
    public WebElement saveBtn;

    @FindBy(css=".button-cancel-address")
    public WebElement cancelBtn;

    @FindBy(name="firstName")
    public WebElement pickUpEditFn;

    @FindBy(name="lastName")
    public WebElement pickUpEditLn;

    @FindBy(name="phoneNumber")
    public WebElement pickUpEditmobile;

    @FindBy(name="pickUpAlternate.firstName")
    public WebElement altFn;

    @FindBy(name="pickUpAlternate.lastName")
    public WebElement altLn;

    @FindBy(name="pickUpAlternate.emailAddress")
    public WebElement altEmail;



    @FindBy(css=".input-common.first-name input")
    public WebElement pickUpFNameTxtFld;

    @FindBy(css=".input-common.first-name input ~ .inline-error-message")
    public WebElement pickUpFNameErr;

    @FindBy(css=".input-common.last-name input")
    public WebElement pickUpLNameTxtFld;

    @FindBy(css=".input-common.last-name input ~ .inline-error-message")
    public WebElement pickUpLNameErr;

    @FindBy(css=".input-common.email-address input")
    public WebElement pickUpEmailTxtFld;

    @FindBy(css=".input-common.email-address input ~ .inline-error-message")
    public WebElement pickUpEmailErr;

    @FindBy(css=".input-common.phone-number input")
    public WebElement pickUpPhoneTxtFld;

    @FindBy(css=".input-common.phone-number input ~ .inline-error-message")
    public WebElement pickUpPhoneErr;

    @FindBy(css=".container-button .button-primary.button-next-step")
    public WebElement nxtButton;

    @FindBy(css="button.button-login")
    public WebElement eSpotLoginLink;

    @FindBy(css="button.button-register")
    public WebElement eSpotCreateAccountLink;

    @FindBy(css=".button-modal-close")
    public WebElement closeOverlayBtn;


}