package org.agingpuzzle.repo;

import org.agingpuzzle.model.Member;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.Person;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.model.view.Membership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

public interface MemberRepository extends AbstractRepository<Member> {

    @Query("select new org.agingpuzzle.model.view.Membership(m.id, p, m.role) from Person p, Member m" +
            " where p.baseEntity = m.basePerson" +
            " and m.baseOrganization.id = ?1" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Membership<Person>> findPersonsByOrganization(Long baseOrganizationId, String lang);

    @Query("select new org.agingpuzzle.model.view.Membership(m.id, o, m.role) from Organization o, Member m" +
            " where o.baseEntity = m.baseOrganization" +
            " and m.basePerson.id = ?1" +
            " and o.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Membership<Organization>> findOrganizationsByPerson(Long basePersonId, String lang);

    @Query("select new org.agingpuzzle.model.view.Membership(m.id, p, m.role) from Person p, Member m" +
            " where p.baseEntity = m.basePerson" +
            " and m.baseProject.id = ?1" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Membership<Person>> findPersonsByProject(Long baseProjectId, String lang);

    @Query("select new org.agingpuzzle.model.view.Membership(m.id, p, m.role) from Project p, Member m" +
            " where p.baseEntity = m.baseProject" +
            " and m.basePerson.id = ?1" +
            " and p.language = ?2")
    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Membership<Project>> findProjectsByPerson(Long basePersonId, String lang);
}
