package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Project extends AbstractEntity {

    private String name;

    private String description;

    private String type;

    private String status;

    @ManyToOne
    @JoinColumn
    private Organization organization;

    @ManyToOne
    @JoinColumn
    private Area area;

    @ManyToMany
    private List<Member> members;

}
