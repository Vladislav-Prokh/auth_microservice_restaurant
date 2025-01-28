package auth.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "`authorizationconsent`")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
public class AuthorizationConsent {
    @Id
    @Column(name = "registeredclientid")
    private String registeredClientId;
    @Id
    @Column(name = "principalname")
    private String principalName;
    @Column(length = 1000)
    private String authorities;

    @Getter
    @Setter
    @Data
    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;
        private String principalName;
    }
}