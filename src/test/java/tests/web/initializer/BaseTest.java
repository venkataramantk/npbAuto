package tests.web.initializer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by skonda on 5/18/2016.
 */
public class BaseTest extends PageInitializer {
    protected static final String storeXml = "store", usersXml = "users";
    protected static final String dataProviderName = "StoreAndUsers";
    protected static final String WIC = "wic", USONLY = "usonly", REGISTEREDONLY = "registeredonly", GUESTONLY = "guestonly",
            GIFTCARDPRODUCT = "giftcardproduct", PROD_REGRESSION = "prodregression", SMOKE = "smoke", CAONLY = "caonly", PDP = "pdp", PLP = "plp", SEARCH = "search",
            MYACCOUNT = "myaccount", GLOBALCOMPONENT = "globalcomponent", CHECKOUT = "checkout", PAYPAL = "paypal", GIFTCARD = "giftcard", PRODUCTION = "production", PLCC = "plcc", FDMS = "fdms", REGRESSION = "regression", BOPIS = "bopis", COUPONS = "coupons", CHEETAH = "cheetah", CREATEACCOUNT = "createaccount",
            DRAGONFLY = "dragonfly", RTPS = "rtps", R6 = "r6", EAGLE = "eagle", SHOPPINGBAG = "shoppingbag", PRODUCTIONONLY = "productiononly", OUTFIT = "outfit",INT_CHECKOUT= "intcheckout",RECOMMENDATIONS="recommendations",REMEMBEREDUSER="remembereduser",CARTMERGE="cartmerge", INTUATSTG="int_uatstg",
            OTTER = "otter",RHINO="rhino",FAVORITES="favorites";

    public String rowInExcelUS = "CreateAccountUS", rowInExcelCA = "CreateAccountCA";
    public ExcelReader e2eExcelReader = new ExcelReader(Config.getDataFile(Config.E2E_TESTING_DATAFILE));
    public String visa, mc_5series, mc_2series, amex, discover;
    public ExcelReader ropisExcelReader = new ExcelReader(Config.getDataFile(Config.PHASE2_DETAILS));
    ExcelReader phase2DetailsExcelReader = new ExcelReader(Config.getDataFile(Config.PHASE2_DETAILS));
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));

    public static final int MAX_COMPLETEDSTATUS_COUNT = 5;
    public Set<String> footerLinksAsGuest;
    public Set<String> footerLinksAsRegistered;


    public int bagCount;
    public int actualBagCount;
    public int selectedProductPos;
    public String cartCount;
    public String estimatedTot;

    public String orderNumber;

    @DataProvider(name = dataProviderName)
    public static Object[][] getUsersAndStore(ITestContext context) {
        String store = context.getCurrentXmlTest().getLocalParameters().get(storeXml);
        String user = context.getCurrentXmlTest().getLocalParameters().get(usersXml);

        List<String> users = Arrays.asList(user.split(","));
        Object[][] storeWithUser = new Object[users.size()][1];

        for (int i = 0; i < users.size(); i++) {
            String userAs = users.get(i).trim();
            storeWithUser[i] = new Object[]{store, userAs};
        }
        return storeWithUser;
       // return new Object[][]{{"US", "registered"}};
    }

    @DataProvider(name = "StoreUsersAndCard")
    public static Object[][] getUsersStoreAndCard(ITestContext context) {
        String store = context.getCurrentXmlTest().getLocalParameters().get(storeXml);
        String user = context.getCurrentXmlTest().getLocalParameters().get(usersXml);
        String cardType = context.getCurrentXmlTest().getLocalParameters().get("cardtype");

        List<String> users = Arrays.asList(user.split(","));
        List<String> cards = Arrays.asList(cardType.split(","));
        Object[][] storeWithUserCard = new Object[cards.size()][1];

        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i).trim();
            storeWithUserCard[i] = new Object[]{store, user, card};
        }
        return storeWithUserCard;
        //  return new Object[][]{{"US", "guest","Visa_P_New"},{"US", "guest","Visa_U_New"},{"US", "guest","Amex_P_New"}};
    }

    @DataProvider(name = "Users")
    public Object[][] users() {
        return new Object[][]{{"registered"}, {"guest"}};
    }

    @DataProvider(name = "Store")
    public Object[][] store() {
        return new Object[][]{{"US"}, {"CA"}};
    }

    /*Use this data provider in case you want to unit test your
    test method. Change the data provider name to "StoreAndUsersSingleTest"
    as @Test(dataProvider = "StoreAndUsersForSingleTest", priority = 3, groups = {"plp"}.
    To run a test for single parameter remove or comment all the other parameters*/
    @DataProvider(name = "StoreAndUsersForSingleTest")
    public static Object[][] getStoreAndUserForSingleTest() {
        return new Object[][]{{"US", "registered"},{"US", "guest"},
                {"CA", "guest"}, {"CA", "registered"}};
    }



    public boolean checkValueInList(List<String> lists, String value) {
        boolean compare = true;
        for (String list : lists) {
            if (list.contains(value)) {
                compare = true;
                break;
            }
        }
        return compare;
    }

    public String domUserName() {
        return Config.getDomUsername();
    }

    public String domPassword() {
        return Config.getDomPassword();
    }


/*    public void clickLoginAndLoginAsRegUser(String un, String pwd) {
        if (headerMenuActions.waitUntilElementDisplayed(overlayHeaderActions.closeOnDrawer, 5)) {
            overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        }
        if (headerMenuActions.waitUntilElementDisplayed(headerMenuActions.loginLinkHeader, 5)) {
            headerMenuActions.clickLoginLnk(loginDrawerActions);
            AssertFailAndContinue(loginDrawerActions.userLogin(un, pwd, overlayHeaderActions), "Logged in as Reg User: " + un);
        } else if (headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink)) {
            headerMenuActions.addStepDescription("Login link is not present, the user is already logged in?");
        }
    }*/


    public void acceptSecurityWarning() {
        try {
            (new Robot()).keyPress(java.awt.event.KeyEvent.VK_ENTER);

            (new Robot()).keyRelease(java.awt.event.KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getE2ETestingCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) throws Exception {
        Map<String, String> recordByRowName = e2eExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    public String getDT2TestingCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) throws Exception {
        Map<String, String> recordByRowName = dt2ExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    public void navigateToChromeBrowserAndInitializePages() {
        if (!Config.getBrowserType().equalsIgnoreCase("chrome")) {
            navigateToChromeDriver();
            driver = getDriver();
            initializePages(driver);
        }

    }

    public void navigateToChromeBrowserWithEventDriEnabledAndInitializePages() {
        if (!Config.getBrowserType().equalsIgnoreCase("chrome")) {
            navigateToChromeDriverWithEventDriverEnabled();
            driver = getDriver();
            initializePages(driver);
        }

    }

    public void navigateToFFBrowserWithEventDriEnabledAndInitializePages() {
        if (!Config.getBrowserType().equalsIgnoreCase("firefox")) {
            navigateToFFDriverWithEventDriverEnabled();
            driver = getDriver();
            initializePages(driver);
        }

    }


    public Map<String, String> getMapOfItemNamesAndQuantityWithCommaDelimited(String itemNames, String quantity) {
        Map<String, String> itemsAndQuantity = new HashMap<>();
        itemsAndQuantity.clear();
        if (itemNames.contains(",")) {
            String[] itemsSet = itemNames.split(",");
            if (quantity.contains(",")) {
                String[] quantitySet = quantity.split(",");
                for (int i = 0; i < itemsSet.length; i++) {
                    itemsAndQuantity.put(itemsSet[i].trim(), quantitySet[i].trim());
                }
            }
        } else {
            itemsAndQuantity.clear();
            itemsAndQuantity.put(itemNames.trim(), quantity.trim());
        }
        return itemsAndQuantity;
    }

    public List<String> convertCommaSeparatedStringToList(String items) {
        List<String> tokensList = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(items, ",");
        while (tokens.hasMoreTokens()) {
            String nextToken = tokens.nextToken();
            tokensList.add(nextToken.trim());
            logger.info("The token added to list: " + nextToken);
        }
        return tokensList;
    }

    public WebDriver closeAndReOpenBrowserAndNavigateToURL() throws Exception {

        driver.close();
        initializeDriver();
        driver = getDriver();
        initializePages(driver);

        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.refreshPage();
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.logoAgview);
        return driver;
    }

    public void switchToOldBrowser(WebDriver browser) {
        setDriver(browser);
        driver = getDriver();
        initializePages(driver);
    }

    public String getPhase2DetailsCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) {
        Map<String, String> recordByRowName = phase2DetailsExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    @BeforeSuite(alwaysRun = true)
    public void deleteOld_TestReports() throws IOException {
        System.out.println("Testing the before suites.....");
        String currentDir = System.getProperty("user.dir");
        try {
            String reportsDir = currentDir  + "/Test Reports/Results/";
            if (new File(reportsDir).exists()) {
                delete_OldRunDir(reportsDir);
            } else {
                logger.info("Test Reports folder doesn't exists.");
            }
        } catch (Throwable t) {
            logger.info("For some reason, the reports deletion method threw exception ");
        }
    }


    public Comparator<String> sortResults() {
        final Pattern p = Pattern.compile("[0-9]+");
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                Matcher m = p.matcher(object1);
                Integer number1 = null;
                if (!m.find()) {
                    return object1.compareTo(object2);
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2);
                    if (!m.find()) {
                        return object1.compareTo(object2);
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.compareTo(object2);
                        }
                    }
                }
            }
        };
        return c;
    }

    public boolean delete_OldRunDir(String reportsDir) throws IOException {
        File file = new File(reportsDir);
        String[] names = file.list();
        List<String> list = new ArrayList<String>(Arrays.asList(names));
        Comparator<String> c = sortResults();
        Collections.sort(list, c);
        int runCounts = 0;
        boolean isRunFolderDeleted = false;
        for (String run : list) {
            if (new File(reportsDir + run).isDirectory()) {
                if (run.contains("Run")) {
                    runCounts = runCounts + 1;

                }
            }
        }
        System.out.println("The total existing run counts " + runCounts);
        if (runCounts >= 10) {
            for (int i = 0; i < list.size(); i++) {
                if (new File(reportsDir + list.get(i)).isDirectory()) {
                    if (runCounts >= 20) {
                        if (new File(reportsDir + list.get(i)).exists()) {
                            FileUtils.deleteDirectory(new File(reportsDir + list.get(i)));
                            isRunFolderDeleted = !new File(reportsDir + list.get(i)).exists();
                            System.out.println("The Oldest run folder " + list.get(i) + " got deleted");
                            if (isRunFolderDeleted) {
                                runCounts--;
                                isRunFolderDeleted = true;
                            }

                        }
                    } else {
                        break;
                    }


                }
            }
            return isRunFolderDeleted;

        }
        return isRunFolderDeleted;
    }


    public void closeWindow() {
        driver.getWindowHandle();
        driver.close();
    }


    public String getTestCardDataFromExcel(String cvvIs, String cardType) {
        cardType = cardType.toUpperCase();
        if (cvvIs.equalsIgnoreCase("On")) {
            switch (cardType) {
                case "VISA":
                    return "VISA_New";
                case "MC_5SERIES":
                    return "MasterCard_New_5series";
                case "MC_2SERIES":
                    return "MasterCard_New_2series";
                case "AMEX":
                    return "Amex_New";
                case "DISCOVER":
                    return "Discover_New";
             /*   case "VISA_P":
                    return "VISA_P_New";
                case "MC_5SERIES_P":
                    return "MasterCard_P_New_5series";
                case "MC_2SERIES_P":
                    return "MasterCard_P_New_2series";
                case "AMEX_P":
                    return "Amex_P_New";
                case "DISCOVER_P":
                    return "Discover_P_New";
                case "VISA_U":
                    return "VISA_U_New";
                case "AMEX_Blank":
                    return "Amex_Blank_New";
                case "DISCOVER_U":
                    return "Discover_U_New";
                case "VISA_N":
                    return "VISA_N_New";
                case "MC_5SERIES_N":
                    return "MasterCard_N_New_5series";
                case "MC_2SERIES_N":
                    return "MasterCard_N_New_2series";
                case "AMEX_N":
                    return "Amex_N_New";
                case "DISCOVER_N":
                    return "Discover_N_New";*/
            }

        } else if (cvvIs.equalsIgnoreCase("Off")) {
            switch (cardType) {
                case "VISA":
                    return "Visa";
                case "MC_5SERIES":
                    return "MasterCard";
                case "MC_2SERIES":
                    return "MasterCard_2Series";
                case "AMEX":
                    return "Amex";
                case "DISCOVER":
                    return "Discover";
            }
        }

        return "Visa";
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

    public void switchBackToParentWindow(String parentWindow) {
        logger.info("Switching back to parent window" + parentWindow);
        driver.switchTo().window(parentWindow);


    }

    /**
     * Add product tobag through api method
     *
     * @param email of the page expected
     * @param password of the page expected
     * @param store of the page expected
     * @param qty of the page expected
     * @return true on clicking on shopping bag
     */

}
