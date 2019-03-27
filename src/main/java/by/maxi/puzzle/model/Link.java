package by.maxi.puzzle.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Link extends AbstractEntity {

    private String url;

}
