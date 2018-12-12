package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import javax.naming.Name;

/**
 * Created by Balu on 12/03/2018.
 */

public class HeaderRepo extends UiBase {

    @FindBy(xpath = ".//*[contains(@class,'av-active')][contains(.,'Dashboard')]")
    public WebElement dashboardLbl;

    @FindBy(name = "arrow-dropdown")
    public WebElement orgDropdownLbl;

    @FindBy(xpath = ".//*[contains(@class,'button-inner')][contains(.,'Log out')]")
    public WebElement logoutButtonBtn;

    public WebElement loginIDTxtLbl(String emailID){
        return getDriver().findElement(By.xpath(".//*[contains(@class,'logout')][contains(.,'"+emailID+"')]"));
    }


}
