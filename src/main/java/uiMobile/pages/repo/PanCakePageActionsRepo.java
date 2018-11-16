package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class PanCakePageActionsRepo extends UiBaseMobile {

    @FindBy(css = ".create-account-link")
    public WebElement createAccLink;

    @FindBy(css = ".login-link")
    public WebElement loginLink;

    @FindBy(css = ".my-place-rewards-link")
    public WebElement mprLink;

    @FindBy(css = ".title-home-link")
    public WebElement homeLink;

    @FindBy(css = ".view-account")
    public WebElement myAccountLink;

    @FindBy(css = ".wishlist-link")
    public WebElement favoritesLink;

    @FindBy(css = ".button-find-store")
    public WebElement findAstoreLink;

    @FindBy(css = ".my-place-rewards-link")
    public WebElement myPlaceRewardsLink;

    @FindBy(css = ".welcome-message")
    public WebElement salutationLink;

    @FindBy(css = ".menu-header .menu-close")
    public WebElement menuClose;

    @FindBy(css = ".hello-message")
    public WebElement salutationLinkOnLogin;

    @FindBy(css = ".view-account")
    public WebElement myAccount;

    @FindBy(css = ".close-menu")
    public WebElement closePanCake;

    @FindBy(css = ".welcome-menu")
    public WebElement panLinks;

    @FindBy(id = "ecom-gift-cards")
    public WebElement buyGiftCards;

    @FindBy(css = ".coupon-list-container")
    public WebElement mycoupons;

    @FindBy(css = ".navigation-level-one")
    public WebElement levelone;

    @FindBy(css = ".navigation-level-one")
    public List<WebElement> levelones;

    @FindBy(css = ".navigation-level-two")
    public WebElement leveltwo;

    @FindBy(css = ".accordion-expanded.accordion-category-list .accordion-category-sub-list h4")
    public List<WebElement> leveltwos;

    //Pooja
    public WebElement getLTwoText(String text) {
        return getDriver().findElement(By.xpath("//div[@class='sub-menu active']/ul/li[@class='navigation-level-two']/a[text()='" + text + "']"));
    }

    //Pooja
//    public WebElement lThreeCategory(String categoryName) {
//        return getDriver().findElement(By.xpath("//div[@class='sub-menu active']/ul/child::li/a[text()='" + categoryName + "']"));
//    }

    @FindBy(xpath = "//div[@class='accordion accordion-expanded accordion-category-sub-list']//li/a[text()='Shop All']")
    public WebElement levelthree;

    //Pooja
    @FindBy(xpath = "//li[@class='navigation-level-two']/div[@class='sub-menu active']/ul/li[@class='navigation-level-two']/a")
    public List<WebElement> levelthrees;

    @FindBy(xpath = "//li[@class='navigation-level-one']//a[contains(@href,'/c/14501') or contains(@href,'/c/girls-clothing')]")
    public WebElement loneGirl;

    @FindBy(xpath = "//li[@class='navigation-level-one']//a[contains(@href,'/c/14502') or contains(@href,'/c/toddler-girl-clothes')]")
    public WebElement loneTGirl;

    @FindBy(xpath = "//li[@class='navigation-level-one']//a[contains(@href,'ca/c/14503') or contains(@href,'us/c/boys-clothing')]")
    public WebElement loneBoy;

    @FindBy(xpath = "//li[@class='navigation-level-one']//a[contains(@href,'ca/c/14504') or contains(@href,'us/c/toddler-boy-clothes')]")
    public WebElement loneTBoy;

    @FindBy(xpath = "//li[@class='navigation-level-one']//a[contains(@href,'ca/c/14505') or contains(@href,'us/c/baby-clothes')]")
    public WebElement loneBaby;

    @FindBy(css = ".navigation-level-one>a[href*='/c/childrens-shoes-kids-shoes']")
    public WebElement loneShoes;

    @FindBy(css = ".navigation-level-one>a[href*='/c/kids-accessories']")
    public WebElement loneAccessories;

    @FindBy(xpath = "//li[@class='navigation-level-one']/a/span[text()='Clearance']")
    public WebElement loneclearance;

    //@FindBy(css = ".navigation-level-one>.sub-menu.active>.header-sub-menu>h3")
    @FindBy(xpath = "//div[@class='accordion accordion-expanded accordian-main-category']/h4/span/span[1]")
    public WebElement lTwoHeader;

    @FindBy(css = ".rewards-points p:nth-child(1)")
    public WebElement currentPoints;

    @FindBy(css = ".rewards-points p:nth-child(2)")
    public WebElement myRewards;

    @FindBy(css = ".order-status a")
    public WebElement orderNumber;

    public WebElement getLone(String cat) {
        return getDriver().findElement(By.xpath("//h3[text()='" + cat + "']"));
    }

    @FindBy(css = ".size-description")
    public List<WebElement> sizeRanges;

    //From Lion-11
    public WebElement lOneCategory(String categoryName) {
        return getDriver().findElement(By.xpath("//span[text()='" + categoryName.toUpperCase() + "']"));
    }

    public WebElement subSection(String categoryName) {
        return getDriver().findElement(By.xpath("//h4[text()='" + categoryName.toUpperCase() + "']"));
    }

    public WebElement lTwoCategory(String categoryName) {
        return getDriver().findElement(By.xpath("//h4[text()='" + categoryName + "']"));
    }

    public WebElement lThreeCategory(String categoryName) {
        return getDriver().findElement(By.linkText(categoryName));
    }
}
