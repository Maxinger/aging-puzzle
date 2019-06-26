package org.agingpuzzle.repo;

import org.agingpuzzle.model.Update;
import org.agingpuzzle.model.view.UpdateView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpdateRepository extends TranslatableRepository<Update> {

    @Query("select new org.agingpuzzle.model.view.UpdateView(u, o, p) from Update u" +
            " left join Organization o on o.baseEntity = u.baseEntity.baseOrganization and o.language = u.language" +
            " left join Project p on p.baseEntity = u.baseEntity.baseProject and p.language = u.language" +
            " where u.language = ?1")
    List<UpdateView> viewAllByLanguage(String lang, Pageable pageable);

    default Pageable page(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "baseEntity.date"));
    }
}
