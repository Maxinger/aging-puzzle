package org.agingpuzzle.repo;

import org.agingpuzzle.model.PuzzleConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleConfigRepository extends JpaRepository<PuzzleConfig, Long> {

    PuzzleConfig findFirstByOrderByCreatedAtDesc();
}
