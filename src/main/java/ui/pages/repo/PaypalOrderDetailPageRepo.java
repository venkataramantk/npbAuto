package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by user on 6/24/2016.
 */
public class PaypalOrderDetailPageRepo extends HeaderMenuRepo {
    @FindBy(css=".buttons.reviewButton")
    public WebElement confirmButton;

    @FindBy(css="#ryiForm")
    public WebElement payPalConfirmationIFrame;

    /*@FindBy(css="#ryiForm input[type=submit]")*/
    //@FindBy(css="input#continue[value='Continue']")
    @FindBy(xpath = ".//div[@class=\"buttons reviewButton\"]")
    public WebElement payPalContinueButton;

    @FindBy(css="#ryiForm input[type=submit]")
    public WebElement payPalContinueButtonNew;

    @FindBy(xpath = "//h4[contains(.,'Ship to') or @id='shippingHeading']")
    public WebElement lbl_ShipTo;

    @FindBy(xpath = "//h4[contains(.,'Pay with') or @id='pymtMethod']")
    public WebElement lbl_PayWith;

    @FindBy(xpath = ".//div[contains(.,'paypal-button-env')]")
    public WebElement paypalCheckoutButton;

    @FindBy(id="defaultCancelLink")
    public WebElement cancelAndReturnButtonNew;

}
