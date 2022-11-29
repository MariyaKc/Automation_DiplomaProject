package entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "SuiteBuilder", setterPrefix = "with", buildMethodName = "create")
public class Suite {
    @Expose
    @SerializedName("title")
    String suiteName;
    @Expose
    String description;
    @Expose
    String preconditions;

    public static class SuiteBuilder {
        public SuiteBuilder() {
        }
    }
}



