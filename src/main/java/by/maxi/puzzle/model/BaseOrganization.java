package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class BaseOrganization extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Link> links;
}
