package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by skonda on 8/3/2016.
 */
public class MailInatorPageRepo extends UiBase {

    @FindBy(css="#inboxfield")
    public WebElement inboxField;

    @FindBy(css=".input-group-btn button")
    public WebElement goButton;

    @FindBy(css="div[title='FROM:']")
    public WebElement mailFrom;
}
