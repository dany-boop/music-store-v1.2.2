package com.music.music_store.dto.req;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateReq {
    private String name;

    private String email;

    private String phone;

    private String address;
}
