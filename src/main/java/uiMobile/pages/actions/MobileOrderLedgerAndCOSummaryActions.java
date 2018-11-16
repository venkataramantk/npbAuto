package uiMobile.pages.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uiMobile.pages.repo.OrderLedgerRepo;

public class MobileOrderLedgerAndCOSummaryActions extends OrderLedgerRepo {
    WebDriver driver;

    public boolean validateTotalAfterRemoveCoupon() {
        click(removeCoupon);
        String offerCost = getText(offerPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float offerAmount = Float.parseFloat(offerCost);
        float estimatedTot = Float.parseFloat(estimatedCost);

        if (offerAmount == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

     /* Created By Raman
    This Method will Apply code on expresscheckout Page*/

    public boolean applyCouponCode(String couponCode) {
        //if(!isDisplayed(couponCodeFld));
        scrollDownToElement(couponCodeFieldArea);
        if (!isDisplayed(couponCodeFld)) {
            click(couponToggleButton);
        }
        addStepDescription("coupon details::" + couponCode);
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        boolean couponAppliedAtOrderLedger = waitUntilElementDisplayed(removeCoupon);
        return couponAppliedAtOrderLedger && waitUntilElementDisplayed(appliedCouponText);
    }

    /**
     * Created by RichaK
     * This method will apply coupon from the list of coupons based on the text passed.
     *
     * @return
     */
    public boolean applyCouponByText(String couponText, WebDriver mobileDriver) {
        scrollDownToElement(couponCodeFieldArea);
        if (!isDisplayed(couponCodeFld)) {
            click(couponToggleButton);
        }
        clear(couponCodeFld);
        waitUntilElementDisplayed(showMoreLnk, 20);
        javaScriptClick(mobileDriver, showMoreLnk);
        waitForTextToAppear(showMoreLnk, "Show less", 30);
        WebElement applyCouponCode = applyToOrderButtonForText(couponText);
        javaScriptClick(mobileDriver, applyCouponCode);
        staticWait(1000);
        return waitUntilElementDisplayed(couponCodeApplied, 20);
    }

    public boolean verifyCouponFieldDisplayedOnOrderLedger() {
        if (!isDisplayed(coupnField)) {
            click(orderSummaryToggleBtn);
        }
        return waitUntilElementDisplayed(coupnField);

    }

    public boolean validateTotalAfterApplyingCoupn(Float orderTotal) {
        // applyCouponCode(couponCode);
        // String originalCost = getText(originalPrice).replaceAll("[^0-9.]", "");
        String originalCost = String.valueOf(orderTotal);
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float originalAmount = Float.parseFloat(originalCost);
        float estimatedTot = Float.parseFloat(estimatedCost);

        if (originalAmount == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }


    public boolean removeCouponAndCheckTotal(WebDriver mobileDriver) {
        // scrollDownToElement(removeCoupon);
        javaScriptClick(mobileDriver, removeCoupon);
        calculatingEstimatedTotal();
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public boolean calculatingEstimatedTotal() {
        String itemCost = getText(itemPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float itemPrice = Float.parseFloat(itemCost);
        float estimatedTot = Float.parseFloat(estimatedCost);

        if (itemPrice == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public boolean verifyEstmdTotalOnOrderLedger(float estmtdTotal) {
        float orderTotalOnConfirmationPage = 0;
        if (waitUntilElementDisplayed(estimatedTot, 10)) {
            orderTotalOnConfirmationPage = Float.parseFloat(getEstimateTotal());
        }
        if (orderTotalOnConfirmationPage == estmtdTotal) return true;
        else {
            return false;
        }

    }

    public String getEstimateTotal() {
        String orderTotal = getText(estimatedTot).replace("$", "");
        return orderTotal;
    }

    public double getEstimatedTax() {
        String estTax = getText(estimatedTax).replaceAll("[^0-9.]", "");
        double estimatedTax = Double.parseDouble(estTax);
        return estimatedTax;

    }

    public double getEstimatedPriceDisplayedOnOrderLedger() {
        double estimatedPrice;
        try {
            estimatedPrice = Double.valueOf(getText(estimatedTotalPrice).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException e) {
            addStepDescription("Estimated total price is not showing at order ledger");
            estimatedPrice = 0.00;
        }
        return estimatedPrice;
    }

    /**
     * Created by Richa Priya
     *
     * @return value of Shipping field displayed on order ledger
     */
    public String getShippingDetailsFromOrderSummaryText() {
        staticWait(3000);
        String shippingCost = "";
        try {
            if (!isDisplayed(expandToggleBtn)) {
                expandOrderSummary();
            }
            //  scrollDownUntilElementDisplayed(orderSummarySection);
            shippingCost = getText(shippingTot);
            addStepDescription("shippingCost returned from UI::" +shippingCost);
        }catch(Exception e){
            shippingCost = getText(shippingTot);
        }
        return shippingCost;
    }


    /*Added by Raman Jha to validate if the coupon is applied or not
     *
     *
     * */
    public boolean ApplyCouponInOrderSummery() {
        waitUntilElementDisplayed(estimatedTotal);
        if (isDisplayed(appliedCouponText)) {
            addStepDescription("Coupon is applied in Order Summery");
            return true;
        } else {
            return false;
        }
    }

    public boolean validateTotalAfterAppliedCoupon() {
        String originalCost = getText(itemTotal).replaceAll("[^0-9.]", "");
        String couponCost = null;
        try {
            couponCost = getText(couponCodeApplied).replaceAll("[^0-9.]", "");

        } catch (NullPointerException e) {
            couponCost = null;
        }

        String estTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float originalAmount = Float.parseFloat(originalCost);
        float couponAmount = 0.0F;
        if (couponCost != (null)) {
            couponAmount = Float.parseFloat(couponCost);
        }
        float estimatedTot = Float.parseFloat(estTotal);
        float estiTot = originalAmount - couponAmount;

        if (estimatedTot == estiTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public boolean RemoveCoupanInOrderSummery() {
        waitUntilElementDisplayed(couponCodeFieldArea);
        if (isDisplayed(appliedCouponText)) {

            return false;
        } else {
            addStepDescription("Coupon line item is removed in Order Summery");
            return true;
        }
    }

    public boolean removeCoupon() {

        scrollDownToElement(couponCodeFld);
        if (!isDisplayed(couponCodeFld)) {
            click(couponToggleButton);
        }
        scrollDownToElement(couponCodeFld);
        if(isDisplayed(removeCoupon)) {
            click(removeCoupon);
            staticWait(15000);
        }
        return !waitUntilElementDisplayed(removeCoupon, 5);
    }

    /**
     * Created by Raman Jha
     * Click on expand button to display order summary
     *
     * @return true if order summary is displayed
     */

    public boolean expandOrderSummary() {
        waitUntilElementDisplayed(OrderSummeryExpandBtn);
        click(OrderSummeryExpandBtn);
        return waitUntilElementDisplayed(itemTotal, 5);

    }

    public boolean applyCoupanAndCheckEstimatedTotal() {
        String coupnsDis = null, promotionAvailable = null, shippingCost = null;
        staticWait(3000);
        String itemTotalCost = getText(itemTotal).replaceAll("[^0-9.]", "");
        try {
            coupnsDis = getText(couponPrice).replaceAll("[^0-9.]", "");
        } catch (Throwable t) {
            coupnsDis = null;
        }
        try {
            promotionAvailable = getText(promotionsTot).replaceAll("[^0-9.]", "");
        } catch (Throwable t) {
            promotionAvailable = null;
        }
        try {
            shippingCost = getText(shippingTot).replaceAll("[^0-9.]", "");
        } catch (Throwable t) {
            shippingCost = null;
        }
        double estimatedTax = getEstimatedTax();

        String estimatedCost = getEstimateTotal();

        double itemPrice = Double.parseDouble(itemTotalCost);
        double couponAmount = 0.00, promoAmt = 0.00, shippingPrice = 0.00;
        if (coupnsDis != (null)) {
            couponAmount = Double.parseDouble(coupnsDis);
        } else if (coupnsDis == (null)) {
            couponAmount = 0.00;
        }
        if (promotionAvailable != (null)) {
            promoAmt = Double.parseDouble(promotionAvailable);
        } else if (promotionAvailable == (null)) {
            promoAmt = 0.00;
        }

        if (shippingCost != (null) && !shippingCost.isEmpty()) {
            shippingPrice = Double.parseDouble(shippingCost);
        } else if (shippingCost == (null) || shippingCost.isEmpty()) {
            shippingPrice = 0.00;
        }
        double estimatedTotal = Double.parseDouble(estimatedCost);


        double total = (double) Math.round((itemPrice + estimatedTax + shippingPrice - couponAmount - promoAmt) * 100) / 100;
        if (total == estimatedTotal) {
            return true;
        } else {
            addStepDescription("The total calculating itemprice, tax, ship price, coupon amount and promotion amountt is " + total + " the estimated order total showing " + estimatedTotal);
            return false;

        }
    }

    /**
     * Raman
     *
     * @return true if the overlay opens on clicking need help link
     */
    public boolean validateOverlayOnClickingNeedHelpLink() {
        scrollDownToElement(couponCodeFieldArea);
        click(couponToggleButton);
        waitUntilElementDisplayed(needHelpCouponLnk);
        click(needHelpCouponLnk);
        return waitUntilElementDisplayed(couponHelpPage);
    }


    /**
     * Created by Richa Priya
     *
     * @return true if the overlay opens on clicking need help link
     */
    public boolean verifyGCBalance_Calculation() {
        boolean flag = false;
        String estimatedTtl = getText(estimatedTotalPrice).replaceAll("[^0-9.]", "");
        String giftCardTtl = getText(giftCardTot).replaceAll("[^0-9.]", "");
        String balanceTtl = getText(estimatedTot).replaceAll("[^0-9.]", "");

        double estimatedCost = Double.parseDouble(estimatedTtl);
        double giftCardCost = Double.parseDouble(giftCardTtl);
        double balanceTtlCost = Double.parseDouble(balanceTtl);

        if ((estimatedCost - giftCardCost) == balanceTtlCost) {
            flag = true;
        }
        return flag;

    }

    /**
     * Verify total order cost
     *
     * @param estimatedTotal expected estimate cost
     * @return true if the expected and actual matches
     */
    public boolean validateEstmtedTtlOnOrderLedger(String estimatedTotal) {
        staticWait(3000);
        String orderTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float estimatedCost = Float.parseFloat(estimatedTotal);

        if (orderTot == estimatedCost)
            return true;
        else
            return false;
    }

    public String getGiftCardTotal() {
        return getText(giftCardTot).replace("$", "");
    }

    /**
     * Created by Richa Priya
     * Verify total order cost
     *
     * @return true if the expected and actual matches
     */
    public boolean validateEstimatedTtlWithShipCostOnOrderLedger() {
        staticWait(3000);
        String orderTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");
        String shipTotal = getText(shippingTot).replaceAll("[^0-9.]", "");
        String estTax = getText(estimatedTax).replaceAll("[^0-9.]", "");
        String itemCost = getText(itemPrice).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float shipTotalCost = Float.parseFloat(shipTotal);
        float estTaxCost = Float.parseFloat(estTax);
        float itemPriceCost = Float.parseFloat(itemCost);

        float totalPrice = itemPriceCost + shipTotalCost + estTaxCost;

        return totalPrice == orderTot;
    }

    public boolean validateLineItems_OrderLedgerSection() {
        return waitUntilElementDisplayed(itemPriceTtlLineItem, 10)
                && waitUntilElementDisplayed(estimatedTtlLineItem)
                && waitUntilElementDisplayed(estimatedTaxLineItem);
    }

    public boolean validateLineItems_OrderLedgerSectionAfterCouponApplied() {
        return waitUntilElementDisplayed(itemPriceTtlLineItem, 10)
                && waitUntilElementDisplayed(estimatedTtlLineItem)
                && waitUntilElementDisplayed(estimatedTaxLineItem)
                && waitUntilElementDisplayed(couponPrice);
    }

    /**
     * Created by RichaK
     * return the total for gift service price.
     *
     * @return
     */
    public String getGiftServicePrice() {
        return getText(giftServiceTtlValue);
    }

    /**
     * Created by Richak
     * Verify the order total by passing the gift service amount.
     *
     * @param giftServiceCostStr
     * @return
     */
    public boolean verifyOrderTotalWithGiftService(String giftServiceCostStr) {
        String itemCostStr = getText(itemTotal).replaceAll("[^0-9.]", "");
        String taxTotalStr = getText(taxTotal).replaceAll("[^0-9.]", "");
        String estTotalStr = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        giftServiceCostStr = giftServiceCostStr.replaceAll("[^0-9.]", "");

        float itemCostAmount = Float.parseFloat(itemCostStr);
        float taxTotalAmount = Float.parseFloat(taxTotalStr);
        float giftServiceAmountF = Float.parseFloat(giftServiceCostStr);

        float actualEstimatedTot = Float.parseFloat(estTotalStr);
        String actualEstimatedTotStr = String.valueOf(actualEstimatedTot);

        float expectedEstTotal = itemCostAmount + taxTotalAmount + giftServiceAmountF;
        String expectedEstTotalStr = String.format("%.2f", expectedEstTotal); //Rounding upto 2 decimal points

        if (expectedEstTotalStr.equals(actualEstimatedTotStr)) {
            return true;
        }
        return false;
    }

    /**
     * Created by RichaK
     * This will return the total item price.
     *
     * @return
     */
    public String getItemsTotalAmount() {
        return getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
    }

    /**
     * Created by RichaK
     * This will return the item count in the order ledger.
     *
     * @return
     */
    public int getItemsCountInOL() {
        String item = getText(itemsCount);
        int startIndex = item.indexOf("(");//Integer.parseInt(item);
        int endIndex = item.indexOf(")");
        int itemCount = Integer.parseInt(item.substring(startIndex + 1, endIndex));
        return itemCount;
    }

    public boolean isDisabledApplyToOrder_Applying(String couponValue) {
        click(merchandiseApplyButton(couponValue));
        boolean applyButtonDisabled = !isEnabled(merchandiseApplyButton(couponValue));
        addStepDescriptionWithStatus(applyButtonDisabled, "Is the applyToOrder button disabled while applying " + applyButtonDisabled);
        return applyButtonDisabled;
    }


    public boolean clickMerchandiseApplyCoupon(String couponValue, String couponCode) {
        boolean isMerchApplied = false;
        try {
            isDisabledApplyToOrder_Applying(couponValue);
            staticWait(2000);
            isMerchApplied = waitUntilElementDisplayed(merchandiseRemoveButton(couponValue), 20);
        } catch (Exception e) {
            scrollDownToElement(couponCodeFld);
            isMerchApplied = applyCouponCode(couponCode);
        }

        scrollDownToElement(couponCodeFld);
        return isMerchApplied;
    }


    /**
     * Created by Richa Priya
     * This will return true if coupon is expanded
     */
    public boolean expandCouponSectionIfNot() {

        if (!isDisplayed(couponCodeFld)) {
            javaScriptClick(driver, couponToggleButton);
        }
        return waitUntilElementDisplayed(couponCodeFld);
    }


    public String applyCouponAndCheckError(String couponCode) {
        if (!isDisplayed(couponCodeFld)) {
            click(couponToggleButton);
        }
        addStepDescription("coupon details::" + couponCode);
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        return getText(couponErrorMessage);
    }

    public String getShippingPrice() {
        staticWait(3000);
        String shippingCost = "";
        try {
            if (!isDisplayed(expandToggleBtn)) {
                expandOrderSummary();
            }
            //  scrollDownUntilElementDisplayed(orderSummarySection);
            if(getText(shippingTot).equalsIgnoreCase("Free")){
                shippingCost = getText(shippingTot);
            }else{
                shippingCost = getText(shippingTot).replaceAll("[^0-9.]", "");

            }
            addStepDescription("shippingCost returned from UI::" +shippingCost);
        }catch(Exception e){
            shippingCost = getText(shippingTot).replaceAll("[^0-9.]", "");
        }
        return shippingCost;
    }

}

