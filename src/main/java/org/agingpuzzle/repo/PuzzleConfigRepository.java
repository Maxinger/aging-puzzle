package org.agingpuzzle.repo;

import org.agingpuzzle.model.PuzzleConfig;
import org.springframework.data.repository.CrudRepository;

public interface PuzzleConfigRepository extends CrudRepository<PuzzleConfig, Long> {

    PuzzleConfig findFirstByOrderByCreatedAtDesc();
}
