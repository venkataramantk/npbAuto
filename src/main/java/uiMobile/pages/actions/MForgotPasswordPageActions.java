package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MForgotPasswordPageActionsRepo;

/**
 * Created by JKotha on 20/10/2017.
 */
public class MForgotPasswordPageActions extends MForgotPasswordPageActionsRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MForgotPasswordPageActions.class);
    MCreateAccountActions createAccountActions;

    public MForgotPasswordPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Clicks on Back to login link
     * in forgot password page
     *
     * @return true if Login Page is displayed
     */
    public boolean validateAllFields() {
        return waitUntilElementDisplayed(forgotpwdTitle) &&
                waitUntilElementDisplayed(backToLoginLink) &&
                waitUntilElementDisplayed(sectionHeading) &&
                waitUntilElementDisplayed(emailInstructionstxt) &&
                waitUntilElementDisplayed(emailAddrField) &&
                waitUntilElementDisplayed(resetPwdButton) &&
                waitUntilElementDisplayed(createAccountTxt) &&
                waitUntilElementDisplayed(createAccountBtn);
    }

    public boolean validateSectionHeadingText() {
        String heading = getText(sectionHeading);
        return heading.contains("Forgot your password?") &&
                heading.contains("No worries!");
    }

    public boolean validateSectionSubHeading() {
        String heading = getText(emailInstructionstxt);
        return heading.contains("Enter your email address, and we’ll send you instructions to reset your password.");
    }

    public boolean validateCreateAccountTxt() {
        String ca = getText(createAccountTxt);
        return ca.contains("Don't have an account? Create one now");
    }

    /**
     * Reset password for the email
     *
     * @param emailAddr of the user to reset password
     * @return true reset password successful
     */
    public boolean enterEmailAddrAndSubmit(String emailAddr) {
        clearAndFillText(emailAddrField, emailAddr);
        staticWait(3000);
        click(resetPwdButton);
        return true;
    }

    public boolean validateCustmorCareLink() {
        waitUntilElementDisplayed(custmorCareLink);
        click(custmorCareLink);
        return waitUntilElementDisplayed(emailusTitle);
    }

    /**
     * Click back to login
     *
     * @return
     */
    public boolean validateBackToLogin() {
        MLoginPageActions actions = new MLoginPageActions(mobileDriver);
        waitUntilElementDisplayed(backToLoginLink);
        staticWait();
        click(backToLoginLink);
        return waitUntilElementDisplayed(actions.emailAddrField);
    }

    public boolean validateErrormEssageForEmptyEmail() {
        click(emailAddrField);
        click(sectionHeading);
        return waitUntilElementDisplayed(inlineerrorMsg);
    }

    public boolean validateCreateAccontBtn() {
        MCreateAccountActions action = new MCreateAccountActions(mobileDriver);
        click(createAccountBtn);
        return waitUntilElementDisplayed(action.emailAddrField);
    }

    public WebElement inlineErrorMessage(String field) {
        WebElement inlineError;
        switch (field) {
            case "email":
                inlineError = getDriver().findElement(By.xpath("//div[text()='Email Address']/../following-sibling::div[1]/div"));
                break;
            default:
                inlineError = getDriver().findElement(By.cssSelector("div.inline-error-message"));
        }
        return inlineError;
    }

    public boolean clickBackToLogin(MLoginPageActions mloginPageActions) {
        if (waitUntilElementDisplayed(backToLoginLink, 10)) {
            click(backToLoginLink);
            return
                    waitUntilElementDisplayed(mloginPageActions.emailAddrField);
        } else {
            addStepDescription("Back to login link is not displayed in Fotgot Password page");
            return false;
        }
    }

}
