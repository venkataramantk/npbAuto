package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by Venkat on 8/22/2016.
 */
public class SiteMapPageRepo extends UiBase {

    @FindBy(xpath = "//li[.='See more' or .='Voir plus' or .='Ver más']/parent::*/parent::*/a[@class='openClose']")
    public List<WebElement> arrow_RowSubHeadings;

    @FindBy (xpath = "//li[.='See more' or .='Voir plus' or .='Ver más']/parent::*/parent::*")
    public List<WebElement> arrow_RowSubHeadingsName;

    //EnglishUS
    @FindBy(xpath="//div[@class='kids']//span[contains(.,'kids') or contains(.,'niños') or contains(.,'enfants')]")
    public WebElement lbl_kids;

    @FindBy(xpath="//div[@class='toddler']//span[contains(.,'toddler') or contains(.,'pequeños') or contains(.,'tout-petit')]")
    public WebElement lbl_toddler;

    @FindBy(xpath="//div[@class='others']//span[contains(.,'Baby') or contains(.,'Bebé') or contains(.,'Bébé')]")
    public WebElement lbl_baby;

    @FindBy(xpath="//span[@class='place-shops'][contains(.,'Place Shops') or contains(.,'Galerías') or contains(.,'Boutiques')]")
    public WebElement lbl_PlaceShop;

    @FindBy(xpath="//span[@class='clearance'][contains(.,'Clearance') or contains(.,'Liquidation')]")
    public WebElement lbl_Clearance;

    @FindBy(xpath="//span[@class='more'][contains(.,'More') or contains(.,'Más') or contains(.,'Plus')]")
    public WebElement lbl_More;

    @FindBy(xpath="//a[contains(@href,'us/content/help-center-home') or contains(@href,'ca/content/help-center-home')][contains(.,' Place Help Center') or contains(.,'Centro de ayuda de The Children') or contains(.,'Centre d')]")
    public WebElement lnk_Help;

    @FindBy(xpath="//a[contains(.,'Shopping') or contains(.,'Compras') or contains(.,'Magasiner')]")
    public WebElement lnk_Shopping;

    @FindBy(xpath="//a[contains(.,'About Us') or contains(.,'Nosotros') or contains(.,'À notre sujet')]")
    public WebElement lnk_AboutUs;

    @FindBy(xpath="//a[contains(@href,'us/content/myplace-rewards-page') or contains(@href,'ca/content/myplace-rewards-page')][contains(.,'My Place Rewards') or contains(.,'Recompensas My Place')]")
    public WebElement lnk_mPR;

    @FindBy(xpath = "//a[contains(@href,'childrensplace.com')][contains(.,\"The Children's Place \") and contains(.,'Home')]")
    public WebElement lbl_TCP;


//    @FindBy(xpath="//a[contains(@href,'us/content/place-card')]")
//    public WebElement lnk_PlaceCardUS;

    @FindBy(xpath = "//a[contains(.,'The My Place Rewards Credit Card')]")
    public WebElement lnk_PlaceCardUS;

}
