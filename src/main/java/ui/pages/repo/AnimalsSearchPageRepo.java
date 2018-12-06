package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class AnimalsSearchPageRepo extends UiBase {

    @FindBy(xpath=".//*[@ng-reflect-title='Animals'][contains(.,'Animals')]")
    public WebElement AnimalsHeaderTab;

    @FindBy(xpath = ".//*[@class='c-btn'][contains(.,'Select Species')]")
    public WebElement SelectSpeciesDropdown;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Aquaculture')]")
    public WebElement AquacultureSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Cattle')]")
    public WebElement CattleSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Camelid')]")
    public WebElement CamelidSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Goats')]")
    public WebElement GoatsSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Cervids')]")
    public WebElement CervidsSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Chickens')]")
    public WebElement ChickensSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Equine')]")
    public WebElement EquineSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Sheep')]")
    public WebElement SheepSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Swine')]")
    public WebElement SwineSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Turkeys')]")
    public WebElement TurkeysSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Dogs')]")
    public WebElement DogsSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Cats')]")
    public WebElement CatsSpecies;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'Other')]")
    public WebElement OtherSpecies;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Add New')]")
    public WebElement AddNewButton;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Import CSV')]")
    public WebElement ImportCSVButton;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'CSV Template')]")
    public WebElement CSVTemplateButton;

    @FindBy(xpath = ".//*[@class='c-btn'][contains(.,'Add Saved Animals by Name')]")
    public WebElement SavedAnimalDropdownSelection;

    @FindBy(xpath = "//div[@class='animal-selection-bottom']//label[2]")
    public WebElement SavedAnimalsList_Random;

    @FindBy(xpath = ".//*[@class='item-wrapper']")
    public WebElement AddedAnimalRecord_Random;


}
