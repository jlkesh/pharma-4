package com.onlinepharma.onlinepharma.dto.auth;

import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Users user;

    public UserDetails(Users user) {
        this.user = user;
    }


    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
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
