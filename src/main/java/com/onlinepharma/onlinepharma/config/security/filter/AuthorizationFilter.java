package com.onlinepharma.onlinepharma.config.security.filter;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepharma.onlinepharma.config.security.utils.JWTUtils;
import com.onlinepharma.onlinepharma.dto.auth.UserDetails;
import com.onlinepharma.onlinepharma.dto.response.AppErrorDto;
import com.onlinepharma.onlinepharma.dto.response.DataDto;
import com.onlinepharma.onlinepharma.repository.auth.UserRepository;
import com.onlinepharma.onlinepharma.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/access/token")
                || request.getServletPath().equals("/refresh/token")
                || request.getServletPath().equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {

                String token = authorizationHeader.substring("Bearer ".length());

                DecodedJWT decodedJWT = JWTUtils.getVerifier().verify(token);

                long id = Long.parseLong(decodedJWT.getSubject());

                UserDetails userDetails = new UserDetails(userRepository.find(id));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);

            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                DataDto<AppErrorDto> responseDTO = new DataDto<>(new AppErrorDto(HttpStatus.FORBIDDEN, exception.getLocalizedMessage(), request.getRequestURI()));
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                mapper.writeValue(response.getOutputStream(), responseDTO);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}