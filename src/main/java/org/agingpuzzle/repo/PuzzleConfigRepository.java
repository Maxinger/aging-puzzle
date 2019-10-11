package org.agingpuzzle.repo;

import org.agingpuzzle.model.PuzzleConfig;

public interface PuzzleConfigRepository extends AbstractRepository<PuzzleConfig> {

    PuzzleConfig findFirstByOrderByCreatedAtDesc();
}
