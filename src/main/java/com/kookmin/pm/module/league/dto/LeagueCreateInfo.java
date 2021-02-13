package com.kookmin.pm.module.league.dto;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.league.domain.LeagueStatus;
import com.kookmin.pm.module.league.domain.LeagueType;
import com.kookmin.pm.module.league.domain.ParticipantType;
import com.kookmin.pm.module.member.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LeagueCreateInfo {
    private String title;
    private String description;
    private String activityArea;
    private Integer maxCount;
    private String leagueType;
    private String participantType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    private String category;
}
