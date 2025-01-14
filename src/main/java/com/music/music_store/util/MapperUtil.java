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
}