package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.LocateAStoreRepo;

/**
 * Created by skonda on 11/7/2016.
 */
public class LocateAStoreActions extends LocateAStoreRepo{
    WebDriver driver = null;
    Logger logger = Logger.getLogger(LocateAStoreActions.class);


    public LocateAStoreActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }
}
