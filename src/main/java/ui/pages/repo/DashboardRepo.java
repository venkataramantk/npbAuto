package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 12/03/2018.
 */

public class DashboardRepo extends UiBase {

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Create CVI')]")
    public WebElement CreateCVIBtn;

    @FindBy(xpath=".//*[@class='button-inner'][contains(.,'View All')]")
    public WebElement ViewAllBtn;

    @FindBy(xpath = ".//*[@class='segment-button'][contains(.,'Small Animal')]")
    public WebElement SmallAnimal_TypePopUp;

    @FindBy(xpath = ".//*[@class='segment-button'][contains(.,'Large Animal')]")
    public WebElement LargeAnimal_TypePopUp;

    @FindBy(xpath = ".//*[@class='button-inner'][contains(.,'Start CVI')]")
    public WebElement StartCVIButton_TypePopUp;

}
