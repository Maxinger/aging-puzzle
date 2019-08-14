package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
public class BaseOrganization extends AbstractEntity implements WithImage {

    @ManyToOne
    @JoinColumn
    private BaseOrganization parent;

    @OneToOne
    @JoinColumn
    private Image image;

    private String links;
}
