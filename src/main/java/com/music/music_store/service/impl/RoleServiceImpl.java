package com.music.music_store.service.impl;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.music.music_store.dto.req.PageReq;
import com.music.music_store.dto.req.RoleReq;
import com.music.music_store.dto.res.RoleRes;
import com.music.music_store.entity.Role;
import com.music.music_store.repository.RoleRepository;
import com.music.music_store.service.RoleService;
import com.music.music_store.util.LogUtil;
import com.music.music_store.util.MapperUtil;
import com.music.music_store.util.SortUtil;
import com.music.music_store.util.ValidationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;
    private final ValidationUtil validationUtil;

    @PostConstruct
    public void init() {
        if (roleRepository.existsByName("SuperAdmin"))
            return;

        Role role = Role.builder()
                .name("SuperAdmin")
                .build();
        roleRepository.save(role);
    }

    @Override
    public Page<RoleRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort()));
        Page<Role> roles = roleRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return roles.map(MapperUtil::toRoleRes);
    }

    @Override
    public RoleRes getById(String id) {
        return MapperUtil.toRoleRes(getOne(id));
    }

    @Override
    public Role getOne(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found"));
    }

    @Override
    public Role getByName(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found"));
    }

    @Override
    public RoleRes create(RoleReq req) {
        LogUtil.info("creating role");
        validationUtil.validate(req);
        Role role = Role.builder()
                .name(req.getName())
                .build();
        roleRepository.saveAndFlush(role);
        LogUtil.info("finished creating role");
        return MapperUtil.toRoleRes(role);
    }

    @Override
    public RoleRes update(String id, RoleReq req) {
        LogUtil.info("updating role");
        validationUtil.validate(req);
        Role role = getOne(id);
        role.setName(req.getName());
        roleRepository.saveAndFlush(role);
        LogUtil.info("finished updating role");
        return MapperUtil.toRoleRes(role);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting role");
        Role role = getOne(id);
        role.setDeleted(!role.isDeleted());
        roleRepository.save(role);
        LogUtil.info("finished deleting role");
    }
}
