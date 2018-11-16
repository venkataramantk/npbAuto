package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.MailInatorPageRepo;

/**
 * Created by skonda on 8/3/2016.
 */
public class MailInatorActions extends MailInatorPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(MailInatorActions.class);

    public MailInatorActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean enterEmailAddressAndNavigateToInbox(String emailAddress){
        waitUntilElementDisplayed(inboxField);
        clearAndFillText(inboxField,emailAddress);
        click(goButton);
        return waitUntilElementDisplayed(mailFrom);
    }
}
