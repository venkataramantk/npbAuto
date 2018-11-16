package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class Ecomm_orders_US extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;

    @BeforeClass
    public void initDriver() throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openBrowser() throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        headerMenuActions.addStateCookie("NJ");
    }

    @Test(invocationCount = 10, enabled = false)
    public void placeEcommorder() throws Exception {

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "superSaver");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = "Place@123";
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "00700953912534");
        productDetailsPageActions.selectFitAndSize();
        productDetailsPageActions.updateQty(qty);
        productDetailsPageActions.clickAddToBag();
        headerMenuActions.clickMiniCartAndCheckoutAsRegUser(shoppingBagDrawerActions, shippingPageActions);
        shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue"));
        enterPlaceCardDetailsAsGuestAndSubmit();
//        String orderno = receiptThankYouPageActions.getOrderNum();
//        System.out.print(emailAddressReg + "  order:  " + orderno);
    }

    @Test(invocationCount = 10, enabled = true)
    public void placeBopis() throws Exception {

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "superSaver");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> editBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");

        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = "Place@123";
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited("00440000474881", "07728");
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.clickProceedToBOPISCheckout(checkoutPickUpDetailsActions);
        checkoutPickUpDetailsActions.clickNextBillingButton(billingPageActions);
        billingPageActions.clearAndFillText(billingPageActions.cardNumField, "5780971104732265");
        billingPageActions.fillPaymentAddrDetailsWithoutSameAsShip(editBillUS.get("fName"), editBillUS.get("lName"), editBillUS.get("addressLine1"), editBillUS.get("city"), editBillUS.get("stateShortName"), editBillUS.get("zip"));
        billingPageActions.clickNextReviewButton(reviewPageActions, "");
//        reviewPageActions.clickSubmOrderButton(receiptThankYouPageActions);
//        String orderno = receiptThankYouPageActions.getOrderNum();
//        System.out.print(emailAddressReg + "  order:  " + orderno);
    }

    @Test(invocationCount = 10, enabled = true)
    public void mixedbag() throws Exception {

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "superSaver");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = "Place@123";
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "00700953912534");
        productDetailsPageActions.selectFitAndSize();
        productDetailsPageActions.updateQty(qty);
        productDetailsPageActions.clickAddToBag();

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited("00440000474881", "07728");
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.clickProceedToBOPISCheckout(checkoutPickUpDetailsActions);
        pickUpPageActions.clickShippingBtn(shippingPageActions);
        shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue"));
        enterPlaceCardDetailsAsGuestAndSubmit();
//        String orderno = receiptThankYouPageActions.orderNumbers.get(0).getText();
//        String orderno1 = receiptThankYouPageActions.orderNumbers.get(1).getText();
//        System.out.print("mixed cart" + emailAddressReg + "  order:  " + orderno + "  " + orderno1);
    }
}