package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ui.pages.repo.ProductDetailsPageRepo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skonda on 5/25/2016.
 */
public class ProductDetailsPageActions extends ProductDetailsPageRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(ProductDetailsPageActions.class);
    boolean colours;
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
    public static double actualPrice = 0.00;
    public static String regularPrice = "";

    public static String itemNum;


    public ProductDetailsPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickAddToBagAndVerifyConfWindow() {
        staticWait();
        click(addToBag);
        return waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);
    }

    public boolean clickAddToBagAndVerifyCOOnConf() {
        staticWait();
        click(addToBag);
        boolean coNotifi = waitUntilElementDisplayed(checkoutFromNotification, 30);
        clickClose_ConfViewBag();
        return coNotifi;

    }
    public void clickClose_ConfViewBag(){
        if(isDisplayed(conf_ButtonClose)) {
            click(conf_ButtonClose);
            waitUntilElementClickable(shoppingBagIcon, 15);
        }
    }

    public boolean clickAddToBag() {
        if (isItemOutOfStock()) {
            addStepDescription("The item is out of stock");
        }
        String getBagCountBefore = getText(bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        //staticWait(2000);
        click(addToBag);
        boolean isViewBagConf = waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);
        if(isDisplayed(conf_ButtonClose)){
        click(conf_ButtonClose);}
//        if (isDisplayed(continueShoppingLink)) {
//            click(continueShoppingLink);
//        }
        staticWait(3000);
        waitUntilElementClickable(shoppingBagIcon, 15);
        String getBagCountAfter = getText(bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
//        waitUntilElementDisplayed(shoppingBagIcon, 5);

        boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
        return isProductAddedToBag || isViewBagConf;
    }

    public boolean clickAddToWishListAsGuest() {
        click(addToWishList);
        return waitUntilElementDisplayed(loginPopupEmail);
    }

    public boolean clickAddToBagSizeErr(ProductDetailsPageActions productDetailsPageActions) {
        click(addToBag);
        return waitUntilElementDisplayed(productDetailsPageActions.selectSizeErrMessage, 10);
    }

    public boolean clickAddToBagQtyLimErr() {
        click(addToBag);
        return waitUntilElementDisplayed(quantityLimitErrMessage, 10);
    }

    public boolean selectAColor(ProductDetailsPageActions productDetailsPageActions) {
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
        if (waitUntilElementDisplayed(outOfStockItemError, 1)) {
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

    public int getAvailSizeFromSelectedColors() {
        int availColors = availableColors.size();
        int availSize = 0;
        for (int i = 1; i <= availColors; i++) {
            click(colorElementByPos(i));
            availSize = availableSizes.size();
            if (availSize >= 1) {
                return availSize;
            }
        }
        return availSize;

    }

    public boolean selectASize() {
        waitUntilElementsAreDisplayed(availableSizes, 10);
        int eleSize = availableSizes.size();
        if (eleSize == 0) {
            eleSize = getAvailSizeFromSelectedColors();
        }

        for (int i = 1; i <= eleSize; i++) {
            if (isEnabled(selectASizeByPosition(i))) {
                if (isSelected(selectASizeByPosition(i))) {
                    return true;
                } else {
                    click(selectASizeByPosition(i));
                    return isSelected(selectASizeByPosition(i));
                }
            }
        }


        return false;
    }

    public String getSelectedSize() {
        try {
            return GetSize();//getText(selectedSizeElement);
        } catch (Throwable t) {
            return "The size is not selected";
        }
    }

    public void updateQty(String qty) {
        waitUntilElementDisplayed(qtyDropDown, 5);
        selectDropDownByVisibleText(qtyDropDown, qty);
        addStepDescription("Qty updated Expecting: "+qty);
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

    public boolean addToBagFromPDPAndVerify() {

        click(addToBag);
        boolean viewBagConf = waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 20);
        clickClose_ConfViewBag();
        return viewBagConf;
    }

    public boolean selectAnySizeAndClickAddToBagInPDP() {
        isOutfitItemOutOfStock();
        //Select any size from PDP Page
        selectASize();
        //click on Add to Bag
        if (addToBagFromPDPAndVerify())
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

    public boolean addToWLAsGuest(LoginPageActions loginPageActions) {
        click(addToWishList);
        staticWait(3000);
        return waitUntilElementDisplayed(loginPageActions.emailAddrField);
    }

    public boolean addToWLAsReg() {
        if (waitUntilElementDisplayed(addToWishList, 5))
            click(addToWishList);
        return waitUntilElementDisplayed(addedToWishList);
    }

    public boolean selectAny_Color_Size_AddToWL_Guest(LoginPageActions loginPageActions) {
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

    public boolean verificationOfBreadcrumb(HomePageActions homePageActions, DepartmentLandingPageActions departmentLandingPageActions, CategoryDetailsPageAction categoryDetailsPageAction) {
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


    public boolean clickWriteAReviewLinkAsGuest(LoginPageActions loginPageActions) {
        mouseHover(TCPLogo);
        click(writeAReviewLink());
        return waitUntilElementDisplayed(loginPageActions.emailFldOnLoginForm);
    }

    public boolean clickWriteAReviewLinkAsRemembered(LoginDrawerActions loginDrawerActions) {
        mouseHover(TCPLogo);
        click(writeAReviewLink());
        return waitUntilElementDisplayed(loginDrawerActions.rememberedLogout);
    }

    public int colorSwatchesCount() {
        return colorSwatchesList.size();
    }

    public List<String> getColorsFromSwatches() {
        List<String> colors = new ArrayList<>();
        int count = colorSwatchesCount();
        for (int i = 1; i <= count; i++) {
            String color = getAttributeValue(availColorSwatchByPos(i), "title");
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

    public boolean clickPickUpStoreBtn(BopisOverlayActions bopisOverlayActions) {
//       driver.navigate().refresh();
//        scrollDownUntilElementDisplayed(pickUpStore);
        click(pickUpStore);
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 30);
    }

    public boolean clickPickUpStoreForSelectedSize() {
        click(pickUpStore);
        boolean viewBagConf = waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);
        clickClose_ConfViewBag();
        return viewBagConf;
    }
    public boolean clickPickUpStoreForSelectedSizeMaxErr(BopisOverlayActions bopisOverlayActions,String zipcode,String error) {
        click(pickUpStore);
        bopisOverlayActions.enterZipAndSearchStore(zipcode);
        WebElement selectStore = bopisOverlayActions.selectStoreByPosition(1);
        click(selectStore);
        String err = bopisOverlayActions.getText(bopisOverlayActions.maxLimitError);
        if (err.contains(error)){
            return true;
        } else {
            addStepDescription("Max Limit error is not displayed");
            return false;
        }
    }

    public boolean clickPickUpStoreForTwoSelectedSizeMaxErr(BopisOverlayActions bopisOverlayActions,String zipcode,String error) {
        click(pickUpStore);
        bopisOverlayActions.enterZipAndSearchStore(zipcode);
        WebElement selectStore = bopisOverlayActions.selectStoreByPosition(2);
        click(selectStore);
        waitUntilElementDisplayed(viewBagButtonFromAddToBagConf);
        click(conf_ButtonClose);
        click(pickUpStore);
        bopisOverlayActions.enterZipAndSearchStore(zipcode);
        WebElement selectStore1 = bopisOverlayActions.selectStoreByPosition(1);
        click(selectStore1);
        String err = bopisOverlayActions.getText(bopisOverlayActions.maxLimitError);
        if (err.contains(error)){
            return true;
        } else {
            addStepDescription("Max Limit error is not displayed");
            return false;
        }
    }

    public boolean isPickUpInStoreButtonAvailable() {
//        scrollDownToElement(pickUpStore);
        return waitUntilElementDisplayed(pickUpStore, 20);
    }

    public boolean isPickUpInStoreButtonDisabled() {
        return !(isEnabled(pickUpStore));
    }

    public String getBopisSubtitleSection() {
        return getText(subtitleBopisSection);
    }

    public boolean selectAllSizesWithOneColor(ProductDetailsPageActions productDetailsPageActions, HeaderMenuActions headerMenuActions, int bagLimit) {
        int bagCount = Integer.parseInt(headerMenuActions.getQtyInBag());
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
                    bagCount = Integer.parseInt(headerMenuActions.getQtyInBag());
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
                bagCount = Integer.parseInt(headerMenuActions.getQtyInBag());
            }
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    //Need to do some validation
    public boolean selectFitAndSize() {
        waitUntilElementsAreDisplayed(availableColors, 5);
        waitUntilElementsAreDisplayed(availableSizes, 5);
        if (availableFits.size() == 1) {
            javaScriptClick(driver, availableFits.get(0));
        } else if (availableFits.size() > 1) {
            javaScriptClick(driver, availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(1000);
        }
        if (availableSizes.size() == 1) {
            javaScriptClick(driver, availableSizes.get(0));
        } else if (availableSizes.size() > 1) {
            javaScriptClick(driver, availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }
        return waitUntilElementDisplayed(addToBag, 10);
    }

    public boolean writeAReview_Reg(String reviewText) {
        waitUntilElementDisplayed(writeAReviewLink(), 5);
        click(writeAReviewLink());
        waitUntilElementDisplayed(postReviewButton, 10);
//        click(overallRating_Stars);
        staticWait();
        waitUntilElementDisplayed(reviewTextArea, 5);
        clearAndFillText(reviewTextArea, reviewText);
        click(qualityRating_Stars);
        staticWait(500);
        click(stylingRating_Stars);
        staticWait(500);
        click(durabilityRating_Stars);
        staticWait(500);
        click(fitRating_Stars);
        staticWait(500);
        click(valueRating_Stars);
        staticWait(500);
        click(overallRating_Stars);
        staticWait(500);
        if (isDisplayed(nickNameTextArea))
            clearAndFillText(nickNameTextArea, "test" + getRandomNumber(9));

        click(postReviewButton);
        waitUntilElementDisplayed(submittedText, 15);
        waitUntilElementDisplayed(reviewClose, 3);
        click(reviewClose);
        scrollDownToElement(addToBag);
        return waitUntilElementDisplayed(addToBag, 10);
    }

    public boolean moveProdToWLFromPDP() {

        String wishListCountBefore = getText(wishListQty).replaceAll("[^0-9.]", "");
        int countBeforeAddToWL = Integer.parseInt(wishListCountBefore);

        click(addToWishList);
        staticWait(5000);

        String wishListCountAfter = getText(wishListQty).replaceAll("[^0-9.]", "");

        int countAfterAddToWl = Integer.parseInt(wishListCountAfter);
        staticWait(2000);
        //changed the method since counter will be increased after the feed

        //boolean isProductAddedToWL = (countBeforeAddToWL + 1) == countAfterAddToWl;

        return waitUntilElementDisplayed(pdp_FavIconEnabled,3);
    }

    public boolean checkProdRecommendationIsAvailable() {
        click(recommendationHeading);
        boolean prodAvailable = waitUntilElementsAreDisplayed(prodRecommendation, 10);
        return prodAvailable;
    }

    public boolean validateSubcate() {
        return waitUntilElementDisplayed(subCate);
    }

    public boolean checkProdRecommendationImageAndName() {

        boolean prodAvailable = waitUntilElementsAreDisplayed(prodRecommendationimg, 10) &&
                waitUntilElementsAreDisplayed(prodRecommendationName, 10);
        return prodAvailable;
    }

    public void getImage(List<WebElement> enabledGC) {
        if (enabledGC.size() >= 1) {
            click(enabledGC.get(randInt(0, (enabledGC.size() - 1))));
            staticWait(1000);
        }
    }

    public boolean addGCFromPDP(HeaderMenuActions headerMenuActions) {

        waitUntilElementsAreDisplayed(enabledGC, 15);
        click(enabledGC.get(0));
        String expPriceColor = "#990000";
        if (availableGCValues.size() >= 1) {
            click(availableGCValues.get(randInt(0, (availableGCValues.size() - 1))));
            staticWait(1000);
        }
        String selectedSize = getText(selectedGCSize).replaceAll("[^0-9.]", "");
        int size = Integer.parseInt(selectedSize);

        String selectedPrice = getText(selectedGCValue).replaceAll("[^0-9.]", "");//.substring(0,2);
        int price = Integer.parseInt(selectedPrice);
        String priceGC = getText(priceDisplayPDP).replaceAll("$", "");
        priceGC.equalsIgnoreCase(selectedSize);
        boolean isSelectedPrice = size == price;
        String priceColorCheckValue = discountPriceCheck();
        expPriceColor.equalsIgnoreCase(priceColorCheckValue);
        String bagCountBefore = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAdd = Integer.parseInt(bagCountBefore);
        staticWait(2000);

        click(addToBag);
        staticWait(5000);
        clickClose_ConfViewBag();
        String bagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAdd = Integer.parseInt(bagCountAfter);
        staticWait(2000);

        boolean isProductAdded = (bagCountBeforeAdd + 1) == bagCountAfterAdd;
        return isProductAdded && isSelectedPrice;
    }

    public String discountPriceCheck() {
        waitUntilElementDisplayed(priceDisplayPDP, 3);
        String color = priceDisplayPDP.getCssValue("color");
        return convertRgbToTextColor(color);
    }

    public boolean selectFitAndSizeInPDP() {

        waitUntilElementsAreDisplayed(availableColors, 5);
        waitUntilElementsAreDisplayed(availableSizes, 5);

        if (availableFits.size() == 1) {
            click(availableFits.get(0));
            staticWait(1000);
        } else if (availableSizes.size() == 1) {
            click(availableSizes.get(0));
            staticWait(1000);
        } else if (availableFits.size() >= 1) {
            click(availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(1000);
        } else if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }
        //click(addToBag);
        return waitUntilElementDisplayed(addToBag, 10);

    }

    public boolean updateQtyInPDP(String updatequantity) {
        waitUntilElementDisplayed(qtyDropDown, 5);
        selectDropDownByVisibleText(qtyDropDown, updatequantity);
        staticWait(10);
        return waitUntilElementDisplayed(addToBag, 10);
    }

    public boolean checkErrMsg(String sizeErr, String qtyErr) {

        waitUntilElementDisplayed(addToBag, 10);
        click(addToBag);
        waitUntilElementDisplayed(selectSize_Error, 10);
        boolean sizeError = getText(selectSize_Error).contains(sizeErr);

        if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }

        updateQty("15");
        click(addToBag);
//        staticWait(5000);
        clickClose_ConfViewBag();
        click(addToBag);
        waitUntilElementDisplayed(quantityLimitErrMessage, 10);

        boolean qtyError = getText(quantityLimitErrMessage).contains(qtyErr);

        if (sizeError && qtyError)
            return true;
        else
            return false;
    }

    public boolean validateSocialLinks() {

        scrollDownToElement(prodDesc);
        staticWait(2000);
        scrollUpToElement(twitterLink);
        if (isDisplayed(twitterLink) &&
                isDisplayed(facebookLink) &&
                isDisplayed(pinterestLink))
            return true;
        else
            return false;
    }

    public boolean isProductBadgeDisplayingByBadge(String badgeName) {
        return getText(productBadge).equalsIgnoreCase(badgeName);
    }


    public boolean homeBreadcrumb() {

        if (waitUntilElementDisplayed(homeBreadcrumb, 10)) {
            click(homeBreadcrumb);
            return verifyElementNotDisplayed(addToBag, 10);
        } else if (waitUntilElementDisplayed(backToResultsLink, 5)) {
            click(backToResultsLink);
            addStepDescription("Back to Results is getting displayed in PDP");
            return verifyElementNotDisplayed(addToBag, 10);
        } else {
            addStepDescription("Check the breadcrumb in PDP page");
            return false;
        }
    }

    public boolean pdpURLCheck(String urlValue) {
        waitUntilElementDisplayed(addToBag);
        String currentUrl = getCurrentURL();
        return currentUrl.contains(urlValue);
    }

    public boolean viewProdDetails_Lnk() {

        int count = sizeDropDown_Outfit.size();
        for (int i = 0; i < count; i++) {
            int sizeCount = sizeDisplay_Outfit.size();
            for (int j = 0; j < sizeCount; j++) {
                String sizeDisplayed = getText(sizeDisplay_Outfit.get(j));
                for (int k = j + 1; k < sizeCount; k++) {
                    String count1 = getText(sizeDisplay_Outfit.get(k));
                    if (count1.equalsIgnoreCase(sizeDisplayed)) {
                        addStepDescription("Duplicate sizes are present");
                        return false;
                    }
                }
            }
        }
        waitUntilElementsAreDisplayed(viewProdLink, 10);
        String outfitPDPPrice = getText(originalPrice_Outfit.get(0));
        if (viewProdLink.size() >= 0) {
            click(viewProdLink.get(0));
            staticWait(3000);
        }
        String size = getText(firstSize_Outfit);
        return pdpItemAttributesDisplay(outfitPDPPrice, size);

    }

    public boolean checkOutfitProdRecommendationIsAvailable() {
        waitUntilElementDisplayed(completeTheLook, 5);
        click(completeTheLook);
        mouseHover(shopTheLook);
        scrollDownToElement(youMayAlsoLike);
        click(youMayAlsoLike);
        staticWait(4000);
        boolean prodAvailable = waitUntilElementsAreDisplayed(outfitProdRecomDisplay, 10);
        staticWait();
        waitUntilElementClickable(outfitProdRecomDisplay.get(randInt(0, outfitProdRecomDisplay.size() - 1)), 10);

        return prodAvailable;
    }

    public boolean outfitRecommendedProdRedirection() {
        staticWait();
        scrollDownToElement(completeTheLook);
        staticWait();
        waitUntilElementsAreDisplayed(outfitProdRecomDisplay, 10);
        if (outfitProdRecomDisplay.size() >= 0) {
            waitUntilElementClickable(outfitProdRecomDisplay.get(randInt(0, outfitProdRecomDisplay.size() - 1)), 10);
            click(outfitProdRecomDisplay.get(randInt(0, outfitProdRecomDisplay.size() - 1)));
            staticWait(3000);
        }
        return waitUntilElementDisplayed(backLink, 20);
    }

    public boolean gcFavCheck(ShoppingBagDrawerActions shoppingBagDrawerActions) {
        staticWait();
        click(shoppingBagIcon);
        boolean isWLIconDisplay = false;
        try {
            shoppingBagDrawerActions.wishListIcons.get(0);
        } catch (IndexOutOfBoundsException i) {
            isWLIconDisplay = false;
        }
        if (!isDisplayed(shoppingBagDrawerActions.fitDropDown) && (!isWLIconDisplay)) {
            return true;
        } else
            return false;
    }


    public boolean isItemNumDisplayNextToPrice() {
        return isDisplayed(itemNumber);
    }

    public boolean clickRatingsAndScrollDownToReviewSection() {
        scrollToTheTopHeader(ratingsLink);
        waitUntilElementDisplayed(ratingsLink, 20);
        long bClick = scrollPosition();
        mouseHover(ratingsLink);
        click(ratingsLink);
        waitUntilElementDisplayed(reviewsHeader);
        long aClick = scrollPosition();
        return bClick < aClick;
    }

    public boolean isShowMoreAndProdDescDisplay() {
        boolean isShowMoreLink = isDisplayed(showMoreLink);
        click(showMoreLink);
        boolean isShowLessLink = waitUntilElementDisplayed(showLessLink, 10);
        String styleAttr = getAttributeValue(prodDescIntro, "style");
        boolean isProdDescHidden = styleAttr.contains("visible");
        return isShowMoreLink && isShowLessLink && isProdDescHidden;
    }

    public boolean isShowLessAndProdDescDisplay() {
        boolean isShowLessLink = isDisplayed(showLessLink);
        click(showLessLink);
        boolean isShowMoreLink = waitUntilElementDisplayed(showMoreLink, 10);
        String styleAttr = getAttributeValue(prodDescIntro, "style");
        boolean isProdDescVisible = styleAttr.contains("hidden");
        return isShowMoreLink && isShowLessLink && isProdDescVisible;
    }

    public boolean originalPriceDisplayWithLabel() {
        try {
            WebElement orgPr = originalPrice;
            return getText(orgPr).replaceAll("[^a-zA-Z]", "").equalsIgnoreCase("Was");
        }catch (Throwable e){
            return false;
        }
    }

    public boolean originalPriceAndSalePriceDisplay() {
        return isDisplayed(originalPrice) && isDisplayed(salePrice);
    }

    public boolean isPromotinalMsgMatchesWithPLP(String promotinalMsgAtPlp) {
        return getText(promotionalMsg).equalsIgnoreCase(promotinalMsgAtPlp);
    }

    public boolean clickOnAltImgByPosAndVerifyHeroImg() {
        boolean prevAndNextHeroImg = false;
        String nextHeroImg, prevHeroImg;
        click(altImageByPosition(1));
        waitUntilElementDisplayed(heroImg, 10);
        String heroImgUrl = getAttributeValue(heroImg, "src");
        prevHeroImg = getImgJPGFromSRCUrl(heroImgUrl);

        for (int i = 2; i <= altImages.size(); i++) {
            click(altImageByPosition(i));
            waitUntilElementDisplayed(heroImg, 10);

            heroImgUrl = getAttributeValue(heroImg, "src");
            nextHeroImg = getImgJPGFromSRCUrl(heroImgUrl);
            if (!nextHeroImg.equalsIgnoreCase(prevHeroImg)) {
                prevAndNextHeroImg = true;
            } else {
                return false;
            }
            prevHeroImg = nextHeroImg;
        }
        if (!prevHeroImg.isEmpty()) {
            prevAndNextHeroImg = true;
        }
        click(altImageByPosition(1));
        waitUntilElementDisplayed(heroImg, 10);
        return prevAndNextHeroImg;

    }

    public String getImgJPGFromSRCUrl(String imgSrcUrl) {
        int startIndex = imgSrcUrl.lastIndexOf("/");
        String jpg = imgSrcUrl.substring(startIndex);
        return jpg;
    }

    public boolean clickMainImgCarouselAndVerify() {
        boolean prevAndNextHeroImg = false;
        String nextHeroJPG, prevHeroJPG;

        String heroImgUrl = getAttributeValue(heroImg, "src");
        prevHeroJPG = getImgJPGFromSRCUrl(heroImgUrl);
        for (int i = 2; i <= altImages.size(); i++) {
            click(nextArrow_LargeImg);
            staticWait();
            waitUntilElementDisplayed(heroImg, 10);
            heroImgUrl = getAttributeValue(heroImg, "src");
            nextHeroJPG = getImgJPGFromSRCUrl(heroImgUrl);
            if (!nextHeroJPG.equalsIgnoreCase(prevHeroJPG)) {
                prevAndNextHeroImg = true;
            } else {
                return false;
            }
            prevHeroJPG = nextHeroJPG;
        }
        if (!prevHeroJPG.isEmpty()) {
            prevAndNextHeroImg = true;
        }
        return prevAndNextHeroImg;
    }

    public boolean clickFullSizeLink() {
        click(fullSizeLink);
        boolean fullSizeView = waitUntilElementDisplayed(imageLargerView, 20);
        return fullSizeView;
    }

    public boolean verifyFullSizeAttributes() {
        return waitUntilElementsAreDisplayed(fullSize_AltImgs, 1) && isDisplayed(fullSize_colorContainer) && isDisplayed(fullSize_ProdName) && isDisplayed(imageLargerView);
    }

    public boolean fullSize_clickOnAltImgByPosAndVerifyHeroImg() {
        boolean prevAndNextHeroImg = false;
        String nextHeroImg, prevHeroImg;
        click(fullSize_altImageByPos(1));
        waitUntilElementDisplayed(imageLargerView, 10);
        String largerImgUrl = getAttributeValue(imageLargerView, "src");
        prevHeroImg = getImgJPGFromSRCUrl(largerImgUrl);

        if (fullSize_AltImgs.size() > 1) {
            for (int i = 2; i <= fullSize_AltImgs.size(); i++) {
                click(fullSize_altImageByPos(i));
                waitUntilElementDisplayed(imageLargerView, 10);

                largerImgUrl = getAttributeValue(imageLargerView, "src");
                nextHeroImg = getImgJPGFromSRCUrl(largerImgUrl);
                if (!nextHeroImg.equalsIgnoreCase(prevHeroImg)) {
                    prevAndNextHeroImg = true;
                } else {
                    return false;
                }
                prevHeroImg = nextHeroImg;
            }
        }
        if (!prevHeroImg.isEmpty()) {
            prevAndNextHeroImg = true;
        }
        return prevAndNextHeroImg;

    }

    public String fullSize_clickOnColorSwatchByPosAndGetJPG() {
        WebElement colorSwatch = null;
        try {
            colorSwatch = fullSize_UnCheckedColorSwatches.get(0);
        } catch (Exception e) {
            colorSwatch = fullSize_CheckedColorSwatch;
        }
        click(colorSwatch);
        staticWait(3000);
        waitUntilElementDisplayed(imageLargerView, 10);
        String largerImgUrl = getAttributeValue(imageLargerView, "src");
        String jpg = getImgJPGFromSRCUrl(largerImgUrl);

        return jpg;

    }

    public boolean clickColorSwatchByPosAndVerifyImg() {
        boolean prevAndNextHeroImg = false, selectedColor = false;
        String nextHeroImg, prevHeroImg;
        if (waitUntilElementsAreDisplayed(availableColorsNotSelected, 5)) {
            String heroImgUrl = getAttributeValue(heroImg, "src");
            prevHeroImg = getImgJPGFromSRCUrl(heroImgUrl);
            for (int i = 1; i <= availableColorsNotSelected.size(); i++) {
                click(colorElementByPos(i));
                staticWait();
                waitUntilElementDisplayed(heroImg, 10);
                heroImgUrl = getAttributeValue(heroImg, "src");
                nextHeroImg = getImgJPGFromSRCUrl(heroImgUrl);
                selectedColor = isDisplayed(colorName);
                if (!nextHeroImg.equalsIgnoreCase(prevHeroImg)) {
                    prevAndNextHeroImg = true;
                } else {
                    return false;
                }
                prevHeroImg = nextHeroImg;
            }
            if (!prevHeroImg.isEmpty()) {
                prevAndNextHeroImg = true;
            }
            return prevAndNextHeroImg && selectedColor;
        }
        addStepDescription("Color swatches are not available");
        return false;

    }

    public String getHeroImgRrl() {
        String heroImgUrl = getAttributeValue(heroImg, "src");
        String mainImgPDP = getImgJPGFromSRCUrl(heroImgUrl);
        return mainImgPDP;
    }

    public void clickCloseModal() {
        click(closeModal);
    }

    public double getProdOriginalPr() {
        double orgPr = 0.00;
        try {
            orgPr = Double.valueOf(getText(originalPrice).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException o) {
            orgPr = 0.00;
        }
        return orgPr;
    }

    public double getProdSalePr() {
        double salePr = 0.00;
        try {
            salePr = Double.valueOf(getText(salePrice).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException o) {
            salePr = 0.00;
        }
        return salePr;
    }

    public boolean clickSizeChartAndVerify() {
        if (isDisplayed(sizeChartLink)) {
            click(sizeChartLink);
            staticWait(3000);
            return waitUntilElementDisplayed(sizeChartModal, 20);
        }
        return false;
    }

    public boolean clickSizeChartAndVerify_Outfits() {
        int sizeChartLinkSize = sizeChartLinks.size();
        for (int i = 1; i < sizeChartLinkSize; i++) {
            if (isDisplayed(sizeChartLinkByPos(i))) {
                click(sizeChartLinkByPos(i));
                if (waitUntilElementDisplayed(sizeChartModal, 20)) {
                    return sizeChartMetrics();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean sizeChartMetrics() {
        waitUntilElementsAreDisplayed(sizeChartMenu, 3);
        staticWait(2000);
        boolean isChartDisplayCorrect = false;
        int chartMenu = sizeChartMenu.size();
        for (int i = 1; i <= chartMenu; i++) {
            click(sizeChartMenuByPos(i));
            waitUntilElementDisplayed(deptActiveByPos(i));
            waitUntilElementsAreDisplayed(sizeChartDisplay, 10);
            staticWait(2000);
            int chartDisplayByMenu = sizeChartDisplay.size();
            for (int j = 1; j <= chartDisplayByMenu; j++) {
                staticWait();
                String chartDisplayByPos = getText(sizeChartDisplayByPos(j)).toLowerCase();
                if (getText(deptActiveByPos(i)).equalsIgnoreCase("Accessories")) {
                    if (chartDisplayByPos.contains("/")) {
                        int endIndex = chartDisplayByPos.indexOf("/");
                        chartDisplayByPos = chartDisplayByPos.substring(0, endIndex);
                    }
                }
                click(sizeChartDisplayByPos(j));
                if (getText(deptActiveByPos(i)).equalsIgnoreCase("Shoes")) {
                    isChartDisplayCorrect = waitUntilElementDisplayed(shoesDeptHeaderByPos(j), 10);

                } else {
                    isChartDisplayCorrect = waitUntilElementDisplayed(deptHeaderByDeptNameAndPos(chartDisplayByPos, 1), 10);
                }
                if (!isChartDisplayCorrect) {
                    return isChartDisplayCorrect;
                }
            }
        }
        return isChartDisplayCorrect;


    }

    public boolean validateSizeChartAttributes() {
        staticWait(3000);
        waitUntilElementDisplayed(sizeChart_MetricLink, 10);
        return isDisplayed(sizeInfoHeader) && isDisplayed(sizeChartMeasureLinks) && isDisplayed(sizeChartMeasureImg)
                && isDisplayed(closeModal);
    }

    public boolean isQtyDropDownAllValuesAvailable() {
        boolean isTrue = false;
        boolean qtyByDefault = getText(qtyDrpDownSelected).equalsIgnoreCase("1");
        String[] expOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        click(qtyDropDown);
        staticWait(2000);
        List<WebElement> qtyOptions = getDropDownOption(qtyDropDown);
        int i = 0;
        for (WebElement qtyOption : qtyOptions) {
            while (i < expOptions.length) {
                if (getText(qtyOption).equals(expOptions[i])) {
                    i++;
                    isTrue = true;
                    break;

                } else {
                    return false;
                }

            }
        }
        return isTrue && qtyByDefault;
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

    public boolean isRegularDisplayByDefault() {
        for (int i = 0; i < list_SelectAFit.size(); i++) {
            String fitName = getText(list_SelectAFit.get(i));
            if (fitName.equalsIgnoreCase("Regular")) {
                String isDisabled = getAttributeValue(fitSelectionByPos(i + 1), "class");
                if (!isDisabled.equalsIgnoreCase("disabled")) {
                    return getAttributeValue(list_SelectAFit.get(i), "class").contains("item-selected-option");

                } else {
                    addStepDescription("Regular fit is disabled");
                }

            } else {
                addStepDescription("Regular fit is disabled or not available");
                return getAttributeValue(list_SelectAFit.get(i), "class").contains("item-selected-option");
            }
        }
        return false;
    }

    public boolean clickChangeStoreAndVerify(StoreLocatorPageActions storeLocatorPageActions) {
        if (waitUntilElementDisplayed(changeStoreLink, 10) && verifyElementNotDisplayed(viewBagButtonFromAddToBagConf, 10)) {
            click(changeStoreLink);
            return waitUntilElementDisplayed(storeLocatorPageActions.searchStoreField, 30);
        } else {
            addStepDescription("Change Store link is not displaying at PDP");
            return false;
        }
    }

    public boolean clickBackToResultsLink(SearchResultsPageActions searchResultsPageActions, String searchTerm) {
        click(backToResultsLink);
        waitUntilElementDisplayed(searchResultsPageActions.searchResultsTerm, 30);
        return searchResultsPageActions.searchResultsBySearchTerm(searchTerm);
    }

    public boolean clickColorSwatchAndVerifySizes() {
        boolean isSizesAvail = false;
        int colorCount = getElementsSize(availableColorSwatches);
        for (int i = 1; i <= colorCount; i++) {
            click(availColorSwatchByPos(i));
            int availSizes = 0, unAvailSizes = 0;
            try {
                availSizes = availableSizes.size();
            } catch (Exception e) {
                unAvailSizes = getElementsSize(unAvailableSizes);
            }
            if (availSizes == 0 || availSizes > 0 || unAvailSizes > 0) {
                isSizesAvail = true;
            } else {
                return false;
            }
            colorCount = getElementsSize(availableColorSwatches);
        }
        return isSizesAvail;
    }

    public boolean sizeChartToggleUS_Metric_Back_US() {
        //click(sizeChart_MetricLink);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var a=document.getElementsByClassName('off');a[0].click()");
        boolean metricsLinkOn = waitUntilElementDisplayed(sizeChart_MetricOn, 10);
        click(sizeChart_USLink);
        boolean usLinkOn = waitUntilElementDisplayed(sizeChart_USOn, 10);
        if (!metricsLinkOn && !usLinkOn) {
            addStepDescription("Clicking on US link is returned " + usLinkOn + " Clicking on Metrics link returned " + metricsLinkOn);
        }
        return metricsLinkOn && usLinkOn;
    }

    public boolean clickAddToBagByPos() {
        for (WebElement addToBag : addToBag_Outfits) {
            click(addToBag);
            boolean noProdImg = waitUntilElementDisplayed(prodImgOnNotiOverlay, 10);
            clickClose_ConfViewBag();
            return noProdImg;
        }
        return false;
    }

    public boolean validateOutfittingPDP() {
        if (isDisplayed(addToBag_Outfits.get(0)) && isDisplayed(viewProdLink.get(0)) && isDisplayed(favIcon_Outfits.get(0))
                && isDisplayed(sizeChartLinks.get(0)) && !isDisplayed(prodDescription_PDP) && isDisplayed(availableColors.get(0))) {
            return true;
        } else {
            addStepDescription("check the attributes displayed in Outfitting PDP");
            return false;
        }
    }

    public boolean clickColorSwatchAndSelectOutfitSize(String qty) {
        boolean isProdAvail = false;

        int availSize = 0;
        availSize = (selectSize_Outfit).size();
        if (availSize > 0) {
            click(availColorSwatchByPos(1));
            waitUntilElementsAreDisplayed(sizeDropDown_Outfit, 3);
            String firstSize = getText(firstSize_Outfit);
            selectDropDownByVisibleText(sizeDropDown_Outfit.get(0), firstSize);
            selectDropDownByValue(qtyDropDown, qty);
            isProdAvail = true;
        } else {
            addStepDescription("Youth 13 size is not available for the Outfits, Please update the size");
            return false;
        }

        return isProdAvail;
    }

    public boolean pdpItemAttributesDisplay(String price, String outfitSize) {
        boolean duplicate = false;
        if (isDisplayed(productName) && isDisplayed(originalPrice) && isDisplayed(addToBag) && isDisplayed(favIcon_Outfits.get(0))
                && isDisplayed(colorSwatches)) {
            String pdpPrice = getText(salePrice);
            String errCopy = "Sorry, you have reached the quantity limit for this item. Please select another color or size.";
            price.equalsIgnoreCase(pdpPrice);
            int count = sizeFacet_PDP.size();
            for (int i = 0; i < count; i++) {
                String size = getText(sizeFacet_PDP.get(i));
                if (size.equalsIgnoreCase(outfitSize)) {
                    click(sizeFacet_PDP.get(i));
                    click(addToBag);
                    waitUntilElementDisplayed(quantityLimitErrMessage);
                    String displayedErr = getText(quantityLimitErrMessage).replaceAll("Error: ", "");
                    if (displayedErr.equalsIgnoreCase(errCopy)) {
                        return duplicate = true;
                    }
                }
            }
            if (duplicate != false)
                for (int i = 0; i < count; i++) {
                    String size = getText(sizeFacet_PDP.get(i));
                    for (int j = i + 1; j < count; j++) {
                        String pdpSizeFacet = getText(sizeFacet_PDP.get(j));
                        if (pdpSizeFacet.contains(size)) {
                            addStepDescription("PDP have duplicate size Facet");
                            return false;
                        }
                    }
                }
        } else {
            addStepDescription("Check the redirected PDP page details");
            return false;
        }
        return true;
    }

    public boolean addToBagMaxError() {
        clickColorSwatchAndSelectOutfitSize("1");
        for (WebElement addToBag : addToBag_Outfits) {
            click(addToBag);
            return waitUntilElementDisplayed(quantityLimitErrMessage, 10);
        }
        return false;
    }

    public boolean verifyBopisPromo() {
        return waitUntilElementDisplayed(bopisPromoContent, 5);
    }

    /**
     * Created By Pooja on 9th May,2018
     * This Method verifies that out of all available sizes of an item only one is in stock and is not selected
     */
    public boolean verifySingleSizeInStockNotSelected() {
        waitUntilElementsAreDisplayed(product_sizes, 10);
        if (product_sizes.size() > 1 && availableSizes.size() == 1) {
            return waitUntilElementDisplayed(productSizeAvailableAndNotSelected_btn);
        }
        return false;
    }

    public boolean checkFavIconEnabled() {
        if (isDisplayed(pdp_FavIconEnabled)) {
            return true;
        } else {
            addStepDescription("Fav icon is not enabled");
            return false;
        }
    }

    public boolean validateSBConfEcom() {
        isOutfitItemOutOfStock();
        //Select any size from PDP Page
        selectASize();
        click(addToBag);
        waitUntilElementDisplayed(addToBagNotification, 2);
        if (isDisplayed(conf_ViewBag) && isDisplayed(conf_ContinueCheckout) && isDisplayed(conf_Checkout)) {
            switchToContinuePayPalFrameIfAvailable();
            waitUntilElementDisplayed(paypalSbModal);
            switchToDefaultFrame();
            if(isDisplayed(conf_ButtonClose)){
            clickClose_ConfViewBag();}
            return true;
        } else {
            addStepDescription("Buttons are not displayed in the SB confirmation modal for Ecom");
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

    public boolean validateSBConfBopis(BopisOverlayActions bopisOverlayActions, String zip) {
        isOutfitItemOutOfStock();
        //Select any size from PDP Page
        clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zip);
        bopisOverlayActions.click(bopisOverlayActions.availableStrCheckBox);
        bopisOverlayActions.selectAvailableStoreButton();
        waitUntilElementDisplayed(addToBagNotification, 2);
        if (isDisplayed(conf_ViewBag) && isDisplayed(conf_ContinueCheckout) && isDisplayed(conf_Checkout)) {
            clickClose_ConfViewBag();
            return true;
        } else {
            addStepDescription("Buttons are not displayed in the SB confirmation modal");
            return false;
        }
    }

    public boolean changeColorAndCheckFavIconEn() {
        waitUntilElementsAreDisplayed(colorSwatchesList, 2);
        int count = colorSwatchesList.size();
        if (count > 1) {
            for (int i = 0; i < count; ) {
                click(availableColors.get(i));
                if (isDisplayed(pdp_FavIconEnabled)) {
                    addStepDescription("Fav icon is enabled");
                    return false;
                } else {
                    i++;
                }
            }
        } else {
            logger.info("Multiple colors are not available");
            return false;
        }
        return true;
    }

    public boolean changeColorAftrAddFavIconEn(String addedColor) {
        waitUntilElementsAreDisplayed(colorSwatchesList, 2);
        String selectedColor = getText(selectedAttribute);
        int count = colorSwatchesList.size();
        if (count > 1) {
            for (int i = 0; i < count; ) {
                if (selectedColor.equalsIgnoreCase(addedColor)) {
                    checkFavIconEnabled();
                    return true;
                } else {
                    click(availableColors.get(i));
                    staticWait(2000);
                    String selectedColor1 = getText(selectedAttribute);
                    if (selectedColor1.equalsIgnoreCase(addedColor)) {
                        checkFavIconEnabled();
                        return true;
                    }
                    if (isDisplayed(pdp_FavIconEnabled)) {
                        addStepDescription("Fav icon is enabled for other items");
                        return false;
                    } else {
                        i++;
                    }
                }
            }
        } else {

            logger.info("Multiple colors are not available");
            return false;
        }
        return false;
    }

    public boolean checkFavAfterEditFromWL(String addedColor) {
        waitUntilElementsAreDisplayed(colorSwatchesList, 2);
        //   String selectedColor = getText(selectedAttribute);
        int count = colorSwatchesList.size();
        if (count > 1) {
            for (int i = 0; i < count; ) {
                click(availableColors.get(i));
                String selectedColor1 = getText(selectedAttribute);
                if (selectedColor1.equalsIgnoreCase(addedColor)) {
                    if (!isDisplayed(pdp_FavIconEnabled)) {
                        return true;
                    }
                } else {
                    i++;
                }
            }
        } else {
            logger.info("Single color is available");
            return false;
        }
        addStepDescription("fav icon is enabled for the previously selected color");
        return false;
    }

    /**
     * Created By Pooja on 11th May,2018
     * This Method clicks on add to Bag icon of the Recommendations Product and
     * verifies that One Size Button is selected. It searches for all the recomendation products even by clicking right arrow untill product with
     * One Size Button is not displayed
     */
    public boolean verifyOneSizeSelectedOnRecomPdp() {
        boolean prodAvailable = waitUntilElementsAreDisplayed(prodRecommendationPdp, 10);
        if (prodAvailable) {
            scrollDownToElement(prodRecommendationPdp.get(0));
            for (int i = 0; i < prodRecommendationPdp.size(); i++) {
                staticWait(1000);
                mouseHover(prodRecommendationPdp.get(i));
                staticWait(1000);
                click(addToBagIcon.get(i));
                staticWait(1000);
                if (waitUntilElementDisplayed(alreadySelectedOneSizeBtnInRecom, 10)) {
                    return true;
                }
                if (verifyElementNotDisplayed(alreadySelectedOneSizeBtnInRecom, 10) && i == prodRecommendationPdp.size() - 1) {
                    while (waitUntilElementClickable(navButton, 3)) {
                        click(navButton);
                        staticWait(1000);
                        for (int j = 0; j < prodRecommendationPdp.size(); j++) {
                            mouseHover(prodRecommendationPdp.get(j));
                            staticWait(1000);
                            click(addToBagIcon.get(j));
                            staticWait(1000);
                            if (waitUntilElementDisplayed(alreadySelectedOneSizeBtnInRecom, 10)) {
                                return true;
                            }
                        }
                    }
                }
            }
            addStepDescription("One Size Button is not displayed in Recommended Products available");
            return false;
        } else {
            addStepDescription("Recommended Products are not available");
            return false;
        }
    }

    /**
     * Created By Pooja on 12th May,2018
     * This Method verifies the breadcrumb
     */
    public boolean verifyBreadcrumbsAsPerNavigation(List<String> expectedBreadcrumbs) {
        if (waitUntilElementsAreDisplayed(breadcrumb_values, 10)) {
            List<String> Actualbrdcrmbs = new ArrayList<String>();
            for (WebElement e : breadcrumb_values) {
                Actualbrdcrmbs.add(getText(e));
            }
            for (String ex : expectedBreadcrumbs) {
                if (!Actualbrdcrmbs.contains(ex)) {
                    addStepDescription("Correct Breadcrumbs are not available");
                    return false;
                }
            }
            addStepDescription("Correct Breadcrumbs are displayed");
            return true;

        } else {
            addStepDescription("Breadcrumbs are not available");
            return false;
        }

    }

    /**
     * Created By Pooja on 14th May,2018
     * This method verifies the One Size Button is selected on PDP on Clicking Recommendation Product
     */
    public boolean verifyOneSizeBtnOnRecomPdp() {
        if (waitUntilElementsAreDisplayed(prodRecommendationPdp, 10)) {
            for (int i = 0; i < prodRecommendationPdp.size(); i++) {
                click(prodRecommendationPdp.get(i));
                if (waitUntilElementDisplayed(alreadySelectedOneSizeBtn, 10)) {
                    addStepDescription("Product with One Size Button is present on the Page");
                    return true;

                } else {
                    driver.navigate().back();
                    addStepDescription("moving back to PLP for checking next Outfit");
                }
            }
        }
        addStepDescription("Product with One Size Button is not present on the Page");
        return false;
    }

    public boolean proceedToPaypalFromConfProd(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, PayPalPageActions payPalPageActions, BillingPageActions billingPageActions,
                                               ShoppingBagDrawerActions shoppingBagDrawerActions, String parentWindow) {
        waitUntilElementDisplayed(shoppingBagDrawerActions.paypalSbDrawer);
        click(shoppingBagDrawerActions.paypalSbDrawer);
//        payPalPageActions.switchToContinuePayPalFrameIfAvailable();
//        waitUntilElementDisplayed(payPalPageActions.proceedPaypalBtn, 5);
//        payPalPageActions.click(payPalPageActions.proceedPaypalBtn);
        payPalPageActions.payPalNewWindow(billingPageActions);
        return payPalPageActions.paypalLoginProd(paypalOrderDetailsPageActions, billingPageActions, parentWindow);
    }

    public boolean proceedToPaypalFromConf(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, PayPalPageActions payPalPageActions,
                                           BillingPageActions billingPageActions, ReviewPageActions reviewPageActions, String email, String password, String parentWindow) {
        waitUntilElementDisplayed(paypalSbModal);
        click(paypalSbModal);
//        payPalPageActions.switchToContinuePayPalFrameIfAvailable();
//        waitUntilElementDisplayed(payPalPageActions.proceedPaypalBtn, 5);
//        payPalPageActions.click(payPalPageActions.proceedPaypalBtn);
        payPalPageActions.payPalNewWindow(billingPageActions);
        return payPalPageActions.paypalLogin(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, email, password, parentWindow);
    }

    /**
     * Created By Pooja
     * This Method clicks on recommended products as per index
     */
    public boolean proceedToPaypalInvalidLoginFromConf(PaypalOrderDetailsPageActions paypalOrderDetailsPageActions, PayPalPageActions payPalPageActions, BillingPageActions billingPageActions,
                                                       ReviewPageActions reviewPageActions, ShoppingBagPageActions shoppingBagPageActions, String email, String password, String parentWindow) {
        waitUntilElementDisplayed(paypalSbModal);
        click(paypalSbModal);
//        payPalPageActions.switchToContinuePayPalFrameIfAvailable();
//        waitUntilElementDisplayed(payPalPageActions.proceedPaypalBtn, 5);
//        payPalPageActions.click(payPalPageActions.proceedPaypalBtn);
        payPalPageActions.payPalNewWindow(billingPageActions);
        return payPalPageActions.paypalLogin_WrongID(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, shoppingBagPageActions, email, password, parentWindow);
    }


    /*Created By Pooja
     * This Method clicks on recommended products as per index*/
    public boolean clickRecommendedProductOnPdp(int i) {
        if (waitUntilElementsAreDisplayed(prodRecommendationimg, 5)) {
            click(prodRecommendationimg.get(i));
            return true;
        }
        return false;
    }

    /**
     * Created By Pooja
     * This Method clicks on Outfits products as per index
     */
    public boolean moveToOutfitsPlp(CategoryDetailsPageAction categoryDetailsPageAction, int i) {
        if (waitUntilElementsAreDisplayed(categoryDetailsPageAction.outfitProducts, 5)) {
            mouseHover(categoryDetailsPageAction.outfitProducts.get(i));
            click(shopTheLookLink.get(i));
            return waitUntilElementDisplayed(outfitsMainImg, 10);
        } else {
            return false;
        }
    }

    /**
     * Created By Pooja
     * This Method clicks on Outfits products as per index
     */
    public boolean moveToOutfitsPdp(int i) {
        click(viewProdLink.get(i));
        return waitUntilElementDisplayed(addToBag, 10);

    }

    /**
     * Created By Pooja
     * This Method clicks on the close icon of  recommended product Bag Flip
     */
    public void closeRecomProdCard() {
        click(close_link_Recom_Prod);
        mouseHover(recommendationHeading);//added this to remove the focus on the Add to Bag icon on recommended product

    }

    /**
     * created by Pooja on 8th May,2018
     * This method clicks on Outfits tile present on Outfits PLP and verifies One Size Button is displayed and is Selected
     */
    public boolean clickOutfitBySearchingOnPlp(CategoryDetailsPageAction categoryDetailsPageAction) {
        if (waitUntilElementsAreDisplayed(categoryDetailsPageAction.outfitProducts, 5)) {
            for (int i = 0; i < categoryDetailsPageAction.outfitProducts.size(); i++) {
                mouseHover(categoryDetailsPageAction.outfitProducts.get(i));
                click(shopTheLookLink.get(i));
                if (waitUntilElementDisplayed(alreadySelectedOneSizeBtn, 10)) {
                    addStepDescription("Product with One Size Button is present on the Page");
                    return true;

                } else {
                    driver.navigate().back();
                    addStepDescription("moving back to PLP for checking next Outfit");
                }
            }
            addStepDescription("Product with One Size selected are not present");
            return false;
        }

        addStepDescription("Product on Outfits PLP are not present");
        return false;

    }

    public boolean checkActiveCategory() {
        if (isDisplayed(leftNavActiveCatOutfits)) {
            return true;
        } else {
            addStepDescription("Outfit is not active in Landing page");
            return false;
        }
    }

    public boolean checkFooterOutfit() {
        if (isDisplayed(signupEmailLink_Outfits) && isDisplayed(smsLink_Outfits) && isDisplayed(findAStoreLink_Outfits)) {
            return true;
        } else {
            addStepDescription("Check the Footer links displayed in Outfit Landing Page");
            return false;
        }
    }

    public boolean multiColorCheckUnavailable() {
        addStepDescription("Please update the test data with multi color");
        return false;
    }

    public boolean checkFavStoreDisplay(String store) {

        waitUntilElementDisplayed(changedFavStoreName, 2);
        String currentStore = getText(changedFavStoreName).toLowerCase();
        if (currentStore.equalsIgnoreCase(store)) {
            return true;
        } else {
            addStepDescription("Favorite store displayed in PDP is different");
            return false;
        }

    }
    public boolean clickOnRating(){
        if(isDisplayed(ratingDisplayPDP.get(0)) && isDisplayed(sortByOptionPDP)){
            return true;
        }
        else{
            addStepDescription("Rating is not displayed in PDP");
            return false;
        }
    }

    public boolean defaultFitOptionDisplay(){
        waitUntilElementDisplayed(fitOptionSelected,2);
        String defaultFit = getText(fitOptionSelected);
        if(defaultFit.equalsIgnoreCase("regular")){
            return true;
        }
        else{
            addStepDescription("Regular Fit is not selected by default");
            return false;
        }
    }
    public boolean validateHrefLangTag() {
        boolean href = false;
        for (WebElement tag : hrefLangTags) {
            href = getAttributeValue(tag, "href").contains("www") && hrefLangTags.size() == 4;
        }
        return href;
    }

}