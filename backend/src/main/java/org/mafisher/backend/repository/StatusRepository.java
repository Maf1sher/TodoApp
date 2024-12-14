package org.mafisher.backend.repository;

import org.mafisher.backend.entity.StatusEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatusRepository extends CrudRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByName(String name);
}
