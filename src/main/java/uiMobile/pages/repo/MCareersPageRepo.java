package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

public class MCareersPageRepo extends UiBaseMobile {

    @FindBy(css = ".discover-roles__flag-buttons h3")
    public List<WebElement> careersCountries;

    @FindBy(css = ".discover-roles__flag-buttons__us__buttons>li")
    public List<WebElement> usCareers;

    @FindBy(css = ".discover-roles__flag-buttons__ca__buttons>li")
    public List<WebElement> caCareers;
}
