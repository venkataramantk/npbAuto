package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;
import java.util.List;

/**
 * Created by JKotha on 11/14/2017.
 */
public class MPayPalPageRepo extends UiBaseMobile {

    @FindBy(css = "#loginSection a")
    public WebElement loginButton;

    @FindBy(css = "#login #cancelLink")
    public WebElement cancelLinkInLoginPage;

    @FindBy(css = ".textInput input#email")
    public WebElement emailAddress;

    @FindBy(id = "btnNext")
    public WebElement nextBtn;

    @FindBy(xpath = "(//*[@class='cancelUrl']/a)[2]")
    public WebElement cancelLink;

    @FindBy(css = ".textInput input#password")
    public WebElement passwordFld;

    @FindBy(id = "btnLogin")
    public WebElement accountLogin;

    @FindBy(id = "confirmButtonTop")
    public WebElement continueBtn;

    @FindBy(xpath = "//h3[.='Your order summary']")
    public WebElement summary;

    @FindBy(css = ".button-primary.button-next-step.button-proceed-with-paypal")
    public WebElement proceedWithPaPalButton;

    @FindBy(css = ".button-modal-close")
    public WebElement paypalClose;

    @FindBy(css = ".xcomponent-component-frame.xcomponent-visible")
    public WebElement paypalFrame;

    @FindBy(css = ".paypal-button-logo.paypal-button-logo-pp.paypal-button-logo-blue")
    public WebElement paypalCheckoutOnPaypalModal;

    @FindBy(css = "#shippingAddress .edits.changeShipping")
    public WebElement paypalChangeAddrBtn;

    @FindBy(css = "ul[select-box='checkoutShippingAddresses.addresses'] li a")
    public List<WebElement> paypalAddrList;

    @FindBy(css = "a.textLink")
    public WebElement notYouLink;
}
