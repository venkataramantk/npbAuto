package uiMobile.pages.actions;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MSearchResultsPageRepo;

import java.util.ArrayList;
import java.util.List;

public class MSearchResultsPageActions extends MSearchResultsPageRepo {
    WebDriver mobileDriver;
    public int counter = 0;

    public MSearchResultsPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Selects particular item in the search results page
     *
     * @param i position of the item. I dex starts from 1
     * @return true if PDP page of item displayed
     */
    public boolean clickOnProductImageByPosition(int i) {
        MProductDetailsPageActions productDetailsPageActions = new MProductDetailsPageActions(mobileDriver);
        click_Selenium(productImageByPosition(i));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean selectBoysShirtsSuggestions() {
        javaScriptClick(mobileDriver, boysShirtsSuggestions);
        return waitUntilElementDisplayed(catPage);
    }

    /**
     * Created By Pooja
     * This Method clicks on Link displayed with Did You Mean text
     *
     * @return true if it navigates to PLP
     */
    public boolean clickDidYouMeanResultLink() {
        click(didYouMeanResutLink);
        return waitUntilElementDisplayed(addToBagIconByPos(1));
    }

    /**
     * Perform search
     *
     * @param keyword to search
     */
    public void performSearch(String keyword) {
//        click(searchIcon);
        clear(searchBox);
        click(searchSubmitButton);
        waitUntilElementDisplayed(catPage);
    }


    public void selectAnySize() throws InterruptedException {  //Added by Badhu

        try {
            List<WebElement> list = this.mobileDriver.findElements(By.xpath("//*[@id='choice-Size'] //li[not(@class='selected')] //a"));
            int totalCount = list.size();
            if (totalCount > 1 && counter == 0) {
                list.get(randInt(0, (list.size() - 1))).click();
            } else if (totalCount > 1) {
                list.get(counter - 1).click();
                System.out.println(list.get(counter - 1));
            } else if (totalCount == 1) {
                list.get(0).click();
            }

            //Adding a counter so it doesn't runs a test case for ever.//NCL_Shoes_Canada was running for ever as there were no products available for all sub categories
            if (mobileDriver.findElements(By.id("Size-clear")).size() == 0 && totalCount > counter) {
                mobileDriver.navigate().back();
                staticWait(10);
                counter = counter + 1;
                if (mobileDriver.findElements(By.xpath("//a[@id='Size-clear'][@style='display:block']")).size() == 0) {
                    click(link_SizeFacet);
                }
                selectAnySize();
            } else {
                verifySizeIsSelected();
            }
            if (totalCount == counter) {
                ATUReports.add("", "<font color=RED><B>0 All size and color combination have one produc in the Category page. Hence it failed </B></font>",
                        LogAs.WARNING, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            }
            counter = 0;
        } catch (Exception e) {
            e.printStackTrace();
            counter = 0;
        }

    }

    public void verifySizeIsSelected() throws InterruptedException {
        try {
            staticWait();
            clearSize().isDisplayed();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * <<<<<<< HEAD
     * Created By Pooja
     * This Method verifies the You Searched for Text
     *
     * @param searchString Search String
     */
    public boolean youSearchedForText(String searchString) {
        String actual = getText(youSearchedForText);
        String expected = "You searched for\n" + "\"" + searchString + "\"";
        return actual.equals(expected);
    }

    /**
     * Created By Pooja
     * This Method verifies the Noting matched your Seach for Text
     *
     * @param searchString Search String
     */
    public boolean nothingMatchedSrchText(String searchString) {
        String actual = getText(emptySearchPage);
        return actual.contains(searchString) && actual.contains("Nothing matched your search for");
    }

    /**
     * Created By Pooja
     * This Method get the count of Product Tiles present on the PLP/SRP
     * and verify the count of product tiles and the count of Tiles Containing searched Keyword is same
     */
    public boolean verifyGirlsShortsResult() {
        return productTitles.size() == productTitlesWithText("Girls").size() && productTitles.size() == productTitlesWithText("Shorts").size();
    }

    /**
     * Created By Pooja
     * This Method get the count of Product Tiles present on the PLP/SRP
     * and verify the count of product tiles and the count of Tiles Containing Denim/Jeans Keyword is same
     */
    public boolean verifyDenimResult() {
        if (productTitles.size() > productTitlesWithText("Denim").size()) {
            if (productTitlesWithText("Jeans").size() > 0) {
                return productTitles.size() == (productTitlesWithText("Denim").size() + productTitlesWithText("Jeans").size());
            }
            return false;
        }
        return productTitles.size() == productTitlesWithText("Denim").size();
    }

    /**
     * Created by Pooja Sharma
     * This Method get the count of Products on PLP and matches with the data passed
     */
    public boolean compareProductsCount(int count) {
        waitUntilElementsAreDisplayed(productTitles, 10);
        return productTitles.size() == count;
    }

    /**
     * Created by Pooja Sharma
     * This Method scrolls down and verify lazy loading feature
     */
    public boolean verifyLazyLoad() {
        scrollToBottom();
        return waitUntilElementDisplayed(lazyloading);
    }

    /**
     * Created by Pooja Sharma
     * This Method adds the Product to Bag
     */
    public boolean addProdToBag(MCategoryDetailsPageAction mCategoryDetailsPageAction, int i) {
        click(addToBagIconByPos(i));
        mCategoryDetailsPageAction.selectRandomSizeFromFlip();
        click(addToBagBtn);
        return waitUntilElementDisplayed(addtoBagNotification);
    }

    /**
     * Created By Pooja
     * This Method get the count of Product Tiles present on the PLP/SRP
     * and compare with the count of Products as per keyword
     *
     * @param keyword Keyword to be searched
     */
    public boolean compareResultForKeyword(String keyword) {
        return productTitles.size() >= productTitlesWithText(keyword).size();
    }

    /**
     * validates filter and sort CTA are displayed in different CTA
     *
     * @return true if both buttons displayed
     */
    public boolean verifyFilterAndsortCTA() {
        boolean areCTASdisplayed = waitUntilElementDisplayed(filterBtn, 20) &&
                waitUntilElementDisplayed(sortyByBtn, 20);

        boolean ctaColors = filterBtn.getCssValue("background-color").equalsIgnoreCase("rgba(51, 51, 51, 1)") &&
                sortyByBtn.getCssValue("background-color").equalsIgnoreCase("rgba(51, 51, 51, 1)");

        return areCTASdisplayed && ctaColors;
    }

    /**
     * Opens the filter and validate color of done and filter clolors
     *
     * @return
     */
    public boolean openFilterButton() {
        scrollToTop();
        click(filter);
        return waitUntilElementDisplayed(doneBtn, 10) && filter.getCssValue("background-color").equalsIgnoreCase("rgba(202, 0, 136, 1)") &&
                doneBtn.getCssValue("background-color").equalsIgnoreCase("rgba(202, 0, 136, 1)");
    }

    /**
     * Gets the current state of Clear All Button in filter
     *
     * @return state of the clear all button
     */
    public boolean validateClearAllButtonState() {
        waitUntilElementDisplayed(clearAllBtn);
        return isEnabled(clearAllBtn);
    }

    /**
     * Select random color for Color Facet
     *
     * @return
     */
    public boolean selectRandomColor() {
        click(filterFacetCTA("color"));
        int cate = colors.size();

        if (cate > 1) {
            click(colors.get(randInt(0, (colors.size() - 1))));
        } else if (colors.size() == 1) {
            click(colors.get(0));
        }
        return waitUntilElementDisplayed(clearColorFilter);
    }

    /**
     * Select random size from Size facet
     *
     * @return
     */
    public boolean selectRandomSize() {
        click(filterFacetCTA("size"));
        if (sizes.size() > 1) {
            click(sizes.get(randInt(0, (sizes.size() - 1))));
        } else if (sizes.size() == 1) {
            click(sizes.get(0));
        }
        return waitUntilElementDisplayed(clearSizeFilter);
    }

    /**
     * Select gender from Gender Facet
     *
     * @param gender of the selection
     * @return
     */
    public boolean selectGender(String gender) {
        click(filterFacetCTA("gender"));
        click(selectGenderFilter(gender));
        return waitUntilElementDisplayed(clearFilter("gender"));
    }

    /**
     * Select random price from price facet
     *
     * @return
     */
    public boolean selectRandomPrice() {
        click(filterFacetCTA("price"));
        if (prices.size() > 1) {
            click(prices.get(randInt(0, (prices.size() - 1))));
        } else if (prices.size() == 1) {
            click(prices.get(0));
        }
        return waitUntilElementDisplayed(clearFilter("price"));
    }

    /**
     * select random fit from fit Facet
     *
     * @return
     */
    public boolean selectRandomFit() {
        click(filterFacetCTA("fit"));

        if (fits.size() > 1) {
            click(fits.get(randInt(0, (fits.size() - 1))));
        } else if (fits.size() == 1) {
            click(fits.get(0));
        }
        return waitUntilElementDisplayed(clearFilter("fit"));
    }

    /**
     * Select random age from Age facet
     *
     * @return
     */
    public boolean selectRandomAge() {
        click(filterFacetCTA("size range"));

        if (ageList.size() > 1) {
            click(ageList.get(randInt(0, (ageList.size() - 1))));
        } else if (ageList.size() == 1) {
            click(ageList.get(0));
        }
        return waitUntilElementDisplayed(clearFilter("size range"));
    }

    /**
     * Open SortBy CTA
     *
     * @return
     */
    public boolean openSortByCTA() {
        scrollToTop();
        click(sortyByBtn);
        return waitUntilElementDisplayed(sortCloseBtn, 10) && sortCloseBtn.getCssValue("background-color").equalsIgnoreCase("rgba(202, 0, 136, 1)");
    }

    /**
     * Close the required faced
     *
     * @param facetName to expand
     * @return
     */
    public boolean closeFacet(String facetName) {
        boolean openFacets = true;
        waitUntilElementDisplayed(expandFacet(facetName), 10);
        click(expandFacet(facetName));
        waitUntilElementDisplayed(collapseFacet(facetName), 10);
        switch ((facetName.toUpperCase())) {
            case "SIZE":
                openFacets = waitUntilElementsAreDisplayed(sizes, 5);
                break;
            case "COLOR":
                openFacets = waitUntilElementsAreDisplayed(colors, 5);
                break;
            case "FIT":
                openFacets = waitUntilElementsAreDisplayed(fits, 5);
                break;
            case "GENDER":
                openFacets = !waitUntilElementDisplayed(collapseFacet(facetName), 10);
                break;
            case "PRICE":
                openFacets = waitUntilElementsAreDisplayed(prices, 5);
                break;
            case "SIZE RANGE":
                openFacets = waitUntilElementsAreDisplayed(ageList, 5);
                break;
        }
        return !openFacets;
    }

    /**
     * Opens the required facet name
     *
     * @param facetName to open
     * @return
     */
    public boolean openFacet(String facetName) {
        boolean openFacets = false;
        waitUntilElementDisplayed(collapseFacet(facetName), 5);
        click(collapseFacet(facetName));
        waitUntilElementDisplayed(expandFacet(facetName), 5);
        switch ((facetName.toUpperCase())) {
            case "SIZE":
                openFacets = waitUntilElementsAreDisplayed(sizes, 5);
                break;
            case "COLOR":
                openFacets = waitUntilElementsAreDisplayed(colors, 5);
                break;
            case "FIT":
                openFacets = waitUntilElementsAreDisplayed(fits, 5);
                break;
            case "GENDER":
                openFacets = verifyElementNotDisplayed(selectGenderFilter("BOY"), 10) || verifyElementNotDisplayed(selectGenderFilter("GIRL"), 10);
                break;
            case "PRICE":
                openFacets = waitUntilElementsAreDisplayed(prices, 5);
                break;
            case "SIZE RANGE":
                openFacets = waitUntilElementsAreDisplayed(ageList, 5);
                break;
        }
        return openFacets && waitUntilElementDisplayed(clearFilter(facetName));
    }

    /**
     * Verify the sort options available
     *
     * @return
     */
    public boolean validateSortoptions(int sort) {
        waitUntilElementsAreDisplayed(sortOptions, 20);
        return sortOptions.size() == sort;
    }

    /**
     * Sort the options based on selection
     *
     * @param type of sort
     * @return
     */
    public boolean sortByOptionPlp(String type) {
        boolean sort = false;
        int count, i;
        String price1, priceLast;
        double firstPrice, lastItemPrice;
        waitUntilElementDisplayed(sortyByBtn, 15);
        click(sortyByBtn);
        waitUntilElementsAreDisplayed(sortOptions, 10);
        switch (type.toUpperCase()) {
            case "LOW_TO_HIGH":
                click(sort_LowToHigh);
                staticWait(5000); //this is for to wait until the page refreshes with data according to sort selection
                waitUntilElementsAreDisplayed(productImages, 4);
                count = productImages.size();
                i = count - 1;
                price1 = getText(offerPrices.get(0)).replace("$", "");
                priceLast = getText(offerPrices.get(i)).replace("$", "");
                firstPrice = Float.valueOf(price1);
                lastItemPrice = Float.valueOf(priceLast);
                if (lastItemPrice >= firstPrice) {
                    sort = true;
                }
                break;
            case "HIGH_TO_LOW":
                click(sort_HighToLow);
                staticWait(5000); //this wait required to wait until the data load completed for sort
                waitUntilElementsAreDisplayed(productImages, 60);
                count = productImages.size();
                i = count - 1;
                price1 = getText(offerPrices.get(0)).replace("$", "");
                priceLast = getText(offerPrices.get(i)).replace("$", "");
                firstPrice = Float.valueOf(price1);
                lastItemPrice = Float.valueOf(priceLast);
                if (lastItemPrice <= firstPrice) {
                    sort = true;
                }
                break;
            case "TOP RATED":
                click(topRated);
                staticWait(5000); //this wait required to wait until the data load completed for sort
                waitUntilElementsAreDisplayed(productImages, 60);
                sort = true;
                break;
        }
        return sort;
    }

    /**
     * verify if  bacl to top is visible
     *
     * @return
     */
    public boolean isBackToTopVisible() {
        return waitUntilElementDisplayed(backToTop);
    }

    /**
     * Click on back to top verify back to top button is disappear
     *
     * @return
     */
    public boolean clickBackToTop() {
        click(backToTop);
        return verifyElementNotDisplayed(backToTop, 5);
    }

    /**
     * Click on back to top verify back to top button is disappear
     *
     * @return
     */
    public boolean ExpandFilterButton(String filter) {
        click(filterButton(filter));
        return waitUntilElementDisplayed(expandedFilterButton(filter), 5);
    }

    /**
     * Clicks on the first product mathces with badge name
     *
     * @param mProductDetailsPageActions
     * @param badgeName                  to be checked
     * @return
     */
    public boolean clickFirstProductMatchWIthBadge(MProductDetailsPageActions mProductDetailsPageActions, String badgeName) {
        waitUntilElementsAreDisplayed(badgeProducts(badgeName), 10);
        click(badgeProducts(badgeName).get(0));
        return waitUntilElementDisplayed(mProductDetailsPageActions.addToBag, 10);
    }

    public String getFirstProdName(){
        return getText(getFirstElementFromList(productNames));
    }

    public List<String> getAllProductName() {
        List<String> names = new ArrayList<>();
        waitUntilElementsAreDisplayed(productNames, 30);
        for (WebElement e : productNames) {
            names.add(e.getText());
        }
        return names;
    }
}