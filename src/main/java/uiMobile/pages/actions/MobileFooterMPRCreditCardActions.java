package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileFooterMPRCreditCardRepo;

public class MobileFooterMPRCreditCardActions extends MobileFooterMPRCreditCardRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileFooterMPRCreditCardActions.class);

    public MobileFooterMPRCreditCardActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean clickApplyToday() {

        scrollDownToElement(applyButton);
        click(applyButton);
        return waitUntilElementDisplayed(firstName, 30);
    }
    public boolean validateApprovedPage_Guest(){
        waitUntilElementDisplayed(createAccLink,10);

        if (isDisplayed(createAccLink)&&
                isDisplayed(loginLink)&&
                isDisplayed(offerCode)&&
                isDisplayed(copyCliboardLink)&&
                scrollDownToElement(continueShoppingButton)&&
                isDisplayed(continueShoppingButton)&&
                isDisplayed(detailsLink))
            return true;
        else
            return false;
    }

    public boolean validateContactInfoAsGuest(){

        if (isDisplayed(firstName)&&
                isDisplayed(middleName)&&
                isDisplayed(lastName)&&
                isDisplayed(addressLine1)&&
                isDisplayed(addressLine2)&&
                isDisplayed(cityTextBox)&&
                isDisplayed(stateDropDown)&&
                isDisplayed(zipCode)&&
                isDisplayed(mobilePhoneNumber)&&
                isDisplayed(emailAddress)&&
                isDisplayed(alternatePhoneNumber))

            return true;
        else
            return false;
    }
    public boolean validatePersonalInfoAsGuest(){
        scrollDownToElement(birthMonth);
        if (isDisplayed(birthMonth)&&
                isDisplayed(birthDay)&&
                isDisplayed(birthYear))
            return true;
        else
            return false;
    }

    public boolean checkTermsCheckBox(){
        if(!(isSelected(termsCheckBox))){
            click(termsAndCondition);
        }
        return isSelected(termsCheckBox);
    }

    public String getResponseCodeDescription() {
        String response = getValueOfDataElement("response_tcpstore/processWIC");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCodeDescription=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 8).trim();
    }

    public String getRTPSResponseCode() {
        String response = getValueOfDataElement("response_tcpstore/processPreScreenAcceptance");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCode=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 2).trim();
    }
}

