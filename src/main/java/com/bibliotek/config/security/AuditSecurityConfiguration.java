package com.bibliotek.config.security;

import com.bibliotek.domain.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing()
public class AuditSecurityConfiguration {

    @Bean
    AuditorAware<User> auditorAware() {
        return new SpringSecurityAuditorAwere();
    }
}
