package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
