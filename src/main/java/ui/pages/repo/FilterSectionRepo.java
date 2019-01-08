package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class FilterSectionRepo extends UiBase {

    @FindBy(xpath = ".//*[@class='filter-text' and contains(.,'Show')]")
    public WebElement showFiltersLnk;

    @FindBy(xpath = ".//*[@class='filter-text' and contains(.,'Hide')]")
    public WebElement hideFiltersLnk;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[1]")
    public WebElement creatorFilterTxtbx;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[2]")
    public WebElement assignedVetFilterTxtbx;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[3]")
    public WebElement consignorFilterTxtbx;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[4]")
    public WebElement consigneeFilterTxtbx;

    @FindBy(xpath = ".//*[@aria-labelledby='lbl-15']")
    public WebElement animalNicknameFilterTxtbx;

    @FindBy(xpath = ".//*[@id='select-10-0']//span[@class='button-inner']")
    public WebElement lastUpdatedFilterDropdown;

    @FindBy(id = "select-16-0")
    public WebElement speciesFilterDropdown;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Aquaculture')]")
    public WebElement aquacultureFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Cattle')]")
    public WebElement cattleFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Camelid')]")
    public WebElement camelidFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Goats')]")
    public WebElement goatsFilterOptn;

    @FindBy(xpath = "..//*[@class='label label-md'][contains(.,'Cervids')]")
    public WebElement cervidsFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Chickens')]")
    public WebElement chickensFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Equine')]")
    public WebElement equineFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Sheep')]")
    public WebElement sheepFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Swine')]")
    public WebElement swineFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Turkeys')]")
    public WebElement turkeysFilterOptn;

    @FindBy(xpath = "..//*[@class='label label-md'][contains(.,'Dogs')]")
    public WebElement dogsFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Cats')]")
    public WebElement catsFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Other')]")
    public WebElement otherFilterOptn;

    @FindBy(id = "select-17-0")
    public WebElement sortByFilterDropdown;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Date - Most Recent')]")
    public WebElement mostRecentDateOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Date - Oldest')]")
    public WebElement oldestDateOptn;

    @FindBy(xpath = ".//*[@id='lbl-67'][contains(.,'Animal')]")
    public WebElement animalOptn;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last 15 days')]")
    public WebElement lastUpdated15days;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last 30 days')]")
    public WebElement lastUpdated30days;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last Week')]")
    public WebElement lastUpdatedWeek;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last Month')]")
    public WebElement lastUpdatedMonth;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Custom')]")
    public WebElement lastUpdatedCustom;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Clear')]")
    public WebElement clearBtn;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Search')]")
    public WebElement searchBtn;



}
