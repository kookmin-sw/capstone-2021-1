package com.kookmin.pm.module.league.dto;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueParticipants;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeagueParticipantDetails {
    private Long id;
    private MemberDetails member;
    private LeagueDetails league;
    private String status;

    public LeagueParticipantDetails(LeagueParticipants participants) {
        this.id = participants.getId();
        this.status = participants.getStatus().toString();
    }
}
