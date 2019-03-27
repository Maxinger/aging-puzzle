package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Member extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    private Person person;

    private String role;

}
