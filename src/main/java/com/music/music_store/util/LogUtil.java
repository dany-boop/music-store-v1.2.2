package com.music.music_store.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import com.music.music_store.entity.User;

@Slf4j
public class LogUtil {
    public static void info(String message) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User: {} {}", user.getUsername(), message);
    }

    public static void error(String message) {
        log.error(message);
    }
}
