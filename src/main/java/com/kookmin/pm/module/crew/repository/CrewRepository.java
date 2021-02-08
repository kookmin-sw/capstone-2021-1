package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.crew.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew,Long> {
}
