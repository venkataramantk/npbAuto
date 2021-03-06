package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.DashboardRepo;
import ui.pages.repo.LoginPageRepo;
import ui.pages.repo.LeftNavRepo;

/**
 * Created by venkat on 11/29/2018.
 */


public class LoginPageActions extends LoginPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(LoginPageActions.class);

    public LoginPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean loginAsVetUser(String emailAddr, String pwd){

        if(isDisplayed(loginBtn)){
            clearAndFillText(emailTxt,emailAddr);
            clearAndFillText(passwordTxt,pwd);
            click(loginBtn);
        }

        try {
            return waitUntilElementDisplayed(dashboardLnk, 5) && waitUntilElementDisplayed(logoAgView, 5);
        } catch (Throwable t) {
            return true;
        }
    }

    public boolean loginAsStdUser(String emailAddr, String pwd, LeftNavRepo leftNavRepo, DashboardRepo dashboardRepo){

        if(isDisplayed(loginBtn)){
            clearAndFillText(emailTxt,emailAddr);
            clearAndFillText(passwordTxt,pwd);
            click(loginBtn);
        }

        try {
            if(waitUntilElementDisplayed(leftNavRepo.dashboardBtn))
            return (!waitUntilElementDisplayed(dashboardRepo.createCVIBtn)) && waitUntilElementDisplayed(logoAgView, 5);

            else return (!waitUntilElementDisplayed(leftNavRepo.dashboardBtn));
        } catch (Throwable t) {
            return true;
        }
    }
}
