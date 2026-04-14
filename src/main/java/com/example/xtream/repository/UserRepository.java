package com.example.xtream.repository;

import com.example.xtream.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    @Query("select 1 from User u where u.userName = :username AND u.hashedPassword = :hashedPassword ")
    Optional<Long> isUserExist(String username, String hashedPassword);

}
