package uiMobile.pages.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MPayPalPageRepo;

import java.util.Map;

/**
 * Created by JKotha on 20/10/2017.
 */
public class MPayPalPageActions extends MPayPalPageRepo {
    WebDriver mobileDriver;

    public MPayPalPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Closes the pay with paypal popup
     *
     * @return true if the popup closed
     */
    public boolean closePayWithPaypal() {
        switchToParent();
        waitUntilElementDisplayed(paypalClose, 20);
        javaScriptClick(mobileDriver, paypalClose);
        return verifyElementNotDisplayed(paypalClose, 20);
    }

    /**
     * Opens paypal login page
     *
     * @return true if the new window opens
     */
    public boolean clickPayPalCheckout() {
        /*waitUntilElementDisplayed(paypalFrame);
        switchToFrame(paypalFrame);
        waitUntilElementDisplayed(paypalCheckoutOnPaypalModal, 20);
        click(paypalCheckoutOnPaypalModal);*/
        return getCurrentWindowHandles() == 2;
    }


    public boolean clickContinueButton(MReviewPageActions reviewPageActions) {
        waitUntilElementDisplayed(continueBtn, 120);
        staticWait(5000); //this wait is for handeling cases when spinner loading even after required element is displayed
        click(continueBtn);
        int i = 0;
        do {
            staticWait(3000);
            i++;
            if (i > 10) break;
        }
        while (getCurrentWindowHandles() > 1);
        switchToParent();
        return waitUntilElementDisplayed(reviewPageActions.submitOrderBtn);
    }


    /**
     * To paywith paypal
     *
     * @param email paypal user id
     * @param pwd   paypal pwd
     * @return review page
     */
    public Boolean paypalLogin(String email, String pwd, MReviewPageActions mreviewPageActions) {
        if (getCurrentWindowHandles() > 1) {
            switchToWindow();
            if(waitUntilElementDisplayed(loginButton,30)) {
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                click(loginButton);
                waitUntilElementDisplayed(emailAddress, 180);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(emailAddress, email);
                click(nextBtn);
                waitUntilElementDisplayed(passwordFld, 180);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return clickContinueButton(mreviewPageActions);
            }
            else if (waitUntilElementDisplayed(notYouLink,20)) {
                click(notYouLink);
                waitUntilElementDisplayed(accountLogin, 280);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                //   click(loginButton);
                waitUntilElementDisplayed(emailAddress, 180);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(emailAddress, email);
                // click(nextBtn);
                waitUntilElementDisplayed(passwordFld, 180);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return clickContinueButton(mreviewPageActions);
            }
            else if(waitUntilElementDisplayed(emailAddress)){
                clearAndFillText(emailAddress, email);
                click(nextBtn);
                waitUntilElementDisplayed(passwordFld, 180);
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return clickContinueButton(mreviewPageActions);
            }
            else if(isDisplayed(passwordFld)){
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return clickContinueButton(mreviewPageActions);
            }
            else{
                return clickContinueButton(mreviewPageActions);

            }
        }

        else {
            addStepDescription("something wrong with PayPal, PayPal Window is not available");
            return false;
        }
    }

    public void clickCancelPaypalPaymentLink() {
        if (getCurrentWindowHandles() > 1) {
            switchToWindow();
            waitUntilElementDisplayed(cancelLinkInLoginPage, 5);
            click(cancelLinkInLoginPage);
        }
    }

    /**
     * To pay with PayPal for International
     *
     * @param email paypal user id
     * @param pwd   paypal pwd
     * @return review page
     */
    public Boolean paypalLogin_int(String email, String pwd, MReviewPageActions mreviewPageActions) {
        waitUntilElementDisplayed(loginButton, 120);
        staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
        click(loginButton);
        waitUntilElementDisplayed(emailAddress, 120);
        staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
        clearAndFillText(emailAddress, email);
        click(nextBtn);
        waitUntilElementDisplayed(passwordFld, 120);
        staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
        clearAndFillText(passwordFld, pwd);
        click(accountLogin);
        waitUntilElementDisplayed(proceedWithPaPalButton, 5);
        click(proceedWithPaPalButton);
        return waitUntilElementDisplayed(mreviewPageActions.submitOrderBtn);
    }

    public boolean clickProceedToPaypalBtn() {
        waitUntilElementDisplayed(proceedWithPaPalButton, 5);
        click(proceedWithPaPalButton);
        staticWait(5000);
        waitForFrameToLoad(0, 60);
        return waitUntilElementDisplayed(loginButton, 20);
    }

    public boolean comparePositionOfElement(Map<String, String> val1, Map<String, String> val2) {
        return Float.parseFloat(val1.get("left")) > Float.parseFloat(val2.get("right"));
    }

    /**
     * Created by Richa Priya
     * Click on change link and change the shipping address randomly
     * from the list of addresses displayed on PayPal
     *
     * @return updated address
     */
    public String changeBillingAddFromPayPal() {
        waitUntilElementClickable(paypalChangeAddrBtn, 10);
        click(paypalChangeAddrBtn);
        int index = randInt(1, paypalAddrList.size());
        String addToBeUpdated = getText(paypalAddrList.get(index));
        click(paypalAddrList.get(index));
        waitUntilElementDisplayed(paypalChangeAddrBtn, 10);
        return addToBeUpdated;
    }

    /**
     * Created by Richa Priya
     * overloaded method of paypal which will login into paywith paypal
     * but not click on click on continue button
     *
     * @param email paypal user id
     * @param pwd   paypal pwd
     * @return review page
     */
    public boolean paypalLogin(String email, String pwd) {
        if (getCurrentWindowHandles() > 1) {
            switchToWindow();
            if (waitUntilElementDisplayed(loginButton, 20)) {
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                click(loginButton);
                waitUntilElementDisplayed(emailAddress, 120);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(emailAddress, email);
                click(nextBtn);
                waitUntilElementDisplayed(passwordFld, 120);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return waitUntilElementDisplayed(paypalChangeAddrBtn, 20);
            } else if (waitUntilElementDisplayed(notYouLink,20)) {
                click(notYouLink);
                waitUntilElementDisplayed(accountLogin, 280);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                //   click(loginButton);
                waitUntilElementDisplayed(emailAddress, 180);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(emailAddress, email);
                // click(nextBtn);
                waitUntilElementDisplayed(passwordFld, 180);
                staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return waitUntilElementDisplayed(paypalChangeAddrBtn, 20);
            }
            else{
                clearAndFillText(passwordFld, pwd);
                click(accountLogin);
                return waitUntilElementDisplayed(paypalChangeAddrBtn, 20);
            }
        }
        else {
            addStepDescription("something wrong with PayPal, PayPal Window is not available");
            return false;
        }
    }

    /**
     * Created by Richa Priya
     *
     * @param email paypal user id
     * @param pwd   paypal pwd
     * @return review page
     */
    public Boolean paypalLogin_InvalidCred(String email, String pwd, MShoppingBagPageActions mShoppingBagPageActions) {
        if (getCurrentWindowHandles() > 1) {
            switchToWindow();
            waitUntilElementDisplayed(loginButton, 120);
            staticWait(5000); //this wait is for handeling cases when spinner loading even after required element is displayed
            click(loginButton);
            waitUntilElementDisplayed(emailAddress, 120);
            staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
            clearAndFillText(emailAddress, email);
            click(nextBtn);
            waitUntilElementDisplayed(passwordFld, 120);
            staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
            clearAndFillText(passwordFld, pwd);
            click(accountLogin);
            return clickContinueButton_InvalidCreds(mShoppingBagPageActions);
            //return waitUntilElementDisplayed(login_InvalidErr, 30);
        } else {
            addStepDescription("something wrong with PayPal, PayPal Window is not available");
            return false;
        }
    }

    public boolean clickContinueButton_InvalidCreds(MShoppingBagPageActions mshoppingBagPageActions) {
        waitUntilElementDisplayed(continueBtn, 120);
        staticWait(5000); //this wait is for handeling cases when spinner loading even after required element is displayed
        click(continueBtn);
        int i = 0;
        do {
            staticWait(3000);
            i++;
            if (i > 10) break;
        }
        while (getCurrentWindowHandles() > 1);
        switchToParent();
        return waitUntilElementDisplayed(mshoppingBagPageActions.checkoutBtn,120);
    }

    public Boolean paypalLogin_AfterInvalidLogin(String email, String pwd,MReviewPageActions mreviewPageActions) {
        if (getCurrentWindowHandles() > 1) {
            switchToWindow();
            if(waitUntilElementDisplayed(notYouLink)){
                click(notYouLink);
            }
            waitUntilElementDisplayed(accountLogin, 280);
            staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
            //   click(loginButton);
            waitUntilElementDisplayed(emailAddress, 180);
            staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
            clearAndFillText(emailAddress, email);
            // click(nextBtn);
            waitUntilElementDisplayed(passwordFld, 180);
            staticWait(2000); //this wait is for handeling cases when spinner loading even after required element is displayed
            clearAndFillText(passwordFld, pwd);
            click(accountLogin);
            return clickContinueButton(mreviewPageActions);
        }else {
            addStepDescription("something wrong with PayPal, PayPal Window is not available");
            return false;
        }
    }

}
