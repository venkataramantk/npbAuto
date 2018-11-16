package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileDomCustomerOrderRepo;

/**
 * Created by skonda on 7/20/2016.
 */
public class MobileDomCustomerOrderActions extends MobileDomCustomerOrderRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileDomCustomerOrderActions.class);

    public MobileDomCustomerOrderActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public void clickAppeasements(){
        waitUntilElementDisplayed(appeasementsElement);
        String appeasementId = getAttributeValue(appeasementsElement,"id");
        clickJavaScriptExecute(appeasementId);
        waitUntilElementDisplayed(appeasementsWindowName);
    }
    public boolean clickAddAppeasementsAtOrderLevel(){
        waitUntilElementDisplayed(addAppeasementOrderLevel);
        String addAppeaseAtOrderId = getAttributeValue(addAppeasementOrderLevel,"id");
        clickJavaScriptExecute(addAppeaseAtOrderId);
        return waitUntilElementDisplayed(dollarOffRadioButton);
    }
    public int addAppeasementsLinesCount(){
        return addAppeasementLinks.size();
    }

    public boolean clickAddAppeasementsAtLineLevel(int i){
        waitUntilElementDisplayed(addAppeasementOrderLevel);
        String addAppeaseId = getAttributeValue(addAppeasementLineLevelByPos(i),"id");
        clickJavaScriptExecute(addAppeaseId);
        return waitUntilElementDisplayed(dollarOffRadioButton);
    }


    public boolean applyAppeasement(String reason,String amtOff){
        double beforeAppliedAppeasement = getAppliedAppeasements();
        click(dollarOffRadioButton);
        clearAndFillText(dollarOffFieldElement,amtOff);
        staticWait();
        click(selectAReasonDropDown);
        waitUntilElementDisplayed(selectAReasonByOption(reason));
        click(selectAReasonByOption(reason));
        staticWait();
        String applyButtonID = getAttributeValue(applyButton,"id");
        clickJavaScriptExecute(applyButtonID);
        double amtDollarOff = Double.valueOf(amtOff);
        double totalAppeasementCalc = Math.round((beforeAppliedAppeasement + amtDollarOff) * 100)/100;
        double afterAppliedAppeaseFromUI = getAppliedAppeasements();
        return totalAppeasementCalc == afterAppliedAppeaseFromUI;
    }



    public boolean clickAndAddAppeasementsAtOrderLevelAndVerify(String reason,String amtOff){
        clickAppeasements();
        clickAddAppeasementsAtOrderLevel();
        return applyAppeasement(reason, amtOff);
    }

    public boolean clickAndAddAppeasementsAtLineLevelAndVerify(String reason,String amtOff, int position){
        clickAppeasements();
        clickAddAppeasementsAtLineLevel(position);
        return applyAppeasement(reason,amtOff);
    }

    public double getAppliedAppeasements(){
        return textToPrice(getText(totalAppeasementsValue));
    }
    public double textToPrice(String priceText){
        if(priceText.contains("$")){
            priceText = priceText.replace("$","");
        }
        if(priceText.contains(",")){
            priceText = priceText.replace(",","");
        }
        if(priceText.contains("-")){
            priceText = priceText.replace("-","");
        }if(priceText.contains(" ")){
            priceText = priceText.replace(" ","");
        }
        double price = new Double(priceText);
        return price;
    }
}
