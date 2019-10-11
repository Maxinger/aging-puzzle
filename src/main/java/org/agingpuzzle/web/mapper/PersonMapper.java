package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.BasePerson;
import org.agingpuzzle.model.Person;
import org.agingpuzzle.web.form.PersonForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    @Mappings({
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract PersonForm personToForm(Person person);

    @Mappings({
            @Mapping(ignore = true, target = "id"),
            @Mapping(source = "id", target = "baseId"),
            @Mapping(source = "image.path", target = "imagePath"),
            @Mapping(source = "image.source", target = "imageSource"),
    })
    public abstract PersonForm basePersonToForm(BasePerson basePerson);

    public abstract void formToPerson(PersonForm form, @MappingTarget Person person);

}
