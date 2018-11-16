package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.OverlayHeaderRepo;

/**
 * Created by skonda on 3/2/2017.
 */
public class OverlayHeaderActions extends OverlayHeaderRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(OverlayHeaderActions.class);

    public OverlayHeaderActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickCreateAccountOnOverlayHeader(CreateAccountActions createAccountActions){

        click(createAccountLink);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton,30) && waitUntilElementDisplayed(createAccountDrawer);
    }

    public boolean clickLoginOnOverlayHeader(){
        click(loginLink);
        return waitUntilElementDisplayed(loginLink,30) && waitUntilElementDisplayed(loginDrawer);
    }

    public boolean clickWishListOnOverlayHeader(){
        waitUntilElementDisplayed(wishListLink,10);
        click(wishListLink);
        return waitUntilElementDisplayed(loginLink,30);
    }

    public boolean closeOverlayDrawer(HeaderMenuActions headerMenuActions){
        if(waitUntilElementDisplayed(closeOnDrawer,10)) {
            click(closeOnDrawer);
        }
        return waitUntilElementDisplayed(headerMenuActions.searchBox,30);
    }

    public String getQtyFromMiniCart(){
        return getText(miniCartIcon);
    }

    public boolean clickRemoveLinkByPosition(int position) {
        staticWait(5000);
        WebElement removeElement = removeLinkByItemPosition(position);
        click(removeElement);
//        staticWait(5000);
        return verifyElementNotDisplayed(removeElement, 20);

    }


}
