package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.HomePageRepo;

import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class HomePageActions extends HomePageRepo {
    WebDriver driver;
    public static String deptName;
    Logger logger = Logger.getLogger(HomePageActions.class);

    public HomePageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickRandomDepartment(DepartmentLandingPageActions departmentLandingPageActions) {
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
             //   waitUntilElementDisplayed(link_Shoes, 2) &&
                waitUntilElementDisplayed(link_Accessories, 2)) //&&
            //    waitUntilElementDisplayed(link_Clearance, 1))
            return true;
        else
            return false;
    }


    public boolean isOnlineProducts() throws InterruptedException {
        boolean foundFlag = false;
        String xpath = "//li[@class='long special' or @class='short'][contains(.,'Online Only')]/a //parent::* //div[@class='nav-content-left'] //a[contains(.,'Online Only')]";
        int size = driver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = driver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(driver, MenuLink);

            List<WebElement> listOfOnlineOnly = driver.findElements(By.xpath(".//*[contains(@class,'product productLarge')]"));
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

        int size = driver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = driver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(driver, MenuLink);

            List<WebElement> listOfNewArrivals = driver.findElements(By.xpath(".//*[@class='product productLarge'] //img[@title='LIMITEDINVENTORY' or @title='INVENTARIO LIMITADO' and not(contains(@src,'noimage'))]/parent::*/parent::*/div[@class='quick-view']"));
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
        int size = driver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = driver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(driver, MenuLink);

            List<WebElement> listofNewArrivals = driver.findElements(By.xpath(".//*[contains(@class,'product productLarge')] //div[@class='new']"));
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
        int size = driver.findElements(By.xpath(xpath)).size();

        for (int i = 1; i <= size; i++) {
            WebElement MenuLink = driver.findElement(By.xpath("(" + xpath + ")[" + i + "]"));
            javaScriptClick(driver, MenuLink);

            List<WebElement> listofNewArrivals = driver.findElements(By.xpath(".//*[contains(@class,'product productLarge')] //img[@title='Extended Sizes' or @title='Talles especiales']/parent::*/parent::*/div[@class='quick-view']"));
            if (listofNewArrivals.size() > 0) {
                foundFlag = true;
                return foundFlag;
            }
        }

        return foundFlag;
    }

    public boolean selectDeptWithName(DepartmentLandingPageActions departmentLandingPageActions, String dept) {
        waitUntilElementDisplayed(deptLinkWithName(dept), 5);
        staticWait(4000);
        click(deptLinkWithName(dept));
        staticWait(5000);
        return waitUntilElementDisplayed(departmentLandingPageActions.subCatLeftNavBlock);
    }

    public boolean selectOtherDeptWithName(DepartmentLandingPageActions departmentLandingPageActions, String dept) {
        waitUntilElementDisplayed(otherDeptLinkWithName(dept), 5);
        click(otherDeptLinkWithName(dept));
        return waitUntilElementDisplayed(departmentLandingPageActions.subCatLeftNavBlock, 5);
    }


    public boolean clickProdImage(ProductDetailsPageActions productDetailsPageActions) {
        if (prodRecommendation.size() > 1) {
            click(prodRecommendation.get(2));
            staticWait(3000);
            return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 10);
        }
        return false;
    }

    public boolean selectRandomSizeAndAddToBag(HeaderMenuActions headerMenuActions) {
        if (availableColors.size() >= 1) {
            click(availableColors.get(randInt(0, (availableColors.size() - 1))));
            staticWait(2000);
        }
        if (availableFits.size() >= 1) {
            click(availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(2000);
        }
        if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(2000);
        }

        String getBagCountBefore = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        scrollDownToElement(addToBagRecom);
        click(addToBagRecom);
        staticWait(2000);
        String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
        boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
        return isProductAddedToBag;
    }

    public boolean validateEspotInHomePage() {
        driver.navigate().refresh();
        if (waitUntilElementDisplayed(headerBanner_Espot, 10) &&
                waitUntilElementDisplayed(mainImage_Espot, 10) &&
                scrollDownToElement(secondImg_Espot))
            return true;
        else
            return false;

    }

    public boolean howerOverDeptWithName(DepartmentLandingPageActions departmentLandingPageActions, String dept) {
        //mouseHover(departmentLandingPageActions.clearanceDep_Lnk);
        waitUntilElementDisplayed(deptLinkWithName(dept), 5);
        click(deptLinkWithName(dept));
        return waitUntilElementDisplayed(departmentLandingPageActions.subCatLeftNavBlock);
    }

    public boolean clickRecommendationProdImg(ProductDetailsPageActions productDetailsPageActions) {
        waitUntilElementsAreDisplayed(prodWithPrice, 10);

        if (prodWithPrice.size() >= 0) {
            click(prodWithPrice.get(randInt(0, prodWithPrice.size() - 1)));
            staticWait(3000);
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 10);
    }


    public boolean selectRandomSizeAndAddToBagPDP(HeaderMenuActions headerMenuActions) {
        staticWait(2000);

        if (availableColors.size() >= 1) {
            click(availableColors.get(randInt(0, (availableColors.size() - 1))));
            staticWait(9000);
        }
        if (availableFits.size() >= 1) {
            click(availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(5000);
        }
        if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }

        String getBagCountBefore = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        staticWait(4000);
        scrollDownToElement(addToBagPDPRecom);
        click(addToBagPDPRecom);
        staticWait(6000);
        String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
        staticWait(9000);
        boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
        return isProductAddedToBag;
    }
    public boolean validateHrefLangTag() {
        boolean href = false;
        for (WebElement tag : hrefLangTags) {
            href = getAttributeValue(tag, "href").contains("www") && hrefLangTags.size() == 4;
        }
        return href;
    }
}