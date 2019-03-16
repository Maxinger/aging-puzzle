package by.maxi.puzzle.service;

import by.maxi.puzzle.image.Area;
import by.maxi.puzzle.image.Layout;
import by.maxi.puzzle.image.PuzzleGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuzzleService {

    private int[][] empty = {
            {0, 2}, {0, 3},
            {1, 1}, {1, 2}, {1, 3},
            {2, 1}, {2, 2}, {2, 3}, {2, 4},
            {3, 2}, {3, 3}
    };

    private Layout layout = new PuzzleGenerator().generateLayout(200, 4, 6, empty);

    public List<Area> getAreas() {
        return layout.getAreas();
    }

}
