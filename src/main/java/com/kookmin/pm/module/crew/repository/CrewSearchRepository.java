package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.member.domain.Member;

import java.util.List;

public interface CrewSearchRepository {
    public List<Member> findMemberInCrewParticipants(Long crewId);
}
