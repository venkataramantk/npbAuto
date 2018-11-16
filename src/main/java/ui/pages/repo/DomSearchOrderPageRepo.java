package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 5/26/2016.
 */
public class DomSearchOrderPageRepo extends UiBase {
    @FindBy(xpath="//span[text()='Search Orders']")
    public WebElement searchOrdersFrameText;

    @FindBy(css="input[id='dataForm:listView1:coListFilterId:field440value1']")
    public WebElement orderNumTextBox;

    @FindBy(css="input[id='dataForm:listView1:coListFilterId:coListFilterIdapply']")
    public WebElement orderApplyBtn;

    @FindBy(css=".x-tool-img.x-tool-maximize")
    public WebElement maximizeImg;

    @FindBy(css=".x-component.x-fit-item.x-window-item.x-component-default>iframe ")
    public WebElement frame;

    @FindBy(css="span[id='dataForm:listView1:coList_sync_list_comp_table_id:0:coList_OrderNbr_ColValue']")
    public WebElement orderNumberElement;

    @FindBy(css="input[id='dataForm:coList_View_btn_id']")
    public WebElement viewOrderDetailsButton;

    @FindBy(css="span[id='dataForm:listView1:coList_sync_list_comp_table_id:0:coList_status_ColValue']")
    public WebElement orderStatusElement;

    @FindBy(css="input[id='dataForm:coViewAI_releaseDOBtn']")
    public WebElement releaseDOButton;

    @FindBy(css="input[id='dataForm:coViewAI_createDOBtn']")
    public WebElement createDOButton;

    @FindBy(css="select[id='dataForm:drop_down_page_ids_som']")
    public WebElement moreDropDownButton;

    @FindBy(css=".x-tool-img.x-tool-refresh")
    public WebElement refreshImg;

    @FindBy(css="span[id='dataForm:op_txt_postorder_view_createCO_PaymentStatus']")
    public WebElement payStatus;

    @FindBy(css="span[id='dataForm:coROILTable:0:coROILCOLineStatusStr']")
    public WebElement lineStatus;

    public WebElement lineStatus(int i){
        return getDriver().findElement(By.cssSelector("span[id='dataForm:coROILTable:"+i+":coROILCOLineStatusStr']"));
    }

    @FindBy(css="span[id='dataForm:op_txt_postorder_view_createCO_status']")
    public WebElement statusTextElement;

    @FindBy(css="div[id='dataForm:busy_dialog']  .pop_body.-pdlg_dbg tr:nth-child(2) td")
    public WebElement pleaseWaitPopUpAfterSaveLineDetails;

    @FindBy(css="span[id='customerOrderAdditionalDetailTab_lnk'")
    public WebElement orderAdditionalDetailsTab;

    //Distribution Order Details Page
    @FindBy(css="span[id='dataForm:dolinelistview_id:DOLineList_MainListTable:0:DO_Lines_List_DOId_param_Out']")
    public WebElement doNumber;

    @FindBy(xpath=".//*[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr")
    public List<WebElement> doLines;

    public WebElement doNum(int j)
    {
        return getDriver().findElement(By.cssSelector("span[id='dataForm:dolinelistview_id:DOLineList_MainListTable:" + (j - 1) + ":DO_Lines_List_DOId_param_Out']"));
    }

    public WebElement itemNum(int j)
    {
        return getDriver().findElement(By.cssSelector("a[id='dataForm:dolinelistview_id:DOLineList_MainListTable:"+ (j - 1) + ":doLine_order_item_id']"));
    }

    public WebElement doLineNum(int j)
    {
        return getDriver().findElement(By.cssSelector("span[id='dataForm:dolinelistview_id:DOLineList_MainListTable:"+ (j - 1) + ":DO_Lines_List_DOLineId_param_Out']"));
    }

    @FindBy(css="span[id='dataForm:dolinelistview_id:DOLineList_MainListTable:0:DO_Lines_List_ord_qty_ot']")
    public WebElement orderedQuantity;

    public WebElement orderedQtyAtOrderDetails(int i){
        return getDriver().findElement(By.cssSelector("span[id='dataForm:coROILTable:"+i+":coROILCOLineItemQuantity"));

    }

    public WebElement orderedQty(int j)
    {
        return getDriver().findElement(By.cssSelector("span[id='dataForm:dolinelistview_id:DOLineList_MainListTable:" + (j - 1)+ ":DO_Lines_List_ord_qty_ot']"));
    }

    @FindBy(css="img[id='dataForm:navigation_flow_a4j_repeat:2:navigationStepIconWithHyperlink']")
    public WebElement shippingInfoImg;

    @FindBy(css="span[id='dataForm:coLineViewShipToDetailsShippingMethodOpText'")
    public WebElement shippingMethod;

    @FindBy(xpath=".//*[@id='dataForm:coROILTable_body']/tbody/tr")
    public List<WebElement> itemList;

    public WebElement itemStatusAtOrderDetails(int i){
        return getDriver().findElement(By.cssSelector("span[id='dataForm:coROILTable:"+i+":coROILCOLineStatusStr']"));
    }

    public WebElement itemDescription(int i){
        return getDriver().findElement(By.cssSelector("span[id='dataForm:coROILTable:"+i+":coROILCOLineItemDescription']"));
    }

    public WebElement itemID(int i){
        return getDriver().findElement(By.cssSelector("a[id='dataForm:coROILTable:"+i+":coROILCOLineItemIdLink']"));
    }
    public WebElement qty(int i){
        return getDriver().findElement(By.cssSelector("a[id='dataForm:coROILTable:"+i+":coROILCOLineItemIdLink']"));
    }
}
