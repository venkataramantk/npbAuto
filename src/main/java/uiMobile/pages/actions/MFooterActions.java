package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.actions.MyPlaceRewardsActions;
import uiMobile.pages.repo.MFooterRepo;

import java.util.Calendar;

/**
 * Created by JKotha on 11/22/2017.
 */
public class MFooterActions extends MFooterRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MFooterActions.class);

    public MFooterActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * click on language button
     *
     * @return true if change country is displayed
     */
    public boolean clickOnLanguageButton() {
        try {
            staticWait();
            javaScriptClick(mobileDriver, languageButton);
            waitUntilElementDisplayed(shipToCountryDropdown, 5);
            return waitUntilElementDisplayed(shipToModalDialog);
        } catch (Throwable t) {
            staticWait();
            javaScriptClick(mobileDriver, languageButton);
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
        clickOnLanguageButton();
        selectDropDownByVisibleText(shipToCountryDropdown, country);
        click(saveBtn);
        verifyElementNotDisplayed(saveBtn, 10);
        waitUntilElementDisplayed(flagCountryImg);
        if (country.equalsIgnoreCase("USA")) {
            return getAttributeValue(flagCountryImg, "src").contains("US.gif");//waitUntilElementDisplayed(imgFlagUS, 30);
        } else if (country.equalsIgnoreCase("CANADA")) {
            return getAttributeValue(flagCountryImg, "src").contains("CA.gif");//waitUntilElementDisplayed(imgFlagCA, 30);
        } else if (country.equalsIgnoreCase("INDIA")) {
            return getAttributeValue(flagCountryImg, "src").contains("IN.gif");//waitUntilElementDisplayed(imgFlagCA, 30);
        } else
            return false;
//            return verifyElementNotDisplayed(flagCountryImg, 10);
    }


    /**
     * Chagne country and language form footer
     *
     * @param country  to ship
     * @param language to display
     * @return true if country changed
     */
    public boolean changeCountryAndLanguage(String country, String language) {
        clickOnLanguageButton();
        selectDropDownByValue(shipToCountryDropdown, country);
        selectDropDownByVisibleText(langDropdown, language);
        staticWait(3000);
        click(saveBtn);
//        staticWait(5000);
        verifyElementNotDisplayed(saveBtn, 30);
        waitUntilElementDisplayed(flagCountryImg);
        String initials = country.substring(0, 2).toUpperCase();
        return getAttributeValue(flagCountryImg, "alt").contains(initials);
    }

    /**
     * Expand Footer section
     *
     * @param section to expand
     */
    public void openFooterSection(String section) {
        String cls = sectionLink(section).getAttribute("class");
        if (!cls.contains("open")) {
            scrollDownToElement(sectionLink(section));
            javaScriptClick(mobileDriver, sectionLink(section));
            waitUntilElementDisplayed(sectionLinkOpen(section), 20);
        }
    }

    public boolean clickPLCCImg() {
        click(sectionLink("My Place Rewards Credit Card"));
        click(plccImg);
        return waitUntilElementDisplayed(applayOrAcceptOffer);
    }

    public boolean clickMPRLOGO() {
        MyPlaceRewardsActions action = new MyPlaceRewardsActions(mobileDriver);
        scrollDownToElement(sectionLink("My Place Rewards"));
        click(sectionLink("My Place Rewards"));
        click(mprlogo);
        waitUntilElementDisplayed(action.mprLandingLogo, 10);
        return mobileDriver.getCurrentUrl().contains("/us/content/myplace-rewards-page");
    }

    public boolean goTrackOrder() {
        scrollDownToElement(sectionLink("Help Center"));
        javaScriptClick(mobileDriver, sectionLink("Help Center"));
        waitUntilElementDisplayed(sectionLinkOpen("Help Center"), 20);
        click(link_OrderStatus);
        return waitUntilElementDisplayed(orderDetailsSpinner, 10);
    }

    public boolean trackOrderFromFooter(String email, String order) {
        MMyAccountPageActions action = new MMyAccountPageActions(mobileDriver);
        goTrackOrder();
        clearAndFillText(trackerEmail, email);
        clearAndFillText(ordernoFld, order);
        click(trackorderBtn);
        staticWait(15000);
        return waitUntilElementDisplayed(orderDetailsSpinner, 20);
    }

    public boolean trackOrder(String email, String order) {
        clearAndFillText(trackerEmail, email);
        clearAndFillText(ordernoFld, order);
        click(trackorderBtn);
        return waitUntilElementDisplayed(orderDetailsSpinner, 20);
    }

    public boolean validateAllFieldsInTrackOrder() {
        return waitUntilElementDisplayed(trackerEmail, 1) &&
                waitUntilElementDisplayed(ordernoFld, 1) &&
                waitUntilElementDisplayed(needHelpLink, 1) &&
                waitUntilElementDisplayed(trackinternationlOrder, 1) &&
                waitUntilElementDisplayed(trackorderBtn, 1) &&
                waitUntilElementDisplayed(loginlink, 1) &&
                waitUntilElementDisplayed(closeLinkOnShipTOModal, 1);
    }

    public boolean validateAllFieldsInShipToPage() {
        return waitUntilElementDisplayed(shipToModalDialog) &&
                waitUntilElementDisplayed(saveBtn) &&
                waitUntilElementDisplayed(changePrefernceTxt) &&
                waitUntilElementDisplayed(noteTxt);
    }

    public boolean validateErrorMessagesinTrackOrder() {
        click(ordernoFld);
        tabFromField(ordernoFld);
        tabFromField(trackerEmail);
        return waitUntilElementDisplayed(orderNoErrorMsg) &&
                waitUntilElementDisplayed(emailErrorMsg);
    }

    public boolean validateNeedHelpLink() {

        return waitUntilElementDisplayed(needHelpLink);
    }


    public boolean validateTermsAndConditionsLink() {
        scrollToBottom();
        waitUntilElementDisplayed(termsAndCondition, 20);
        click(termsAndCondition);
        waitUntilElementDisplayed(acticTAndCheader);
        String header = getText(acticTAndCheader);
        return header.contains("Terms and Conditions");
    }

    public boolean shipTo_termsAndConditionsLink() {
        scrollToBottom();
        waitUntilElementDisplayed(shipToTermsAndCondition, 20);
        click(shipToTermsAndCondition);
        waitUntilElementDisplayed(acticTAndCheader);
        String header = getText(acticTAndCheader);
        return header.contains("Terms and Conditions");
    }

    public boolean validatePrivacyPolicyLink() {
        scrollToBottom();
        waitUntilElementDisplayed(privacyPolicy);
        click(privacyPolicy);
        waitUntilElementDisplayed(activeSessionHeader);
        String header = getText(activeSessionHeader);
        return header.contains("Privacy Policy");
    }

    public boolean validateCaliforniaAct() {
        scrollToBottom();
        waitUntilElementDisplayed(californiaSupplyChainLink);
        click(californiaSupplyChainLink);
        waitUntilElementDisplayed(supplyChaninHeader);
        String header = getText(supplyChaninHeader);
        return header.contains("Supply Chain");
    }

    public boolean validateSiteMap() {
        scrollToBottom();
        waitUntilElementDisplayed(siteMapLink);
        click(siteMapLink);
        waitUntilElementDisplayed(siteMapHeader);
        String header = getText(siteMapHeader);
        return header.equalsIgnoreCase("SITE MAP");
    }

    /**
     * Verify blog link under About Us section
     *
     * @return
     */
    public boolean validateBlog() {
        waitUntilElementDisplayed(blogLink);
        javaScriptClick(mobileDriver, blogLink);
        switchToWindow();
        return getCurrentURL().equalsIgnoreCase("https://blog.childrensplace.com/");
    }

    public boolean validateOutfits() {
        PanCakePageActions action = new PanCakePageActions(mobileDriver);
        waitUntilElementDisplayed(girlsOutfittingLink);
        click(girlsOutfittingLink);
        return action.getPlpCategory().contains("Outfits");
    }

    public boolean validateCopyRightInfo() {
        scrollToBottom();
        waitUntilElementDisplayed(copyRightInfo);
        String copyInfo = getText(copyRightInfo);
        Calendar now = Calendar.getInstance();
        String year = Integer.toString(now.get(Calendar.YEAR));
        return copyInfo.contains(year) &&
                copyInfo.contains("Big Fashion, Little Prices");
    }

    public boolean validateSocialNetworkingLinks() {
        scrollToBottom();
        return waitUntilElementDisplayed(link_Facebook) &&
                waitUntilElementDisplayed(link_Instagram) &&
                waitUntilElementDisplayed(link_Twitter) &&
                waitUntilElementDisplayed(link_Pinterest);
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

    public boolean validateEmptyStyleSquadFields(String fnameErr, String lNameErr, String emailErr, String yearErr, String cityErr, String stateErr, String zipErr, String childnameErr, String monthErr, String genderErr,
                                                 String socialMediaErr, String waiverErr, String digitalErr) {
        click(styleSquad_Submit);
        waitUntilElementDisplayed(getError("parentfirst"));
        return getText(getError("parentfirst")).equals(fnameErr) &&
                getText(getError("parentlast")).equals(lNameErr) &&
                getText(getError("email")).equals(emailErr) &&
                getText(getError("parentbirth")).equals(yearErr) &&
                getText(getError("ciy")).equals(cityErr) &&
                getText(getError("state")).equals(stateErr) &&
                getText(getError("zip")).equals(zipErr) &&
                getText(getError("childname1")).equals(childnameErr) &&
                getText(getError("month1")).equals(monthErr) &&
                getText(getError("year1")).equals(yearErr) &&
                getText(getError("gender1")).equals(genderErr) &&
                getText(getError("socialMediaGuidelinesConfirmation")).equals(socialMediaErr) &&
                getText(getError("waiverAndReleaseConfirmation")).equals(waiverErr) &&
                getText(getError("signature")).equals(digitalErr);
    }

    public void fillStyleSquadAndSubmit(String fname, String lName, String email, String city, String state, String zip, String childname, String childMonth, String gender, String digitalSign) {
        waitUntilElementDisplayed(styleSquad_Espot);
        clearAndFillText(styleSquad_FirstName, fname);
        clearAndFillText(styleSquad_LastName, lName);
        clearAndFillText(styleSquad_Email, email);

        //To DO need to remove the hard coded value
        selectDropDownByVisibleText(styleSquad_ParentYear, "2000");
        clearAndFillText(styleSquad_City, city);
        selectDropDownByValue(styleSquad_State, state);
        clearAndFillText(styleSquad_Zip, zip);
        clearAndFillText(styleSquad_ChildName, childname);
        selectDropDownByValue(styleSquad_BirthMon, childMonth);
        //To DO need to remove the hard coded value
        selectDropDownByValue(styleSquad_BirthYear, "2017");
        selectDropDownByValue(styleSquad_Gender, gender);
        select(styleSquad_MediaGuide, styleSquad_MediaGuide);
        select(styleSquad_WavierRelease, styleSquad_WavierRelease);
        clearAndFillText(styleSquad_DigitalSign, digitalSign);
    }

    /**
     * Created By Pooja
     * This Method clicks on Gift Cards link under Shopping section and verify Application moved to Gift Card Page
     */
    public boolean moveToGiftCardPage(MGiftCardsPageActions mGiftCardsPageActions) {
        javaScriptClick(mobileDriver, link_GiftCards);
        return waitUntilElementDisplayed(mGiftCardsPageActions.giftCardPage);
    }

    public boolean fav_recommAsReg() {
        waitUntilElementsAreDisplayed(recomm_facIcons, 20);
        javaScriptClick(mobileDriver, recomm_facIcons.get(0));
        return waitUntilElementDisplayed(favorited_item);
    }

    public boolean fav_recommAsGuest(MLoginPageActions loginPageActions) {
        waitUntilElementsAreDisplayed(recomm_facIcons, 20);
        javaScriptClick(mobileDriver, recomm_facIcons.get(0));
        return waitUntilElementDisplayed(loginPageActions.emailAddrField);
    }

    /**
     * validate Mom space
     *
     * @return
     */
    public boolean validateMomSpace(String url) {
        waitUntilElementDisplayed(link_MomSpace, 20);
        click(link_MomSpace);
        waitUntilElementDisplayed(img_TheMomSpace, 30);
        return mobileDriver.getCurrentUrl().equalsIgnoreCase(url);
    }

    /**
     * Click Faqs link under help center
     */
    public boolean gotFaqs() {
        waitUntilElementDisplayed(link_FAQs);
        click(link_FAQs);
        return waitUntilElementDisplayed(helpCenterHomePage, 10);
    }

    /**
     * Click bopis link from help center
     *
     * @return
     */
    public boolean clickBopisLink() {
        if (waitUntilElementDisplayed(bopis_Link, 20)) {
            click(bopis_Link);
            return waitUntilElementDisplayed(bopisFaqPage, 10);
        } else {
            return false;
        }
    }

    public boolean verifyInternationTrackPage() {
        waitUntilElementDisplayed(trackinternationlOrder, 20);
        click(trackinternationlOrder);
        switchToWindow();
        return mobileDriver.getCurrentUrl().equalsIgnoreCase("https://www.borderfree.com/order-status/");
    }

    public boolean validateContactUsLink() {
        scrollToBottom();
        openFooterSection("Help Center");
        waitUntilElementDisplayed(contactUsLink, 20);
        click(contactUsLink);
        waitUntilElementDisplayed(selectedSection);
        return getCurrentURL().contains("/help-center/#customerServiceli");
    }

    /**
     * Click on create account link in Footer and verify create account page is displayed
     *
     * @param mCreateAccountActions
     * @return true if create account page is displayed
     */
    public boolean validateCreateAccountLink(MCreateAccountActions mCreateAccountActions) {
        scrollToBottom();
        openFooterSection("Shopping");
        waitUntilElementDisplayed(createAccountLink, 10);
        click(createAccountLink);
        return waitUntilElementDisplayed(mCreateAccountActions.createAccountButton);
    }

    /**
     * Validate Return Policy link in Footer section
     *
     * @return
     */
    public boolean validateReturnPolicyLink() {
        scrollToBottom();
        openFooterSection("Help Center");
        waitUntilElementDisplayed(returnPolicyLink, 10);
        click(returnPolicyLink);
        waitUntilElementDisplayed(selectedSection, 20);
        return getText(selectedSection).equalsIgnoreCase("Policies");
    }

    /**
     * Click Member Benefits link
     *
     * @return
     */
    public boolean validateMemberBenefitsLink() {
        click(sectionLink("My Place Rewards"));
        waitUntilElementDisplayed(membersBenefitLink, 20);
        click(membersBenefitLink);
        return mobileDriver.getCurrentUrl().contains("/us/content/myplace-rewards-page");
    }

    /**
     * Validate Points balance link
     *
     * @param mLoginPageActions
     * @return
     */
    public boolean validateCheckPointsBalance(MLoginPageActions mLoginPageActions) {
        scrollToBottom();
        click(sectionLink("My Place Rewards"));
        waitUntilElementDisplayed(checkPointsBalance, 10);
        click(checkPointsBalance);
        return waitUntilElementDisplayed(mLoginPageActions.emailAddrField);
    }

    /**
     * Validate Size chart link
     *
     * @return
     */
    public boolean validateSizeChart() {
        scrollToBottom();
        openFooterSection("Shopping");
        waitUntilElementDisplayed(link_SizeChart, 10);
        javaScriptClick(mobileDriver, link_SizeChart);
        return getCurrentURL().contains("content/size-chart");
    }

    /**
     * Click find a store link
     *
     * @param mStoreLocatorPageActions
     * @return
     */
    public boolean validateStoreLocatorLink(MStoreLocatorPageActions mStoreLocatorPageActions) {
        scrollToBottom();
        openFooterSection("Shopping");
        waitUntilElementDisplayed(storeLocatorTest, 10);
        click(storeLocatorTest);
        return waitUntilElementDisplayed(mStoreLocatorPageActions.searchStoreField);
    }

    /**
     * Click find a store link
     *
     * @return
     */
    public boolean validateSeasonalLookBooksLink() {
        scrollToBottom();
        openFooterSection("Shopping");
        waitUntilElementDisplayed(seasonalLookBooksLink, 10);
        click(seasonalLookBooksLink);
        return waitUntilElementsAreDisplayed(differentLookbooks, 10);
    }

    /**
     * Verify download odf and next curosel
     *
     * @return
     */
    public boolean checkPdfLinkInLookBook() {
        return waitUntilElementDisplayed(downloadLookBookPdfBtn) &&
                waitUntilElementDisplayed(nextCarousel);
    }

    /**
     * Validate Careers Page
     *
     * @return
     */
    public boolean validateCareersPage() {
        scrollToBottom();
        openFooterSection("About Us");
        waitUntilElementDisplayed(careersLink, 10);
        click(careersLink);
        return waitUntilElementDisplayed(careersPage, 10);
    }

    /**
     * validate Gift card balance page
     *
     * @return
     */
    public boolean validateGiftCardBalanceLink(MGiftCardsPageActions mGiftCardsPageActions) {
        openFooterSection("Help Center");
        waitUntilElementDisplayed(gcBalanceLink, 10);
        click(gcBalanceLink);
        return waitUntilElementDisplayed(mGiftCardsPageActions.giftCardPage, 10);
    }

    public boolean clickEmailUsLink(MEmailUsPageActions mEmailUsPageActions) {
        waitUntilElementDisplayed(contactUsEmilLink, 10);
        javaScriptClick(mobileDriver, contactUsEmilLink);
        return waitUntilElementDisplayed(mEmailUsPageActions.firstName, 20);
    }

    public void goToGiftcardsPage() {
        openFooterSection("Shopping");
        click(link_GiftCards);
    }

    public boolean verifyMPRSection() {
        return waitUntilElementDisplayed(mprSection, 10);
    }
}

