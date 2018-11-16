package ui.pages.repo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.UiBase;

import java.util.List;

/**
 * Created by user on 6/15/2016.
 */
public class OrderStatusRepo extends UiBase {


    @FindBy(css = ".breadcrum-link:nth-child(2)")
    public WebElement orderDetailsPage;

    @FindBy(css = ".main-section-container h1")
    public WebElement orderDetailHeader;

    @FindBy(xpath = "//*[@class='confirmation-title'][contains(.,'order details')]")
    public WebElement orderDetailsTitle;

    @FindBy(css = ".discount-applied")
    public WebElement discountAppliedElementsBox;

    @FindBy(xpath = ".//*[@id='WC_TCPMyPlace_Formlink_OrderHistoryTab'][@class='active']")
    public WebElement onlineOrderHistoryTabOpen;

    @FindBy(css = ".balance-total")//(xpath = ".//th[@class='order-or-reservation-total'][.='Total']")
    public WebElement totalText;

    @FindBy(css = ".title-description")//(css = ".table-orders-title")
    public List<WebElement> orderTitle;

    @FindBy(css = ".order-date")//(css = ".order-date-container")
    public WebElement orderDate;

    @FindBy(css = ".status")//(css = ".status-container")
    public WebElement orderStatus;

    @FindBy(css = ".balance-total strong")//(css = ".order-or-reservation-total.total-container")
    public WebElement orderTotalAmount;

    @FindBy(css = ".order-summary>li:nth-child(1) strong")
    public WebElement itemsTotal;

    @FindBy(css = ".order-number")
    public WebElement orderNoLink;

    @FindBy(xpath = ".//li[@class='balance-total']//strong")
    public WebElement estimatedTot;

    @FindBy(css = ".card-info")
    public WebElement cardDetails;

    @FindBy(css = ".product-title-in-table")
    public WebElement prodName;

    @FindBy(xpath = ".//div[@class=\"shipping-section\"]")
    public WebElement shippingDetailsSection;

    @FindBy(xpath = ".//div[@class=\"billing-section\"]")
    public WebElement billingDetailsSection;

    @FindBy(xpath = ".//div[@class=\"billing-section ledger-section\"]")
    public WebElement orderSummarySection;

    @FindBy(css = ".order-number")//.//a[@class=\"navigation-item-link navigation-item-link-selected\"]")
    public WebElement orderHighlighted;

    @FindBy(xpath = ".//li[@class=\"items-total\"][contains(.,\"Items\")]")
    public WebElement itemCount_Ledger;

    @FindBy(xpath = ".//li[@class=\"items-total\"][contains(.,\"Coupons & Promotions \")]")
    public WebElement couponAndPromotions_Ledger;

    @FindBy(xpath = " .//li[@class=\"tax-total\"]")
    public WebElement tax_Ledger;

    @FindBy(xpath = " .//li[@class=\"balance-total\"][contains(.,\"Total\")]")
    public WebElement total_ledger;

    @FindBy(css = ".container-description-view")
    public WebElement prodDetails;

    @FindBy(css = ".subtotal")
    public WebElement subTotal;

    @FindBy(xpath = ".//tr[@class=\"container-titles\"][contains(.,\"ProductOriginal PriceYou PaidQtySubtotal\")]")
    public WebElement productTableContent;

    @FindBy(name = "emailAddress")
    public WebElement emailIDField;

    @FindBy(name = "orderNumber")
    public WebElement orderIdField;

    @FindBy(xpath = ".//button[@class=\"button-primary button-submit\"]")
    public WebElement trackOrderbutton;

    @FindBy(xpath = ".//h1[@class=\"order-details-guest-title\"]")
    public WebElement orderDetailsHeading_Guest;

    @FindBy(xpath = ".//div[@class=\"ecom-information\"]//span[@class=\"name\"]")
    public WebElement firatName_Shipping;

    @FindBy(xpath = ".//div[@class=\"ecom-information\"]//span[@class=\"address\"]")
    public WebElement address_Shipping;

    @FindBy(xpath = ".//div[@class=\"billing-section\"]//span[@class=\"address\"]")
    public WebElement address_Billing;

    @FindBy(xpath = ".//div[@class=\"billing-section\"]//span[@class=\"name\"]")
    public WebElement firstName_Shipping;

    @FindBy(xpath = ".//div[@class=\"breadcrum-container\"]")
    public WebElement orderDetails_Breadcrumb;

    @FindBy(xpath = ".//ul[@class=\"my-account-navigation-container\"]")
    public WebElement orderDetails_LeftNav;

    @FindBy(css = ".store-info-data")
    public WebElement storeData;

    @FindBy(css = ".billing-section .address-container")
    public WebElement billingAddress;

    @FindBy(css = ".shipping-section .address-container")
    public WebElement shippingAddress;

    @FindBy(xpath = ".//p[@class=\"card-info\"]//span[contains(.,\"Ending in\")]")
    public WebElement cardNumberEnding;

    @FindBy(xpath = ".//div[@class=\"billing-section\"]//p[@class=\"address-details\"]")
    public WebElement addressDetailsBilling;

    @FindBy(xpath = ".//div[@class=\"guest-rewards-promo-container summary-message\"]")
    public WebElement mprBannerEspt;

    @FindBy(css = ".order-hour")
    public WebElement order_DetailsHour;

    @FindBy(css = ".person-name")
    public List<WebElement> pickupPersons;

    @FindBy(css = ".gift-cards-container")
    public WebElement gcInfo;

    @FindBy(css = ".button-modal-close")
    public WebElement closeModal;

    @FindBy(xpath = ".//div[@class=\"billing-section\"]//span[@class=\"name\"]")
    public WebElement billingSection_Name;

    @FindBy(xpath = ".//div[@class=\"billing-section\"]//span[@class=\"address\"]")
    public WebElement billingSection_Address;

    @FindBy(xpath = ".//div[@class=\"shipping-section\"]//span[@class=\"name\"]")
    public WebElement shippingSection_Name;

    @FindBy(xpath = ".//div[@class=\"shipping-section\"]//span[@class=\"address\"]")
    public WebElement shippingSection_Address;

    @FindBy(css = ".custom-loading-icon")
    public WebElement loadingIcon;

    @FindBy(css = ".header-status")
    public WebElement orderStatusDisplay;

    @FindBy(css = ".international-order a")
    public WebElement intOrder;

    @FindBy(css = ".gift-cards-container")
    public WebElement GiftCardPaymentSection;

    @FindBy(css = ".store-name")
    public WebElement store_Name;

    @FindBy(css = ".store-address")
    public WebElement store_Address;

    @FindBy(css = ".today-schedule")
    public WebElement store_Timings;

    @FindBy(css = ".store-phone-number")
    public WebElement store_PhoneNo;

    @FindBy(css = ".button-get-directions")
    public WebElement get_DirectionsLink;

    @FindBy(css = ".searchbox")
    public List<WebElement> searchBoxDisplay;

    //Taking as List because multiple item can be placed in Single Order
    @FindBy(css = ".text-upc")
    public List<WebElement> item_UPC;

    @FindBy(css = ".text-color")
    public List<WebElement> item_Color;

    @FindBy(css = ".text-size")
    public List<WebElement> item_Size;

    @FindBy(xpath = "//*[@class=\"container-titles\"]/*[contains(.,'Product')]")
    public WebElement containerName_Product;

    @FindBy(xpath = "//*[@class=\"container-titles\"]/*[contains(.,'Original Price')]")
    public WebElement containerName_OriginalPrice;

    @FindBy(xpath = "//*[@class=\"container-titles\"]/*[contains(.,'You Paid')]")
    public WebElement containerName_YouPaid;

    @FindBy(xpath = "//*[@class=\"container-titles\"]/*[contains(.,'Qty')]")
    public WebElement containerName_Qty;

    @FindBy(xpath = "//*[@class=\"container-titles\"]/*[contains(.,'Subtotal')]")
    public WebElement getContainerName_Subtotal;

    @FindBy(css = ".text-price.product-list-price.product-price-within-offer")
    public List<WebElement> itemOriginalPrice;

    @FindBy(css = ".text-price.product-offer-price")
    public List<WebElement> itemPricePaid;

    @FindBy(css = ".text-qty")
    public List<WebElement> quantityItem;

    @FindBy(css = ".subtotal")
    public List<WebElement> subtotalDisplayInContainer;

    @FindBy(css = ".hello-title")
    public WebElement hiFirstName;

    @FindBy(css = ".tracking-number-cta")
    public WebElement trackingButton;

    @FindBy(xpath = "//*[@class='card-type'][contains(.,'PayPal')]")
    public WebElement payPalText;
}
