package tests.web.initializer;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.*;
import io.appium.java_client.ios.*;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ui.UiBase;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.WebEventListener;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;
import static org.openqa.selenium.ie.InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY;

/**
 * Created by venkat on 12/13/2018.
 */
@Listeners({ATUReportsListener.class, ConfigurationListener.class,
        MethodListener.class, ui.utils.MethodListener.class})
public class mobileInitializer extends BrowserInitializer{

    public AppiumDriver mobileDriver;

    @SuppressWarnings("rawtypes")
    protected AndroidDriver androidDriver;
    protected IOSDriver iosDriver;
    protected WebDriverWait wait;

    UiBase base = new UiBase();
    Logger logger = Logger.getLogger(mobileInitializer.class);
    List<Thread> threadCollection = new ArrayList<Thread>();
    EventFiringWebDriver firingWebDriver;
    WebEventListener webEventListener;

    {

        String propertiesFile = Config.getReportPropertiesFile();
        System.setProperty("atu.reporter.config", propertiesFile);
    }

    public mobileInitializer() {

        ATUReports.indexPageDescription = "NPB Automation";

    }

    private void setTimeOuts(WebDriver driver) {
//        driver.manage().timeouts().implicitlyWait(UiBase.getDefaultImplicitTimeout(), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(UiBase.getMaxPageLoadTimeout(), TimeUnit.SECONDS);
    }

    private void setWindowSize(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public void setDriver(AppiumDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
    }

    public WebDriver getDriver() {
        return this.mobileDriver;
    }

    public void initializeMobileDriver() throws Exception {
        String os = System.getProperty("os.name");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String deviceType = Config.getBrowserType();
        /*if (Config.getExecutionType().equalsIgnoreCase("local")) {*/
            switch (deviceType.toUpperCase()) {

                case "ANDROID":
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"GalaxyS7");
                    capabilities.setCapability(MobileCapabilityType.VERSION, "6.0.1");
                    capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10000);
                    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Android");
                    capabilities.setCapability(MobileCapabilityType.APP,"/Users/venkataramant/Documents/NPB/agconnect-1.0.17-(10017).apk");
                    capabilities.setCapability("appPackage","com.Ag.FieldDataEnt");
                    capabilities.setCapability(MobileCapabilityType.UDID,"ce07160724e0a30c03");//"4d00be724c41510b"//320822d94b546153
                    capabilities.setCapability("automationName", "uiautomator2");
                    androidDriver = new AndroidDriver(new URL("http://127.0.0.1:42021/wd/hub"), capabilities);
                    break;

                case "IOS":
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"iPad Pro");
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10");
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone X");
                    capabilities.setCapability("newCommandTimeout", 60 * 5);
                    capabilities.setCapability("safariInitialUrl", EnvironmentConfig.getApplicationUrl());
                    iosDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                    break;

                default:
                    break;
            }

       /* } else if (Config.getExecutionType().equalsIgnoreCase("grid")) {
            logger.info("Initializing driver on Grid machine....");
            if (deviceType.equalsIgnoreCase("ANDROID")) {
                String mobHubUrl = Config.getMobHubUrl();
                ChromeOptions and_Opt = new ChromeOptions();
               *//* and_Opt.addArguments("--disable-save-password-bubble");
                and_Opt.addArguments("--disable-notifications");*//*

              *//*  Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                and_Opt.setExperimentalOption("prefs", prefs);*//*

                and_Opt.addArguments("--disable-extensions", "test-type",
                        "no-default-browser-check", "ignore-certificate-errors",
                        "--disable-notifications",
                        "--disable-offer-store-unmasked-wallet-cards",
                        "--disable-autofill-keyboard-accessory-view");
                and_Opt.addArguments("--disable-translate");

                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
                capabilities.setCapability("nativeWebScreenshot", true);
                capabilities.setCapability(ChromeOptions.CAPABILITY, and_Opt);


                driver = new AndroidDriver(new URL("http://" + mobHubUrl + ":4444/wd/hub"), capabilities);

            } else {
                String hubUrl = Config.getHubUrl();
                capabilities.setBrowserName(deviceType);
                driver = new RemoteWebDriver(new URL("http://" + hubUrl + ":4444/wd/hub"), capabilities);
                driver.manage().window().maximize();
            }
        }*/
        base.setDriver(mobileDriver);
        setDriver(mobileDriver);
//        setUpDriverToEventDriver();
//        setTimeOuts(mobileDriver);
//        shutdownHook(mobileDriver);
    }

//    public void setUpDriverToEventDriver() {
//        firingWebDriver = new EventFiringWebDriver(mobileDriver);
//        webEventListener = new WebEventListener();
//        firingWebDriver.register(webEventListener);
//        driver = firingWebDriver;
//    }

    public void setAuthorInfo(String authorInfo) {
        ATUReports.setAuthorInfo(authorInfo, Utils.getCurrentTime(), "1.0");
    }

    public void setRequirementCoverage(String requirementCoverage) {
        ATUReports.setTestCaseReqCoverage(requirementCoverage);
    }

    public void AddInfoStep(String infoDescription) {
        ATUReports.setWebDriver(mobileDriver);
        ATUReports.add(infoDescription, LogAs.INFO, new CaptureScreen(
                CaptureScreen.ScreenshotOf.BROWSER_PAGE));
    }

    public void AssertFailAndContinue(boolean result, String stepDescription) {
        try {
            ATUReports.setWebDriver(mobileDriver);
            stepDescription = stepDescription.replaceAll(":", "").replaceAll("\\*", "").replaceAll("\"", "").replaceAll("<", "")
                    .replaceAll(">", "").replaceAll("\\?", "").replaceAll("\\/", "").replaceAll("\\\\", "").replaceAll("\\|", "");
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.FAILED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
//                String takeScreenshotLocation = System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\";
//                createFolder(takeScreenshotLocation);
//
//                takeEntirePageScreenShot(driver, takeScreenshotLocation + stepDescription + ".png");
            } else {
               /* ATUReports.add(stepDescription, LogAs.PASSED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));*/
                ATUReports.add(stepDescription, LogAs.PASSED, null);
                //takeSnapShot(driver,System.getProperty("user.dir")+"\\"+"target\\screenshots\\"+methodName+"\\"+stepDescription+".png");
            }
        } catch (Exception e) {
            ATUReports.add(stepDescription, LogAs.FAILED, new CaptureScreen(
                    CaptureScreen.ScreenshotOf.BROWSER_PAGE));
        }
    }

    public void AssertFailAndContinueWithTextFont(boolean result, String stepDescription) {
        try {
            ATUReports.setWebDriver(mobileDriver);

            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.FAILED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                takeSnapShot(mobileDriver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
            } else {
                ATUReports.add(stepDescription, LogAs.PASSED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                //takeSnapShot(driver,System.getProperty("user.dir")+"\\"+"target\\screenshots\\"+methodName+"\\"+stepDescription+".png");
            }
        } catch (Exception e) {

        }
    }

    public void AssertWarnAndContinueWithTextFont(boolean result, String stepDescription) {
        try {
            ATUReports.setWebDriver(mobileDriver);

            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.WARNING, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                takeSnapShot(mobileDriver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
            } else {
                ATUReports.add(stepDescription, LogAs.PASSED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                //takeSnapShot(driver,System.getProperty("user.dir")+"\\"+"target\\screenshots\\"+methodName+"\\"+stepDescription+".png");
            }
        } catch (Exception e) {

        }
    }

    public void AssertWarnAndContinue(boolean result, String stepDescription) {
        try {
            ATUReports.setWebDriver(mobileDriver);
            stepDescription = stepDescription.replaceAll(":", "").replaceAll("\\*", "").replaceAll("\"", "").replaceAll("<", "")
                    .replaceAll(">", "").replaceAll("\\?", "").replaceAll("\\/", "").replaceAll("\\\\", "").replaceAll("\\|", "");
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.WARNING, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
//                takeSnapShot(driver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
            } else {
                ATUReports.add(stepDescription, LogAs.PASSED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                //takeSnapShot(driver,System.getProperty("user.dir")+"\\"+"target\\screenshots\\"+methodName+"\\"+stepDescription+".png");
            }
        } catch (Exception e) {

        }
    }

    public void AssertFail(boolean result, String stepDescription) {
        try {
            ATUReports.setWebDriver(mobileDriver);
            stepDescription = stepDescription.replaceAll(":", "").replaceAll("\\*", "").replaceAll("\"", "").replaceAll("<", "")
                    .replaceAll(">", "").replaceAll("\\?", "").replaceAll("\\/", "").replaceAll("\\\\", "").replaceAll("\\|", "");
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.FAILED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                takeSnapShot(mobileDriver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
                Assert.fail(stepDescription);
            } else {
                ATUReports.add(stepDescription, LogAs.PASSED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                //takeSnapShot(driver,System.getProperty("user.dir")+"\\"+"target\\screenshots\\"+methodName+"\\"+stepDescription+".png");
            }
        } catch (Exception e) {
            Assert.fail(stepDescription);
        }
    }

    public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

        //Call getScreenshotAs method to create image file

        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        String screenShotFilePath = fileWithPath;

        //  screenShotFilePath.replaceAll(":","").replaceAll("")

        File DestFile = new File(fileWithPath);

        //Copy file at destination

        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            e.getMessage();
        }


    }

    //Create Folder Method
    public void createFolder(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdirs()) {
                System.out.println("Directory: " + path + " is created!");
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }

    public void takeEntirePageScreenShot(WebDriver webdriver, String fileWithPath) throws IOException {
        // Here 100 is a scrollTimeOut in milliseconds, For every 100 ms the
        // screen will be scrolled and captured
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(webdriver);
//        String testScreenShotDirectory = (fileWithPath);

        // To save the screenshot in desired location
        ImageIO.write(screenshot.getImage(), "PNG",
                new File(fileWithPath));

    }

    private void shutdownHook(WebDriver driver) {
        final WebDriver finalDriver = driver;


        final Thread thread = new Thread() {
            public void run() {
                try {
                    int mb = 1024 * 1024;
                    Runtime runtime = Runtime.getRuntime();

                    final Thread currentThread = Thread.currentThread();
                    if (Config.getExecutionType().equalsIgnoreCase("grid")) {
                        finalDriver.quit();
                    } else {
                        finalDriver.quit();
                    }
                    System.out.println("All Tests Completed");
                } catch (Exception e) {
                    final Thread currentThread = Thread.currentThread();
                    logger.info("Thread Name is" + currentThread.getName() + "and " + currentThread.isAlive());
                    try {
                        if (Config.getExecutionType().equalsIgnoreCase("grid")) {
                            finalDriver.quit();
                        } else {
                            finalDriver.quit();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        finalDriver.quit();
                    }
                }
            }
        };
        threadCollection.add(thread);
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
