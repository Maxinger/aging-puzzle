package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseOrganization;
import org.agingpuzzle.model.BaseProject;
import org.agingpuzzle.model.Update;
import org.agingpuzzle.repo.BaseOrganizationRepository;
import org.agingpuzzle.repo.BaseProjectRepository;
import org.agingpuzzle.web.form.UpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UpdateMapper {

    @Autowired
    protected BaseOrganizationRepository baseOrganizationRepository;

    @Autowired
    protected BaseProjectRepository baseProjectRepository;

    @Mappings({
            @Mapping(source = "baseEntity.date", target = "date"),
            @Mapping(source = "baseEntity.baseOrganization.id", target = "baseOrganizationId"),
            @Mapping(source = "baseEntity.baseProject.id", target = "baseProjectId"),
    })
    public abstract UpdateForm updateToForm(Update update);

    @Mappings({
            @Mapping(source = "date", target = "baseEntity.date"),
            @Mapping(source = "baseOrganizationId", target = "baseEntity.baseOrganization"),
            @Mapping(source = "baseProjectId", target = "baseEntity.baseProject"),
    })
    public abstract void formToUpdate(UpdateForm form, @MappingTarget Update update);

    public BaseOrganization baseOrganizationById(Long id) {
        return baseOrganizationRepository.safeFindById(id);
    }

    public BaseProject baseProjectById(Long id) {
        return baseProjectRepository.safeFindById(id);
    }
}
