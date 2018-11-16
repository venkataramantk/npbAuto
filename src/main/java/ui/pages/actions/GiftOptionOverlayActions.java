package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.GiftOptionOverlayRepo;

/**
 * Created by user on 6/15/2016.
 */
public class GiftOptionOverlayActions extends GiftOptionOverlayRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(GiftOptionOverlayActions.class);

    public GiftOptionOverlayActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver,this);
    }

    public boolean clickOnNext(ShippingPageActions shippingPageActions){
        click(btn_Next);
        return waitUntilElementDisplayed(shippingPageActions.continueCheckoutButton);
    }

}
