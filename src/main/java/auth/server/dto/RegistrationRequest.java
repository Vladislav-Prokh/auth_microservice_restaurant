package auth.server.dto;

import auth.server.annotations.StrongPassword;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String surname;
    @Email
    private String email;
    @StrongPassword
    private String password;
    private String locale;
}
