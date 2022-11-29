package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageObjects.baseObjects.BasePage;

public class DialogWindowPage extends BasePage {
    private final By dialogWindow = By.className("ReactModal__Content");
    private final By title = By.xpath("//*[@id='modals']//h1");
    private final By notNowBtn = By.className("K_P4Gu");
    private final By upgradeBtn = By.xpath("//a[contains(@class,'tscvgR')]");

    public DialogWindowPage() {
        verifyDialogPage();
    }

    public DialogWindowPage verifyDialogPage() {
        Assert.assertEquals(getText(title), "Upgrade your subscription");
        return this;
    }

    public DialogWindowPage clickNotNow(){
        click(notNowBtn);
        return this;
    }

    public DialogWindowPage clickUpgrade(){
        click(upgradeBtn);
        return this;
    }

    public DialogWindowPage verifyThenWindowIsClosed(){
        elementNotExist(dialogWindow);
        return this;
    }


}
