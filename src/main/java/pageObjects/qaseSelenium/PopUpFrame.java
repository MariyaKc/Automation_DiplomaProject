package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class PopUpFrame extends BasePage {
    private final By chatBtn = By.cssSelector("[aria-label='Chat']");
    private final By chatPopUp = By.id("intercom-container-body");
    private final By gettingStartArticle =By.xpath("//li[@aria-label='Getting Started']");
    private final By titleGettingStart = By.className("eltcq8z0");
    private final By returnToChatBtn = By.xpath("//div[@class='intercom-jd3v4z eg87d392']");
    private final By closeChatBtn = By.xpath("//div[contains(@class,'e1jjo5ve1')]");
    private final By reactionTitle =By.id("reaction-prompt");
    private final By imgBtn = By.className("X8BNLp");
    private final By imgPopUp = By.className("WHRMzV");
    private final By signOutBtn = By.xpath("//span[contains(text(),'Sign out')]");

    public PopUpFrame clickChat() {
        click(chatBtn);
        return this;
    }

    public PopUpFrame verifyThenChatEnabled() {
        driver.switchTo().frame("intercom-messenger-frame");
        waitVisibilityOfElement(chatPopUp);
        return this;
    }

    public PopUpFrame clickGettingStartArticle() {
        scrollElementIntoView(gettingStartArticle);
        click(gettingStartArticle);
        return this;
    }

    public PopUpFrame verifyGettingStartTitle() {
        waitVisibilityOfElement(titleGettingStart);
        Assert.assertEquals(getText(titleGettingStart),"Getting Started");
        scrollElementIntoView(reactionTitle);
        return this;
    }

    public PopUpFrame clickReturnToChat() {
        click(returnToChatBtn);
        return this;
    }

    public PopUpFrame clickCloseChat() {
        clickWithJS(closeChatBtn);
        return this;
    }

    public PopUpFrame clickImg() {
        click(imgBtn);
        return this;
    }

    public PopUpFrame verifyThenImgPopUpEnabled() {
        waitVisibilityOfElement(imgPopUp);
        return this;
    }

    public PopUpFrame clickSignOut() {
        click(signOutBtn);
        return this;
    }

    public PopUpFrame verifyThenImgPopUpClosed(){
        elementNotExist(imgPopUp);
        return this;
    }
}
