package com.music.music_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_customer")
public class Customer extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address", nullable = true, length = 255)
    private String address;

    @OneToOne
    @Column(name = "user_id", nullable = false)
    private User user;
}
