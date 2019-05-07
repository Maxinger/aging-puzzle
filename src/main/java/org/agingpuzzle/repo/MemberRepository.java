package org.agingpuzzle.repo;

import org.agingpuzzle.model.Member;
import org.agingpuzzle.model.Membership;
import org.agingpuzzle.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select new org.agingpuzzle.model.Membership(m.id, p, m.role) from Person p, Member m" +
            " where p.baseEntity = m.basePerson" +
            " and m.baseOrganization.id = ?1" +
            " and p.language = ?2")
    List<Membership<Person>> findPersonsByOrganization(Long baseOrganizationId, String lang);

    @Query("select new org.agingpuzzle.model.Membership(m.id, o, m.role) from Organization o, Member m" +
            " where o.baseEntity = m.baseOrganization" +
            " and m.basePerson.id = ?1" +
            " and o.language = ?2")
    List<Membership<Person>> findOrganizationsByPerson(Long basePersonId, String lang);

}
