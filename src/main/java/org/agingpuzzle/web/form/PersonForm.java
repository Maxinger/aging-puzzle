package org.agingpuzzle.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PersonForm extends AbstractForm {

    private Long baseId;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 20)
    private String description;

    private String residence;

    private String imagePath;

    private String imageSource;

    private String links;
}
