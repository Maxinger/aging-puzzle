package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Organization extends NamedEntity<BaseOrganization> implements WithImage {

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
