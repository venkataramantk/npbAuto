package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 5/9/2017.
 * modified by Jagadeesh Kotha on 7/5/2018.
 */
public class Checkout_OrderLedger extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        headerMenuActions.deleteAllCookies();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY,PROD_REGRESSION}, priority = 0)
    public void validateOrderLedgerInSP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if " + user + " user can increase, decrease, remove and move the product to WL and also verify the bag count and item count in shipping page for " + store.toUpperCase() + " store");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        addToBagBySearching(searchKeywordAndQty);
        checkCountAndTotalInShippingPage(user);
        AssertFailAndContinue(shippingPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");
        shoppingBagPageActions.updateQty("3");
        checkCountAndTotalInShippingPage(user);
        AssertFailAndContinue(shippingPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");
        shoppingBagPageActions.updateQty("1");
        checkCountAndTotalInShippingPage(user);
        //Add product to bag and remove the product and also check the total and count
        AssertFailAndContinue(shippingPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");
        AssertFailAndContinue(shoppingBagPageActions.clickRemoveLinkByPosition(1), "Remove the first occurred product from the bag page and verify the count and the total");
        checkCountAndTotalInShippingPage(user);
        //Add product to bag and move the product to WL and also check the total and count
        AssertFailAndContinue(shippingPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");
        AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsReg(1), "Move the product to wishlist and verify the count and order total of the product");
        checkCountAndTotalInShippingPage(user);
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT,PROD_REGRESSION}, priority = 1)
    public void validateOrderLedgerInBP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if " + user + " user can increase, decrease, remove and move the product to WL and also verify the bag count and item count in billing page");

        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");

        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        addToBagBySearching(searchKeywordAndQty);
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
            if(env.equalsIgnoreCase("prod")){
                billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

            }
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            if (env.equalsIgnoreCase("prod")) {
                billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");

        AssertFailAndContinue(reviewPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");
        String estimatedTot = shoppingBagPageActions.getEstimateTotal();
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "Verify user navigate to billing page");
        AssertFailAndContinue(billingPageActions.validateTotInShippingAndBillingPage(estimatedTot), "validate estimated total in billing page and shopping bag page");
        billingPageActions.returnToBagPage(shoppingBagPageActions);

        AssertFailAndContinue(shoppingBagPageActions.updateQty("3"), "Update product qty to 3 and in bag and verify");
        String cartCount1 = shoppingBagPageActions.getBagCount();
        String estimatedTot1 = shoppingBagPageActions.getEstimateTotal();
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }

        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(shippingPageActions.validateItemCountWithBagCount(cartCount1), "Validate the cart count with the item count in the shipping page");
        AssertFailAndContinue(billingPageActions.validateTotInShippingAndBillingPage(estimatedTot1), "validate estimated total in billing page and shopping bag page");

        shippingPageActions.returnToBagPage(shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.updateQty("1"), "Update product qty to 1 and in bag and verify");
        String estimatedTot2 = shoppingBagPageActions.getEstimateTotal();
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }

        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.validateTotInShippingAndBillingPage(estimatedTot2), "validate estimated total in billing page and shopping bag page");

        if (user.equalsIgnoreCase("registered")) {
            shippingPageActions.returnToBagPage(shoppingBagPageActions);
            AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsReg(1), "Move the product to wishlist and verify the count and order total of the product");
            String cartCount3 = shoppingBagPageActions.getBagCount();
            String estimatedTot3 = shoppingBagPageActions.getEstimateTotal();
            AssertFailAndContinue(shippingPageActions.validateItemCountWithBagCount(cartCount3), "Validate the cart count with the item count in the shipping page");
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
            shippingPageActions.clickNextBillingButton(billingPageActions);
            AssertFailAndContinue(billingPageActions.validateTotInShippingAndBillingPage(estimatedTot3), "validate estimated total in billing page and shopping bag page");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT, USONLY,PROD_REGRESSION}, priority = 2)
    public void validateOrderLedgerInRP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if register user can increase, decrease, remove and move the product to WL and also verify the bag count and item count in review page");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        if(env.equalsIgnoreCase("prod")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Verify guest user navigate to shipping page on clicking the checkout as guest button ");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");
        String estimatedTot = shoppingBagPageActions.getEstimateTotal();
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "Verify user navigate to billing page");
        AssertFailAndContinue(billingPageActions.enterCVVForExpressAndClickNextReviewBtn(billDetailUS.get("securityCode"), reviewPageActions), "Click on next review button and check user navigate to review page");
        AssertFailAndContinue(reviewPageActions.validateTotInShippingAndBagPage(estimatedTot), "validate the estimated total in shopping bag page with the shipping page");
        AssertFailAndContinue(billingPageActions.returnToBagPage(shoppingBagPageActions), "Verify user navigate to shopping bag page");

        shoppingBagPageActions.updateQty("3");
        checkCountAndTotalInReviewPage(billDetailUS.get("securityCode"), user);
        reviewPageActions.returnToBagPage(shoppingBagPageActions);
        shoppingBagPageActions.updateQty("1");
        checkCountAndTotalInReviewPage(billDetailUS.get("securityCode"), user);

        if (user.equalsIgnoreCase("registered")) {
            reviewPageActions.returnToBagPage(shoppingBagPageActions);
            AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsReg(1), "Move the product to wishlist and verify the count and order total of the product");
            double estimatedTotalAtSB = Double.valueOf(shoppingBagPageActions.getEstimateTotal());
            checkCountAndTotalInReviewPage(billDetailUS.get("securityCode"), user);
            if(!env.equalsIgnoreCase("prod")) {
                reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
                double estimatedTotalAtCP = Double.valueOf(orderConfirmationPageActions.getEstimateTotal());
                AssertFailAndContinue(estimatedTotalAtSB == estimatedTotalAtCP, "The order total in shopping bag " + estimatedTotalAtSB + " and order confirmation page order total " + estimatedTotalAtCP);
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT,PROD_REGRESSION}, priority = 3)
    public void changeShippingMethodAndValidateTot(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the register user can  change the shipping method in shipping page and check it reflected accordingly in the billing page");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "express");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Verify guest user navigate to shipping page on clicking the checkout as guest button ");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(!shippingPageActions.isSelected(shippingPageActions.signupEmail_ChkBox, shippingPageActions.signupEmail_ChkInput), "Opt into marketing emails should be defaulted as un-selected");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1") + "   ",
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        AssertFailAndContinue(billingPageActions.validateEstimatedTotal(), "Validate the estimated total in billing page order ledger section");
        AssertFailAndContinue(billingPageActions.returnToShippingPage(shippingPageActions), "Verify user navigate to shipping page  page");
        AssertFailAndContinue(shippingPageActions.changeShippingMethod(), "Change the shipping method from one to another");
        AssertFailAndContinue(shippingPageActions.validateTotAfterShippingMethodChanged(), "Validate the estimated total after change the shipping method");
        String estimatedCost = shippingPageActions.getEstimateTotal();//get the estimated total in shipping page
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "Navigate back to billing page");
        AssertFailAndContinue(billingPageActions.validateTotInShippingAndBillingPage(estimatedCost), "Compare the estimated total in billing page with shipping page after change the shipping method");
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT,PROD_REGRESSION}, priority = 4)
    public void validateInOrderReceiptPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if user can add the product into the cart and verify the bag count and item count in shipping page");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> billDetailCA = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");

        if(env.equalsIgnoreCase("prod")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        addToBagFromFlip(searchKeyword, "2");
        headerMenuActions.navigateToShoppingBagPageFromHeader(shoppingBagDrawerActions, shoppingBagPageActions);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");

        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Verify guest user navigate to shipping page on clicking the checkout as guest button ");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
            shipDetailUS = shipDetailCA;
            billDetailUS = billDetailCA;
            if (env.equalsIgnoreCase("prod")) {
                billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        AssertFailAndContinue(reviewPageActions.returnToBagPage(shoppingBagPageActions), "Click on return to bag button");
        String cartCount = shoppingBagPageActions.getBagCount();
        AssertFailAndContinue(shippingPageActions.applyCouponCode(couponCode.get(0).toLowerCase()), "Enter the valid coupon code in lower case and click on apply button");
        String estimatedTot = shoppingBagPageActions.getEstimateTotal();

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Verify guest user navigate to shipping page on clicking the checkout as guest button ");
        }

        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheItemCountWithBag(cartCount), "Verify the item count should be match with the cart count in shopping bag page");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheEstimatedTotalWithBag(estimatedTot), "Verify the estimated total should be matched with the total in shopping bag page");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {CHECKOUT}, priority = 5)
    public void validateInOrderReceiptPageTaxAddress(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if user can add the product into the cart and verify the bag count and item count in shipping page");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validPOAddress");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "priority");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> billDetailCA = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        String couponCode = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
        if(env.equalsIgnoreCase("prod")){
            couponCode = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            couponCode = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA");
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
        }
        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(shippingPageActions.applyCouponCode(couponCode), "Enter the valid coupon code and click on apply button");

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Verify guest user navigate to shipping page on clicking the checkout as guest button ");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
            //shipDetailUS = shipDetailCA;
            billDetailUS = billDetailCA;
        }

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.returnToBagPage(shoppingBagPageActions), "Click on return to bag button");
        String cartCount = shoppingBagPageActions.getBagCount();

        String estimatedTot = shoppingBagPageActions.getEstimateTotal();

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
            /*AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"),
                    shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");*/

        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Verify guest user navigate to shipping page on clicking the checkout as guest button ");
            /*AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"),
                    shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");*/
        }

        //DT-38662
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions),"Verify user is redirected to billing page");
        AssertFailAndContinue(billingPageActions.enterCVVForExpressAndClickNextReviewBtn(billDetailUS.get("securityCode"), reviewPageActions), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        reviewPageActions.validateOrderLedgerSection();
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheItemCountWithBag(cartCount), "Verify the item count should be match with the cart count in shopping bag page");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheEstimatedTotalWithBag(estimatedTot), "Verify the estimated total should be matched with the total in shopping bag page");
        }
    }
}