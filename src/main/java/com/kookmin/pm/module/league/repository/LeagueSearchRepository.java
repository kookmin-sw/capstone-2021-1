package com.kookmin.pm.module.league.repository;

import com.kookmin.pm.module.league.dto.LeagueDetails;
import com.kookmin.pm.module.league.dto.LeagueSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeagueSearchRepository {
    public Page<LeagueDetails> searchLeague(Pageable pageable, LeagueSearchCondition searchCondition);
}
