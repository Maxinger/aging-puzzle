package org.agingpuzzle.repo;

import org.agingpuzzle.model.Member;
import org.agingpuzzle.model.OrganizationMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {

    @Query("select new org.agingpuzzle.model.OrganizationMember(p, m.role) from Person p, Member m" +
            " where p.baseEntity = m.basePerson" +
            " and m.baseOrganization.id = ?1" +
            " and p.language = ?2")
    List<OrganizationMember> findOrganizationMembers(Long baseOrganizationId, String lang);

}
