package org.agingpuzzle.repo;

import org.agingpuzzle.model.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends AbstractRepository<Message> {

    List<Message> findAllByOrderByTypeAscKeyAscLanguageAsc();

    @Transactional
    default void batchUpdate(List<Message> toSave, List<Message> toDelete) {
        saveAll(toSave);
        deleteAll(toDelete);
    }
}
