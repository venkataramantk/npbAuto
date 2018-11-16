package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileDepartmentLandingPageRepo;

/**
 * Created by user on 6/16/2016.
 */
public class MobileDepartmentLandingPageActions extends MobileDepartmentLandingPageRepo {

    WebDriver
            mobileDriver;
    public static String catName;
    Logger logger = Logger.getLogger(MobileDepartmentLandingPageActions.class);

    public MobileDepartmentLandingPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean selectRandomCategory(MCategoryDetailsPageAction categoryDetailsPageAction) {
        staticWait(5000);
        int totalCount = list_Categories.size();
        if (totalCount > 1) {
            WebElement catList = list_Categories.get(randInt(0, (list_Categories.size() - 1)));
            catName = catList.getText();
            logger.info("Category Name: " + catName);
            click(catList);
        } else if (list_Departments.size() == 1) {
            WebElement catList = list_Categories.get(0);
            catName = catList.getText();
            logger.info("Category Name: " + catName);
            click(catList);
        }

        if (getDriver().findElements(By.cssSelector(".product.productLarge:nth-child(2) .productImage a img")).size() == 0) {
            return true;
        } else if (getDriver().findElements(By.cssSelector(".product.productLarge:nth-child(1) .productImage a img")).size() == 0) {
            return true;
        } else if (getDriver().findElements(By.cssSelector(".fit-banner-desktop")).size() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validateCatPageURL(String url) {
        String catPageURL = getCurrentURL();
        if (catPageURL.contains(url)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validateDeptPageByName(String name) {
        boolean DeptName = getText(deptName).contains(name);
        return DeptName;
    }


    //This is invalid validation, so removing from the scripts
    public boolean validateLinksCount(String dept) {
        mouseHover(deptLinkWithName(dept));
        staticWait(5000);
        int getListSize = subCategoryLink(dept).size();
        int getLeftNavLinksSize = leftNavCategoriesList.size();

        boolean verifySize = (getListSize == getLeftNavLinksSize);
        return verifySize;
    }
}
