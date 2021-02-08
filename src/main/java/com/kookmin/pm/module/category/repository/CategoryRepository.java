package com.kookmin.pm.module.category.repository;

import com.kookmin.pm.module.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    void deleteByName(String name);
}
