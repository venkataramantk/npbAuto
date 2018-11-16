package ui.utils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.openqa.selenium.WebDriver;
import org.testng.IConfigurationListener2;
import org.testng.ITestResult;
import ui.UiBase;
import ui.pages.actions.HeaderMenuActions;

/**
 * Created by skonda on 10/26/2017.
 */
public class MyConfigListener implements IConfigurationListener2 {
    @Override
    public void beforeConfiguration(ITestResult tr) {

    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {

    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {

//         ATUReports.add("Failed", LogAs.FAILED, new CaptureScreen(
//                CaptureScreen.ScreenshotOf.BROWSER_PAGE));
        System.out.println("Some configMethod exception in  " +itr.getTestClass().getTestName()+" The exception is "+ itr.getThrowable().getLocalizedMessage());

    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
//        ATUReports.add("Browser should navigate to as mentioned.", "Browser is navigated.", LogAs.FAILED, new CaptureScreen(
//                CaptureScreen.ScreenshotOf.BROWSER_PAGE));
//        System.out.println(">>>> " + itr.getThrowable().getLocalizedMessage());
    }
}
