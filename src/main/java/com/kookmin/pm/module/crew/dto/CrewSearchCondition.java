package com.kookmin.pm.module.crew.dto;

import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CrewSearchCondition {
    private String name;
    private Integer maxCount;
    private String activityArea;
    private String category;
    private String host;
}
