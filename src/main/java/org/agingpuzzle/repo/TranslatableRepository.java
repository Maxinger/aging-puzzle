package org.agingpuzzle.repo;

import org.agingpuzzle.model.TranslatableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

@NoRepositoryBean
public interface TranslatableRepository<T extends TranslatableEntity> extends AbstractRepository<T> {

    Optional<T> findByBaseEntity_IdAndLanguage(Long id, String language);

    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<T> findAllByLanguage(String language);

    int countByLanguage(String language);

    @Query("select e.baseEntity.id from #{#entityName} e where e.language = ?1")
    Set<Long> findAllIdsByLanguage(String language);
}
