package com.music.music_store.service.impl;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.music.music_store.service.RedisService;
import com.music.music_store.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RedisService redisService;

    @Value("120000")
    private Long REFRESH_TOKEN_EXPERIRATION_IN_SECONDS;

    @Override
    public String generateRefreshToken(String userId) {
        String refreshToken = UUID.randomUUID().toString();
        if (redisService.isExist("refreshToken:" + userId))
            deleteRefreshToken(userId);
        redisService.save("refreshToken:" + userId, refreshToken,
                Duration.ofSeconds(REFRESH_TOKEN_EXPERIRATION_IN_SECONDS));
        redisService.save("refreshToken:" + refreshToken, userId,
                Duration.ofSeconds(REFRESH_TOKEN_EXPERIRATION_IN_SECONDS));

        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(String userId) {
        String refreshToken = redisService.get("refreshToken:" + userId);
        redisService.delete("refreshToken:" + userId);
        redisService.delete("userId:" + refreshToken);
    }

    @Override
    public String rotateRefreshToken(String userId) {
        deleteRefreshToken(userId);
        return generateRefreshToken(userId);
    }

    @Override
    public String getUserIdFromRefreshToken(String refreshToken) {
        if (!redisService.isExist("userId:" + refreshToken))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ERROR_REFRESH_TOKEN_INVALID_MSG");
        return redisService.get("userId:" + refreshToken);
    }
}
