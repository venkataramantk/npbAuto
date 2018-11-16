package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.DepartmentLandingPageRepo;

/**
 * Created by user on 6/16/2016.
 */
public class DepartmentLandingPageActions extends DepartmentLandingPageRepo {

    WebDriver driver;
    public static String catName;
    Logger logger = Logger.getLogger(DepartmentLandingPageActions.class);

    public DepartmentLandingPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
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

    /**
     * Validates espots in Department page
     *
     * @return true if espots present
     */
    public boolean validateEspots() {
        if (waitUntilElementDisplayed(eSpots)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validatesubCategories() {
        if (waitUntilElementDisplayed(subCatLeftNavBlock)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean mprLinkDisplay() {
        if (getCurrentURL().contains("us")) {
            waitUntilElementDisplayed(mprLink, 3);
            return (isDisplayed(mprLink) && isDisplayed(globalEspot));
        } else
            return waitUntilElementDisplayed(globalEspot);
    }

}
