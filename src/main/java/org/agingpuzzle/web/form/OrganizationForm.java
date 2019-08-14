package org.agingpuzzle.web.form;

import lombok.Data;
import org.agingpuzzle.model.ToValidate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OrganizationForm extends AbstractForm {

    private Long baseId;

    private Long parentId;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 20, groups = ToValidate.class)
    private String description;

    private String location;

    private String links;

    private String imagePath;

    private String imageSource;
}
