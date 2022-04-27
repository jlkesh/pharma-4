package com.onlinepharma.onlinepharma.dto.auth;

import com.onlinepharma.onlinepharma.dto.Dto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto implements Dto {
    private String principal;
    private String password;
}
