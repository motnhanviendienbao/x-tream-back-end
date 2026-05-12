package com.example.xtream.security.basicAuthen.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
@Getter
@Setter
public class BasicAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserDetailsService userDetailsService;
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.info("[NGOC TU SERVER]: Failed To Authenticate Since No Credentials Provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            logger.info("[NGOC TU SERVER]: Authentication Principal( As Username/Id ): " + authentication.getPrincipal());
            logger.info("[NGOC TU SERVER]: Raw Authentication Credentials( As Password/Value ): " + authentication.getCredentials());
            logger.info("[NGOC TU SERVER]: Raw Authentication PresentedPassword: " + authentication.getCredentials().toString());
            logger.info("[NGOC TU SERVER]: Raw Authentication Granted Authorities( As Permission):" + authentication.getAuthorities().size());
            logger.info("[NGOC TU SERVER]: Authentication Object Status This Time ( Processing ): " + authentication.isAuthenticated());
            logger.info("[NGOC TU SERVER]: Authentication Object: " + authentication.toString() );
            String presentedPassword = authentication.getCredentials().toString();
            // Compare Password Got Process Here:
            // If Valid: Continue To Next Steps
            // If Invalid: Throw Exception Since Stop Flow
            if (!NoOpPasswordEncoder.getInstance().matches(presentedPassword, userDetails.getPassword())) {
                logger.debug("[NGOC TU SERVER]: Failed To Authenticate Since Password Does Not Match Stored Value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    @Override
    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
//        prepareTimingAttackProtection();
        try {
            UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
//            mitigateAgainstTimingAttack(authentication);
            throw ex;
        }
        catch (InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
}
