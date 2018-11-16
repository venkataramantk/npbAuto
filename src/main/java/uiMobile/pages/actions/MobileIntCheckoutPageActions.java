package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileIntCheckoutRepo;


/**
 * Created by Venkat on 11/15/2016.
 */
public class MobileIntCheckoutPageActions extends MobileIntCheckoutRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileIntCheckoutPageActions.class);

    public MobileIntCheckoutPageActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean isShippingPageDisplayed(String shippingPage){
        return getText(pageTitle).equalsIgnoreCase(shippingPage);
    }


    public boolean enterShippingDetailsAndShipMethod(String fName, String lName, String addrLine1, String addrLine2,String addrLine3, String city,
                                                     String region, String zip, String phNum,String email){
        clearAndFillText(txtFirstName(),fName);
        clearAndFillText(txtLastName(),lName);
        clearAndFillText(txtAddressLine1(),addrLine1);
        clearAndFillText(txtAddressLine2(),addrLine2);
        clearAndFillText(txtAddressLine3(),addrLine3);
        clearAndFillText(txtCity(),city);
        clearAndFillText(txtRegion(),region);
        clearAndFillText(txtZip(),zip);
        clearAndFillText(txtPhone(), phNum);
        clearAndFillText(txtEmail(), email);
        staticWait();
        click(INT_ShippingMethod);

        return waitUntilElementDisplayed(Chk_SameAsShippingAddress,5);
    }

    public boolean shippingSectionVerification(String pageTitleText){
        boolean pageTitleDisplaying = isShippingPageDisplayed(pageTitleText);

        boolean fNameFldDisplaying = waitUntilElementDisplayed(txtFirstName());
        boolean lNameFldDisplaying = waitUntilElementDisplayed(txtLastName());
        boolean addr1FldDisplaying = waitUntilElementDisplayed(txtAddressLine1());
        boolean addr2FldDisplaying = waitUntilElementDisplayed(txtAddressLine2());
        boolean addr3FldDisplaying = waitUntilElementDisplayed(txtAddressLine3());
        boolean cityFldDisplaying = waitUntilElementDisplayed(txtCity());
        boolean regionFldDisplaying = waitUntilElementDisplayed(txtRegion());
        boolean zipFldDisplaying = waitUntilElementDisplayed(txtZip());
        boolean phoneFldDisplaying = waitUntilElementDisplayed(txtPhone());
        boolean emailFldDisplaying = waitUntilElementDisplayed(txtEmail());
        boolean shipMethodFldDisplaying = waitUntilElementDisplayed(INT_ShippingMethod);

        if (pageTitleDisplaying &&fNameFldDisplaying &&lNameFldDisplaying
                &&addr1FldDisplaying &&addr2FldDisplaying &&addr3FldDisplaying
                &&cityFldDisplaying  &&regionFldDisplaying &&zipFldDisplaying
                &&phoneFldDisplaying &&emailFldDisplaying &&shipMethodFldDisplaying){
            return true;
        }

        else
            return false;
    }

//    public boolean shippingPageErrorVerification(){
//        if (!(verifyElementNotDisplayed(err_FirstName) && verifyElementNotDisplayed(err_LastName) && verifyElementNotDisplayed(err_AddressLine1) &&   verifyElementNotDisplayed(err_City) &&   verifyElementNotDisplayed(err_State) &&  verifyElementNotDisplayed(err_POZipCode) &&  verifyElementNotDisplayed(err_PhoneNumber))){
//            return true;
//        }
//
//        else
//            return false;
//    }
//
//    public boolean shippingPageErrorVerificationAfterEnteringValues(){
//        if ((verifyElementNotDisplayed(err_FirstName) && verifyElementNotDisplayed(err_LastName) && verifyElementNotDisplayed(err_AddressLine1) &&   verifyElementNotDisplayed(err_City) &&   verifyElementNotDisplayed(err_State) &&  verifyElementNotDisplayed(err_POZipCode) &&  verifyElementNotDisplayed(err_PhoneNumber))){
//            return true;
//        }
//
//        else
//            return false;
//    }

    public WebElement txtFirstName(){
        return txt_FirstName;
    }

    public WebElement txtLastName(){
        return txt_LastName;
    }

    public WebElement txtAddressLine1(){
        return txt_AddressLine1;
    }

    public WebElement txtAddressLine2(){
        return txt_AddressLine2;
    }

    public WebElement txtAddressLine3(){
        return txt_AddressLine3;
    }

    public WebElement txtCity(){
        return txt_City;
    }

    public WebElement txtRegion(){
        return txt_Region;
    }

    public WebElement txtZip(){
        return txt_PostalCode;
    }

    public WebElement txtPhone(){
        return txt_Phone;
    }

    public WebElement txtEmail(){
        return txt_Email;
    }


    public double txtSubtotalPriceAlone(){
        return Double.parseDouble(mobileDriver.findElement(By.xpath("//*[@id='WC_SingleShipmentOrderTotalsSummary_td_2']")).getText().replace("$", ""));
    }

    public WebElement checkBoxSameAsShipping(){
        return Chk_SameAsShippingAddress;
    }

    public WebElement btnRadCC(){
        return INT_PayWithCC;
    }

    public WebElement btnRadPayPal(){
        return INT_PayWithPayPal;
    }

    public WebElement txtCC(){
        return txt_CreditCardNumber;
    }

    public WebElement listCCMonth(){
        return list_CCMonth;
    }

    public WebElement listCCYear(){
        return list_CCYear;
    }

    public WebElement txtCCCode(){
        return txt_CCCode;
    }

    public boolean paymentSectionVerification(){
        boolean sameAsCheckBoxDisplaying = waitUntilElementDisplayed(checkBoxSameAsShipping());
        boolean creditCardRadioBtnDisplaying = waitUntilElementDisplayed(btnRadCC());
        boolean payPalRadioBtnDisplaying = waitUntilElementDisplayed(btnRadPayPal());

        switchToFrame(creditCardIFrame);
        boolean creditCardTxtDisplaying = waitUntilElementDisplayed(txtCC());
        boolean monthListCCDisplaying = waitUntilElementDisplayed(listCCMonth());
        boolean yearListCCDisplaying = waitUntilElementDisplayed(listCCYear());
        boolean codeCCTxtDisplaying = waitUntilElementDisplayed(txtCCCode());

        if (sameAsCheckBoxDisplaying &&creditCardRadioBtnDisplaying
                &&payPalRadioBtnDisplaying &&creditCardTxtDisplaying
                &&monthListCCDisplaying &&yearListCCDisplaying
                &&codeCCTxtDisplaying)
            return true;

        else
            return false;
    }

    /**
     * Created by Richa Priya
     * Fill shipping details for INT order
     *
     * @param fName
     * @param lName
     * @param addrLine1
     * @param city
     * @param region
     * @param city
     * @param zip
     * @param phNum
     * @param email
     * @param shipMethod
     */
    public boolean enterShippingDetailsAndShipMethod(String email,String fName, String lName, String addrLine1, String city,
                                                                   String region, String zip, String phNum,String shipMethod){
        clearAndFillText(txt_Email,email);
        staticWait();
        clearAndFillText(txt_FirstName,fName);
        staticWait();
        clearAndFillText(txt_LastName,lName);
        clearAndFillText(txt_AddressLine1,addrLine1);;
        clearAndFillText(txt_PostalCode,zip);
        clearAndFillText(txt_City,city);
        clearAndFillText(txt_Region,region);
        staticWait();
        clearAndFillText(txt_Phone, phNum); 
        staticWait();
        selectShippingMethodFromRadioButton(shipMethod);
        javaScriptClick(mobileDriver,continueBtn);

        return waitUntilElementDisplayed(paymentText,10);
    }

    /**
     * Created by Richa Priya
     *
     * Select ship method passed from the excel
     *
     * @param shipMethod
     */
    public void selectShippingMethodFromRadioButton(String shipMethod) {
        scrollToBottom();
        if(!isSelected(shippingMethodRadioButtonByValue(shipMethod))) {
            javaScriptClick(mobileDriver, shippingMethodRadioButtonByValue(shipMethod));
        }
        staticWait(2000);
    }

    /**
     * Created by Richa Priya
     *
     * Fill shipping details for INT order
     *
     * @param cardNo
     * @param expMonth
     * @param cvv
     *
     */
    public void enterBillingDetailsForIntCheckout(String cardNo, String expMonth, String cvv){
        switchToFrame(creditCardIFrame);
        String expYear = getFutureYearWithCurrentDate("YY", 5);
        String expDetails = expMonth+"/"+expYear;
        if(!isDisplayed(cardNoField)){
            scrollDownToElement(cardNoField);
        }
        clearAndFillText(cardNoField,cardNo);
        clearAndFillText(expDetailsField,expDetails);
        clearAndFillText(cvvField,cvv);

    }

    /**
     * Created by Richa Priya
     *
     * Click on Place order button on Checkout page
     *
     * Return true if successfully redirected to Order confirmation page
     */
    public  boolean clickPlaceOrderIntCheckout(){
        if(!isDisplayed(placeOrderBtn)){
            scrollDownToElement(placeOrderBtn);
        }
        click(placeOrderBtn);
        switchToDefaultFrame();
        return waitUntilElementDisplayed(thankTextForInt,10);
    }

}
