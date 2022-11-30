package WEB_Tests;

import WEB_Tests.Steps.LoginSteps;
import WEB_Tests.Steps.ProjectSteps;
import entity.Milestone;
import javaFaker.MilestoneFaker;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.MilestonePage;
import pageObjects.qaseSelenium.MilestoneTablePage;
import pageObjects.qaseSelenium.ProjectPage;
import pageObjects.qaseSelenium.navigation.NavigationTab;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Milestone_Test extends BaseTest {

    @BeforeTest
    public void preconditions() {
        get(LoginSteps.class).login();
        get(ProjectSteps.class).createProject("MilestoneProject","MP");
    }

    @Test(description = "Test for create 3 new milestones with all fields (using Java Faker) and delete cases by count")
    public void createMilestones() {

        get(NavigationTab.class).clickNavigationItem("Milestones");

        for (int i = 1; i <= 3; i++) {
            MilestoneFaker milestoneFaker = new MilestoneFaker();
            Milestone milestone = milestoneFaker.getMilestone();
            get(MilestonePage.class)
                    .create()
                    .enterData(milestone)
                    .saveMilestone().verifyFlashMessage();
        }
    }

    @Test(description = "Check sort by Title descending", dependsOnMethods = "createMilestones")
    public void test1() {
        Map<String, List<String>> allTableData = get(MilestoneTablePage.class).clickTableColumn("Title").getTableData();
        List<String> actualData = allTableData.get("Title").stream().collect(Collectors.toList());
        System.out.println("I'm actual descending data :: " + actualData);
        List<String> expectedData = actualData.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("I'm expected descending data :: " + expectedData);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test(description = "Check sort by Title ascending", dependsOnMethods = "test1")
    public void test2() {
        Map<String, List<String>> allTableData = get(MilestoneTablePage.class).clickTableColumn("Title").getTableData();
        List<String> actualData = allTableData.get("Title").stream().collect(Collectors.toList());
        System.out.println("I'm actual ascending data :: " + actualData);
        List<String> expectedData = actualData.stream().collect(Collectors.toList());
        System.out.println("I'm expected ascending data :: " + expectedData);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test(description = "Check sort by Date descending", dependsOnMethods = "test2")
    public void test3() {
        Map<String, List<String>> allTableData = get(MilestoneTablePage.class).clickTableColumn("Due date").getTableData();
        List<String> actualData = allTableData.get("Due date").stream().collect(Collectors.toList());
        System.out.println("I'm actual descending data :: " + actualData);

        List<String> expectedData = actualData.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("I'm expected descending data :: " + expectedData);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test(description = "Check sort by Date ascending", dependsOnMethods = "test3")
    public void test4() {
        Map<String, List<String>> allTableData = get(MilestoneTablePage.class).clickTableColumn("Due date").getTableData();
        List<String> actualData = allTableData.get("Due date").stream().collect(Collectors.toList());
        System.out.println("I'm actual ascending data :: " + actualData);

        List<String> expectedData = actualData.stream().collect(Collectors.toList());
        System.out.println("I'm expected ascending data :: " + expectedData);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test(description = "Delete Milestone By count", dependsOnMethods = "test4")
    public void test5(){
        get(MilestonePage.class).deleteForCount(3);
    }

    @AfterClass(alwaysRun = true)
    public void post(){
        get(NavigationTab.class).clickNavigationItem("Projects");
        get(ProjectPage.class).deleteForName("MilestoneProject");
    }

}
