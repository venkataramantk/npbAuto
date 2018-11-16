package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 5/25/2016.
 */
public class ProductDetailsPageRepo extends HeaderMenuRepo {

    @FindBy(css = ".product-title")
    public WebElement productTitle;

    @FindBy(css = ".number-item")
    public WebElement itemNumber;

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBag;

    @FindBy(css = ".button-add-to-bag")
    public List<WebElement> addToBag_Outfits;

    public WebElement addToBag_OutfitsByPos(int i) {
        return getDriver().findElement(By.cssSelector("li:nth-child(" + i + ") .button-add-to-bag"));
    }

    @FindBy(css = ".button-change-store")
    public WebElement changeStoreLink;

    @FindBy(css = ".button-wishlist")//".button-add-to-wishlist")
    public WebElement addToWishList;

    @FindBy(xpath = ".//button[@class='button-wishlist added-to-wishlist']")
    public WebElement addedToWishList;

    @FindBy(xpath = ".//*[.='Select a Size: ']/parent::div//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableSizes;

    @FindBy(xpath = ".//*[.='Select a Size: ']/parent::div//label[(contains(@class,'disabled'))]")
    public List<WebElement> unAvailableSizes;

    @FindBy(xpath = ".//*[.='Select a Fit: ']/parent::div//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableFits;

    public WebElement selectASizeByPosition(int position) {
        return getDriver().findElement(By.xpath("(.//*[.='Select a Size: ']/parent::div//label[not(contains(@class,'disabled'))])[" + position + "]"));//.//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'disabled'))]["+position+"]"));
    }

    @FindBy(css = ".product-details-bopis-promo")
    public WebElement bopisPromoContent;

    @FindBy(id = "imperial")
    public WebElement sizeChart_USLink;

    @FindBy(css = "#imperial .on")
    public WebElement sizeChart_USOn;

    @FindBy(id = "metric")
    public WebElement sizeChart_MetricLink;

    @FindBy(css = "#metric .on")
    public WebElement sizeChart_MetricOn;

    @FindBy(xpath = "(//div[@class='color-chips-selector-items-list'])//div[1][not(contains(@class,'disabled'))]")
    public List<WebElement> availableColors;

    //Pooja
    @FindBy(xpath = "(//div[@class='color-chips-selector-items-list'])//div[1][contains(@class,'unchecked')][not(contains(@class,'disabled'))]")
    public List<WebElement> availableColorsNotSelected;

    public WebElement colorElementByPos(int i) {
        return getDriver().findElement(By.xpath("((//div[@class='color-chips-selector-items-list'])//div[1][contains(@class,'unchecked')][not(contains(@class,'disabled'))])["+i+"]"));
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

    public WebElement outOfStockEleByPos(int position) {
        return getDriver().findElement(By.cssSelector("#select_TCPSize .out-of-stock:nth-child(" + position + ")"));
    }

    @FindBy(css = "[name='quantity']")
    public WebElement qtyDropDown;

    @FindBy(css = ".select-option-selected")
    public WebElement qtyDrpDownSelected;

    @FindBy(css = "h1[itemprop='name']")
    public WebElement productName;

    @FindBy(xpath = ".//div[@class='color-chips-selector-container color-chips-selector']/span[@class='selected-option']")
    public WebElement colorName;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//div[@class='input-radio-icon-checked']")
    public WebElement selectedSizeElement;

    @FindBy(css = "#product-detail-wrapper>.site-breadcrumbs>ul")
    public WebElement BreadCrumb;

    @FindBy(css = "#li15")
    public WebElement subCategory;

    @FindBy(css = ".color-chips-selector-title")
    public WebElement colorLabel;

    @FindBy(css = ".color-chips-selector-items-list")
    public WebElement colorSwatches;

    @FindBy(css = ".size-and-fit-detail-items-list + .inline-error-message")
    public WebElement selectSizeErrMessage;

    @FindBy(css = ".error-box")
    public WebElement quantityLimitErrMessage;

    @FindBy(css = ".product-description.section-block>p")
    public WebElement productDescriptionHeader;

    @FindBy(css = ".product-description.section-block>ul")
    public WebElement productDescriptionContent;

    @FindBy(css = "#ropis-cta")
    public WebElement ropisBlockPDP;

    @FindBy(css = "#ropis-cta .btn-go")
    public WebElement reservationGoNowBtnPDP;


    @FindBy(xpath = ".//*[.='Select a Fit: ']/parent::div//label[(contains(@class,'item-selected-option'))]")
    public WebElement fitSelection;

    @FindBy(xpath = ".//*[.='Select a Fit: ']/parent::div//label")
    public List<WebElement> list_SelectAFit;

    public WebElement fitSelectionByPos(int i) {
        return getDriver().findElement(By.xpath(".//*[.='Select a Fit: ']/parent::div//label[" + i + "]/div/input"));
    }

    public WebElement fitByName(String name) {
        return getDriver().findElement(By.xpath("//div[@class='input-radio-title']//span[text()='" + name + "']"));
    }

    @FindBy(xpath = "//*[@id='select_TCPFit']/p[contains(.,'Select a Fit:')]")
    public WebElement lbl_SelectAFit;

    public WebElement writeAReviewLink() {
        return getFirstElementFromList(writeAReviewButtonAndLink);
    }


    @FindBy(css = ".bv-write-review.bv-focusable.bv-submission-button")
    public List<WebElement> writeAReviewButtonAndLink;

    @FindBy(css = "#WC_CQ_Container_PDP .quick-view .js-quick-view-modal")
    public List<WebElement> quickViewLinksOnRightRail;

    @FindBy(xpath = "//img[@alt='Online Only']")
    public WebElement badge_OnlineOnly;

    @FindBy(xpath = ".//*[@id='online-only-icon']/img")
    public WebElement img_OnlineOnly;

    @FindBy(xpath = "//p[contains(.,'LIMITED QUANTITIES')]")
    public WebElement lbl_LimitedQuantities;

    @FindBy(xpath = "//img[@alt='New']")
    public WebElement img_NewTag;

    @FindBy(xpath = ".//*[.='Select a Color: ']/parent::div//label")
    public List<WebElement> colorSwatchesList;

    @FindBy(xpath = ".//*[.='Select a Color: ']/parent::div//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableColorSwatches;
    
    @FindBy(css = ".color-chips-selector-title")
    public WebElement selectColorLabel;
    
    @FindBy(css = ".selected-option")
    public WebElement selectedColor;

    public WebElement availColorSwatchByPos(int i) {
        return getDriver().findElement(By.xpath("(.//*[.='Select a Color: ']/parent::div//label[not(contains(@class,'disabled'))])[" + i + "]"));
    }

    public WebElement ColorByName(String color) {
        return getDriver().findElement(By.xpath("//span[@title='" + color + "']//../preceding-sibling::div"));
    }

    public WebElement viewLargerImageLink() {
        return getFirstElementFromList(getDriver().findElements(By.cssSelector(".openModal.btn-zoom")));
    }

    public WebElement sizeByNumber(String size) {
        return getDriver().findElement(By.xpath("//input[@value='" + size + "']/../.."));
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

    @FindBy(css = "#points_pdp")
    public WebElement mpRPointsEarned;

    //*@FindBy(xpath=  ".//div[@class='bopis-cta']//strong")*/
    @FindBy(css = ".button-find-in-store")
    public WebElement pickUpStore;

    @FindBy(css = ".subtitle-bopis-section")
    public WebElement subtitleBopisSection;

    @FindBy(css = ".size.ui-radio")
    public List<WebElement> selectASizeBoxes;

    public String GetSize() {
        return getText(getDriver().findElement(By.xpath("//div[@class='size-and-fit-detail-container size-and-fit-detail'][contains(.,'Select a Size')]/span[2]")));//("//ul[@id='select_TCPSize']//li[contains(@class,'selected')]")));
    }

    @FindBy(css = ".price-item.actual-price")
    public WebElement salePrice;

    @FindBy(css = ".price-item.original-price")
    public WebElement originalPrice;

    @FindBy(name = "bv-submit-button")
    public WebElement postReviewButton;

    @FindBy(css = ".bv-mbox-close")
    public WebElement closeReviewOverlay;

    @FindBy(id = "bv-radio-rating-3")
    public WebElement overallRating_Stars;

    @FindBy(id = "bv-radio-rating_Quality-3")
    public WebElement qualityRating_Stars;

    @FindBy(id = "bv-radio-rating_Styling-3")
    public WebElement stylingRating_Stars;

    @FindBy(id = "bv-radio-rating_Durability-3")
    public WebElement durabilityRating_Stars;

    @FindBy(id = "bv-radio-rating_FitComfort-3")
    public WebElement fitRating_Stars;

    @FindBy(id = "bv-radio-rating_Value-3")
    public WebElement valueRating_Stars;

    @FindBy(id = "bv-textarea-field-reviewtext")
    public WebElement reviewTextArea;

    @FindBy(id = "bv-text-field-usernickname")
    public WebElement nickNameTextArea;

    @FindBy(id = "bv-mbox-label-text")
    public WebElement submittedText;

    @FindBy(css = ".wishlist-qty-information")
    public WebElement wishListQty;

    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure//a")
    public List<WebElement> prodRecommendation;

    @FindBy(css = ".heading-product-recomendation")
    public WebElement recommendationHeading;

    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure")
    public List<WebElement> prodRecommendationimg;

    @FindBy(xpath = ".//ol[@class='content-product-recomendation']//figure//div[2]//h3")
    public List<WebElement> prodRecommendationName;

    @FindBy(css = ".navigation-level-one-container")
    public WebElement subCate;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label[not(contains(@class,'disabled'))]//div[not(contains(@class,'checked'))]")
    public List<WebElement> availableGC;

    @FindBy(xpath = "(//div[@class='color-chips-selector-items-list']//label[not(contains(@class,'disabled'))])")
    public List<WebElement> enabledGC;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableGCValues;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-container size-and-fit-detail']//span[@class='selected-option']")
    public WebElement selectedGCValue;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-container size-and-fit-detail']//label[contains(@class,'item-selected-option')]//div/span")
    public WebElement selectedGCSize;

    @FindBy(css = ".price-item.actual-price")
    public WebElement actualPrice;

    @FindBy(css = ".size-and-fit-detail-container.size-and-fit-detail .inline-error-message")
    public WebElement selectSize_Error;

    @FindBy(xpath = "product-description-list")
    public WebElement prodDesc;

    @FindBy(css = ".badge-item-container.inline-badge-item>p")
    public WebElement productBadge;

    @FindBy(xpath = ".//div[@class='social-networks']//a[text()='Twitter']")
    public WebElement twitterLink;

    @FindBy(xpath = ".//div[@class='social-networks']//a[text()='Facebook']")
    public WebElement facebookLink;

    @FindBy(xpath = ".//div[@class='social-networks']//a[text()='Pinterest']")
    public WebElement pinterestLink;

    @FindBy(xpath = ".//p[@class='breadcrum-item-container']//a")
    public WebElement homeBreadcrumb;

    @FindBy(xpath = ".//ul[@class='outfiting-list-container']//a[@class='outfit-item-details-link']")
    public List<WebElement> viewProdLink;

    @FindBy(css = ".button-cart")
    public WebElement bagLink;

    // @FindBy(xpath = ".//div[@class=\"recommendations-and-outfit-container\"]//h2[text()=\"Complete The Look\"]")
    @FindBy(xpath = ".//div[@class=\"recommendations-and-outfit-container\"]//h2[text()=\"Other Ways To Wear It\"]")
    public WebElement completeTheLook;

    // @FindBy(xpath = ".//div/h2[text()=\"Complete The Look\"]//following-sibling::div//figure//a")
    @FindBy(xpath = ".//div/h2[text()=\"Other Ways To Wear It\"]//following-sibling::div//figure//a")
    public List<WebElement> outfitProdRecomDisplay;

    @FindBy(xpath = ".//a[@class='cover-shadow-link']")
    public List<WebElement> shopTheLookLink;

    @FindBy(xpath = ".//div[@class=\"go-back-container\"]//button[text()='Back']")
    public WebElement backLink;

    @FindBy(css = ".button-go-back")
    public WebElement backToResultsLink;

    @FindBy(css = ".button-show-more")
    public WebElement showMoreLink;

    @FindBy(css = ".button-show-less")
    public WebElement showLessLink;

    @FindBy(css = ".introduction-text")
    public WebElement prodDescIntro;

    @FindBy(css = ".button-resize")
    public WebElement fullSizeLink;

    @FindBy(css = ".overlay-content .principal-preview-image-container img")
    public WebElement imageLargerView;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModal;

    @FindBy(css = ".product-details-header-promo-text-area")
    public WebElement promotionalMsg;

    @FindBy(css = ".bv-rating-stars-container")
    public WebElement ratingsLink;

    @FindBy(css = ".bv-action-bar-header")
    public WebElement reviewsHeader;

    @FindBy(css = ".bv-mbox-close.bv-focusable")
    public WebElement reviewClose;

    @FindBy(css = ".image-item img")
    public List<WebElement> altImages;

    public WebElement altImageByPosition(int i) {
        return getDriver().findElement(By.cssSelector("li:nth-child(" + i + ") .image-item img"));
    }

    @FindBy(css = ".principal-preview-image-container>div>img")
    public WebElement heroImg;

    @FindBy(css = ".button-next")
    public WebElement nextArrow_LargeImg;

    @FindBy(css = ".overlay-content .color-chips-selector-container.color-chips-selector")
    public WebElement fullSize_colorContainer;

    @FindBy(xpath = "(//div[@class='overlay-content']//div[@class='color-chips-selector-items-list'])/label/div[(contains(@class,'input-radio-icon-unchecked'))]")
    public List<WebElement> fullSize_UnCheckedColorSwatches;

    @FindBy(xpath = "(//div[@class='overlay-content']//div[@class='color-chips-selector-items-list'])/label/div[(contains(@class,'input-radio-icon-checked'))]")
    public WebElement fullSize_CheckedColorSwatch;

    @FindBy(css = ".modal-title")
    public WebElement fullSize_ProdName;

    @FindBy(css = ".overlay-content .image-item")
    public List<WebElement> fullSize_AltImgs;

    public WebElement fullSize_altImageByPos(int i) {
        return getDriver().findElement(By.cssSelector(".overlay-content li:nth-child(" + i + ") .image-item img"));
    }

    @FindBy(css = ".button-size-chart")
    public List<WebElement> sizeChartLinks;

    @FindBy(css = ".page-header>h1")
    public WebElement sizeInfoHeader;

    @FindBy(css = ".measurement-units")
    public WebElement sizeChartMeasureLinks;

    //@FindBy(css = ".measure>img")
    @FindBy(xpath = "(//*[@class=\"measure\"]/img)[1]")
    public WebElement sizeChartMeasureImg;

    public WebElement sizeChartLinkByPos(int i) {
        return getDriver().findElement(By.cssSelector("li:nth-child(" + i + ") .button-size-chart"));
    }

    @FindBy(css = ".button-size-chart")
    public WebElement sizeChartLink;

    @FindBy(id = "size-charts")
    public WebElement sizeChartModal;

    @FindBy(css = "#size-menu li")
    public List<WebElement> sizeChartMenu;

    public WebElement deptActiveByPos(int i) {
        return getDriver().findElement(By.xpath("//ul[@id='size-menu']/li["+i+"]/a[@class='dept-active']"));
    }

    public WebElement sizeChartMenuByPos(int i) {
        return getDriver().findElement(By.cssSelector("#size-menu li:nth-child(" + i + ")"));
    }

    @FindBy(css = "#size-chart-display li")
    public List<WebElement> sizeChartDisplay;

    public WebElement sizeChartDisplayByPos(int i) {
        return getDriver().findElement(By.cssSelector("#size-chart-display li:nth-child(" + i + ")"));
    }


    public WebElement deptHeaderByDeptNameAndPos(String dept, int i) {
        return getDriver().findElement(By.cssSelector("#" + dept + " .department-header:nth-child(" + i + ")"));
    }

    public WebElement shoesDeptHeaderByPos(int i) {
        return getDriver().findElement(By.cssSelector(".subnav-content div:nth-child(" + i + ") .department-header"));
    }

    //OutFits
    @FindBy(css = ".outfiting-main-image")
    public WebElement outfitsMainImg;

    @FindBy(css = ".outfit-item-image")
    public List<WebElement> outfitsItemImgs;

    @FindBy(xpath = "(.//*[@class=\"select-common select-size mini-dropdown\"]//option[not(contains(@class,'disabled'))])[1]")
    public List<WebElement> selectSize_Outfit;

    public WebElement availSizeSwatchByPos(int i) {
        return getDriver().findElement(By.xpath("(.//*[.='Size:']/ancestor::div//option[not(contains(@value,'disabled'))])[\"+i+\"]"));
    }

    @FindBy(css = ".button-wishlist")
    public List<WebElement> favIcon_Outfits;

    @FindBy(css = ".cover-shadow-link")
    public WebElement shopTheLook;

    @FindBy(xpath = "//select[@name='quantity']")
    public List<WebElement> qtyDropDown_Outfits;

    @FindBy(css = ".price-item.actual-price")
    public List<WebElement> originalPrice_Outfit;

    @FindBy(xpath = "//select[@name='size']")
    public List<WebElement> sizeDropDown_Outfit;

    @FindBy(xpath = ".//div[@class=\"size-and-fit-detail-items-list\"]//span")
    public List<WebElement> sizeFacet_PDP;

    @FindBy(xpath = "(//select[@id='labeled-select_1']//option)[\"+i+\"]")
    public List<WebElement> sizeDisplay_Outfit;

    @FindBy(css = ".title-product-description")
    public WebElement prodDescription_PDP;

    @FindBy(xpath = "((//select[@name=\"size\"])[1]//option[not(contains(@class,'disabled'))])[2]")
    public WebElement firstSize_Outfit;

    @FindBy(css = ".price-item.actual-price")
    public WebElement priceDisplayPDP;

    @FindBy(xpath = "//div[@class='size-and-fit-detail-items-list']/label")
    public List<WebElement> product_sizes;

    @FindBy(xpath = "//div[@class='size-and-fit-detail-items-list']/label[contains(@class,'size-and-fit-detail')][not(contains(@class,'item-disabled-option'))][not(contains(@class,'item-selected-option'))]")
    public WebElement productSizeAvailableAndNotSelected_btn;

    @FindBy(css = ".button-wishlist.added-to-wishlist")
    public WebElement pdp_FavIconEnabled;

    @FindBy(xpath = "//div[@class='product-recomendation viewport-container'][h2[contains(.,'You May Also Like')]][not(button[@class='button-close'])]//a[@class='product-item-link']")
    public List<WebElement> prodRecommendationPdp;

    @FindBy(xpath = "//div[@class='product-recomendation viewport-container'][h2[contains(.,'You May Also Like')]]//label[contains(@class,'size-and-fit-detail item-selected-option')]")
    public WebElement alreadySelectedOneSizeBtnInRecom;

    @FindBy(xpath = "//div[@class='product-recomendation viewport-container']//button[@class='button-close']")
    public WebElement close_link_Recom_Prod;

    @FindBy(css = ".button-view-bag")
    public WebElement conf_ViewBag;

    @FindBy(css = ".button-close")
    public WebElement conf_ButtonClose;

    @FindBy(css = ".button-checkout")
    public WebElement conf_Checkout;

    @FindBy(css = ".continue-shopping")
    public WebElement conf_ContinueCheckout;

    @FindBy(css = ".breadcrum-container")
    public WebElement breadCrumbPDP;

    @FindBy(css = ".selected-option")
    public WebElement selectedAttribute;

    //@FindBy(css = ".paypal-button-desktop")
    @FindBy(xpath = "//*[@alt='paypal']" )
    public WebElement paypalSbModal;

    @FindBy(css = ".xcomponent-component-frame.xcomponent-visible")
    public WebElement sbModal_PayPalFrame;

    //Pooja
    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list'][//div[@class='input-radio-icon-checked']]//span[contains(.,'ONE SIZE')]")
    public WebElement alreadySelectedOneSizeBtn;

    @FindBy(css = ".size-and-fit-detail-container.size-and-fit-detail .selected-option")
    public WebElement selectedSizeInPDP;

    @FindBy(xpath = "(//div[@class=\"size-and-fit-detail-container size-and-fit-detail\"]//span[@class=\"selected-option\"])[2]")
    public WebElement selectedSizeInPDPWithFit;

    @FindBy(css = ".selection.select-option-selected")
    public WebElement quantitySelectedInPDP;
}
