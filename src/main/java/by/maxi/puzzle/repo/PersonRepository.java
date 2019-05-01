package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends TranslatableRepository<Person> {
}
