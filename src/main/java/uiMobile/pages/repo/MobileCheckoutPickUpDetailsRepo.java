package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileCheckoutPickUpDetailsRepo extends MobileOrderSummarySectionRepo {



    @FindBy(css=".input-common.first-name input")
    public WebElement pickUpFNameTxtFld;

    @FindBy(css=".input-common.last-name input")
    public WebElement pickUpLNameTxtFld;

    @FindBy(css=".input-common.email-address input")
    public WebElement pickUpEmailTxtFld;


    @FindBy(css=".input-common.phone-number input")
    public WebElement pickUpPhoneTxtFld;

    @FindBy(css=".container-button .button-primary.button-next-step")
    public WebElement nxtButton;

    @FindBy(css="button.button-login")
    public WebElement eSpotLoginLink;

    @FindBy(css="button.button-register")
    public WebElement eSpotCreateAccountLink;

    @FindBy(css=".button-modal-close")
    public WebElement closeOverlayBtn;


}