package com.kookmin.pm.module.matching.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchingDetails {
    private Long id;
    private String title;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    private Double latitude;
    private Double longitude;
    private Double distance;
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

    public MatchingDetails (Long id, String title, String description, Timestamp startTime, Timestamp endTime,
                            Double latitude, Double longitude, String status, Integer maxCount, Double distance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = convertTimeStamp(startTime);
        this.endTime = convertTimeStamp(endTime);
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.status = status;
        this.maxCount = maxCount;
    }

    @QueryProjection
    public MatchingDetails(Long id, String title, String description, LocalDateTime startTime, LocalDateTime endTime,
                           Double latitude, Double longitude, String status, Integer maxCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.maxCount = maxCount;
    }

    private LocalDateTime convertTimeStamp(Timestamp time) {
        if(time==null) return null;
        return time.toLocalDateTime();
    }
}
