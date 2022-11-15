package com.example.online_shop_spring_boot.repository;

import com.example.online_shop_spring_boot.domains.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthPermissionRepository extends JpaRepository<AuthRole, Long> {
}
