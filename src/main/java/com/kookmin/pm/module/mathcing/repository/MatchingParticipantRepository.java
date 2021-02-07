package com.kookmin.pm.module.mathcing.repository;

import com.kookmin.pm.module.mathcing.domain.MatchingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingParticipantRepository extends JpaRepository<MatchingParticipant, Long> {
}
