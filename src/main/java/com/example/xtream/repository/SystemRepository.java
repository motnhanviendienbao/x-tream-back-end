package com.example.xtream.repository;

import com.example.xtream.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);

    @Query("""
        SELECT DISTINCT
            permission.action
        FROM User user
        JOIN Role role ON user.role = role.code
        JOIN RolePermission rolePermisson ON role.id = rolePermisson.role.id
        JOIN Permission permission ON rolePermisson.permission.id = permission.id
        WHERE user.id = :userId
    """)
    List<String> findAuthoritiesByUserId(Long userId);
}
