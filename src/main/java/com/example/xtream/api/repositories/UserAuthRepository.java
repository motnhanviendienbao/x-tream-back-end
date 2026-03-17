package com.example.xtream.api.repositories;

import com.example.xtream.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserAuthRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByUserName(String userName);

}
