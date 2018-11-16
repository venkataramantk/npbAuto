package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.MIFRepo;

/**
 * Created by skonda on 6/2/2016.
 */
public class MIFActions extends MIFRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(MIFActions.class);

    public MIFActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
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
