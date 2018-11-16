package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import uiMobile.pages.repo.MProductDetailsPageRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JKotha 11/08/2017.
 */
public class MProductDetailsPageActions extends MProductDetailsPageRepo {
    WebDriver mobileDriver = null;
    Logger logger = Logger.getLogger(MProductDetailsPageActions.class);
    boolean sizes;
    public static WebElement selectedSize;
    public static String ProductName = "";
    public static double Price = 0;
    public static String Size = "";
    public static String Color = "";
    public static int Quantity;
    public static double CalculatedPrice = 0;
    public static int NumberOfProductsInBag = 0;
    public static String imageURLsrc = "";
    public static String itemNo = "";

    public static String prices = "";
    //public static double actualPrice = 0.00;
    public static String regularPrice = "";

    public static String itemNum;


    public MProductDetailsPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Validate add to bag notification
     *
     * @return
     */
    public boolean clickAddToBagAndVerifyConfWindow() {
        waitUntilElementDisplayed(addToBag);
        String color = getAttributeValue(selectedColor, "value");
        String size = getAttributeValue(selectedSize, "value");
        String quantity = getSelectOptions(qtyDropDown);

        click(addToBag);

        waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 10);
        return getText(prodSize).contains(size) && getText(prodColor).contains("color") && getText(prodAty).contains(quantity);
    }

    public String getSelectedColor() {
        return getText(selectedColorText);
    }

    public boolean clickAddToBag() {
        String getBagCountBefore = getText(bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        javaScriptClick(mobileDriver, addToBag);
        waitUntilElementDisplayed(addtoBagNotification, 10);
        refreshPage();
        int bagCountAfterAddToBag = Integer.parseInt(getText(bagLink).replaceAll("[^0-9.]", ""));
        boolean isProductAddedToBag = bagCountBeforeAddToBag < bagCountAfterAddToBag;
        return isProductAddedToBag;
    }

    public void clickAddToBagBtn() {
        javaScriptClick(mobileDriver, addToBag);
    }

    public boolean clickAddToWishListAsRegistered() {
        int initial = Integer.parseInt(getText(wishListQty).replaceAll("[^0-9.]", ""));
        click(addToWishList);
        staticWait(2000);
        return Integer.parseInt(getText(wishListQty).replaceAll("[^0-9.]", "")) == initial + 1;
    }

    public boolean clickAddToWishListAsGuest() {
        click(addToWishList);
        return waitUntilElementDisplayed(loginPopupEmail, 10);
    }

    /**
     * Verify Error message for size selection
     *
     * @return
     */
    public boolean clickAddToBagSizeErr() {
        click(addToBag);
        return waitUntilElementDisplayed(selectSizeErrMessage, 10);
    }

    public boolean clickAddToBagQtyLimErr(MProductDetailsPageActions productDetailsPageActions) {
        click(addToBag);
        return waitUntilElementDisplayed(productDetailsPageActions.quantityLimitErrMessage, 10);
    }

    public boolean selectAColor(MProductDetailsPageActions productDetailsPageActions) {
        int eleColor = availableColors.size();
        if (eleColor > 1) {
            click(availableColors.get(randInt(0, (availableColors.size() - 1))));
        } else if (eleColor == 1) {
            click(availableColors.get(0));
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean clickNextSwatchForAvailInvByPos() {
        int size = availableColors.size();
        for (int i = 0; i < size; i++) {
            click(colorElementByPos(i + 1));
            staticWait();
            if (!isItemOutOfStock()) {
                break;
            }
        }
        return isItemOutOfStock();
    }

    //TODO: Adding for workaround
    public boolean isItemOutOfStock() {
        if (waitUntilElementDisplayed(outOfStockItemError, 5)) {
            return true;
        } else {
            try {
                if (availableSizes.size() > 0) {
                    return false;
                } else {
                    return true;
                }
            } catch (Throwable t) {
                return true;
            }
        }
    }

    public boolean selectASize() {
        waitUntilElementsAreDisplayed(availableSizes, 10);
        if (availableSizes.size() > 1) {
            javaScriptClick(mobileDriver, availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }
        if (availableSizes.size() == 1) {
            javaScriptClick(mobileDriver, availableSizes.get(0));
        }
        return false;
    }

    public String getSelectedSize() {
        return getText(selectedSizeText);
    }

    public String getGiftCardSelectedValue() {
        return getText(selectedGiftCardValue);
    }

    public String getItemNumber() {
        itemNum = getText(itemNumber);
        int startIndex = itemNum.indexOf(":");
        itemNum = itemNum.substring(startIndex + 2);
        return itemNum;
    }


    public void updateQty(String qty) {
        waitUntilElementDisplayed(qtyDropDown, 5);
        //scrollDownToElement(qtyDropDown);
        selectDropDownByVisibleText(qtyDropDown, qty);
        staticWait();
    }

    public int getQuantity() {
        WebElement option = null;
        String trimTxtQty = null;

        try {
            Select select = new Select(getDriver().findElement(By.xpath("//select[@name='quantity']")));
            option = select.getFirstSelectedOption();
            trimTxtQty = option.getText().replace(" ", "").replace("Quantity:", "").replace("Quantité:", "").replace("Cantidad:", "");
            return Integer.parseInt(trimTxtQty);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;

        }
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getColorName() {
        return getText(colorName);
    }

    public boolean addToBagFromPDPAndGetProductInfo() {
        if (ProductName.equalsIgnoreCase(getProductName()) &&
                Color.equalsIgnoreCase(getColorName()) &&
                Size.equalsIgnoreCase(getSelectedSize())) {
            Quantity = Quantity + getQuantity();
        } else {
            ProductName = getProductName();
            Price = getPrice();
            Color = getColorName();
            Size = getSelectedSize();
            Quantity = getQuantity();
            NumberOfProductsInBag = NumberOfProductsInBag + 1;
        }
        CalculatedPrice = Quantity * getPrice();
        click(addToBag);
//        mouseHover(shoppingBagOverlayActions.confirmationOverlay);
        return waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 20);
    }

    public boolean selectAnySizeAndClickAddToBagInPDP() {
        //Select any size from PDP Page
        selectASize();
        //click on Add to Bag
        if (addToBagFromPDPAndGetProductInfo())
            return true;
        else
            return false;
    }

    public boolean isOutfitItemOutOfStock() {
        int outFitDataAvail = selectASizeBoxes.size();
        boolean isDataOutOfStock = false;
        for (int i = 0; i < outFitDataAvail; i++) {
            int dataStock = Integer.parseInt(getAttributeValue(selectASizeBoxes.get(i), "data-stock"));
            if (dataStock <= 0) {
                isDataOutOfStock = true;
            }

        }
        logger.info("Is Product Out of stock = " + " " + isDataOutOfStock);
        return isDataOutOfStock;
    }

    public boolean addToWLAsGuest(MLoginPageActions loginPageActions) {
        click(addToWishList);
        return waitUntilElementDisplayed(loginPageActions.loginButton);
    }

    public boolean addToWLAsReg() {
        click(addToWishList);
        return waitUntilElementDisplayed(addedToWishList);
    }

    /*public boolean selectAny_Color_Size_AddToWL_Guest(MLoginPageActions loginPageActions) {
        staticWait(2000);
        disableImageZoom();
        if (!(isItemOutOfStock() && isOutfitItemOutOfStock())) {
//            selectAnyColor();
            staticWait(1000);
            selectAnySize();
            addToWLAsGuest(loginPageActions);
            staticWait(1000);
        }

        return waitUntilElementDisplayed(loginPageActions.loginButton, 30) || waitUntilElementDisplayed(loginPageActions.loginPopupButton, 30);
    }
*/

    public boolean selectAnySize() {
        staticWait();
        if (availableSizes != null) {
            int count = 0;
            int totalSize = availableSizes.size();
            //do{
            sizes = false;
            if (totalSize == 1) {
                selectedSize = availableSizes.get(0);
                click(selectedSize);
                Size = GetSize();
                sizes = true;
            } else if (count == 0 && totalSize > 1) {
                selectedSize = availableSizes.get(randInt(0, (totalSize - 1)));
                click(selectedSize);
                Size = GetSize();
                sizes = true;
            } else if (totalSize == 0) {
                sizes = false;
            } else {
                selectedSize = availableSizes.get(count);
                click(selectedSize);
                Size = GetSize();
                sizes = true;
            }
            count++;
            //}while(count<totalSize && isDisplayed(notAvailableErrorMessage));
        } else {
            sizes = false;
        }
        return sizes;
    }

    public double getPrice() {
        try {
            String price = getText(salePrice);
            if (price.contains("for")) {
                return Double.parseDouble(price.substring(price.indexOf("$")).replace("$", "").replace(" ", "").replace(",", ".").replace("USD", ""));
            } else if (price.contains("Paquete De")) {
                return Double.parseDouble(price.substring(price.indexOf("$")).replace("$", "").replaceAll("\\s", "").replace(",", ".").replace("USD", ""));
            } else if (price.contains("pour")) {
                return Double.parseDouble(price.substring(price.indexOf("pour") + 4, price.indexOf("$")).replace("$", "").replaceAll("\\s", "").replace(",", ".").replace("USD", ""));
            } else {
                return Double.parseDouble(price.replace("$", "").replace(",", ".").replace("USD", ""));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public boolean clearanceBreadCrumbVerification_PDP() {
        String clearancePDP_BreadCrumb = BreadCrumb.getText();
        if (clearancePDP_BreadCrumb.toLowerCase().contains("home") && !clearancePDP_BreadCrumb.toLowerCase().contains("clearance")) {
            return true;
        } else {
            return false;
        }
    }

    public String getSubCategory() {
        return getText(subCategory);

    }

    public boolean verificationOfBreadcrumb(MobileHomePageActions homePageActions, MobileDepartmentLandingPageActions departmentLandingPageActions, MCategoryDetailsPageAction categoryDetailsPageAction) {
        String PDP_BreadCrumb = BreadCrumb.getText();
        if (PDP_BreadCrumb.toLowerCase().contains("home")
                && PDP_BreadCrumb.toLowerCase().contains(homePageActions.deptName.toLowerCase())
                && PDP_BreadCrumb.toLowerCase().contains(departmentLandingPageActions.catName.toLowerCase()))
            /*&& PDP_BreadCrumb.contains(categoryDetailsPageAction.nameOfLink))*/ {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateColorLabel() {
        return waitUntilElementDisplayed(colorLabel, 10);
    }

    public boolean validateColorSwatch() {
        return waitUntilElementDisplayed(colorSwatches, 10);
    }

    public boolean validateProductDescription() {
        waitUntilElementDisplayed(productDescriptionHeader, 5);
        return waitUntilElementDisplayed(productDescriptionContent, 5);
    }

    public boolean validatePDPURL(String url) {
        String pdpURL = getCurrentURL();
        if (pdpURL.contains(url)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean createNewWishList(String wishListName) {
        staticWait();
        getItemNumber();
        selectASize();
        click(addToDefaultWLBtn);
        waitUntilElementDisplayed(seeMoreLink, 15);
        mobileDriver.navigate().refresh();
        selectASize();
        waitUntilElementDisplayed(seeMoreLink, 15);
        mouseClick(seeMoreLink);
        waitUntilElementDisplayed(createNewWishListLink, 15);
        mouseClick(createNewWishListLink);
        waitUntilElementDisplayed(wishListNameTextBox, 10);
        staticWait();
        clearAndFillText(wishListNameTextBox, wishListName);
        mouseHover(createWLButton);
        click(createWLButton);
        return waitUntilElementDisplayed(successfulMsgCreatedWL, 15);
    }

    public boolean addProductToExistingWishList(String wishListName) {

        click(seeMoreLink);
        waitUntilElementDisplayed(createdWishListByName(wishListName));
        click(createdWishListByName(wishListName));
        return waitUntilElementDisplayed(successfulMsgCreatedWL);

    }

    /*public boolean clickWriteAReviewLinkAsGuest(MLoginPageActions loginPageActions) {
        mouseHover(TCPLogo);
        click(writeAReviewLink());
        return waitUntilElementDisplayed(loginPageActions.emailFldOnLoginForm);
    }

    public boolean clickWriteAReviewLinAsGuestAndLogin(String emailAddr, String pwd, MLoginPageActions loginPageActions, MobileHeaderMenuActions headerMenuActions) {
        clickWriteAReviewLinkAsGuest(loginPageActions);
        return loginPageActions.loginFromLoginPopUpPage(emailAddr, pwd, headerMenuActions);
    }

    public boolean clickWriteAReviewLinAsGuestAndLoginWithInvalidCre(String invalidEmail, String invalidPwd, MLoginPageActions loginPageActions) {
        clickWriteAReviewLinkAsGuest(loginPageActions);
        return loginPageActions.loginWithInvalidCreFromLoginPopUpPage(invalidEmail, invalidPwd);
    }
*/

    public boolean loginOverlayPDP(MobileWishListLoginOverlayActions wishListLoginOverlayActions) {
        staticWait();
        selectASize();
        click(addToWishList);
        return waitUntilElementDisplayed(wishListLoginOverlayActions.loginButtonPDP, 5);
    }

    public int colorSwatchesCount() {
        return colorSwatchesList.size();
    }

    public List<String> getColorsFromSwatches() {
        List<String> colors = new ArrayList<>();
        int count = colorSwatchesCount();
        for (int i = 1; i <= count; i++) {
            String color = getAttributeValue(colorSwatchesByPosition(i), "title");
            colors.add(color);
        }
        return colors;
    }

    public boolean clickOnViewLargerImage() {
        click(viewLargerImageLink());
        return waitUntilElementDisplayed(largerImgDialogModal);
    }

    public int colorSwatchesCountFromLargerImg() {
        return colorsOnLargerImg.size();
    }

    public List<String> getColorsFromLargerImage() {
        List<String> colors = new ArrayList<>();
        int count = colorSwatchesCountFromLargerImg();
        for (int i = 1; i <= count; i++) {
            click(colorsOnLargerImgByPosition(i));
            staticWait();
            String color = getText(largerImgColor);
            colors.add(color);
        }
        return colors;
    }

    public boolean validateOldMprNotDisplayed() {
        boolean isMprSectionDisplaying = waitUntilElementDisplayed(mpRPointsSection, 5);
        boolean isMprPointsEarnedDisplaying = waitUntilElementDisplayed(mpRPointsSection, 5);

        if (isMprSectionDisplaying && isMprPointsEarnedDisplaying)
            return false;
        else
            return true;
    }

    public boolean clickPickUpStoreBtn(MobileBopisOverlayActions bopisOverlayActions) {
        javaScriptClick(mobileDriver, pickUpStore);
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 30);
    }

    public boolean clickPickUpStoreBtn2StoresSelected(MobileBopisOverlayActions bopisOverlayActions) {
        javaScriptClick(mobileDriver, pickUpStore);
        return waitUntilElementDisplayed(bopisOverlayActions.checkAvailability, 30);
    }

    public boolean isPickUpInStoreButtonAvailable() {
        return waitUntilElementDisplayed(pickUpStore, 20);
    }

    public boolean isPickUpInStoreButtonDisabled() {
        return !(isEnabled(pickUpStore));
    }

    public String getBopisSubtitleSection() {
        return getText(subtitleBopisSection);
    }

    public boolean selectAllSizesWithOneColor(MProductDetailsPageActions productDetailsPageActions, MobileHeaderMenuActions headerMenuActions, int bagLimit) {
        int bagCount = Integer.parseInt(headerMenuActions.getQty());
        int availableColor = availableColors.size();
        if (availableColor > 1) {
            for (int i = 0; i < availableColor; i++) {
                int outOfStockEleSize = 0;
                try {
                    outOfStockEleSize = outOfStockSize.size();
                } catch (Throwable t) {
                    outOfStockEleSize = 0;
                }
                click(availableColors.get(i));
                staticWait(3000);
                waitUntilElementsAreDisplayed(availableSizes, 10);
                int availableSize = availableSizes.size();
                for (int j = 0; j < (availableSize) && bagCount < bagLimit; j++) {
                    click(availableSizes.get(j));
                    clickAddToBagAndVerifyConfWindow();
//                    waitUntilElementDisplayed(shoppingBagOverlayActions.continueShopping, 40);
//                    click(shoppingBagOverlayActions.continueShopping);
//                    disableImageZoom();
//                    staticWait();
                    bagCount = Integer.parseInt(headerMenuActions.getQty());
                }
            }

        } else if (availableColor == 1) {
            click(availableColors.get(0));
            int outOfStockEleSize = 0;
            try {
                outOfStockEleSize = outOfStockSize.size();
            } catch (Throwable t) {
                outOfStockEleSize = 0;
            }
            waitUntilElementsAreDisplayed(availableSizes, 10);
            int availableSize = availableSizes.size();
            for (int j = 0; j < availableSize && bagCount < bagLimit; j++) {
                click(availableSizes.get(j));
                clickAddToBagAndVerifyConfWindow();
//                click(addToBag);
//                waitUntilElementDisplayed(shoppingBagOverlayActions.continueShopping, 40);
//                click(shoppingBagOverlayActions.continueShopping);
//                disableImageZoom();
//                staticWait();
                bagCount = Integer.parseInt(headerMenuActions.getQty());
            }
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    //Need to do some validation

    /**
     * Modified by Pooja
     * Added condition for single fit and size
     *
     * @return
     */
    public boolean selectFitAndSize() {
        waitUntilElementsAreDisplayed(availableColors, 5);
        waitUntilElementsAreDisplayed(availableSizes, 5);
        if (availableFits.size() == 1) {
            javaScriptClick(mobileDriver, availableFits.get(0));
        } else if (availableSizes.size() == 1) {
            javaScriptClick(mobileDriver, availableSizes.get(0));
        } else if (availableFits.size() > 1) {
            javaScriptClick(mobileDriver, availableFits.get(randInt(0, (availableFits.size() - 1))));
            javaScriptClick(mobileDriver, availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        } else if (availableSizes.size() > 1) {
            javaScriptClick(mobileDriver, availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }
        return waitUntilElementDisplayed(addToBag, 10);
    }

    /**
     * Created by RichaK
     *
     * @return
     */
    public boolean selectFitAndSize(String fit, String size) {
        waitUntilElementsAreDisplayed(availableColors, 5);
        waitUntilElementsAreDisplayed(availableSizes, 5);

        if (availableSizes.size() > 0) {
            try {
                WebElement selectedSize = selectASizeByText(size);
                javaScriptClick(mobileDriver, selectedSize);
            } catch (Exception e) {
                addStepDescription("Size to be selected is not available" + e.getMessage());
            }
        }
        if (availableFits.size() > 0) {
            try {
                WebElement selectedFit = selectAFitByText(fit);
                javaScriptClick(mobileDriver, selectedFit);
            } catch (Exception e) {
                addStepDescription("Fit to be selected is not available" + e.getMessage());
            }
        }

        return waitUntilElementDisplayed(addToBag, 10);
    }

    public boolean writeAReview_Reg(String reviewText) {
        waitUntilElementsAreDisplayed(writeAReview_Links, 5);
        click(getFirstElementFromList(writeAReview_Links));
        waitUntilElementDisplayed(postReviewButton, 10);
//        click(overallRating_Stars);
        staticWait();
        waitUntilElementDisplayed(reviewTextArea, 5);
        clearAndFillText(reviewTextArea, reviewText);
        click(qualityRating_Stars);
        click(stylingRating_Stars);
        click(durabilityRating_Stars);
        click(fitRating_Stars);
        click(valueRating_Stars);
        staticWait();
        click(postReviewButton);
        staticWait();
        return waitUntilElementDisplayed(submittedText, 5);
    }

    public boolean moveProdToWLFromPDP() {

        String wishListCountBefore = getText(wishListQty).replaceAll("[^0-9.]", "");
        int countBeforeAddToWL = Integer.parseInt(wishListCountBefore);
        staticWait(2000);

        click(addToWishList);
        staticWait(5000);

        String wishListCountAfter = getText(wishListQty).replaceAll("[^0-9.]", "");
        int countAfterAddToWl = Integer.parseInt(wishListCountAfter);
        staticWait(2000);

        boolean isProductAddedToWL = (countBeforeAddToWL + 1) == countAfterAddToWl;

        return isProductAddedToWL;
    }

    public void getImage(List<WebElement> enabledGC) {
        if (enabledGC.size() >= 1) {
            click(enabledGC.get(randInt(0, (enabledGC.size() - 1))));
            staticWait(1000);
        }
    }

    public boolean addGCFromPDP(MobileHomePageActions homePageActions) {

/*        if (availableGC.size() >= 1){
        for (WebElement element: availableGC)
            {
                if (element.isDisplayed())
                click(element);

                if (availableGCValues.size() >= 1) {
                    click(availableGCValues.get(randInt(0, (availableGCValues.size() - 1))));
                    staticWait(1000);
                    break;
                }
            }
        }*/
        waitUntilElementDisplayed(enabledGC, 15);
        click(enabledGC);

        if (availableGCValues.size() >= 1) {
            click(availableGCValues.get(randInt(0, (availableGCValues.size() - 1))));
            staticWait(1000);
        }
        String selectedSize = getText(selectedGCValue).replaceAll("[^0-9.]", "");
        int size = Integer.parseInt(selectedSize);

        String selectedPrice = getText(selectedGCValue).replaceAll("[^0-9.]", "").substring(0, 2);
        int price = Integer.parseInt(selectedPrice);

        boolean isSelectedPrice = size == price;

        String bagCountBefore = getText(bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAdd = Integer.parseInt(bagCountBefore);
        staticWait(2000);

        click(addToBag);
        staticWait(5000);

        String bagCountAfter = getText(bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAdd = Integer.parseInt(bagCountAfter);
        staticWait(2000);

        boolean isProductAdded = (bagCountBeforeAdd + 1) == bagCountAfterAdd;


        return isProductAdded && isSelectedPrice;
    }


    public boolean clickBackToResultsLink() {
        click(backToResultsLink);
        return verifyElementNotDisplayed(backToResultsLink);
    }

    /**
     * Created By Pooja,
     * This Method clicks on Size Chart Link on PDP and Verify respective size chart is displayed.Also close the size chart Modal
     */
    public boolean verifySizeChartAndClose(String Dept_name, String subNav_name) {
        javaScriptClick(mobileDriver, size_chart_lnk);
        if (waitUntilElementDisplayed(sizeChartModal) && waitUntilElementDisplayed(active_dpt_Size_chart(Dept_name)) && waitUntilElementDisplayed(active_SubNav_Size_chart(subNav_name))) {
            click(closeOverlay);
            return waitUntilElementDisplayed(size_chart_lnk);
        }
        return false;
    }

    /**
     * Created By Pooja,
     * This Method Verifies the content and functionality related to expand/collpase of Recommendation Section
     */
    public boolean verifyExpandCollapseRecommendationSection() {
        if (waitUntilElementDisplayed(recommendationSection) &&
                waitUntilElementDisplayed(recommendationSectionExpanded) &&
                waitUntilElementDisplayed(recommendationToggleBtn)) {
            click(recommendationToggleBtn);
            return verifyElementNotDisplayed(recommendationSectionExpanded);
        }
        return false;
    }

    /**
     * Created By Pooja,
     * This Method Verifies the product title of recommended item is similar to base item and on clicking carets, next item is displayed in Recommendation Section
     */
    public boolean verifyRecommendationSectionFunctionality() {
        scrollDownToElement(recommendationSectionExpanded);
        if (waitUntilElementDisplayed(recommendationToggleBtn)) {

            String recProductName = recommendationProductName(1).getText();
            int index = recProductName.indexOf(" ");
            String firstString = recProductName.substring(0, index);

            String productTitle = productTitleInformation().getText();
            int index1 = productTitle.indexOf(" ");
            String firstString1 = productTitle.substring(0, index1);
            if (firstString.equals(firstString1)) {
                waitUntilElementDisplayed(recommendationPrevBtnDisabled);
                if (waitUntilElementDisplayed(recommendationNextBtn)) {
                    click(recommendationNextBtn);
                    return waitUntilElementDisplayed(recommendationPrevBtn);
                }

            }
        }
        return false;
    }

    /**
     * Created By Pooja,
     * This Method Verifies the next product displayed on clicking circles below recommended product of Recommendation Section
     */
    public boolean verifyRecommendedProductsAccess() {
        scrollDownToElement(recommendationPrevBtnDisabled);
        if (waitUntilElementDisplayed(recommendationSection) &&
                waitUntilElementDisplayed(recommendationSectionExpanded) &&
                waitUntilElementDisplayed(recommendationNextBtn)) {
            click(recommendationCouponDots(1));
            return waitUntilElementDisplayed(recommendationCouponDots(1));
        }
        return false;
    }

    /**
     * Created By Pooja,
     * This Method Verifies that clicking on product image link in recommendation section redirects to PDP page of that product
     */
    public boolean verifyRecommendedProductImagePDPRedirection() {
        if (waitUntilElementDisplayed(recommendationSection)) {
            javaScriptClick(mobileDriver, recommendedProductImage);
            return waitUntilElementDisplayed(addToBag);
        }
        addStepDescription("Recommendation Products are not present");
        return false;
    }

    /**
     * Created By Pooja,
     * This Method Verifies that clicking on product name link in recommendation section redirects to PDP page of that product
     */
    public boolean verifyRecommendedProductNamePDPRedirection() {
        if (waitUntilElementDisplayed(recommendationSection)) {
            javaScriptClick(mobileDriver, recommendationProductName(1));
            return waitUntilElementDisplayed(addToBag);
        }
        addStepDescription("Recommendation Products are not present");
        return false;
    }

    /**
     * Created By Pooja, 6/13/18
     * This method clicks on product Image in PDP and full size modal with close button (X) at top right is displayed and on clicking
     * close button user is displayed with corresponding PDP
     */
    public boolean verifyFullSizeImageModal() {
        if (waitUntilElementDisplayed(heroImg)) {
            click(heroImg);
            waitUntilElementDisplayed(imageLargerView);
            waitUntilElementDisplayed(closeOverlay);
            if (closeOverlay.isDisplayed()) {
                click(closeOverlay);
                return (verifyElementNotDisplayed(imageLargerView) && waitUntilElementDisplayed(heroImg));
            }
        }
        return false;
    }

    /**
     * Verify product name is displayed in pdp page
     *
     * @return
     */
    public boolean verifyProductTextInPDP() {
        waitUntilElementDisplayed(gobackbtn);
        return Integer.parseInt(outfitsProductTitle.getCssValue("font-weight")) >= 500;
    }

    /**
     * Validate the fields in outfits pdp page
     *
     * @return true if all the fields displayed
     */
    public boolean validateOutfitFields() {
        return waitUntilElementDisplayed(addToWishList, 10) &&
                waitUntilElementDisplayed(pdpLinkInOutfits, 10) &&
                waitUntilElementDisplayed(originalPrice, 10) &&
                waitUntilElementDisplayed(actualPrice, 10) &&
                waitUntilElementDisplayed(colorLabel, 10) &&
                waitUntilElementDisplayed(outfitssizeDD, 10) &&
                waitUntilElementDisplayed(outfitsqtyDD, 10) &&
                waitUntilElementDisplayed(size_chart_lnk, 10) &&
                waitUntilElementDisplayed(outfitsOOS, 10);
    }

    /**
     * Open size chart modal
     *
     * @return
     */
    public boolean openSizeChardFromOutfits() {
        waitUntilElementDisplayed(size_chart_lnk, 20);
        javaScriptClick(mobileDriver, size_chart_lnk);
        return waitUntilElementDisplayed(sizeChartModal);
    }

    /**
     * close the size chart modal
     *
     * @return
     */
    public boolean closeSizeChartmodal() {
        waitUntilElementDisplayed(closeOverlay);
        click(closeOverlay);
        return verifyElementNotDisplayed(closeOverlay, 10);
    }

    /**
     * Clicks on product details link in outfits pdp
     *
     * @return
     */
    public boolean clickProductDetailsLink() {
        waitUntilElementDisplayed(pdpLinkInOutfits);
        javaScriptClick(mobileDriver, pdpLinkInOutfits);
        return waitUntilElementDisplayed(addToBag, 30);
    }

    /**
     * Created By Pooja
     * Clicks on Next Arrow and verify the Alt Images displayed
     *
     * @return
     */
    public boolean verifyAltImages() {
        if (waitUntilElementDisplayed(nextArrow)) {
            String heroImage = getAttributeValue(heroImg, "href");
        }
        addStepDescription("Next Arrow for Alt Images is not present");
        return false;
    }

    /**
     * Click on complete the look
     *
     * @return
     */
    public boolean clickCompleteTheLook() {
        waitUntilElementDisplayed(completeTheLookCTA, 10);
        click(completeTheLookCTA);
        return waitUntilElementDisplayed(completeTheLook, 10);
    }

    /**
     * Click on Ratings & Reviews Link
     *
     * @return
     */
    public boolean expandRatingsSection() {
        click(ratingsAndReviewsLnk);
        return waitUntilElementDisplayed(getFirstElementFromList(writeAReview_Links), 10);
    }

    /**
     * Get the prodict badge
     *
     * @return badge text
     */
    public String getProductBadge() {
        if (waitUntilElementDisplayed(productBadge, 15)) {
            return getText(productBadge);
        } else {
            return "";
        }
    }

    /**
     * Created by Richa Priya
     * click on write a review link
     */
    public boolean clickOnWriteAReviewLink(MLoginPageActions mLoginPageActions) {
        waitUntilElementDisplayed(writeAReviewButtonAndLink);
        click(writeAReviewButtonAndLink);
        return waitUntilElementDisplayed(mLoginPageActions.emailAddrField);
    }

    public boolean clickWriteAReviewAsGuest(MLoginPageActions mLoginPageActions) {
        expandRatingsSection();
        click(getFirstElementFromList(writeAReview_Links));
        return waitUntilElementDisplayed(mLoginPageActions.emailAddrField);
    }

    public boolean verifyBopisPromo() {
        return waitUntilElementDisplayed(bopisPromoContent, 5);
    }

    /**
     * Created by Richa Priya
     * Select qty on PDP
     */
    public void selectQty(String qty) {
        selectDropDownByVisibleText(qtyDropDown, qty);
    }

    public boolean verifyGiftCardPDPPage() {
        return getText(outfitsProductTitle).equalsIgnoreCase("Gift Card");
    }

    public boolean validateSocialLinks() {

        if (isDisplayed(twitterIcon) &&
                isDisplayed(facebookIcon) &&
                isDisplayed(pininterestIcon))
            return true;
        else
            return false;
    }

    public boolean clickSizeChartAndVerify() {
        if (isDisplayed(size_chart_lnk)) {
            click(size_chart_lnk);
            staticWait(3000);
            return waitUntilElementDisplayed(sizeChartModal, 20);
        }
        return false;
    }

    public boolean sizeChartToggleUS_Metric_Back_US() {
        //click(sizeChart_MetricLink);
        JavascriptExecutor js = (JavascriptExecutor) mobileDriver;
        js.executeScript("var a=document.getElementsByClassName('off');a[0].click()");
        boolean metricsLinkOn = waitUntilElementDisplayed(sizeChart_MetricOn, 10);
        click(sizeChart_USLink);
        boolean usLinkOn = waitUntilElementDisplayed(sizeChart_USOn, 10);
        if (!metricsLinkOn && !usLinkOn) {
            addStepDescription("Clicking on US link is returned " + usLinkOn + " Clicking on Metrics link returned " + metricsLinkOn);
        }
        return metricsLinkOn && usLinkOn;
    }

    public boolean clickChangeStoreAndVerify(MStoreLocatorPageActions mstoreLocatorPageActions) {
        if (waitUntilElementDisplayed(changeStoreLink, 10) && verifyElementNotDisplayed(viewBagButtonFromAddToBagConf, 10)) {
            click(changeStoreLink);
            return waitUntilElementDisplayed(mstoreLocatorPageActions.searchStoreField, 30);
        } else {
            addStepDescription("Change Store link is not displaying at PDP");
            return false;
        }
    }

    public boolean clickStarRatings() {
        if (waitUntilElementDisplayed(starRatings, 10)) {
            click(starRatings);
            return waitUntilElementDisplayed(ratingsSection, 10);
        } else {
            addStepDescription("Star ratings are not displayed for product in PDP");
            return false;
        }
    }

    public boolean isFitSectionsAvailable() {
        return waitUntilElementsAreDisplayed(availableFits, 10);
    }

    public String getProductImage() {
        return getAttributeValue(heroImg, "src");
    }

    public boolean minimiseCompleteLook() {

        if (waitUntilElementDisplayed(completeTheLook, 10)) {
            click(completeTheLook);
            return waitUntilElementDisplayed(completeLookImage, 10);
        } else {
            addStepDescription("Complete look section is not displayed");
            return false;
        }
    }

    public boolean isFitOptionsDisplay() {
        int fitSize = getElementsSize(list_SelectAFit);
        for (int i = 0; i < fitSize; i++) {
            String fitName = getText(list_SelectAFit.get(i));
            if (fitName.equalsIgnoreCase("Regular") || fitName.equalsIgnoreCase("Slim") || fitName.equalsIgnoreCase("Husky") || fitName.equalsIgnoreCase("Plus")) {
                return true;
            }
        }
        return false;
    }
}