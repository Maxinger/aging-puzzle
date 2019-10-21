package org.agingpuzzle.repo;

import org.agingpuzzle.model.Person;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends TranslatableRepository<Person> {

    @Query("select p from Person p" +
            " where p.baseEntity not in (select m.basePerson from Member m where m.baseProject.id = ?1)" +
            " and p.language = ?2")
    List<Person> findCandidatesForProject(Long baseProjectId, String lang);

    @Query("select p from Person p" +
            " where p.baseEntity not in (select m.basePerson from Member m where m.baseOrganization.id = ?1)" +
            " and p.language = ?2")
    List<Person> findCandidatesForOrganization(Long baseOrganizationId, String lang);
}
