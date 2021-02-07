package com.kookmin.pm.module.mathcing.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MatchingCreateInfo {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private Double latitude;
    private Double longitude;
    private Integer maxCount;
}
