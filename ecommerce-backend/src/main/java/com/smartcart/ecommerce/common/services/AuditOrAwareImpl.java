package com.smartcart.ecommerce.common.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditOrAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication ==null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")){
            return Optional.of("SYSTEM");
        }
        return Optional.of(authentication.getName());
    }
}
