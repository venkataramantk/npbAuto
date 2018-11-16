package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileMyAccountPageDrawerRepo;

/**
 * Created by AbdulazeezM on 2/24/2017.
 */
public class MobileMyAccountPageDrawerActions extends MobileMyAccountPageDrawerRepo {

    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileMyAccountPageDrawerActions.class);

    public MobileMyAccountPageDrawerActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean validateMyAccPageDrawerContent(String text1, String text2) {
        return getText(pointsToNextRewards).toLowerCase().contains(text1.toLowerCase()) &&
                getText(myRewardsAndOffers).toLowerCase().contains(text2.toLowerCase()) &&
                waitUntilElementDisplayed(currentPointText, 10) &&
                waitUntilElementDisplayed(myRewardsText, 10);

    }

    public boolean clickViewMyAccount(MobileHeaderMenuActions headerMenuActions, MMyAccountPageActions myAccountPageActions) {
        if (waitUntilElementDisplayed(viewMyAccountLink, 10))
            click(viewMyAccountLink);

        else {
            waitUntilElementDisplayed(headerMenuActions.welcomeCustomerLink, 30);
            click(headerMenuActions.welcomeCustomerLink);
            waitUntilElementDisplayed(viewMyAccountLink, 10);
            click(viewMyAccountLink);
        }
        staticWait(5000);
        return waitUntilElementDisplayed(myAccountPageActions.lnk_AccountOverView, 30);
    }

    public boolean linkDisplayed() {
        if (waitUntilElementDisplayed(fName) &&
                waitUntilElementDisplayed(pointsAndRewards))
            return true;
        else
            return false;
    }

    public boolean closeOverlay(MobileHomePageActions homePageActions) {
        click(closeSymbol);
        return waitUntilElementDisplayed(homePageActions.hiUserName, 5);
    }

    /**
     * Created by Richa Priya
     * Clicks on logout btn
     *
     * @return true if url is changed to home
     */
    public boolean clickLogoutLink(MobileHeaderMenuActions mheaderMenuActions) {
        click(logOutLnk);
        staticWait(10000);
        return mheaderMenuActions.waitUntilUrlChanged("home", 10);
    }

    public boolean validateNumbersInProgressBar() {
        int i = 0;
        for (WebElement number : progressbarNumber) {
            if (Integer.parseInt(getText(number)) == i)
                i = i + 25;
        }
        return true;
    }


    public boolean validatePointsAndRewardsInPanCake() {
        if (waitUntilElementDisplayed(rewardsContainer, 10)) {
            return getText(rewardsContainer).contains("points") && getText(rewardsContainer).contains("in Rewards");
        }
        return false;
    }

    public int getCurrentPoints() {
        return Integer.parseInt(getText(currentPoints).replaceAll("[^0-9]", ""));
    }

    public boolean validateOrderDetails() {
        return waitUntilElementDisplayed(orderStatus, 10) &&
                waitUntilElementDisplayed(orderNo, 3) &&
                waitUntilElementDisplayed(trackingNo, 2);
    }
}


