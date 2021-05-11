package com.kookmin.pm.module.image.repository;

import com.kookmin.pm.module.image.domain.ImageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageName, Long> {
    public List<ImageName> findByRelatedId(Long relatedId);
}
