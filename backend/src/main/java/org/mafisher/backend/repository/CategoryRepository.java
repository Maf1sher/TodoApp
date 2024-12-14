package org.mafisher.backend.repository;

import org.mafisher.backend.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByUserId(Long userId);
}
