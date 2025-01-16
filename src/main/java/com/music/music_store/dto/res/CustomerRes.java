package com.music.music_store.dto.res;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRes extends BaseRes {
    private String name;

    private String email;

    private String phone;

    private String addresses;

    private UserRes user;
}