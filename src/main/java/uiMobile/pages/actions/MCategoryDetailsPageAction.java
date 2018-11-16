package uiMobile.pages.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MCategoryDetailsPageRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MCategoryDetailsPageAction extends MCategoryDetailsPageRepo {
    WebDriver mobileDriver;
    public static String catName;
    public String nameOfLink;
    public static WebElement storeWebElement;
    MLoginPageActions loginDrawerActions;

    public MCategoryDetailsPageAction(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        loginDrawerActions = new MLoginPageActions(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Verifies if the Category page displayed or not
     *
     * @return true if the category page displayed
     */
    public boolean isCategoryLandPageDisplay() {
        return waitUntilElementDisplayed(deptNameTitle);
    }

    /**
     * Select product by position
     *
     * @param i position of the image
     * @return Return if the product add to bag
     */
    public boolean clickProductByPosition(int i) {
        MProductDetailsPageActions productDetailsPageActions = new MProductDetailsPageActions(mobileDriver);
        //scrollDownToElement(productImgByPosition(i));
        staticWait(1000);
        javaScriptClick(mobileDriver, productImgByPosition(i));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean clickBOPISbyPos(int i) {
        MobileBopisOverlayActions action = new MobileBopisOverlayActions(mobileDriver);
        scrollDownToElement(bopisProduct(i));
        mouseHover(bopisProduct(i));
        click((bopisProduct(i)));
        return waitUntilElementDisplayed(action.zipCodeField);
    }

    /**
     * Author: JK
     * Gets the price product
     *
     * @param position: of the product
     * @return price
     */
    public String getProductPrice(int position) {
        waitUntilElementsAreDisplayed(productContainers, 20);
        return productContainers.get(position).findElement(By.cssSelector(".text-price.offer-price")).getText();
    }

    /**
     * Open Product Card by position
     *
     * @param i position of the Product
     * @return true if product added to bag
     * Modified by Pooja
     * Removed unneccesary mouseHover event on Product
     */

    public boolean openProdCardViewByPos(int i) {
        //  MSearchResultsPageActions searchResultsPageActions = new MSearchResultsPageActions(mobileDriver);
        // mouseHover(mSearchResultsPageActions.productImageByPosition(i));
        scrollUpToElement(addToBagIconByPos(i));
        javaScriptClick(mobileDriver, addToBagIconByPos(i));
        return waitUntilElementDisplayed(addToBagBtn);
    }

    public boolean clickRandomProductByImage(MProductDetailsPageActions productDetailsPageActions) {
        int size = productImages.size();
        if (size > 1) {
            click(productImages.get(randInt(0, (productImages.size() - 1))));
        } else if (productImages.size() == 1) {
            click(productImages.get(0));
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 30);
    }

    public boolean selectRandomCategory() {
        click(filterFacetCTA("category"));
        int cate = categories.size();

        if (cate > 1) {
            click(categories.get(randInt(0, (categories.size() - 1))));
        } else if (categories.size() == 1) {
            click(categories.get(0));
        }
        clickDoneBtn();
        return waitUntilElementDisplayed(filter, 30);
    }

    public void selectRandomSort() {
        int cate = sorts.size();
        if (cate > 1) {
            click(sorts.get(randInt(0, (sorts.size() - 1))));
        } else if (sorts.size() == 1) {
            click(sorts.get(0));
        }
    }

    public void selectRandomCategori() {
        int cate = cates.size();

        if (cate > 1) {
            click(cates.get(randInt(0, (cates.size() - 1))));
        } else if (cates.size() == 1) {
            click(cates.get(0));
        }
    }

    public List<String> getAllValues() {
        List<String> options = new ArrayList<>();
        int size = sorts.size();
        for (int i = 0; i < size; i++) {
            options.add(getText(sorts.get(i)));
        }

        return options;
    }

    public boolean selectRandomColor() {
        javaScriptClick(mobileDriver, filterFacetCTA("color"));
        int cate = colors.size();
        if (cate > 1) {
            click_Selenium(colors.get(randInt(0, (colors.size() - 1))));
        } else if (colors.size() == 1) {
            click_Selenium(colors.get(0));
        }
        return waitUntilElementsAreDisplayed(selectedColors, 15);
    }

    public boolean selectRandomSize() {
        javaScriptClick(mobileDriver, filterFacetCTA("size"));
        if (sizes.size() > 1) {
            click_Selenium(sizes.get(randInt(0, (sizes.size() - 1))));
        } else if (sizes.size() == 1) {
            click_Selenium(sizes.get(0));
        }
        return waitUntilElementsAreDisplayed(selectedSizes, 15);
    }

    public boolean selectGender(String gender) {
        click(filterFacetCTA("gender"));
        click(selectGenderFilter(gender));
        return waitUntilElementDisplayed(clearFilter("gender"));
    }

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
     * Select random age from Age facet
     *
     * @return
     */
    public boolean selectRandomSizeRange() {
        click(filterFacetCTA("size range"));

        if (ageList.size() > 1) {
            click(ageList.get(randInt(0, (ageList.size() - 1))));
        } else if (ageList.size() == 1) {
            click(ageList.get(0));
        }
        return waitUntilElementDisplayed(clearFilter("size range"));
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
     * Created By Pooja
     * This Method will select the size from Filter
     * data-Pass the Title of the Filter say:-ONE SIZE
     */
    public boolean applySizeFilterByTitle(String title) {
        openFilterButton();
        click(filterFacetCTA("size"));
        if (sizes.size() > 1) {
            click(selectProdSizeByTitle(title));
            scrollDownToElement(doneBtn);
            javaScriptClick(mobileDriver, doneBtn);
        } else if (sizes.size() == 1) {
            click(sizes.get(0));
            scrollDownToElement(doneBtn);
            javaScriptClick(mobileDriver, doneBtn);
        }
        return waitUntilElementDisplayed(filter);
    }

    /**
     * Created By Pooja
     * This Method will select the Color from Filter
     * data-Pass the Title of the Filter say:-WHITE
     */
    public boolean selectColorByTitle(String title) {
        click(applyColorFilterByTitle(title));
        return waitUntilElementDisplayed(clearColorFilter);
    }


    /**
     * Created By Pooja
     * This Method will click on Done Button under Filter
     */
    public boolean clickDoneBtn() {
        staticWait(5000);
        click(doneBtn);
        staticWait(5000);
        return waitUntilElementDisplayed(filter, 30);
    }

    public boolean clickFilterAndVerifyExpands(){
        click(filter);
        return waitUntilElementDisplayed(filterExpanded, 30);
    }

    public boolean clearFilterSelections(String attribute) {
        boolean selection = true;
        switch (attribute) {
            case "size":
                click(clearSizeFilter);
                selection = waitUntilElementsAreDisplayed(selectedSizes, 5);
                break;
            case "color":
                click(clearColorFilter);
                selection = waitUntilElementsAreDisplayed(selectedColors, 5);
                break;
        }
        return selection;
    }

    /**
     * Author: JK
     * select random size from add to bag flip
     *
     * @return true if any value is selected
     */
    public boolean selectRandomSizeFromFlip() {
        if (availableFlipSizes.size() == 1) {
            click(availableFlipSizes.get(0));
        } else if (availableFlipSizes.size() > 1) {
            click(availableFlipSizes.get(randInt(0, (availableFlipSizes.size() - 1))));
        }
        return waitUntilElementDisplayed(selectedFlipSize);
    }

    /**
     * Author: JK
     * Select a random size from add to bag flip
     *
     * @return if the correct value is selected
     */
    public boolean selectQtyFromFlip(String qty) {
        selectDropDownByVisibleText(qtyDropDown, qty);
        return getSelectOptions(qtyDropDown) == qty;
    }

    public boolean filterWith_Attribute(String attribute) {
        switch (attribute) {
            case "cate":
                selectRandomCategori();
                break;
            case "color":
                selectRandomColor();
                break;
            case "size":
                selectRandomSize();
                break;
            case "sort":
                selectRandomSort();
                break;
        }
        javaScriptClick(mobileDriver, doneBtn);
        return waitUntilElementDisplayed(filter, 20);
    }

    /**
     * Modified by Pooja as per Unbxd Changes
     * Modified by JK as per KOMODO changes
     */
    public boolean openFilterButton() {
        scrollToTop();
        click(filter);
        return waitUntilElementDisplayed(doneBtn, 10) &&
                filter.getCssValue("background-color").equalsIgnoreCase("rgba(202, 0, 136, 1)") &&
                doneBtn.getCssValue("background-color").equalsIgnoreCase("rgba(202, 0, 136, 1)");
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


    public boolean validateClearAllButtonState(String State) {
        waitUntilElementDisplayed(clearAllBtn);
        if (State.equalsIgnoreCase("Enable")) {
            return isEnabled(clearAllBtn);
        }
        if (State.equalsIgnoreCase("Disable")) {
            return (!isEnabled(clearAllBtn));
        }
        return false;
    }

    /**
     * Created By Pooja on 23/05/2018
     * This Method Stores the count of items on PLP in a Global Variable
     */

    public int getCount(MSearchResultsPageActions mSearchResultsPageActions) {
        return Integer.parseInt(getText(mSearchResultsPageActions.itemsCount));
    }

    /**
     * Created By Pooja
     * This Method clicks on Add To bag Button and verifies if One Size
     */
    public boolean verifyOneSizeButtonSelected(int i) {
        javaScriptClick(mobileDriver, addToBagIconByPos(i));
        return waitUntilElementDisplayed(selectedFlipOneSize, 10);

    }

    /**
     * Created By Pooja
     * This Method clicks on Add To bag Button and verifies all the items on Bag Flip
     */
    public boolean verifyBagFlipItems() {
        boolean flipItems = waitUntilElementsAreDisplayed(availableColors, 10) &&
                waitUntilElementsAreDisplayed(availableFlipSizes, 2) &&
                waitUntilElementDisplayed(addToBagBtn, 2) &&
                getText(viewProdDetailsLink).contains("View Product Details");
        return flipItems;
    }

    /**
     * Created By Pooja
     * This Method clicks on Add To bag Button and verifies if One Size
     */
    public boolean clickProductDetails(MCategoryDetailsPageAction mCategoryDetailsPageAction) {
        click(viewProdDetailsLink);
        return waitUntilElementDisplayed(mCategoryDetailsPageAction.addToBagBtn);
    }


    /**
     * Created By Pooja
     * This Method Matches the alternate value of the default Selected Color Swatch on PLP and Bag Flip
     */
    public boolean MatchDefaultColorOnBagFlip() {
        String defaultSelectedColorSwatch = "";
        if (waitUntilElementsAreDisplayed(multiColoredImage, 10)) {
            for (int i = 0; i < multiColoredImage.size(); i++) {
                if (waitUntilElementDisplayed(multiColoredImage.get(i), 5) && verifyElementNotDisplayed(outofStock, 5)) {
                    defaultSelectedColorSwatch = defaultSwatch.get(i).getAttribute("alt");
                    click(addToBagBtnOfMultiColordProd.get(i));
                    if (getAttributeValue(selectedFlipColor, "value").equals(defaultSelectedColorSwatch)) {
                        closeBagFlip();
                        return true;
                    }
                    addStepDescription("Default selected color swatch on PLP and Bag Flip does not match");
                    closeBagFlip();
                    return false;

                }
            }

        }
        addStepDescription("Multicolored Products are not present");
        return false;
    }

    /**
     * Created By Pooja
     * This Method Closes the Bag Flip and verifies close Link is not displayed
     */
    public boolean closeBagFlip() {
        click(closeLink);
        return verifyElementNotDisplayed(closeLink);

    }

    /**
     * Created By Pooja
     * This Method verifies the value of selected Flip Color is one of the related color selected in Filter
     */
    public boolean verifySelectedFlipWhiteColor() {
        List<String> colors = Arrays.asList("SNOW", "WHITE", "SIMPLYWHT");
        String defaultSelectedColor = getAttributeValue(selectedFlipColor, "value");
        return colors.contains(defaultSelectedColor);

    }


    /**
     * Created By Pooja to click on fav icon to add the product into Favourite list
     */
    public void clickAddToWishListAsRegistered(int i) {
        click(addToFavIcon.get(i));
        staticWait(5000);
    }

    /**
     * Created By Pooja to
     * click on fav icon to add the product into Favourite list
     */
    public boolean clickAddToWishListAsGuest(MLoginPageActions mLoginPageActions, int i) {
        click(addToFavIcon.get(i));
        return mLoginPageActions.waitUntilElementDisplayed(mLoginPageActions.emailAddrField);
    }

    /**
     * Created By Pooja to get the Product Name from PLP
     */
    public String getProdName(int i) {
        return getText(productImages.get(i));

    }

    /**
     * Created By Pooja on 16th May,2018
     * This Method verifies the correct breadcrumbs are displayed
     */
    public boolean verifyBreadcrumbsAsPerNavigation(List<String> expectedBreadcrumbs) {
        if (waitUntilElementsAreDisplayed(breadcrumb_values, 10)) {
            List<String> Actualbrdcrmbs = new ArrayList<String>();
            for (WebElement e : breadcrumb_values) {
                Actualbrdcrmbs.add(getText(e));
            }
            for (String ex : expectedBreadcrumbs) {
                if (!Actualbrdcrmbs.contains(ex)) {
                    addStepDescription("Correct Breadcrumbs are not available");
                    return false;
                }
            }
            addStepDescription("Correct Breadcrumbs are displayed");
            return true;
        } else {
            addStepDescription("Breadcrumbs are not available");
            return false;
        }
    }

    /**
     * Created By Pooja on 16th May,2018
     * This Method verifies the Error message when trying to add OOS Item to favourite
     */

    public boolean verifyOOSErrOnaddingToFav() {
        if (waitUntilElementsAreDisplayed(productImages, 5)) {
            for (int i = 0; i < productImages.size(); i++) {
                clickAddToWishListAsRegistered(i);
                if (waitUntilElementDisplayed(outofStock, 5)) {
                    return true;
                }
            }
            addStepDescription("OOS Products are not present on the PLP");
            return false;
        } else {
            addStepDescription("Products are not present on the PLP");
            return false;
        }
    }

    /**
     * Created By Pooja to click on Pick Up in Store Button on PLP
     */
    public boolean clickPickUpStoreBtn(MobileBopisOverlayActions bopisOverlayActions, int i) {
        click(pickupstoreBtn.get(i));
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 30);
    }

    /**
     * Created By Pooja
     * This method adds the Product to Bag and verify the View Bag Notification
     */
    public boolean addItemToBagAndVerifyNotification(int i) {
        //scrollDownToElement(addToBagIconByPos(i));
        javaScriptClick(mobileDriver, addToBagIconByPos(i));
        click(addToBagBtn);
        return waitUntilElementDisplayed(addtoBagNotification);
    }

    /**
     * Created By Pooja
     * This method clicks on L1 Category Breadcrumb and navigate to Home Page
     */
    public boolean clickL1Breadcrumb(MobileHomePageActions mobileHomePageActions) {
        click(validBreadcrumb);
        return waitUntilElementsAreDisplayed(mobileHomePageActions.subCategoriesLink, 10);
    }

    /**
     * Created By Pooja, This Method will select a Category from Filter
     */
    public String applyCategoryFilter(MSearchResultsPageActions mSearchResultsPageActions, int i) {
        click(mSearchResultsPageActions.filterButton("Category"));
        String selected_Category = getText(categoryChips.get(i));
        click_Selenium(categoryChips.get(i));
        clickDoneBtn();
        if (waitUntilElementDisplayed(filter, 10)) {
            return selected_Category;
        }
        addStepDescription("App not navigated Back to SRP/PLP after Category Filter applied");
        return null;
    }

    /**
     * Created By Pooja, This Method will verify a Category Selected from Filter
     */
    public boolean verifyCategoryFilterApplied(String category) {
        return category.equalsIgnoreCase(getText(appliedFilterList));
    }

    /**
     * Created by Richa
     * Click on Add to cart button on flip
     *
     * @return true if add to cart button is clicked successfully
     */
    public boolean clickAddToBagButtonOnFlip() {
        click(addToBagBtn);
        return waitUntilElementDisplayed(addtoBagNotification);
    }

    /**
     * Created By Pooja, This Method will verify the Selected Sort Option
     */
    public boolean verifySelectedSortOption(String sortOption) {
        return sortOption.equals(getText(selectedSortOption));
    }

    /**
     * Created By Pooja, This Method will verify the Selected Sort Option
     */
    public boolean clickBackToTopBtn() {
        jqueryClick(".scroll-to-top-container.active-scroll");
        return verifyElementNotDisplayed(backToTopBtn);
    }

    /**
     * Created By Pooja
     * This Method verifies the Application is on Content Landing Page
     */
    public boolean verifyContentLandingPage(String searchString) {
        if (waitUntilElementsAreDisplayed(contentLandingBrdcrumb, 10)) {
            return contentLandingBrdcrumb.size() == 1 && getText(contentLandingBrdcrumb.get(0)).equals(searchString);
        }

        addStepDescription("Not Landed on Content landing Page");
        return false;
    }

    /**
     * validate product card length in PLP
     *
     * @return true if it matched with expected
     */

    public boolean validatedProductWidth() {
        waitUntilElementDisplayed(productContainers.get(1), 30);
        String productwidth = productContainers.get(1).getCssValue("width");

        return productwidth.equalsIgnoreCase("202.5px");
    }

    /**
     * validate outfits product card length in PLP
     *
     * @return true if it matched with expected
     */

    public boolean validatedOutfitsProductWidth() {
        waitUntilElementDisplayed(outfitProductcontainers.get(1), 30);
        int productwidth = Integer.parseInt(outfitProductcontainers.get(1).getCssValue("width").split("px")[0]);
        return productwidth >= 290;
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
     * Expands the required faced
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
        }
        return !openFacets;
    }

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
                openFacets = waitUntilElementDisplayed(expandFacet(facetName));
                break;
            case "PRICE":
                openFacets = waitUntilElementsAreDisplayed(prices, 20);
                break;
        }
        return openFacets;
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
     * Verify the sort options available
     *
     * @return
     */
    public boolean validateFilterOptions(int sort) {
        waitUntilElementsAreDisplayed(filterOptions, 20);
        return filterOptions.size() == sort;
    }

    /**
     * returns current page breadcrumb
     */
    public String getCurrentBreatcrumb() {
        waitUntilElementDisplayed(breadCrumb, 30);
        return getText(breadCrumb);
    }

    /**
     * Click on outfit image
     *
     * @param i position of the product
     * @return true if the page opens
     */
    public boolean clickOutfitsImageByPosition(int i) {
        if (OutfitsImages.size() >= 1) {
            mouseHover(OutfitsImages.get(i));
            javaScriptClick(mobileDriver, shopTheLook.get(i));
            return mobileDriver.getCurrentUrl().contains("outfit");
        }
        return false;
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
            case "HIGH_TO_LOW":
                click(sort_HighToLow);
                staticWait(5000); //this is for to wait until the page refreshes with data according to sort selection
                waitUntilElementsAreDisplayed(productImages, 4);
                count = productImages.size();
                i = count - 1;
                price1 = getText(offerPrices.get(0)).replace("$", "");
                priceLast = getText(offerPrices.get(i)).replace("$", "");

                firstPrice = Float.valueOf(price1);
                lastItemPrice = Float.valueOf(priceLast);
                if (firstPrice >= lastItemPrice) {
                    sort = true;
                }
                break;
            case "LOW_TO_HIGH":
                click(sort_LowToHigh);
                staticWait(5000); //this wait required to wait until the data load completed for sort
                waitUntilElementsAreDisplayed(productImages, 60);
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
            case "TOP_RATED":
                click(topRated);
                staticWait(5000); //this wait required to wait until the data load completed for sort
                waitUntilElementsAreDisplayed(productImages, 60);
                sort = true;
        }
        return sort;
    }

    /**
     * Get available badges from PLP
     *
     * @return
     */
    public List<String> getAvailableBadges() {
        List<String> badges = new ArrayList<>();
        if (inlineBadges.size() > 0) {
            for (WebElement e : inlineBadges) {
                badges.add(e.getText());
            }
        } else {
            addStepDescription("no Products available with Badges");
        }
        return badges;
    }

    public boolean validateInEligibleBopisIcons(int i) {

        mouseHover(itemContainers.get(i));
        waitUntilElementsAreDisplayed(addToBagIcon, 10);
        boolean BOPISAva = !waitUntilElementsAreDisplayed(addToBopisIcon, 3);
        if (BOPISAva) {
            return true;
        } else
            return false;
    }

    public boolean addGCToFav_PLPReg(int i) {
        mouseHover(itemImg(i));
        waitUntilElementDisplayed(addToFavBtnPLP, 5);
        if (addToFavIcon.size() >= 1) {
            click(addToFavBtnPLP);
            staticWait(3000);
            mouseHover(itemImg(i));
            return waitUntilElementDisplayed(errorBox, 15);
        }
        return false;
    }
    
    /**
     * Created by RichaK
     * return the error message as text
     * @return
     */
    public String getErrorMessage()
    {
    	waitUntilElementDisplayed(errorBox);
    	return getText(errorBox);
    }
}
