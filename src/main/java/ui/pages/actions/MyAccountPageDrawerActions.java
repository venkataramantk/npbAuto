package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.MyAccountPageDrawerRepo;

/**
 * Created by AbdulazeezM on 2/24/2017.
 */
public class MyAccountPageDrawerActions extends MyAccountPageDrawerRepo {

    WebDriver driver;
    Logger logger = Logger.getLogger(MyAccountPageDrawerActions.class);

    public MyAccountPageDrawerActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean validateMyAccPageDrawerContent(String text1, String text2) {
        staticWait(4000);
        boolean pointsText = isPointsToNextRewardsDisplaying(text1);
        boolean rewardsText = getText(myRewardsAndOffers).toLowerCase().contains(text2.toLowerCase());
        if (pointsText && rewardsText)
            return true;
        else
            return false;
    }

    public boolean isPointsToNextRewardsDisplaying(String text1) {
        boolean pointsText = getText(pointsToNextRewards).toLowerCase().contains(text1.toLowerCase());
        staticWait(2000);
        return pointsText;

    }

    public boolean clickViewMyAccount(HeaderMenuActions headerMenuActions, MyAccountPageActions myAccountPageActions) {
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

    public boolean clickLogoutLink(HeaderMenuActions headerMenuActions) {
        click(logOutLnk);
        staticWait(10000);
        return waitUntilElementDisplayed(headerMenuActions.createAccountLink);
    }

    public boolean mprButton(MyAccountPageActions myAccountPageActions) {
        click(rewardsButton);
        return waitUntilElementDisplayed(myAccountPageActions.myAccountLink);
    }

    public boolean linkDisplayed() {
        if (waitUntilElementDisplayed(fName) &&
                waitUntilElementDisplayed(pointsAndRewards))
            return true;
        else
            return false;
    }

    public boolean closeOverlay(HomePageActions homePageActions) {
        click(closeSymbol);
        return waitUntilElementDisplayed(homePageActions.hiUserName, 5);
    }

    public boolean applyRewardsAndCoupons() {
        waitUntilElementsAreDisplayed(applyRewards, 10);

        int applyRewardSize = applyRewards.size();

        if (applyRewardSize >= 0) {
            WebElement selectCoupon = applyRewards.get(randInt(0, applyRewards.size() - 1));
            click(selectCoupon);
        }

        return waitUntilElementDisplayed(removeBtn, 10);
    }

    public boolean removeAppliedCoupon() {
        waitUntilElementDisplayed(removeBtn, 10);
        click(removeBtn);
        return verifyElementNotDisplayed(removeBtn, 10);
    }

    public boolean applyPercentageCoupons() {
        waitUntilElementDisplayed(showAllLink, 3);
        click(showAllLink);
        scrollUpToElement(showLess);
        waitUntilElementDisplayed(coupon_Auto10Off, 10);
        click(coupon_Auto10Off);
        return waitUntilElementDisplayed(removeBtn, 10);
    }

    public boolean applyMPRCoupons() {
        waitUntilElementDisplayed(showAllLink, 3);
        click(showAllLink);
        if (isDisplayed(loyalty_Coupon.get(0))) {
            waitUntilElementsAreDisplayed(coupon_MPR$5, 10);
            click(coupon_MPR$5.get(0));
            if (waitUntilElementDisplayed(successMessage, 3)) {
                return true;
            } else
                return waitUntilElementDisplayed(couponErrorMessage, 3);
        } else {
            addStepDescription("Loyalty coupons are not present in the test account");
            return false;
        }
    }

    public boolean mprCOuponDisplayCheck() {
        waitUntilElementDisplayed(showAllLink, 3);
        click(showAllLink);
        if (isDisplayed(coupon_MPR$5.get(0))) {
            addStepDescription("Loyalty coupon is displayed");
            return false;
        } else {
            addStepDescription("Loyalty coupon is not displayed");
            return true;
        }
    }

    public boolean mprCouponErrCheck(String errMag) {
        waitUntilElementDisplayed(coupon_MPR$5.get(0), 3);
        if (isDisplayed(coupon_MPR$5.get(0))) {
            click(coupon_MPR$5.get(0));
            waitUntilElementDisplayed(coupon_MPR$5.get(0));
            addStepDescription("Loyalty coupon is displayed");
            return false;
        } else {
            addStepDescription("Loyalty coupon is not displayed");
            return true;
        }
    }

    public boolean mprCouponValue() {
        waitUntilElementsAreDisplayed(loyalty_Coupon, 3);
        click(showAllLink);
        int count = (loyalty_Coupon.size());
        int sum = 0;
        for (int i = 0; i < count; i++) {

            int value = Integer.parseInt(getText(loyalty_Coupon.get(i)).replaceAll("LOYALTY $", "").substring(9, 10));
            sum = sum + value;
            int headerValue = Integer.parseInt(getText(rewardsHeader).replaceAll("0 points, $", ""));
            if (headerValue == sum) {
                if(value%5==0);
                return true;
            }

            else {
                addStepDescription("Available rewards and sum of rewards coupon are different");
                return false;
            }

    } return false;
    }
    public boolean checkCouponDisplay(){
        waitUntilElementDisplayed(showAllLink,2);
        if(isDisplayed(couponList)){
            return true;
        }
        else{
            addStepDescription("coupons are not getting displayed in My Account after login");
            return false;
        }
    }
}

