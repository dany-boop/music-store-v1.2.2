package com.music.music_store.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.music.music_store.entity.User;
import com.music.music_store.service.JwtService;
import com.music.music_store.service.RedisService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final RedisService redisService;
    private final String BLACKLISTED = "BLACKLISTED";

    @Value("")
    private String JWT_SECRET;

    @Value("")
    private String JWT_ISSUER;

    @Value("120000")
    private Integer JWT_EXPIRATION_IN_SECONDS;

    @Override
    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withSubject(user.getId())
                    .withClaim("role", user.getRole().getName())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(JWT_EXPIRATION_IN_SECONDS, ChronoUnit.SECONDS))
                    .withIssuer(JWT_ISSUER)
                    .sign(Algorithm.HMAC256(JWT_SECRET));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error Generating a Token");
        }
    }

    @Override
    public DecodedJWT verifyToken(String bearerToken) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer(JWT_ISSUER)
                    .build();
            return verifier.verify(parseToken(bearerToken));
        } catch (JWTVerificationException e) {
            throw new RuntimeException("ERROR_VERIFY_TOKEN_MSG");
        }
    }

    @Override
    public void blacklistAccessToken(String bearerToken) {
        DecodedJWT decodedJWT = verifyToken(bearerToken);
        Date expiresAt = decodedJWT.getExpiresAt();
        long timeLeft = expiresAt.getTime() - System.currentTimeMillis();
        redisService.save(parseToken(bearerToken), BLACKLISTED, Duration.ofMillis(timeLeft));
    }

    @Override
    public boolean isTokenBlacklisted(String bearerToken) {
        String blacklistedToken = redisService.get(parseToken(bearerToken));
        return blacklistedToken != null && blacklistedToken.equals(BLACKLISTED);
    }

    private String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer"))
            return bearerToken.substring(7);
        return null;
    }
}
