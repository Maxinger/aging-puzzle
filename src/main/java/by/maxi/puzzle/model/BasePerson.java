package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class BasePerson extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Image image;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Link> links;

}
