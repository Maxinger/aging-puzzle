package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Organization extends TranslatableEntity<BaseOrganization> implements WithImage {

    @NotNull
    private String name;

    @NotNull
    @Size(min = 20)
    private String description;

    private String location;

    public String getLinks() {
        return getBaseEntity().getLinks();
    }

    public void setLinks(String links) {
        getBaseEntity().setLinks(links);
    }
}
