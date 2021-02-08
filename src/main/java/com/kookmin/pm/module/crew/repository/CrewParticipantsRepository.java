package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.crew.domain.CrewParticipants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewParticipantsRepository extends JpaRepository<CrewParticipants, Long> {
}
