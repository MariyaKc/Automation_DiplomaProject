package entity;
import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

    @Data
    @Builder(builderClassName = "ProjectBuilder", setterPrefix = "with", buildMethodName = "create")
    public class Project {
        @Expose
        private String title;
        @Expose
        private String code;
        @Expose
        private String description;
        private String access;

        public static class ProjectBuilder {
            public ProjectBuilder() {
            }
        }
}
