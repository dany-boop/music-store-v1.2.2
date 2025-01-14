package com.music.music_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_role")
public class Role extends BaseEntity {

    @Column(name = "role", nullable = false, unique = true)
    private String role;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @PrePersist
    protected void prePersist() {
        this.role = String.format("ROLE_%s", this.name.replaceAll("", "_").toUpperCase());
    }

    @PreUpdate
    protected void preUpdate() {
        this.role = String.format("ROLE_%s", this.name.replaceAll(" ", "_").toUpperCase());
    }

}
