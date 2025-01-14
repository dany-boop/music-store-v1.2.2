package com.music.music_store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
@Table(name = "file")
@Inheritance(strategy = InheritanceType.JOINED)
public class File extends BaseEntity {
    @Column(name = "filename", nullable = false)
    @Size(max = 255)
    private String filename;

    @Column(name = "path", nullable = false, unique = true)
    @Size(max = 512)
    private String path;

    @Column(name = "content_type", nullable = false)
    @Size(max = 50)
    private String contentType;

    @Column(name = "size", nullable = false)
    @Min(0)
    private long size;
}
