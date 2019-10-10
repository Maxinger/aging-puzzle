package org.agingpuzzle.web.form;

import org.agingpuzzle.model.Update;
import org.agingpuzzle.repo.BaseOrganizationRepository;
import org.agingpuzzle.repo.BaseProjectRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class FormMapper {

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
            @Mapping(expression = "java(baseOrganizationRepository.safeFindById(updateForm.getBaseOrganizationId()))", target = "baseEntity.baseOrganization"),
            @Mapping(expression = "java(baseProjectRepository.safeFindById(updateForm.getBaseProjectId()))", target = "baseEntity.baseProject"),
    })
    public abstract void formToUpdate(UpdateForm form, @MappingTarget Update update);

}
