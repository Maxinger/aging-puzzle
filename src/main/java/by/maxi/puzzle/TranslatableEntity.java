package by.maxi.puzzle;

import by.maxi.puzzle.model.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class TranslatableEntity<T> extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    @NotNull
    private T baseEntity;

    @NotNull
    private String language;

}
