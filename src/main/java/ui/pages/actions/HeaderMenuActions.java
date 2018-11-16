package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.script.FindFailed;
import ui.pages.repo.HeaderMenuRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class HeaderMenuActions extends HeaderMenuRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(HeaderMenuActions.class);

    //public String storeName;

    public HeaderMenuActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoginDisplaying() {
        return waitUntilElementDisplayed(loginLinkHeader);
    }

    public boolean isCreateAccountDisplaying() {
        return waitUntilElementDisplayed(createAccountLink);
    }

    public boolean clickCreateAccount(MyAccountPageDrawerActions myAccountPageDrawerActions, CreateAccountActions createAccountActions) {
        if (waitUntilElementDisplayed(myAccountPageDrawerActions.logOutLnk, 15)) {
            click(myAccountPageDrawerActions.logOutLnk);
        }
        waitUntilElementDisplayed(createAccountLink, 30);
        click(createAccountLink);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 30) /*&& createAccountActions.createAccountButton.isEnabled()*/;
    }

    public boolean clickCreateAccountOverlay(OverlayHeaderActions overlayHeaderActions, CreateAccountActions createAccountActions) {
        if (waitUntilElementDisplayed(overlayHeaderActions.logout, 1)) {
            click(overlayHeaderActions.logout);
            waitUntilElementDisplayed(createAccountOverlay, 30);
        }
        click(createAccountOverlay);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 30);
    }

    public boolean isUserSignIn() {
        return waitUntilElementDisplayed(welcomeMessage, 30);
    }

    public boolean isUserLogOut() {
        return waitUntilElementDisplayed(loginLinkHeader, 10);

    }

    public boolean clickReservationLinkAndVerifyEmptyResSummary(String emptyMsg) {
        waitUntilElementDisplayed(reservationLink);
        click(reservationLink);
        staticWait();
        return getText(emptyResSummary).equalsIgnoreCase(emptyMsg);
    }

    public boolean clickReservationLinkAndVerifyAvailResSummary(String msg, String resNum) {
        clickReservationLink();
        return getText(availableResMsgOnResSummary).equalsIgnoreCase(msg) && getText(getFirstElementFromList(availableResNumOnResSummary)).equalsIgnoreCase(resNum);
    }

    public void clickReservationLink() {
        click(reservationLink);
        staticWait();
    }

    public boolean clickReservationLinkAndVerify() {
        click(reservationLink);
        staticWait();
        return waitUntilElementDisplayed(ropisPopOver);
    }


    public String getReservationText() {
        return getText(reservationLink);
    }

    public void clickOnReservationIDForRememberedUser() {
        click(getFirstElementFromList(availableResNumOnResSummary));
    }

    public boolean enterTextInSearch(String text) {
        clearAndFillText(searchBox, text);
        return waitUntilElementDisplayed(suggestion, 5);
    }
    public boolean searchAndCheckText(String searchTxt) {
        clearAndFillText(searchBox, searchTxt);
        if (getAttributeValue(searchBox, "value").equals(searchTxt))
            return true;
        else
            return false;
    }

    public boolean searchAndSubmit(CategoryDetailsPageAction categoryDetailsPageAction, String searchItem) {
        waitUntilElementDisplayed(searchBox, 20);
        clearAndFillText(searchBox, searchItem);
        staticWait(3000);
        click(searchSubmitButton);
        staticWait(5000);
        if (isAlertPresent()) {
            acceptAlert();
        }
        return waitUntilElementDisplayed(categoryDetailsPageAction.firstImg, 20);
    }

    public boolean searchUsingKeyWordLandOnPDP(ProductDetailsPageActions productDetailsPageActions, String searchItem) {
        waitUntilElementDisplayed(searchBox, 30);
        clearAndFillText(searchBox, searchItem);
        staticWait(2000);
        click(searchSubmitButton);
        if (isAlertPresent()) {
            acceptAlert();
        }
        return waitUntilElementDisplayed(productDetailsPageActions.colorSwatches, 10);
    }

    public boolean searchWithoutKeyWord(SearchResultsPageActions searchResultsPageActions, String searchTerm) {
        String searchResults = getText(searchResultsPageActions.searchResultsTerm).replaceAll("\"", "");
        waitUntilElementDisplayed(searchBox, 5);
        click(searchSubmitButton);
        boolean remainOnPageAfterClickingSearch = searchResults.equalsIgnoreCase(searchTerm);

        searchBox.sendKeys(Keys.ENTER);
        boolean remainOnPageAfterPressingEnter = searchResults.equalsIgnoreCase(searchTerm);

        return remainOnPageAfterClickingSearch && remainOnPageAfterPressingEnter;
    }

    public boolean clickCheckoutAsGuest(ShoppingBagDrawerActions shoppingBagDrawerActions, LoginPageActions loginPageActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        return shoppingBagDrawerActions.clickCheckoutAsGuestUser(loginPageActions);
    }
    public boolean clickMiniCartAndCheckoutAsRegUser(ShoppingBagDrawerActions shoppingBagDrawerActions, ShippingPageActions shippingPageActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        waitUntilElementDisplayed(shoppingBagDrawerActions.checkoutButton);
        return shoppingBagDrawerActions.clickCheckoutButtonAsRegUser(shippingPageActions);
    }

    public boolean clickMiniCartAndCheckoutAsGuestUser(ShoppingBagDrawerActions shoppingBagDrawerActions, ShippingPageActions shippingPageActions) {
        LoginPageActions loginPageActions = new LoginPageActions(driver);
        clickShoppingBagIcon(shoppingBagDrawerActions);
        waitUntilElementDisplayed(shoppingBagDrawerActions.checkoutButton);
        click(shoppingBagDrawerActions.checkoutButton);
        waitUntilElementDisplayed(loginPageActions.continueAsGuestButton);
        click(loginPageActions.continueAsGuestButton);
        return shoppingBagDrawerActions.waitUntilElementDisplayed(shippingPageActions.nextBillingButton);
    }

    public boolean clickOnUserNameAndLogout(MyAccountPageDrawerActions myAccountPageDrawerActions, HeaderMenuActions headerMenuActions) {
        if (waitUntilElementDisplayed(welcomeCustomerLink)) {
            click(welcomeCustomerLink);
            waitUntilElementDisplayed(myAccountPageDrawerActions.logOutLnk);
            return myAccountPageDrawerActions.clickLogoutLink(headerMenuActions);
        }
        return waitUntilElementDisplayed(headerMenuActions.loginLinkHeader);

    }

    public boolean clickLogin(LoginDrawerActions loginDrawerActions) {

        click(loginLinkHeader);
        waitUntilElementDisplayed(loginLinkOverlay);
        return waitUntilElementDisplayed(loginDrawerActions.emailAddrField);
    }

    public boolean clickClearanceCategory(CategoryDetailsPageAction categoryDetailsPageAction, int i) {
        mouseHover(clearanceLink);
        staticWait(2000);
        try {
            click(subCategoryUnderClearanceByPos(i));
        } catch (Throwable t) {
            refreshPage();
            click(clearanceLink);
            waitUntilElementDisplayed(categoryDetailsPageAction.girlFacet);
            click(categoryDetailsPageAction.girlFacet);

        }

        return waitUntilElementDisplayed(categoryDetailsPageAction.firstImg);
    }

    public String getQtyInBag() {
        waitUntilElementDisplayed(shoppingBagIcon, 10);
        return getText(shoppingBagIcon);
    }

    public String getViewBagCount() {
        return getText(viewBag).replaceAll("[^0-9]", "");
    }


    public boolean navigateToShoppingBagPageFromHeader(ShoppingBagDrawerActions shoppingBagDrawerActions, ShoppingBagPageActions shoppingBagPageActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        return shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);

    }

    public boolean clickShoppingBagIconOnEmptyBag(ShoppingBagPageActions shoppingBagPageActions) {
        click(shoppingBagIcon);
        return waitUntilElementDisplayed(shoppingBagPageActions.emptyBagTextContainer, 30);
    }

    public boolean clickBagIconOnEmptyBag(ShoppingBagDrawerActions shoppingBagDrawerActions) {
        waitUntilElementDisplayed(shoppingBagIcon, 20);
        click(shoppingBagIcon);
        return waitUntilElementDisplayed(shoppingBagDrawerActions.createAccBtn, 30);
    }

    public void clickOnTCPLogo() {
        click(TCPLogo);
        validateGlobalNavBanner();
    }

    public boolean clickReturnToBagButton(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(returnToBagBtn);
        click(returnToBagBtn);
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn);
    }

    public boolean clickViewBagLink(ShoppingBagPageActions shoppingBagPageActions) {
        click(viewBagButtonFromAddToBagConf);
        return waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle, 20);
    }

    public boolean clickWishListAsRegistered(FavoritePageActions favoritePageActions) {
        staticWait(3000);
//        if (getDriver().findElements(By.xpath("(.//a[@class='wishlist-link'])[2]")).size()==1) {
//            click(wishlistIcon);
//        }
        boolean isUserSignedIn = isUserSignIn();
        click(wishListLink);
        waitUntilElementDisplayed(favoritePageActions.defaultWLName);
        if (isUserSignedIn) {
            return favoritePageActions.isWishListPageDisplayed();
        }
        return false;
    }

    public boolean clickWishListAsGuest(WishListDrawerActions wishListDrawerActions) {
        staticWait(5000);
        click(wishListLink);
        if (isUserLogOut()) {
            return waitUntilElementDisplayed(wishListDrawerActions.loginButton);
        }
        return false;
    }

    public boolean clickWishListAsRememberUser(LoginDrawerActions loginDrawerActions) {
        click(wishListLink);
        return waitUntilElementDisplayed(loginDrawerActions.rememberedLogout);

    }

    public boolean clickWishListOverlayAsGuest(WishListDrawerActions wishListDrawerActions) {
        click(wishlistOverlay);
        if (isUserLogOut()) {
            return waitUntilElementDisplayed(wishListDrawerActions.loginButton);
        }
        return false;
    }

    public String getHi_UserName() {
        return getText(hiUserName);
    }


    public boolean productDetails(String productNameBeforeReserve, String productSizeBeforeReserve, String productColorBeforeReserve) {
        String prodName = resProductName.getText().toLowerCase().substring(0, 17);
        String prodSize = resProductSize.getText();
        String prodCol = resProductColor.getText();
        if (productNameBeforeReserve.contains(prodName) && productSizeBeforeReserve.contains(prodSize) && productColorBeforeReserve.contains(prodCol))
            return true;
        else
            return false;
    }

    public boolean storeName(String storeName) {
        String storeNameCheck = reserveStoreName.getText();
        if (storeName.contains(storeNameCheck))
            return true;
        else
            return false;
    }

    public boolean reservationID(String resID) {
        String reserveID = this.reserveID.getText();
        if (resID.contains(reserveID))
            return true;
        else
            return false;
    }


    public boolean clickOnReservationLinkResDetails(String productNameBeforeReserve, String productSizeBeforeReserve, String productColorBeforeReserve, String storeName, String reserveID) {
        staticWait();
        mouseClick(reservationsLink);
        waitUntilElementDisplayed(reservationDetails, 5);

        boolean isYourReservationLabelDisplaying = waitUntilElementDisplayed(yourReservationsLbl);
        boolean areProductDetailsDisplaying = productDetails(productNameBeforeReserve, productSizeBeforeReserve, productColorBeforeReserve);
        boolean isStoreNameDisplaying = storeName(storeName);
        boolean isReservationIDPresent = reservationID(reserveID);

        if (isYourReservationLabelDisplaying &&
                areProductDetailsDisplaying &&
                isStoreNameDisplaying &&
                isReservationIDPresent)
            return true;
        else

            return false;
    }

    public void closeMPRBanner() {
        waitUntilElementDisplayed(mPRBannerCloseButton, 5);
        mouseClick(mPRBannerCloseButton);
    }

    public boolean closeMPRHeaderBanner() {
        boolean isMPRBannerDisplaying = waitUntilElementDisplayed(mPRBannerCloseButton);
        if (isMPRBannerDisplaying) {
            closeMPRBanner();
            return true;
        } else
            return false;
    }

    public boolean validateRewards(String filePath, String fileName) throws FindFailed {

        boolean isRewardsLinkDisplaying = waitUntilElementDisplayed(rewardsLink, 5);
        if (isRewardsLinkDisplaying) {

            mouseClick(rewardsLink);
//            sikuliClick(filePath, fileName);
            return true;
        }

        return false;
    }

     public boolean clickOutFitsFromCategoryByPosition(int i, CategoryDetailsPageAction categoryDetailsPageAction) {
        mouseHover(categoryByPosition(i));
//        staticWait();
        click(outfitsLinkFromCategoryByPosition(i));
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    public boolean clickBabyOutFitsFromCategoryByPosition(int i, CategoryDetailsPageAction categoryDetailsPageAction) {
        mouseHover(categoryByPosition(i));
//        staticWait();
        click(outfitsLinkFromCategoryByPosition(i));
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    //Newly Added
    public boolean clickLoginLnk(LoginDrawerActions loginDrawerActions) {
        waitUntilElementDisplayed(flagCountryImg);
        String currentUrl = getCurrentURL();
        if (currentUrl.contains(".com/us")) {
            waitUntilElementDisplayed(mprLink, 2);
        } else {
            verifyElementNotDisplayed(mprLink, 5);
        }
        waitUntilElementDisplayed(loginLinkHeader, 20);
        staticWait(5000);
        click(loginLinkHeader);
        return waitUntilElementDisplayed(loginDrawerActions.emailAddrField, 30);
    }


    public boolean clickShoppingBagIcon(ShoppingBagDrawerActions shoppingBagDrawerActions) {
        waitUntilElementClickable(shoppingBagIcon, 20);
        click(shoppingBagIcon);
        return waitUntilElementDisplayed(shoppingBagDrawerActions.checkoutButton, 20) || waitUntilElementDisplayed(shoppingBagDrawerActions.emptyTextMessage, 10);
    }

    public boolean navigateToShoppingBag(ShoppingBagDrawerActions shoppingBagDrawerActions, ShoppingBagPageActions shoppingBagPageActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        return shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);
    }

    public boolean navigateToCheckoutAsRegUser(ShoppingBagDrawerActions shoppingBagDrawerActions, ShippingPageActions shippingPageActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        return shoppingBagDrawerActions.clickCheckoutButtonAsRegUser(shippingPageActions);
    }

    public WebElement dropDownElements() {
        return getDriver().findElement(By.cssSelector(".navigation-level-one:nth-child(2) .sub-menu"));
    }
    public boolean hoverOnDeptName(String deptName) {

        if (deptName.equalsIgnoreCase("girl")) {
            mouseHover(deptLinkWithName(deptName));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        } else if (deptName.equalsIgnoreCase("boy")) {
            mouseHover(deptLinkWithName(deptName));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        } else if (deptName.replaceAll(" ", "").equalsIgnoreCase("toddlergirl")) {
            mouseHover(deptLinkWithName(deptName));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        } else if (deptName.replaceAll(" ", "").equalsIgnoreCase("toddlerboy")) {
            mouseHover(deptLinkWithName(deptName));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        } else if (deptName.equalsIgnoreCase("baby")) {
            mouseHover(deptLinkWithName(deptName));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        } else if (deptName.equalsIgnoreCase("shoes")) {
            try {
                deptLinkWithName(deptName);
                 mouseHover(deptLinkWithName(deptName));
                return waitUntilElementDisplayed(deptLinksContainer(deptName));
            }catch (NoSuchElementException e){

            }
            return true;
        } else if (deptName.equalsIgnoreCase("accessories")) {
            mouseHover(deptLinkWithName(deptName));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        } else {
            mouseHover(deptLinkWithName("Clearance"));
            return waitUntilElementDisplayed(deptLinksContainer(deptName));
        }

    }

    public boolean isSizesTxtLblDisplaying(String deptName) {

        mouseHover(deptLinkWithName(deptName));
        return waitUntilElementDisplayed(sizesTxtLbl(deptName));
    }

    public boolean submitSearch(SearchResultsPageActions searchResultsPageActions, String searchItem) {
        if (waitUntilElementDisplayed(closeTheOpenDrawerBtn, 10)) {
            click(closeTheOpenDrawerBtn);
        } else
            waitUntilElementDisplayed(searchBox, 20);
        clearAndFillText(searchBox, searchItem);
        click(searchSubmitButton);
        staticWait(10000);
        if (isAlertPresent()) {
            acceptAlert();
        }


        boolean isSearchKeywordDisplaying = waitForTextToAppear(searchResultsPageActions.searchResultsTerm, searchItem, 30);//searchResultsPageActions.searchResultsTerm.getText().trim().contains(searchItem);
        return isSearchKeywordDisplaying && waitUntilElementDisplayed(searchResultsPageActions.productImageByPosition(1), 30);
    }

    /**
     * Verify if paypal button is displayed in Add to bag notification
     *
     * @return true if paypal button is displayed
     */
    public boolean verifyPaypalFromNotificationWindow() {
        waitUntilElementDisplayed(addToBagNotification);
        return waitUntilElementDisplayed(paypalFromNotification, 5);
    }

    public boolean verifyAutoSuggestion() {
        return waitUntilElementDisplayed(suggestion, 10);
    }

    public boolean comparelevelTwoCategories(String deptName, List<String> input) {
        List<String> categories = new ArrayList<>();
        for (WebElement levelTwo : getLevelTwoCategories(deptName)) {
            categories.add(getText(levelTwo));
        }
        return input.equals(categories);
    }

    public boolean selectRandomCategoryFromSearchSuggestions() {
        CategoryDetailsPageAction action = new CategoryDetailsPageAction(driver);
        int categorySize = categorySuggestion.size();
        int randomSuggesion = randInt(2, categorySize);
        if (randomSuggesion >= 3)
            randomSuggesion = randomSuggesion - 1;
        click(categorySuggestion.get(randomSuggesion));
        return waitUntilElementDisplayed(action.deptNameTitle);
    }

    public boolean compareCatFromSearchSuggestions() {
        staticWait(3000);
        CategoryDetailsPageAction action = new CategoryDetailsPageAction(driver);
        int categorySize = categorySuggestion.size();
        int randomSuggesion = randInt(2, categorySize);
        for (int i = 0; i < categorySize; i++) {
            String value1 = getText(categorySuggestion.get(i));
            for (int j = i + 1; j < categorySize; j++) {
                String value2 = getText(categorySuggestion.get(j));
                if (value1.equalsIgnoreCase(value2)) {
                    addStepDescription("contains duplicate values");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean submitInvalidSearch(SearchResultsPageActions searchResultsPageActions, String searchItem) {
        if (waitUntilElementDisplayed(closeTheOpenDrawerBtn, 10)) {
            click(closeTheOpenDrawerBtn);
        } else
            waitUntilElementDisplayed(searchBox, 20);
        clearAndFillText(searchBox, searchItem);
        boolean autoSuggestions = !isDisplayed(searchResultsPageActions.search_SuggestedKey);
        click(searchSubmitButton);
        staticWait(10000);
        if (isAlertPresent()) {
            acceptAlert();
        }

        boolean emptySearchContent = waitUntilElementDisplayed(searchResultsPageActions.noResults_SearchField, 10);
        return emptySearchContent && autoSuggestions;
    }

    public boolean clickOnCustomerName(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(welcomeCustomerLink, 10);
        click(welcomeCustomerLink);
        boolean isViewMyAccountLnkDisplaying = waitUntilElementDisplayed(createAccountActions.viewMyAccount, 5);
        boolean isLogOutLnkDisplaying = waitUntilElementDisplayed(createAccountActions.logOutLnk, 5);


        return isViewMyAccountLnkDisplaying
                && isLogOutLnkDisplaying;
    }

    public boolean validateHeaderElements() {
        return waitUntilElementDisplayed(globalEspot, 10) &&
                waitUntilElementDisplayed(findStoreBtn, 10) &&
                waitUntilElementDisplayed(searchBox, 10);
    }

    public boolean clickLoggedLink(MyAccountPageDrawerActions myAccountPageDrawerActions) {
        waitUntilElementDisplayed(welcomeCustomerLink, 10);
        staticWait(9000);
        click(welcomeCustomerLink);
        staticWait(9000);
        return waitUntilElementDisplayed(welcomeCustomerHeaderOverlay, 5);
    }

    public boolean findStoreLnk() {
        click(findStoreLnk);
        return waitUntilElementDisplayed(findStoreBtn, 5);
    }
//    public boolean clickBagIcon(InlineShoppingBagActions inlineShoppingBagActions)
//    {
//        click(bagLink);
//        return waitUntilElementDisplayed(inlineShoppingBagActions.viewBagLnk,5);
//    }

    public boolean closeTheVisibleDrawer() {
        //switchToDefaultFrame();
        waitUntilElementDisplayed(closeTheOpenDrawerBtn, 10);
        click(closeTheOpenDrawerBtn);
        return !(waitUntilElementDisplayed(closeTheOpenDrawerBtn, 5));
    }

    public boolean isProductAddedToBag(ShoppingBagDrawerActions shoppingBagDrawerActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        waitUntilElementDisplayed(shoppingBagDrawerActions.checkoutButton);
        return closeTheVisibleDrawer();
    }

    public boolean emptyShoppingCartContents(ShoppingBagDrawerActions shoppingBagDrawerActions, ShoppingBagPageActions shoppingBagPageActions) {
        clickShoppingBagIcon(shoppingBagDrawerActions);
        shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);
        return shoppingBagPageActions.validateEmptyShoppingCart();
    }

    public boolean validateGlobalNavBanner() {
        return waitUntilElementDisplayed(globalNavBanner, 40);
    }

    public boolean clickViewBagButtonFromAddToBagConf(ShoppingBagPageActions shoppingBagPageActions) {
        click(viewBagButtonFromAddToBagConf);
        return waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle, 30);
    }

    public boolean clickOnMPRLink() {
        waitUntilElementDisplayed(mprLink, 10);
        click(mprLink);
        return waitUntilElementDisplayed(loginLink_MPR, 10) && waitUntilElementDisplayed(createAccLink_MPR, 10)
                && waitUntilElementDisplayed(learnMoreLink_MPR, 10);
    }

    public boolean hoverOnMPRLink() {
        waitUntilElementDisplayed(mprLink, 2);
        mouseHover(mprLink);
        return waitUntilElementDisplayed(loginLink_MPR, 3);
    }

    public boolean clickLogin_MPR(LoginDrawerActions loginDrawerActions) {
        waitUntilElementDisplayed(loginLink_MPR, 5);
        click(loginLink_MPR);
        return waitUntilElementDisplayed(loginDrawerActions.loginButton, 10);
    }

    public boolean clickCreateAcc_MPR(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(createAccLink_MPR, 5);
        click(createAccLink_MPR);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 10);
    }

    public boolean clickLearnMore_MPR() {
        waitUntilElementDisplayed(learnMoreLink_MPR, 5);
        click(learnMoreLink_MPR);
        return waitUntilElementDisplayed(createAccountLink, 10);
    }

    public boolean clickLogoutFromWlandingPage(FavoritePageActions favoritePageActions) {
        staticWait(3000);
        boolean isUserSignedIn = isUserSignIn();
        click(wishListLink);
        waitUntilElementDisplayed(favoritePageActions.defaultWLName);
        if (isUserSignedIn) {
            return favoritePageActions.isWishListPageDisplayed();
        }
        staticWait(5000);
        waitUntilElementDisplayed(logoutFooter);
        click(logoutFooter);
        staticWait(5000);
        return waitUntilElementDisplayed(createAccountLink);
    }

    public void searchWithClick(String searchItem) {
        clearAndFillText(searchBox, searchItem);
        click(searchSubmitButton);
    }

    public void searchWithEnter(String searchItem) {
        clearAndFillText(searchBox, searchItem);
        searchBox.sendKeys(Keys.ENTER);
    }

    public boolean checkMPR_Reg() {
        staticWait();
        if ((verifyElementNotDisplayed(mprLink, 3))) {
            return true;
        } else
            return false;

    }

    public boolean pointsText() {
        staticWait(4000);
        String currentUrl = getCurrentURL();
        if (currentUrl.contains(".com/us")) {
            waitUntilElementDisplayed(pointsAndRewards_Text, 2);
            String point = getText(pointsAndRewards_Text).replaceAll("[^0-9]", "");
            String reward = getText(pointsAndRewards_Text).replaceAll("[^0-9]", "");
            int points = Integer.parseInt(point);
            int rewards = Integer.parseInt(reward);
            int totalPoints = 0;
            int totalRewards = 0;
            if ((points == totalPoints) && (rewards == (totalRewards))) {
                return true;
            } else return false;
        } else {
            verifyElementNotDisplayed(pointsAndRewards_Text, 5);
        }
        staticWait(1000);
        return waitUntilElementDisplayed(welcomeCustomerLink, 5);
    }

    public boolean navigateToDepartment(String deptName) {
        waitUntilElementDisplayed(deptLinkWithName(deptName), 10);
        click(deptLinkWithName(deptName));
        return waitUntilElementDisplayed(deptNameTitle, 30);
    }


    public boolean clickOnL2_ByL1(CategoryDetailsPageAction categoryDetailsPageAction, String l1, String l2) {
        hoverOnDeptName(l1);
        try {
            click(link_L2ByL1(l1, l2));
        }catch(NoSuchElementException e){
            return false;
        }
            return waitUntilElementDisplayed(categoryDetailsPageAction.deptNameTitle);
    }

    public boolean enterSpaceKeyInSearchAndSubmit(SearchResultsPageActions searchResultsPageActions) {
        searchBox.sendKeys(Keys.SPACE);
        searchBox.sendKeys(Keys.SPACE);
        searchBox.sendKeys(Keys.SPACE);

        click(searchSubmitButton);
        return waitUntilElementDisplayed(searchResultsPageActions.searchResultsTerm, 10);
    }

    public boolean validatePageAfterLogout(String url) {
        waitUntilElementDisplayed(createAccountLink, 3);
        return getCurrentURL().contains(url);
    }

    public boolean rewardsLinkDispay() {
        waitUntilElementDisplayed(viewMyAccount, 3);
        if (isDisplayed(rewardsDisplayHeader)) {
            addStepDescription("Rewards is displayed in CA store");
            return false;
        }
        return true;
    }

    public boolean clickCheckoutFromAddToBagConfGuest(LoginPageActions loginPageActions) {
        click(checkoutFromNotification);
        return waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 30);
    }

    /*Created By Pooja on 04-May-2018
     This method Hover over Accessories header and click on Girls sub Menu*/
    public boolean clickGirlsUnderAccessoriesCategory(CategoryDetailsPageAction categoryDetailsPageAction) {
        //mouseHover(categoryByPosition(7));
        mouseHover(accessories_l1Category);
        click(girlLinkUnderAccessoriesCategory());
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    public boolean clickBoyUnderAccessoriesCategory(CategoryDetailsPageAction categoryDetailsPageAction) {
        //mouseHover(categoryByPosition(7));
        mouseHover(accessories_l1Category);
        click(boyLinkUnderAccessoriesCategory());
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    /*Created By Pooja on 12-May-2018
     This method Hover over Girl header and click on Shoes sub Menu*/
    public boolean clickShoesUnderGirlCategory(CategoryDetailsPageAction categoryDetailsPageAction) {
        //mouseHover(categoryByPosition(1));
        mouseHover(girl_l1Category);
        click(linkUnderGirlCategory("Shoes"));
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    public boolean clickShoesUnderBoyCategory(CategoryDetailsPageAction categoryDetailsPageAction) {
        //mouseHover(categoryByPosition(1));
        mouseHover(boy_l1Category);
        click(linkUnderBoyCategory("Shoes"));
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    /*Created By Pooja on 12-May-2018
     This method Hover over Girl header and click on Tops sub Menu*/
    public boolean clickTopsUnderGirlCategory(CategoryDetailsPageAction categoryDetailsPageAction) {
        //mouseHover(categoryByPosition(1));
        mouseHover(girl_l1Category);
        click(linkUnderGirlCategory("Tops"));
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }
    public boolean clickTopsUnderToddlerGirlCategory(CategoryDetailsPageAction categoryDetailsPageAction) {
        //mouseHover(categoryByPosition(1));
        mouseHover(toddlerGirl_l1Category);
        click(linkUnderToddlerGirlCategory("Tops"));
        return categoryDetailsPageAction.isCategoryLandPageDisplay();
    }

    public boolean checkTheTitleDisplayed(String text) {
        waitUntilElementDisplayed(bodyCopyText, 3);
        String currentText = driver.getTitle();
        if (text.contains(currentText)) {
            return true;
        } else {
            addStepDescription("Check the browser tab Name");
            return false;
        }
    }

    public boolean checkOrganizedDisplay(String deptName) {
        waitUntilElementDisplayed(deptLinksContainer(deptName));
        if (isDisplayed(deptFlyoutCatDisplay(deptName)) && isDisplayed(deptFlyoutFeatDisplay(deptName))) {
            // && isDisplayed(deptFlyoutOthertDisplay(deptName))){
            return true;
        } else {
            addStepDescription("Organised Flyouts are not getting displayed");
            return false;
        }
    }

    public boolean checkFavStoreDisplay(String store) {
        waitUntilElementDisplayed(changedFavStoreName, 2);
        String currentStore = getText(changedFavStoreName).toLowerCase();
        if (currentStore.equalsIgnoreCase(store)) {
            return true;
        } else {
            addStepDescription("Favorite store displayed in My Account is different");
            return false;
        }
    }
    public boolean checkFavStoreDisplayAfterStoreSwitch(String store) {
        waitUntilElementDisplayed(changedFavStoreName, 2);
        String currentStore = getText(changedFavStoreName).toLowerCase();
        if (currentStore.equalsIgnoreCase(store)) {
            addStepDescription("Same Favorite store displayed after the store switch");
            return false;
        } else {
             return true;
        }
    }
    public boolean checkSuggestionNotDisplayedInSearch(String text) {
        clearAndFillText(searchBox, text);
        return !waitUntilElementDisplayed(searchSuggestionFlyOut, 5);
    }
    public boolean clickRandomRecomImg(ProductDetailsPageActions productDetailsPageActions){
        if(isDisplayed(recomImag.get(0))){
            int size = recomImag.size();
            click(recomImag.get(randInt(0,(size-1))));
            return waitUntilElementDisplayed(productDetailsPageActions.addToBag,3);
        }
        else {
            addStepDescription("User is not redirected to Item PDP");
            return false;
        }
    }
    public boolean verifyDidYouMean() {
        return waitUntilElementDisplayed(didYouMeanText, 10);
    }
    public boolean clickDidYouMeantText(CategoryDetailsPageAction CategoryDetailsPageAction,ProductDetailsPageActions productDetailsPageActions) {
        click(didYouMeanText);
        return waitUntilElementsAreDisplayed(CategoryDetailsPageAction.productImages,3) || waitUntilElementDisplayed(productDetailsPageActions.productName, 3);
    }
}
