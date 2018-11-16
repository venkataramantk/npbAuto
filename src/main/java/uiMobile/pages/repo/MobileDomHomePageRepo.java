package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MobileDomHomePageRepo extends UiBaseMobile {

    @FindBy(css="#textfield-1019-inputEl")
    public WebElement userNameFld;

    @FindBy(xpath="//a[text()='here']")
    public WebElement clickHereLinkToLogin;

    @FindBy(css="#textfield-1020-inputEl")
    public WebElement passwordFld;

    @FindBy(css="#button-1022")
    public WebElement loginBtn;

    @FindBy(css="#button-1012-btnIconEl")
    public WebElement menuBtn;

    @FindBy(xpath=".//*[@class='x-form-field x-form-text x-form-focus x-field-form-focus x-field-default-form-focus']")
    public WebElement menuSearchBox;

    @FindBy(xpath="//label[text()='ORD']")
    public WebElement tileORD;

    @FindBy(xpath="//span[text()='Select a Store']")
    public WebElement selectAStore;

    @FindBy(xpath="//span[@class='x-btn-button']/span[text()='OK']")
    public WebElement selectAStoreOKButton;

    @FindBy(css=".x-btn-wrap .x-btn-button span:nth-child(1)")
    public List<WebElement> buttonsList;

    @FindBy(xpath="//div[text()='0001']")
    public WebElement storeCode1Row;

    public WebElement storeCode1Row(String storeID){
        return getDriver().findElement(By.xpath("//div[text()='"+storeID+"']"));
    }
    @FindBy(xpath=".//*[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']")
    public WebElement storeModalArrow;

    @FindBy(xpath="//label[text()='RET']")
    public WebElement tileRET;

    @FindBy(xpath="(//input[@placeholder='order number'])[1]")
    public WebElement orderNumFldForORD;

    @FindBy(xpath="(//input[@placeholder='order number'])[2]")
    public WebElement orderNumFldForRET;

    @FindBy(xpath="(//table[@class='x-form-trigger-wrap']/tbody/tr/td[2]/div[1])[1]")
    public WebElement orderSearchButtonOnORD;

    @FindBy(xpath="(//table[@class='x-form-trigger-wrap']/tbody/tr/td[2]/div[1])[4]")
    public WebElement orderSearchButtonOnRET;

    @FindBy(css="#orderNumber-1")
    public WebElement ordNumResultOnSearch;

    @FindBy(xpath="//label[text()='CUSTOMER  TRANSACTIONS']")
    public WebElement customerTransactionsWindow;

    @FindBy(xpath="//label[text()='ORDER STATUS']")
    public WebElement ordStatusAtCustomerTrans;

    @FindBy(xpath="//label[text()='CREATE RETURN']")
    public WebElement createReturnWindow;


    @FindBy(xpath=".//*[@class='x-btn-icon-el']")
    public WebElement closeLinkOnOpenWindow;

    @FindBy(xpath=".//*[@class='x-btn-icon-el wt-window']")
    public WebElement openWindowsIcon;

    public WebElement storeIDOn_DOMHeader(String storeID){
        return getDriver().findElement(By.xpath("//div[text()='"+storeID+"']"));
    }
}
