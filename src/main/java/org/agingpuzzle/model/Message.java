package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Message extends AbstractEntity {

    private String type;
    private String language;
    private String key;
    private String value;
}
