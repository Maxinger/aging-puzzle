package org.agingpuzzle.repo;

import org.agingpuzzle.model.BaseOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseOrganizationRepository extends JpaRepository<BaseOrganization, Long> {
}
