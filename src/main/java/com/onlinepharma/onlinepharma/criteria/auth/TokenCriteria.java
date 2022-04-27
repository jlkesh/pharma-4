package com.onlinepharma.onlinepharma.criteria.auth;

import com.onlinepharma.onlinepharma.criteria.GenericCriteria;
import com.onlinepharma.onlinepharma.enums.TokenType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

@Getter
@Setter
@NoArgsConstructor
@ParameterObject
public class TokenCriteria extends GenericCriteria {
    private String token;
    private TokenType tokenType;

    @Builder(builderMethodName = "childBuilder")
    public TokenCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String token, TokenType tokenType) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.token = token;
        this.tokenType = tokenType;
    }
}
