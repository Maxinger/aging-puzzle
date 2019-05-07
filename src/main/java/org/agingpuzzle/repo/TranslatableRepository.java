package org.agingpuzzle.repo;

import org.agingpuzzle.model.TranslatableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoRepositoryBean
public interface TranslatableRepository<T extends TranslatableEntity> extends JpaRepository<T, Long> {

    Optional<T> findByBaseEntity_IdAndLanguage(Long id, String language);

    List<T> findAllByLanguage(String language);

    @Query("select e.baseEntity.id from #{#entityName} e where e.language = ?1")
    Set<Long> findAllIdsByLanguage(String language);
}
