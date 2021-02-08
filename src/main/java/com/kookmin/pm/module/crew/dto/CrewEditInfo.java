package com.kookmin.pm.module.crew.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrewEditInfo {
    private Long id;
    private String name;
    private String description;
}
