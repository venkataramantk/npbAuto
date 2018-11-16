package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.StaticContentCarrerPageRepo;

/**
 * Created by AbdulazeezM on 10/26/2017.
 */
public class StaticContentCarrerPageActions extends StaticContentCarrerPageRepo{
    WebDriver driver;
    Logger logger = Logger.getLogger(StaticContentCarrerPageActions.class);

    public StaticContentCarrerPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean validateStaticContentAndBtns(){

        if (isDisplayed(carrerInUS_Content)&&
                isDisplayed(corporateBtn)&&
                isDisplayed(storesBtn_US)&&
                isDisplayed(distribution_Btn_US)&&
                isDisplayed(storesBtn_CA)&&
                isDisplayed(carrerCA_Content)&&
                isDisplayed(distribution_Btn_CA)&&
                isDisplayed(itCenter_IND))
            return true;
        else
            return false;
    }
// Corporate career page - US

    public boolean validateCorporateCareersPage(){
        waitUntilElementDisplayed(allOpenJobsLink,10);
        staticWait(4000);
        if(isDisplayed(allOpenJobsLink)&&
                isDisplayed(searchBtn)&&
                isDisplayed(logBackInLink))
            return true;

        if (waitUntilElementDisplayed(categoryDropdown,10)&&
                waitUntilElementDisplayed(positionDropdown,10)&&
                waitUntilElementDisplayed(locationDropdown,10))
            return true;
        else
            return false;
    }
    public boolean searchJobByText(String searchTerm){
        waitUntilElementDisplayed(searchField,10);
        clearAndFillText(searchField,searchTerm);
        staticWait(1000);
        click(searchBtn);
        return waitUntilElementDisplayed(searchResultText,10);
    }
    public boolean clickWelcomeLink(){
        waitUntilElementDisplayed(welcomePageLink,10);
        click(welcomePageLink);
        staticWait(1000);
        return verifyElementNotDisplayed(searchResultText,10);
    }
    public boolean clickViewAllOpeningsLink(){
        waitUntilElementDisplayed(allOpenJobsLink,10);
        click(allOpenJobsLink);
        staticWait(1000);
        return verifyElementNotDisplayed(searchResultText,10);
    }
    //Store Career page - US

    public boolean validateStoreCareersPage(){
        if (isDisplayed(allOpenJobsLink)&&
                isDisplayed(searchBtn)&&
                isDisplayed(logBackInLink))
            return true;

        if (waitUntilElementDisplayed(categoryDropdown,10)&&
                waitUntilElementDisplayed(locationDropdown,10))
            return true;
        else
            return false;
    }
    public boolean validateErrorMessage(String invalidPwd) {
        waitUntilElementDisplayed(searchField, 5);
        clearAndFillText(searchField,"");
        waitUntilElementDisplayed(invalidErrorMsg, 5);
        boolean invalidError = getText(invalidErrorMsg).contains(invalidPwd);

        if (invalidError)
            return true;
        else
            return false;
    }
    public boolean jobOpeningCheck(String text1){

        waitUntilElementDisplayed(noJobOpeningDisplay,10);
        String text = getText(noJobOpeningDisplay);
        if(isDisplayed(noJobOpeningDisplay)){ // returning true both places because both cases are acceptable
           text.equalsIgnoreCase(text1);
            addStepDescription("Currently no openings are available");
            return true;
        }
        else
            addStepDescription("Job Openings are available");
            return true;
    }

}