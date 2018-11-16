package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;
import uiMobile.UiBaseMobile;

/**
 * Created by user on 8/16/2016.
 */
public class MobileIntCheckoutRepo extends UiBaseMobile {

    @FindBy(css="#top")
    public WebElement pageTitle;

    @FindBy(xpath="//*[@id='envoyApp']//*[@id='shippingAddress']")
    public WebElement shipAddrSection;

    @FindBy(xpath = "//*[@id='shipping-first-name']")
    public WebElement txt_FirstName;

    @FindBy(xpath = "//*[@id='shipping-last-name']")
    public WebElement txt_LastName;

    @FindBy(xpath = "//*[@id='shipping-address-line1']")
    public WebElement txt_AddressLine1;

    @FindBy(xpath = "//section[@id='shippingAddress'] //*[@id='shipping-address-line2']")
    public WebElement txt_AddressLine2;

    @FindBy(xpath = "//*[@id='shipping-address-line3']")
    public WebElement txt_AddressLine3;

    @FindBy(xpath = "//*[@id='shipping-city']")
    public WebElement txt_City;

    @FindBy(xpath = "//*[@id='shipping-region']")
    public WebElement txt_Region;

    @FindBy(xpath = "//*[@id='shipping-postal-code']")
    public WebElement txt_PostalCode;

    @FindBy(xpath = "//*[@id='shipping-country']")
    public WebElement list_Country;

    @FindBy(xpath = "//*[@id='shipping-tel']")
    public WebElement txt_Phone;

//    @FindBy(xpath = "//section[@id='shippingAddress'] //*[@id='shipping-tel-alternate']")
//    public WebElement txt_AlternativePhone;

    @FindBy(xpath = "//*[@id='shipping-email']")
    public WebElement txt_Email;

    @FindBy(xpath = "//*[@id='input-EXPRESS_DDP-prepaid']")
    public WebElement INT_ShippingMethod;

    @FindBy(xpath = "//div[@id='billing'] //*[@id='same-billing-address']")
    public WebElement Chk_SameAsShippingAddress;

    @FindBy(xpath = "//div[@id='billing'] //*[@id='payment-method-cc']")
    public WebElement INT_PayWithCC;

    @FindBy(xpath = "//div[@id='billing'] //*[@id='payment-method-PAYPAL_PAYMENT']")
    public WebElement INT_PayWithPayPal;

//    @FindBy(xpath = "//div[@id='billing'] //*[@id='payment-method-UPOP']")
//    public WebElement INT_PayWithUPOP;

    @FindBy(xpath = "//*[@id='cc-num']")
    public WebElement txt_CreditCardNumber;

    @FindBy(xpath = "//select[@id='cc-exp-month']")
    public WebElement list_CCMonth;

    @FindBy(xpath = "//select[@id='cc-exp-year']")
    public WebElement list_CCYear;

    @FindBy(xpath = "//input[@id='cc-sec-num']")
    public WebElement txt_CCCode;

    @FindBy(xpath = "//h2[@class='step-title ng-binding'][contains(.,'Delivery')]")
    public WebElement deliveryText;

    @FindBy(xpath = "//h2[@class='step-title ng-binding'] [contains(.,'Payment')]")
    public WebElement paymentText;

    @FindBy(css="#envoyId")
    public WebElement internationalCheckoutIFrame;

    @FindBy(css="#cc-frame")
    public WebElement creditCardIFrame;

    @FindBy(css="#continue-btn-left")
    public WebElement continueBtn;

    @FindBy(css=".field-input.ng-valid-card-type.ng-valid-card-number")
    public WebElement cardNoField;

  /*  @FindBy(xpath="//*[@name='cc-number']")
    public WebElement cardNoField;*/

    @FindBy(css=".field-input.ng-valid-expiration-date")
    public WebElement expDetailsField;

    @FindBy(css=".field-input.ng-valid-security-code")
    public WebElement cvvField;

    @FindBy(css="#submit-order-btn-left")
    public WebElement placeOrderBtn;

    @FindBy(xpath = "//h3[contains(.,'Thank you for your order,')]")
    public WebElement thankTextForInt;


    public WebElement shippingMethodRadioButtonByValue(String value) {
        return getDriver().findElement(By.xpath("//td/div[contains(.,'Standard')]"));////td[contains(.,'" + value + "')]/preceding-sibling::td//input[@name='shipping-method'])"));
    }


}
