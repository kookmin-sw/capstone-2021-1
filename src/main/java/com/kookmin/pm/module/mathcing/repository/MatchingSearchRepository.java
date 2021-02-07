package com.kookmin.pm.module.mathcing.repository;

import com.kookmin.pm.module.member.domain.Member;

import java.util.List;

public interface MatchingSearchRepository {
    public List<Member> searchMemberInMatchingParticipant(Long matchingId);
}
