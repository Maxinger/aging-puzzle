package by.maxi.puzzle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
public abstract class TranslatableEntity<T> extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    @NotNull
    private T baseEntity;

    @NotNull
    @Size(max = 2)
    private String language;

}
