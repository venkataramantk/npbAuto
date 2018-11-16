package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

public class MEmailUsPageRepo extends UiBaseMobile {

    @FindBy(name = "firstName")
    public WebElement firstName;

    @FindBy(name = "lastName")
    public WebElement lastName;

    @FindBy(name = "emailAddress")
    public WebElement emailAddress;

    @FindBy(name = "confirmEmailAddress")
    public WebElement confirmEmailAddress;

    @FindBy(name = "phoneNumber")
    public WebElement phoneNumber;

    @FindBy(css = ".custom-select-button.subject-button-closed")
    public WebElement subject;

    @FindBy(css = ".item-list-common.subject-items-list li")
    public List<WebElement> subjects;

    @FindBy(css = ".item-list-common.subject-items-list")
    public WebElement subjectList;

    @FindBy(css = ".custom-select-common.reason-code.reason-code-closed")
    public WebElement reason;

    public WebElement messageSelection(String msg) {
        return getDriver().findElement(By.xpath("//span[text()='" + msg + "']"));
    }

    @FindBy(name = "message")
    public WebElement message;

    @FindBy(css = ".button-submit.button-secondary")
    public WebElement submitBtn;

    @FindBy(css = ".success-text")//".success-box")
    public WebElement successBox;
}
