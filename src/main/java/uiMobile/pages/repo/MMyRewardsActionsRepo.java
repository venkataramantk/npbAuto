package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 23/10/2017.
 */
public class MMyRewardsActionsRepo extends UiBaseMobile {

    @FindBy(css = "img[alt='HoverBanner-GlobalHeader-MPR_Mobile']")
    public WebElement myPlaceRewardsBanner;

    @FindBy(css = ".button-menu")
    public WebElement closeIcon;

    @FindBy(css = "a[href= '/us/home/login']")
    public WebElement loginLink;

    @FindBy(css = "a[href= '/us/home/register']")
    public WebElement createAccountLink;

    @FindBy(css = "a[href= '/us/content/myplace-rewards-page']")
    public WebElement learnMoreLink;

    @FindBy(css = ".button-apply.button-primary")
    public WebElement applyButton;

}
