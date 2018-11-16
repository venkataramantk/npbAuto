package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.MyPlaceRewardsRepo;

/**
 * Created by skonda on 11/7/2016.
 */
public class MyPlaceRewardsActions extends MyPlaceRewardsRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(MyPlaceRewardsActions.class);

    public MyPlaceRewardsActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }
}
