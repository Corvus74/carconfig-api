package com.applicationdemo.carconfig.security;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record OrderUserDetails(OrderUser orderUser) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (orderUser.getRole() == null) {
            return Collections.emptyList();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + orderUser.getRole().name()));
    }

    @Override
    public String getPassword() {
        return orderUser.getPassword();
    }

    @Override
    public String getUsername() {
        return orderUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return orderUser.isValid();
    }
}
