package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by venkat on 03/12/2018.
 */
public class HeaderMenuRepo extends LeftNavRepo {

    @FindBy(css = "img.fd-logo-menu")
    public WebElement logoAgView;

    @FindBy(css = "a.av-active[title='User Administration and Preferences']")
    public WebElement dashboardLnk;

    @FindBy(css = "div.av-global-nav a:nth-child(3)")
    public WebElement adminLnk;

    @FindBy(css = "div.av-global-nav a[title='Help']")
    public WebElement helpLnk;

}

