package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Update extends TranslatableEntity<BaseUpdate> implements WithImage {

    @NotNull(groups = ToValidate.class)
    private String title;

    private String preview;

    @NotNull(groups = ToValidate.class)
    @Size(min = 20, max = 200, groups = ToValidate.class)
    private String fullText;
}
