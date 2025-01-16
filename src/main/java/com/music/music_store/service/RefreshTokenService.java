package com.music.music_store.service;

public interface RefreshTokenService {
    String generateRefreshToken(String userId);

    void deleteRefreshToken(String userId);

    String rotateRefreshToken(String userId);

    String getUserIdFromRefreshToken(String refreshToken);
}
