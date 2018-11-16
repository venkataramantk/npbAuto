package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileMailInatorPageRepo;

/**
 * Created by skonda on 8/3/2016.
 */
public class MobileMailInatorActions extends MobileMailInatorPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileMailInatorActions.class);

    public MobileMailInatorActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean enterEmailAddressAndNavigateToInbox(String emailAddress){
        waitUntilElementDisplayed(inboxField);
        clearAndFillText(inboxField,emailAddress);
        click(goButton);
        return waitUntilElementDisplayed(mailFrom);
    }
}
