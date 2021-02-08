package com.kookmin.pm.module.mathcing.repository;

import com.kookmin.pm.module.mathcing.dto.MatchingDetails;
import com.kookmin.pm.module.mathcing.dto.MatchingSearchCondition;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingSearchRepository {
    public List<Member> searchMemberInMatchingParticipant(Long matchingId);
    public Page<MatchingDetails> searchMatching(Pageable pageable, MatchingSearchCondition searchCondition);
}
