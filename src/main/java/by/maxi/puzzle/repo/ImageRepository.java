package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
