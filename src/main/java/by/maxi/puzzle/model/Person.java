package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Person extends AbstractEntity {

    @NotNull
    private String language;

    private String name;

    private String description;
}
