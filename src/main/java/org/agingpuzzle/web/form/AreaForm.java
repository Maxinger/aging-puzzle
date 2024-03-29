package org.agingpuzzle.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AreaForm extends AbstractForm {

    private Long baseId;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 20)
    private String description;

    private String imagePath;

    private String imageSource;
}
