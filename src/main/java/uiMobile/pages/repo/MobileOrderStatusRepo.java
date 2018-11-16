package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by user on 6/15/2016.
 */
public class MobileOrderStatusRepo extends UiBaseMobile {


    @FindBy(css = ".order-summary>li:nth-child(1) strong")
    public WebElement itemsTotal;

    @FindBy(css = ".order-number")
    public WebElement orderNoLink;

    @FindBy(xpath = ".//li[@class='balance-total']//strong")
    public WebElement estimatedTot;

    @FindBy(css = ".card-info")
    public WebElement cardDetails;

    @FindBy(css = ".product-title-in-table")
    public WebElement prodName;

    @FindBy(css = ".container-description-view")
    public WebElement prodDetails;

    @FindBy(css = ".subtotal")
    public WebElement subTotal;

    @FindBy(css = ".store-info-data")
    public WebElement storeData;

    @FindBy(css = ".address-container")
    public WebElement profileInfo;
}
