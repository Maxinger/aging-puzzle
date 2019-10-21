package org.agingpuzzle.repo;

import org.agingpuzzle.model.NamedEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface NamedRepository<T extends NamedEntity> extends TranslatableRepository<T> {

    @Query("select e.name from #{#entityName} e where e.baseEntity.id = ?1 and e.language = ?2")
    Optional<String> getName(Long baseId, String language);
}
