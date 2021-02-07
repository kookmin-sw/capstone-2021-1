package com.kookmin.pm.module.mathcing.dto;

import com.kookmin.pm.module.mathcing.domain.Matching;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchingDetails {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double latitude;
    private Double longitude;
    private String status;
    private Integer maxCount;
    private Integer participantsCount;
    private MemberDetails host;
    private List<MemberDetails> participants;


    public MatchingDetails(Matching matching) {
        this.id = matching.getId();
        this.title = matching.getTitle();
        this.description = matching.getDescription();
        this.startTime = matching.getStartTime();
        this.endTime = matching.getEndTime();
        this.latitude = matching.getLatitude();
        this.longitude = matching.getLongitude();
        this.status = matching.getStatus().toString();
        this.maxCount = matching.getMaxCount();
    }
}
