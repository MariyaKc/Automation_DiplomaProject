package WEB_Tests;

import WEB_Tests.Steps.LoginSteps;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.BillingPage;
import pageObjects.qaseSelenium.DialogWindowPage;
import pageObjects.qaseSelenium.navigation.NavigationTab;


public class DialogWindow_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();
    }

    @Test(description = "Test for choice dialog window options 'Not Now'")
    public void dialogTest1(){
        get(NavigationTab.class).clickNavigationItem("Dashboards");
        get(DialogWindowPage.class).clickNotNow().verifyThenWindowIsClosed();

    }

    @Test(description = "Test for choice dialog window options 'Upgrade'")
    public void dialogTest2(){
        get(NavigationTab.class).clickNavigationItem("Dashboards");
        get(DialogWindowPage.class).clickUpgrade();
        get(BillingPage.class).verifyBuildingPage();
    }

}
