package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.BasePerson;
import org.springframework.data.repository.CrudRepository;

public interface BasePersonRepository extends CrudRepository<BasePerson, Long> {
}
