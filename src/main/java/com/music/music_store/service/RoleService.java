package com.music.music_store.service;

import org.springframework.data.domain.Page;

import com.music.music_store.dto.req.PageReq;
import com.music.music_store.dto.req.RoleReq;
import com.music.music_store.dto.res.RoleRes;
import com.music.music_store.entity.Role;

public interface RoleService {
    Page<RoleRes> getAll(PageReq req);

    RoleRes getById(String id);

    Role getOne(String id);

    Role getByName(String name);

    RoleRes create(RoleReq req);

    RoleRes update(String id, RoleReq req);

    void delete(String id);
}