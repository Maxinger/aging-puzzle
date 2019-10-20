package org.agingpuzzle.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberForm extends AbstractForm {

    @NotNull
    private Long baseEntityId;

    @NotNull
    private Long basePersonId;

    @NotEmpty
    private String[] rolesSelected;
}
