package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.DomManageAvailabilityPageRepo;

/**
 * Created by skonda on 8/12/2016.
 */
public class DomManageAvailabilityPageActions extends DomManageAvailabilityPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(DomManageAvailabilityPageActions.class);

    public DomManageAvailabilityPageActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickUSECommerceArrow(){
        click(arrowForUSECommerce);
        return waitUntilElementDisplayed(testDropDown);
    }

    public boolean clickCAECommerceArrow(){
        click(arrowForCAECommerce);
        return waitUntilElementDisplayed(testDropDown);
    }
    public boolean isUSECommerceActive(){
        waitUntilElementDisplayed(activeUSECommerceElement);
        String textForActive = getText(activeUSECommerceElement);
        return textForActive.equalsIgnoreCase("Active");
    }

    public boolean isCAECommerceActive(){
        waitUntilElementDisplayed(activeCAECommerceElement);
        String textForActive = getText(activeCAECommerceElement);
        return textForActive.equalsIgnoreCase("Active");
    }

    public boolean clickTestDropDown(){
        mouseHover(testDropDown);
        boolean isReBuildPresent = waitUntilElementDisplayed(reBuildOption, 30);
        if(!isReBuildPresent){
            click(testDropDown);
        }

        return waitUntilElementDisplayed(reBuildOption, 30);

    }

    public void clickMaximizeWindow(){
        click(maximizeImg);
        staticWait(5000);
    }
    public boolean clickRebuildOption(){
        click(reBuildOption);
        return waitUntilElementDisplayed(publishAvailabilityCheckBox);
    }

    public boolean isPublishAvailabilityDisabled(){
        if(!isSelected(publishAvailabilityCheckBox)){
            return true;
        }
        else{
            click(publishAvailabilityCheckBox);
            if(!isEnabled(publishAvailabilityCheckBox)){
                return true;
            }
            return false;
        }
    }

    public void clickYesOnPublishAvailability(){
        click(yesButtonOnPubAvail);
        staticWait(5000);
//        return verifyElementNotDisplayed(yesButtonOnPubAvail);
    }
}
