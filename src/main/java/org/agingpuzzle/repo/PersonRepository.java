package org.agingpuzzle.repo;

import org.agingpuzzle.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

public interface PersonRepository extends NamedRepository<Person> {

    @Query("select p from Person p" +
            " where p.baseEntity not in (select m.basePerson from Member m where m.baseProject.id = ?1)" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Person> findCandidatesForProject(Long baseProjectId, String lang);

    @Query("select p from Person p" +
            " where p.baseEntity not in (select m.basePerson from Member m where m.baseOrganization.id = ?1)" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Person> findCandidatesForOrganization(Long baseOrganizationId, String lang);
}
