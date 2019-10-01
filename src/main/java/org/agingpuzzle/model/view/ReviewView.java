package org.agingpuzzle.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewView {

    private Long entityId;
    private String entityType;
    private String name;
    private int count;
    private int daysWaiting;
}
