package org.agingpuzzle.repo;

import org.agingpuzzle.model.Update;
import org.agingpuzzle.model.view.UpdateView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

public interface UpdateRepository extends TranslatableRepository<Update> {

    @Query("select new org.agingpuzzle.model.view.UpdateView(u, o, p) from Update u" +
            " left join Organization o on o.baseEntity = u.baseEntity.baseOrganization and o.language = u.language" +
            " left join Project p on p.baseEntity = u.baseEntity.baseProject and p.language = u.language" +
            " where u.language = ?1")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<UpdateView> viewAllByLanguage(String lang, Pageable pageable);

    @Query("select new org.agingpuzzle.model.view.UpdateView(u, o, p) from Update u" +
            " left join Organization o on o.baseEntity = u.baseEntity.baseOrganization and o.language = u.language" +
            " left join Project p on p.baseEntity = u.baseEntity.baseProject and p.language = u.language" +
            " where p.baseEntity.id = ?1 and u.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<UpdateView> viewAllByProject(Long baseProjectId, String lang, Pageable pageable);

    @Query("select count(u) from Update u" +
            " left join Project p on p.baseEntity = u.baseEntity.baseProject and p.language = u.language" +
            " where p.baseEntity.id = ?1 and u.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    int countAllByProject(Long baseProjectId, String lang);

    @Query("select new org.agingpuzzle.model.view.UpdateView(u, o, p) from Update u" +
            " left join Organization o on o.baseEntity = u.baseEntity.baseOrganization and o.language = u.language" +
            " left join Project p on p.baseEntity = u.baseEntity.baseProject and p.language = u.language" +
            " where o.baseEntity.id = ?1 and u.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<UpdateView> viewAllByOrganization(Long baseOrganizationId, String lang, Pageable pageable);

    @Query("select count(u) from Update u" +
            " left join Organization o on o.baseEntity = u.baseEntity.baseOrganization and o.language = u.language" +
            " where o.baseEntity.id = ?1 and u.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    int countAllByOrganization(Long baseOrganizationId, String lang);

    default Pageable page(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "baseEntity.date"));
    }
}
