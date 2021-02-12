package com.kookmin.pm.module.league.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueStatus;
import com.kookmin.pm.module.league.domain.LeagueType;
import com.kookmin.pm.module.league.domain.ParticipantType;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LeagueDetails {
    private Long id;
    private String title;
    private String description;
    private String activityArea;
    private String leagueType;
    private Integer maxCount;
    private String participantType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    private String status;
    private MemberDetails host;
    private String category;

    public LeagueDetails(League league) {
        this.id = league.getId();
        this.title = league.getTitle();
        this.description = league.getDescription();
        this.activityArea = league.getActivityArea();
        this.leagueType = league.getType().toString();
        this.maxCount = league.getMaxCount();
        this.participantType = league.getParticipantType().toString();
        this.createdAt = league.getCreatedAt();
        this.startTime = league.getStartTime();
        this.endTime = league.getEndTime();
        this.status = league.getStatus().toString();
        this.category = league.getCategory().getName();
    }
}
