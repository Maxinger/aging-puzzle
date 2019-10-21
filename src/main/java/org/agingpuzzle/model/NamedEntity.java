package org.agingpuzzle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public class NamedEntity<T extends AbstractEntity> extends TranslatableEntity<T> {

    @NotNull
    private String name;

}
