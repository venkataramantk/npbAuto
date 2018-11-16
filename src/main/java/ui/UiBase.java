package ui;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
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
import ui.support.Config;

import java.awt.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by skonda on 5/18/2016.
 */
public class UiBase {
    private static final String SELENIUM_MAXTIMEOUT = "selenium.element.maxtimeout";
    private static final String SELENIUM_MINTIMEOUT = "selenium.element.mintimeout";
    private static final String SELENIUM_IMPLICITWAIT = "selenium.element.implicitwait";
    private static final String PAGELOAD_MAXTIMEOUT = "selenium.pageload.timeout";
    private static final String CONFIG_FILE = "Config.properties";
    private static String browserType;
    public static int surveryModalCount = 0;
    public static int safariNewsLetterModal = 0;
    private WebDriver driver;
    Logger logger = Logger.getLogger(UiBase.class);

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void clearAndFillText(WebElement element, String text) {
        element.clear();
        staticWait(500);
        element.sendKeys(text);
    }

    public void elementToPresentByForcingWithJS(WebElement elem) {
        String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
        ((JavascriptExecutor) driver).executeScript(js, elem);
    }

    public static void highLightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

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

    public void fillText(WebElement element, String text) {
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        try {
            return element.getText();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }

    }

    public String getRandomNumber(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

    public void click(WebElement element) {
//        waitUntilElementDisplayed(element);
        element.click();
    }

    public void clear(WebElement element) {
        element.clear();
    }

    public boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Throwable t) {
            return false;
        }
    }

    @Deprecated //{@link refer # isSelected(WebElement element, WebElement inputElement)
    public boolean isSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Throwable t) {
            return false;
        }
    }

    /**
     * @param element Determine whether or not this element is selected or not.
     *                This operation only applies to input elements such as checkboxes, options in a select and radio buttons.
     * @return True if the element is currently selected or checked, false otherwise.
     * @Author: Jkotha
     */
    public boolean isSelected(WebElement element, WebElement inputElement) {
        if (waitUntilElementDisplayed(element, 10) && inputElement.isSelected())
            return true;
        else
            return false;
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
            click(element);
    }

    public void deleteAllCookies() {
        //String privacyCookie = "tcpPrivacyPolicyDisplayed";
        //Cookie cookie = driver.manage().getCookieNamed(privacyCookie);
        driver.manage().deleteAllCookies();
        /*if (cookie != null) {
            driver.manage().deleteCookieNamed(privacyCookie);
            refreshPage();

            Cookie privacy = new Cookie(privacyCookie, "TRUE");
            driver.manage().addCookie(privacy);
            logger.info("Edited Privacy cookie and Deleted all Cookies" + privacy);
        }*/
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
        driver.navigate().refresh();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public Boolean waitUntilElementClickable(WebElement element, int timeInSeconds) {
        try {

            (new WebDriverWait(driver, timeInSeconds))

                    .until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean waitUntilElementDisplayed(WebElement element, int timeInSeconds) {
        try {

            (new WebDriverWait(driver, timeInSeconds))

                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean waitUntilElementsAreDisplayed(List<WebElement> elements, int timeInSeconds) {
        try {

            (new WebDriverWait(driver, timeInSeconds))

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

            (new WebDriverWait(driver, getMaxTimeout()))

                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {

            return false;
        }

    }

    public void jqueryClick(String cssSelector) {
        ((JavascriptExecutor) getDriver()).executeScript("$(\"" + cssSelector + "\").click()");
    }

    public Boolean verifyElementNotDisplayed(WebElement element, int timeInSeconds) {
        try {
            try {
                (new WebDriverWait(driver, timeInSeconds))

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
                (new WebDriverWait(driver, timeInSeconds))

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
            Boolean invisible = (new WebDriverWait(driver, getMinTimeout()))
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

    public String getSelectOption(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        return dropdown.getFirstSelectedOption().getText();
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

    public void switchToFrame(int frameId) {
        getDriver().switchTo().frame(frameId);
    }

    public void switchToFrame(String frameName) {
        getDriver().switchTo().frame(frameName);
    }

    public void switchToDefaultFrame() {
        getDriver().switchTo().defaultContent();
    }

    public String getAttributeValue(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public boolean waitForTextToAppear(WebElement element, String textToAppear, int secs) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, secs);
            wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForFrameToLoad(int frameId, int secs) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, secs);
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

    public void closeSurveyPopUp(WebDriver dr) {
        try {
            if (surveryModalCount == 0 && (Config.getEnvironmentProfile().toLowerCase().contains("prod") || (Config.getBrowserType().toLowerCase().contains("safari")))) {
                //ThreadExt.sleep(WaitTime.Medium);
                if (dr.findElements(By.xpath("//*[@id='IPEinvL']/map/area[contains(@alt,'close')]")).size() > 0) {
                    dr.findElement(By.xpath("//*[@id='IPEinvL']/map/area[contains(@alt,'close')]")).click();
                    surveryModalCount = 1;
                }
            }
            if (safariNewsLetterModal == 1 && Config.getBrowserType().toLowerCase().contains("safari")) {
                if (dr.findElements(By.xpath("//span[contains(.,'close')]")).size() == 1) {
                    dr.findElement(By.xpath("//span[contains(.,'close')]")).click();
                    safariNewsLetterModal = 2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            driver.switchTo().alert().accept();
    }

    public boolean waitForTextToAppearEqual(final WebElement element, final String textToAppear, int secs) {
        boolean result = false;
        try {
            result = new WebDriverWait(driver, secs).until(new ExpectedCondition<Boolean>() {
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

    public boolean isPromoNotDisplaying(WebDriver driver) {
        try {
            /*WebElement closeLinkOnPromo = driver.findElement(By.cssSelector(".ui-icon.ui-icon-closethick"));*/
            WebElement closeLinkOnPromo = driver.findElement(By.cssSelector("#email-signup-modal .email-signup-modal-header>button"));

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

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public boolean waitForTextToPresent(String text, WebElement selector, int secs) {
        try {
            WebDriverWait waitElem = new WebDriverWait(driver, secs);
            return waitElem.until(ExpectedConditions.textToBePresentInElement(selector, text));
        } catch (Exception e) {
            return false;
        }
    }


    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }


    public String getTextFromAlert(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        String value = alert.getText();
        return value;
    }

    public String verifyGhostText(WebElement SearchField) {
        String ghostText = SearchField.getAttribute("value");
        return ghostText;
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void mouseHover(WebElement element) {
        staticWait();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            WebDriverWait wait = new WebDriverWait(driver, waitTimeInSeconds);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();
            logger.info("Alert Displayed: " + alert.getText());
            alert.accept();
            staticWait(3000);
            logger.info("Alert Accepted");

        } catch (Exception e) {
            logger.info("Alert Not Displayed.");
        }
    }

    public boolean setAttributeValue(WebElement element, String attributeName, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('" + element + "').setAttribute('" + attributeName + "', '" + value + "')");
        return getAttributeValue(element, attributeName).equalsIgnoreCase(value);
    }

    public void clickJavaScriptExecute(String element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('" + element + "').click()");
    }

    public void javaScriptClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollBy(String element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('" + element + "').scrollBy(0,250)");

    }

    public void clearAndFillTextUsingJavaScript(WebDriver driver, String element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByName('" + element + "')[0].value=''");
        js.executeScript("document.getElementsByName('" + element + "')[0].value='" + text + "'");

    }

    public void clickElementByClass(String element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByClassName('" + element + "')[0].click()");

    }

    public boolean TextPresent(WebDriver driver, String SearchText) {
        if (driver.getPageSource().toLowerCase().contains(SearchText.toLowerCase())) {
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

    public Object getRGBOfAnElement(String element) {
        return ((JavascriptExecutor) getDriver()).executeScript("$(\"" + element + "\").css('" + "color" + "')");
    }

    public Object getElementFontWeight(String element) {
        return ((JavascriptExecutor) getDriver()).executeScript("$(\"" + element + "\").css('" + "font-weight" + "')");
    }

    public Object getElementTextDecoration(String element) {
        return ((JavascriptExecutor) getDriver()).executeScript("$(\"" + element + "\").css('" + "text-decoration" + "')");
    }

    public void addStateCookie(String state) {
        Cookie cookie = driver.manage().getCookieNamed("tcpState");
        if (cookie == null) {
            Cookie stateCookie = new Cookie("tcpState", state);
            driver.manage().addCookie(stateCookie);
            logger.info("Added State cookie " + stateCookie);
            refreshPage();
        } //else if (!cookie.getValue().equals("NJ") && !cookie.getValue().equals("TX") && !cookie.getValue().equals("CA") && !cookie.getValue().equals("AL") && !cookie.getValue().equalsIgnoreCase("MB")) {
            else {
            driver.manage().deleteCookie(cookie);
            logger.info("Deleted  cookie " + cookie);
            Cookie stateCookie = new Cookie("tcpState", state);
            driver.manage().addCookie(stateCookie);
            logger.info("Added State cookie " + stateCookie);
            refreshPage();

        }

    }

    public void deleteIPerceptionCookie() {
//        Cookie cookie = driver.manage().getCookieNamed("ipe.30858.pageViewedCount");
//        if(cookie!=null) {
        driver.manage().deleteCookieNamed("ipe.30858.pageViewedCount");
        System.out.println("Deleted the iPerception cookie");
        driver.navigate().refresh();
//        }

    }

    public void addJSessionCookie(String jSessionID) {
        Cookie cookie = driver.manage().getCookieNamed("JSESSIONID");
//        if(cookie==null) {
        Cookie jsessionidCookie = new Cookie("JSESSIONID", jSessionID);
        driver.manage().addCookie(jsessionidCookie);
        logger.info("Added JSessionID cookie " + jsessionidCookie);
        refreshPage();
//        }


    }

    public void disableZoom(WebDriver driver) throws InterruptedException {
        mouseHover(driver.findElement(By.xpath("//*[@class='overlay-slot']/a")));
        disableImageZoom();
    }

    public void disableImageZoom() {
        try {
            String s1 = "$('.cloudzoom.large-image').off()";
            ((JavascriptExecutor) driver).executeScript(s1);
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

    //Sikuli Functions
//    public boolean sikuliClick(String filePath, String fileName) throws FindFailed {
//        try {
//
//            sikuliFind(filePath, fileName);
//            screen.click(buildImagePath(filePath, fileName));
//            return true;
//        }catch (FindFailed f){
//            f.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean sikuliDoubleClick(String filePath, String fileName) throws FindFailed {
//        try {
//
//            sikuliFind(filePath, fileName);
//            screen.doubleClick(buildImagePath(filePath, fileName));
//            return true;
//        }catch (FindFailed f){
//            f.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean sikuliHover(String filePath, String fileName) throws FindFailed {
//        try {
//
//            sikuliFind(filePath, fileName);
//            screen.hover(buildImagePath(filePath, fileName));
//            return true;
//        }catch (FindFailed f){
//            f.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean sikuliFind(String filePath, String fileName) throws FindFailed {
//        try {
//
//            sikuliWait(filePath, fileName);
//            screen.find(buildImagePath(filePath, fileName));
//            return true;
//        }catch (FindFailed f){
//            f.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean sikuliWait(String filePath, String fileName) throws FindFailed {
//        try {
//
//            screen.wait(buildImagePath(filePath, fileName),10);
//            return true;
//        }catch (FindFailed f){
//            f.printStackTrace();
//            return false;
//        }
//    }
//
//    //Implements the KeyBoard End key press action
//    public void sikuliScrollEnd(){
//        screen.type(Key.END);
//    }
//
//    //Implements the KeyBoard arrow down press action.
//    //The number of times the Key is pressed, is passed as a parameter.
//    public void sikuliScrollDown(int n) {
//        int i=0;
//        while (i<n) {
//
//            screen.type(Key.DOWN);
//            i++;
//        }
//    }

//    //Builds the reference Image file path, needed by Sikuli.
//    public String buildImagePath(String path, String imageName){
//        return Paths.get(path+"/" + Config.getBrowserType().toLowerCase()+"/" +imageName).toString();
//    }

    public String getDateTimeByAddingDays(String dateFormat, int days) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(dateFormat);
        getCurrentDateTime(dateFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, days);
        return sdfDate.format(cal.getTime());

    }

    public String getCurrentDateTime(String datFormat) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(datFormat);
        Calendar cal = Calendar.getInstance();
        return sdfDate.format(cal.getTime());
    }

    public boolean scrollDownUntilElementDisplayed(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
//        staticWait(1000);
        return waitUntilElementDisplayed(webElement, 10);
    }

    public boolean scrollDownToElement(WebElement webElement) {
//        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        jse.executeScript("window.scrollBy(0,450)","");
        return waitUntilElementDisplayed(webElement, 15);
    }

    public boolean scrollUpToElement(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-650)", "");
        return waitUntilElementDisplayed(webElement, 5);
    }

    public String getLastFourDigitsOfCCNumber(String creditCardNumber) {
        return creditCardNumber.substring(creditCardNumber.length() - 4).trim();
    }

    public void scrollToBottom() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public Object getTextAlignedValue(String cssSelector) {
        return ((JavascriptExecutor) getDriver()).executeScript("return $(\"" + cssSelector + "\").css('text-align')");
    }

    public long scrollPosition() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (Long) executor.executeScript("return window.scrollY;");
    }

    public String getValueOfDataElement(String dataElementName) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object result = executor.executeScript("return _satellite.getVar('" + dataElementName + "');");
        return result.toString();
    }

    public boolean scrollToTheTopHeader(WebElement webElement) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(document.body.scrollHeight,0)");
        return waitUntilElementDisplayed(webElement, 5);
    }

    public int getElementsSize(List<WebElement> elements) {
        try {
            return elements.size();
        } catch (Exception e) {
            return 0;
        }

    }

    public String getCurrentWindow() {
        return driver.getWindowHandle();
    }

    public void clickAndSwitchToWindowFrom(WebElement element, String parentWindow) {
        click(element);
        Set<String> childWindows = driver.getWindowHandles();
        for (String window : childWindows) {
            System.out.println("Current windows" + window);
            if (!window.equals(parentWindow)) {
                logger.info("Switch to window" + window);
                driver.switchTo().window(window);
            }
        }
    }

    public void switchBackToParentWindow(String parentWindow) {
        driver.switchTo().window(parentWindow);

    }

    public void switchToWindow(String parentWindow) {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            System.out.println("Current windows" + window);
            if (!window.equals(parentWindow)) {
                logger.info("Switch to window" + window);
                driver.switchTo().window(window);
            }
        }
    }

    public void closeDriver() {
        driver.close();
    }

    public void addStepDescription(String description) {
        ATUReports.add(description, LogAs.INFO, null);
    }
    public void addStepDescriptionWithScreenshot(String description) {
        ATUReports.setWebDriver(driver);
        ATUReports.add(description, LogAs.INFO, new CaptureScreen(
                CaptureScreen.ScreenshotOf.BROWSER_PAGE));
    }
    public void addStepDescWithRedFont(String description) {
        ATUReports.add("<b><font color=#8b0000>" + description + "</font></b>", LogAs.INFO, null);
    }

    public void addStepDescriptionWithStatus(boolean status, String description) {
        if (!status) {
            ATUReports.add(description, LogAs.FAILED, null);
        } else {
            ATUReports.add(description, LogAs.PASSED, null);
        }
    }

    public void addStepDescriptionWithWarn(boolean status, String description) {
        if (!status) {
            ATUReports.add(description, LogAs.WARNING, null);
        } else {
            ATUReports.add(description, LogAs.PASSED, null);
        }
    }
    public boolean isChecked(WebElement element) {
        String isCheckecd = getAttributeValue(element, "value");
        if (isCheckecd.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    public Object getElementLengthAtTop(String cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.querySelector(\"" + cssSelector + "\").getBoundingClientRect().top;");
        return js;
    }

    public Map<String, Object> getABTestAPICalls(){
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        Map<String,Object> val = (Map<String,Object>) (js.executeScript("return window.TCP_REACT_APP.ABTestRegistration"));
        return val;
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

}
