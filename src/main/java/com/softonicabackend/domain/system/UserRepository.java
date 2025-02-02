package com.softonicabackend.domain.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.username = :login OR u.email = :login")
    Optional<UserEntity> findByUsernameOrEmail(@Param("login") String login);
    Optional<UserEntity> findByUsername(String username);
}
