package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.web.form.OrganizationForm;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = EntityMapper.class,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class OrganizationMapper {

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

}
