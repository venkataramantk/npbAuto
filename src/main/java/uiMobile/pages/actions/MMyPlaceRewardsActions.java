package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MMyRewardsActionsRepo;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MMyPlaceRewardsActions extends MMyRewardsActionsRepo {
    WebDriver
            mobileDriver;
    Logger logger = Logger.getLogger(MMyPlaceRewardsActions.class);

    public MMyPlaceRewardsActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Verify if My Place Rewards Page is displayed
     * or not
     *
     * @return Whether or not expected Page is displayed
     */
    public boolean isAtMyPlaceRewardsPage() {
        return waitUntilElementDisplayed(applyButton);
    }

    /**
     * clicks on close icon in My Place Rewards Page
     */
    public void closeMyPlaceRewardsPage() {
        waitUntilElementDisplayed(closeIcon, 30);
        click(closeIcon);
    }

    public boolean clickLoginLink(MLoginPageActions mloginPageActions) {
        if (waitUntilElementDisplayed(loginLink, 10)) {
            click(loginLink);
            return waitUntilElementDisplayed(mloginPageActions.emailAddrField);
        } else {
            addStepDescription("Login link is not displayed in MPR landing page");
            return false;
        }
    }

    public boolean clickCreateAccountLink(MCreateAccountActions mCreateAccountActions) {
        if (waitUntilElementDisplayed(createAccountLink, 10)) {
            click(createAccountLink);
            return waitUntilElementDisplayed(mCreateAccountActions.emailAddrField);
        } else {
            addStepDescription("Create account link is not displayed in MPR landing page");
            return false;
        }
    }

    public boolean clickLearnMoreLink(MRTPSAndWicActions mrtpsAndWicActions) {
        if (waitUntilElementDisplayed(learnMoreLink, 10)) {
            click(learnMoreLink);
            return waitUntilElementDisplayed(mrtpsAndWicActions.loginLink);
        } else {
            addStepDescription("Learn More link is not displayed in MPR landing page");
            return false;
        }
    }
}
