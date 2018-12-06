package ui.pages.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by venkat on 12/03/2018.
 */

public class DirectoryPageRepo extends HeaderMenuRepo {

    @FindBy(css = "div.title-wrapper>h3")
    public WebElement directoryTxtLbl;

    @FindBy(css = "span.count")
    public WebElement addressCountTxtLbl;

    @FindBy(css=".segment-button[ng-reflect-value='ADDRESSES']")
    public WebElement addressesTabLnk;

    @FindBy(css="button.create-button")
    public WebElement createAddressBtn;

    @FindBy(css="input.searchbar-input")
    public WebElement searchAddressTextBox;

    public WebElement addressNameByPosition(int position){
        return getDriver().findElement(By.xpath("(//div[contains(@class,'fd-main-title')])["+position+"]"));
    }

    public WebElement addressDetailsByPosition(int position){
        return getDriver().findElement(By.xpath("(//div[contains(@class,'fd-sub-title')])["+position+"]"));
    }

    public WebElement favoriteIconClearByPosition(int position){
        return getDriver().findElement(By.xpath("(//i[contains(@class,'fd-item-fav-icon')])["+position+"]"));
    }

    public WebElement favoriteIconFilledByPosition(int position){
        return getDriver().findElement(By.xpath("(//i[contains(@class,'fd-item-fav-icon') and contains(@class,'favorited')])["+position+"]"));
    }

    public WebElement addressActionBtnByPosition(int position){
        return getDriver().findElement(By.xpath("(//div[contains(@class,'fd-actions-icon-wrapper')])["+position+"]"));
    }

}
