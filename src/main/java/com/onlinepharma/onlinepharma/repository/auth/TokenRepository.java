package com.onlinepharma.onlinepharma.repository.auth;

import com.onlinepharma.onlinepharma.criteria.auth.TokenCriteria;
import com.onlinepharma.onlinepharma.dao.GenericDao;
import com.onlinepharma.onlinepharma.domain.auth.Token;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TokenRepository extends GenericDao<Token, TokenCriteria> {

    @Override
    protected void defineCriteriaOnQuerying(TokenCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getToken())) {
            whereCause.add("token = :token");
            params.put("token", criteria.getToken());
        }

        if (!utils.isEmpty(criteria.getTokenType())) {
            whereCause.add("tokenType = :tokenType");
            params.put("tokenType", criteria.getTokenType());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }


}
