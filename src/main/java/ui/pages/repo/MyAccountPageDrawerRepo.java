package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by AbdulazeezM on 2/24/2017.
 */
public class MyAccountPageDrawerRepo extends UiBase

{
    @FindBy(css=".overlay-header-desktop .welcome-message")
    public WebElement welcomeLink;

    @FindBy(css=".my-account-options a")
    public WebElement viewMyAccountLink;

    @FindBy(css=".remembered-logout>.button-logout")
    public WebElement logOutLnk;

    @FindBy(css=".rewards-status>.title")
    public WebElement pointsToNextRewards;

    @FindBy(css=".rewards-status>.rewards-bar")
    public WebElement rewardsBar;

    @FindBy(css=".rewards-status>.button-reward")
    public WebElement rewardsButton;

    @FindBy(xpath =".//div[@class=\"rewards-points\"]/p")
    public WebElement currentPointText;

    @FindBy(xpath="//div[@class='available-rewards']/div/h3[contains(text(),'My Rewards and Offers')]")
    public WebElement myRewardsAndOffers;

    @FindBy(xpath = ".//button[@class='active']/span")
    public WebElement pointsAndRewards;

    @FindBy(xpath = ".//button[@class='active']/strong")
    public WebElement fName;

//    @FindBy(css = ".overlay-header .button-overlay-close")
    @FindBy(css=".overlay-header-desktop .button-overlay-close")
    public WebElement closeSymbol;

    public WebElement applyRewards(int i) {
        // return getDriver().findElement(By.xpath(".//*[@id='choice-Color']//*[contains(@id,'Color_"+i+"')]"));// uatlive2
        return getDriver().findElement(By.xpath("(.//button[@class='apply-coupons-button'])[" + i + "]"));

    }

   // @FindBy(xpath=".//button[@class='apply-coupons-button']")
    @FindBy(xpath = ".//div[@class='my-account-content-overlay']//button[@class='apply-coupons-button']")
    public List<WebElement> applyRewards;

    //@FindBy(xpath = ".//button[@class='apply-coupons-button'][text()='Remove']")
    @FindBy(xpath = ".//div[@class='my-account-content-overlay']//button[@class='apply-coupons-button'][text()='Remove']")
    public WebElement removeBtn;

  /*  public WebElement coupon_10Off(String text){
        return getDriver().findElement(By.xpath("(.//div[@class=\'coupon-list-container\']//li[@class=\'coupon\'][contains(.,"+text+")]//button[@class=\'apply-coupons-button\'])[1]"));
    }*/

  //@FindBy(xpath = ".//div[@class=\"coupon-list-container\"]//li[contains(.,'10% OFF')]//button[@class=\"apply-coupons-button\"]")
    @FindBy(xpath = ".//div[@class=\"coupon-list-container\"]//li[contains(.,\"10% Off\")]//button[@class=\"apply-coupons-button\"]")
    public WebElement coupon_10Off;

    @FindBy(xpath = ".//li[contains(.,\"AUTOMATION 10%\")]//button[@class=\"apply-coupons-button\"]")
    public WebElement coupon_Auto10Off;

  @FindBy(css = ".button-view-all")
  public WebElement showLess;

    @FindBy(xpath = ".//div[@class='coupon-list-container']//li[@class='coupon'][contains(.,\"$5\")]//button[@class='apply-coupons-button']")
    public List<WebElement> coupon_$5MPROff;

    @FindBy(css = ".button-view-all")
    public WebElement showAllLink;

    //@FindBy(xpath = ".//div[@class=\"coupon-list-container\"]//li[@class=\"coupon\"][contains(.,'$5')]//button[@class=\"apply-coupons-button\"]")
    @FindBy(xpath = ".//div[@class=\"coupon-list-container\"]//li[@class=\"coupon\"][contains(.,'LOYALTY $5')]//button[@class=\"apply-coupons-button\"]")
    public List<WebElement> coupon_MPR$5;

    @FindBy(css =".success-box" )
    public WebElement successMessage;

    @FindBy(css = "error-box")
    public WebElement couponErrorMessage;

   // @FindBy(xpath = ".//li[@class=\"coupon expiration-limit\"]//strong[contains(.,\"LOYALTY\")]")
    @FindBy(xpath = ".//*[@class=\"information-coupon\"]//strong[contains(.,\"LOYALTY\")]")
    public List<WebElement> loyalty_Coupon;

    @FindBy(xpath = ".//header[@class=\"overlay-header-desktop\"]//span[contains(.,\"Rewards)\")]")
    public WebElement rewardsHeader;

    @FindBy(css = ".coupon-list-container")
    public WebElement couponList;

    }


