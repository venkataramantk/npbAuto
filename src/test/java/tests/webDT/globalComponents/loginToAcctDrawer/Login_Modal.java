//package tests.webDT.globalComponents.loginToAcctDrawer;
//
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.*;
//import tests.web.initializer.BaseTest;
//import ui.support.Config;
//import ui.support.EnvironmentConfig;
//import ui.utils.ExcelReader;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Venkat on 10/05/2016.
// */
////User Story: DT-650
////@Listeners(MethodListener.class)
//public class Login_Modal extends BaseTest {
//
//    WebDriver driver;
//    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
//    String emailAddressReg;
//    private String password;
//
//    @Parameters({storeXml, usersXml})
//    @BeforeClass(alwaysRun = true)
//    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
//        initializeDriver();
//
//        driver = getDriver();
//        initializePages(driver);
//        driver.get(EnvironmentConfig.getApplicationUrl());
//
//        if (store.equalsIgnoreCase("US")) {
//            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
//            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
//
//        } else if (store.equalsIgnoreCase("CA")) {
//            headerMenuActions.deleteAllCookies();
//            footerActions.changeCountryAndLanguage("CA", "English");
//            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
//            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
//        }
//        headerMenuActions.deleteAllCookies();
//    }
//
//    @Parameters(storeXml)
//    @BeforeMethod(alwaysRun = true)
//    public void openBrowser(@Optional("US") String store) throws Exception {
//        driver.get(EnvironmentConfig.getApplicationUrl());
//        if (store.equalsIgnoreCase("US")) {
//            headerMenuActions.addStateCookie("NJ");
//        } else if (store.equalsIgnoreCase("CA")) {
//            footerActions.changeCountryAndLanguage("CA", "English");
//        }
//
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void clearCookies() {
//        headerMenuActions.deleteAllCookies();
//    }
//
//    //script 001
//    @Parameters("store")
//    @Test(priority = 8, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PRODUCTION})
//    public void loginDrawerFieldValidation(@Optional("CA") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify if the Guest user in the US Store is able to validate the contents of the Login drawer. ");
//
//        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Click on Login link from the header and opened the login drawer overlay");
//        if (store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(loginDrawerActions.validateLoginDrawer(), "Validating the fields present in wish list login page");
//        }
//        if (store.equalsIgnoreCase("CA")) {
//            AssertFailAndContinue(loginDrawerActions.validateLoginDrawerCA(), "Validating the fields present in wish list login page");
//        }
//        AssertFailAndContinue(loginDrawerActions.showHideLinkPassword("P@ssw0rd"), "Validated the actions of the Show/Hide link");
//
//        if (store.equalsIgnoreCase("US")) {
//            List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "messageOnForm", "LoginDrawerContent"));
//            AssertFailAndContinue(loginDrawerActions.validateLoginOverlayContent(txtContent.get(0), txtContent.get(1), txtContent.get(2), txtContent.get(3)), "Validating the content present in the Login page");
//        }
//        if (store.equalsIgnoreCase("CA")) {
//            List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "messageOnForm", "LoginDrawerContentCA"));
//            AssertFailAndContinue(loginDrawerActions.validateLoginOverlayContentCA(txtContent.get(0), txtContent.get(1)), "Validating the content present in the Login page");
//        }
//        AssertFailAndContinue(overlayHeaderActions.clickCreateAccountOnOverlayHeader(createAccountActions), "Clicking On Create Account link on Login Drawer landed to the CreateAccount Drawer");
//        AssertFailAndContinue(overlayHeaderActions.clickWishListOnOverlayHeader(), "Clicking On wishlist header from login drawer and check if user displayed with wishlist login drawer");
//        AssertFailAndContinue(overlayHeaderActions.clickLoginOnOverlayHeader(), "Clicked on Login link on Wishlist login overlay header landed on the login drawer");
//
//    }
//
//    //script 002
//    @Parameters(storeXml)
//    @Test(priority = 2, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void wishListDrawerLoginValidation(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify if the Guest user in the US Store is able to validate the contents of the Login drawer, when Wish List link is clicked on the Header.");
//        List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "messageOnForm", "wlLoginDrawerContent"));
//        List<String> createAcctTxt = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "messageOnForm", "LoginDrawerContent"));
//
//        AssertFailAndContinue(headerMenuActions.clickWishListAsGuest(wishListDrawerActions), "Click on wish list link from the header");
//        AssertFailAndContinue(loginDrawerActions.validateBtnLoginEnableDisable(emailAddressReg, password), "Validated if the Login button gets enabled only when credentials are entered.");
//        if(store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(wishListDrawerActions.validateWishListLoginOverlayContent(txtContent.get(0), txtContent.get(1), txtContent.get(2), createAcctTxt.get(3)), "Validating the content present in the wishlist page");
//        }
//       else{
//            AssertFailAndContinue(wishListDrawerActions.validateWishListLoginOverlayContentCA(txtContent.get(0), txtContent.get(1)), "Validating the content present in the wishlist page");
//        }
//        AssertFailAndContinue(loginDrawerActions.showHideLinkPassword("P@ssw0rd"), "Validated the actions of the Show/Hide link");
//        AssertFailAndContinue(loginPageActions.clickForgotPassword(forgotYourPasswordPageActions), "Clicked on the Forgot Password link and landed on the Forgot Password page.");
//        AssertFailAndContinue(forgotYourPasswordPageActions.returnToLoginLink(loginDrawerActions), "Clicked on the Back to Login Link and landed on the Login Drawer.");
//        if (store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(loginPageActions.clickHereLink(forgotYourPasswordPageActions), "Clicked on the Click here link above email address field and landed on the Forgot Password page.");
//            AssertFailAndContinue(forgotYourPasswordPageActions.returnToLoginLink(loginDrawerActions), "Clicked on the Back to Login Link and landed on the Login Drawer.");
//        }
//        AssertFailAndContinue(loginDrawerActions.clickCreateAccButton(createAccountActions), "Clicked on the Create Account Button and land on the Create Account Drawer.");
//        AssertFailAndContinue(headerMenuActions.clickWishListOverlayAsGuest(wishListDrawerActions), "Click on wish list link from the header");
//        AssertFailAndContinue(wishListDrawerActions.successfulLogin(emailAddressReg, password, favoritePageActions), "loggined with valid credentials and navigated to wishlist page");
//
//    }
//
//    //script 003
//    @Parameters(storeXml)
//    @Test(priority =11, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void errMsgValidateLogin(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify if the Guest user in the US Store is able to validate the error messages in the login drawer.");
//        Map<String, String> acct = excelReaderDT2.getExcelData("Login", "Credentials");
//        List<String> loginErrMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErrMsgTab", "expectedContent"));
//        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginSplCharErr", "expectedContent"));
//        List<String> loginErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErr", "expectedContent"));
//
//        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Click on Login link from the header");
//        AssertFailAndContinue(loginDrawerActions.validateErrorMessagesTab(loginErrMsgTab.get(0), loginErrMsgTab.get(1)), "Validate Error message when Tab the Field");
//        AssertFailAndContinue(loginDrawerActions.validateErrMsgSpecialChar(errMsgSplChar.get(0)), "Validate Error message when given special characters in email field and leaving password field empty");
//        AssertFailAndContinue(loginDrawerActions.validErrMsgValidUserName(emailAddressReg, loginErrMsgTab.get(1)), "Enter valid username and empty password field");
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(acct.get("invalidEmail"), acct.get("invalidPassword"), loginErr.get(0)), "Entered invalid Email Address and clicked on the Login button to validate the error message.");
//        //       AssertFailAndContinue(loginDrawerActions.accountLocksWith_5_InvalidAttempts(emailAddressReg, acct.get("invalidPassword"), "Sorry, you've entered an incorrect password multiple times. For your security, the account has been locked.Please click here to reset your password."), "Entered the invalid password to lock the account. Validated the error message displayed.");
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(emailAddressReg,acct.get("invalidPassword"), loginErr.get(0)),"ENter invalid credentials");
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(emailAddressReg,acct.get("invalidPassword"), loginErr.get(0)),"ENter invalid credentials");
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(emailAddressReg,acct.get("invalidPassword"), loginErr.get(0)),"ENter invalid credentials");
//        AssertFailAndContinue(loginDrawerActions.validateRecaptcha(),"Verify that the re-captcha is displayed to the user");
//    }
//
//    //script 004
//    @Parameters(storeXml)
//    @Test(priority = 12, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void errMsgValidateWListLogin(@Optional("CA") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify if the Guest user in the US Store is able to validate the error messages in the wish list login drawer.");
//        Map<String, String> acct = excelReaderDT2.getExcelData("Login", "Credentials");
//        List<String> loginErrMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErrMsgTab", "expectedContent"));
//        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginSplCharErr", "expectedContent"));
//        List<String> loginErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErr", "expectedContent"));
//
//        AssertFailAndContinue(headerMenuActions.clickWishListAsGuest(wishListDrawerActions), "Click on Login link from the header");
//        AssertFailAndContinue(loginDrawerActions.validateErrorMessagesTab(loginErrMsgTab.get(0), loginErrMsgTab.get(1)), "Validate Error message when Tab the Field");
//        AssertFailAndContinue(loginDrawerActions.validateErrMsgSpecialChar(errMsgSplChar.get(0)), "Validate Error message when given special characters in email field and leaving password field empty");
//        AssertFailAndContinue(loginDrawerActions.validErrMsgValidUserName("test78d3122825542@yopmail", loginErrMsgTab.get(1)), "Enter valid username and empty password field");
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(acct.get("invalidEmail"), acct.get("invalidPassword"), loginErr.get(0)), "Entered invalid Email Address and clicked on the Login button to validate the error message.");
//       // AssertFailAndContinue(loginDrawerActions.accountLocksWith_5_InvalidAttempts(emailAddressReg, acct.get("invalidPassword"), "Sorry, you've entered an incorrect password multiple times. For your security, the account has been locked.Please click here to reset your password."), "Entered the invalid password to lock the account. Validated the error message displayed.");
//        AssertFail(loginDrawerActions.isDisplayed(loginDrawerActions.forgotPasswordLink), "Verifying for Forgot password link");
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(emailAddressReg,acct.get("invalidPassword"), loginErr.get(1)),"ENter invalid credentials");
//        AssertFailAndContinue(loginDrawerActions.validateRecaptcha(),"Verify that the re-captcha is displayed to the user");
//
//    }
//
//    @Parameters(storeXml)
//    @Test(priority = 13, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void enterValidPwdAfterAccountLock(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Shyamala");
//        setRequirementCoverage("As a guest/registered/remembered user in U.S/C.A/INT store, In the login modal enter the email address that was used for account lock , and enter unmatching password,and click og \"login\"CTA, Verify user is displayed with \"incorrect username or password\" error message, and also verify when the user enter matching password user is able to login successfully.");
//        Map<String, String> acct = excelReaderDT2.getExcelData("Login", "PasswordExp");
//        List<String> loginErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "PasswordExp", "expectedContent"));
//
//        headerMenuActions.clickLoginLnk(loginDrawerActions);
//        //       AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(emailAddressReg, acct.get("invalidPassword"), loginErr.get(0)), "Entered invalid Email Address and clicked on the Login button to validate the error message.");
//        AssertFailAndContinue(loginDrawerActions.accountLockedErrorMsgLogin(emailAddressReg, password), "loggined with valid credentials and navigated to wishlist page");
//        //checking by login the Expire Password account
//        AssertFailAndContinue(loginDrawerActions.loginWithInvalidCred(acct.get("invalidEmail"),acct.get("Password"),loginErr.get(0)),"Verify the error message displayed while entering the expired password credentials");
//    }
//
//    //script 005
//    @Parameters(storeXml)
//    @Test(priority = 14, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void forgotPasswordLoginDrawer(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify if guest user is redirected to Forgot password page when he clicks on Forgot password? hyperlink from Login page");
//        Map<String, String> acct = excelReaderDT2.getExcelData("Login", "Credentials");
//
//        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login link on the Header.");
//        AssertFailAndContinue(loginDrawerActions.clickForgotPassword(forgotYourPasswordPageActions), "Clicked on the Forgot Password link and landed on the Forgot Password page.");
//        AssertFailAndContinue(forgotYourPasswordPageActions.enterEmailAddrAndSubmit(emailAddressReg), "Enter the email ID and check the message");
//        if (store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(forgotYourPasswordPageActions.isDisplayed(forgotYourPasswordPageActions.messageContent1), "Validated the Label content");
//        }
//        if (store.equalsIgnoreCase("CA")) {
//            AssertFailAndContinue(forgotYourPasswordPageActions.isDisplayed(forgotYourPasswordPageActions.messageContent2), "Validated the label content");
//        }
//        headerMenuActions.click(headerMenuActions.backToLogin);
//        headerMenuActions.waitUntilElementDisplayed(loginDrawerActions.emailAddrField, 3);
//    }
//
//    //script 006
//    @Parameters(storeXml)
//    @Test(priority = 0, groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
//    public void reviewLinkInPDP(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify the legacy login model is getting displayed on clicking the review link in PDP page");
//        String search_Term = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductUPC");
//        String search_Term1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
//        Map<String, String> acct = excelReaderDT2.getExcelData("Login", "PasswordExp");
//
//        if (store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, search_Term), "Entered the Search Term and Clicked on the Search button");
//            AssertFailAndContinue(productDetailsPageActions.addToWLAsGuest(loginPageActions), "Select any size and click on wishlist link");
//            loginPageActions.closeLoginModal();
//            AssertFailAndContinue(productDetailsPageActions.clickOnRating(),"Verify the rating is displayed in PDP page");
//            AssertFailAndContinue(productDetailsPageActions.clickWriteAReviewLinkAsGuest(loginPageActions), "Check the login overlay is getting displayed");
//            AssertFailAndContinue(loginDrawerActions.accountLockedErrorMsgLogin(acct.get("invalidEmail"),acct.get("Password")), "loggined with valid credentials and navigated to wishlist page");
//            loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions);
//            headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
//            AssertFailAndContinue(myAccountPageDrawerActions.checkCouponDisplay(),"Verify the coupons are getting displayed after login");
//            headerMenuActions.closeTheVisibleDrawer();
//            AssertFailAndContinue(productDetailsPageActions.writeAReview_Reg("I bought this for my little brother and he loves it."), "verified write a review overlay");
//        }
//        if (store.equalsIgnoreCase("CA")) {
//            AssertFailAndContinue(headerMenuActions.searchAndSubmit(categoryDetailsPageAction, search_Term1), "Entered the Search Term and Clicked on the Search button");
//            AssertFailAndContinue(searchResultsPageActions.clickAddToFavByPosAsGuest(loginPageActions, 1), "Clicked on Add To Favorites as guest and observed Login Button");
//            loginPageActions.closeLoginModal();
//            AssertFailAndContinue(searchResultsPageActions.clickOnProductImageByPosition(1, productDetailsPageActions), "Open PDP page by clicking the product image");
//            AssertFailAndContinue(productDetailsPageActions.addToWLAsGuest(loginPageActions), "Select any size and click on wishlist link");
//            loginPageActions.closeLoginModal();
//            AssertFailAndContinue(productDetailsPageActions.clickWriteAReviewLinkAsGuest(loginPageActions), "Check the login overlay is getting displayed");
//            AssertFailAndContinue(loginDrawerActions.accountLockedErrorMsgLogin(emailAddressReg, password), "loggined with valid credentials and navigated to wishlist page");
//            loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions);
//            AssertFailAndContinue(productDetailsPageActions.writeAReview_Reg("this is very good product to test and write review"), "verified write a review overlay");
//            productDetailsPageActions.click(productDetailsPageActions.writeAReviewButtonAndLink.get(1));
//        }
//    }
//
//    //script 007
//    @Parameters(storeXml)
//    @Test(groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void loginButtonDisableEnable(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify the guest user can validate the login button is enabled/disabled until the values are enter into the fields");
//
//        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Click on the login link");
//        AssertFailAndContinue(loginDrawerActions.validateBtnLoginEnableDisable(emailAddressReg, password), "Validated if the Login button gets enabled only when credentials are entered.");
//        AssertFailAndContinue(loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions), "Enter the valid login details and click on the login button");
//    }
//
//    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, REGISTEREDONLY})
//    public void validateContentForRegUser(@Optional("US") String store, @Optional("registered") String user) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify login button is disabled when giving invalid input and enabled when providing valid inputs");
//        List<String> myAccDrawerContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("myAccountDrawer", "content", "validContent"));
//
//        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
//
//        AssertFailAndContinue(headerMenuActions.clickLoggedLink(myAccountPageDrawerActions), "Click on fname link after the successful login");
//        if (EnvironmentConfig.getEnvironmentProfile().equalsIgnoreCase("prod") || EnvironmentConfig.getEnvironmentProfile().equalsIgnoreCase("prodstaging")) {
//            AssertFailAndContinue(myAccountPageDrawerActions.isPointsToNextRewardsDisplaying(myAccDrawerContent.get(1)), "Verified Points to next rewards displaying");
//        } else if (store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(myAccountPageDrawerActions.validateMyAccPageDrawerContent(myAccDrawerContent.get(1), myAccDrawerContent.get(2)), "Verify the contents present in the my account drawer");
//        } else if (store.equalsIgnoreCase("CA")) {
//            myAccountPageDrawerActions.waitUntilElementDisplayed(myAccountPageDrawerActions.viewMyAccountLink);
//        }
//    }
//
//    @Test(dataProvider = dataProviderName, priority = 1, groups = {GLOBALCOMPONENT, SMOKE, REGISTEREDONLY, PRODUCTION, INTUATSTG})
//    public void logoutFromPages(@Optional("US") String store, @Optional("registered") String user) throws Exception {
//        setAuthorInfo("Azeez");
//        setRequirementCoverage("Verify if user logged out from my account drawer and remain in the same page where they logged out");
//        List<String> myAccDrawerContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("myAccountDrawer", "content", "validContent"));
//
//        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
//        if (store.equalsIgnoreCase("US")) {
//            AssertFailAndContinue(headerMenuActions.pointsText(), "Check whether the points and rewards text is displayed in header for Registered user");
//            AssertFailAndContinue(headerMenuActions.checkMPR_Reg(), "Check whether the MPR link is not displayed to Reg user");
//            AssertFailAndContinue(headerMenuActions.clickLoggedLink(myAccountPageDrawerActions), "Click on Hi Username link and verify if user displayed with the my account drawer");
//            if (EnvironmentConfig.getEnvironmentProfile().equalsIgnoreCase("prod") || EnvironmentConfig.getEnvironmentProfile().equalsIgnoreCase("prodstaging")) {
//                AssertFailAndContinue(myAccountPageDrawerActions.isPointsToNextRewardsDisplaying(myAccDrawerContent.get(1)), "Verified Points to next rewards displaying");
//            } else {
//                AssertFailAndContinue(myAccountPageDrawerActions.validateMyAccPageDrawerContent(myAccDrawerContent.get(1), myAccDrawerContent.get(2)), "Verify the contents present in the my account drawer");
//            }
//            AssertFailAndContinue(myAccountPageDrawerActions.clickLogoutLink(headerMenuActions), "Click on logout link and check user displayed with corresponding page where they logged out");
//            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
//            AssertFailAndContinue(headerMenuActions.checkMPR_Reg(), "Check whether the MPR link is not displayed to Reg user");
//        }
////        headerMenuActions.click(headerMenuActions.welcomeCustomerLink);
////        headerMenuActions.click(headerMenuActions.viewMyAccount);
//        AssertFailAndContinue(headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions,headerMenuActions), "Click Logout from My Account page");
//        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
//        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "Uniforms");
//        String url = headerMenuActions.getCurrentURL();
//        headerMenuActions.click(headerMenuActions.welcomeCustomerLink);
//        myAccountPageDrawerActions.clickLogoutLink(headerMenuActions);
//        AssertFailAndContinue(headerMenuActions.validatePageAfterLogout(url), "verify that the user remains in the same page after logout");
//
//    }
//
//    @Test(dataProvider = dataProviderName, priority = 4, groups = {GLOBALCOMPONENT,SMOKE, REGISTEREDONLY, PROD_REGRESSION})
//    public void validateCartMergeForRegUser(@Optional("US") String store, @Optional("registered") String user) throws Exception {
//
//        setAuthorInfo("Srijith");
//        setRequirementCoverage("Verify whether cart merge is not happening when the guest user adds new items to bag and login");
//        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
//        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
//        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
//
//        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
//        addToBagBySearching(searchKeywordAndQty);
////        addToBagApi_Reg_Login(emailAddressReg,password,"1",store,1);
//        String name = shoppingBagPageActions.getText(shoppingBagPageActions.prodNames.get(0));
//        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
//        myAccountPageDrawerActions.clickLogoutLink(headerMenuActions);
//
//        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
//        String qty1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
//        Map<String, String> searchKeyword1AndQty1 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword1, qty1);
//        addToBagBySearching(searchKeyword1AndQty1);
//        clickLoginAndLoginAsRegUser(emailAddressReg, password);
//        headerMenuActions.closeTheVisibleDrawer();
//        AssertFailAndContinue(shoppingBagPageActions.checkTheItemDisplayed(name),"verify the same item is not getting displayed after login");
//    }
//
//    @Parameters(storeXml)
//    @Test(groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
//    public void storeSwitchAndLogin(@Optional("US") String store) throws Exception {
//        setAuthorInfo("Srijith");
//        setRequirementCoverage("Verify whether user is able login successful after switching store");
//        if (store.equalsIgnoreCase("US")) {
//            footerActions.changeCountryAndLanguage("CA", "English");
//        }
//        if (store.equalsIgnoreCase("CA")) {
//            footerActions.changeCountryAndLanguage("US", "English");
//        }
//        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
//        AssertFailAndContinue(headerMenuActions.clickLoggedLink(myAccountPageDrawerActions), "Validate user login after store switch from US to CA");
//    }
//
//    @Test(dataProvider = dataProviderName, priority = 10, groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, USONLY, PROD_REGRESSION})
//    public void loginFromMPR(@Optional("US") String store, @Optional("guest") String guest) throws Exception {
//        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
//        String qty1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
//        Map<String, String> searchKeyword1AndQty1 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword1, qty1);
//        if (store.equalsIgnoreCase("US")) {
//            addToBagBySearching(searchKeyword1AndQty1);
//            headerMenuActions.clickBagIconOnEmptyBag(shoppingBagDrawerActions);
//            AssertFailAndContinue(shoppingBagDrawerActions.loginFromMPR(), "verify that when user clicks on \"LOG IN\" link in MPR espot,verify that login model is getting displayed to the user.");
//        }
//    }
//}