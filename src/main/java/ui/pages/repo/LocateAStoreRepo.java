package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by skonda on 11/7/2016.
 */
public class LocateAStoreRepo extends HeaderMenuRepo {

    @FindBy(css="#storeLocatorFields>h1")
    public WebElement locateAStoreHeader;
}
