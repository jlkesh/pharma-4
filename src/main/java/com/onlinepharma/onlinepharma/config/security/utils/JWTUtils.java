package com.onlinepharma.onlinepharma.config.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTUtils {
    public static Integer expiry = 1800000000;
    public static String secret = "SD784SRTED34JNBNJ@@*&p45kbNBKRIIHB@456#$%&f%&t#sxyASX345";

    public static Date getExpiry() {
        return new Date(System.currentTimeMillis() + expiry);
    }


    public static Date getExpiryForRefreshToken() {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(40));
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }


    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }
}
