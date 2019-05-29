package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Project extends TranslatableEntity<BaseProject> {

    @NotNull(groups = ToValidate.class)
    private String name;

    @NotNull(groups = ToValidate.class)
    @Size(min = 20, max = 200, groups = ToValidate.class)
    private String description;

    private String status;

    public Image getImage() {
        return getBaseEntity().getImage();
    }

    public void setImage(Image image) {
        getBaseEntity().setImage(image);
    }

    public String getLinks() {
        return getBaseEntity().getLinks();
    }

    public void setLinks(String links) {
        getBaseEntity().setLinks(links);
    }
}
