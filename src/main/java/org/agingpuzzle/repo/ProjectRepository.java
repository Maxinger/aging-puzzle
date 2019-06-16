package org.agingpuzzle.repo;

import org.agingpuzzle.model.Project;
import org.agingpuzzle.model.view.Statistics;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends TranslatableRepository<Project> {

    @Query("select p from Project p" +
            " where p.baseEntity.baseArea.id = ?1" +
            " and p.language = ?2")
    List<Project> findAllByArea(Long baseAreaId, String lang);

    @Query("select new org.agingpuzzle.model.view.Statistics(a.id, count(p)) from Project p, Area a" +
            " where p.language = ?1" +
            " and a.language = ?1" +
            " and p.baseEntity.baseArea = a.baseEntity" +
            " group by p")
    List<Statistics> getCountsByArea(String lang);
}
