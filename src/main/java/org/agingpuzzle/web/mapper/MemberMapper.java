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
            @Mapping(expression = "java(splitByComma(member.getRole()))", target = "rolesSelected"),
    })
    public abstract MemberForm projectMemberToForm(Member member);

    @Mappings({
            @Mapping(source = "baseEntityId", target = "baseProject"),
            @Mapping(source = "basePersonId", target = "basePerson"),
            @Mapping(expression = "java(joinWithComma(form.getRolesSelected()))", target = "role"),
    })
    public abstract void formToProjectMember(MemberForm form, @MappingTarget Member person);

    @Mappings({
            @Mapping(source = "baseOrganization.id", target = "baseEntityId"),
            @Mapping(source = "basePerson.id", target = "basePersonId"),
            @Mapping(expression = "java(splitByComma(member.getRole()))", target = "rolesSelected"),
    })
    public abstract MemberForm organizationMemberToForm(Member member);

    @Mappings({
            @Mapping(source = "baseEntityId", target = "baseOrganization"),
            @Mapping(source = "basePersonId", target = "basePerson"),
            @Mapping(expression = "java(joinWithComma(form.getRolesSelected()))", target = "role"),
    })
    public abstract void formToOrganizationMember(MemberForm form, @MappingTarget Member person);

    protected String[] splitByComma(String str) {
        return str.split(",");
    }

    protected String joinWithComma(String[] strings) {
        return String.join(",", strings);
    }

}
