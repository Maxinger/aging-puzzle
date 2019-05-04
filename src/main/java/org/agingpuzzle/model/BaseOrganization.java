package org.agingpuzzle.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class BaseOrganization extends AbstractEntity {

    private String links;
}
