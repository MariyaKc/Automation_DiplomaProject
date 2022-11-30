package cucumberSteps;

import entity.Milestone;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javaFaker.MilestoneFaker;
import org.testng.Assert;
import pageObjects.baseObjects.BaseTest;
import pageObjects.qaseSelenium.MilestonePage;
import pageObjects.qaseSelenium.MilestoneTablePage;
import pageObjects.qaseSelenium.navigation.NavigationTab;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MilestoneSteps extends BaseTest {

    @When("I go to Milestones Page")
    public void openMilestones() {
        get(NavigationTab.class).clickNavigationItem("Milestones");
    }

    @Then("I create {int} new milestone and verify, then milestones was created")
    public void createMilestone(int count){
        for (int i = 1; i <= count; i++) {
            MilestoneFaker milestoneFaker = new MilestoneFaker();
            Milestone milestone = milestoneFaker.getMilestone();
            get(MilestonePage.class)
                    .create()
                    .enterData(milestone)
                    .saveMilestone().verifyFlashMessage();
        }
    }

    @Then("I sort by {string} descending and check")
    public void sortByTitle1(String name){
        Map<String, List<String>> allTableData = get(MilestoneTablePage.class).clickTableColumn(name).getTableData();
        List<String> actualData = allTableData.get(name).stream().collect(Collectors.toList());
        System.out.println("I'm actual descending data :: " + actualData);
        List<String> expectedData = actualData.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("I'm expected descending data :: " + expectedData);
        Assert.assertEquals(actualData, expectedData);
    }

    @And("I sort by {string} ascending and check")
    public void sortByTitle2(String name){
        Map<String, List<String>> allTableData = get(MilestoneTablePage.class).clickTableColumn(name).getTableData();
        List<String> actualData = allTableData.get(name).stream().collect(Collectors.toList());
        System.out.println("I'm actual ascending data :: " + actualData);
        List<String> expectedData = actualData.stream().collect(Collectors.toList());
        System.out.println("I'm expected ascending data :: " + expectedData);
        Assert.assertEquals(actualData, expectedData);
    }

    @When("I delete Milestones by count {int}")
    public void deleteMilestone(int count){
        get(MilestonePage.class).deleteForCount(count);
    }

}
