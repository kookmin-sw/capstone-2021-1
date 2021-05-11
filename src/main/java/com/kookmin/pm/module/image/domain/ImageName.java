package com.kookmin.pm.module.image.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE_NAME")
@NoArgsConstructor
@Getter
@Setter
public class ImageName {
    @Id @GeneratedValue
    @Column(name = "IMAGE_NAME_ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "RELATED_ID", nullable = false)
    private Long relatedId;

    @Builder
    public ImageName(String name, Long relatedId) {
        this.name = name;
        this.relatedId = relatedId;
    }
}
