package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseArea;
import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.BasePerson;
import org.agingpuzzle.model.BaseProject;
import org.agingpuzzle.repo.BaseAreaRepository;
import org.agingpuzzle.repo.BaseOrganizationRepository;
import org.agingpuzzle.repo.BasePersonRepository;
import org.agingpuzzle.repo.BaseProjectRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class EntityMapper {

    @Autowired
    protected BaseAreaRepository baseAreaRepository;

    @Autowired
    protected BaseOrganizationRepository baseOrganizationRepository;

    @Autowired
    protected BasePersonRepository basePersonRepository;

    @Autowired
    protected BaseProjectRepository baseProjectRepository;

    public BaseArea baseAreaById(Long id) {
        return baseAreaRepository.safeFindById(id);
    }

    public BaseOrganization baseOrganizationById(Long id) {
        return baseOrganizationRepository.safeFindById(id);
    }

    public BasePerson basePersonById(Long id) {
        return basePersonRepository.safeFindById(id);
    }

    public BaseProject baseProjectById(Long id) {
        return baseProjectRepository.safeFindById(id);
    }
}
