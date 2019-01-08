package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by Balu on 17/12/2018.
 */

public class CattleADPRepo extends UiBase {

    @FindBy(xpath=".//*[@class='segment-button'][contains(text(),'Dairy')]")
    public WebElement dairyTypeOption;

    @FindBy(xpath = ".//*[@class='segment-button'][contains(text(), 'Beef')]")
    public WebElement beefTypeOption;

    @FindBy(xpath = ".//*[@class='segment-button'][contains(text(), 'Bison')]")
    public WebElement bisonTypeOtion;


}
