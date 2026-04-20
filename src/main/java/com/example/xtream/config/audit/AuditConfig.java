package com.example.xtream.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * specify production own date time provider following app config , not default by JPA
 */
@EnableJpaAuditing(dateTimeProviderRef = "auditDatetimeProvider")
@Configuration
public class AuditConfig {

    /**
     * define bean for app make its new fetching date time with reference by name: auditDatetimeProvider for JPA
     *
     * @return a function provides new date fetching
     */
    @Bean(name = "auditDatetimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
