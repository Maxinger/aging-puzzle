package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
public class BaseOrganization extends AbstractEntity implements WithImage {

    @OneToOne
    @JoinColumn
    private Image image;

    private String links;
}
