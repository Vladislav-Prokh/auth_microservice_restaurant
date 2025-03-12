package auth.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmailRequest {
    private String oldEmail;
    private String newEmail;
}
