package tests.mobileDT.billing;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

public class MBillingPageExpressCheckout extends MobileBaseTest{

    WebDriver mobileDriver;
    String email = UiBaseMobile.randomEmail();
    String password = "";
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
//        try {
//            if (user.equalsIgnoreCase("registered")) {
//                if (store.equalsIgnoreCase("CA")) {
//                    createAccFlag = createAccountApi.createAccount(createUserDetailsCA.get("FirstName"), createUserDetailsCA.get("LastName"), createUserDetailsCA.get("Password"), createUserDetailsCA.get("ZipCode"), createUserDetailsCA.get("PhoneNumber"), email,store);
//                }else{
//                    createAccFlag = createAccountApi.createAccount(createUserDetailsUS.get("FirstName"), createUserDetailsUS.get("LastName"), createUserDetailsUS.get("Password"), createUserDetailsUS.get("ZipCode"), createUserDetailsUS.get("PhoneNumber"), email,store);
//                    System.out.println(createAccFlag);
//                }
//            }
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered") && !createAccFlag) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered") && !createAccFlag) {
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
    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY})
    public void verifyDefaultPaymentMethodOnBillingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        Map<String, String> paymentMethod1 = null;
        Map<String, String> paymentMethod2 = null;
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify default payment method is prepopulated when user navigates back to billing page.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> billingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");

        paymentMethod1 = dt2ExcelReader.getExcelData("PaymentDetails", "MasterCard");
        paymentMethod2 = dt2ExcelReader.getExcelData("PaymentDetails", "Visa");
        if (env.equals("prod")) {
            paymentMethod1 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD_2");
            paymentMethod2 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addNewShippingAddress(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"),
                    billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("country"), billingAdd.get("phNum"),panCakePageActions);
        }
        if (store.equalsIgnoreCase("CA")) {
            Map<String, String> caShipping = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
            mmyAccountPageActions.addNewShippingAddress(caShipping.get("fName"), caShipping.get("lName"), caShipping.get("addressLine1"), caShipping.get("city"),
                    caShipping.get("stateShortName"), caShipping.get("zip"), caShipping.get("country"), caShipping.get("phNum"),panCakePageActions);
        }
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        AddInfoStep("Add master card in my account");
        mmyAccountPageActions.addACreditCard(paymentMethod1.get("cardNumber"),
                paymentMethod1.get("expMonthValueUnit"),
                paymentMethod1.get("expYear"), true, billingAdd.get("fName"),
                billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"),panCakePageActions);
        //Now after adding one, one billing address will already appear
        AddInfoStep("Add Visa Card in my account");
        mmyAccountPageActions.addACreditCard(paymentMethod2.get("cardNumber"),
                paymentMethod2.get("expMonthValueUnit"),
                paymentMethod2.get("expYear"));


        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress( mreviewPageActions),"Verify user redirected to Express checkout screen");
        AssertFailAndContinue(mreviewPageActions.clickReturnToBillingLink(mbillingPageActions),"Return to billing page");

        //DT-36517
        if (env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.cardNo(paymentMethod1.get("Last4"))), "Verify Last 4 digits of default card number displayed in billing page");
            AddInfoStep("Default payment method is shown on billing page:" + paymentMethod1.get("Last4"));
        } else {
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.cardNo(paymentMethod1.get("Last4"))), "Verify Last 4 digits of default card number displayed in billing page");
            AddInfoStep("Default payment method is shown on billing page:" + paymentMethod1.get("Last4"));
        }
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {REGISTEREDONLY,GIFTCARD,RECAPTCHA})
    public void editBillingOnECPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha/RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify if user clicks on Edit on expresscheckout, "
                + "user gets redirected to Billing Page" + "\n" + "Verify billing summary on order review page");

        makeExpressCheckoutUser(store, env);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        Map<String, String> giftCard=null;

        if (store.equalsIgnoreCase("US"))
            giftCard = dt2ExcelReader.getExcelData("GiftCard", "usgc3");
        else if (store.equalsIgnoreCase("CA"))
            giftCard = dt2ExcelReader.getExcelData("GiftCard", "cagc3");

        mmyAccountPageActions.addAGiftCard(giftCard.get("Card"), giftCard.get("Pin"));

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Verify click on shopping bag");
        //DT-38016
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.couponText), "Verify coupon as line item is not shown when coupon is not applied.");
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.couponCodeApplied), "Verify coupon as line item is not shown when coupon is not applied.");

        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);

        AssertFailAndContinue(mreviewPageActions.isCvvFieldIsDisplayed(), "Verify Cvv field is displayed in review page for Express Checkout");
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");

        //DT-35356
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(), "Verify Billing Address section in Review Page");
        AssertFailAndContinue(mreviewPageActions.verifyPaymentMethodSection(), "Verify Payment Method section in Review Page");
        AssertFailAndContinue(mreviewPageActions.verifyGiftCardAvailableSection(), "Verify that available gift card section is shown");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.applyGCButton), "Verify apply gift card button is shown");
        AssertFailAndContinue(mreviewPageActions.isNewGiftCardBtnDisplayed(), "Verify New Gift Card button is displayed on order Review Page");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.editBillingLink), "Verify Edit billing link is displayed.");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.tooltipCvv), "Verify tooltip above security field is displayed.");
        //DT-35370
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.itemTotalText), "Verify item total is displayed in order ledger.");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.shippingText), "Verify shipping  is displayed in order summary.");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.estimatedTax), "Verify estimated tax  is displayed in order summary");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.estimatedTotalPrice), "Verify estimated total   is displayed in order summary");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.submitOrderButton), "Verify submit order button is displayed");

        //DT-36663
        AssertFailAndContinue(mreviewPageActions.getCardImgAltText() != null, "Verify Card image is displayed.");
        AssertFailAndContinue(mreviewPageActions.getLast4DigitCC() != null, "Verify last 4 digits of card are displayed.");

        String giftCardLast4Digits = null;
        if (store.equals("US"))
            giftCardLast4Digits = getmobileDT2CellValueBySheetRowAndColumn("GiftCard", "usgc3", "Last4Digits");
        if (store.equals("CA"))
            giftCardLast4Digits = getmobileDT2CellValueBySheetRowAndColumn("GiftCard", "cagc3", "Last4Digits");
        //DT-35358
        String giftCardLast4DigitsDisplayed = mreviewPageActions.getLast4DigitsGiftCard();
        AssertFailAndContinue(giftCardLast4DigitsDisplayed.contains(giftCardLast4Digits), "Verify my account gift card is getting displayed.");

        mreviewPageActions.clickApplyButton();
        AssertFailAndContinue(mreviewPageActions.verifyGiftCardAppliedSection(), "Verify applied gift card section is displayed");
        //DT-36661
        String giftCardLast4DigitsAppliedSec = mreviewPageActions.getLast4DigitsGiftCardAppliedSection();
        AssertFailAndContinue(giftCardLast4DigitsAppliedSec.contains("Ending in"), "Verify Ending in text is shown");
        AssertFailAndContinue(giftCardLast4DigitsAppliedSec.contains(giftCardLast4Digits), "Verify last 4 digits are shown in applied section");
        AssertFailAndContinue(giftCardLast4DigitsAppliedSec.contains("Remaining Balance"), "Verify Remaining balance is shown");

        //DT-35440
        AssertFailAndContinue(mreviewPageActions.clickEditBillingLink(mbillingPageActions), "Verify user is redirected to Billing Page");
        //DT-35354
        AssertFailAndContinue(mbillingPageActions.verifyExpressCheckoutTitle(), "Verify Title remains as EXPRESS CHECK OUT");
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"));
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title remains as EXPRESS CHECK OUT");
    }


    @AfterMethod(alwaysRun = true)
    public void clearCookies () {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver () {
        mobileDriver.quit();
    }

}
