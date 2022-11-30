package entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "MilestoneBuilder", setterPrefix = "with", buildMethodName = "create")
public class Milestone {
    String milestoneName;
    String description;
    String status;
    String dueDate;

    public static class MilestoneBuilder {
        public MilestoneBuilder() {
        }
    }
}
