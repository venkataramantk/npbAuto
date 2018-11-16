package uiMobile.pages.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MRecommendationsRepo;

public class MRecommendationsActions extends MRecommendationsRepo {
    WebDriver driver;

    public MRecommendationsActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * verify if Recommended Sections is displayed
     */
    public boolean isRecommendedSectionsDisplayed() {
        waitUntilElementDisplayed(recommendationsHeading);
        click(recommendationsHeading);
        return waitUntilElementDisplayed(recommendationsHeading);
    }

    /**
     * Verify Next btn , Previous btn, fav icon and add to bag btn
     * for all Recommendations
     *
     * @return true if all the icons are displayed
     */
    public boolean verifyAllicons() {
        return waitUntilElementDisplayed(btnNext) &&
                waitUntilElementDisplayed(btnPrevious) &&
                waitUntilElementDisplayed(favIcon) &&
                waitUntilElementDisplayed(addToBagBtn);
    }

    /**
     * scroll to the recommendation product with price tag available
     */
    public void gotoProductWithPrice() {
        int products = dots.size();
        for (int i = 1; i <= products; i++) {
            if (!getAvailableProduct(i).getText().contains("$")) {
                click(btnNext);
            } else {
                break;
            }
        }
    }

    public void clickNextBtn() {
        javaScriptClick(driver, btnNext);
    }

    /**
     * Click product and verify landed on PDP page
     * @param mProductDetailsPageActions
     * @return
     */
    public boolean clickProduct(MProductDetailsPageActions mProductDetailsPageActions) {
        click(productImg);
        return waitUntilElementDisplayed(mProductDetailsPageActions.addToBag, 10);
    }
    
    public boolean verifyLoginfromRecommendation(MLoginPageActions mloginPageActions, PanCakePageActions panCakePageActions, String email, String pwd) {
        verifyAllicons();
        click(favIcon);
        waitUntilElementDisplayed(mloginPageActions.loginButton);
        mloginPageActions.loginAsRegisteredUserFromLoginForm( email,  pwd);
        
        return waitUntilElementDisplayed(panCakePageActions.salutationLinkOnLogin, 60);
        
        
    }
}
