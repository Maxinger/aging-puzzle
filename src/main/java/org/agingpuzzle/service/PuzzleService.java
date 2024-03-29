package org.agingpuzzle.service;

import lombok.Getter;
import org.agingpuzzle.image.Layout;
import org.agingpuzzle.image.PuzzleGenerator;
import org.agingpuzzle.model.Area;
import org.agingpuzzle.model.PuzzleConfig;
import org.agingpuzzle.repo.AreaRepository;
import org.agingpuzzle.repo.PuzzleConfigRepository;
import org.agingpuzzle.web.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    @Value("${image.dir}")
    private String imageDir;

    @Getter
    private PuzzleConfig config;

    private Map<String, Layout> layouts = new HashMap<>();

    @PostConstruct
    private void initLayout() {
        PuzzleConfig config = puzzleConfigRepository.findFirstByOrderByCreatedAtDesc();

        if (config != null) {
            setPuzzleConfig(config);
        } else {
            config = new PuzzleConfig();
            config.setLayout(
                    "| 1|  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |"
            );
            config.setCreatedAt(LocalDateTime.now());
            newPuzzleConfig(config);
        }
    }

    public void refreshPuzzle() {
        setLayout(config.getLayout());
    }

    public void newPuzzleConfig(PuzzleConfig config) {
        puzzleConfigRepository.save(config);
        setPuzzleConfig(config);
    }

    private void setPuzzleConfig(PuzzleConfig config) {
        this.config = config;
        setLayout(config.getLayout());
    }

    private void setLayout(String layoutStr) {
        for (String lang : LanguageUtils.getSupportedLanguages()) {

            Map<Long, Area> areaMap = new HashMap<>();
            areaRepository.findAllByLanguage(lang).forEach(area -> areaMap.put(area.getBaseEntity().getId(), area));

            String[] rows = layoutStr.split("\n");

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

            Layout layout = new Layout(areas, 200);
            File outputFile = Paths.get(imageDir, String.format("puzzle_%s.png", lang)).toFile();
            puzzleGenerator.render(layout, outputFile);

            layouts.put(lang, layout);
        }
    }

    public List<TileView> getPuzzleView(String lang) {
        return layouts.get(lang).getPuzzleView((area, coords) -> new TileView(area, coords));
    }

}
