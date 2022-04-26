package com.onlinepharma.onlinepharma.repository.auth;

import com.onlinepharma.onlinepharma.criteria.auth.UserCriteria;
import com.onlinepharma.onlinepharma.dao.GenericDao;
import com.onlinepharma.onlinepharma.domain.auth.Users;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends GenericDao<Users, UserCriteria> {
}
