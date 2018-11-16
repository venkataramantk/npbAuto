package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.ProductCardViewRepo;

import java.util.NoSuchElementException;

/**
 * Created by skonda on 8/28/2017.
 */
public class ProductCardViewActions extends ProductCardViewRepo {
    WebDriver driver = null;
    Logger logger = Logger.getLogger(ProductCardViewActions.class);

    public ProductCardViewActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }
    public void clickClose_ConfViewBag(){
        click(conf_ButtonClose);
        waitUntilElementClickable(shoppingBagIcon, 15);
    }

    public boolean selectAFitAndSizeAndAddToBag() {
        String itemName = getText(cqItemName.get(0));
        for (int i = 1; i <= colorImages.size(); i++) {
            click(colorSwatchByPos(i));
            staticWait(2000);
            waitUntilElementDisplayed(selectAFitOrSizeList.get(0));
            if (selectAvailableSizes.size() > 0) {
                for (int j = 0; j < selectAvailableSizes.size(); j++) {
                    click(selectAvailableSizes.get(j));
                    staticWait();
                    click(addToBagBtn);
                    waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 30);
                    clickClose_ConfViewBag();
                    click(shoppingBagIcon);
                    waitUntilElementDisplayed(overlay_ItemNAme.get(0));
                    String name1 = getText(overlay_ItemNAme.get(0));
                    return name1.equalsIgnoreCase(itemName);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean selectAFitSizeUpdateQtyAndAddToBag(String qty) {
        for (int i = 1; i <= colorImages.size(); i++) {
            click(colorSwatchByPos(i));
            staticWait(2000);
            waitUntilElementDisplayed(selectAFitOrSizeList.get(0));
            if (selectAvailableSizes.size() > 0) {
                for (int j = 0; j < selectAvailableSizes.size(); j++) {
                    click(selectAvailableSizes.get(j));
                    staticWait();
                    updateQuantity(qty);
                    click(addToBagBtn);
                    boolean viewBagConf = waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 20);
                    clickClose_ConfViewBag();
                    return viewBagConf;
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

    public boolean clickViewProductDetailsAndVerify(ProductDetailsPageActions productDetailsPageActions) {
        waitUntilElementDisplayed(viewProductDetailsLink, 5);
        click(viewProductDetailsLink);
        return waitUntilElementDisplayed(productDetailsPageActions.productName);
    }

    public boolean isColorSwatchDisplaying(int pos) {
        return isDisplayed(colorSwatchByPos(pos));
    }

    public boolean selectASizeAndAddToBag() {

        waitUntilElementDisplayed(selectAFitOrSizeList.get(0));
        if (selectAvailableSizes.size() > 0) {
            for (int j = 0; j < selectAvailableSizes.size(); j++) {
                click(selectAvailableSizes.get(j));
                staticWait();
                click(addToBagBtn);
                boolean viewBagConf = waitUntilElementDisplayed(addToBagNotification, 30);
                clickClose_ConfViewBag();
                return viewBagConf;
            }
        } else {
            return false;
        }
        return waitUntilElementDisplayed(addToBagBtn, 30);
    }
}