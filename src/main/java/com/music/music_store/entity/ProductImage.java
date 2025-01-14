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
@Table(name = "product_view")
public class ProductImage extends File {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
