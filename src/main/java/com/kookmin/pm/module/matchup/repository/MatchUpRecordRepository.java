package com.kookmin.pm.module.matchup.repository;

import com.kookmin.pm.module.matchup.domain.MatchUpRecord;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchUpRecordRepository extends JpaRepository<MatchUpRecord, Long> {
    List<MatchUpRecord> findByWinnerOrLoser(Member winner, Member loser);
}
