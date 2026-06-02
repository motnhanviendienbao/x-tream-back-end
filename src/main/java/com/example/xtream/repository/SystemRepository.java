package com.example.xtream.repository;

import com.example.xtream.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);

    @Query("""
        SELECT DISTINCT
        ""
        FROM User user
        WHERE user.id = :userId
    """)
    List<String> findAuthoritiesByUserId(int userId);
}
