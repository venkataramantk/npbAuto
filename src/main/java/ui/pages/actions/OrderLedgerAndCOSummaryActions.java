package ui.pages.actions;

import org.openqa.selenium.WebElement;
import ui.pages.repo.OrderLedgerRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderLedgerAndCOSummaryActions extends OrderLedgerRepo {


    public boolean applyCouponCode(String couponCode) {
        waitUntilElementDisplayed(couponCodeFld, 20);
        isDisabledApplyButton_Applying(couponCode);
        boolean couponAppliedAtOrderLedger = false;
        if(waitUntilElementDisplayed(couponCodeApplied,20)){
            couponAppliedAtOrderLedger = true;
        }
        if(waitUntilElementDisplayed(freeShippingCouponApplied,20)){
            couponAppliedAtOrderLedger = true;
        }
        boolean appliedCouponTxt = waitUntilElementDisplayed(appliedCouponText, 20);
        if (!couponAppliedAtOrderLedger && !appliedCouponTxt) {
            addStepDescription(" The coupon applied at order ledger is " + couponAppliedAtOrderLedger
                    + " The coupon applied showing at applied coupons section " + appliedCouponTxt);
        }

        return couponAppliedAtOrderLedger && appliedCouponTxt;
    }

    public boolean applyCouponCode(String couponCode, String couponAmt) {
        if (!isDisplayed(merchandiseRemoveButton(couponAmt))) {
            return applyCouponCode(couponCode);
        } else {
            return isDisplayed(merchandiseRemoveButton(couponAmt));
        }
    }

    public boolean isMerchandiseCouponApplied(String couponValue) {
        boolean merchndiseRemoveButton = false;
        try {
            merchndiseRemoveButton = isDisplayed(merchandiseRemoveButton(couponValue));
        } catch (Exception e) {
            return merchndiseRemoveButton;
        }
        return merchndiseRemoveButton;
    }

    public boolean removeAppliedCoupons() {
        WebElement remove1stButton = null;
        try {
            remove1stButton = removeButtonsOfCoupons.get(0);
        } catch (Exception e) {
            return false;
        }
        scrollToTheTopHeader(removeButtonsOfCoupons.get(0));
        boolean isRemoveButtonDisabled = false;
        for (WebElement removeButton : removeButtonsOfCoupons) {
            click(removeButton);
            isRemoveButtonDisabled = !isEnabled(removeButton);
            verifyElementNotDisplayed(removeButton, 5);
        }
        addStepDescription("Is Remove Button disabled while removing the coupon? " + isRemoveButtonDisabled);
        return isRemoveButtonDisabled && !waitUntilElementsAreDisplayed(removeButtonsOfCoupons, 1);
    }

    public boolean calculatingEstimatedTotal() {
        String promotion = null, couponDiscount = null;
        String itemCost = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getEstimateTotal();
        if(isDisplayed(orderPromo)) {
            try {
                couponDiscount = getText(orderPromo).replaceAll("[^0-9.]", "");
            } catch (Throwable t) {
                couponDiscount = null;
            }
            //couponDiscount = getText(orderPromo).replaceAll("[^0-9.]", "");
        }
        if(isDisplayed(promotionApplied)) {
            try {
                promotion = getText(promotionApplied).replaceAll("[^0-9.]", "");
            } catch (Throwable t) {
                promotion = null;
            }
           // promotion = getText(promotionApplied).replaceAll("[^0-9.]", "");

        }
        double couponAmount = 0.00;
        double promoAmt = 0.00;
        double itemPrice = Float.parseFloat(itemCost);
        double estimatedTot = Double.parseDouble(estimatedCost);
        double estimatedTax = getEstimatedTax();
        if (couponDiscount != (null)) {
            couponAmount = Double.parseDouble(couponDiscount);
        } else if (couponDiscount == (null)) {
        couponAmount = 0.00;
        }
        if (promotion != (null)) {
            promoAmt = Double.parseDouble(promotion);
        } else if (promotion == (null)) {
        promoAmt = 0.00;
        }
        double totalScriptCalc = (double) Math.round((itemPrice + estimatedTax - promoAmt - couponAmount) * 100) / 100;
        if (totalScriptCalc == estimatedTot) {
           // return true && isDisplayed(tax_TotalTxt);
            return true;
        } else {
            return false;
        }
//        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public double getEstimatedTax() {
        String estTax = "";
        double estimatedTax = 0.00;
        try {
            estTax = getText(tax_Total).replaceAll("[^0-9.]", "");
            estimatedTax = Double.parseDouble(estTax);
        }catch (Throwable t){

        }
        return estimatedTax;

    }

    public String getEstimateTotal() {

        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }
    public String getCouponAppliedText() {

        String estimatedCost = getText(couponCodeApplied).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }

    public double getCouponDiscount() {
        double couponDiscount = 0.00;
        try {
            couponDiscount = Double.valueOf(getText(couponCodeApplied).replaceAll("[^0-9.]", ""));
        } catch (Throwable t) {
            couponDiscount = 0.00;
        }
        return couponDiscount;
    }

    public boolean estimatedTotalAfterAppliedCoupon() {
        String couponDiscount = null, promotion = null, shippingPr = null;
        staticWait(3000);
        String itemCost = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
        try {
            couponDiscount = getText(orderPromo).replaceAll("[^0-9.]", "");
        } catch (Throwable t) {
            couponDiscount = null;
        }
        try {
            promotion = getText(promotionApplied).replaceAll("[^0-9.]", "");
        } catch (Throwable t) {
            promotion = null;
        }
        try {
            shippingPr = getText(shippingTotalPrice).replaceAll("[^0-9.]", "");
        } catch (Throwable t) {
            shippingPr = null;
        }
        double estimatedTax = getEstimatedTax();

        String estimatedCost = getEstimateTotal();

        double itemPrice = Double.parseDouble(itemCost);
        double couponAmount = 0.00, promoAmt = 0.00, shippingPrice = 0.00;
        if (couponDiscount != (null)) {
            couponAmount = Double.parseDouble(couponDiscount);
        } else if (couponDiscount == (null)) {
            couponAmount = 0.00;
        }
        if (promotion != (null)) {
            promoAmt = Double.parseDouble(promotion);
        } else if (promotion == (null)) {
            promoAmt = 0.00;
        }

        if (shippingPr != (null) && !shippingPr.isEmpty()) {
            shippingPrice = Double.parseDouble(shippingPr);
        } else if (shippingPr == (null) || shippingPr.isEmpty()) {
            shippingPrice = 0.00;
        }
        double estimatedTot = Double.parseDouble(estimatedCost);


        double total = (double) Math.round((itemPrice + estimatedTax + shippingPrice - couponAmount - promoAmt) * 100) / 100;
        if (total == estimatedTot) {
            return true;
        } else {
            addStepDescription("The total calculating itemprice, tax, ship price, coupon amt and promo amt is " + total + " the estimated order total showing " + estimatedTot);
            return false;

        }
    }


    public boolean isCouponPercentOffDisplayAtOL(String couponValue) {
        double couponDiscount = 0.00, promotion = 0.00;
        String itemCost = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
        double itemPrice = Double.valueOf(itemCost);

        try {
            promotion = Double.valueOf(getText(promotionApplied).replaceAll("[^0-9.]", ""));
        } catch (Throwable t) {
            promotion = 0.00;
        }
        itemPrice = (double) Math.round((itemPrice - promotion) * 100) / 100;
        double percentOFFOnItem = (double) Math.round(((itemPrice * Double.valueOf(couponValue)) / 100) * 100) / 100;
        try {
            couponDiscount = Double.valueOf(getText(orderPromo).replaceAll("[^0-9.]", ""));
        } catch (Throwable t) {
            couponDiscount = 0.00;
        }

        if (couponDiscount == percentOFFOnItem) {
            return true;
        } else {
            return false;
        }
    }

    // validate the applied coupon should be equal to subtract the value from original price and offer price.(For one prod)
    public boolean validateTotalAfterAppliedCoupon() {
        String originalCost = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
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

    public boolean validateTotalAfterAppliedAssociateID() {
        String couponCost = "0";
        String originalCost = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
        try{
            couponCost = getText(promotionApplied).replaceAll("[^0-9.]", "");
        }catch (Exception e){

        }
        String estTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float originalAmount = Float.parseFloat(originalCost);
        float couponAmount = Float.parseFloat(couponCost);
        float estimatedTot = Float.parseFloat(estTotal);
        float estiTot = originalAmount - couponAmount;

        if (estimatedTot == estiTot) {
            return true;
        }
        return false;
    }

    public boolean isAssociatePromoApplied(int associatePercent) {
        double promotion = 0.00;
        double originalCost = Double.valueOf(getText(itemsTotalPrice).replaceAll("[^0-9.]", ""));

        double associatePromo = ((double) Math.round(((originalCost) * associatePercent)) / 100);
        try {
            promotion = (double) Math.round(Double.valueOf(getText(promotionApplied).replaceAll("[^0-9.]", "")) * 100) / 100;
        } catch (Throwable t) {
            promotion = 0.00;
        }
        if (associatePromo == promotion) {
            return true;
        } else {
            addStepDescription("The associate promo applied expecting is " + associatePromo + " promotion actual applied is " + promotion);
            return false;
        }

    }

    public boolean associatePromoAfterCouponApplied(int associatePercent) {
        double promotion = 0.00, couponDiscount = 0.00;
        double originalCost = Double.valueOf(getText(itemsTotalPrice).replaceAll("[^0-9.]", ""));
        try {
            couponDiscount = Double.valueOf(getText(orderPromo).replaceAll("[^0-9.]", ""));
        } catch (Throwable t) {
            couponDiscount = 0.00;
        }
        double associatePromo = (double) Math.round(((originalCost - couponDiscount) * associatePercent)) / 100;
        try {
            promotion = (double) Math.round((Double.valueOf(getText(promotionApplied).replaceAll("[^0-9.]", ""))) * 100) / 100;
        } catch (Throwable t) {
            promotion = 0.00;
        }
        if (associatePromo == promotion) {
            return true;
        } else {
            return false;
        }

    }

    public double getGiftCardTotalApplied() {
        double gcPrice = 0.00;
        try {
            gcPrice = Double.valueOf(getText(giftCardsTotal).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException e) {
            addStepDescription("Gift Cards total is not showing at order ledger");
            gcPrice = 0.00;
        }
        return gcPrice;
    }

    public boolean clickMerchandiseApplyCoupon(String couponValue, String couponCode) {
        boolean isMerchApplied = false;
        try {
            isDisabledApplyToOrder_Applying(couponValue);
            staticWait(2000);
            isMerchApplied = waitUntilElementDisplayed(merchandiseRemoveButton(couponValue), 20);
        } catch (Exception e) {
            scrollToTheTopHeader(couponCodeFld);
            isMerchApplied = applyCouponCode(couponCode);
        }

        scrollToTheTopHeader(couponCodeFld);
        return isMerchApplied;
    }

    public boolean isDisabledApplyToOrder_Applying(String couponValue) {
        click(merchandiseApplyButton(couponValue));
        boolean applyButtonDisabled = !isEnabled(merchandiseApplyButton(couponValue));
        addStepDescriptionWithWarn(applyButtonDisabled, "Is the applyToOrder button disabled while applying " + applyButtonDisabled);
        return applyButtonDisabled;
    }

    public boolean isDisabledApplyButton_Applying(String couponCode) {
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        boolean isButtonDisabled = !isEnabled(applyCouponButton);
        addStepDescriptionWithWarn(isButtonDisabled, "Is the apply button disabled while applying " + isButtonDisabled);
        return isButtonDisabled;
    }

    public boolean applyLTYPointsToOrder() throws ParseException {
        if (isCouponDateExpired()) {
            addStepDescription("The coupon is expired");
            return false;
        } else {
            double estTotal = Double.valueOf(getEstimateTotal());
            double couponValue = getCouponValue();
            double couponDiscount = 0.00;
            if (couponValue != 0.00) {
                if (estTotal > couponValue) {
                    click(applyToOrderLTY_ByPos(1));
                    waitUntilElementDisplayed(removeCoupon, 20);
                    waitUntilElementDisplayed(orderPromo, 20);
                    try {
                        couponDiscount = Double.valueOf(getText(orderPromo).replaceAll("[^0-9.]", ""));
                    } catch (Throwable t) {
                        couponDiscount = 0.00;
                    }
                    return couponDiscount == couponValue;
                } else {
                    addStepDescription("The coupon value is more then the estimated total");
                    return false;
                }
            } else {
                addStepDescription("The coupon value is equal to " + couponValue);
                return false;
            }
        }

    }

    public boolean applyPlaceCashToOrder() throws ParseException {
        if (isPlaceCashDateExpired()) {
            addStepDescription("The coupon is expired");
            return false;
        } else {
            double estTotal = Double.valueOf(getEstimateTotal());
            double couponValue = getCouponValue();
            double couponDiscount = 0.00;
            if (couponValue != 0.00) {
                if (estTotal >= couponValue*2) {
                    click(applyToOrderPlaceCash_ByPos(1));
                    waitUntilElementDisplayed(removeCoupon, 20);
                    waitUntilElementDisplayed(orderPromo, 20);
                    try {
                        couponDiscount = Double.valueOf(getText(orderPromo).replaceAll("[^0-9.]", ""));
                    } catch (Throwable t) {
                        couponDiscount = 0.00;
                    }
                    return couponDiscount == couponValue;
                } else {
                    addStepDescription("The coupon value is more then the estimated total");
                    return false;
                }
            } else {
                addStepDescription("The coupon value is equal to " + couponValue);
                return false;
            }
        }

    }
    public boolean clickFreeShippingButtonFromRewards() {
        click(freeShippingApplyButton);
        return waitUntilElementDisplayed(freeShippingRemoveButton, 15);

    }

    public boolean isFreeShippingApplied() {
        return waitForTextToAppear(shippingTotalPrice, "Free", 30);
    }

    public boolean isCouponDateExpired() throws ParseException {
        if(!waitUntilElementsAreDisplayed(rewardsLTYExpireDates, 1)){
            clickShowMoreAndVerifyAvailCoupons();
        }
        for (WebElement expDate : rewardsLTYExpireDates) {
            Date expiryDate = new SimpleDateFormat("M/dd/yy").parse(getText(expDate).replaceAll(" ", "").replaceAll("[^0-9/.]", ""));
            Date currentDate = new SimpleDateFormat("M/dd/yy").parse(getCurrentDateTime("M/dd/yy"));
            if (expiryDate.compareTo(currentDate) > 0) {
                System.out.println("The coupon expiry date has still time: " + expiryDate.toString());
                return false;
            } else if (expiryDate.compareTo(currentDate) < 0) {
                System.out.println("The coupon expiry date is expired: " + expiryDate.toString());
                return true;
            } else if (expiryDate.compareTo(currentDate) == 0) {
                System.out.println("The coupon expiry date is today's date: " + expiryDate.toString());
                return false;
            } else {
                System.out.println("Something is invalid");
                return true;
            }
        }
        return true;
    }
    public boolean isPlaceCashDateExpired() throws ParseException {
        if(!waitUntilElementsAreDisplayed(placeCashExpireDates, 1)){
            clickShowMoreAndVerifyAvailCoupons();
        }
        for (WebElement expDate : placeCashExpireDates) {
            String expDateVal = getText(expDate);
            int start = expDateVal.indexOf("-");
            expDateVal = expDateVal.substring(start + 1).trim();

            Date expiryDate = new SimpleDateFormat("M/dd/yy").parse((expDateVal).replaceAll(" ", "").replaceAll("[^0-9/.]", ""));
            Date currentDate = new SimpleDateFormat("M/dd/yy").parse(getCurrentDateTime("M/dd/yy"));
            if (expiryDate.compareTo(currentDate) > 0) {
                System.out.println("The coupon expiry date has still time: " + expiryDate.toString());
                return false;
            } else if (expiryDate.compareTo(currentDate) < 0) {
                System.out.println("The coupon expiry date is expired: " + expiryDate.toString());
                return true;
            } else if (expiryDate.compareTo(currentDate) == 0) {
                System.out.println("The coupon expiry date is today's date: " + expiryDate.toString());
                return false;
            } else {
                System.out.println("Something is invalid");
                return true;
            }
        }
        return true;
    }

    public double getCouponValue() {
        for (WebElement couponValue : rewardsCouponNames) {
            int startIndex = getText(couponValue).indexOf("$");
            int endIndex = 0;
            String couponVal = getText(couponValue);
            if(!couponVal.contains("PLACE CASH")) {
                 endIndex = getText(couponValue).indexOf(" (");
            }
            else{
                 endIndex = getText(couponValue).indexOf(" OFF");
            }
            String coupon = "0";
            if (couponVal.contains("REWARDS")) {
                coupon = getText(couponValue).substring(startIndex + 1);
                return Double.parseDouble(coupon);
            }
            else if(coupon.contains("LOYALTY") || (couponVal.contains("PLACE CASH"))){
                coupon = getText(couponValue).substring(startIndex + 1, endIndex);
                return Double.parseDouble(coupon);
            }
//            else if(couponVal.contains("PLACE CASH"))
//                coupon = getText(couponValue).substring(startIndex + 1, endIndex);
//            }


        }
        return 0.00;
    }

    public double getPromotionDiscount() {
        double promotion = 0.00;
        try {
            promotion = Double.valueOf(getText(promotionApplied).replaceAll("[^0-9.]", ""));
        } catch (Throwable t) {
            promotion = 0.00;
        }
        return promotion;
    }

    public boolean clickShowMoreAndVerifyAvailCoupons() {
        int rewardsCountBeforeShowMoreClick = rewardsContentDisplay.size();
        if (isDisplayed(showMoreLnk)) {
            click(showMoreLnk);
            boolean showLessLink = waitForTextToAppear(showMoreLnk, "Show less", 30);
            int rewardsCountAfterShowMoreClick = rewardsContentDisplay.size();
            return (rewardsCountBeforeShowMoreClick < rewardsCountAfterShowMoreClick) && showLessLink;
        } else {
            return false;
        }

    }
    public boolean orderLedgerRmvCoupon(){
        waitUntilElementDisplayed(estimatedTotal,2);
        if(isDisplayed(coupon_Label)){
            addStepDescription("Coupon line item is getting displayed in Order Ledger");
            return false;
        }
        else{
            return true;
        }
    }

    public boolean bopisCouponDisplay(){
        waitUntilElementDisplayed(showMoreLnk,3);
        click(showMoreLnk);
        waitUntilElementDisplayed(bopisGenCoupon,2);
        if(isDisplayed(bopisGenCoupon) && isDisplayed(bopisMPRCoupon) && isDisplayed(bopisPLCCCoupon)){
            return true;
        }
        else{
            addStepDescription("BOPIS coupons are not displayed in the environment check in CMC");
        return false;
        }
    }

    public boolean checkCouponAfterAddingAssociateDis(String coupon) {
        waitUntilElementDisplayed(orderLedgerSection, 2);
        if (isDisplayed(couponCodeApplied)) {
            addStepDescription("Promotion is not removed after applying associate ID");
            return false;
        } else {
            clearAndFillText(couponCodeFld, coupon);
            click(applyCouponButton);
            if (isDisplayed(couponCodeError)) {
                return true;
            } else {
                addStepDescription("$Off coupon is getting applied with associate ID");
                return false;
            }
        }
    }
    public boolean verifyLTYEarnPointsMatchesWithOrderTotal() {
        int estimatedRoundUp = (int) Math.round((Double.valueOf(getEstimateTotal()) * 100)) / 100;
        int earnPoints = Integer.parseInt(getText(ltyEarnPoints));
        return ((estimatedRoundUp == earnPoints) || (estimatedRoundUp * 2 == earnPoints));
    }

    public boolean verifyLTYPtsMatchesWithOrderTotal_PLCC() {
        int estimatedRoundUp = (int) Math.round((Double.valueOf(getText(estimatedTotal)) * 100)) / 100;
        int earnPoints = Integer.parseInt(getText(ltyEarnPoints));
        return ((estimatedRoundUp * 2 == earnPoints) || (estimatedRoundUp * 3 == earnPoints));
    }

}
