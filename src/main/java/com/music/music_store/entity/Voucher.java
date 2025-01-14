package com.music.music_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_voucher")
public class Voucher extends BaseEntity {
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "description", nullable = false)
    private String description;
}
