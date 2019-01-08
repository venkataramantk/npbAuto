package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018. ; Edited by Balu on 12/14/2018.
 */

public class DashboardRepo extends UiBase {

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Create CVI')]")
    public WebElement createCVIBtn;

    @FindBy(xpath=".//*[@class='button-inner'][contains(.,'View All')]")
    public WebElement viewAllBtn;

    @FindBy(xpath = ".//*[@class='count-label']")
    public WebElement countAssignedCVI;

    @FindBy(xpath = ".//*[@class='datatable-row-wrapper']")
    public WebElement countCVIDashboard;

    @FindBy(xpath = "//div[@class='fd-item-actions-wrapper']")
    public WebElement firstAnimalRecordActionWrapper;

    @FindBy(xpath = "//ion-item-sliding[contains(@class,'active-options-right')]//span[contains(.,'Copy')]//parent::button")
    public WebElement firstAnimalRecordActionWrapperCopy;

    @FindBy(xpath = "//ion-item-sliding[contains(@class,'active-options-right')]//span[contains(.,'Delete')]//parent::button")
    public WebElement firstAnimalRecordActionWrapperRemove;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'OK')]")
    public WebElement deleteAnimalPopupNo;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Cancel')]")
    public WebElement deleteAnimalPopupYes;


}
