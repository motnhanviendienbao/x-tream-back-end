package com.example.xtream;

import com.example.xtream.api.repositories.TokenRepository;
import com.example.xtream.api.repositories.UserAuthRepository;
import com.example.xtream.api.models.Auth.Token;
import com.example.xtream.api.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TokenBasedUserDetailsService implements UserDetailsService {

    @Autowired
    private  TokenRepository tokenRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String tokenId) throws UsernameNotFoundException {
        long id;
        try {
            id = Long.parseLong(tokenId);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid token id");
        }

        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Token not found"));
        if (token.isExpired()) {
            throw new UsernameNotFoundException("Token expired");
        }

        Long userId = token.getAdminId().orElseGet(() -> token.getCustomerId().orElse(null));
        if (userId == null) {
            throw new UsernameNotFoundException("Token has no owner");
        }

        User user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastAccess = user.getLastAccess();
        if (lastAccess != null && ChronoUnit.MINUTES.between(lastAccess, now) >= 5) {
            throw new UsernameNotFoundException("Session expired");
        }
        user.setLastAccess(now);
        userAuthRepository.save(user);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new com.example.xtream.UserDetails(
                token,
                authorities,
                user.getId(),
                user.getHashedPassword(),
                user.getUserName(),
                user.getIsResetHashedPass(),
                user.getFailedCount() == null ? 0 : user.getFailedCount(),
                user.getIsLocked(),
                user.getRole().name(),
                user.getLastAccess()
        );
    }
}
