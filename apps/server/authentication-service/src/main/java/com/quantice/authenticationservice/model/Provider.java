package com.quantice.authenticationservice.model;

import com.quantice.authenticationservice.model.enums.ProviderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "auth_provider")
public class Provider {

    @Id
    @Column(name = "auth_provider_id")
    private int authProviderId;

    @Column(name = "auth_provider_name", nullable = false)
    private String authProviderName;

    @Column(name = "auth_provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

}
