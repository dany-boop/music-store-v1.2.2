package com.music.music_store.service;

import com.music.music_store.dto.req.AuthReq;
import com.music.music_store.dto.res.AuthRes;
import com.music.music_store.dto.res.CustomerRes;

public interface AuthService {

    CustomerRes register(AuthReq req);

    AuthRes login(AuthReq req);

    void logout(String bearerToken);

    AuthRes refreshToken(String refreshToken);
}
