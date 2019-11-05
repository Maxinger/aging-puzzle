package org.agingpuzzle.repo;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.annotations.QueryHints.CACHEABLE;

public interface OrganizationRepository extends NamedRepository<Organization> {

    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Organization> findAllByLanguageAndBaseEntity_IdNot(String language, Long id);

    @QueryHints(@QueryHint(name = CACHEABLE, value = "true"))
    List<Organization> findAllByLanguageAndBaseEntityParent(String language, BaseOrganization parent);

}
