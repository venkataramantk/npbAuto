package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MCreateAccountPageRepo;

/**
 * Created by JKotha on 30/10/2016.
 */
public class MCreateAccountActions extends MCreateAccountPageRepo {
    WebDriver mobileDriver = null;
    Logger logger = Logger.getLogger(MCreateAccountActions.class);

    public MCreateAccountActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    /**
     * Get first Name for create account
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set firstName
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get Last Name for create account
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set lastName
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get Email Address for create account
     *
     * @return lastName
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set EmailAddress
     *
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Get Password for create account
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set EmailAddress
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Enter Details for new Account
     *
     * @param fName   First Name
     * @param lName   Last Name
     * @param email   Email
     * @param pwd     Password
     * @param zipCode ZipCode
     * @param phNum   Phone Number
     */
    public void enterAccountDetails(String fName, String lName, String email, String pwd, String zipCode, String phNum) {

        clearAndFillText(firstNameField, fName);
        clearAndFillText(lastNameField, lName);
        clearAndFillText(emailAddrField, email);
        clearAndFillText(confEmailAddrFld, email);
        clearAndFillText(passwordFld, pwd);
        clearAndFillText(confPasswordFld, pwd);
        clearAndFillText(zipPostalCodeFld, zipCode);
        clearAndFillText(phNumberFld, phNum);
        select(termsCheckBox, termsCheckBoxInput);
    }

    /**
     * Validates all fields in create account page
     *
     * @return True if all the fields are present
     */
    public boolean validateFieldsInPage() {
        boolean areAllFieldsDisplaying = waitUntilElementDisplayed(firstNameField, 2) &&
                waitUntilElementDisplayed(lastNameField, 2) &&
                waitUntilElementDisplayed(emailAddrField, 2) &&
                waitUntilElementDisplayed(confEmailAddrFld, 2) &&
                waitUntilElementDisplayed(passwordFld, 2) &&
                waitUntilElementDisplayed(confPasswordFld, 2) &&
                waitUntilElementDisplayed(zipPostalCodeFld, 2) &&
                waitUntilElementDisplayed(phNumberFld, 2) &&
                waitUntilElementDisplayed(termsText, 2) &&
                waitUntilElementDisplayed(createAccountButton, 2);
        if (areAllFieldsDisplaying)
            return true;
        else
            return false;
    }

    /**
     * Create a new Account for Site
     *
     * @param fName   firstName
     * @param lName   Last name
     * @param pwd     Password
     * @param zipCode Zip Code
     * @param phNum   Phone number
     * @return true if the account created
     */
    public boolean createAnAccount(String fName, String lName, String email, String pwd, String zipCode, String phNum) {
        logger.info("The random email is generated: " + email);
        PanCakePageActions panCakePageActions = new PanCakePageActions(mobileDriver);
        enterAccountDetails(fName, lName, email, pwd, zipCode, phNum);
        click(createAccountButton);
        staticWait(10000);
        logger.info("The account has been created. Email:  "+email+" password: "+pwd);
        return waitUntilElementDisplayed(panCakePageActions.salutationLink, 30);
    }

    /**
     * Verify Remember me Checkbox
     *
     * @param label to check
     * @return true if the required label present
     */
    public boolean verifyRememberMeForNextTimeLabel(String label) {
        for (WebElement rememberMeLabel : rememberMeForNextTimeLabel) {
            if (getText(rememberMeLabel).equalsIgnoreCase(label)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public boolean verifyHelpCenterPage() {
        click(termsAndConditionsLink);
        switchToWindow();
        return waitUntilElementDisplayed(helpCenter);
    }

    public boolean verifyEmailUspage() {
        click(contactUsLink);
        switchToWindow();
        return waitUntilElementDisplayed(emailusPage);
    }

    public void clickCreateAccountBtn() {
        javaScriptClick(mobileDriver, createAccountButton);
    }
    
    /**
     * Created by RichaK
     * This method will click on reset password link present on Create account page.
     * @param actions
     * @return
     */
    public boolean clickResetPasswordLnk(MForgotPasswordPageActions actions)
    {
    	waitUntilElementDisplayed(resetPasswordLnk);
    	click(resetPasswordLnk);
    	return waitUntilElementDisplayed(actions.sectionHeading);
    }

    /**
     * Created by RichaK
     * Verify the error message on create account overlay.
     * @param fName
     * @param lName
     * @param email
     * @param pwd
     * @param zipCode
     * @param phNum
     * @param fieldName
     * @param errMsg
     * @param mcreateAccountActions
     * @return
     */
    public boolean verifycreateAccountErrorMessage(String fName, String lName, String email, String pwd, String zipCode, String phNum, String fieldName, String errMsg, MCreateAccountActions mcreateAccountActions) {

    	enterAccountDetails(fName, lName, email, pwd, zipCode, phNum);
    	staticWait(1000);
    	click(createAccountButton);
    	WebElement errorMsgElem=null;
    	switch(fieldName)
    	{
    		case "EMAIL":
    			errorMsgElem = mcreateAccountActions.inlineErrorMessage("email");
    	}

        waitUntilElementDisplayed(errorMsgElem, 30);
        boolean validateErrMsg = getText(errorMsgElem).contains(errMsg);
        return validateErrMsg;
    }

    /**
     * Create by  Richa Priya
     * @param fName   firstName
     * @param lName   Last name
     * @param pwd     Password
     * @param zipCode Zip Code
     * @param phNum   Phone number
     * @return true if the account created
     */
    public boolean createAnAccountFromSB(String fName, String lName, String email, String pwd, String zipCode, String phNum,MShoppingBagPageActions mShoppingBagPageActions) {
        logger.info("The random email is generated: " + email);
        enterAccountDetails(fName, lName, email, pwd, zipCode, phNum);
        click(createAccountButton);
        staticWait(10000);
        return waitUntilElementDisplayed(mShoppingBagPageActions.checkoutBtn);
    }

}



