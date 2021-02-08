package com.kookmin.pm.module.crew.dto;

import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private MemberDetails host;
    private List<MemberDetails> participants;

    public CrewDetails(Crew crew) {
        this.id = crew.getId();
        this.name = crew.getName();
        this.description = crew.getDescription();
        this.maxCount = crew.getMaxCount();
        this.activityArea = crew.getActivityArea();
        this.category = crew.getCategory().getName();
    }
}
