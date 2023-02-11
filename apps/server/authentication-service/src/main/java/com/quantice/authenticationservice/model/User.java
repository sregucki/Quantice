package com.quantice.authenticationservice.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder
@Table("user_login_data")
public class User implements UserDetails {

    @Id
    @Column("user_id")
    private String userId;

    @Column("login_name")
    private String loginName;

    @Column("password_hash")
    private String passwordHash;

    @Builder.Default()
    private List<String> roles = new ArrayList<>(Arrays.asList("ROLE_USER"));

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return authorities;
    }

    @Override
    public String getPassword() {

        return passwordHash;
    }

    @Override
    public String getUsername() {

        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return false;
    }

    @Override
    public boolean isEnabled() {

        return false;
    }

}
