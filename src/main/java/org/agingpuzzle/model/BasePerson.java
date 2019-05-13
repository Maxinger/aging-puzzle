package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BasePerson extends AbstractEntity {

    @OneToOne
    @JoinColumn
    private Image image;

    private String links;

}
