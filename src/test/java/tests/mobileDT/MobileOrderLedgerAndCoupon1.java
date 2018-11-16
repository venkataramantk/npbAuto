package tests.mobileDT;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.ArrayList;
import java.util.Map;

public class MobileOrderLedgerAndCoupon1 extends MobileBaseTest {
    WebDriver mobileDriver;
    String productName0 = "";
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email = UiBaseMobile.randomEmail();
    String password = "";
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);

        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }
    
    @Test(priority = 0, dataProvider = dataProviderName, groups = {SHOPPINGBAG, COUPONS,PROD_REGRESSION})
    public void applyMerchFreeShipCoupon(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +  "Verify user applying   merchandise from Shopping Bag and free shipping from shipping Page, is successfully apply the coupon and Order Ledger updated with Coupons and Estimate Total");
        Map<String, String> usBillingAdd = null;
        Map<String, String> caShippingAdd = null;
         //TR-3941-TR-3942
        if (store.equalsIgnoreCase("US")) {
            usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        String searchTerm = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchTerm, qty);
        String merchCoupon = null;
        if (store.equalsIgnoreCase("US")) {
            merchCoupon = getmobileDT2CellValueBySheetRowAndColumn("Coupons", "Flat5Off", "couponCode");
        } else if (store.equalsIgnoreCase("CA")) {
            merchCoupon = getmobileDT2CellValueBySheetRowAndColumn("Coupons", "validCode", "coupon5CodeCA");

        }
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email ,password);
        }
        addToBagBySearching(searchKeywordAndQty);
        String productName0 = mheaderMenuActions.getText(mshippingPageActions.produtnames.get(0));
       mshoppingBagPageActions.changeQuantity(productName0, "3");
       mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
       mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.nextBillingButton);
      
       if (store.equalsIgnoreCase("US")) {
           mshippingPageActions.enterShippingDetailsAndShipType(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email);
       }
       if (store.equalsIgnoreCase("CA")) {
           mshippingPageActions.enterShippingDetailsAndShipType(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email);
       }
       
       Map<String, String> shipMethodDetailsExpress = dt2ExcelReader.getExcelData("ShippingMethodCodes", "express");
       mshippingPageActions.selectShippingMethodFromRadioButton(mshippingPageActions.getAvailableShippingMethods().get(1));
       AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.shippingTot).equals(shipMethodDetailsExpress.get("shipPrice")), "Verify shipping price is shown as $15 for express shipping selected.");
       mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
       Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
       mshoppingBagPageActions.applyCouponByText("AUTOFREESHIP");
       Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
       AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
       AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals("Free"), "Verify shipping price is removed on applying AUTOFREESHIP coupon");
       AssertFailAndContinue(mshoppingBagPageActions.clickMerchandiseApplyCoupon("$1", merchCoupon), "Merchandise $5 coupon has been applied");
       AssertFailAndContinue(mshoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Estimated total after applying merchandise and free shipping coupons");
	}
    
    @Test(priority = 1, dataProvider = dataProviderName, groups= {COUPONS, SHOPPINGBAG} )
    public  void verifyApplyAmountOffCoupons(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to see " +
                "apply amount off type of coupon like DOLLAROFFDENIM and the coupon amount gets deducted from each product.(DT-40411)+"
                + "DT-40413: Verify user is able to remove DOLLAROFFDENIM coupon.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        
        AddInfoStep("Add two denim products to the bag");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Value");
        qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        mheaderMenuActions.clickShoppingBagIcon();
        
        Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        ArrayList<Double> initialPriceList = mshoppingBagPageActions.getItemPriceList();
        mshoppingBagPageActions.applyCouponByText("DOLLAROFFDENIM");
        ArrayList<Double> finalPriceList = mshoppingBagPageActions.getItemPriceList();
        Double amountOff = (double) 1;
        //DT-40411
        AssertFailAndContinue(mshoppingBagPageActions.verifyAmountOffCoupon(initialPriceList, finalPriceList, amountOff), "Verify coupon amount gets deducted from each of product price.");
        Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponText), "Verify applied coupon section is shown.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shopping bag");
        
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shipping page");
        Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        if(store.equalsIgnoreCase("CA"))
        	usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
        
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        Map<String, String> mc = null;
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on review page");
        //DT-40413
        mreviewPageActions.removeCoupon();
        AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on review page");
        mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on billing page");
        mbillingPageActions.clickReturnToShippingPage();
        AssertFailAndContinue(!mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is not  shown in Order Ledger on shipping page");
        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shopping bag page");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups= {COUPONS, SHOPPINGBAG} )
    public  void verifyEnteredAmountOffCoupons(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to" +
                "enter amount off type of coupon like DOLLAROFFDENIM and the coupon amount gets deducted from each product and then user can remove the coupon: DT-40414");
        String mCoupon = null;
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
       if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mshoppingBagPageActions.validateEmptyShoppingCart();
       }
        
       	AddInfoStep("Add two denim products to the bag");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Value");
        qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        mheaderMenuActions.clickShoppingBagIcon();
        
        Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        ArrayList<Double> initialPriceList = mshoppingBagPageActions.getItemPriceList();
        if (store.equalsIgnoreCase("US")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar", "couponCode");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar", "couponCodeCA");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA");
            }
        }

        mshoppingBagPageActions.applyCouponCode(mCoupon);
        ArrayList<Double> finalPriceList = mshoppingBagPageActions.getItemPriceList();
        Double amountOff = (double) 1;
        //DT-40411
        AssertFailAndContinue(mshoppingBagPageActions.verifyAmountOffCoupon(initialPriceList, finalPriceList, amountOff), "Verify coupon amount gets deducted from each of product price.");
        Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponText), "Verify applied coupon section is shown.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shopping bag");
        
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shipping page");
        Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        if(store.equalsIgnoreCase("CA"))
        	usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
        
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        Map<String, String> mc = null;
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on review page");
       
		mreviewPageActions.removeCoupon();
		AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on review page");
		mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
		AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on billing page");
		mbillingPageActions.clickReturnToShippingPage();
		AssertFailAndContinue(!mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is not  shown in Order Ledger on shipping page");
		mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
		AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shopping bag page");
    }
    
    @Test(priority = 3, dataProvider = dataProviderName, groups= {COUPONS, SHOPPINGBAG} )
    public void verifyDenimCpnNotAppliesOnNonDenim(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("RichaK");
		setRequirementCoverage("As a " + user + " user in " + store + " store, DT-40410: Verify if the user is able to see " +
		    "error message when he tries to apply DOLLAROFFDENIM coupon on non -denim product.");
		
		if (user.equalsIgnoreCase("registered")) {
		panCakePageActions.navigateToMenu("LOGIN");
		    mloginPageActions.loginAsRegisteredUserFromLoginForm(email ,password);
		}
		
		if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
		    mshoppingBagPageActions.validateEmptyShoppingCart();
		}
        
		AddInfoStep("Add non denim product to the bag");
		String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber2", "Value");
		String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber2", "Qty");
    	Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
    	addToBagFromPDP(searchKeywordAndQty);
    	
    	mheaderMenuActions.clickShoppingBagIcon();
	    
    	String invalidCouponErrMsg = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "specialChar", "validErrorMessage");
	    String errorMessage = mshoppingBagPageActions.applyInvalidCouponByText("DOLLAROFFDENIM");
	    AssertFailAndContinue(errorMessage.contains(invalidCouponErrMsg),"DT-40410 :: Verify applying DOLLAROFFDENIM coupon to non-denim product throws error");
	   
	    if (user.equalsIgnoreCase("guest")) {
	    	AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
		}
		if (user.equalsIgnoreCase("registered")) {
		    AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
		}
		mshippingPageActions.expandOrderSummary();
		AssertFailAndContinue(!mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shipping page");
		Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
		if(store.equalsIgnoreCase("CA"))
			usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
		
		AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
		Map<String, String> mc = null;
		if (store.equalsIgnoreCase("US")) {
		    mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
		    if (env.equalsIgnoreCase("prod")) {
		        mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
		    }
		} else if (store.equalsIgnoreCase("CA")) {
		    mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
		    if (env.equalsIgnoreCase("prod")) {
		        mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
		    }
	      }
        
		mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary(); 
		
        AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on review page");
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shopping bag page");
        
    }
   
    @Test(priority = 4, dataProvider = dataProviderName, groups= {COUPONS, SHOPPINGBAG} )
    public  void verifyEnteredDenimCpnNotAppliesOnNonDenim(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, DT-40412: Verify if the user is able to see " +
    		    "error message when he tries to apply DOLLAROFFDENIM coupon on non -denim product by entering coupon code.");
        
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email ,password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        
        AddInfoStep("Add non denim products to the bag");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber2", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        mheaderMenuActions.clickShoppingBagIcon();

        String invalidCouponErrMsg = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "specialChar", "validErrorMessage");
        String errorMessage = mshoppingBagPageActions.applyInvalidCouponCodeByEntering("DOLLAROFFDENIM");
        AssertFailAndContinue(errorMessage.contains(invalidCouponErrMsg),"DT-40412 :: Verify applying invalid coupon by entering throws error message");
       
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(!mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shipping page");
        Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        if(store.equalsIgnoreCase("CA"))
        	usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
        
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        Map<String, String> mc = null;
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary(); 
		
        AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on review page");
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shopping bag page");
    }
    
    @Test(priority = 5, dataProvider =dataProviderName, groups= {COUPONS, SHOPPINGBAG} )
    public  void verifyDollar10OffCoupons(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to" +
                "apply $10 off your entire order coupon on orders which amounts more than dollar 10 and amount gets deducted +"
                + "DT-40437,DT-40441");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email ,password);
        }
        
       if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        
        AddInfoStep("Add products which amount to more than dollar 10 to the bag");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Value");
        qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        mheaderMenuActions.clickShoppingBagIcon();
        
        Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        
        String couponCode = null;
        if (store.equalsIgnoreCase("US")){
        	couponCode=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar10Select", "couponCode");
        }else if (store.equalsIgnoreCase("CA")) {
        	 couponCode=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar10Select", "couponCodeCA");
        }
        
        mshoppingBagPageActions.applyCouponByText(couponCode);
        
        Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mshoppingBagPageActions.getEstimateTotal());
        Double amountOff = (double) 10;
        Double difference =(double) (estTotalOnOLBeforeApplyingCoupon-estTotalOnOLAfterApplyingCoupon);
        AssertFailAndContinue(difference.equals(amountOff), "Verify coupon amount gets deducted.");
        AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponText), "Verify applied coupon section is shown.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shopping bag");
        
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on shipping page");
        Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        if(store.equalsIgnoreCase("CA"))
        	usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
        
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        Map<String, String> mc = null;
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on billing page");
        mbillingPageActions.removeCoupon();
    }
    
    @Test(priority = 6, dataProvider = dataProviderName, groups= {COUPONS, SHOPPINGBAG} )
    public void verifyDollar10OFFNotApplies(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("RichaK");
		setRequirementCoverage("As a " + user + " user in " + store + " store, DT-40438;DT-40442: Verify if the user is able to see " +
		    "error message when he tries to apply $10 off your entire order coupon on products which amount less than dollar 10");
		
		if (user.equalsIgnoreCase("registered")) {
		panCakePageActions.navigateToMenu("LOGIN");
		    mloginPageActions.loginAsRegisteredUserFromLoginForm(email ,password);
		}
		
		if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
		    mshoppingBagPageActions.validateEmptyShoppingCart();
		}
        
		AddInfoStep("Add products which costs less than $10 to the bag");
		String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "MultiColorProductWithDiffPrices", "Value");
		String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "MultiColorProductWithDiffPrices", "Qty");
    	Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
    	addToBagFromPDP(searchKeywordAndQty);
    	
    	mheaderMenuActions.clickShoppingBagIcon();
	    
    	String invalidCouponErrMsg = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "specialChar", "validErrorMessage");
    	String couponCode =null;
    	if (store.equalsIgnoreCase("US")){
    		couponCode=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar10Select", "couponCode");
    	}else if(store.equalsIgnoreCase("CA")){
    		couponCode=getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCodeDollar10Select", "couponCodeCA");
	    }
	   
    	String errorMessage=mshoppingBagPageActions.applyInvalidCouponByText(couponCode);
    	AssertFailAndContinue(errorMessage.contains(invalidCouponErrMsg),"DT-40438 :: Verify applying $10 off your entire order coupon to  product less than amount $10 throws error");
	   
	    if (user.equalsIgnoreCase("guest")) {
	    	AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
		}
		if (user.equalsIgnoreCase("registered")) {
		    AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
		}
		mshippingPageActions.expandOrderSummary();
		AssertFailAndContinue(!mshippingPageActions.isDisplayed(mshippingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shipping page");
		Map<String, String> usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
		if(store.equalsIgnoreCase("CA"))
			usShippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
		
		AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingDetails.get("fName"), usShippingDetails.get("lName"), usShippingDetails.get("addressLine1"), usShippingDetails.get("addressLine2"), usShippingDetails.get("city"), usShippingDetails.get("stateShortName"), usShippingDetails.get("zip"), usShippingDetails.get("phNum"), usShippingDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
		Map<String, String> mc = null;
		if (store.equalsIgnoreCase("US")) {
		    mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
		    if (env.equalsIgnoreCase("prod")) {
		        mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
		    }
		} else if (store.equalsIgnoreCase("CA")) {
		    mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
		    if (env.equalsIgnoreCase("prod")) {
		        mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
		    }
	      }
        
		mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary(); 
        AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on review page");
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.coupnField), "Verify coupon code field is not shown in Order Ledger on shopping bag page");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups= {COUPONS, PAYPAL} )
    public  void verifyApplyingPayPalCoupon(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to apply " +
                "paypal coupon by navigating back to billing page and the coupon is shown in the order ledger. (DT-36708)");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);

        mheaderMenuActions.clickShoppingBagIcon();
        String username=null;
        String pwd=null;
        AssertFailAndContinue(mshoppingBagPageActions.clickOnPayPalIcon(mpayPalPageActions), "Click PayPal button in Shopping bag page and verify PayPal modal");
        if (store.equalsIgnoreCase("US")) {
            username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPaypal", "UserName");
            pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPaypal", "Password");
        }
        if (store.equalsIgnoreCase("CA")) {
            username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "UserName");
            pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "CA_PayPal", "Password");
        }

        mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions);
        mreviewPageActions.clickOnBillingAccordion(mbillingPageActions);
        mbillingPageActions.expandOrderSummary();

        Float estTotalOnOLBeforeApplyingCoupon= Float.parseFloat(mbillingPageActions.getEstimateTotal());
        //DT-36708
        mbillingPageActions.applyCouponByText("Paypal", mobileDriver);
        Float estTotalOnOLAfterApplyingCoupon= Float.parseFloat(mbillingPageActions.getEstimateTotal());
        AssertFailAndContinue(estTotalOnOLAfterApplyingCoupon<estTotalOnOLBeforeApplyingCoupon, "Verify estimated total gets updated after applying coupon.");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.appliedCouponText), "Verify applied coupon section is shown.");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on billing page");
        mbillingPageActions.selectPayPalAsPaymentWhenAlreadyLoggedIn(mpayPalPageActions, mreviewPageActions);
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.coupnField), "Verify coupon code field is shown in Order Ledger on review page");
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        mshoppingBagPageActions.removeCouponFromShoppingBag();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }
}