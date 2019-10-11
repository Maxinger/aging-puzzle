package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseProject;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.repo.BaseProjectRepository;
import org.agingpuzzle.web.form.ProjectForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Autowired
    protected BaseProjectRepository baseProjectRepository;

    @Mappings({
            @Mapping(source = "baseEntity.parent.id", target = "parentId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract ProjectForm projectToForm(Project project);


    @Mappings({
            @Mapping(source = "id", target = "baseId"),
            @Mapping(source = "baseArea.id", target = "baseAreaId"),
            @Mapping(source = "baseOrganization.id", target = "baseOrganizationId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract ProjectForm baseProjectToForm(BaseProject project);


    @Mappings({
            @Mapping(source = "parentId", target = "baseEntity.parent"),
    })
    public abstract void formToProject(ProjectForm form, @MappingTarget Project project);

    public BaseProject baseProjectById(Long id) {
        return baseProjectRepository.safeFindById(id);
    }

}
