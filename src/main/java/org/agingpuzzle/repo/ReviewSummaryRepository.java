package org.agingpuzzle.repo;

import org.agingpuzzle.model.view.ReviewSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, Long> {
}
