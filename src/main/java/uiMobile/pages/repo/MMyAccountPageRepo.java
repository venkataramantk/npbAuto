package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 11/10/2016.
 */
public class MMyAccountPageRepo extends UiBaseMobile {

    //Overview

    @FindBy(css = ".button-logout")
    public WebElement lnk_Logout;

    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(.,'Offers')]")
    public WebElement offersLink;

    @FindBy(css = ".error-box")
    public WebElement couponError;

    @FindBy(css = ".button-prev")
    public WebElement previousBtn;

    @FindBy(css = ".button-next")
    public WebElement nextBtn;

    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'orders')]")
    public WebElement ordersLnk;

    @FindBy(css = "a[href*='/account/address-book']")
    public WebElement addressbook;

    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'profile')]")
    public WebElement profileInfoLnk;
    
    @FindBy(css = ".profileInformation-link")
    public WebElement profileInfoText;

    @FindBy(css = ".success-text")
    public WebElement successMessage;

    @FindBy(css = ".button-tooltip-wire")
    public WebElement rewardsToolTip;

    @FindBy(css = ".pending-rewards-tooltip")
    public WebElement toolTipExpand;

    @FindBy(css = ".reward-message-tooltip")
    public WebElement rewardsMessage;

    @FindBy(css = ".button-close")
    public WebElement tooltipClose;

    //Birthday savings
    @FindBy(css = "a[href*='account/profile/birthday-savings']")
    public WebElement addBdayInfoBtn;

    @FindBy(css = ".birthdaySavings-link")
    public WebElement birthdaySavingsPage;

    @FindBy(css = ".add-birthday-message")
    public WebElement birthdaySavingsPageContent;

    @FindBy(xpath = "//li[contains(@class,'enabled')]//button")
    public WebElement addAChildBtn;

    @FindBy(css = "ul.birthdays-container>li[class*=birthday-unused-item]")
    public List<WebElement> addAChildBtns;

    @FindBy(name = "childName")
    public WebElement childName;

    @FindBy(css = "select[name='birthMonth']")
    public WebElement birthMth;

    @FindBy(css = "select[name='birthYear']")
    public WebElement birthYear;

    @FindBy(css = "select[name='birthYear'] option")
    public List<WebElement> totalYears;

    @FindBy(css = "[name='gender']")
    public WebElement gender;

    @FindBy(css = ".select-common.select-month")
    public WebElement monthDrpDownDisplay;

    @FindBy(css = ".select-common.select-year")
    public WebElement yearDrpDownDisplay;

    @FindBy(css = ".select-common.input-gender")
    public WebElement genderDrpDownDisplay;

    @FindBy(css = "input[name='digitalSignatureFirstName']")
    public WebElement childFn;

    @FindBy(css = "input[name='digitalSignatureLastName']")
    public WebElement childLn;

    @FindBy(css = ".timestamp-container")
    public WebElement timeStamplbl;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div")
    public WebElement addAChildTAndCChecbox;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div input")
    public WebElement addAChildTAndCChecboxInput;

    @FindBy(linkText = "Privacy")
    public WebElement privacyLink;

    @FindBy(css = "a[href*='/help-center/#faq']")
    public WebElement faqLink;

    @FindBy(css = ".help-center-content")
    public WebElement helpcenter;

    @FindBy(css = ".timestamp-date")
    public WebElement birthTimeStamp;

    @FindBy(css = ".button-cancel-information")
    public WebElement birthCancelBtn;

    @FindBy(css = ".button-add")
    public WebElement addChildbtn;

    public WebElement removeChild(String childName) {
        return getDriver().findElement(By.xpath("//span[text()='" + childName + "']/following-sibling::button"));
    }

    @FindBy(css = ".modal-subtitle")
    public WebElement removeBirthDayInfo;

    @FindBy(css = ".button-remove")
    public WebElement yesRemoveBtn;

    @FindBy(css = ".button-cancel-process")
    public WebElement noBtn;


    @FindBy(css = ".button-modal-close")
    public WebElement closeIcon;


    public WebElement getInline(String field) {
        return getDriver().findElement(By.xpath("//div[contains(text(),'" + field + "')]/..//following-sibling::div/div"));
    }

    @FindBy(xpath = "//div[contains(text(),'Please select a month')]")
    public WebElement birthdayMontherr;

    @FindBy(xpath = "//div[contains(text(),'Please select a year')]")
    public WebElement birthdayYearerr;

    @FindBy(xpath = "//div[contains(text(),'Please select a gender')]")
    public WebElement birthdayGendererr;

    @FindBy(xpath = "//div[contains(text(),'Please accept the terms by selecting the box above.')]")
    public WebElement termsErr;
    //offers and coupons

    //orders link
    @FindBy(css = ".orderDetails-link")
    public WebElement orderDetailsPage;

    @FindBy(css = ".tracking-number")
    public WebElement trackingNo;

    @FindBy(css = "a.orders-link")
    public WebElement ordersPage;

    @FindBy(css = ".button-show-order")
    public WebElement canadaOrders;

    @FindBy(css = ".button-show-international-order")
    public WebElement intOrders;

    public WebElement orderNo(String order) {
        return getDriver().findElement(By.linkText("" + order + ""));
    }

    @FindBy(css = ".order-display")
    public WebElement orderId;

    @FindBy(css = ".order-number")
    public WebElement orderIdvalue;

    @FindBy(css = ".order-display strong:nth-of-type(1)")
    public WebElement ordertext;

    @FindBy(css = ".order-display strong:nth-of-type(2)")
    public WebElement orderDateSec;

    @FindBy(css = ".order-date")
    public WebElement orderDateVal;

    @FindBy(css = ".expiration-date-container span")
    public WebElement orderExpirationDate;

    @FindBy(css = "p.title-description")
    public WebElement orderShippingsec;

    @FindBy(css = ".address-details")
    public WebElement shippingAddress;

    @FindBy(css = ".billing-section .title-description")
    public WebElement orderBillingsec;

    @FindBy(css = ".title-description.title-order-summary")
    public WebElement orderSummaryHeading;

    @FindBy(css = ".store-name a")
    public WebElement storeName;

    @FindBy(css = ".store-address")
    public WebElement storeAddress;

    @FindBy(css = ".today-schedule")
    public WebElement storeTimings;

    @FindBy(css = ".store-phone-number")
    public WebElement storePhoneNo;

    @FindBy(css = ".button-get-directions")
    public WebElement getDirectionsLink;

    public WebElement getOrderDate(String orderNo) {
        return getDriver().findElement(By.xpath("//*[text()='" + orderNo + "']/../../td[1]"));
    }

    public WebElement getOrderNo(String orderNo) {
        return getDriver().findElement(By.xpath("//*[text()='" + orderNo + "']/../../td[2]"));
    }

    public WebElement getOrderTotal(String orderNo) {
        return getDriver().findElement(By.xpath("//*[text()='" + orderNo + "']/../../td[3]"));
    }

    public WebElement getOrderType(String orderNo) {
        return getDriver().findElement(By.xpath("//*[text()='" + orderNo + "']/../../td[4]"));
    }

    public WebElement getOrderStatus(String orderNo) {
        return getDriver().findElement(By.xpath("//*[text()='" + orderNo + "']/../../td[5]"));
    }

    @FindBy(css = ".ecom-information .name")
    public WebElement shippingAddressName;

    @FindBy(css = ".billing-section .name")
    public WebElement billingAddressName;

    @FindBy(css = ".card-info")
    public WebElement cardInfo;

    @FindBy(css = ".header-status")
    public WebElement itemsToggle;

    @FindBy(css = ".product-description")
    public WebElement productDesc;

    @FindBy(css = ".product-title-in-table")
    public WebElement pdtName;

    @FindBy(css = ".text-color")
    public WebElement pdtColor;

    @FindBy(css = ".text-qty")
    public WebElement pdtQty;

    @FindBy(css = ".text-size")
    public WebElement pdtsize;

    @FindBy(css = ".items-total")
    public WebElement itemTotalLabel;

    @FindBy(css = ".order-summary div li")
    public WebElement couponsAndPromotionsLabel;

    @FindBy(css = ".order-summary div li:nth-child(2)")
    public WebElement storePickUpLabel;

    @FindBy(css = ".tax-total")
    public WebElement taxLabel;

    @FindBy(css = ".order-summary .items-total strong")
    public WebElement totalItemsCost;

    @FindBy(css = ".person-name")
    public WebElement pickUpPerson;

    @FindBy(css = ".pickup-person-container h3:nth-child(2)")
    public WebElement alternatePickUpPerson;

    @FindBy(css = ".order-summary div .items-total strong")
    public WebElement coupons;

    @FindBy(css = ".order-summary .tax-total strong")
    public WebElement taxCost;

    @FindBy(css = ".order-summary .balance-total strong")
    public WebElement totalCost;

    //addressbook
    public WebElement makeDefaultLink(String address1) {
        return getDriver().findElement(By.xpath("//span[text()='" + address1 + "']/ancestor::li[@class='address-item-container']/button[@class='button-default-shipping-method']"));
    }

    @FindBy(css = ".Default.Shipping.badge")
    public WebElement defaultshipping;

    @FindBy(xpath = "//div[@class='address-type-badges-container has-default']/preceding-sibling::p")
    //span[@class='default-shipping-method']/../div")
    public WebElement defaultshipaddress;

    @FindBy(xpath = "//span[@class='default-payment-method']/..")
    public WebElement defaultcard;

    @FindBy(css = ".addressBook-link")
    public WebElement addressBookBreadCrumb;

    @FindBy(css = ".delete-button.action-item")//button[@class='button-close']")
    public List<WebElement> removeAddress;

    @FindBy(css = ".button-close")
    public List<WebElement> removeCards;

    @FindBy(css = ".modal-title.modal-only-title")
    public WebElement deleteAddressPage;

    @FindBy(css = ".button-cancel")
    public WebElement cancelDeleteAdd;

    @FindBy(css = ".button-confirm")
    public WebElement okDeleteAdd;

    @FindBy(xpath = "//a[contains(@href,'add-new-address')]")
    public WebElement btn_AddNewAddress;

    @FindBy(css = "input[name='address.firstName']")
    public WebElement newAddress_FirstName;

    @FindBy(css = "input[name='address.lastName']")
    public WebElement newAddress_LastName;

    @FindBy(css = "input[name='address.addressLine1']")
    public WebElement newAddress_AddressLine1;

    @FindBy(css = "input[name='address.addressLine2']")
    public WebElement newAddress_AddressLine2;

    @FindBy(css = "input[name='address.city']")
    public WebElement newAddress_City;

    @FindBy(css = ".select-common.select-state")
    public WebElement newAddress_Statedd;

    @FindBy(css = "select[name='address.state']")
    public WebElement newAddress_State;

    @FindBy(css = "input[name='address.zipCode']")
    public WebElement newAddress_ZipCode;

    @FindBy(css = "input[name='phoneNumber']")
    public WebElement newAddress_PhoneNumeber;

    @FindBy(css = ".select-common.select-country")
    public WebElement newAddress_Countrydd;

    @FindBy(css = "select[name='address.country']")
    public WebElement newAddress_CountryFld;

    @FindBy(css = ".input-checkbox div")
    public WebElement newAddress_SetDefaultAddressCheckBox;

    @FindBy(css = ".input-checkbox div input")
    public WebElement newAddress_SetDefaultAddressCheckBoxInput;

    @FindBy(css = ".button-container>button[type='submit']")
    public WebElement newAddress_AddNewAddressBtn;

    @FindBy(css = ".button-container .cancel")
    public WebElement newAddressCancelBtn;

    @FindBy(css = ".address-verification button[type='submit']")
    public WebElement addressVerificationContinue;

    @FindBy(css = ".edit-address.action-item")
    public WebElement editShipAddressLink;

    @FindBy(css = ".button-edit")
    public WebElement avsEditLink;

    @FindBy(css = ".editAddress-link")
    public WebElement editshippingTitle;

    @FindBy(css = ".button-container button[type='submit']")
    public WebElement updateOnAddressBook;
    //payment , gift cards

    //profile info
    @FindBy(css = ".address-details .name")
    public WebElement name;

    @FindBy(css = ".address-details .address")
    public WebElement address;


    public WebElement getEditAddressLink(String add1) {
        return getDriver().findElement(By.xpath("//span//text()[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + add1.toLowerCase() + "')]/ancestor::li//a[@class='edit-address action-item']"));
    }

    @FindBy(xpath = ".//p[@class='address-additional']/span")
    public WebElement emailID;

    @FindBy(xpath = ".//form[@class='personal-information-form']//input[@name='emailAddress']")
    public WebElement emailIdField;

    @FindBy(css = ".hint-text")
    public WebElement emailIdNote;

    @FindBy(css = ".address-additional")
    public WebElement phoneNoLbl;

    @FindBy(css = ".rewards-message")
    public WebElement myPlaceRewardsLabel;

    @FindBy(css = ".profile-information .edit-button")//".button-action.button-edit")
    public WebElement editButton;

    @FindBy(css = ".button-save")
    public WebElement saveButton;

    @FindBy(css = ".scroll-to-top-icon")
    public WebElement scrollToTop;

    @FindBy(css = ".button-cancel-information")
    public WebElement cancelButton;

    @FindBy(xpath = ".//button[@class='button-information'][.='Change password']")
    public WebElement changePwdBtn;

    @FindBy(css = ".message-password")
    public WebElement passwordInstructions;

    @FindBy(name = "currentPassword")
    public WebElement currentPwdField;

    @FindBy(xpath = "//input[@name='currentPassword']/../../../button")
    public WebElement currentPwdshowHideLink;

    @FindBy(css = "[name='password']")
    public WebElement newPwdField;

    @FindBy(xpath = "//input[@name='password']/..//span[@class='success-text']")
    public WebElement newPwdGreen;

    @FindBy(xpath = "//input[@name='password']/..//span[@class='success-text']")
    public WebElement ConfirmPwdGreen;

    @FindBy(xpath = "//input[@name='confirmPassword']/../../../button")
    public WebElement newPwdshowHideLink;

    @FindBy(name = "confirmPassword")
    public WebElement confirmPwdField;

    @FindBy(xpath = "//input[@name='confirmPassword']/../../../button")
    public WebElement confirmPwdshowHideLink;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-employee div")
    public WebElement tcpEmpChkBox;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-employee div input")
    public WebElement tcpEmpChkBoxInput;

    @FindBy(css = "[name='associateId']")
    public WebElement associateIDField;

    @FindBy(css = ".inline-error-message")
    public WebElement inlineErrMsg;

    @FindBy(xpath = "//span[text()='Email Address']/../../../div/div")
    public WebElement emailError;

    public WebElement getErrorMessage(String field) {
        return getDriver().findElement(By.xpath("//div[text()='" + field + "']/../following-sibling::div/div"));
    }

    public WebElement getInlineErrMsg(String field) {
        return getDriver().findElement(By.xpath("//div[text()='" + field + "']/../following-sibling::div"));
    }

    @FindBy(css = ".error-box")
    public WebElement pageLeverError;

    // END //
    //New Address Book


    //Payment and gift cards
    @FindBy(css = "a[href*='/account/payment']")
    public WebElement giftcards;

    @FindBy(css = ".my-place-rewards-banner")
    public WebElement mprBanner;

    @FindBy(css = "a[href*='/account/payment/add-credit-card']")
    public WebElement addACreditCardBtn;

    @FindBy(css = "a[href*='/account/payment/add-gift-card']")
    public WebElement addAGiftCardBtn;

    @FindBy(css = ".input-common.gift-card-pin input")
    public WebElement pinField;

    @FindBy(css = "iframe[src*=\"https://www.google.com/recaptcha/api2\"]")
    public WebElement captchFrameLoc;

    @FindBy(css = ".recaptcha-checkbox-checkmark")
    public WebElement captchaCheckbox;

    @FindBy(css = ".add-giftcard-message-title")
    public WebElement giftCardMsg;

    @FindBy(css = ".gift-card-save")
    public WebElement giftCardSaveBtn;

    public WebElement cardCaptch(String cardLastdigits) {
        return getDriver().findElement(By.xpath("//strong[contains(.,'" + cardLastdigits + "')]/../following-sibling::section//iframe"));
    }

    public WebElement removeCard(String cardNo) {
        return getDriver().findElement(By.xpath("//strong[contains(.,'" + cardNo + "')]/../preceding-sibling::button"));
    }

    public WebElement EditCardLink(String cardNo) {
        return getDriver().findElement(By.xpath("//strong[contains(.,'" + cardNo + "')]/../following-sibling::a"));
    }

    @FindBy(css = "input[name='cardNumber']")
    public WebElement addPayment_CardNumber;

    @FindBy(xpath = "//h2[contains(.,'Delete card')]")
    public WebElement removeCardPage;

    @FindBy(css = ".select-common.select-exp-mm")
    public WebElement expMonthDropdown;

    @FindBy(css = ".select-exp-mm [name='expMonth']")
    public WebElement addPayment_expmonth;

    @FindBy(css = ".select-common.select-exp-yy")
    public WebElement expYearDropdown;

    @FindBy(css = ".select-exp-yy [name='expYear']")
    public WebElement addPayment_expyr;

    //Billing Address
    @FindBy(css = ".input-common.input-first-name input")
    public WebElement billingFn;

    @FindBy(css = ".input-common.input-last-name input")
    public WebElement billingLn;

    @FindBy(css = ".input-common.input-address-line1 input")
    public WebElement billingAdd1;

    @FindBy(css = ".input-common.input-address-line2 input")
    public WebElement billingAdd2;

    @FindBy(css = ".input-common.input-city input")
    public WebElement billingCity;

    @FindBy(css = ".select-common.select-state select")
    public WebElement billingState;

    @FindBy(css = ".select-common.select-state span")
    public WebElement billingStatelabel;

    @FindBy(css = ".select-common.select-state")
    public WebElement billingStateDD;

    @FindBy(css = ".input-common.input-zip-code input")
    public WebElement billingZip;

    @FindBy(css = ".input-common.input-zip-code div")
    public WebElement billingZipGhostText;

    @FindBy(css = "select[name='address.country']")
    public WebElement billingCountry;

    @FindBy(css = ".select-common.select-country")
    public WebElement billingCountryeDD;

    @FindBy(css = ".button-cancel")
    public WebElement billingCancel;

    @FindBy(css = "button.button-add-new-card")
    public WebElement btnAddCard;

    @FindBy(css = ".address-container")
    public WebElement addressBlock;

    @FindBy(css = ".button-select-shipping-address.select[type='submit']")
    public WebElement updateAddressBtn;

    @FindBy(xpath = "//input[@name='cardNumber']/../following-sibling::div")
    public WebElement ccErr;

    @FindBy(xpath = "//select[@name='expMonth']/../following-sibling::div")
    public WebElement expMotnhErr;

    @FindBy(xpath = "//select[@name='expYear']/../following-sibling::div")
    public WebElement expYearErr;

    @FindBy(xpath = "//input[@name='address.firstName']/../following-sibling::div")
    public WebElement fnameErr;

    @FindBy(xpath = "//input[@name='address.lastName']/../following-sibling::div")
    public WebElement lnameErr;

    @FindBy(xpath = "//input[@name='address.addressLine1']/../following-sibling::div")
    public WebElement address1Err;

    @FindBy(xpath = "//input[@name='address.city']/../following-sibling::div")
    public WebElement cityErr;

    @FindBy(xpath = "//*[@name='address.state']/../following-sibling::div")
    public WebElement stateErr;

    @FindBy(xpath = "//input[@name='address.zipCode']/../following-sibling::div")
    public WebElement zipErr;


    @FindBy(css = ".view")
    public WebElement viewmyacctLink;

    @FindBy(css = ".myPlace-customer>h4")
    public WebElement lbl_FirstName;

    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'payment')]")
    public WebElement lnk_PaymentGC;

    @FindBy(xpath = "//*[@id='box'] //a[contains(.,'online order history') or contains(.,'historique des commandes en ligne')]")
    public WebElement tab_OnlineHistory;

    @FindBy(css = ".overlay-button-container button.button-confirm")
    public WebElement btnOverlayConfirmRemove;

    @FindBy(css = ".credit-card-item-container .button-close")
    public WebElement paymentInfo_RemoveLink;

    @FindBy(xpath = "//a[contains(@href,'payment/add-credit-card')]")
    public WebElement btn_AddNewCard;

    @FindBy(xpath = "//*[@id='orderHistory']//tr[1][@class='triple-row']")
    public WebElement orderHistory_FirstRow;


    @FindBy(xpath = "//*[@class='points-history-table']/tbody/*[@class='single-row']/*[contains(.,'Total Points:')]")
    public WebElement totalPoints;

    @FindBy(css = ".my-account-navigation-container li:nth-child(1)")
    public WebElement lnk_AccountOverView;


    //Added by me

    @FindBy(css = "[name='firstName']")
    public WebElement firstNameField;

    @FindBy(css = "[name='lastName']")
    public WebElement lastNameField;

    @FindBy(css = "img[alt='PLACE CARD']")
    public WebElement imgPLCC;

    @FindBy(css = "img[alt='VISA']")
    public WebElement imgVISA;

    @FindBy(css = "img[alt='MC']")
    public WebElement imgMC;

    @FindBy(css = "img[alt='DISC']")
    public WebElement imgDiscover;

    @FindBy(css = "img[alt='AMEX']")
    public WebElement imgAMEX;

    @FindBy(css = "img[alt='Card icon']")
    public WebElement imgGC;

    @FindBy(css = ".breadcrum-link")
    public WebElement addressBookText;

    @FindBy(css = ".default-store-title")
    public WebElement defautStoreTitle;

    @FindBy(css = ".button-primary.button-update")
    public WebElement findStoreBtn;

    @FindBy(css = ".profile-information-only-title")
    public WebElement birthdaySavings;

    @FindBy(css = ".birthday-information-container a")
    public WebElement birthdaysavingsbtn;

    @FindBy(css = ".link-view-all-rewards")
    public WebElement viewRewardsLink;


    @FindBy(xpath = ".//li[@class='my-rewards-navigation-item'][2]")
    public WebElement pointsHistoryLink;

    @FindBy(css = ".button-merge-account-request")
    public WebElement custmorServiceLink;

    @FindBy(css = ".email-title")
    public WebElement emailUs;

    @FindBy(css = ".input-title")
    public WebElement termsAndCondition;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div")
    public WebElement termsCheckBox;


    @FindBy(css = ".button-action.button-cancel-information")
    public WebElement cancelLink;

    //AddressBook


    // @FindBy(xpath = ".//div[@class=\"address-container\"]")
    @FindBy(xpath = ".//p[@class=\"address-details\"]")
    public List<WebElement> savedAddress_MyAccount;

    @FindBy(xpath = ".//button[@class=\"uncondense-view-and-print-button\"]")
    public List<WebElement> viewAndPrintCoupon;

    @FindBy(xpath = ".//span[@class=\"uncondense-expire-information\"]")
    public List<WebElement> expiryDateCoupon;

    @FindBy(css = ".uncondense-apply-coupons-button")
    public List<WebElement> applyToBagButton;

    @FindBy(xpath = ".//*[@alt='AUTOMATION 10% OFF']//ancestor::li//button[@class=\"uncondense-apply-coupons-button\"]")
    public WebElement apply10Off;

    @FindBy(css = ".uncondense-applied-coupons-button")
    public WebElement removeButton;

    @FindBy(css = ".default-store-container .default-store-title")
    public WebElement defaultFavStore;

    @FindBy(css = ".order-list-container button.accordion-toggle")
    public WebElement orderListContainerToggle;

    @FindBy(css = "strong.product-price-within-offer")
    public WebElement itemOriginalPriceNotStrikedOut;

    @FindBy(css = ".card-type")
    public WebElement cardType;

    @FindBy(css = ".card-suffix")
    public WebElement cardSuffix;

    @FindBy(css = ".uncondense-expire-information")
    public WebElement couponExpireInfo;

    @FindBy(css = "div.order-status-container strong.message")
    public WebElement orderStatusLabel;

    @FindBy(css = ".custom-loading-icon")
    public WebElement orderDetailsSpinner;

    @FindBy(css = ".empty-address-book-and-button a")
    public WebElement emptyAddAddressBtn;

    @FindBy(css = ".cards-empty-container a")
    public WebElement emptyAddCardBtn;

    @FindBy(css = ".balance")
    public WebElement giftCardInfo;

    @FindBy(css = ".complete-address-section .complete-profile-zip span")
    public WebElement zipCodeInMailingAddressSection;

    @FindBy(css = ".complete-profile-heading")
    public WebElement completedProfileHeading;

    @FindBy(css = ".complete-address-section button")
    public WebElement completeAddressBtn;

    @FindBy(css = "input[name='phoneNumber']")
    public WebElement phoneNoFld;

    @FindBy(css = "input[name='address.zipCode']")
    public WebElement zipCodeField;

    @FindBy(css = ".button-continue")
    public WebElement continueBtn;

    @FindBy(css = ".rewards-status-container")
    public WebElement mprRewardsHeader;

    @FindBy(css = ".address-item-container")
    public List<WebElement> shippingAddressContainers;

    @FindBy(xpath = "//div[@class='address-type-badge']/ancestor::li//button")
    public WebElement defaultShippingAddressDeleteBtn;

    @FindBy(css = ".title-billing-address")
    public WebElement mailingAddressTitle;

    @FindBy(css = ".default-payment-method")
    public WebElement defaultBilling;

    @FindBy(css = ".custom-select-button.dropdown-address-book-button-closed")
    public WebElement addressBookDDInMailing;

    @FindBy(css = ".address-you-entered .checkbox-combo.address-options")
    public WebElement yourEnteredAddBtn;

    @FindBy(css = ".address-you-entered .checkbox-combo.address-options input")
    public WebElement yourEnteredInputAddBtn;

    @FindBy(xpath = "(//li[@class='my-account-navigation-item']//a[contains(@href,'rewards')])[1]")
    public WebElement rewardsLnk;

    @FindBy(xpath = "//ul[@class='my-rewards-navigation-container']//a[contains(@href,'rewards/offers')]")
    public WebElement offersLnk;

    @FindBy(css = "div.uncondense-information-coupon .coupon-value")
    public List<WebElement> couponValueList;

    @FindBy(css = "div.coupon-dots button.button-pagination-dot.active")
    public WebElement currentDisplayedCoupon;

    @FindBy(css = ".coupon-dots button")
    public List<WebElement> couponDots;
}
