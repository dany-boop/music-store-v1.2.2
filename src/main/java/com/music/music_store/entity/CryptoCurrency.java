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
@Table(name = "t_crypto_currency")
public class CryptoCurrency extends BaseEntity {
    @Column(name = "crypto_currency", nullable = false, unique = true)
    private String cryptoCurrency;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @PrePersist
    protected void prePersist() {
        this.cryptoCurrency = this.name.replaceAll(" ", "_").toUpperCase();
        this.slug = this.name.replaceAll(" ", "_").toLowerCase(); // Set slug on persist
    }

    @PreUpdate
    protected void preUpdate() {
        this.cryptoCurrency = this.name.replaceAll(" ", "_").toUpperCase();
        this.slug = this.name.replaceAll(" ", "_").toLowerCase(); // Update slug on update
    }
}
