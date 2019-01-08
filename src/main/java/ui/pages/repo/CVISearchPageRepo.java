package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class CVISearchPageRepo extends UiBase {

    @FindBy(xpath=".//*[@class='count']")
    public WebElement cviCount;

    @FindBy(xpath = ".//*[@class='datatable-row-wrapper']")
    public WebElement allCVIsRecords;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'All')]")
    public WebElement allCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Pending')]")
    public WebElement pendingCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Issued')]")
    public WebElement issuedCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Ready')]")
    public WebElement readyCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Draft')]")
    public WebElement draftCVIsTab;

}
