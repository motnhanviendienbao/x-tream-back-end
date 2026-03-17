package com.example.xtream.api.repositories;

import com.example.xtream.api.models.Auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Override
    Optional<Token> findById(Long aLong);
}
