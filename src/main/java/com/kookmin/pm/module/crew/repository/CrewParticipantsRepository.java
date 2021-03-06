package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.crew.domain.CrewParticipantStatus;
import com.kookmin.pm.module.crew.domain.CrewParticipants;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewParticipantsRepository extends JpaRepository<CrewParticipants, Long> {
    public Optional<CrewParticipants> findByMemberAndCrew(Member member, Crew crew);
    public List<CrewParticipants> findByCrewAndStatus(Crew crew, CrewParticipantStatus status);
    public List<CrewParticipants> findByMemberAndStatus(Member member, CrewParticipantStatus status);
    public Long countCrewParticipantsByCrewAndStatus(Crew crew, CrewParticipantStatus status);
    public void deleteAllByCrew(Crew crew);
}
