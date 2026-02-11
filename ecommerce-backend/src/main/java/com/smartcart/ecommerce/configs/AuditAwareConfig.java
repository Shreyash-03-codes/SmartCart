package com.smartcart.ecommerce.configs;

import com.smartcart.ecommerce.services.impl.AuditOrAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditAwareConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditOrAwareImpl();
    }
}
