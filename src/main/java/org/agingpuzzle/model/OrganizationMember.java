package org.agingpuzzle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrganizationMember {

    private Person person;
    private Member.Role role;
}
