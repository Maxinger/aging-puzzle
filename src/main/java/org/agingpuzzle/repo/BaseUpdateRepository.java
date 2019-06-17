package org.agingpuzzle.repo;

import org.agingpuzzle.model.BaseUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUpdateRepository extends JpaRepository<BaseUpdate, Long> {
}
