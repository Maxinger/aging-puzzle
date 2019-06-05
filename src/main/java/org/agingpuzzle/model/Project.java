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
public class Project extends TranslatableEntity<BaseProject> implements WithImage {

    @NotNull(groups = ToValidate.class)
    private String name;

    @NotNull(groups = ToValidate.class)
    @Size(min = 20, max = 200, groups = ToValidate.class)
    private String description;

    private String status;

    public String getLinks() {
        return getBaseEntity().getLinks();
    }

    public void setLinks(String links) {
        getBaseEntity().setLinks(links);
    }
}
