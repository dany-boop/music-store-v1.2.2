package com.music.music_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_category")
public class Category extends BaseEntity {
    @Column(name = "category", nullable = false, unique = true)
    private String category;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @PrePersist
    protected void prePersist() {
        this.category = this.name.replaceAll(" ", "_").toUpperCase();
        this.slug = this.name.replaceAll(" ", "_").toLowerCase(); // Set slug on persist
    }

    @PreUpdate
    protected void preUpdate() {
        this.category = this.name.replaceAll(" ", "_").toUpperCase();
        this.slug = this.name.replaceAll(" ", "_").toLowerCase(); // Update slug on update
    }
}
