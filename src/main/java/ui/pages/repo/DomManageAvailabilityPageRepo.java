package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

/**
 * Created by skonda on 8/12/2016.
 */
public class DomManageAvailabilityPageRepo extends UiBase {

    @FindBy(xpath="//span[text()='Manage Availability']")
    public WebElement manageAvailabilityFrameText;

    @FindBy(css=".x-tool-img.x-tool-maximize")
    public WebElement maximizeImg;

    @FindBy(xpath="//div[contains(div/div/div/label,'US ECOMMERCE')]/div[4]/div/div/a")
    public WebElement arrowForUSECommerce;

    @FindBy(xpath="//div[contains(div/div/div/label,'CA ECOMMERCE')]/div[4]/div/div/a")
    public WebElement arrowForCAECommerce;

    @FindBy(xpath="//div[contains(div/div/div/label,'US ECOMMERCE')]/div[3]/div/div/label[1]")
    public WebElement activeUSECommerceElement;

    @FindBy(xpath="//div[contains(div/div/div/label,'CA ECOMMERCE')]/div[3]/div/div/label[1]")
    public WebElement activeCAECommerceElement;

    @FindBy(xpath="//span[text()='Test']")
    public WebElement testDropDown;

    @FindBy(xpath="//span[text()='Rebuild']")
    public WebElement reBuildOption;

    @FindBy(xpath="//label[text()='Publish availability']")
    public WebElement publishAvailabilityCheckBox;

//    @FindBy(css="#button-1317-btnIconEl")
//    public WebElement yesButtonOnPubAvail;

    @FindBy(xpath="//span[contains(span[text()='Yes'],'Yes')]/span[2]")
    public WebElement yesButtonOnPubAvail;
}
