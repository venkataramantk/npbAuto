package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class LeftNavRepo extends UiBase {

    @FindBy(xpath = ".//*[contains(@class,'label')][contains(.,'Dashboard')]/ancestor::button")
    public WebElement dashboardBtn;

    @FindBy(xpath = ".//*[contains(@class,'label')][contains(.,'CVI')]/ancestor::button")
    public WebElement cviBtn;

    @FindBy(xpath = ".//*[contains(@class,'label')][contains(.,'Directory')]/ancestor::button")
    public WebElement directoryBtn;

}
