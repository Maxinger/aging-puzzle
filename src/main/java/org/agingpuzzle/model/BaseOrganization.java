package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Entity
public class BaseOrganization extends AbstractEntity implements WithImage {

    private LocalDate dateCreated = LocalDate.now();

    @ManyToOne
    @JoinColumn
    private BaseOrganization parent;

    @OneToOne
    @JoinColumn
    private Image image;

    private String links;
}
