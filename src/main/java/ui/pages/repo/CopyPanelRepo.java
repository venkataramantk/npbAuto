package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 17/12/2018.
 */

public class CopyPanelRepo extends UiBase {

    @FindBy(xpath="//input[@type='number']")
    public WebElement numOfCopiesTxtBox;

    @FindBy(xpath = ".//*[@class='fd-radios']//ion-segment-button[contains(.,'Auto Increment')]")
    public WebElement autoIncrementBtn;

    @FindBy(xpath = ".//*[@class='fd-radios']//ion-segment-button[contains(.,'Enter Manually')]")
    public WebElement enterManuallyBtn;

    @FindBy(xpath = ".//*[@class='fd-radios']//ion-segment-button[contains(.,'Leave Empty')]")
    public WebElement leaveEmptyBtn;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Cancel')]")
    public WebElement cancelBtn;

    @FindBy(xpath = ".//*[@class='button button-md button-default button-default-md button-md-green']//span")
    public WebElement copyBtn;

    @FindBy(xpath = ".//*[@class='input-wrapper']//textarea")
    public WebElement enterManuallyListOfIds;

}
