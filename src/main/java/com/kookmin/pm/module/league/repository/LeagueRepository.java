package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueStatus;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League, Long>, LeagueSearchRepository {
    public List<League> findByMemberAndStatus(Member member, LeagueStatus status);
}
