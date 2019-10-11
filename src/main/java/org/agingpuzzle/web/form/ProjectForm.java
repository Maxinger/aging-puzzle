package org.agingpuzzle.web.form;

import lombok.Data;
import org.agingpuzzle.model.ToValidate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProjectForm extends AbstractForm {

    private Long baseId;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 20, groups = ToValidate.class)
    private String description;

    private Long baseAreaId;

    private Long baseOrganizationId;

    private String status;

    private String imagePath;

    private String imageSource;

    private String links;
}