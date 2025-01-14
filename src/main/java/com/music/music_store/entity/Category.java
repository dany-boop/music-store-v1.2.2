package com.music.music_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;
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
    }

    @PreUpdate
    protected void preUpdate() {
        this.category = this.name.replaceAll(" ", "_").toUpperCase();
    }

    @PreUpdate
    private void generateSlug() {
        this.slug = this.name.replaceAll(" ", "_").toLowerCase();
    }
}
