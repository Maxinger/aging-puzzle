package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.repo.BaseOrganizationRepository;
import org.agingpuzzle.web.form.OrganizationForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class OrganizationMapper {

    @Autowired
    protected BaseOrganizationRepository baseOrganizationRepository;

    @Mappings({
            @Mapping(source = "baseEntity.parent.id", target = "parentId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract OrganizationForm organizationToForm(Organization organization);

    @Mappings({
            @Mapping(ignore = true, target = "id"),
            @Mapping(source = "id", target = "baseId"),
            @Mapping(source = "parent.id", target = "parentId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract OrganizationForm baseOrganizationToForm(BaseOrganization baseOrganization);

    @Mappings({
            @Mapping(source = "parentId", target = "baseEntity.parent"),
    })
    public abstract void formToOrganization(OrganizationForm form, @MappingTarget Organization organization);

    public BaseOrganization baseOrganizationById(Long id) {
        return baseOrganizationRepository.safeFindById(id);
    }

}
