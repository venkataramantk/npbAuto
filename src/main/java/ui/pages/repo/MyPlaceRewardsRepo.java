package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by skonda on 11/7/2016.
 */
public class MyPlaceRewardsRepo extends HeaderMenuRepo {

    @FindBy(css=".mpr-landing-2016")
    public WebElement mprLandingLogo;
}
