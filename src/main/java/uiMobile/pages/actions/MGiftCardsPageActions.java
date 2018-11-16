package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MGiftCardsPageRepo;

import java.util.List;

/**
 * Created by JKotha on 23/10/2017.
 */
public class MGiftCardsPageActions extends MGiftCardsPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MGiftCardsPageActions.class);


    public MGiftCardsPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Click  on send gift card button
     * ??Modified By Pooja, added wait for eSendGiftCardPage
     *
     * @return true if gift cards page displayed
     */
    public boolean clickSendAGiftCardsButton() {
        click(sendAGiftCard_Btn);
        return waitUntilElementDisplayed(quantity, 10) || waitUntilElementDisplayed(eSendGiftCardPage, 10);
    }

    /**
     * Created By Pooja
     * Click  on Plastic gift card button
     *
     * @return true if gift cards page displayed
     */
    public boolean openGiftCardByType(String type) {
        scrollDownToElement(getGiftCardByTitle(type));
        click(getGiftCardByTitle(type));
        return waitUntilElementDisplayed(sendAGiftCard_Btn, 10);
    }

    /**
     * select a gift card of value, Qty and add to bag
     *
     * @param value of the gift card
     * @param qty   size of the gift card
     * @return true if the action successful and add to bag
     */
    public boolean selectGiftCardDetailsAndAddToBag(String value, String qty) {
        MobileHeaderMenuActions headerMenuActions = new MobileHeaderMenuActions(mobileDriver);
        if (value == "null") {
            selectRandomGiftCardValue();
        } else {
            click(getCardValue(value));
        }
        staticWait();
        selectDropDownByValue(quantity, qty);
        staticWait(2000);
        click(addToBag);
        return waitUntilElementDisplayed(headerMenuActions.viewBagButtonFromAddToBagConf, 30);
    }

    /**
     * Select a random gift card value
     */
    public void selectRandomGiftCardValue() {
        List<WebElement> list = getDropDownOption(cardValue);
        if (list.size() > 1) {
            list.get(randInt(0, (list.size() - 1))).click();
        } else if (list.size() == 1) {
            list.get(0).click();
        }
    }

    /**
     * Selects a random gift card quantity
     */
    public void selectRandomCardQuantity() {
        List<WebElement> list = getDropDownOption(quantity);
        if (list.size() > 1) {
            list.get(randInt(0, (list.size() - 1))).click();
        } else if (list.size() == 1) {
            list.get(0).click();
        }
    }

    public void fillRecipientInformation(String amount, String recipientName, String recipientEmail, String recipientMsg) {
        selectDropDownByValue(amountdd, amount);
        clearAndFillText(recipientNameFld, recipientName);
        clearAndFillText(recipientEmailFld, recipientEmail);
        clearAndFillText(recipientMessageFld, recipientMsg);
    }

    public void fillSenderInformation(String deliveryDate, String senderName, String senderEmail) {
        clearAndFillText(deliveryDateFld, deliveryDate);
        clearAndFillText(senderEmailFld, senderName);
        clearAndFillText(senderEmailFld, senderEmail);
    }

    public void fillCardinformation(String ccNo, String expMonth, String expYear, String cvv) {
        clearAndFillText(creditCardnofld, ccNo);
        selectDropDownByValue(expMonthDropdown, expMonth);
        selectDropDownByValue(expYearDropdown, expYear);
        clearAndFillText(cvvfld, cvv);
    }

    public void fillBillingInfo(String fn, String ln, String zip, String phno) {
        clearAndFillText(firstNameFld, fn);
        clearAndFillText(lastNameFld, ln);
        clearAndFillText(zipCodeFld, zip);
        clearAndFillText(phnoFld, phno);
    }

    //TO DO: need to call all separate methods
    public void buyEgiftCard() {

    }


    public void goToGiftCardPage(String giftCard) {
        switch (giftCard) {
            case "giftCard":
                scrollDownToElement(sendAGiftCard_Btn);
                javaScriptClick(mobileDriver, sendAGiftCard_Btn);
                break;
            case "egiftCard":
                scrollDownToElement(sendEGiftCard);
                javaScriptClick(mobileDriver, sendEGiftCard);
                switchToWindow();
                break;
            case "animatedGift":
                scrollDownToElement(sendAnimatedGiftCard);
                javaScriptClick(mobileDriver, sendAnimatedGiftCard);
                switchToWindow();
                break;
        }
    }

    public boolean goGiftCardBalancePage() {
        javaScriptClick(mobileDriver, giftCardBalanace);
        return waitUntilElementDisplayed(cardNo);
    }

    public void verifyGiftcardBalance(String no, String pin) {
        clearAndFillText(cardNo, no);
        clearAndFillText(cardPin, pin);
        click(checkBalanceBtn);
    }

    public boolean validatedGiftCards() {
        return waitUntilElementDisplayed(sendAGiftCard_Btn, 10) &&
                waitUntilElementDisplayed(sendEGiftCard, 5) &&
                waitUntilElementDisplayed(sendAnimatedGiftCard, 5);
    }

    public boolean validatedGiftCards_CA() {
        return waitUntilElementDisplayed(sendAGiftCard_Btn, 5) &&
                !waitUntilElementDisplayed(sendEGiftCard, 5) &&
                !waitUntilElementDisplayed(sendAnimatedGiftCard, 5);
    }

}
