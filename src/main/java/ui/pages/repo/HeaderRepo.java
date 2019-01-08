package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import javax.naming.Name;

/**
 * Created by Balu on 12/03/2018. ; Edited by Balu on 12/14/2018.
 */

public class HeaderRepo extends UiBase {

    @FindBy(xpath = ".//*[contains(@class,'av-active')][contains(.,'Dashboard')]")
    public WebElement dashboardLbl;

    @FindBy(xpath = ".//*[contains(@class,'av-global-nav')]//*[contains(.,'Your Data')]")
    public WebElement yourDataLbl;

    @FindBy(xpath = ".//*[contains(@class,'av-global-nav')]//*[contains(.,'Admin')]")
    public WebElement adminLbl;

    @FindBy(xpath = ".//*[contains(@class,'av-global-nav')]//*[contains(.,'Help')]")
    public WebElement helpLbl;

    @FindBy(xpath = ".//*[@name='arrow-dropdown']")
    public WebElement orgDropdownLbl;

    @FindBy(xpath = ".//*[contains(@class,'button-inner')][contains(.,'Log out')]")
    public WebElement logoutButtonBtn;

    @FindBy(xpath = ".//*[contains(@class,'logout')][contains(.,"+emailID+")]//label")
    public WebElement loginIDTxtLbl;


}
