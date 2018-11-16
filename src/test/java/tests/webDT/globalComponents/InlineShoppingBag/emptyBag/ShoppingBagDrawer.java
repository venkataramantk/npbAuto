package tests.webDT.globalComponents.InlineShoppingBag.emptyBag;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 3/6/2017.
 */
//USer Story: DT-858
//@Listeners(MethodListener.class)
public class ShoppingBagDrawer extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(ShoppingBagDrawer.class);
    String emailAddress;
    private String password;
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
            //Have Registered user related scenario
        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
      //  headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.deleteAllCookies();
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");}

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
       // driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION,PROD_REGRESSION})
    public void validateEmptyShoppingBagGuestUser(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to shopping bag drawer when he clicks on shopping bag icon from header");
        List<String> emptyBagContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MiniShoppingBag", "emptyBagContent", "expectedContent"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Verify if guest user redirected to the empty shopping bag drawer");
       if(user.equalsIgnoreCase("guest")) {
           AssertFailAndContinue(shoppingBagDrawerActions.validateEmptyShoppingBagAsGuest(emptyBagContent.get(0), emptyBagContent.get(1), emptyBagContent.get(2)), "Verify if the empty text message, create new account text and  subtotal text message in empty shopping bag drawer");
           AssertFailAndContinue(shoppingBagDrawerActions.validateButtonsInEmptyBag(), "Verify if the appropriate buttons and links are present in the empty shopping bag drawer");
       }
       else {
           AssertFailAndContinue(shoppingBagDrawerActions.validateEmptyBagDrawerForRegUser(emptyBagContent.get(0),emptyBagContent.get(2)),"Verify the empty SB Drawer for Reg user");
       }
        AssertFailAndContinue(shoppingBagDrawerActions.clickOnViewBagOnEmptyBag(shoppingBagPageActions), "Verify if user can able to click on the view bag link from the shopping bag drawer");
        headerMenuActions.click(headerMenuActions.shoppingBagIcon);
        AssertFailAndContinue(shoppingBagDrawerActions.clickContShoppingButtonAndVerify(shoppingBagPageActions), "Clicking on Continue Shopping Button redirects to the same page.");
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION,SMOKE,PRODUCTION, INTUATSTG})
    public void add_RemoveProdAndVerifyCartUpdated(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("As a guest U.S Store, when user removes the products from bag, verify that the product notification of all quantities of all products available in the bag is updated within the minicart displayed in the header");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        String moreItemsToAdd = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String moreQtyToAdd = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> moreSearchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(moreItemsToAdd, moreQtyToAdd);
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        int totalQtyInBag = Integer.parseInt(headerMenuActions.getQtyInBag());
        int moreQty = Integer.parseInt(moreQtyToAdd);
        headerMenuActions.click(headerMenuActions.shoppingBagIcon);
        int viewCount1 = Integer.parseInt(headerMenuActions.getViewBagCount());
        headerMenuActions.click(overlayHeaderActions.closeOnDrawer);
        addToBagBySearching(moreSearchKeywordAndQty);
        int totalQtyInBagAfterAddingMoreProd = Integer.parseInt(headerMenuActions.getQtyInBag());
        AssertFailAndContinue(totalQtyInBagAfterAddingMoreProd == totalQtyInBag + moreQty, "Total Qty in Bag got updated after adding  " + totalQtyInBagAfterAddingMoreProd + " more prod to bag.  The total qty in bag before adding the prod and more product to add " + (totalQtyInBag + moreQty));

        headerMenuActions.click(headerMenuActions.shoppingBagIcon);
        shoppingBagDrawerActions.checkUPCInlineCArtDisplay();
        int totalCountOfItem = Integer.parseInt(headerMenuActions.getViewBagCount());
        AssertFailAndContinue(totalCountOfItem == viewCount1 + moreQty, "Check the total quantity of item after updating the qty");
        //AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Verify if guest user redirected to the shopping bag drawer with products");
        int totalQtyInDrawer = shoppingBagDrawerActions.sumOfTotalProdInDrawer();
        int qtyInMiniCart = Integer.parseInt(overlayHeaderActions.getQtyFromMiniCart());
        AssertFailAndContinue(totalQtyInDrawer == qtyInMiniCart, "mini cart is " + qtyInMiniCart + " reflecting the sum total of all quantities of all SKU's " + totalQtyInDrawer + " displayed within the inline shopping bag drawer.");
        AssertFailAndContinue(shoppingBagDrawerActions.removeProdAndVerifyMiniCartUpdated(overlayHeaderActions, 1), "Removed 1 product from bag and verified the mini cart got updated");
        AssertFailAndContinue(shoppingBagDrawerActions.editUpdateQty_SingleProduct("2"), "verified the qty of a product got updated");
        AssertFailAndContinue(shoppingBagDrawerActions.getProdPriceTotal() == shoppingBagDrawerActions.getSubTotalPrice(), "Verify if guest user able to see SubTotal Field above the Checkout Button");
        headerMenuActions.closeTheVisibleDrawer();
        if (user.equalsIgnoreCase("guest")) {
            shoppingBagDrawerActions.clickWLIconAsGuest(emailAddress, password);
        }
    }

    @Parameters(usersXml)
    @Test(groups = {GLOBALCOMPONENT, SMOKE, CAONLY,PROD_REGRESSION})
    public void getAirMiles_CA(@Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("verify the guest user can add the airmiles number and validate the same in all pages");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
        Map<String, String> sd = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> mc = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        String text= getDT2TestingCellValueBySheetRowAndColumn("ShoppingBag","AirmilesText","ActualText");
        footerActions.changeCountryAndLanguage("CA", "English");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }
//        headerMenuActions.click(headerMenuActions.globalEspot);

        addToBagBySearching(searchKeywordAndQty);
        shoppingBagDrawerActions.click(headerMenuActions.bagLink);
        shoppingBagDrawerActions.enterAirMilesNo("23456789098");
        headerMenuActions.staticWait(2000);
        shoppingBagDrawerActions.getAirMilesNo();
        AssertFailAndContinue(shoppingBagDrawerActions.validateAirmilesText(text),"Verify the Airmiles text displayed in the SB modal");
        shoppingBagDrawerActions.click(shoppingBagDrawerActions.closeOverlay);
        shoppingBagDrawerActions.validateAirmilesText(text);
        AssertFailAndContinue(shoppingBagPageActions.checkAirmilesNo(shoppingBagDrawerActions), "Check the airmiles number is same which entered in the shopping bag drawer");
        AssertFailAndContinue(shoppingBagPageActions.airmilesLandingPage(headerMenuActions), "Verify the Airmiles e-spot redirection");
        shoppingBagPageActions.navigateBack();
        if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions);
            loginPageActions.clickContinueAsGuestButton(shippingPageActions);
        }
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        }
        shippingPageActions.checkAirmilesNo();
        shippingPageActions.validateAirmilesText(text);
        AssertFailAndContinue(shippingPageActions.enterShipDetailsAndShipMethodByPosAndContinue_GuestCA(billingPageActions, sd.get("fName"), sd.get("lName"), sd.get("addressLine1"), sd.get("addressLine2"),
                sd.get("city"), sd.get("stateShortName"), sd.get("zip"), sd.get("phNum"), sm.get("shipMethodPos")), "entered shipping details and Navigates to Order Receipt page");
        billingPageActions.checkAirmilesNo();
        billingPageActions.validateAirmilesText(text);
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, mc.get("fName"), mc.get("lName"), mc.get("addressLine1"), mc.get("addressLine2"),
                mc.get("city"), mc.get("stateShortName"), mc.get("zip"), mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit")), "Fill all the fields in paymentPage");
        AssertFailAndContinue(reviewPageActions.checkAirmilesNo(),"Verify the Airmiles number");
       if(!env.equalsIgnoreCase("prod")) {
           reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
       }
    }

    @Parameters(storeXml)
    @Test(priority = 2, groups = {GLOBALCOMPONENT, REGRESSION,PROD_REGRESSION,REGISTEREDONLY})
    public void addingProdFromBagToWLPage(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if the user can move the product from shopping bag page to WL page");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);


        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        addToBagBySearching(searchKeywordAndQty);
/*        AssertFailAndContinue(homePageActions.clickRandomDepartment(departmentLandingPageActions),"Verify if guest user can redirected to the department landing page");
        AssertFailAndContinue(departmentLandingPageActions.selectRandomCategory(categoryDetailsPageAction),"Verify if guest user can redirect to the category landing page");
        AssertFailAndContinue(categoryDetailsPageAction.openRandomQuickView(quickViewModalActions),"Open QV overlay for any product in category landing page");
        AssertFailAndContinue(quickViewModalActions.selectAny_Color_Size_AddToBag(shoppingBagOverlayActions),"Verify if user displayed with the shopping bag overlay");
        AssertFailAndContinue(shoppingBagOverlayActions.closeOverlay(categoryDetailsPageAction),"Verify if the shopping bag overlay is closed and the user displayed with PDP page");*/

        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Verify if guest user redirected to the shopping bag drawer with products");
        ;
        AssertFailAndContinue(shoppingBagDrawerActions.clickWLIconAsReg(), "Click on WL icon from the bag drawer and verify the empty text message");
    }


    @Test(dataProvider = dataProviderName, priority = 1, groups = {GLOBALCOMPONENT, REGRESSION, SMOKE,PRODUCTION, INTUATSTG})
    public void validateShoppingBagRegUser(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify after add the product into the cart as a guest user can redirected to shopping bag drawer when he clicks on shopping bag icon from header");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name1", "Value"));
        List<String> couponCode = null;
        couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons","Value","couponCode"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        }

        if (store.equalsIgnoreCase("CA")) {
            if (user.equalsIgnoreCase("registered")) {
                clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
                AssertFailAndContinue(myAccountPageDrawerActions.mprCOuponDisplayCheck(), "Apply the coupon code from the my account drawer");
                headerMenuActions.closeTheVisibleDrawer();
            }
        }
        AssertFailAndContinue(homePageActions.clickRandomDepartment(departmentLandingPageActions), "Verify if guest user can redirected to the department landing page");
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(0)), "Verify if the registered user redirected to the " + deptName.get(1) + " department landing page");
        departmentLandingPageActions.click(departmentLandingPageActions.lnk_Girl_Bottom);
        AssertFailAndContinue(categoryDetailsPageAction.clickRandomProductByImage(productDetailsPageActions), "Verify if user displayed with the PDP page of the particular product");
        AssertFailAndContinue(productDetailsPageActions.selectAnySizeAndClickAddToBagInPDP(), "Verify if user displayed with the shopping bag overlay");
//        AssertFailAndContinue(shoppingBagOverlayActions.closeOverlayInPdp(productDetailsPageActions),"Verify if the shopping bag overlay is closed and the user displayed with PDP page");
        headerMenuActions.staticWait(4000);
        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Verify if guest user redirected to the shopping bag drawer with products");
        AssertFailAndContinue(shoppingBagDrawerActions.isSubTotalFldDisplayingAboveCheckOutButton(), "Verify if guest user able to see SubTotal Field above the Checkout Button");
        AssertFailAndContinue(shoppingBagDrawerActions.getProdPriceTotal() == shoppingBagDrawerActions.getSubTotalPrice(), "Verify if guest user able to see SubTotal Field above the Checkout Button");
        //     shoppingBagDrawerActions.verifyBagCountWithProdQuantity();
        AssertFailAndContinue(shoppingBagDrawerActions.validateBagIconWithProd(), "Verify the product details is getting displayed in the shopping bag drawer");
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                AssertFailAndContinue(shoppingBagDrawerActions.isDisplayed(shoppingBagDrawerActions.pointsEarnedInEspotReg), "PLCC Espot displaying as registered");
                AssertFailAndContinue(shoppingBagDrawerActions.getMPRESpotColorReg("#333333"), "MPR Espot color is in blue as a registered only if user uses PLCC card");
            } else if (user.equalsIgnoreCase("guest")) {
                AssertFailAndContinue(shoppingBagPageActions.isDisplayed(shoppingBagDrawerActions.pointsEarnedInEspotGuest), "PLCC Espot displaying as Guest");
                AssertFailAndContinue(shoppingBagDrawerActions.getMPRESpotColorGuest("#333333"), "MPR Espot color is in orange as a guest");
            }
        }
        AssertFailAndContinue(shoppingBagDrawerActions.removeItemfromDrawer(),"Verify that the user is able to remove the item");
    }

    @Parameters(storeXml)
    @Test(priority = 4, groups = {GLOBALCOMPONENT, REGRESSION,REGISTEREDONLY,PROD_REGRESSION})
    public void applyCouponInBagDrawer(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify the guest user can apply the coupon from the shopping bag drawer");
        String Add = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "loyaltyAccount1", "Email");
        String pwds = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "loyaltyAccount1", "Password");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        String prodUpc = excelReaderDT2.getExcelData("Search", "bopisInEligible").get("ProductUPC");
        List<String> couponCode = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "Value", "couponCode"));
        } else if (store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
        }
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> mc = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            mc = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            mc = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }

        clickLoginAndLoginAsRegUserAndCloseDrawer(Add, pwds);//"aug161@yopmail.com","P@ssw0rd");
        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
       if(store.equalsIgnoreCase("US")){
        myAccountPageActions.clickOnPersonalInfoEditButton();}
       AssertFailAndContinue(myAccountPageActions.removeAssociateID(),"Verify that Associate ID is getting removed properly");

        addToBagBySearching(searchKeywordAndQty);
/*
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, "00700953898517");
        AssertFailAndContinue(productCardViewActions.selectASizeAndAddToBag(), "Add the recommendation product in to the cart");
*/
        headerMenuActions.staticWait(3000);
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon,3);
        productDetailsPageActions.click(productDetailsPageActions.shoppingBagIcon);
        headerMenuActions.waitUntilElementDisplayed(overlayHeaderActions.closeOnDrawer);
       // Changed from editing particular size, since we are adding item randomly.
        AssertFailAndContinue(shoppingBagDrawerActions.editUpdateQty_Product(2, 1, 1,"1"), "Update the color, fit and quantity of the item");
        headerMenuActions.closeTheVisibleDrawer();
        headerMenuActions.clickOnTCPLogo();
        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
        AssertFailAndContinue(myAccountPageDrawerActions.applyPercentageCoupons(), "Apply the coupon code from the my account drawer");
        headerMenuActions.closeTheVisibleDrawer();
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.discountItemDisplay(),"Verify the discount item display");
        headerMenuActions.click(overlayHeaderActions.closeOnDrawer);
        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
        AssertFailAndContinue(myAccountPageDrawerActions.removeAppliedCoupon(), "Remove the coupon code");
        headerMenuActions.closeTheVisibleDrawer();
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, prodUpc);
        AssertFailAndContinue(productCardViewActions.selectASizeAndAddToBag(), "Add the recommendation product in to the cart");
        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(myAccountPageDrawerActions.applyMPRCoupons(), "Apply the MPR coupon code from the my account drawer");
            headerMenuActions.closeTheVisibleDrawer();
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
            AssertFailAndContinue(shoppingBagPageActions.validateTotalAfterAppliedCoupon(), "Verify the original total and estimated total when coupons applied");
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
            AssertFailAndContinue(billingPageActions.enterCVVForExpressAndClickNextReviewBtn(mc.get("securityCode"), reviewPageActions), "Entered CVV which is already saved in myaccount and Navigates to Review page");
//            AssertFailAndContinue(myAccountPageDrawerActions.removeAppliedCoupon(), "Remove the coupon code");
            //  AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
            billingPageActions.returnToBagPage(shoppingBagPageActions);
            headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
            AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
            AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
            myAccountPageActions.clickOnPersonalInfoEditButton();
            AssertFailAndContinue(myAccountPageActions.addAssociateID("310620",shipDetailUS.get("addressLine1"),shipDetailUS.get("city"), shipDetailUS.get("stateShortName")), "Enter the valid associate ID");
            headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
            AssertFailAndContinue(myAccountPageDrawerActions.applyMPRCoupons(), "Apply the MPR coupon code from the my account drawer");
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
            shippingPageActions.clickNextBillingButtonOnEditShipping(billingPageActions);
            billingPageActions.clickNextReviewButton(reviewPageActions, "123");
            if(!env.equalsIgnoreCase("prod")) {
                reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
                getAndVerifyOrderNumber("applyCouponInBagDrawer");
                orderConfirmationPageActions.validateOrderLedgerSectionGuestRegAppliedCoupon();
                headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
                myAccountPageDrawerActions.applyMPRCoupons();
                myAccountPageDrawerActions.mprCouponValue();
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
            headerMenuActions.rewardsLinkDispay();
        }
    }
    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION,CHECKOUT, PRODUCTION})
    public void checkSBmodalAndPaypal(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the SB modal displayed after adding the Item to bag");

        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailUS = null;
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "superSaver");
      if(env.equalsIgnoreCase("prod")){
          validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails","searchForFitProd","validUPCNumber");
      }
        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
           }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
            validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
           }

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress,password);
        }
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, validUPCNumber);
        AssertFailAndContinue(productDetailsPageActions.validateSBConfEcom(), "validate SB modal after adding Ship to item");
        headerMenuActions.staticWait(3000);
        String parentWindow = driver.getWindowHandle();
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.paypalButtonCheck(), "check PayPal button display for SB drawer");
        headerMenuActions.closeTheVisibleDrawer();
        if(!env.equalsIgnoreCase("prod")){

            AssertFailAndContinue(productDetailsPageActions.validateSBConfBopis(bopisOverlayActions, validZip), "Validate the SB confirmation for BOPIS");
            productDetailsPageActions.clickClose_ConfViewBag();
            headerMenuActions.staticWait(3000);
            headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
            shoppingBagDrawerActions.paypalButtonCheck();
            headerMenuActions.closeTheVisibleDrawer();

        headerMenuActions.staticWait(3000);
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "Tops");
        headerMenuActions.scrollToBottom();
        headerMenuActions.isDisplayed(headerMenuActions.loadingIcon);
        headerMenuActions.scrollToBottom();
        footerActions.clickBackToTop();
        categoryDetailsPageAction.loadingIcon();
        if (user.equalsIgnoreCase("registered")) {
            logoutTheSession();
        }
    }
        headerMenuActions.navigateToDepartment("Girl");
        departmentLandingPageActions.click(departmentLandingPageActions.lnk_Girl_Bottom);
        headerMenuActions.staticWait(3000);
        categoryDetailsPageAction.clickRandomProductByImage(productDetailsPageActions);
        String crumbBefore = headerMenuActions.getText(productDetailsPageActions.breadCrumbPDP);
        if(user.equalsIgnoreCase("guest")){
        headerMenuActions.click(headerMenuActions.loginLinkHeader);
        loginDrawerActions.userLoginPDP(emailAddress,password,overlayHeaderActions);}
        headerMenuActions.waitUntilElementDisplayed(productDetailsPageActions.breadCrumbPDP,10);
        String crumbAfter = headerMenuActions.getText(productDetailsPageActions.breadCrumbPDP);
        AssertFailAndContinue(crumbAfter.equalsIgnoreCase(crumbBefore),"Ensure the breadcrumb are equal");
        productDetailsPageActions.selectASize();
        productDetailsPageActions.clickAddToBag();
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
//        shoppingBagDrawerActions.click(shoppingBagDrawerActions.continueShoppingLnk);
        headerMenuActions.closeTheVisibleDrawer();
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions,reviewPageActions);
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsWithoutCHeckbox_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        billingPageActions.waitUntilElementDisplayed(billingPageActions.paypalRadioButton,3);

    }

}
