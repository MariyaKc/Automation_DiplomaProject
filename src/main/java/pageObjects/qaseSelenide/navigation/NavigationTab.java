package pageObjects.qaseSelenide.navigation;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.baseObjects.SelenideBasePage;

import static com.codeborne.selenide.Selenide.$;
@Log4j
public class NavigationTab extends SelenideBasePage {
    private SelenideElement getNavigationLink(String linkText) {
        return $(By.partialLinkText(linkText));
    }

    public NavigationTab clickNavigationItem(String linkText) {
        log.debug("Click navigation item");
        getNavigationLink(linkText).click();
        return this;
    }
}
