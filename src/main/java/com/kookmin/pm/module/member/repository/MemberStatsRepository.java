package com.kookmin.pm.module.member.repository;

import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.MemberStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberStatsRepository extends JpaRepository<MemberStats, Long> {
    Optional<MemberStats> findByMember(Member member);
}
