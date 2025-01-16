package com.music.music_store.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.music.music_store.entity.User;

public interface JwtService {
    String generateToken(User user);

    DecodedJWT verifyToken(String bearerToken);

    void blacklistAccessToken(String bearerToken);

    boolean isTokenBlacklisted(String bearerToken);
}
