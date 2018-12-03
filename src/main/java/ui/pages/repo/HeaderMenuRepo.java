package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class HeaderMenuRepo extends FooterRepo {

    @FindBy(css = ".av-active[title='User Administration and Preferences']")
    public WebElement dashboardLnk;

    @Deprecated
    @FindBy(xpath = "//li[@id='customer-name']//a[contains(.,'Log Out') or contains(.,'Déconnexion') or contains(.,'Salir')]")
    public WebElement logOut;

    @Deprecated
    @FindBy(css = ".welcome-message button[alt='My Account']")
    public WebElement myAccountLink;

    @Deprecated
    public WebElement myAccountLinkWithText(String text) {
        return getDriver().findElement(By.xpath("//li/a[text()='" + text + "']"));
    }

    public WebElement subCategoryUnderClearanceByPos(int i) {
        return getDriver().findElement(By.xpath("(//ul[@class='menus'])[13]/li[" + i + "]/a"));
    }

    public WebElement deptLinkWithName(String deptName) {
        String deptNameUC = deptName.toUpperCase();
        String deptNameLC = deptName.toLowerCase();
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one']/a[contains(translate(., '" + deptNameUC + "', '" + deptNameLC + "'), '" + deptNameLC + "')]"));
    }

    @FindBy(css = "img.fd-logo-menu")
    public WebElement logoAgview;

    @Deprecated//FooterRepo.shipToLink (ShipTo link is not a link (R2 code))
    @FindBy(css = "#WC_TCPCachedCommonToolbar_FormLink_IShipping1")
    public WebElement shipToLink;

    @Deprecated//@link refer #FooterRepo
    public WebElement countryImgByCountry(String country) {
        return getDriver().findElement(By.cssSelector("img[title='Ship to Country: " + country + "']"));
    }

    @Deprecated
    /*@FindBy(css = "#bag-icon-link")*/
    @FindBy(css = ".minicart-container .button-cart")
    public WebElement shoppingCartImg;

    @Deprecated
   @FindBy(css = "ul>.points-total")
    public WebElement myPointsLink;


    @Deprecated//Please refer #wishListLink
    @FindBy(css = ".button-add-to-wishlist")
    public WebElement wishListIcon;


    public WebElement deptLinksContainer(String deptName) {
//        return getDriver().findElement(By.xpath("//a[contains(@href,'/c/')][.='"+ deptName +"']/following-sibling::div"));
        return getDriver().findElement(By.xpath("//li[@class='navigation-level-one'][a[text()='" + deptName + "']]/div"));
    }


}

