package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "CaseBuilder", setterPrefix = "with", buildMethodName = "create")
public class Case {
    @Expose
    String title;
    @Expose
    @SerializedName("status.title")
    String status;
    @Expose
    String description;
    @Expose
    String suite;
    @Expose
    @SerializedName("severity.title")
    String severity;
    @Expose
    @SerializedName("priority.title")
    String priority;
    @Expose
    @SerializedName("type.title")
    String type;
    String layer;
    @Expose
    String isFlaky;

    @Expose
    @SerializedName("milestone_id")
    String milestone;

    @Expose
    @SerializedName("behavior.title")
    String behavior;
    @Expose
    String automationStatus;

    @Expose
    String preconditions;
    @Expose
    String postconditions;

    public static class CaseBuilder {
        public CaseBuilder() {
        }
    }
}