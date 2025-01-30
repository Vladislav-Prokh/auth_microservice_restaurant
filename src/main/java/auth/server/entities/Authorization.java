package auth.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "`authorization`")
public class Authorization {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(name = "registeredclientid", nullable = false)
    private String registeredClientId;

    @Column(name = "principalname", nullable = false)
    private String principalName;

    @Column(name = "authorizationgranttype", nullable = false)
    private String authorizationGrantType;

    @Column(name = "authorizedscopes", length = 1000)
    private String authorizedScopes;

    @Column(length = 4000)
    private String attributes;

    @Column(length = 500)
    private String state;

    @Column(name = "authorizationcodevalue", length = 4000)
    private String authorizationCodeValue;

    @Column(name = "authorizationcodeissuedat")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorizationcodeexpiresat")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "authorizationcodemetadata", length = 2000)
    private String authorizationCodeMetadata;

    @Column(name = "accesstokenvalue", length = 10000)
    private String accessTokenValue;

    @Column(name = "accesstokenissuedat")
    private Instant accessTokenIssuedAt;

    @Column(name = "accesstokenexpiresat")
    private Instant accessTokenExpiresAt;

    @Column(name = "accesstokenmetadata", length = 2000)
    private String accessTokenMetadata;

    @Column(name = "accesstokentype")
    private String accessTokenType;

    @Column(name = "accesstokenscopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "refreshtokenvalue", length = 4000)
    private String refreshTokenValue;

    @Column(name = "refreshtokenissuedat")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refreshtokenexpiresat")
    private Instant refreshTokenExpiresAt;

    @Column(name = "refreshtokenmetadata", length = 2000)
    private String refreshTokenMetadata;

    @Column(name = "oidcidtokenvalue", length = 4000)
    private String oidcIdTokenValue;

    @Column(name = "oidcidtokenissuedat")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidcidtokenexpiresat")
    private Instant oidcIdTokenExpiresAt;

    @Column(name = "oidcidtokenmetadata", length = 2000)
    private String oidcIdTokenMetadata;

    @Column(name = "oidcidtokenclaims", length = 2000)
    private String oidcIdTokenClaims;

    @Column(name = "usercodevalue", length = 4000)
    private String userCodeValue;

    @Column(name = "usercodeissuedat")
    private Instant userCodeIssuedAt;

    @Column(name = "usercodeexpiresat")
    private Instant userCodeExpiresAt;

    @Column(name = "usercodemetadata", length = 2000)
    private String userCodeMetadata;

    @Column(name = "devicecodevalue", length = 4000)
    private String deviceCodeValue;

    @Column(name = "devicecodeissuedat")
    private Instant deviceCodeIssuedAt;

    @Column(name = "devicecodeexpiresat")
    private Instant deviceCodeExpiresAt;

    @Column(name = "devicecodemetadata", length = 2000)
    private String deviceCodeMetadata;
}
