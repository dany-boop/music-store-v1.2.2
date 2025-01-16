package com.music.music_store.util;

import com.music.music_store.dto.res.*;
import com.music.music_store.entity.*;

public class MapperUtil {
   public static RoleRes toRoleRes(Role role) {
      return RoleRes.builder()
            .id(role.getId())
            .name(role.getName())
            .createdAt(role.getCreatedAt())
            .updatedAt(role.getUpdatedAt())
            .build();
   }

   public static UserRes toUserRes(User user) {
      return UserRes.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole().getName())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
   }

   public static CustomerRes toCustomerRes(Customer customer) {
      UserRes user = toUserRes(customer.getUser());
      return CustomerRes.builder()
            .id(customer.getId())
            .name(customer.getName())
            .email(customer.getEmail())
            .phone(customer.getPhone())
            .addresses(customer.getAddress())
            .createdAt(customer.getCreatedAt())
            .updatedAt(customer.getUpdatedAt())
            .user(user)
            .build();
   }

   public static AuthRes toAuthRes(User user, String token, String refreshToken) {
      return AuthRes.builder()
            .userId(user.getId())
            .role(user.getRole().getName())
            .token(token)
            .refreshToken(refreshToken)
            .build();
   }
}