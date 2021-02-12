package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.league.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long>, LeagueSearchRepository {
}
