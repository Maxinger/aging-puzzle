package org.agingpuzzle.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.agingpuzzle.model.Organization;
import org.agingpuzzle.model.Project;
import org.agingpuzzle.model.Update;

@AllArgsConstructor
@Getter
public class UpdateView {

    private Update update;
    private Organization organization;
    private Project project;

}
