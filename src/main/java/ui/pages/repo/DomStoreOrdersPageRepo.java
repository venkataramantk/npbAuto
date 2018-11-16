package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 9/20/2016.
 */
public class DomStoreOrdersPageRepo extends UiBase {

    @FindBy(xpath="//span[text()='Store Orders']")
    public WebElement storeOrdersFrameText;

    @FindBy(xpath=".//*[@id='dataForm:AJAXOrderSummaryPanel']/table/tbody/tr[2]/td[1]/table/tbody/tr[contains(.,'Pending acceptance :')]/td[3]/a")
    public WebElement pendingAcceptanceCountElement;

    @FindBy(xpath=".//*[@id='dataForm:OrderListPage_entityListView:releaseDataTable_body']/tbody/tr[contains(.,'Open')]")
    public List<WebElement> referenceOrderOpenStatuses;

    public WebElement checkBoxByReferenceOrder(String resOrder){
        return getDriver().findElement(By.xpath(".//*[@id='dataForm:OrderListPage_entityListView:releaseDataTable_body']/tbody/tr[contains(.,'"+resOrder+"')]/td[1]/input[1]"));
    }

    @FindBy(xpath=".//*[@id='buttonsInList_1']/div/table/tbody/tr/td[1]/input[@value='Accept / Print Pick List']")
    public WebElement acceptPrintPickListButton;

    @FindBy(xpath=".//*[@id='buttonsInList_1']/div/table/tbody/tr/td[1]/input[@value='Submit Pick']")
    public WebElement submitPickButton;

    @FindBy(css="input[id='dataForm:OrderDetailsSPC_entityListView:submitPickListTable_bulkPick:0:SubmitPick_param_input_AvailQty']")
    public WebElement quantityFld;

    @FindBy(css="input[id='dataForm:d_submit_finishOrder']")
    public WebElement finishOrderButton;

    @FindBy(css="span[id='dataForm:OrderDetailsMainHeader_Out_TransStatus']")
    public WebElement orderStatus;

    @FindBy(css="span[id='dataForm:isptl1:detailsTable:0:linestatus']")
    public WebElement itemStatus;

    @FindBy(css="span[id='dataForm:isptl1:detailsTable:0:reasonCode_output1']")
    public WebElement shortReasonElement;

    @FindBy(css="span[id='dataForm:OrderDetailsMainHeader_Out_ReferenceOrder']")
    public WebElement refOrderNumberElement;

    @FindBy(css="select[id='dataForm:OrderDetailsSPC_entityListView:submitPickListTable_bulkPick:0:CancelReasonCode_lineItem_DropDown']")
    public WebElement shortReasonDropDown;

    public WebElement statusByReferenceOrder(String resOrder){
        return getDriver().findElement(By.xpath(".//*[@id='dataForm:OrderListPage_entityListView:releaseDataTable_body']/tbody/tr[contains(.,'"+resOrder+"')]/td[5]/div/span"));
    }

    public WebElement referenceOrderRowPresent(String resOrder){
        return getDriver().findElement(By.xpath(".//*[@id='dataForm:OrderListPage_entityListView:releaseDataTable_body']/tbody/tr[contains(.,'"+resOrder+"')]"));
    }

    @FindBy(css="input[id='dataForm:OrderListPage_entityListView:releaseDataTable:pager:next']")
    public WebElement nextArrow;

}
