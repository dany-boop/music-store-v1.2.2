package com.music.music_store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.music.music_store.dto.req.*;
import com.music.music_store.dto.res.*;
import com.music.music_store.entity.Role;
import com.music.music_store.entity.User;
import com.music.music_store.service.*;
// import com.music.music_store.util.LogUtil;
import com.music.music_store.util.MapperUtil;
import com.music.music_store.util.ValidationUtil;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerRes register(AuthReq req) {
        validationUtil.validate(req);
        Role role = roleService.getByName("Customer");
        UserReq userReq = UserReq.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .roleId(role.getId())
                .build();
        UserRes userRes = userService.create(userReq);
        CustomerReq customerReq = CustomerReq.builder()
                .userId(userRes.getId())
                .build();
        return customerService.create(customerReq);
    };

    @Override
    public AuthRes login(AuthReq req) {
        validationUtil.validate(req);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.generateRefreshToken(user.getId());
        return MapperUtil.toAuthRes(user, token, refreshToken);
    }

    @Override
    public void logout(String bearerToken) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refreshTokenService.deleteRefreshToken(user.getId());
        jwtService.blacklistAccessToken(bearerToken);
    }

    @Override
    public AuthRes refreshToken(String refreshToken) {
        String userId = refreshTokenService.getUserIdFromRefreshToken(refreshToken);
        User user = userService.getOne(userId);
        String newToken = jwtService.generateToken(user);
        String newRefreshToken = refreshTokenService.rotateRefreshToken(userId);
        return MapperUtil.toAuthRes(user, newToken, newRefreshToken);
    }
}
