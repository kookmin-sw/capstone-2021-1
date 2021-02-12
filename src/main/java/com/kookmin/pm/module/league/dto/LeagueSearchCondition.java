package com.kookmin.pm.module.league.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LeagueSearchCondition {
    private String title;
    private String activityArea;
    private String leagueType;
    private Integer maxCount;
    private String participantType;
    private String status;
    private Long host;
    private String category;
}
