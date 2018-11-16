package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileCheckoutPickUpDetailsRepo;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileCheckoutPickUpDetailsActions extends MobileCheckoutPickUpDetailsRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileCheckoutPickUpDetailsActions.class);


    public MobileCheckoutPickUpDetailsActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }


    public boolean clickNextShippingButton(MShippingPageActions shippingPageActions){
        scrollDownUntilElementDisplayed(nxtButton);
        click(nxtButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton,10);
    }

    public void enterBOPISDetailsGuest(String fName, String lName, String emailAddress, String phNum){
        waitUntilElementDisplayed(pickUpFNameTxtFld,5);
        clearAndFillText(pickUpFNameTxtFld,fName);
        staticWait();
        clearAndFillText(pickUpLNameTxtFld,lName);
        staticWait();
        clearAndFillText(pickUpEmailTxtFld,emailAddress);
        staticWait();
        clearAndFillText(pickUpPhoneTxtFld, phNum);
        staticWait();
    }

    public boolean clickNextBillingButton(MBillingPageActions mbillingPageActions){
        scrollDownUntilElementDisplayed(nxtButton);
        click(nxtButton);
        return waitUntilElementDisplayed(mbillingPageActions.nextReviewButton);
    }

    public boolean enterBOPISDetailsAndClickNextGuest(MShippingPageActions shippingPageActions, MBillingPageActions billingPageActions, String fName, String lName, String emailAddress, String phNum) {
        enterBOPISDetailsGuest(fName,lName,emailAddress,phNum);
        if(getText(nxtButton).contains("SHIPPING")) {
            return clickNextShippingButton(shippingPageActions);
        }
        else
            return clickNextBillingButton(billingPageActions);
    }

    public boolean clickLoginOnESpot(MLoginPageActions loginPageActions){
        waitUntilElementDisplayed(eSpotLoginLink, 10);
        click(eSpotLoginLink);
        return waitUntilElementDisplayed(loginPageActions.loginButton,20);
    }

    public boolean clickCreateAccountOnESpot(MCreateAccountActions createAccountActions){
        waitUntilElementDisplayed(eSpotCreateAccountLink,10);
        click(eSpotCreateAccountLink);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton,20);
    }

    public boolean loginFromESpot(MLoginPageActions loginPageActions, String email, String password){
        clickLoginOnESpot(loginPageActions);
        loginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        return waitUntilElementDisplayed(nxtButton,10);
    }

    /*public boolean createAccountFromESpot(MCreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone){
        clickCreateAccountOnESpot(createAccountActions);
        createAccountActions.createAnAccount_UserInputEmail(email, fName, lName, password, zip, phone);
        return waitUntilElementDisplayed(nxtButton,10);
    }*/

    public boolean closeLoginCreateAccOverlay(){
        waitUntilElementDisplayed(closeOverlayBtn, 10);
        click(closeOverlayBtn);
        return (!waitUntilElementDisplayed(closeOverlayBtn,5));
    }

    public boolean fillCreateAccDetailsCloseOverlay(MCreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone){
        clickCreateAccountOnESpot(createAccountActions);
       /* clearAndFillText(createAccountActions.emailAddrField, email);
        clearAndFillText(createAccountActions.confEmailAddrFld, email);*/
        createAccountActions.enterAccountDetails(fName, lName, email, password, zip, phone);
        return closeLoginCreateAccOverlay();
    }

}
