package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.league.domain.LeagueParticipants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueParticipantsRepository extends JpaRepository<LeagueParticipants, Long> {
}
