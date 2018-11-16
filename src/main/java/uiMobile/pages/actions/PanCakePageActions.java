package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.PanCakePageActionsRepo;

import java.util.*;

/**
 * Created by JKotha on 11/10/2016.
 */
public class PanCakePageActions extends PanCakePageActionsRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(PanCakePageActions.class);
    MobileHeaderMenuActions headerMenuActions;

    public PanCakePageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        headerMenuActions = new MobileHeaderMenuActions(mobileDriver);
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Verify whether the link is displayed or not
     * in pan cake section
     *
     * @param linkName The name of the link to verify
     * @return Whether or not expected link is displayed
     */
    public boolean isLinkDisplayed(String linkName) {
        boolean displayed = false;
        switch (linkName.toUpperCase()) {
            case "REWARDS":
                displayed = isDisplayed(myPlaceRewardsLink);
                break;

            case "FINDASTORE":
                displayed = isDisplayed(findAstoreLink);
                break;
            case "MYACCOUNT":
                displayed = isDisplayed(myAccountLink);
                break;
            default:
        }
        return displayed;
    }

    /**
     * Navigate to particular link
     *
     * @param linkName Name of the link to Navigate
     */
    public void navigateToMenu(String linkName) {
        javaScriptClick(mobileDriver, headerMenuActions.menuIcon);
        waitUntilElementDisplayed(menuClose, 20);

        switch (linkName.toUpperCase()) {
            case "REWARDS":
                click(myPlaceRewardsLink);
                break;
            case "FINDASTORE":
                javaScriptClick(mobileDriver, findAstoreLink);
                break;
            case "FAVORITES":
                click(favoritesLink);
                MobileFavouritesActions action = new MobileFavouritesActions(mobileDriver);
                waitUntilElementDisplayed(action.defaultWLName, 30);
                break;
            case "CREATEACCOUNT":
                click(createAccLink);
                break;
            case "LOGIN":
                //scrollToTop();
                javaScriptClick(mobileDriver, loginLink);
                break;
            case "VIEW":
                click(salutationLink);
                waitUntilElementDisplayed(mycoupons, 300);
                break;
            case "MPR":
                click(mprLink);
                break;
            case "USER":
                click(salutationLink);
                break;
            case "MYACCOUNT":
                javaScriptClick(mobileDriver, myAccount);
                break;
            case "BUYGIFTCARDS":
                click(buyGiftCards);
                break;
            default:
        }
    }

    /**
     * select any random Level1 category
     */
    public void selectRandomLevelOneCategory() {
        waitUntilElementsAreDisplayed(levelones, 20);
        int levels = levelones.size();
        Random r = new Random();
        click(levelones.get(randInt(0, levels)));
        waitUntilElementDisplayed(leveltwo);
    }

    /**
     * select any random Level2 category
     */
    public void selectRandomLevelTwoCategory() {
        waitUntilElementsAreDisplayed(leveltwos, 20);
        int levels = leveltwos.size();
        click(leveltwos.get(randInt(1, levels - 1)));
        //handle l3
        if (waitUntilElementDisplayed(levelthree, 5)) {
            click(levelthree);
        }
    }

    public boolean verifyL2CatNotRepeating() {
        List<String> duplicates = new ArrayList<>();
        Set<String> val = new HashSet<>();
        for (WebElement l2 : leveltwos) {
            if (!val.add(getText(l2))) {
                duplicates.add(getText(l2));
            }
        }
        return duplicates.size() == 0;

    }

    public void selectLevelOneCategory(String category) {
        navigateToPlpCategory(category, null, null, null);
    }

    /**
     * Created By Pooja
     * This will selecte specific level two Category and click on Level Three if exists
     */
    public void selectLevelTwoCategory(String category) {
        click(lTwoCategory(category));
        if (waitUntilElementDisplayed(levelthree, 15)) {
            click(levelthree);
        }
    }


    public String getPlpCategory() {
        MCategoryDetailsPageAction mcategoryDetailsPageAction = new MCategoryDetailsPageAction(mobileDriver);
        MSearchResultsPageActions msearchResultsPageActions = new MSearchResultsPageActions(mobileDriver);
        if (waitUntilElementDisplayed(mcategoryDetailsPageAction.breadCrumbCategory, 10)) {
            return getText(mcategoryDetailsPageAction.breadCrumb);
        } else {
            return getText(msearchResultsPageActions.currentSearch);
        }
    }

    public boolean navigateToRandomPLP() {
        MCategoryDetailsPageAction action = new MCategoryDetailsPageAction(mobileDriver);
        selectLevelOneCategory("Girl");
        selectRandomLevelTwoCategory();
        return waitUntilElementDisplayed(action.breadCrumb);
    }

    public boolean isUserLoggedIn(String userName, boolean loggedIn) {
        click(headerMenuActions.menuIcon);
        if (!loggedIn) {
            boolean localFlag = isDisplayed(loginLink);
            mobileDriver.navigate().refresh();
            return localFlag;
        } else {
            String logInLinkText = getText(salutationLink);
            mobileDriver.navigate().refresh();
            boolean localFlag;
            return logInLinkText.toLowerCase().contains(userName.toLowerCase());
        }

    }

    /**
     * Created By Pooja on 23rd May,2018
     * This Method stores the name of categories displayed after clicking Level 1 Category
     */
    public List<String> storeL2Categories(MobileHomePageActions mobileHomePageActions) {
        List<String> allCategories = new ArrayList<>();
        for (WebElement element : leveltwos) {
            allCategories.add(getText(element));
        }
        return allCategories;
    }

    /**
     * Created By Pooja on 23rd May,2018
     * This Method verifies the categories on Category Landing page matches with the categories on Pancake
     */
    public boolean verifyCategoriesMatchPancake(MobileHomePageActions mobileHomePageActions, PanCakePageActions panCakePageActions, List<String> allCategories) {
        if (allCategories.size() == panCakePageActions.leveltwos.size()) {
            for (int i = 0; i < mobileHomePageActions.subCategoriesLink.size(); i++) {
                if (!getText(panCakePageActions.leveltwos.get(i)).equals(allCategories.get(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Created By Pooja
     * select Level2 category and move to Level Three
     */
    public boolean moveToLevelThreeCategory(String levelTwo) {
        click(lTwoCategory(levelTwo));
        return waitUntilElementDisplayed(levelthree);
    }

    /**
     * Created By Pooja on 16th May,2018
     * This Method verifies no duplicate values present in the Sub Menu
     * Data:-Name of the Main menu under which you want to verify the sub Menus
     */
    public boolean verifyNoDuplicatesInSubMenu() {
        for (int i = 0; i < leveltwos.size(); i++) {
            for (int j = i + 1; j < leveltwos.size(); j++) {
                if (getText(leveltwos.get(i)).equals(leveltwos.get(j))) {
                    return false;

                }
            }
        }
        return true;
    }

    /*
      Author:Shilpa P
      This method would click on the Menu Icon
    * */
    public void clickMenuIcon() {
        click(headerMenuActions.menuIcon);
    }

    /**
     * Created by RichaK
     * This will click on the cross icon and close the menu.
     */
    public void clickCloseIcon() {
        javaScriptClick(mobileDriver, menuClose);
    }

    /**
     * Created BY Pooja to select Level Three Category by Title
     */
    public void selectLevelThreeCategory(String category, MCategoryDetailsPageAction mcategoryDetailsPageAction) {
        click(lThreeCategory(category));
        waitUntilElementDisplayed(mcategoryDetailsPageAction.filter);
    }

    /**
     * Validate whether size ranges are displayed or not
     * in pancake for all departments
     *
     * @return
     */
    public boolean verifySizeRanges() {
        return waitUntilElementsAreDisplayed(sizeRanges, 20);
    }

    /**
     * Navigate a particular category
     *
     * @param lOne       category to select
     * @param subSection to expand
     * @param lTwo       to select
     * @param lThree     to navigate
     */
    public void navigateToPlpCategory(String lOne, String subSection, String lTwo, String lThree) {
        waitUntilElementDisplayed(headerMenuActions.menuIcon, 10);
        click(headerMenuActions.menuIcon);
        waitUntilElementDisplayed(menuClose, 10);
        if (lOne != null) {
            click(lOneCategory(lOne));
        }
        if (subSection != null) {
            click(subSection(subSection));
        }

        if (lTwo != null) {
            click(lTwoCategory(lTwo));
        }

        if (lThree != null) {
            click(lThreeCategory(lThree));
        }
    }

//    /**
//     * Verify rewards bar
//     *
//     * @return true if rewards bar displayed
//     */
//    public boolean verifyRewardsBar() {
//        return waitUntilElementDisplayed(rewardsBar, 20);
//    }



    public String verifySectionHeader() {
        return getText(lTwoHeader);
    }

}
