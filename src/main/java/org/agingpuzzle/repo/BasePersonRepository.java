package org.agingpuzzle.repo;

import org.agingpuzzle.model.BasePerson;
import org.springframework.data.repository.CrudRepository;

public interface BasePersonRepository extends CrudRepository<BasePerson, Long> {
}
