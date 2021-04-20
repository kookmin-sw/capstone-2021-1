package com.kookmin.pm.module.league.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LeagueEditInfo {
    private Long id;
    private String title;
    private String description;
    private String activityArea;
    private Integer maxCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    private String category;
}
