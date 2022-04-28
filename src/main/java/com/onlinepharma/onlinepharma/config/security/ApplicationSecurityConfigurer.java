package com.onlinepharma.onlinepharma.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepharma.onlinepharma.config.handlers.CustomAuthenticationFailureHandler;
import com.onlinepharma.onlinepharma.config.security.filter.AuthenticationFilter;
import com.onlinepharma.onlinepharma.config.security.filter.AuthorizationFilter;
import com.onlinepharma.onlinepharma.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class ApplicationSecurityConfigurer extends WebSecurityConfigurerAdapter {
    public final static String[] WHITE_LIST = {
            "/error",
            "/api/login",
            "/api/v1/auth/access/token",
            "/api/v1/auth/refresh/token",
            "/api/v1/auth/register",
            "/swagger-ui/**",
            "/api-docs/**"
    };

    private final AuthService authService;
    private final ObjectMapper mapper;
    private final AuthorizationFilter authorizationFilter;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry.antMatchers(WHITE_LIST)
                                .permitAll()
                                .anyRequest()
                                .authenticated());

        http.addFilter(new AuthenticationFilter(authenticationManager(), mapper));
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
