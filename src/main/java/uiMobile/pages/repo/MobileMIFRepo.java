package uiMobile.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;
import uiMobile.UiBaseMobile;

/**
 * Created by skonda on 6/2/2016.
 */
public class MobileMIFRepo extends UiBaseMobile {
    @FindBy(css=".moimg")
    public WebElement menuDropDown;

    @FindBy(xpath="//a[text()='Utilities']")
    public WebElement utilitiesTab;

    @FindBy(css=".tabs .tabon")
    public WebElement tabOpened;

    @FindBy(xpath="//a[text()='Post Message']")
    public WebElement postMessage;

    @FindBy(css="textarea[id='inputRequest']")
    public WebElement xmlTextArea;

    @FindBy(css="#sendMsg")
    public WebElement sendMsgButton;

    @FindBy(xpath="//table/tbody/tr[4]/td[3]")
    public WebElement messageSentSuccessfullyElement;

    @FindBy(xpath="//table/tbody/tr[5]/td[3]/textarea")
    public WebElement messageResponse;

    @FindBy(xpath="//table/tbody/tr[3]/td[3]")
    public WebElement messageResponseCode;
}
