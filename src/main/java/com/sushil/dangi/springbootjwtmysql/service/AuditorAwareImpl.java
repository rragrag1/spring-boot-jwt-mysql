package com.sushil.dangi.springbootjwtmysql.service;

import com.sushil.dangi.springbootjwtmysql.domain.User;
import com.sushil.dangi.springbootjwtmysql.login.UserPrincipal;
import com.sushil.dangi.springbootjwtmysql.utils.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditorAwareImpl implements AuditorAware<String> {

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UserPrincipal) auth.getPrincipal()).getUser();
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return Optional.of(Constants.ANONYMOUS_USER);
        }
        return Optional.of(currentUser.getUsername());
    }
}
