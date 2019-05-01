package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"base_entity_id", "language"}))
public class Organization extends TranslatableEntity<BaseOrganization> {

    @NotNull(groups = ToValidate.class)
    private String name;

    @NotNull(groups = ToValidate.class)
    @Size(min = 20, groups = ToValidate.class)
    private String description;

    private String location;

    @ManyToMany(cascade = CascadeType.ALL)
//    @Where(clause = "language = 'EN'")
    private List<Member> members;

}
