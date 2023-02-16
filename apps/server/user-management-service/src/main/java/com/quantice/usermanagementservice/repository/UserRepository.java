package com.quantice.usermanagementservice.repository;

import com.quantice.usermanagementservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
