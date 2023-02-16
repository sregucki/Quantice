package com.quantice.usermanagementservice.repository;

import com.quantice.usermanagementservice.model.user.Role;
import com.quantice.usermanagementservice.model.utils.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByRoleType(RoleType roleType);
}
