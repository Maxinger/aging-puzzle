package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class BaseProject extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    @NotNull
    private BaseArea baseArea;

    @ManyToOne
    @JoinColumn
    private BaseOrganization baseOrganization;

    @OneToOne
    @JoinColumn
    private Image image;

    private String links;
}
