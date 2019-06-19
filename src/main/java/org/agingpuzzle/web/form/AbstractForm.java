package org.agingpuzzle.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractForm {

    private Long id;

    public boolean isNew() {
        return id == null;
    }
}
