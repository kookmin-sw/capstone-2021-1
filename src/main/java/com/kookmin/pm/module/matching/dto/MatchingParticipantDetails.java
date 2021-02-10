package com.kookmin.pm.module.matching.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingParticipant;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MatchingParticipantDetails {
    private Long id;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private MemberDetails member;
    private MatchingDetails matching;

    public MatchingParticipantDetails(MatchingParticipant matchingParticipant) {
        this.id = matchingParticipant.getId();
        this.status = matchingParticipant.getStatus().toString();
        this.createdAt = matchingParticipant.getCreatedAt();
        this.member = new MemberDetails(matchingParticipant.getMember());
        this.matching = new MatchingDetails(matchingParticipant.getMatching());
    }
}
