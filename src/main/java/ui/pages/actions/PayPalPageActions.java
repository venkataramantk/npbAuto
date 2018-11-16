package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.UiBase;
import ui.pages.repo.PayPalPageRepo;

/**
 * Created by user on 6/15/2016.
 */
public class PayPalPageActions extends PayPalPageRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(CreateAccountActions.class);

    public PayPalPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean cancelPayPalMethod(ShoppingBagPageActions shoppingBagPageActions) {
        switchToDefaultFrame();
        waitUntilElementDisplayed(cancelPayPalPayment);
        click(cancelPayPalPayment);
        return waitUntilElementDisplayed(shoppingBagPageActions.proceedToCheckOutButton, 30);
    }

    public boolean switchToContinuePayPalFrameIfAvailable() {
        boolean isIFrameAvailable = waitUntilElementDisplayed(continuePayPalFrame, 30);
        if (isIFrameAvailable) {
            switchToFrame(continuePayPalFrame);
        }
        return isIFrameAvailable;
    }

    public boolean paypalLogin(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, BillingPageActions billingPageActions, ReviewPageActions reviewPageActions, String email, String pwd, String parentWindow) {
        staticWait(5000);
        if (waitUntilElementDisplayed(billingPageActions.continueButtonOnPaypalModal, 10)) {
            click(billingPageActions.continueButtonOnPaypalModal);
            staticWait(2000);
            switchBackToParentWindow(parentWindow);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 30);
        } else if (waitUntilElementDisplayed(emailAddressNew, 5)) {
            clearAndFillText(emailAddressNew, email);
            click(clickNextButton);
            waitUntilElementDisplayed(emailPasswordNew, 3);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(9000);
            scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        } else if (waitUntilElementDisplayed(payPalLoginButton, 5)) {
            click(payPalLoginButton);
            waitUntilElementDisplayed(emailAddressNew, 15);
            clearAndFillText(emailAddressNew, email);
            click(clickNextButton);
            waitUntilElementDisplayed(emailPasswordNew, 5);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(9000);
            waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton, 20);
            // scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        } else if (isDisplayed(emailPasswordNew) || (isDisplayed(btn_Login))) {
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(8000);
            scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        } else if (isDisplayed(emailAddressNew)) {
            switchToDefaultFrame();
            switchToFrame(0);
            waitUntilElementDisplayed(emailAddressNew, 20);
            staticWait(5000);
            clearAndFillText(emailAddressNew, email);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_LoginNew);
            staticWait(10000);
            waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButtonNew, 10);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        }
        else{
            addStepDescription("Unable to continue PayPal checout");
            closeDriver();
            switchBackToParentWindow(parentWindow);
            return false;
        }
    }

    public boolean payPalNewWindow(BillingPageActions billingPageActions) {
        String currentWindow = driver.getWindowHandle();
        switchToWindow(currentWindow);
        waitUntilElementDisplayed(payPalLoginButton, 9);
        driver.manage().window().maximize();
        return (isDisplayed(emailAddressNew)) || isDisplayed(billingPageActions.continuePaypalBtn) || isDisplayed(payPalLoginButton);

    }

    public boolean clickProceedToPaypalBtnDrawer(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions) {
        waitUntilElementDisplayed(paypalSbDrawer, 5);
        click(paypalSbDrawer);
        staticWait(5000);
//        click(paypalCheckoutButton);
//        waitForFrameToLoad(0, 60);
        return waitUntilElementDisplayed(btn_LoginNew, 20) || waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
        //  waitForFrameToLoad(0, 60);
        //   return (isDisplayed(emailAddressNew))|| waitUntilElementDisplayed(billingPageActions.continuePaypalBtn);
    }

    public boolean loginPaypalAcc(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions) {
        switchToDefaultFrame();

//          driver.switchTo().frame(driver.findElement(By.name(".//*[@name='injectedUl']")));
        waitUntilElementDisplayed(emailAddress, 5);
        clearAndFillText(emailAddress, "dk255@rocketmail.com");
        waitUntilElementDisplayed(emailPassword, 5);
        clearAndFillText(emailPassword, "Test123!");
        click(btn_Login);
        return waitUntilElementDisplayed(paypalOrderDetailsPageActions.confirmButton, 5);
    }

    public Boolean paypalLogin_WrongID(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, BillingPageActions billingPageActions, ReviewPageActions reviewPageActions, ShoppingBagPageActions shoppingBagPageActions, String email, String pwd, String parentWindow) {
        staticWait(5000);

        if (waitUntilElementDisplayed(billingPageActions.continueButtonOnPaypalModal, 20)) {
            click(billingPageActions.continueButtonOnPaypalModal);
            return paypalOrderDetailsPageActions.clickOnContinueButton_WrongPaypal(shoppingBagPageActions, parentWindow);
        } else if (waitUntilElementDisplayed(emailAddressNew, 5)) {
            clearAndFillText(emailAddressNew, email);
            click(clickNextButton);
            waitUntilElementDisplayed(emailPasswordNew);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(8000);
            scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton_WrongPaypal(shoppingBagPageActions, parentWindow);
        } else if (waitUntilElementDisplayed(payPalLoginButton, 5)) {
            click(payPalLoginButton);
            waitUntilElementDisplayed(emailAddressNew, 8);
            clearAndFillText(emailAddressNew, email);
            click(clickNextButton);
            waitUntilElementDisplayed(emailPasswordNew, 5);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(9000);
            waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton, 20);
            // scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton_WrongPaypal(shoppingBagPageActions, parentWindow);
        } else if (isDisplayed(emailPasswordNew) || (isDisplayed(btn_Login))) {
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(8000);
            scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        } else
            switchToDefaultFrame();
        switchToFrame(0);
        waitUntilElementDisplayed(emailAddressNew, 20);
        staticWait(5000);
        clearAndFillText(emailAddressNew, email);
        clearAndFillText(emailPasswordNew, pwd);
        click(btn_LoginNew);
        staticWait(10000);
        waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButtonNew, 20);
        return paypalOrderDetailsPageActions.clickOnContinueButton_WrongPaypal(shoppingBagPageActions, parentWindow);

    }

    public boolean returnToStore(ShoppingBagPageActions shoppingBagPageActions) {
        switchToDefaultFrame();
        switchToFrame(0);
        if (waitUntilElementDisplayed(returnToStore, 5)) {
            click(returnToStore);
            if (waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 5)) {
                return true;
            } else
                return false;
        } else {
            addStepDescription("Unable to find the returnToStore link on paypal login page");
            return false;
        }
    }

    public boolean paypalLoginProd(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, BillingPageActions billingPageActions, String parentWindow) {
        staticWait(5000);
        if (waitUntilElementDisplayed(emailAddressNew, 5)) {
            click(cancelAndReturnButtonNew);
            staticWait(5000);
            switchBackToParentWindow(parentWindow);
            staticWait(5000);
            // switchToContinuePayPalFrameIfAvailable();
//            waitUntilElementDisplayed(closePayPalModal, 10);
//            click(closePayPalModal);
            addStepDescription("PayPal login is displayed to the user");
            return true;
        } else if (waitUntilElementDisplayed(payPalLoginButton, 5)) {
            click(cancelAndReturnButtonNew);
            staticWait(5000);
            switchBackToParentWindow(parentWindow);
            staticWait(5000);
            // switchToContinuePayPalFrameIfAvailable();
//            waitUntilElementDisplayed(closePayPalModal, 10);
//            click(closePayPalModal);
            addStepDescription("PayPal login is displayed to the user");
            return true;
        } else if (isDisplayed(emailPasswordNew) || (isDisplayed(btn_Login))) {
            click(cancelAndReturnButtonNew);
            switchBackToParentWindow(parentWindow);
            staticWait(5000);
//            waitUntilElementDisplayed(closePayPalModal, 3);
//            click(closePayPalModal);
            addStepDescription("PayPal login is displayed to the user");
            return true;
        } else
            switchToDefaultFrame();
        switchToFrame(0);
        waitUntilElementDisplayed(emailAddressNew, 20);
        staticWait(5000);
        clearAndFillText(emailAddressNew, email);
        click(cancelAndReturnButtonNew);
        waitUntilElementDisplayed(continuePayPalFrame, 3);
        click(closePayPalModal);
        return waitUntilElementDisplayed(paypalCheckoutButton, 3);
    }

    public boolean clickPayPalCheckout(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions) {
        waitUntilElementDisplayed(payPalIntCheckoutButton, 2);
        click(payPalIntCheckoutButton);
        return waitUntilElementDisplayed(payPalLoginButton, 10) || waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButtonNew, 10);
    }

    public Boolean paypalLogin_IntCheckout(ReviewPageActions reviewPageActions,PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, String email, String pwd, String fname, String lname,String address,
                                           String countryCode, String city, String zipcode, String parentWindow) {
        staticWait(5000);

         if (waitUntilElementDisplayed(emailAddressNew, 5)) {
            clearAndFillText(emailAddressNew, email);
            click(clickNextButton);
            waitUntilElementDisplayed(emailPasswordNew);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(8000);
             changeAddressPayPal(paypalOrderDetailsPageActions,countryCode,fname,lname,address,city,zipcode);
            scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        } else if (waitUntilElementDisplayed(payPalLoginButton, 5)) {
            click(payPalLoginButton);
            waitUntilElementDisplayed(emailAddressNew, 8);
            clearAndFillText(emailAddressNew, email);
            click(clickNextButton);
            waitUntilElementDisplayed(emailPasswordNew, 5);
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(9000);
             changeAddressPayPal(paypalOrderDetailsPageActions,countryCode,fname,lname,address,city,zipcode);
             waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton, 20);
            // scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);
        } else if (isDisplayed(emailPasswordNew) || (isDisplayed(btn_Login))) {
            clearAndFillText(emailPasswordNew, pwd);
            click(btn_Login);
            staticWait(8000);
             changeAddressPayPal(paypalOrderDetailsPageActions,countryCode,fname,lname,address,city,zipcode);
             scrollDownUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton);
            return paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions, parentWindow);

        } else {
            addStepDescription("Unable to do checkout with PayPal");
            return false;
        }

    }

    public boolean changeAddressPayPal(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions,String countryValue,String fname, String lname, String address, String city, String postalcode){
        waitUntilElementDisplayed(chagneShipAddress,2);
        click(chagneShipAddress);
        waitUntilElementDisplayed(addNewAddressPayPal,3);
        click(addNewAddressPayPal);
        waitUntilElementDisplayed(country_PayPal,3);
        selectDropDownByValue(country_PayPal,countryValue);
        clearAndFillText(firstName_PayPal,fname);
        clearAndFillText(lastName_PayPal,lname);
        clearAndFillText(addNewAddressPayPal,address);
        clearAndFillText(city_PayPal,city);
        clearAndFillText(postalCode_PayPal,postalcode);
        click(saveButton_PayPal);
       return waitUntilElementDisplayed(paypalOrderDetailsPageActions.payPalContinueButton,5);

    }
}