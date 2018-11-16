package tests.mobileDT.pdp;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;
import uiMobile.pages.actions.MShoppingBagPageActions;

import java.util.List;
import java.util.Map;

public class FindChangeStore extends MobileBaseTest {
	WebDriver mobileDriver;
	String email = "", password = "";

	@Parameters({storeXml, usersXml})
	@BeforeClass(alwaysRun = true)
	public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		initializeDriver();
		mobileDriver = getDriver();
		initializeMobilePages(mobileDriver);
		mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
		mheaderMenuActions.deleteAllCookies();
		email = UiBaseMobile.randomEmail();
		if (store.equalsIgnoreCase("US")) {
			if (user.equalsIgnoreCase("registered")) {
				createAccount(rowInExcelUS, email);
			}
		}
		if (store.equalsIgnoreCase("CA")) {
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

	@Test(priority = 0, dataProvider = dataProviderName, groups = {PDP, GUESTONLY})
	public void verifyFavStoreNotDisplayedInBopis(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("Pooja_Sharma");
		setRequirementCoverage("As a guest user in U.S/C.A/INT store, has no BOPIS items added to bag and has not made an order within the current session, when the user navigates to PDP of any BOPIS eligible item through browse/search/favorite/Product Recommemdation /PLP/Outfit-PDP and initiates \"Pick Up in Store\" modal. Verify that the \"Recent store\" visual representation is not displayed among the 5 other stores in the In store Availability modal.");
		//DT-37516
		AssertFailAndContinue(mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber1", "validUPCNumber")), "Enter textr in field and click search button");
		AssertFailAndContinue(mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions), "Click on Product and click on Find In Store Button");
		AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore("07470"), "select Size,enter Zip Code and click on Search Button");
		AssertFailAndContinue(mproductDetailsPageActions.verifyElementNotDisplayed(mbopisOverlayActions.favStoreSelectedFirst, 5), "Verify Favourite Store representation is not displayed");
		//DT-37484
		AssertFailAndContinue(mbopisOverlayActions.waitUntilElementDisplayed(mbopisOverlayActions.storesListContainer), "Verify Available Stores displayed below in the Bopis Overlay");
	}

	@Test(priority = 1, dataProvider = dataProviderName, groups = {PDP, REGISTEREDONLY, BOPIS})
	public void verifyFavStoreInBopis(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("Pooja_Sharma");
		setRequirementCoverage("As a registered user in U.S/C.A/INT store, has a store previously set through geo-location or saved in their account as their favorite store, when the user navigates to PDP of any BOPIS eligible item through browse/search/favorite/Product Recommemdation /PLP/Outfit-PDP and in product PDP, Verify that the user is able to see a visual distinction as \"Favourite store\"among the other 5 stores displayed in the In store Availability modal." +
				"DT-44194, DT-44258, DT-44257, DT-44256, DT-44255, DT-44266, DT-44265, DT-44264, DT-44263" +
				"DT-44274, DT-44276, DT-44285, DT-44287");

		panCakePageActions.navigateToMenu("FINDASTORE");
		mstoreLocatorPageActions.searchStore("07470");
		AssertFailAndContinue(mstoreLocatorPageActions.showDetailsOfStore(), "Click show details link for the store, verify details are displayed");

		//DT-37503
		AssertFailAndContinue(mstoreLocatorPageActions.addAFavStore_Reg(mheaderMenuActions, panCakePageActions, "07470"), "Add a Store as Favourite to Account");
		String favStoreName = mstoreLocatorPageActions.getFavStoreName();
		panCakePageActions.navigateToMenu("MYACCOUNT");
		mmyAccountPageActions.clickSection("PROFILE");

		AssertFailAndContinue(mmyAccountPageActions.verifyFavStoreIsDisplayed(), "Verify selected fav store is display in profile info");
		AssertFailAndContinue(mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber1", "validUPCNumber")), "Enter textr in field and click search button");
		AssertFailAndContinue(mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions), "Click on Product and click on Find In Store Button");
		AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore("07470"), "select Size,enter Zip Code and click on Search Button");
		AssertFailAndContinue(mbopisOverlayActions.verifyFavORRecent_Store(favStoreName), "Verify Favourite Store is displayed on Top of all stores and displayed with Icon along with address details");
		//DT-37497
		String recentStore = mbopisOverlayActions.selectAnotherStore(favStoreName);
		AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click on Shopping Bag on header");
		AssertFailAndContinue(mshoppingBagPageActions.clickChangeStoreLink(mbopisOverlayActions), "Click on Change Store Link of Product in Shopping Bag");
		AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore("07470"), "select Size,enter Zip Code and click on Search Button");
		AssertFailAndContinue(mbopisOverlayActions.waitUntilElementDisplayed(mbopisOverlayActions.searchButton), "Verify Search Button is displayed that ensures IN Stock Availability Page navigation");
		AssertFailAndContinue(mbopisOverlayActions.verifyFavORRecent_Store(recentStore), "Verify recently added Store is displayed on Top of all stores and displayed with Icon along with address details");
		AssertFailAndContinue(mbopisOverlayActions.waitUntilElementDisplayed(mbopisOverlayActions.notSelectedStoreByTtitle(favStoreName)), "Verify Store added as Fav is displayed but not selected");
		//DT-37499
		String recentlyAddedStoreB = mbopisOverlayActions.selectAnotherStore(favStoreName);
		AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click on Shopping Bag on header");
		AssertFailAndContinue(mshoppingBagPageActions.clickChangeStoreLink(mbopisOverlayActions), "Click on Change Store Link of Product in Shopping Bag");
		AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore("07470"), "select Size,enter Zip Code and click on Search Button");
		AssertFailAndContinue(mbopisOverlayActions.verifyFavORRecent_Store(recentlyAddedStoreB), "Verify recently added Store is displayed on Top of all stores and displayed with Icon along with address details");
		//DT-37501, DT-37481
		AssertFailAndContinue(mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber")), "Enter textr in field and click search button");
		AssertFailAndContinue(mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions), "Click on Product and click on Find In Store Button");
		AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore("07470"), "select Size,enter Zip Code and click on Search Button");
		AssertFailAndContinue(mbopisOverlayActions.verifyFavORRecent_Store(recentlyAddedStoreB), "Verify Recent Store is displayed on Top of all stores and displayed with Icon along with address details");
		AssertFailAndContinue(mbopisOverlayActions.waitUntilElementDisplayed(mbopisOverlayActions.notSelectedStoreByTtitle(recentStore)), "Verify Store added as Fav is displayed but not selected");
		String recentlyAddedStoreC = mbopisOverlayActions.selectAnotherStore(recentlyAddedStoreB);
		AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click on Shopping Bag on header");
		AssertFailAndContinue(mshoppingBagPageActions.clickChangeStoreLink(mbopisOverlayActions), "Click on Change Store Link of Product in Shopping Bag");
		List<String> contentValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "headerContent", "expectedheaderTextContent"));
		AssertFailAndContinue(mbopisOverlayActions.validateTextInBopisOverlay(contentValidation.get(0), contentValidation.get(1)), "Validate the content present in the header of the BOPIS overlay");
		AssertFailAndContinue(mbopisOverlayActions.waitUntilElementDisplayed(mbopisOverlayActions.checkAvailability), "Verify Check Availability button is displayed");
		AssertFailAndContinue(mbopisOverlayActions.validateStoresDetailsInBopisModal(recentlyAddedStoreB, recentlyAddedStoreC), "Verify added Stores B and C displayed in Bopis Modal and click on Check Availability button and verify store details");
	}

	@Test(priority = 2, dataProvider = dataProviderName, groups = {PDP})
	public void verifyFavStoreSelectedBopis(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("Shubhika");
		setRequirementCoverage("User navigate to pdp,select the product and check for bopis stores available.and clicks on"+
				"PICK UP IN STORE CTA. Verify that the product is added to that corresponding store automatically without triggering BOPIS modal "+"DT-37358");
		String productSize=null;
		String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
		String qty = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "Qty");
		String zipcode=getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
		if (user.equalsIgnoreCase("registered")) {
			panCakePageActions.navigateToMenu("LOGIN");
			mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
			
		}
		if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
	           mheaderMenuActions.clickShoppingBagIcon();
	           mshoppingBagPageActions.validateEmptyShoppingCart();
	       }
		AssertFailAndContinue(mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber")), "Enter text in field and click search button");
		AssertFailAndContinue(mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions), "Click on Product and click on Find In Store Button");
		AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore(zipcode), "select Size,enter Zip Code and click on Search Button");
		mbopisOverlayActions.selectFromAvailableStores(mheaderMenuActions, 0);
		mheaderMenuActions.clickShoppingBagIcon();
		productSize=mshoppingBagPageActions.getSize();
		Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
		selectSizeFitQtyOnPDPPage(searchKeywordAndQty, productSize, "");
		mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions);
		mheaderMenuActions.clickShoppingBagIcon();
		int itemCount=mshoppingBagPageActions.getBagCountFromShoppingBag();
		AssertFailAndContinue( itemCount==2,"The product has been successfully added to the bag");


	}

}


