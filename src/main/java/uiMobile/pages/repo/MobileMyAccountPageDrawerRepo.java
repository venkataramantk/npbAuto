package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.pages.repo.HeaderMenuRepo;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileMyAccountPageDrawerRepo extends HeaderMenuRepo {
    @FindBy(css = ".welcome-message>.active")
    public WebElement welcomeText;

    @FindBy(css = ".my-account-options a")
    public WebElement viewMyAccountLink;

    @FindBy(css = ".remembered-logout>.button-logout")
    public WebElement logOutLnk;

    @FindBy(css = ".rewards-status>.title")
    public WebElement pointsToNextRewards;

    @FindBy(css = ".rewards-status>.rewards-bar")
    public WebElement rewardsBar;

    @FindBy(css = ".rewards-status>.button-reward")
    public WebElement rewardsButton;

    @FindBy(css = ".rewards-points p:nth-child(1)")
    public WebElement currentPointText;

    @FindBy(css = ".rewards-points p:nth-child(2)")
    public WebElement myRewardsText;

    @FindBy(css = ".rewards-points p:nth-child(1) span")
    public WebElement currentPoints;

    @FindBy(css = ".rewards-points p:nth-child(2) span")
    public WebElement myRewards;

    @FindBy(xpath = "//div[@class='available-rewards']/div/h3[contains(text(),'My Rewards and Offers')]")
    public WebElement myRewardsAndOffers;

    @FindBy(xpath = ".//button[@class='active']/span")
    public WebElement pointsAndRewards;

    @FindBy(xpath = ".//button[@class='active']/strong")
    public WebElement fName;

    @FindBy(css = ".overlay-header .button-overlay-close")
    public WebElement closeSymbol;

    @FindBy(css = ".rewards-bar .number-progressbar")
    public List<WebElement> progressbarNumber;

    @FindBy(css = ".reward-details-container")
    public WebElement rewardsContainer;

    @FindBy(css = ".order-status")
    public WebElement orderStatus;

    @FindBy(css = ".order-status a")
    public WebElement orderNo;

    @FindBy(css = ".order-status a:nth-child(1)")
    public WebElement trackingNo;

}
