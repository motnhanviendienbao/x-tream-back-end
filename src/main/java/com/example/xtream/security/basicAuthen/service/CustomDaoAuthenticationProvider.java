package com.example.xtream.security.basicAuthen.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.LinkedHashSet;

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
@Service
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    public static final Logger logger = LogManager.getLogger(CustomDaoAuthenticationProvider.class);

    /**
     * Constructor injection
     * @param userDetailsService define for spring automatically injects
     * @param userCache define for spring automatically injects
     */
    @Autowired
    public CustomDaoAuthenticationProvider(UserDetailsService userDetailsService, UserCache userCache ) {
        super(userDetailsService);
        logger.info("[NGOC TU SERVER]: Init DaoAuthenticationProvider Custom Object  ");
        super.setUserCache(userCache);
        logger.info("[NGOC TU SERVER]: Set UserCache For DaoAuthenticationProvider Custom Object  ");
    }

    /**
     * Custom logic by override
     *
     * @param userDetails as retrieved from the
     * {@link #retrieveUser(String, UsernamePasswordAuthenticationToken)} or
     * <code>UserCache</code>
     * @param authentication the current request that needs to be authenticated
     */
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
}


