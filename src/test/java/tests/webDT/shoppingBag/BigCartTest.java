package tests.webDT.shoppingBag;

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
 * Created by Venkat_TK on 4/19/2017.
 */
public class BigCartTest extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;
    String email1;

    @BeforeClass
    public void initDriver() throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @BeforeMethod
    public void openBrowser() throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        emptyShoppingBag();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @Test
    public void bigCartCheckout() throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify the user is able to add 120 different products to the bag and is able to perform checkout using VISA payment method.");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        String searchKeyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermBigCart", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermBigCart", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyWord, qty);

        String cartLimit = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermBigCart", "CartLimit");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");


        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        addToBagBySearchingBigCart(itemsAndQty,cartLimit);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );

        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions,reviewPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")),"Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions,visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"),visaBillUS.get("addressLine2"), visaBillUS.get("city"), visaBillUS.get("stateShortName"), visaBillUS.get("zip"),visaBillUS.get("cardNumber"), visaBillUS.get("securityCode"), visaBillUS.get("expMonthValueUnit")),"Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions),"Clicked on the Submit Order button in the Order Review section.");
        getAndVerifyOrderNumber("bigCartCheckout");
    }
}