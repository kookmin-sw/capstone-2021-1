package com.kookmin.pm.module.crew.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CrewDetails {
    private Long id;
    private String name;
    private String description;
    private Integer maxCount;
    private String activityArea;
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private MemberDetails host;
    private Integer participantsCount;
    private List<MemberDetails> participants;
    private List<String> imageList;

    public CrewDetails(Crew crew) {
        this.id = crew.getId();
        this.name = crew.getName();
        this.description = crew.getDescription();
        this.maxCount = crew.getMaxCount();
        this.activityArea = crew.getActivityArea();
        this.category = crew.getCategory().getName();
        this.createdAt = crew.getCreatedAt();
    }

    @QueryProjection
    public CrewDetails(Long id, String name, String description, Integer maxCount, String activityArea,
                       String category, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxCount = maxCount;
        this.activityArea = activityArea;
        this.category = category;
        this.createdAt = createdAt;
    }
}
