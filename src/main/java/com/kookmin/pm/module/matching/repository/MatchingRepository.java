package com.kookmin.pm.module.matching.repository;

import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingStatus;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching,Long>, MatchingSearchRepository {
    List<Matching> findByMember(Member member);
    List<Matching> findByMemberAndStatus(Member member, MatchingStatus status);
}
