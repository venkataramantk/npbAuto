package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.CheckoutPickUpDetailsRepo;

/**
 * Created by skonda on 9/22/2016.
 */
public class CheckoutPickUpDetailsActions extends CheckoutPickUpDetailsRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(CheckoutPickUpDetailsActions.class);


    public CheckoutPickUpDetailsActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }


    public boolean clickNextShippingButton(ShippingPageActions shippingPageActions) {
        scrollDownUntilElementDisplayed(nxtButton);
        click(nxtButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 10);
    }

    public void enterBOPISDetailsGuest(String fName, String lName, String emailAddress, String phNum) {
        waitUntilElementDisplayed(pickUpFNameTxtFld, 5);
        clearAndFillText(pickUpFNameTxtFld, fName);
        clearAndFillText(pickUpLNameTxtFld, lName);
        clearAndFillText(pickUpEmailTxtFld, emailAddress);
        clearAndFillText(pickUpPhoneTxtFld, phNum);
    }


    public boolean clickNextBillingButton(BillingPageActions billingPageActions) {
        scrollDownUntilElementDisplayed(nxtButton);
        click(nxtButton);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton);
    }

    public boolean enterBOPISDetailsAndClickNextGuest(ShippingPageActions shippingPageActions, BillingPageActions billingPageActions, String fName, String lName, String emailAddress, String phNum) {
        enterBOPISDetailsGuest(fName, lName, emailAddress, phNum);
        if (getText(nxtButton).contains("SHIPPING")) {
            return clickNextShippingButton(shippingPageActions);
        } else
            return clickNextBillingButton(billingPageActions);
    }

    public boolean enterAlterantePersonDetails(String fn, String ln, String email) {
        //check
        click(altPickUpCheckbox);
        waitUntilElementDisplayed(altFn);
        clearAndFillText(altFn, fn);
        clearAndFillText(altLn, ln);
        clearAndFillText(altEmail, email);
        return waitUntilElementDisplayed(altFn);
    }

    public boolean validatePickUpInformation_reg(String fname, String lname, String email, String phno) {
        String address = getText(pickupAddress);
        return address.contains(fname) &&
                address.contains(lname) &&
                address.contains(email) &&
                address.contains(phno);
    }

    public boolean validateEditPage() {
        click(editLink);
        return waitUntilElementDisplayed(saveBtn) &&
                waitUntilElementDisplayed(cancelBtn);
    }

    public boolean updatePickUpInfo(String fn, String ln, String phone) {
        clearAndFillText(pickUpEditFn, fn);
        clearAndFillText(pickUpEditLn, ln);
        clearAndFillText(pickUpEditmobile, phone);
        click(saveBtn);
        return waitUntilElementDisplayed(pickupAddress);
    }

    public boolean clickNextBillingButtonExpressCheckout(ReviewPageActions reviewPageActions) {
        scrollDownUntilElementDisplayed(nxtButton);
        click(nxtButton);
        return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 10);
    }

    public boolean clickLoginOnESpot(LoginPageActions loginPageActions) {
        waitUntilElementDisplayed(eSpotLoginLink, 10);
        click(eSpotLoginLink);
        return waitUntilElementDisplayed(loginPageActions.loginButton, 20);
    }

    public boolean clickCreateAccountOnESpot(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(eSpotCreateAccountLink, 10);
        click(eSpotCreateAccountLink);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 20);
    }

    public boolean loginFromESpot(LoginPageActions loginPageActions, String email, String password) {
        clickLoginOnESpot(loginPageActions);
        loginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        return waitUntilElementDisplayed(nxtButton, 10);
    }

    public boolean createAccountFromESpot(CreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone) {
        clickCreateAccountOnESpot(createAccountActions);
        createAccountActions.createAnAccount_UserInputEmail(email, fName, lName, password, zip, phone);
        return waitUntilElementDisplayed(nxtButton, 10);
    }

    public boolean closeLoginCreateAccOverlay() {
        waitUntilElementDisplayed(closeOverlayBtn, 10);
        click(closeOverlayBtn);
        return (!waitUntilElementDisplayed(closeOverlayBtn, 5));
    }

    public boolean fillLoginDetailsCloseOverlay(LoginPageActions loginPageActions, String email, String password) {
        clickLoginOnESpot(loginPageActions);
        clearAndFillText(loginPageActions.emailFldOnLoginForm, email);
        clearAndFillText(loginPageActions.pwdFldOnLoginForm, password);
        return closeLoginCreateAccOverlay();
    }

    public boolean fillCreateAccDetailsCloseOverlay(CreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone) {
        clickCreateAccountOnESpot(createAccountActions);
        clearAndFillText(createAccountActions.emailAddrField, email);
        clearAndFillText(createAccountActions.confEmailAddrFld, email);
        createAccountActions.enterAccountDetails(fName, lName, password, zip, phone);
        return closeLoginCreateAccOverlay();
    }

    public boolean applyCouponCode(String couponCode) {
        waitUntilElementDisplayed(couponCodeFld, 20);
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        boolean couponAppliedAtOrderLedger = waitUntilElementDisplayed(couponCodeApplied, 10);
        return couponAppliedAtOrderLedger && waitUntilElementDisplayed(appliedCouponText, 20);
    }
    public boolean checkTransactionalSMSCheckbox(){
        if(isDisplayed(transactionalSMSCheckboxUnChecked)){
            return true;
        }
        else{
            addStepDescription("SMS checkbox is not displayed or checked by Default");
            return false;
        }
    }
    public boolean checkSMSFieldsDisplay(){
        click(transactionalSMSCheckboxUnChecked);
        if(isDisplayed(smsPrivacyPolicy) && isDisplayed(transactionalNumber)){
            return true;
        }
        else{
            addStepDescription("Some of the fields are missing in the Transactional SMS section");
            return false;
        }
    }
    public void enterNumberInSMSField(String number){
        waitUntilElementDisplayed(transactionalNumber);
        clearAndFillText(transactionalNumber,number);
    }

    public boolean checkTheErrorMessageInSMSField(String text,String error){
        waitUntilElementDisplayed(transactionalNumber);
        clearAndFillText(transactionalNumber,"");
        clearAndFillText(transactionalNumber,text);
        click(nxtButton);
        String errorDisplayed = getText(smsInlineErrorMessage);
        if(errorDisplayed.contains(error)){
            return true;
        }
        else{
            addStepDescription("check the error copy");
            return false;
        }
    }

}
