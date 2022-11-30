package pageObjects.qaseSelenide;

import com.codeborne.selenide.SelenideElement;
import entity.Project;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.baseObjects.SelenideBasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectPage extends SelenideBasePage {

    private final SelenideElement title = $(By.tagName("h1"));
    private final SelenideElement createNewProjectBtn = $("#createButton");
    private final SelenideElement inputProjectName = $("#project-name");
    private final SelenideElement inputProjectCode = $("#project-code");
    private final SelenideElement inputDescription = $("#description-area");
    private final SelenideElement createBtn = $("[type='submit']");
    private final SelenideElement deleteProjectBtn = $(".DRnS3P");

    private SelenideElement getRadiobutton(String value) {
        return $x("//*[contains(@type, 'radio') and contains(@value,'"+ value +"')]");
    }

    private SelenideElement getEllipseProjectForNameBtn(String name) {
        return $x("//a[contains(text(),'"+ name +"')]/ancestor::tr/td[@class='text-end']");
    }

    private SelenideElement deleteProjectForNameBtn(String name) {
        return $x("//a[contains(text(),'"+ name +"')]/ancestor::tr/td[@class='text-end']//button");
    }

    public ProjectPage createNewProject() {
        click(createNewProjectBtn);
        return this;
    }
    public ProjectPage enterProjectName(String name) {
        enter(this.inputProjectName, name);
        return this;
    }

    public ProjectPage enterProjectCode(String code) {
        enter(this.inputProjectCode, code);
        return this;
    }

    public ProjectPage enterDescription(String name) {
        enter(this.inputDescription, name);
        return this;
    }

    public ProjectPage clickRadiobutton(String value) {
        click(getRadiobutton(value));
        return this;
    }

    public ProjectPage enterData(Project project) {
        enterProjectName(project.getTitle());
        enterProjectCode(project.getCode());
        enterDescription(project.getDescription());
        clickRadiobutton(project.getAccess());
        return this;
    }
    public ProjectPage createProject() {
        click(createBtn);
        return this;
    }

    public ProjectPage deleteForName(String name) {
        click(getEllipseProjectForNameBtn(name));
        click(deleteProjectForNameBtn(name));
        click(deleteProjectBtn);
        return this;
    }

}
