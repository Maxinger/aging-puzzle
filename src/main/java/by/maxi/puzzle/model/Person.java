package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Person extends AbstractEntity {

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Link> links;

}
