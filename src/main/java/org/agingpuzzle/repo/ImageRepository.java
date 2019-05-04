package org.agingpuzzle.repo;

import org.agingpuzzle.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
