package org.agingpuzzle.web.form;

import org.agingpuzzle.model.Update;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FormMapper {

    @Mappings({
        @Mapping(source = "baseEntity.date", target = "date")
    })
    UpdateForm updateToForm(Update update);

}
