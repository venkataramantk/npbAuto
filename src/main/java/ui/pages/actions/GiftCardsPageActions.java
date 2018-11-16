package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.GiftCardsPageRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by skonda on 5/19/2016.
 */
public class GiftCardsPageActions extends GiftCardsPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(GiftCardsPageActions.class);

    public GiftCardsPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickSendAGiftCardsButton(ProductDetailsPageActions productDetailsPageActions) {
        click(sendAGiftCard_Btn);
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 30);
    }

    public boolean verifyOptions(WebElement dropdown, Map<String, String> dataTable) {
        ArrayList al = new ArrayList();
        al.add(dataTable.get("Option1"));
        al.add(dataTable.get("Option2"));
        al.add(dataTable.get("Option3"));
        al.add(dataTable.get("Option4"));
        List<WebElement> options = getDropDownOption(dropdown);

        for (WebElement temp : options) {
            Iterator itr = al.iterator();
            if (itr.hasNext()) {
                if (!(temp.getText().contains(itr.next().toString()))) {
                    return false;
                }
            }
        }
        return true;
    }


        /* if(options.containsAll(al)){
            return true;
        }
        else{
            return false;
        }*/


    public void selectRandomGiftCardValue() {
        List<WebElement> list = getDropDownOption(cardValue);
        if (list.size() > 1) {
            list.get(randInt(0, (list.size() - 1))).click();
        } else if (list.size() == 1) {
            list.get(0).click();
        }
    }

    public void selectRandomCardName() {
        List<WebElement> list = getDropDownOption(giftCardsDropDown);
        if (list.size() > 1) {
            list.get(randInt(0, (list.size() - 1))).click();
        } else if (list.size() == 1) {
            list.get(0).click();
        }
    }

    public boolean giftCardValidations(String store) {
        scrollDownToElement(giftCardBanner);
        waitUntilElementDisplayed(giftCardBanner, 5);
        Boolean links = false;
        if (store.equalsIgnoreCase("US"))
            links = isDisplayed(giftCardBalanceLink) && isDisplayed(giftCardElink) && isDisplayed(giftCardFaqLink) && isDisplayed(giftCardTemrsConditoonsLink);
        if (store.equalsIgnoreCase("CA"))
            links = isDisplayed(giftCardBalanceLink) && isDisplayed(giftCardFaqLink) && isDisplayed(giftCardTemrsConditoonsLink);
        return links;
    }

    public boolean eGiftCardValidations() {
        waitUntilElementDisplayed(giftCardBanner, 5);
        if (isDisplayed(eGiftCardImage) &&
                isDisplayed(giftCardElink)) {
            return true;
        }
        return false;
    }

    public boolean TermsConditionToolTip() {
        scrollDownToElement(giftCardTemrsConditoonsLink);
        waitUntilElementDisplayed(giftCardTemrsConditoonsLink, 3);
        String TCToolTip = getAttributeValue(giftCardTemrsConditoonsLink, "title");
        if (TCToolTip.equalsIgnoreCase("Terms & Conditions")) {
            return true;
        }
        return false;
    }

    public boolean clickAndValidateGiftCardURL(String linkName, String urlValue) {
        scrollDownUntilElementDisplayed(giftCardLinksByName(linkName));
        click(giftCardLinksByName(linkName));
        staticWait(5000);
        String currentUrl = getCurrentURL();
        navigateBack();
        return currentUrl.contains(urlValue);
    }

    public boolean eGiftCardNavigation(String urlValue) {
        scrollDownToElement(mailGiftCardButton);
        waitUntilElementDisplayed(mailGiftCardButton);
        click(mailGiftCardButton);
        waitUntilElementDisplayed(giftCardPDPTitle);
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlValue);
    }

    public boolean validateGiftCardBalance() {
        staticWait(5000);
        click(giftCardBalanceLink);
        waitUntilElementDisplayed(giftCardBalanceHeader, 3);
        click(giftCardBalanceClose);
        waitUntilElementDisplayed(giftCardBalanceLink);
        click(giftCardBalanceLink);
        int BalanceSteps = giftCardBalancesteps.size();
        int GiftBalanceImg = giftCardBalanceImages.size();
        if (isDisplayed(giftCardBalanceNumber) &&
                isDisplayed(giftCardBalancepin) &&
                isDisplayed(giftCardBalanceCancelButton) &&
                isDisplayed(giftCardBalanceCheckButton) &&
                (BalanceSteps == 4) &&
                (GiftBalanceImg == 2) &&
                isDisplayed(giftCardBalanceClose)) {
            return true;
        }
        return false;
    }

    public boolean giftCardFieldValidations(String Errmsg1, String Errmsg2, String Errmsg3) {
        waitUntilElementDisplayed(giftCardBalanceNumber, 10);
        clearAndFillText(giftCardBalanceNumber, "invalid");
        clearAndFillText(giftCardBalancepin, "pin");
        click(giftCardBalanceCancelButton);
        waitUntilElementDisplayed(giftCardBalanceLink, 3);
        click(giftCardBalanceLink);
        waitUntilElementDisplayed(giftCardBalanceNumber, 3);
        clearAndFillText(giftCardBalanceNumber, "invalid");
        clearAndFillText(giftCardBalancepin, "pin");
        waitUntilElementDisplayed(giftCardBalancePinErr, 3);
        tabFromField(giftCardBalanceCheckButton);
        boolean BalanceCardErr = getText(giftCardBalanceNumberErr).contains(Errmsg1);
        boolean BalancePinErr = getText(giftCardBalancePinErr).contains(Errmsg2);
        click(giftCardBalanceCheckButton);
        if (BalanceCardErr && BalancePinErr) {
            return true;
        }
        return false;

    }

    public boolean giftCardPDPValidations() {
        waitUntilElementDisplayed(giftCardPDPTitle, 5);
        scrollDownToElement(giftCardPDAddToBag);
        boolean favIcon = verifyElementNotDisplayed(addToWishList);
        boolean bopisIcon = verifyElementNotDisplayed(pickUpStore);
        if (isDisplayed(giftCardPDPPrice) &&
                isDisplayed(giftCardPDPSelectDesign) &&
                isDisplayed(giftCardPDPSelectValue) &&
                isDisplayed(giftCardPDQuantity) &&
                isDisplayed(giftCardPDAddToBag) &&
                isDisplayed(giftCardPDSendGiftLink) && favIcon) {
            return true;
        }
        return false;
    }

    public boolean giftCardValueUpdate() {
        waitUntilElementDisplayed(giftCardPDPSelectValue, 3);
        scrollDownToElement(giftCardPDPSelectValue);
        int availableSelectValue = giftCardBalanceSelectValueText.size();
        int randomSelect = randInt(0, (availableSelectValue - 1));
        String storeSelectValue = getText(giftCardBalanceSelectValueText.get(randomSelect));
        click(giftCardBalanceSelectValueText.get(randomSelect));
        String Currentprice = getText(giftCardPDPSelectValueupdate);
        if (storeSelectValue.equalsIgnoreCase(Currentprice)) {
            return true;
        }
        return false;
    }

    public boolean addGiftCardToBag() {
        waitUntilElementDisplayed(giftCardPDPSelectValue, 2);
        int availableSelectValue = giftCardBalanceSelectValueText.size();
        int randomSelect = randInt(0, (availableSelectValue - 1));
        // Since by default 25 will be selected, sometimes this leads to throw N response in the subtotal
        /*String storeSelectValue = getText(giftCardBalanceSelectValueText.get(randomSelect));
        click(giftCardBalanceSelectValueText.get(randomSelect));
        */click(addToBag);
        boolean notification = waitUntilElementDisplayed(addToBagNotification,3);
        if (notification==true) {
            click(conf_ButtonClose);
            return true;
        } else {
            addStepDescription("Something happened while adding Gift card");
            return false;
        }
    }
    public boolean editAnGiftCardFromDrawer() {
        waitUntilElementDisplayed(shoppingBagIcon, 3);
        click(shoppingBagIcon);
        waitUntilElementDisplayed(editLinkSBDrawer, 2);
        click(editLinkSBDrawer);
        waitUntilElementDisplayed(designDropDown, 2);
        click(designDropDown);
        waitUntilElementsAreDisplayed(listOfGC, 2);
        int availableList = listOfGC.size();
        if (availableList > 1) {
            int randomSelect = randInt(0, (availableList - 1));
            click(listOfGC.get(randomSelect));
            waitUntilElementDisplayed(updateButton, 2);
            click(updateButton);
            return waitUntilElementDisplayed(updateNotification, 2);
        }
        else if(availableList==1){
            selectDropDownByValue(qtyDropDown, "2");
            click(updateButton);
            return waitUntilElementDisplayed(updateNotification, 2);
        }
        else{
            addStepDescription("Available GC list is less than or equal to one");
            return false;
        }
    }

}
