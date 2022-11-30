package pageObjects.qaseSelenium.navigation;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.baseObjects.BasePage;

@Log4j
public class NavigationTab extends BasePage {

    private WebElement getNavigationLink(String linkText) {
        return findElement(By.partialLinkText(linkText));
    }

    public NavigationTab clickNavigationItem(String linkText) {
        log.debug("Click navigation item");
        retryingFindClick(getNavigationLink(linkText));
        return this;
    }

}
