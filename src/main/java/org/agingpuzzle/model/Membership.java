package org.agingpuzzle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Membership<T extends TranslatableEntity> {

    private Long id;
    private T entity;
    private Member.Role role;
}
