package org.agingpuzzle.repo;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.model.view.Statistics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

public interface ProjectRepository extends NamedRepository<Project> {

    @Query("select p from Project p" +
            " where p.baseEntity.baseOrganization.id = ?1" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Project> findAllByOrganization(Long baseOrganizationId, String lang);

    @Query("select p from Project p" +
            " where p.baseEntity.baseArea.id = ?1" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Project> findAllByArea(Long baseAreaId, String lang, Pageable pageable);

    @Query("select count(p) from Project p" +
            " where p.baseEntity.baseArea.id = ?1" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    int countByArea(Long baseAreaId, String lang);

    @Query("select new org.agingpuzzle.model.view.Statistics(a.id, count(p)) from Project p, Area a" +
            " where p.language = ?1" +
            " and a.language = ?1" +
            " and p.baseEntity.baseArea = a.baseEntity" +
            " group by a")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Statistics> getCountsByArea(String lang);
}
