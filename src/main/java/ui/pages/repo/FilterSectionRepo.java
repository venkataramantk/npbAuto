package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class FilterSectionRepo extends UiBase {

    @FindBy(xpath = ".//*[@class='filter-text' and contains(.,'Show')]")
    public WebElement ShowFiltersLnk;

    @FindBy(xpath = ".//*[@class='filter-text' and contains(.,'Hide')]")
    public WebElement HideFiltersLnk;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[1]")
    public WebElement CreatorFilterTxtbx;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[2]")
    public WebElement AssignedVetFilterTxtbx;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[3]")
    public WebElement ConsignorFilterTxtbx;

    @FindBy(xpath = "(.//*[@class='searchbar-input'])[4]")
    public WebElement ConsigneeFilterTxtbx;

    @FindBy(xpath = ".//*[@aria-labelledby='lbl-15']")
    public WebElement AnimalNicknameFilterTxtbx;

    @FindBy(xpath = ".//*[@id='select-10-0']//span[@class='button-inner']")
    public WebElement LastUpdatedFilterDropdown;

    @FindBy(id = "select-16-0")
    public WebElement SpeciesFilterDropdown;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Aquaculture')]")
    public WebElement AquacultureFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Cattle')]")
    public WebElement CattleFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Camelid')]")
    public WebElement CamelidFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Goats')]")
    public WebElement GoatsFilterOptn;

    @FindBy(xpath = "..//*[@class='label label-md'][contains(.,'Cervids')]")
    public WebElement CervidsFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Chickens')]")
    public WebElement ChickensFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Equine')]")
    public WebElement EquineFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Sheep')]")
    public WebElement SheepFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Swine')]")
    public WebElement SwineFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Turkeys')]")
    public WebElement TurkeysFilterOptn;

    @FindBy(xpath = "..//*[@class='label label-md'][contains(.,'Dogs')]")
    public WebElement DogsFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Cats')]")
    public WebElement CatsFilterOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Other')]")
    public WebElement OtherFilterOptn;

    @FindBy(id = "select-17-0")
    public WebElement SortByFilterDropdown;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Date - Most Recent')]")
    public WebElement MostRecentDateOptn;

    @FindBy(xpath = ".//*[@class='label label-md'][contains(.,'Date - Oldest')]")
    public WebElement OldestDateOptn;

    @FindBy(xpath = ".//*[@id='lbl-67'][contains(.,'Animal')]")
    public WebElement AnimalOptn;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last 15 days')]")
    public WebElement LastUpdated15days;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last 30 days')]")
    public WebElement LastUpdated30days;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last Week')]")
    public WebElement LastUpdatedWeek;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Last Month')]")
    public WebElement LastUpdatedMonth;

    @FindBy(xpath = ".//*[@class='input-wrapper'][contains(.,'Custom')]")
    public WebElement LastUpdatedCustom;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Clear')]")
    public WebElement ClearBtn;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Search')]")
    public WebElement SearchBtn;



}
