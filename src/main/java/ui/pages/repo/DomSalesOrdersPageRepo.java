package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by skonda on 9/20/2016.
 */
public class DomSalesOrdersPageRepo extends UiBase {
    @FindBy(xpath="//span[text()='Sales Orders']")
    public WebElement salesOrdersFrameText;

    @FindBy(xpath=".//*[@id='dataForm:SOList_entityListView:SOList_filterId2:field3value1']")
    public WebElement orderNumberFld;

    @FindBy(xpath=".//*[@id='dataForm:SOList_entityListView:SOList_filterId2:SOList_filterId2apply']")
    public WebElement applyButton;

    @FindBy(xpath=".//*[@id='dataForm:SOList_entityListView:salesOrderData:0:statusDesc1']")
    public WebElement orderStatusElement;

    @FindBy(xpath=".//*[@id='dataForm:SOList_entityListView:salesOrderData_body']/tbody/tr[1]/td[4]/div/span")
    public WebElement orderNumberElement;
}
