package com.kookmin.pm.module.matching.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchingSearchCondition {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double distance;
    private String status;
    private Integer maxCount;
    private String hostEmail;
    private Double latitude;
    private Double longitude;
}
