package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileMIFRepo;

/**
 * Created by skonda on 6/2/2016.
 */
public class MobileMIFActions extends MobileMIFRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileMIFActions.class);

    public MobileMIFActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean clickMenuDropDownDown(){
        waitUntilElementDisplayed(menuDropDown);
        click(menuDropDown);
        return waitUntilElementDisplayed(utilitiesTab);
    }

    public boolean clickUtilitiesTab(){
        if(!getText(tabOpened).equalsIgnoreCase("Utilities")) {
            click(utilitiesTab);
            return waitUntilElementDisplayed(postMessage);
        }
        else{
            return waitUntilElementDisplayed(postMessage);
        }
    }

    public boolean navigateToPostMsgTxtArea(){
        click(postMessage);
        switchToFrame("iframe1");
        return waitUntilElementDisplayed(sendMsgButton);
    }

    public boolean enterXmlToPostMessage(String xml){

        xmlTextArea.sendKeys(Keys.TAB);
        clearAndFillText(xmlTextArea, xml);
        click(sendMsgButton);
       return waitForTextToAppear(messageSentSuccessfullyElement, "Message sent to destination successfully", 30);

    }

    public boolean postXmlMessageFromMIF(String xmlMsg){
        clickMenuDropDownDown();
        clickUtilitiesTab();
        navigateToPostMsgTxtArea();
        logger.info("Posting with this XML Message: "+xmlMsg);
        return enterXmlToPostMessage(xmlMsg);
    }

    public String getResponse(){
        String response = getText(messageResponse);
        logger.info("The Response is: "+response);
        return response;
    }

    public String getResponseCode(){
        String responseCode = getText(messageResponseCode);
        logger.info("The Response Code is: "+responseCode);
        return responseCode;
    }

}
