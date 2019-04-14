package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class PuzzleConfig extends AbstractEntity {

    @NotNull
    private String layout;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

}
