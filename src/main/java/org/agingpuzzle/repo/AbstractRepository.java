package org.agingpuzzle.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractRepository<T> extends JpaRepository<T, Long> {

    default T safeFindById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(this::findById)
                .orElse(null);
    }

}
