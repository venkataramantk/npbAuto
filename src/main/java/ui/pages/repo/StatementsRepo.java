package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 17/12/2018.
 */

public class StatementsRepo extends UiBase {

    @FindBy(xpath=".//*[@id='control258']//input")
    public WebElement entryPermitNumTxt;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Add statement')]")
    public WebElement addStatementBtn;

    @FindBy(xpath = "//textarea[@class='text-input text-input-md']")
    public WebElement addNewStatementTxt;

    @FindBy(xpath=".//*[@class='button-inner'][contains(.,'Back')]")
    public WebElement backBtn;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Next')]")
    public WebElement nextBtn;
    

}
