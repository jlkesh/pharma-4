package com.onlinepharma.onlinepharma.dto.auth;

import com.onlinepharma.onlinepharma.dto.Dto;
import com.onlinepharma.onlinepharma.enums.TokenType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenCreateDTO implements Dto {

    private String token;

    private TokenType tokenType;

    private Date expiresAt;

    private Long userId;

}
