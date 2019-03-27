package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Area extends AbstractEntity {

    private String name;

    private String description;



}
