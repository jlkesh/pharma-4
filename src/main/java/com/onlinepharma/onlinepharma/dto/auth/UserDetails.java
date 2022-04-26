package com.onlinepharma.onlinepharma.dto.auth;

import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Users user;

    public UserDetails(Users user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.addAll(role.getPermissions()
                    .stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getAuthority()))
                    .toList());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getCredential();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStatus().equals(Status.BLOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
