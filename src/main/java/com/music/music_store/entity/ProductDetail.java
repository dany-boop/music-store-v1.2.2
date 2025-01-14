package com.music.music_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_detail")
public class ProductDetail {
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Vinyl-specific attributes
    @Column(name = "artist", nullable = true)
    private String artist;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "release_year", nullable = true)
    private Integer releaseYear;

    // Instrument-specific attributes
    @Column(name = "instrument_type", nullable = true)
    private String instrumentType; // e.g., Acoustic, Electric

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "color")
    private String color;
}
