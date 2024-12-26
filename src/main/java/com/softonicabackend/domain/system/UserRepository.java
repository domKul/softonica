package com.softonicabackend.domain.system;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
