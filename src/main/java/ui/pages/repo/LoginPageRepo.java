package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by venkat on 11/29/2018. ; Edited by Balu on 12/14/2018.
 */

public class LoginPageRepo extends HeaderMenuRepo {

    @FindBy(id = "username")
    public WebElement emailTxt;

    @FindBy(id = "password")
    public WebElement passwordTxt;

    @FindBy(id="forgotPassword")
    public WebElement forgotPsswrdLnk;

    @FindBy(css = "div.login-bottom button")
    public WebElement loginBtn;

    @FindBy(id = "register")
    public WebElement registerNowLnk;

    @FindBy(xpath = ".//*[contains(@class,'alert')][contains(.,'deactivate')]")
    public WebElement deactivatedAccountMsgLbl;

    @FindBy(xpath = ".//*[contains(@class,'alert')][contains(.,'logged out')]")
    public WebElement loggedOutMsgLbl;

    @FindBy(xpath = ".//*[@class='selection']/span")
    public WebElement multipleEntityDropdown;

    @FindBy(xpath = ".//*[@class='select2-results']//li[contains(.,'EntityName')]")
    public WebElement multipleEntityDropdownOptions;

    @FindBy(xpath = ".//*[@type='submit']")
    public WebElement submitBtn;

    @FindBy(xpath = ".//*[@class='btn btn-act btn-act-positive--primary'][contains(.,'Logout')]")
    public WebElement multipleEntityLogoutBtn;



}
