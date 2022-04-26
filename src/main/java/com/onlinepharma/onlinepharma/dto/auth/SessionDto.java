package com.onlinepharma.onlinepharma.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDto implements Serializable {

    private Long accessTokenExpiry;

    private Long refreshTokenExpiry;

    private Long issuedAt;

    private String accessToken;

    private String refreshToken;
}