package uiMobile;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import io.appium.java_client.MobileDriver;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.support.EnvironmentConfig;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

//import java.util.function.Supplier;

/**
 * Created by skonda on 2/6/2017.
 */

public class UiBaseMobile {
    private static final String SELENIUM_MAXTIMEOUT = "selenium.element.maxtimeout";
    private static final String SELENIUM_MINTIMEOUT = "selenium.element.mintimeout";
    private static final String SELENIUM_IMPLICITWAIT = "selenium.element.implicitwait";
    private static final String PAGELOAD_MAXTIMEOUT = "selenium.pageload.timeout";
    private static final String CONFIG_FILE = "Config.properties";
    private static String browserType;
    public static String parentWindow;
    public static int surveryModalCount = 0;
    public static int safariNewsLetterModal = 0;
    Logger logger = Logger.getLogger(UiBaseMobile.class);
    private WebDriver mobDriver;

    public WebDriver getDriver() {
        return mobDriver;
    }

    public void setDriver(WebDriver mobDriver) {
        this.mobDriver = mobDriver;
    }

    /*public void swipeBig() throws MalformedURLException {
        if(mobDriver !=null) {
            String context = mobDriver.getContext();
            mobDriver.context("NATIVE_APP");
            Dimension size = mobDriver.manage().window().getSize();
            int startx = (int) (size.width * 0.20);
            int endx = (int) (size.width * 0.20);
            int starty = (int) (size.height * 0.6);
            int endy = (int) (size.height * 0.1);
            Tou
            mobDriver.swipe(startx, starty, endx, endy, 1000);

            mobDriver.context(context);
        }
    }

    public void swipe() {
        if(mobDriver !=null) {
            String context = mobDriver.getContext();
            mobDriver.context("NATIVE_APP");
            Dimension size = mobDriver.manage().window().getSize();
            int startx = (int) (size.width * 0.20);
            int endx = (int) (size.width * 0.20);
            int starty = size.height / 2;
            int endy = size.height / 4;
            mobDriver.swipe(startx, starty, endx, endy, 1000);
            mobDriver.context(context);
        }

    }

    public void swipeUp() {
        if(mobDriver !=null) {
            String context = mobDriver.getContext();
            mobDriver.context("NATIVE_APP");
            Dimension size = mobDriver.manage().window().getSize();
            int startx = (int) (size.width * 0.20);
            int endx = (int) (size.width * 0.20);
            int starty = size.height / 2;
            int endy = size.height / 4;
            mobDriver.swipe(startx, starty, startx, endy, 1000);
            mobDriver.context(context);
        }

    }

    public void swipeBigUp() {
        if(mobDriver !=null) {
            String context = mobDriver.getContext();
            mobDriver.context("NATIVE_APP");
            Dimension size = mobDriver.manage().window().getSize();
            int startx = (int) (size.width * 0.20);
            int endx = (int) (size.width * 0.20);
            int starty = (int) (size.height * 0.1);
            int endy = (int) (size.height * 0.9);
            mobDriver.swipe(startx, starty, endx, endy, 1000);
            mobDriver.context(context);
        }
    }*/


    public static void highLightElement(MobileDriver mobDriver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) mobDriver;

        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            System.out.println(e.getMessage());
        }

        js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

    }

    public void tabFromField(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    /**
     * Executes keyborad commands
     */
    public void keyBoard(WebElement element, String key) {
        switch (key) {
            case "ENTER":
                element.sendKeys(Keys.ENTER);
                break;
        }
    }

    /**
     * Type the text in side a webelement
     *
     * @param element to found
     * @param text    to be type
     */
    public void fillText(WebElement element, String text) {
        element.sendKeys(text);
        try {
            //getDriver().hideKeyboard();
        } catch (Exception e) {

        }
    }

    public String isKeyboardVisible() {
        String keyboardStatus = "true";
        String cmd = "adb shell dumpsys input_method | grep mInputShown";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s[] = reader.readLine().split("=");
            keyboardStatus = s[4].trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyboardStatus;
    }

    /**
     * Get the innerText of the element, including sub-elements,
     * without any leading or trailing whitespace
     *
     * @param element to getText
     * @return Inner text of the Element
     */
    public String getText(WebElement element) {
        waitUntilElementDisplayed(element, 3);
//        scrollDownToElement(element);
        if (element.getTagName().equalsIgnoreCase("label") ||
                element.getTagName().equalsIgnoreCase("div") ||
                element.getTagName().equalsIgnoreCase("p") ||
                element.getTagName().equalsIgnoreCase("label") ||
                element.getTagName().equalsIgnoreCase("h1")) {
            //TO DO: Fina a solution to scroll to element
        }
        return element.getText();
    }

    public int getRandomNumber(int count) {
        return Integer.parseInt(RandomStringUtils.randomNumeric(count));
    }

    /**
     * Command to click on this element.
     *
     * @param element to be clicked
     */
    public void click(WebElement element) {
        waitUntilElementDisplayed(element, 3);
//        //scrollDownToElement(element);
//        element.click();
        javaScriptClick(mobDriver,element);
    }

    public void click_Selenium(WebElement element) {
        element.click();
    }

    /**
     * Select an element if it is not.
     *
     * @param element to be selected
     */
    public void select(WebElement element) {
        waitUntilElementDisplayed(element, 3);
        //  scrollDownToElement(element);

        if (!(element.isSelected())) {
            logger.info("Element is not selected, selecting now");
            javaScriptClick(mobDriver, element);
        } else {
            logger.info("Element is already selected");
        }
    }

    /**
     * un-select an element if it selected.
     *
     * @param element to be un-selected
     */
    public void deselect(WebElement element) {
        waitUntilElementDisplayed(element, 3);
        scrollDownToElement(element);
        if (element.isSelected()) {
            logger.info("Element is selected, un-selecting now");
            element.click();
        } else {
            logger.info("Element is already un - selected");
        }
    }

    /**
     * Command to click on this element.
     *
     * @param element to be clicked
     * @return true if element is selected
     */
    public boolean isSelected(WebElement element) {
        waitUntilElementDisplayed(element, 3);
        scrollDownToElement(element);
        return element.isSelected();
    }

  /*  public void swipeToElement(WebElement element) {
        String context = mobDriver.getContext();
        mobDriver.context("NATIVE_APP");
        TouchAction t = new TouchAction(mobDriver);
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        t.moveTo(eleWidth, eleHeight);
        mobDriver.context(context);
    }*/

    /**
     * Navigate to a webPage
     *
     * @param link to navigate
     */
    public void navigateTo(String link) {
        parentWindow = mobDriver.getWindowHandle();
        mobDriver.get(link);
    }

    public void clear(WebElement element) {
        waitUntilElementDisplayed(element, 3);
        element.clear();
    }

    public boolean isEnabled(WebElement element) {
        try {
            waitUntilElementDisplayed(element, 3);
            scrollDownToElement(element);
            return element.isEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * Author: JK
     * This method will delete all cookies from the site and disable
     * privacy policy popup
     */
    public void deleteAllCookies() {
        String privacyCookie = "tcpPrivacyPolicyDisplayed";
//        Cookie cookie = mobDriver.manage().getCookieNamed(privacyCookie);
        mobDriver.manage().deleteAllCookies();
//        if (cookie != null) {
//            mobDriver.manage().deleteCookieNamed(privacyCookie);
//            refreshPage();
//
//            Cookie privacy = new Cookie(privacyCookie, "TRUE");
//            mobDriver.manage().addCookie(privacy);
//            logger.info("Edited Privacy cookie and Deleted all Cookies" + privacy);
//        }
        refreshPage();
    }

    public void staticWait() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void staticWait(int timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void navigateBack() {
        getDriver().navigate().back();
    }

    public Boolean waitUntilElementClickable(WebElement element, int timeInSeconds) {
        try {

            (new WebDriverWait(getDriver(), timeInSeconds)).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean waitUntilElementDisplayed(WebElement element, int timeInSeconds) {
        try {
            (new WebDriverWait(getDriver(), timeInSeconds)).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean waitUntilElementsAreDisplayed(List<WebElement> elements, int timeInSeconds) {
        try {

            (new WebDriverWait(getDriver(), timeInSeconds))

                    .until(ExpectedConditions.visibilityOfAllElements(elements));
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public static int getMinTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();
        return getTimeOutForProperty(config, SELENIUM_MINTIMEOUT);
    }


    public static int getMaxTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();

        return getTimeOutForProperty(config, SELENIUM_MAXTIMEOUT);
    }


    public Boolean waitUntilElementDisplayed(WebElement element) {
        try {
            (new WebDriverWait(getDriver(), getMaxTimeout()))
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean waitUntilElementDisappear(By element) {
        try {
            (new WebDriverWait(getDriver(), getMaxTimeout()))
                    .until(ExpectedConditions.invisibilityOfElementLocated(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean waitUntilElementsDisplayed(List<WebElement> element) {
        try {
            (new WebDriverWait(getDriver(), getMaxTimeout()))
                    .until(ExpectedConditions.visibilityOfAllElements(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void jqueryClick(String cssSelector) {
        ((JavascriptExecutor) getDriver()).executeScript("window.jQuery(\"" + cssSelector + "\").click()");
    }


    public Boolean verifyElementNotDisplayed(WebElement element, int timeInSeconds) {
        try {
            try {
                (new WebDriverWait(getDriver(), timeInSeconds))

                        .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(getCssLocator(element))));
                return true;
            } catch (Throwable in) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean verifyElementNotDisplayedWithText(WebElement element, String text, int timeInSeconds) {
        try {
            try {
                (new WebDriverWait(getDriver(), timeInSeconds))

                        .until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector(getCssLocator(element)), text));
                return true;
            } catch (Throwable in) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean verifyElementNotDisplayed(WebElement element) {
        try {
            Boolean invisible = (new WebDriverWait(getDriver(), getMinTimeout()))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(getCssLocator(element))));
            return invisible;
        } catch (Exception e) {
            return false;
        }
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public static String getCssLocator(WebElement element) {
        String val = element.toString();
        return val.substring(val.lastIndexOf("selector:") + 10, val.length() - 1).trim();
    }

    public static String randomEmail() {
        String n = UUID.randomUUID().toString().substring(30);
        String s = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()).toString().replaceAll(":", "").
                replaceAll(" ", "").replaceAll("-", "").replace(".", "").substring(2);
        String email = "test" + n + s + "@yopmail.com";
        return email;
    }

    public static String randomPassword() {
        String password = "P@sswOrd" + GetTime();
        return password;
    }

    public static String GetTime() {
        DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static int getDefaultImplicitTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();
        return getTimeOutForProperty(config, SELENIUM_IMPLICITWAIT);
    }

    private static int getTimeOutForProperty(PropertiesConfiguration config, String config_property_name) {
        String timeOut = config.getString(config_property_name);
        return Integer.parseInt(timeOut);
    }

    public static int getMaxPageLoadTimeout() {
        PropertiesConfiguration config = getPropertiesConfiguration();

        return getTimeOutForProperty(config, PAGELOAD_MAXTIMEOUT);
    }


    private static PropertiesConfiguration getPropertiesConfiguration() {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(loadAndGetResourceLocation(CONFIG_FILE));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return config;
    }

    private static String loadAndGetResourceLocation(String fileName) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }

    public void selectDropDownByIndex(WebElement selectElement, int index) {
        Select dropdown = new Select(selectElement);
        dropdown.selectByIndex(index);
    }

    public void selectDropDownByVisibleText(WebElement selectElement, String dropDownText) {
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(dropDownText);
    }

    public void selectDropDownByValue(WebElement selectElement, String dropDownText) {
        Select dropdown = new Select(selectElement);
        dropdown.selectByValue(dropDownText);
    }

    public List<WebElement> getDropDownOption(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        List<WebElement> options = dropdown.getOptions();
        return options;
    }

    public String getSelectOptions(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    public List<String> getAllOptionsText(WebElement dropdownElement) {
        List<String> optionsText = new ArrayList<String>();
        Select dropdown = new Select(dropdownElement);
        List<WebElement> options = dropdown.getOptions();
        for (WebElement option : options) {
            optionsText.add(option.getText());
        }
        return optionsText;
    }

    public String getFutureYearWithCurrentDate(String dateFormat, int year) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        return sdfDate.format(cal.getTime());
    }

    public String getCurrentDateWithFormat() {
        Date currentDate = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("MMMM D, YYYY");
        return sdfDate.format(currentDate).toLowerCase();
    }

    /**
     * Convert a normal string to camelCase
     *
     * @param originalString to convert
     * @return
     */
    public String convertToCamelCase(String originalString) {
        originalString = originalString.replace(originalString.substring(0, 1), originalString.substring(0, 1).toLowerCase());
        int middleLetterIndex = originalString.length() / 2;
        return originalString.replace(originalString.substring(middleLetterIndex, middleLetterIndex + 1), originalString.substring(middleLetterIndex, middleLetterIndex + 1).toUpperCase());
    }

    public void switchToFrame(int frameId) {
        getDriver().switchTo().frame(frameId);
    }

    public void switchToFrame(String frameName) {
        getDriver().switchTo().frame(frameName);
    }

    public void switchToDefaultFrame() {
        getDriver().switchTo().defaultContent();
    }

    /**
     * Return particular attribute from DOM for
     * given element
     *
     * @param element       to be found
     * @param attributeName to get the value
     * @return value of the attribute. If passed attribute is not available
     * returns "null"
     */
    public String getAttributeValue(WebElement element, String attributeName) {
        //waitUntilElementDisplayed(element);
        //scrollDownToElement(element);
        return element.getAttribute(attributeName).trim();
    }

    public boolean waitForTextToAppear(WebElement element, String textToAppear, int secs) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), secs);
            wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForFrameToLoad(int frameId, int secs) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), secs);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void switchToFrame(WebElement element) {
        waitUntilElementDisplayed(element);
        getDriver().switchTo().frame(element);
    }

    public boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        }   // try
        catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    public void acceptAlert() {
        if (isAlertPresent())
            getDriver().switchTo().alert().accept();
    }

    public boolean waitForTextToAppearEqual(final WebElement element, final String textToAppear, int secs) {
        boolean result = false;
        try {
            result = new WebDriverWait(mobDriver, secs).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    String text = element.getText();
                    return text.equalsIgnoreCase(textToAppear);
                }
            });
        } catch (Exception e) {
            return false;
        }
        return result;
    }

    public boolean isPromoNotDisplaying(MobileDriver driver) {
        try {
            /*WebElement closeLinkOnPromo = getDriver().findElement(By.cssSelector(".ui-icon.ui-icon-closethick"));*/
            WebElement closeLinkOnPromo = getDriver().findElement(By.cssSelector("#email-signup-modal .email-signup-modal-header>button"));

            if (isDisplayed(closeLinkOnPromo)) {
                click(closeLinkOnPromo);
                logger.info("The promo is displaying....clicked on close");
                try {
                    return verifyElementNotDisplayed(closeLinkOnPromo, 20);
                } catch (Throwable invEx) {
                    return true;
                }
            }
        } catch (Throwable t) {
            return true;
        }
        return true;

    }

    /**
     * @param element
     * @return
     */
    public boolean isDisplayed(WebElement element) {
        try {
            return waitUntilElementDisplayed(element, 30);
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public boolean waitForTextToPresent(String text, WebElement selector, int secs) {
        try {
            WebDriverWait waitElem = new WebDriverWait(getDriver(), secs);
            return waitElem.until(ExpectedConditions.textToBePresentInElement(selector, text));
        } catch (Exception e) {
            return false;
        }
    }


    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min) + min;
        return randomNum;
    }


    public String getTextFromAlert(MobileDriver driver) {
        Alert alert = getDriver().switchTo().alert();
        String value = alert.getText();
        return value;
    }

    public String verifyGhostText(WebElement SearchField) {
        String ghostText = SearchField.getAttribute("value");
        return ghostText;
    }

    public String getCurrentURL() {
        return getDriver().getCurrentUrl();
    }

    public void mouseHover(WebElement element) {
        //not required mouse hove in mobile
        /*staticWait();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        staticWait();*/

    }

    public void mouseClick(WebElement element) {

        staticWait();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();
        actions.click().build().perform();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        staticWait();

    }

    public void waitAndAcceptAlert(int waitTimeInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), waitTimeInSeconds);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert();
            logger.info("Alert Displayed: " + alert.getText());
            alert.accept();
            staticWait(3000);
            logger.info("Alert Accepted");

        } catch (Exception e) {
            logger.info("Alert Not Displayed.");
        }
    }

    public boolean setAttributeValue(WebElement element, String attributeName, String value) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('" + element + "').setAttribute('" + attributeName + "', '" + value + "')");
        return getAttributeValue(element, attributeName).equalsIgnoreCase(value);
    }

    public void clickJavaScriptExecute(String element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('" + element + "').click()");
    }

    public void javaScriptClick(WebDriver mobDriver, WebElement element) {
        ((JavascriptExecutor) mobDriver).executeScript("arguments[0].click();", element);
    }

    public void scrollBy(String element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('" + element + "').scrollBy(0,250)");

    }

    /**
     * clear the value of an element and
     * and type the value
     *
     * @param element to perform actions
     * @param text    to be type on element
     */
    public void clearAndFillText(WebElement element, String text) {
        waitUntilElementDisplayed(element);
        element.clear();
        element.sendKeys(text);
        try {
            //getDriver().hideKeyboard();
        } catch (Exception e) {
        }
    }

    public void clearAndFillTextUsingJavaScript(MobileDriver mobDriver, String element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) mobDriver;
        js.executeScript("document.getElementsByName('" + element + "')[0].value=''");
        js.executeScript("document.getElementsByName('" + element + "')[0].value='" + text + "'");

    }

    public void clickElementByClass(String element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementsByClassName('" + element + "')[0].click()");

    }

    public boolean TextPresent(WebDriver mobDriver, String SearchText) {
        if (mobDriver.getPageSource().toLowerCase().contains(SearchText.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStringInCamelCasePattern(String text) {
        String camelCasePattern = "([A-Z]+[a-z]+\\w+)+";
        System.out.println(text.matches(camelCasePattern));
        return text.matches(camelCasePattern);
    }

    public WebElement getFirstElementFromList(List<WebElement> elements) {
        WebElement firstElement = null;
        for (WebElement element : elements) {
            firstElement = element;
            break;
        }
        return firstElement;
    }

    public WebElement getLastElementFromList(List<WebElement> elements) {
        WebElement lastElement = null;
        int size = elements.size();
        lastElement = elements.get(size - 1);
        return lastElement;
    }

    public String convertRgbToTextColor(String color) {
        String s1 = color.substring(5);
        StringTokenizer st = new StringTokenizer(s1);
        int r = Integer.parseInt(st.nextToken(",").trim());
        int g = Integer.parseInt(st.nextToken(",").trim());
        int b = Integer.parseInt(st.nextToken(",").trim());
        Color c = new Color(r, g, b);
        String hex = "#" + Integer.toHexString(c.getRGB()).substring(2);
        return hex;
    }

    public Object getRGBOfAnElement(WebElement element) {
        return ((JavascriptExecutor) getDriver()).executeScript("$(\"" + element + "\").css(" + "color" + ")");
    }

    public void addStateCookie(String state) {
        Cookie cookie = getDriver().manage().getCookieNamed("tcpState");
        if (cookie == null) {
            Cookie stateCookie = new Cookie("tcpState", state);
            getDriver().manage().addCookie(stateCookie);
            logger.info("Added State cookie " + stateCookie);
            refreshPage();
        } else if (!cookie.getValue().equals("NJ") && !cookie.getValue().equals("TX") && !cookie.getValue().equals("CA") && !cookie.getValue().equals("AL") && !cookie.getValue().equals("ON") && !cookie.getValue().equals("MB")) {
            getDriver().manage().deleteCookie(cookie);
            logger.info("Deleted  cookie " + cookie);
            Cookie stateCookie = new Cookie("tcpState", state);
            getDriver().manage().addCookie(stateCookie);
            logger.info("Added State cookie " + stateCookie);
            refreshPage();

        }

    }

    public void addJSessionCookie(String jSessionID) {
        Cookie cookie = getDriver().manage().getCookieNamed("JSESSIONID");
//        if(cookie==null) {
        Cookie jsessionidCookie = new Cookie("JSESSIONID", jSessionID);
        getDriver().manage().addCookie(jsessionidCookie);
        logger.info("Added JSessionID cookie " + jsessionidCookie);
        refreshPage();
//        }


    }

    public void disableZoom(MobileDriver driver) throws InterruptedException {
        mouseHover(getDriver().findElement(By.xpath("//*[@class='overlay-slot']/a")));
        disableImageZoom();
    }

    public void disableImageZoom() {
        try {
            String s1 = "$('.cloudzoom.large-image').off()";
            ((JavascriptExecutor) getDriver()).executeScript(s1);
        } catch (Exception e) {
            logger.info("No Zoom is displaying");
        }
    }


    public static boolean checkAlphabeticalOrder(LinkedList<String> product_names) {
        String previous = "";

        for (final String current : product_names) {
            if (current.compareTo(previous) < 0)
                return false;
            previous = current;
        }

        return true;
    }

    public String getCurrentDateTime(String datFormat) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(datFormat);
        Calendar cal = Calendar.getInstance();
        return sdfDate.format(cal.getTime());
    }

    public boolean scrollDownUntilElementDisplayed(WebElement webElement) {
        ((JavascriptExecutor) mobDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        staticWait(1000);
        return waitUntilElementDisplayed(webElement, 10);
    }

    public void scrollToBottom() {
        staticWait(500);
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        staticWait(500);
    }

    /**
     * @param element Determine whether or not this element is selected or not.
     *                This operation only applies to input elements such as checkboxes, options in a select and radio buttons.
     * @return True if the element is currently selected or checked, false otherwise.
     * @Author: Jkotha
     */
    public boolean isSelected(WebElement element, WebElement inputElement) {
        waitUntilElementDisplayed(element);
        return inputElement.isSelected();
    }

    /**
     * @param element unselect the element if selected
     *                This operation only applies to input elements such as checkboxes, options in a select and radio buttons.
     * @Author: Jkotha
     */
    public void unSelect(WebElement element, WebElement inputElement) {
        if (waitUntilElementDisplayed(element, 10) && isSelected(element, inputElement))
            click(element);
    }

    /**
     * @param element unselect the element if selected
     *                This operation only applies to input elements such as checkboxes, options in a select and radio buttons.
     * @Author: Jkotha
     */
    public void select(WebElement element, WebElement inputElement) {
        if (waitUntilElementDisplayed(element, 10) && !isSelected(element, inputElement))
            javaScriptClick(mobDriver, element);
    }

    public String getValueOfDataElement(String dataElementName) {
        JavascriptExecutor executor = (JavascriptExecutor) mobDriver;
        Object result = executor.executeScript("return _satellite.getVar('" + dataElementName + "');");
        return result.toString();
    }

    public void scrollToTop() {
        staticWait(500);
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
        staticWait(500);
    }

    public boolean scrollDownToElement(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        int x = webElement.getLocation().getX();
        int y = webElement.getLocation().getY();
        js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
        return waitUntilElementDisplayed(webElement);
    }

    public void scrollToPosition(int x, int y) {
        ((JavascriptExecutor) mobDriver).executeScript("window.scrollBy(" + x + "," + y + ")");
    }

    /**
     * Switch to a child window if two windows displayed a
     */
    public void switchToWindow() {
        Set<String> windows = mobDriver.getWindowHandles();
        for (String window : windows) {
            logger.info("Current windows" + window);
            if (!window.equals(parentWindow)) {
                logger.info("Switch to window" + window);
                mobDriver.switchTo().window(window);
            }
        }
    }

    /**
     * get size of current windows opened by driver
     *
     * @return size of windows
     */
    public int getCurrentWindowHandles() {
        return mobDriver.getWindowHandles().size();
    }

    /**
     * Switch to original window
     */
    public void switchToParent() {
        mobDriver.switchTo().window(parentWindow);
    }

    public boolean scrollUpToElement(WebElement webElement) {
//        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
//        jse.executeScript("window.scrollBy(0,-650)","");
        return waitUntilElementDisplayed(webElement, 5);
    }

    /**
     * Created By Pooja on 23/05/2018
     * This Method compares two counts are not equal
     */

    public boolean compareTwoCount(int previousCount, int finalCount) {
        if (previousCount > finalCount) {
            return true;
        }
        return false;

    }

    /**
     * Created By Pooja on 23rd May,2018
     * This Method stores the name of categories displayed after clicking Espot on Home Page
     */
    public List<String> getListText(List<WebElement> ele) {
        List<String> allCategories = new ArrayList<>();
        for (WebElement element : ele) {
            allCategories.add(getText(element));
        }
        return allCategories;
    }

    /**
     * Created By Pooja on 23rd May,2018
     * This Method Compares Texttwo Lists text and verifies there is nothing common in the two lists
     */
    public boolean compareTextInTwoList(List<String> list, List<String> subList) {
        for (String element : subList) {
            if (list.contains(element)) {
                return false;
            }
        }
        return true;
    }

    public void addStepDescription(String description) {
        ATUReports.add(description, LogAs.INFO, null);
    }

    /**
     * Created By Pooja on 23/05/2018
     * This Method compares two counts are equal
     */

    public boolean verifyTwoCountsEqual(int previousCount, int finalCount) {
        if (previousCount == finalCount) {
            return true;
        }
        return false;

    }


    /**
     * Created by Richa Priya
     * Wait for the url to load
     *
     * @param urlValue      Pass string type value of the url
     * @param timeInSeconds Pass the time in second for which you want your condition to wait.
     * @return true if url is changed to the Url provided
     */
    public Boolean waitUntilUrlChanged(String urlValue, int timeInSeconds) {
        try {
            (new WebDriverWait(getDriver(), timeInSeconds)).until(ExpectedConditions.urlContains(urlValue));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Get the position attribute of element passed
     *
     * @param cssSelector Pass string type value css selector
     * @param value       Pass the value you want to get -> options :: top, left, right, bottom, x and y.
     * @return string of position returned of an element
     */
    public String getElementPosition(String cssSelector, String value) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String returnedVal = null;

        switch (value) {
            case "top":
                returnedVal = js.executeScript("return document.querySelector(\"" + cssSelector + "\").getBoundingClientRect().top ;").toString();

            case "right":
                returnedVal = js.executeScript("return document.querySelector(\"" + cssSelector + "\").getBoundingClientRect().right ;").toString();

            case "bottom":
                returnedVal = js.executeScript("return document.querySelector(\"" + cssSelector + "\").getBoundingClientRect().bottom ;").toString();

            case "left":
                returnedVal = js.executeScript("return document.querySelector(\"" + cssSelector + "\").getBoundingClientRect().left ;").toString();

            case "all":
                returnedVal = js.executeScript("return document.querySelector(\"" + cssSelector + "\").getBoundingClientRect();").toString();

        }
        return returnedVal;
    }

    /**
     *  * Created by Richa
     *  * Get the product title by index
     *  * @return product title at specified index
     *  
     */
    public String getTextByIndex(List<WebElement> element, int index) {
        waitUntilElementDisplayed(element.get(index));
        return element.get(index).getText();
    }

    /**
     *   Created by Pooja
     *   Get the Text of the lement
     *   @return verify the element Text contains the Text being Passed as an argument
     *  
     */
    public boolean verifyTextUsingContains(WebElement element, String text) {
        return getText(element).contains(text);
    }

    /* Created by RichaK
     * @param index
     * @return true if bopis radio button is enabled.
     */
    public boolean isRadioButtonEnabled(List<WebElement> elem, int index) {
        WebElement radioButton = elem.get(index);
        return isEnabled(radioButton);
    }

    public void jqueryClickXPath(String xpath) {
        ((JavascriptExecutor) getDriver()).executeScript("$x(\"" + xpath + "\").click()");
    }

    /**
     * Select an element if it is not.
     *
     * @param element to be selected
     */
    public void selectByJQueryClick(String element) {

        jqueryClickXPath(element);

    }

    /**
     * form the api url based on the env
     *
     * @param endpoint of the api
     * @return full api call
     */
    public String formApiUrl(String endpoint) {
        String currentenv = "";
        try {
            currentenv = EnvironmentConfig.getApplicationUrl().split("/us/home")[0];
            currentenv = currentenv + "/api/" + endpoint;
            return currentenv;
        } catch (Exception e) {
            return currentenv;
        }
    }

    /**
     * Get the response code for the api call
     *
     * @param requestUrl to check
     * @return status code of the url
     */
    public int getResponseCodeForApi(String requestUrl, String requestMethod) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod.toUpperCase());
            connection.connect();
            System.out.print(connection.getResponseCode());
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get the cart count from cookie
     *
     * @return no of items in bag
     */
    public String getCookieValue(String cookieName) {
        Cookie cookie = getDriver().manage().getCookieNamed(cookieName);
        return cookie.getValue();
    }

    /**
     * * Created by Richa Priya
     * * Get the index by providing text
     * * @return product title at specified index
     */
    public int getIndexByText(List<WebElement> element, String text) {
        int index = 0;
        for (int i = 0; i < element.size(); i++) {
            waitUntilElementDisplayed(element.get(i));
            if (element.get(i).getText().contains(text)) {
                index = i;
                break;
            }
        }
        addStepDescription("Index for " + text + " present on Order Ledger is" + index);
        return index;
    }

    /**
     * * Created by RichaK
     * * Get the index by providing class
     * * @return index of specified class
     */
    public int getIndexByClass(List<WebElement> element, String className) {
        int index = 0;
        for (int i = 0; i < element.size(); i++) {
            waitUntilElementDisplayed(element.get(i));
            if (element.get(i).getAttribute("class") != null || !element.get(i).getAttribute("class").equals("")) {
                if (element.get(i).getAttribute("class").contains(className)) {
                    index = i;
                    break;
                }
            }
        }
        addStepDescription("Index for " + className + " present " + index);
        return index;
    }

    public String getLastFourDigitsOfCCNumber(String creditCardNumber) {
        return creditCardNumber.substring(creditCardNumber.length() - 4).trim();
    }

    public void addStepDescriptionWithStatus(boolean status, String description) {
        if (!status) {
            ATUReports.add(description, LogAs.FAILED, null);
        } else {
            ATUReports.add(description, LogAs.PASSED, null);
        }
    }

    public boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e){
            addStepDescription("Not a number");
            return false;
        }
    }
    
    public String getCurrentWindow() {
        return mobDriver.getWindowHandle();
    }
    
    public void clickAndSwitchToWindowFrom(WebElement element, String parentWindow) {
        click(element);
        Set<String> childWindows = mobDriver.getWindowHandles();
        for (String window : childWindows) {
            System.out.println("Current windows" + window);
            if (!window.equals(parentWindow)) {
                logger.info("Switch to window" + window);
                mobDriver.switchTo().window(window);
            }
        }
    }
    
    public void switchBackToParentWindow(String parentWindow) {
        mobDriver.switchTo().window(parentWindow);
    }
    
    public void closeDriver() {
        mobDriver.close();
    }
    
    
    public int getElementsSize(List<WebElement> elements) {
        try {
            return elements.size();
        } catch (Exception e) {
            return 0;
        }

    }

}