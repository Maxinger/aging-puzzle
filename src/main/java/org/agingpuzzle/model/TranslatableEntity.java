package org.agingpuzzle.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
public abstract class TranslatableEntity<T extends AbstractEntity> extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private T baseEntity;

    @NotNull
    @Size(max = 2)
    private String language;

    public Long getBaseId() {
        return baseEntity.getId();
    }

}
