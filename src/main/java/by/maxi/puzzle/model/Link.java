package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Link {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String url;

}
