package org.agingpuzzle.model.view;

import lombok.Data;
import org.agingpuzzle.model.AbstractEntity;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;

@Data
@Entity
@Subselect("select * from review_summary")
public class ReviewSummary extends AbstractEntity {

    private Long baseEntityId;
    private String entityType;
    private String entityName;
    private String language;
    private int translations;
    private int reviewsCount;
    private int daysWaiting;
}
