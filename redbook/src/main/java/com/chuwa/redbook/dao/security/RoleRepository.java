package com.chuwa.redbook.dao.security;

import com.chuwa.redbook.entity.security.Role;
import com.chuwa.redbook.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<User, Long> {
    Optional<Role> findByName(String name);
}
