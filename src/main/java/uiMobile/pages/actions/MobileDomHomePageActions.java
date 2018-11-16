package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.support.EnvironmentConfig;
import uiMobile.pages.repo.MobileDomHomePageRepo;

import java.awt.*;

/**
 * Created by skonda on 5/26/2016.
 */
public class MobileDomHomePageActions extends MobileDomHomePageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileDomHomePageActions.class);

    public MobileDomHomePageActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }


    public void doDomRopisLoginByGivenCredentials(String userName, String password){
        if(waitUntilElementDisplayed(menuBtn,5))
        {

        }
        else if(waitUntilElementDisplayed(userNameFld)) {
            try {
                fillText(userNameFld, userName);
                fillText(passwordFld, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            staticWait();
            click(loginBtn);
            waitUntilElementDisplayed(menuBtn, 30);
        }


    }

    public void doDomLoginByGivenCredentials(String userName, String password){
        if(waitUntilElementDisplayed(menuBtn,5))
        {

        }
        else if(waitUntilElementDisplayed(clickHereLinkToLogin, 5)){
            click(clickHereLinkToLogin);
            waitUntilElementDisplayed(userNameFld);
            try {
                fillText(userNameFld, userName);
                fillText(passwordFld, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            staticWait();
            click(loginBtn);
            waitUntilElementDisplayed(menuBtn, 30);
        }
        else if(waitUntilElementDisplayed(userNameFld)) {
            try {
                fillText(userNameFld, userName);
                fillText(passwordFld, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            staticWait();
            click(loginBtn);
            waitUntilElementDisplayed(menuBtn, 30);
        }


    }


    public void searchFromMenu(String searchText, WebElement element){
        waitUntilElementDisplayed(menuBtn);
        staticWait(4000);
        click(menuBtn);
        waitUntilElementDisplayed(menuSearchBox, 3);
//        click(dropDown);

        staticWait(2000);
        clearAndFillText(menuSearchBox, searchText);
        menuSearchBox.sendKeys(Keys.RETURN);
        waitUntilElementDisplayed(element, 30);
        switchToFrame(0);
    }
    public void closeFrame() {
        switchToDefaultFrame();
        jqueryClick(".x-tool-close");

    }
    public boolean selectAStore(){
        if(waitUntilElementDisplayed(selectAStore)){
            waitUntilElementDisplayed(storeCode1Row);
            click(storeCode1Row);
            waitUntilElementDisplayed(selectAStoreOKButton);
            for(int i =1; i<=buttonsList.size(); i++)
                if(getText(buttonsList.get(i)).equalsIgnoreCase("OK")) {
                    String idValue = getAttributeValue(buttonsList.get(i),"id");
                    clickJavaScriptExecute(idValue);
                    break;
                }
        }
        return waitUntilElementDisplayed(menuBtn);

    }
    public void clickStoreIDWindowDown() throws AWTException {
        String scrollDownId = getAttributeValue(storeModalArrow, "id");
        JavascriptExecutor js = (JavascriptExecutor)mobileDriver;
        js.executeScript("document.getElementById('"+scrollDownId+"').scrollTop += 250", storeModalArrow);
    }

    public boolean selectAStoreByStoreID(String storeID) throws AWTException {
        if(waitUntilElementDisplayed(selectAStore)) {
            staticWait(20000);
            boolean isStoreIDVisible = false;
            int count = 0;
            if (storeID == null) {
                throw new IllegalStateException("The store ID is showing: " + storeID);
            }
            do {
                try {
                    isStoreIDVisible = waitUntilElementDisplayed(storeCode1Row(storeID), 1);
                } catch (Throwable t) {
                    isStoreIDVisible = false;
                }
                if (!isStoreIDVisible) {
                    clickStoreIDWindowDown();
                } else {
                    break;
                }
                try {
                    isStoreIDVisible = waitUntilElementDisplayed(storeCode1Row(storeID), 1);
                } catch (Throwable t) {
                    isStoreIDVisible = false;
                }
                count++;
            } while (!isStoreIDVisible || count <= 300);
            if (isStoreIDVisible) {
                click(storeCode1Row(storeID));
                waitUntilElementDisplayed(selectAStoreOKButton);
                for (int i = 1; i <= buttonsList.size(); i++)
                    if (getText(buttonsList.get(i)).equalsIgnoreCase("OK")) {
                        String idValue = getAttributeValue(buttonsList.get(i), "id");
                        clickJavaScriptExecute(idValue);
                        break;
                    }
            }
            else{
                throw new IllegalStateException("The Store ID is not visible ");
            }
        }
        return waitUntilElementDisplayed(menuBtn);

    }

    public boolean searchOrderFromRETTile(String orderNumber){
        mouseHover(tileRET);
        staticWait();
        waitUntilElementDisplayed(orderNumFldForRET, 30);
        clearAndFillText(orderNumFldForRET,orderNumber);
        staticWait();
        click(orderSearchButtonOnRET);
        return waitUntilElementDisplayed(ordNumResultOnSearch);
    }

    public boolean clickOrderNumOnSearchResultsOnRET(){
        click(ordNumResultOnSearch);
        return waitUntilElementDisplayed(createReturnWindow);
    }

    public boolean clickOrderNumOnSearchResultsOnORD(){
        click(ordNumResultOnSearch);
        waitUntilElementDisplayed(customerTransactionsWindow);
        return waitUntilElementDisplayed(ordStatusAtCustomerTrans);
    }

    public boolean searchOrderFromORDTile(String orderNumber){
        mouseHover(tileORD);
        staticWait();
        waitUntilElementDisplayed(orderNumFldForORD, 30);
        staticWait();
        clearAndFillText(orderNumFldForORD,orderNumber);
        click(orderSearchButtonOnORD);
        waitUntilElementDisplayed(ordNumResultOnSearch);
        return clickOrderNumOnSearchResultsOnORD();
    }

    public void clickCloseOpenWindows(){
        mouseHover(openWindowsIcon);
        waitUntilElementDisplayed(closeLinkOnOpenWindow);
        click(closeLinkOnOpenWindow);
        staticWait(5000);
    }

    public void acceptDOMRefreshPageAlert()
    {
        waitAndAcceptAlert(20);
    }

    public boolean isStoreIDDisplayOnHeader(String storeID){
        return waitUntilElementDisplayed(storeIDOn_DOMHeader(storeID));
    }
}
