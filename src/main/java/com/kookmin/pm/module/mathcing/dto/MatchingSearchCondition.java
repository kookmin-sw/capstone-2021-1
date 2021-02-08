package com.kookmin.pm.module.mathcing.dto;

import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchingSearchCondition {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer maxCount;
    private String hostEmail;
}
