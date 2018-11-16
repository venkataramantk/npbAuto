package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.WishListLoginOverlayRepo;

/**
 * Created by Venkat on 04/10/2016.
 */
public class WishListLoginOverlayActions extends WishListLoginOverlayRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(WishListLoginOverlayActions.class);

    public WishListLoginOverlayActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }


    public void enterEmailQV(String email) {
        waitUntilElementDisplayed(emailIDFldQV, 2);
        clearAndFillText(emailIDFldQV, email);
    }

    public void enterEmailPDP(String email) {
        waitUntilElementDisplayed(emailIDFldPDP, 2);
        clearAndFillText(emailIDFldPDP, email);
    }

    public void enterEmailShoppingBag(String email) {
        waitUntilElementDisplayed(emailIDFldShoppingBag, 2);
        clearAndFillText(emailIDFldShoppingBag, email);
    }

    public void enterPasswordQV(String password) {
        waitUntilElementDisplayed(passwordFldQV, 2);
        clearAndFillText(passwordFldQV, password);
    }

    public void enterPasswordPDP(String password) {
        waitUntilElementDisplayed(passwordFldPDP, 2);
        clearAndFillText(passwordFldPDP, password);
    }

    public void enterPasswordShoppingBag(String password) {
        waitUntilElementDisplayed(passwordFldShoppingBag, 2);
        clearAndFillText(passwordFldShoppingBag, password);
    }


}
