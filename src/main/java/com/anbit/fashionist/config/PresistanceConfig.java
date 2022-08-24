package com.anbit.fashionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.anbit.fashionist.domain.common.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PresistanceConfig {
    @Bean
    AuditorAware<String> auditorProvider() {        return new AuditorAwareImpl();
    }
}
