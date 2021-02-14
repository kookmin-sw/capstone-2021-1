package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.league.domain.LeagueParticipantsStatus;
import com.kookmin.pm.module.league.dto.LeagueDetails;
import com.kookmin.pm.module.league.dto.LeagueSearchCondition;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeagueSearchRepository {
    public List<Member> findMemberInLeague(Long leagueId, LeagueParticipantsStatus status);
    public List<LeagueDetails> findParticipatedLeague(LeagueSearchCondition searchCondition);
    public Page<LeagueDetails> searchLeague(Pageable pageable, LeagueSearchCondition searchCondition);
}
