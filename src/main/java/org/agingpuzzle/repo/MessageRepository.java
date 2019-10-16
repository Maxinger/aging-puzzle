package org.agingpuzzle.repo;

import org.agingpuzzle.model.Message;
import org.agingpuzzle.model.PuzzleConfig;

import java.util.List;

public interface MessageRepository extends AbstractRepository<PuzzleConfig> {

    List<Message> findAllOrderByType();
}
