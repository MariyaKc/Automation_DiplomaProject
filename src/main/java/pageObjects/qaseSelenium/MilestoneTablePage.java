package pageObjects.qaseSelenium;

import org.openqa.selenium.By;
import pageObjects.baseObjects.BasePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilestoneTablePage extends BasePage {
    private final By headers = By.xpath("//table//th");
    private By getTableRow(String index) {
        return By.xpath("//table//tbody//tr[" + index + "]");
    }

    public MilestoneTablePage clickTableColumn(String columnName) {
        findElement(headers).findElement(By.xpath("//th[text()='" + columnName + "']")).click();
        return this;
    }

    public MilestoneTablePage doubleClickTableColumn(String columnName) {
        actions.doubleClick(findElement(headers).findElement(By.xpath("//th[text()='" + columnName + "']"))).build().perform();
        return this;
    }

    public List<List<String>> getTableRowsData() {
        Integer rowCount = findElements(getTableRow("..")).size();
        List<List<String>> data = new ArrayList<>();
        for (int row = 0; row < rowCount; row++) {
            List<String> columData = new ArrayList<>();
            Integer columnCount = findElement(getTableRow(Integer.toString(row + 1))).findElements(By.xpath(".//td")).size();
            for (int column = 0; column < columnCount; column++) {
                columData.add(findElement(getTableRow(Integer.toString(row + 1))).findElement(By.xpath(".//td[" + (column + 1) + "]")).getText());
            }
            data.add(columData);
        }
        return data;
    }

    public Map<String, List<String>> getTableData() {
        Map<String, List<String>> mapData = new HashMap<>();
        Integer headerCount = findElements(headers).size();
        List<List<String>> tableData = getTableRowsData();
        for (int header = 0; header < headerCount; header++) {
            List<String> columnData = new ArrayList<>();
            for (List<String> data : tableData) {
                columnData.add(data.get(header));
            }
            mapData.put(findElements(headers).get(header).getText(), columnData);
        }
        return mapData;
    }
}
