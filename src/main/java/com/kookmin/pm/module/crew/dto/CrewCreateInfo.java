package com.kookmin.pm.module.crew.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class CrewCreateInfo {
    private String name;
    private String description;
    private Integer maxCount;
    private String activityArea;
    private String category;
}
