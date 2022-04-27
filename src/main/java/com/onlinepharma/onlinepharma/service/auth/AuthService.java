package com.onlinepharma.onlinepharma.service.auth;

import com.onlinepharma.onlinepharma.criteria.auth.TokenCriteria;
import com.onlinepharma.onlinepharma.criteria.auth.UserCriteria;
import com.onlinepharma.onlinepharma.domain.auth.Token;
import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.dto.auth.TokenCreateDTO;
import com.onlinepharma.onlinepharma.dto.auth.UserDetails;
import com.onlinepharma.onlinepharma.repository.auth.TokenRepository;
import com.onlinepharma.onlinepharma.repository.auth.UserRepository;
import com.onlinepharma.onlinepharma.utils.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
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

    @Transactional
    public void storeToken(TokenCreateDTO dto) {
        Token token = new Token();
        token.setToken(dto.getToken());
        token.setTokenType(dto.getTokenType());
        token.setExpiresAt(dto.getExpiresAt());
        token.setCreatedBy(dto.getUserId());
        tokenRepository.save(token);
    }
}
