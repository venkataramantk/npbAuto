package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.CategoryDetailsPageRepo;

import java.util.*;

/**
 * Created by skonda on 6/7/2016.
 */
public class CategoryDetailsPageAction extends CategoryDetailsPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(CategoryDetailsPageAction.class);
    public static String subCatName;
    public static String catName;
    public String nameOfLink;
    public static WebElement storeWebElement;

    public CategoryDetailsPageAction(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isCategoryLandPageDisplay() {
        return waitUntilElementDisplayed(deptNameTitle);
    }

    public boolean clickImageByPosition(ProductDetailsPageActions productDetailsPageActions, int i) {
        scrollDownToElement(itemImg(i));
        click(itemImg(i));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean clickProductNameByPosition(ProductDetailsPageActions productDetailsPageActions, int i) {
        scrollDownToElement(itemName(i));
        click(itemName(i));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }


    public boolean clickImageByPosition(ProductDetailsPageActions productDetailsPageActions) {
        for (int i = 1; i <= productImages.size(); i++) {
            boolean justFewLeftEle = false;
            try {
                justFewLeftEle = isDisplayed(justFewLeftBadge(i));
            } catch (NoSuchElementException n) {
                justFewLeftEle = false;
            }
            if (justFewLeftEle) {
                click(itemImg(i));
                return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
            }
        }
        return false;
    }

    public boolean verifyCount() {
        return Integer.parseInt(getText(countText).split(" ")[1]) != 0;
    }

    public boolean verifyProductardsinPLP() {
        return waitUntilElementsAreDisplayed(itemContainers, 30);
    }

    public boolean openProdCardWithItem() {
        SearchResultsPageActions searchResultsPageActions = new SearchResultsPageActions(driver);
        int size = itemContainers.size();
        for (int j = 0; j < size - 1; j++) {
            mouseHover(searchResultsPageActions.productImageByPosition(j + 1));
            if (addToBagButton.size() > 0) {
                click(addToBagButton.get(j));
                return waitUntilElementDisplayed(addToBagBtn);
            }
            if (addToBagIcon.size() > 0) {
                click(addToBagIcon.get(j));
                return waitUntilElementDisplayed(addToBagBtn);
            }
            //   click(addToBagIconByPos(j + 1));
            if (!waitUntilElementDisplayed(flipOOS, 10)) {
                logger.info("item is available");
                break;
            } else {
                logger.info("item OOS");
            }
        }
        return waitUntilElementsAreDisplayed(availableSizes, 10);
    }

    /**
     * Modified by Pooja
     * Added condition to handle both add to bag button and add to Bag icon on PLP whichever is displayed
     *
     * @param searchResultsPageActions
     * @param i
     * @return
     */
    public boolean openProdCardViewByPos(SearchResultsPageActions searchResultsPageActions, int i) {
        try {
            mouseHover(searchResultsPageActions.productImageByPosition(i));
            if (addToBagButton.size() > 0) {
                click(addToBagButton.get(i - 1));
                return waitUntilElementDisplayed(addToBagBtn);
            }
            if (addToBagIcon.size() > 0) {
                click(addToBagIcon.get(i - 1));
                return waitUntilElementDisplayed(addToBagBtn);
            }
        } catch (Exception e) {
            addStepDescription("Unable to add product to bag from product card");
        }
        return false;
    }

    public boolean validateAddToBagFlip() {
        openProdCardWithItem();

        return waitUntilElementDisplayed(addToBagBtn) &&
                waitUntilElementDisplayed(viewProdDetailsLink) &&
                waitUntilElementsAreDisplayed(availableColors, 5) &&
                waitUntilElementsAreDisplayed(availableSizes, 5) &&
                waitUntilElementsAreDisplayed(allFits, 5) &&
                waitUntilElementDisplayed(selectQuantityDropDown, 5) &&
                waitUntilElementDisplayed(closeAddToBaglink, 5);
    }

    public boolean clickRandomProductByImage(ProductDetailsPageActions productDetailsPageActions) {
        waitUntilElementsAreDisplayed(productImages, 30);
        int size = 0;
        try {
            size = productImages.size();
        } catch (Exception e) {
            size = 0;
        }
        if (size > 1) {
            click(productImages.get(randInt(0, (productImages.size() - 1))));
        } else if (productImages.size() == 1) {
            click(productImages.get(0));
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 30);
    }

    public boolean clickRandomProductByName(ProductDetailsPageActions productDetailsPageActions) {
        int size = 0;
        try {
            size = productImages.size();
        } catch (Exception e) {
            size = 0;
        }
        if (size > 1) {
            click(itemName(randInt(1, (productImages.size() - 1))));
        } else if (productImages.size() == 1) {
            click(itemName(1));
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 30);
    }

    public boolean clickOnRandomSubCategory() {
        int size = subcategory_LeftNav.size();
        if (size > 1) {
            WebElement link = subcategory_LeftNav.get(randInt(0, (subcategory_LeftNav.size() - 1)));
            nameOfLink = getText(link);
            click(link);


        } else if (subcategory_LeftNav.size() == 1) {
            WebElement link = subcategory_LeftNav.get(0);
            nameOfLink = getText(link);
            click(link);

        }
        return waitUntilElementDisplayed(subcategory_BreadCrumb, 30);
    }

    public boolean validateBreadCrumbDisplayed() {
        boolean isBreadCrumbDisplaying = waitUntilElementDisplayed(breadCrumb);
        if (isBreadCrumbDisplaying)
            return true;
        else
            return false;
    }

    public boolean verifyPagination() {

        if (verifyElementNotDisplayed(lnk_PreviousPage))
            click(lnk_NextPage);
        return waitUntilElementDisplayed(lnk_PreviousPage);
    }


    public boolean selectRandomSubCategory(CategoryDetailsPageAction categoryDetailsPageAction, DepartmentLandingPageActions departmentLandingPageActions) {
        int totalCount = subcategory_LeftNav.size();
        if (totalCount > 1) {
            WebElement subCatList = subcategory_LeftNav.get(randInt(0, (subcategory_LeftNav.size() - 1)));
            subCatName = subCatList.getText();
            mouseClick(subCatList);
            System.out.println(subCatName);
        } else if (subcategory_LeftNav.size() == 1) {
            WebElement subCatList = subcategory_LeftNav.get(0);
            subCatName = subCatList.getText();
            mouseClick(subCatList);
            System.out.println(subCatName);
        } else if (totalCount == 0) {
            WebElement catList;
            do {
                catList = linksUnselected_LeftNavigation.get(randInt(0, (linksUnselected_LeftNavigation.size() - 1)));
                catList.click();
                catName = catList.getText();
                departmentLandingPageActions.catName = catName;
                staticWait(10);
            }
            while (subcategory_LeftNav.size() == 0);
            selectRandomSubCategory(categoryDetailsPageAction, departmentLandingPageActions);
            System.out.println(catName);
        }

        return true;
    }


    public boolean subCategoryBreadCrumbVerification(DepartmentLandingPageActions departmentLandingPageActions, HomePageActions homePageActions) {
        String breadCrumb_Cat = breadCrumb.getText();

        if (breadCrumb_Cat.contains("Home") || breadCrumb_Cat.contains("Inicio") && breadCrumb_Cat.contains(homePageActions.deptName)
                && breadCrumb_Cat.contains(departmentLandingPageActions.catName) && breadCrumb_Cat.contains(subCatName))
            return true;
        else
            return false;
    }

    public boolean verifySortAndFilter() {
        return waitUntilElementDisplayed(sortBy_dropdown, 20) &&
                waitUntilElementDisplayed(filterByText, 3) &&
                waitUntilElementDisplayed(sizeFilterLink, 3) &&
                waitUntilElementDisplayed(colorFilterLink, 3) &&
                waitUntilElementDisplayed(genderFilterLink, 3) &&
                waitUntilElementDisplayed(priceFilterLink, 3) &&
                waitUntilElementDisplayed(fitFilterLink, 3) &&
                waitUntilElementDisplayed(sizeRangeFilterLink, 3);
    }

    public boolean verifySizeFilter() {
        click(sizeFilterLink);
        return waitUntilElementsAreDisplayed(sizeAttributes, 5) &&
                waitUntilElementDisplayed(sizeApplyBtn) &&
                !waitUntilElementDisplayed(selectedSize, 5);
    }

    public boolean clickOnOpenSizeFilter() {
        click(sizeFilterLinkOpen);
        return waitUntilElementDisplayed(sizeFilterLink, 20);
    }

    public boolean verifyColorFilter() {
        click(colorFilterLink);
        return waitUntilElementsAreDisplayed(sizeAttributes, 5) &&
                waitUntilElementDisplayed(sizeApplyBtn) &&
                !waitUntilElementDisplayed(selectedSize, 5);
    }

    public boolean verifyPriceFilter() {
        click(priceFilterLink);
        return waitUntilElementsAreDisplayed(priceAttributes, 2) &&
                waitUntilElementDisplayed(sizeApplyBtn) &&
                !waitUntilElementDisplayed(selectedSize, 2);
    }


    /**
     * Created By Pooja, This Method Closes the Opened Color Filter Sectipon
     */

    public boolean verifyGenderFilter() {
        click(genderFilterLink);
        return waitUntilElementsAreDisplayed(priceAttributes, 2) &&
                waitUntilElementDisplayed(sizeApplyBtn) &&
                !waitUntilElementDisplayed(selectedSize, 2);
    }

    public boolean verifyFitFilter() {
        click(fitFilterLink);
        return waitUntilElementsAreDisplayed(priceAttributes, 2) &&
                waitUntilElementDisplayed(sizeApplyBtn) &&
                !waitUntilElementDisplayed(selectedSize, 2);
    }

    public boolean verifySizeRangeFilter() {
        click(sizeRangeFilterLink);
        return waitUntilElementsAreDisplayed(sizeRangeAttributes, 2) &&
                waitUntilElementDisplayed(sizeApplyBtn) &&
                !waitUntilElementDisplayed(selectedSize, 2);
    }


    /*Created By Pooja, This Method Closes the Opened Color Filter Sectipon*/
    public boolean closeColorFilterSection() {
        click(openedColorFilter);
        return waitUntilElementDisplayed(colorFilterLink);
    }

    public boolean closePriceFilterSection() {
        click(openedPriceFilter);
        return waitUntilElementDisplayed(priceFilterLink);
    }

    public boolean closeFitFilterSection() {
        click(openedFitFilter);
        return waitUntilElementDisplayed(priceFilterLink);
    }

    public boolean closeGenderFilterSection() {
        click(openedGenderFilter);
        return waitUntilElementDisplayed(priceFilterLink);
    }

    public boolean closeSizeRangeFilterSection() {
        click(openedSizeRangeFilter);
        return waitUntilElementDisplayed(priceFilterLink);
    }

    public boolean verifyL3Cat() {
        waitUntilElementDisplayed(l3CategoryInBreadCrumb);
        System.out.println(deptNameTitle.getCssValue("color"));
        //rgba(202, 0, 136, 1) equals #ca0088
        return convertRgbToTextColor(deptNameTitle.getCssValue("color")).equalsIgnoreCase("#ca0088");
    }

    public boolean verify404Page() {
        return waitUntilElementDisplayed(errorPagePuppyBox) && waitUntilElementDisplayed(errorPageContentBox);
        //    return waitUntilElementDisplayed(errorPage);
    }

    public boolean verifySortBySelectOptions(String sortBy) {
        int options = sortByOptions.size();
        for (int i = 0; i < options; i++)
            if (getText(sortByOptions.get(i)).trim().contains(sortBy))
                break;
        return true;
    }

    public String getSelectSort() {
        System.out.println(getText(selectedSort));
        return getText(selectedSort);
    }

    public boolean getAllSortOptions() {
        click(sortByOption);
        return getText(availableSorts).contains("Recommended") &&
                getText(availableSorts).contains("Newest") &&
                getText(availableSorts).contains("Price: Low to High") &&
                getText(availableSorts).contains("Price: High to Low") &&
                getText(availableSorts).contains("Most Favorited") &&
                getText(availableSorts).contains("Top Rated");
    }


    public boolean checkSorting(String sortingValue) {
        List<WebElement> products_Webelement = null;
        String attribute = "";

        //store the products (web elements) into the linkedlist
        if (sortingValue.equalsIgnoreCase("Best Match")) {
            products_Webelement = driver.findElements(By.xpath("//*[@class='product productLarge'] //*[@class='categoryTitle']"));
            attribute = "text";
        } else if (sortingValue.equalsIgnoreCase("New Arrivals")) {
            products_Webelement = driver.findElements(By.xpath("//*[@class='product productLarge'] //*[@class='new']"));
            attribute = "alt";
        } else if (sortingValue.equalsIgnoreCase("Price: Low to High")) {
            products_Webelement = driver.findElements(By.xpath("//*[@class='product productLarge'] //*[@class='price productRow']//p"));
            attribute = "text";
        } else //(SortingValue.equalsIgnoreCase("Price: High to Low"))
        {
            products_Webelement = driver.findElements(By.xpath("//*[@class='product productLarge'] //*[@class='price productRow']//p"));
            attribute = "text";
        }

        //create another linked list of type string to store image title
        LinkedList<String> product_names = new LinkedList<String>();

        //loop through all the elements of the product_webelement list get it title and store it into the product_names list
        for (int i = 0; i < products_Webelement.size(); i++) {
            String s = "";
            if (attribute.equalsIgnoreCase("alt")) {
                s = products_Webelement.get(i).getAttribute(attribute);
            } else {
                s = products_Webelement.get(i).getText();
            }
            product_names.add(s);
        }

        //send the list to chkalphabetical_order method to check if the elements in the list are in alphabetical order
        boolean result = checkAlphabeticalOrder(product_names);

        if (sortingValue.equalsIgnoreCase("Price: High to Low")) {
            if (result) {
                result = false;
            } else {
                result = true;
            }
        }

        if (result) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateIcons(int i) {
        mouseHover(itemContainers.get(i));
        return waitUntilElementDisplayed(addToBagIcon.get(i)) && waitUntilElementDisplayed(addToFavIcon.get(i))
                && waitUntilElementDisplayed(itemImg(i)) && waitUntilElementDisplayed(itemName(i));
    }

    public String clickAndGetColorSwatch(String att, ProductDetailsPageActions productDetailsPageActions) {
        if (itemNameWithColorSwatches.size() > 1) {
            String colorSelected = clickOnColorSwatch(1, 1, att);
            clickOnSelectedColorSwatchProdName(productDetailsPageActions, 1);
            return colorSelected;
        }
        addStepDescription("Items with multiple color swatches not Present");
        return "false";
    }

    public String clickOnColorSwatch(int i, int j, String att) {
        mouseHover(itemNameWithColorSwatch(i));
        click(colorSwatchPosByProd(i, j));
        waitUntilElementDisplayed(activeColorSwatchByProd(i), 10);
        return getAttributeValue(activeColorSwatchByProd(i), att);

    }

    public boolean clickOnSelectedColorSwatchProdName(ProductDetailsPageActions productDetailsPageActions, int i) {
        mouseHover(activeColorSwatchProdName(i));
        click(activeColorSwatchProdName(i));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean selectRandomSizeAndAddToBag(HeaderMenuActions headerMenuActions) {
        if (availableSizes.size() > 1) {
            click(availableSizeByPosition(randInt(1, (availableSizes.size() - 1))));
        } else if (availableSizes.size() == 1) {
            click(availableSizeByPosition(1));
        }
        click(addToBagBtn);
        return validateSBConfEcom();
    }

    public void selectRandomColor() {
        if (availableColors.size() > 1) {
            click(availableColors.get(randInt(1, (availableColors.size() - 1))));
        } else if (availableColors.size() == 1) {
            click(availableColors.get(0));
        }
    }

    public void clickClose_ConfViewBag() {
        if (isDisplayed(conf_ButtonClose)) {
            click(conf_ButtonClose);
            waitUntilElementClickable(shoppingBagIcon, 15);
        }
    }

    public boolean validateSBConfEcom() {

        waitUntilElementDisplayed(addToBagNotification, 1);
        if (isDisplayed(conf_ViewBag) && isDisplayed(conf_ContinueCheckout) && isDisplayed(conf_Checkout)
                && waitUntilElementDisplayed(paypal_Conf, 20)) {
            clickClose_ConfViewBag();
            return true;
        } else {
            addStepDescription("Buttons are not displayed in the SB confirmation modal for Ecom");
            return false;
        }
    }

    public boolean addProdToFav_GuestPLP(int i, LoginDrawerActions loginDrawerActions, String emailID, String password) {
        mouseHover(itemImg(i));
        waitUntilElementDisplayed(addToFavBtnPLP);

        if (addToFavIcon.size() >= 1) {
            click(addToFavIcon.get(i - 1));
        }
        loginDrawerActions.enterEmailAddressAndPwd(emailID, password);
        click(loginDrawerActions.loginButton);
        mouseHover(itemImg(i));
        return waitUntilElementDisplayed(favIconEnabled.get(i - 1));
    }

    public boolean addProdToFav_RegPLP(int i) {
        staticWait();
        mouseHover(itemImg(i));
        waitUntilElementsAreDisplayed(addToFavIcon, 5);
//        waitUntilElementsAreDisplayed(addToFavIcon, 5);
//        waitUntilElementsAreDisplayed(colourSwatch, 5);
        if (addToFavIcon.size() > 0) {
            click(addToFavIcon.get(i - 1));
            //staticWait(3000);
            mouseHover(itemImg(i));
            return waitUntilElementsAreDisplayed(favIconEnabled, 10);
        }
        return false;
    }

    /**
     * Created By Pooja
     * This Method clicks on add to Favorite icon on PLP
     */
    public boolean addProdToFav(SearchResultsPageActions searchResultsPageActions, int i) {
        waitUntilElementDisplayed(searchResultsPageActions.productImageByPosition(i));
        mouseHover(searchResultsPageActions.productImageByPosition(i));
        click(addToFavIcon.get(i - 1));
        return waitUntilElementsAreDisplayed(favIconEnabled, 5);
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

    public boolean addToFavoritesMessageDisplay(int i) {
        mouseHover(addToFavIcon.get(i - 1));
        return waitUntilElementDisplayed(addToFavMessage.get(i - 1), 10);
    }

    public boolean pickUpInStoreMessageDisplay(int i) {
        mouseHover(pickUpInStoreIcons.get(i - 1));
        return waitUntilElementDisplayed(pickUpInStoreMessage.get(i - 1), 10);
    }

    public boolean addToBagMessageDisplay(int i) {
        mouseHover(addToBagIcon.get(i - 1));
        return waitUntilElementDisplayed(addToBagIconMsg.get(i - 1), 10);
    }

    /**
     * Author: JK
     * select random size from add to bag flip
     *
     * @return true if any value is selected
     */
    public boolean selectRandomSizeFromFlip() {
        if (availableSizes.size() == 1) {
            click(availableSizes.get(0));
        } else if (availableSizes.size() > 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
        }
        return waitUntilElementDisplayed(selectedFlipSize);
    }

    /**
     * Author: JK
     * Select a random size from add to bag flip
     *
     * @return if the correct value is selected
     */
    public void selectQtyFromFlip(String qty) {
        selectDropDownByVisibleText(qtyDropDown, qty);
    }

    public void clickCloseProdCardView() {
        click(closeAddToBaglink);
        staticWait();
    }

    public boolean clickViewProductDetailsLink() {
        click(viewProdDetailsLink);
        return waitUntilElementDisplayed(addToBagBtn);
    }

    public boolean applySizeFilter(int i) {
        scrollUpToElement(sizeFilterLink);
        click(sizeFilterLink);
        staticWait(4000);//Applied filter takes time to be reflected on PLP
        if (sizeAttributes.size() >= 1) {
            for (int j = 0; j < i && j < sizeAttributes.size(); j++) {
                click(sizeAttributes.get(randInt(0, (sizeAttributes.size() - 1))));
                waitUntilElementDisplayed(selectedSize, 20);
            }
            click(sizeApplyBtn);
            staticWait(3000);//Applied filter takes time to be reflected on PLP
        }
        waitUntilElementsAreDisplayed(lnk_ClearColor, 60);
        return lnk_ClearColor.size() >= i;
    }


    /**
     * Created By Pooja
     * This Method allows to select specific size in Size filter and apply
     */
    public boolean applySizeFilterByTitle(String title) {
        scrollUpToElement(sizeFilterLink);
        click(sizeFilterLink);
        staticWait(4000);//Added as the size Filter section takes time to be loaded completely
        if (sizeAttributes.size() >= 1) {
            click(selectProdSizeByTitle(title));
            click(sizeApplyBtn);
        }
        return waitUntilElementsAreDisplayed(lnk_ClearColor, 60);

    }

    public boolean applyColorFilter(int i) {
        click(colorFilterLink);
        staticWait(3000);//Added as the color Filter section takes time to be loaded completely
        if (colorAttributes.size() >= 1) {
            for (int j = 0; j < i && j < colorAttributes.size(); j++) {
                click(colorAttributes.get(j));
                waitUntilElementDisplayed(selectedColor, 5);
            }
            click(sizeApplyBtn);
            staticWait(3000);
        }
        waitUntilElementsAreDisplayed(lnk_ClearColor, 10);
        return lnk_ClearColor.size() >= i;
    }

    public boolean validateCateGoryFilter() {
        return getText(departmentItems).contains("Girl") &&
                getText(departmentItems).contains("Toddler Girl") &&
                getText(departmentItems).contains("Boy") &&
                getText(departmentItems).contains("Toddler Boy") &&
                getText(departmentItems).contains("Baby") &&
                getText(departmentItems).contains("Shoes") &&
                getText(departmentItems).contains("Accessories");
    }

    public boolean applyDepartmentFilter(String category) {
        click(departmentFilter(category));
        waitUntilElementDisplayed(totalFilters);
        return getText(totalFilters).contains(category) && waitUntilElementsAreDisplayed(lnk_ClearColor, 10);
    }

    /**
     * Created By Pooja, This Method will click on Category Filter and select the category
     * Also verify the selected Filter is applied
     */
    public String applyCategoryFilter(int i) {
        if (waitUntilElementDisplayed(categoryFilter, 10)) {
            click(categoryFilter);
            String appliedFilter = getText(categoryList.get(i));
            click(categoryList.get(i));
            click(sizeApplyBtn);
            if (waitUntilElementsAreDisplayed(lnk_ClearColor, 20) && getText(totalFilters).contains(appliedFilter)) {
                return appliedFilter;
            }

        }
        addStepDescription("category drop down is not present");
        return null;
    }

    public boolean verifyCategoryFilter() {
        List<String> cat = new ArrayList<>();
        waitUntilElementDisplayed(categoryFilter);
        click(categoryFilter);
        for (WebElement sc : categoryList) {
            cat.add(sc.getText());
        }
        Set<String> noDupes = new HashSet<>(cat);
        return noDupes.size() == cat.size();
    }

    public boolean clearFilter(String filter) {
        scrollUpToElement(sizeFilterLink);
        int beforeSize = lnk_ClearColor.size();
        click(closeFilter(filter));
        staticWait(3000);//clearing a filter takes time to remove
        if (lnk_ClearColor.size() == beforeSize - 1) {
            return true;
        }
        return false;
    }

    public boolean applySizeAndColorFilters() {

        click(colorFilterLink);
        staticWait(1000);
        if (colorAttributes.size() >= 1) {
            click(colorAttributes.get(randInt(0, (colorAttributes.size() - 1))));
            waitUntilElementDisplayed(selectedColor, 5);
            String selectedColorAttribute = getText(selectedColor);
            staticWait(4000);
            click(sizeApplyBtn);
            staticWait(9000);
            getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedColorAttribute + "')]"));
        }

        waitUntilElementDisplayed(sizeFilterLink, 5);
        click(sizeFilterLink);
        staticWait(3000);
        if (sizeAttributes.size() >= 1) {
            click(sizeAttributes.get(randInt(0, (sizeAttributes.size() - 1))));
            waitUntilElementDisplayed(selectedSize, 5);
            String selectedSizeAttribute = getText(selectedSize);
            staticWait(4000);
            click(sizeApplyBtn);
            staticWait(9000);
            getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedSizeAttribute + "')]"));

        }
        return waitUntilElementDisplayed(filteringByText, 5);
    }

    public boolean checkErrMsgPLP() {
        openProdCardWithItem();
        click(addToBagBtn);
        return waitUntilElementDisplayed(selectSizeErrorPLP, 10);
    }

    public boolean closeAddtoBagFlip() {
        mouseHover(closeAddToBaglink);
        click(closeAddToBaglink);
        return !waitUntilElementDisplayed(addToBagBtn, 5);
    }

    public boolean validateBopisIcons(BopisOverlayActions bopisOverlayActions) {
        mouseHover(itemContainerWithBopisICons.get(0));
        if (waitUntilElementDisplayed(addToBopisIcon.get(0), 10)) {
            click(addToBopisIcon.get(0));
        } else if (waitUntilElementDisplayed(pickUpInStoreBtnOnPLP(1))) {
            click(pickUpInStoreBtnOnPLP(1));
        }
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 4);
    }

    public boolean checkTopBadgesByBadgeName(HeaderMenuActions headerMenuActions, CategoryDetailsPageAction categoryDetailsPageAction, String badgeSearch, String badgeDisplay) {
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, badgeSearch);
        waitUntilElementsAreDisplayed(topBadgesBy_BadgeName(badgeDisplay), 10);
        if (topBadgesBy_BadgeName(badgeDisplay).size() >= 1) {
            return waitUntilElementsAreDisplayed(topBadgesBy_BadgeName(badgeDisplay), 10);
        }
        return false;
    }

    public boolean verifyBadge() {
        return waitUntilElementsAreDisplayed(productBadge, 2);
    }

    public boolean checkInlineBadgesByBadgeName(HeaderMenuActions headerMenuActions, CategoryDetailsPageAction categoryDetailsPageAction, String badgeSearch, String badgeDisplay) {
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, badgeSearch);
        waitUntilElementsAreDisplayed(inlineBadgesBy_BadgeName(badgeDisplay), 10);
        if (inlineBadgesBy_BadgeName(badgeDisplay).size() >= 1) {
            return waitUntilElementsAreDisplayed(inlineBadgesBy_BadgeName(badgeDisplay), 10);
        }
        return false;
    }


    public String getFirstTopProductBadgeName(String badgeDisplay) {
        if (waitUntilElementDisplayed(getFirstElementFromList(topBadgesBy_BadgeName(badgeDisplay)), 2)) {
            return getText(topBadgesBy_BadgeName(badgeDisplay).get(0));
        } else {
            return "Not displaying in category page with " + badgeDisplay;
        }
    }

    public boolean clickFirstTopProductBadgeName(String badgeDisplay, ProductDetailsPageActions productDetailsPageActions) {
        if (waitUntilElementDisplayed(getFirstElementFromList(topBadgesBy_BadgeName(badgeDisplay)), 1)) {
            click(imageWithBadgeName(badgeDisplay).get(0));
            return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
        } else {
            return false;
        }
    }

    public boolean validateNextButtonOnProductImage() {
        //get Product count
        String firstimg = "", secondImg = "";
        int count = prodImgWith_Next().size();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                mouseHover(prodImgWith_Next().get(i));
                if (prodImgWith_Next().size() > 0) {
                    firstimg = getAttributeValue(itemImgContentWithNext(i + 1), "src");
                    click(prodImgButton_Next.get(0));
                    staticWait(1000);
                    secondImg = getAttributeValue(itemImgContentWithNext(i + 1), "src");
                    return !firstimg.equalsIgnoreCase(secondImg);
                }

            }

            return false;
        }
        addStepDescription("No Product Present");
        return false;
    }

    public boolean isProdImgPrevArrowButtonDisplay() {
        String firstimg = null, secondImg = null;
        int count = prodImgWith_Prev().size();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                mouseHover(prodImgWith_Prev().get(i));
                if (waitUntilElementsAreDisplayed(prodImgWith_Prev(), 5)) {
                    firstimg = getAttributeValue(itemImgContentWithPrev(i + 1), "src");
                    click(prev_ProdImgButton.get(0));
                    staticWait(3000);
                    secondImg = getAttributeValue(itemImgContentWithPrev(i + 1), "src");
                    return !firstimg.equalsIgnoreCase(secondImg);
                }

            }

            return false;
        }
        addStepDescription("No Product Present");
        return false;
    }

    public boolean sortByOptionPlp(String sortingValue) {
        boolean sort = false;
        waitUntilElementDisplayed(sortByOption, 15);
        click(sortByOption);
        waitUntilElementDisplayed(sortList);
        if (sortingValue.equalsIgnoreCase("New Arrivals")) {
            click(sort_NewArrivals);
            staticWait(5000);
            waitUntilElementDisplayed(fpo, 10);
            waitUntilElementsAreDisplayed(itemContainers, 20);
            sort = true;
        } else if (sortingValue.equalsIgnoreCase("Low to High")) {
            click(sort_LowToHigh);
            staticWait(5000);
            waitUntilElementDisplayed(fpo, 10);
            waitUntilElementsAreDisplayed(itemContainers, 4);
            int count = offerPrice.size();
            int i = count - 1;
            String price1 = getText(offerPrice.get(0)).replace("$", "");
            String priceLast = getText(offerPrice.get(i)).replace("$", "");
            double firstPrice = Float.valueOf(price1);
            double lastItemPrice = Float.valueOf(priceLast);
            if (lastItemPrice >= firstPrice) {
                sort = true;
            }
        } else if (sortingValue.equalsIgnoreCase("High to Low")) {
            click(sort_HighToLow);
            staticWait(5000); //this wait required to wait until the data load completed for sort //TO DO: need to see better appraoch
            waitUntilElementDisplayed(fpo, 10);
            waitUntilElementsAreDisplayed(itemContainers, 60);
            int count = offerPrice.size();
            int i = count - 1;
            String price1 = getText(offerPrice.get(0)).replace("$", "");
            String priceLast = getText(offerPrice.get(i)).replace("$", "");
            double firstPrice = Float.valueOf(price1);
            double lastItemPrice = Float.valueOf(priceLast);
            if (lastItemPrice <= firstPrice) {
                sort = true;
            }
        } else if (sortingValue.equalsIgnoreCase("TOP RATED")) {
            click(sort_topRated);
            staticWait(5000);
            waitUntilElementDisplayed(fpo, 10);
        } else {
            click(sort_MostFavorite);
            waitUntilElementDisplayed(fpo, 10);
            waitUntilElementsAreDisplayed(itemContainers, 20);
            sort = true;
        }
        return sort;
    }

    public int getProductsCount() {
        waitUntilElementsAreDisplayed(itemContainers, 20);
        return itemContainers.size();
    }

    public boolean clickOutfitsImageByPosition(int i) {
        if (productImages.size() >= 1) {
            mouseHover(productImages.get(i));
            click(shopTheLook.get(i));
            return driver.getCurrentUrl().contains("outfit");
        }
        return false;
    }

    public boolean checkOutfitDisplay(CategoryDetailsPageAction categoryDetailsPageAction, HomePageActions homePageActions, DepartmentLandingPageActions departmentLandingPageActions, String urlValue, int i) {

        boolean isProdAvailable = true;
        staticWait(4000);
        waitUntilElementDisplayed(outfitProdImage(i));
        isProdAvailable = categoryDetailsPageAction.waitUntilElementClickable(outfitProdImage(i), 5);

        if (isProdAvailable) {
            homePageActions.selectDeptWithName(departmentLandingPageActions, "Toddler Girl");
            staticWait();
            departmentLandingPageActions.outfits_Lnk.click();
            waitUntilElementDisplayed(outfitProdImage(i));
            String currentUrl = getCurrentURL();

            if (isProdAvailable) {
                homePageActions.selectDeptWithName(departmentLandingPageActions, "Boy");
                staticWait();
                departmentLandingPageActions.outfits_Lnk.click();
                waitUntilElementDisplayed(outfitProdImage(i));
                String currentUrl1 = getCurrentURL();


                if (isProdAvailable)
                    homePageActions.selectDeptWithName(departmentLandingPageActions, "Toddler Boy");
                staticWait();
                departmentLandingPageActions.outfits_Lnk.click();
                waitUntilElementDisplayed(outfitProdImage(i));
                String currentUrl2 = getCurrentURL();

                return currentUrl.contains(urlValue) && currentUrl1.contains(urlValue) && currentUrl2.contains(urlValue);
            }
        }
        return waitUntilElementDisplayed(outfitProdImage(i), 3);
    }

    public boolean selectASizeAndAddToBag(HomePageActions homePageActions) {
        staticWait();
        waitUntilElementsAreDisplayed(sizeDropdown, 10);
        if (availableFits.size() >= 1) {
            click(availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(5000);
        }
        click(addToBag.get(randInt(0, addToBag.size() - 1)));
        waitUntilElementDisplayed(sizeErrMsg, 3);
        staticWait(5000);
        if (selectAvailableSize.size() >= 0) {
            click(selectAvailableSize.get(randInt(0, selectAvailableSize.size() - 1)));
            waitUntilElementClickable(selectSizeAddToBag, 20);
            click(selectSizeAddToBag);
        }
        return waitUntilElementDisplayed(homePageActions.shoppingBagIcon, 5);
    }

    public boolean validateOnlineOnlyL2Page(int i) {
        waitUntilElementDisplayed(searchOnlineOnly, 5);
        click(productImages.get(i));
        return waitUntilElementDisplayed(onlineOnly_PDP, 5);

    }

    public boolean clickNextButtonOnProductImg(int i) {
        mouseHover(itemImg(i));
        String getCurrentImg = itemImg(i).getAttribute("src");
        click(prodImgButton_Next.get(i - 1));
        staticWait();
        String getAltImg = itemImg(i).getAttribute("src");
        return !getCurrentImg.equalsIgnoreCase(getAltImg);
    }

    public boolean checkProdImgPrevArrowButtonDisplay(int i) {

        mouseHover(itemImg(i));
        waitUntilElementDisplayed(prev_ProdImgButton.get(i), 3);
        String getCurrentImg = itemImg(i).getAttribute("src");
        click(prev_ProdImgButton.get(i - 1));
        staticWait();
        String getAltImg = itemImg(i).getAttribute("src");
        return !getCurrentImg.equalsIgnoreCase(getAltImg);
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

    public boolean l3CategotyDisplay() {
        return waitUntilElementDisplayed(l3LinksDisplay);
    }

    public String getActiveL2() {
        return getText(activel2CategoryInLeftNavigationPane);
    }

    public boolean clickL3category() {
        waitUntilElementsAreDisplayed(l3CategoryLink, 3);
        int size = l3CategoryLink.size();
        try {
            if (size > 1) {
                click(l3CategoryLink.get(randInt(0, (l3CategoryLink.size() - 1))));
            } else if (l3CategoryLink.size() == 1) {
                click(l3CategoryLink.get(0));
            }

            if (waitUntilElementDisplayed(firstImg, 30)) {
                return true;
            }
        } catch (Throwable t) {
            logger.info("item(s) are unavailable " + t.getMessage());
        }
        return false;
    }

    public boolean l3CategoryPageDisplay() {
        waitUntilElementDisplayed(firstImg, 3);
        if (isDisplayed(sortByOption) && isDisplayed(sizeFilterLink) && isDisplayed(colorFilterLink) && isDisplayed(breadCrumb)) {
            return true;
        } else return false;
    }

    public boolean clickOnBreadcrumb() {
        waitUntilElementDisplayed(breadcrumbL2CategoryLink, 4);
        click(breadcrumbL2CategoryLink);
        return waitUntilElementDisplayed(firstImg, 4);
    }

    public String discountPriceCheck() {
        String color = "";
        if (waitUntilElementsAreDisplayed(offerPrice, 3)) {
            color = offerPrice.get(0).getCssValue("color");
            return convertRgbToTextColor(color);
        } else
            addStepDescription("Discount price is not available for the products");

        return convertRgbToTextColor(color);
    }

    public String actualPriceCheck() {
        waitUntilElementsAreDisplayed(actualPrice, 20);
        String color = actualPrice.get(0).getCssValue("color");
        return convertRgbToTextColor(color);
    }

    public boolean clickInlineBadgeBy_BadgeName(String badgeName) {
        int badgeSize = inlineBadgesBy_BadgeName(badgeName).size();
        if (badgeSize > 0) {
            click(inlineBadgesBy_BadgeName(badgeName).get(randInt(0, badgeSize - 1)));
            return waitUntilElementDisplayed(addToBagBtn, 20);
        }
        addStepDescription("Inline Badges are not showing with the term " + badgeName);
        return false;
    }

    public boolean verifyLazyLoad() {
        scrollToBottom();
        return waitUntilElementDisplayed(lazyloading);
    }

    public void scrollDownUntilLangButtonAppear() {
        int counter = 0;
        while (!waitUntilElementDisplayed(languageButton, 5) && counter <= 20) {
            scrollToBottom();
            counter++;
        }
    }

    public boolean verifyLazyLoadWhileSorting() {
        waitUntilElementDisplayed(sortByOption, 15);
        click(sortByOption);
        click(sort_MostFavorite);
        return waitUntilElementDisplayed(lazyloading);
    }


    /**
     * Created By Pooja on 10th May,2018
     * This method verifies the size  selected on Bag Flip is same as applied in Size filter
     * and removes the filter
     * Also select one of the color swatch if multiple color swatch displayed
     */
    public boolean verifyFilteredSizeSelectedOnProdCard() {
        if (waitUntilElementDisplayed(appliedSizeFilterValue, 10)) {
            String appliedFilter = getText(appliedSizeFilterValue);
            if (appliedFilter.equals(getAttributeValue(alreadySelectedSizeBtn_value, "value"))) {
                addStepDescription("Applied Size filter =" + appliedFilter + "is defaulted=" + getAttributeValue(alreadySelectedSizeBtn_value, "value") + " on Bag Flip");
                return true;
            }

        } else {
            addStepDescription("Filters are not Applied");
            return false;
        }
        addStepDescription("Applied Size filter =" + appliedFilter + "is defaulted=" + getAttributeValue(alreadySelectedSizeBtn_value, "value") + " on Bag Flip");
        return false;
    }

    /**
     * Created By Pooja on 10th May,2018
     * This method verifies the color  selected on Bag Flip is same as applied in color filter
     */
    public boolean verifyColorSelectedOnProdCard(String color) {
        if (getText(appliedColorFilterValue).equalsIgnoreCase(color)) {
            List<String> colors = Arrays.asList("SNOW", "WHITE", "SIMPLYWHT", "BLACK");
            String defaultSelectedColor = getAttributeValue(alreadySelectedColor_value, "value");
            if (colors.contains(defaultSelectedColor)) {
                return true;
            }

        } else {
            addStepDescription(color + " Color Filter is not Applied");
            return false;
        }
        addStepDescription("Applied Size filter =" + getText(appliedColorFilterValue) + "is defaulted=" + getAttributeValue(alreadySelectedColor_value, "value") + " on Bag Flip");
        return false;
    }

    /**
     * Created By Pooja on 10th May,2018
     * This method removes the Size/Color/Category filter regardless of values selected
     */
    public boolean removeFilters() {
        if (waitUntilElementsAreDisplayed(lnk_ClearColor, 10)) {
            scrollUpToElement(sizeFilterLink);
            int size = lnk_ClearColor.size();
            for (int i = 0; i < size; i++) {
                click(lnk_ClearColor.get(0));
                staticWait(3000);//clicking cross icon on selected Filter takes time to be removed
            }
            return true;
        } else {
            addStepDescription("Filters are not Applied and Remove icon was not there");
            return false;
        }
    }


    public boolean loadingIcon() {
        if (!isDisplayed(loadingIcon)) {
            return true;
        } else {
            addStepDescription("Loading Icon is displayed in PLP");
            return false;
        }
    }

    /**
     * Created By Pooja
     * This Method returns the title of the product
     */
    public String getProductTitle(int i) {
        waitUntilElementDisplayed(itemName(i), 10);
        return getText(itemName(i));
    }

    /**
     * Created By Pooja
     * This Method apply the color Filter as per color Title
     */
    public boolean applyColorFilterByTitle(String title) {
        click(colorFilterLink);
        staticWait(3000);//as color Filter Section takes time to load completely
        if (waitUntilElementDisplayed(colorAttributesByTitle(title), 5)) {
            click(colorAttributesByTitle(title));
            staticWait(3000);//color gets deselected
            click(sizeApplyBtn);
            staticWait(3000);
            return waitUntilElementsAreDisplayed(lnk_ClearColor, 5);
        }
        addStepDescription(title + "-color is not Present in the Filter");
        return false;
    }

    /**
     * Created By Pooja, This method verifies the count of Filters applied
     *
     * @param i - Number of Filters to be verified
     */
    public boolean verifyAppliedFiltersCount(int i) {
        waitUntilElementsAreDisplayed(lnk_ClearColor, 20);
        return lnk_ClearColor.size() == i;
    }

    public boolean applyMultipleFilter() {
        click(colorFilterLink);
        staticWait(1000);
        if (colorAttributes.size() >= 1) {
            click(colorAttributes.get(randInt(0, (colorAttributes.size() - 1))));
            waitUntilElementDisplayed(selectedColor, 5);
            String selectedColorAttribute = getText(selectedColor);
            staticWait(4000);
            click(sizeApplyBtn);
            staticWait(9000);
            getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedColorAttribute + "')]"));
        }

        waitUntilElementDisplayed(sizeFilterLink, 5);
        click(sizeFilterLink);
        staticWait(3000);
        if (sizeAttributes.size() >= 1) {
            click(sizeAttributes.get(randInt(0, (sizeAttributes.size() - 1))));
            waitUntilElementDisplayed(selectedSize, 5);
            String selectedSizeAttribute = getText(selectedSize);
            staticWait(4000);
            click(sizeApplyBtn);
            staticWait(9000);
            getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedSizeAttribute + "')]"));

        }
        waitUntilElementDisplayed(priceFilterLink, 2);
        click(priceFilterLink);
        staticWait(3000);
        if (sizeAttributes.size() >= 1) {
            click(sizeAttributes.get(randInt(0, (sizeAttributes.size() - 1))));
            waitUntilElementDisplayed(selectedSize, 5);
            String selectedSizeAttribute = getText(selectedSize);
            staticWait(4000);
            click(sizeApplyBtn);
            staticWait(9000);
            getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedSizeAttribute + "')]"));
        }
        if (isDisplayed(fitFilterLink)) {
            click(fitFilterLink);
            staticWait(3000);
            if (sizeAttributes.size() >= 1) {
                click(sizeAttributes.get(randInt(0, (sizeAttributes.size() - 1))));
                waitUntilElementDisplayed(selectedSize, 5);
                String selectedSizeAttribute = getText(selectedSize);
                staticWait(4000);
                click(sizeApplyBtn);
                staticWait(9000);
                getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedSizeAttribute + "')]"));
            }
        }
        if (isDisplayed(genderFilterLink)) {
            click(genderFilterLink);
            staticWait(3000);
            if (sizeAttributes.size() >= 1) {
                click(sizeAttributes.get(randInt(0, (sizeAttributes.size() - 1))));
                waitUntilElementDisplayed(selectedSize, 5);
                String selectedSizeAttribute = getText(selectedSize);
                staticWait(4000);
                click(sizeApplyBtn);
                staticWait(9000);
                getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedSizeAttribute + "')]"));
            }
        }
        if (isDisplayed(sizeRangeFilterLink)) {
            click(sizeRangeFilterLink);
            staticWait(3000);
            if (sizeRangeAttributes.size() >= 1) {
                click(sizeRangeAttributes.get(randInt(0, (sizeRangeAttributes.size() - 1))));
                waitUntilElementDisplayed(selectedSize, 5);
                String selectedSizeAttribute = getText(selectedSize);
                staticWait(4000);
                click(sizeApplyBtn);
                staticWait(9000);
                getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + selectedSizeAttribute + "')]"));
            }
        }
        return waitUntilElementDisplayed(filteringByText, 5);
    }

    public boolean OutfitUnavailble() {
        addStepDescription("Outfits are unavailable in the Outfit Landing page");
        return false;
    }

    /**
     * Created By Pooja
     *
     * @param levelThree to click on
     * @return true if the selected level three is highlighted
     */
    public boolean clickL3Categ(String levelThree) {
        waitUntilElementDisplayed(levelThreeLink(levelThree), 15);
        click(levelThreeLink(levelThree));
        return waitForTextToAppear(levelThreeLinkActive(levelThree), levelThree, 20);
    }

    public boolean checkFavStoreDisplay(String store) {

        waitUntilElementDisplayed(changedFavStoreName, 2);
        String currentStore = getText(changedFavStoreName).toLowerCase();
        if (currentStore.equalsIgnoreCase(store)) {
            return true;
        } else {
            addStepDescription("Favorite store displayed in PLP is different");
            return false;
        }

    }

    public boolean ratingDisplay() {
        if (waitUntilElementsAreDisplayed(rating_ProductTile, 2)) {
            return true;
        } else {
            addStepDescription("Change the search term and check the Product badge Display");
            return false;
        }
    }

    /**
     * Clicks on the first product mathces with badge name
     *
     * @param productDetailsPageActions
     * @param badgeName                 to be checked
     * @return
     */
    public boolean clickFirstProductMatchWIthBadge(ProductDetailsPageActions productDetailsPageActions, String badgeName) {
        waitUntilElementsAreDisplayed(badgeProducts(badgeName), 10);
        click(badgeProducts(badgeName).get(0));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 10);
    }
}
