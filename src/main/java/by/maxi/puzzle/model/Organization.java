package by.maxi.puzzle.model;

import lombok.Data;
import org.hibernate.annotations.Filter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Organization extends AbstractEntity {

    private String name;

    private String description;

    private String location;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Link> links;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Member> members;

}
