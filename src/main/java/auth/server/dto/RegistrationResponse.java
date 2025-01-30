package auth.server.dto;

import auth.server.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private String name;
    private String surname;
    private String email;
    private Role role;
}
