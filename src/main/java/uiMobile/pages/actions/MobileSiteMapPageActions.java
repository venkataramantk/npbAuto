package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileSiteMapPageRepo;

import java.util.List;


/**
 * Created by Venkat on 8/22/2016.
 */
public class MobileSiteMapPageActions extends MobileSiteMapPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileSiteMapPageActions.class);

    public MobileSiteMapPageActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean verifyKidsLabel(){
        if(isDisplayed(lbl_kids)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyToddlerLabel(){
        if(isDisplayed(lbl_toddler)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifybabyLabel(){
        if(isDisplayed(lbl_baby)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyPlaceShopLabel(){
        if(isDisplayed(lbl_PlaceShop)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyClearanceLabel(){
        if(isDisplayed(lbl_Clearance)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyMoreLabel(){
        if(isDisplayed(lbl_More)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyHelpLink(){
        if(isDisplayed(lnk_Help)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyShoppingLink(){
        if(isDisplayed(lnk_Shopping)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyAboutUsLink(){
        if(isDisplayed(lnk_AboutUs)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyMPRLink(){
        if(isDisplayed(lnk_mPR)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean verifyPlaceCardLink(){
        if(isDisplayed(lnk_PlaceCardUS)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validateLabels(){
        if(verifyKidsLabel()&& verifyToddlerLabel() && verifybabyLabel() && verifyPlaceShopLabel()
                && verifyClearanceLabel()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validateFooterLinksUS(){
        if(verifyMoreLabel()&& verifyHelpLink() && verifyShoppingLink() && verifyAboutUsLink()
                && verifyMPRLink() && verifyPlaceCardLink()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validateFooterLinksCA(){
        if(verifyMoreLabel()&& verifyHelpLink() && verifyShoppingLink() && verifyAboutUsLink()
                /*&& verifyMPRLink()*/){
            return true;
        }
        else{
            return false;
        }
    }

    static WebElement StoredSeeLess = null;
    static List<WebElement> links;

    public void SubHeaderWithShowMore() throws InterruptedException{
        List<WebElement> list = arrow_RowSubHeadings;
        List<WebElement> listName = arrow_RowSubHeadingsName;

        int count = list.size();
        int randomCount = randInt(0, count);
        String sublinkName="";

        list.get(randomCount).click();

        links =listName.get(randomCount).findElements(By.tagName("a"));
        if (links.size()>5)
        {
            for (WebElement eachlink : links)
            {
                sublinkName=eachlink.getText().toLowerCase().trim();
                if (sublinkName.equalsIgnoreCase("see more")||sublinkName.equalsIgnoreCase("voir plus")||sublinkName.equalsIgnoreCase("ver más"))
                {
                    eachlink.click();
                    StoredSeeLess = eachlink;
                    staticWait(10);
                    break;
                }
            }
        }
    }

    public void SubHeaderWithShowLess() throws InterruptedException{
        StoredSeeLess.click();
    }

    public void seeMore_SeeLess() throws InterruptedException {
        SubHeaderWithShowMore();
        staticWait(10);
        SubHeaderWithShowLess();
        staticWait(10);
    }

}
