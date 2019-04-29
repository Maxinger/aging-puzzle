package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.TranslatableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.Set;

@NoRepositoryBean
public interface TranslatableRepository<T extends TranslatableEntity> extends CrudRepository<T, Long> {

    Optional<T> findByBaseEntity_IdAndLanguage(Long id, String language);

    Iterable<T> findAllByLanguage(String language);

    @Query("select e.baseEntity.id from #{#entityName} e where e.language = ?1")
    Set<Long> findAllIdsByLanguage(String language);
}
