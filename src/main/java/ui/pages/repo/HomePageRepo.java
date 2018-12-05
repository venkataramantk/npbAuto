package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class HomePageRepo extends HeaderMenuRepo {

    //    @FindBy(css=".ui-icon.ui-icon-closethick")
    @FindBy(css = "#email-signup-modal .email-signup-modal-header>button")
    public WebElement closeLinkOnPromo; // works both live2,1

    @FindBy(css = "img[title='Ship to Country: CA']")
    public WebElement ca_HomePage;//not found

    @FindBy(css = "img[title='Ship to Country: US']")
    public WebElement us_HomePage;//not found

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Girl') or contains(.,'Fille') or contains(.,'NiÃ±a')]")*/
    //@FindBy(xpath = "//a[contains(@href,'/c/')][.='Girl' or .='Fille' or .='Niña']")//live2
//    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Girl') or starts-with( .,'Fille')or starts-with( .,'Niña')]")//live1,2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Girl']")
    public WebElement link_Girls;

    /*@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Toddler Girl') or contains(.,'Tout-petit, fille')  or contains(.,'Niña pequeña')]")*/
    //@FindBy(xpath = "//a[contains(@href,'/c/')][.='Toddler Girl' or .='Tout-petit, fille' or .='Niña pequeña']") //live2
//    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Toddler Girl') or starts-with( .,'Tout-petit, fille')or starts-with( .,'Niña pequeña')]")//live1,2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Toddler Girl']")
    public WebElement link_ToddlerGirls;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Boy') or contains(.,'Niño') or contains(.,'Garçon')]")*/
    //@FindBy(xpath = "//a[contains(@href,'/c/')][.='Boy' or .='Garçon'or .='Niño']")//live2
//    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Boy') or starts-with( .,'Garçon')or starts-with( .,'Niño')]")//live1,2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Boy']")
    public WebElement link_Boys;

    /*@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Toddler Boy') or contains(.,'Niño pequeño') or contains(.,'Tout-petit, garçon')]")*/
    //@FindBy(xpath ="//a[contains(@href,'/c/')][.='Toddler Boy'or .='Tout-petit, garçon' or .='Niño pequeño']") //live2
//    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Toddler Boy') or starts-with( .,'Tout-petit, garçon')or starts-with( .,'Niño pequeño')]")//live1,2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Toddler Boy']")
    public WebElement link_ToddlerBoys;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Baby') or contains(.,'Bebé') or contains(.,'Bébé')]")*/
//    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Baby' or .='Bebé' or .='Bébé']") // works in live2,1
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Baby']")
    public WebElement link_Baby;

    //    @FindBy(xpath = "//li[@class='short']/a[contains(.,'Shoes') or contains(.,'Calzado') or contains(.,'Chaussures')]")
//    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Shoes']")// works in live2,1
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Shoes']")
    public WebElement link_Shoes;

    //    @FindBy(xpath = "//li[@class='long special']/a[contains(.,'Accessories') or contains(.,'Accesorios') or contains(.,'Accessoires')]")
//    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Accessories']")// works in live2,1
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Accessories']")
    public WebElement link_Accessories;

    //@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Clearance') or contains(.,'Liquidación') or contains(.,'Liquidation')]") //live2
//    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'clearance') or starts-with( .,'Liquidación')or starts-with( .,'Liquidation')]")//live1,
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[text()='Clearance']")
    public WebElement link_Clearance;


    @FindBy(xpath = "//li[contains(@class,'placeShops')]")
    public WebElement link_PlaceShops; // not in scope, removed

    @FindBy(xpath = "//li[@class='long special' or @class='short'][contains(.,'Online Only')]/a //parent::* //div[@class='nav-content-left'] //a[contains(.,'Online Only')]")
    public WebElement link_OnlineOnly; //not found

    @FindBy(xpath = "//*[@id='recommendations-slider']/li")
    public WebElement cqRecommendations; // more than 15 object found

    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure//a")
    public List<WebElement> prodRecommendation;

    @FindBy(css = ".heading-product-recomendation")
    public WebElement recommendationHeading;


    // @FindBy(css = ".bag-icon-container.hover-button-enabled")
    // @FindBy(xpath = ".//div[@class=\"item-button-container\"]/button[@class=\"bag-icon-container hover-button-enabled\"]")
    @FindBy(xpath = ".//div[@class='container-product-recomendation']//li//span[@class='text-price offer-price']//ancestor::li//button[@class=\"bag-icon-container hover-button-enabled\"]")
    public List<WebElement> addToBagIcon;


    public WebElement itemImg(int i) {
        return getDriver().findElement(By.xpath("(.//ol[@class='content-product-recomendation']//figure//a)  [" + i + "]"));
    }

    @FindBy(css = ".link-redirect")
    public WebElement viewProdDetailsLink;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    //@FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'.item-disabled-option'))]")
    @FindBy(xpath = " .//span[@class='size-and-fit-detail-title'][text()=\"Select a Size\"]/parent::div//label[not(@class=\"label-radio input-radio size-and-fit-detail-item size-and-fit-detail item-disabled-option\")]")
    public List<WebElement> availableSizes;

    // @FindBy(xpath = "(.//div[@class='size-and-fit-detail-items-list'])[1]//span")
    @FindBy(xpath = ".//span[@class='size-and-fit-detail-title'][text()=\"Select a Fit\"]/parent::div/div/label")
    public List<WebElement> availableFits;

    @FindBy(css = ".button-close")
    public WebElement closeLink;

    @FindBy(xpath = ".//div[@class='header-global-banner']//img")
    public WebElement headerBanner_Espot;

    @FindBy(xpath = ".//div[@class='main-image-container']//img")
    public WebElement mainImage_Espot;

    @FindBy(xpath = ".//div[@class='full-width clearfix outfits-row removeMobile']//img")
    public WebElement secondImg_Espot;


    //@FindBy (xpath = ".//ol[@class=\"content-product-recomendation\"]//button[@class=\"button-add-to-bag\"]")
    public WebElement addToBagPDPRecom;


}
