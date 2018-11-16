package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

/**
 * Created by JKotha on 23/10/2017.
 */
public class MCategoryDetailsPageRepo extends UiBaseMobile {
    @FindBy(xpath = "//span[@class='items-count-content']/..")
    public WebElement deptNameTitle;

    @FindBy(xpath = "(//li[@class='item-container']//img)[1]")
    public WebElement firstImg;

    @FindBy(css = ".item-container")
    public List<WebElement> productContainers;

    @FindBy(css = ".outfit-container.item-container")
    public List<WebElement> outfitProductcontainers;

    @FindBy(css = ".item-title")
    public WebElement plpHeader;

    @FindBy(css = ".go-back-container button")
    public WebElement gobackbtn;

    public WebElement productName(int i) {
        return getDriver().findElement(By.xpath("(//li[@class='item-container']//div[@class='product-title-container']/h3)[" + i + "]"));
    }

    public WebElement bopisProduct(int i) {
        return getDriver().findElement(By.xpath("(//li[@class='item-container']//div[@class='buttons-container']/button[@class='pickup-button-container'])[" + i + "]"));
    }

    public WebElement productImgByPosition(int i) {
        return getDriver().findElement(By.xpath("(//li[@class='item-container']//figure//img)[" + i + "]"));
    }


    @FindBy(css = ".product-title-container")
    public WebElement productTitle;

    @FindBy(css = ".product-title-container h3")
    public List<WebElement> productTitles;

    @FindBy(css = ".badge-item-container.top-badge-container")
    public WebElement tag;

    @FindBy(css = ".badge-item-container.inline-badge-container")
    public List<WebElement> inlineBadges;

    @FindBy(css = ".breadcrum-container")
    public WebElement breadCrumb;

    @FindBy(css = ".breadcrum-container p a")
    public WebElement breadCrumbCategory;

    @FindBy(css = ".text-price.list-price")
    public WebElement wasPrice;

    @FindBy(css = ".text-price.offer-price")
    public WebElement offerPrice;
    //Pooja
    @FindBy(css = ".text-price.offer-price")
    public List<WebElement> offerPrices;

    @FindBy(css = ".product-title-content.name-item")
    public List<WebElement> productImages;

    @FindBy(css = ".product-image-container img")
    public List<WebElement> OutfitsImages;

    @FindBy(css = ".pickup-button-container")
    public List<WebElement> pickupstoreBtn;

    @FindBy(xpath = "//h2[text()='Loading...']")
    public WebElement spinner;

    @FindBy(css = ".error-icon")
    public WebElement outofStock;

    @FindBy(css = "button.bag-button-container")
    public List<WebElement> addToBagIcon;

    public WebElement addToBagIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-of-type(" + i + ") .bag-button-container span"));
    }

    @FindBy(css = "button.favorite-icon-container")
    public List<WebElement> addToFavIcon;

    @FindBy(css = ".favorite-icon-active")
    public List<WebElement> favIconEnabled;

    @FindBy(css = ".item-container")
    public List<WebElement> itemContainers;

    @FindBy(css = ".color-chips-container")
    public List<WebElement> colourSwatch;

    @FindBy(xpath = "//li[div[@class='color-chips-container']]")
    public List<WebElement> multiColoredImage;

    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    @FindBy(css = ".link-redirect")
    public WebElement viewProdDetailsLink;

    @FindBy(xpath = ".//div[@class='color-chips-selector-items-list']//label")
    public List<WebElement> availableColors;

    @FindBy(xpath = ".//div[@class='size-and-fit-detail-items-list']//label[not(contains(@class,'item-disabled-option'))]")
    public List<WebElement> availableFlipSizes;

    @FindBy(css = "[name='quantity']")
    public WebElement qtyDropDown;

    @FindBy(css = ".label-radio.input-radio.size-and-fit-detail-item.size-and-fit-detail.item-selected-option")
    public WebElement selectedFlipSize;

    //Pooja
    @FindBy(xpath = "//div[@class='input-radio-title']/span[text()='ONE SIZE']")
    public WebElement selectedFlipOneSize;

    @FindBy(css = ".list-container li div")//".department-detail-container.department-detail label div:nth-of-type(2) span")
    public List<WebElement> categories;

    //Pooja
    @FindBy(css = ".filter-size-and-color-container div li")//".categories-details-chips-item span")
    public List<WebElement> categoryChips;

    @FindBy(css = ".open-filter-button")
    public WebElement filter;

    @FindBy(css = ".open-filter-button-expanded")
    public WebElement filterExpanded;

    @FindBy(css = ".applied-filter-list div span")
    public WebElement appliedFilterList;

    //Pooja
    @FindBy(css = ".category-filter-applied-filters-list div ul div")
    public WebElement appliedCategFilterList;

    //Pooja
    @FindBy(css = ".size-filter-applied-filters-list div ul div span")//".category-filter-applied-filters-list div ul div")
    public WebElement appliedSizeFilterList;

    //Pooja
    @FindBy(css = ".color-filter-applied-filters-list div ul div")
    public WebElement appliedColorFilterList;

    @FindBy(css = ".applied-filter-color-chip")
    public WebElement appliedColor;

    @FindBy(css = ".button-close")
    public WebElement closeLink;

    @FindBy(css = ".filter-title")
    public WebElement filterByText;

    @FindBy(css = ".custom-select-common.sorter-filter-chips.sorter-filter-chips")
    public WebElement sortFilter;

    //@FindBy(css = ".custom-select-common.sorter-filter-chips.sorter-filter-chips div ul li")
    @FindBy(css = ".item-common.sorter-filter-item")
    public List<WebElement> sorts;

    //@FindBy(css = ".item-common.sorter-filter-chips-item.item-selected.sorter-filter-chips-selected")
    @FindBy(css = ".sorter-filter-selected.item-highlighted")
    public WebElement selectedSortOption;

    @FindBy(css = ".item-common.sorter-filter-chips-item.item-selected.sorter-filter-chips-selected")
    public List<WebElement> selectedSortOptions;

    @FindBy(css = ".custom-select-common.categories-details-chips.categories-details-chips")
    public WebElement categoryFilter;

    @FindBy(css = ".custom-select-common.categories-details-chips.categories-details-chips div ul li")
    public List<WebElement> cates;

    @FindBy(css = ".custom-select-common.size-detail-chips.size-detail-chips")
    public WebElement sizeFilterLink;

    public WebElement selectGenderFilter(String gender) {
        return getDriver().findElement(By.cssSelector("div[data-title='" + gender + "']"));
    }

    @FindBy(css = ".custom-select-common.color-detail-chips.color-detail-chips")
    public WebElement colorFilterLink;

    //Modified by Pooja to consider only not selected colors on Bag Flip
    // @FindBy(xpath = "//div[@class='custom-select-common color-detail-chips color-detail-chips']//li/div[not(contains(@class,'selected'))]")
    @FindBy(xpath = "//div[contains(@class,'color-detail-chips-item')][not(contains(@class,'selected'))]/div[@class='color-title']")
    public List<WebElement> colors;

    @FindBy(css = ".item-list-common.item-list-collapsible.item-list-collapsible-expanded.size-detail-chips-items-list li div span")
    public List<WebElement> sizes;

    @FindBy(css = ".custom-select-common.price-detail-chips.price-detail-chips ul li div span")
    public List<WebElement> prices;

    @FindBy(css = ".item-list-common.fit-detail-chips-items-list li div span")
    public List<WebElement> fits;

    @FindBy(css = " .custom-select-common.age-detail-chips.age-detail-chips ul li div span")
    public List<WebElement> ageList;

    public WebElement filterFacetCTA(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']"));
    }

    public WebElement clearFilter(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']/following-sibling::button[1]"));
    }

    @FindBy(css = ".send-information-button")
    public WebElement doneBtn;

    @FindBy(css = ".send-information-button")
    public List<WebElement> doneBtns;

    @FindBy(css = ".send-information-button-black")
    public WebElement clearAllBtn;

    @FindBy(css = ".clear-all-color-plp-filters")
    public WebElement clearColorFilter;

    @FindBy(css = ".clear-all-size-plp-filters")
    public WebElement clearSizeFilter;

    public WebElement clearAllFilter(String facet) {
        return getDriver().findElement(By.xpath("//"));
    }

    @FindBy(xpath = ".//div[@class='list-container']//li")
    public List<WebElement> sizeAttributes;

    @FindBy(css = ".apply-filter-button")
    public WebElement sizeApplyBtn;

    @FindBy(css = ".filtering-title")
    public WebElement filteringByText;

    @FindBy(css = ".item-common.size-detail-chips-item.item-selected.size-detail-chips-selected")
    public WebElement selectedSize;

    @FindBy(css = ".item-common.size-detail-chips-item.item-selected.size-detail-chips-selected")
    public List<WebElement> selectedSizes;

    @FindBy(xpath = ".//div[@class='list-container']//li")
    public List<WebElement> colorAttributes;

    @FindBy(css = ".item-common.color-detail-chips-item.item-selected.color-detail-chips-selected")
    public List<WebElement> selectedColors;

    @FindBy(css = ".applied-filter-remove-button")
    public List<WebElement> filterRemove;

    @FindBy(css = ".container-price .offer-price")
    public List<WebElement> productsOfferPrice;

    //Pooja
    @FindBy(xpath = "//p[@class='breadcrum-item-container'][1]/a[not(contains(.,'|'))]")
    public WebElement validBreadcrumb;
    //Pooja
    @FindBy(css = ".color-chips-container ol button.active img")
    public List<WebElement> defaultSwatch;
    //Pooja
    @FindBy(css = ".color-chips-selector.item-selected-option input")
    public WebElement selectedFlipColor;

    //Pooja
    @FindBy(xpath = "//li[div[@class='color-chips-container']]/div/button[@class='bag-button-container']")
    public List<WebElement> addToBagBtnOfMultiColordProd;

    //Pooja
    @FindBy(xpath = "//div[@class='breadcrum-container']/p/a")
    public List<WebElement> breadcrumb_values;

    //Pooja
    public WebElement selectProdSizeByTitle(String size) {
        return getDriver().findElement(By.xpath("//span[@class='size-title'][text()='" + size + "']"));
    }

    //Pooja
    public WebElement getSelectedFlipSizebyTitle(String title) {
        return getDriver().findElement(By.xpath("//label[@class='label-radio input-radio size-and-fit-detail-item size-and-fit-detail item-selected-option']/div/span[text()='" + title + "']"));
    }

    //Pooja
    @FindBy(xpath = "//ul[@class='item-list-common color-detail-chips-items-list']/li//img")
    public WebElement selectedFlipColorValue;

    //Pooja
    public WebElement applyColorFilterByTitle(String title) {
        return getDriver().findElement(By.xpath("//div[@class='custom-select-common color-detail-chips color-detail-chips']//li/div[not(contains(@class,'selected'))]//img[@alt='" + title + "']"));
    }

    //Pooja
    @FindBy(css = ".add-to-bag-confirmation-content")
    public WebElement addtoBagNotification;

    //Pooja
    public WebElement getSortoption(String option) {
        return getDriver().findElement(By.xpath("//ul[@class='item-list-common sorter-filter-items-list']//span[contains(.,'" + option + "')]"));
    }

    @FindBy(css = ".open-filter-button")
    public WebElement filterBtn;

    @FindBy(css = ".accordion.accordion-filters-list")
    public List<WebElement> filterOptions;

    @FindBy(css = ".custom-select-button.sorter-filter-button-closed")
    public WebElement sortyByBtn;

    public WebElement collapseFacet(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']/ancestor::div[@class='accordion accordion-filters-list']//span[@class='accordion-title']"));
    }

    public WebElement expandFacet(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']/ancestor::div[@class='accordion accordion-expanded accordion-filters-list']//span[@class='accordion-title']"));
    }

    @FindBy(css = ".sort-title")
    public List<WebElement> sortOptions;

    @FindBy(css = "div[data-title='min_offer_price desc'] span")
    public WebElement sort_HighToLow;

    @FindBy(css = "div[data-title='min_offer_price desc'] span")
    public WebElement sort_LowToHigh;

    @FindBy(css = "div[data-title='TCPBazaarVoiceRating desc']")
    public WebElement topRated;

    @FindBy(css = ".cover-shadow-content label")
    public List<WebElement> shopTheLook;

    @FindBy(css = ".custom-select-button.sorter-filter-button")
    public WebElement sortCloseBtn;

    //Pooja
    @FindBy(css = ".scroll-to-top-container.active-scroll")
    public WebElement backToTopBtn;

    //Pooja
    @FindBy(xpath = "//span[@class='text-price offer-price']/following-sibling::span[@class='text-price list-price no-badge']")
    public WebElement listPrDisplayBelowOfferPr;

    //Pooja
    @FindBy(css = ".plp-section-container.search-from-navigation .breadcrum-item-container a")
    public List<WebElement> contentLandingBrdcrumb;

    //Pooja
    public WebElement breadcrumbsOnPlp(String l1, String l2) {
        return getDriver().findElement(By.xpath("//div[p[@class='breadcrum-item-container'][a[text()='" + l1 + "']]/following-sibling::p[a[text()='" + l2 + "']]]"));
    }

    public List<WebElement> badgeProducts(String badge) {
        return getDriver().findElements(By.xpath("//p[text()='" + badge + "']/../ancestor::li//img"));
    }

    @FindBy(css = ".search-result-slot")
    public WebElement searchBanner;


    @FindBy(css = "button.pickup-icon-container")
    public List<WebElement> addToBopisIcon;

    public WebElement itemImg(int i) {
        return getDriver().findElement(By.xpath("(//li[contains(@class,'item-container')]//img[@class='product-image-content img-item'])[" + i + "]"));
    }

    @FindBy(css = ".hover-button-enabled.favorite-icon-container")
    public WebElement addToFavBtnPLP;

    @FindBy(css = ".error-box")
    public WebElement errorBox;

}
