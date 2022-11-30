package entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "DefectBuilder", setterPrefix = "with", buildMethodName = "create")
public class Defect {
    String defectTitle;
    String actualResult;
    String severity;


    public static class DefectBuilder {
        public DefectBuilder() {
        }
    }
}
