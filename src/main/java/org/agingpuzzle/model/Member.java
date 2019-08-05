package org.agingpuzzle.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Member extends AbstractEntity {

    public enum Role {
        FOUNDER, LEAD, CEO;

        public static List<String> getValues() {
            return Arrays.stream(Member.Role.values()).map(Member.Role::name).collect(Collectors.toList());
        }
    }

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private BasePerson basePerson;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseOrganization baseOrganization;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseProject baseProject;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

}