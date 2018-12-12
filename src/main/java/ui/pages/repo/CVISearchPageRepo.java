package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class CVISearchPageRepo extends UiBase {

    @FindBy(xpath=".//*[@class='count']")
    public WebElement CVIsCount;

    @FindBy(xpath = ".//*[@class='datatable-row-wrapper']")
    public WebElement AllCVIsRecords;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'All')]")
    public WebElement AllCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Pending')]")
    public WebElement PendingCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Issued')]")
    public WebElement IssuedCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Ready')]")
    public WebElement ReadyCVIsTab;

    @FindBy(xpath = ".//*[@role='button'][contains(.,'Draft')]")
    public WebElement DraftCVIsTab;

}
