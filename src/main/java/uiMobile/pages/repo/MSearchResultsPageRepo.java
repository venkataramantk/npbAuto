package uiMobile.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uiMobile.UiBaseMobile;

import java.util.List;

public class MSearchResultsPageRepo extends UiBaseMobile {

    public WebElement productImageByPosition(int i) {
        return getDriver().findElement(By.cssSelector(".products-listing-grid li:nth-child(" + i + ") figure"));
    }

    @FindBy(xpath = ".//*[contains(@id,'SBN_facet_TCPSize')]")
    public WebElement link_SizeFacet;

    public WebElement addToFavIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .favorite-icon-container"));
    }

    public WebElement clearSize() {
        return getDriver().findElement(By.id("Size-clear"));
    }


    @FindBy(name = "typeahead")
    public WebElement searchBox;

    //Pooja
    @FindBy(xpath = "//ul[@class='item-list-common display-search-suggested-keywords-items-list']/li/div[not(H4)]/a[text()='Boy>Tops>Shirts']")
    public WebElement boysShirtsSuggestions;

    @FindBy(className = "typeahead-icon")
    public WebElement searchIcon;

    @FindBy(className = "button-search")
    public WebElement searchSubmitButton;

    @FindBy(css = ".empty-search-result-title")
    public WebElement emptySearchPage;

    @FindBy(css = ".items-count-content")
    public WebElement catPage;

    @FindBy(css = ".badge-item-container.top-badge-container")
    public WebElement tag;

    @FindBy(css = ".search-tips-title")
    public WebElement searchTips;

    @FindBy(css = ".display-search-suggested-keywords-input")
    public List<WebElement> newSearchBar;

    @FindBy(css = ".search-by-keywords-content")
    public WebElement serachedFor;

    @FindBy(css = ".scroll-to-top-icon")
    public WebElement backToTop;

    @FindBy(css = ".open-filter-button")
    public WebElement filterBtn;

    @FindBy(css = ".custom-select-button.sorter-filter-button-closed")
    public WebElement sortyByBtn;

    @FindBy(css = ".current-search")
    public WebElement currentSearch;


    @FindBy(css = ".items-count-content-number")
    public WebElement itemsCount;

    // @FindBy(css = "items-count-container.from-navigation-bar")
    @FindBy(css = ".search-by-keywords-content")//Live1
    public WebElement youSearchedForText;

    //Pooja
    @FindBy(css = ".product-title-container h3")
    public List<WebElement> productTitles;

    //Pooja
    public List<WebElement> productTitlesWithText(String Keyword) {
        String keywordUC = Keyword.toUpperCase();
        String keywordLC = Keyword.toLowerCase();
        return getDriver().findElements(By.xpath("//h3/a[@class='product-title-content name-item'][contains(translate(., '" + keywordUC + "', '" + keywordLC + "'), '" + keywordLC + "')]"));
    }

    //Pooja
    @FindBy(css = ".custom-loading-icon")
    public WebElement lazyloading;

    //Pooja
    @FindBy(css = ".button-add-to-bag")
    public WebElement addToBagBtn;

    //Pooja
    public WebElement addToBagIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .bag-button-container span"));
    }

    //Pooja
    @FindBy(css = ".add-to-bag-confirmation-content")
    public WebElement addtoBagNotification;

    //Pooja
    @FindBy(css = ".ranking-wrapper")
    public List<WebElement> starRating;

    //Pooja
    @FindBy(css = "a.empty-search-result-suggestion")
    public WebElement didYouMeanResutLink;
    @FindBy(css = ".open-filter-button")
    public WebElement filter;

    @FindBy(css = ".send-information-button")
    public WebElement doneBtn;

    @FindBy(css = ".send-information-button-black")
    public WebElement clearAllBtn;

    public WebElement filterFacetCTA(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']"));
    }

    public WebElement collapseFacet(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']/ancestor::div[@class='accordion accordion-filters-list']//span[@class='accordion-title']"));
    }

    public WebElement expandFacet(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']/ancestor::div[@class='accordion accordion-expanded accordion-filters-list']//span[@class='accordion-title']"));
    }

    public WebElement clearFilter(String facet) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + facet + "']/following-sibling::button[1]"));
    }

    @FindBy(css = "div.color-title")
    public List<WebElement> colors;


    @FindBy(css = ".item-list-common.item-list-collapsible.item-list-collapsible-expanded.size-detail-chips-items-list li div span")
    public List<WebElement> sizes;

    @FindBy(css = ".custom-select-common.price-detail-chips.price-detail-chips ul li div span")
    public List<WebElement> prices;

    @FindBy(css = ".item-list-common.fit-detail-chips-items-list li div span")
    public List<WebElement> fits;

    @FindBy(css = ".custom-select-common.age-detail-chips.age-detail-chips ul li div span")
    public List<WebElement> ageList;

    public WebElement selectGenderFilter(String gender) {
        return getDriver().findElement(By.xpath("//span[translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ') = '" + gender + "']"));
    }

    @FindBy(css = ".clear-all-color-plp-filters")
    public WebElement clearColorFilter;

    @FindBy(css = ".clear-all-size-plp-filters")
    public WebElement clearSizeFilter;

    @FindBy(css = ".custom-select-button.sorter-filter-button")
    public WebElement sortCloseBtn;

    @FindBy(css = ".sort-title")
    public List<WebElement> sortOptions;

    @FindBy(css = "div[data-title='min_offer_price desc'] span")
    public WebElement sort_HighToLow;

    @FindBy(css = "div[data-title='min_offer_price desc'] span")
    public WebElement sort_LowToHigh;

    @FindBy(css = "div[data-title='TCPBazaarVoiceRating desc']")
    public WebElement topRated;

    @FindBy(css = ".text-price.offer-price")
    public List<WebElement> offerPrices;

    @FindBy(css = ".product-title-content.name-item")
    public List<WebElement> productImages;

    //Pooja
    @FindBy(css = ".pup-box")
    public WebElement errorPagePuppyBox;

    //Pooja
    public WebElement filterButton(String filter) {
        return getDriver().findElement(By.xpath("//div[@class='accordion accordion-filters-list']//span[@class='accordion-title'][contains(.,'" + filter + "')]"));
    }

    //Pooja
    public WebElement expandedFilterButton(String filter) {
        return getDriver().findElement(By.xpath("//div[@class='accordion accordion-expanded accordion-filters-list']//span[@class='accordion-title'][contains(.,'" + filter + "')]"));
    }

    public List<WebElement> badgeProducts(String badgeName) {
        return getDriver().findElements(By.xpath("//p[text()='" + badgeName.toUpperCase() + "']/../..//img"));
    }

    @FindBy(css = ".product-title-content.name-item")
    public List<WebElement> productNames;

    @FindBy(css = ".error-box")
    public WebElement errorMessage;

    @FindBy(css = ".search-result-empty-container .empty-search-result-title")
    public WebElement emptySearchLblContent;
}