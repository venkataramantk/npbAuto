package ui.pages.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.RTPSAndWicRepo;

import java.util.ArrayList;
import java.util.List;

public class RTPSAndWicActioins extends RTPSAndWicRepo {
    WebDriver driver;

    public RTPSAndWicActioins(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Verify if the PLCC form for RTPS
     *
     * @return true if PLCC form is displayed
     */
    public Boolean verifyPlCCForm() {
        return waitUntilElementDisplayed(yesiaminterstedBtn);
    }

    public boolean clickYesiamInterested() {
        waitUntilElementDisplayed(yesiaminterstedBtn);
        click(yesiaminterstedBtn);
        return waitUntilElementDisplayed(firstName);
    }

    public boolean validateErrorMeesageFor5cards() {
        if (waitUntilElementDisplayed(errorMessage)) {
            click(errorMessage);
            String error = getText(errorMessage);
            return error.contains("A maximum of five payment methods may be saved to your account");
        }
        return false;
    }

    public boolean clickNoThanks() {
        waitUntilElementDisplayed(noThanksBtn);
        click(noThanksBtn);
        staticWait(10000);
        return !waitUntilElementDisplayed(yesiaminterstedBtn, 10);
    }

    public boolean enterApplicationDetails_Approve(String fname, String lname, String address1, String city, String state, String zipcode, String phone, String email, String altphone, String birthmonth, String birthday, String birthyear, String ssn) {
        waitUntilElementDisplayed(firstName, 30);

        clearAndFillText(firstName, fname);
        clearAndFillText(lastName, lname);
        clearAndFillText(addressLine1, address1);
        clearAndFillText(cityTextBox, city);
        selectDropDownByVisibleText(stateDropDown, state);
        clearAndFillText(zipCode, zipcode);
        clearAndFillText(mobilePhoneNumber, phone);
        clearAndFillText(emailAddress, email);
        staticWait();
        clearAndFillText(emailAddress, email);
        clearAndFillText(alternatePhoneNumber, altphone);
        selectDropDownByVisibleText(birthMonth, birthmonth);
        selectDropDownByVisibleText(birthDay, birthday);
        selectDropDownByVisibleText(birthYear, birthyear);
        clearAndFillText(socialSecurityNumber, ssn);
        scrollToBottom();
        select(termsCheckBox, termsCheckBoxinput);

        click(submitButton);
        if (waitUntilElementDisplayed(addressVerificationContinue, 10))
            click(addressVerificationContinue);
        return waitUntilElementDisplayed(approvedPlccContainer, 30);
    }

    public boolean validatedFieldsInPlccForm() {
        String notDisplayed = "The following fields are not displayed in PLCC form\n";
        boolean com = true;
        if (!waitUntilElementDisplayed(firstName))
            notDisplayed = notDisplayed + "firstName\n";
        com = com && waitUntilElementDisplayed(firstName);
        if (!waitUntilElementDisplayed(lastName))
            notDisplayed = notDisplayed + "LastName\n";
        com = com && waitUntilElementDisplayed(lastName);
        if (!waitUntilElementDisplayed(middleName))
            notDisplayed = notDisplayed + "Middle Name\n";
        com = com && waitUntilElementDisplayed(middleName);
        if (!waitUntilElementDisplayed(addressLine1))
            notDisplayed = notDisplayed + "address line1\n";
        com = com && waitUntilElementDisplayed(addressLine1);
        if (!waitUntilElementDisplayed(addressLine2))
            notDisplayed = notDisplayed + "address line2\n";
        com = com && waitUntilElementDisplayed(addressLine2);
        if (!waitUntilElementDisplayed(cityTextBox))
            notDisplayed = notDisplayed + "city Text\n";
        com = com && waitUntilElementDisplayed(cityTextBox);
        if (!waitUntilElementDisplayed(zipCode))
            notDisplayed = notDisplayed + "zipcode\n";
        com = com && waitUntilElementDisplayed(zipCode);
        if (!waitUntilElementDisplayed(mobilePhoneNumber))
            notDisplayed = notDisplayed + "mobile\n";
        com = com && waitUntilElementDisplayed(mobilePhoneNumber);
        if (!waitUntilElementDisplayed(alternatePhoneNumber))
            notDisplayed = notDisplayed + "alternate mobile\n";
        com = com && waitUntilElementDisplayed(alternatePhoneNumber);
        if (!waitUntilElementDisplayed(emailAddress))
            notDisplayed = notDisplayed + "email address\n";
        com = com && waitUntilElementDisplayed(emailAddress);
        if (!waitUntilElementDisplayed(socialSecurityNumber))
            notDisplayed = notDisplayed + "ssn\n";
        com = com && waitUntilElementDisplayed(socialSecurityNumber);
        if (!waitUntilElementDisplayed(termsCheckBox))
            notDisplayed = notDisplayed + "terms checkbox\n";
        com = com && waitUntilElementDisplayed(termsCheckBox);
        if (!waitUntilElementDisplayed(submitButton))
            notDisplayed = notDisplayed + "submit button\n";
        com = com && waitUntilElementDisplayed(submitButton);
        if (!waitUntilElementDisplayed(plccNothanks))
            notDisplayed = notDisplayed + "No Thanks button\n";
        com = com && waitUntilElementDisplayed(plccNothanks);
        System.out.println(notDisplayed);
        return com;
    }

    public boolean validatevalues(String fname, String lname, String address1, String city, String state, String zipcode, String phone, String email) {
        waitUntilElementDisplayed(firstName);
        return getAttributeValue(firstName, "value").equalsIgnoreCase(fname) &&
                getAttributeValue(lastName, "value").equalsIgnoreCase(lname) &&
                getAttributeValue(addressLine1, "value").equalsIgnoreCase(address1) &&
                getAttributeValue(cityTextBox, "value").equalsIgnoreCase(city) &&
                getAttributeValue(zipCode, "value").equalsIgnoreCase(zipcode) &&
                getAttributeValue(mobilePhoneNumber, "value").equalsIgnoreCase("") &&
                getAttributeValue(emailAddress, "value").equalsIgnoreCase(email) &&
                getAttributeValue(alternatePhoneNumber, "value").equalsIgnoreCase("") &&
                getSelectOption(birthDay).equalsIgnoreCase("dd") &&
                getSelectOption(birthMonth).equalsIgnoreCase("mm") &&
                getSelectOption(birthYear).equalsIgnoreCase("yyyy");
    }

    public boolean validatedCongratsMsg(String fn) {
        String actual = getText(congratsMsg);
        String expected = "Congratulations, " + fn + "!\nYou’re approved for the MY PLACE REWARDS CREDIT CARD.";
        return actual.equalsIgnoreCase(expected);
    }

    public boolean verifyProcessingError() {
        return waitUntilElementDisplayed(processingError);
    }

    public boolean verifyCreditLimit() {
        String actual = getText(cclimtMsg);
        String expected = "YOUR CREDIT LIMIT: $1000";
        return actual.equalsIgnoreCase(expected);
    }

    public boolean verifyMailText() {
        return waitUntilElementDisplayed(mailTimeText);
    }

    public List<String> getElectroinceCommunicationCoordinates() {
        List<String> coordinates = new ArrayList<>();
        waitUntilElementDisplayed(financialTermsContainer);
        String x = financialTermsContainer.getCssValue("width");
        String y = financialTermsContainer.getCssValue("height");
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }
    public boolean compareRTPSText(String text){
        waitUntilElementDisplayed(legalCopyDisplay);
        String copy = getText(legalCopyDisplay);
        if(copy.contains(text)){
            return true;
        }
        else{
            addStepDescription("THe text copy have been changed");
            return false;
        }
    }

    /**
     * @Author: JK
     * Validated if legal copy is displaying in bullet points
     * @return true
     */
    public boolean validateLegalCopy() {
        waitUntilElementDisplayed(legalpoints);
        String type = legalpoints.getCssValue("list-style-type");
        return type.equalsIgnoreCase("disc");
    }
}
