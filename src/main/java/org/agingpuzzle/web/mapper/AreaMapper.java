package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.Area;
import org.agingpuzzle.model.BaseArea;
import org.agingpuzzle.web.form.AreaForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public abstract class AreaMapper {

    @Mappings({
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract AreaForm areaToForm(Area update);

    @Mappings({
            @Mapping(ignore = true, target = "id"),
            @Mapping(source = "id", target = "baseId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract AreaForm baseAreaToForm(BaseArea baseArea);

    public abstract void formToArea(AreaForm form, @MappingTarget Area area);

}
