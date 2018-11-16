package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.ShoppingBagDrawerRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skonda on 3/24/2017.
 */
public class ShoppingBagDrawerActions extends ShoppingBagDrawerRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(ShoppingBagDrawerActions.class);

    public ShoppingBagDrawerActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickCreateAcctLinkFromEspot(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(eSpotCreateAccountLink);
        click(eSpotCreateAccountLink);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 15);

    }

    public boolean clickCheckoutButtonAsRegUser(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(checkoutButton, 30);
        click(checkoutButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 30);
    }

    public String getUPCNumByPosition(int position){
        return getText(getUPCNumWebElementByPosition(position)).replace("UPC","").replace("-","").replace(" ","");

    }
    public boolean clickCheckoutAsGuestUser(LoginPageActions loginPageActions) {
        waitUntilElementDisplayed(checkoutButton, 20);
        click(checkoutButton);
        return waitUntilElementDisplayed(loginPageActions.continueAsGuestButton);
    }

    public boolean clickViewBagLink(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(viewBagLink, 10);
        if (waitUntilElementDisplayed(checkoutButton, 5)) {
            mouseHover(checkoutButton);
        } else
            mouseHover(continueShoppingLnk);
        click(viewBagLink);
        return waitUntilElementDisplayed(myBagTitle, 20) || waitUntilElementDisplayed(shoppingBagPageActions.emptyBagTextContainer, 10);

    }

    public boolean validateEmptyShoppingBagAsGuest(String text1, String text2, String text3) {

        boolean checkEmptyMessage = getText(emptyTextMessage).contains(text1);
        boolean checkNewAccMessage = getText(createNewAccText).contains(text2);
        boolean checkSubTotalText = getText(subTotalText).contains(text3);
        boolean emptyPLCCEspot = waitUntilElementDisplayed(emptyEspot);

        if (checkEmptyMessage && checkNewAccMessage && checkSubTotalText && emptyPLCCEspot)
            return true;
        else
            return false;
    }


    public boolean validateEmptyShoppingBagAsReg(String text1, String text3) {

        boolean checkEmptyMessage = getText(emptyTextMessage).contains(text1);
        boolean checkSubTotalText = getText(subTotalText).contains(text3);

        if (checkEmptyMessage)
            return checkSubTotalText;
        if (checkSubTotalText) {
            return false;
        } else
            return true;
    }

    public boolean validateButtonsInEmptyBag() {
        if (waitUntilElementDisplayed(loginButton, 2) &&
                (!waitUntilElementDisplayed(checkoutButton, 2)) &&
                waitUntilElementDisplayed(viewBagLink, 2) &&
                waitUntilElementDisplayed(createAccBtn, 2))
            return true;
        else
            return false;
    }

    public boolean clickContShoppingButtonAndVerify(ShoppingBagPageActions shoppingBagPageActions) {
        click(continueShoppingLnk);
        return waitUntilElementDisplayed(shoppingBagPageActions.emptyBagTextContainer, 20);
    }

    public boolean validateBagIconForRegUser(String text1, String text3) {
        boolean checkEmptyMessage = getText(emptyTextMessage).contains(text1);
        boolean checkSubTotalText = getText(subTotalText).contains(text3);

        if (checkEmptyMessage && checkSubTotalText && waitUntilElementDisplayed(checkoutButton, 2) &&
                waitUntilElementDisplayed(viewBagLink, 2) &&
                waitUntilElementDisplayed(continueShoppingLnk, 2))
            return true;
        else
            return false;
    }

    public boolean validateEmptyBagDrawerForRegUser(String text1, String text3) {
        boolean checkEmptyMessage = getText(emptyTextMessage).toLowerCase().contains(text1.toLowerCase());
        boolean checkSubTotalText = getText(subTotalText).contains(text3);

        if (checkEmptyMessage && checkSubTotalText &&
                isDisplayed(viewBagLink) &&
                isDisplayed(continueShoppingLnk))
            return true;
        else
            return false;
    }

    public boolean clickOnViewBagOnEmptyBag(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(viewBagLink, 20);
        click(viewBagLink);
        return waitUntilElementDisplayed(shoppingBagPageActions.emptyBagTextContainer);
    }

    public boolean clickViewBagOnProdInBag(ShoppingBagPageActions shoppingBagPageActions) {
        click(viewBagLink);
        return waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle, 20);
    }

    public boolean validateBagIconWithProd() {
//        boolean checkSubTotaltext = getText(subTotalText).contains("Subtotal");

        if (waitUntilElementDisplayed(checkoutButton, 10) && waitUntilElementDisplayed(viewBagLink, 10) &&
                waitUntilElementDisplayed(getFirstElementFromList(editLnks), 10) &&
                waitUntilElementDisplayed(getFirstElementFromList(wishListIcons), 10) && waitUntilElementDisplayed(getFirstElementFromList(removeLinks), 10))
            return true;
        else
            return false;
    }

    public boolean verifyBagCountWithProdQuantity(/*OverlayHeaderActions overlayHeaderActions*/) {
//        int totalQtyInBag = Integer.valueOf(overlayHeaderActions.getQtyFromMiniCart());
        int index = inlineQty.size();
        List<Integer> totQty = new ArrayList<>();
        for (int i = 1; i <= index; i++) {
            int getQtyOfEachLineItem = Integer.parseInt(inline_qty(i).getText().replaceAll("[^0-9]", ""));
            totQty.add(getQtyOfEachLineItem);
        }
//    int sumOfTotQt =

        return false;
    }

    public boolean removeProdAndVerifyMiniCartUpdated(OverlayHeaderActions overlayHeaderActions, int noOfProdToRemove) {
        int totalQtyInBag = Integer.valueOf(overlayHeaderActions.getQtyFromMiniCart());
        int prodSize = removeLinks.size();
        if (prodSize > noOfProdToRemove) {
            for (int i = 0; i < noOfProdToRemove; i++) {
                click(removeLinks.get(i));
                waitUntilElementDisplayed(updateNotification);
                staticWait(3000);
            }
        } else {
            throw new IllegalStateException("The qty in Bag " + totalQtyInBag + " is less than the qty to remove from bag " + noOfProdToRemove);
        }
        int qtyInBagAfterRemove = Integer.valueOf(overlayHeaderActions.getQtyFromMiniCart());
        return (totalQtyInBag - noOfProdToRemove) == qtyInBagAfterRemove;
    }

    public boolean editUpdateQty_SingleProduct(String qtyToUpdate) {
        int prodEditSize = editLnks.size();
        for (int i = 0; i < prodEditSize; i++) {
            click(editLnks.get(i));
            waitUntilElementDisplayed(updateLink, 15);
            addStepDescriptionWithScreenshot("Clicked on Edit link from shopping bag drawer");
            selectDropDownByValue(qtyDropDown, qtyToUpdate);
            click(updateLink);
            break;
        }
        return waitUntilElementDisplayed(updateNotification);

    }

    public int sumOfTotalProdInDrawer() {
        int prodSize = prodQty.size();
        int totalQtyInDrawer = 0;
        for (int i = 0; i < prodSize; i++) {
            int prodQtyInDrawer = Integer.parseInt(getText(prodQty.get(i)).replace("Qty:", "").replace(" ", ""));
            int prevProdQty = prodQtyInDrawer;
            totalQtyInDrawer = totalQtyInDrawer + prevProdQty;
            staticWait(3000);
        }

        return totalQtyInDrawer;
    }

    public int totalItemLines() {
        return prodLines.size();
    }

    public boolean isSubTotalFldDisplayingAboveCheckOutButton() {
        return waitUntilElementDisplayed(subTotalFldDisplayAboveChkOutButton);
    }

    public boolean validateProdDetailsInBag() {

        if (waitUntilElementDisplayed(prodColor, 10) &&
                waitUntilElementDisplayed(prodname, 5) && waitUntilElementDisplayed(getFirstElementFromList(prodPrices), 5) &&
                waitUntilElementDisplayed(getFirstElementFromList(prodQty), 5) && waitUntilElementDisplayed(prodSize))

            return true;
        else
            return false;
    }

    public double getProdPriceTotal() {
        int noOfProdPrices = prodPrices.size();
        double totalProdPrice = 0.00;
        for (int i = 0; i < noOfProdPrices; i++) {
            double currentProdPrice = Double.valueOf(getText(prodPrices.get(i)).replace("$", ""));
            double prevProdPrice = currentProdPrice;
            totalProdPrice = prevProdPrice + totalProdPrice;
        }
        return totalProdPrice;
    }

    public double getSubTotalPrice() {
        return Double.valueOf(getText(subTotalPrice).replace("$", ""));
    }

    public boolean clickWLIconAsReg() {
        staticWait(4000);
        click(getLastElementFromList(wishListIcons));
        waitUntilElementDisplayed(updateNotification, 3);
        return waitUntilElementDisplayed(emptyTextMessage, 30);
    }

    public boolean clickWLIconAsGuest(String userName, String password) {
        waitUntilElementsAreDisplayed(wishListIcons, 10);
        staticWait(3000);
        click((wishListIcons).get(0));
        waitUntilElementDisplayed(emailAddrField, 5);
        clearAndFillText(emailAddrField, userName);
        clearAndFillText(passwordField, password);
        click(FavLoginButton);
        return waitUntilElementDisplayed(updateNotification, 4);

    }

    public boolean clickOnCheckoutBtnAsReg(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(checkoutButton, 10);
        click(checkoutButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 40);
    }

    public boolean clickOnCheckoutBtnAsRegForExpressCO(ReviewPageActions reviewPageActions) {
        waitUntilElementDisplayed(checkoutButton, 10);
        click(checkoutButton);
        return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 40);
    }

    public boolean clickCreateAccLnk(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(createAccBtn, 30);
        click(createAccBtn);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 5);
    }

    public boolean enterAirMilesNo(String number) {
        waitUntilElementDisplayed(collectorNoField, 10);
        waitUntilElementDisplayed(airMiles_Espot, 10);
        clearAndFillText(collectorNoField, number);
        return waitUntilElementDisplayed(editLink, 10);
    }

    public String getAirMilesNo() {

        String airMiles = getText(airMilesNumb).replaceAll("[^0-9]", "");
        return airMiles;
    }

    public boolean validateAirmilesText(String airmilesText) {
        staticWait(2000);
        if (isDisplayed(airmiles_Text) || isDisplayed(airmileSBText)) {
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

    public void clickCloseDrawer() {
        click(closeOverlay);
        staticWait(5000);
    }

    public boolean editUpdateQty_Product(int count, int fit, int size,String qtyToUpdate) {
        int prodEditSize = editLnks.size();
        for (int i = 0; i < prodEditSize; i++) {
            click(editLnks.get(i));
            waitUntilElementDisplayed(updateLink);
            click(colorDropDown);
            waitUntilElementsAreDisplayed(selectColor, 3);
            click(selectColor.get(count));
            staticWait();
            if (isDisplayed(fitDropDown)) {
                click(fitDropDown);
                click(fitOptionAvailable.get(fit));
            }
            click(sizeDropDown);
            click(sizeOptionAvailable.get(size));
            selectDropDownByValue(qtyDropDown, qtyToUpdate);
            click(updateLink);
            break;
        }
        return waitUntilElementDisplayed(updateNotification);

    }

    public boolean loginFromMPR() {
        waitUntilElementDisplayed(mprLogin);
        click(mprLogin);
        return waitUntilElementDisplayed(emailAdd);
    }

    public String getProdSize() {
        waitUntilElementDisplayed(prodSize, 5);
        return getText(prodSize).replaceAll("Size", "").replaceAll(":", "").trim();
    }

    public boolean clickOnProductNameFromInlineSB(ProductDetailsPageActions productDetailsPageActions) {
        waitUntilElementsAreDisplayed(overlay_ProdName, 3);
        waitUntilElementDisplayed(checkoutButton);
        String itemName = getText(overlay_ProdName.get(0));
        click(overlay_ProdName.get(0));
        String pdpName = getText(productDetailsPageActions.productName);
        if (itemName.equalsIgnoreCase(pdpName)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean mprEspotBannerGuestCheck() {
        waitUntilElementDisplayed(mprBannerEspt, 2);
        int displayedPoint = Integer.parseInt(getText(pointsEarnedInEspotGuest));
        double points = (int) Math.round((Double.valueOf(getText(prodPrices.get(0)).replaceAll("[^0-9.]", "")) * 100)) / 100;
        double doublePoints = 0.00;
        doublePoints = points + points;
        if (points == displayedPoint) {
            return true;
        } else if (displayedPoint == doublePoints) {
            addStepDescription("Double promotion is enabled");
            return true;
        } else {
            return false;
        }
    }

    public boolean mprEspotBannerRegCheck() {
        waitUntilElementDisplayed(mprBannerEspt, 2);
        int displayedPoint = Integer.parseInt(getText(pointsEarnedInEspotReg));
        double points = (int) Math.round((Double.valueOf(getText(prodPrices.get(0)).replaceAll("[^0-9.]", "")) * 100)) / 100;
        double doublePoints = 0.00;
        doublePoints = points + points;
        if (points == displayedPoint) {
            return true;
        } else if (displayedPoint == doublePoints) {
            addStepDescription("Double promotion is enabled. displaying "+displayedPoint);
            return true;
        } else {
            addStepDescription("Displaying points "+displayedPoint+" expected: "+points+" or "+doublePoints);
            return false;
        }
    }

    public boolean mprCheckCA() {
        if (isDisplayed(mprBannerEspt)) {
            addStepDescription("MPR E-spot is displayed in CA store");
            return false;
        } else {
            return true;
        }
    }

    public boolean validateMPReSpot() {
        if (waitUntilElementDisplayed(mprBannerEspt, 10)) {
            addStepDescription("MPR E-spot is displayed");
            return true;
        }
        return false;
    }

    public boolean itemOrderDisplay(String prodName1) {
        waitUntilElementsAreDisplayed(overlay_ProdName, 3);
        String firstName = getText(overlay_ProdName.get(0));
        if (firstName.equalsIgnoreCase(prodName1)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUPCInlineCArtDisplay() {
        if (isDisplayed(upcNumber)) {
            addStepDescription("UPC number is displayed in SB drawer");
            return false;
        } else {
            addStepDescription("UPC number is displayed in SB drawer");
            return true;
        }
    }

    public boolean discountItemDisplay() {
        waitUntilElementsAreDisplayed(strikeOutPrice, 3);
        if (isDisplayed(strikeOutPrice.get(0))) {
            addStepDescription("Flash is applied to clearance Item");
            getSubTotalPrice();
            return true;
        } else {
            addStepDescription("Flash sale discount is not applied to Clearance Item");
            return false;
        }

    }

    public boolean getMPRESpotColorReg(String mprEspotColor) {
        return convertRgbToTextColor(regPointsEarnedEspot_Inline.getCssValue("color")).equalsIgnoreCase(mprEspotColor);

    }

    public boolean getMPRESpotColorGuest(String mprEspotColor) {
        return convertRgbToTextColor(guestPointsEarnedEspot_Inline.getCssValue("color")).equalsIgnoreCase(mprEspotColor);

    }

    public boolean removeItemfromDrawer() {
        waitUntilElementDisplayed(removeLink, 2);
        click(removeLink);
        return waitUntilElementDisplayed(emptyTextMessage, 5);
    }

    public boolean paypalButtonCheck() {
        waitUntilElementDisplayed(checkoutButton, 3);
//        switchToContinuePayPalFrameIfAvailable();
        //waitUntilElementDisplayed(paypalSbDrawer);
        if (isDisplayed(paypalSbDrawer)) {
            return true;
        } else {
            addStepDescription("Paypal is not displayed in SB drawer");
            return false;
        }
    }
    public boolean switchToContinuePayPalFrameIfAvailable() {
        boolean isIFrameAvailable = waitUntilElementDisplayed(sbModal_PayPalFrame, 30);
        if (isIFrameAvailable) {
            switchToFrame(sbModal_PayPalFrame);
        }
        return isIFrameAvailable;
    }
    public boolean clickOnPayPalButtonFromDrawerProd(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, PayPalPageActions payPalPageActions, BillingPageActions billingPageActions, String parentWindow) {
        waitUntilElementDisplayed(paypalSbDrawer, 3);
        click(paypalSbDrawer);
//        payPalPageActions.switchToContinuePayPalFrameIfAvailable();
//        waitUntilElementDisplayed(payPalPageActions.proceedPaypalBtn, 5);
//        payPalPageActions.click(payPalPageActions.proceedPaypalBtn);
//        waitForFrameToLoad(0, 45);
        payPalPageActions.payPalNewWindow(billingPageActions);
        return payPalPageActions.paypalLoginProd(paypalOrderDetailsPageActions, billingPageActions, parentWindow);
    }

    public boolean clickOnPayPalButtonFromDrawerAndInvalidCheck(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, PayPalPageActions payPalPageActions, BillingPageActions billingPageActions, String parentWindow
            , ReviewPageActions reviewPageActions, ShoppingBagPageActions shoppingBagPageActions, String email, String password) {
        waitUntilElementDisplayed(paypalSbDrawer, 3);
        click(paypalSbDrawer);
//        payPalPageActions.switchToContinuePayPalFrameIfAvailable();
//        waitUntilElementDisplayed(payPalPageActions.proceedPaypalBtn, 5);
//        payPalPageActions.click(payPalPageActions.proceedPaypalBtn);
//        waitForFrameToLoad(0, 45);
        payPalPageActions.payPalNewWindow(billingPageActions);
        return payPalPageActions.paypalLogin_WrongID(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, shoppingBagPageActions, email, password, parentWindow);
    }

    @Deprecated//#Duplicate
    public boolean clickOnPayPalButtonFromSBDrawer(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, PayPalPageActions payPalPageActions, BillingPageActions billingPageActions,
                                                   ReviewPageActions reviewPageActions, String email, String password, String parentWindow) {
        waitUntilElementDisplayed(paypalSbDrawer, 3);
        click(paypalSbDrawer);
//        payPalPageActions.switchToContinuePayPalFrameIfAvailable();
//        waitUntilElementDisplayed(payPalPageActions.proceedPaypalBtn, 5);
//        payPalPageActions.click(payPalPageActions.proceedPaypalBtn);
//        waitForFrameToLoad(0, 45);
        payPalPageActions.payPalNewWindow(billingPageActions);
        return payPalPageActions.paypalLogin(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, email, password, parentWindow);
    }

    /**
     * Get all the badges from products in mini-bag
     *
     * @return list of badges
     */
    public List<String> getBadgesForProducts() {
        List<String> badges = new ArrayList<>();
        if (inlineBadgeDisplay.size() > 0) {
            for (WebElement el : inlineBadgeDisplay) {
                badges.add(el.getText());
            }
        } else {
            addStepDescription("No badges are available for products in Mini-bag");
        }
        return badges;
    }
    public String getProdFit() {
        waitUntilElementDisplayed(productFit, 5);
        return getText(productFit).replaceAll("Fit", "").replaceAll(":", "").trim();
    }
}

