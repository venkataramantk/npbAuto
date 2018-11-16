package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.PaypalOrderDetailPageRepo;

/**
 * Created by user on 6/24/2016.
 */
public class PaypalOrderDetailsPageActions extends PaypalOrderDetailPageRepo {

    WebDriver driver = null;
    Logger logger = Logger.getLogger(PaypalOrderDetailsPageActions.class);

    public PaypalOrderDetailsPageActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickOnContinueButton(ReviewPageActions reviewPageActions,String parentWindow){
        staticWait(5000);
       // boolean isContinueButtonVisible = waitUntilElementDisplayed(payPalContinueButton, 60);

         /* if (isContinueButtonVisible) {
              click(payPalContinueButton);
          }
          else*/
             {
                 waitUntilElementDisplayed(payPalContinueButton,10);
                 click(payPalContinueButtonNew);
               //  switchToDefaultFrame();
             }
        staticWait(9000);
        switchBackToParentWindow(parentWindow);
        return waitUntilElementDisplayed(reviewPageActions.submOrderButton,20);
    }

    public boolean clickOnContinueButton_WrongPaypal(ShoppingBagPageActions shoppingBagPageActions,String parentWindow){
        staticWait(5000);
        boolean isContinueButtonVisible = waitUntilElementDisplayed(payPalContinueButton, 60);

        if (isContinueButtonVisible) {
            click(payPalContinueButton);
        }
        else {

            switchBackToParentWindow(parentWindow);
            if (isDisplayed(shoppingBagPageActions.checkoutBtn)) {
                return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 30);
            } else {
                switchToFrame(0);
                click(confirmButton);
                switchToDefaultFrame();
            }
        }
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn,30);
    }

    }
