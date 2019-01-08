package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018. ; Edited by Balu on 12/14/2018.
 */

public class AnimalsHomePageRepo extends UiBase {

    @FindBy(xpath=".//*[@role='button'][contains(.,'Animals')]")
    public WebElement animalsHeaderTab;

    @FindBy(xpath = ".//*[@class='c-btn'][contains(.,'Select Species')]")
    public WebElement selectSpeciesDropdown;

    @FindBy(xpath = ".//*[@class='pure-checkbox'][contains(.,'EnterSpeciesName')]")
    public WebElement selectSpeciesOption;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Add New')]")
    public WebElement addNewButton;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Import CSV')]")
    public WebElement importCSVButton;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'CSV Template')]")
    public WebElement csvTemplateButton;

    @FindBy(xpath = ".//*[@class='c-btn'][contains(.,'Add Saved Animals by Name')]")
    public WebElement savedAnimalDropdownSelection;

    @FindBy(xpath = "//label[contains(@class,'animal-name')][contains(.,'SavedAnimal')]")
    public WebElement savedAnimalsListOptionSelection;

    @FindBy(xpath = "//*[contains(@class, 'flex-column')]//ion-label[2]")
    public WebElement totalNumOfRecords;

    @FindBy(xpath = "//*[contains(@class, 'flex-column')]//ion-label[4]")
    public WebElement totalNumOfAnimals;

    @FindBy(xpath = ".//*[contains(@class, 'item-wrapper')]")
    public WebElement addedAnimalRecord_Random;

    @FindBy(xpath = "//div[@class='fd-item-actions-wrapper']")
    public WebElement firstAnimalRecordActionWrapper;

    @FindBy(xpath = "//ion-item-sliding[contains(@class,'active-options-right')]//span[contains(.,'Copy')]//parent::button")
    public WebElement firstAnimalRecordActionWrapperCopy;

    @FindBy(xpath = "//ion-item-sliding[contains(@class,'active-options-right')]//span[contains(.,'Remove')]//parent::button")
    public WebElement firstAnimalRecordActionWrapperRemove;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'No')]")
    public WebElement deleteAnimalPopupNo;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Yes')]")
    public WebElement deleteAnimalPopupYes;



}
