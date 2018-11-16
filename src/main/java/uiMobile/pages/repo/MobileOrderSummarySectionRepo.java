package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 *  Created by skonda on 3/31/2017.
 */
public class MobileOrderSummarySectionRepo extends UiBaseMobile {
    @FindBy(css=".items-total strong")
    public WebElement itemsTotalPrice;

    @FindBy(css=".items-total")
    public WebElement itemsCount;

    @FindBy(css=".shipping-total>strong")
    public WebElement shippingTotalPrice;

    @FindBy(css=".tax-total>strong")
    public WebElement tax_Total;

    @FindBy(css=".balance-total>strong")
    public WebElement estimated_Total;

    @FindBy(xpath="//ol[@class='order-summary']/li")
    public List<WebElement> orderSummaryLineItems;
}
