package com.kookmin.pm.module.crew.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kookmin.pm.module.crew.domain.CrewParticipants;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CrewParticipantsDetails {
    private Long id;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private MemberDetails member;
    private CrewDetails crew;

    public CrewParticipantsDetails(CrewParticipants crewParticipants) {
        this.id = crewParticipants.getId();
        this.status = crewParticipants.getStatus().toString();
        this.createdAt = crewParticipants.getCreatedAt();
        this.member = new MemberDetails(crewParticipants.getMember());
        this.crew = new CrewDetails(crewParticipants.getCrew());
    }
}
