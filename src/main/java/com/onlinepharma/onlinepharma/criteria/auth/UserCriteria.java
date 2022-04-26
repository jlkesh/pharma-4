package com.onlinepharma.onlinepharma.criteria.auth;

import com.onlinepharma.onlinepharma.criteria.GenericCriteria;
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
    private String credential;

    @Builder(builderMethodName = "childBuilder")
    public UserCriteria(Long selfId, Integer page, Integer perPage, String sortBy, String sortDirection, String credential) {
        super(selfId, page, perPage, sortBy, sortDirection);
        this.credential = credential;
    }

}
