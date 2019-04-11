package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Area extends AbstractEntity {

    @NotNull
    private String name;

    @NotNull
    @Size(min = 20)
    private String description;

}
