package tests.web.initializer;

import org.openqa.selenium.WebDriver;
import ui.pages.actions.*;

/**
 * Created by venkat on 12/03/2018.
 */
public class PageInitializer extends BrowserInitializer {
    public HeaderMenuActions headerMenuActions;
    public LoginPageActions loginPageActions;



    public void initializePages(WebDriver driver) {
        headerMenuActions = new HeaderMenuActions(driver);
        loginPageActions = new LoginPageActions(driver);
    }
}
