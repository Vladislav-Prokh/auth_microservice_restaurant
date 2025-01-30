package auth.server.dto;

import auth.server.entities.Role;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
}
