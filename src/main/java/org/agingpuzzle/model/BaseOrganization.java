package org.agingpuzzle.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
