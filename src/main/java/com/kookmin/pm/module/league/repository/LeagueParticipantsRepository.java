package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueParticipants;
import com.kookmin.pm.module.league.domain.LeagueParticipantsStatus;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueParticipantsRepository extends JpaRepository<LeagueParticipants, Long> {
    public Optional<LeagueParticipants> findByMemberAndLeague(Member member, League league);
    public List<LeagueParticipants> findByMemberAndStatus(Member member, LeagueParticipantsStatus status);
    public void deleteByLeagueAndStatus(League league, LeagueParticipantsStatus status);
}
