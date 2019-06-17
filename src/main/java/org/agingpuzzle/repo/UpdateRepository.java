package org.agingpuzzle.repo;

import org.agingpuzzle.model.Update;

import java.util.List;

public interface UpdateRepository extends TranslatableRepository<Update> {

    List<Update> findTop3ByLanguageOrderByBaseEntityDate(String lang);
}
