package com.onlinepharma.onlinepharma.domain.auth;

import com.onlinepharma.onlinepharma.domain.Auditable;
import com.onlinepharma.onlinepharma.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_token")
public class Token extends Auditable {

    private String token;

    private TokenType tokenType;

    private Date expiresAt;

}
