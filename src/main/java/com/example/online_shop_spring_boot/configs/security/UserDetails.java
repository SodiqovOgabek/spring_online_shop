package com.example.online_shop_spring_boot.configs.security;

import com.example.online_shop_spring_boot.domains.AuthRole;
import com.example.online_shop_spring_boot.domains.AuthUser;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ToString
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final AuthUser user;

    public UserDetails(AuthUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (AuthRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));

        }
        return authorities;
    }

    public AuthUser getUser() {
        return user;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
