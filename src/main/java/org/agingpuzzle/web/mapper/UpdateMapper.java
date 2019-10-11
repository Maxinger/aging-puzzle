package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BaseUpdate;
import org.agingpuzzle.model.Update;
import org.agingpuzzle.web.form.UpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public abstract class UpdateMapper {

    @Mappings({
            @Mapping(source = "baseEntity.date", target = "date"),
            @Mapping(source = "baseEntity.baseOrganization.id", target = "baseOrganizationId"),
            @Mapping(source = "baseEntity.baseProject.id", target = "baseProjectId"),
    })
    public abstract UpdateForm updateToForm(Update update);

    @Mappings({
            @Mapping(ignore = true, target = "id"),
            @Mapping(source = "id", target = "baseId"),
            @Mapping(source = "baseOrganization.id", target = "baseOrganizationId"),
            @Mapping(source = "baseProject.id", target = "baseProjectId"),
    })
    public abstract UpdateForm baseUpdateToForm(BaseUpdate baseUpdate);

    @Mappings({
            @Mapping(source = "date", target = "baseEntity.date"),
            @Mapping(source = "baseOrganizationId", target = "baseEntity.baseOrganization"),
            @Mapping(source = "baseProjectId", target = "baseEntity.baseProject"),
    })
    public abstract void formToUpdate(UpdateForm form, @MappingTarget Update update);
}
