package pageObjects.baseObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;


import java.util.Properties;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static propertyHelper.PropertyReader.getProperties;

@Log4j
public abstract class SelenideBasePage {

    protected Properties properties;

    protected SelenideBasePage() {
        properties = getProperties();
    }

    protected void verifyUri(String uri) {
        webdriver().shouldHave(urlContaining(uri));
        log.debug("I'm verify then page contains uri :: " + uri);
    }

    protected void enter(SelenideElement element, CharSequence... enterData) {
        log.debug("I'm verify then element is visible and enter :: " + enterData + ", by locator :: " + element);
        String os = System.getProperty("os.name");
        if (os.contains("Mac")) {
           element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
           element.sendKeys(Keys.chord(Keys.DELETE));
        } else {
           element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
           element.sendKeys(Keys.chord(Keys.DELETE));
        }
        element
                .sendKeys(enterData);
    }

    protected void click(SelenideElement element) {
        log.debug("I'm click by :: " + element);
        element.should(enabled).click();
    }

    protected void verifyText(SelenideElement element, String title) {
        log.debug("I'm verify, then " + element + "match text :: " + title);
        element.shouldBe(Condition.visible).shouldHave(Condition.matchText(title));
    }

    protected void verifyAlert(SelenideElement element, String alert) {
        if (element.exists()) {
            log.debug("I'm verify, then " + element + "is exists and match text :: " + alert);
            element.shouldBe(matchText(alert));
        }
    }
}
