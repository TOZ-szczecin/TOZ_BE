package com.intive.patronage.toz.tokens;

import com.intive.patronage.toz.users.model.db.RoleEntity;
import com.intive.patronage.toz.users.model.db.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
class JwtFactory {

    private static final String EMAIL_CLAIM_NAME = "email";
    private static final String SCOPES_CLAIM_NAME = "scopes";

    private final long expirationTime;
    private final String secret;

    public JwtFactory(@Value("${jwt.expiration-time-minutes}") long expirationTime,
                      @Value("${jwt.secret-base64}") String secret) {
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim(EMAIL_CLAIM_NAME, user.getEmail())
                .claim(SCOPES_CLAIM_NAME, getRolesFromUser(user))
                .setIssuedAt(new Date(Instant.now().toEpochMilli()))
                .setExpiration(new Date(Instant.now().plus(expirationTime, ChronoUnit.MINUTES).toEpochMilli()))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(secret))
                .compact();
    }

    private static List<User.Role> getRolesFromUser(User user) {
        List<User.Role> roles = new ArrayList<>();
        for (RoleEntity entity : user.getRoles()) {
            roles.add(entity.getRole());
        }
        return roles;
    }
}