package org.agingpuzzle.repo;

import org.agingpuzzle.model.Project;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends TranslatableRepository<Project> {

    @Query("select p from Project p" +
            " where p.baseEntity.baseArea.id = ?1" +
            " and p.language = ?2")
    List<Project> findAllByArea(Long baseAreaId, String lang);
}
