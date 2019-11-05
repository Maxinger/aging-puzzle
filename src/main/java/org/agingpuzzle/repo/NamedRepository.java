package org.agingpuzzle.repo;

import org.agingpuzzle.model.NamedEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.QueryHint;
import java.util.Optional;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

@NoRepositoryBean
public interface NamedRepository<T extends NamedEntity> extends TranslatableRepository<T> {

    @Query("select e.name from #{#entityName} e where e.baseEntity.id = ?1 and e.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    Optional<String> getName(Long baseId, String language);
}
