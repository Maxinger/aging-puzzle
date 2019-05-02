package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Image extends AbstractEntity {

    @NotNull
    private String path;

    private String source;

}
