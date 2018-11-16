package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by skonda on 7/20/2016.
 */
public class DomCustomerOrderRepo extends UiBase {
    @FindBy(xpath="//span[text()='APPEASEMENTS']")
    public WebElement appeasementsElement;

    @FindBy(xpath="//label[text()='APPEASEMENTS']")
    public WebElement appeasementsWindowName;

    @FindBy(xpath="(//span[text()='ADD APPEASEMENTS'])[1]")
    public WebElement addAppeasementOrderLevel;

    public WebElement addAppeasementLineLevelByPos(int i){
        return getDriver().findElement(By.xpath("(//span[text()='ADD APPEASEMENTS'])["+i+"]"));
    }

    @FindBy(xpath="//span[text()='ADD APPEASEMENTS']")
    public List<WebElement> addAppeasementLinks;

    @FindBy(xpath="//label[text()='$Off']")
    public WebElement dollarOffRadioButton;

    @FindBy(xpath="//input[@name='appeasementType' and @placeholder='0.00']")
    public WebElement dollarOffFieldElement;

    @FindBy(xpath="//input[@placeholder='Select a reason']")
    public WebElement selectAReasonDropDown;

    public WebElement selectAReasonByOption(String reason){
        return getDriver().findElement(By.xpath("//li[text()='"+reason+"']"));
    }

    @FindBy(xpath="//textarea[@placeholder='Describe the reason']")
    public WebElement describeTheReasonTextArea ;

    @FindBy(xpath="//span[text()='APPLY']")
    public WebElement applyButton ;

    @FindBy(xpath="//tr[contains(.,'Total appeasements value')]/td[2]/div")
    public WebElement totalAppeasementsValue ;

    @FindBy(css=".x-tool-img.x-tool-close")
    public WebElement closeAppeasementsWindow ;
}
