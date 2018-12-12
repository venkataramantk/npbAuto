package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
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


}
