package com.music.music_store.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.music.music_store.dto.req.PageReq;
import com.music.music_store.dto.req.UserChangePasswordReq;
import com.music.music_store.dto.req.UserReq;
import com.music.music_store.dto.req.UserUpdateReq;
import com.music.music_store.dto.res.UserRes;
import com.music.music_store.entity.User;

public interface UserService extends UserDetailsService {
    Page<UserRes> getAll(PageReq req);

    UserRes getById(String id);

    User getOne(String id);

    UserRes create(UserReq req);

    UserRes changePassword(String id, UserChangePasswordReq req);

    UserRes update(String id, UserUpdateReq req);

    void delete(String id);

}
