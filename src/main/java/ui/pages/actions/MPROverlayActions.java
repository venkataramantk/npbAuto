package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.MPROverlayRepo;

/**
 * Created by AbdulazeezM on 8/11/2017.
 */
public class MPROverlayActions extends MPROverlayRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(MPROverlayActions.class);

    public MPROverlayActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }
    public boolean clickApplyToday() {

        scrollDownToElement(applyButton);
        click(applyButton);
        return waitUntilElementDisplayed(firstName, 30);
    }

    public boolean enterApplicationDetails_Reg(String fname, String lname, String address1, String address2, String city, String state, String zipcode, String phone, String email, String altphone, String birthmonth, String birthday, String birthyear, String ssn) {
        waitUntilElementDisplayed(firstName, 30);

        clearAndFillText(firstName, fname);
        clearAndFillText(lastName, lname);
        clearAndFillText(addressLine1, address1);
        clearAndFillText(addressLine2, address2);
        clearAndFillText(cityTextBox, city);
        selectDropDownByVisibleText(stateDropDown, state);
        clearAndFillText(zipCode, zipcode);
        clearAndFillText(mobilePhoneNumber, phone);
        clearAndFillText(emailAddress, email);
        clearAndFillText(alternatePhoneNumber, altphone);

        selectDropDownByVisibleText(birthMonth, birthmonth);
        selectDropDownByVisibleText(birthDay, birthday);
        selectDropDownByVisibleText(birthYear, birthyear);
        clearAndFillText(socialSecurityNumber, ssn);
        scrollDownUntilElementDisplayed(termsAndCondition);
        checkTermsCheckBox();
        click(submitButton);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        staticWait(5000);

        return waitUntilElementDisplayed(approvedMessage,10) && waitUntilElementDisplayed(continueShoppingButton,10);
    }

    public boolean checkTermsCheckBox(){
        if(!(isSelected(termsCheckBox))){
            click(termsAndCondition);
        }
        return isSelected(termsCheckBox);
    }
    public boolean closeMpr_Overlay(ShoppingBagPageActions shoppingBagPageActions){
        waitUntilElementsAreDisplayed(closeMprOverlay,20);
        click(getLastElementFromList(closeMprOverlay));
        return waitUntilElementDisplayed(shoppingBagPageActions.emptyBagMessage,10);
    }
}
