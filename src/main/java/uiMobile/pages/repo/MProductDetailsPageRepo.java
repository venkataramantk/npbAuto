package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

/**
 * Created by JKotha 11/08/2017.
 */
public class MProductDetailsPageRepo extends MobileHeaderMenuRepo {

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBag;

    @FindBy(css = ".button-change-store")
    public WebElement changeStoreLink;

    @FindBy(css = ".button-wishlist")
    public WebElement addToWishList;

    @FindBy(xpath = ".//button[@class='button-wishlist added-to-wishlist']")
    public WebElement addedToWishList;

    //    @FindBy(xpath = ".//*[@class='size-and-fit-detail-items-list']/label[not(contains(@class,'disabled'))]")
    @FindBy(xpath = ".//*[.='Select a Size: ']/parent::div//label[not(contains(@class,'disabled'))]//div[1]/input|.//*[.='Select a Talla: ']/parent::div//label[not(contains(@class,'disabled'))]//div[1]/input|.//*[.='Choisir un(e) Taille: ']/parent::div//label[not(contains(@class,'disabled'))]//div[1]/input")
    public List<WebElement> availableSizes;

    public WebElement selectASizeByText(String text) {
        return getDriver().findElement(By.xpath(".//*[.='Select a Size: ']/parent::div//label[not(contains(@class,'disabled'))]//div[1]/input[@value='" + text + "']"));
    }

    @FindBy(xpath = ".//*[.='Select a Fit: ']/parent::div//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableFits;

    public WebElement selectAFitByText(String text) {
        return getDriver().findElement(By.xpath(".//*[.='Select a Fit: ']/parent::div//label[not(contains(@class,'disabled'))]//span[contains(text(),'" + text + "')]"));
    }

    @FindBy(css = ".color-chips-selector .input-radio-title")
    public List<WebElement> availableColors;

    public WebElement colorElementByPos(int i) {
        return getDriver().findElement(By.cssSelector(".color-chips-selector label:nth-child(" + i + ") .input-radio-title"));
    }

    @FindBy(css = "#select_TCPSize .out-of-stock")
    public List<WebElement> outOfStockSize;
    @FindBy(xpath = "//form[@class='product-details-form-container']//div[contains(text(),'item is out of stock')]")
    public WebElement outOfStockItemError;

    @FindBy(css = ".see-more")
    public WebElement seeMoreLink;

    @FindBy(css = ".add-to-default-wishlist.collapsed")
    public WebElement addToDefaultWLBtn;

    @FindBy(css = ".create-wishlist>a")
    public WebElement createNewWishListLink;

    @FindBy(css = ".wishlist-name>input")
    public WebElement wishListNameTextBox;

    @FindBy(css = ".create-new-wishlist-cta")
    public WebElement createWLButton;

    @FindBy(css = ".transaction-messaging.success.active>a")
    public WebElement successfulMsgCreatedWL;

    public WebElement createdWishListByName(String wishListName) {
        return getDriver().findElement(By.xpath("(//a[text()='" + wishListName + "'])[1]"));
    }


    /*@FindBy(css="span[id='partNumber']")*/
    @FindBy(css = ".number-item")
    public WebElement itemNumber;

    @FindBy(css = "[name='quantity']")
    public WebElement qtyDropDown;

    @FindBy(css = "h1[itemprop='name']")
    public WebElement productName;

    /*@FindBy(id="colorName")*/
    @FindBy(xpath = ".//div[@class='color-chips-selector-container color-chips-selector']/span[@class='selected-option']")
    public WebElement colorName;


    @FindBy(css = ".breadcrum-container")
    public WebElement BreadCrumb;

    @FindBy(css = "#li15")
    public WebElement subCategory;

    /*@FindBy(css=".color-swatches.section-block>p")*/
    @FindBy(css = ".color-chips-selector-container.color-chips-selector")
    public WebElement colorLabel;

    /*@FindBy(css="#prod-swatches")*/
    /*@FindBy(css=".color-swatches #prod-swatches")*/
    @FindBy(css = ".color-chips-selector-items-list")
    public WebElement colorSwatches;

    /*@FindBy(css="#error_Size")*/
    @FindBy(css = ".inline-error-message")
    public WebElement selectSizeErrMessage;

    @FindBy(css = ".go-back-container button")
    public WebElement gobackbtn;

    @FindBy(css = ".product-title")
    public WebElement outfitsProductTitle;

    /*@FindBy(css="#ErrorMessageText")*/
    @FindBy(css = ".error-box")
    public WebElement quantityLimitErrMessage;

    @FindBy(css = ".product-description.section-block>p")
    public WebElement productDescriptionHeader;

    @FindBy(css = ".product-description.section-block>ul")
    public WebElement productDescriptionContent;


    @FindBy(css = ".bv-write-review.bv-focusable.bv-submission-button")
    public WebElement writeAReviewButtonAndLink;


    @FindBy(css = ".button-secondary.button-create-account")
    public WebElement createAccountButton;

    @FindBy(css = "#prod-swatches .block")
    public List<WebElement> colorSwatchesList;

    public WebElement colorSwatchesByPosition(int i) {
        return getDriver().findElement(By.cssSelector("#prod-swatches li:nth-child(" + i + ") img"));
    }

    public WebElement viewLargerImageLink() {
        return getFirstElementFromList(getDriver().findElements(By.cssSelector(".openModal.btn-zoom")));
    }

    @FindBy(css = "#colorName-zoom")
    public WebElement largerImgColor;

    @FindBy(css = "#dialog-modal")
    public WebElement largerImgDialogModal;

    @FindBy(css = ".swatches-zoom>ul>li>a>img")
    public List<WebElement> colorsOnLargerImg;

    public WebElement colorsOnLargerImgByPosition(int i) {
        return getFirstElementFromList(getDriver().findElements(By.cssSelector(".swatches-zoom>ul>li:nth-child(" + i + ")>a>img")));
    }

    @FindBy(css = ".mpr-points")
    public WebElement mpRPointsSection;


    @FindBy(css = ".button-find-in-store")
    public WebElement pickUpStore;

    @FindBy(css = ".subtitle-bopis-section")
    public WebElement subtitleBopisSection;

    @FindBy(css = ".size.ui-radio")
    public List<WebElement> selectASizeBoxes;


    public String GetSize() {
        /*return getText(getDriver().findElement(By.xpath("/*//*[@class='radio-group-container']/*//*[contains(@class,'checked')]")));*/
        return getText(getDriver().findElement(By.xpath("//div[@class='size-and-fit-detail-container size-and-fit-detail'][contains(.,'Select a Size')]/span[2]")));//("//ul[@id='select_TCPSize']//li[contains(@class,'selected')]")));
    }

    @FindBy(css = ".price-item.actual-price")
    public WebElement salePrice;

    @FindBy(css = ".price-item.original-price")
    public WebElement originalPrice;

    @FindBy(css = ".bv-rating-ratio a")
    public WebElement starRatings;

    @FindBy(css = ".bv-action-bar-header")
    public WebElement ratingsSection;

    @FindBy(css = ".bv-write-review.bv-focusable.bv-submission-button")
    public List<WebElement> writeAReview_Links;

    @FindBy(xpath = "//div[@class='ratings-and-reviews-accordion accordion']/h4[contains(.,'Ratings & Reviews')]")
    public WebElement ratingsAndReviewsLnk;

    @FindBy(name = "bv-submit-button")
    public WebElement postReviewButton;

    @FindBy(id = "[id='bv-radio-rating-3']")
    public WebElement overallRating_Stars;

    @FindBy(id = "[id='bv-radio-rating_Quality-3']")
    public WebElement qualityRating_Stars;

    @FindBy(id = "[id='bv-radio-rating_Styling-3']")
    public WebElement stylingRating_Stars;

    @FindBy(id = "[id='bv-radio-rating_Durability-3']")
    public WebElement durabilityRating_Stars;

    @FindBy(id = "[id='bv-radio-rating_FitComfort-3']")
    public WebElement fitRating_Stars;

    @FindBy(id = "[id='bv-radio-rating_Value-3']")
    public WebElement valueRating_Stars;

    @FindBy(id = "[id='bv-textarea-field-reviewtext']")
    public WebElement reviewTextArea;

    @FindBy(id = "[id='bv-mbox-label-text']")
    public WebElement submittedText;

    @FindBy(css = ".wishlist-qty-information")
    public WebElement wishListQty;

    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure//a")
    public List<WebElement> prodRecommendation;

    @FindBy(css = ".heading-product-recomendation")
    public WebElement recommendationHeading;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label[not(contains(@class,'disabled'))]//div[not(contains(@class,'checked'))]")
    public List<WebElement> availableGC;

    @FindBy(xpath = "(//div[@class='color-chips-selector-items-list']//label[not(contains(@class,'disabled'))])[4]")
    public WebElement enabledGC;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableGCValues;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-container size-and-fit-detail']//span[@class='selected-option']")
    public WebElement selectedGCValue;

    @FindBy(css = ".price-item.actual-price")
    public WebElement actualPrice;

    //Pooja
    public WebElement actualPriceCurrency(String currency) {
        return getDriver().findElement(By.xpath("//span[@class='price-item actual-price'][contains(.,'" + currency + "')]"));
    }


    @FindBy(css = ".button-go-back")
    public WebElement backToResultsLink;

    //Pooja
    @FindBy(id = "size-charts")
    public WebElement sizeChartModal;

    //Pooja
    @FindBy(css = ".button-size-chart")
    public WebElement size_chart_lnk;

    //Pooja
    public WebElement active_dpt_Size_chart(String dpt) {
        return getDriver().findElement(By.xpath("//a[@class='dept-active'][text()='" + dpt + "']"));
    }

    //Pooja
    public WebElement active_SubNav_Size_chart(String subNav) {
        return getDriver().findElement(By.xpath("//a[@class='subnav-link dept-active'][text()='" + subNav + "']"));
    }

    @FindBy(css = ".button-modal-close")
    public WebElement closeOverlay;

    @FindBy(css = ".container-product-recomendation")
    public WebElement completeLookImage;


    //Pooja-6/6/18
    @FindBy(xpath = ".//div[@class=\"recommendations-and-outfit-container\"]//h4[text()=\"Complete The Look\"]")
    public WebElement completeTheLook;

    @FindBy(css = ".complete-the-look-cta")
    public WebElement completeTheLookCTA;

    //Pooja-6/6/18
    @FindBy(xpath = "//div[@class='recommendations-and-outfit-container']//h4[contains(.,'You May Also Like')]")
    public WebElement recommendationSection;

    //Pooja-6/6/18
    @FindBy(xpath = "//div[@class='recommendations-and-outfit-container']/div[@class='accordion accordion-expanded']/h4[contains(.,'You May Also Like')]")
    public WebElement recommendationSectionExpanded;

    //Pooja-6/6/18
    @FindBy(css = "//div[@class='recommendations-and-outfit-container']/div[@class='accordion accordion-expanded']/h4[contains(.,'You May Also Like')]/button[@title='Toggle You May Also Like']")
    public WebElement recommendationToggleBtn;

    //Pooja-6/7/18
    public WebElement productTitleInformation() {
        return getDriver().findElement(By.xpath("//div[@class='product-details-content']//h1"));
    }

    //Pooja-6/7/18
    @FindBy(xpath = "//button[@title='Toggle You May Also Like']/../..//button[@class='button-prev'][@disabled]")
    public WebElement recommendationPrevBtnDisabled;

    //Pooja-6/7/18
    @FindBy(xpath = "//button[@title='Toggle You May Also Like']/../..//button[@class='button-prev']")
    public WebElement recommendationPrevBtn;

    //Pooja-6/7/18
    @FindBy(xpath = "//button[@title='Toggle You May Also Like']/../..//button[@class='button-next']")
    public WebElement recommendationNextBtn;

    //Pooja-6/7/18
    public WebElement recommendationCouponDots(int i) {
        return getDriver().findElement(By.xpath("//button[@title='Toggle You May Also Like']/../..//button[@class='button-pagination-dot active'][@data-index=\" + i + \"]"));
    }

    //Pooja-6/7/18
    public WebElement recommendationProductName(int i) {
        return getDriver().findElement(By.xpath("//div[@class='product-recomendation']//li[" + i + "]//h3[@class='department-name']"));
    }

    //Pooja-6/7/18
    @FindBy(xpath = "//button[@title='Toggle You May Also Like']/../..//div[@class='container-item-recomendation']//img")
    public WebElement recommendedProductImage;

    //Pooja-6/13/18
    @FindBy(css = ".overlay-content figure.image-zoom img")
    public WebElement imageLargerView;

    //Pooja-6/13/18
    @FindBy(css = ".principal-preview-image img")
    public WebElement heroImg;

    @FindBy(css = ".outfit-item-details-link")
    public WebElement pdpLinkInOutfits;

    @FindBy(css = ".select-common.select-size.mini-dropdown")
    public WebElement outfitssizeDD;

    @FindBy(css = ".select-common.select-qty")
    public WebElement outfitsqtyDD;

    @FindBy(css = ".outfit-unavailable-notification")
    public WebElement outfitsOOS;

    //Pooja
    @FindBy(css = ".product-images-container .button-next")
    public WebElement nextArrow;

    @FindBy(css = ".label-radio.input-radio.color-chips-selector-item.color-chips-selector.item-selected-option input")
    public WebElement selectedColor;

    @FindBy(css = ".color-chips-selector-container.color-chips-selector span:nth-of-type(2)")
    public WebElement selectedColorText;

    @FindBy(xpath = "//span[text()='Select a Size: ']/../span[2]")
    public WebElement selectedSizeText;

    @FindBy(css = ".size-and-fit-detail-container.size-and-fit-detail .selected-option")
    public WebElement selectedGiftCardValue;

    @FindBy(css = ".label-radio.input-radio.size-and-fit-detail-item.size-and-fit-detail.item-selected-option input")
    public WebElement selectedSize;

    @FindBy(css = ".badge-item-container.inline-badge-item")
    public WebElement productBadge;

    @FindBy(css = ".product-details-bopis-promo")
    public WebElement bopisPromoContent;

    @FindBy(css = "div.product-images-container a.icon-fcbk")
    public WebElement facebookIcon;

    @FindBy(css = "div.product-images-container a.icon-twitter")
    public WebElement twitterIcon;

    @FindBy(css = "div.product-images-container a.icon-pinterest")
    public WebElement pininterestIcon;


    @FindBy(css = "#imperial .on")
    public WebElement sizeChart_USOn;

    @FindBy(id = "li#metric a")
    public WebElement sizeChart_MetricLink;

    @FindBy(css = "#metric .on")
    public WebElement sizeChart_MetricOn;

    @FindBy(css = "li#imperial a")
    public WebElement sizeChart_USLink;

    @FindBy(css = ".badge-item-container.inline-badge-item p")
    public WebElement badge;
	
    @FindBy(xpath = ".//*[.='Select a Fit: ']/parent::div//label")
    public List<WebElement> list_SelectAFit;

    @FindBy(css = ".color-chips-selector-title")
    public WebElement selectColorLabel;



}