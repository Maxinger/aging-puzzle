package org.agingpuzzle.repo;

import org.agingpuzzle.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
