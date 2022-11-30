package pageObjects.qaseSelenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entity.Defect;
import org.openqa.selenium.By;
import pageObjects.baseObjects.SelenideBasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DefectPage extends SelenideBasePage {

    private final SelenideElement inputDefectTitle = $("#title");
    private final SelenideElement inputActualResult = $("#actual_result");
    private final SelenideElement dropDownSeverity = $("#react-select-2-input");
    private final SelenideElement click = $(".k6Rjac");
    private final SelenideElement createBtn = $(".save-button");
    private final SelenideElement flashMessage = $(".OL6rtE");
    private final SelenideElement confirmDeleteBtn = $(".DRnS3P");

    private SelenideElement getEllipseDefectForNameBtn(String name) {
        return $x("//a[contains(text(),'" + name + "')]/ancestor::tr//a[contains(@class,'btn-dropdown')]");
    }

    private SelenideElement deleteDefectForNameBtn(String name) {
        return $x("//a[contains(text(),'"+ name +"')]/ancestor::tr//div[@class='dropdown']//a[@class='text-danger']");
    }


    public DefectPage open(String projectCode) {
        Selenide.open("https://app.qase.io/defect/" + projectCode + "/create");
        return this;
    }

    public DefectPage enterDefectTitle (String title) {
        enter(this.inputDefectTitle, title);
        return this;
    }

    public DefectPage enterActualResult (String result) {
        enter(this.inputActualResult, result);
        return this;
    }

    public DefectPage enterSeverity (String severity) {
        enter(this.dropDownSeverity, severity);
        click(click);
        return this;
    }

    public DefectPage enterData(Defect defect) {
        enterDefectTitle(defect.getDefectTitle());
        enterActualResult(defect.getActualResult());
        enterSeverity(defect.getSeverity());
        return this;
    }

    public DefectPage clickCreate() {
        click(createBtn);
        return this;
    }

    public DefectPage verifyFlashMessage() {
        verifyAlert(flashMessage, "Defect was successfully created.");
        return this;
    }

    public DefectPage delete(String name) {
        click(getEllipseDefectForNameBtn(name));
        click(deleteDefectForNameBtn(name));
        click(confirmDeleteBtn);
        return this;
    }

}
