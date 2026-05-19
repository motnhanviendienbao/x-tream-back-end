package com.example.xtream.security.basicAuthen.service;

import com.example.xtream.constant.ErrorMessages;
import com.example.xtream.security.basicAuthen.cache.BasicUserCache;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Set;

/**
 * Inherit all resource from DaoAuthenticationProvider class
 * include:
 * filed and method
 * then
 * custom some method based on demand of logic
 * <p>
 *
 * NOTICE:
 * In high level at AbstractUserDetailsAuthenticationProvider.
 * Where the main authentication method run (authenticate method),
 * Having an impressive mechanic fallback focus on redirect flow logic:
 * In authenticate method when your additionalAuthenticationChecks throw any AuthenticationException,
 * Main: if after get object from cache and do additionalAuthenticationChecks or preAuthenticationChecks make any AuthenticationException
 * Flow will get again directly by loadUserByUsername again.
 * In brief, Got from cache ->
 * do additionalAuthenticationChecks,preAuthenticationChecks ->
 * any AuthenticationException -> retry got latest user detail object directly from loadUserByUsername.
 * <p>
 *
 * NOTICE:
 * In ProviderManager take role like {@link AuthenticationManager }
 * its task is foreach through all provider to run authenticate if condition checks by supports() is true
 * Main: have fallback mechanic by setter parent Authentication
 * will run authenticate if got setting and not any provider matching supports()
 * <p>
 *
 * NOTICE:
 * SecurityContextRepository in basic authentication actually use request attribute to store context
 * <p>
 *
 * NOTICE:
 * SecurityContextHolderStrategy:
 * It defines HOW and WHERE the SecurityContext is stored and accessed.
 * <p>
 *
 * SecurityContextHolder
 *     → HAS-A → SecurityContextHolderStrategy ( default is: ThreadLocalSecurityContextHolderStrategy())
 *         → HAS-A → SecurityContext (stored internally, e.g. ThreadLocal)
 *             → HAS-A → Authentication
 * <p>
 * SecurityContextHolder = chooses & delegates to strategy
 * SecurityContextHolderStrategy = where the SecurityContext is actually stored and mutated
 * One thread can only have ONE SecurityContext at a time.
 */
//@Service
@RequiredArgsConstructor
@Getter
@Setter
@Lazy
public final class BasicAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doAfterPropertiesSet() {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    @Override
//    @Autowired
    public void setUserCache(UserCache userCache) {
        super.setUserCache(userCache);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.info("Failed To Authenticate Since No Credentials Provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            logger.info("Authentication Principal( As Username/Id ): " + authentication.getPrincipal());
            logger.info("Raw Authentication Credentials( As Password/Value ): " + authentication.getCredentials());
            logger.info("Raw Authentication PresentedPassword: " + authentication.getCredentials().toString());
            logger.info("Raw Authentication Granted Authorities( As Permission):" + authentication.getAuthorities().size());
            logger.info("Authentication Object Status This Time ( Processing ): " + authentication.isAuthenticated());
            logger.info("Authentication Object: " + authentication);
            String presentedPassword = authentication.getCredentials().toString();
            String userDetailsPassword = userDetails.getPassword();
            // Compare Password Got Process Here:
            // If Valid: Continue To Next Steps
            // If Invalid: Throw Exception Since Stop Flow
            if (!(Objects.nonNull(presentedPassword)
                    && Objects.nonNull(userDetailsPassword)
                    && presentedPassword.equals(userDetailsPassword))) {
                logger.debug("Failed To Authenticate Since Password Does Not Match Stored Value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException
    {
        try {
            UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(ErrorMessages.USER_DETAIL_IS_NULL);
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
}
