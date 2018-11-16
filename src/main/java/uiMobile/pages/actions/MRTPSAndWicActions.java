package uiMobile.pages.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MRTPSAndWicRepo;

import java.util.ArrayList;
import java.util.List;

public class MRTPSAndWicActions extends MRTPSAndWicRepo {

    WebDriver mobileDriver;
    MobileHeaderMenuActions headerMenuActions;

    public MRTPSAndWicActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        headerMenuActions = new MobileHeaderMenuActions(mobileDriver);
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Verify if the PLCC form for RTPS
     *
     * @return true if PLCC form is displayed
     */
    public Boolean verifyPlCCForm() {
        return waitUntilElementDisplayed(noThanksBtn, 20);
    }

    public boolean validatemprTextAndEspot() {
        return waitUntilElementDisplayed(mprEspot);
    }

    public boolean verifyCongratsMessge() {
        if (waitUntilElementDisplayed(congrats))
            return getText(congrats).contains("Congrats");
        return false;
    }

    public boolean verifyDiscountImage() {
        return waitUntilElementDisplayed(img_discount);
    }

    public boolean verifyPromotBelowDiscountImg() {
        return waitUntilElementDisplayed(discountPromo);
    }

    public boolean clickYesiamInterested() {
        waitUntilElementDisplayed(yesiaminterstedBtn);
        click(yesiaminterstedBtn);
        return waitUntilElementDisplayed(firstName);
    }

    public boolean verifyButtons() {
        return waitUntilElementDisplayed(noThanksBtn) && waitUntilElementDisplayed(yesiaminterstedBtn);
    }

    public boolean validateFactaText() {
        if (waitUntilElementDisplayed(factatext))
            return getText(factatext).contains("1-888-567-8688") &&
                    getText(factatext).contains("PRESCREEN & OPT-OUT NOTICE");

        return false;
    }

    public boolean validateTermsText() {
        if (waitUntilElementDisplayed(termsText))
            return getText(termsText).contains("Subject to final credit approval.");

        return false;
    }

    public boolean noThanksBtn() {
        MBillingPageActions actions = new MBillingPageActions(mobileDriver);
        waitUntilElementDisplayed(noThanksBtn);
        click(noThanksBtn);
        return waitUntilElementDisplayed(actions.nextReviewButton);
    }

    public boolean enterApplicationDetails_Approve(String fname, String lname, String address1, String city, String state, String zipcode, String phone, String email, String altphone, String birthmonth, String birthday, String birthyear, String ssn) {
        waitUntilElementDisplayed(firstName, 30);

        clearAndFillText(firstName, fname);
        clearAndFillText(lastName, lname);
        clearAndFillText(emailAddress, email);
        clearAndFillText(addressLine1, address1);
        clearAndFillText(cityTextBox, city);
        selectDropDownByVisibleText(stateDropDown, state);
        clearAndFillText(zipCode, zipcode);
        clearAndFillText(mobilePhoneNumber, phone);
        clearAndFillText(alternatePhoneNumber, altphone);
        selectDropDownByVisibleText(birthMonth, birthmonth);
        selectDropDownByVisibleText(birthDay, birthday);
        selectDropDownByVisibleText(birthYear, birthyear);
        clearAndFillText(socialSecurityNumber, ssn);
        scrollToBottom();
        select(termsCheckBox, termsCheckBoxinput);

        click(submitButton);
        boolean addressVerify = waitUntilElementDisplayed(addressVerificationContinue, 10);
        if (addressVerify == true)
            click(addressVerificationContinue);
        return waitUntilElementDisplayed(approvedPlccContainer);
    }

    public boolean loginFromForm(String pwd) {
        waitUntilElementDisplayed(loginLink);
        click(loginLink);
        waitUntilElementDisplayed(password);
        clearAndFillText(password, pwd);
        click(loginBtn);
        return true;
    }

    public boolean clickLoginLink(MLoginPageActions mLoginPageActions) {
        if (waitUntilElementDisplayed(loginBtn, 10)) {
            click(loginBtn);
            return waitUntilElementDisplayed(mLoginPageActions.emailAddrField, 10);
        } else {
            addStepDescription("Login link is not displayed in WIC page");
            return false;
        }
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

    public boolean clickApplyOrAcceptOffer() {
        waitUntilElementDisplayed(applyOrAcceptOffer);
        click(applyOrAcceptOffer);
        return waitUntilElementDisplayed(firstName);
    }

    /**
     * Verify Existing popup for PLCC/WIC data
     *
     * @return
     */
    public boolean verifyExistingAccountPopup() {
        return waitUntilElementDisplayed(accountExists);
    }

    public String getCooponCode() {
        waitUntilElementDisplayed(couponCode);
        return getText(couponCode);
    }

    /**
     * Verify Click here link is not displayed
     *
     * @return
     */
    public boolean verifyClickHereLink() {
        return waitUntilElementDisplayed(clickhere);
    }
}
