package com.anbit.fashionist.domain.common;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null){
            return Optional.of("SELF");
        }else{
            return Optional.of(userDetails.getUsername());
        }
    }
}
