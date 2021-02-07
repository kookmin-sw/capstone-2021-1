package com.kookmin.pm.module.mathcing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MatchingEditInfo {
    private Long id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private Integer maxCount;
    private LocalDateTime startTime;
}
