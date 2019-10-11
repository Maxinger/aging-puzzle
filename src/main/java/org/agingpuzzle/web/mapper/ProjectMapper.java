package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseProject;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.web.form.ProjectForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public abstract class ProjectMapper {

    @Mappings({
            @Mapping(source = "baseEntity.baseArea.id", target = "baseAreaId"),
            @Mapping(source = "baseEntity.baseOrganization.id", target = "baseOrganizationId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract ProjectForm projectToForm(Project project);


    @Mappings({
            @Mapping(ignore = true, target = "id"),
            @Mapping(source = "id", target = "baseId"),
            @Mapping(source = "baseArea.id", target = "baseAreaId"),
            @Mapping(source = "baseOrganization.id", target = "baseOrganizationId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract ProjectForm baseProjectToForm(BaseProject baseProject);


    @Mappings({
            @Mapping(source = "baseAreaId", target = "baseEntity.baseArea"),
            @Mapping(source = "baseOrganizationId", target = "baseEntity.baseOrganization"),
    })
    public abstract void formToProject(ProjectForm form, @MappingTarget Project project);
}
