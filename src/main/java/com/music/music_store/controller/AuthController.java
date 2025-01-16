package com.music.music_store.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.music.music_store.dto.req.AuthReq;
import com.music.music_store.dto.res.AuthRes;
import com.music.music_store.service.AuthService;
import com.music.music_store.util.ResUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;

    @Value("120000")
    private Integer REFRESH_TOKEN_EXPIRATION_IN_SECONDS;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> register(@RequestBody AuthReq req) {
        return ResUtil.buildRes(
                HttpStatus.OK,
                "SUCCESS_REGISTER_MSG",
                authService.register(req));
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<?> login(@RequestBody AuthReq req, HttpServletResponse res) {
        AuthRes authRes = authService.login(req);
        setCookie(res, authRes.getRefreshToken());
        return ResUtil.buildRes(
                HttpStatus.OK,
                "SUCCESS_LOGIN_MSG",
                authRes);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest req, HttpServletResponse res) {
        String refreshToken = getRefreshTokenFromCookie(req);
        AuthRes authRes = authService.refreshToken(refreshToken);
        setCookie(res, authRes.getRefreshToken());
        return ResUtil.buildRes(
                HttpStatus.OK,
                "SUCCESS_REFRESH_TOKEN_MSG",
                authRes);
    }

    @PostMapping(path = "/signout")
    public ResponseEntity<?> logout(HttpServletRequest req) {
        String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        authService.logout(bearerToken);
        return ResUtil.buildRes(
                HttpStatus.OK,
                "SUCCESS_LOGOUT_MSG",
                null);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest req) {
        Cookie cookie = Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ERROR_REFRESH_TOKEN_NOT_FOUND_MSG"));
        return cookie.getValue();
    }

    private void setCookie(HttpServletResponse res, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_TOKEN_EXPIRATION_IN_SECONDS);
        res.addCookie(cookie);
    }
}
