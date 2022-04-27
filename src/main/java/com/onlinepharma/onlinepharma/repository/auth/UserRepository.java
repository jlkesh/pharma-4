package com.onlinepharma.onlinepharma.repository.auth;

import com.onlinepharma.onlinepharma.criteria.auth.UserCriteria;
import com.onlinepharma.onlinepharma.dao.GenericDao;
import com.onlinepharma.onlinepharma.domain.auth.Users;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepository extends GenericDao<Users, UserCriteria> {
    @Override
    protected void defineCriteriaOnQuerying(UserCriteria criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {

        if (!utils.isEmpty(criteria.getPrincipal())) {
            whereCause.add("principal = :principal");
            params.put("principal", criteria.getPrincipal());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }

}
