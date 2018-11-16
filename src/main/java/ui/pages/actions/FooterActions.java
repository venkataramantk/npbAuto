package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.pages.repo.FooterRepo;
import ui.support.EnvironmentConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by skonda on 5/19/2016.
 */
public class FooterActions extends FooterRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(FooterActions.class);

    public FooterActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickGetItNowLinkAtGiftCard(GiftCardsPageActions giftCardsPageActions) {
        click(getItNow_GiftCardLink);
        return waitUntilElementDisplayed(giftCardsPageActions.sendAGiftCard_Btn, 30);
    }

    public boolean clickOnOrderStatus(OrderStatusActions orderStatusActions) {
        click(link_OrderStatus);
        return waitUntilElementDisplayed(orderStatusActions.trackOrderbutton, 30);

    }

    public boolean clickOnGiftCardsLink(GiftCardsPageActions giftCardsPageActions) {
        click(footerLinksByName("Gift Cards"));
        return waitUntilElementDisplayed(giftCardsPageActions.sendAGiftCard_Btn, 30);
    }

    public boolean clickOnMyAccountLink(MyAccountPageActions myAccountPageActions) {
        click(footerLinksByName("My Account"));
        return myAccountPageActions.isAccountPageDisplayed();
    }

    public boolean clickOnMyAccountAndVerifyRememberedUserLogin(LoginDrawerActions loginDrawerActions) {
        click(footerLinksByName("My Account"));
        return waitUntilElementDisplayed(loginDrawerActions.rememberedLogout, 30);
    }

    public boolean clickOnSiteMapLink(SiteMapPageActions siteMapPageActions) {
        click(siteMapLink);
        return waitUntilElementDisplayed(siteMapPageActions.lbl_TCP);
    }

    public boolean clickOnCreateAnAccount(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(link_CreateAnAccount, 30);
        click(link_CreateAnAccount);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 30);

    }

    public int footerLinksCount() {
        return footerLinks.size();
    }

    public Set<String> getFooterLinks() {
        Set<String> footerLinkSet = new HashSet<>();
        int footerLinksCt = footerLinksCount();
        for (int i = 0; i < footerLinksCt; i++) {
            footerLinkSet.add(getText(footerLinks.get(i)));

        }
        return footerLinkSet;
    }

    public boolean clickOnFooterByLinkAndVerify(String footerLink, String urlContains) {
        click(footerLinksByName(footerLink));
        getDefaultImplicitTimeout();
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlContains);
    }

    public boolean clickOnFooterMyPlaceRewardsCCByLinkAndVerify(String footerLink, String urlContains) {
        click(footerLinkMyPlaceRewardCCByName(footerLink));
        getDefaultImplicitTimeout();
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlContains);
    }

    public boolean clickOnFooterMyPlaceRewardsByLinkAndVerify(String footerLink, String urlContains) {
        click(getFirstElementFromList(footerLinkMyPlaceRewardByName(footerLink)));
        getDefaultImplicitTimeout();
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlContains);
    }

    //newly added
    public boolean validateMPRLnks() {
        if (waitUntilElementDisplayed(link_CreateAnAccount, 5) &&
                waitUntilElementDisplayed(link_ChkPntBalance, 5) &&
                waitUntilElementDisplayed(link_RedeemRewards, 5) &&
                waitUntilElementDisplayed(link_MembersBenefit))
            return true;
        else
            return false;
    }

    public boolean validateMPRcreditCardLnks() {
        scrollToBottom();
        staticWait(1000);
        scrollToBottom();
        staticWait(1000);
        scrollDownToElement(termsAndConditionLnk);
        if (waitUntilElementDisplayed(link_ApplyNow, 5) &&
                waitUntilElementDisplayed(link_PayYourBill, 5) &&
                waitUntilElementDisplayed(link_ManageYouAcc, 5) &&
                waitUntilElementDisplayed(link_LearnMore, 5))
            return true;
        else
            return false;
    }

    public boolean validateHelpCenterLnks() {
        if (waitUntilElementDisplayed(link_FAQs, 5) &&
                waitUntilElementDisplayed(link_OrderStatus, 5) &&
                waitUntilElementDisplayed(link_ReturnPolicy, 5) &&
                waitUntilElementDisplayed(link_ShippingOption, 5) &&
                waitUntilElementDisplayed(link_GCBal, 5) &&
                waitUntilElementDisplayed(link_PrivacyPolicy, 5) &&
                //waitUntilElementDisplayed(link_InternetAds, 5) &&
                waitUntilElementDisplayed(link_TermsAndCondition, 5))
            return true;
        else
            return false;
    }

    public boolean validateShoppingGroupLnks() {
        boolean myAcctLink = waitUntilElementDisplayed(link_MyAcc, 5);
        boolean couponsLink = waitUntilElementDisplayed(link_Coupons, 5);
        boolean storeLocLink = waitUntilElementDisplayed(link_StoreLocator, 5);
        boolean sizeChartLink = waitUntilElementDisplayed(link_SizeChart, 5);
        boolean giftCardsLink = waitUntilElementDisplayed(link_GiftCards, 5);
        boolean giftServicesLink = waitUntilElementDisplayed(link_GiftServices, 5);
        boolean favLink = waitUntilElementDisplayed(link_Fav, 5);
        boolean seaBooksLink = waitUntilElementDisplayed(link_SeasonalLoookbooks, 5);
        boolean mobileAppLink = waitUntilElementDisplayed(link_MobileApp, 5);

        if (myAcctLink &&
                couponsLink &&
                storeLocLink &&
                sizeChartLink &&
                giftCardsLink &&
                giftServicesLink &&
                favLink &&
                seaBooksLink &&
                mobileAppLink)
            return true;
        else
            addStepDescWithRedFont("MyAccountLinK: " + myAcctLink + " Coupons Link " + couponsLink + " storeLocator Link " + storeLocLink + " size Chart link: " + sizeChartLink
                    + " gift cards link: " + giftCardsLink + " gift services link " + giftServicesLink + " favorites link " + favLink + " seesonal Books link " + seaBooksLink + " mobile App link: " + mobileAppLink);
        return false;
    }

    public boolean validateShoppingGroupLnksCA() {
        return waitUntilElementDisplayed(link_MyAcc, 5) &&
                waitUntilElementDisplayed(link_StoreLocator, 5) &&
                waitUntilElementDisplayed(link_SizeChart, 5) &&
                waitUntilElementDisplayed(link_GiftCards, 5) &&
                waitUntilElementDisplayed(link_Fav, 5) &&
                waitUntilElementDisplayed(link_SeasonalLoookbooks, 5);

    }

    public boolean validateAboutUsGroupLnksUS() {
        if (waitUntilElementDisplayed(link_PublicRelation, 5) &&
                waitUntilElementDisplayed(link_InvestorRelation, 5) &&
                waitUntilElementDisplayed(link_Careers, 5) &&
                waitUntilElementDisplayed(link_SocialResponse, 5) &&
                waitUntilElementDisplayed(link_InterOpportuinities, 5) &&
                waitUntilElementDisplayed(link_RecallInfo, 5) &&
                waitUntilElementDisplayed(link_MomSpace, 5))
            return true;
        else
            return false;
    }

    public boolean validateAboutUsGroupLnksCA() {
        if (waitUntilElementDisplayed(link_PublicRelation, 5) &&
                waitUntilElementDisplayed(link_InvestorRelation, 5) &&
                waitUntilElementDisplayed(link_Careers, 5) &&
                waitUntilElementDisplayed(link_SocialResponse, 5) &&
                waitUntilElementDisplayed(link_InterOpportuinities, 5) &&
                waitUntilElementDisplayed(link_RecallInfo, 5))
            return true;
        else
            return false;
    }

    public boolean navTermLink() {
        return (waitUntilElementDisplayed(link_ItermNavTermsAndCondition, 5) &&
                waitUntilElementDisplayed(link_ItermNavPrivacyPolicy, 5) &&
                waitUntilElementDisplayed(link_ItermNavSupplyChainAct, 5) &&
                waitUntilElementDisplayed(link_ItermNavSiteMap, 5));

    }

    public boolean siteMap() {
        click(link_ItermNavSiteMap);
        if (waitUntilElementDisplayed(siteMap_title, 5) &&
                waitUntilElementDisplayed(girls_Outfits, 5) &&
                waitUntilElementDisplayed(boys_Outfits, 5) &&
                waitUntilElementDisplayed(toddlergirls_Outfits, 5)) {
            click(girls_Outfits);
            waitUntilElementDisplayed(outfits_title, 5);
            return true;
        }
        return false;
    }

    public boolean validateBottomOfFooterLnks() {
        return (waitUntilElementDisplayed(link_FooterPrivacy, 5) &&
                waitUntilElementDisplayed(link_SupplyChain, 5) &&
                waitUntilElementDisplayed(link_SiteMap, 5) &&
                waitUntilElementDisplayed(termsAndConditionLnk, 5));

    }

    public boolean clickOnShoppingAndValidateURL(String linkName, String country) {
        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        staticWait(5000);
        //      String currentUrl = getCurrentURL();
        if (linkName.equalsIgnoreCase("size chart")) {
            if (isDisplayed(sizeChartHeader)) {
                return true;
            }

        } else if (linkName.equalsIgnoreCase("gift cards")) {
            if (country.equalsIgnoreCase("US")) {
                if (isDisplayed(link_EmailAnGC)) {
                    return true;
                }
            } else if (country.equalsIgnoreCase("CA")) {
                if (waitUntilElementDisplayed(link_EmailAnGC_CA, 20)) {
                    return true;
                }
            }
        } else if (linkName.equalsIgnoreCase("gift services")) {
            if (isDisplayed(giftServiceTitle)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("seasonal lookbooks")) {
            if (isDisplayed(lookbookContainer)) {
                return true;
            }
        } else if ((linkName.equalsIgnoreCase("Mobile App"))) {
            if (isDisplayed(mobileAppContainer)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("store locator")) {
            if (isDisplayed(searchStoreLocation)) {
                return true;
            }
        } else if (isDisplayed(continueShoppingLink)) {
            return true;
        }
        addStepDescription("Check the redirection is not to 404 Page");
        return false;

    }

    public boolean clickOnHelpCenterLinkAndValidateURL(String linkName, String urlValue) {
//        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        staticWait(5000);
        //      String currentUrl = getCurrentURL();
        if (linkName.equalsIgnoreCase("gift card balance")) {
            if (isDisplayed(link_EmailAnGC)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("return policy")) {
            if (isDisplayed(continueShoppingLink)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("faq") || linkName.equalsIgnoreCase("Shipping Options") || linkName.equalsIgnoreCase("Privacy Policy")) {
            if (isDisplayed(helpCenterLinkSelected)) {
                return true;
            }
        } else {
            if (isDisplayed(continueShoppingLink)) {
                return true;
            }
        }
        addStepDescription("Check the redirection is not to 404 Page");
        return false;

    }

    public boolean clickOnAboutUSLinkAndValidateURL(String linkName, String urlValue) {
        String env = EnvironmentConfig.getEnvironmentProfile();
        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        staticWait(5000);
        //      String currentUrl = getCurrentURL();
        if (linkName.equalsIgnoreCase("public relations")) {
            if (isDisplayed(continueShoppingLink)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("careers")) {
            if (env.equalsIgnoreCase("prod")) {
                if (isDisplayed(careersPage_Prod)) {
                    return true;
                }
            } else if (isDisplayed(corporateBtn)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("responsible sourcing")) {
            if (isDisplayed(vendorCodeOfConduct_Link)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("international opportunities")) {
            if (isDisplayed(internationalOppBanner)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("recall information")) {
            if (isDisplayed(recallInfo)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("blog")) {
            String currentWindow = driver.getWindowHandle();
            switchToWindow(currentWindow);
            if (isDisplayed(headerInBlogPage)) {
                return true;
            }
        } else
            addStepDescription("Check the redirection is not to 404 Page");
        return false;

    }

    public boolean clickOnLinkAndValidateHREF(String linkName, String hrefValue) {
        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        staticWait(5000);
        String href = getAttributeValue(helpCenterLinkSelected, "href");
        return href.contains(hrefValue);
    }

    public boolean clickOnButtonAndValidateURL(String linkName, String urlValue) {
        click(footerButtonsByName(linkName));
        staticWait(5000);
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlValue);
    }

    public boolean clickOnFavoritesAsGuest(WishListDrawerActions wishListDrawerActions) {
        click(link_Fav);
        return waitUntilElementDisplayed(wishListDrawerActions.emailAddrField, 30);
    }

    public boolean clickOnLinkDataIdAndValidateURL(String linkName, String urlValue) {
        scrollDownUntilElementDisplayed(footerLinkDataId(linkName));
        click(footerLinkDataId(linkName));
        staticWait(5000);
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlValue);
    }

    public boolean clickOnSocialMediaLinkAndValidateURL(String linkName, String urlValue) {
        click(socialMediaLinksInFooter(linkName));
        staticWait(5000);
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlValue);
    }

    public boolean clickOnMPRCCAndValidateURL(String linkName, String urlValue) {
        waitUntilElementDisplayed(MPRLinksInFooter(linkName), 10);
        click(MPRLinksInFooter(linkName));
        staticWait(5000);
        // String currentUrl = getCurrentURL();
        if (linkName.equalsIgnoreCase("apply now")) {
            isDisplayed(firstNameField_WIC);
            return true;
        } else if (linkName.equalsIgnoreCase("pay your bill") || linkName.equalsIgnoreCase("Manage Your Account")) {
            isDisplayed(userNameField);
            return true;
        } else if (linkName.equalsIgnoreCase("learn more")) {
            isDisplayed(applyOrAcceptOfferButton.get(0));
            return true;
        } else
            addStepDescription("check the 404 error redirection");
        return false;
    }

    public boolean clickOnMPRLinksAndValidateURL(String linkName, String urlValue) {
        waitUntilElementDisplayed(MPRLinksInFooter(linkName), 10);
        click(MPRLinksInFooter(linkName));
        staticWait(5000);
        String currentUrl = getCurrentURL();
        if (linkName.equalsIgnoreCase("create account")) {
            if (isDisplayed(closemodal)) {
                click(closemodal);
                return true;
            }
        } else if (linkName.equalsIgnoreCase("check point balance")) {
            if (isDisplayed(emailIDFieldName)) {
                click(closemodal);
                return true;
            } else if (isDisplayed(myAccountContainer)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("redeem rewards")) {
            if (isDisplayed(emailIDFieldName)) {
                click(closemodal);
                return true;
            } else if (isDisplayed(myAccountContainer)) {
                return true;
            }
        } else if (linkName.equalsIgnoreCase("member benefits")) {
            if (currentUrl.contains("content/myplace-rewards-page")) {
                return true;
            }
        } else
            addStepDescription("check the 404 error redirection");
        return false;
    }

//    public void clickENLnk() {
//        click(ENLink);
//    }
//
//    public void clickESLnk() {
//        click(ESLink);
//    }

//    public boolean changeCountryAndLanguageForEn(String country, String language) {
//        clickENLnk();
//        selectDropDownByVisibleText(shipToCountryDropdown, country);
//        if (language.contains("English")) {
//            selectDropDownByValue(langDropdown, "en");
//        } else if (language.contains("Spanish")) {
//            selectDropDownByValue(langDropdown, "es");
//        } else if (language.contains("French")) {
//            selectDropDownByVisibleText(langDropdown, "fr");
//            click(saveBtn);
//      /*  staticWait(1000);
//        if (country == "USA") {
//            return waitUntilElementDisplayed(imgFlagUS, 30);
//        } else if (country == "CANADA") {
//            return waitUntilElementDisplayed(imgFlagCA, 30);
//        } else
//            return verifyElementNotDisplayed(imgFlagUS, 10) && verifyElementNotDisplayed(imgFlagCA, 10);*/
//        }
//        return false;
//
//    }

    //    public boolean changeCountryAndLanguageForES(String country, String language) {
//        clickESLnk();
//        selectDropDownByVisibleText(shipToCountryDropdown, country);
//        if (language.contains("English")) {
//            selectDropDownByValue(langDropdown,"en");
//        } else if (language.contains("Spanish")) {
//            selectDropDownByValue(langDropdown,"es");
//        } else if (language.contains("French")) {
//            selectDropDownByVisibleText(langDropdown,"fr");
//            click(saveBtn);
//      /*  staticWait(1000);
//        if (country == "USA") {
//            return waitUntilElementDisplayed(imgFlagUS, 30);
//        } else if (country == "CANADA") {
//            return waitUntilElementDisplayed(imgFlagCA, 30);
//        } else
//            return verifyElementNotDisplayed(imgFlagUS, 10) && verifyElementNotDisplayed(imgFlagCA, 10);*/
//        }
//        return false;
//    }
    public boolean clickOnLanguageButton() {
        try {
            scrollDownToElement(languageButton);
            waitUntilElementDisplayed(languageButton, 5);
            //javaScriptClick(driver,languageButton);
            click(languageButton);
            waitUntilElementDisplayed(shipToCountryDropdown, 5);
            return waitUntilElementDisplayed(shipToModalDialog);
        } catch (Throwable t) {
            scrollDownToElement(languageButton);
            click(languageButton);
            waitUntilElementDisplayed(shipToCountryDropdown, 5);
            return waitUntilElementDisplayed(shipToModalDialog);

        }

    }

    public boolean changeLanguageByLanguage(String language) {

        clickOnLanguageButton();
        if (language.contains("Español")) {
            selectDropDownByValue(langDropdown, "es");
        } else if (language.contains("Français")) {
            selectDropDownByValue(langDropdown, "fr");
        }
        click(saveBtn);
        staticWait(5000);
        getDefaultImplicitTimeout();
        try {
            return verifyElementNotDisplayed(saveBtn, 30);
        } catch (Throwable throwable) {
            return true;

        }
    }

    public boolean changeCountryByCountry(String country) {
        boolean isURLAndFlag = false;
        clickOnLanguageButton();
        selectDropDownByVisibleText(shipToCountryDropdown, country);
        click(saveBtn);
        waitUntilElementDisplayed(flagCountryImg);
        staticWait(5000);

        if (country == "United States") {
            String currentUrl = getCurrentURL();
            String expected_USUrl = null;
            try {
                expected_USUrl = EnvironmentConfig.getApplicationUrl();
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean isURLNavigated = currentUrl.equalsIgnoreCase(expected_USUrl);
            boolean isFlagChanged = getAttributeValue(flagCountryImg, "src").contains("US.gif"); //waitUntilElementDisplayed(imgFlagUS, 30);
            isURLAndFlag = isFlagChanged && isURLNavigated;
            if (!isURLAndFlag) {
                Assert.fail("The URL is navigated. " + isURLNavigated + " Expected: " + expected_USUrl + " currenturl: " + currentUrl + " Is flag image changed? " + isFlagChanged);
            }
        }
        return isURLAndFlag;
    }


    public boolean verifyShipToCountry(String CountryName) throws InterruptedException {
        staticWait(10);
        if (driver.findElement(By.xpath("//img[@id='countryFlagInToolbar']")).getAttribute("title").replace("Ship to Country:", "").trim().equalsIgnoreCase(CountryName))
            return true;
        else
            return false;

    }

    public boolean changeCountryAndLanguage(String country, String language) throws Exception {
        clickOnLanguageButton();
        selectDropDownByValue(shipToCountryDropdown, country);
//        selectDropDownByVisibleText(languageDropDown,language);
        if (language.contains("Français")) {
            selectDropDownByValue(langDropdown, "fr");
        } else if (language.contains("English")) {
            selectDropDownByValue(langDropdown, "en");
        } else if (language.contains("Español")) {
            selectDropDownByValue(langDropdown, "es");
        }
        boolean isURLAndFlag = false;
        staticWait(3000);
        click(saveBtn);
        staticWait(5000);
//        verifyElementNotDisplayed(saveBtn, 30);
        waitUntilElementDisplayed(flagNameByCountry(country), 40);
        String currentUrl = getCurrentURL();
        if (country == "US" && language.equalsIgnoreCase("English")) {
            String expected_USUrl = EnvironmentConfig.getApplicationUrl();
            boolean isURLNavigated = currentUrl.equalsIgnoreCase(expected_USUrl);
            boolean isFlagChanged = getAttributeValue(flagCountryImg, "src").contains("US.gif"); //waitUntilElementDisplayed(imgFlagUS, 30);
            isURLAndFlag = isFlagChanged && isURLNavigated;
            if (!isURLAndFlag) {
                Assert.fail("The URL is navigated. " + isURLNavigated + " Expected: " + expected_USUrl + " currenturl: " + currentUrl + " Is flag image changed? " + isFlagChanged);
            }
        }
        if (country == "IN" && language.equalsIgnoreCase("English")) {
            String expected_USUrl = EnvironmentConfig.getApplicationUrl();
            boolean isURLNavigated = currentUrl.equalsIgnoreCase(expected_USUrl);
            boolean isFlagChanged = getAttributeValue(flagCountryImg, "src").contains("IN.gif"); //waitUntilElementDisplayed(imgFlagUS, 30);
            isURLAndFlag = isFlagChanged && isURLNavigated;
            if (!isURLAndFlag) {
                Assert.fail("The URL is navigated. " + isURLNavigated + " Expected: " + expected_USUrl + " currenturl: " + currentUrl + " Is flag image changed? " + isFlagChanged);
            }
        }
        if (country == "CH" && language.equalsIgnoreCase("English")) {
            String expected_USUrl = EnvironmentConfig.getApplicationUrl();
            boolean isURLNavigated = currentUrl.equalsIgnoreCase(expected_USUrl);
            boolean isFlagChanged = getAttributeValue(flagCountryImg, "src").contains("CH.gif"); //waitUntilElementDisplayed(imgFlagUS, 30);
            isURLAndFlag = isFlagChanged && isURLNavigated;
            if (!isURLAndFlag) {
                Assert.fail("The URL is navigated. " + isURLNavigated + " Expected: " + expected_USUrl + " currenturl: " + currentUrl + " Is flag image changed? " + isFlagChanged);
            }
        }
//        else
        return isURLAndFlag;
//            return verifyElementNotDisplayed(flagCountryImg, 10);


    }

      public boolean clickPLCCImageFromFooter(FooterMPRCreditCardActions footerMPRCreditCardActions) {
        scrollDownUntilElementDisplayed(image_Plcc);
        click(image_Plcc);
        return waitUntilElementDisplayed(footerMPRCreditCardActions.applyButton, 30);
    }

    public boolean clickContinueShopping(String linkName, HeaderMenuActions headerMenuActions) {

        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        scrollToTheTopHeader(continueShoppingLink);
        waitUntilElementDisplayed(continueShoppingLink);
        click(continueShoppingLink);
        staticWait(5000);
        return waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon, 3);

    }
//    public boolean errorPhoneNumber(String linkName, String phonenumber) {
    //       scrollDownUntilElementDisplayed(footerLinksByName(linkName));
    //
    //      click(footerLinksByName(linkName));
    //    scrollUpToElement(emailUS_Lnk);
    //      waitUntilElementDisplayed(emailUS_Lnk);
    //      click(emailUS_Lnk);
//        staticWait(5000);
//  click(phoneNumberForm);
//   clearAndFillText(phoneNumberForm,"qwertryuu");
    //      return waitUntilElementDisplayed();}

    public boolean clickOnFooterAndFavIcon(String linkName, LoginPageActions loginPageActions) {

        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        staticWait(5000);
        return waitUntilElementDisplayed(loginPageActions.emailAddrField, 3);
    }

    public boolean mobileAppRedirection(String email) {
        waitUntilElementDisplayed(signUpEmail, 5);
        clearAndFillText(signUpEmail, email);
        staticWait();
        click(submitButton);
        staticWait(5000);
        return waitUntilElementDisplayed(shopNowButton, 5) && waitUntilElementDisplayed(locateAStore, 5);
    }

    public boolean addRecommendationProdCard(ProductCardViewActions productCardViewActions) {
        boolean prodAvailable = waitUntilElementsAreDisplayed(prodWithPrice, 10);

        if (prodAvailable) {
            scrollDownToElement(prodWithPrice.get(0));
            staticWait(6000);
            mouseHover(prodWithPrice.get(0));
            staticWait(2000);
            mouseHover(prodWithPrice.get(0));
            click(addToBagIcon.get(0));
            staticWait(2000);
        }

        if (!prodAvailable) {
            waitUntilElementDisplayed(navButton, 3);
            if (isDisplayed(navButton)) {
                click(navButton);
                staticWait(3000);
                waitUntilElementDisplayed(prodWithPrice.get(0), 4);
                mouseHover(prodWithPrice.get(0));
                click(addToBagIcon.get(0));
            } else {
                Assert.assertFalse(false, "The product with price and the next arrow navigation buttons are not available");
            }
        }

        return waitUntilElementDisplayed(productCardViewActions.addToBagBtn, 3);

    }

    public boolean productRecommDisplayValidation() {
        staticWait();
        scrollDownToElement(youMayAlsoLike);
        if (waitUntilElementDisplayed(youMayAlsoLike, 4) &&
                waitUntilElementDisplayed(productDisplay, 4))
            return true;
        else
            return false;

    }

    public boolean checkProdRecommendationIsAvailable() {

        scrollDownUntilElementDisplayed(recommendationHeading);
        boolean prodAvailable = waitUntilElementsAreDisplayed(prodRecommendation, 10);
        return prodAvailable;
    }

    public boolean addProdToFav_Reg() {
        mouseHover(prodWithPriceByPos(1));
        staticWait();
        click(addToFavIcon.get(0));
        staticWait(3000);
        mouseHover(prodWithPriceByPos(1));
        return waitUntilElementDisplayed(favIconEnabled.get(0));
    }

    public boolean addProdToFav_Guest(LoginPageActions loginPageActions) {
        mouseHover(prodWithPrice.get(0));
        waitUntilElementsAreDisplayed(addToBagIcon, 5);
        waitUntilElementsAreDisplayed(addToFavIcon, 5);
        if (addToFavIcon.size() >= 1) {
            click(addToFavIcon.get(0));
            staticWait(3000);
            return waitUntilElementDisplayed(loginPageActions.loginButton, 20);
        }
        return false;
    }

    public int getProductRecommProdOnEmptyBag() {
        return prodRecommendation.size();
    }

    public boolean clickOnItemAtProdRecommByPos(int i, ProductDetailsPageActions productDetailsPageActions) {
        click(prodRecommendation.get(i - 1));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean checkShipToModal(String store) {
        String selectedStore = "";
        String availableLang = "";
        String currency = "";
        String intStore = "";
        String currentCurrency = "";
        if (store.equalsIgnoreCase("US")) {
            selectedStore = "United States";
            availableLang = "English\nSpanish";
            currency = "Dollars";
            intStore = "India";
            currentCurrency = getText(shipTOCurrency.get(0));
        }
        if (store.equalsIgnoreCase("CA")) {
            selectedStore = "CANADA";
            availableLang = "English\nFrench";
            currency = "Canada Dollar";
            intStore = "India";
            currentCurrency = getText(shipTOCurrency.get(1));
        }
        if (store.equalsIgnoreCase("IN")) {
            selectedStore = "INDIA";
            availableLang = "English\nSpanish";
            currency = "Indian Rupee";
            intStore = "India";
            currentCurrency = getText(indianCurrency);
        }
        waitUntilElementDisplayed(selectedCountry, 4);
        String currentCountry = getText(selectedCountry);
        String currentLanguage = getText(availaableLangUS);

        if (selectedStore.equalsIgnoreCase(currentCountry) && (currentLanguage.equalsIgnoreCase(availableLang)) && currency.equalsIgnoreCase(currentCurrency)) {
            return true;
        } else
            return (intStore.equalsIgnoreCase(currentCountry) && (currentLanguage.equalsIgnoreCase(availableLang)) && currency.equalsIgnoreCase(currentCurrency));

    }

    public boolean changeStoreFromModal(String country) {
        boolean isURLAndFlag = false;
        waitUntilElementDisplayed(noteTextInShipToModal, 5);
        selectDropDownByVisibleText(shipToCountryDropdown, country);
        click(saveBtn);
        waitUntilElementDisplayed(flagCountryImg);
        staticWait(5000);

        if (country == "United States") {
            String currentUrl = getCurrentURL();
            String expected_USUrl = null;
            try {
                expected_USUrl = EnvironmentConfig.getApplicationUrl();
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean isURLNavigated = currentUrl.equalsIgnoreCase(expected_USUrl);
            boolean isFlagChanged = getAttributeValue(flagCountryImg, "src").contains("US.gif"); //waitUntilElementDisplayed(imgFlagUS, 30);
            isURLAndFlag = isFlagChanged && isURLNavigated;
            if (!isURLAndFlag) {
                Assert.fail("The URL is navigated. " + isURLNavigated + " Expected: " + expected_USUrl + " currenturl: " + currentUrl + " Is flag image changed? " + isFlagChanged);
            }
        }
        return isURLAndFlag;
    }

    public boolean validateProduceRecommendPriceDetails() {
        while (nextcarousel.isEnabled() && !waitUntilElementsAreDisplayed(prodWithPrice, 15)) {
            click(nextcarousel);
        }

        if (waitUntilElementsAreDisplayed(prodWithPrice, 2) && waitUntilElementDisplayed(offerPrice, 5)) {
            return true;
        } else {
            Assert.fail("There are no products available with price in recommendadtions");
            return false;
        }
    }

    public boolean addProdToFav_Guest_login(String userName, String Password) {
        mouseHover(prodWithPriceByPos(1));
        staticWait();
        click(addToFavIcon.get(0));
        LoginPageActions loginPageActions = new LoginPageActions(driver);
        loginPageActions.clearAndFillText(loginPageActions.emailAddrField, userName);
        loginPageActions.clearAndFillText(loginPageActions.passwordField, Password);
        loginPageActions.click(loginPageActions.loginButton);
        staticWait(2000);
        mouseHover(prodWithPriceByPos(1));
        return waitUntilElementDisplayed(favIconEnabled.get(0));
    }

    public void clickAddToBag() {
        mouseHover(prodWithPriceByPos(1));
        click(addToBagIcon.get(0));
    }

    public boolean checkHeader() {
        scrollToBottom();
        return waitUntilElementDisplayed(stickeyHeader);
    }

    public boolean checkTranslatedContentShipTo(String country, String language, String currency) {

        waitUntilElementDisplayed(selectedCountry, 4);
        String currentCountry = getText(label_TranslatedCountry.get(0));
        String currentLanguage = getText(label_TranslatedLang.get(0));
        String currency1 = getText(label_TranslatedCurrency.get(0));
        return (country.equals(currentCountry) && (language.equals(currentLanguage)) && currency.equalsIgnoreCase(currency1));

    }

    public boolean isSelectedLanguageInBold() {
        String selectedLang = getText(selectedLanguage);
        return Integer.parseInt(getElementFontWeight(selectedLang).toString()) >= 700;
    }

    public boolean styleSquadCheck() {
        waitUntilElementDisplayed(styleSquad_Espot, 3);
        if (isDisplayed(styleSquad_FirstName) && isDisplayed(styleSquad_LastName) && isDisplayed(styleSquad_Zip)
                && isDisplayed(styleSquad_BirthMon) && isDisplayed(styleSquad_BirthYear) && isDisplayed(styleSquad_ChildName)
                && isDisplayed(styleSquad_DigitalSign) && isDisplayed(styleSquad_WavierRelease) && isDisplayed(styleSquad_State)
                && isDisplayed(styleSquad_Gender) && isDisplayed(styleSquad_MediaGuide) && isDisplayed(styleSquad_City)
                && isDisplayed(styleSquad_ParentYear) && isDisplayed(styleSquad_Email)) {
            return true;
        } else {
            addStepDescription("Check the Elements in Style ssquad Page or 404 redirection");
            return false;
        }
    }

    public boolean submitStyleSquadForm(String fName, String lName, String email, String pYear, String city, String state,
                                        String zip, String childName, String childMon, String childYear, String gender, String sign,
                                        String error1, String error2, String error3, String error4, String error5, String error6, String error7, String error8,
                                        String error9, String error10, String error11, String error12, String error13, String error14) {
        waitUntilElementDisplayed(styleSquad_FirstName, 3);
        tabFromField(styleSquad_FirstName);
        String err1 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_FirstName, fName);

        tabFromField(styleSquad_LastName);
        String err2 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_LastName, lName);

        tabFromField(styleSquad_Email);
        String err3 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_Email, email);

        tabFromField(styleSquad_ParentYear);
        String err4 = getText(styleSquad_Error);
        selectDropDownByValue(styleSquad_ParentYear, pYear);

        tabFromField(styleSquad_City);
        String err5 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_City, city);

        tabFromField(styleSquad_State);
        String err6 = getText(styleSquad_Error);
        selectDropDownByValue(styleSquad_State, state);

        tabFromField(styleSquad_Zip);
        String err7 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_Zip, zip);

        tabFromField(styleSquad_ChildName);
        String err8 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_ChildName, childName);

        tabFromField(styleSquad_BirthMon);
        String err9 = getText(styleSquad_Error);
        selectDropDownByValue(styleSquad_BirthMon, childMon);

        tabFromField(styleSquad_BirthYear);
        String err10 = getText(styleSquad_Error);
        selectDropDownByValue(styleSquad_BirthYear, childYear);

        tabFromField(styleSquad_Gender);
        String err11 = getText(styleSquad_Error);
        selectDropDownByValue(styleSquad_Gender, gender);

        tabFromField(styleSquad_MediaGuide);
        String err12 = getText(styleSquad_Error);
        click(styleSquad_MediaGuide);

        tabFromField(styleSquad_WavierRelease);
        String err13 = getText(styleSquad_Error);
        click(styleSquad_WavierRelease);

        tabFromField(styleSquad_DigitalSign);
        String err14 = getText(styleSquad_Error);
        clearAndFillText(styleSquad_DigitalSign, sign);
        //     click(styleSquad_Submit);

        if (err1.equalsIgnoreCase(error1) && err2.equalsIgnoreCase(error2) && err3.equalsIgnoreCase(error3) && err4.equalsIgnoreCase(error4)
                && err5.equalsIgnoreCase(error5) && err6.equalsIgnoreCase(error6) && err7.equalsIgnoreCase(error7) && err8.equalsIgnoreCase(error8)
                && err9.equalsIgnoreCase(error9) && err10.equalsIgnoreCase(error10) && err11.equalsIgnoreCase(error11) && err12.equalsIgnoreCase(error12)
                && err13.equalsIgnoreCase(error13) && err14.equalsIgnoreCase(error14)) {
            return true;
        } else {
            addStepDescription("unable to submit form due to the re-captcha");
            return false; // need to update after the fixing of the issue
        }

    }

    /**
     * Verifies meta tag content
     * Autho: JK
     *
     * @param desc : string to compare
     * @return true if matched
     */
    public boolean metaDescInSiteMap(String desc) {
        waitUntilElementDisplayed(link_ItermNavSiteMap);
        click(link_ItermNavSiteMap);
        String value = getAttributeValue(metaDescription, "content");
        return value.contains(desc);
    }

    /**
     * @param s: Page Heading to compare
     * @return true if page heading matched
     * @Author: JK
     */
    public boolean validatePageHeading(String s) {
        waitUntilElementDisplayed(siteMapHeading);
        Boolean heading = getText(siteMapHeading).equalsIgnoreCase(s);
        Boolean tag = siteMapHeading.getTagName().equalsIgnoreCase("H1");
        return heading && tag;
    }

    /**
     * @param title of the page
     * @return true if page title matches
     * @Author: JK
     * Validate Site Map Page title
     */
    public boolean validateSiteMapPageTitle(String title) {
        return driver.getTitle().equalsIgnoreCase(title);
    }

    public boolean clickBackToTop() {
        scrollToBottom();
        mouseHover(backToTopIcon);
        click(backToTopIcon);
        staticWait(1000);
        return !waitUntilElementDisplayed(backToTopIcon, 5);
    }

    public boolean validateBackToTop() {
        scrollToBottom();
        if (waitUntilElementDisplayed(backToTopIcon)) {
            mouseHover(backToTopIcon);
            return getText(backToTop).contains("Back");
        } else {
            return false;
        }
    }

    public boolean blogLinkNotDisplayedInCA() {
        scrollToBottom();
        if (!isDisplayed(blogLink)) {
            return true;
        } else {
            addStepDescription("Blog link is displayed in CA Store");
            return false;
        }
    }

    public boolean clickBOPISLinkFromContentPage() {
        waitUntilElementDisplayed(bopisLink, 3);
        click(bopisLink);
        if (isDisplayed(bopislandingPage_Content)) {
            return true;
        } else {
            addStepDescription("Check the BOPIS link content page redirection");
            return false;
        }
    }

    public boolean clickOrderStatusLink(OrderStatusActions orderStatusActions) {
        waitUntilElementDisplayed(orderStausLink_Footer, 30);
        scrollToBottom();
        click(orderStausLink_Footer);
        return waitUntilElementDisplayed(orderStatusActions.emailIDField, 20);
    }

    public boolean clickHereLinkREdirection() {
        waitUntilElementDisplayed(clickHereLinkStoreLocatorPage, 2);
        click(clickHereLinkStoreLocatorPage);
        waitUntilElementDisplayed(intStoreLists, 5);
        isDisplayed(countryListDropDown);
        click(countryListDropDown);
        return waitUntilElementDisplayed(intStoreLists, 10);
    }

    public boolean contactUSLinkValidation(String linkName, String urlValue, String store) {
        scrollDownUntilElementDisplayed(footerLinksByName(linkName));
        click(footerLinksByName(linkName));
        staticWait(5000);
        String currentURL = getCurrentURL();
        if (currentURL.contains(urlValue)) {
            if (store.equalsIgnoreCase("US")) {
                if (isDisplayed(expandedCustomerField)) {
                    String expandedDisplay = getAttributeValue(expandedCustomerField, "data-accordion-toggle");
                    if (expandedDisplay.equalsIgnoreCase("true")) {
                        return true;
                    }
                } else {
                    addStepDescription("The accordian for the content is not opened up properly, check DTN-2365");
                    return false;
                }
            }
            return true;
        } else {
            addStepDescription("Check the URL in the redirected page");
            return false;
        }
    }
}


