package com.example.xtream.api.repositories;

import com.example.xtream.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);


    @Query("select 1 from User u where u.userName = :username AND u.hashedPassword = :hashedPassword ")
    Optional<Long> isUserExist(String username, String hashedPassword);

}
