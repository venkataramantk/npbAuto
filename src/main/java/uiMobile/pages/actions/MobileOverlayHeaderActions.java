package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileOverlayHeaderRepo;

/**
 * Created by skonda on 3/2/2017.
 */
public class MobileOverlayHeaderActions extends MobileOverlayHeaderRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileOverlayHeaderActions.class);

    public MobileOverlayHeaderActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean clickCreateAccountOnOverlayHeader(MCreateAccountActions createAccountActions){

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

    public boolean closeOverlayDrawer(MobileHeaderMenuActions headerMenuActions){
        waitUntilElementDisplayed(closeOnDrawer,10);
        click(closeOnDrawer);
        return waitUntilElementDisplayed(headerMenuActions.searchBox,30);
    }

    public String getQtyFromMiniCart(){
        return getText(miniCartIcon);
    }

    public boolean clickRemoveLinkByPosition(int position) {
        staticWait(5000);
        WebElement removeElement = removeLinkByItemPosition(position);
        click(removeElement);
        staticWait(5000);
        return verifyElementNotDisplayed(removeElement, 20);

    }


}
