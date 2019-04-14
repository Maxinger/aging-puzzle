package by.maxi.puzzle.repo;

import by.maxi.puzzle.TestConfiguration;
import by.maxi.puzzle.model.PuzzleConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest(classes = TestConfiguration.class)
@RunWith(SpringRunner.class)
public class PuzzleConfigRepositoryTests {

    @Autowired
    PuzzleConfigRepository puzzleConfigRepository;

    @Test
    public void initialConfig() {
        puzzleConfigRepository.deleteAll();
        if (puzzleConfigRepository.count() == 0) {
            PuzzleConfig initialConfig = new PuzzleConfig();
            initialConfig.setLayout(
                    "| 1|  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |"
            );
            initialConfig.setCreatedAt(LocalDateTime.now());
            puzzleConfigRepository.save(initialConfig);
        }

        PuzzleConfig config = new PuzzleConfig();
        config.setLayout("new");
        config.setCreatedAt(LocalDateTime.now());
        puzzleConfigRepository.save(config);

        PuzzleConfig loaded = puzzleConfigRepository.findFirstByOrderByCreatedAtDesc();
        Assert.assertEquals(config.getId(), loaded.getId());

        puzzleConfigRepository.delete(loaded);
    }
}

