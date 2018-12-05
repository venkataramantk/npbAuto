package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import javax.naming.Name;

/**
 * Created by Balu on 12/03/2018.
 */

public class HeaderRepo extends UiBase {

    @FindBy(xpath = ".//*[contains(@class,'av-active')][contains(.,'Dashboard')]")
    public WebElement DashboardLbl;

    @FindBy(name = "arrow-dropdown")
    public WebElement OrgDropdownLbl;

    @FindBy(xpath = ".//*[contains(@class,'button-inner')][contains(.,'Log out')]")
    public WebElement LogoutButtonBtn;

    @FindBy(xpath = ".//*[contains(@class,'logout')][contains(.,'#LoginID#')]")
    public WebElement LoginIDTxtLbl;


}
