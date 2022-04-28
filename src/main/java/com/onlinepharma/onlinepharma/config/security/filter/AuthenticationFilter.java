package com.onlinepharma.onlinepharma.config.security.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepharma.onlinepharma.config.security.utils.JWTUtils;
import com.onlinepharma.onlinepharma.dto.auth.LoginDto;
import com.onlinepharma.onlinepharma.dto.auth.SessionDto;
import com.onlinepharma.onlinepharma.dto.auth.UserDetails;
import com.onlinepharma.onlinepharma.dto.response.AppErrorDto;
import com.onlinepharma.onlinepharma.dto.response.DataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;

    public AuthenticationFilter(AuthenticationManager manager, ObjectMapper mapper) {
        this.authenticationManager = manager;
        this.mapper = mapper;
        super.setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginDto loginDto = null;
        try {
            loginDto = mapper.readValue(request.getReader(), LoginDto.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getPrincipal(), loginDto.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException, IOException {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        Date expiryForAccessToken = JWTUtils.getExpiry();

        Date expiryForRefreshToken = JWTUtils.getExpiryForRefreshToken();

        Date issuedAt = new Date();

        String accessToken = JWT.create()
                .withSubject("" + user.getUser().getId())
                .withExpiresAt(expiryForAccessToken)
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(issuedAt)
                .withClaim("authorities", user.getAuthorities())
                .sign(JWTUtils.getAlgorithm());

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiryForRefreshToken)
                .withIssuer(request.getRequestURL().toString())
                .withIssuedAt(issuedAt)
                .sign(JWTUtils.getAlgorithm());

        SessionDto sessionDto = SessionDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(expiryForAccessToken.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiry(expiryForRefreshToken.getTime())
                .issuedAt(System.currentTimeMillis())
                .build();

//        authService.storeToken(TokenCreateDTO.builder()
//                .token(accessToken)
//                .expiresAt(expiryForAccessToken)
//                .tokenType(TokenType.ACCESS)
//                .userId(user.getUser().getId())
//                .build());
//
//        authService.storeToken(TokenCreateDTO.builder()
//                .token(refreshToken)
//                .expiresAt(expiryForRefreshToken)
//                .tokenType(TokenType.REFRESH)
//                .userId(user.getUser().getId())
//                .build());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), new DataDto<>(sessionDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // authService.updateLoginTryCount(loginDto.getCredential(), false);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(),
                new DataDto<>(new AppErrorDto(HttpStatus.INTERNAL_SERVER_ERROR,
                        failed.getMessage(),
                        request.getRequestURL().toString())));
    }
}
