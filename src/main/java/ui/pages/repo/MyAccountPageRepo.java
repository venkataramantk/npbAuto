package ui.pages.repo;

import org.dom4j.datatype.DatatypeElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 6/7/2016.
 */
public class MyAccountPageRepo extends HeaderMenuRepo {

    @FindBy(css = "select[name='amountToRedeem']")
    public WebElement amtToRedeemDropDown;

    @FindBy(css = "#convertPoint")
    public WebElement convertPointsButton;

    @FindBy(css = "#SlidesContainer div")
    public List<WebElement> availableRewards;

    @FindBy(css = "#TcpViewProgramDetails")
    public WebElement lnk_ProgramDetails;

    @FindBy(xpath = "//*[@id='redeemPoints']//a[contains(.,'FAQs')]")
    public WebElement lnk_ProgramFAQs;

    @FindBy(xpath = "//*[@class='program-details']/*[@class='links']/a[contains(.,'FAQs')]")
    public WebElement lnk_FAQs;

    @FindBy(xpath = "//*[@id='redeemPoints']//a[contains(.,'Terms & Conditions')]")
    public WebElement lnk_ProgramTerms;

    @FindBy(xpath = "//*[@class='program-details']//a[contains(.,'Terms & Conditions')]")
    public WebElement lnk_TermsAndCondition;

    @FindBy(xpath = "//*[@class='program-details']//a[contains(.,'Apply today')]")
    public WebElement lnk_ApplyToday;

    @FindBy(xpath = "//*[@id='redeemPoints']//*[@class='pointCount']")
    public WebElement lbl_AvblePts;

    @FindBy(xpath = ".//*[@class='mpr-landing-2016']//*[contains(@alt,'My Place Rewards')]")
    public WebElement page_ProgramDetails;

    @FindBy(xpath = "//*[@class='help-right-content']//h1[contains(.,'Membership')]")
    public WebElement page_FAQs;

    @FindBy(xpath = "//*[@class='help-right-content']//h1[contains(.,'Terms & Conditions')]")
    public WebElement page_TermsAndCondition;

    @FindBy(xpath = "//a[contains(@href,'#personalInfo')]")
    public WebElement lbl_PersonalInfoTab;

    @FindBy(xpath = "//*[@id='customer-name']//a[contains(.,'Hi')]")
    public WebElement lbl_GreetingLink;

    @FindBy(css = "#ui-dialog-title-updateTcpAccountDialog")
    public WebElement editAddrModalWindow;

    @FindBy(css = ".myPlace-customer>h4")
    public WebElement lbl_FirstName;

    /*@FindBy(xpath = "/*//*[@id='tcpUserName0'][contains(.,'Lname')]")
    public WebElement lbl_LastName;*/

    @FindBy(id = "tcpEmail1")
    public WebElement lbl_EmailAddress;

    @FindBy(xpath = "//*[@id='personalInfo']//span[contains(.,'********')]")
    public WebElement lbl_LogonPassword;

    @FindBy(id = "tcpCity")
    public WebElement lbl_Zip;

    @FindBy(id = "tcpPhone")
    public WebElement lbl_Phone;

    @FindBy(id = "TcpCompleteHomeAddress")
    public WebElement lbl_CompleteAddressLink;

    //Complete your address overlay
    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_address1_In_Register_1")
    public WebElement txt_Address1;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_city_In_Register_1")
    public WebElement txt_City;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_state_In_Register_1")
    public WebElement drp_State;

    @FindBy(id = "WC_EditAccount_Submit")
    public WebElement btn_Save;

    @FindBy(xpath = "//div[@class='ui-dialog-buttonset']/button[1]")
    public WebElement useAsEnteredButton;

    @FindBy(id = "tcpStreets")
    public WebElement lbl_NewAddr;

    @FindBy(id = "tcpCity")
    public WebElement lbl_NewCity;

    @FindBy(xpath = "//*[@id='personalInfo']//span[contains(.,'Birthday Savings')]")
    public WebElement lbl_BdayClub;

    @FindBy(xpath = "//*[@id='personalInfo']//span[contains(.,'Your Birth Month')]")
    public WebElement lbl_BdayMonth;

    @FindBy(id = "TcpUpdateEmail")
    public WebElement lnk_EmailUpdate;

    @FindBy(id = "WC_TCPEditPersonalEmail_FormInput_email_In_Register_1")
    public WebElement txt_EmailId;

    @FindBy(id = "WC_TCPEditPersonalEmail_FormInput_emailVerify_In_Register_1")
    public WebElement txt_EmailIdConf;

    @FindBy(id = "WC_EditAccount_Submit")
    public WebElement btn_EmailUpdateSubmit;

    @FindBy(xpath = "//*[@id='customer-name']//a[contains(.,'(Log Out)')]")
    public WebElement lnk_Logout;

    @FindBy(xpath = "//span[contains(.,'Use As Entered')]")
    public WebElement btn_MellisaData;

    @FindBy(css = ".address-verification button[type='submit']")
    public WebElement addressVerificationContinue;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Email addresses must match')]")
    public WebElement err_EmailAddressNotMatch;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Email format is invalid')][contains(@style,'block')]")
    public WebElement err_EmailFormatInvalid;

    @FindBy(xpath = "//span[contains(.,'close')]")
    public WebElement close_EditEmailAddressOverlay;

    @FindBy(xpath = "//*[@id='TcpUpdatePassword']")
    public WebElement update_Password;

    @FindBy(xpath = "//input[contains(@id,'logonPassword_In_Register_1')]")
    public WebElement edit_Password;

    @FindBy(xpath = "//input[contains(@id,'logonPasswordVerify_In_Register_1')]")
    public WebElement edit_ConfirmPassword;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Your account has been updated')]")
    public WebElement Changing_ConfirmationMsg;

    @FindBy(xpath = "//*[@id='tcpUserName0']")
    public WebElement lbl_UserName;

    @FindBy(xpath = "//*[@id='box']//h4[contains(.,'Welcome')]")
    public WebElement lbl_WelcomeAndFirstname;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Please enter a valid password')]")
    public WebElement err_PleaseEnterValidPassword;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Passwords must match')]")
    public WebElement err_PasswordNotMatch;

//    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'A strong password is required. Strong passwords are at least 8 characters and must contain at least one uppercase letter, one number and one special character. It cannot match your User ID or repeat your last used password.')]")
//    public WebElement err_PasswordNotinConstraint;

    public WebElement err_PasswordNotinConstraint(String errMessage) {
        return getDriver().findElement(By.xpath("//*[@id='editRegister']//label[contains(.,'" + errMessage + "')]"));
    }

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Your new password cannot replicate any recent passwords you may have used.  Please select another password and try again.')]")
    public WebElement err_NewPasswordCannotReplicateOldPassword;

    @FindBy(xpath = "//*[@id='employee']")
    public WebElement chk_ChildrenPlaceEmployee;

    @FindBy(css = ".associateInput")
    public WebElement tcpEmployeeID_Fld;

    @FindBy(xpath = "//*[@id='tcpEmployeeId']")
    public WebElement Unchecked_TCPEmployeeID;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Please enter a valid associate ID')]")
    public WebElement err_PleaseEnterAValidAssociateID;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'The employee id you entered does not exist. Type a different  employee id and try again.')]")
    public WebElement err_InvalidAssociateIDMsg;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'The employee id you entered is different from the one that is already bound to current account')]")
    public WebElement err_ThisEmployeeIDAlreadyExistInAnotherAccount;

    @FindBy(xpath = "//*[@id='editRegister']//label[@style='display: block;']")
    public WebElement check_DisplayError;

    @FindBy(xpath = "//*[@id='TcpUpdateBirthday']")
    public WebElement btn_EditBirthdaySavings;

    @FindBy(xpath = "//input[@id='terms']/ancestor::p[1][contains(.,'legal')]")
    public WebElement termsBirthdaySavingsContent;

    @FindBy(css = ".ui-icon.ui-icon-closethick")
    public WebElement closeBDayModal;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'A month has not been specified. Do not specify any birthday values if you prefer to keep this information confidential.')]")
    public WebElement err_MonthNotSelectedMsg;

    @FindBy(xpath = "//*[@id='_null_r_1']//label[contains(.,'please indicate the value of ChildName')]")
    public WebElement err_PleaseIndicateTheValueOfChildName;

    @FindBy(xpath = "//*[@id='_null_r_1']//label[contains(.,'please indicate the value of ChildBirthYear')]")
    public WebElement err_PleaseIndicateTheValueOfChildBirthYear;

    @FindBy(xpath = "//*[@id='_null_r_1']//label[contains(.,'please indicate the value of ChildBirthMonth')]")
    public WebElement err_PleaseIndicateTheValueOfChildBirthMonth;

    @FindBy(xpath = "//*[@id='_null_r_1']//label[contains(.,'please indicate the value of ChildGender')]")
    public WebElement err_PleaseIndicateTheValueOfChildGender;

    @FindBy(xpath = "//*[@id='addChildButton']")
    public WebElement btn_AddChildButton;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Please accept the terms by selecting the box below and re-submit')]")
    public WebElement err_PleaseAcceptTermAndCondition;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Type a name in the Last name field.')]")
    public WebElement err_TypeANameInLastNameField;

    @FindBy(id = "TcpUpdateHomeAddress")
    public WebElement editHomeAddress;

    @FindBy(id = "tcpUserName0")
    public WebElement FirstNameLastName;

    @FindBy(xpath = "//*[@id='editRegister']//input[@name='firstName']")
    public WebElement txt_FirstNameFromDigitalSignature;

    @FindBy(xpath = "//*[@id='editRegister']//input[@name='lastName']")
    public WebElement txt_LastNameFromDigitalSignature;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Type a name in the Last name field.')]")
    public WebElement err_LastNameFieldMissing;

    @FindBy(id = "TcpUpdateHomeAddress")
    public WebElement editAddress;

    @FindBy(xpath = "//*[@id='terms']")
    public WebElement chk_TermAndConditionInBirthdayClub;

    @FindBy(xpath = "//*[@id='editRegister']//label[contains(.,'Your account has been updated')]")
    public WebElement lbl_YourAccountHasBeenUpdated;

    @FindBy(xpath = "//*[@id='WC_TCPEditBirthdayClub_FormInput_ChildBirthMonth_null_r_1__In_Register_1']")
    public WebElement click_SelectMonthFromYourBirthdayMonth;

//    @FindBy(xpath = "//*[@id='WC_TCPEditBirthdayClub_FormInput_birth_month_In_Register_1")
//    public WebElement select_AnyMonthFromYourBirthdayMonth;

    /*@FindBy(xpath = "/*//*[@id='box']//a[contains(.,'address book') or contains(.,'libreta de direcciones') or contains(.,'carnet')]")*/
    /*@FindBy(css = "a[href='#addressBook']")*/
    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'address-book')]")
    public WebElement lnk_AddressBook;

    /*@FindBy(xpath = "//span/a[@id='TCPMyAddressBook_new']")*/
    /*@FindBy(css = "span>#TCPMyAddressBook_new")*/
    @FindBy(xpath = "//a[contains(@href,'add-new-address')]")
//    @FindBy(xpath = ".//a[@class='navigation-item-link'][.='Address Book']")
    public WebElement btn_AddNewAddress;

    @FindBy(xpath = "//*[@id='TCPMyAddressBook_new']/img")
    public WebElement img_AddNewAddress;

    @FindBy(xpath = "//*[contains(@id,'tcp_address_delete')] //a[contains(.,'remove') or contains(.,'supprimer')  or contains(.,'eliminar')]")
    public WebElement lnk_Remove;

    @FindBy(xpath = "//*[contains(@id,'tcp_address_delete')] //a[contains(.,'edit')]")
    public WebElement lnk_Edit;

    /*@FindBy(xpath = "/*//*[@id='tcp-myaddressbook-display']/*//*[@class='shipping-address']/*//*[@class='address-col']")*/
    //@FindBy(xpath = "//span[@class='default-shipping-method']/preceding-sibling::div//span[@class='name']")//css = ".address-details .address")
    @FindBy(xpath = "//*[@class='address-items']//*[@class='name']")
    public WebElement defaultShipAddress;

    //@FindBy(xpath = "//span[@class='default-shipping-method']/preceding-sibling::div//span[@class='address']")//".address-details .name")
   // @FindBy(xpath = "//span[@class='default-shipping-method']/preceding-sibling::div//*[@class=\"address-details\"]")
    @FindBy(xpath = "//*[@class='address-items']//*[@class='address']")
    public WebElement defaultShipAddressName;

    //@FindBy(css = ".default-shipping-method")
    @FindBy(css = ".Default.Shipping.badge")
    public WebElement defaultShipMethodLbl;

    @FindBy(css="p.address-details")
    public WebElement defaultAddressInAcctOverview;

   @FindBy(xpath = "//*[@id='tcp-myaddressbook-display'] //*[contains(.,'Shipping Method') or contains(.,'Método de envío')] //a[contains(.,'edit') or contains(.,'modifier')]")
    public WebElement addressShippingEdit;

    @FindBy(xpath = "//span[contains(@id,'shipModeApplied')]")
    public WebElement addressShippingMethodStatus;

    public WebElement errorMessageByFieldError(String fldErrMsg) {
        return getDriver().findElement(By.xpath("//div[@class='ghost-error-container']//div[contains(.,'" + fldErrMsg + "')]"));
    }

    /*@FindBy(xpath = "/*//*[@id='box'] //a[contains(.,'payment info') or contains(.,'infos sur le paiement') or contains(.,'información de pago')]")*/
    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'payment')]")
    public WebElement lnk_PaymentGC;

    @FindBy(xpath = "//*[@id='box'] //a[contains(.,'online order history') or contains(.,'historique des commandes en ligne')]")
    public WebElement tab_OnlineHistory;

    @FindBy(xpath = "//*[@id='box'] //a[contains(.,'points history') or contains(.,'Historique des points')]")
    public WebElement tab_PointsHistory;

    @FindBy(xpath = "//*[@id='SlidesContainer'] //p[contains(@data-bind,'code')]")
    public WebElement list_CouponCode;

    /*@FindBy(xpath="/*//*[@id='myPaymentInfoModalDialog'] //a[contains(.,'Remove')]")*/
    @FindBy(css = ".overlay-button-container button.button-confirm")
    public WebElement btnOverlayConfirmRemove;

    @FindBy(xpath = "//*[@id='myPaymentInfoModalDialog'] //a[contains(.,'Cancel')]")
    public WebElement btn_cancel;

    @FindBy(css = "#TcpUpdateEmployeeId")
    public WebElement editEmployeeID;

    @FindBy(id = "ui-dialog-title-updateTcpAccountDialog")
    public WebElement editHomeAddressDialogTitle;

    //My Account Error validation
    @FindBy(xpath = "//*[@id='editRegister']//label/b[contains(.,'The new address infomation you entered is the same as the previous one.')]")
    public WebElement pageLevel_Error;

    @FindBy(xpath = "//*//label[contains(.,'Please enter first name.')]")
    public WebElement firstName_Error;

    @FindBy(xpath = "//label[contains(.,'Please enter last name.')]")
    public WebElement lastName_Error;

    @FindBy(xpath = "//label[contains(.,'Please enter a valid street address.')]")
    public WebElement streetAddress_Error;

    @FindBy(xpath = "//label[contains(.,'Please enter a valid city.')]")
    public WebElement city_Error;

    @FindBy(xpath = "//label[contains(.,'Please select a state/province.')]")
    public WebElement state_Error;

    @FindBy(xpath = "//label[contains(.,'Please enter a valid zip/postal code.')]")
    public WebElement zipCode_Error;

    @FindBy(xpath = ".//div[@class=\"input-common air-miles-card\"]")
    public WebElement airmilesSection;

    @FindBy(name = "airMilesAccountNumber")
    public WebElement airmilesField;

    @FindBy(xpath = ".//div[@class=\"inline-error-message\"]")
    public WebElement airmilesError;

    @FindBy(xpath = "//label[contains(.,'Please enter a valid phone number.')]")
    public WebElement phoneNumber_Error;

    @FindBy(xpath = "//label[contains(.,'First name field should not contain any special characters.')]")
    public WebElement firstName_SpecialCharError;

    @FindBy(xpath = "//label[contains(.,'Last name field should not contain any special characters.')]")
    public WebElement lastName_SpecialCharError;

    @FindBy(xpath = "//label[contains(.,'Zip code must contain five digits.')]")
    public WebElement zipcode_LessThan_five_Error;

    @FindBy(xpath = "//label[contains(.,'Postal code must be at least 6 character')]")
    public WebElement zipcode_LessMoreThan_Six_Error_CA;

    @FindBy(xpath = "//label[contains(.,'Zip code can only contain five digits.')]")
    public WebElement ZipCode_MoreThan_five_Error;

    @FindBy(xpath = "//label[contains(.,'Please select the card type.')]")
    public WebElement creditcardmethod_Error;

    @FindBy(xpath = "//label[contains(.,'This is required field. Please enter the value.')]")
    public WebElement creditcardnumber_Error;

    @FindBy(xpath = "//label[contains(.,'Card number does not match with card type. Please select correct credit card type and try again.')]")
    public WebElement creditcardnumber_InvalidError;

    //Canada
    @FindBy(xpath = "//label[contains(.,'Postal code must be at least 6 characters in length (ex. M5B 2H1).')]")
    public WebElement ZipCode_six_Can_Error;

    @FindBy(xpath = "//label[contains(.,'Postal Code must be 6 characters in length disregarding space')]")
    public WebElement ZipCode_MoreThan_six_Can_Error;

    @FindBy(xpath = "//label[contains(.,'Postal Code can not exceed 7 characters in length.')]")
    public WebElement ZipCode_seven_Can_Error;

    /*@FindBy(xpath = "./[@id='WC_TCPAddAddress_FormInput_firstName']")*/
    /*@FindBy(css="#WC_TCPAddAddress_FormInput_firstName")*/
    @FindBy(css = "input[name='address.firstName']")
    public WebElement newAddress_FirstName;

    /*@FindBy(xpath = "./*//*[@id='WC_TCPAddAddress_FormInput_lastName']")*/
    /*@FindBy(css = "#WC_TCPAddAddress_FormInput_lastName")*/
    @FindBy(css = "input[name='address.lastName']")
    public WebElement newAddress_LastName;

    /*@FindBy(id = "WC_TCPAddAddress_FormInput_address_1")*/
    @FindBy(css = "input[name='address.addressLine1']")
    public WebElement newAddress_AddressLine1;

    /*@FindBy(id = "WC_TCPAddAddress_FormInput_address_2")*/
    @FindBy(css = "input[name='address.addressLine2']")
    public WebElement newAddress_AddressLine2;

    /*@FindBy(id = "WC_TCPAddAddress_FormInput_city")*/
    @FindBy(css = "input[name='address.city']")
    public WebElement newAddress_City;

    /*@FindBy(id = "WC_TCPAddAddress_FormSelect_state")*/
    @FindBy(css = "select[name='address.state']")
    public WebElement newAddress_State;

    @FindBy(css = ".select-common.select-state")
    public WebElement newAddress_StateFld;

    /*@FindBy(id = "WC_TCPAddAddress_FormInput_addressField3")*/
    @FindBy(css = "input[name='address.zipCode']")
    public WebElement newAddress_ZipCode;

    /*@FindBy(id = "WC_TCPAddAddress_FormInput_phone")*/
    @FindBy(css = "input[name='phoneNumber']")
    public WebElement newAddress_PhoneNumeber;

//    @FindBy(id = "WC_TCPAddAddress_FormInput_country")

    @FindBy(css = ".select-common.select-country")
    public WebElement newAddress_CountryFld;

    @FindBy(css = ".select-common.select-country")
    public WebElement newAddress_Country;

    @FindBy(name = "address.country")
    public WebElement newAddress_ChangeCountry;

    /*@FindBy(id = "WC_TCPAddAddress_FormInput_primary")*/
    @FindBy(css = ".set-as-default-checkbox-container div label div ")
    public WebElement newAddress_SetDefaultAddressCheckBox;

    @FindBy(css = ".set-as-default-checkbox-container div label div input")
    public WebElement newAddress_SetDefaultAddressCheckBoxInput;

    /*@FindBy(xpath = "./*//*[@class='login-submit'] //a[contains(.,'Add Address') or contains(.,'Agregar dirección') or contains(.,'Ajouter une adresse')]")*/
    /*@FindBy(css="#WC_TCPAddAddress_FormLink_1")*/
    @FindBy(css = ".button-container .select[type='submit']")
    public WebElement newAddress_AddNewAddressBtn;

    /*@FindBy(xpath = "./*//*[@id='AddAddress']//a[contains(.,'Cancel') or contains(.,'Cancelar') or contains(.,'Annuler')]")*/
    @FindBy(css = ".button-container .cancel")
    public WebElement newAddressCancelBtn;

    @FindBy(xpath = "//*[@id='WC_hidden']/label")
    public WebElement newAddress_SuccessMessage;

    /*@FindBy(css = ".login-submit")*/
    @FindBy(css = "button.button-add-new-card")
    public WebElement btnAddCard;

    @FindBy(css = ".button-save-changes")
    public WebElement btnSaveChanges;

    @FindBy(css = "a.button-cancel")
    public WebElement btnCancelAddCard;

    /*@FindBy(xpath = "/*//*[@id='tcp-mypaymentinfo-display']/*//*[contains(.,'Default Credit Card')]//a[contains(.,'remove')]")*/
    /*@FindBy(css="#removeCreditCardLink")*/
    @FindBy(css = ".credit-card-item-container .button-close")
    public WebElement paymentInfo_RemoveLink;

    @FindBy(css = ".credit-card-items li")
    public List<WebElement> cardContainers;

    @FindBy(css = ".credit-card-items")
    public WebElement totalCards;

    @FindBy(xpath = "//li[@class='credit-card-item-container']/span[contains(@class,'default-payment-method')]/preceding-sibling::button")
    public WebElement defaultPayment_RemoveLink;

    /*@FindBy(id = "TCPMyPaymentInfo_link_add")*/
    @FindBy(xpath = "//a[contains(@href,'payment/add-credit-card')]")
    public WebElement btn_AddNewCard;

    @FindBy(css = "#billing_state1")
    public WebElement addPayment_State;

    @FindBy(name = "address.country")
    public WebElement addPayment_Country;

    @FindBy(css = "#billing_phone1")
    public WebElement addPayment_PhoneNo;

    @FindBy(id = "payMethodId")
    public WebElement addPayment_CardSelection;

    /*@FindBy(css = "#pay_temp_account")*/
    @FindBy(xpath = ".//input[@name='cardNumber']")
    public WebElement addPayment_CardNumber;

    /*@FindBy(id = "pay_expire_month")*/
    // @FindBy(css = ".select-exp-mm [name='expMonth']")
    @FindBy(xpath = ".//div[@class=\"select-common select-exp-mm\"]//select")
    public WebElement addPayment_expmonth;

    @FindBy(xpath = ".//div[@class=\"select-common select-exp-mm label-error\"]//select")
    public WebElement addPayment_expMonthErrDrop;

    /*@FindBy(id = "pay_expire_year")*/
    @FindBy(css = ".select-exp-yy [name='expYear']")
    public WebElement addPayment_expyr;

    //@FindBy(xpath = "(//label[contains(@class,'select-exp-mm')]//span)[1]")
    @FindBy(css = ".select-common.select-exp-mm")
    public WebElement addExpMonthLbl;

    /*@FindBy(xpath = "./*//*[@id='tcp-mypaymentinfo-display'] /*//*[contains(.,'Default Credit Card')] //div[contains(@class,'address')][2]")*/
//    @FindBy(css = ".card-info .ending-numbers")
//    public WebElement lbl_CardNumber;

    public WebElement lbl_CardNumber(String trimCardNumber) {
        return getDriver().findElement(By.xpath("//strong[@class='ending-numbers'][contains(.,'" + trimCardNumber + "')]"));
    }

    @FindBy(xpath = ".//*[@id='tcp-mypaymentinfo-display'] //*[contains(.,'Default Credit Card')] //a[contains(.,'edit')]")
    public WebElement payment_editbtn;

    public WebElement label_CardNumber(String trimCardNumber) {
        return getDriver().findElement(By.xpath("//p[@class='card-info'][contains(.,'" + trimCardNumber + "')]"));
    }

    @FindBy(css = ".edit_myplace_cancel")
    public WebElement btn_Cancel;

    @FindBy(css = ".credit-card-item-container .default-payment-method")
    public WebElement defaultPaymentMethodLbl;

    @FindBy(css = ".credit-card-item-container")
    public List<WebElement> availableCounts;

    @FindBy(xpath = "//span[@class=\"default-payment-method\"]/..")
    public WebElement defaultpaymentContainer;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_country_In_Register_1")
    public WebElement drp_Country;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_addressField3_In_Register_1")
    public WebElement txt_Zipcode;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_country_In_Register")
    public WebElement txt_Country;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_phone1_In_Register_1")
    public WebElement txt_PhoneNumber;

    @FindBy(id = "WC_TCPEditHomeAddress_FormInput_address2_In_Register_1")
    public WebElement txt_Address2;

    @FindBy(css = "a[href='#ropisOrderHistory']")
    public WebElement reservationHistoryTab;

    @FindBy(xpath = "//*[@id='orderHistory']//th[contains(.,'Date')]")
    public WebElement header_Date;

    @FindBy(xpath = "//*[@id='orderHistory']//th[contains(.,'Order/Transaction Number')]")
    public WebElement header_Transaction;

    @FindBy(xpath = "//*[@id='orderHistory']//th[contains(.,'Total')]")
    public WebElement header_Total;

    @FindBy(xpath = "//*[@id='orderHistory']//th[contains(.,'Status')]")
    public WebElement header_Status;

    @FindBy(xpath = "//*[@id='orderHistory']//tr[1][@class='triple-row']")
    public WebElement orderHistory_FirstRow;

    @FindBy(xpath = "//*[@class='confirmation-title'][contains(.,'order details')]")
    public WebElement orderDetailsTitle;

    @FindBy(xpath = "//*[@class='summary-title']")
    public WebElement orderSummaryTitle;

    @FindBy(xpath = "//*[@class='bag-title order-summary-title'][contains(.,'amount billed')]")
    public WebElement amountBilledTitle;

    @FindBy(xpath = "//*[@class='bag-title order-summary-title'][contains(.,'payment summary')]")
    public WebElement paymentSummaryTitle;

    @FindBy(xpath = "//*[@class='receipt-bag-title']")
    public WebElement shoppingBagTitle;

    @FindBy(xpath = "//*[@id='pointsHistory']/*[contains(.,'POINTS TRANSACTION HISTORY')]")
    public WebElement lbl_PointsTransaction;

    @FindBy(xpath = ".//*[@class='points-history-table'] //th[contains(.,'Date')]")
    public WebElement pointsHistory_HeaderData;

    @FindBy(xpath = ".//*[@class='points-history-table'] //th[contains(.,'Transaction')]")
    public WebElement pointsHistory_Transaction;

    @FindBy(xpath = "//*[@class='points-history-table'] //th[contains(.,'Total Points')]")
    public WebElement pointsHistory_TotalPoints;

    @FindBy(id = "addChildButton")
    public WebElement addAnotherChild;

    @FindBy(xpath = "//div[contains(@id,'_null_r_')]")
    public WebElement numOfChildRows;

    private int newlyAddedRow() {
        return getDriver().findElements(By.xpath("//div[contains(@id,'_null_r_')]")).size();
    }

    public WebElement newChildName() {
        return getDriver().findElement(By.xpath("//div[contains(@id,'_null_r_')][" + newlyAddedRow() + "] //input[contains(@id,'ChildName')]"));
    }

    public WebElement newChildGender() {
        return getDriver().findElement(By.xpath("//div[contains(@id,'_null_r_')][" + newlyAddedRow() + "] //select[contains(@id,'ChildGender')]"));
    }

    public WebElement newChildMonth() {
        return getDriver().findElement(By.xpath("//div[contains(@id,'_null_r_')][" + newlyAddedRow() + "] //select[contains(@id,'ChildBirthMonth')]"));
    }

    public WebElement newChildYear() {
        return getDriver().findElement(By.xpath("//div[contains(@id,'_null_r_')][" + newlyAddedRow() + "] //select[contains(@id,'ChildBirthYear')]"));
    }

    @FindBy(xpath = "//div[contains(@id,'_null_r_')] //a[contains(.,'Delete')]")
    public WebElement deleteAllButton;

    @FindBy(id = "infoLink14")
    public WebElement link_PointsClaim;

    @FindBy(id = "Label12")
    public WebElement claimPointsFromRecentPurchase;

    @FindBy(xpath = "//*[@class='userInfoLabel'][contains(.,'My Place Rewards #')]")
    public WebElement myPlaceRewardsLabel;

    @FindBy(xpath = "(//*[@class='points-intro'][contains(.,'WHEN WILL MY POINTS EXPIRE')]//*[@class='single-row']/td)[1]")
    public WebElement expiration_Date;

    @FindBy(xpath = "(//*[@class='points-intro'][contains(.,'WHEN WILL MY POINTS EXPIRE')]//*[@class='single-row']/td)[2]")
    public WebElement expiration_Points;

    @FindBy(xpath = "//*[@class='points-history-table']/tbody/*[@class='single-row']/td[3][not(contains(., 'Total Points'))]")
    public List<WebElement> eachTransactionPoints;

    @FindBy(xpath = "//*[@class='points-history-table']/tbody/*[@class='single-row']/*[contains(.,'Total Points:')]")
    public WebElement totalPoints;

    @FindBy(css = "a[href='#personalInfo']")
    public WebElement tab_PersonalInfo;

    @FindBy(css = ".my-account-navigation-container li:nth-child(1)")
    public WebElement lnk_AccountOverView;

    @FindBy(css = "a[href='#ropisOrderHistory']")
    public WebElement tab_ReservationHistory;

    @FindBy(xpath = "(//button[@class='delete-button action-item'])[1]")
    public WebElement removeFirstAddress;

    @FindBy(css = ".button-edit")
    public WebElement editPaymentAddressLnk;


    //@FindBy(css = ".button-edit")
    @FindBy(css = ".edit-button")
    public WebElement editLnkOnPersonalInfo;

    @FindBy(xpath = ".//p[@class=\"air-miles-message\"]")
    public WebElement airmilesNumberDisplay;

    @FindBy(css = ".address-container")
    public WebElement profileInfo;

    //Added by me
    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'profile')]")
//    @FindBy(xpath = ".//a[@class='navigation-item-link navigation-item-link-selected'][.='Profile Information']")
    public WebElement profileInfoLnk;

    @FindBy(xpath = ".//button[@class='button-information'][.='Change password']")
    public WebElement changePwdBtn;

    @FindBy(xpath = ".//div[@class='birthday-information-container']//a[@class='button-information'][.='Add birthday info']")
    public WebElement addBdayInfoBtn;

    @FindBy(xpath = ".//div[@class='birthday-information-container']//a[@class='button-information'][.='Edit birthday info']")
    public WebElement editBdayInfoBtn;


    @FindBy(name = "currentPassword")
    public WebElement currentPwdField;

    @FindBy(css = "[name='password']")
    public WebElement newPwdField;

    @FindBy(name = "confirmPassword")
    public WebElement confirmPwdField;

    @FindBy(css = ".success-box.inline-success-message")
    public WebElement successMsg;

    @FindBy(css = ".button-container .button-save")
    public WebElement saveButton;

    @FindBy(css = ".button-save-changes")
    public WebElement saveChanges;

    @FindBy(css = ".success-box")
    public WebElement updatedSuccessMsg;

    @FindBy(xpath = ".//div[@class=\"error-box\"]")
    public WebElement pInfoError;

    @FindBy(css = ".inline-error-message")
    public WebElement inlineErrMsgCard;
    //@FindBy(css = "[name='currentPassword']~.inline-error-message")
    //    @FindBy(css = "[name='currentPassword']~.inline-error")
//    @FindBy(css = "#error_currentPassword_2 div")
    @FindBy(xpath = "(//div[@class='inline-error-message'][text()='Please enter your password'])[1]")
    public WebElement currentPwdErr;

//    @FindBy(css = "[name='password'] ~ .inline-error-message")
//    @FindBy(css = "#error_password_3  div")
    //   @FindBy(css = "[name='confirmPassword']~.inline-error-message")

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid password.']")
    public WebElement confirmPwdErr;

    @FindBy(xpath = ".//span[@class=\"name\"]")
    public WebElement firstNamePI;

    @FindBy(xpath = ".//strong[@class=\"hello-title\"]")
    public WebElement helloFNameDisplay;

    //    @FindBy(css = "[name='password'] ~ .inline-error-message")
    //      @FindBy(css = "#error_password_3  div")
    @FindBy(xpath = "(//div[@class='inline-error-message'][text()='Please enter your password'])[2]")
    public WebElement newPwdErr;

    //    @FindBy(css = "[name='confirmPassword'] ~ .inline-error-message")
    @FindBy(xpath = "//div[@class='inline-error-message'][text()='Passwords must match']")
    public WebElement misMatchErr;

    @FindBy(xpath = "(//div[@class='input-with-button'])[2]/button")
//(css = "[name='password'] + .button-show-password")
    public WebElement newPwdShowHideLink;

    @FindBy(xpath = "(//div[@class='input-with-button'])[1]/button")
//(css = "[name='currentPassword'] + .button-show-password")
    public WebElement currentPwdShowHideLink;

    @FindBy(xpath = "(//div[@class='input-with-button'])[3]/button")
//(css = "[name='confirmPassword'] + .button-show-password")
    public WebElement confirmPwdShowHideLink;

    //    @FindBy(css = ".error-box")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Passwords must match']")
    public WebElement errorMsgBox;

    @FindBy(css = ".inline-error-message")
    public WebElement inlineErrMsg;

    @FindBy(css = "[name='firstName']")
    public WebElement firstNameField;

    @FindBy(css = "[name='lastName']")
    public WebElement lastNameField;

    //    @FindBy(xpath = ".//label[@class='input-common email-address']//input")
    @FindBy(xpath = ".//form[@class='personal-information-form']//input[@name='emailAddress']")
    public WebElement emailIdField;

    @FindBy(css = "[name='phoneNumber']")
    public WebElement mobileNoField;

    //    @FindBy(css = "[name=firstName] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a first name']")
    public WebElement firstNameErr;

    //    @FindBy(css = "[name=lastName] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a last name']")
    public WebElement lastNameErr;

    //    @FindBy(css = "[name=emailAddress] ~ .inline-error-message")
    //@FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your email address']")
    @FindBy(xpath = "//div[@class='inline-error-message'][contains(.,'Please enter a valid email')]")
    public WebElement emailErr;

    //    @FindBy(css = "[name=phoneNumber] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your phone number']")
    public WebElement mobileErr;

    @FindBy(css = ".checkbox-employee>label>div")//".input-checkbox-icon-unchecked")
    public WebElement associateID_CheckBox;

    @FindBy(css = ".checkbox-employee>label>div>input")//".input-checkbox-icon-unchecked")
    public WebElement associateID_ChkBoxVal;

    @FindBy(css = "[name='associateId']")
    public WebElement associateIDField;

    //    @FindBy(css = "[name='associateId']~ .inline-error-message")
//    @FindBy(css=".error-box")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='The Associate ID you entered does not exist. Please try again']")
   // @FindBy(xpath = ".//div[@class='error-box'][contains(.,'The Associate ID you entered does not exist. Please try again')]")
    public WebElement associateIDErr;

    @FindBy(xpath = ".//button[@class='button-info']")
    public WebElement tooltipBtn;

    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'orders')]")
    public WebElement ordersLnk;

    @FindBy(css = ".order-number")
    public WebElement orderNoLink;

    @FindBy(xpath = ".//th[@class='order-or-reservation-total'][.='Total']")
    public WebElement totalText;

    @FindBy(css = ".table-orders-title")
    public WebElement orderTitle;

    @FindBy(css = ".order-date-container")
    public List<WebElement> orderDate;

    @FindBy(css = ".purchase-container")
    public List<WebElement> orderType;

    @FindBy(css = ".status-container")
    public List<WebElement> orderStatus;

    @FindBy(css = ".order-or-reservation-total.total-container")
    public List<WebElement> orderTotalAmount;

    @FindBy(css = ".button-show-order")
    public WebElement canadaOrderLink;

    @FindBy(xpath = ".//button[@class=\"button-show-order\"][contains(.,\"Show US Orders\")]")
    public WebElement usOrderLink;

    @FindBy(css = ".button-show-international-order")
    public WebElement intOrderLink;

    @FindBy(xpath = "//label//img[contains(@src,place-card)]")
    public WebElement imgPLCC;

    @FindBy(xpath = "//label//img[contains(@src,visa)]")
    public WebElement imgVISA;

    @FindBy(xpath = "//label//img[contains(@src,mc)]")
    public WebElement imgMC;

    @FindBy(xpath = "//label//img[contains(@src,disc)]")
    public WebElement imgDiscover;

    @FindBy(xpath = "//label//img[contains(@src,amex)]")
    public WebElement imgAMEX;

    @FindBy(css = ".breadcrum-link")
    public WebElement addressBookText;

    @FindBy(css = ".addressBook-link")
    public WebElement addressBookBreadCrumb;

    //    @FindBy(css = "input[name='address.firstName']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a first name']")
    public WebElement fnameErr;

    //    @FindBy(css = "input[name='address.lastName']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a last name']")
    public WebElement lnameErr;

    //    @FindBy(css = "input[name='address.addressLine1']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid street address']")
    public WebElement address1Err;

    //    @FindBy(css = "input[name='address.city']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid city']")
    public WebElement cityErr;

    //    @FindBy(css = "[name='address.state']~.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a state' or text()='Please select a province']")
    public WebElement stateErr;

    //    @FindBy(css = "input[name='address.zipCode']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your zip code']")
    public WebElement zipErr;

    //    @FindBy(css = "input[name='phoneNumber']+.inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter your phone number']")
    public WebElement phoneErr;

    @FindBy(css = ".edit-address.action-item")//".edit")
    public WebElement editShipAddressLink;

    @FindBy(xpath = "//div[@class='address-container'][contains(.,'Default Shipping')]/following-sibling::div/a[@class='edit-address action-item']")
    public WebElement editDefShipAddrLink;

    @FindBy(css = ".button-select-shipping-address.cancel.select")
    public WebElement cancelBtn;

    @FindBy(css = ".dropdown-address-book-button-closed")
    public WebElement expandAdrBookDropDownBillInfo;

    @FindBy(css = ".button-add-new-address")
    public WebElement addNewBillingAddressOption;

    @FindBy(css = ".title-confirmation")
    public WebElement deleteAddressOverlay;

    @FindBy(xpath = ".//div[@class=\"card-details-container\"]/h2")
    public WebElement deleteModalHeader;

    @FindBy(css = ".button-modal-close")
//    @FindBy(css = ".button-close")
    public WebElement closeModal;

    @FindBy(css = ".button-confirm")
    public WebElement okButton;

    @FindBy(css = ".button-primary.button-add-payment-method")
    public WebElement addPayment_Btn;

    @FindBy(css = ".button-primary.button-add-address")
    public WebElement addAddress_Btn;

    @FindBy(css = ".link-view-all-rewards")
    public WebElement viewRewardsLink;

    @FindBy(css = ".uncondense-apply-coupons-button")
    public List<WebElement> applyToBagButton;

    @FindBy(xpath = ".//button[@class=\"uncondense-view-and-print-button\"]")
    public List<WebElement> viewAndPrintCoupon;

    @FindBy(xpath = ".//span[@class=\"uncondense-expire-information\"]")
    public List<WebElement> expiryDateCoupon;

    //@FindBy(xpath = "//strong[text()='AUTOMATION 10% OFF']")//.//div[@class=\"uncondense-coupon-content\"][contains(.,\"$10 off \")]//button[@class=\"uncondense-apply-coupons-button\"]")
    @FindBy(xpath = ".//*[@alt='AUTOMATION 10% OFF']//ancestor::li//button[@class=\"uncondense-apply-coupons-button\"]")
    public WebElement apply10Off;

    @FindBy(css = ".uncondense-applied-coupons-button")
    public WebElement removeButton;

    @FindBy(xpath = ".//div[@class=\"ghost-error-container\"][contains(.,\"Coupon is not applicable.\")]")
    public WebElement couponError;

    @FindBy(xpath = ".//li[@class='my-rewards-navigation-item'][3]")
    public WebElement couponsLink;

    //@FindBy(xpath = ".//li[@class='my-rewards-navigation-item'][2]")
    @FindBy(xpath = "//li[@class=\"my-rewards-navigation-item\"]/a[text()='Points History']")
    public WebElement pointsHistoryLink;

    @FindBy(xpath = ".//li[@class='my-rewards-navigation-item'][1]")
    public WebElement rewardsLink;

    @FindBy(xpath = "//li[@class='my-account-navigation-item']//a[contains(@href,'account/rewards')][contains(.,'My Rewards')]")
    //@FindBy(css = ".navigation-item-link.navigation-item-link-selected")
    public WebElement myRewardsLink;

    @FindBy(css = ".no-rewards-message")
    public WebElement noRewardsText;

    @FindBy(css = ".empty-points-history")
    public WebElement emptyText;

    @FindBy(css=".rewards-bar")
    public WebElement pointsBar;

    @FindBy(css = ".button-submit-point-claim")
    public WebElement submitFormLink;

    @FindBy(css = ".button-submit-request")
    public WebElement submitReqBtn;

    @FindBy(xpath = "//div[@class='custom-select-button dropdown-address-book-button-closed']//p")
    public WebElement dropDownLink;

    @FindBy(css = ".button-add-new.button-add-new-address")
    public WebElement addNewAddress;

    @FindBy(css = ".button-save-changes")
    public WebElement saveChangesBtn;

    @FindBy(css = "p.success-box")
    public WebElement successMessageBox;

    @FindBy(xpath = "//li[contains(@class,'enabled')]//button")
    public WebElement addAChildBtn;

    @FindBy(xpath = "(.//button[@class='button-delete'])[1]")
    public WebElement childRemoveBtn;

    @FindBy(name = "childName")
    public WebElement childName;

    @FindBy(name = "digitalSignatureFirstName")
    public WebElement childFname;

    @FindBy(name = "digitalSignatureLastName")
    public WebElement childLname;

    @FindBy(css = "select[name='birthMonth']")
    public WebElement birthMth;

    @FindBy(css = ".select-common.select-month")
    public WebElement monthDrpDownDisplay;

    @FindBy(css = "select[name='birthYear']")
    public WebElement birthYear;

    @FindBy(css = ".select-common.select-year")
    public WebElement yearDrpDownDisplay;

    @FindBy(css = "[name='gender']")
    public WebElement gender;

    @FindBy(css = ".select-common.input-gender")
    public WebElement genderDrpDownDisplay;

    @FindBy(css = ".timestamp-date")
    public WebElement birthTimeStamp;

    @FindBy(css = ".button-add")
    public WebElement addChildBtn;

    @FindBy(css = ".button-cancel-information")
    public WebElement cancelInfoBtn;

    public WebElement addChildByPosition(int i) {
        return getDriver().findElement(By.xpath("(.//button[@class='button-add-new-child button-primary'])[ " + i + "]"));
    }

    public WebElement removeLinkByPosition(int i) {
        return getDriver().findElement(By.xpath("(.//button[@class='button-delete'])[" + i + "]"));
    }

    @FindBy(css = ".button-remove")
    public WebElement yesRemoveBtn;

    // @FindBy(css = ".button-cancel-process" )
    @FindBy(css = ".button-cancel")
    public WebElement cancelButton;

    @FindBy(xpath = ".//a[@class='button-information'][.='Add birthday info']")
    public WebElement addBirthdayInfoBtn;

    //  @FindBy(css = "[name='childName'] + .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a name']")
    public WebElement childnameErr;

    //    @FindBy(css = "[name='birthMonth'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a month']")
    public WebElement birthMthError;

    //    @FindBy(css = "[name='birthYear'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a year']")
    public WebElement birthYrError;

    //    @FindBy(css = "[name='gender'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a gender']")
    public WebElement genderError;

    //    @FindBy(css = "[name='digitalSignatureFirstName'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a first name']")
    public WebElement childFnameError;

    //    @FindBy(css = "[name='digitalSignatureLastName'] ~ .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a last name']")
    public WebElement childLnameError;

    //    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions.label-error .inline-error-message")
    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please accept the terms by selecting the box above.']")
    public WebElement termsError;

    @FindBy(css = ".input-title")
    public WebElement termsAndCondition;

    @FindBy(css = ".label-checkbox.input-checkbox.checkbox-term-and-conditions div")
    public WebElement termsCheckBox;

    //    @FindBy(xpath = ".//figure[@class='image-container']//img")
    @FindBy(xpath = ".//div[@id='header-global-banner-container']//img")
    public WebElement myPlaceRewardsEspot;

    @FindBy(css = ".button-cancel-information")
    public WebElement cancelPerInfoButton;

    @FindBy(css = ".hint-text")
    public WebElement hintText;

    @FindBy(css = ".input-radio-icon-unchecked")
    public WebElement youEnteredCheckBox;

    @FindBy(css = ".button-edit")
    public WebElement addressVerificationEdit;

    @FindBy(css = ".profileInformation-link")
    public WebElement profileInfoText;

    @FindBy(css = ".child-name")
    public WebElement child_Name;

    @FindBy(xpath = ".//p[@class='address-details']/span")
    public WebElement nameField;

    @FindBy(xpath = ".//p[@class='address-additional']/span")
    public WebElement emailID;

    @FindBy(xpath = ".//p[@class='rewards-message']/span")
    public WebElement mprRewards;

    //AddressBook
    @FindBy(css = ".button-container button[type='submit']")
    public WebElement updateOnAddressBook;

    public WebElement orderNoByOrder(String orderNum) {
        return getDriver().findElement(By.xpath("//table[@class='order-or-reservation-list']//tr[contains(.,'" + orderNum + "')]/td[2]/a"));
    }

    @FindBy(xpath = ".//a[@class='button-primary button-update']//span")
    public WebElement findStoreBtn;

//    @FindBy(css = ".checkbox-employee div:nth-child(1)")//.//input[@type='checkbox']")
//    public WebElement associateID_CheckBox;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='First name field should not contain any special characters']")
    public WebElement fnameSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Last name field should not contain any special characters']")
    public WebElement lnameSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='The value entered in the street address has special character']")
    public WebElement address1SplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='The value entered in the city has special character']")
    public WebElement citySplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid zip code']")
    public WebElement zipSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid phone number']")
    public WebElement phoneSplErr;

    @FindBy(css = ".button-merge-account-request")
    public WebElement coustomerService;

    @FindBy(css = ".myRewards-link")
    public WebElement rewardsma_Link;

    @FindBy(xpath = "//li[@class=\"my-rewards-navigation-item\"]/a[text()='Rewards']")
    public WebElement rewardsMyAccount_link;

    @FindBy(xpath = "//p[@class=\"rewards-message\"][text()=\"MY PLACE REWARDS #: \"]")
    public WebElement mprRewardsNumber;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='First name field should not contain any special characters']")
    public WebElement firstNameSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Last name field should not contain any special characters']")
    public WebElement lastNameSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Email format is invalid.']")
    public WebElement emailSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid phone number']")
    public WebElement mobileSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Your current password is incorrect. Please try again.']")
    public WebElement currentPwdSplErr;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please enter a valid password.']")
    public WebElement newPwdSplErr;

    @FindBy(css = ".error-box")
    public WebElement previousPwdErr;

    @FindBy(xpath = "//div[@class=\"inline-error-message\"][text()=\"Child's name field should not contain any special characters\"]")
    public WebElement splbdayError;

    @FindBy(xpath = "//div[@class=\"inline-error-message\"][text()=\"First name field should not contain any special characters\"]")
    public WebElement splbdayFnameError;

    @FindBy(xpath = "//div[@class=\"inline-error-message\"][text()=\"Last name field should not contain any special characters\"]")
    public WebElement splbdayLnameError;

    @FindBy(css = ".error-box")
    public WebElement invalidID_Error;

    @FindBy(css = ".rewards-status")
    public WebElement pointsSection;

    @FindBy(xpath = ".//a[@aria-label=\"Update shipping address\"]")
    public WebElement updateButtonShipAdd;

    @FindBy(xpath = ".//div[@class=\"error-box\"][contains(.,\"Please check if the card is already in use.\")]")
    public WebElement usedCardError;

    @FindBy(xpath = ".//div[@class=\"inline-error-message\"][contains(.,\"Please enter a valid first name\")]")
    public WebElement maxFNameErr;

    @FindBy(xpath = ".//div[@class=\"inline-error-message\"][contains(.,\"Please enter a valid last name\")]")
    public WebElement maxLNameErr;

    @FindBy(xpath = ".//span[@class=\"input-checkbox-title\"]//a[@alt=\"Frequently Asked Questions\"]")
    public WebElement faqLink;

    @FindBy(xpath = ".//button[@class=\"button-add-new-child button-primary\"]")
    public List<WebElement> bdayAddChildButton;

    @FindBy(xpath = ".//a[@class=\"wic-button-learn-more\"]/img[@alt=\"My Place Rewards\"]")
    public WebElement mprEspotAccOverview;

    @FindBy(css = ".button-primary.button-apply")
    public WebElement acceptOrApplyButton;

    //@FindBy(xpath = ".//section[@class=\"default-shipping-address-container\"]//p")
    @FindBy(xpath = ".//section[@class=\"default-shipping-address-container\"]//div//p")
    public WebElement shippingText;

    @FindBy(xpath = ".//section[@class=\"default-shipping-address-container\"]//span[contains(.,\"Save A Shipping Address\")]")
    public WebElement saveShippingHeader;

    @FindBy(css = ".button-add-new-giftcard.button-secondary")
    public WebElement addGiftCard_button;

    @FindBy(name = "cardNumber")
    public WebElement giftCard_NumberField;

    @FindBy(name = "cardPin")
    public WebElement giftCard_PinField;

    @FindBy(css = ".recaptcha")
    public WebElement recaptcha_Field;

    @FindBy(css = ".button-cancel.link-button-cancel")
    public WebElement cancel_Button;

    @FindBy(css = ".add-giftcard-message")
    public WebElement giftcard_Message1;

    @FindBy(css = "add-giftcard-message-title")
    public WebElement giftcard_Header;

    @FindBy(css = ".gift-card-save.button-save")
    public WebElement save_Button;

    @FindBy(css = ".inline-error-message")
    public WebElement recaptcha_Error;

    @FindBy(xpath = ".//li[@class=\"gift-card-item-container\"]//button[@class=\"button-close\"]")
    public List<WebElement> closeGiftcard;

    @FindBy(css = ".card-details-container")
    public WebElement confirmationText;

    @FindBy(css = ".button-cancel")
    public WebElement cancelDeleteGc;

    @FindBy(css = ".gift-card-add-submit")
    public List<WebElement> checkBalanceButton;

    @FindBy(xpath = ".//ul[@class=\"uncondense-list-coupons\"][contains(.,\"LOYALTY\")]//button[contains(.,\"Apply to bag\")]")
    public List<WebElement> applyMPRCoupon;

    @FindBy(css = ".success-box")
    public WebElement successMessage;

    @FindBy(css = "error-box")
    public WebElement couponErrorMessage;

    @FindBy(xpath = ".//li[@class=\"coupon expiration-limit\"]//strong[contains(.,\"LOYALTY\")]")
    public WebElement loyalty_Coupon;

    @FindBy(xpath = ".//div[@class=\"address-container\"]")
    public WebElement savedAddress_MyAccount;

    @FindBy(css = ".breadcrum-container")
    public WebElement breadCrumbContainer;

    @FindBy(css = ".button-cancel")
    public WebElement cancelButton_PointHistory;

    @FindBy(css = ".payment-add-new-button")
    //@FindBy(css = ".button-add-new-giftcard.button-secondary")
    public WebElement addNew_GC;

    public String newAddress_FnameValue ="input[name='address.firstName']";

    @FindBy(css = ".error-box")
    public WebElement errorContainer;

    //@FindBy(xpath = ".//li[@class=\"my-account-navigation-item\"]//a[contains(.,'Offers & Coupons')]")
    @FindBy(xpath = "//*[@class=\"viewport-container\"]//a[contains(.,'Offers & Coupons')]")
    public WebElement lnk_OffersAndCoupons;

    @FindBy(xpath = "//*[@class=\"address-container\"]")
    public WebElement billingAddressContainer;

    @FindBy(css = ".store-name")
    public WebElement favStoreDisplay;

    @FindBy(css = ".reward-message-tooltip")
    public WebElement tooltipContentPoints;

    @FindBy(css = ".button-tooltip-wire")
    public WebElement tooltipPoints;

    @FindBy(xpath = "//div[@class='inline-error-message'][contains(.,'Please enter a valid first name')]")
    public WebElement maxChar_FNameField;

    @FindBy(xpath = "//div[@class='inline-error-message'][contains(.,'Please enter a valid last name')]")
    public WebElement maxChar_LastNameField;

    @FindBy(xpath = "//div[@class='inline-error-message'][contains(.,'Please shorten the street address')]")
    public WebElement maxChar_AddLine1;

    @FindBy(xpath = "//div[@class='inline-error-message'][contains(.,'Please enter a valid city')]")
    public WebElement maxChar_CityErr;

    @FindBy(xpath = "//div[@class='inline-error-message'][contains(.,'Please enter a valid zip code')]")
    public WebElement maxChar_ZipCodeErr;

    @FindBy(css = ".custom-loading-icon")
    public WebElement loadingIcon;

    @FindBy(name = "address.addressLine1")
    public WebElement mailingAdd_Line1;

    @FindBy(name = "address.city")
    public WebElement mailingAdd_City;

    @FindBy(name = "address.zipCode")
    public WebElement mailingAdd_Zip;

    @FindBy(xpath = "//*[@name='address.state']")
    public WebElement mailingAdd_StateDropDown;

    @FindBy(xpath = "//*[@class='address-item-container']//*[@class='address-type-badge']/ancestor::li//button[@class=\"delete-button action-item\"]")
    public WebElement deleteDefaultAddress;

    @FindBy(xpath = "//*[@class='address-item-container']//*[@class='address-type-badge']/ancestor::li//button[@class=\"delete-button action-item\"]")
    public WebElement defaultAddressDeleteLink;

    @FindBy(xpath = "//*[@class='address-item-container']//*[@class='address-type-badge']/ancestor::li//a[@class=\"edit-address action-item\"]")
    public WebElement defaultAddressEditLink;

    @FindBy(css = ".address-item-container")
    public List<WebElement> addressContainer;

    @FindBy(css = ".custom-select-common.dropdown-address-book.dropdown-address-book-closed")
    public WebElement mailingAddress_AddressBookDD;

    @FindBy(css = ".address-container")
    public List<WebElement> addressList;

    @FindBy(xpath = "//div[@class=\"inline-error-message\"][contains(.,\"street\")]")
    public WebElement mailingAddress_StreetErr;

    @FindBy(xpath = "//div[@class=\"inline-error-message\"][contains(.,\"city\")]")
    public WebElement mailingAddress_CityErr;

    @FindBy(xpath = "//div[@class=\"inline-error-message\"][contains(.,\"code\")]")
    public WebElement mailingAddress_ZipErr;

    @FindBy(xpath = "//*[@class='address-item-container']//*[@class='address-type-badge']/ancestor::li//button[@class='button-default-shipping-method action-item']")
    public WebElement setAsDefaultLinkForDefaultAddress;

    @FindBy(css = ".edit-address.action-item")
    public List<WebElement> editLinkForAddress;

    @FindBy(css = ".delete-button.action-item")
    public List<WebElement> deleteLinkForAddress;

    @FindBy(xpath = "//div[@class=\"uncondense-information-coupon\"][contains(.,\"20% off\")]")
    public WebElement odmOffCoupon;

    @FindBy(css = ".uncondense-expire-information.invisible")
    public WebElement couponExpireInfoODM;

}

