package com.onlinepharma.onlinepharma.service.auth;

import com.onlinepharma.onlinepharma.criteria.auth.UserCriteria;
import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.dto.auth.UserDetails;
import com.onlinepharma.onlinepharma.repository.auth.UserRepository;
import com.onlinepharma.onlinepharma.utils.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BaseUtils utils;

    @Override
    public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
        Users users = userRepository.find(UserCriteria.childBuilder().credential(credential).build());

        if (utils.isEmpty(users))
            throw new RuntimeException("User not found Exception");

        return new UserDetails(users);
    }

    public void updateLoginTryCount(String credential, boolean cell) {
        if (cell) {
            /**
             * to zero
             */
        } else {
            /**
             * increment ++
             */
        }

    }
}
