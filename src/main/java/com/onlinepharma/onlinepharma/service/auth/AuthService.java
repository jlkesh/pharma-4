package com.onlinepharma.onlinepharma.service.auth;

import com.onlinepharma.onlinepharma.criteria.auth.UserCriteria;
import com.onlinepharma.onlinepharma.domain.auth.Token;
import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.dto.auth.LoginDto;
import com.onlinepharma.onlinepharma.dto.auth.SessionDto;
import com.onlinepharma.onlinepharma.dto.auth.TokenCreateDTO;
import com.onlinepharma.onlinepharma.dto.auth.UserDetails;
import com.onlinepharma.onlinepharma.dto.response.DataDto;
import com.onlinepharma.onlinepharma.repository.auth.TokenRepository;
import com.onlinepharma.onlinepharma.repository.auth.UserRepository;
import com.onlinepharma.onlinepharma.utils.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService, BaseService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BaseUtils utils;
    private final RestTemplate restTemplate;


    public ResponseEntity<String> login(LoginDto dto) {
        return restTemplate.postForEntity("http://localhost:9090/api/login", dto, String.class);
    }


    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        Users users = userRepository.find(UserCriteria.childBuilder().principal(principal).build());
        if (utils.isEmpty(users))
            throw new UsernameNotFoundException("User not found Exception");

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
