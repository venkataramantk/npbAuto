package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileReceiptThankYouPageRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jkotha on 12/19/2017.
 */
public class MReceiptThankYouPageActions extends MobileReceiptThankYouPageRepo {

    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MReceiptThankYouPageActions.class);

    public static String Number = "";
    public static String Date = "";
    public static String Points = "";

    public static double Total;

    public MReceiptThankYouPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }


    public String getOrderNum() {
        waitUntilElementDisplayed(orderNumber);
        String orderNum = getText(orderNumber);
        return orderNum;
    }

    public String getTotal() {
        String total = lbl_TotalAmount.getText();
        return total;

    }


    public String productName() {
        return getText(orderReceipt_ProductName).toLowerCase().replaceAll(" ", "");
    }


    public boolean validateTextInOrderConfirmPageReg() {
        if (waitUntilElementDisplayed(thanksText, 10) &&
                waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(orderNumber, 10) &&
                waitUntilElementDisplayed(estimatedTot, 10))
            return true;
        else
            return false;
    }

    public boolean validateErrorMessagesTab(String passwordErr, String confirmPwdErr) {

        waitUntilElementDisplayed(enterPasswordField, 5);
        enterPasswordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(err_password, 5);
        boolean validatePasswordErr = getText(err_password).contains(passwordErr);

        waitUntilElementDisplayed(confirmPasswordField, 5);
        confirmPasswordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(err_ConfirmPassword, 5);
        boolean validateConfirmPasswordErr = getText(err_ConfirmPassword).contains(confirmPwdErr);

        if (validatePasswordErr && validateConfirmPasswordErr)
            return true;
        else
            return false;
    }

    public boolean showHideLinkPassword(String pwd) {
        waitUntilElementDisplayed(enterPasswordField, 4);
        clearAndFillText(enterPasswordField, pwd);
        click(showHideLink);
        boolean checkShow = getAttributeValue(enterPasswordField, "value").contains(pwd) && getText(showHideLink).equalsIgnoreCase("hide");
        click(showHideLink);
        boolean checkHide = (!(getText(enterPasswordField).contains(pwd))) && getText(showHideLink).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }

    public String getItemCount() {
        String item = getText(itemTotal).replace("$", "");
        return item;
    }


    public boolean validateCreateAccountFlds() {
        return waitUntilElementDisplayed(createAccountTitle, 5) &&
                waitUntilElementDisplayed(userEmailTitle, 5) &&
                waitUntilElementDisplayed(userEmail, 5) &&
                waitUntilElementDisplayed(passwordFld, 5) &&
                waitUntilElementDisplayed(confirmPasswordField, 5) &&
                waitUntilElementDisplayed(showConfirmPwd, 5) &&
                waitUntilElementDisplayed(showPwd, 5) &&
                waitUntilElementDisplayed(termsAndConditionTxt, 5) &&
                waitUntilElementDisplayed(termsAndConditionTlink, 5) &&
                waitUntilElementDisplayed(contactUsLink, 5) &&
                waitUntilElementDisplayed(createAccountButton, 5);
    }

    public boolean createAccount(String pwd) {
        clearAndFillText(passwordFld, pwd);
        clearAndFillText(confirmPasswordField, pwd);
        javaScriptClick(mobileDriver, termsAndConditionTxt);
        javaScriptClick(mobileDriver, createAccountButton);
        return waitUntilElementDisplayed(accountSuccess, 30);
    }

    public void clickTermsnConditions() {
        javaScriptClick(mobileDriver, termsAndConditionTxt);
    }


    public boolean validateErrorMessage() {
        return waitUntilElementDisplayed(pwdError) &&
                waitUntilElementDisplayed(confirmPwdError) &&
                waitUntilElementDisplayed(termsANdConditionsError);
    }

    public String getPasswordError() {
        return getText(pwdError);
    }

    public boolean showPwds(String pwd) {
        clearAndFillText(passwordFld, pwd);
        clearAndFillText(confirmPasswordField, pwd);
        click(showPwd);
        click(showConfirmPwd);
        return true;
    }


    public boolean validateAirmilesText(String airmilesText) {
        if (isDisplayed(airmiles_Text)) {
            String text = getText(airmiles_Text);
            if (text.contains(airmilesText)) {
                return true;
            }
        } else {
            addStepDescription("The text is not getting displayed properly in SB page");
            return false;
        }
        return false;
    }

    /*created by Raman

       To validate the existing account error if user try to create account with existing email
       */
    public boolean createAccountError(String pwd) {
        clearAndFillText(passwordFld, pwd);
        clearAndFillText(confirmPasswordField, pwd);
        click(termsAndConditionTxt);
        click(createAccountButton);
        return waitUntilElementDisplayed(existingAccError);
    }

    public String getErrorMessage() {
        String errorDisplayed = getText(existingAccError);
        return errorDisplayed;
    }


    public boolean validateTextInOrderConfirmPageRegForIntStore() {
        if (waitUntilElementDisplayed(thankTextForInt, 10) &&
                waitUntilElementDisplayed(OrderNoTextForInt, 10) &&
                waitUntilElementDisplayed(estTotalForInt, 10))
            return true;
        else
            return false;
    }

    public boolean validateToolTip(String s) {
        waitUntilElementDisplayed(toolTip, 10);
        click(toolTip);
        return getText(toolTipContent).equalsIgnoreCase(s);
    }


    /**
     * Click on order no in Order Confirmation page
     *
     * @param order no to click
     * @return
     */
    public boolean clickOnOrderNo(MMyAccountPageActions mMyAccountPageActions, String order) {
        click(orderNoLink(order));
        return waitUntilElementDisplayed(mMyAccountPageActions.orderId, 10) && waitUntilElementDisplayed(mMyAccountPageActions.orderDetailsSpinner, 10);
    }

    public int getconfirmationFullFillmentListItemSize() {
        return confirmationFullFillmentListItem.size();
    }

    public boolean verifyOrderModifyCancelNotification(String confirmation) {
        waitUntilElementDisplayed(orderModifyCancelConfirmation, 20);
        System.out.println(confirmation);
        System.out.println(getText(orderModifyCancelConfirmation));
        return getText(orderModifyCancelConfirmation).equalsIgnoreCase(confirmation);
    }

    /**
     * Created by Richa Priya
     * This Method clicks the tool tip
     */
    public boolean clickOnToolTipIcon() {
        waitUntilElementDisplayed(toolTip);
        javaScriptClick(mobileDriver, toolTip);
        return waitUntilElementDisplayed(toolTipContent);
    }

    /**
     * Created by Richa Priya
     * This Method validates tool tip details
     */
    public boolean validateContentOnToolTip(List<String> details) {
        List<String> toolTipDetails = new ArrayList<String>();

        waitUntilElementDisplayed(toolTipContent);
        toolTipDetails.add(getText(toolTipStoreName));
        toolTipDetails.add(getText(toolTipDetail));

        addStepDescription("Bopis store and address details displayed on tool tip::" + toolTipContent);

        return toolTipDetails.get(0).contains(details.get(0)) && toolTipDetails.get(1).contains(details.get(1)) && toolTipDetails.get(1).contains("Today:") &&
                toolTipDetails.get(1).contains("Tomorrow:") && toolTipDetails.get(1).contains("Phone:") && !(toolTipDetails.get(1).contains(null));
    }

    /**
     * Created by Raman Jha
     * This Method validates name on Order confirmation
     */
    public boolean verifyNameonOrderConfirmationHybrid(String fName, String lName) {
        waitUntilElementDisplayed(thanksText);
        String fullName = getText(customerName);
        return fullName.equalsIgnoreCase(fName + " " + lName);
    }

    /**
     * Created by Raman Jha
     * This Method validates redirection to store locator when we click on fulfillment center link
     */
    public boolean clickFulfillmentCenterLink() {
        waitUntilElementDisplayed(storeLocator, 10);
        javaScriptClick(mobileDriver, storeLocator);
        staticWait(4000);
        boolean compareURL = getCurrentURL().contains("store");
        if (compareURL) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Created by RichaK
     * This will click on the create account button on order confirmation page.
     */
    public void clickCreateAccount() {
        waitUntilElementDisplayed(createAccountBtn);
        javaScriptClick(mobileDriver, createAccountBtn);
    }

    public String getTotalCost() {
        waitUntilElementDisplayed(totalCost);
        return getText(totalCost);
    }

    public boolean validateSmsSignUpCB() {
        return waitUntilElementDisplayed(marketingSmsCB, 5) && !isSelected(marketingSmsCB, marketingSmsCBInput);
    }

    public boolean checkSmsSignUpCB() {
        waitUntilElementDisplayed(marketingSmsCB, 10);
        select(marketingSmsCB, marketingSmsCBInput);
        return waitUntilElementDisplayed(smsDisclaimer, 10) && waitUntilElementDisplayed(marketingSmsPhoneField, 10) && waitUntilElementDisplayed(smsPrivacyPolicy, 10);
    }

    public boolean uncheckSmsSignUpCB() {
        waitUntilElementDisplayed(marketingSmsCB, 10);
        staticWait();
        unSelect(marketingSmsCB, marketingSmsCBInput);
        return !waitUntilElementDisplayed(smsDisclaimer, 5);
    }

    public String getSmsPhone() {
        waitUntilElementDisplayed(marketingSmsPhoneField, 10);
        return getAttributeValue(marketingSmsPhoneField, "value");
    }

    public void updateSmsPhone(String phone) {
        waitUntilElementDisplayed(marketingSmsPhoneField, 10);
        clearAndFillText(marketingSmsPhoneField, phone);
    }

    public boolean validateSmsErrorMessage(String message) {
        waitUntilElementDisplayed(phoneNoErrMsg, 10);
        return getText(phoneNoErrMsg).contains(message);
    }

    public boolean validatePageLevelError(String message) {
        waitUntilElementDisplayed(pageLeverError, 10);
        return getText(pageLeverError).contains(message);
    }

    public void clickSubmitBtn() {
        waitUntilElementDisplayed(submitBtn, 10);
        javaScriptClick(mobileDriver, submitBtn);
    }

    public boolean getSuccessMessage(String msg) {
        waitUntilElementDisplayed(accountSuccess, 20);
        return getText(accountSuccess).contains(msg);
    }

    //ODM
    public boolean validateODMHeader() {
        if (waitUntilElementDisplayed(odmHeaderText, 10)) {
            return waitUntilElementDisplayed(odmHeaderText, 3);
        } else {
            addStepDescription("ODM header is not displayed");
            return false;
        }
    }

    public boolean validateODMCouponFields() {
        if (waitUntilElementDisplayed(expandODMCoupon(1))) {
            click(expandODMCoupon(1));
            return waitUntilElementDisplayed(couponDescription, 10) &&
                    waitUntilElementDisplayed(barCode, 10) &&
                    waitUntilElementDisplayed(promoCode, 2) &&
                    waitUntilElementDisplayed(validityDate, 3) &&
                    waitUntilElementDisplayed(detailsLink, 1);
        } else {
            addStepDescription("ODM Coupon is not displayed");
            return false;
        }
    }

    public boolean validateDetailsLink() {
        if (waitUntilElementDisplayed(detailsLink, 10)) {
            click(detailsLink);
            return waitUntilElementDisplayed(longDescrption);
        } else {
            addStepDescription("Details Link is not displayed");
            return false;
        }
    }

    public boolean closeDetailsLink() {
        if (waitUntilElementDisplayed(closeDetailsLink, 10)) {
            click(closeDetailsLink);
            return !waitUntilElementDisplayed(longDescrption, 3);
        } else {
            addStepDescription("Close Details CTA is not displayed");
            return false;
        }
    }

    public String getCouponDate() {
        if (waitUntilElementDisplayed(validityDate, 10)) {
            return getText(validityDate);
        } else {
            addStepDescription("Coupon date is not displayed");
            return "";
        }
    }

    public boolean validateCouponsStartDate() {
        String date = getCouponDate();

        if (getCouponDate().contains("Now through")) {
            addStepDescription("Current Date is equal to or later than start date - Displaying Now through");
            return true;
        } else if (getCouponDate().contains("-")) {
            addStepDescription("Current date is prior to start date - Displaying start date - end date format");
            return true;
        }
        return false;
    }
}
