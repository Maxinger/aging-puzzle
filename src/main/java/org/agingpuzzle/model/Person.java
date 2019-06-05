package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Person extends TranslatableEntity<BasePerson> implements WithImage {

    @NotNull(groups = ToValidate.class)
    private String name;

    @NotNull(groups = ToValidate.class)
    @Size(min = 20, max = 200, groups = ToValidate.class)
    private String description;

    public String getLinks() {
        return getBaseEntity().getLinks();
    }

    public void setLinks(String links) {
        getBaseEntity().setLinks(links);
    }
}
