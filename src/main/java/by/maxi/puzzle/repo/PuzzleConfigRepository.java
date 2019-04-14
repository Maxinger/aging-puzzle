package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.PuzzleConfig;
import org.springframework.data.repository.CrudRepository;

public interface PuzzleConfigRepository extends CrudRepository<PuzzleConfig, Long> {

    PuzzleConfig findFirstByOrderByCreatedAtDesc();
}
