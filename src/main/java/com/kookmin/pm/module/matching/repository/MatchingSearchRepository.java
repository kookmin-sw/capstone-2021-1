package com.kookmin.pm.module.matching.repository;

import com.kookmin.pm.module.matching.domain.ParticipantStatus;
import com.kookmin.pm.module.matching.dto.MatchingDetails;
import com.kookmin.pm.module.matching.dto.MatchingSearchCondition;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingSearchRepository {
    public List<Member> searchMemberInMatchingParticipant(Long matchingId, ParticipantStatus status);
    public List<MatchingDetails> findParticipatedMatching(MatchingSearchCondition searchCondition);
    public Page<MatchingDetails> searchMatching(Pageable pageable, MatchingSearchCondition searchCondition);
}
