package uiMobile.pages.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileHeaderMenuRepo;

/**
 * Created by JKotha on 09/11/2017.
 */
public class MobileHeaderMenuActions extends MobileHeaderMenuRepo {
    WebDriver mobileDriver = null;
    public String storeName;

    public MobileHeaderMenuActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean isRewards_WL_ShoppingBagImgAndCheckoutLinksDisplaying() {
        return waitUntilElementDisplayed(rewardsLink, 30) && waitUntilElementDisplayed(shoppingCartImg, 30) &&
                waitUntilElementDisplayed(wishListLink, 30) && waitUntilElementDisplayed(checkoutButton, 30);
    }

    public boolean openCreateaccountPage(MCreateAccountActions createaccount, PanCakePageActions sideMenu) {
        sideMenu.navigateToMenu("CREATEACCOUNT");
        return waitUntilElementDisplayed(createaccount.createAccountTitle);
    }

    public boolean verifypaypalInAddToBagNotification() {
        waitUntilElementDisplayed(addtoBagNotification);
        return waitUntilElementDisplayed(paypalFromNotification);
    }

    public void clickMenuIcon() {
        click(menuIcon);
    }

    public boolean isUserLogOut() {
        return waitUntilElementDisplayed(loginLinkHeader, 10);
    }

    public boolean searchUsingKeyWordLandOnPDP(MProductDetailsPageActions productDetailsPageActions, String searchItem) {
        clickSearchIconIfDisplay();
        clearAndFillText(searchBox, searchItem);
//        click(searchSubmitButton);
        keyBoard(searchBox, "ENTER");
        //staticWait(5000);
        if (isAlertPresent()) {
            acceptAlert();
        }
        return waitUntilElementDisplayed(productDetailsPageActions.colorSwatches, 10);
    }

    public boolean validateGlobalNavBanner() {
        return waitUntilElementDisplayed(globalMarketBanner, 10);
    }

    public boolean clickLogin() {
        MLoginPageActions loginDrawerActions = new MLoginPageActions(mobileDriver);
        waitUntilElementDisplayed(loginLinkOverlay);
        click(loginLinkOverlay);
        return waitUntilElementDisplayed(loginDrawerActions.emailAddrField);
    }

    public String getQty() {
        waitUntilElementDisplayed(shoppingBagIcon);
        staticWait(5000);
        return getText(shoppingBagIcon);
    }

    public void clickOnTCPLogo() {
        javaScriptClick(mobileDriver, TCPLogo);
        staticWait();
    }

    /**
     * Created By Pooja
     * This Method click on Menu Icon>Favourites link and verify Favourite Page is displayed
     */
    public boolean moveToWishListAsReg(PanCakePageActions panCakePageActions, MobileFavouritesActions mobileFavouritesActions) {
        click(menuIcon);
        click(panCakePageActions.favoritesLink);
        return waitUntilElementDisplayed(mobileFavouritesActions.defaultWLName);
    }

    public boolean clickWishListOverlayAsGuest(MobileFavouritesActions mobileFavouritesActionss) {
        click(wishlistOverlay);
        if (isUserLogOut()) {
            return waitUntilElementDisplayed(mobileFavouritesActionss.loginButton);
        }
        return false;
    }


    public boolean clickLoginLnk() {
        MLoginPageActions loginDrawerActions = new MLoginPageActions(mobileDriver);
        waitUntilElementDisplayed(loginLinkHeader, 20);
        staticWait(5000);
        click(loginLinkHeader);
        return waitUntilElementDisplayed(loginDrawerActions.emailAddrField, 30);
    }

    public boolean clickShoppingBagIcon() {
        MShoppingBagPageActions MShoppingBagPageActions = new MShoppingBagPageActions(mobileDriver);
        waitUntilElementDisplayed(shoppingBagIcon, 20);
        scrollToTop();
        if (waitUntilElementDisplayed(addtoBagClose, 10)) {
            staticWait(5000); // this wait required to close the add to bag confirmation, clicking on close will throw exception
        }
        javaScriptClick(mobileDriver, shoppingBagIcon);
        return waitUntilElementDisplayed(MShoppingBagPageActions.checkoutBtn, 20) || waitUntilElementDisplayed(MShoppingBagPageActions.emptyBagMessage, 10);
    }

    public boolean closeTheVisibleDrawer() {
        waitUntilElementDisplayed(closeTheOpenDrawerBtn, 10);
        click(closeTheOpenDrawerBtn);
        return !(waitUntilElementDisplayed(closeTheOpenDrawerBtn, 5));
    }

    public boolean isProductAddedToBag() {
        MShoppingBagPageActions shoppingBagDrawerActions = new MShoppingBagPageActions(mobileDriver);
        clickShoppingBagIcon();
        waitUntilElementDisplayed(shoppingBagDrawerActions.checkoutButton);
        return closeTheVisibleDrawer();
    }

    public boolean clickViewBagButtonFromAddToBagConf(MShoppingBagPageActions shoppingBagPageActions) {
        click(viewBagButtonFromAddToBagConf);
        return waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle, 30);
    }

    @Deprecated// //Disable this as per new design - DTN-2237
    public boolean clickPayPal(MPayPalPageActions payPalPageActions) {
        waitUntilElementDisplayed(payPalFrame, 10);
        switchToFrame(payPalFrame);
        waitUntilElementDisplayed(paypalCheckoutOnPaypalModal, 10);
        click(paypalCheckoutOnPaypalModal);
        return getCurrentWindowHandles() == 2;
    }

    /**
     * Created By Pooja
     * This method clicks on search icon and searches for a text and clicks on Submit button
     */
    public boolean searchAndSubmit(MCategoryDetailsPageAction mCategoryDetailsPageAction, String searchText) {
        scrollToTop();
        //boolean searchIconDisplay = waitUntilElementDisplayed(searchIcon, 20);
        if (waitUntilElementDisplayed(searchIcon, 20)) {
            click(searchIcon);
        }
        waitUntilElementDisplayed(searchBox, 15);
        clear(searchBox);
        fillText(searchBox, searchText);
        //click(searchSubmitButton);
        keyBoard(searchBox, "ENTER");
        return waitUntilElementDisplayed(mCategoryDetailsPageAction.addToBagIconByPos(1), 50);
    }

    /**
     * Created By Pooja
     * This method clicks on search icon and searches for a text and Veriies if search Suggestion is displayed
     */
    public boolean enterTextInSearchField(String searchText) {
        clickSearchIconIfDisplay();
        clearAndFillText(searchBox, searchText);
        return waitUntilElementsAreDisplayed(searchSuggestions, 5);
    }

    /*Created By Raman
     * This method return the product back to shopping bag*/
    public boolean returnToBag(MShoppingBagPageActions shoppingBagPageAction) {
        if (!isDisplayed(returnBagBtn)) {
            click(backBtn);
        }
        waitUntilElementDisplayed(returnBagBtn, 30);
        click(returnBagBtn);
        return waitUntilElementDisplayed(shoppingBagPageAction.checkoutBtn, 30);


    }

    public boolean validateSearchBar(String ghostTxt) {
        boolean isSearchIconDisplayed = waitUntilElementDisplayed(searchIcon, 15);
        //ToDO: Need research why tap is not working
        if (isSearchIconDisplayed) {
            click(searchIcon);
        } else {
            isSearchIconDisplayed = true;
        }
        boolean isSearchBarPresent = waitUntilElementDisplayed(searchBox, 3);
        boolean isGhostTextDisplayed = checkSearchGhostText(ghostTxt);
        boolean isSearchButtonDisplayed = !waitUntilElementDisplayed(searchSubmitButton, 3);

        boolean validateSearch = isSearchIconDisplayed && isSearchBarPresent && isGhostTextDisplayed && isSearchButtonDisplayed;
        return validateSearch;
    }

    public boolean checkSearchGhostText(String SearchText) {
        if (mobileDriver.findElement(By.cssSelector("[name='typeahead']")).getAttribute("placeholder").toString().equalsIgnoreCase(SearchText))
            return true;
        else
            return false;
    }

    /**
     * select random auto suggestions from search results
     * Existing Method Modified By Pooja to handle the clicking on search results
     */
    public void selectRandomSuggestions(MSearchResultsPageActions msearchResultsPageActions) {
        if (searchSuggestions.size() == 1) {
            click(searchSuggestionsItem.get(0));
        } else {
            click(searchSuggestions.get(getRandomNumber(searchSuggestionsItem.size())));
        }
        waitUntilElementDisplayed(msearchResultsPageActions.catPage);
    }

    public boolean searchAndCheckText(String searchTxt) {
        clickSearchIconIfDisplay();
        clearAndFillText(searchBox, searchTxt);
        if (getAttributeValue(searchBox, "value").equals(searchTxt))
            return true;
        else
            return false;
    }

    public boolean closeSearchAndVerifySearchBox() {
        if (waitUntilElementDisplayed(closeSearch, 10)) {
            click(closeSearch);
        }
        return !waitUntilElementDisplayed(searchBox, 10);
    }

    public boolean searchAndVerifyNoSuggestions() {
        clickSearchIconIfDisplay();
        clearAndFillText(searchBox, "ZEEP");
        return !waitUntilElementsAreDisplayed(searchSuggestions, 10);
    }

    public boolean clickSearchIconIfDisplay() {
        if (waitUntilElementDisplayed(searchIcon, 10)) {
            click(searchIcon);
        }
        return waitUntilElementDisplayed(searchBox, 10);
    }

    /**
     * verify Did you mean text in null search results page
     *
     * @return true if displayed
     */
    public boolean verifyDidYouMean() {
        return waitUntilElementDisplayed(didYouMeanText, 10);
    }

    /**
     * Click did you mean link and verify category page is displayed
     *
     * @param mCategoryDetailsPageAction
     * @return
     */
    public boolean clickDidYouMeantText(MCategoryDetailsPageAction mCategoryDetailsPageAction) {
        click(didYouMeanText);
        return waitUntilElementDisplayed(mCategoryDetailsPageAction.filter);
    }

    public boolean submitAndVerifyEmptyPage(MSearchResultsPageActions msearchResultsPageActions, String keyword) {
        boolean searchIconDisplay = waitUntilElementDisplayed(searchIcon, 20);
        scrollToTop();
        if (searchIconDisplay) {
            click(searchIcon);
        }
        waitUntilElementDisplayed(searchBox, 15);
        clearAndFillText(searchBox, keyword);
//        click(searchSubmitButton);
        keyBoard(searchBox, "ENTER");
        boolean isEmptyPage = isDisplayed(msearchResultsPageActions.emptySearchPage);
        boolean isSearchTips = isDisplayed(msearchResultsPageActions.searchTips);
        boolean isNewSearchBar = msearchResultsPageActions.newSearchBar.size() == 1;
        if (!isEmptyPage) {
            addStepDescription("Verify 0 results page not displaying");
        }
        if (!isSearchTips) {
            addStepDescription("Tips for improving search results are not displaying");

        }
        if (!isNewSearchBar) {
            addStepDescription("Verify new search bar is displayed");
        }
        return isEmptyPage && isSearchTips && isNewSearchBar;

    }

    public boolean searchAndKeyboardEnter(MCategoryDetailsPageAction mcategoryDetailsPageAction, MSearchResultsPageActions msearchResultsPageActions, String searchTerm) {
        boolean searchIconDisplay = waitUntilElementDisplayed(searchIcon, 20);
        scrollToTop();
        if (searchIconDisplay) {
            click(searchIcon);
        }
        waitUntilElementDisplayed(searchBox, 15);
        clear(searchBox);
        fillText(searchBox, searchTerm);
        staticWait();
        keyBoard(searchBox, "ENTER");

        boolean deptTitle = isDisplayed(mcategoryDetailsPageAction.deptNameTitle);
        boolean searchedForTerm = isDisplayed(msearchResultsPageActions.serachedFor);
        boolean catgoryPage = isDisplayed(msearchResultsPageActions.catPage);
        if (!deptTitle) {
            addStepDescription("Verify category results page");
        }
        if (!searchedForTerm) {
            addStepDescription("Verify Searched for");

        }
        if (!catgoryPage) {
            addStepDescription("Verify Search results count");
        }

        return deptTitle && searchedForTerm && catgoryPage;
    }

    public boolean searchWithEmptyKBEnter() {
        clickSearchIconIfDisplay();
        clear(searchBox);
        keyBoard(searchBox, "ENTER");
        return !waitUntilElementDisplayed(searchSubmitButton, 5);
    }

    public String searchAndVerifyInlineBadge(String searchTerm, MCategoryDetailsPageAction mCategoryDetailsPageAction) {
        clickSearchIconIfDisplay();
        clearAndFillText(searchBox, searchTerm);
        keyBoard(searchBox, "ENTER");
        boolean inlineBadge = waitUntilElementDisplayed(mCategoryDetailsPageAction.inlineBadges.get(0));
        return getText(mCategoryDetailsPageAction.inlineBadges.get(0));
    }

    /**
     * validate side banner at the top of the page
     *
     * @return
     */
    public boolean verifySiteWideBanner() {
        return waitUntilElementDisplayed(siteWideBanner, 20);
    }

    /**
     * Created By Pooja
     * This method verifies maximum of 8 items will be displayed in Suggestion List
     */
    public boolean verifySuggestionsListSize() {
        int i = 12;
        return searchSuggestionsItem.size() > 0 && searchSuggestionsItem.size() <= i;
    }

    /**
     * Created By Pooja
     * This Method clicks on Add to favourite icon on SRP Product Tile and also store the name of fav item added in a global variable
     * Expected result:- Add to Favourite Button on Product Tile should be displayed Selected
     */
    public boolean verifyNoDuplicatesInSgstionList() {
        for (int i = 0; i < searchSuggestionsItem.size(); i++) {
            for (int j = i + 1; j < searchSuggestionsItem.size(); j++) {
                if (getText(searchSuggestionsItem.get(i)) == getText(searchSuggestionsItem.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Created By Pooja
     * This method verifies maximum of 12 items will be displayed in Suggestion List
     */
    public boolean closeSearchModal() {
        if (waitUntilElementDisplayed(closeSearchModal)) {
            click(closeSearchModal);
            return waitUntilElementDisplayed(tcpLogo);
        }
        addStepDescription("Search list is displaying on the same page");
        return true;
    }
    /*
     * Created By Raman
     * ClickOnChekout from Add to Bag Notification
     * continue as Guest
     * @return true if  shipping Page is displayed
     */

    public boolean clickCheckoutFrmNotificationAsGuest(MShippingPageActions mshippingPageActions, MShoppingBagPageActions mshoppingBagPageAction) {
        waitUntilElementDisplayed(addtoBagNotification);
        javaScriptClick(mobileDriver, checkoutFrmNotification);
        waitUntilElementDisplayed(mshoppingBagPageAction.continueAsGuest, 20);
        javaScriptClick(mobileDriver, mshoppingBagPageAction.continueAsGuest);
        return waitUntilElementDisplayed(mshippingPageActions.nextBillingButton, 20);
    }
    /*
     * Created By Shubhika
     * ClickOnViewBag Notification

     * @return true if  shopping Bag is displayed
     */


    public boolean clickOnViewBagNotification(MShoppingBagPageActions mshoppingBagPageAction) {
        //waitUntilElementDisplayed(addtoBagNotification);
        javaScriptClick(mobileDriver, viewBagNotification);
        return waitUntilElementDisplayed(mshoppingBagPageAction.continueShopping, 20);

    }

    /*
     * Created By Raman
     * ClickOnChekoutfrom Add to Bag Notification
     * continue as RegisteredUser
     * @return true if  shipping Page is displayed
     */
    public boolean clickCheckoutFrmNotificationAsReg(MShippingPageActions mshippingPageActions, MReviewPageActions mreviewPageActions) {
        waitUntilElementDisplayed(addtoBagNotification);
        javaScriptClick(mobileDriver, checkoutFrmNotification);
        scrollDownUntilElementDisplayed(mshippingPageActions.nextBillingButton);
        return waitUntilElementDisplayed(mshippingPageActions.nextBillingButton, 40) || mreviewPageActions.isReviewPageDisplayed();
    }

    /**
     * scroll to bottom and verify scroll to top icon is displayed
     *
     * @return true if the scroll to top is not displayed
     */
    public boolean clickScrollToTop() {
        scrollToBottom();
        waitUntilElementDisplayed(scrollToTop, 10);
        click(scrollToTop);
        return !waitUntilElementDisplayed(scrollToTop, 10);
    }

    /**
     * Created By Pooja
     * This method verifies maximum of 8 items will be displayed in I'M Looking For Suggestion List
     */
    public boolean verifyImLookingSectionSize() {
        return lookingForSuggestions.size() > 0 && lookingForSuggestions.size() <= 8;
    }

    /**
     * Get bag count from mini cart
     *
     * @return
     */
    public int getBagCount() {
        waitUntilElementDisplayed(shoppingBagIcon);
        return Integer.parseInt(getText(bagLink).replaceAll("[^0-9.]", ""));
    }

    public boolean clickCloseIconOnATBNotification(MProductDetailsPageActions mProductDetailsPageActions) {
        click(addtoBagClose);
        return isDisplayed(mProductDetailsPageActions.productTitleInformation());
    }

    public boolean validateHrefLangTag() {
        boolean href = false;
        for (WebElement tag : hrefLangTags) {
            href = getAttributeValue(tag, "href").contains("childrensplace.com") && hrefLangTags.size() == 4;
        }
        return href;
    }
}