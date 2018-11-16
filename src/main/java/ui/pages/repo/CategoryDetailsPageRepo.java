package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by skonda on 6/7/2016.
 */
public class CategoryDetailsPageRepo extends HeaderMenuRepo {

    @FindBy(xpath = "(//li[contains(@class,'item-container')]//img[@class='product-image-content img-item'])[1]")
    public WebElement firstImg;

    public WebElement itemImg(int i) {
        return getDriver().findElement(By.xpath("(//li[contains(@class,'item-container')]//img[@class='product-image-content img-item'])[" + i + "]"));
    }
    public WebElement itemImgContentWithNext(int i) {
        return getDriver().findElement(By.xpath("(//li[@class='item-container'][contains(.,'next')])["+i+"]//img[@class='product-image-content img-item']"));
    }

    public WebElement itemImgContentWithPrev(int i) {
        return getDriver().findElement(By.xpath("(//li[@class='item-container'][contains(.,'prev')])["+i+"]//img[@class='product-image-content img-item']"));
    }
    @FindBy(css = ".badge-item-container.top-badge-container")
    public List<WebElement> productBadge;

    public WebElement itemName(int i) {
        // return getDriver().findElement(By.xpath("(//li[@class='item-container']/div[@class='product-title-container']/h3)[" + i + "]"));
        return getDriver().findElement(By.xpath("(//li[contains(@class,'item-container')]//div[@class='product-title-container']/h3)[" + i + "]"));

    }

    public WebElement itemNameWithColorSwatch(int i) {
        return getDriver().findElement(By.xpath("(//li[contains(@class,'item-container')]//div[@class='color-chips-container'])[" + i + "]"));
    }

    @FindBy(xpath = "//li[@class='item-container']//div[@class='color-chips-container']//button")//(//li[@class='item-container']/div[contains(@class,'color-chips-container')]/following-sibling::div[contains(@class,'product-title-container')])")
    public List<WebElement> itemNameWithColorSwatches;

    public WebElement justFewLeftBadge(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .top-badge-container>p"));
    }

    public WebElement selectColorSwatch() {
        return getDriver().findElement(By.xpath("(//*[@class='product-color-chip-image']/../../../..)"));
    }

    public WebElement colorSwatchPosByProd(int prodPos, int i) {
        return getDriver().findElement(By.xpath("((//div[@class='color-chips-container'])["+prodPos+"]//img)["+i+"]"));//(//li[div[contains(@class,'color-chips-container')]][" + prodPos + "]//img[@class='product-color-chip-image'])[" + i + "]"));
    }

    public List<WebElement> colorSwatchListByProd(int prodPos) {
        return getDriver().findElements(By.xpath("(//li[" + prodPos + "]//img[@class='product-color-chip-image'])"));
    }

    public WebElement activeColorSwatchByProd(int i) {
        return getDriver().findElement(By.xpath("(//div[@class='color-chips-container'])["+i+"]//button[@class='active']/img"));//(//li[div[contains(@class,'color-chips-container')]][" + i + "]//button[@class='active'])/img"));
    }
    public WebElement activeColorSwatchProdName(int i) {
        return getDriver().findElement(By.xpath("(//div[@class='color-chips-container'])["+i+"]//button[@class='active']//parent::ol/parent::div/following-sibling::div"));
    }

    @FindBy(xpath = "//img[contains(@alt,'Looking for something?')]")
    public WebElement errorPage;

    @FindBy(css = ".pup-box")
    public WebElement errorPagePuppyBox;

    @FindBy(css = ".content-box")
    public WebElement errorPageContentBox;

    @FindBy(xpath = "//*[@class='list-container']//li") // modified for uatlive1
    public List<WebElement> sortByOptions;

    @FindBy(xpath = "//*[@class='custom-select-button sorter-filter-button-closed']")
    public WebElement sortBy_dropdown;

    @FindBy(css = "#SBN_facet_Girl")
    public WebElement girlFacet; // not found

    @FindBy(xpath = "(//img[@class='product-image-content img-item'])[2]")//uatlive2
    public WebElement secondImg;

    public List<WebElement> quickviewList;

    public WebElement quickViewLinks() {
        return getDriver().findElement(By.cssSelector(".quick-view a"));
    } // need to update for uatlive 1

    @FindBy(css = ".breadcrum-container")//live2
    public WebElement breadCrumb;

    @FindBy(css = ".product-image-content.img-item")
    public List<WebElement> productImages; // not found

    @FindBy(css = ".button-size-chart")
    public WebElement sizeCharBtn;

    @FindBy(css = ".cover-shadow-content label")
    public List<WebElement> shopTheLook;

    @FindBy(css = ".custom-loading-icon")
    public WebElement lazyloading;

    @FindBy(css = ".content-slot-list-container img")
    public WebElement fpo;

    @FindBy(css = ".item-container")
    public List<WebElement> itemContainers;

    @FindBy(css = ".item-container.grid-cta-enabled")
    public List<WebElement> itemCOntainerABEnabled;

    @FindBy(css = ".site-breadcrumbs>ul")
    public WebElement clearanceBreadCrumb; // not found

    @FindBy(css = "#sub-cats >ul>li")
    public List<WebElement> links_LeftNavigation; // live1, no left navigation link in live2

    @FindBy(xpath = "//*[@id='sub-cats']//*[@class='leftNavLevel0']")
    public List<WebElement> linksUnselected_LeftNavigation; // live1, no left navigation link in live2

    @FindBy(xpath = ".//*[@id='li9'][3]")
    public WebElement clearance_Level2; // live1, no left navigation link in live2

    @FindBy(xpath = ".//*[@class='leftNavLevel0 selected']//*[contains(@id,'SBN_facet_')][contains(@class,'notSelected')]")
    public List<WebElement> subcategory_LeftNav;

    @FindBy(css = "#li15")
    public WebElement subcategory_BreadCrumb;

    @FindBy(css = ".horizontal-flag>[title='Extended Sizes']")
    public WebElement getBatch_ExtendedSizes;

    @FindBy(xpath = "//nav[contains(@class,'sortBar')] //*[@id='breadCrumb-pagination-right-top'] //a[contains(.,'<')]")
    public WebElement lnk_PreviousPage;

    @FindBy(xpath = "//nav[contains(@class,'sortBar')] //*[@id='breadCrumb-pagination-right-top'] //a[contains(.,'>')]")
    public WebElement lnk_NextPage;

    @FindBy(xpath = "//nav[contains(@class,'sortBar')] //a[contains(.,'Show All')]")
    public WebElement lnk_ShowAll;

    @FindBy(xpath = "//nav[contains(@class,'sortBar')] //a[contains(.,'Show Less')]")
    public WebElement lnk_ShowLess;

    @FindBy(css = ".applied-filter-remove-button")
    public List<WebElement> lnk_ClearColor;

    //Pooja
    public WebElement selectProdSizeByTitle(String title) {
        return getDriver().findElement(By.xpath("//div[@class='item-common size-detail-item']/span[text()='" + title + "']"));
    }

    @FindBy(xpath = "//*[@class='product productLarge'] //*[@class='categoryTitle']")
    public WebElement sortByBestMatch;

    @FindBy(xpath = "//*[@class='product productLarge'] //*[@class='new']")
    public WebElement list_NewArrivals;

    @FindBy(xpath = "//*[@class='product productLarge'] //*[@class='price productRow']//p")
    public WebElement sortByPrice;

    @FindBy(css = "a[id='SBN_facet_School Uniforms']")
    public WebElement schoolUniformFacet;

    @FindBy(css = ".outfit-container.item-container")
    public List<WebElement> outfitProducts;

    @FindBy(xpath = "//*[@id='outfits-rows-wrapper']//img")
    public List<WebElement> outfitProducts_CA;

    public WebElement outfitProductImg_CAByPosition(int i) {
        return getDriver().findElement(By.xpath("(//*[@id='outfits-rows-wrapper']//li//div)[" + i + "]"));
    }

    public WebElement quickViewLink_ByPosition(int i) {
        return getDriver().findElement(By.cssSelector(".product.productLarge:nth-child(" + i + ") .quick-view a"));
    }

    // @FindBy(id = "SBN_facet_Outfits")
    @FindBy(css = ".breadcrum-last-item")
    public WebElement outfitLink;


    @FindBy(css = "button.pickup-icon-container")
    public List<WebElement> addToBopisIcon;

    @FindBy(xpath = "//button[@class='pickup-icon-container hover-button-enabled']/parent::div/parent::div")
    public List<WebElement> itemContainerWithBopisICons;

    public WebElement addToBagIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .bag-icon-container"));
    }

    @FindBy(css = ".favorite-icon-container .message-icon")
    public List<WebElement> addToFavMessage;

    @FindBy(css = "button.favorite-icon-container")
    public List<WebElement> addToFavIcon;

    @FindBy(css = "button.pickup-icon-container")
    public List<WebElement> pickUpInStoreIcons;

    @FindBy(css = ".pickup-icon-container .message-icon")
    public List<WebElement> pickUpInStoreMessage;

    @FindBy(css = "button.bag-icon-container")
    public List<WebElement> addToBagIcon;

    @FindBy(css = "button.bag-icon-container .message-icon")
    public List<WebElement> addToBagIconMsg;

    @FindBy(css = ".favorite-icon-active")
    public List<WebElement> favIconEnabled;

    @FindBy(css = ".color-chips-container")
    public List<WebElement> colourSwatch;

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(css = ".bag-icon-container.hover-button-enabled")
    public WebElement addToBagBtnPLP;

    @FindBy(css = ".hover-button-enabled.favorite-icon-container")
    public WebElement addToFavBtnPLP;

    @FindBy(css = ".link-redirect")
    public WebElement viewProdDetailsLink;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-container size-and-fit-detail'][contains(.,'Select a Size')]//label[not(contains(@class,'disabled'))]")
    public List<WebElement> availableSizes;

    @FindBy(css = ".label-radio.input-radio.size-and-fit-detail-item.size-and-fit-detail.item-selected-option")
    public WebElement selectedFlipSize;

    public WebElement availableSizeByPosition(int i) {
        return getDriver().findElement(By.xpath("(.//div[@class='size-and-fit-detail-container size-and-fit-detail'][contains(.,'Select a Size')]//label[not(contains(@class,'disabled'))])[" + i + "]"));
    }

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label")
    public List<WebElement> allSizes;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label")
    public List<WebElement> allFits;

    @FindBy(xpath = "(.//div[@class='size-and-fit-detail-items-list'])[1]//span")
    public List<WebElement> availableFits;

    @FindBy(css = ".button-close")
    public WebElement closeAddToBaglink;

    @FindBy(css = ".filter-title")
    public WebElement filterByText;

    @FindBy(css = "p.breadcrum-item-container:nth-child(3)")
    public WebElement l3CategoryInBreadCrumb;

    //@FindBy(xpath = "//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Size')]")
    @FindBy(xpath = "(//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Size')])[1]")
    public WebElement sizeFilterLink;

    @FindBy(css = ".custom-select-button.size-detail-button")
    public WebElement sizeFilterLinkOpen;

    //@FindBy(xpath = "//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Color')]")
    @FindBy(css = ".custom-select-button.color-filter-chip.size-detail-button-closed.custom-select-button-placeholder")
    public WebElement colorFilterLink;

    @FindBy(xpath = "//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Price')]")
    public WebElement priceFilterLink;

    @FindBy(xpath = "//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Fit')]")
    public WebElement fitFilterLink;

    @FindBy(xpath = "//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Gender')]")
    public WebElement genderFilterLink;

    //@FindBy(xpath = "//div[@class='custom-select-common size-range-detail size-range-detail-closed']/div[contains(.,'Size Range')]")
    @FindBy(xpath = "//div[@class='custom-select-common size-detail size-detail-closed']/div[contains(.,'Size Range')]")
    public WebElement sizeRangeFilterLink;

    @FindBy(xpath = "//div[@class='custom-select-button color-filter-chip size-detail-button custom-select-button-placeholder'][contains(.,'Color')]")
    public WebElement openedColorFilter;

    @FindBy(xpath = "//div[@class='custom-select-button size-detail-button custom-select-button-placeholder'][contains(.,'Fit')]")
    public WebElement openedFitFilter;

    @FindBy(xpath = "//div[@class='custom-select-button size-detail-button custom-select-button-placeholder'][contains(.,'Gender')]")
    public WebElement openedGenderFilter;

    @FindBy(xpath = "//div[@class='custom-select-button size-detail-button custom-select-button-placeholder'][contains(.,'Range')]")
    public WebElement openedSizeRangeFilter;

    @FindBy(xpath = "//div[@class='custom-select-button size-detail-button custom-select-button-placeholder'][contains(.,'Price')]")
    public WebElement openedPriceFilter;

    @FindBy(xpath = ".//div[@class='list-container']//li[div[not(contains(@class,'color-detail-selected'))]]")
//Modified By Pooja to exclude already selected color if any
    public List<WebElement> sizeAttributes;

    @FindBy(xpath = "//div[@class='list-container']//li[div[@class=\"item-common size-detail-item\"]]")
    public List<WebElement> priceAttributes;

    @FindBy(xpath = "//div[@class='list-container']//div[@class='item-common size-range-detail-item']")
    public List<WebElement> sizeRangeAttributes;

    @FindBy(css = ".apply-filter-button")
    public WebElement sizeApplyBtn;

    @FindBy(css = ".item-common.size-detail-item.item-selected.size-detail-selected")
    public WebElement selectedSize;

    @FindBy(css = ".filtering-title")
    public WebElement filteringByText;

    public WebElement appliedFilterLbl(String label) {
        return getDriver().findElement(By.xpath("//span[@class='applied-filter-title'][contains(.,'" + label + "')]"));
    }

    @FindBy(xpath = "//div[@class='applied-filter-item'][not(img)]/span[@class='applied-filter-title']")
    public WebElement appliedSizeFilterValue;

    @FindBy(xpath = "//div[@class='applied-filter-item'][img[@class='applied-filter-color-chip']]/span[@class='applied-filter-title']")
    public WebElement appliedColorFilterValue;


    @FindBy(css = ".size-and-fit-detail.item-selected-option .input-radio-icon-checked input")
    public WebElement alreadySelectedSizeBtn_value;

    @FindBy(css = ".color-chips-selector.item-selected-option .input-radio-icon-checked input")
    public WebElement alreadySelectedColor_value;

    @FindBy(css = ".color-chips-selector .input-radio-icon-unchecked input")
    public List<WebElement> notSelectedColor_swatch;

    @FindBy(css = ".department-detail-items-list")
    public WebElement departmentItems;

    @FindBy(css = ".applied-filter-list")
    public WebElement totalFilters;

    public WebElement closeFilter(String filter) {
        return getDriver().findElement(By.xpath("//span[text()='" + filter + "']/../button"));
    }

    public WebElement departmentFilter(String department) {
        return getDriver().findElement(By.xpath("//label/div/span[text()='" + department + "']"));
    }

    @FindBy(css = ".search-result-slot")
    public WebElement searchBanner;

    @FindBy(css = ".applied-filter-item")
    public WebElement appliedFilter;

    //@FindBy(xpath = ".//div[@class='list-container']//li")
    @FindBy(xpath = ".//div[@class='list-container']//div/img")
    public List<WebElement> colorAttributes;

    @FindBy(css = ".item-common.size-detail-item.item-selected.size-detail-selected")
    public WebElement selectedColor;

    public List<WebElement> topBadgesBy_BadgeName(String badgeName) {
        return getDriver().findElements(By.xpath("//*[@class='badge-item-container top-badge-container'][contains(.,'" + badgeName + "')]"));
    }

    public List<WebElement> imageWithBadgeName(String badgeName) {
        return getDriver().findElements(By.xpath("//li[contains(@class,'item-container')][contains(.,'" + badgeName + "')]"));
    }

    public List<WebElement> inlineBadgesBy_BadgeName(String badgeName) {
        return getDriver().findElements(By.xpath("//*[@class='badge-item-container inline-badge-container'][contains(.,'" + badgeName + "')]/parent::div"));
    }

    @FindBy(css = ".error-box")
    public WebElement flipOOS;

    @FindBy(css = ".error-box")
    public WebElement errorBox;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a size']")
    // select a size error message
    public WebElement selectSizeErrorPLP;

    @FindBy(css = ".select-common.select-qty")
    public WebElement selectQuantityDropDown;

    @FindBy(css = "select[name='quantity']")
    public WebElement qtyDropDown;

    public WebElement addBopisItemByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .pickup-icon-container"));
    }

    @FindBy(xpath = "//div[@class='badge-item-container top-badge-container'] [contains(.,'CLEARANCE')]")
    public List<WebElement> badge_Clearance;

    @FindBy(css = ".product-image-container .button-next")
    public List<WebElement> prodImgButton_Next;

    public List<WebElement> prodImgWith_Next(){
        return getDriver().findElements(By.xpath("(//li[@class='item-container'][contains(.,'next')])"));
    }

    public List<WebElement> prodImgWith_Prev(){
        return getDriver().findElements(By.xpath("(//li[@class='item-container'][contains(.,'prev')])"));
    }
    @FindBy(css = ".button-next")
    public WebElement next_button;

    @FindBy(css = ".button-prev")
    public WebElement pre_button;

    @FindBy(css = ".product-image-container .button-prev")
    public List<WebElement> prev_ProdImgButton;

    @FindBy(css = ".custom-select-button.sorter-filter-button-closed")
    public WebElement sortByOption;

    @FindBy(css = ".list-container")
    public WebElement sortList;

    @FindBy(css = ".sort-item-selected")
    public WebElement selectedSort;

    @FindBy(css = ".items-count-content")
    public WebElement countText;

    @FindBy(css = ".list-container ul")
    public WebElement availableSorts;

    @FindBy(xpath = "//span[@class=\"sort-title\"][text()='New Arrivals']")
    public WebElement sort_NewArrivals;

    @FindBy(xpath = "//span[@class=\"sort-title\"][text()='Price: Low to High']")
    public WebElement sort_LowToHigh;

    @FindBy(xpath = "//span[@class=\"sort-title\"][text()='Price: High to Low']")
    public WebElement sort_HighToLow;

    @FindBy(xpath = "//span[@class=\"sort-title\"][text()='Top Rated']")
    public WebElement sort_topRated;

    @FindBy(xpath = "//span[@class=\"sort-title\"][text()='Most Favorited']")
    public WebElement sort_MostFavorite;

    // @FindBy(xpath = "//div[@class=\"custom-select-button size-detail categories-detail-button-closed custom-select-button-placeholder\"]")
    @FindBy(xpath = "//div[@class='custom-select-button size-detail-button-closed custom-select-button-placeholder'][contains(.,'Category')]")
//Live1
    public WebElement categoryFilter;

    @FindBy(css = ".list-container li")
    public List<WebElement> categoryList;

    @FindBy(xpath = "//div[@class=\"list-container\"]//li")
    public List<WebElement> categoryOptions;

    @FindBy(xpath = "//div[@class=\"list-container\"]//div[@class=\"item-common size-detail categories-detail-item\"]")
    public WebElement selectCategory;

    @FindBy(xpath = ".//ul[@class='outfiting-list-container']//a[@class='outfit-item-details-link']")
    public List<WebElement> viewProdLink;

    @FindBy(xpath = ".//div[@class='inline-error-message'][text()='Please select a size']")
    public WebElement sizeErrMsg;

    @FindBy(xpath = ".//ul[@class='outfiting-list-container']//button[@class='button-add-to-bag']")
    public List<WebElement> addToBag_Btn;

    //@FindBy(xpath = ".//ul[@class='outfiting-list-container']//label[@class='select-common select-size mini-dropdown']")
    @FindBy(xpath = ".//ul[@class='outfiting-list-container']//div[@class='select-common select-size mini-dropdown label-error']")
    //@FindBy(xpath = ".//ul[@class='outfiting-list-container']//div[@class='select-common select-size mini-dropdown']")
    public List<WebElement> sizeDropdown;

    @FindBy(xpath = ".//div[@class='list-and-notification-container enabled-notifications']//div[@class='select-common select-size mini-dropdown']//select/option[not(@disabled)]")
    public List<WebElement> selectAvailableSize;

    @FindBy(xpath = ".//div[@class=\"select-common select-size mini-dropdown\"]//span[@class=\"selection select-option-selected\"]/ancestor::form//button[@class='button-add-to-bag']")
    public WebElement selectSizeAddToBag;

    @FindBy(css = ".button-cart")
    public WebElement bagLink;

    public WebElement outfitProdImage(int i) {
        return getDriver().findElement(By.xpath("(//div[@class='product-grid-content']//div//a) [" + i + "]"));
    }

    @FindBy(xpath = "(//div[@class=\"wishlist-icon-container\"]//button)[\"+i+\"]")
    public List<WebElement> addOutfitToFav;

    @FindBy(xpath = ".//div[@class='wishlist-icon-container']//button[@class=\"button-wishlist added-to-wishlist\"]")
    public WebElement itemAddedToFav;

    @FindBy(xpath = ".//div[@class=\"outfit-item-container\"]//section[@class=\"size-chart-form-container\"]")
// @FindBy(css = ".size-chart-form-container")
    public List<WebElement> sizeChartLink;

    @FindBy(xpath = "//div[@class=\"page-header\"]")
    public WebElement pageHeader;

    @FindBy(xpath = ".//ul[@class='outfiting-list-container'].button-modal-close")
    public WebElement closeModal;

    @FindBy(xpath = ".//div[@class=\"go-back-container\"]//button[text()='Back']")
    public WebElement backLink;

    @FindBy(xpath = ".//div[@class=\"list-and-notification-container enabled-notifications\"]//h3[text()=\" item(s) in this outfit are currently unavailable\"]")
    public WebElement outfitOOS;

    @FindBy(xpath = ".//div[@class='product-grid-content']//div//a")
    public List<WebElement> outfitimages;

    @FindBy(css = ".outfiting-figure-container")
    public WebElement outfitMainImage;

    @FindBy(xpath = ".//div[@class=\"content-wrapper\"]")
    public WebElement outfitUnavailable;

    @FindBy(css = ".button-add-to-bag")
    public List<WebElement> addToBag;

    @FindBy(xpath = ".//p[contains(text(),'ONLINE EXCLUSIVE')]")
    public WebElement searchOnlineOnly;

    @FindBy(xpath = ".//div[@class=\"badge-item-container inline-badge-item\"] [contains(.,'ONLINE EXCLUSIVE')]")
    public WebElement onlineOnly_PDP;

    @FindBy(xpath = ".//div[@class=\"department-detail-container department-detail\"]//label[@for=\"departments_661_2181\"]")
    public WebElement toddlerGirl_FilterBy;

    //@FindBy(css = ".navigation-level-two-container.open-navigation-level-two")
    @FindBy(css = ".sub-menu-category-item.navigation-level-three-item")
    public WebElement l3LinksDisplay;

    //@FindBy(css = ".navigation-level-one-item.inline-navigation-active a")
    @FindBy(css = ".navigation-level-two-link.active")
    public WebElement activel2CategoryInLeftNavigationPane;

    //@FindBy(xpath = ".//ul[@class=\"navigation-level-two-container open-navigation-level-two\"]/li")
    @FindBy(xpath = ".//*[@class=\"sub-menu-category sub-menu-category-level-three\"]//li")
    public List<WebElement> l3CategoryLink;

    @FindBy(xpath = "(.//div[@class=\"breadcrum-container\"]/p)[2]")
    public WebElement breadcrumbL2CategoryLink;

    @FindBy(css = ".text-price.offer-price")
    public List<WebElement> offerPrice;

    @FindBy(xpath = ".//span[contains(@class,'text-price list-price')]")
    public List<WebElement> actualPrice;

    @FindBy(css = ".body-copy")
    public WebElement bodyCopyText;

    //Pooja
    public WebElement colorAttributesByTitle(String title) {
        return getDriver().findElement(By.xpath(".//div[@class='list-container']//div[not(contains(@class,'selected'))]/div[span[text()='" + title + "']]"));
    }

    //Pooja
    public WebElement levelThreeLink(String l3Cate) {
        return getDriver().findElement(By.xpath("//li[@class='sub-menu-category-item navigation-level-three-item']/a[contains(text(),'" + l3Cate + "')]"));
    }

    //Pooja
    public WebElement levelThreeLinkActive(String l3Cate) {
        return getDriver().findElement(By.xpath("//li[@class='sub-menu-category-item navigation-level-three-item']/a[@class='active']"));
    }

    //Pooja
    public WebElement pickUpInStoreBtnOnPLP(int i) {
        return getDriver().findElement(By.xpath(".//li[contains(@class,'item-container')][" + i + "]//button[@class='pickup-button-container']/span[text()='Pick up in store']"));
    }

    //Pooja
    @FindBy(css = ".bag-button-container")
    public List<WebElement> addToBagButton;

    @FindBy(css = ".custom-ranking-container")
    public List<WebElement> rating_ProductTile;

    public List<WebElement> badgeProducts(String badgeName) {
        return getDriver().findElements(By.xpath("//p[text()='" + badgeName.toUpperCase() + "']/../..//img"));
    }
    @FindBy(xpath = ".//div[@class=\"badge-item-container top-badge-container\"][contains(.,'NEW!')]")
    public List<WebElement> newBadge_PLP;

    @FindBy(xpath = ".//div[@class=\"badge-item-container top-badge-container\"][contains(.,'ONLINE EXCLUSIVE')]")
    public List<WebElement> onlineOnlyBadge_PLP;

    public WebElement pickUpInStoreIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .pickup-icon-container"));
    }
}


