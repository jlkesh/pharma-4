package com.onlinepharma.onlinepharma.config.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepharma.onlinepharma.dto.response.AppErrorDto;
import com.onlinepharma.onlinepharma.dto.response.DataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        mapper.writeValue(response.getOutputStream(),
                new DataDto<>(new AppErrorDto(HttpStatus.INTERNAL_SERVER_ERROR,
                        exception.getLocalizedMessage(),
                        request.getRequestURL().toString())));

    }

}
