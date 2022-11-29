package entity;

import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "SignUpBuilder", setterPrefix = "with", buildMethodName = "create")
@Data
public class SignUp {
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean checkbox;

    public static class SignUpBuilder {
        public SignUpBuilder() {
        }
    }
}
