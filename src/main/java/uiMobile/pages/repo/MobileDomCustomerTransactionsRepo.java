package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileDomCustomerTransactionsRepo extends UiBaseMobile {
    @FindBy(xpath="//table[@class='x-field olm-displayfield x-table-plain x-form-item x-form-readonly x-form-type-text x-box-item x-field-default x-vbox-form-item']/tbody/tr[contains(.,'Invoice status')]/td[2]")
    public WebElement invoiceStatus;


    @FindBy(xpath="//div[contains(.,'order')]/table/tbody/tr/td[2]/table/tbody/tr/td/input[@placeholder='Keyword Search']")
    public WebElement orderKeywordSearch;

    @FindBy(xpath="//div[contains(.,'order')]/table/tbody/tr/td[2]/table/tbody/tr/td/div")
    public WebElement orderSearchButton;

    public WebElement orderHighLightedSearchResult(String orderNum){
        return getDriver().findElement(By.xpath(".//*[@id='orderNumber-1']/span[text()='"+orderNum+"']"));
    }

    @FindBy(xpath="//label[text()='ORDER DETAILS']")
    public WebElement orderDetailsAtCustomerOrdWin;
}
