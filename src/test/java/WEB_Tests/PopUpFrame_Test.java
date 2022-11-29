package WEB_Tests;


import WEB_Tests.Steps.LoginSteps;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.LoginPage;
import pageObjects.qaseSelenium.PopUpFrame;

public class PopUpFrame_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();
    }

    @Test(description = "Test for open chat pop up, choose option, scroll and close pop up.")
    public void popUpChatTest() {
        get(PopUpFrame.class)
                .clickChat()
                .verifyThenChatEnabled()
                .clickGettingStartArticle()
                .verifyGettingStartTitle()
                .clickReturnToChat()
                .clickCloseChat();
    }

    @Test(description = "Test for open img pop up and log out")
    public void popUpImgTest() {
        get(PopUpFrame.class)
                .clickImg().verifyThenImgPopUpEnabled().clickSignOut().verifyThenImgPopUpClosed();
        get(LoginPage.class).verifyLoginPage();
    }

}
