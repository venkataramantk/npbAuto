package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by AbdulazeezM on 10/26/2017.
 */
public class StaticContentCarrerPageRepo extends UiBase {

    @FindBy(xpath = ".//h3[@class='discover-roles__flag-buttons__us__title']")
    public WebElement carrerInUS_Content;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__us__buttons']//a[text()='CORPORATE']")
    public WebElement corporateBtn;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__us__buttons']//a[text()='STORES']")
    public WebElement storesBtn_US;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__us__buttons']//a[text()='DISTRIBUTION']")
    public WebElement distribution_Btn_US;

    @FindBy(xpath = ".//section[@class='discover-roles__flag-buttons__us']//h3[@class='discover-roles__flag-buttons__ca__title']")
    public WebElement storesBtn_CA;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__ca__buttons']//a[text()='STORES']")
    public WebElement carrerCA_Content;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__ca__buttons']//a[text()='DISTRIBUTION']")
    public WebElement distribution_Btn_CA;

    @FindBy(xpath = ".//ul[@class='discover-roles__flag-buttons__ca__buttons']//a[text()='IT CENTER']")
    public WebElement itCenter_IND;

    @FindBy(xpath = ".//div[@class='iCIMS_InfoMsg iCIMS_InfoMsg_JobSearch']//a")
    public WebElement allOpenJobsLink;

    @FindBy(css = ".form-control.Field_SearchKeywords.iCIMS_RoundedInput")
    public WebElement searchField;

    //@FindBy(xpath = ".//div[@class='iCIMS_SearchButtonKeywordContainer']//input[@class='iCIMS_PrimaryButton']")
    @FindBy(css = "#jsb_form_submit_i")
    public WebElement searchBtn;

    //@FindBy(xpath = ".//div[@class='iCIMS_InfoData iCIMS_InfoData_JobSearch iCIMS_TableCell']//select[@name='searchCategory']//option")
    @FindBy(css = "#jsb_f_position_s")
    public WebElement categoryDropdown;

    //@FindBy(xpath = ".//div[@class='iCIMS_InfoData iCIMS_InfoData_JobSearch iCIMS_TableCell']//select[@name='searchPositionType']//option")
    @FindBy(css = "#jsb_f_positiontype_s")
    public WebElement positionDropdown;

    //@FindBy(xpath = ".//div[@class='iCIMS_InfoData iCIMS_InfoData_JobSearch iCIMS_TableCell']//select[@name='searchLocation']//option")
    @FindBy(css = "#jsb_f_location_s")
    public WebElement locationDropdown;

    @FindBy(xpath = ".//div[@class='container-fluid iCIMS_SearchResultsHeader']//h2")
    public WebElement searchResultText;

    @FindBy(xpath = ".//span[@class='iCIMS_NavigationText']")
    public WebElement welcomePageLink;

    @FindBy(xpath = ".//div[@class='iCIMS_userMenuLink']")
    public WebElement logBackInLink;

    @FindBy(xpath = ".//div[@class='iCIMS_Message iCIMS_ErrorMessage iCIMS_GenericMessage']")
    public WebElement invalidErrorMsg;

    @FindBy(className= "iCIMS_InfoMsg_JobSearch")
    public WebElement noJobOpeningDisplay;

}
