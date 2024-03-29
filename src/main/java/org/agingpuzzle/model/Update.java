package org.agingpuzzle.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "update_", uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Update extends TranslatableEntity<BaseUpdate> implements WithImage {

    @NotNull
    private String title;

    private String preview;

    @NotNull
    @Size(min = 20)
    @Lob
    private String fullText;

}
