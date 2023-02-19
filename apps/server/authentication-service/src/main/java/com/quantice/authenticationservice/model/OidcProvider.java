package com.quantice.authenticationservice.model;

import com.quantice.authenticationservice.model.enums.OidcProviderType;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "oidc_provider")
public class OidcProvider {

    @Id
    @Column(name = "id")
    private int oidcProviderId;

    @Column(name = "oidc_provider_name", nullable = false)
    private String oidcProviderName;

    @Column(name = "oidc_provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OidcProviderType oidcProviderType;

}
