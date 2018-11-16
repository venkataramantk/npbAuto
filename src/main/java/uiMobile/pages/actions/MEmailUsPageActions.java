package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MEmailUsPageRepo;

public class MEmailUsPageActions extends MEmailUsPageRepo {

    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MFooterActions.class);

    public MEmailUsPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Contact the cc with emails
     *
     * @param fn       first name
     * @param ln       last anem
     * @param email    email
     * @param phNo     ph no
     * @param subjectM Subject
     * @param reasonM  subject reason
     * @param msg      additional msg
     * @return
     */
    public boolean contactWithEmail(String fn, String ln, String email, String phNo, String subjectM, String reasonM, String msg) {
        waitUntilElementDisplayed(firstName, 20);
        clearAndFillText(firstName, fn);
        clearAndFillText(lastName, ln);
        clearAndFillText(emailAddress, email);
        clearAndFillText(confirmEmailAddress, email);
        clearAndFillText(phoneNumber, phNo);
        click(subject);
        javaScriptClick(mobileDriver, messageSelection(subjectM));
        click(reason);
        javaScriptClick(mobileDriver, messageSelection(reasonM));
        clearAndFillText(message, msg);
        javaScriptClick(mobileDriver, submitBtn);
        return waitUntilElementDisplayed(successBox, 30);
    }

    public boolean validateCASubject() {
        click(subject);
        return subjects.size() == 10 && getText(subjectList).contains("Airmiles");
    }

    public boolean validateUSSubject() {
        javaScriptClick(mobileDriver, subject);
        return subjects.size() == 10 && getText(subjectList).contains("MyPLACE Rewards Credit Card");
    }
}
