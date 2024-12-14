package org.mafisher.backend.repository;

import org.mafisher.backend.entity.CategoryEntity;
import org.mafisher.backend.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    List<TaskEntity> findByUserId(Long userId);
}
