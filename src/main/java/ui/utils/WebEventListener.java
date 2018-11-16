package ui.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import ui.UiBase;
import ui.support.Config;

/**
 * Created by skonda on 6/1/2016.
 */
public class WebEventListener extends AbstractWebDriverEventListener {
    UiBase base = new UiBase();
    public String LinkText = "";
    boolean ignoreError = false;
    Config config = new Config();

    public void beforeNavigateTo(String url, WebDriver driver) {
        System.out.println("Before navigating to: '" + url + "'");
    }

    @Override
    public void beforeFindBy(By by, WebElement closeLinkOnPromo, WebDriver driver) {
        String browserType = config.getBrowserType();
        if(browserType.equalsIgnoreCase("CHROME")){
            try {
                try{
                    WebElement closeLinkOnPerceptionsFrame = driver.findElement(by.xpath("//div[@id='IPEinvL']//area[@alt='no']"));
                    if (base.isDisplayed(closeLinkOnPerceptionsFrame)) {
                        base.click(closeLinkOnPerceptionsFrame);
                    }
                } catch(Exception e){
                }
                WebElement emailSignUpModal = driver.findElement(by.xpath("//*[@class='email-signup-modal-content']/img"));
                closeLinkOnPromo = driver.findElement(by.cssSelector(".email-signup-modal-container .email-signup-modal-header button"));//".email-signup-modal-header button"));

                if (base.isDisplayed(emailSignUpModal)) {
                    base.click(closeLinkOnPromo);
                }

            } catch (Exception e) {
            }}

        else if (browserType.equalsIgnoreCase("IOS") || browserType.equalsIgnoreCase("ANDROID"))
            try {
                WebElement mobileIPerception = driver.findElement(by.id("btnNo"));
                if (base.isDisplayed(mobileIPerception)) {
                    base.click(mobileIPerception);
                    base.staticWait(1000);
                }
            } catch (Exception e) {
            }
    }
}
