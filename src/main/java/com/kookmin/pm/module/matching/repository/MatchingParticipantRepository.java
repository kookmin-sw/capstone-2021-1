package com.kookmin.pm.module.matching.repository;

import com.kookmin.pm.module.matching.domain.Matching;
import com.kookmin.pm.module.matching.domain.MatchingParticipant;
import com.kookmin.pm.module.matching.domain.ParticipantStatus;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingParticipantRepository extends JpaRepository<MatchingParticipant, Long> {
    Optional<MatchingParticipant> findByMemberAndMatching(Member member, Matching matching);
    List<MatchingParticipant> findByMatching(Matching matching);
    void deleteAllByMatching(Matching matching);
    void deleteByMemberUidAndMatchingId(String uid, Long matchingId);
    Long countByMatchingAndStatus(Matching matching, ParticipantStatus status);
}
