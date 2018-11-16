package tests.web.initializer;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
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
 * Created by skonda on 5/18/2016.
 */
@Listeners({ATUReportsListener.class, ConfigurationListener.class,
        MethodListener.class, ui.utils.MethodListener.class})
public class BrowserInitializer {
    public WebDriver driver;
    //public WebDriver mobileDriver;
    UiBase base = new UiBase();
    Logger logger = Logger.getLogger(BrowserInitializer.class);
    List<Thread> threadCollection = new ArrayList<Thread>();
    EventFiringWebDriver firingWebDriver;
    WebEventListener webEventListener;

    {

        String propertiesFile = Config.getReportPropertiesFile();
        System.setProperty("atu.reporter.config", propertiesFile);
    }

    public BrowserInitializer() {

        ATUReports.indexPageDescription = "TCP Automation";

    }

    private void setTimeOuts(WebDriver driver) {
//        driver.manage().timeouts().implicitlyWait(UiBase.getDefaultImplicitTimeout(), TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(UiBase.getMaxPageLoadTimeout(), TimeUnit.SECONDS);
    }

    private void setWindowSize(WebDriver driver) {
        driver.manage().window().maximize();
    }

    private void setChromeCapabilities(DesiredCapabilities capabilities) throws Exception {
        capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
//        File chromedriver = new ChromeDriverDownloader().downloadAndExtract();
        capabilities.setCapability(CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "//bin//chromedriver.exe");
    }

    public void setDesiredCapabilities(DesiredCapabilities capabilities) throws Exception {
        Config config = new Config();
        String browserType = config.getBrowserType();
        if (browserType.equalsIgnoreCase("IE")) {
            capabilities.setBrowserName("internet explorer");
        } else {
            capabilities.setBrowserName(browserType.toLowerCase());
        }
        if (browserType.equalsIgnoreCase("Chrome")) {
            setChromeCapabilities(capabilities);

        }
        if (browserType.equalsIgnoreCase("FireFox")) {

        }

    }


    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void initializeDriver() throws Exception {
        String os = System.getProperty("os.name");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String browserType = Config.getBrowserType();
        if (Config.getExecutionType().equalsIgnoreCase("local")) {
            switch (browserType.toUpperCase()) {
                case "FIREFOX":
                    if (os.contains("Windows"))
                        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/bin/geckodriver.exe");
                    if (os.contains("mac"))
                        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/bin/geckodriver");
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setAcceptUntrustedCertificates(true);
                    profile.setAssumeUntrustedCertificateIssuer(true);
                    profile.setPreference("security.enable_java", true);

                    profile.setPreference("plugin.state.java", 2);
                    driver.manage().window().maximize();
                    break;
                case "CHROME":
                    if (os.contains("Mac"))
                        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver");
                    else if (os.contains("Windows"))
                        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver.exe");
//                    ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File(System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/bin/chromedriver"))).usingAnyFreePort().build();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized");
//                    options.addArguments("--start-fullscreen");
                    options.setCapability(ChromeOptions.CAPABILITY, options);
                    driver = new ChromeDriver(options);
                    break;
                case "INTERNET EXPLORER":

                    setIeCapabilities(capabilities);
                    System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\" + "bin\\IEDriverServer.exe");
                    base.setBrowserType("IE");
                    capabilities.setCapability(
                            InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                            true);
                    driver = new InternetExplorerDriver(capabilities);
                    driver.manage().window().maximize();
                    break;
                case "HEADLESS":
                    if (os.contains("Mac"))
                        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver");
                    else if (os.contains("Windows"))
                        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver.exe");
                    options = new ChromeOptions();
                    options.addArguments("headless");
                    driver = new ChromeDriver(options);
                    driver.manage().window().maximize();
                    break;
                case "ANDROID":
                    ChromeOptions and_Opt = new ChromeOptions();
                    and_Opt.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                    and_Opt.addArguments("--disable-notifications");

                    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
                    capabilities.setCapability("nativeWebScreenshot", true);
                    capabilities.setCapability("newCommandTimeout", 60 * 5);
                    capabilities.setCapability("automationName", "UiAutomator2");
                    //capabilities.setCapability("automationName", "Appium");
                    capabilities.setCapability("disable-popup-blocking", true);

                    capabilities.setCapability(ChromeOptions.CAPABILITY, and_Opt);
                    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                    break;
                case "IOS":
                   /* String url = "https://jagadeeshkotha1:QfpvJ5armd29tmBpYRsr@hub-cloud.browserstack.com/wd/hub";
                    capabilities.setCapability("browserName", "iPhone");
                    capabilities.setCapability("device", "iPhone X");
                    capabilities.setCapability("realMobile", "true");
                    capabilities.setCapability("os_version", "11.0");
                    capabilities.setCapability("browserstack.local", "true");
                    capabilities.setCapability("browserstack.debug", true);
                    driver = new RemoteWebDriver(new URL(url), capabilities);*/
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.3");
                    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
                    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone X");
                    capabilities.setCapability("newCommandTimeout", 60 * 5);
                    capabilities.setCapability("safariInitialUrl", EnvironmentConfig.getApplicationUrl());
                    driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                    break;
                default:
                    break;
            }

        } else if (Config.getExecutionType().equalsIgnoreCase("grid")) {
            logger.info("Initializing driver on Grid machine....");
            if (browserType.equalsIgnoreCase("ANDROID")) {
                String mobHubUrl = Config.getMobHubUrl();
                ChromeOptions and_Opt = new ChromeOptions();
               /* and_Opt.addArguments("--disable-save-password-bubble");
                and_Opt.addArguments("--disable-notifications");*/

              /*  Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                and_Opt.setExperimentalOption("prefs", prefs);*/

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
                capabilities.setBrowserName(browserType);
                driver = new RemoteWebDriver(new URL("http://" + hubUrl + ":4444/wd/hub"), capabilities);
                driver.manage().window().maximize();
            }
        }
        base.setDriver(driver);
        setDriver(driver);
        setUpDriverToEventDriver();
        setTimeOuts(driver);
        shutdownHook(driver);
    }

    public void setUpDriverToEventDriver() {
        firingWebDriver = new EventFiringWebDriver(driver);
        webEventListener = new WebEventListener();
        firingWebDriver.register(webEventListener);
        driver = firingWebDriver;
    }

    private void setIeCapabilities(DesiredCapabilities capabilities) {
//        File internetExplorerExe = new IEDriverDownloader().downloadAndExtract();
        capabilities.setCapability(IE_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "//bin//IEDriverServer.exe");
        capabilities.setJavascriptEnabled(true);

    }

    public void setAuthorInfo(String authorInfo) {
        ATUReports.setAuthorInfo(authorInfo, Utils.getCurrentTime(), "1.0");
    }

    public void setRequirementCoverage(String requirementCoverage) {
        ATUReports.setTestCaseReqCoverage(requirementCoverage);
    }

    public void AddInfoStep(String infoDescription) {
        ATUReports.setWebDriver(driver);
        ATUReports.add(infoDescription, LogAs.INFO, new CaptureScreen(
                CaptureScreen.ScreenshotOf.BROWSER_PAGE));
    }

    public void AssertFailAndContinue(boolean result, String stepDescription) {
        try {
            ATUReports.setWebDriver(driver);
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
            ATUReports.setWebDriver(driver);

            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.FAILED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                takeSnapShot(driver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
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
            ATUReports.setWebDriver(driver);

            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.WARNING, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                takeSnapShot(driver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
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
            ATUReports.setWebDriver(driver);
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
            ATUReports.setWebDriver(driver);
            stepDescription = stepDescription.replaceAll(":", "").replaceAll("\\*", "").replaceAll("\"", "").replaceAll("<", "")
                    .replaceAll(">", "").replaceAll("\\?", "").replaceAll("\\/", "").replaceAll("\\\\", "").replaceAll("\\|", "");
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String methodName = e.getMethodName();
            if (!result) {
                ATUReports.add(stepDescription, LogAs.FAILED, new CaptureScreen(
                        CaptureScreen.ScreenshotOf.BROWSER_PAGE));
                takeSnapShot(driver, System.getProperty("user.dir") + "\\" + "target\\screenshots\\" + methodName + "\\" + stepDescription + ".png");
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

    public void navigateToChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\" + "bin\\chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        base.setDriver(driver);
        setDriver(driver);
//        setUpDriverToEventDriver();
        setTimeOuts(driver);
        shutdownHook(driver);
    }

    public void navigateToChromeDriverWithEventDriverEnabled() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\" + "bin\\chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        base.setDriver(driver);
        setDriver(driver);
        setUpDriverToEventDriver();
        setTimeOuts(driver);
        shutdownHook(driver);
    }

    public void navigateToFFDriverWithEventDriverEnabled() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("security.enable_java", true);

        profile.setPreference("plugin.state.java", 2);
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        driver = new FirefoxDriver(capabilities);
        driver.manage().window().maximize();
        base.setDriver(driver);
        setDriver(driver);
        setUpDriverToEventDriver();
        setTimeOuts(driver);
        shutdownHook(driver);
    }

    public void navigateToPersistentChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\" + "bin\\chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("user-data-dir=C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        capabilities.setCapability("chrome.switches", Arrays.asList("--disable-local-storage"));

        options.addArguments("--disable-extensions");
        capabilities.setCapability("chrome.binary", "C:\\Users\\" + System.getProperty("user.name") + "\\.chromedriver\\chromedriver.exe");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        base.setDriver(driver);
        setDriver(driver);
        setUpDriverToEventDriver();
        setTimeOuts(driver);
        shutdownHook(driver);
    }

    public String getNewCoupon(List<String> couponCodes, String usedCouponCodesFile) {
        String usedCoupons = "";
        try {
            usedCoupons = ui.utils.FileUtils.readFileContent(usedCouponCodesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String newCouponCode = "";
        for (String couponCode : couponCodes) {
            if (!usedCoupons.contains(couponCode.trim())) {
                newCouponCode = couponCode.trim();
                ui.utils.FileUtils.writeDataToFile(usedCouponCodesFile, newCouponCode);
                logger.info("New Coupon used is:" + newCouponCode);
                return newCouponCode.trim();
            }
        }
        if (newCouponCode.isEmpty())
//            logger.info("All the coupon codes used, Please populate with new coupons");
            AddInfoStep("All the coupon codes used, Please populate with new coupons");
        return newCouponCode;
    }


}
