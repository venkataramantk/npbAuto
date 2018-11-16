package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileProductCardViewRepo;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by skonda on 8/28/2017.
 */
public class MobileProductCardViewActions extends MobileProductCardViewRepo {
    WebDriver mobileDriver = null;
    Logger logger = Logger.getLogger(MobileProductCardViewActions.class);

    public MobileProductCardViewActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean selectAFitAndSizeAndAddToBag() {
        for (int i = 0; i < colorImages.size(); i++) {
            click(colorImages.get(i));
            staticWait(2000);
            waitUntilElementDisplayed(selectAFitOrSizeList.get(0));
            if (selectAvailableSizes.size() > 0) {
                for (int j = 0; j < selectAvailableSizes.size(); j++) {
                    click(selectAvailableSizes.get(j));
                    staticWait();
                    click(addToBagBtn);
                    return waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);
                }
            } else {
                return false;
            }
        }
        return waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);


    }

    public boolean selectAFitSizeUpdateQtyAndAddToBag(String qty) {
        for (int i = 0; i < colorImages.size(); i++) {
            click(colorImages.get(i));
            staticWait(2000);
            waitUntilElementDisplayed(selectAFitOrSizeList.get(0));
            if (selectAvailableSizes.size() > 0) {
                for (int j = 0; j < selectAvailableSizes.size(); j++) {
                    click(selectAvailableSizes.get(j));
                    staticWait();
                    updateQuantity(qty);
                    click(addToBagBtn);
                    return waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);
                }
            }

        }
        return false;
    }

    public boolean isItemOutOfStock() {
        try {
            if (availableSizes.size() > 0) {
                return false;
            } else {
                return true;
            }
        } catch (NoSuchElementException t) {
            return true;
        }
    }

    public void updateQuantity(String qty) {
        selectDropDownByVisibleText(qtyDropDown, qty);
        staticWait();
    }

    public String getProductNameByPos(int i) {
        return getText(productNamesByPosition(i));
    }


    /**
     * Created By Pooja on 22nd May,2018
     * This Method verifies Quantity Drop down contains values 1 to 15
     */
    public boolean isQuantityDDAllValuesAvailable() {
        click(qtyDropDown);
        for (int i = 1; i < 16; i++) {
            if (Integer.parseInt(getText(qtyDropDownOptions.get(i - 1))) != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * Created By Pooja
     * This Method Validates icons for products in PLP
     */
    public boolean verifyProdCardItems(MCategoryDetailsPageAction mCategoryDetailsPageAction) {
        boolean prodCard = waitUntilElementDisplayed(mCategoryDetailsPageAction.firstImg, 5) &&
                waitUntilElementsAreDisplayed(mCategoryDetailsPageAction.addToBagIcon, 2) &&
                waitUntilElementDisplayed(mCategoryDetailsPageAction.productTitle, 2) &&
                waitUntilElementDisplayed(mCategoryDetailsPageAction.offerPrice, 2) &&
                waitUntilElementsAreDisplayed(mCategoryDetailsPageAction.addToFavIcon, 2) &&
                waitUntilElementDisplayed(mCategoryDetailsPageAction.listPrDisplayBelowOfferPr);

        return prodCard;
    }

    /**
     * Created By Pooja
     * This Method verifies the Currency of the Offer Price
     */
    public boolean verifyOfferPriceCurrency(MCategoryDetailsPageAction mCategoryDetailsPageAction, String currency) {
        return getText(mCategoryDetailsPageAction.offerPrice).contains(currency);
    }

    /**
     * Created By Pooja
     * This method verifies the size  selected on Bag Flip is same as applied in Size filter
     * and removes the filter
     */
    public boolean verifyFilteredSizeSelectedOnProdCard(List<String> filter) {
        if (waitUntilElementsAreDisplayed(selectAvailableSizes, 10)) {
            for (int i = 0; i < selectedSizeOnBagFlip.size(); i++) {
            }
        } else {
            addStepDescription("Item is Out of Stock");
            return false;
        }
        return true;
    }


    public void clickCloseIcon() {
        click(closeIcon);
    }


    public void selectRandomColor() {
        int eleColor = colorImages.size();
        if (eleColor > 1) {
            click(colorImages.get(randInt(0, (colorImages.size() - 1))));
        } else if (eleColor == 1) {
            click(colorImages.get(0));
        }
    }

    public void selectAFit() {
        if (waitUntilElementsAreDisplayed(firstFitAndSize, 2)) {

        }
        int eleColor = firstFitAndSize.size();
        if (eleColor > 1) {
            click(colorImages.get(randInt(0, (colorImages.size() - 1))));
        } else if (eleColor == 1) {
            click(colorImages.get(0));
        }
    }


    public void selectASize() {
        List<WebElement> eleSize;
        if (fitAndSize.size() == 2)
            eleSize = secondFitAndSize;
        else
            eleSize = firstFitAndSize;
        int eleColor = eleSize.size();
        if (eleColor > 1) {
            click(colorImages.get(randInt(0, (colorImages.size() - 1))));
        } else if (eleColor == 1) {
            click(colorImages.get(0));
        }
    }

    /**
     * Click on save product details button
     *
     * @return
     */
    public boolean editDetailsAndSave() {
        selectRandomColor();
        selectAFit();
        if (waitUntilElementsAreDisplayed(selectedFits, 2))
            selectASize();
        click(addToBagBtn);
        return !waitUntilElementDisplayed(addToBagBtn, 10);
    }
}
