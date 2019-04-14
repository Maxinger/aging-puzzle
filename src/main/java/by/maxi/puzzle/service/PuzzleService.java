package by.maxi.puzzle.service;

import by.maxi.puzzle.image.Layout;
import by.maxi.puzzle.image.PuzzleGenerator;
import by.maxi.puzzle.model.Area;
import by.maxi.puzzle.model.PuzzleConfig;
import by.maxi.puzzle.repo.AreaRepository;
import by.maxi.puzzle.repo.PuzzleConfigRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PuzzleService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private PuzzleConfigRepository puzzleConfigRepository;

    @Autowired
    private PuzzleGenerator puzzleGenerator;

    @Getter
    private PuzzleConfig config;

    private Layout layout;

    @PostConstruct
    private void initLayout() {
        setPuzzleConfig(puzzleConfigRepository.findFirstByOrderByCreatedAtDesc());
    }

    public void newPuzzleConfig(PuzzleConfig config) {
        puzzleConfigRepository.save(config);
        setPuzzleConfig(config);
    }

    private void setPuzzleConfig(PuzzleConfig config) {
        this.config = config;
        setLayout(config.getLayout());
    }

    private void setLayout(String layout) {
        Map<Long, Area> areaMap = new HashMap<>();
        areaRepository.findAll().forEach(area -> areaMap.put(area.getId(), area));

        String[] rows = layout.split("\n");

        Optional<Area>[][] areas = new Optional[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            String[] cols = rows[row].substring(1, rows[row].length() - 1)
                    .split("\\|");

            areas[row] = new Optional[cols.length];
            for (int col = 0; col < cols.length; col++) {
                try {
                    Long id = Long.parseLong(cols[col].trim());
                    areas[row][col] = Optional.ofNullable(areaMap.get(id));
                } catch (NumberFormatException e) {
                    areas[row][col] = Optional.empty();
                }
            }
        }

        this.layout = new Layout(areas, 200);
        String outputPath = getClass().getResource("/static/img").getPath() + File.separator + "puzzle.png";
        puzzleGenerator.render(this.layout, outputPath);
    }

    public List<TileView> getPuzzleView() {
        return layout.getPuzzleView((area, coords) -> new TileView(area, coords));
    }

}
