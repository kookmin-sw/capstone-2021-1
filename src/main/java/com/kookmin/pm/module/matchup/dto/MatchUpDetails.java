package com.kookmin.pm.module.matchup.dto;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.dto.LeagueDetails;
import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.dto.MatchingDetails;
import com.kookmin.pm.module.matchup.domain.MatchUp;
import com.kookmin.pm.module.matchup.domain.MatchUpRecord;
import com.kookmin.pm.module.matchup.domain.MatchUpStatus;
import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.dto.MemberDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class MatchUpDetails {
    private Long id;
    private String status;
    private MemberDetails firstMember;
    private MemberDetails secondMember;
    private LeagueDetails league;
    private MatchingDetails matching;
    private MatchUpRecordDetails matchUpRecord;

    public MatchUpDetails(MatchUp matchUp) {
        this.id = matchUp.getId();
        this.status = matchUp.getStatus().toString();
        this.firstMember = new MemberDetails(matchUp.getFirstMember());
        this.secondMember = new MemberDetails(matchUp.getSecondMember());
    }

    public MatchUpDetails(MatchUp matchUp, MatchUpRecord matchUpRecord) {
        this(matchUp);
        if(matchUpRecord != null) this.matchUpRecord = new MatchUpRecordDetails(matchUpRecord);
    }

    public MatchUpDetails(MatchUp matchUp, MatchUpRecord matchUpRecord, League league, Matching matching) {
        this(matchUp,matchUpRecord);
        this.league = new LeagueDetails(league);
        if(matching != null) this.matching = new MatchingDetails(matching);
    }
}
