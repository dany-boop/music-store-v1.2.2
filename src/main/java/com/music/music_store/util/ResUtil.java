package com.music.music_store.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.music.music_store.dto.res.PageInfo;
import com.music.music_store.dto.res.PageRes;
import com.music.music_store.dto.res.Res;

import java.util.List;

public class ResUtil {
    public static <T> ResponseEntity<?> buildRes(HttpStatus status, String message, T data) {
        Res<T> body = Res.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    public static <T> ResponseEntity<?> buildPageRes(HttpStatus status, String message, Page<T> page) {
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .pages(page.getTotalPages())
                .page(page.getPageable().getPageNumber() + 1)
                .size(page.getSize())
                .build();
        PageRes<List<T>> res = PageRes.<List<T>>builder()
                .status(status.value())
                .message(message)
                .data(page.getContent())
                .pagination(pageInfo)
                .build();
        return ResponseEntity.status(status).body(res);
    }
}