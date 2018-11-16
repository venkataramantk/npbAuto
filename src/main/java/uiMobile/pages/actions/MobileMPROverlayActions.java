package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileMPROverlayRepo;

/**
 * Created by AbdulazeezM on 8/11/2017.
 */
public class MobileMPROverlayActions extends MobileMPROverlayRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileMPROverlayActions.class);

    public MobileMPROverlayActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }
    public boolean clickApplyToday() {

        scrollDownToElement(applyButton);
        click(applyButton);
        return waitUntilElementDisplayed(firstName, 30);
    }

    public boolean checkTermsCheckBox(){
        if(!(isSelected(termsCheckBox))){
            click(termsAndCondition);
        }
        return isSelected(termsCheckBox);
    }
}
