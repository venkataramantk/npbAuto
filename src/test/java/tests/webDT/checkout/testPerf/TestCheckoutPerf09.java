package tests.webDT.checkout.testPerf;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

/**
 * Created by Venkat_TK on 5/25/2017.
 */
public class TestCheckoutPerf09 extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(TestCheckoutPerf09.class);

    @BeforeClass
    public void initDriver() throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @BeforeMethod
    public void openBrowser() throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test
    public void regNavigateToBillingPage()throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if the Registered user having products in the bag, is redirected to the Billing page, after clicking on the Next:Billing button in the Shipping page and clicks Submit order places the normal checkout ship to home order");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        Map<String, String> credentials = excelReaderDT2.getExcelData("PerfLogin", "User9");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchPerf3", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchPerf3", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(credentials.get("Email"), credentials.get("Password"));
        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );

        //Clicked on Proceed to Checkout, land on the Checkout page, with the Shipping Accordion expanded.
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions,reviewPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")),"Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions,billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"),billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"),billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")),"Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions),"Clicked on the Submit Order button in the Order Review section.");
        getAndVerifyOrderNumber("regNavigateToBillingPage");
    }





}
