package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MCareersPageRepo;

import java.util.ArrayList;
import java.util.List;

public class MCareersPageActions extends MCareersPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MFooterActions.class);

    public MCareersPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean validateCareersCountries() {
        List<String> countries = new ArrayList<>();
        for (WebElement e : careersCountries) {
            countries.add(getText(e));
        }

        if (countries.get(0).contains("UNITED") && countries.get(1).contains("CANADA") && countries.get(2).contains("INDIA")) {
            return true;
        } else {
            addStepDescription("something wrong with countries matching");
            return false;
        }
    }

    /**
     * verify different Jobs in Us
     *
     * @return
     */
    public boolean verifyJobsInUS() {
        List<String> us = new ArrayList<>();
        for (WebElement e : usCareers) {
            us.add(getText(e));
        }

        if (us.get(0).equalsIgnoreCase("CORPORATE") && us.get(1).contains("STORES") && us.get(2).contains("DISTRIBUTION")) {
            return true;
        } else {
            addStepDescription("something wrong with countries matching in US jobs");
            return false;
        }
    }

    /**
     * verify different Jobs in Us
     *
     * @return
     */
    public boolean verifyJobsInCA() {
        List<String> ca = new ArrayList<>();
        for (WebElement e : caCareers) {
            ca.add(getText(e));
        }

        if (ca.get(0).equalsIgnoreCase("STORES") && ca.get(1).contains("DISTRIBUTION") ) {
            return true;
        } else {
            addStepDescription("something wrong with countries matching in CA jobs");
            return false;
        }
    }
}
