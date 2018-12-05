package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class AnimalSelectionOverlayRepo extends UiBase {

    @FindBy(xpath = ".//*[@class='segment-button'][contains(.,'Small Animal')]")
    public WebElement SmallAnimal_TypePopUp;

    @FindBy(xpath = ".//*[@class='segment-button'][contains(.,'Large Animal')]")
    public WebElement LargeAnimal_TypePopUp;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Start CVI')]")
    public WebElement StartCVIButton_TypePopUp;

}
