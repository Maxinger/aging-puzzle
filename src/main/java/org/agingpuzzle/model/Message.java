package org.agingpuzzle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractEntity {

    private String type;
    private String key;
    private String language;
    private String value;
}
