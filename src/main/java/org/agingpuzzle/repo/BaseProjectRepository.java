package org.agingpuzzle.repo;

import org.agingpuzzle.model.BaseProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseProjectRepository extends JpaRepository<BaseProject, Long> {
}
