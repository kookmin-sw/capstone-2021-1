package com.kookmin.pm.module.category.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name="CATEGORY")
public class Category {
    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
