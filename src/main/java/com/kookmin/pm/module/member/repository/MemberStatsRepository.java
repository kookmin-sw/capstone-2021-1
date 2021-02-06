package com.kookmin.pm.module.member.repository;

import com.kookmin.pm.module.member.domain.MemberStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStatsRepository extends JpaRepository<MemberStats, Long> {
}
