package com.onlinepharma.onlinepharma.criteria.auth;

import com.onlinepharma.onlinepharma.criteria.GenericCriteria;
import com.onlinepharma.onlinepharma.enums.UserType;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

@Getter
@Setter
@NoArgsConstructor
@ParameterObject
public class UserCriteria extends GenericCriteria {

    @Parameter(hidden = true)
    private String principal;
    private UserType userType;

    @Builder(builderMethodName = "childBuilder")

    public UserCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String principal, UserType userType) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.principal = principal;
        this.userType = userType;
    }
}
