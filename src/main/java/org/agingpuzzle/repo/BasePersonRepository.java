package org.agingpuzzle.repo;

import org.agingpuzzle.model.BasePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasePersonRepository extends JpaRepository<BasePerson, Long> {
}
