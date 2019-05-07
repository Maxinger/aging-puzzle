package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Member extends AbstractEntity {

    public enum Role {
        FOUNDER, LEAD;

        public static List<String> getValues() {
            return Arrays.stream(Member.Role.values()).map(Member.Role::name).collect(Collectors.toList());
        }
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private BasePerson basePerson;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn
    private BaseOrganization baseOrganization;

    @Enumerated(EnumType.STRING)
    private Role role;

}