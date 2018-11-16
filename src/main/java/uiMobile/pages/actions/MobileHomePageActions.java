package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileHomePageRepo;

import java.util.List;

/**
 * Created by JKotha on 10/10/2017.
 */
public class MobileHomePageActions extends MobileHomePageRepo {
    WebDriver mobileDriver;
    public static String deptName;
    Logger logger = Logger.getLogger(MobileHomePageActions.class);
    public static int headerHeight;

    public MobileHomePageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean clickRandomDepartment(MobileDepartmentLandingPageActions departmentLandingPageActions) {
        int totalCount = list_Departments.size();
        if (totalCount > 1) {
            WebElement department_List = list_Departments.get(randInt(0, (list_Departments.size() - 1)));
            deptName = department_List.getText();
            department_List.click();
        } else if (list_Departments.size() == 1) {
            list_Departments.get(0).click();
        }

        if (getDriver().findElements(By.cssSelector(".leftNav.block")).size() == 0) {
            return true;
        } else if (getDriver().findElements(By.cssSelector(".clearance-banner-full-width")).size() == 0) {
            return true;
        } else return false;

    }

    public boolean clickOnCategoryByName(String catName, MCategoryDetailsPageAction categoryDetailsPageAction) {
        int totalCount = list_Departments.size();
        boolean isCatLanded = false;
        for (int i = 1; i <= totalCount; i++) {
            if (getText(categoryByNamePosition(catName, i)).equalsIgnoreCase(catName)) {
                click(categoryByNamePosition(catName, i));
                isCatLanded = waitForTextToAppear(categoryDetailsPageAction.deptNameTitle, catName, 30);
                break;
            }
        }
        return isCatLanded;

    }

   /* public boolean clickOnClearanceDept(MCategoryDetailsPageAction categoryDetailsPageAction) {
        click(clearanceLink);
        return waitUntilElementDisplayed(categoryDetailsPageAction.leftNavigation, 30);
    }*/

    public boolean validateHomeURL(String url) {
        String homeURL = getCurrentURL();
        if (homeURL.contains(url)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCA_HomePageDisplaying() {
        return waitUntilElementDisplayed(ca_HomePage);
    }

    public boolean isUS_HomePageDisplaying() {
        return waitUntilElementDisplayed(us_HomePage);
    }

    public boolean verificationAllDepartmentsDisplayed() {
        if (waitUntilElementDisplayed(link_Girls, 2) &&
                waitUntilElementDisplayed(link_ToddlerGirls, 2) &&
                waitUntilElementDisplayed(link_Boys, 2) &&
                waitUntilElementDisplayed(link_ToddlerBoys, 2) &&
                waitUntilElementDisplayed(link_Baby, 2) &&
                waitUntilElementDisplayed(link_Shoes, 2) &&
                waitUntilElementDisplayed(link_Accessories, 2))
            return true;
        else
            return false;
    }


    public boolean isOnlineProducts() throws InterruptedException {
        boolean foundFlag = false;
        String xpath = "//li[@class='long special' or @class='short'][contains(.,'Online Only')]/a //parent::* //div[@class='nav-content-left'] //a[contains(.,'Online Only')]";
        int size = mobileDriver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = mobileDriver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(mobileDriver, MenuLink);

            List<WebElement> listOfOnlineOnly = mobileDriver.findElements(By.xpath(".//*[contains(@class,'product productLarge')]"));
            if (listOfOnlineOnly.size() > 0) {
                foundFlag = true;
                return foundFlag;
            }
            staticWait(10);
        }

        return foundFlag;
    }


    public boolean isLimitedQuantityProducts() throws InterruptedException {
        boolean foundFlag = false;
        String xpath = "//li[@class='long special' or @class='short'][contains(.,'tops')]/a //parent::* //div[@class='nav-content-right'] //a[contains(.,'tops')]";
        //Checking if the limited quanitites is getting displayed using "tops" category

        int size = mobileDriver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = mobileDriver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(mobileDriver, MenuLink);

            List<WebElement> listOfNewArrivals = mobileDriver.findElements(By.xpath(".//*[@class='product productLarge'] //img[@title='LIMITEDINVENTORY' or @title='INVENTARIO LIMITADO' and not(contains(@src,'noimage'))]/parent::*/parent::*/div[@class='quick-view']"));
            if (listOfNewArrivals.size() > 0) {
                foundFlag = true;
                return foundFlag;
            }
        }

        return foundFlag;
    }

    public boolean isNewArrivalsProducts() throws InterruptedException {
        boolean foundFlag = false;
        String xpath = "//li[@class='long special' or @class='short'][contains(.,'New Arrivals')]/a //parent::* //div[@class='nav-content-left'] //a[contains(.,'New Arrivals')]";
        int size = mobileDriver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = mobileDriver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(mobileDriver, MenuLink);

            List<WebElement> listofNewArrivals = mobileDriver.findElements(By.xpath(".//*[contains(@class,'product productLarge')] //div[@class='new']"));
            if (listofNewArrivals.size() > 0) {
                foundFlag = true;
                return foundFlag;
            }
        }

        return foundFlag;
    }


    public boolean isExtendedSizesProducts() throws InterruptedException {
        boolean foundFlag = false;
        String xpath = "//li[@class='long special' or @class='short'][contains(.,'extended sizes')]/a //parent::* //div[@class='nav-content-right'] //a[contains(.,'extended sizes')]";
        int size = mobileDriver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = mobileDriver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(mobileDriver, MenuLink);

            List<WebElement> listofNewArrivals = mobileDriver.findElements(By.xpath(".//*[contains(@class,'product productLarge')] //img[@title='Extended Sizes' or @title='Talles especiales']/parent::*/parent::*/div[@class='quick-view']"));
            if (listofNewArrivals.size() > 0) {
                foundFlag = true;
                return foundFlag;
            }
        }

        return foundFlag;
    }

    /**
     * Close survey popup
     */
    public void closeSurveyPopup() {
        staticWait(10000);
        if (waitUntilElementDisplayed(noButton)) {
            click(noButton);
        }
        headerHeight = header.getSize().height;
    }

    public boolean selectDeptWithName(MobileDepartmentLandingPageActions departmentLandingPageActions, String dept) {
        waitUntilElementDisplayed(deptLinkWithName(dept), 5);
        click(deptLinkWithName(dept));
        return waitUntilElementDisplayed(departmentLandingPageActions.subCatLeftNavBlock);
    }

    public boolean selectOtherDeptWithName(MobileDepartmentLandingPageActions departmentLandingPageActions, String dept) {
        waitUntilElementDisplayed(otherDeptLinkWithName(dept), 5);
        click(otherDeptLinkWithName(dept));
        return waitUntilElementDisplayed(departmentLandingPageActions.subCatLeftNavBlock, 5);
    }

     public boolean addProdToFav_Reg(int i) {
        mouseHover(itemImg(i));
        waitUntilElementsAreDisplayed(addToBagIcon, 5);
        waitUntilElementsAreDisplayed(addToFavIcon, 5);
        if (addToFavIcon.size() > 1) {
            click(addToFavIcon.get(0));
            staticWait(3000);
            return waitUntilElementDisplayed(favIconEnabled.get(0));
        }
        return false;
    }

    public boolean clickProdImage(MProductDetailsPageActions productDetailsPageActions) {
        if (prodRecommendation.size() > 1) {
            click(prodRecommendation.get(2));
            staticWait(3000);
            return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 10);
        }
        return false;
    }

    public boolean selectRandomSizeAndAddToBag(MobileHeaderMenuActions headerMenuActions, int i) {
        mouseHover(itemImg(i));
        if (addToBagIcon.size() > 1) {
            click(addToBagIcon.get(1));
            staticWait(3000);
        }
        staticWait(2000);

        if (availableColors.size() >= 1) {
            click(availableColors.get(randInt(0, (availableColors.size() - 1))));
            staticWait(1000);
        }
        if (availableFits.size() >= 1) {
            click(availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(1000);
        }
        if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }

        String getBagCountBefore = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        staticWait(2000);
        click(addToBagBtn);
        staticWait(2000);
        String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
        staticWait(2000);
        boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
        return isProductAddedToBag;
    }

   /* public boolean validateEspotInHomePage() {
        mobileDriver.navigate().refresh();
        if (waitUntilElementDisplayed(headerBanner_Espot, 20) &&
                waitUntilElementDisplayed(mainImage_Espot, 20) &&
                scrollDownToElement(secondImg_Espot))
            return true;
        else
            return false;

    }*/

    public boolean validateSignUpFields() {
        return waitUntilElementDisplayed(signUpemail);
    }


    /**
     * Created By Pooja on 16th May,2018
     * This Method Clicks on Espot Displayed on Home Page
     */
    public void selectEspot(String espot, MobileHeaderMenuActions mobileHeaderMenuActions) {
        switch (espot) {
            case "Girl":
                click(girlsEspot);
                break;
            case "Toddler_Girl":
                click(toddlerGirlsEspot);
                break;
            case "Boy":
                click(boysEspot);
                break;
            case "Toddler_Boy":
                click(toddlerBoyEspot);
                break;
            case "Baby":
                click(babyEspot);
                break;
            default:
        }
    }


}