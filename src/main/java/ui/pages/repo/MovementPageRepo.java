package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by venkat on 12/07/2018.
 */

public class MovementPageRepo extends UiBase {

    //Add New Consignor Address or Update Consignor Address

    @FindBy(xpath = "//*[@ng-reflect-layout-index='0,0']//span[contains(text(),'Add New')]")
    public WebElement consignorAddNewBtn;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Name')]/following-sibling::ion-input//input")
    public WebElement consignorNameTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Save')]")
    public WebElement consignorSaveInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Update')]")
    public WebElement consignorUpdateInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address')]/following-sibling::ion-input//input")
    public WebElement consignorAddressTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address Line')]/following-sibling::ion-input//input")
    public WebElement consignorAddress2Txt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'City')]/following-sibling::ion-input//input")
    public WebElement consignorCityTxt;

    public WebElement consignorStateSelectionByStateCode(String stateCode){
        return getDriver().findElement(By.xpath("//div[contains(.,'Consignor')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'State')]/following-sibling::angular2-multiselect//label[contains(.,'"+stateCode+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Zip')]/following-sibling::ion-input//input")
    public WebElement consignorZipTxt;

    public WebElement consignorCountySelectionByCountyName(String countyName){
        return getDriver().findElement(By.xpath("//div[contains(.,'Consignor')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'County')]/following-sibling::angular2-multiselect//label[contains(.,'"+countyName+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Phone')]/following-sibling::ion-input//input")
    public WebElement consignorPhoneTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Latitude')]/following-sibling::ion-input//input")
    public WebElement consignorLatTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Longitude')]/following-sibling::ion-input//input")
    public WebElement consignorLongTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Email')]/following-sibling::ion-input//input")
    public WebElement consignorEmailTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Premises')]/following-sibling::ion-input//input")
    public WebElement consignorPremIDTxt;

    @FindBy(xpath = "//div[contains(.,'Consignor')]/ancestor::div[@class='form-card']//div[@class='directory-button-wrapper']//span[contains(.,'Cancel')]/parent::button")
    public WebElement consignorCancelBtn;


    //Add New Location of Animals or Update Location

    @FindBy(xpath = "//*[@ng-reflect-layout-index='0,1']//span[contains(text(),'Add New')]")
    public WebElement locationAddNewBtn;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Name')]/following-sibling::ion-input//input")
    public WebElement locationNameTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Save')]")
    public WebElement locationSaveInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Update')]")
    public WebElement locationUpdateInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address')]/following-sibling::ion-input//input")
    public WebElement locationAddressTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address Line')]/following-sibling::ion-input//input")
    public WebElement locationAddress2Txt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'City')]/following-sibling::ion-input//input")
    public WebElement locationCityTxt;

    public WebElement locationStateSelectionByStateCode(String stateCode){
        return getDriver().findElement(By.xpath("//div[contains(.,'Location')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'State')]/following-sibling::angular2-multiselect//label[contains(.,'"+stateCode+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Zip')]/following-sibling::ion-input//input")
    public WebElement locationZipTxt;

    public WebElement locationCountySelectionByCountyName(String countyName){
        return getDriver().findElement(By.xpath("//div[contains(.,'Location')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'County')]/following-sibling::angular2-multiselect//label[contains(.,'"+countyName+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Phone')]/following-sibling::ion-input//input")
    public WebElement locationPhoneTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Latitude')]/following-sibling::ion-input//input")
    public WebElement locationLatTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Longitude')]/following-sibling::ion-input//input")
    public WebElement locationLongTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Email')]/following-sibling::ion-input//input")
    public WebElement locationEmailTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Premises')]/following-sibling::ion-input//input")
    public WebElement locationPremIDTxt;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//div[@class='directory-button-wrapper']//span[contains(.,'GPS')]/parent::button")
    public WebElement locationGPSBtn;

    @FindBy(xpath = "//div[contains(.,'Location')]/ancestor::div[@class='form-card']//div[@class='directory-button-wrapper']//span[contains(.,'Cancel')]/parent::button")
    public WebElement locationCancelBtn;


    //Area Status section
    @FindBy(xpath = "//div[@class='section-header']//label[contains(.,'Area Status')]")
    public WebElement areaStatusLabel;

    @FindBy(xpath = "//ion-label[contains(.,'Bovine Tuberculosis')]/ancestor::div[contains(@class,'inline-segment')]//ion-segment-button[contains(.,'Free')]")
    public WebElement bovineTBFreeBtn;

    @FindBy(xpath = "//ion-label[contains(.,'Bovine Tuberculosis')]/ancestor::div[contains(@class,'inline-segment')]//ion-segment-button[contains(.,'MA')][@ng-reflect-value='MA']")
    public WebElement bovineTBMABtn;

    @FindBy(xpath = "//ion-label[contains(.,'Bovine Tuberculosis')]/ancestor::div[contains(@class,'inline-segment')]//ion-segment-button[contains(.,'MAA')]")
    public WebElement bovineTBMAABtn;

    @FindBy(xpath = "//ion-label[contains(.,'Brucellosis')]/ancestor::div[contains(@class,'inline-segment')]//ion-segment-button[contains(.,'Free')]")
    public WebElement brucellocisFreeBtn;

    @FindBy(xpath = "//ion-label[contains(.,'Brucellosis')]/ancestor::div[contains(@class,'inline-segment')]//ion-segment-button[contains(.,'DSA')]")
    public WebElement brucellosisDSABtn;

    @FindBy(xpath = "//ion-label[contains(.,'Other')]/following-sibling::ion-input//input")
    public WebElement otherTxtFld;




    //Add New Consignee Address or Update Consignee Address

    @FindBy(xpath = "//*[@ng-reflect-layout-index='1,0']//span[contains(text(),'Add New')]")
    public WebElement consigneeAddNewBtn;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Name')]/following-sibling::ion-input//input")
    public WebElement consigneeNameTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Save')]")
    public WebElement consigneeSaveInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Update')]")
    public WebElement consigneeUpdateInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address')]/following-sibling::ion-input//input")
    public WebElement consigneeAddressTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address Line')]/following-sibling::ion-input//input")
    public WebElement consigneeAddress2Txt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'City')]/following-sibling::ion-input//input")
    public WebElement consigneeCityTxt;

    public WebElement consigneeStateSelectionByStateCode(String stateCode){
        return getDriver().findElement(By.xpath("//div[contains(.,'Consignee')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'State')]/following-sibling::angular2-multiselect//label[contains(.,'"+stateCode+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Zip')]/following-sibling::ion-input//input")
    public WebElement consigneeZipTxt;

    public WebElement consigneeCountySelectionByCountyName(String countyName){
        return getDriver().findElement(By.xpath("//div[contains(.,'Consignee')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'County')]/following-sibling::angular2-multiselect//label[contains(.,'"+countyName+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Phone')]/following-sibling::ion-input//input")
    public WebElement consigneePhoneTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Latitude')]/following-sibling::ion-input//input")
    public WebElement consigneeLatTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Longitude')]/following-sibling::ion-input//input")
    public WebElement consigneeLongTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Email')]/following-sibling::ion-input//input")
    public WebElement consigneeEmailTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Premises')]/following-sibling::ion-input//input")
    public WebElement consigneePremIDTxt;

    @FindBy(xpath = "//div[contains(.,'Consignee')]/ancestor::div[@class='form-card']//div[@class='directory-button-wrapper']//span[contains(.,'Cancel')]/parent::button")
    public WebElement consigneeCancelBtn;


    //Add New Destination of Animals or Update Destination

    @FindBy(xpath = "//*[@ng-reflect-layout-index='1,1']//span[contains(text(),'Add New')]")
    public WebElement destinationAddNewBtn;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Name')]/following-sibling::ion-input//input")
    public WebElement destinationNameTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Save')]")
    public WebElement destinationSaveInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Update')]")
    public WebElement destinationUpdateInDirectoryLbl;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address')]/following-sibling::ion-input//input")
    public WebElement destinationAddressTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Address Line')]/following-sibling::ion-input//input")
    public WebElement destinationAddress2Txt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'City')]/following-sibling::ion-input//input")
    public WebElement destinationCityTxt;

    public WebElement destinationStateSelectionByStateCode(String stateCode){
        return getDriver().findElement(By.xpath("//div[contains(.,'Destination')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'State')]/following-sibling::angular2-multiselect//label[contains(.,'"+stateCode+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Zip')]/following-sibling::ion-input//input")
    public WebElement destinationZipTxt;

    public WebElement destinationCountySelectionByCountyName(String countyName){
        return getDriver().findElement(By.xpath("//div[contains(.,'Destination')]/ancestor::" +
                "div[@class='form-card']//ion-label[contains(.,'County')]/following-sibling::angular2-multiselect//label[contains(.,'"+countyName+"')]/parent::li"));
    }

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Phone')]/following-sibling::ion-input//input")
    public WebElement destinationPhoneTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Latitude')]/following-sibling::ion-input//input")
    public WebElement destinationLatTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Longitude')]/following-sibling::ion-input//input")
    public WebElement destinationLongTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Email')]/following-sibling::ion-input//input")
    public WebElement destinationEmailTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//ion-label[contains(.,'Premises')]/following-sibling::ion-input//input")
    public WebElement destinationPremIDTxt;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//div[@class='directory-button-wrapper']//span[contains(.,'GPS')]/parent::button")
    public WebElement destinationGPSBtn;

    @FindBy(xpath = "//div[contains(.,'Destination')]/ancestor::div[@class='form-card']//div[@class='directory-button-wrapper']//span[contains(.,'Cancel')]/parent::button")
    public WebElement destinationCancelBtn;


    //Carrier and Transport

    @FindBy(xpath = "//ion-label[contains(.,'Carrier')]/ancestor::div[@class='segment-button']//ion-segment-button[contains(.,'Consignor')]")
    public WebElement carrierConsignorBtn;

    @FindBy(xpath = "//ion-label[contains(.,'Carrier')]/ancestor::div[@class='segment-button']//ion-segment-button[contains(.,'Consignee')]")
    public WebElement carrierConsigneeBtn;

    @FindBy(xpath = "//ion-label[contains(.,'Carrier')]/ancestor::div[@class='segment-button']//ion-segment-button[contains(.,'Other')]")
    public WebElement carrierOtherBtn;

    //Other Carrier details

    @FindBy(xpath = "//label[contains(.,'Carrier & Transport')]/ancestor::div[contains(@class,'section-wrapper')]//ion-label[contains(.,'Name')]/following-sibling::ion-input//input")
    public WebElement otherCarrierNameTxt;

    @FindBy(xpath = "//label[contains(.,'Carrier & Transport')]/ancestor::div[contains(@class,'section-wrapper')]//ion-label[contains(.,'Address')]/following-sibling::ion-input//input")
    public WebElement otherCarrierAddressTxt;

    @FindBy(xpath = "//label[contains(.,'Carrier & Transport')]/ancestor::div[contains(@class,'section-wrapper')]//ion-label[contains(.,'City')]/following-sibling::ion-input//input")
    public WebElement otherCarrierCityTxt;

    public WebElement otherStateSelectionByStateCode(String stateCode){
        return getDriver().findElement(By.xpath("//label[contains(.,'Carrier & Transport')]/ancestor::" +
                "div[contains(@class,'section-wrapper')]//ion-label[contains(.,'State')]/following-sibling::angular2-multiselect//label[contains(.,'"+stateCode+"')]/parent::li"));
    }

    @FindBy(xpath = "//label[contains(.,'Carrier & Transport')]/ancestor::div[contains(@class,'section-wrapper')]//ion-label[contains(.,'Zip')]/following-sibling::ion-input//input")
    public WebElement otherCarrierZipTxt;

    @FindBy(xpath = "//label[contains(.,'Carrier & Transport')]/ancestor::div[contains(@class,'section-wrapper')]//ion-label[contains(.,'Phone')]/following-sibling::ion-input//input")
    public WebElement otherCarrierPhoneTxt;

    @FindBy(xpath = "//label[contains(.,'Carrier & Transport')]/ancestor::div[contains(@class,'section-wrapper')]//ion-label[contains(.,'Email')]/following-sibling::ion-input//input")
    public WebElement otherCarrierEmailTxt;


    @FindBy(xpath = "//ion-label[contains(.,'Interstate')]/ancestor::div[@class='segment-button']//ion-segment-button[contains(.,'Yes')]")
    public WebElement carrierInterstateBtn;

    @FindBy(xpath = "//ion-label[contains(.,'Interstate')]/ancestor::div[@class='segment-button']//ion-segment-button[contains(.,'No')]")
    public WebElement carrierIntrastateBtn;

    @FindBy(xpath = "//ion-label[contains(.,'Transport Date')]/ancestor::div[@class='clearfix']//input")
    public WebElement carrierTransportDateTxt;

    public WebElement carrierTransportMethodSelectionByType(String transportType){
        return getDriver().findElement(By.xpath("//ion-label[contains(.,'Transport Method')]/following-sibling::angular2-multiselect//label[contains(.,'"+transportType+"')]/parent::li"));
    }


    @FindBy(xpath = "//span[contains(.,'Next')]/parent::button")
    public WebElement movementNextBtn;

}
