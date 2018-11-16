package ui.pages.actions;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.SearchResultsPageRepo;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPageActions extends SearchResultsPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(HomePageActions.class);
    public int counter = 0;

    public SearchResultsPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean searchResultsBySearchTerm(String searchTerm) {
        try {
            return getText(searchResultsTerm).replaceAll("\"", "").equalsIgnoreCase(searchTerm);
        }catch (NullPointerException e){
            return false;
        }
    }

    public void selectAnyProductViaQuickView() {
        if (quickViewLinksAll != null) {
            if (quickViewLinksAll.size() > 1) {
                click(quickViewLinksAll.get(randInt(0, (quickViewLinksAll.size() - 1))));
            } else if (quickViewLinksAll.size() == 1) {
                quickViewLinksAll.get(0).click();
            }
        }
    }

    public boolean youSearchedForText(String searchString) {
        waitUntilElementDisplayed(youSearchedFor, 30);
        String actual = getText(youSearchedFor);
        String expected = "You searched for " + "\"" + searchString + "\"";
        return actual.equalsIgnoreCase(expected);
    }

    /**
     * Created By Pooja
     * This Method verifies the Application is on Content Landing Page
     */
    public boolean verifyContentLandingPage(String searchString) {
        if (waitUntilElementsAreDisplayed(ContentLandingPageBrdcrumb, 10)) {
            return ContentLandingPageBrdcrumb.size() == 1 && getText(ContentLandingPageBrdcrumb.get(0)).equals(searchString);
        }

        addStepDescription("Not Landed on Content landing Page");
        return false;
    }

    public boolean clickOnProductImageByPosition(int i, ProductDetailsPageActions productDetailsPageActions) {
        click(productImageByPosition(i));
        staticWait(3000);
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }


    public List<String> getAllProductName() {
        List<String> names = new ArrayList<>();
        waitUntilElementsAreDisplayed(productNames, 30);
        for (WebElement e : productNames) {
            names.add(e.getText());
        }
        return names;
    }

    public boolean verifyGhostText_SearchKeyword(String searchTerm) {
        checkSearchGhostText("Search");
        clearAndFillText(searchBox, searchTerm);
        return waitUntilElementDisplayed(search_SuggestedKey, 20) || waitUntilElementsAreDisplayed(search_SuggestedCategory, 20);
    }

    public boolean isIamLookingForDisplayInSuggestions() {
        clearAndFillText(searchBox, "Shorts");
        waitUntilElementDisplayed(search_SuggestedKey, 10);
        System.out.println("I am looking for test: " + getText(getFirstElementFromList(search_SuggestedCategory)));
        return getText(getFirstElementFromList(search_SuggestedCategory)).toLowerCase().contains("looking for");

    }

    public boolean checkSearchGhostText(String SearchText) {
        if (driver.findElement(By.cssSelector("[name='typeahead']")).getAttribute("placeholder").toString().equalsIgnoreCase(SearchText))
            return true;
        else
            return false;
    }

    public void selectAnySuggestedKeywords(String GlobalBrowser) throws InterruptedException {
        staticWait();
        List<WebElement> list = this.driver.findElements(By.xpath("//*[@id='suggestedKeywordResults']//li//a/strong"));
        if (list.size() > 1) {
            WebElement text = list.get(randInt(0, (list.size() - 1)));
            mouseClick(text);
        } else if (list.size() == 1) {
            list.get(0).click();

        }
        acceptAlert();
    }


    public void selectAnySuggestedCategory(String GlobalBrowser) throws InterruptedException {

        List<WebElement> list = this.driver.findElements(By.xpath("//*[@id='autoSuggestStatic_1'] //li //a"));
        waitUntilElementsAreDisplayed(list, 5);
        if (list.size() > 1) {
            WebElement text = list.get(randInt(0, (list.size() - 1)));
            mouseClick(text);
        } else if (list.size() == 1) {
            click(list.get(0));

        }
    }

    public boolean findProductNamesInResults(String ProductNames) {
        boolean Foundflag = false;
        List<WebElement> list = driver.findElements(By.xpath("//*[@class='product productLarge']//div[@class='productRow name']//span"));
        for (WebElement element : list) {
            if (element.getText().trim().toLowerCase().contains(ProductNames.toLowerCase().trim())) {
                Foundflag = true;
                break;
            }
        }
        if (Foundflag) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyZeroResultPage() {
        if (searchResultsTerm.getText().toLowerCase().trim().contains("results for") &&
                searchResultsTerm.getText().trim().contains("\"@#@#\"") &&
                searchResultsTerm.getText().trim().contains("0 matches"))
            return true;
        else
            return false;
    }

    public boolean verifyHintsForSearching() {
        if (hintsForSearching.getText().trim().contains("Tips for searching:") &&
                hintsForSearching.getText().trim().contains("Check your spelling") &&
                hintsForSearching.getText().trim().contains("Use simplified item keywords (jeans, tee, top, hat, dress)") &&
                hintsForSearching.getText().trim().contains("Try broader searches and then narrow your results") &&
                hintsForSearching.getText().trim().contains("Try searching by themes or specific categories (dinosaur, bear, active, uniform, pj set)"))
            return true;
        else
            return false;

    }

    public boolean verifyValidSearchTermInZeroResultPage(String term) {
        clearAndFillText(txt_SearchBox, term);
        click(btn_SearchSubmit);

        if (searchResultsTerm.getText().toLowerCase().contains("results for") &&
                searchResultsTerm.getText().contains(term))
            return true;
        else
            return false;
    }

    public boolean verifyLeftNavigationFacets() {

        if (isDisplayed(subCatSection) &&
                isDisplayed(link_SizeFacet) &&
                isDisplayed(link_ColorFacet))
            return true;
        else
            return false;
    }

    public void selectAnyColor() throws InterruptedException {
        try {
            Actions actions = new Actions(getDriver());

            int random_num = 0;
            List<WebElement> list = driver.findElements(By.xpath("//*[@id='choice-Color'] //li[not(@class='selected')] //img"));
            if (list.size() > 1) {
                random_num = randInt(0, (list.size() - 1));
            }

            actions.moveToElement(list.get(random_num)).perform();
            actions.click().build().perform();
            verifyColorIsSelected();
        } catch (Exception e) {
            staticWait(20);
            if (driver.findElements(By.xpath("//*[@id='choice-Color'] //li[(@class='selected')] //img")).size() == 0)
                selectAnyColor();
        }
    }

    public void selectAnySize() throws InterruptedException {  //Added by Badhu

        try {
            List<WebElement> list = this.driver.findElements(By.xpath("//*[@id='choice-Size'] //li[not(@class='selected')] //a"));
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
            if (driver.findElements(By.id("Size-clear")).size() == 0 && totalCount > counter) {
                driver.navigate().back();
                staticWait(10);
                counter = counter + 1;
                if (driver.findElements(By.xpath("//a[@id='Size-clear'][@style='display:block']")).size() == 0) {
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


    public boolean function_LeftNav_SelectAnySizeAndColor() throws InterruptedException {
        click(link_ColorFacet);
        waitUntilElementDisplayed(color_Options);
        selectAnyColor();

        click(link_SizeFacet);
        waitUntilElementDisplayed(size_Options);
        selectAnySize();

        return true;
    }


    public boolean function_LeftNav_VerifySizeAndColorOptionsAreDisplayed() throws InterruptedException {

        click(link_ColorFacet);
        waitUntilElementDisplayed(color_Options);

        click(link_SizeFacet);
        waitUntilElementDisplayed(size_Options);

        return true;
    }

    public void verifyColorIsSelected() throws InterruptedException {
        staticWait();
        clearColor().isDisplayed();
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


    public boolean breadCrumbVerification(String searchTerm, HomePageActions homePageActions) {
        String breadCrumb_Cat = breadCrumb.getText();

        if (breadCrumb_Cat.contains("Home") && breadCrumb_Cat.contains(searchTerm))
            return true;
        else
            return false;
    }

    public boolean verifyClearLinks() {
        if (isDisplayed(lnk_ClearColor) &&
                isDisplayed(lnk_ClearSize) &&
                isDisplayed(lnk_ClearAll))
            return true;
        else
            return false;
    }


    public boolean verifyAllProductsInfo() {
        int numofProdNames = driver.findElements(By.xpath("//*[@class='product productLarge'] //div[@class='productRow name'] //span")).size();
        int numofProdImages = driver.findElements(By.cssSelector(".product.productLarge .productImage img")).size();
        int numofProdPrices = driver.findElements(By.xpath("//*[@class='product productLarge'] //*[@class='price productRow'] //p")).size();
        int numOfQuickViews = driver.findElements(By.cssSelector(".quick-view a")).size();
        int numOfSwatches = driver.findElements(By.cssSelector(".quick-view a")).size();

        if (numofProdImages == numofProdNames & numofProdNames == numofProdPrices & numofProdPrices == numOfQuickViews & numOfQuickViews == numOfSwatches)
            return true;
        else
            return false;

    }


    public boolean selectShopByCategory(String CategoryString) throws InterruptedException {
        for (WebElement element : driver.findElements(By.xpath("//*[contains(@class,'leftNavLevel0')] //a"))) {
            if (element.getText().toString().trim().toLowerCase().equals(CategoryString.trim().toLowerCase())) {
                element.click();
                break;
            }
        }

        return waitUntilElementDisplayed(searchResultsTerm, 10);
    }

    //DT2 Actions

    public boolean validateSearchBar(String ghostTxt) {
        boolean isSearchBarPresent = waitUntilElementDisplayed(searchBox, 3);
        boolean isGhostTextDisplayed = checkSearchGhostText(ghostTxt);
        boolean isSearchButtonDisplayed = waitUntilElementDisplayed(searchSubmitButton, 3);


        boolean validateSearch = isSearchBarPresent && isGhostTextDisplayed && isSearchButtonDisplayed;
        return validateSearch;
    }


    public boolean clickAddToFavByPosAsGuest(LoginPageActions loginPageActions, int i) {

        mouseHover(productImageByPosition(i));
        click(addToFavIconByPos(i));

        return waitUntilElementDisplayed(loginPageActions.loginButton, 30);
    }

    public boolean clickPicUpInStoreIconByPos(BopisOverlayActions bopisOverlayActions, int i) {

        mouseHover(productImageByPosition(i));
        click(pickUpInStoreIconByPos(i));

        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 30);
    }

    public boolean validateEmptySearchResults(String msgContent, String searchTerm) {
        waitUntilElementDisplayed(noResults_SearchField, 5);
        return getText(emptySearchLblContent).contains(searchTerm) &&
                waitUntilElementDisplayed(searchTipsLbl, 5);
    }

    public boolean isListPriceDisplayBelowOfferPriceAndColor(String color) {
        int randomProductPr = randInt(0, productsListPrice.size() - 1);
        String listPrColor = convertRgbToTextColor(productsListPrice.get(0).getCssValue("color"));
        boolean listPrDisplayColor = listPrColor.equalsIgnoreCase(color);
        double listPrice = Double.valueOf(getText(productsListPrice.get(randomProductPr)).replaceAll("[^0-9.]", ""));
        double offerPrice = Double.valueOf(getText(productsOfferPrice.get(randomProductPr)).replaceAll("[^0-9.]", ""));
        boolean priceCompare = listPrice > offerPrice;
        boolean listPrBelowOfferPr = waitUntilElementsAreDisplayed(listPrDisplayBelowOfferPr, 1);
        addStepDescription("List price display below offer price " + listPrBelowOfferPr + " List price color " + listPrDisplayColor + " list price should be grater than offer price " + priceCompare);
        return listPrBelowOfferPr && listPrDisplayColor && priceCompare;
    }

    public boolean verifySearchNotFoundPage() {
        return waitUntilElementDisplayed(pageNotFound_Page);
    }

    public void searchfromEmptySearch(String text) {
        clearAndFillText(noResults_SearchField, text);
        click(noResults_searchIcon);
    }

    public boolean verifyProductNoinSearchResults(String productNo) {
        int endIndex = productNo.indexOf("_");
        productNo = productNo.substring(0, endIndex);
        List<String> imgs = new ArrayList<>();
        boolean pro = true;
        for (WebElement image : productImages)
            imgs.add(image.getAttribute("src"));

        for (String img : imgs) {
            if (!img.contains(productNo)) {
                pro = false;
                break;
            }
        }
        return pro;
    }

    public boolean clickClearanceProductAddToBagByPos(ProductCardViewActions productCardViewActions, int i, String searchTerm) {
        mouseHover(prodImgByBadgeAndPos(searchTerm, i));
        click(prodAddToBagIconsByBadgeName(searchTerm, i));
        return waitUntilElementDisplayed(productCardViewActions.addToBagBtn);
    }


    /**
     * Created By Pooja on 3rd May,2018
     * This Method clicks on Add to favourite icon on SRP Product Tile and also store the name of fav item added in a global variable
     *
     * @param:-position of product tile
     * Expected result:- Add to Favourite Button on Product Tile should be displayed Selected
     */
    public boolean verifyNoDuplicatesInSearchSuggestionList() {
        for (int i = 0; i < searchSuggestionList_values.size(); i++) {
            for (int j = i + 1; j < searchSuggestionList_values.size(); j++) {
                if (getText(searchSuggestionList_values.get(i)) != "CATEGORY" || getText(searchSuggestionList_values.get(i)) != "I'M LOOKING FOR..." &&
                        getText(searchSuggestionList_values.get(j)) != "CATEGORY" || getText(searchSuggestionList_values.get(j)) != "I'M LOOKING FOR...") {
                    if (getText(searchSuggestionList_values.get(i)) == getText(searchSuggestionList_values.get(j))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Created By Pooja on 12th May,2018
     * This Method will hover mouse over the product Tile and click on Pick Up In Store icon,
     * @param:-i=position of product tile
     * Expected Result:- Add to Bag button on Product Tile should be displayed
     */
    public boolean clickPickUpInStoreByPos(CategoryDetailsPageAction categoryDetailsPageAction, BopisOverlayActions bopisOverlayActions, int i) {
//        mouseHover(itemContainerWithBopisICons.get(i));
        if (waitUntilElementsAreDisplayed(itemContainerWithBopisICons, 10)) {
            if (itemContainerWithBopisICons.size() == 1) {
                mouseHover(itemContainerWithBopisICons.get(0));
                click(categoryDetailsPageAction.addToBopisIcon.get(0));
            } else {
                mouseHover(itemContainerWithBopisICons.get(i));
                click(categoryDetailsPageAction.addToBopisIcon.get(i));
            }
            return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 10);
        }
//        else if(waitUntilElementsAreDisplayed(getProductsWithBopisBtn,10)){
//            if (getProductsWithBopisBtn.size() == 1) {
//                mouseHover(getProductsWithBopisBtn.get(0));
//                click(categoryDetailsPageAction.pickUpInStoreBtnOnPLP(1));
//            } else {
//                mouseHover(getProductsWithBopisBtn.get(i));
//                click(categoryDetailsPageAction.pickUpInStoreBtnOnPLP(i+1));
//            }
//            return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 10);
//        }
        else {
            addStepDescription("Product with Bopis enabled is not present");
            return false;
        }
    }

    public boolean searchSuggestionCheck(){
        waitUntilElementDisplayed(searchBox,2);
        clearAndFillText(searchBox,"T");
        if(isDisplayed(suggestion)){
            addStepDescription("Suggestion are displayed for Single character and Check DT-42917");
            return false;
        }
        else{
            addStepDescription("DT-42917 is working fine");
            return true;
        }
    }

}