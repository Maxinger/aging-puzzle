package org.agingpuzzle.repo;

import org.agingpuzzle.model.Update;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UpdateRepository extends TranslatableRepository<Update> {

    @Query("select u from Update u" +
            " where u.language = ?1")
    List<Update> findAllByLanguage(String lang, Pageable pageable);

    default Pageable page(int page, int size) {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "baseEntity.date"));
    }
}
