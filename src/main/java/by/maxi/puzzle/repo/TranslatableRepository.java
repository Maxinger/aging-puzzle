package by.maxi.puzzle.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface TranslatableRepository<T> extends CrudRepository<T, Long> {

    Optional<T> findByBaseEntity_IdAndLanguage(Long id, String language);

    Iterable<T> findAllByLanguage(String language);
}
