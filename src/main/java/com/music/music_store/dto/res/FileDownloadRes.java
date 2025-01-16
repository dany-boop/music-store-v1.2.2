package com.music.music_store.dto.res;

import lombok.*;
import org.springframework.core.io.Resource;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadRes {
    private Resource resource;

    private String contentType;
}