package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Photo extends AbstractEntity {

    @NotNull(groups = ToValidate.class)
    private String path;

    private String source;

}
