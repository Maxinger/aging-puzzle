package org.agingpuzzle.web.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UpdateForm extends AbstractForm {

    private Long baseId;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date = LocalDate.now();

    @NotNull
    private String title;

    private String preview;

    @NotNull
    @Size(min = 20)
    private String fullText;

    private Long baseOrganizationId;

    private Long baseProjectId;
}
