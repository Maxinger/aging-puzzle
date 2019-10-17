package org.agingpuzzle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractEntity {

    private String type;

    @Column(name = "key_")
    private String key;

    private String language;
    private String text;
}
