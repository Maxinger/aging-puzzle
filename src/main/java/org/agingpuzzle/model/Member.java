package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Member extends AbstractEntity {

    public enum Role {
        FOUNDER, LEAD
    }

    @ManyToOne
    @JoinColumn
    private BasePerson basePerson;

    @ManyToOne
    @JoinColumn
    private BaseOrganization baseOrganization;

    @Enumerated(EnumType.STRING)
    private Role role;

}