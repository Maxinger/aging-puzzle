package org.agingpuzzle.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.agingpuzzle.model.Member;
import org.agingpuzzle.model.TranslatableEntity;

@AllArgsConstructor
@Getter
public class Membership<T extends TranslatableEntity> {

    private Long id;
    private T entity;
    private String role;
}
