package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by user on 6/16/2016.
 */
public class DepartmentLandingPageRepo extends HeaderMenuRepo {

    @FindBy(css=".inline-navigation-container")//"#sub-cats.block")
    public WebElement subCatLeftNavBlock;

    @FindBy(css=".breadcrum-last-item")//".department-name-title")
    public WebElement deptName;

    @FindBy(css=".container-fluid")
   // @FindBy(css = ".container")
    public WebElement eSpots;


    @FindBy(xpath ="//*[contains(@class,'sub-categories')] //a[not(contains(@class,'notSelected'))][contains(@id,'SBN_facet_Tops')]")
    public WebElement lnk_ToddlerBoy_Tops;

    @FindBy(xpath = "//div[@class='inline-navigation-container sticky-sidenav']//ol/li/a[(text()='Tops')]") //click on Girl Tops
    public WebElement lnk_Girl_Tops;

    @FindBy(xpath = "//li[@class=\"navigation-level-one-item\"]/a[contains(text(),'Sleepwear')]") //Click on Toddler Girl Sleepwear
    public WebElement lnk_ToddlerGirl_Sleepwear;

    @FindBy(xpath = "//div[@class='inline-navigation-container sticky-sidenav']//ol/li/a[(text()='Bottoms')]") //Click on Toddler Girl Bottom
    public WebElement lnk_Girl_Bottom;

    //Pooja
    @FindBy(xpath = "//div[@class='inline-navigation-container']//ol/li/a[(text()='Graphic Tees')]")
    public WebElement lnk_Graphic_Tees;

    @FindBy(xpath = "//div[@class='inline-navigation-container sticky-sidenav']//ol/li/a[(text()='Denim')]")////li[@class=\"navigation-level-one-item\"]/a[contains(text(),'Denim')]")
    public WebElement lnk_Denim;

    @FindBy(xpath = "//a[@class=\"navigation-level-one-link\"] [text()=\"clearance\"]")
    public WebElement clearanceDep_Lnk;

    @FindBy(xpath = "//div[@class='inline-navigation-container']//ol/li/a[(text()='Outfits')]")
    public WebElement outfits_Lnk;

    @FindBy(xpath = "//ol[@class=\"navigation-level-one-container\"]//a[(text()='Online Only')]")
    public WebElement onlineonly_Lnk;

    @FindBy(xpath = "//ol[@class=\"navigation-level-one-container\"]//a[(text()='Clearance')]")
    public WebElement clearance_lnk;

    @FindBy(xpath = "//ol[@class=\"navigation-level-one-container\"]//a[(text()='New Arrivals')]")
    public WebElement newarrivals_link;


  }
