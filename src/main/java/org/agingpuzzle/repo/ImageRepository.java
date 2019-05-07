package org.agingpuzzle.repo;

import org.agingpuzzle.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
