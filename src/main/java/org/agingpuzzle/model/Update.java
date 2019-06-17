package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Update extends TranslatableEntity<BaseUpdate> implements WithImage {

    private String title;
    private String preview;
    private String fullText;
}
