package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPageRepo extends HeaderMenuRepo {

    @FindBy(css = ".quick-view>a")
    public List<WebElement> quickViewLinksAll;

    public WebElement quickViewLinks() {
        return getDriver().findElement(By.cssSelector(".quick-view>a"));
    }

    @FindBy(xpath = "//*[contains(@title,'Extended')]/ancestor::div[2]//div[@class='quick-view']//a")
    public List<WebElement> quickViewLinksWithExtendedSizes;

    @FindBy(xpath = "//*[contains(@title,'LIMITEDINVENTORY')]/ancestor::div[2]//div[@class='quick-view']//a")
    public List<WebElement> quickViewLinksWithLimitedQuantities;

    @FindBy(css = ".search-count")
    public WebElement searchMatches;

    @FindBy(css = ".product-title-content.name-item")
    public List<WebElement> productNames;

    public WebElement limitedQuantitiesImgByPosition(int i) {
        return getDriver().findElement(By.cssSelector(".product.productLarge:nth-child(" + i + ") .horizontal-flag img"));
    }

    public WebElement productImageByPosition(int i) {
//		return getDriver().findElement(By.xpath("(//div[@class='productImage'])["+i+"]/a/img"));
        return getDriver().findElement(By.cssSelector(".product-grid-block-container li:nth-of-type(" + i + ")"));
    }

    public List<WebElement> productImageOnPlp() {
        return getDriver().findElements(By.xpath("//div[@class='product-grid-block-container']/li[@class='item-container']"));
    }

    public WebElement prodImgByBadgeAndPos(String searchTerm, int i) {
        return getDriver().findElement(By.xpath("(//div[@class='badge-item-container top-badge-container'] [contains(.,'" + searchTerm + "')]/parent::li)[" + i + "]"));
    }

    public List<WebElement> prodImagesByBadge(String searchTerm) {
        return getDriver().findElements(By.xpath("//div[@class='badge-item-container top-badge-container'] [contains(.,'" + searchTerm + "')]/parent::li"));
    }

    public WebElement prodAddToBagIconsByBadgeName(String searchTerm, int i) {
        return getDriver().findElement(By.xpath("(//div[@class='badge-item-container top-badge-container'] [contains(.,'" + searchTerm + "')]/preceding-sibling::div/button[@class='bag-icon-container hover-button-enabled'])[" + i + "]"));
    }


    public WebElement addToFavIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .favorite-icon-container"));
    }

    @FindBy(css = ".product-grid-block-container li a div img")
    public List<WebElement> productImages;

    @FindBy(css = ".error-box")
    public WebElement errorMessage;

    @FindBy(css = "p.search-by-keywords-content")
    public WebElement youSearchedFor;

    @FindBy(css = ".search-by-keywords-content strong")
    public WebElement searchResultsTerm;

    @FindBy(xpath = "//*[@id='Search_Result_Summary']/*[@class='hints']")
    public WebElement hintsForSearching;

    @FindBy(css = ".list-container")
    public WebElement search_SuggestedKey;

    @FindBy(css = ".display-search-suggested-keywords-disabledOption>h4")
    public List<WebElement> search_SuggestedCategory;

    @FindBy(id = "SimpleSearchForm_SearchTerm2")
    public WebElement txt_SearchBox;

    @FindBy(xpath = "//*[@id='CatalogSearchFormAgain'] //input[@type='submit']")
    public WebElement btn_SearchSubmit;

    @FindBy(xpath = "//section[@id='sub-cats']")
    public WebElement subCatSection;

    @FindBy(xpath = ".//*[contains(@id,'SBN_facet_TCPSize')]")
    public WebElement link_SizeFacet;

    @FindBy(id = "SBN_facet_Color")
    public WebElement link_ColorFacet;

    @FindBy(id = "choice-Color")
    public WebElement color_Options;

    @FindBy(xpath = "//ul[@id='choice-Size']")
    public WebElement size_Options;

    @FindBy(id = "Color-clear")
    public WebElement lnk_ClearColor;

    @FindBy(id = "Size-clear")
    public WebElement lnk_ClearSize;

    @FindBy(id = "clearAll")
    public WebElement lnk_ClearAll;

    public WebElement clearColor() {
        return getDriver().findElement(By.id("Color-clear"));
    }

    public WebElement clearSize() {
        return getDriver().findElement(By.id("Size-clear"));
    }

    @FindBy(css = ".site-breadcrumbs>ul")
    public WebElement breadCrumb;

    @FindBy(css = ".pickup-icon-container")
    public List<WebElement> pickUpInStoreIcons;

    public WebElement pickUpInStoreIconByPos(int i) {
        return getDriver().findElement(By.cssSelector(".item-container:nth-child(" + i + ") .pickup-icon-container"));
    }

    @FindBy(css = ".search-result-empty-container .typeahead input")
    public WebElement noResults_SearchField;

    @FindBy(css = ".main-section-container.search-result-empty-container .typeahead .button-search")
    public WebElement noResults_searchIcon;

    @FindBy(css = ".search-result-empty-container .empty-search-result-title")
    public WebElement emptySearchLblContent;

    @FindBy(css = ".search-tips-message-container")
    public WebElement searchTipsLbl;

    @FindBy(css = ".empty-search-result-title")
    public WebElement pageNotFound_Page;

    @FindBy(css = ".search-result-slot>div>span")
    public WebElement promotionalMsg;

    @FindBy(css = ".container-price .offer-price")
    public List<WebElement> productsOfferPrice;

    @FindBy(css = ".container-price .list-price")
    public List<WebElement> productsListPrice;

    @FindBy(xpath = "//span[@class='text-price offer-price']/following-sibling::span[contains(@class,'text-price list-price')]")
    public List<WebElement> listPrDisplayBelowOfferPr;

    @FindBy(css = ".outfit-container .product-image-container a")
    public List<WebElement> outfitsImgOnPlp;

    @FindBy(xpath = "//div[@class='display-search-suggested-keywords-button']/div[@class='list-container']//li")
    public List<WebElement> searchSuggestionList_values;

    //Pooja
    @FindBy(xpath = "//li[div/button[@class='pickup-icon-container hover-button-enabled']]")
    public List<WebElement> productsWithBopis;

    //Pooja
    @FindBy(xpath = ".//li[contains(@class,'item-container')][//button[@class='pickup-button-container']/span[text()='Pick up in store']]")
    public List<WebElement> getProductsWithBopisBtn;

    //Pooja
    @FindBy(xpath = "//div[@class='plp-section-container search-result-container viewport-container search-from-navigation']//p/a[@class='breadcrum-last-item']")
    public List<WebElement> ContentLandingPageBrdcrumb;

    @FindBy(xpath = "//button[@class='pickup-icon-container hover-button-enabled']/parent::div")
    public List<WebElement> itemContainerWithBopisICons;


}

