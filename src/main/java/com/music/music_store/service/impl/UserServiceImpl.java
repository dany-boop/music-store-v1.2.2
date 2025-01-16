package com.music.music_store.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.music.music_store.dto.req.*;
import com.music.music_store.dto.res.UserRes;
import com.music.music_store.entity.Role;
import com.music.music_store.entity.User;
import com.music.music_store.repository.UserRepository;
import com.music.music_store.service.RoleService;
import com.music.music_store.service.UserService;
import com.music.music_store.util.LogUtil;
import com.music.music_store.util.MapperUtil;
import com.music.music_store.util.SortUtil;
import com.music.music_store.util.ValidationUtil;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("admin1")
    private String ADMIN_USERNAME;

    @Value("admin1")
    private String ADMIN_PASSWORD;

    @PostConstruct
    public void init() {
        if (userRepository.existsByUsername(ADMIN_USERNAME))
            return;
        User user = User.builder()
                .username(ADMIN_USERNAME)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(roleService.getByName("SuperAdmin"))
                .build();
        userRepository.save(user);

    }

    @Override
    public Page<UserRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                SortUtil.parseSort(req.getSort()));

        Page<User> users = userRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return users.map(MapperUtil::toUserRes);
    }

    @Override
    public UserRes getById(String id) {
        return MapperUtil.toUserRes(getOne(id));
    }

    @Override
    public User getOne(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @Override
    public UserRes create(UserReq req) {
        validationUtil.validate(req);
        Role role = roleService.getOne(req.getRoleId());
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();

        userRepository.saveAndFlush(user);
        return MapperUtil.toUserRes(user);

    }

    @Override
    public UserRes changePassword(String id, UserChangePasswordReq req) {
        LogUtil.info("changing password");
        validationUtil.validate(req);
        User user = getOne(id);
        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Invalid");
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.saveAndFlush(user);
        LogUtil.info("Password Sucessfully Change");
        return MapperUtil.toUserRes(user);
    }

    @Override
    public UserRes update(String id, UserUpdateReq req) {
        LogUtil.info("Updating User");
        validationUtil.validate(req);
        Role role = roleService.getOne(req.getRoleId());
        User user = getOne(id);
        user.setUsername(req.getUsername());
        user.setRole(role);
        userRepository.saveAndFlush(user);
        LogUtil.info("User successfully updated");
        return MapperUtil.toUserRes(user);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting user");
        User user = getOne(id);
        user.setDeleted(!user.isDeleted());
        userRepository.save(user);
        LogUtil.info("finished deleting user");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().isDeleted())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR_USER_HAS_BEEN_DELETED_MSG");
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR_GET_USER_MSG");
        return user.get();
    }

}
