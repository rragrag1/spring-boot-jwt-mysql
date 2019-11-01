package com.sushil.dangi.springbootjwtmysql.login;

import com.sushil.dangi.springbootjwtmysql.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final User user;

    UserPrincipal(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user
                .getAuthorities()
                .stream()
                .map(authorities -> new SimpleGrantedAuthority(authorities.getName()))
                .collect(Collectors.toCollection(() -> new HashSet<>(user.getAuthorities().size())));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
