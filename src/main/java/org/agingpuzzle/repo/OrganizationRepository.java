package org.agingpuzzle.repo;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.Person;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizationRepository extends NamedRepository<Organization> {

    List<Organization> findAllByLanguageAndBaseEntity_IdNot(String language, Long id);

    List<Organization> findAllByLanguageAndBaseEntityParent(String language, BaseOrganization parent);

}
