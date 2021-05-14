package com.kookmin.pm.module.matching.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class MatchingSearchCondition {
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private Double distance;
    private String status;
    private Integer maxCount;
    private Long host;
    private Long participant;
    private Double latitude;
    private Double longitude;
    private String category;
}
