package pageObjects.baseObjects;

import driver.UIElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static driver.DriverManager.getDriver;
import static propertyHelper.PropertyReader.getProperties;

//Класс для инициализации объектов страниц
@Log4j
public abstract class BasePage {

    protected WebDriverWait wait;
    protected WebDriver driver;
    protected Actions actions;
    protected Properties properties;

    protected BasePage() {
        driver = getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        actions = new Actions(driver);
        properties = getProperties();
    }

    //гибкое ожидание
    protected FluentWait<WebDriver> fluentWait(long timeout, long pollingEvery) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingEvery))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void load() {
        log.debug("Open page :: " + properties.getProperty("url"));
        driver.get(properties.getProperty("url"));
    }

    protected void verifyUri(String uri) {
        log.debug("Verify page uri :: " + uri);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(uri));
    }

    protected String getPageUrl() {
        log.debug("Get page url");
        return driver.getCurrentUrl();
    }

    protected void enter(WebElement webElement, String enterData) {
        log.debug("I'm enter :: " + enterData + ", by web element :: " + webElement);
        webElement.clear();
        webElement.sendKeys(enterData);
    }

    protected void enter(By locator, CharSequence... enterData) {
        String os = System.getProperty("os.name");
        if (os.contains("Mac")) {
            findElement(locator).sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
        } else {
            findElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        }
        findElement(locator).sendKeys(enterData);
        log.debug("I'm enter :: " + String.valueOf(enterData) + ", by locator :: " + locator);
    }

    protected void click(By locator) {
        log.debug("I'm click by :: " + locator);
        verifyElementClickable(locator);
        findElement(locator).click();
    }

    protected void clickWithJS(By locator){
        log.debug("I'm click with JS by :: " + locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", findElement(locator));
    }

    protected void clickWithJS(WebElement element){
        log.debug("I'm click with JS by :: " + element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollElementIntoView(By by){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findElement(by));
       // actions.scrollToElement(driver.findElement(by)).build().perform();
        log.debug("I'm scroll into view element :: " + by);
        waitVisibilityOfElement(by);
    }


    protected boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                log.debug("I'm click by :: " + by);
                findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    protected void click(WebElement webElement) {
        log.debug("I'm click by :: " + webElement);
        new UIElement(driver, wait, webElement).click();
    }

    protected void uploadFile(String fileName, By element) {
        log.debug("Upload file with name :: " +fileName+ "to :: " + element );
        File file = new File(System.getProperty("user.dir") + "/files/"+fileName);
        findElement(element).sendKeys(file.getAbsolutePath());
        waitUntil(2);
    }

    protected File getLastDownloadFileInDirectory() {
        File directory = new File(System.getProperty("user.dir")+ "/files/downloads/");
        File[] files = directory.listFiles(File::isFile);
        if (files == null || files.length == 0) {
            return null;
        }

        File lastDownloadFile = files[0];
        for (int i = 0; i < files.length; i++) {
            if (lastDownloadFile.lastModified() < files[i].lastModified()) {
                lastDownloadFile = files[i];
            }
        }
        log.debug("I'm download :: " + lastDownloadFile.getName());
        return lastDownloadFile;
    }

    protected void clickAll(By... locators) {
        for (By locator : locators) {
            log.debug("I'm click by :: " + locator);
            List<WebElement> buttons = findElements(locator);
            for (WebElement button : buttons) {
                button.click();
            }
        }
    }

    protected void clickAll(WebElement... elements) {
        for (WebElement element : elements) {
            log.debug("I'm click by :: " + element);
            List<WebElement> buttons = new ArrayList<>();
            buttons.add(element);
            for (WebElement button : buttons) {
                button.click();
            }
        }
    }

    protected void select(By locator, Integer index) {
        log.debug("Select by locator => " + locator + " with index => " + index);
        Select select = new Select(findElement(locator));
        select.selectByIndex(index);
    }

    protected void select(By locator, String value) {
        log.debug("Select by locator => " + locator + " with value => " + value);
        Select select = new Select(findElement(locator));
        select.selectByVisibleText(value);
    }
    protected void select(WebElement element, Integer index) {
        log.debug("Select by locator => " + element + " with index => " + index);
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    protected void select(WebElement element, String value) {
        log.debug("Select by locator => " + element + " with value => " + value);
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    protected Integer countFoundElements(By element) {
        log.debug("Count elements :: " + element);
        int count = findElements(element).size();
        return count;
    }

    protected String getText(By locator) {
        log.debug("I'm get text by  :: " + locator);
        return findElement(locator).getText();
    }

    protected String getText(WebElement webElement) {
        log.debug("I'm get text by  :: " + webElement);
        return webElement.getText();
    }

    protected List<String> getTexts(By locator) {
        log.debug("I'm get texts by  :: " + locator);
        return findElements(locator).stream().map(webElement -> webElement.getText()).collect(Collectors.toList());
    }
    protected List<String> getTexts(List<WebElement> webElements) {
        log.debug("I'm get texts by  :: " + webElements);
        return webElements.stream().map(webElement -> webElement.getText()).collect(Collectors.toList());
    }

    protected String getElementAttribute(By by, String attribute) { //получение атрибута элемента
        log.debug("Get element => " + by + ", attribute :: " + attribute);
        return findElement(by).getAttribute(attribute);
    }

    protected List<String> getElementsAttribute(By by, String attribute) {
        log.debug("Get element => " + by + ", attribute :: " + attribute);
        return findElements(by).stream().map(webElement -> webElement.getAttribute(attribute)).collect(Collectors.toList());
    }

    public Boolean elementExist(By by) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            log.debug("Wait element exist :: "+ by );
            if (findElements(by).size() == 0) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
                return false;
            }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return true;
    }

    public Boolean elementNotExist(By by) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        for (int counter = 1; counter < 20; counter++) {
            log.debug("Wait element not exist count = " + counter);
            if (findElements(by).size() == 0) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                return true;
            }
            waitUntil(1);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return false;
    }

    protected void waitVisibilityOfElement(By locator) {
        log.debug("wait visibility of element => " + locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean waitVisibilityOfElements(By... locators) {
        for (By locator : locators) {
            log.debug("wait visibility of element => " + locator);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        return true;
    }

    protected void verifyElementTextToBe(By locator, String text) {
        log.debug("verify element text to be => " + locator);
        wait.until(ExpectedConditions.textToBe(locator, text));
    }

    protected void verifyElementClickable(By locator) {
        log.debug("verify element clickable => " + locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void waitUntil(Integer timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

