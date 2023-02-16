package com.quantice.usermanagementservice.repository;

import com.quantice.usermanagementservice.model.auth.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Integer> {

}
