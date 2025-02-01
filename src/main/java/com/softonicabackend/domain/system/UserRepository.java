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
    void deleteByUsername(String username);
    @Query(value = "SELECT COUNT(*)  FROM users u " +
            "WHERE u.username = :username AND u.id NOT IN (" +
            "SELECT DISTINCT a.userid FROM accounts a " +
            "UNION " +
            "SELECT DISTINCT p.userid FROM profiles p " +
            "UNION " +
            "SELECT DISTINCT c.userid FROM categories c " +
            "UNION " +
            "SELECT DISTINCT t.userid FROM transactions t " +
            "UNION " +
            "SELECT DISTINCT tg.userid FROM tags tg " +
            "UNION " +
            "SELECT DISTINCT pr.userid FROM projects pr " +
            "UNION " +
            "SELECT DISTINCT asgn.userid FROM assignments asgn " +
            "UNION " +
            "SELECT DISTINCT exp.userid FROM experiences exp " +
            "UNION " +
            "SELECT DISTINCT ach.userid FROM achievements ach " +
            "UNION " +
            "SELECT DISTINCT n.userid FROM notifications n " +
            "UNION " +
            "SELECT DISTINCT ctr.userid FROM contributors ctr " +
            "UNION " +
            "SELECT DISTINCT rl.userid FROM `releases` rl)",
            nativeQuery = true)
    int countUserLinks(@Param("username") String username);
}
