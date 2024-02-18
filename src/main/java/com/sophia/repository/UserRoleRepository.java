package com.sophia.repository;

import com.sophia.entity.concrates.user.UserRole;
import com.sophia.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRoleName(String roleName);

    Optional<Object> findByRoleType(RoleType roleType);
}
