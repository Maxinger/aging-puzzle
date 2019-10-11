package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.Area;
import org.agingpuzzle.web.form.AreaForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class AreaMapper {

    @Mappings({
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract AreaForm areaToForm(Area update);

    public abstract void formToArea(AreaForm form, @MappingTarget Area area);

}
