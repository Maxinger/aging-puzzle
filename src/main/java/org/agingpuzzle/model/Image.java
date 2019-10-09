package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
@Entity
public class Image extends AbstractEntity {

    @NotNull
    private String path;

    private String source;

    public String getSourceUrl() {
        return Optional.ofNullable(source)
                .map(String::toLowerCase)
                .filter(s -> s.startsWith("http"))
                .orElse(null);
    }

}
