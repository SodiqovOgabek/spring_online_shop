package com.example.online_shop_spring_boot.repository;

import com.example.online_shop_spring_boot.domains.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Long> {
    @Query("select p from AuthUser p where p.username = ?1")
    Optional<AuthUser> findByUsername(String username);


}
