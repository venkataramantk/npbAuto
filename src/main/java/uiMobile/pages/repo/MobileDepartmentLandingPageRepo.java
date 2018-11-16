package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileDepartmentLandingPageRepo extends MobileHeaderMenuRepo {
    @FindBy(css=".leftNav.block")
    public WebElement leftNavigation;

    @FindBy(xpath = "//*[contains(@class,'sub-categories')] //a[not(contains(@class,'notSelected')) and not(contains(@id,'SBN_facet_Outfits'))]")
    public List <WebElement> list_Categories;

    @FindBy(css=".inline-navigation-container")//"#sub-cats.block")
    public WebElement subCatLeftNavBlock;

    @FindBy(css=".breadcrum-last-item")//".department-name-title")
    public WebElement deptName;

    @FindBy(xpath = "//span[contains(@class,'leftNavLevel0Title block')]//a[contains(@id,'SBN_facet')]")
    public List <WebElement> leftNavCategoriesList;



}
