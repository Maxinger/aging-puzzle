package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Person extends TranslatableEntity<BasePerson> {

    @NotNull(groups = ToValidate.class)
    private String name;

    @NotNull(groups = ToValidate.class)
    @Size(min = 20, max = 200, groups = ToValidate.class)
    private String description;

    public Image getImage() {
        return getBaseEntity().getImage();
    }

    public void setImage(Image image) {
        getBaseEntity().setImage(image);
    }

    public List<Link> getLinks() {
        return getBaseEntity().getLinks();
    }

    public void setLinks(List<Link> links) {
        getBaseEntity().setLinks(links);
    }
}
