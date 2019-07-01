package org.agingpuzzle.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseUpdate extends AbstractEntity implements WithImage {

    private LocalDate date;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseOrganization baseOrganization;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BaseProject baseProject;

    @Override
    public Image getImage() {
        return baseOrganization != null
                ? baseOrganization.getImage()
                : baseProject != null
                        ? baseProject.getImage()
                        : null;
    }

    @Override
    public void setImage(Image image) {
        throw new UnsupportedOperationException();
    }
}
