package org.agingpuzzle.web.mapper;

import org.agingpuzzle.model.Member;
import org.agingpuzzle.web.form.MemberForm;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = EntityMapper.class,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class MemberMapper {

    @Mappings({
            @Mapping(source = "baseProject.id", target = "baseEntityId"),
            @Mapping(source = "basePerson.id", target = "basePersonId"),
            @Mapping(expression = "java(member.getRole().split(\",\"))", target = "rolesSelected"),
    })
    public abstract MemberForm projectMemberToForm(Member member);

    @Mappings({
            @Mapping(source = "baseEntityId", target = "baseProject"),
            @Mapping(source = "basePersonId", target = "basePerson"),
            @Mapping(expression = "java(String.join(\",\", form.getRolesSelected()))", target = "role"),
    })
    public abstract void formToProjectMember(MemberForm form, @MappingTarget Member person);

}
