package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileHomePageRepo extends MobileHeaderMenuRepo {


    @FindBy(css = "#email-signup-modal .email-signup-modal-header>button")
    public WebElement closeLinkOnPromo; // works both live2,1

    @FindBy(css = "img[title='Ship to Country: CA']")
    public WebElement ca_HomePage;//not found

    @FindBy(css = "img[title='Ship to Country: US']")
    public WebElement us_HomePage;//not found

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Girl') or contains(.,'Fille') or contains(.,'NiÃ±a')]")*/
    //@FindBy(xpath = "//a[contains(@href,'/c/')][.='Girl' or .='Fille' or .='Niña']")//live2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Girl') or starts-with( .,'Fille')or starts-with( .,'Niña')]")
//live1,2
    public WebElement link_Girls;

    /*@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Toddler Girl') or contains(.,'Tout-petit, fille')  or contains(.,'Niña pequeña')]")*/
    //@FindBy(xpath = "//a[contains(@href,'/c/')][.='Toddler Girl' or .='Tout-petit, fille' or .='Niña pequeña']") //live2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Toddler Girl') or starts-with( .,'Tout-petit, fille')or starts-with( .,'Niña pequeña')]")
//live1,2
    public WebElement link_ToddlerGirls;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Boy') or contains(.,'Niño') or contains(.,'Garçon')]")*/
    //@FindBy(xpath = "//a[contains(@href,'/c/')][.='Boy' or .='Garçon'or .='Niño']")//live2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Boy') or starts-with( .,'Garçon')or starts-with( .,'Niño')]")
//live1,2
    public WebElement link_Boys;

    /*@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Toddler Boy') or contains(.,'Niño pequeño') or contains(.,'Tout-petit, garçon')]")*/
    //@FindBy(xpath ="//a[contains(@href,'/c/')][.='Toddler Boy'or .='Tout-petit, garçon' or .='Niño pequeño']") //live2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'Toddler Boy') or starts-with( .,'Tout-petit, garçon')or starts-with( .,'Niño pequeño')]")
//live1,2
    public WebElement link_ToddlerBoys;

    /*@FindBy(xpath = "//li[@class='short']/a[contains(.,'Baby') or contains(.,'Bebé') or contains(.,'Bébé')]")*/
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Baby' or .='Bebé' or .='Bébé']") // works in live2,1
    public WebElement link_Baby;

    //    @FindBy(xpath = "//li[@class='short']/a[contains(.,'Shoes') or contains(.,'Calzado') or contains(.,'Chaussures')]")
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Shoes']")// works in live2,1
    public WebElement link_Shoes;

    //    @FindBy(xpath = "//li[@class='long special']/a[contains(.,'Accessories') or contains(.,'Accesorios') or contains(.,'Accessoires')]")
    @FindBy(xpath = "//a[contains(@href,'/c/')][.='Accessories']")// works in live2,1
    public WebElement link_Accessories;

    //@FindBy(xpath = "//li[@class='long special']/a[contains(.,'Clearance') or contains(.,'Liquidación') or contains(.,'Liquidation')]") //live2
    @FindBy(xpath = "//li[@class='navigation-level-one']/a[starts-with(.,'clearance') or starts-with( .,'Liquidación')or starts-with( .,'Liquidation')]")
//live1,
    public WebElement link_Clearance;


    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure//a")
    public List<WebElement> prodRecommendation;

    @FindBy(css = ".heading-product-recomendation")
    public WebElement recommendationHeading;

    @FindBy(css = "button.favorite-icon-container")
    public List<WebElement> addToFavIcon;

    @FindBy(css = "button.bag-icon-container")
    public List<WebElement> addToBagIcon;

    @FindBy(css = ".favorite-icon-active")
    public List<WebElement> favIconEnabled;

    public WebElement itemImg(int i) {
        return getDriver().findElement(By.xpath("(.//ol[@class='content-product-recomendation']//figure//a)  [" + i + "]"));
    }

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'.item-disabled-option'))]")
    public List<WebElement> availableSizes;

    @FindBy(xpath = "(.//div[@class='size-and-fit-detail-items-list'])[1]//span")
    public List<WebElement> availableFits;


    @FindBy(id = "btnNo")
    public WebElement noButton;

    @FindBy(tagName = "header")
    public WebElement header;


    @FindBy(name = "emailAddress")
    public WebElement signUpemail;


    //Pooja
    @FindBy(css = "a.main-bold div.btn-white-blk-border.main-blk-button.g-b-ba-button.main-g-banner")
    public WebElement girlsEspot;

    @FindBy(css =  ".btn-white-blk-border.main-blk-button.tg-tb-button.main-tg-banner")
    public WebElement toddlerGirlsEspot;

    @FindBy(css = ".btn-white-blk-border.main-blk-button.g-b-ba-button.main-b-banner")
    public WebElement boysEspot;

    @FindBy(css = ".btn-white-blk-border.main-blk-button.tg-tb-button.main-tb-banner")
    public WebElement toddlerBoyEspot;

    @FindBy(css = ".btn-white-blk-border.main-blk-button.tg-tb-button.ecom-single-full.main-baby-banner")
    public WebElement babyEspot;

    @FindBy(css = "nav.navigation-link-container a.navigation-link")
    public List<WebElement> subCategoriesLink;

    //Pooja
    @FindBy(css = ".loading-more-product")
    public WebElement loadingMoreLink;
}
